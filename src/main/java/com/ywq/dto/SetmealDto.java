package com.ywq.dto;

import com.ywq.entity.SetMeal;
import com.ywq.entity.SetMealDish;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class SetmealDto extends SetMeal {
    private List<SetMealDish> setmealDishes;
    private String categoryName;
}
