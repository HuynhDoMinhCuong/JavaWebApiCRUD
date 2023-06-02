package com.mycompany.Controller;

import com.mycompany.entity.Users;
import com.mycompany.user.UserNotFoundException;
import com.mycompany.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller     //Để xử lý các request, trả về trang html
//@RestController   //Xây dựng các RESTful API
@RequestMapping(path = "/api/v1") //Annotation này ánh xạ các HTTP request tới các phương thức xử lý của MVC và REST controller
public class UserControllerFullStack {
    @Autowired
    private UserService service;   //service Lấy dữ liệu từ UserService

    //Read: dùng GET method
    //Danh sách các users có trường enabled là true
    @GetMapping ("/ListUsers")        //Đặt tên đường dẫn, viết lại đường dẫn ở trang index.html sẽ thấy đường dẫn th:href="@{/ListUsers}">
    public String showUserListTrue(Model model) {
        List<Users> listUsers = service.listAllEnabledTrue(); //Lấy danh sách tất cả các Users có trường enabled là true
        model.addAttribute("listUsersEnableTrue", listUsers);    //Viết lại "listUsersEnableTrue" đã đặt trong trang lstUsersEnableTrue.html     <th:block th:each="user : ${listUsersEnableTrue}">

        System.out.println("User Controller");
        return "lstUsersEnableTrue"; //Trả về trang lstUsersEnableTrue.html để xem danh sách các users có trường enable là true
    }

    //Read: dùng GET method
    //Danh sách các users ó trường enabled là false
    @GetMapping ("/ListUsers/Recover")        //Đặt tên đường dẫn, viết lại đường dẫn ở trang index.html sẽ thấy đường dẫn <a class="h2" th:href="@{/api/v1/ListUsers/Recover}"> Recover Deleted Users </a>
    public String showUserListFalse(Model model) {
        List<Users> listUsers = service.listAllEnabledFalse(); //Lấy danh sách tất cả các Users có trường enabled là false
        model.addAttribute("listUsersEnableFalse", listUsers);    //Viết lại "listUsers" đã đặt trong trang lstUsersEnableFalse.html     <th:block th:each="user : ${listUsersAll}">

        System.out.println("User Controller");
        return "lstUsersEnableFalse"; //Trả về trang lstUsersEnableFalse.html để xem danh sách các users
    }

    //Read: dùng GET method
    //Hàm dẫn đường dẫn đến trang user_form_Save.html
    @GetMapping ("/ListUsers/AddNewUser") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsersEnableTrue.html sẽ thấy đường dẫn <a class="h3" th:href="@{/api/v1/ListUsers/AddNewUser}"> Add New User </a>
    public String showNewForm(Model model) {
        model.addAttribute("AddNewUser", new Users()); //Đặt tên AddNewUser, gọi tên vào th:object="${AddNewUser}" trong user_form_Save.html
        model.addAttribute("pageTitle", "Add New User"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form_Update.html, in ra chữ AddNewUser
        return "user_form_Save"; //Trả về trang user_form_Save.html để điền các thông tin của 1 user
    }

    //Create: dùng POST method
    //Hàm save dùng để lưu 1 user mới
    //Xem Class Users sẽ thấy khai báo private boolean enabled = true; //Mặc định khi khởi tạo là true = 1
    @PostMapping("/ListUsers/save") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form_Add.html sẽ thấy đường dẫn th:action="@{/ListUsers/save}" method="post" th:object="${AddNewUser}"
    public String saveUser (Users AddNewUser, RedirectAttributes ra) throws UserNotFoundException{
        service.saveNewUser(AddNewUser); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
        System.out.println("Hello: " + AddNewUser.getFirstName());
        ra.addFlashAttribute("message", "The user has been save successfully"); //Đưa ra thông báo khi lưu thông tin người dùng thành công, xem lstUsersEnableTrue.html sẽ thấy khai báo [[${message}]] hiển thị thông báo
        return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn @GetMapping ("/ListUsers"), trang lstUsersEnableTrue.html

    }

    //Create: dùng POST method
    //Hàm dẫn đường dẫn đến trang lstUsersEnableTrue.html
    @PostMapping("/ListUsers/update") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form_Update.html sẽ thấy đường dẫn th:action="@{/ListUsers/update}" method="post" th:object="${EditUser}"
    public String updateUser (Users EditUser, RedirectAttributes ra) throws UserNotFoundException {
        service.EditSave(EditUser); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
        System.out.println("Hello: " + EditUser.getFirstName());
        ra.addFlashAttribute("message", "The user has been update successfully"); //Đưa ra thông báo khi lưu thông tin người dùng thành công, xem lstUsersEnableTrue.html sẽ thấy khai báo [[${message}]] hiển thị thông báo
        return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn @GetMapping ("/ListUsers"), trang lstUsersEnableTrue.html
    }

    //Create: dùng POST method, editSave
    //Hàm dẫn đường dẫn đến trang lstUsersEnableFalse.html
    @PostMapping("/ListUsers/updateEnable") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form_Update_Enabled.html sẽ thấy đường dẫn th:action="@{/ListUsers/updateEnable}" method="post" th:object="${EditUser}"
    public String updateUserEnable (Users EditUser, RedirectAttributes ra) throws UserNotFoundException {
        service.EditSave(EditUser); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
        System.out.println("Hello: " + EditUser.getFirstName());
        ra.addFlashAttribute("message", "The user has been update successfully"); //Đưa ra thông báo khi lưu thông tin người dùng thành công, xem lstUsersEnableTrue.html sẽ thấy khai báo [[${message}]] hiển thị thông báo
        return "redirect:/api/v1/ListUsers/Recover"; //Trả về đường dẫn @GetMapping ("/ListUsers/Recover"), trang lstUsersEnableFalse.html
    }

    //Read: dùng GET method
    //Hàm updateUser, xem trang user_form_Update.html. Không cho cập nhật lại trường enable, các trường khác sửa thông tin và cập nhật bình thường.
    @GetMapping("/ListUsers/edit/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsersEnableTrue.html, đoạn code edit và delete theo mã id, th:href="@{'/ListUsers/edit/' + ${user.id}}"
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Users user = service.searchID(id); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
            model.addAttribute("EditUser", user); //Đặt tên EditUser, gọi tên vào th:object="${EditUser}" trong user_form_Update.html
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form_Update.html, in ra chữ Edit User có mã là...
            return "user_form_Update"; //Trả về trang user_form_Update.html, dùng để điền các thông tin của 1 user, editSave
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm save. Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsersEnableTrue.html
            return "redirect:/api/v1/ListUsers/enabled"; //Trả về đường dẫn @GetMapping ("/ListUsers"), trang lstUsersEnableTrue.html
        }
    }

