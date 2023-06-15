package com.mycompany.dto;

import java.util.List;

public class BaseRespone {
    String status;
    String message;
    Object result;
    List resultList;

    public BaseRespone() {
    }

    public BaseRespone(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseRespone(String status, String message, Object result, List resultList) {
        this.status = status;
        this.message = message;
        this.result = result;
        this.resultList = resultList;
    }
}
