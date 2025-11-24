package com.upsaude.mapper;

import com.upsaude.api.request.UserRequest;
import com.upsaude.api.response.UserResponse;
import com.upsaude.dto.UserDTO;
import com.upsaude.entity.User;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface UserMapper extends EntityMapper<User, UserDTO> {

    User toEntity(UserDTO dto);

    UserDTO toDTO(User entity);

    User fromRequest(UserRequest request);

    UserResponse toResponse(User entity);
}

