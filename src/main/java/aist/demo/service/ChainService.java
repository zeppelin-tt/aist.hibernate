package aist.demo.service;

import aist.demo.converter.ChainConverter;
import aist.demo.domain.Chain;
import aist.demo.dto.ChainDto;
import aist.demo.exceptions.NotFoundException;
import aist.demo.repository.ChainRepo;
import aist.demo.specification.ChainSpecificationsBuilder;
import aist.demo.specification.SearchCriteria;
import aist.demo.validator.ChainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ChainService {

    private final ChainRepo chainRepo;
    private final ChainConverter chainConverter;
    private final ChainValidator chainValidator;

    @Autowired
    public ChainService(ChainRepo chainRepo, ChainConverter chainConverter, ChainValidator chainValidator) {
        this.chainRepo = chainRepo;
        this.chainConverter = chainConverter;
        this.chainValidator = chainValidator;
    }

    public ChainDto find(Long id) {
        Chain chain = chainRepo.findById(id)
                // TODO: 22.01.2019 в фильтр добавить проверку на владельца цепочки. См. Оригинал, там все будет посложнее
                .filter(c -> !c.isLegacy())
                .orElseThrow(() -> new NotFoundException("Нет цепочки с id: " + id));
        return chainConverter.convert(chain);
    }

    // TODO: 22.01.2019 добавить фильтр!
    public Set<ChainDto> findAll() {
        return chainRepo.findAll().stream()
                .map(chainConverter::convert)
                .collect(Collectors.toSet());
    }

    @Transactional
    public ChainDto save(ChainDto dto) {
        chainValidator.forSave(dto);
        Chain chain = chainConverter.convert(dto);
        Chain savedChain = chainRepo.save(chain);
        return chainConverter.convert(savedChain);
    }

    @Transactional
    public void update(ChainDto dto) {
        chainValidator.forUpdate(dto);
        Chain chain = chainConverter.convert(dto);
        chainRepo.save(chain);
    }

    @Transactional
    public void delete(Long id) {
        Chain chain = new Chain();
        chain.setId(id);
        chainRepo.delete(chain);
    }

    public Set<ChainDto> postSearch(List<SearchCriteria> criteria) {
        ChainSpecificationsBuilder builder = new ChainSpecificationsBuilder();
        for(SearchCriteria criterion : criteria) {
            builder.with(criterion.getKey(), criterion.getOperation(), criterion.getValue(), criterion.isOrPredicate());
        }
        Specification<Chain> spec = builder.build();
        return chainRepo.findAll(spec).stream()
                .map(chainConverter::convert)
                .collect(Collectors.toSet());
    }

    public Set<ChainDto> getSearch(String criteria) {
        ChainSpecificationsBuilder builder = new ChainSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|~|!|startWith|endWith|contains)(\\w+?),");
        Matcher matcher = pattern.matcher(criteria + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Chain> spec = builder.build();
        return chainRepo.findAll(spec).stream()
                .map(chainConverter::convert)
                .collect(Collectors.toSet());
    }

}
