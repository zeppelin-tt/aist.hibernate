package aist.demo.service;

import aist.demo.converter.TribeConverter;
import aist.demo.domain.Tribe;
import aist.demo.dto.TribeDto;
import aist.demo.exceptions.NotFoundException;
import aist.demo.repository.TribeRepo;
import aist.demo.validator.TribeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TribeService {

    private final TribeRepo tribeRepo;
    private final TribeConverter tribeConverter;
    private final TribeValidator tribeValidator;

    @Autowired
    public TribeService(TribeRepo standRepo, TribeConverter tribeConverter, TribeValidator tribeValidator) {
        this.tribeRepo = standRepo;
        this.tribeConverter = tribeConverter;
        this.tribeValidator = tribeValidator;
    }

    public TribeDto find(Long id) {
        Tribe stand = tribeRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no Tribe by id: " + id));
        return tribeConverter.convert(stand);
    }

    public Set<TribeDto> findAll() {
        return tribeRepo.findAll().stream()
                .map(tribeConverter::convert)
                .collect(Collectors.toSet());
    }

    // TODO: 19.01.2019  обработка sessionId. Во всем классе
    @Transactional
    public TribeDto save(String sessionId, TribeDto tribeDto) {
        tribeValidator.forSave(tribeDto);
        Tribe tribe = tribeConverter.convert(tribeDto);
        Tribe savedStand = tribeRepo.save(tribe);
        return tribeConverter.convert(savedStand);
    }

    @Transactional
    public Long update(String sessionId, TribeDto tribeDto) {
        tribeValidator.forUpdate(tribeDto);
        Tribe tribe = tribeConverter.convert(tribeDto);
        return tribeRepo.save(tribe).getId();
    }

}
