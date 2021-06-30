package com.kend.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kend.backend.common.dto.SysMenuDto;
import com.kend.backend.entity.SysMenu;
import com.kend.backend.entity.SysUser;
import com.kend.backend.mapper.SysMenuMapper;
import com.kend.backend.mapper.SysUserMapper;
import com.kend.backend.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kend.backend.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ken
 * @since 2021-06-22
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {


    @Autowired
    ISysUserService sysUserService;

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public List<SysMenuDto> getCurrentUserNav() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = sysUserService.getUserByUsername(username);
        List<Long> menuIds = sysUserMapper.getNavMenuIds(sysUser.getId());
        List<SysMenu> menus = this.listByIds(menuIds);

        // 转成树状结构
        List<SysMenu> menuTree = buildTreeMenu(menus);
        // 实体转dto
       return convert(menuTree);

    }

    @Override
    public List<SysMenu> tree() {
        List<SysMenu> sysMenus = this.list(new QueryWrapper<SysMenu>().orderByAsc("orderNum"));

        return buildTreeMenu(sysMenus);
    }

    private List<SysMenuDto> convert(List<SysMenu> menuTree) {
        List<SysMenuDto> menuDtos = new ArrayList<>();

        menuTree.forEach(m->{
            SysMenuDto dto = new SysMenuDto();
            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setComponent(m.getComponent());
            dto.setPath(m.getPath());
            dto.setIcon(m.getIcon());
            // 如果用子类菜单，将子类菜单对象转成dto
            if(m.getChildren().size() > 0){

                dto.setChildren(convert((m.getChildren())));
            }
            menuDtos.add(dto);
        });
        return menuDtos;
    }

    private List<SysMenu> buildTreeMenu(List<SysMenu> menus) {
        List<SysMenu> finalMenus = new ArrayList<>();

        // 寻找子类菜单
        for(SysMenu menu: menus){
            for(SysMenu subMenu : menus) {
                if(menu.getId() == subMenu.getParentId()){
                    menu.getChildren().add(subMenu);

                }
            }
            // 提取出父节点
            if(menu.getParentId() == 0L){
                finalMenus.add(menu);
            }
        }


        return finalMenus;

    }
}
