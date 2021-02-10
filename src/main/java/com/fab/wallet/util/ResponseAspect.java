package com.fab.wallet.util;

import com.fab.wallet.Reply;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Level;

@Component
@Aspect
public class ResponseAspect {
    private TokenUtil tokenUtil;
    private final ObjectMapper mapper;
    Logger logger=LoggerFactory.getLogger(ResponseAspect.class);
    @Autowired
    public ResponseAspect(TokenUtil tokenUtil, ObjectMapper mapper) {
        this.tokenUtil = tokenUtil;
        this.mapper = mapper;
    }
    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void logging(JoinPoint joinPoint){
        logger.info(joinPoint.getSignature().getName()+" called");
    }

    @AfterReturning(pointcut = "execution(* com.fab.wallet.user.controller.UserController.userLogin(..))", returning = "reply")
    public void afterPostReturningForLogin(Reply reply) {
        if(reply.getData()!=null)
            reply.setToken(generateToken(reply.getSession()));
    }

    @AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.GetMapping)", returning = "reply")
    public void afterGetReturning(Reply reply) {
        reply.setToken(generateToken(reply.getSession()));
    }

    @AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.PostMapping) && !execution(* com.fab.wallet.user.controller.UserController.userSignUp(..)) && !execution(* com.fab.wallet.user.controller.UserController.userLogin(..))", returning = "reply")
    public void afterPostReturning(Reply reply) {
        reply.setToken(generateToken(reply.getSession()));
    }

    private String generateToken(Map<String,Object> session){
        try {
            return tokenUtil.generateToken(mapper.writeValueAsString(session));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
