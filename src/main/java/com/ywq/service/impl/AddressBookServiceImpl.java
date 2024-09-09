package com.ywq.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ywq.common.BaseContext;
import com.ywq.entity.AddressBook;
import com.ywq.mapper.AddressBookMapper;
import com.ywq.service.AddressBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Transactional
    public void setDefault(AddressBook addressBook) {
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentUserId());
        wrapper.set(AddressBook::getIsDefault,"0");
        this.update(wrapper);

        addressBook.setIsDefault("1");
        this.updateById(addressBook);
    }
}
