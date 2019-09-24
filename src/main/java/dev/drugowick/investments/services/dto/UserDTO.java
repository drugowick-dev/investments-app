package dev.drugowick.investments.services.dto;

import lombok.*;

/**
 * A DTO for the {@link dev.drugowick.investments.domain.security.User} entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"password"})
public class UserDTO {

    private String username;
    private String password;
    private boolean enabled;
    private String fullName;
    private String email;
    private String bio;
}
