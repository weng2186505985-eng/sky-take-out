package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    public User wxLogin(UserLoginDTO userLoginDTO) {
        log.info("开始微信登录，code: {}", userLoginDTO.getCode());

        try {
            // 调用微信接口服务，获取当前微信用户的openid
            String openid = getOpenId(userLoginDTO.getCode());

            if (openid == null) {
                log.error("获取openid失败");
                throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
            }

            log.info("获取到openid: {}", openid);

            // 判断是否为新用户
            User user = userMapper.getByOpenId(openid);
            if (user == null) {
                log.info("新用户注册，openid: {}", openid);
                user = User.builder()
                        .openid(openid)
                        .createTime(LocalDateTime.now())
                        .build();
                // 如果是新用户，自动完成注册
                userMapper.insert(user);
            } else {
                log.info("老用户登录，用户ID: {}", user.getId());
            }

            // 返回这个用户对象
            return user;

        } catch (Exception e) {
            log.error("微信登录异常", e);
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
    }

    private String getOpenId(String code) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("appid", weChatProperties.getAppid());
            map.put("secret", weChatProperties.getSecret());
            map.put("js_code", code);
            map.put("grant_type", "authorization_code");

            log.info("调用微信API，参数: appid={}, code={}", weChatProperties.getAppid(), code);

            String json = HttpClientUtil.doGet(WX_LOGIN, map);


            log.info("微信API返回: {}", json);

            JSONObject jsonObject = JSON.parseObject(json);

            // 检查微信返回的错误码
            if (jsonObject.containsKey("errcode")) {
                Integer errcode = jsonObject.getInteger("errcode");
                String errmsg = jsonObject.getString("errmsg");
                log.error("微信API返回错误 - errcode: {}, errmsg: {}", errcode, errmsg);
                return null;
            }

            String openid = jsonObject.getString("openid");

            return openid;

        } catch (Exception e) {
            log.error("获取openid异常", e);
            return null;
        }
    }
}
