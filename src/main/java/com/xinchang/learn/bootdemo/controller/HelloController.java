package com.xinchang.learn.bootdemo.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xinchang.learn.bootdemo.model.vo.UserVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RefreshScope
public class HelloController {
	
	@Resource
	RedisTemplate<String,String>  redisTemplate;
	
	@Value("${msg}")
	private String msg;
	
	@GetMapping(value="/api/hello")
	@ApiOperation("获取")
	public String sayHello() {
		
		redisTemplate.opsForValue().set("test", "test01");
		
		return "Swagger Hello World:"+msg;
	}
	
	
	
	@ApiOperation("添加")
	@PostMapping(value = "/api/hello/helloPut")
	public String putHello(@RequestBody UserVO uservo) {
		return "Swagger Hello World";
	}
	
	
	
}
