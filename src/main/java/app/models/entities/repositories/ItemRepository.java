package app.models.entities.repositories;

import app.models.entities.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Optional<Item> findByUuid(String uuid);
}
