package com.ywq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ywq.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
