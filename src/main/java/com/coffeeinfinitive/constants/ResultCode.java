package com.coffeeinfinitive.constants;

/**
 * Created by jinz on 5/1/17.
 */
public enum ResultCode {
    Success(0,"Success"),
    UTF8_UNSUPPORT(1,"UTF8-Unsupport"),
    INVALID_SIGNATURE(2,"Invalid Signture configuration / Couldn't convert Claims."),
    INVALID_TOKEN(3,"Invalid token","Token không hợp lệ"),
    USER_NOT_FOUND(4,"User not found","Tài khoản không tồn tại trong hệ thống"),
    USERNAME_INVALID_REQUIRED(6,"User not found","Tên đăng nhập không được để trống"),
    PASSWORD_INVALID_REQUIRED(7,"User not found","Mật khẩu không được để trống"),
    PARSE_JSON_TO_OBJECT(8,"Cant parse json string to object","Không thể parse json thành object"),
    TOKEN_NOT_FOUND_IN_HEADER(9,"Cant find token param in header request","Không tìm thấy tham số X-Authorization trên header request"),
    TOKEN_EXPIRED(10,"Token expired","Phiên làm việc đã hết hạn, vui lòng đăng nhập lại sau"),
    BAD_CREDENTIAL(11,"Wrong password","Tên đăng nhập hoặc mật khẩu không đúng");
    private int code;
    private String message;
    private String messageVn;
    ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }
    ResultCode(int code, String message, String messageVn){
        this.code = code;
        this.message = message;
        this.messageVn = messageVn;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageVn() {
        return messageVn;
    }

    public void setMessageVn(String messageVn) {
        this.messageVn = messageVn;
    }
}
