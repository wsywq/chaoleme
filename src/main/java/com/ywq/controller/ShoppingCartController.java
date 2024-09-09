package com.ywq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ywq.common.BaseContext;
import com.ywq.common.ResponseTemplate;
import com.ywq.entity.ShoppingCart;
import com.ywq.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public ResponseTemplate<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("Add shopping cart {}", shoppingCart.toString());
        Long currentUserId = BaseContext.getCurrentUserId();
        shoppingCart.setUserId(currentUserId);

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentUserId);
        Long dishId = shoppingCart.getDishId();
        if (dishId != null) {
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);
        if (cartServiceOne != null) {
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppingCartService.updateById(cartServiceOne);
        } else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }

        return ResponseTemplate.success(cartServiceOne);
    }

    @GetMapping("/list")
    public ResponseTemplate<List<ShoppingCart>> list() {
        log.info("get shopping cart list");
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        Long currentUserId = BaseContext.getCurrentUserId();
        queryWrapper.eq(ShoppingCart::getUserId, currentUserId);
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);
        return ResponseTemplate.success(list);
    }

    @DeleteMapping("/clean")
    public ResponseTemplate<String> clean(){
        log.info("clean shopping cart");
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentUserId());
        shoppingCartService.remove(queryWrapper);
        return ResponseTemplate.success("Clean successfully");
    }
}
