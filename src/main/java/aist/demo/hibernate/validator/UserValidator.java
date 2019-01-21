package aist.demo.hibernate.validator;

import aist.demo.hibernate.annotate.Validator;
import aist.demo.hibernate.domain.User;
import aist.demo.hibernate.dto.UserDto;
import aist.demo.hibernate.exceptions.AistBaseException;
import aist.demo.hibernate.exceptions.NotFoundException;
import aist.demo.hibernate.repository.UserRepo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static aist.demo.hibernate.util.EncryptionUtil.decrypt;
import static aist.demo.hibernate.util.EncryptionUtil.hexStringToBytes;

@Validator
public class UserValidator {

    private final UserRepo userRepo;

    @Autowired
    public UserValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void loginForToken(UserDto model) {
        String login = checkConsistent(model);
        if (!userRepo.existsByLogin(login)) {
            throw new NotFoundException("Пользователь с логином " + login + " не найден");
        }
    }

    public void loginForRegistration(UserDto model) {
        String login = checkConsistent(model);
        if (userRepo.existsByLogin(login)) {
            throw new NotFoundException("Пользователь с логином " + login + " уже зарегистрирован");
        }
    }

    public void passwordVerification(UserDto model) throws Exception {
        byte[] b = hexStringToBytes(model.getPassword());
        String decryptedPass = DigestUtils.md5Hex(decrypt(b));
        Set<User> users = userRepo.findByLogin(model.getLogin());
        if (users.isEmpty()){
            throw new NotFoundException("Пользователь с логином " + model.getLogin() + " не найден");
        }
        String curPass = users.iterator().next().getPassword();
        if (!decryptedPass.equals(curPass)) {
            throw new NotFoundException("Пароль некорректен");
        }
    }

    private String checkConsistent(UserDto model) {
        String login = model.getLogin();
        if (login == null || model.getPassword() == null) {
            throw new AistBaseException("Ожидается логин и пароль пользователя");
        }
        return login;
    }
}
