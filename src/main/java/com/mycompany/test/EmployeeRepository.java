package com.mycompany.test;

import com.mycompany.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Users, Integer> {

}
