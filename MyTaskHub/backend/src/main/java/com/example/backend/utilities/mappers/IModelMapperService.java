package com.example.backend.utilities.mappers;

import org.modelmapper.ModelMapper;

public interface IModelMapperService {

    ModelMapper forResponse();

    ModelMapper forRequest();
}
