package com.ywq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ywq.entity.Category;

public interface CategoryService extends IService<Category> {

    public void removeCategory(Long id);
}
