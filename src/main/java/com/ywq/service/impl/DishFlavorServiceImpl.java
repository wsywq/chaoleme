package com.ywq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywq.entity.DishFlavor;
import com.ywq.mapper.DishFlavorMapper;
import com.ywq.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
