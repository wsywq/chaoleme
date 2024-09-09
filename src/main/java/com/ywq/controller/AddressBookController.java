package com.ywq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ywq.common.BaseContext;
import com.ywq.common.ResponseTemplate;
import com.ywq.entity.AddressBook;
import com.ywq.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    @PostMapping
    public ResponseTemplate<String> save(@RequestBody AddressBook addressBook) {
        log.info("Save addressBook :{}", addressBook.toString());
        addressBook.setUserId(BaseContext.getCurrentUserId());
        addressBookService.save(addressBook);
        return ResponseTemplate.success("Save successfully");
    }

    @GetMapping("/default")
    public ResponseTemplate getDefault() {
        log.info("get default address");
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentUserId());
        wrapper.eq(AddressBook::getIsDefault, "1");
        AddressBook addressBook = addressBookService.getOne(wrapper);
        if (addressBook != null) {
            return ResponseTemplate.success(addressBook);
        } else {
            return ResponseTemplate.error("Don't find default addressBook");
        }

    }

    @PutMapping("/default")
    public ResponseTemplate<String> setDefault(@RequestBody AddressBook addressBook) {
        log.info("Save default {}", addressBook.toString());
        addressBookService.setDefault(addressBook);
        return ResponseTemplate.success("Save default successfully");
    }

    @GetMapping("/{id}")
    public ResponseTemplate getById(@PathVariable Long id) {
        log.info("get address by id {}", id);
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return ResponseTemplate.success(addressBook);
        } else {
            return ResponseTemplate.error("This address is not existed");
        }
    }

    @GetMapping("/list")
    public ResponseTemplate<List<AddressBook>> list(AddressBook addressBook) {
        log.info("list address book {}", addressBook.toString());
        LambdaQueryWrapper<AddressBook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentUserId());
        wrapper.orderByDesc(AddressBook::getUpdateTime);
        return ResponseTemplate.success(addressBookService.list(wrapper));
    }


}
