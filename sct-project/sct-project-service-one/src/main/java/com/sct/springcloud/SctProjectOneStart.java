package com.sct.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableDiscoveryClient //服务发现
@EnableFeignClients(basePackages= {"com.sct.springcloud"})
@ComponentScan("com.sct.springcloud")
@EnableSwagger2
public class SctProjectOneStart{
	public static void main(String[] args)
	{
		SpringApplication.run(SctProjectOneStart.class, args);
	}
}
