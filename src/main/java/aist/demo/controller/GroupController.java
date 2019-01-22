package aist.demo.controller;


import aist.demo.dto.GroupDto;
import aist.demo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("groups")
public class GroupController {
    // TODO: 20.01.2019 метод getGroupsAndPotentialMembers полностью завязан на регистрации... Пока тут все просто для демо.

    private final GroupService service;

    @Autowired
    public GroupController(GroupService service) {
        this.service = service;
    }

    @GetMapping
    public Set<GroupDto> getAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public GroupDto save(@RequestBody GroupDto dto) {
        return service.save(dto);
    }

}
