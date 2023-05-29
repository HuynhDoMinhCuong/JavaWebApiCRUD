package com.mycompany.user;

import com.mycompany.entity.Users2;
import org.springframework.data.repository.CrudRepository;


//Từ Class User, có khoá chính là ID, kiểu dữ liệu Integer
//CrudRepository có các hàm save, findById, delete
//Dùng cho hàm thêm 1 thông tin user, bị ràng buộc id trong User2, tránh dùng Postman nhập id trùng khiến nó update lại thay vì thêm mới.
public interface UserRepository2 extends CrudRepository<Users2, Integer> {

}
