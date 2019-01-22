package aist.demo.controller;

import aist.demo.annotate.ApiController;
import aist.demo.dto.UserDto;
import aist.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiController("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // TODO: 19.01.2019 непонятно, зачем дублирование...
    @GetMapping({"login", "registration"})
    public ResponseEntity preLogin() {
        return userService.getUserData();
    }

    @PostMapping("login")
    public void postLogin(@RequestBody() UserDto dto) {
        userService.setToken(dto);
    }

    @PostMapping("registration")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDto postRegister(@RequestBody() UserDto dto) {
        return userService.registerUser(dto);
    }

}
