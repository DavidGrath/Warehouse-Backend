package app.models.entities.repositories;

import app.models.entities.Warehouse;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {
    Optional<Warehouse> findByName(String name);
    Optional<Warehouse> findByUuid(String uuid);
}
