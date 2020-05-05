package app.models.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
public class Warehouse {
    public Warehouse() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    //TODO There must be a better way
    @Column(unique = true, columnDefinition = "char(36) default random_uuid()")
    private String uuid = UUID.randomUUID().toString();
    @Lob
    private Byte[] picture;
    @ManyToOne
    @JoinTable(name = "warehouse_owner", joinColumns = @JoinColumn(name = "warehouse_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id", referencedColumnName = "id"))
    private Person owner;
    @OneToMany(mappedBy = "warehouse")
    Collection<Item> items;
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "warehouse_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    Collection<Person> users;

    public Warehouse(String name, String address, Byte[] picture, Person owner, Collection<Item> items, Collection<Person> users) {
        this.name = name;
        this.address = address;
        this.picture = picture;
        this.owner = owner;
        this.items = items;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
        this.picture = picture;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }

    public Collection<Person> getUsers() {
        return users;
    }

    public void setUsers(Collection<Person> users) {
        this.users = users;
    }
}