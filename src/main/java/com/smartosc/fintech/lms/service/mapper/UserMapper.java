package com.smartosc.fintech.lms.service.mapper;

import com.smartosc.fintech.lms.dto.UserDto;
import com.smartosc.fintech.lms.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    static UserMapper getInstance() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Named("mapUserEntityToDto")
    UserDto mapToDto(UserEntity userEntity);

    UserEntity mapToEntity(UserDto userDto);
}
