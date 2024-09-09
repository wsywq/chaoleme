package com.ywq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ywq.common.ResponseTemplate;
import com.ywq.dto.SetmealDto;
import com.ywq.entity.SetMeal;
import com.ywq.service.CategoryService;
import com.ywq.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseTemplate<String> addNewSetmeal(@RequestBody SetmealDto setmealDto) {
        log.info("Add new setmeal:{}", setmealDto.toString());
        setmealService.addSetmealWithDish(setmealDto);
        return ResponseTemplate.success("Add successfully");
    }

    @GetMapping("/page")
    public ResponseTemplate<Page> getSetmealPage(int page, int pageSize, String name) {
        log.info("get setmeal page: {},{},{}", page, pageSize, name);
        Page<SetMeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<SetMeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        setmealQueryWrapper.like(name != null, SetMeal::getName, name);
        setmealService.page(setmealPage, setmealQueryWrapper);

        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        List<SetMeal> setmealList = setmealPage.getRecords();
        List<SetmealDto> setmealDtoList = setmealList.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
//            Long categoryId = item.getCategoryId();
//            Category category = categoryService.getById(categoryId);
//            if (category != null) {
//                String categoryName = category.getName();
//                setmealDto.setCategoryName(categoryName);
//            }
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(setmealDtoList);
        return ResponseTemplate.success(setmealDtoPage);
    }

    @DeleteMapping
    public ResponseTemplate<String> deleteSetmeal(@RequestParam List<Long> ids) {
        log.info("delete setmeal, {}", ids.toString());
        setmealService.deleteSetmealWithDish(ids);
        return ResponseTemplate.success("Delete successfully");
    }

    @GetMapping("/list")
    public ResponseTemplate<List<SetMeal>> list(SetMeal setmeal) {
        log.info("get setmeal list {}", setmeal.toString());
        LambdaQueryWrapper<SetMeal> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(setmeal.getCategoryId() != null, SetMeal::getCategoryId, setmeal.getCategoryId());
//        queryWrapper.eq(setmeal.getStatus() != null, SetMeal::getStatus, setmeal.getStatus());
        List<SetMeal> list = setmealService.list(queryWrapper);
        return ResponseTemplate.success(list);
    }

}
