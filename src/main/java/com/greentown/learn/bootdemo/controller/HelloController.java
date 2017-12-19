package com.greentown.learn.bootdemo.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.greentown.learn.bootdemo.model.vo.UserVO;

import io.swagger.annotations.ApiOperation;

@RestController
@RefreshScope
public class HelloController {
	
	@Resource
	RedisTemplate<String,String>  redisTemplate;
	
	@Value("${msg}")
	private String msg;
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/hello")
	@ApiOperation("获取")
	public String sayHello() {
		
		redisTemplate.opsForValue().set("test", "test01");
		
		return "Swagger Hello World:"+msg;
	}
	
	
	@ApiOperation("获取用户信息")
	@RequestMapping(method = RequestMethod.GET, value = "/api/hello/user")
	public UserVO getUser() {
		UserVO user=new UserVO();
		user.setBroker("吕自强");
		user.setHouseId(12222L);
		redisTemplate.opsForValue().set("test", "test01");
		return user;
	}
	
	
	@ApiOperation("添加")
	@RequestMapping(method = RequestMethod.POST, value = "/api/hello/helloPut")
	public String putHello(@RequestBody UserVO uservo) {
		return "Swagger Hello World";
	}
	
	
	
}
