package com.mycompany.test;

import com.mycompany.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<User, Integer> {

}
