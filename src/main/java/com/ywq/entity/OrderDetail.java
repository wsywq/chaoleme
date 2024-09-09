package com.ywq.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long orderId;

    private Long dishId;

    private Long setmealId;
}
