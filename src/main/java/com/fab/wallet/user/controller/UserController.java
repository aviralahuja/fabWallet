package com.fab.wallet.user.controller;

import com.auth0.jwt.interfaces.Claim;
import com.fab.wallet.Reply;
import com.fab.wallet.user.dto.UserProfileDto;
import com.fab.wallet.user.service.UserService;
import com.fab.wallet.util.Const;
import com.fab.wallet.util.TokenUtil;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;
    private final TokenUtil tokenUtil;

    @Autowired
    public UserController(UserService userService, TokenUtil tokenUtil) {
        this.userService = userService;
        this.tokenUtil = tokenUtil;
    }

    @PostMapping("user/signUp")
    public Reply userSignUp(@RequestBody @NotNull @Valid UserProfileDto userProfileDto){
        Reply reply=new Reply();
        boolean signUpResult=userService.addUpdateUser(userProfileDto,true);
        if(signUpResult){
            reply.setMessage("USER SIGN UP SUCCESSFULL", Const.MessageType.INFO);
        }else{
            reply.setMessage("USER SIGN UP FAILED", Const.MessageType.ERROR);
        }
        return reply;
    }

    @PostMapping("user/login")
    public Reply userLogin(@RequestBody @NotNull @Valid UserProfileDto userProfileDto){
        Reply reply=new Reply();
        userService.loginUser(userProfileDto,reply);
        reply.addToSession(Const.SessionEnum.LOGIN_ID.getKey(),userProfileDto.getUserId());
        return reply;
    }

    @PostMapping("api/user/update")
    public Reply updateUserProfile(@RequestBody @NotNull @Valid UserProfileDto userProfileDto,@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        Reply reply=tokenUtil.getReply(token);
        userProfileDto.setUserId((String)reply.getFromSession(Const.SessionEnum.LOGIN_ID.getKey())); // as user can only update his profile
        boolean signUpResult=userService.addUpdateUser(userProfileDto,false);
        if(signUpResult){
            reply.setMessage("USER PROFILE UPDATED SUCCESSFULLY", Const.MessageType.INFO);
        }else{
            reply.setMessage("USER PROFILE UPDATION FAILED", Const.MessageType.ERROR);
        }
        return reply;
    }
    @GetMapping("api/user/profile")
    public Reply getUserProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        Reply reply=tokenUtil.getReply(token);
        UserProfileDto userProfileDto=userService.getUserProfile(reply.getFromSession(Const.SessionEnum.LOGIN_ID.getKey()).toString());
        if(userProfileDto==null){
            reply.setMessage("ERROR OCCURED!! TRY AFTER SOME TIME", Const.MessageType.ERROR);
        }else{
            reply.setData(userProfileDto);
        }
        return reply;
    }
}
