package com.mycompany.entity;

import jakarta.persistence.*;
import lombok.Data;


//Dùng cho hàm tìm kiếm theo id, update, delete
@Data
@Entity
@Table(name = "users") //Tên bảng sẽ lưu vào MySQL
public class Users {
    @Id
    //Tự động tạo ra giá trị id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Khoá chính

    private Integer id;

    @Column(name = "email", nullable = false, unique = true, length = 45) //Khoá ngoại
    @UsersEmail
    private String email;


    @Column(name = "password", length = 15, nullable = false)
    private String password;

    @Column(name = "first_name", length = 45, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 45, nullable = false)
    private String lastName;

    @Column(name = "enabled")
    private boolean enabled = true; //Mặc định khi khởi tạo là true = 1


}