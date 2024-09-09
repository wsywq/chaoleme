package com.ywq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ywq.dto.DishDto;
import com.ywq.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {

    public boolean addNewDish(Dish dish);

    public boolean updateDish(Dish dish);

    public void addWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

    public void submitDishList(List<DishDto> dishDtoList);
}
