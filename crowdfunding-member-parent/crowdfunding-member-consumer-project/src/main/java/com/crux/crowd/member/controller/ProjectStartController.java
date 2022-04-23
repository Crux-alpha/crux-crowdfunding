package com.crux.crowd.member.controller;

import static com.crux.crowd.common.util.CrowdConstant.*;
import static com.crux.crowd.common.util.CrowdConstant.TipsMessage.*;
import com.crux.crowd.common.util.ResponseResult;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.api.AliyunOSSRemoteService;
import com.crux.crowd.member.api.DataSourceRemoteService;
import com.crux.crowd.member.entity.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * 项目处理器
 */
@Slf4j
@RestController
@RequestMapping("/start")
public class ProjectStartController{

	private final AliyunOSSRemoteService ossRemoteService;
	private final DataSourceRemoteService dataSourceRemoteService;

	public ProjectStartController(AliyunOSSRemoteService ossRemoteService, DataSourceRemoteService dataSourceRemoteService){
		Assert.notNull(ossRemoteService, "ossRemoteService为空");
		Assert.notNull(dataSourceRemoteService, "dataSourceRemoteService为空");
		this.ossRemoteService = ossRemoteService;
		this.dataSourceRemoteService = dataSourceRemoteService;
	}

	/**
	 * <h3>发起项目的步骤2</h3>
	 * 将项目信息收集，将图片上传到OSS并将URL封装到实体中，将实体保存到Session
	 * @param projectVO 项目信息表单数据
	 * @param headerPicture 项目头图
	 * @param detailPicture 项目详情图片
	 * @return 处理结果
	 */
	@PostMapping("/project_submit")
	public ResultEntity<String,?> projectSubmit(ProjectVO projectVO, MultipartFile headerPicture, List<MultipartFile> detailPicture, HttpSession session){
		// 1、上传头图
		if(headerPicture.isEmpty()) return ResultEntity.failure(HEADER_IMAGE_NOT_UPLOAD);
		ResultEntity<String,String> uploadResult = ossRemoteService.uploadMultipartFile(headerPicture);
		if(!ResponseResult.SUCCESS.equalsResultEntity(uploadResult)) return uploadResult;
		// 3、获取OSS存储的图片URL保存
		projectVO.setHeaderPicturePath(uploadResult.getData().get(AliyunOSSRemoteService.DATA_OSS_FILE_ACCESS_PATH));

		// 4、上传详情图片
		Optional.ofNullable(detailPicture).ifPresent(list ->
				list.stream().filter(dp -> !dp.isEmpty()).map(ossRemoteService::uploadMultipartFile).filter(ResponseResult.SUCCESS::equalsResultEntity)
						.map(result -> result.getData().get(AliyunOSSRemoteService.DATA_OSS_FILE_ACCESS_PATH))
						.forEach(projectVO.getDetailPicturePathList()::add));
		/* 传统foreach遍历
		for(MultipartFile dp : detailPicture){
			if(dp.isEmpty()) continue;
			try{
				ResultEntity<String,String> detailPictureUploadResult = ossRemoteService.upload(dp.getOriginalFilename(), dp.getInputStream());
				if(detailPictureUploadResult.getResult() == ResponseResult.SUCCESS){
					projectVO.getDetailPicturePathList().add(detailPictureUploadResult.getData().get(AliyunOSSRemoteService.DATA_OSS_FILE_ACCESS_PATH));
				}
			}catch(IOException e){
				log.warn("项目详情图片上传失败，原因：{}", e.getMessage());
				e.printStackTrace();
			}
		}
		*/

		// 5、保存到session，处理成功
		session.setAttribute(SESSION_ATTRIBUTE_PROJECT_DATA, projectVO);
		return ResultEntity.success();
	}

