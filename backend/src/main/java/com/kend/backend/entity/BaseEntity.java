package com.kend.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {

    @TableId(value="id",type = IdType.AUTO)
    private Long Id;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Integer status;

}
