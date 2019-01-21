package aist.demo.hibernate.converter;

import aist.demo.hibernate.annotate.Converter;
import aist.demo.hibernate.domain.Chain;
import aist.demo.hibernate.domain.Group;
import aist.demo.hibernate.domain.User;
import aist.demo.hibernate.dto.UserDto;
import aist.demo.hibernate.repository.ChainRepo;
import aist.demo.hibernate.repository.GroupRepo;
import aist.demo.hibernate.validator.UserValidator;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static aist.demo.hibernate.util.EncryptionUtil.decrypt;
import static aist.demo.hibernate.util.EncryptionUtil.hexStringToBytes;

@Converter
public class UserConverter {

    private final GroupRepo groupRepo;
    private final ChainRepo chainRepo;
    private final UserValidator userValidator;

    @Autowired
    public UserConverter(GroupRepo groupRepo, ChainRepo chainRepo, UserValidator userValidator) {
        this.groupRepo = groupRepo;
        this.chainRepo = chainRepo;
        this.userValidator = userValidator;
    }

    // для сохранения нового.
    public User convert(UserDto model) throws Exception {
        User user = new User();
        byte[] b = hexStringToBytes(model.getPassword());
        String decryptedPass = DigestUtils.md5Hex(decrypt(b));
        user.setPassword(decryptedPass);
        user.setLogin(model.getLogin());
        user.setId(model.getId());
        user.setEmail(model.getEmail());
        user.setToken(model.getToken());
        Set<Group> groups = null;
        if (model.getGroups() != null) {
            groups = new HashSet<>(groupRepo.findAllById(model.getGroups()));
        }
        user.setGroups(groups);
        Set<Chain> chains = null;
        if (model.getChains() != null) {
            chains = new HashSet<>(chainRepo.findAllById(model.getChains()));
        }
        user.setChains(chains);
        return user;
    }

}
