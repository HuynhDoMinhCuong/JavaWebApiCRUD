package com.mycompany.entity;


import jakarta.servlet.annotation.HttpConstraint;

import java.lang.annotation.*;

@Documented
@HttpConstraint
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsersEmail {
    String message() default "Please enter your email";
    Class<?>[] groups() default {};
    Class<? extends Users>[] payload() default {};
 
}
