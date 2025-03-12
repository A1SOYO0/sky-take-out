package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    void newAddress(AddressBook addressBook);

    List<AddressBook> getAllAddress();

    AddressBook getDefault();

    void editAddress(AddressBook addressBook);

    AddressBook getById(Long id);

    void setDefault(AddressBook addressBook);

    void deleteById(Long id);
}
