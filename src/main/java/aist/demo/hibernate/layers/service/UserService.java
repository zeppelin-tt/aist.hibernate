package aist.demo.hibernate.layers.service;

import aist.demo.hibernate.domain.entity.User;
import aist.demo.hibernate.domain.model.UserModel;
import aist.demo.hibernate.exceptions.ServerErrorException;
import aist.demo.hibernate.layers.converter.UserConverter;
import aist.demo.hibernate.layers.repository.UserRepo;
import aist.demo.hibernate.layers.validator.UserValidator;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

import static aist.demo.hibernate.util.EncryptionUtil.*;

@Service
public class UserService {

    private final UserValidator userValidator;
    private final UserRepo userRepo;
    private final UserConverter userConverter;

    @Autowired
    public UserService(UserValidator userValidator, UserRepo userRepo, UserConverter userConverter) {
        this.userValidator = userValidator;
        this.userRepo = userRepo;
        this.userConverter = userConverter;
    }

    @Deprecated
    public ResponseEntity getUserData() {
        try {
            generateKey();
            HashMap<String, Object> result = new HashMap<String, Object>() {{
                put("n", getModulus());
                put("e", "10001");
            }};
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchAlgorithmException | IOException | ClassNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Deprecated
    public void setToken(UserModel model) {
        userValidator.loginForToken(model);
        try {
            // TODO: 19.01.2019 всякие такие штуки надо менять на спринговые, я думаю... Не стал заморачиваться, вроде Костя этим занимается.
            userValidator.passwordVerification(model);
            Integer token = generateToken();
            while (userRepo.existsByToken(token)) {
                token = generateToken();
            }
            userRepo.updateTokenByLogin(token, model.getLogin());
        } catch (Exception e) {
            throw new ServerErrorException("Неизвестная ошибка сервера");
        }
    }

    @Deprecated
    // TODO: 19.01.2019 пока возвращается только id в модели. А нужно ли что-то еще?..
    public UserModel registerUser(UserModel model) {
        userValidator.loginForRegistration(model);
        User savedUser = null;
        try {
            User user = userConverter.convert(model);
            savedUser = userRepo.save(user);
            // TODO: 19.01.2019 предусмотреть все связи при создании пользователя
//            connector.update("UPDATE groups SET members = members || '" + login + ",' WHERE name = 'public'");
        } catch (Exception e) {
            throw  new ServerErrorException("Неизвестная ошибка сервера");
        }
        UserModel onlyId = new UserModel();
        onlyId.setId(savedUser.getId());
        return onlyId;
    }

    private static int generateToken() {
        return new SecureRandom().nextInt(100000);
    }

}
