package com.kend.backend.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kend.backend.common.lang.Const;
import com.kend.backend.common.lang.Result;
import com.kend.backend.entity.SysRole;
import com.kend.backend.entity.SysRoleMenu;
import com.kend.backend.entity.SysUserRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.kend.backend.controller.BaseController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ken
 * @since 2021-06-22
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {


    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id ){
        SysRole role = sysRoleService.getById(id);

        List<SysRoleMenu> roleMenuList =  sysRoleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_id",id));

        List<Long> menuIds = roleMenuList.stream().map(p->p.getMenuId()).collect(Collectors.toList());

        role.setMenuIds(menuIds);

        return Result.succ(role);
    }
    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("/list")
    public Result list(String name){
       Page<SysRole> pageData = sysRoleService.page(getPage(),
                new QueryWrapper<SysRole>().eq(StrUtil.isNotBlank(name),"name",name));

        return Result.succ(pageData);
    }
    @PreAuthorize("hasAuthority('sys:role:save')")
    @GetMapping("/save")
    public Result create(@Validated @RequestBody SysRole role){

        role.setCreated(LocalDateTime.now());
        role.setStatus(Const.ACTIVE);
        sysRoleService.save(role);

        // 更新缓存
        sysUserService.clearUserAuthorityByRoleId(role.getId());

        return Result.succ(role);
    }
    @PreAuthorize("hasAuthority('sys:role:update')")
    @GetMapping("/update")
    public Result update(@Validated @RequestBody SysRole role){
        role.setUpdated(LocalDateTime.now());
        sysRoleService.updateById(role);

        return Result.succ(role);
    }
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @DeleteMapping("/delete")
    @Transactional
    public Result info(@RequestBody  Long[] roleIds){
        sysRoleService.removeByIds(Arrays.asList(roleIds));


        // 删除中间表数据
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().in("role_id",roleIds));
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().in("role_id",roleIds));


        // 缓存同步删除
        Arrays.stream(roleIds).forEach(id->{
            sysUserService.clearUserAuthorityByRoleId(id);
        });

        return Result.succ("删除成功");
    }
    @PreAuthorize("hasAuthority('sys:role:update')")
    @PostMapping("/perms/{roleId}")
    @Transactional
    public Result updatePerm(@PathVariable("roleId") Long roleId, @RequestBody Long[] menuIds){

        List<SysRoleMenu> sysRoleMenus = new ArrayList();


        Arrays.stream(menuIds).forEach(menuId->{
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(menuId);
            roleMenu.setRoleId(roleId);

            sysRoleMenus.add(roleMenu);
        });

        // 删除原先菜单信息
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));

        // 上传新的菜单信息
        sysRoleMenuService.saveBatch(sysRoleMenus);

        //删除缓存信息
        sysUserService.clearUserAuthorityByRoleId(roleId);

        return Result.succ(menuIds);

    }

}
