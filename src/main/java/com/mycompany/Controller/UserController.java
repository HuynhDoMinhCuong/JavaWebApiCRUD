package com.mycompany.Controller;

import com.mycompany.user.User;
import com.mycompany.user.UserNotFoundException;
import com.mycompany.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//@Controller     //để xử lý các request
@RestController   //xây dựng các RESTful API
@RequestMapping(path = "/api/v1") //Annotation này ánh xạ các HTTP request tới các phương thức xử lý của MVC và REST controller
public class UserController {
    @Autowired
    private UserService service;   //service Lấy dữ liệu từ UserService

    //Read: dùng GET method
    @GetMapping ("/ListUsers")        //Đặt tên đường dẫn, viết lại đường dẫn ở trang index.html sẽ thấy đường dẫn th:href="@{/ListUsers}">
    public String showUserList(Model model) {
        List<User> listUsers = service.listAll(); //Lấy danh sách tất cả các Users
        model.addAttribute("listUsers", listUsers);    //Viết lại "listUsers" đã đặt trong trang lstUsers.html     <th:block th:each="user : ${listUsers}">

        System.out.println("User Controller");
        return "lstUsers"; //Trả về trang lstUsers.html để xem danh sách các users
    }

    //
    @GetMapping ("/ListUsers/AddNewUser") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsers.html sẽ thấy đường dẫn th:href="@{/ListUsers/AddNewUser}
    public String showNewForm(Model model) {
        model.addAttribute("AddNewUser", new User()); //Đặt tên AddNewUser, gọi tên vào th:object="${AddNewUser}" trong user_form.html
        model.addAttribute("pageTitle", "Add New User"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form.html, in ra chữ AddNewUser
        return "user_form"; //Trả về trang user_form.html để điền các thông tin của 1 user
    }

    //Create: dùng POST method
    @PostMapping("/ListUsers/save") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form.html sẽ thấy đường dẫn th:action="@{/ListUsers/save}" method="post" th:object="${AddNewUser}"
    public String saveUser (User AddNewUser, RedirectAttributes ra) {
         service.save(AddNewUser);
         System.out.println("Hello: " + AddNewUser.getFirstName());
         ra.addFlashAttribute("message", "The user has been saved successfully"); //Đưa ra thông báo khi lưu thông tin người dùng thành công, xem lstUsers.html sẽ thấy khai báo [[${message}]] hiển thị thông báo
         return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn ListUsers, trang lstUsers.html
    }

    @GetMapping("/ListUsers/edit/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang users.html, đoạn code edit và delete theo mã id, th:href="@{'/ListUsers/edit/' + ${user.id}}"
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = service.edit(id); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form.html
            model.addAttribute("AddNewUser", user); //Đặt tên AddNewUser, gọi tên vào th:object="${AddNewUser}" trong user_form.html
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form.html, in ra chữ Edit User có mã là...
            return "user_form"; //Trả về trang user_form.html, dùng để điền các thông tin của 1 user
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm save. Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsers.html
            return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn ListUsers, trang lstUsers.html
        }
    }


    @GetMapping("/ListUsers/delete/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsers.html, đoạn code edit và delete theo mã id, th:href="@{'/ListUsers/edit/' + ${user.id}}"
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message","The user ID " + id + " has been deleted.");

        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm delete.Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsers.html
        }
        return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn ListUsers, trang lstUsers.html
    }


    //Test Postman
    //Test Class User
    @GetMapping ("/test-users")
    public User TestShowUser(@RequestBody User user) {

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
    @GetMapping ("/test-list-users")
    public  List<User> TestShowUserList(@RequestBody User user) {
        List<User> listUsers = service.listAll();

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
    @GetMapping ("/test-list-users-enabled-true")
    public  List<User> TestShowUserListEnabled(@RequestBody User user) {
        List<User> listUsers = service.listAllEnabled();

        System.out.println("Id: " + user.getId());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        System.out.println("First Name: " + user.getFirstName());
        System.out.println("Last Name: " + user.getLastName());
        System.out.println("Enabled: " + user.isEnabled());

        return listUsers; //Trả về trang lstUsers.html để xem danh sách các users
    }

    //Test Postman, thêm 1 user mới
    @PostMapping ("/test-create-users")
    public User TestCreateUser(@RequestBody User AddNewUser) {
        return service.save(AddNewUser);

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
    @GetMapping("/test-list-users-edit/{id}")
    public User TestShowEditForm(@RequestBody User user, @PathVariable("id") Integer id) {
        try {
            return service.edit(id);
        } catch (UserNotFoundException e) {

            return user;
        }
    }

   /*
    {
        "id": null

    } */

    //Test Postman edit, tìm kiếm thông tin user theo mã id, lưu lại thay đổi thông tin người dùng
    @PutMapping ("/test-list-users-edit-save/{id}")
    public User TestShowEditSaveForm(@RequestBody User user, @PathVariable("id") Integer id) {
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
    @GetMapping("/test-list-users-delete/{id}")
    public User TestDeleteUser(@RequestBody User user, @PathVariable("id") Integer id) {
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
    @PutMapping ("/test-list-users-delete-enabled-false/{id}")
    public String TestDeleteUserEnabled(@PathVariable("id") Integer id) {
        try {
            service.deleteSaveEnabled(id);   //Cập nhật lại enable true thành false để ẩn khỏi danh sách các users có enabled là true, xem hàm trong UserService
            return "Delete success"    ;
        } catch (UserNotFoundException e) {

            return e.getMessage();
        }
    }

    //Master 14:31 12.05.2023
    //Dev 14.33 12.05.2023
}
