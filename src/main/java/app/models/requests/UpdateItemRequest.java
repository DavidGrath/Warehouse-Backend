package app.models.requests;

import org.springframework.web.multipart.MultipartFile;

public class UpdateItemRequest {
    String name;
    String warehouseId;
    Integer quantity;
    String currencyCode;
    Float unitPrice;
    MultipartFile picture;

    public UpdateItemRequest(String name, String warehouseId, Integer quantity, String currencyCode, Float unitPrice, MultipartFile picture) {
        this.name = name;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.currencyCode = currencyCode;
        this.unitPrice = unitPrice;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }
}
