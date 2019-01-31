package aist.demo.converter;

import aist.demo.annotate.Converter;
import aist.demo.domain.*;
import aist.demo.dto.ChainDto;
import aist.demo.repository.AutomatedSystemRepo;
import aist.demo.repository.ContourRepo;
import aist.demo.repository.GroupRepo;
import aist.demo.repository.UserRepo;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class ChainConverter {

    private final UserRepo userRepo;
    private final ContourRepo contourRepo;
    private final AutomatedSystemRepo systemRepo;
    private final GroupRepo groupRepo;

    @Autowired
    public ChainConverter(UserRepo userRepo, ContourRepo contourRepo, AutomatedSystemRepo systemRepo, GroupRepo groupRepo) {
        this.userRepo = userRepo;
        this.contourRepo = contourRepo;
        this.systemRepo = systemRepo;
        this.groupRepo = groupRepo;
    }

    public Chain convert(ChainDto dto) {
        Chain chain = new Chain();
        chain.setId(dto.getId());
        chain.setName(dto.getName());
        JsonElement form = dto.getForm() == null ?
                new Gson().toJsonTree("[]") : // TODO: 23.01.2019 как-то не очень красиво. Разобраться.
                new Gson().toJsonTree(dto.getForm());
        chain.setForm(form);
        Contour contour = dto.getContourId() == null ?
                null :
                contourRepo.getOne(dto.getContourId());
        chain.setContour(contour);
        Set<AutomatedSystem> systems = dto.getSystemIdSet() == null ?
                Collections.emptySet() :
                new HashSet<>(systemRepo.findAllById(dto.getSystemIdSet()));
        chain.setSystems(systems);
        chain.setTestIdOrder(dto.getTestIdOrder());
        User user = dto.getCreatorId() == null ?
                null :
                userRepo.getOne(dto.getCreatorId());
        chain.setCreator(user);
        Set<Group> groups = dto.getGroupIdSet() == null ?
                Collections.emptySet() :
                new HashSet<>(groupRepo.findAllById(dto.getGroupIdSet()));
        chain.setGroups(groups);
        chain.setWithoutForm(dto.isWithoutForm());
        chain.setAverageTimeSec(dto.getAverageTimeSec());
        chain.setAverageTimeByTestsSec(dto.getAverageTimeByTestsSec());
        chain.setLegacy(dto.isLegacy());
        chain.setComment(dto.getComment());
        return chain;
    }

    public ChainDto convert(Chain chain) {
        ChainDto dto = new ChainDto();
        dto.setId(chain.getId());
        dto.setName(chain.getName());
        dto.setForm(chain.getForm().getAsString());
        dto.setContourId(chain.getContour().getId());
        Set<Long> systemIdSet = chain.getSystems()
                .stream()
                .map(AutomatedSystem::getId)
                .collect(Collectors.toSet());
        dto.setSystemIdSet(systemIdSet);
        dto.setCreatorId(chain.getCreator().getId());
        dto.setTestIdOrder(chain.getTestIdOrder());
        Set<Long> groupIdSet = chain.getGroups()
                .stream()
                .map(Group::getId)
                .collect(Collectors.toSet());
        dto.setGroupIdSet(groupIdSet);
        dto.setWithoutForm(chain.isWithoutForm());
        dto.setAverageTimeSec(chain.getAverageTimeSec());
        dto.setAverageTimeByTestsSec(chain.getAverageTimeByTestsSec());
        dto.setLegacy(chain.isLegacy());
        dto.setComment(chain.getComment());
        return dto;
    }

}
