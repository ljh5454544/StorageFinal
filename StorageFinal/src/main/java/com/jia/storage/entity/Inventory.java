package com.jia.storage.entity;

import java.io.Serializable;
import java.util.Date;

public class Inventory implements Serializable {
    private static final Long serialVersionUID =  2640445916174263508L;

    private Long id;

    private String identity;

    private Long undertaker;

    private String agent;

    private Long kind;

    private Long price;

    private String recordtime;

    private String dealtime;

    private String positions;

    private Long stat;

    private String userName;

    public Inventory(Long id, String identity, Long undertaker, String agent, Long kind, Long price, String recordtime, String dealtime, String positions) {
        this.id = id;
        this.identity = identity;
        this.undertaker = undertaker;
        this.agent = agent;
        this.kind = kind;
        this.price = price;
        this.recordtime = recordtime;
        this.dealtime = dealtime;
        this.positions = positions;
    }

    public Inventory(Long id, String identity, Long undertaker, String agent, Long kind, Long price, String recordtime, String dealtime, String positions, String userName) {
        this.id = id;
        this.identity = identity;
        this.undertaker = undertaker;
        this.agent = agent;
        this.kind = kind;
        this.price = price;
        this.recordtime = recordtime;
        this.dealtime = dealtime;
        this.positions = positions;
        this.userName = userName;
    }

    public Inventory() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public Long getUndertaker() {
        return undertaker;
    }

    public void setUndertaker(Long undertaker) {
        this.undertaker = undertaker;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    public Long getKind() {
        return kind;
    }

    public void setKind(Long kind) {
        this.kind = kind;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public String getDealtime() {
        return dealtime;
    }

    public void setDealtime(String dealtime) {
        this.dealtime = dealtime;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions == null ? null : positions.trim();
    }

    public Long getStat() {
        return stat;
    }

    public void setStat(Long stat) {
        this.stat = stat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", identity='" + identity + '\'' +
                ", undertaker=" + undertaker +
                ", agent='" + agent + '\'' +
                ", kind=" + kind +
                ", price=" + price +
                ", recordtime=" + recordtime +
                ", dealtime=" + dealtime +
                ", positions='" + positions + '\'' +
                ", stat=" + stat +
                ", userName='" + userName + '\'' +
                '}';
    }
}