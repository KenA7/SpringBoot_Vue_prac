package com.kend.backend.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class SysMenuDto implements Serializable {

    private Long id;
    private String name;
    private String title;
    private String icon;
    private String component;
    private String  path;
    private List<SysMenuDto> children = new ArrayList<>();

}
