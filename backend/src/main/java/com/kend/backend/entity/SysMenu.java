package com.kend.backend.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author ken
 * @since 2021-06-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotNull(message= "上级菜单不能为空")
    private Long parentId;

    @NotBlank(message = "菜单名称不能为空")
    private String name;

    private String path;

    @NotBlank(message = "菜单授权码不能为空")
    private String perms;

    private String component;

    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    private String icon;

    @TableField("orderNum")
    private Integer orderNum;

    @TableField(exist = false)
    private List<SysMenu>  children = new ArrayList<>();

}
