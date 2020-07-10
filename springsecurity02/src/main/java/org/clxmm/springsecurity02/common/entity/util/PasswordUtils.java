package org.clxmm.springsecurity02.common.entity.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clxmm.springsecurity02.config.Constants;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;

/**
 * <p> 加密工具 </p>
 *
 * @author clx
 * @date 2020/7/9 16:38
 */
@Slf4j
public class PasswordUtils {


    /**
     * 校验密码是否一致
     *
     * @param password:     前端传过来的密码
     * @param hashedPassword：数据库中储存加密过后的密码
     * @param salt：盐值
     * @return
     */
    public static boolean isValidPassword(String password, String hashedPassword, String salt) {
        return hashedPassword.equals(encodePassword(password, salt));
    }


    /**
     * 通过SHA1对密码进行编码
     *
     * @param password：密码
     * @param salt：盐值
     * @return
     */
    public static String encodePassword(String password, String salt) {
        String encodePassword;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            if (StringUtils.isNotBlank(salt)) {
                digest.reset();
                digest.update(salt.getBytes());
            }
            byte[] hashed = digest.digest(password.getBytes());
            int iterations = Constants.HASH_ITERATIONS - 1;
            for (int i = 0; i < iterations; i++) {
                digest.reset();
                hashed = digest.digest(hashed);
            }
            encodePassword = new String(Hex.encode(hashed));
        } catch (Exception e) {
            log.error("验证密码异常!", e);
            return null;
        }
        return encodePassword;
    }


}
