package com.ywq.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SetMealDish implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long setMealId;

    private Long dishId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
