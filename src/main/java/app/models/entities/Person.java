package app.models.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
public class Person {
    protected Person() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //TODO There must be a better way
    @Column(unique = true, columnDefinition = "char(36) default random_uuid()")
    private String UUID = java.util.UUID.randomUUID().toString();
    private String username;
    private String firstName;
    private String lastName;
    private String eMail;
    private String password;
    @Lob
    private Byte[] displayPicture;

    @ManyToMany
    @JoinTable(name = "role_person", joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    Collection<Role> roles;
    @ManyToMany(mappedBy = "users")
    Collection<Warehouse> warehouses;
    @OneToMany(mappedBy = "owner")
    Collection<Item> items;

    public Person(String username, String firstName, String lastName, String eMail, String password, Byte[] displayPicture, Collection<Role> roles, Collection<Warehouse> warehouses, Collection<Item> items) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.password = password;
        this.displayPicture = displayPicture;
        this.roles = roles;
        this.warehouses = warehouses;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte[] getDisplayPicture() {
        return displayPicture;
    }

    public void setDisplayPicture(Byte[] displayPicture) {
        this.displayPicture = displayPicture;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Collection<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Collection<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public Collection<Item> getItems() {
        return items;
    }

    public void setItems(Collection<Item> items) {
        this.items = items;
    }
}
