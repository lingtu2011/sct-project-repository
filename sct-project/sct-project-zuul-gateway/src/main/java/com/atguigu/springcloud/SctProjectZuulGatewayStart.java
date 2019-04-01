package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class SctProjectZuulGatewayStart
{
	public static void main(String[] args)
	{
		SpringApplication.run(SctProjectZuulGatewayStart.class, args);
	}
}
