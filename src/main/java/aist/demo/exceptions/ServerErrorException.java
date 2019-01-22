package aist.demo.exceptions;

import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class ServerErrorException extends AistBaseException {

    public ServerErrorException(String message) {
        super(message);
    }

    @Override
    ResponseEntity handle() {
        return ResponseEntity.status(500).body(Collections.singletonMap("message", getMessage()));
    }
}
