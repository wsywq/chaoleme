package com.ywq.dto;

import com.ywq.entity.Dish;
import lombok.Data;

@Data
public class DishDto extends Dish {

    private String categoryName;

    //单次下单数量
    private Integer currentCount;
}
