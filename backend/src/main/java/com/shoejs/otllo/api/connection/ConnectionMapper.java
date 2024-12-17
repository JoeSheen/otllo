package com.shoejs.otllo.api.connection;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConnectionMapper {

    ConnectionMapper INST = Mappers.getMapper(ConnectionMapper.class);
}
