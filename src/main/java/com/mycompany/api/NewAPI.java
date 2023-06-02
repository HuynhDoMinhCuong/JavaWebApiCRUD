package com.mycompany.api;

import com.mycompany.dto.UsersDTO;
import org.springframework.web.bind.annotation.*;

//@Controller
@RestController
public class NewAPI {
    //@RequestMapping(value = "/new", method = RequestMethod.POST)

    //@ResponseBody


    //Lấy ra danh sách
    //UserDTO chỉ là class truyền và nhận dữ liệu, không lưu vào MySQL
    //Dùng công cụ Postman để test các api
    @PostMapping (value = "/new")

    public UsersDTO createNew(@RequestBody UsersDTO model) {

        return model;
    }

    @PutMapping (value = "/new")

    public UsersDTO updateNew(@RequestBody UsersDTO model) {

        return model;
    }

    @DeleteMapping (value = "/new")
    public void deleteNew(@RequestBody long[] ids) {

    }
}
