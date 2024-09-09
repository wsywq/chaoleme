package com.ywq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywq.entity.OrderDetail;
import com.ywq.mapper.OrderDetailMapper;
import com.ywq.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
