package aist.demo.hibernate.layers.validator;

import aist.demo.hibernate.annotate.Validator;
import aist.demo.hibernate.domain.entity.User;
import aist.demo.hibernate.domain.model.UserModel;
import aist.demo.hibernate.exceptions.AistBaseException;
import aist.demo.hibernate.exceptions.NotFoundException;
import aist.demo.hibernate.layers.repository.UserRepo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static aist.demo.hibernate.util.EncryptionUtil.decrypt;
import static aist.demo.hibernate.util.EncryptionUtil.hexStringToBytes;

@Validator
public class UserValidator {

    private final UserRepo userRepo;

    @Autowired
    public UserValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void loginForToken(UserModel model) {
        String login = checkConsistent(model);
        if (!userRepo.existsByLogin(login)) {
            throw new NotFoundException("Пользователь с логином " + login + " не найден");
        }
    }

    public void loginForRegistration(UserModel model) {
        String login = checkConsistent(model);
        if (userRepo.existsByLogin(login)) {
            throw new NotFoundException("Пользователь с логином " + login + " уже зарегистрирован");
        }
    }

    public void passwordVerification(UserModel model) throws Exception {
        byte[] b = hexStringToBytes(model.getPassword());
        String decryptedPass = DigestUtils.md5Hex(decrypt(b));
        User user = userRepo.findOneByLogin(model.getLogin());
        String curPass = user.getPassword();
        if (!decryptedPass.equals(curPass)) {
            throw new NotFoundException("Пароль некорректен");
        }
    }

    private String checkConsistent(UserModel model) {
        String login = model.getLogin();
        if (login == null || model.getPassword() == null) {
            throw new AistBaseException("Ожидается логин и пароль пользователя");
        }
        return login;
    }
}
