package app.models.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Item {
    public Item() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //TODO There must be a better way
    @Column(unique = true, columnDefinition = "char(36) default random_uuid()")
    private String uuid = UUID.randomUUID().toString();
    @ManyToOne
    @JoinTable(name = "item_owner", joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "owner_id", referencedColumnName = "id"))
    private Person owner;
    @ManyToOne
    @JoinTable(name = "item_warehouse", joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "warehouse_id", referencedColumnName = "id"))
    private Warehouse warehouse;
    private String name;
    private Integer quantity;
    @Column(columnDefinition = "DECIMAL(10, 5) DEFAULT 0.00")
    private Float unitPrice;
    @Column(columnDefinition = "VARCHAR(3) DEFAULT 'NGN'")
    private String currencyCode;
    @Lob
    private Byte[] picture;

    public Item(Person owner, Warehouse warehouse, String name, Integer quantity, Float unitPrice, String currencyCode, Byte[] picture) {
        this.owner = owner;
        this.warehouse = warehouse;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.currencyCode = currencyCode;
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
        this.picture = picture;
    }
}
