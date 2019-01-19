package aist.demo.hibernate.controller;

import aist.demo.hibernate.annotate.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// TODO: 19.01.2019 swagger добавить в класс. Я просто в нем не разбираюсь, убрал пока...
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
    public void postLogin(@RequestBody() UserModel model) {
        userService.setToken(model);
    }

    @PutMapping("registration")
    public UserModel postRegister(@RequestBody() UserModel model) {
        return userService.registerUser(model);
    }

}
