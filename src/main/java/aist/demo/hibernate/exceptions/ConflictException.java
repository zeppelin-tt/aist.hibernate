package aist.demo.hibernate.exceptions;

import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class ConflictException extends AistBaseException {

    public ConflictException(String message) {
        super(message);
    }

    @Override
    ResponseEntity handle() {
        return ResponseEntity.status(409).body(Collections.singletonMap("message", getMessage()));
    }
}