    //Read: dùng GET method
    //Hàm update, xem trang user_form_Update_Enable cho phép chỉnh sửa trường enable từ false sang true
    @GetMapping("/ListUsers/editEnable/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsersEnableFalse.html, đoạn code edit và delete theo mã id, th:href="@{'/ListUsers/editEnable/' + ${user.id}}"
    public String showEditFormUpdateEnable(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Users user = service.searchID(id); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
            model.addAttribute("EditUserUpdateEnable", user); //Đặt tên EditUserUpdateEnable, gọi tên vào th:object="${EditUserUpdateEnable}" trong user_form_Update_Enable.html
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form_Update.html, in ra chữ Edit User có mã là...
            return "user_form_Update_Enable"; //Trả về trang user_form_Update_Enable.html, dùng để điền các thông tin của 1 user, editSave
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm save. Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsersEnableTrue.html
            return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn @GetMapping ("/ListUsers"), trang lstUsersEnableFalse.html
        }
    }

    //Cách 1. Nhấn nút delete trong lisUser.html sẽ cập nhật lại trường enable từ true thành false
    /*
    @GetMapping("/ListUsers/delete/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsersEnableTrue.html, đoạn code edit và delete theo mã id, th:href="@{'/ListUsers/edit/' + ${user.id}}"
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            service.deleteEnabledTrue(id);
            ra.addFlashAttribute("message","The user ID " + id + " has been deleted.");

        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm delete.Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsersEnableTrue.html
        }
        return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn ListUsers, trang lstUsersEnableTrue.html
    }*/

    //Read: dùng GET method
    //Cách 2. Dựa vào hàm editSave. Khi nhấn nút Delete sẽ đưa toàn bộ thông tin dữ liệu id đó lên user_form_Delete. Từ user_form_Delete.html Khi nhấn nút Delete sẽ cập nhật lại trường enable true thành false
    @GetMapping("/ListUsers/delete/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstusers.html, đoạn code edit và delete theo mã id, th:href="@{'/ListUsers/edit/' + ${user.id}}"
    public String showDeleteForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Users user = service.searchID(id); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
            model.addAttribute("DeleteUser", user); //Đặt tên DeleteUser, gọi tên vào th:object="${DeleteUser}" trong user_form_Delete.html
            model.addAttribute("pageTitle", "Delete User (ID: " + id + ")"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form_Update.html, in ra chữ Edit User có mã là...
            return "user_form_Delete"; //Trả về trang user_form_Delete.html
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm save. Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsersEnableTrue.html
            return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn @GetMapping ("/ListUsers/enabled"), trang lstUsersEnableTrue.html
        }
    }

    //Create: dùng POST method
    //user_form_Delete.html khi nhấn nút Delete sẽ dẫn xuống hàm này, cập nhật lại trường enable true thành false, tránh xoá luôn dữ liệu, danh sách user sẽ bị ẩn id có trường enable là false, còn trong MySQL vẫn còn chứa dữ liệu, không xoá luôn
    @PostMapping("/ListUsers/delete") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form3.html sẽ thấy đường dẫn th:action="@{/ListUsers/delete}" method="post" th:object="${AddNewUser}"
    public String deleteUser (Users DeleteUser, RedirectAttributes ra) {
        System.out.println("Hello: " + DeleteUser.getFirstName());
        try {
            service.deleteEnabledTrue2(DeleteUser); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
            ra.addFlashAttribute("message", "The user has been delete successfully"); //Đưa ra thông báo khi lưu thông tin người dùng thành công, xem lstUsersEnableTrue.html sẽ thấy khai báo [[${message}]] hiển thị thông báo

        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm delete.Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsersEnableTrue.html
        }
        return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn @GetMapping ("/ListUsers/enabled"), trang lstUsersEnableTrue.html
    }

}
