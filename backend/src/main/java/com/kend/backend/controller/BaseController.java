package com.kend.backend.controller;

import com.kend.backend.service.*;
import com.kend.backend.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    HttpServletRequest req;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    ISysRoleService sysRoleService;

    @Autowired
    ISysMenuService sysMenuService;

    @Autowired
    ISysRoleMenuService sysRoleMenuService;

    @Autowired
    ISysUserRoleService sysUserRoleService;
}
