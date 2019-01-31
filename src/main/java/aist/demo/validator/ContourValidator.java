package aist.demo.validator;

import aist.demo.annotate.Validator;
import aist.demo.dto.ContourDto;
import aist.demo.exceptions.NotFoundException;
import aist.demo.exceptions.ConflictException;
import aist.demo.repository.ContourRepo;
import aist.demo.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;

@Validator
public class ContourValidator {

    private final ContourRepo contourRepo;
    private final CollectionValidator collectionValidator;

    @Autowired
    public ContourValidator(ContourRepo contourRepo, CollectionValidator collectionValidator) {
        this.contourRepo = contourRepo;
        this.collectionValidator = collectionValidator;
    }

    public ContourDto forSave(ContourDto dto) {
        ValidateUtil.instance.checkNonNull(dto.getId(), "Id контура");
        if (contourRepo.existsByNameOrCode(dto.getName(), dto.getCode())) {
            throw new ConflictException("Контур с таким именем или кодом уже существует: " + dto.getCode());
        }
        collectionValidator.validateChains(dto.getChainIdSet(), true, true);
        return dto;
    }

    public ContourDto forUpdate(ContourDto dto) {
        ValidateUtil.instance.checkNull(dto.getId(), "Id контура");
        if (!contourRepo.existsById(dto.getId())) {
            throw new NotFoundException("Id нет в базе: " + dto.getId());
        }
        collectionValidator.validateChains(dto.getChainIdSet(), true, true);
        return dto;
    }

}
