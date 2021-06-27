package com.kend.backend.security;

import com.kend.backend.entity.SysUser;
import com.kend.backend.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    ISysUserService sysUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 从数据库中找出user
        SysUser sysUser = sysUserService.getUserByUsername(username);
        if (sysUser ==null){
            throw new UsernameNotFoundException("用户名或密码不正确");
        }

        // 将数据库中的user 信息返回给springboot security 进行验证
        return new AccountUser(sysUser.getId(),sysUser.getUsername(),sysUser.getPassword(),authorities(sysUser.getId()));
    }


    public List<GrantedAuthority> authorities(Long userId){

        String authority = sysUserService.getUserAuthorityInfo(userId);

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
