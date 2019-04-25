package com.bosssoft.formdesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableCircuitBreaker
@EnableHystrix//开启熔断
@EnableEurekaClient
public class FormdesignApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormdesignApplication.class, args);
	}

	//使用的是负载均衡
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate()
	{
		return  new RestTemplate();
	}


}

