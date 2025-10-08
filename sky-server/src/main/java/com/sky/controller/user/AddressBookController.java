package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "c端用户地址簿相关接口")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;
    @PostMapping
    @ApiOperation("新增地址")
    public Result save(@RequestBody AddressBook addressBook) {
        log.info(addressBook.toString());
        addressBookService.save(addressBook);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("查询当前登录用户的所有地址信息")
    public Result<List<AddressBook>> getAddressList() {
       List<AddressBook> list= addressBookService.getAddressList();
       return Result.success(list);
    }
    @GetMapping("/list/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefaultAddress(){
         AddressBook addressBook=addressBookService.getDefaultAddress();
         return Result.success(addressBook);
    }

    @PutMapping
    @ApiOperation("修改地址")
    public Result updateAddress(@RequestBody AddressBook addressBook){
          addressBookService.updateAddress(addressBook);
          return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> gerAddressById(@PathVariable Long id){
        AddressBook addressBook=addressBookService.getById(id);
        return Result.success(addressBook);
    }
    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result deleteAddress(Long id){
        addressBookService.deleteAddressById(id);
        return Result.success();
    }

    public Result setDefaultAddress(@RequestBody AddressBook addressBook){
        addressBookService.setDefaultAddress(addressBook);
        return Result.success();
    }
}
