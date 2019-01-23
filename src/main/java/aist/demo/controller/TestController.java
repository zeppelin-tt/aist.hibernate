package aist.demo.controller;


import aist.demo.dto.TestDto;
import aist.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("test")
public class TestController {
    // TODO: 20.01.2019 метод getGroupsAndPotentialMembers полностью завязан на регистрации... Пока тут все просто для демо.

    private final TestService service;

    @Autowired
    public TestController(TestService service) {
        this.service = service;
    }

    @GetMapping
    public Set<TestDto> getAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TestDto save(@RequestBody TestDto dto) {
        return service.save(dto);
    }

}
