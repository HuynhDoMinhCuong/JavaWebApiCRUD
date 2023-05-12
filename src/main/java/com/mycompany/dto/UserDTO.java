package com.mycompany.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;


}
