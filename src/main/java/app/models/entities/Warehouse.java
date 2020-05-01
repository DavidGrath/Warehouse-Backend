package app.models.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Warehouse {
    public Warehouse() {
    }

    @Id
    Long id;
    String name;
}