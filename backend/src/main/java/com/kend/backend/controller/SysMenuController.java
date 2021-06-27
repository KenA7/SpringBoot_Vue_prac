package com.kend.backend.controller;


import cn.hutool.core.map.MapUtil;
import com.kend.backend.common.dto.SysMenuDto;
import com.kend.backend.common.lang.Result;
import com.kend.backend.entity.SysUser;
import io.netty.util.internal.StringUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.kend.backend.controller.BaseController;

import java.security.Principal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ken
 * @since 2021-06-22
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {


    /**
     * 获取当前用户权限和菜单
     * @param principal
     * @return
     */
    @GetMapping("/nav")
    public Result nav(Principal principal){
        SysUser sysUser = sysUserService.getUserByUsername(principal.getName());

        //获取权限信息
        String authorityInfo = sysUserService.getUserAuthorityInfo(sysUser.getId());
        String[] authority = StringUtils.tokenizeToStringArray(authorityInfo,","); //将逗号隔开的String变成数组

        //获取导航栏信息
        List<SysMenuDto> nav = sysMenuService.getCurrentUserNav();

        return Result.succ(
                MapUtil.builder()
                .put("authority",authority)
                .put("nav",nav)
                .map()
        );

    }
}
