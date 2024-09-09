package com.ywq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywq.common.CustomException;
import com.ywq.dto.SetmealDto;
import com.ywq.entity.SetMeal;
import com.ywq.entity.SetMealDish;
import com.ywq.mapper.SetmealMapper;
import com.ywq.service.SetmealDishService;
import com.ywq.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, SetMeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    public void addSetmealWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetMealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(item->{
            item.setSetMealId(setmealDto.getSetId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);
    }

    @Transactional
    public void deleteSetmealWithDish(List<Long> ids) {
        LambdaQueryWrapper<SetMeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.in(SetMeal::getSetId,ids);
        long count = this.count(setmealQueryWrapper);
        if(count>0){
            throw new CustomException("Setmeal is still alive and cannot be deleted");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<SetMealDish> setmealDishQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishQueryWrapper.in(SetMealDish::getDishId,ids);
        setmealDishService.removeByIds(ids);
    }
}
