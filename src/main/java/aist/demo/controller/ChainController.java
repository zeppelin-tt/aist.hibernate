package aist.demo.controller;

import aist.demo.annotate.ApiController;
import aist.demo.dto.ChainDto;
import aist.demo.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

// TODO: 21.01.2019 тут во всем классе добавить штуки с авторизацией и РОЛЕВУЮ модель доступа!
@ApiController("chain") // was stands
public class ChainController {

    private final ChainService service;

    @Autowired
    public ChainController(ChainService service) {
        this.service = service;
    }

    @GetMapping("{id:\\d+}")
    public ChainDto getById(@PathVariable("id") Long id) {
        return service.find(id);
    }

    // TODO: 22.01.2019 добавить пагинацию или что-то в этом духе
    @GetMapping
    public Set<ChainDto> getAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ChainDto save(@RequestBody ChainDto dto) {
        return service.save(dto);
    }

    @PutMapping
    public void update(@RequestBody ChainDto dto) {
        service.update(dto);
    }

    @DeleteMapping("{id:\\d+}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    // TODO: 22.01.2019 добавить филтр
//    @PostMapping("filter")
//    public ResponseEntity<String> filter(@RequestHeader(value = AuthUtils.TOKEN_PARAM_NAME, required = false) String token,
//                                         @RequestBody ChainTemplatesFilter filter) {
//        filter.setUser(AuthUtils.getUsernameByToken(token));
//        return ResponseEntity.ok(filter.fetch());
//    }

}
