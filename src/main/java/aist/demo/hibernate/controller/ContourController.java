package aist.demo.hibernate.controller;

import aist.demo.hibernate.annotate.ApiController;
import aist.demo.hibernate.model.ContourModel;
import aist.demo.hibernate.service.ContourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@ApiController("contour") // was stands
public class ContourController {

    private final ContourService service;

    @Autowired
    public ContourController(ContourService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ContourModel getStand(@PathVariable("id") Long id) {
        return service.find(id);
    }

    @PostMapping(path = "update")
    public Long update(@RequestBody ContourModel contourModel) {
        return service.update(contourModel);
    }

    @PostMapping(path = "create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ContourModel save(@RequestBody ContourModel contourModel) {
        return service.save(contourModel);
    }

    @GetMapping
    public Set<ContourModel> getAllStands() {
        return service.findAll();
    }

}
