package com.greentown.learn.bootdemo.controller;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.greentown.learn.bootdemo.model.vo.UserVO;

@RestController
public class HelloController {
	
	@Resource
	RedisTemplate<String,String>  redisTemplate;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/hello")
	public String sayHello() {
		
		redisTemplate.opsForValue().set("test", "test01");
		
		return "Swagger Hello World";
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/api/hello/helloPut")
	public String putHello(@RequestBody UserVO uservo) {
		return "Swagger Hello World";
	}
	
	
	
}
