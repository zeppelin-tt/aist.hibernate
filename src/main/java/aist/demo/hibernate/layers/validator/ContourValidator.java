package aist.demo.hibernate.layers.validator;

import aist.demo.hibernate.annotate.Validator;
import aist.demo.hibernate.domain.entity.Chain;
import aist.demo.hibernate.domain.model.ContourModel;
import aist.demo.hibernate.exceptions.AistBaseException;
import aist.demo.hibernate.exceptions.ConflictException;
import aist.demo.hibernate.exceptions.ConsistentModelException;
import aist.demo.hibernate.exceptions.NotFoundException;
import aist.demo.hibernate.layers.repository.ChainRepo;
import aist.demo.hibernate.layers.repository.ContourRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Validator
public class ContourValidator {

    private final ContourRepo contourRepo;
    private final ChainRepo chainRepo;

    @Autowired
    public ContourValidator(ContourRepo contourRepo, ChainRepo chainRepo) {
        this.contourRepo = contourRepo;
        this.chainRepo = chainRepo;
    }

    public ContourModel forSave(ContourModel model) {
        if (model.getId() != null) {
            throw new AistBaseException("Контур для сохранения имеет id");
        }
        if (contourRepo.existsByNameOrCode(model.getName(), model.getCode())) {
            throw new ConflictException("Контур с таким именем или кодом уже существует");
        }
        validateChains(model);
        return model;
    }

    public ContourModel forUpdate(ContourModel model) {
        if (model.getId() == null) {
            throw new AistBaseException("Контур для сохранения не имеет id");
        }
        if (!contourRepo.existsById(model.getId())) {
            throw new NotFoundException("Id нет в базе: " + model.getId());
        }
        validateChains(model);
        return model;
    }

    // TODO: 19.01.2019 наверное, надо будет вынести в валидатор цепочек. Чтобы не дублировать...
    private void validateChains(ContourModel model) {
        Set<Long> chainIdSet = model.getChainIdSet();
        if (chainIdSet != null && !chainIdSet.isEmpty()) {
            Set<Chain> dbChains = new HashSet<>(chainRepo.findAllById(chainIdSet));
            if(dbChains.size() != chainIdSet.size()) {
                throw new ConsistentModelException("Не все id цепочек контура есть в БД");
            }
        }
    }

}
