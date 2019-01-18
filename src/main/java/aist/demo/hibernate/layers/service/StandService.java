package aist.demo.hibernate.layers.service;

import aist.demo.hibernate.domain.entity.Stand;
import aist.demo.hibernate.domain.model.object.StandModel;
import aist.demo.hibernate.exceptions.AistBaseException;
import aist.demo.hibernate.exceptions.ConflictException;
import aist.demo.hibernate.exceptions.NotFoundException;
import aist.demo.hibernate.layers.converter.StandConverter;
import aist.demo.hibernate.layers.repository.StandRepo;
import aist.demo.hibernate.layers.validator.StandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StandService {

    private final StandRepo standRepo;
    private final StandConverter standConverter;
    private final StandValidator standValidator;

    @Autowired
    public StandService(StandRepo standRepo, StandConverter standConverter, StandValidator standValidator) {
        this.standRepo = standRepo;
        this.standConverter = standConverter;
        this.standValidator = standValidator;
    }

    public StandModel find(Long id) {
        Stand stand = standRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no Stand by id: " + id));
        return standConverter.convert(stand);
    }

    public Set<StandModel> findAll() {
        return standRepo.findAll().stream()
                .map(standConverter::convert)
                .collect(Collectors.toSet());
    }

    public StandModel save(StandModel standModel) {
        standValidator.forSave(standModel);
        Stand stand = standConverter.convert(standModel);
        Stand savedStand = standRepo.save(stand);
        return standConverter.convert(savedStand);
    }

    public Long update(StandModel standModel) {
        standValidator.forUpdate(standModel);
        Stand stand = standConverter.convert(standModel);
        return standRepo.save(stand).getId();
    }

}
