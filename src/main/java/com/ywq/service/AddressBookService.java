package com.ywq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ywq.entity.AddressBook;

public interface AddressBookService extends IService<AddressBook> {
    public void setDefault(AddressBook addressBook);
}
