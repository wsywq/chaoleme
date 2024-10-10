package com.ywq.dto;

import com.ywq.entity.Dish;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DishDto extends Dish {

    private String categoryName;

    //单次下单数量
    private Integer currentCount;
}
