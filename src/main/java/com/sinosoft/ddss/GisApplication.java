package com.sinosoft.ddss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan("com.sinosoft.ddss.dao")
@EnableEurekaClient
public class GisApplication {
	public static void main(String[] args) {
		SpringApplication.run(GisApplication.class, args);
	}
}
