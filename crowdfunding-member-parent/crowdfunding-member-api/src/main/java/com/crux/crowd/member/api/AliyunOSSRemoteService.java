package com.crux.crowd.member.api;

import com.crux.crowd.common.util.ResultEntity;

/**
 * 阿里云OSS远程服务接口
 * @since 2022/04/06
 */
public interface AliyunOSSRemoteService{

	/**
	 * 使用阿里云OSS的SDK将文件上传到OSS服务器中
	 * @param originalName 原始的文件名
	 * @param body 文件输入流
	 * @return 上传结果
	 */
	ResultEntity<String,String> upload(String originalName, java.io.InputStream body);
}
