package com.ywq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywq.entity.Employee;
import com.ywq.mapper.EmployeeMapper;
import com.ywq.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
