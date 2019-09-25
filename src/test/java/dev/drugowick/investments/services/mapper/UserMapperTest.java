package dev.drugowick.investments.services.mapper;

import dev.drugowick.investments.domain.security.User;
import dev.drugowick.investments.services.dto.UserDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserMapperTest {

    static User user_global;
    static UserDTO userDto_global;

    @BeforeAll
    static void setUp() {
        user_global = new User(
                "brunodrugowick",
                "123",
                true,
                null,
                null,
                null);

        userDto_global = UserMapper.INSTANCE.toDto(user_global);
    }

    @Test
    void toDto() {

//        //when
//        UserDTO userDto = ProfileMapper.INSTANCE.toDto(profile_global);
//
//        //then
//        assertThat(userDto).isNotNull();
//        assertThat(userDto.getId()).isEqualTo(1L);
//        assertThat(userDto.getFullName()).isEqualTo("Bruno Drugowick");
//        assertThat(userDto.getEmail()).isEqualTo("bruno.drugowick@gmail.com");
//        assertThat(userDto.getBio()).isEqualTo("A nice guy wannabe.");
    }

    @Test
    void toEntity() {

//        //when
//        Profile profile = ProfileMapper.INSTANCE.toEntity(userDto_global);
//
//        //then
//        assertThat(profile).isNotNull();
//        assertThat(profile.getId()).isEqualTo(1L);
//        assertThat(profile.getFullName()).isEqualTo("Bruno Drugowick");
//        assertThat(profile.getEmail()).isEqualTo("bruno.drugowick@gmail.com");
//        assertThat(profile.getBio()).isEqualTo("A nice guy wannabe.");
    }


}