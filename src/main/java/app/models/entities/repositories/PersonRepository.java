package app.models.entities.repositories;

import app.models.entities.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
    boolean existsByUUID(String uuid);
    boolean existsByUsername(String username);
}
