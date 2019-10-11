package dev.drugowick.investments.domain.security;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"providerId"}))
public class User {

    private String providerId;

    private String provider;

    private boolean enabled;

    private String fullName;

    @Id
    private String email;

    private String bio;

}
