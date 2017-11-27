package com.greentown.learn.bootdemo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	
	 private final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

	    @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	                             Object handler) throws Exception {
	    	
	    	
	        if (CorsUtils.isPreFlightRequest(request)) {
	            return true;
	        }

	        return true;
	    }


}
