package com.ywq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ywq.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    int[] getDishIdByOrderId(@Param("orderId") int orderId);
}
