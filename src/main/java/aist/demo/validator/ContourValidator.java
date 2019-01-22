package aist.demo.validator;

import aist.demo.annotate.Validator;
import aist.demo.dto.ContourDto;
import aist.demo.exceptions.AistBaseException;
import aist.demo.exceptions.ConsistentModelException;
import aist.demo.exceptions.NotFoundException;
import aist.demo.domain.Chain;
import aist.demo.exceptions.ConflictException;
import aist.demo.repository.ChainRepo;
import aist.demo.repository.ContourRepo;
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

    public ContourDto forSave(ContourDto dto) {
        if (dto.getId() != null) {
            throw new AistBaseException("Контур для сохранения имеет id");
        }
        if (contourRepo.existsByNameOrCode(dto.getName(), dto.getCode())) {
            throw new ConflictException("Контур с таким именем или кодом уже существует: " + dto.getCode());
        }
        validateChains(dto);
        return dto;
    }

    public ContourDto forUpdate(ContourDto dto) {
        if (dto.getId() == null) {
            throw new AistBaseException("Контур для редактирования не имеет id");
        }
        if (!contourRepo.existsById(dto.getId())) {
            throw new NotFoundException("Id нет в базе: " + dto.getId());
        }
        validateChains(dto);
        return dto;
    }

    // TODO: 19.01.2019 наверное, надо будет вынести в валидатор цепочек. Чтобы не дублировать...
    private void validateChains(ContourDto dto) {
        Set<Long> chainIdSet = dto.getChainIdSet();
        if (chainIdSet != null && !chainIdSet.isEmpty()) {
            Set<Chain> dbChains = new HashSet<>(chainRepo.findAllById(chainIdSet));
            if(dbChains.size() != chainIdSet.size()) {
                throw new ConsistentModelException("Не все id цепочек контура есть в БД");
            }
        }
    }

}
