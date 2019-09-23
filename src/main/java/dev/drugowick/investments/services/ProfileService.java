package dev.drugowick.investments.services;

import dev.drugowick.investments.domain.Profile;
import dev.drugowick.investments.repositories.ProfileRepository;
import dev.drugowick.investments.services.dto.ProfileDTO;
import dev.drugowick.investments.services.mapper.ProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProfileService {

    private static final Logger log = LoggerFactory.getLogger(ProfileService.class);

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    public ProfileService(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    /**
     * Save a Profile
     *
     * @param profileDTO the entity to save.
     * @return the saved entity.
     */
    public ProfileDTO save(ProfileDTO profileDTO) {
        log.debug("Request to save Profile : {}", profileDTO);
        Profile profile = profileMapper.toEntity(profileDTO);
        profile = profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    /**
     * Get all Profiles
     *
     * @param pageable the pagination information
     * @return the list of ProfilesDTO
     */
    @Transactional(readOnly = true)
    public Page<ProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Profiles");
        return profileRepository.findAll(pageable)
                .map(profileMapper::toDto);
    }

    /**
     * Find one specific profile
     *
     * @param id the id of the Profile to find
     * @return the Profile DTO if found
     */
    @Transactional(readOnly = true)
    public Optional<ProfileDTO> findOne(Long id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findById(id)
                .map(profileMapper::toDto);
    }

    /**
     * Delete profile by id
     *
     * @param id the id of the profile to delete
     */
    public void delete(Long id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.deleteById(id);
    }

}
