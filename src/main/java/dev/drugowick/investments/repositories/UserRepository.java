package dev.drugowick.investments.repositories;

import dev.drugowick.investments.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Profile entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
