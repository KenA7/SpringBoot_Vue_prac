package com.kend.backend.controller;

import com.kend.backend.common.lang.Result;
import com.kend.backend.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/test")
    public Result test(){
        return Result.succ(sysUserService.list());
    }


    @GetMapping("/test/pass")
    public Result pass(){
        String password = bCryptPasswordEncoder.encode("1234");
        bCryptPasswordEncoder.matches("1234",password);
        return Result.succ(password);
    }
}
