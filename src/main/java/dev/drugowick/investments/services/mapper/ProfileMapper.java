package dev.drugowick.investments.services.mapper;

import dev.drugowick.investments.domain.Profile;
import dev.drugowick.investments.services.dto.ProfileDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    //TODO map username to the DTO attribute `username`
    ProfileDTO toDto(Profile profile);

    Profile toEntity(ProfileDTO profileDTO);

}
