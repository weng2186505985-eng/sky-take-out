package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;


    public void  save(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.save(addressBook);
    }


    public List<AddressBook> getAddressList() {
        Long userId= BaseContext.getCurrentId();
        List<AddressBook> list= addressBookMapper.list(userId);
        return list;
    }

    public AddressBook getDefaultAddress() {
        Long userId= BaseContext.getCurrentId();
        AddressBook addressBook=addressBookMapper.getDefault(userId);
        return addressBook;
    }

    public void updateAddress(AddressBook addressBook) {
        addressBookMapper.updateAddress(addressBook);
    }

    public AddressBook getById(Long id) {
        AddressBook addressBook=addressBookMapper.getById(id);
        return addressBook;
    }

    public void deleteAddressById(Long id) {
        addressBookMapper.deleteById(id);
    }
    @Transactional
    public void setDefaultAddress(AddressBook addressBook) {
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateIsDefaultByUserId(addressBook);

        addressBook.setIsDefault(1);
        addressBookMapper.updateAddress(addressBook);
    }
}
