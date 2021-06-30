package com.kend.backend.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kend.backend.common.dto.SysMenuDto;
import com.kend.backend.common.lang.Result;
import com.kend.backend.entity.SysMenu;
import com.kend.backend.entity.SysRoleMenu;
import com.kend.backend.entity.SysUser;
import io.netty.util.internal.StringUtil;
import org.springframework.data.annotation.Id;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.kend.backend.controller.BaseController;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.time.LocalDateTime;
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

    @GetMapping("/info/{id}")
    public Result Info(@PathVariable("id") Long id ){
        return Result.succ(sysMenuService.getById(id));
    }


    @GetMapping("/list")
    public Result list(){
        List<SysMenu> menus = sysMenuService.tree();
        return Result.succ(menus);
    }

    @PostMapping("/save")
    // 注解Validated会检验这个对象是否按照要求输入数据，否则会抛出异常
    public Result save(@Validated @RequestBody SysMenu sysMenu){

        sysMenu.setCreated(LocalDateTime.now());
        sysMenuService.save(sysMenu);
        return Result.succ(sysMenu);
    }

    @PostMapping("/update")
    // 注解Validated会检验这个对象是否按照要求输入数据，否则会抛出异常
    public Result update(@Validated @RequestBody SysMenu sysMenu){
        sysMenu.setUpdated(LocalDateTime.now());
        sysMenuService.updateById(sysMenu);

        // 清除所有与该菜单相关的权限缓存
        sysUserService.clearUserAuthorityByMenuId(sysMenu.getId());
        return Result.succ(sysMenu);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Long Id){

        // 判断菜单是否有子集菜单
        int count =sysMenuService.count(new QueryWrapper<SysMenu>().eq("parent_id",Id));
        if(count>0){
            return Result.fail("请删除子菜单");
        }
        sysMenuService.removeById(Id);

        // 删除中间表信息
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id",Id));


        // 清除所有与该菜单相关的权限缓存
        sysUserService.clearUserAuthorityByMenuId(Id);

        return Result.succ("删除成功");
    }
}
