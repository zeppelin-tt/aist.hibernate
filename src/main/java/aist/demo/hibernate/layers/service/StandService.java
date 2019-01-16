package aist.demo.hibernate.layers.service;

import aist.demo.hibernate.annotate.StorageLayer;
import aist.demo.hibernate.domain.entry.Stand;
import aist.demo.hibernate.domain.model.object.StandModel;
import aist.demo.hibernate.exceptions.ConsistentModelException;
import aist.demo.hibernate.layers.converter.StandConverter;
import aist.demo.hibernate.layers.repository.StandRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@StorageLayer
public class StandService {

    private final StandRepo standRepo;
    private final StandConverter standConverter;

    @Autowired
    public StandService(StandRepo standRepo, StandConverter standConverter) {
        this.standRepo = standRepo;
        this.standConverter = standConverter;
    }

    // Может как-нибудь можно обойтись, не вынося Optional в контроллер?
    public Optional<Stand> find(Long id) {
//        Optional<Stand> standOpt = standRepo.findById(id);
//        if (!standOpt.isPresent()) {
//            throw new ConsistentModelException("There is no Stand by id: " + id);
//        }
        return standRepo.findById(id);
    }

    public Stand save(StandModel standModel) {
        if (standModel.getId() != null) {
            throw new ConsistentModelException("This is Id in Stand for save");
        }
        if (standRepo.existsByStandNameOrCode(standModel.getName(), standModel.getCode())) {
            throw new ConsistentModelException("Stand already exists by Name or Code!");
        }
        Stand stand = standConverter.convert(standModel);
        return standRepo.save(stand);
    }

    public Stand update(StandModel standModel) {
        if (standModel.getId() == null) {
            throw new ConsistentModelException("This is no Id in Stand for update");
        }
        if (!standRepo.existsById(standModel.getId())) {
            throw new ConsistentModelException("This Id is not in the database: " + standModel.toString()) ;
        }
        Stand stand = standConverter.convert(standModel);
        return standRepo.save(stand);
    }

}
