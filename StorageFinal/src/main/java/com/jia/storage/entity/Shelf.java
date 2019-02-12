package com.jia.storage.entity;

import java.io.Serializable;

public class Shelf implements Serializable {
    private Long id;

    private String name;

    private Long num;

    private String remark;

    private Long roomId;

    public Shelf(Long id, String name, Long num, String remark, Long roomId) {
        this.id = id;
        this.name = name;
        this.num = num;
        this.remark = remark;
        this.roomId = roomId;
    }

    public Shelf() {
        super();
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
        this.name = name == null ? null : name.trim();
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}