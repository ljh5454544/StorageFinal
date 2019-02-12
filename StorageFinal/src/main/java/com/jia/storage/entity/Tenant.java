package com.jia.storage.entity;

import java.io.Serializable;

public class Tenant implements Serializable {
    private Long id;

    private String name;

    private String agent;

    private String location;

    private String telphone;

    private String remark;

    public Tenant(Long id, String name, String agent, String location, String telphone, String remark) {
        this.id = id;
        this.name = name;
        this.agent = agent;
        this.location = location;
        this.telphone = telphone;
        this.remark = remark;
    }

    public Tenant() {
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

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", agent='" + agent + '\'' +
                ", location='" + location + '\'' +
                ", telphone='" + telphone + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}