package aist.demo.converter;

import aist.demo.annotate.Converter;
import aist.demo.domain.Test;
import aist.demo.dto.TestDto;

@Converter
public class TestConverter {

    public Test convert(TestDto dto) {
        Test test = new Test();
        test.setId(dto.getId());
        test.setName(dto.getName());
        return test;
    }

    public TestDto convert(Test test) {
        TestDto dto = new TestDto();
        dto.setId(test.getId());
        dto.setName(test.getName());
        return dto;
    }

}
