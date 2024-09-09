package com.ywq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ywq.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    int getOrderIdByDate(@Param("checkoutTime") String checkoutTime);
}
