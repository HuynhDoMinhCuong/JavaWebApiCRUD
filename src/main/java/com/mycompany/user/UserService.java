package com.mycompany.user;

import com.mycompany.dto.UsersDTO;
import com.mycompany.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //Trao đổi dữ liệu giữa các ứng dụng hoặc giữa các hệ thống
public class UserService {
    @Autowired
    private UserRepository repo;        //Lấy dữ liệu từ UserRepository

    //Lấy tất cả danh sách từ bảng User
    public List<Users> listAll() {
        return (List<Users>) repo.findAll();
    }

    //Lấy tất cả danh sách từ bảng User có trường enable là true
    public List<Users> listAllEnabledTrue() {
        return (List<Users>) repo.findAllByEnabled(true);  //Trả về danh sách Users có enabled là true
    }

    //Lấy tất cả danh sách từ bảng User có trường enable là false
    public List<Users> listAllEnabledFalse() {
        return (List<Users>) repo.findAllByEnabled(false);  //Trả về danh sách Users có enabled là false
    }

    //Hàm tìm kiếm theo tên gần giống, firstName và lastName và xuất ra danh sách, xem câu lệnh Query trong UserRepository
    public List<Users> findAllSearchName(String keyword) {
        if (keyword != null) {
            return repo.findAllSearchName(keyword);
        }
        return (List<Users>) repo.findAll();
    }

    //Hàm tìm kiếm theo tên gần giống, firstName và lastName, có enable là true và xuất ra danh sách, xem câu lệnh Query trong UserRepository
    public List<Users> findAllSearchNameEnabledTrue(String keyword) {
        if (keyword != null) {
            return repo.findAllSearchNameEnabledTrue(keyword);
        }
        return (List<Users>) repo.findAllByEnabled(true);
    }

    //Hàm tìm kiếm theo tên gần giống, firstName và lastName, có enable là false và xuất ra danh sách, xem câu lệnh Query trong UserRepository
    public List<Users> findAllSearchNameEnabledFalse(String keyword) {
        if (keyword != null) {
            return repo.findAllSearchNameEnabledFalse(keyword);
        }
        return (List<Users>) repo.findAllByEnabled(false);
    }

    //Hàm tìm kiếm theo id
    public List<Users> findAllSearchId(Integer id) {
        if (id != null) {
            return repo.findAllSearchID(id); //Tìm kiếm theo mã id
        }
        return (List<Users>) repo.findAll();
    }

    //Hàm tìm kiếm theo id, có enable là true và xuất ra danh sách, xem câu lệnh Query trong UserRepository
    public List<Users> findAllSearchIdEnabledTrue(Integer id) {
        if (id != null) {
            return repo.findAllSearchIdEnabledTrue(id); //Tìm kiếm theo mã id có enabled là true
        }
        return (List<Users>) repo.findAllByEnabled(true);
    }

    //Hàm tìm kiếm theo id, có enable là false và xuất ra danh sách, xem câu lệnh Query trong UserRepository
    public List<Users> findAllSearchIdEnabledFalse(Integer id) {
        if (id != null) {
            return repo.findAllSearchIdEnabledFalse(id); //Tìm kiếm theo mã id có enabled là false
        }
        return (List<Users>) repo.findAllByEnabled(false);
    }

    //Hàm Save để sửa thông tin 1 User và lưu lại. Dùng cho Update và Delete tạm thời (cập nhập trường enable từ true thành false để ẩn khỏi danh sách Users có trường enable là false)
    //Xem UserControllerBackEnd sẽ thấy được gọi hàm Save ở hàm @PutMapping (value="/users/{id}")
    //Xem UserControllerFullStack sẽ thấy được gọi Save ở hàm @PostMapping("/ListUsers/update")
    public Users save(Users Save){
        Users save = repo.save(Save);
        return save;
    }

    //Hàm saveNewUser, dùng để thêm 1 user mới, bị ràng buộc bởi hàm public boolean isValidationId.
    public Object saveNewUser (Users AddNewUser) throws UserNotFoundException {
        if(isValidationId(AddNewUser)) {
            Users addUser = repo.save(AddNewUser);
            return addUser;
        }
        throw new UserNotFoundException(" Do not enter ID: " + AddNewUser.getId()); //Thông báo lỗi:

       /* if(isValidationId(AddNewUser)){
            Users addUser = repo.save(AddNewUser);
            return addUser;
        }
        else {
            return "Fail";
        }*/


        /*try {
            if(isValidationId(AddNewUser)) {
                Users addUser = repo.save(AddNewUser);
                return addUser;

            }
            throw new UserNotFoundException(" Do not enter ID: " + AddNewUser.getId()); //Thông báo lỗi:

        } catch (UserNotFoundException e) {

            return e.getMessage();
        } */


    }

    //Hàm boolean xét đúng, sai.
    public boolean isValidationId(Users users) throws UserNotFoundException{

        if(users.getId() != null) {              //Không cho nhập id, tránh trường hợp dùng Postman nhập id đã có trong MySQL vào hàm thêm 1 user mới khiến nó update lại thay vì thêm 1 user mới.
           throw new UserNotFoundException(" Do not enter ID: " + users.getId()); //Thông báo lỗi:
        }
       return true;
    }

