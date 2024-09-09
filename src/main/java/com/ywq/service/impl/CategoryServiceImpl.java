package com.ywq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywq.common.CustomException;
import com.ywq.entity.Category;
import com.ywq.entity.Dish;
import com.ywq.entity.SetMeal;
import com.ywq.mapper.CategoryMapper;
import com.ywq.service.CategoryService;
import com.ywq.service.DishService;
import com.ywq.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setMealService;

    @Override
    public void removeCategory(Long id) {
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.eq(Dish::getCategoryId, id);
        long dishCount = dishService.count(dishWrapper);
        if(dishCount > 0){
            // throw Exception
            throw new CustomException("This category has dishes. Delete failed");
        }

        LambdaQueryWrapper<SetMeal> setMealWrapper = new LambdaQueryWrapper<>();
//        setMealWrapper.eq(SetMeal::getCategoryId,id);
        long setMealCount = setMealService.count(setMealWrapper);
        if(setMealCount > 0){
            // throw Exception
            throw new CustomException("This category has setmeals. Delete failed");
        }

        super.removeById(id);

    }
}
