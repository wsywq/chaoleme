package com.ywq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywq.entity.ShoppingCart;
import com.ywq.mapper.ShoppingCartMapper;
import com.ywq.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
