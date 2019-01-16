package aist.demo.hibernate.layers.controller;

import aist.demo.hibernate.domain.entry.Stand;
import aist.demo.hibernate.domain.model.object.StandModel;
import aist.demo.hibernate.domain.model.rest.Response;
import aist.demo.hibernate.layers.service.StandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("stand") // was stands
public class StandController {

    private final StandService service;

    @Autowired
    public StandController(StandService service) {
        this.service = service;
    }

    //todo здесь остановился
//    @GetMapping("{stand_id}")
//    public ResponseEntity getEntry(@PathVariable("stand_id") String id) {
//        Optional<Stand> standOptional = service.find(Long.valueOf(id));
//        if (!standOptional.isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(FormatUtils.toJson(stand), HttpStatus.OK);
//        }
//    }

    @PostMapping(path = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity update(@RequestBody StandModel standModel) {
        Long id = service.update(standModel).getId();
        Response response = new Response(String.valueOf(id), "Stand has successfully updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity getEntries() {
//        List<Stand> stands = DAO.getStandsDictionaryDAO().getAll();
//        return new ResponseEntity<>(FormatUtils.toJson(stands), HttpStatus.OK);
//    }


}
