package com.ywq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ywq.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    Dish getDishById(@Param("id") int id);
}
