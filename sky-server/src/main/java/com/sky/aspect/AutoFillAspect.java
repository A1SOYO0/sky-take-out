package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    // 缓存 Method 对象
    private final Map<String, Method> methodCache = new ConcurrentHashMap<>();

    // 定义切点：拦截带有 @AutoFill 注解的方法
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    // 前置通知：在方法执行前填充字段
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充");

        // 获取方法签名和注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];

        // 填充字段
        LocalDateTime now = LocalDateTime.now();
        long currentId = BaseContext.getCurrentId();

        try {
            if (operationType == OperationType.INSERT) {
                invokeMethod(entity, AutoFillConstant.SET_CREATE_TIME, now);
                invokeMethod(entity, AutoFillConstant.SET_CREATE_USER, currentId);
            }
            invokeMethod(entity, AutoFillConstant.SET_UPDATE_TIME, now);
            invokeMethod(entity, AutoFillConstant.SET_UPDATE_USER, currentId);
        } catch (Exception e) {
            log.error("公共字段自动填充失败", e);
            throw new RuntimeException("公共字段自动填充失败", e);
        }
    }

    // 反射调用方法
    private void invokeMethod(Object entity, String methodName, Object value) throws Exception {
        String cacheKey = entity.getClass().getName() + "." + methodName;
        Method method = methodCache.get(cacheKey);

        if (method == null) {
            try {
                method = entity.getClass().getDeclaredMethod(methodName, value.getClass());
                methodCache.put(cacheKey, method);
            } catch (NoSuchMethodException e) {
                log.warn("方法 {} 不存在，跳过填充", methodName);
                return;
            }
        }

        // 调用方法
        method.invoke(entity, value);
    }
}