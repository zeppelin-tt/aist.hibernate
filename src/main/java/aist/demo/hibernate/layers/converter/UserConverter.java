package aist.demo.hibernate.layers.converter;

import aist.demo.hibernate.annotate.Converter;
import aist.demo.hibernate.domain.entity.*;
import aist.demo.hibernate.domain.model.TribeModel;
import aist.demo.hibernate.domain.model.UserModel;
import aist.demo.hibernate.layers.repository.ChainRepo;
import aist.demo.hibernate.layers.repository.GroupRepo;
import aist.demo.hibernate.layers.repository.TribeCommandRepo;
import aist.demo.hibernate.layers.repository.UserRepo;
import aist.demo.hibernate.layers.validator.UserValidator;
import com.google.common.collect.Sets;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    public User convert(UserModel model) throws Exception {
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
