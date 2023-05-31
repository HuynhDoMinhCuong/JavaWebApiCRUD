package com.mycompany.user;

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


    //Hàm tìm kiếm theo tên gần giống, firstName và lastName và xuất ra danh sách, xem câu lệnh Query trong UserRepository
    public List<Users> findAllSearchName(String keyword) {
        if (keyword != null) {
            return repo.findAllSearchName(keyword);
        }
        return (List<Users>) repo.findAll();
    }

    //Hàm tìm kiếm theo số id và xuất ra danh sách, xem câu lệnh Query trong UserRepository
    public List<Users> findAllSearchID(String id) {
        if (id != null) {
            return repo.findAllSearchID(id);
        }
        return (List<Users>) repo.findAll();
    }



    //Lấy tất cả danh sách từ bảng User, dựa vào trường enabled là true thì mới xuất ra danh sách
    public List<Users> listAllEnabledTrue() {
        return (List<Users>) repo.findAllByEnabled(true);  //Trả về danh sách Users có enabled là true
    }

    public List<Users> listAllEnabledFalse() {
        return (List<Users>) repo.findAllByEnabled(false);  //Trả về danh sách Users có enabled là false
    }

    //Hàm EditSave, sửa thông tin 1 user và lưu lại. Dùng cho update và delete tạm thời (cập nhập trường enable từ true thành false để ẩn khỏi danh sách users có trường enable là false)
    //Xem UserControllerBackEnd sẽ thấy được gọi EditSave ở hàm @PutMapping (value="/users/{id}") ,Xem UserControllerFullStack sẽ thấy được gọi EditSave ở hàm @PostMapping("/ListUsers/update")
    public Users EditSave(Users EditSave){
        Users editSave = repo.save(EditSave);
        return editSave;
    }

    /*
    //Hàm save2 dùng cho hàm thêm mới 1 Users, Class Users2 bị ràng buộc id, id tự tăng theo stt. Tránh trường hợp dùng Postman gọi id bị trùng vào khiến nó update lại thay vì thêm mới.
    public Users2 save2(Users2 AddNewUser){
        Users2 addUser = repo2.save(AddNewUser);
        return addUser;

    }*/

    //Hàm saveNewUser, dùng để thêm 1 user mới, bị ràng buộc bởi hàm public boolean isValidationId.
    public Users saveNewUser (Users AddNewUser) throws UserNotFoundException {

        if(isValidationId(AddNewUser)) {
            Users addUser = repo.save(AddNewUser);
            return addUser;
        }
        throw new UserNotFoundException("Could not save this users with ID " + AddNewUser.getId()); //Thông báo lỗi: Không thể lưu users này với id là...
    }

    // Hàm boolean xét đúng, sai.
    public boolean isValidationId(Users users) {
        if(users.getId() != null) {              //Không cho nhập id, tránh trường hợp dùng Postman nhập id đã có trong MySQL vào hàm thêm 1 user mới khiến nó update lại thay vì thêm 1 user mới.
            return false;
        }
       return true;
    }

    //Test
    /*
    public Users save3(Users AddNewUser) throws UserNotFoundException{
        Users save3 = repo.save(AddNewUser); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (save3.getId()!=null) {

            return save3;
        }
        throw new UserNotFoundException("Could not find any users with ID " + save3.getId()); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }*/

    // Hàm tìm kiếm theo mã id user
    // Xem trang lstUsersEnableTrue.html sẽ thấy đoạn code lấy đường dẫn lấy theo mã id User, edit. <a class="h4 mr-3" th:href="@{'/api/v1/ListUsers/edit/' + ${user.id}}" >Edit</a>
    // UserController @GetMapping("/ListUsers/edit/{id}")
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

    //Hàm update
    public Users editSave(Users user) throws UserNotFoundException {
        Optional<Users> result = repo.findById(user.getId()); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (result.isPresent()) {
            Users updateUser = result.get();

            //updateUser.setId(user.getId());
            updateUser.setEmail(user.getEmail());
            updateUser.setPassword(user.getPassword());
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setEnabled(user.isEnabled());

            Users EditSave = this.EditSave(updateUser);  //Chuyển đến hàm EditSave để lưu lại
            return EditSave;
        }
        throw new UserNotFoundException("Could not find any users with ID " + user.getId()); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }

    //Hàm xoá tạm thời theo mã id user, cập nhật lại trường enabled từ true thành false để ẩn khỏi danh sách users.
    //Xem UserControllerBackEnd sẽ thấy gọi hàm deleteEnabledTrue trong hàm @DeleteMapping ("/users/{id}")
    //Hàm này dùng Postman để kiểm tra api, tìm thấy mã id user thì tiến hành update lại thông tin mới.
    public Users deleteEnabledTrue(int id) throws UserNotFoundException {
        Optional<Users> result = repo.findById(id); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (result.isPresent()) {
            result.get().setEnabled(false);                     //Cập nhật trường enabled thành false

            Users EditSave = this.EditSave(result.get());       //Chuyển đến hàm EditSave để lưu lại
            return EditSave;
        }
        throw new UserNotFoundException("Could not find any users with ID " + id); //Xuất thông báo lỗi không tìm thấy bất kỳ users nào với id là...
    }

    //Hàm xoá tạm thời theo mã id user, cập nhật lại trường enabled từ true thành false để ẩn khỏi danh sách users.
    //Xem UserControllerFullStack sẽ thấy gọi hàm deleteEnabledTrue2 trong hàm @DeleteMapping ("/users/{id}")
    //Hàm này tương tự hàm EditSave, sử dụng trong trường hợp trả về trang web html, tìm thấy mã id user sẽ lấy thông tin users đưa lên user_form_Delete.html. Sau đó ở trong form nhấn nút Delete để xác nhận xoá tạm thời.
    public Users deleteEnabledTrue2(Users user) throws UserNotFoundException {
        Optional<Users> result = repo.findById(user.getId()); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (result.isPresent()) {
            Users updateUser = result.get();

            //Trường enable của Class Users trong Database MySQL hiển thị số 1 là true, số 0 là false
            updateUser.setEnabled(false);                   //Cập nhật lại trường enable trong Class Users từ true thành false. Thì trong danh sách Users nó sẽ bị ẩn thông tin có id đó và trong Database MySQL thì vẫn còn chứa thông tin users, không xoá luôn.

            Users EditSave = this.EditSave(updateUser);     //Chuyển đến hàm EditSave để lưu lại
            return EditSave;
        }
        throw new UserNotFoundException("Could not find any users with ID " + user.getId()); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }

}
