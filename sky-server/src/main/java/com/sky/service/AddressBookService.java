package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    void save(AddressBook addressBook);

    List<AddressBook> getAddressList();

    AddressBook getDefaultAddress();

    void updateAddress(AddressBook addressBook);

    AddressBook getById(Long id);

    void deleteAddressById(Long id);

    void setDefaultAddress(AddressBook addressBook);
}
