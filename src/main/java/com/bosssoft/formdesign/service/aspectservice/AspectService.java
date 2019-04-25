package com.bosssoft.formdesign.service.aspectservice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
  * @类描述： 异常的统一处理
  * @类名称： AspectService
  * @创建人： fw
  * @创建时间： 2018-12-25
  */
@Aspect
@Component
public class AspectService {

    @AfterThrowing(pointcut="execution(* com.bosssoft.formdesign.service.*.*(..))",throwing="exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception){
        String methodName=joinPoint.getSignature().getName();
        System.out.println("method:  "+methodName+"occurs exception:"+exception);
    }

}
