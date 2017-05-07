package com.coffeeinfinitive.constants;

/**
 * Created by jinz on 5/1/17.
 */
public enum ResultCode {
    SUCCESS(0,"Success"),
    UTF8_UNSUPPORT(1,"UTF8-Unsupport"),
    INVALID_SIGNATURE(2,"Invalid Signture configuration / Couldn't convert Claims."),
    INVALID_TOKEN(3,"Invalid token","Token không hợp lệ"),
    USER_NOT_FOUND(4,"User not found","Tài khoản không tồn tại trong hệ thống"),
    USERNAME_INVALID_REQUIRED(6,"User not found","Tên đăng nhập không được để trống"),
    PASSWORD_INVALID_REQUIRED(7,"User not found","Mật khẩu không được để trống"),
    PARSE_JSON_TO_OBJECT(8,"Cant parse json string to object","Không thể parse json thành object"),
    TOKEN_NOT_FOUND_IN_HEADER(9,"Cant find token param in header request","Không tìm thấy tham số X-Authorization trên header request"),
    TOKEN_EXPIRED(10,"Token expired","Phiên làm việc đã hết hạn, vui lòng đăng nhập lại sau"),
    BAD_CREDENTIAL(11,"Wrong password","Tên đăng nhập hoặc mật khẩu không đúng"),
    USER_EXIST(12,"User exist","Tài khoản đã tồn tại trong hệ thống, vui lòng chọn tên đăng nhập khác"),
    BAD_REQUEST(13,"Bad request","Request sai format"),
    ROLE_NOT_FOUND(14,"Role not found","Role không tồn tài, vui lòng chọn role khác"),
    ORGANZITION_NOT_FOUND(15,"Organization no found","CLB, Tổ chức không tồn tại"),
    ORGANZITION_EXIST(16,"Organization exist","CLB, Tổ chức đã tồn tại, vui lòng chọn tổ chức khác"),
    ACTIVITY_EXIST(17,"Activity exist","Hoạt động đã tồn tại"),
    ACTIVITY_NOT_FOUND(18,"Activity not found","Hoạt động không đã tồn tại"),
    REGISTER_EXIST(19,"REGISTER exist","Hoạt động đã đăng ký"),
    REGISTER_NOT_FOUND(20,"REGISTER not found","Hoạt động chưa được đăng ký"),
    ACTIVITY_NOT_ACTIVED(21,"Activity not active","Hoạt động chưa cho phép đăng ký"),
    ACTIVITY_REGISTERED(22,"Activity registerd","Hoạt động đã được đăng ký"),
    COMMENT_NOT_FOUND(23,"Comment not found","Bình luận không tìm thấy"),
    COMMENT_EXIST(24,"Comment not found","Bình luận không tìm thấy"),

    INTERNAL_SYSTEM_ERROR(999,"System error","Lỗi hệ thống");

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
