package com.microservice.productservice.utils;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MapperUtils {

    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> new ModelMapper().map(entity, outCLass))
                .collect(Collectors.toList());
    }
}
