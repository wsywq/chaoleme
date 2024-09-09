package com.ywq.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywq.dto.DishDto;
import com.ywq.dto.OrderListDto;
import com.ywq.entity.*;
import com.ywq.mapper.OrdersMapper;
import com.ywq.mapper.PO.OrderListPO;
import com.ywq.service.*;
import com.ywq.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private DishService dishService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Transactional
    public void submit(List<DishDto> dishDtoList) {
        long orderId = IdWorker.getId();
        Orders order = Orders.builder()
                .id(orderId)
                .checkoutTime(LocalDateTime.now())
                .status(4)
                .remark("无")
                .build();

        List<OrderDetail> orderDetails = dishDtoList.stream().map(item -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setDishId(item.getId());
            // TODO: 套餐和菜品组合
//            orderDetail.setSetmealId(item.getSetmealId());
            return orderDetail;
        }).collect(Collectors.toList());

        try {
            orderDetailService.saveBatch(orderDetails);
            this.save(order);

            log.info("Update dish successfully");
            dishService.submitDishList(dishDtoList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OrderListDto> getTodayOrder() {
        try {
            List<OrderListPO> orderListPOS = ordersMapper.getFullOrderByDate(DateUtils.getTodayDate());
            log.info("query today order deial:", orderListPOS);
            return orderListPOS.stream().map(po -> OrderListDto.builder()
                    .id(po.getId())
                    .checkoutTime(po.getCheckoutTime())
                    .dishName(po.getName())
                    .dishImageUrl(po.getImageUrl())
                    .build()).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
