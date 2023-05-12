package com.mycompany.user;

//Hàm thông báo lỗi
public class UserNotFoundException extends Throwable {
    public UserNotFoundException(String message) {
        super(message);
    }
}
