package com.sct.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableDiscoveryClient //服务发现
@EnableFeignClients(basePackages= {"com.sct.springcloud"})
@ComponentScan("com.sct.springcloud")
public class SctProjectThreeStart{
	public static void main(String[] args)
	{
		SpringApplication.run(SctProjectThreeStart.class, args);
	}
}
