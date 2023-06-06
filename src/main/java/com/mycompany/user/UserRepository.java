package com.mycompany.user;

import com.mycompany.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

//Từ Class User, có khoá chính là ID, kiểu dữ liệu Integer
//CrudRepository có các hàm save, findById, delete
//Dùng cho hàm tìm kiếm theo id, editSave, delete.
public interface UserRepository extends CrudRepository<Users, Integer> {

    //Dùng cho hàm xoá luôn, xem trong UserService
    public Long countById (Integer id); //Độ dài giá trị đếm theo id

    //Xuất ra danh sách Users, dựa vào trường enable là true hoặc false
    //Xem trong UserService sẽ thấy gọi hàm tìm kiếm danh sách dựa vào enable là true hoặc false
    public List<Users> findAllByEnabled(boolean enabled);

    //Hàm tìm kiếm theo họ, tên gần giống
    @Query ("SELECT n FROM Users n WHERE n.firstName LIKE %?1%"
                + " OR n.lastName LIKE %?1%")
    public List<Users> findAllSearchName (String keyword);

    //Hàm tìm kiếm theo id
    @Query ("SELECT i FROM Users i WHERE i.id = ?1")
    public List<Users> findAllSearchID (String id);

    //Hàm tìm kiếm theo họ, tên gần giống, có trường enable là true
    @Query ("SELECT n FROM Users n WHERE n.firstName LIKE %?1% "
            + " AND n.enabled = true"
            + " OR n.lastName LIKE %?1%"
            + " AND n.enabled = true" )
    public List<Users> findAllSearchNameEnableTrue (String keyword);

    //Hàm tìm kiếm theo id có enable là true
    @Query ("SELECT i FROM Users i WHERE i.id = ?1"
            + " AND i.enabled = true" )
    public List<Users> findAllSearchIdEnableTrue (String id);

    //Hàm tìm kiếm theo họ, tên gần giống, có trường enable là false
    @Query ("SELECT n FROM Users n WHERE n.firstName LIKE %?1%"
            + " AND n.enabled = FALSE"
            + " OR n.lastName LIKE %?1%"
            + " AND n.enabled = false" )
    public List<Users> findAllSearchNameEnableFalse (String keyword);

    //Hàm tìm kiếm theo id có enable là false
    @Query ("SELECT i FROM Users i WHERE i.id = ?1"
            + " AND i.enabled = false " )
    public List<Users> findAllSearchIdEnableFalse (String id);


}
