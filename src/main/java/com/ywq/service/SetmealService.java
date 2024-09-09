package com.ywq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ywq.dto.SetmealDto;
import com.ywq.entity.SetMeal;

import java.util.List;

public interface SetmealService extends IService<SetMeal> {
    public void addSetmealWithDish(SetmealDto setmealDto);
    public void deleteSetmealWithDish(List<Long> ids);
}
