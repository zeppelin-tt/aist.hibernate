package aist.demo.controller;

import aist.demo.annotate.ApiController;
import aist.demo.dto.ContourDto;
import aist.demo.service.ContourService;
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

    @GetMapping("{id:\\d+}")
    public ContourDto getById(@PathVariable("id") Long id) {
        return service.find(id);
    }

    @PutMapping
    public void update(@RequestBody ContourDto contourDto) {
        service.update(contourDto);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ContourDto save(@RequestBody ContourDto contourDto) {
        return service.save(contourDto);
    }

    @GetMapping
    public Set<ContourDto> getAll() {
        return service.findAll();
    }

}
