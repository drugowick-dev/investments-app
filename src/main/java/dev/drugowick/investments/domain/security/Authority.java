package dev.drugowick.investments.domain.security;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity(name = "Authorities")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@IdClass(Authority.CompositeKey.class)
public class Authority {

    @Id
    private String username;

    @Id
    private String authority;

    public class CompositeKey implements Serializable {
        private String username;
        private String authority;
    }
}
