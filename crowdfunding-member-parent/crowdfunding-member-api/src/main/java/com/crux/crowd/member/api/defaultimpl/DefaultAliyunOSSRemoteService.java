package com.crux.crowd.member.api.defaultimpl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.crux.crowd.common.util.ResultEntity;
import com.crux.crowd.member.api.AliyunOSSRemoteService;
import com.crux.crowd.member.api.OSSProviderProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service("aliyunOSSRemoteService")
@ConditionalOnBean(OSSProviderProperties.class)
public class DefaultAliyunOSSRemoteService implements AliyunOSSRemoteService{

	private final OSSProviderProperties ossProviderProperties;

	public DefaultAliyunOSSRemoteService(OSSProviderProperties ossProviderProperties){
		this.ossProviderProperties = ossProviderProperties;
	}

	@Override
	public ResultEntity<String,String> upload(String originalName, InputStream body){
		String accessKeyId = ossProviderProperties.getAccessKeyId();
		String accessKeySecret = ossProviderProperties.getAccessKeySecret();
		String endpoint = ossProviderProperties.getEndpoint();

		// 1、创建 OSSClient 实例
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		// 2、生成上传文件的目录
		String folderName = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		/* 3、生成上传文件在 OSS 服务器上保存时的文件名
			原始文件名：beautfulgirl.jpg
			生成文件名：wer234234efwer235346457dfswet346235.jpg
			使用 UUID 生成文件主体名称
		 */
		String fileMainName = UUID.randomUUID().toString().replace("-", "");
		// 4、从原始文件名中获取文件扩展名
		String extensionName = originalName.substring(originalName.lastIndexOf("."));
		// 5、使用目录、文件主体名称、文件扩展名称拼接得到对象名称
		String objectName = folderName + "/" + fileMainName + extensionName;

		try{
			// 6、调用 OSS 客户端对象的方法上传文件并获取响应结果数据
			String bucketName = ossProviderProperties.getBucketName();
			PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, body);
			// 7、从响应结果中获取具体响应消息
			ResponseMessage responseMessage = putObjectResult.getResponse();
			// 8、根据响应状态码判断请求是否成功。如果相应消息为空，则上传成功
			if(Objects.isNull(responseMessage)){
				String bucketDomain = ossProviderProperties.getBucketDomain();
				// 拼接访问刚刚上传的文件的路径
				String ossFileAccessPath = bucketDomain + "/" + objectName;
				// 当前方法返回成功
				return ResultEntity.success(Collections.singletonMap(DATA_OSS_FILE_ACCESS_PATH, ossFileAccessPath));
			}else{
				// 获取响应状态码
				int statusCode = responseMessage.getStatusCode();
				// 如果请求没有成功，获取错误消息
				String errorMessage = responseMessage.getErrorResponseAsString();
				// 当前方法返回失败
				Map<String,String> data = new HashMap<>();
				data.put(DATA_STATUS_CODE, Integer.toString(statusCode));
				data.put(DATA_ERROR_MESSAGE, errorMessage);
				return ResultEntity.failure("上传失败", data);
			}
		}catch(Exception e){
			log.error("文件上传异常，原因：{}", e.getMessage());
			e.printStackTrace();
			return ResultEntity.error(e.getMessage());
		}finally{
			// oss使用完毕后需要关闭
			Optional.ofNullable(ossClient).ifPresent(OSS::shutdown);
		}
	}
}
