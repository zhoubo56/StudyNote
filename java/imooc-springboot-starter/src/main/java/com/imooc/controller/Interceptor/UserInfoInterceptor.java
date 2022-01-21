package com.imooc.controller.Interceptor;

import com.imooc.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInfoInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        var userId = request.getHeader("userId");
        var token = request.getHeader("token");
        if (!StringUtils.hasText(userId) || !StringUtils.hasText(token)) {
            logger.error("user info is missing");
            throw AuthException.Unauthorized("user info is missing");
        }

        if (!userId.equals("1001") || !token.equalsIgnoreCase("abc123")) {
            logger.error("user:" + userId + " don't have permission");
            throw AuthException.Forbidden("user:" + userId + " don't have permission");
        }


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
