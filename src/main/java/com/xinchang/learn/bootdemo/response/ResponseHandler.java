package com.xinchang.learn.bootdemo.response;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.xinchang.common.exception.BusinessLogicException;
import com.xinchang.common.response.ResponseEntity;
import com.xinchang.common.web.CustomHttpHeaderUtil;
import com.xinchang.common.web.ExceptionConstants;
import com.xinchang.common.web.GlobalConstants;
import com.xinchang.common.web.JsonpFastJsonHttpMessageConverter;
import com.xinchang.common.web.ExceptionConstants.ResultEnums;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 这个类用来handle整个系统的异常或正常返回对象，包装成PortalResponse对象返回给调用方
 * 
 * @author Administrator
 *
 */
@Component
@ControllerAdvice(basePackages = { "com.greentown.learn.bootdemo.controller"})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseHandler implements HandlerExceptionResolver, ResponseBodyAdvice<Object> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    /**
     * 这里定义的是需要返回详细错误的异常
     */
    private Map<String, ResultEnums> exceptionMappings = new HashMap<>();

    @PostConstruct
    private void initExceptionMappings() {
        // Springmvc的一些异常
        exceptionMappings.put(NoSuchRequestHandlingMethodException.class.getName(),
            ExceptionConstants.SYSTEM_NO_SUCH_REQUEST_HANDLING_METHOD_EXCEPTION);
        exceptionMappings.put(HttpRequestMethodNotSupportedException.class.getName(),
            ExceptionConstants.SYSTEM_HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION);
        exceptionMappings.put(HttpMediaTypeNotSupportedException.class.getName(),
            ExceptionConstants.SYSTEM_HTTP_MEDIATYPE_NOT_SUPPORTED_EXCEPTION);
        exceptionMappings.put(HttpMediaTypeNotAcceptableException.class.getName(),
            ExceptionConstants.SYSTEM_HTTP_MEDIATYPE_NOT_ACCEPTABLE_EXCEPTION);
        exceptionMappings.put(MissingPathVariableException.class.getName(),
            ExceptionConstants.SYSTEM_MISSING_PATHVARIABLE_EXCEPTION);
        exceptionMappings.put(MissingServletRequestParameterException.class.getName(),
            ExceptionConstants.SYSTEM_MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION);
        exceptionMappings.put(ServletRequestBindingException.class.getName(),
            ExceptionConstants.SYSTEM_SERVLET_REQUEST_BINDING_EXCEPTION);
        exceptionMappings.put(ConversionNotSupportedException.class.getName(),
            ExceptionConstants.SYSTEM_CONVERSION_NOT_SUPPORTED_EXCEPTION);
        exceptionMappings.put(TypeMismatchException.class.getName(),
            ExceptionConstants.SYSTEM_TYPE_MISMATCH_EXCEPTION);
        exceptionMappings.put(HttpMessageNotReadableException.class.getName(),
            ExceptionConstants.SYSTEM_HTTP_MESSAGE_NOT_READABLE_EXCEPTION);
        exceptionMappings.put(HttpMessageNotWritableException.class.getName(),
            ExceptionConstants.SYSTEM_HTTP_MESSAGE_NOT_WRITABLE_EXCEPTION);
        exceptionMappings.put(MethodArgumentNotValidException.class.getName(),
            ExceptionConstants.SYSTEM_METHOD_ARGUMENT_NOT_VALID_EXCEPTION);
        exceptionMappings.put(MissingServletRequestPartException.class.getName(),
            ExceptionConstants.SYSTEM_MISSING_SERVLET_REQUEST_PART_EXCEPTION);
        exceptionMappings.put(BindException.class.getName(),
            ExceptionConstants.SYSTEM_BIND_EXCEPTION);
        exceptionMappings.put(NoHandlerFoundException.class.getName(),
            ExceptionConstants.SYSTEM_NO_HANDLER_FOUND_EXCEPTION);
        // Springmvc的一些异常
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        ResultEnums resultEnum;
        Object extra = null;

        if (ex instanceof BusinessLogicException) {
            BusinessLogicException portalException = (BusinessLogicException) ex;
            resultEnum = portalException.getResultEnum();
            extra = portalException.getExtra();
        }
        else {
            logger.error(
                "IP={}, URL={}, QueryString={}, Project-ID={}, Client-Version={}, Client-Agent={}",
                CustomHttpHeaderUtil.getRemoteIp(request), request.getRequestURI(),
                request.getQueryString(),
                StringUtils.defaultString(CustomHttpHeaderUtil.getProjectId(request)),
                StringUtils.defaultString(CustomHttpHeaderUtil.getClientVersion(request)),
                StringUtils.defaultString(CustomHttpHeaderUtil.getClientAgent(request)));
            logger.error(ex.getMessage(), ex);
            ex.printStackTrace();
            if ((resultEnum = exceptionMappings.get(ex.getClass().getName())) != null) {

            }
            else if (ex instanceof SQLException || ex instanceof DataAccessException) {
                resultEnum = ExceptionConstants.SYSTEM_SQL_EXCEPTION;
            }
            else if (ex instanceof UndeclaredThrowableException) {
                resultEnum = ExceptionConstants.SYSTEM_REMOTE_SERVICE_NOT_FOUND;
            }
            else if (StringUtils.startsWith(ex.getMessage(), "TimeOutException-Timeout")) {
                resultEnum = ExceptionConstants.SYSTEM_CALL_REMOTE_SERVICE_TIME_OUT;
            }
            else if (ex instanceof IllegalStateException && StringUtils.endsWith(ex.getMessage(),
                "Consider declaring it as object wrapper for the corresponding primitive type.")) {
                resultEnum = ExceptionConstants.SYSTEM_MISSING_REQUEST_PARAMETERS;
            }
            else {
                resultEnum = ExceptionConstants.SYSTEM_OTHER_EXCEPTION;
            }
        }

        ResponseEntity portalResponseEntity = new ResponseEntity(resultEnum, extra);
        String jsonpFunction = request.getParameter(GlobalConstants.JSONP_FUNCTION_KEY);
        if (jsonpFunction != null) {
            response.setContentType("application/javascript");
            portalResponseEntity.setJsonpFunction(jsonpFunction);
        }

        CustomHttpHeaderUtil.setResultCode(response,
            String.valueOf(portalResponseEntity.getErrorCode()));
        CustomHttpHeaderUtil.setSerialnum(response, CustomHttpHeaderUtil.getSerialnum(request));

        modelAndView.addObject(portalResponseEntity);

        return modelAndView;
    }

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return JsonpFastJsonHttpMessageConverter.class.isAssignableFrom(converterType);
    }

    /**
     * 将接口返回的对象统一包装成PortalResponseEntity类的实例并增加jsonp的支持
     * @see org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice#beforeBodyWrite(java.lang.Object, org.springframework.core.MethodParameter, org.springframework.http.MediaType, java.lang.Class, org.springframework.http.server.ServerHttpRequest, org.springframework.http.server.ServerHttpResponse)
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        ResponseEntity portalResponseEntity = new ResponseEntity(body);

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request)
            .getServletRequest();
        String jsonpFunction = servletRequest.getParameter(GlobalConstants.JSONP_FUNCTION_KEY);
        if (jsonpFunction != null) {
            response.getHeaders().setContentType(new MediaType("application", "javascript"));
            portalResponseEntity.setJsonpFunction(jsonpFunction);
        }

        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response)
            .getServletResponse();
        CustomHttpHeaderUtil.setResultCode(servletResponse,
            String.valueOf(portalResponseEntity.getErrorCode()));
        CustomHttpHeaderUtil.setSerialnum(servletResponse,
            CustomHttpHeaderUtil.getSerialnum(servletRequest));

        return portalResponseEntity;
    }
}
