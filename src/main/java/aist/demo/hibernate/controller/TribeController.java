package aist.demo.hibernate.controller;

import aist.demo.hibernate.annotate.ApiController;
import aist.demo.hibernate.model.TribeCommandModel;
import aist.demo.hibernate.model.TribeModel;
import aist.demo.hibernate.service.TribeCommandService;
import aist.demo.hibernate.service.TribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

// тут было - "tribes"
// TODO: 19.01.2019 swagger добавить в класс. Я просто в нем не разбираюсь, убрал пока...
@ApiController("tribe")
public class TribeController {

    // TODO: 19.01.2019  AuthUtils еще нету...
    private String sessionId = "123";

    private final TribeService tribeService;
    private final TribeCommandService tribeCommandService;

    @Autowired
    public TribeController(TribeService tribesService, TribeCommandService tribeCommandsService) {
        this.tribeService = tribesService;
        this.tribeCommandService = tribeCommandsService;
    }

    @GetMapping
    public Set<TribeModel> getAllTribes() {
        return tribeService.findAll();
    }

    @GetMapping("{id}")
    public TribeModel getTribe(@PathVariable("id") Long id) {
        return tribeService.find(id);
    }

    // здесь ничего не было
    @PostMapping("create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TribeModel createTribe(
//            @RequestHeader(value = AuthUtils.TOKEN_PARAM_NAME, required = false) String sessionId,
            @RequestBody TribeModel tribeModel) {
        return tribeService.save(sessionId, tribeModel);
    }


    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void updateTribe(
//            @RequestHeader(value = AuthUtils.TOKEN_PARAM_NAME, required = false) String sessionId,
//            @PathVariable("id") Integer id,
            // TODO: 19.01.2019 зачем здесь ID?
            @RequestBody TribeModel tribeModel) {
        tribeService.update(
                sessionId,
                tribeModel);
    }

    @GetMapping("{id}/commands")
    public Set<TribeCommandModel> getCommandsByTribe(@PathVariable("id") Long id) {
        return tribeCommandService.findByTribeId(id);
    }

    @GetMapping("commands")
    public Set<TribeCommandModel> getAllCommands() {
        return tribeCommandService.findAll();
    }

    @GetMapping("commands/{id}")
    public TribeCommandModel getCommand(@PathVariable("id") long id) {
        return tribeCommandService.find(id);
    }

    @PostMapping("commands")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TribeCommandModel createCommand(
//            @RequestHeader(value = AuthUtils.TOKEN_PARAM_NAME, required = false) String sessionId,
            @RequestBody TribeCommandModel commandModel) {
        return tribeCommandService.save(sessionId, commandModel);
    }

    @PutMapping("commands")
    public void updateCommand(
//            @RequestHeader(value = AuthUtils.TOKEN_PARAM_NAME, required = false) String sessionId,
//            @PathVariable("id") Integer id,
            @RequestBody TribeCommandModel commandModel) {
        tribeCommandService.update(sessionId, commandModel);
    }
}
