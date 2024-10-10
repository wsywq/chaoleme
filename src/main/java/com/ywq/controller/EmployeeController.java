package com.ywq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ywq.common.ResponseTemplate;
import com.ywq.entity.Employee;
import com.ywq.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseTemplate<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
        String password = employee.getPassword();
        String md5Pwd = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(employeeLambdaQueryWrapper);

        if (emp == null) {
            return ResponseTemplate.error("Login failed");
        }

        if (!emp.getPassword().equals(md5Pwd)) {
            return ResponseTemplate.error("Login failed");
        }

        if (emp.getStatus() == 0) {
            return ResponseTemplate.error("This account is locked");
        }

        httpServletRequest.getSession().setAttribute("employee", emp.getId());
        return ResponseTemplate.success(emp);
    }

    @PostMapping("/logout")
    public ResponseTemplate<String> logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute("employee");
        return ResponseTemplate.success("Logout successfully");
    }

    @PostMapping
    public ResponseTemplate<String> addNewEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("Add new employee:{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return ResponseTemplate.success("Add successfully");
    }

    @GetMapping("/page")
    public ResponseTemplate<Page> getEmployeePage(int page, int pageSize, String name) {
        log.info("page={}, pageSize={}, name={}", page, pageSize, name);
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> employeeWrapper = new LambdaQueryWrapper<>();
        employeeWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        employeeWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, employeeWrapper);
        return ResponseTemplate.success(pageInfo);
    }

    @PutMapping
    public ResponseTemplate<String> updateEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        Long employeeId = (Long) request.getSession().getAttribute("employee");
        employee.setStatus(employee.getStatus());
        employeeService.updateById(employee);
        return ResponseTemplate.success("Update successfully");
    }

    @GetMapping("/{id}")
    public ResponseTemplate<Employee> queryEmployeeById(@PathVariable Long id) {
        log.info("employee id = {}", id);
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return ResponseTemplate.success(employee);
        }
        return ResponseTemplate.error("This employee is not found");
    }
}
