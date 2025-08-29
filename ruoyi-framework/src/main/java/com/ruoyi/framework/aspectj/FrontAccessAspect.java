package com.ruoyi.framework.aspectj;


import com.ruoyi.common.annotation.FrontAccess;
import com.ruoyi.common.config.WhiteListConfig;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.framework.web.service.TokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class FrontAccessAspect {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WhiteListConfig whiteListConfig;

    @Around("execution(* com.ruoyi.web.controller..*(..))")
    public Object checkFrontAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法和注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        FrontAccess methodAnnotation = method.getAnnotation(FrontAccess.class);
        FrontAccess classAnnotation = method.getDeclaringClass().getAnnotation(FrontAccess.class);
        boolean isFrontAccess = (methodAnnotation != null || classAnnotation != null);

        // 获取请求和用户
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        LoginUser loginUser = tokenService.getLoginUser(request);

        String uri = request.getRequestURI();
        if (whiteListConfig.getWhiteList().contains(uri)) {
            return joinPoint.proceed();
        }

        // 前台接口要求 mUser 存在
        if (isFrontAccess) {
            if (loginUser.getmUser() == null) {
//                throw new ServiceException("无权访问前端接口");
            }
        } else {
            if (loginUser.getUser() == null) {
                throw new ServiceException("无权访问后台接口");
            }
        }

        return joinPoint.proceed();
    }

}
