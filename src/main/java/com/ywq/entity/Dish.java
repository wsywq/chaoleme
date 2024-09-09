package com.ywq.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Dish implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long categoryId;

    private String imageUrl;

    private String description;

    //辣度
    private Integer heatLevel;

    //已点数量
    private Integer count;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String remark;

}
