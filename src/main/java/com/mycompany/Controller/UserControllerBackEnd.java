package com.mycompany.Controller;

import com.mycompany.dto.UsersDTO;
import com.mycompany.entity.Users;
import com.mycompany.entity.Users2;
import com.mycompany.user.UserNotFoundException;
import com.mycompany.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//@Controller     //để xử lý các request, trả về trang
@RestController   //xây dựng các RESTful API
@RequestMapping(path = "/api/v1") //Annotation này ánh xạ các HTTP request tới các phương thức xử lý của MVC và REST controller
public class UserControllerBackEnd {
    @Autowired
    private UserService service;   //service Lấy dữ liệu từ UserService


    //Sử dụng Postman để test api
    //Kiểm tra dữ liệu nhập vào Class User
    @GetMapping (value="/test-users")
    public Users TestShowUser(@RequestBody Users user) {

        System.out.println("Id: " + user.getId());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        System.out.println("First Name: " + user.getFirstName());
        System.out.println("Last Name: " + user.getLastName());
        System.out.println("Enabled: " + user.isEnabled());

        return user;
    }

    /*
    {
            "id": "7",
            "email": "admin7@gmail.com",
            "password": 123456,
            "firstName": "admin7",
            "lastName": "Intern",
            "enabled": false
    } */


    //Test Postman, xem danh sách các users
    @GetMapping (value="/users/all")
    public  List<Users> TestShowUserList(@RequestBody Users user) {
        List<Users> listUsers = service.listAll();

        System.out.println("Id: " + user.getId());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        System.out.println("First Name: " + user.getFirstName());
        System.out.println("Last Name: " + user.getLastName());
        System.out.println("Enabled: " + user.isEnabled());

        return listUsers; //Trả về trang lstUsers.html để xem danh sách các users
    }

       /*
    {
            "id": null

    } */


    //Test Postman, xem danh sách các users có enabled là true
    @GetMapping (value="/users")
    public  List<Users> TestShowUserListEnabled() {
        List<Users> listUsers = service.listAllEnabled();

        //  System.out.println("Id: " + user.getId());
        //  System.out.println("Email: " + user.getEmail());
        //  System.out.println("Password: " + user.getPassword());
        //  System.out.println("First Name: " + user.getFirstName());
        //  System.out.println("Last Name: " + user.getLastName());
        //  System.out.println("Enabled: " + user.isEnabled());

        return listUsers; //Trả về trang lstUsers.html để xem danh sách các users
    }

    //Test Postman, thêm 1 user mới
    @PostMapping (value="/users")
    public Users2 TestCreateUser(@RequestBody Users2 AddNewUser) {
        return service.save2(AddNewUser);
    }

    /*
    {

            "email": "admin8@gmail.com",
            "password": 123456,
            "firstName": "admin8",
            "lastName": "Intern",
            "enabled": true
    } */

    //Test Postman edit, tìm kiếm thông tin user theo mã id
    @GetMapping(value="/users/{id}")
    public Object TestShowEditForm(@PathVariable("id") Integer id) {
        try {
            return service.searchID(id);

        } catch (UserNotFoundException e) {

            return e.getMessage();
        }
    }

   /*
    {
        "id": null

    } */

    //Test Postman edit, tìm kiếm thông tin user theo mã id, lưu lại thay đổi thông tin người dùng
    @PutMapping (value="/users/{id}")
    public Users TestShowEditSaveForm(@RequestBody Users user, @PathVariable("id") Integer id) {
        try {
            user.setId(id);
            return service.editSave(user);
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


    //Test Postman delete, xoá luôn
    @DeleteMapping (value="/test-users/{id}")
    public Users TestDeleteUser(@RequestBody Users user, @PathVariable("id") Integer id) {
        try {
            service.delete(id);

        } catch (UserNotFoundException e) {

        }
        return user;
    }

     /*
    {
        "id": null

    } */

    //Test Postman delete, xoá tạm thời, cập nhật enable true thành false để ẩn khỏi danh sách các users có enabled là true mới được hiển thị.
    @DeleteMapping ("/users/{id}")
    public String TestDeleteUserEnabled(@PathVariable("id") Integer id) {
        try {
            service.deleteEnabledTrue(id);   //Cập nhật lại enable true thành false để ẩn khỏi danh sách các users có enabled là true, xem hàm trong UserService
            return "Delete success";
        } catch (UserNotFoundException e) {

            return e.getMessage();
        }
    }
//
    //Master 14:31 12.05.2023
    //Dev 14.33 12.05.2023
    //Dev 14:58 12.05.2023
}
