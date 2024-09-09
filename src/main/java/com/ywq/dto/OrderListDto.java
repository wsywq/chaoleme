package com.ywq.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderListDto {
    private Long id;
    private LocalDateTime checkoutTime;
    private String dishName;
    private String dishImageUrl;
}

