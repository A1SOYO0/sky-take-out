package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    void insert(AddressBook addressBook);

    @Select("select * from address_book where user_id = #{userId}")
    List<AddressBook> selectAll(Long userId);

    @Select("select * from address_book where user_id = #{userId} and is_default = 1")
    AddressBook getDefaultByUserId(Long userId);

    void update(AddressBook addressBook);

    @Select("select * from address_book where id=#{id}")
    AddressBook getById(Long id);

    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);
}
