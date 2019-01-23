package aist.demo.validator;

import aist.demo.annotate.Validator;
import aist.demo.dto.UserDto;
import aist.demo.exceptions.AistBaseException;
import aist.demo.exceptions.NotFoundException;
import aist.demo.domain.User;
import aist.demo.repository.UserRepo;
import aist.demo.util.EncryptionUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Validator
public class UserValidator {

    private final UserRepo userRepo;

    @Autowired
    public UserValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void loginForToken(UserDto dto) {
        String login = checkConsistent(dto);
        if (!userRepo.existsByLogin(login)) {
            throw new NotFoundException(String.format("Пользователь с логином %s не найден", login));
        }
    }

    public void loginForRegistration(UserDto dto) {
        String login = checkConsistent(dto);
        if (userRepo.existsByLogin(login)) {
            throw new NotFoundException("Пользователь с логином " + login + " уже зарегистрирован");
        }
    }

    public void passwordVerification(UserDto dto) throws Exception {
        byte[] b = EncryptionUtil.hexStringToBytes(dto.getPassword());
        String decryptedPass = DigestUtils.md5Hex(EncryptionUtil.decrypt(b));
        Set<User> users = userRepo.findByLogin(dto.getLogin());
        if (users.isEmpty()){
            throw new NotFoundException("Пользователь с логином " + dto.getLogin() + " не найден");
        }
        String curPass = users.iterator().next().getPassword();
        if (!decryptedPass.equals(curPass)) {
            throw new NotFoundException("Пароль некорректен");
        }
    }

    private String checkConsistent(UserDto dto) {
        String login = dto.getLogin();
        if (login == null || dto.getPassword() == null) {
            throw new AistBaseException("Ожидается логин и пароль пользователя");
        }
        return login;
    }

}
