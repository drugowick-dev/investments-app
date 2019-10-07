package dev.drugowick.investments.domain.security;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"password"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private boolean enabled;

    private String fullName;

    private String email;

    private String bio;

}