    //Hàm tìm kiếm theo mã id user
    //Xem trang lstUsersAll.html sẽ thấy đoạn code lấy đường dẫn theo mã id User
    //<a class="h4 mr-3" th:href="@{'/api/v1/ListUsers/update/' + ${user.id}}" > Edit </a>         <!-- Xem trong UserControllerFullStack hàm @GetMapping("/ListUsers/edit/{id}") -->
    //<a class="h4 mr-3" th:href="@{'/api/v1/ListUsers/delete/' + ${user.id}}" > Delete </a>       <!-- Xem trong UserControllerFullStack hàm @GetMapping("/ListUsers/delete/{id}") -->
    public Users searchID(Integer id) throws UserNotFoundException {
        Optional<Users> result = repo.findById(id); //Tìm kiếm theo mã id
        //Kiểm tra giá trị
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find any users with ID " +id); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }

    //Hàm xoá theo mã id user, xoá luôn trong Database MySQL
    /*
    public void delete (Integer id) throws UserNotFoundException {
         Long count = repo.countById(id);   //Độ dài giá trị đếm theo id, xem hàm khai báo countById trong UserRepository
        //Kiểm tra giá trị đếm
        if (count == null || count ==0) {
            throw new UserNotFoundException("Could not find any users with ID " +id); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
        }
         repo.deleteById(id);
    }*/

    //Hàm update, Postman.
    public Users updateUser(Users user) throws UserNotFoundException {
        Optional<Users> result = repo.findById(user.getId()); //Tìm kiếm theo mã id
        //Kiểm tra giá trị
        if (result.isPresent()) {
            Users update = result.get();

            //updateUser.setId(user.getId());
            update.setEmail(user.getEmail());
            update.setPassword(user.getPassword());
            update.setFirstName(user.getFirstName());
            update.setLastName(user.getLastName());
            update.setEnabled(user.isEnabled());

            Users updateUser = this.save(update);  //Chuyển đến hàm EditSave để lưu lại
            return updateUser;
        }
        throw new UserNotFoundException("Could not find any users with ID " + user.getId()); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }

    //Hàm xoá tạm thời theo mã id user, cập nhật lại trường enabled từ true thành false để ẩn khỏi danh sách users có trường enable là true.
    //Xem UserControllerBackEnd sẽ thấy gọi hàm deleteEnabledTrue trong hàm @DeleteMapping ("/users/{id}")
    //Hàm này dùng Postman để kiểm tra api, tìm thấy mã id user thì tiến hành update lại thông tin mới.
    public Users deleteEnabledTrue(int id) throws UserNotFoundException {
        Optional<Users> result = repo.findById(id); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (result.isPresent()) {
            result.get().setEnabled(false);                  //Cập nhật trường enabled thành false

            Users EditSave = this.save(result.get());       //Chuyển đến hàm Save để lưu lại
            return EditSave;
        }
        throw new UserNotFoundException("Could not find any users with ID " + id); //Xuất thông báo lỗi không tìm thấy bất kỳ users nào với id là...
    }

    //Hàm xoá tạm thời theo mã id user, cập nhật lại trường enabled từ true thành false để ẩn khỏi danh sách users.
    //Xem UserControllerFullStack sẽ thấy hàm @PostMapping("/ListUsers/delete")
    //Hàm này tương tự hàm update, sử dụng trong trường hợp trả về trang web html, tìm thấy mã id user sẽ lấy thông tin user đưa lên user_form_Delete.html. Sau đó ở trong form nhấn nút Delete để xác nhận xoá tạm thời (Update trường enable từ true thành false).
    public Users deleteEnabledTrue2(Users user) throws UserNotFoundException {
        Optional<Users> result = repo.findById(user.getId()); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (result.isPresent()) {
            Users updateUser = result.get();

            //Trường enable của Class Users trong Database MySQL hiển thị số 1 là true, số 0 là false
            updateUser.setEnabled(false);                //Cập nhật lại trường enable trong Class Users từ true thành false. Thì trong danh sách Users có trường enable là true nó sẽ bị ẩn thông tin có id đó và trong Database MySQL thì vẫn còn chứa thông tin users, không xoá luôn.

            Users EditSave = this.save(updateUser);     //Chuyển đến hàm Save để lưu lại
            return EditSave;
        }
        throw new UserNotFoundException("Could not find any users with ID " + user.getId()); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }

    //
    public Users updateEnableFalse(int id) throws UserNotFoundException {
        Optional<Users> result = repo.findById(id); //Tìm kiếm theo mã id
        //Kiểm tra giá trị
        if (result.isPresent()) {
            Users updateUser = result.get();

            //Trường enable của Class Users trong Database MySQL hiển thị số 1 là true, số 0 là false
            updateUser.setEnabled(true);                 //Cập nhật lại trường enable trong Class Users từ false thành true. Trong danh sách Users có trường enable là false, cập nhật lại thành true thì tài khoản User đó sẽ khôi phục

            Users EditSave = this.save(updateUser);     //Chuyển đến hàm Save để lưu lại
            return EditSave;
        }
        throw new UserNotFoundException("Could not find any users with ID " + id); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }

    //Cập nhật trường enable từ false thành true
    //Xem UserControllerFullStack sẽ thấy hàm @PostMapping("/ListUsers/updateEnable")
    public Users updateEnableFalse2(Users user) throws UserNotFoundException {
        Optional<Users> result = repo.findById(user.getId()); //Tìm kiếm theo mã id
        //Kiểm tra giá trị
        if (result.isPresent()) {
            Users updateUser = result.get();

            //Trường enable của Class Users trong Database MySQL hiển thị số 1 là true, số 0 là false
            updateUser.setEnabled(true);                 //Cập nhật lại trường enable trong Class Users từ false thành true. Trong danh sách Users có trường enable là false, cập nhật lại thành true thì tài khoản User đó sẽ khôi phục

            Users EditSave = this.save(updateUser);     //Chuyển đến hàm Save để lưu lại
            return EditSave;
        }
        throw new UserNotFoundException("Could not find any users with ID " + user.getId()); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }

}
