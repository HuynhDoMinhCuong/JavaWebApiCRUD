package com.mycompany.entity;


import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsersEmail {
    String message() default "Vui lòng nhập Email";
    Class<?>[] groups() default {};

 
}
