package aist.demo.exceptions;

import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

public class AistBaseException extends RuntimeException {

    public AistBaseException(String message) {
        super(message);
    }

    public AistBaseException(String messageFormat, Object... messageArgs) {
        super(String.format(messageFormat, messageArgs));
    }

    public AistBaseException() {
        super();
    }

    ResponseEntity handle() {
        return handle(Collections.singletonMap("error", getLocalizedMessage()));
    }

    ResponseEntity handle(Map body) {
        return ResponseEntity.badRequest().body(body);
    }
}
