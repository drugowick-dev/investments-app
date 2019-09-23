package dev.drugowick.investments.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the {@link dev.drugowick.investments.domain.Profile} entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    private Long id;

    private String fullName;

    private String email;

    private String bio;

    private String username;
}
