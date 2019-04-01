package com.sct.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableDiscoveryClient //服务发现
@EnableFeignClients
//@ComponentScan("com.sct.springcloud")
@EnableCircuitBreaker//对hystrixR熔断机制的支持
//@RibbonClient(name="sct-project-service-one",configuration=MySelfRule.class)//自定义的负载均衡策略
public class SctProjectTwoStart{
	public static void main(String[] args)
	{
		SpringApplication.run(SctProjectTwoStart.class, args);
	}
}
