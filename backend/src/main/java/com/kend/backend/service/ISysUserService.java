package com.kend.backend.service;

import com.kend.backend.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.catalina.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ken
 * @since 2021-06-22
 */
public interface ISysUserService extends IService<SysUser> {
    SysUser getUserByUsername(String username);

    String getUserAuthorityInfo(Long userId);

    void clearUserAuthorityByUsername(String username);

    void clearUserAuthorityByRoleId(Long roleId);

    void clearUserAuthorityByMenuId(Long menuId);
}
