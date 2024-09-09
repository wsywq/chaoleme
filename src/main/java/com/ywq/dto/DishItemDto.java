package com.ywq.dto;

import com.ywq.entity.Dish;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public class DishItemDto extends Dish {
    private MultipartFile imageFile;
}
