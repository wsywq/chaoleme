package com.ywq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ywq.common.ResponseTemplate;
import com.ywq.entity.Category;
import com.ywq.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseTemplate<String> addNewCategory(@RequestBody Category category) {
        log.info("Add new category: {}", category.toString());
        categoryService.save(category);
        return ResponseTemplate.success("Add successfully");
    }

    @GetMapping("/page")
    public ResponseTemplate<Page> getCategoryPage(int page, int pageSize) {
        log.info("Query category page {}, pageSize {}", page, pageSize);
        Page<Category> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, queryWrapper);
        return ResponseTemplate.success(pageInfo);
    }

    @PutMapping
    public ResponseTemplate<String> updateCategory(@RequestBody Category category) {
        log.info("Update category {}", category.toString());
        categoryService.updateById(category);
        return ResponseTemplate.success("Update successfully");
    }

    @DeleteMapping
    public ResponseTemplate<String> deleteCategory(Long id) {
        log.info("Delete category id = {}", id);
        categoryService.removeCategory(id);
        return ResponseTemplate.success("Delete successfully");
    }

    @GetMapping("/list")
    public ResponseTemplate<List<Category>> listCategory(Category category) {
        log.info("List category {}", category.toString());
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
//        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return ResponseTemplate.success(list);

    }

}
