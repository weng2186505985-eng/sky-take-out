package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


/**
 * 通用接口
 */
//@RestController
//@RequestMapping("/admin/common")
//@Api(tags ="通用接口" )
//@Slf4j
//public class CommonController{
//    /**
//     * 文件上传
//     * @param file
//     * @return
//     */
//    @PostMapping("/upload")
//    public Result<String> upload(MultipartFile file){
//        log.info("文件上传：{}",file);
//
//        try {
//            String originalFilename = file.getOriginalFilename();
//            //截取原始文件名的后缀
//            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//            String fileName = UUID.randomUUID().toString()+"."+extension;
//            String file_path = upload(file.getBytes(), fileName);
//            return Result.success(file_path);
//        } catch (IOException e) {
//            log.error("文件上传失败");
//        }
//        return null;
//    }
//}