package com.crux.crowd.member.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 对象存储OSS服务配置
 * @since 2022/04/06
 */
@Data
@AllArgsConstructor
@ConfigurationProperties("aliyun.oss")
public class OSSProviderProperties{

	private String accessKeyId;
	private String accessKeySecret;
	private String bucketName;
	private String bucketDomain;
	private String endpoint;

	public OSSProviderProperties(){}
}
