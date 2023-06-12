package com.mycompany.Controller;

import com.mycompany.entity.Users;
import com.mycompany.user.UserNotFoundException;
import com.mycompany.user.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller     //Để xử lý các request, trả về trang html
//@RestController   //Xây dựng các RESTful API
@RequestMapping(path = "/api/v1") //Annotation này ánh xạ các HTTP request tới các phương thức xử lý của MVC và REST controller
public class UserControllerFullStack {
    @Autowired
    private UserService service;   //service Lấy dữ liệu từ UserService

    //Read: dùng GET method
    //Danh sách các users
    @GetMapping ("/list-users/all")        //Đặt tên đường dẫn, viết lại đường dẫn ở trang index.html sẽ thấy đường dẫn th:href="@{/list-users/all}">
    public String showUserListAll(Model model, @Param("keyword") String keyword, @Param("id") Integer id) {
        if (keyword != null){
            List<Users> listUsers = service.findAllSearchName(keyword);          //Tìm kiếm Users theo tên gần giống
            model.addAttribute("listUsersAll", listUsers);           //Viết lại "listUsersAll" đã đặt trong trang lstUsersAll.html     <th:block th:each="user : ${listUsersAll}">
        }
        else if (id != null){
            List<Users> listUsers = service.findAllSearchId(id); //Tìm kiếm Users theo mã id
            //Users listUsers = service.searchID(id); //
            model.addAttribute("listUsersAll", listUsers);          //Viết lại "listUsersAll" đã đặt trong trang lstUsersAll.html     <th:block th:each="user : ${listUsersAll}">
        }
        else {
            List<Users> listUsersAll = service.listAll(); //Lấy danh sách tất cả các Users
            model.addAttribute("listUsersAll", listUsersAll);      //Viết lại "listUsersAll" đã đặt trong trang lstUsersAll.html     <th:block th:each="user : ${listUsersAll}">
        }
        return "lstUsersAll"; //Trả về trang lstUsersAll.html
    }

    //Read: dùng GET method
    //Danh sách các users có trường enabled là true
    @GetMapping ("/list-users/online")        //Đặt tên đường dẫn, viết lại đường dẫn ở trang index.html sẽ thấy đường dẫn th:href="@{/list-users/online}">
    public String showUserListTrue(Model model, @Param("keyword") String keyword, @Param("id") Integer id) throws UserNotFoundException{
        if (keyword != null){
            List<Users> listUsers = service.findAllSearchNameEnabledTrue(keyword); //Tìm kiếm Users theo tên gần giống có enabled là true
            model.addAttribute("lstUsersEnableTrue", listUsers);       //Viết lại "lstUsersEnableTrue" đã đặt trong trang lstUsersEnabledTrue.html     <th:block th:each="user : ${lstUsersEnableTrue}">
        }
        else if (id != null){
            List<Users> listUsers = service.findAllSearchIdEnabledTrue(id);      //Tìm kiếm Users theo mã id có enabled là true
            model.addAttribute("lstUsersEnableTrue", listUsers);      //Viết lại "lstUsersEnableTrue" đã đặt trong trang lstUsersEnabledTrue.html     <th:block th:each="user : ${lstUsersEnableTrue}">
        }
        else
        {
            List<Users> listUsersEnabledTrue = service.listAllEnabledTrue();            //Lấy danh sách tất cả các Users có enabled là true
            model.addAttribute("lstUsersEnableTrue", listUsersEnabledTrue); //Viết lại "lstUsersEnableTrue" đã đặt trong trang lstUsersEnabledTrue.html     <th:block th:each="user : ${lstUsersEnableTrue}">
        }
        System.out.println("User Controller");
        return "lstUsersEnabledTrue"; //Trả về trang lstUsersEnabledTrue.html
    }

