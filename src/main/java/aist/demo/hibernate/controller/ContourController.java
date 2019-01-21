package aist.demo.hibernate.controller;

import aist.demo.hibernate.annotate.ApiController;
import aist.demo.hibernate.dto.ContourDto;
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
    public ContourDto getStand(@PathVariable("id") Long id) {
        return service.find(id);
    }

    @PostMapping(path = "update")
    public Long update(@RequestBody ContourDto contourDto) {
        return service.update(contourDto);
    }

    @PostMapping(path = "create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ContourDto save(@RequestBody ContourDto contourDto) {
        return service.save(contourDto);
    }

    @GetMapping
    public Set<ContourDto> getAllStands() {
        return service.findAll();
    }

}
