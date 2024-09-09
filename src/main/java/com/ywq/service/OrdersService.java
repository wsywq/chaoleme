package com.ywq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ywq.dto.DishDto;
import com.ywq.entity.Orders;

import java.util.List;

public interface OrdersService extends IService<Orders> {
    public void submit(List<DishDto> dishDtoList);

    public List<DishDto> getTodayOrder();
}
