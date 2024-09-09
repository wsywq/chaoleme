package com.ywq.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜系实体
 */
@Data
public class Category implements Serializable {
    private static final long serialVersionUID=1L;

    private Long id;

    // 菜系名称
    private String name;

    // 菜系描述
    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private String remark;

}
