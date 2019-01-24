package aist.demo.converter;

import aist.demo.annotate.Converter;
import aist.demo.domain.Group;
import aist.demo.dto.UserDto;
import aist.demo.domain.Chain;
import aist.demo.domain.User;
import aist.demo.repository.ChainRepo;
import aist.demo.repository.GroupRepo;
import aist.demo.util.EncryptionUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
        byte[] b = EncryptionUtil.hexStringToBytes(dto.getPassword());
        String decryptedPass = DigestUtils.md5Hex(EncryptionUtil.decrypt(b));
        user.setPassword(decryptedPass);
        user.setLogin(dto.getLogin());
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setToken(dto.getToken());
        Set<Group> groups = dto.getGroups() == null ?
                Collections.emptySet() :
                new HashSet<>(groupRepo.findAllById(dto.getGroups()));
        user.setCreatedGroups(groups);
        Set<Chain> chains = dto.getChains() == null ?
                Collections.emptySet() :
                new HashSet<>(chainRepo.findAllById(dto.getChains()));
        user.setChains(chains);
        return user;
    }

}
