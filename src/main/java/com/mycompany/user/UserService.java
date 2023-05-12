package com.mycompany.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;       //Lấy dữ liệu từ UserRepository
    @Autowired
    private UserRepository2 repo2;

    //Lấy tất cả danh sách từ bảng User
    public List<User> listAll() {
        return (List<User>) repo.findAll();
    }


    //Lấy tất cả danh sách từ bảng User, dựa vào trường enabled
    public List<User> listAllEnabled() {
        return (List<User>) repo.findAllByEnabled(true);  //Lấy ra danh sách user có enabled là true
    }

    //Hàm save dùng cho hàm editSave (Sửa 1 user)
    public User save(User EditSave){
        User save = repo.save(EditSave);
        return save;
    }

    //Hàm save dùng cho hàm thêm mới 1 Users
    public User2 save2(User2 AddNewUser){
        User2 save2 = repo2.save(AddNewUser);
        return save2;
    }


    //


    //Hàm Save kiểm tra giá trị id đã tồn tại trong MySQL hay chưa.
    // Sử dụng Postman gọi mã id vào.
    /*
       {
            "id": 18,
            "email": "admin18@gmail.com",
            "password": 123456,
            "firstName": "admin18",
            "lastName": "Intern",
            "enabled": true
       }
     */
    public User saveNewUser(User AddNewUser) throws UserNotFoundException{
        User save = repo.save(AddNewUser); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (save.isEnabled()) {

            return save;
        }
        throw new UserNotFoundException("Could not find any users with ID " + AddNewUser.getId()); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }


    // Hàm edit theo mã id user
    // Xem trang lstUsers.html sẽ thấy đoạn code lấy đường dẫn lấy theo mã id User, edit. <a class="h4 mr-3" th:href="@{'/api/v1/ListUsers/edit/' + ${user.id}}" >Edit</a>
    // UserController @GetMapping("/ListUsers/edit/{id}")
    public User searchID(Integer id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id); //Tìm kiếm theo mã id
        //Kiểm tra giá trị
        if (result.isPresent()) {
            return result.get();
        }
        throw new UserNotFoundException("Could not find any users with ID " +id); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }


    //Hàm xoá theo mã id user
    public void delete (Integer id) throws UserNotFoundException {
         Long count = repo.countById(id);   //Độ dài giá trị đếm theo id, xem hàm khai báo countById trong UserRepository
        //Kiểm tra giá trị đếm
        if (count == null || count ==0) {
            throw new UserNotFoundException("Could not find any users with ID " +id); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...

        }

         repo.deleteById(id);
    }

   //
    public User editSave(User user) throws UserNotFoundException {
        Optional<User> result = repo.findById(user.getId()); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (result.isPresent()) {
            User updateUser = result.get();

            //updateUser.setId(user.getId());
            updateUser.setEmail(user.getEmail());
            updateUser.setPassword(user.getPassword());
            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setEnabled(user.isEnabled());

            User save = this.save(updateUser);
            return save;
        }
        throw new UserNotFoundException("Could not find any users with ID " + user.getId()); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }

    //Hàm xoá tạm thời theo mã id user, cập nhật lại trường enabled từ true thành false để ẩn khỏi danh sách users
    public User deleteSaveEnabled(int id) throws UserNotFoundException {
        Optional<User> result = repo.findById(id); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (result.isPresent()) {
            result.get().setEnabled(false);          //Cập nhật trường enabled thành false

            User updateEnabled = this.save(result.get());
            return updateEnabled;
        }
        throw new UserNotFoundException("Could not find any users with ID " + id); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }
//Master 13:25 12.05.2023
}
