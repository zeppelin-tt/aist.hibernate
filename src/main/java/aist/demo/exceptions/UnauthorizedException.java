package aist.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Collections;

@ControllerAdvice
public class UnauthorizedException extends AistBaseException {
    public UnauthorizedException(String sessionId) {
        super("Не найдено активных сессий с идентификатором " + sessionId);
    }

    public UnauthorizedException() {
        super("Идентификатор сессии не указан!");
    }

    @Override
    ResponseEntity handle() {
        return new ResponseEntity<>(Collections.singletonMap("message", getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
    }
}