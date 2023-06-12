package com.mycompany.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsersDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean enabled;

}
