package aist.demo.service;

import aist.demo.converter.UserConverter;
import aist.demo.dto.UserDto;
import aist.demo.exceptions.ServerErrorException;
import aist.demo.domain.User;
import aist.demo.repository.UserRepo;
import aist.demo.validator.UserValidator;
import aist.demo.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

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
            EncryptionUtil.generateKey();
            HashMap<String, Object> result = new HashMap<String, Object>() {{
                put("n", EncryptionUtil.getModulus());
                put("e", "10001");
            }};
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (NoSuchAlgorithmException | IOException | ClassNotFoundException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Deprecated
    @Transactional
    public void setToken(UserDto dto) {
        userValidator.loginForToken(dto);
        try {
            // TODO: 19.01.2019 всякие такие штуки надо менять на спринговые, я думаю... Не стал заморачиваться, вроде Костя этим занимается.
            userValidator.passwordVerification(dto);
            String token = generateToken();
            while (userRepo.existsByToken(token)) {
                token = generateToken();
            }
            userRepo.updateTokenByLogin(token, dto.getLogin());
        } catch (Exception e) {
            throw new ServerErrorException("Неизвестная ошибка сервера");
        }
    }

    @Deprecated
    @Transactional
    // TODO: 19.01.2019 пока возвращается только id в модели. А нужно ли что-то еще?..
    public UserDto registerUser(UserDto dto) {
        userValidator.loginForRegistration(dto);
        User savedUser = null;
        try {
            User user = userConverter.convert(dto);
            savedUser = userRepo.save(user);
            // TODO: 19.01.2019 предусмотреть все связи при создании пользователя
//            connector.update("UPDATE groupIdSet SET members = members || '" + login + ",' WHERE name = 'public'");
        } catch (Exception e) {
            throw  new ServerErrorException("Неизвестная ошибка сервера");
        }
        UserDto onlyId = new UserDto();
        onlyId.setId(savedUser.getId());
        return onlyId;
    }

    private String generateToken() {
        return String.valueOf(new SecureRandom().nextInt(100000));
    }

}
