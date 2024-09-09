package com.ywq.mapper.PO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderListPO {
    // 订单Id
    private Long id;

    // 订单提交时间
    private LocalDateTime checkoutTime;

    // 菜品名称
    private String name;

    // 菜品图片地址
    private String imageUrl;
}
