package com.mycompany.test;

import com.mycompany.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employess")
public class EmployeeController {
    @Autowired
    private  EmployeeRepository employeeRepository;

    @PostMapping
    public Users createEmployee(@RequestBody Users user) {
        return employeeRepository.save(user);
    }
}
