package app.models.responses;

public class ItemResponse {
    String uuid;
    String name;
    Integer quantity;
    Float unitPrice;
    String currencyCode;
    String pictureUrl;

    public ItemResponse(String uuid, String name, Integer quantity, Float unitPrice, String currencyCode, String pictureUrl) {
        this.uuid = uuid;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.currencyCode = currencyCode;
        this.pictureUrl = pictureUrl;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