    //Read: dùng GET method
    //Danh sách các users ó trường enabled là false
    @GetMapping ("/list-users/recover")        //Đặt tên đường dẫn, viết lại đường dẫn ở trang index.html sẽ thấy đường dẫn <a class="h2" th:href="@{/api/v1/list-users/recover}"> Recover Deleted Users </a>
    public String showUserListFalse(Model model, @Param("keyword") String keyword, @Param("id") Integer id) throws UserNotFoundException{
        if (keyword != null){
            List<Users> listUsers = service.findAllSearchNameEnabledFalse(keyword); //Tìm kiếm Users theo tên gần giống có enabled là false
            model.addAttribute("lstUsersEnableFalse", listUsers);    //Viết lại "lstUsersEnableFalse" đã đặt trong trang lstUsersEnabledFalse.html     <th:block th:each="user : ${lstUsersEnableFalse}">
        }
        else if (id != null){
            List<Users> listUsers = service.findAllSearchIdEnabledFalse(id);    //Tìm kiếm Users theo mã id có enabled là false
            model.addAttribute("lstUsersEnableFalse", listUsers);    //Viết lại "lstUsersEnableFalse" đã đặt trong trang lstUsersEnabledFalse.html     <th:block th:each="user : ${lstUsersEnableFalse}">
        }
        else{
            List<Users> listUsersEnabledFalse = service.listAllEnabledFalse();             //Lấy danh sách tất cả các Users có enable là false
            model.addAttribute("lstUsersEnableFalse", listUsersEnabledFalse);  //Viết lại "lstUsersEnableFalse" đã đặt trong trang lstUsersEnabledFalse.html     <th:block th:each="user : ${lstUsersEnableFalse}">
        }
        System.out.println("User Controller");
        return "lstUsersEnabledFalse"; //Trả về trang lstUsersEnabledFalse.html
    }
    //Read: dùng GET method
    //Hàm dẫn đường dẫn đến trang user_form_Save.html
    @GetMapping ("/list-users/add-new-user") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsersEnabledTrue.html sẽ thấy đường dẫn <a class="h3" th:href="@{/api/v1/ListUsers/AddNewUser}"> Add New User </a>
    public String showNewForm(Model model) {

        model.addAttribute("AddNewUser", new Users()); //Đặt tên AddNewUser, gọi tên vào th:object="${AddNewUser}" trong user_form_Save.html
        model.addAttribute("pageTitle", "Add New User"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form_Update.html, in ra chữ AddNewUser
        return "user_form_Save"; //Trả về trang user_form_Save.html để điền các thông tin của 1 user

    }

    //Create: dùng POST method
    //Hàm save dùng để lưu 1 user mới
    //Xem Class Users sẽ thấy khai báo private boolean enabled = true; //Mặc định khi khởi tạo là true = 1
    @PostMapping("/list-users/save") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form_Add.html sẽ thấy đường dẫn th:action="@{/ListUsers/save}" method="post" th:object="${AddNewUser}"
    public String saveUser (Model model, Users AddNewUser, RedirectAttributes ra) throws UserNotFoundException {
        if (AddNewUser.getEmail()!=null){
            service.saveNewUser(AddNewUser);
            System.out.println("Hello: " + AddNewUser.getFirstName());
            ra.addFlashAttribute("message", "The user has been save successfully"); //Đưa ra thông báo khi lưu thông tin người dùng thành công, xem lstUsersAll.html sẽ thấy khai báo [[${message}]] hiển thị thông báo
            return "redirect:/api/v1/list-users/all"; //Trả về đường dẫn @GetMapping ("/list-users/all"), trang lstUsersAll.html
        }

        else {
            model.addAttribute("AddNewUser", new Users()); //Đặt tên AddNewUser, gọi tên vào th:object="${AddNewUser}" trong user_form_Save.html
            model.addAttribute("pageTitle", "Add New User"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form_Update.html, in ra chữ AddNewUser
            return "user_form_Save"; //Trả về trang user_form_Save.html để điền các thông tin của 1 user
        }


/*
        try {
            service.saveNewUser(AddNewUser);
            System.out.println("Hello: " + AddNewUser.getFirstName());
            ra.addFlashAttribute("message", "The user has been save successfully"); //Đưa ra thông báo khi lưu thông tin người dùng thành công, xem lstUsersAll.html sẽ thấy khai báo [[${message}]] hiển thị thông báo
            return "redirect:/api/v1/list-users/all"; //Trả về đường dẫn @GetMapping ("/list-users/all"), trang lstUsersAll.html
        } catch (UserNotFoundException e) {
            //ra.addFlashAttribute("message", e.getMessage());
            ra.addFlashAttribute("message", "The user has been don't save successfully");
            //return "redirect:/api/v1/list-users/all"; //Trả về đường dẫn @GetMapping ("/ListUsers"), trang lstUsersEnabledTrue.html
            model.addAttribute("AddNewUser", new Users()); //Đặt tên AddNewUser, gọi tên vào th:object="${AddNewUser}" trong user_form_Save.html
            model.addAttribute("pageTitle", "Add New User"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form_Update.html, in ra chữ AddNewUser
            return "user_form_Save"; //Trả về trang user_form_Save.html để điền các thông tin của 1 user
        }

*/


    }

    //Read: dùng GET method
    //Hàm cập nhật Users, xem trang user_form_Update.html. Không cho sửa lại trường enable, các trường khác sửa thông tin và cập nhật bình thường.
    @GetMapping("/list-users/update/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsersAll, lstUsersEnabledTrue.html, đoạn code edit và delete theo mã id, th:href="@{'/list-users/update/' + ${user.id}}" và  th:href="@{'/api/v1/list-users/delete/' + ${user.id}}"
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Users user = service.searchID(id); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
            model.addAttribute("EditUser", user); //Đặt tên EditUser, gọi tên vào th:object="${EditUser}" trong user_form_Update.html
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form_Update.html, in ra chữ Edit User có mã là...
            return "user_form_Update"; //Trả về trang user_form_Update.html, dùng để điền các thông tin của 1 user, editSave
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm save. Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsersEnabledTrue.html
            return "redirect:/api/v1/list-users/all"; //Trả về đường dẫn @GetMapping ("/list-users/all"), trang lstUsersEnabledTrue.html
        }
    }

    //Hàm dẫn đường dẫn đến trang lstUsersEnabledTrue.html
    @PostMapping("/list-users/update") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form_Update.html sẽ thấy đường dẫn th:action="@{/ListUsers/update}" method="post" th:object="${EditUser}"
    public String updateUser (Users EditUser, RedirectAttributes ra) throws UserNotFoundException {
        service.save(EditUser);
        System.out.println("Hello: " + EditUser.getFirstName());
        ra.addFlashAttribute("message", "The user has been update successfully"); //Đưa ra thông báo khi cập nhật thông tin người dùng thành công, xem lstUsersEnabledTrue.html sẽ thấy khai báo [[${message}]] hiển thị thông báo
        return "redirect:/api/v1/list-users/all"; //Trả về đường dẫn @GetMapping ("/list-users/all"), trang lstUsersAll.html
    }

    //Read: dùng GET method
    //Hàm update, xem trang user_form_Update_Enable cho phép chỉnh sửa trường enable từ false sang true
    @GetMapping("/list-users/update-enabled/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsersEnabledFalse.html, đoạn code Recover theo mã id, th:href="@{'/api/v1/list-user/update-enabled/' + ${user.id}}"
    public String showEditFormUpdateEnable(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Users user = service.searchID(id); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
            model.addAttribute("EditUserUpdateEnable", user); //Đặt tên EditUserUpdateEnable, gọi tên vào th:object="${EditUserUpdateEnable}" trong user_form_Update_Enable_False_to_True.html
            model.addAttribute("pageTitle", "Recover User (ID: " + id + ")"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form_Update.html, in ra chữ Edit User có mã là...
            return "user_form_Update_Enable_False_to_True"; //Trả về trang user_form_Update_Enable_False_to_True.html, dùng để điền các thông tin của 1 user, editSave
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm save. Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsersEnabledTrue.html
            return "redirect:/api/v1/list-users/all"; //Trả về đường dẫn @GetMapping ("/ListUsers"), trang lstUsersEnabledFalse.html
        }
    }

    //Hàm dẫn đường dẫn đến trang lstUsersEnabledFalse.html
    @PostMapping("/list-users/update-enabled") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form_Update_Enabled_False_to_True.html sẽ thấy đường dẫn th:action="@{/list-users/update-enabled}" method="post" th:object="${EditUserUpdateEnable}"
    public String updateUserEnable (Users EditUser, RedirectAttributes ra) throws UserNotFoundException {
        service.updateEnableFalse2(EditUser);
        System.out.println("Hello: " + EditUser.getFirstName());
        ra.addFlashAttribute("message", "The user has been recover successfully"); //Đưa ra thông báo khi khôi phục thông tin người dùng thành công, xem lstUsersEnabledTrue.html sẽ thấy khai báo [[${message}]] hiển thị thông báo
        return "redirect:/api/v1/list-users/recover"; //Trả về đường dẫn @GetMapping ("/ListUsers/Recover"), trang lstUsersEnabledFalse.html
    }

    //Cách 1. Nhấn nút delete trong lisUser.html sẽ cập nhật lại trường enable từ true thành false
    /*
    @GetMapping("/ListUsers/delete/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsersEnabledTrue.html, đoạn code edit và delete theo mã id, th:href="@{'/ListUsers/edit/' + ${user.id}}"
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            service.deleteEnabledTrue(id);
            ra.addFlashAttribute("message","The user ID " + id + " has been deleted.");

        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm delete.Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsersEnabledTrue.html
        }
        return "redirect:/api/v1/ListUsers"; //Trả về đường dẫn ListUsers, trang lstUsersEnabledTrue.html
    }*/

    //Read: dùng GET method
    //Cách 2. Dựa vào hàm update. Khi nhấn nút Delete sẽ đưa toàn bộ thông tin dữ liệu id đó lên user_form_Delete. Từ user_form_Delete.html Khi nhấn nút Delete sẽ cập nhật lại trường enable true thành false
    @GetMapping("/list-users/delete/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsersEnabledTrue.html, đoạn code edit và delete theo mã id, th:href="@{'/list-users/delete/' + ${user.id}}"
    public String showDeleteForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Users user = service.searchID(id); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
            model.addAttribute("DeleteUser", user); //Đặt tên DeleteUser, gọi tên vào th:object="${DeleteUser}" trong user_form_Delete.html
            model.addAttribute("pageTitle", "Delete User (ID: " + id + ")"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form_Update.html, in ra chữ Edit User có mã là...
            return "user_form_Delete"; //Trả về trang user_form_Delete.html
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm save. Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsersEnabledTrue.html
            return "redirect:/api/v1/list-users/online"; //Trả về đường dẫn @GetMapping ("/list-users/online"), trang lstUsersEnabledTrue.html
        }
    }

    //user_form_Delete.html khi nhấn nút Delete sẽ dẫn xuống hàm này, cập nhật lại trường enable true thành false, tránh xoá luôn dữ liệu, danh sách user sẽ bị ẩn id có trường enable là false, còn trong MySQL vẫn còn chứa dữ liệu, không xoá luôn
    @PostMapping("/list-users/delete") //Đặt tên đường dẫn, viết lại đường dẫn trang users_form_Delete.html sẽ thấy đường dẫn th:action="@{/list-users/delete}" method="post" th:object="${DeleteUser}"
    public String deleteUser (Users DeleteUser, RedirectAttributes ra) {
        System.out.println("Hello: " + DeleteUser.getFirstName());
        try {
            service.deleteEnabledTrue2(DeleteUser); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
            ra.addFlashAttribute("message", "The user has been delete successfully"); //Đưa ra thông báo khi lưu thông tin người dùng thành công, xem lstUsersEnabledTrue.html sẽ thấy khai báo [[${message}]] hiển thị thông báo

        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm delete.Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsersEnabledTrue.html
        }
        return "redirect:/api/v1/list-users/online"; //Trả về đường dẫn @GetMapping ("/list-users/enabled"), trang lstUsersEnabledTrue.html
    }

    /*
    @GetMapping("/ListUsers/All/{id}") //Đặt tên đường dẫn, viết lại đường dẫn trang lstUsersAll.html, đoạn code edit và delete theo mã id, th:href="@{'/ListUsers/All/' + ${user.id}}"
    public String showEditFormAll(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Users user = service.searchID(id); //Lấy thông tin user theo mã id, khi người dùng nhấn vào edit hoặc delete trong danh sách user_form_Update.html
            model.addAttribute("listUsersAll", user); //Đặt tên UserSearch, gọi tên vào th:object="${UserSearch}" trong user_form_All.html
            model.addAttribute("pageTitle", "Search User (ID: " + id + ")"); //Gọi tiêu đề trang tab <title> và <h2> là [[${pageTitle}]] ở user_form_Update.html, in ra chữ Edit User có mã là...
            return "lstUsersAll"; //Trả về trang user_form_All.html, dùng để điền các thông tin của 1 user, editSave
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage()); //e.getMessage sẽ lấy thông báo từ UserService của hàm save. Thông báo lỗi, thông báo sẽ hiển thị ở trang danh sách users, lstUsersEnabledTrue.html
            return "redirect:/api/v1/ListUsers/All"; //Trả về đường dẫn @GetMapping ("/ListUsers/Search"), trang lstUsersAll.html
        }
    }*/

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "error";
    }

}
