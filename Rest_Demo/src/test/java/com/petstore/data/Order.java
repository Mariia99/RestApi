package com.petstore.data;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Order {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("petId")
    private Integer petId;
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("shipDate")
    private String shipDate;
    @JsonProperty("status")
    private String status;
    @JsonProperty("complete")
    private Boolean complete;

    public Order() {
    }

    public Order(Integer id, Integer petId, Integer quantity, String shipDate, String status, Boolean complete) {
        super();
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("petId")
    public Integer getPetId() {
        return petId;
    }

    @JsonProperty("petId")
    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("shipDate")
    public String getShipDate() {
        return shipDate;
    }

    @JsonProperty("shipDate")
    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("complete")
    public Boolean getComplete() {
        return complete;
    }

    @JsonProperty("complete")
    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Example{" +
                "id=" + id +
                ", petId=" + petId +
                ", quantity=" + quantity +
                ", shipDate='" + shipDate + '\'' +
                ", status='" + status + '\'' +
                ", complete=" + complete +
                '}';
    }
}
