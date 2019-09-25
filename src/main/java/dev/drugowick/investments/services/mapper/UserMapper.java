package dev.drugowick.investments.services.mapper;

import dev.drugowick.investments.domain.security.User;
import dev.drugowick.investments.services.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link User} and its DTO {@link UserDTO}
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDto(User user);

    User toEntity(UserDTO userDto);

}
