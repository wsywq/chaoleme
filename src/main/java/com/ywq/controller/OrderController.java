package com.ywq.controller;

import com.ywq.common.ResponseTemplate;
import com.ywq.dto.DishDto;
import com.ywq.dto.OrderListDto;
import com.ywq.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    public ResponseTemplate<String> submit(@RequestBody List<DishDto> dishDtoList) {
        log.info("Submit order {}", dishDtoList.toString());
        ordersService.submit(dishDtoList);
        return ResponseTemplate.success("Submit successfully");
    }

    @GetMapping("/today")
    public ResponseTemplate<List<OrderListDto>> getTodayOrder(){
        log.info("Get today order");
        return ResponseTemplate.success(ordersService.getTodayOrder());
    }
}
