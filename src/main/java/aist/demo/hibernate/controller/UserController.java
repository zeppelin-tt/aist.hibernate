package aist.demo.hibernate.controller;

import aist.demo.hibernate.annotate.ApiController;
import aist.demo.hibernate.dto.UserDto;
import aist.demo.hibernate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@ApiController("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


//    @GetMapping("registration")
//    public ResponseEntity preRegister() {
//        return userService.getUserData();
//    }

    // TODO: 19.01.2019 непонятно, зачем дублирование...
    @GetMapping({"login", "registration"})
    public ResponseEntity preLogin() {
        return userService.getUserData();
    }

    @PostMapping("login")
    public void postLogin(@RequestBody() UserDto model) {
        userService.setToken(model);
    }

    @PutMapping("registration")
    public UserDto postRegister(@RequestBody() UserDto model) {
        return userService.registerUser(model);
    }

}
