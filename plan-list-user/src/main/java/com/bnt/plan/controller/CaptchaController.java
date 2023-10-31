package com.bnt.plan.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.bnt.plan.common.BaseResponse;
import com.bnt.plan.common.ResultUtils;
import com.bnt.plan.constant.CommonConstant;
import com.bnt.plan.service.RedisService;
import com.bnt.plan.utils.VerifyCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author bnt
 * @version 1.0.0
 * @create 2023/10/31 17:10 bnt
 * @history
 */
@Slf4j
@RestController
@RequestMapping("/captcha")
public class CaptchaController {
    @Autowired
    private RedisService redisService;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public BaseResponse getCode() throws IOException {
        // 生成随机子串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 唯一标识
        String uuid = IdUtil.simpleUUID();
        String verifyKey = CommonConstant.CAPTCHA_CODE_KEY + uuid;
        redisService.setCacheObject(verifyKey, verifyCode, CommonConstant.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, outputStream, verifyCode);
        try {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("uuid", uuid);
            hashMap.put("img", Base64.encode(outputStream.toByteArray()));
            return ResultUtils.success(hashMap);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultUtils.error(e.getMessage());
        } finally {
            outputStream.close();
        }
    }
}
