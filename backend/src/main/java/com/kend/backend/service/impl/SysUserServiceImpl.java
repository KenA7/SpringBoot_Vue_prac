package com.kend.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kend.backend.entity.SysMenu;
import com.kend.backend.entity.SysRole;
import com.kend.backend.entity.SysUser;
import com.kend.backend.mapper.SysUserMapper;
import com.kend.backend.service.ISysMenuService;
import com.kend.backend.service.ISysRoleService;
import com.kend.backend.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kend.backend.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ken
 * @since 2021-06-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    ISysRoleService sysRoleService;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    ISysMenuService sysMenuService;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public SysUser getUserByUsername(String username) {


        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        String authority = "";

        SysUser sysUser = sysUserMapper.selectById(userId);

        // 查找redis缓存中对应的权限数据
        if (redisUtil.hasKey("GrantedAuthority" + sysUser.getUsername())) {
            authority = (String) redisUtil.get("GrantedAuthority" + sysUser.getUsername());
        } else {

            // 从数据中读取权限数据
            List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>()
                    .inSql("id", "select role_id from sys_user_role where user_id = " + userId));

            if (roles.size() > 0) {
                // Role数据转成流，并用逗号隔开
                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = roleCodes;
            }


            // 获取菜单id
            List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
            if (menuIds.size() > 0) {

                List<SysMenu> menus = sysMenuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));

                authority = authority.concat(",").concat(menuPerms);
            }

            //将所有权限数据和菜单数据添加到redis缓存
            redisUtil.set("GrantedAuthority" + sysUser.getUsername(), authority, 60 * 60);

            return authority;
        }
        return authority;


    }

    @Override
    public void clearUserAuthorityByUsername(String username) {

        redisUtil.del("GrantedAuthority" + username);
    }

    @Override
    public void clearUserAuthorityByRoleId(Long roleId) {
       List<SysUser> sysUsers = this.list(new QueryWrapper<SysUser>().inSql("id","select user_id from sys_user_role where role_id = "+ roleId));
       sysUsers.forEach(u->{
           this.clearUserAuthorityByUsername(u.getUsername());
       });
    }

    @Override
    public void clearUserAuthorityByMenuId(Long menuId) {
        List<SysUser> sysUsers = sysUserMapper.listByMenuId(menuId);
        sysUsers.forEach(u->{
            this.clearUserAuthorityByUsername(u.getUsername());
        });
    }
}
