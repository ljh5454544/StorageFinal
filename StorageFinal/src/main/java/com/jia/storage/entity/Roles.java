package com.jia.storage.entity;

import java.io.Serializable;

public class Roles implements Serializable{
    private Long id;

    private String name;

    private String remark;

    public Roles(Long id, String name, String remark) {
        this.id = id;
        this.name = name;
        this.remark = remark;
    }

    public Roles() {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}