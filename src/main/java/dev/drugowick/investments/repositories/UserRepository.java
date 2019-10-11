package dev.drugowick.investments.repositories;

import dev.drugowick.investments.domain.security.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for the Profile entity.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByProviderAndProviderId(String provider, String providerId);

}
