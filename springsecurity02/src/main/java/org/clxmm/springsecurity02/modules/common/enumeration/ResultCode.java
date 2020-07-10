package org.clxmm.springsecurity02.modules.common.enumeration;

/**
 * <p> 响应码枚举 - 可参考HTTP状态码的语义 </p>
 *
 * @author clx
 * @date 2020/7/9 17:13
 */
public enum ResultCode {

    SUCCESS(200, "SUCCESS"), //成功
    FAILURE(400, "FAILURE"), //失败
    UNAUTHORIZED(401, "未认证或token失效"),//未认证（签名错误、token错误）
    USER_UNAUTHORIZED(402, "用户名或密码不正确"), //为通过认证
    NOT_FOUNT(404, "接口不存在!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");

    private int code;
    private String desc;


    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
