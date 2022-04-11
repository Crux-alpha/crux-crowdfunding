package com.crux.crowd.member.api;

import static com.crux.crowd.common.util.CrowdConstant.*;
import com.crux.crowd.common.util.ResponseResult;
import com.crux.crowd.common.util.ResultEntity;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

/**
 * 阿里云OSS远程服务接口
 * @since 2022/04/06
 */
public interface AliyunOSSRemoteService{

	String DATA_OSS_FILE_ACCESS_PATH = "ossFileAccessPath";
	String DATA_STATUS_CODE = "statusCode";
	String DATA_ERROR_MESSAGE = "errorMessage";
	/**
	 * 使用阿里云OSS的SDK将文件上传到OSS服务器中
	 * @param originalName 原始的文件名
	 * @param body 文件输入流
	 * @return 上传结果。结果有SUCCESS、ERROR、FAILURE
	 */
	ResultEntity<String,String> upload(String originalName, InputStream body);

	/**
	 * 上传文件并处理返回结果
	 * @param multipartFile 封装的文件项
	 * @return 处理结果
	 */
	default ResultEntity<String,String> uploadMultipartFile(MultipartFile multipartFile){
		ResultEntity<String,String> uploadResult;
		try(InputStream body = multipartFile.getInputStream()){
			uploadResult = upload(multipartFile.getOriginalFilename(), body);
		}catch(IOException e){
			LoggerFactory.getLogger(getClass()).error("图片上传失败，原因：{}", e.getMessage());
			e.printStackTrace();
			return ResultEntity.error(TipsMessage.SERVER_ERROR, Collections.singletonMap(MESSAGE, e.getMessage()));
		}
		if(ResponseResult.FAILURE.equalsResultEntity(uploadResult)){
			return ResultEntity.failure(TipsMessage.HEADER_IMAGE_UPLOAD_FAILED + uploadResult.getMessage());
		}
		if(ResponseResult.ERROR.equalsResultEntity(uploadResult)){
			return ResultEntity.error(TipsMessage.SERVER_ERROR);
		}
		return uploadResult;
	}
}
