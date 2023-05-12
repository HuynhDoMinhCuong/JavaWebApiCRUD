package com.mycompany.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

//Từ Class User, có khoá chính là ID, kiểu dữ liệu Integer
//CrudRepository có các hàm save, findById, delete
public interface UserRepository extends CrudRepository<User, Integer> {
    public Long countById (Integer id); //Độ dài giá trị đếm theo id

    public List<User> findAllByEnabled(boolean enabled);  //Tìm trường enabled trong Class User để xuất ra danh sách các users có enabled là true
}
