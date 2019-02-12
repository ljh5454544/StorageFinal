package com.jia.storage.component;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class LogAspact {

        private static final Logger log = LoggerFactory.getLogger(LogAspact.class);

        @Pointcut("execution(public * com.jia.storage.controller.*.*.*(..))")
        public void webLog(){}

        @Before("webLog()")
        public void dobefore(JoinPoint joinPoint){
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
        log.info("【注解：Before】------------------切面  before");
        log.info("【注解：Before】浏览器输入的网址=URL : " + request.getRequestURL().toString());
        log.info("【注解：Before】HTTP_METHOD : " + request.getMethod());
        log.info("【注解：Before】IP : " + request.getRemoteAddr());
        log.info("【注解：Before】执行的业务方法名=CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("【注解：Before】业务方法获得的参数=ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info(new Date().toString() + "---方法的返回值 : " + ret);
    }

    //后置异常通知
    @AfterThrowing("webLog()")
    public void throwss(JoinPoint jp){
        log.error(new Date().toString() + "---方法异常时执行.....");
    }

    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint jp){
        log.error(new Date().toString() + "---方法执行完毕.....");
    }

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("webLog()")
    public Object arround(ProceedingJoinPoint pjp) {
        log.info(new Date().toString() + "方法环绕start.....");
        try {
            Object o =  pjp.proceed();
            log.info(new Date().toString() + "方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }


}
