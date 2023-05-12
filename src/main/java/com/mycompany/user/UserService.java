package com.mycompany.user;

import com.mycompany.dto.UsersDTO;
import com.mycompany.entity.Users;
import com.mycompany.entity.Users2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;       //Lấy dữ liệu từ UserRepository
    @Autowired
    private UserRepository2 repo2;

    //Lấy tất cả danh sách từ bảng User
    public List<Users> listAll() {
        return (List<Users>) repo.findAll();
    }


    //Lấy tất cả danh sách từ bảng User, dựa vào trường enabled
    public List<Users> listAllEnabled() {
        return (List<Users>) repo.findAllByEnabled(true);  //Lấy ra danh sách user có enabled là true
    }

    //Hàm save dùng cho hàm editSave (Sửa 1 user)
    public Users save(Users EditSave){
        Users save = repo.save(EditSave);
        return save;
    }


    //Hàm save dùng cho hàm thêm mới 1 Users
    public Users2 save2(Users2 AddNewUser){
        Users2 save2 = repo2.save(AddNewUser);
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
    public Users save3(Users AddNewUser) throws UserNotFoundException{
        Users save3 = repo.save(AddNewUser); //Tìm kiếm theo mã id

        //Kiểm tra giá trị
        if (save3.getId()!=null) {

            return save3;
        }
        throw new UserNotFoundException("Could not find any users with ID " + save3.getId()); //Xuất thông báo không tìm thấy bất kỳ users nào với id là...
    }


    // Hàm edit theo mã id user
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

            Users save = this.save(updateUser);
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

//Master 13:25 12.05.2023
}
