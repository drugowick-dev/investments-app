package dev.drugowick.investments.services.dto;

import lombok.*;

import javax.validation.constraints.Email;

/**
 * A DTO (also called Command Object) for the {@link dev.drugowick.investments.domain.security.User} entity.
 *
 * The validations here are used to render messages on the View via BindingResult object.
 * See https://medium.com/@grokwich/spring-boot-thymeleaf-html-form-handling-part-2-b4c9e83a189c
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String providerId;
    private String provider;
    private boolean enabled;
    private String fullName;

    @Email
    private String email;
    private String bio;
}
