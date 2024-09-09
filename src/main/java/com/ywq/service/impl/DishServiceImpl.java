package com.ywq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywq.dto.DishDto;
import com.ywq.entity.Dish;
import com.ywq.entity.DishFlavor;
import com.ywq.mapper.DishMapper;
import com.ywq.service.DishFlavorService;
import com.ywq.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishMapper dishMapper;

    @Value("${sftp.path}")
    private String basePath;

    @Transactional
    public boolean addNewDish(Dish dish) {
//        dish.setImageUrl(uploadImage(image));
        return this.save(dish);
    }

    private String uploadImage(MultipartFile image) {
        log.info("Upload file");
        String originalFilename = image.getOriginalFilename();
        String suffix;
        if (StringUtils.isNoneEmpty(originalFilename)) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        } else {
            suffix = ".png";
        }
        String fileName = UUID.randomUUID() + suffix;
        File fileDir = new File(basePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        try {
            image.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }

    @Override
    public boolean updateDish(Dish dish) {
        return this.updateById(dish);
    }


    @Transactional
    public void addWithFlavor(DishDto dishDto) {
        this.save(dishDto);

//        Long id = dishDto.getId();
//        List<DishFlavor> flavors = dishDto.getFlavors();
//        flavors = flavors.stream().map((item) -> {
//            item.setDishId(id);
//            return item;
//        }).collect(Collectors.toList());
//
//        dishFlavorService.saveBatch(flavors);
    }

    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);
//        dishDto.setFlavors(list);
        return dishDto;
    }

    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);

//        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
//        dishFlavorService.remove(queryWrapper);
//
//        List<DishFlavor> flavors = dishDto.getFlavors();
//        flavors = flavors.stream().map((item) -> {
//            item.setDishId(dishDto.getId());
//            return item;
//        }).collect(Collectors.toList());
//        dishFlavorService.saveBatch(flavors);
    }

    @Transactional
    public void submitDishList(List<DishDto> dishDtoList) {
        for (DishDto dishDto : dishDtoList) {
            Dish dish = new Dish();
            dish.setId(dishDto.getId());
//            dish.setCurrentCount(dishDto.getCurrentCount());
            // 累计当次菜品数量
            dish.setCount(dishDto.getCount()+ dishDto.getCurrentCount());
            LambdaUpdateWrapper<Dish> dishLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            dishLambdaUpdateWrapper.eq(Dish::getId, dishDto.getId());
            dishMapper.update(dish, dishLambdaUpdateWrapper);
        }
        log.info("update successfully");
    }


}
