package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    //新增地址
    public void newAddress(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(StatusConstant.DISABLE);
        addressBookMapper.insert(addressBook);
    }

    //查询用户所有地址
    public List<AddressBook> getAllAddress(){
        Long userId = BaseContext.getCurrentId();
        List<AddressBook> list = addressBookMapper.selectAll(userId);
        return list;
    }

    //查询默认地址
    public AddressBook getDefault(){
        Long userId = BaseContext.getCurrentId();
        AddressBook addressBook = addressBookMapper.getDefaultByUserId(userId);

        return addressBook;
    }

    //修改地址
    public void editAddress(AddressBook addressBook){
        addressBookMapper.update(addressBook);
    }

    //根据id查询地址
    public AddressBook getById(Long id){

        return addressBookMapper.getById(id);
    }

    //设置默认地址
    @Transactional
    public void setDefault(AddressBook addressBook){
        //取消原来的默认地址
        AddressBook oldAddressBook = addressBookMapper.getDefaultByUserId(BaseContext.getCurrentId());
        if(oldAddressBook != null){
            oldAddressBook.setIsDefault(StatusConstant.DISABLE);
            addressBookMapper.update(oldAddressBook);
        }
        //更新新的默认地址
        addressBook.setIsDefault(StatusConstant.ENABLE);
        addressBookMapper.update(addressBook);
    }

    //根据id删除地址
    public void deleteById(Long id){
        addressBookMapper.deleteById(id);
    }
}
