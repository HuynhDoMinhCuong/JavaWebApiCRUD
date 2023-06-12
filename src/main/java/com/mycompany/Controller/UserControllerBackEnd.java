package com.mycompany.Controller;

import com.mycompany.dto.UsersDTO;
import com.mycompany.entity.Users;
import com.mycompany.user.UserNotFoundException;
import com.mycompany.user.UserRepository;
import com.mycompany.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@Controller     //Để xử lý các request, trả về trang
@RestController   //Xây dựng các RESTful API
@RequestMapping(path = "/api/v1") //Annotation này ánh xạ các HTTP request tới các phương thức xử lý của MVC và REST controller
public class UserControllerBackEnd {
    @Autowired
    private UserService service;   //service lấy dữ liệu từ UserService

    //Sử dụng Postman để test api
    //Kiểm tra dữ liệu nhập vào Class UsersDTO, không có lưu vào Database MySQL.
    @PostMapping (value="/test/users")
    public UsersDTO TestShowUser(@RequestBody UsersDTO userDto) {

        System.out.println("id: " + userDto.getId());
        System.out.println("email: " + userDto.getEmail());
        System.out.println("Password: " + userDto.getPassword());
        System.out.println("First Name: " + userDto.getFirstName());
        System.out.println("Last Name: " + userDto.getLastName());
        System.out.println("Enabled: " + userDto.isEnabled());

        return userDto;
    }

    /*
    {
            "id": "1",
            "email": "admin1@gmail.com",
            "password": 123456,
            "firstName": "admin1",
            "lastName": "Intern",
            "enabled": true
    } */

    //Test Postman, xem danh sách tất cả các users có trong Database MySQL
    @GetMapping (value="/users/all")
    public  List<Users> TestShowUserList() {
        List<Users> listUsers = service.listAll();

        //System.out.println("Id: " + user.getId());
        //System.out.println("Email: " + user.getEmail());
        //System.out.println("Password: " + user.getPassword());
        //System.out.println("First Name: " + user.getFirstName());
        //System.out.println("Last Name: " + user.getLastName());
        //System.out.println("Enabled: " + user.isEnabled());

        return listUsers;
    }

    //Test Postman, xem danh sách các users có enabled là true
    @GetMapping (value="/users/true")
    public  List<Users> TestShowUserListEnabledTrue() {
        List<Users> listUsers = service.listAllEnabledTrue();

        //  System.out.println("Id: " + user.getId());
        //  System.out.println("Email: " + user.getEmail());
        //  System.out.println("Password: " + user.getPassword());
        //  System.out.println("First Name: " + user.getFirstName());
        //  System.out.println("Last Name: " + user.getLastName());
        //  System.out.println("Enabled: " + user.isEnabled());

        return listUsers;
    }

    //Test Postman, xem danh sách các users có enabled là true
    @GetMapping (value="/users/false")
    public  List<Users> TestShowUserListEnabledFalse() {
        List<Users> listUsers = service.listAllEnabledFalse();

        //  System.out.println("Id: " + user.getId());
        //  System.out.println("Email: " + user.getEmail());
        //  System.out.println("Password: " + user.getPassword());
        //  System.out.println("First Name: " + user.getFirstName());
        //  System.out.println("Last Name: " + user.getLastName());
        //  System.out.println("Enabled: " + user.isEnabled());

        return listUsers;
    }

    //Test Postman, thêm 1 user mới
    @PostMapping (value="/users")
    public Object TestCreateUser(@Validated @RequestBody Users AddNewUser) throws UserNotFoundException{

        if(AddNewUser.getEmail().isEmpty() || AddNewUser.getPassword().isEmpty() || AddNewUser.getFirstName().isEmpty() || AddNewUser.getLastName().isEmpty()) {
            return "Please enter full information";
        }

        else {
            try {
                return service.saveNewUser(AddNewUser);

            } catch (UserNotFoundException e) {

                return e.getMessage();
            }

        }

    }

