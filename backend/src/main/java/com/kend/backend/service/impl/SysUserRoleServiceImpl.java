package com.kend.backend.service.impl;

import com.kend.backend.entity.SysUser;
import com.kend.backend.entity.SysUserRole;
import com.kend.backend.mapper.SysUserRoleMapper;
import com.kend.backend.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ken
 * @since 2021-06-22
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public SysUser getUserByUsername() {
        return null;
    }
}