	/**
	 * <h3>发起项目步骤3</h3>
	 * 将回报设置信息收集，保存到projectVO中
	 * @param returnVO 一个回报设置信息实体
	 * @param describePicture 说明图片
	 * @return 处理结果
	 */
	@PostMapping("/return_submit")
	public ResultEntity<?,?> returnSubmit(ReturnVO returnVO, MultipartFile describePicture, HttpSession session){
		// 1、获取projectVO
		Object attribute = session.getAttribute(SESSION_ATTRIBUTE_PROJECT_DATA);
		// 2、如果session中不存在保存的projectVO。应当刷新页面
		if(!(attribute instanceof ProjectVO)) return ResultEntity.failure(HTML_FAILURE);
		ProjectVO projectVO = (ProjectVO)attribute;
		session.removeAttribute(SESSION_ATTRIBUTE_PROJECT_DATA);

		// 3、上传说明图片
		ResultEntity<String,String> uploadResult = ossRemoteService.uploadMultipartFile(describePicture);
		// 如果上传失败，将projectVO的ReturnVOList清空
		if(!ResponseResult.SUCCESS.equalsResultEntity(uploadResult)){
			projectVO.getReturnVOList().clear();
			session.setAttribute(SESSION_ATTRIBUTE_PROJECT_DATA, projectVO);
			return uploadResult;
		}
		// 4、如果上传成功，设置returnVO的path
		returnVO.setDescribePicPath(uploadResult.getData().get(AliyunOSSRemoteService.DATA_OSS_FILE_ACCESS_PATH));
		// 5、将returnVO添加到projectVO中
		projectVO.getReturnVOList().add(returnVO);

		// 6、更新session数据时，必须先删除再设置
		session.setAttribute(SESSION_ATTRIBUTE_PROJECT_DATA, projectVO);

		return ResultEntity.success();
	}

	/**
	 * <h3>发起项目步骤4</h3>
	 * 将发起人提交信息保存到ProjectVO，将ProjectVO发送给数据库服务保存
	 * @param memberConfirmInfoVO 发起人提交信息
	 * @param session 获取member id
	 * @return 处理结果
	 */
	@PostMapping("/confirm_submit")
	public ResultEntity<?,?> confirmSubmit(MemberConfirmInfoVO memberConfirmInfoVO, HttpSession session){
		// 1、获取projectVO
		Object project = session.getAttribute(SESSION_ATTRIBUTE_PROJECT_DATA);
		Object member = session.getAttribute(SESSION_ATTRIBUTE_MEMBER_INFO);

		// 2、如果session中不存在保存的projectVO。应当刷新页面
		if(!(project instanceof ProjectVO) || !(member instanceof MemberInfoVO)) return ResultEntity.failure(HTML_FAILURE);
		ProjectVO projectVO = (ProjectVO)project;
		Integer memberId = ((MemberInfoVO)member).getId();

		memberConfirmInfoVO.setMemberId(memberId);
		projectVO.getMemberLaunchInfoVO().setMemberId(memberId);
		projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

		// 3、保存projectVO
		ResultEntity<?,?> result = dataSourceRemoteService.saveProject(projectVO);
		if(!ResponseResult.SUCCESS.equalsResultEntity(result)) return result;

		session.removeAttribute(SESSION_ATTRIBUTE_PROJECT_DATA);
		return ResultEntity.success("提交成功");
	}

	/**
	 * 步骤4回到步骤3。将保存的回报设置项删除
	 * @return 处理结果
	 */
	@PostMapping("/return_rollback")
	public ResultEntity<?,?> returnRollback(HttpSession session){
		Object project = session.getAttribute(SESSION_ATTRIBUTE_PROJECT_DATA);
		if(project instanceof ProjectVO){
			((ProjectVO)project).getReturnVOList().clear();
			session.removeAttribute(SESSION_ATTRIBUTE_PROJECT_DATA);
			session.setAttribute(SESSION_ATTRIBUTE_PROJECT_DATA, project);
			return ResultEntity.success();
		}else{
			return ResultEntity.failure(HTML_FAILURE);
		}
	}
}
