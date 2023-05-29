package com.mycompany.user;

import com.mycompany.entity.Users;
import com.mycompany.entity.Users2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //Trao đổi dữ liệu giữa các ứng dụng hoặc giữa các hệ thống
public class UserService {
    @Autowired
    private UserRepository repo;        //Lấy dữ liệu từ UserRepository
    @Autowired
    private UserRepository2 repo2;      //Lấy dữ liệu từ UserRepository2

    //Lấy tất cả danh sách từ bảng User
    public List<Users> listAll() {
        return (List<Users>) repo.findAll();
    }

    //Lấy tất cả danh sách từ bảng User, dựa vào trường enabled là true thì mới xuất ra danh sách
    public List<Users> listAllEnabled() {
        return (List<Users>) repo.findAllByEnabled(true);  //Trả về danh sách Users có enabled là true
    }

    //Hàm save dùng cho hàm editSave, sửa thông tin 1 user và lưu lại. Dùng cho update và delete tạm thời (cập nhập trường enable từ true thành false để ẩn khỏi danh sách users có trường enable là false)
    public Users save(Users EditSave){
        Users editSave = repo.save(EditSave);
        return editSave;
    }

    //Hàm save2 dùng cho hàm thêm mới 1 Users, Class Users2 bị ràng buộc id, id tự tăng theo stt. Tránh trường hợp dùng Postman gọi id bị trùng vào khiến nó update lại thay vì thêm mới.
    public Users2 save2(Users2 AddNewUser){
        Users2 addUser = repo2.save(AddNewUser);
        return addUser;
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
    // Xem trang lstUsers.html sẽ thấy đoạn code lấy đường dẫn lấy theo mã id User, edit. <a class="h4 mr-3" th:href="@{'/api/v1/ListUsers/edit/' + ${user.id}}" >Edit</a>
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

            Users save = this.save(updateUser);  //Lấy thông tin đã sửa
            return save;
        }
        throw new UserNotFoundException("Could not find any users with ID " + user.getId()); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }

    //Hàm xoá tạm thời theo mã id user, cập nhật lại trường enabled từ true thành false để ẩn khỏi danh sách users
    public Users deleteEnabledTrue(int id) throws UserNotFoundException {
        Optional<Users> result = repo.findById(id); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (result.isPresent()) {
            result.get().setEnabled(false);          //Cập nhật trường enabled thành false

            Users updateEnabled = this.save(result.get());
            return updateEnabled;
        }
        throw new UserNotFoundException("Could not find any users with ID " + id); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }

    public Users deleteEnabledTrue2(Users user) throws UserNotFoundException {
        Optional<Users> result = repo.findById(user.getId()); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (result.isPresent()) {
            Users updateUser = result.get();


            updateUser.setEnabled(false);

            Users save = this.save(updateUser);
            return save;
        }
        throw new UserNotFoundException("Could not find any users with ID " + user.getId()); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }

//
}
