package aist.demo.hibernate.service;

import aist.demo.hibernate.domain.Tribe;
import aist.demo.hibernate.model.TribeModel;
import aist.demo.hibernate.exceptions.NotFoundException;
import aist.demo.hibernate.converter.TribeConverter;
import aist.demo.hibernate.repository.TribeRepo;
import aist.demo.hibernate.validator.TribeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public TribeModel find(Long id) {
        Tribe stand = tribeRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no Tribe by id: " + id));
        return tribeConverter.convert(stand);
    }

    public Set<TribeModel> findAll() {
        return tribeRepo.findAll().stream()
                .map(tribeConverter::convert)
                .collect(Collectors.toSet());
    }

    // TODO: 19.01.2019  обработка sessionId. Во всем классе
    public TribeModel save(String sessionId, TribeModel tribeModel) {
        tribeValidator.forSave(tribeModel);
        Tribe tribe = tribeConverter.convert(tribeModel);
        Tribe savedStand = tribeRepo.save(tribe);
        return tribeConverter.convert(savedStand);
    }

    public Long update(String sessionId, TribeModel tribeModel) {
        tribeValidator.forUpdate(tribeModel);
        Tribe tribe = tribeConverter.convert(tribeModel);
        return tribeRepo.save(tribe).getId();
    }

}
