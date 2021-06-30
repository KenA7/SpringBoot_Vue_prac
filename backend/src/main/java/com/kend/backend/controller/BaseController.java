package com.kend.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kend.backend.service.*;
import com.kend.backend.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

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


    /**
     * 获取页码
     * @return
     */
    public Page getPage(){
        int currentPageNum = ServletRequestUtils.getIntParameter(req,"currentPageNum",1);
        int size = ServletRequestUtils.getIntParameter(req,"size",10);

        return new Page(currentPageNum,size);
    }
}
