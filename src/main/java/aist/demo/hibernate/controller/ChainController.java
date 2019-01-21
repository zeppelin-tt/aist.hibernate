package aist.demo.hibernate.controller;

import aist.demo.hibernate.annotate.ApiController;
import aist.demo.hibernate.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@ApiController("contour") // was stands
public class ChainController {

    private final ChainService service;

    @Autowired
    public ChainController(ChainService service) {
        this.service = service;
    }
//
//    @GetMapping("{id:\\d+}")
//    public ResponseEntity get(@RequestHeader(value = AuthUtils.TOKEN_PARAM_NAME, required = false) String sessionID,
//                              @PathVariable("id") int id) throws SQLException {
//        String userName = AuthUtils.getUsernameByToken(sessionID);
//        AuthUtils.authorizeRead(DbTables.OWNED_TABLES.CHAIN_TEMPLATES, String.valueOf(id), userName);
//        return ResponseEntity.ok(ChainTemplate.get(id, userName));
//    }
//
//    @GetMapping
//    public ResponseEntity getAll(@RequestHeader(value = AuthUtils.TOKEN_PARAM_NAME, required = false) String token) {
//        return filter(token, new ChainTemplatesFilter());
//    }
//
//    @PutMapping
//    public ResponseEntity add(@RequestHeader(value = AuthUtils.TOKEN_PARAM_NAME, required = false) String token,
//                              @RequestBody List<ChainTemplate> cts) throws SQLException {
//        if (cts.isEmpty())
//            throw new AistBaseException();
//        int id = cts.get(0).insert(AuthUtils.getUsernameByToken(token));
//        return ResponseEntity.ok(Collections.singletonMap("id", id));
//    }
//
//    @PostMapping("{id:\\d+}")
//    public ResponseEntity update(
//            @PathVariable("id") int id,
//            @RequestHeader(value = AuthUtils.TOKEN_PARAM_NAME, required = false) String sessionID,
//            @RequestBody List<ChainTemplate> cts
//    ) throws AistBaseException, SQLException {
//        ChainTemplate ct = cts.get(0);
//        AuthUtils.authorize(DbTables.OWNED_TABLES.CHAIN_TEMPLATES, String.valueOf(id), sessionID);
//        ct.update(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("{id:\\d+}")
//    public ResponseEntity delete(
//            @RequestHeader(AuthUtils.TOKEN_PARAM_NAME) String sessionID,
//            @PathVariable("id") int id
//    ) throws SQLException {
//        AuthUtils.authorize(DbTables.OWNED_TABLES.CHAIN_TEMPLATES, String.valueOf(id), sessionID);
//        ChainTemplate.get(id, AuthUtils.getUsernameByToken(sessionID)).delete();
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("filter")
//    public ResponseEntity<String> filter(@RequestHeader(value = AuthUtils.TOKEN_PARAM_NAME, required = false) String token,
//                                         @RequestBody ChainTemplatesFilter filter) {
//        filter.setUser(AuthUtils.getUsernameByToken(token));
//        return ResponseEntity.ok(filter.fetch());
//    }

}
