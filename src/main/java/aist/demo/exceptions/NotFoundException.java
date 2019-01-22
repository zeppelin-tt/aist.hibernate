package aist.demo.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

public class NotFoundException extends AistBaseException {

    // TODO: 16.10.2018 указать тип и идентификатор сущности.
    public NotFoundException() {
        super("Сущность не найдена");
    }

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    ResponseEntity handle() {
        Map<String, String> payload = null;
        if (StringUtils.isNotEmpty(getMessage())) {
            payload = Collections.singletonMap("message", getLocalizedMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(payload);
    }
}