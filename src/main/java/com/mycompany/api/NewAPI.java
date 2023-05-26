package com.mycompany.api;

import com.mycompany.dto.UserDTO;
import com.mycompany.user.User;
import com.mycompany.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
<<<<<<< HEAD
=======

>>>>>>> origin/Dev01
@RestController
public class NewAPI {
    //@RequestMapping(value = "/new", method = RequestMethod.POST)

    //@ResponseBody


    //Lấy ra danh sách
    //UserDTO chỉ là class truyền và nhận dữ liệu, không lưu vào MySQL
    //Dùng công cụ Postman để test các api
    @PostMapping (value = "/new")

    public UserDTO createNew(@RequestBody UserDTO model) {

        return model;
    }

    @PutMapping (value = "/new")

    public UserDTO updateNew(@RequestBody UserDTO model) {

        return model;
    }

    @DeleteMapping (value = "/new")
    public void deleteNew(@RequestBody long[] ids) {

    }
}
