package com.mycompany.user;

import com.mycompany.dto.UsersDTO;
import com.mycompany.entity.Users2;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository2 extends CrudRepository<Users2, Integer> {

}
