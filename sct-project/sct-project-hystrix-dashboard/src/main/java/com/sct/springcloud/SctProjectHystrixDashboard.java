package com.sct.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class SctProjectHystrixDashboard
{
	public static void main(String[] args)
	{
		SpringApplication.run(SctProjectHystrixDashboard.class, args);
	}
}
