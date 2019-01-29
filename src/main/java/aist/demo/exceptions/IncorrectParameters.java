package aist.demo.exceptions;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;


public class IncorrectParameters extends AistBaseException {

    private static final String MESSAGE = "Получены некорректные параметры.";
    private static final String MESSAGE_WITH_ADDITION = "Получены некорректные параметры: ";

    public IncorrectParameters(String message) {
        super(message);
    }

    public IncorrectParameters(String ... parameters) {
        super(MESSAGE_WITH_ADDITION + String.join(", ", parameters));
    }

    public IncorrectParameters() {
        super(MESSAGE);
    }

    @Override
    ResponseEntity handle(Map body) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @Override
    ResponseEntity handle() {
        ResponseEntity.BodyBuilder reBuilder = ResponseEntity.status(HttpStatus.BAD_REQUEST);
        if (!StringUtils.isEmpty(getMessage()))
            return reBuilder.body(Collections.singletonMap("error", getMessage()));
        else
            return reBuilder.build();
    }

}