    /*
    {

            "email": "admin8@gmail.com",
            "password": 123456,
            "firstName": "admin8",
            "lastName": "Intern",
            "enabled": true
    } */

    //Test Postman tìm kiếm thông tin user theo mã id
    @GetMapping(value="/users/{id}")
    public Object TestShowEditForm(@PathVariable("id") Integer id) {
        try {
            return service.searchID(id);

        } catch (UserNotFoundException e) {

            return e.getMessage();
        }
    }

    //Test Postman edit, tìm kiếm thông tin user theo mã id, lưu lại thay đổi thông tin người dùng
    @PutMapping (value="/users/{id}")
    public Users TestShowEditSaveForm(@RequestBody Users user, @PathVariable("id") Integer id) {
        try {
            user.setId(id);
            return service.updateUser(user);
        } catch (UserNotFoundException e) {

            return user;
        }
    }

     /*
    {

            "email": "admin9@gmail.com",
            "password": 123456,
            "firstName": "admin9",
            "lastName": "Intern",
            "enabled": true
    } */

    //Test Postman, tìm kiếm theo tên gần đúng (first name và last name)
    @GetMapping(value="/users/search/{name}")
    public List<Users> TestShowSearchName(@PathVariable("name") String name) {
        List<Users> listUsers = service.findAllSearchName(name);
        return listUsers;
    }

    //Test Postman delete, xoá luôn
    /*
    @DeleteMapping (value="/test-users/{id}")
    public Users TestDeleteUser(@RequestBody Users user, @PathVariable("id") Integer id) {
        try {
            service.delete(id);

        } catch (UserNotFoundException e) {

        }
        return user;
    }*/

     /*
    {
        "id": null

    } */

    //Test Postman delete, xoá tạm thời, cập nhật enable true thành false để ẩn khỏi danh sách các users có enabled là true mới được hiển thị.
    @DeleteMapping ("/users/{id}")
    public String TestDeleteUserEnabledTrue(@PathVariable("id") Integer id) {
        try {
            service.deleteEnabledTrue(id);   //Cập nhật lại enable true thành false để ẩn khỏi danh sách các users có enabled là true, xem hàm trong UserService
            return "Delete success";
        } catch (UserNotFoundException e) {

            return e.getMessage();
        }
    }

    //Test Postman khôi phục xoá tạm thời, cập nhật enable true thành false
    @GetMapping ("/users/recover/{id}")
    public String TestUpdateUserEnabledFalse(@PathVariable("id") Integer id) {
        try {
            service.updateEnableFalse(id);   //Cập nhật lại enable false thành true, xem hàm trong UserService
            return "Recover success";
        } catch (UserNotFoundException e) {

            return e.getMessage();
        }
    }


    //Test Postman tìm kiếm thông tin user theo mã id có trường enable là true
    @GetMapping(value="/users/enabled-true/{id}")
    public Object TestShowSearchIdTrue(@PathVariable("id") Integer id) {
        return service.findAllSearchIdEnabledTrue(id);
    }

    //Test Postman tìm kiếm thông tin user theo mã id có trường enable là false
    @GetMapping(value="/users/enabled-false/{id}")
    public Object TestShowSearchIdFalse(@PathVariable("id") Integer id) {
        return service.findAllSearchIdEnabledFalse(id);
    }

    //Test Postman, tìm kiếm theo tên gần đúng (first name và last name) có trường enable là true
    @GetMapping(value="/users/enabled-true/name/{name}")
    public List<Users> TestShowSearchNameTrue(@PathVariable("name") String name) {
        List<Users> listUsers = service.findAllSearchNameEnabledTrue(name);
        return listUsers;
    }

    //Test Postman, tìm kiếm theo tên gần đúng (first name và last name) có trường enable là false
    @GetMapping(value="/users/enabled-false/name/{name}")
    public List<Users> TestShowSearchNameFalse(@PathVariable("name") String name) {
        List<Users> listUsers = service.findAllSearchNameEnabledFalse(name);
        return listUsers;
    }

}
