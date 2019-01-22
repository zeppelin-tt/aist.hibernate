package aist.demo.service;

import aist.demo.dto.ContourDto;
import aist.demo.exceptions.NotFoundException;
import aist.demo.domain.Contour;
import aist.demo.converter.ContourConverter;
import aist.demo.repository.ContourRepo;
import aist.demo.validator.ContourValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public ContourDto find(Long id) {
        Contour contour = contourRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no Contour by id: " + id));
        return contourConverter.convert(contour);
    }

    public Set<ContourDto> findAll() {
        return contourRepo.findAll().stream()
                .map(contourConverter::convert)
                .collect(Collectors.toSet());
    }

    @Transactional
    public ContourDto save(ContourDto contourDto) {
        contourValidator.forSave(contourDto);
        Contour contour = contourConverter.convert(contourDto);
        Contour savedContour = contourRepo.save(contour);
        return contourConverter.convert(savedContour);
    }

    @Transactional
    public void update(ContourDto contourDto) {
        contourValidator.forUpdate(contourDto);
        Contour contour = contourConverter.convert(contourDto);
        contourRepo.save(contour);
    }

}
