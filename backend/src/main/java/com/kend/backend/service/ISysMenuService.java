package com.kend.backend.service;

import com.kend.backend.common.dto.SysMenuDto;
import com.kend.backend.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ken
 * @since 2021-06-22
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<SysMenuDto> getCurrentUserNav();
}
