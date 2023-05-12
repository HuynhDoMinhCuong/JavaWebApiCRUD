package com.mycompany.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users") //Tên bảng sẽ lưu vào MySQL
public class Users2 {
    @Id
    //Tự động tạo ra giá trị id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Khoá chính
    private Integer id;

    @Column(name = "email", nullable = false, unique = true, length = 45) //Khoá ngoại
    private String email;

    @Column(name = "password", length = 15, nullable = false)
    private String password;

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Column(name = "enabled")
    private boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if(id!=null)
        {
            return;
        }
        this.id = id;
    }
}
