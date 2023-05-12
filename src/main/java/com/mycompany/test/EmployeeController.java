package com.mycompany.test;

import com.mycompany.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employess")
public class EmployeeController {
    @Autowired
    private  EmployeeRepository employeeRepository;

    @PostMapping
    public User createEmployee(@RequestBody User user) {
        return employeeRepository.save(user);
    }
}
