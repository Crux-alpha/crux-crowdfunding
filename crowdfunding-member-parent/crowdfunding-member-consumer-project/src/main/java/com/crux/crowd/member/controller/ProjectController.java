package com.crux.crowd.member.controller;

import static com.crux.crowd.common.util.CrowdConstant.*;
import static com.crux.crowd.common.util.CrowdConstant.TipsMessage.*;
import com.crux.crowd.common.util.ResponseResult;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.api.AliyunOSSRemoteService;
import com.crux.crowd.member.entity.vo.ProjectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 项目处理器
 */
@Slf4j
@RestController
public class ProjectController{

	private final AliyunOSSRemoteService ossRemoteService;

	public ProjectController(AliyunOSSRemoteService ossRemoteService){
		Assert.notNull(ossRemoteService, "ossRemoteService为空");
		this.ossRemoteService = ossRemoteService;
	}

	/**
	 * <h3>发起项目的步骤2</h3>
	 * 将项目信息收集，将图片上传到OSS并将URL封装到实体中，将实体保存到Session
	 * @param projectVO 项目信息表单数据
	 * @param headerPicture 项目头图
	 * @param detailPicture 项目详情图片
	 * @return 处理结果
	 */
	@PostMapping("/start/project_submit")
	public ResultEntity<?,?> projectSubmit(ProjectVO projectVO, MultipartFile headerPicture, List<MultipartFile> detailPicture, HttpSession session){
		// 1、上传头图
		if(headerPicture.isEmpty()) return ResultEntity.failure(HEADER_IMAGE_NOT_UPLOAD);
		ResultEntity<String,String> headerPictureUploadResult;
		try(InputStream body = headerPicture.getInputStream()){
			headerPictureUploadResult = ossRemoteService.upload(headerPicture.getOriginalFilename(), body);
		}catch(IOException e){
			log.warn("头图上传失败，原因：{}", e.getMessage());
			e.printStackTrace();
			return ResultEntity.error(SERVER_ERROR);
		}
		// 2、如果失败或错误，结束处理
		if(ResponseResult.FAILURE.equalsResultEntity(headerPictureUploadResult)){
			return ResultEntity.failure(HEADER_IMAGE_UPLOAD_FAILED + headerPictureUploadResult.getMessage());
		}
		if(ResponseResult.ERROR.equalsResultEntity(headerPictureUploadResult)){
			return ResultEntity.error(SERVER_ERROR);
		}
		// 3、获取OSS存储的图片URL保存
		projectVO.setHeaderPicturePath(headerPictureUploadResult.getData().get(AliyunOSSRemoteService.DATA_OSS_FILE_ACCESS_PATH));

		// 4、上传详情图片
		detailPicture.stream().filter(dp -> !dp.isEmpty()).map(dp -> {
			try(InputStream body = dp.getInputStream()){
				return ossRemoteService.upload(dp.getOriginalFilename(), body);
			}catch(IOException e){
				log.warn("项目详情图片上传失败，原因：{}", e.getMessage());
				e.printStackTrace();
				return ResultEntity.<String,String>failure("");
			}}).filter(ResponseResult.SUCCESS::equalsResultEntity)
				.map(result -> result.getData().get(AliyunOSSRemoteService.DATA_OSS_FILE_ACCESS_PATH))
				.forEach(projectVO.getDetailPicturePathList()::add);
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
}
