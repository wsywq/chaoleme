package com.ywq.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywq.dto.DishDto;
import com.ywq.entity.*;
import com.ywq.mapper.OrdersMapper;
import com.ywq.service.*;
import com.ywq.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private DishService dishService;

    @Transactional
    public void submit(List<DishDto> dishDtoList) {
        long orderId = IdWorker.getId();
        Orders order = Orders.builder()
                .id(orderId)
                .checkoutTime(DateUtils.getTodayDate())
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

        try{
            orderDetailService.saveBatch(orderDetails);
            this.save(order);

            log.info("Update dish");
            dishService.submitDishList(dishDtoList);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<DishDto> getTodayOrder() {

        return null;
    }


//    private Orders setOrdersDetail(long id, Orders orders, User user, AddressBook addressBook, AtomicInteger amount) {
//        orders.setId(id);
//        orders.setOrderTime(LocalDateTime.now());
//        orders.setCheckoutTime(LocalDateTime.now());
//        orders.setStatus(2);
//        orders.setAmount(new BigDecimal(amount.get()));
//        orders.setNumber(String.valueOf(id));
//        orders.setUserId(user.getId());
//        orders.setUserName(user.getName());
//        orders.setConsignee(addressBook.getConsignee());
//        orders.setPhone(addressBook.getPhone());
//        orders.setAddressBookId(addressBook.getId());
//        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
//                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
//                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
//                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
//        return orders;
//    }
}
