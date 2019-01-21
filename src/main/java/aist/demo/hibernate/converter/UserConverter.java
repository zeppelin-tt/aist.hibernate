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

    @Autowired
    public UserConverter(GroupRepo groupRepo, ChainRepo chainRepo) {
        this.groupRepo = groupRepo;
        this.chainRepo = chainRepo;
    }

    // для сохранения нового.
    public User convert(UserDto dto) throws Exception {
        User user = new User();
        byte[] b = hexStringToBytes(dto.getPassword());
        String decryptedPass = DigestUtils.md5Hex(decrypt(b));
        user.setPassword(decryptedPass);
        user.setLogin(dto.getLogin());
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setToken(dto.getToken());
        Set<Group> groups = null;
        if (dto.getGroups() != null) {
            groups = new HashSet<>(groupRepo.findAllById(dto.getGroups()));
        }
        user.setGroups(groups);
        Set<Chain> chains = null;
        if (dto.getChains() != null) {
            chains = new HashSet<>(chainRepo.findAllById(dto.getChains()));
        }
        user.setChains(chains);
        return user;
    }

}
