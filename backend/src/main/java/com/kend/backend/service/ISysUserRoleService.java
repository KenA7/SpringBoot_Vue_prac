package com.kend.backend.service;

import com.kend.backend.entity.SysUser;
import com.kend.backend.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ken
 * @since 2021-06-22
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    SysUser getUserByUsername();

}
