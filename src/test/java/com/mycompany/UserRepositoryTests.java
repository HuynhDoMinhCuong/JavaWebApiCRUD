package com.mycompany;

import com.mycompany.entity.Users;
import com.mycompany.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

//Hàm test
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @Test //Hàm thêm
    public void testAddNew(){
        Users user = new Users();
        user.setEmail("admin2.gmail.com");
        user.setPassword("123456");
        user.setFirstName("admin2");
        user.setLastName("Intern");

        Users savedUser = repo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test //Hàm lấy danh sách users
    public void testListAll() {
        Iterable<Users> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for (Users user : users) {
            System.out.println(user);
        }
    }

    @Test //Hàm update
    public void testUpdate() {
        Integer userId = 1;
        Optional<Users> optionalUser = repo.findById(userId);
        Users user = optionalUser.get();
        user.setPassword("654321");
        repo.save(user);

        Users updatedUser = repo.findById(userId).get();
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("654321");
    }

    @Test //Hàm tìm kiếm user theo mã userId
    public void testGet() {
        Integer userId = 2;
        Optional<Users> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());

    }

    @Test //Hàm xoá user theo mã userId
    public void testDelete() {
        Integer userId = 2;
        repo.deleteById(userId);

        Optional<Users> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();

    }

}
