package com.ywq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ywq.entity.Orders;
import com.ywq.mapper.PO.OrderListPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    int getOrderIdByDate(@Param("checkoutTime") String checkoutTime);
    List<OrderListPO> getFullOrderByDate(@Param("checkoutTime") String checkoutTime);
}
