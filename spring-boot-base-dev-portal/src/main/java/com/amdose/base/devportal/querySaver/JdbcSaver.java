package com.amdose.base.devportal.querySaver;

import com.amdose.base.devportal.cachdata.ApiModelCircularMap;
import com.amdose.base.devportal.models.JdbcApiModel;
import com.amdose.base.devportal.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alaa Jawhar
 */
@Component
@Aspect
@Slf4j
public class JdbcSaver {

    @Before("execution(* org.springframework.jdbc.core.JdbcTemplate.*(..))")
    public void beforeJdbc(JoinPoint joinPoint) throws Throwable {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        List<StackTraceElement> collect = Arrays.stream(stackTrace)
                .filter(stackTraceElement -> stackTraceElement.getClassName().contains("com.amdose.promptlib.devportal"))
                .collect(Collectors.toList());
        System.out.println("caller:  " + collect.get(1));


        Object[] args = joinPoint.getArgs();
        JdbcApiModel jdbcApiModel = new JdbcApiModel();
        jdbcApiModel.setPackageName(collect.get(1).toString());
        jdbcApiModel.setJdbcTemplateFunctionName(joinPoint.getSignature().getName());
        jdbcApiModel.setJdbcTemplateArgs(JsonUtil.convertToString(args));
        ApiModelCircularMap.getCurrentApiModel().addJdbcApiModel(jdbcApiModel);
    }

    @AfterReturning(pointcut = "execution(* org.springframework.jdbc.core.JdbcTemplate.*(..))", returning = "result")
    public void afterJdbcReturns(JoinPoint joinPoint, Object result) {
        int jdbcApiModelListLength = ApiModelCircularMap.getCurrentApiModel().getJdbcApiModelList().size();
        ApiModelCircularMap.getCurrentApiModel().getJdbcApiModelList().get(jdbcApiModelListLength - 1).setJdbcTemplateResult(JsonUtil.convertToString(JsonUtil.convertToString(result)));
    }

    @AfterThrowing(pointcut = "execution(* org.springframework.jdbc.core.JdbcTemplate.*(..))", throwing = "ex")
    public void afterJdbcThrow(JoinPoint joinPoint, Exception ex) {
        int jdbcApiModelListLength = ApiModelCircularMap.getCurrentApiModel().getJdbcApiModelList().size();
        ApiModelCircularMap.getCurrentApiModel().getJdbcApiModelList().get(jdbcApiModelListLength - 1).setJdbcTemplateResult(JsonUtil.getStackTraceAsString(ex));
    }

}
