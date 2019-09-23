package dev.drugowick.investments.services.mapper;

import dev.drugowick.investments.domain.Profile;
import dev.drugowick.investments.domain.security.User;
import dev.drugowick.investments.services.dto.ProfileDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProfileMapperTest {

    static Profile profile_global;
    static ProfileDTO profileDTO_global;

    @BeforeAll
    static void setUp() {
        profile_global = new Profile(
                1L,
                "Bruno Drugowick",
                "bruno.drugowick@gmail.com",
                "A nice guy wannabe.",
                new User("brunodrugowick", "123", true));

        profileDTO_global = ProfileMapper.INSTANCE.toDto(profile_global);
    }

    @Test
    void toDto() {

        //when
        ProfileDTO profileDto = ProfileMapper.INSTANCE.toDto(profile_global);

        //then
        assertThat(profileDto).isNotNull();
        assertThat(profileDto.getId()).isEqualTo(1L);
        assertThat(profileDto.getFullName()).isEqualTo("Bruno Drugowick");
        assertThat(profileDto.getEmail()).isEqualTo("bruno.drugowick@gmail.com");
        assertThat(profileDto.getBio()).isEqualTo("A nice guy wannabe.");
    }

    @Test
    void toEntity() {

        //when
        Profile profile = ProfileMapper.INSTANCE.toEntity(profileDTO_global);

        //then
        assertThat(profile).isNotNull();
        assertThat(profile.getId()).isEqualTo(1L);
        assertThat(profile.getFullName()).isEqualTo("Bruno Drugowick");
        assertThat(profile.getEmail()).isEqualTo("bruno.drugowick@gmail.com");
        assertThat(profile.getBio()).isEqualTo("A nice guy wannabe.");
    }


}