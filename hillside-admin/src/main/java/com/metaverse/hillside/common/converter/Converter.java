package com.metaverse.hillside.common.converter;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class Converter implements IConverter {

    private static MapperFacade MAPPER_FACADE;

    public Converter() {
        if (ObjectUtils.isEmpty(MAPPER_FACADE)) {
            MAPPER_FACADE = getBeanConverterFactory().getMapperFacade();
        }
    }

    @Override
    public MapperFactory getBeanConverterFactory() {
        return new DefaultMapperFactory.Builder().build();
    }

    @Override
    public <A, B> B convert(A clazzA, Class<B> clazzB) {
        return MAPPER_FACADE.map(clazzA, clazzB);
    }

    @Override
    public <A, B> List<B> convert(Iterable<A> clazzAs, Class<B> clazzB) {
        return MAPPER_FACADE.mapAsList(clazzAs, clazzB);
    }

}
