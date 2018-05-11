package com.xinchang.learn.bootdemo.controller;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.bingoohuang.patchca.color.SingleColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import com.github.bingoohuang.patchca.word.RandomWordFactory;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

	@Resource
	RedisTemplate<String,String>  redisTemplate;
	
	
	 /**
     * 生成验证码
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
    public void getCaptcha(HttpServletResponse response, HttpServletRequest request) {

        String captcha = null;
        ConfigurableCaptchaService service = new ConfigurableCaptchaService();
        service.setColorFactory(new SingleColorFactory(new Color(98, 150, 226)));
        service.setFilterFactory(new CurvesRippleFilterFactory(service.getColorFactory()));
        service.setHeight(50);
        service.setWidth(150);

        RandomWordFactory wordFactory = new RandomWordFactory();
        wordFactory.setMinLength(5);
        wordFactory.setMaxLength(6);
        service.setWordFactory(wordFactory);

        response.setContentType("image/png");
        response.setHeader("Pragma", "No-cache");
        // 获取输出流
        OutputStream outputStream = null;
        String key=null;
        try {
            outputStream = response.getOutputStream();
            
            captcha = EncoderHelper.getChallangeAndWriteImage(service, "png", outputStream);
            
            key="CAPTCHA:"+captcha;
            
            logger.info("key:{},captcha:{}",key,captcha);
     
            redisTemplate.opsForValue().set(key, captcha, 10000, TimeUnit.MILLISECONDS);
            
            outputStream.flush();
            
        } catch (IOException e) {
        	
        	redisTemplate.delete(key);
            
        }
        finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    
                }
            }
        }
    }
}
