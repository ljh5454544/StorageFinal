package com.jia.storage.entity;

import java.io.Serializable;

public class Record implements Serializable {
    private Long id;

    private Long inventoryId;

    private Long commodityId;

    private Long num;

    private Long price;

    private String commodityName;

    private Long commodityPrice;

    public Record(Long id, Long inventoryId, Long commodityId, Long num, Long price, String commodityName, Long commodityPrice) {
        this.id = id;
        this.inventoryId = inventoryId;
        this.commodityId = commodityId;
        this.num = num;
        this.price = price;
        this.commodityName = commodityName;
        this.commodityPrice = commodityPrice;
    }

    public Record() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Long getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(Long commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", inventoryId=" + inventoryId +
                ", commodityId=" + commodityId +
                ", num=" + num +
                ", price=" + price +
                ", commodityName='" + commodityName + '\'' +
                ", commodityPrice=" + commodityPrice +
                '}';
    }
}