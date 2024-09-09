package com.ywq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywq.entity.User;
import com.ywq.mapper.UserMapper;
import com.ywq.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
