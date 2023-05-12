package com.mycompany.Controller;

import com.mycompany.entity.Users;
import com.mycompany.entity.Users2;
import com.mycompany.user.UserNotFoundException;
import com.mycompany.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller     //để xử lý các request, trả về trang html
//@RestController   //xây dựng các RESTful API
@RequestMapping(path = "/api/v1") //Annotation này ánh xạ các HTTP request tới các phương thức xử lý của MVC và REST controller
public class UserControllerFullStack {
    @Autowired
    private UserService service;   //service Lấy dữ liệu từ UserService

    //Read: dùng GET method
    @GetMapping ("/ListUsers")        //Đặt tên đường dẫn, viết lại đường dẫn ở trang index.html sẽ thấy đường dẫn th:href="@{/ListUsers}">
    public String showUserList(Model model) {
        List<Users> listUsers = service.listAllEnabled(); //Lấy danh sách tất cả các Users
        model.addAttribute("listUsers", listUsers);    //Viết lại "listUsers" đã đặt trong trang lstUsers.html     <th:block th:each="user : ${listUsers}">

        System.out.println("User Controller");
        return "lstUsers"; //Trả về trang lstUsers.html để xem danh sách các users
    }

    //
    @GetMapping ("/ListUsers/AddNewUser") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsers.html sẽ thấy đường dẫn th:href="@{/ListUsers/AddNewUser}
    public String showNewForm(Model model) {
        model.addAttribute("AddNewUser", new Users()); //Đặt tên AddNewUser, gọi tên vào th:object="${AddNewUser}" trong user_form.html
        model.addAttribute("pageTitle", "Add New User"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form.html, in ra chữ AddNewUser
        return "user_form2"; //Trả về trang user_form2.html để điền các thông tin của 1 user, form thêm đã ràng buộc id trong Users2
    }

    //Create: dùng POST method, editSave
    @PostMapping("/ListUsers/save") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form.html sẽ thấy đường dẫn th:action="@{/ListUsers/save}" method="post" th:object="${AddNewUser}"
    public String saveUser (Users AddNewUser, RedirectAttributes ra) {
            service.save(AddNewUser); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form.html
            System.out.println("Hello: " + AddNewUser.getFirstName());
            ra.addFlashAttribute("message", "The user has been saved successfully"); //Đưa ra thông báo khi lưu thông tin người dùng thành công, xem lstUsers.html sẽ thấy khai báo [[${message}]] hiển thị thông báo
            return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn ListUsers, trang lstUsers.html
    }

    //Create: dùng POST method, Save
    @PostMapping("/ListUsers/save2") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form.html sẽ thấy đường dẫn th:action="@{/ListUsers/save}" method="post" th:object="${AddNewUser}"
    public String saveUser2 (Users2 AddNewUser, RedirectAttributes ra) {
        service.save2(AddNewUser); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form.html
        System.out.println("Hello: " + AddNewUser.getFirstName());
        ra.addFlashAttribute("message", "The user has been saved successfully"); //Đưa ra thông báo khi lưu thông tin người dùng thành công, xem lstUsers.html sẽ thấy khai báo [[${message}]] hiển thị thông báo
        return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn ListUsers, trang lstUsers.html
    }

    @GetMapping("/ListUsers/edit/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang users.html, đoạn code edit và delete theo mã id, th:href="@{'/ListUsers/edit/' + ${user.id}}"
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Users user = service.searchID(id); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form.html
            model.addAttribute("EditUser", user); //Đặt tên EditUser, gọi tên vào th:object="${EditUser}" trong user_form.html
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form.html, in ra chữ Edit User có mã là...
            return "user_form"; //Trả về trang user_form.html, dùng để điền các thông tin của 1 user, editSave
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm save. Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsers.html
            return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn ListUsers, trang lstUsers.html
        }
    }

    // Cách 1. Nhấn nút delete trong lisUser.html sẽ cập nhật lại trường enable từ true thành false
    /*
    @GetMapping("/ListUsers/delete/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsers.html, đoạn code edit và delete theo mã id, th:href="@{'/ListUsers/edit/' + ${user.id}}"
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            service.deleteEnabledTrue(id);
            ra.addFlashAttribute("message","The user ID " + id + " has been deleted.");

        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm delete.Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsers.html
        }
        return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn ListUsers, trang lstUsers.html
    }*/

    // Cách 2. Dựa vào hàm editSave. Khi nhấn nút Delete sẽ đưa toàn bộ thông tin dữ liệu id đó lên user_form3. Từ user_form3.html Khi nhấn nút Delete sẽ cập nhật lại trường enable true thành false
    @GetMapping("/ListUsers/delete/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstusers.html, đoạn code edit và delete theo mã id, th:href="@{'/ListUsers/edit/' + ${user.id}}"
    public String showDeleteForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Users user = service.searchID(id); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form.html
            model.addAttribute("DeleteUser", user); //Đặt tên DeleteUser, gọi tên vào th:object="${DeleteUser}" trong user_form3.html
            model.addAttribute("pageTitle", "Delete User (ID: " + id + ")"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form.html, in ra chữ Edit User có mã là...
            return "user_form3"; //Trả về trang user_form.html, dùng để điền các thông tin của 1 user, editSave
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm save. Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsers.html
            return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn ListUsers, trang lstUsers.html
        }
    }

    // user_form3.html khi nhấn nút Delete sẽ dẫn xuống hàm này, cập nhật lại trường enable true thành false, tránh xoá luôn dữ liệu, danh sách user sẽ bị ẩn id có trường enable là false, còn trong MySQL vẫn còn chứa dữ liệu, không xoá luôn
    @PostMapping("/ListUsers/delete") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form3.html sẽ thấy đường dẫn th:action="@{/ListUsers/delete}" method="post" th:object="${AddNewUser}"
    public String deleteUser (Users DeleteUser, RedirectAttributes ra) {
        System.out.println("Hello: " + DeleteUser.getFirstName());
        try {
            service.deleteEnabledTrue2(DeleteUser); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form.html
            ra.addFlashAttribute("message", "The user has been saved successfully"); //Đưa ra thông báo khi lưu thông tin người dùng thành công, xem lstUsers.html sẽ thấy khai báo [[${message}]] hiển thị thông báo

        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm delete.Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsers.html
        }
        return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn ListUsers, trang lstUsers.html

    }


}
