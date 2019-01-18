package aist.demo.hibernate.layers.controller;

import aist.demo.hibernate.annotate.ApiController;
import aist.demo.hibernate.domain.model.object.StandModel;
import aist.demo.hibernate.layers.service.StandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@ApiController("stand") // was stands
public class StandController {

    private final StandService service;

    @Autowired
    public StandController(StandService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public StandModel getStand(@PathVariable("id") Long id) {
        return service.find(id);
    }

    @PostMapping(path = "/update")
    public Long update(@RequestBody StandModel standModel) {
        return service.update(standModel);
    }

    @PostMapping(path = "/save")
    @ResponseStatus(code = HttpStatus.CREATED)
    public StandModel save(@RequestBody StandModel standModel) {
        return service.save(standModel);
    }

    @GetMapping
    public Set<StandModel> getAllStands() {
        return service.findAll();
    }


}
