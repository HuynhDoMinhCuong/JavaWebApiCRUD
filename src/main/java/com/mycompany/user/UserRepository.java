package com.mycompany.user;

import com.mycompany.entity.Users;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

//Từ Class User, có khoá chính là ID, kiểu dữ liệu Integer
//CrudRepository có các hàm save, findById, delete
//Dùng cho hàm tìm kiếm theo id, editSave, delete.
public interface UserRepository extends CrudRepository<Users, Integer> {
    //
    public Long countById (Integer id); //Độ dài giá trị đếm theo id
    //
    public List<Users> findAllByEnabled(boolean enabled);  //Tìm trường enabled trong Class User để xuất ra danh sách các users có enabled là true

}
