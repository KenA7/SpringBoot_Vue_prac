package com.kend.backend.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    private Long parentId;

    private String name;

    private String path;

    private String perms;

    private String component;

    private Integer type;

    private String icon;

    @TableField("orderNum")
    private Integer orderNum;

    @TableField(exist = false)
    private List<SysMenu>  children = new ArrayList<>();

}
