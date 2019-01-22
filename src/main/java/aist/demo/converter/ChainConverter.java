package aist.demo.converter;

import aist.demo.annotate.Converter;
import aist.demo.domain.AutomatedSystem;
import aist.demo.domain.Chain;
import aist.demo.domain.Group;
import aist.demo.dto.ChainDto;
import aist.demo.repository.AutomatedSystemRepo;
import aist.demo.repository.ContourRepo;
import aist.demo.repository.GroupRepo;
import aist.demo.repository.UserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
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
        chain.setForm(new Gson().toJsonTree(dto.getForm()));
        chain.setContour(dto.getContourId() == null ? null : contourRepo.getOne(dto.getContourId()));
        chain.setSystems(dto.getSystems() == null ? Collections.emptySet() : new HashSet<>(systemRepo.findAllById(dto.getSystems())));
        chain.setTestIdOrder(dto.getTestIdOrder());
        chain.setUser(dto.getUserId() == null ? null : userRepo.getOne(dto.getUserId()));
        chain.setGroups(dto.getGroupIdSet() == null ? Collections.emptySet() : new HashSet<>(groupRepo.findAllById(dto.getGroupIdSet())));
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
        dto.setSystems(chain.getSystems().stream().map(AutomatedSystem::getId).collect(Collectors.toSet()));
        dto.setUserId(chain.getUser().getId());
        dto.setTestIdOrder(chain.getTestIdOrder());
        dto.setGroupIdSet(chain.getGroups().stream().map(Group::getId).collect(Collectors.toSet()));
        dto.setWithoutForm(chain.isWithoutForm());
        dto.setAverageTimeSec(chain.getAverageTimeSec());
        dto.setAverageTimeByTestsSec(chain.getAverageTimeByTestsSec());
        dto.setLegacy(chain.isLegacy());
        dto.setComment(chain.getComment());
        return dto;
    }

}
