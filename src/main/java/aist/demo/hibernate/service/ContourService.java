package aist.demo.hibernate.service;

import aist.demo.hibernate.domain.Contour;
import aist.demo.hibernate.model.ContourModel;
import aist.demo.hibernate.exceptions.NotFoundException;
import aist.demo.hibernate.converter.ContourConverter;
import aist.demo.hibernate.repository.ContourRepo;
import aist.demo.hibernate.validator.ContourValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContourService {

    private final ContourRepo contourRepo;
    private final ContourConverter contourConverter;
    private final ContourValidator contourValidator;

    @Autowired
    public ContourService(ContourRepo contourRepo, ContourConverter contourConverter, ContourValidator contourValidator) {
        this.contourRepo = contourRepo;
        this.contourConverter = contourConverter;
        this.contourValidator = contourValidator;
    }

    public ContourModel find(Long id) {
        Contour contour = contourRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no Contour by id: " + id));
        return contourConverter.convert(contour);
    }

    public Set<ContourModel> findAll() {
        return contourRepo.findAll().stream()
                .map(contourConverter::convert)
                .collect(Collectors.toSet());
    }

    public ContourModel save(ContourModel contourModel) {
        contourValidator.forSave(contourModel);
        Contour contour = contourConverter.convert(contourModel);
        Contour savedContour = contourRepo.save(contour);
        return contourConverter.convert(savedContour);
    }

    public Long update(ContourModel contourModel) {
        contourValidator.forUpdate(contourModel);
        Contour contour = contourConverter.convert(contourModel);
        return contourRepo.save(contour).getId();
    }

}
