package app.models.responses;

public class WarehouseResponse {
    String name;
    String address;
    String uuid;
    String pictureUrl;
    PersonResponse owner;

    public WarehouseResponse(String name, String address, String uuid, String pictureUrl, PersonResponse owner) {
        this.name = name;
        this.address = address;
        this.uuid = uuid;
        this.pictureUrl = pictureUrl;
        this.owner = owner;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public PersonResponse getOwner() {
        return owner;
    }

    public void setOwner(PersonResponse owner) {
        this.owner = owner;
    }
}
