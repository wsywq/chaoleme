package com.ywq.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderListDto {
    // Order Id
    private Long id;

    // Order checkout_time "HH:mm:ss"
    private String checkoutTime;

    // 菜品名称
    private String dishName;

    // 菜品图片
    private String dishImageUrl;
}

