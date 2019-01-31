package aist.demo.util;

import aist.demo.exceptions.IncorrectParameters;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public enum ValidateUtil {

    instance;

    public ValidateUtil checkNull(Object param, @NotNull String paramName) {
        if (param == null)
            throw new IncorrectParameters("Необходимо указать параметр: " + paramName);
        return this;
    }

    public ValidateUtil checkNonNull(Object param, @NotNull String paramName) {
        if (param != null)
            throw new IncorrectParameters("Параметр не должен быть указан: " + paramName);
        return this;
    }

    public ValidateUtil checkEmptyCollection(Collection param, @NotNull String paramName) {
        if (param.isEmpty())
            throw new IncorrectParameters("Необходимо указать хотя бы один параметр: " + paramName);
        return this;
    }

    public ValidateUtil checkEmptyString(Object param, @NotNull String paramName) {
        if (!(param instanceof String)) {
            throw new IncorrectParameters("Параметр " + paramName + " не является строкой");
        }
        if (((String) param).isEmpty())
            throw new IncorrectParameters("Необходимо указать параметр: " + paramName);
        return this;
    }

    public ValidateUtil checkStrLength(@NotNull String param, int minLength, int maxLength, @NotNull String paramName) {
        return checkStrMaxLength(param, maxLength, paramName).checkStrMinLength(param, minLength, paramName);
    }

    public ValidateUtil checkStrMaxLength(@NotNull String param, int maxLength, @NotNull String paramName) {
        if (param.length() > maxLength)
            throw new IncorrectParameters(String.format("Превышена максимальная длина (%d) параметра %s", maxLength, paramName));
        return this;
    }

    public ValidateUtil checkStrMinLength(@NotNull String param, int minLength, @NotNull String paramName) {
        if (param.length() < minLength)
            throw new IncorrectParameters(String.format("Минимальная длина параметра %s %d символов", paramName, minLength));
        return this;
    }
}
