package com.jia.storage.entity;

import java.io.Serializable;

public class Positions implements Serializable {
    private Long id;

    private String name;

    private Integer checkempty;

    private String remark;

    private Long shelfId;

    private String savenum;

    public Positions(Long id, String name, Integer checkempty, String remark, Long shelfId, String savenum) {
        this.id = id;
        this.name = name;
        this.checkempty = checkempty;
        this.remark = remark;
        this.shelfId = shelfId;
        this.savenum = savenum;
    }

    public Positions() {
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

    public Integer getCheckempty() {
        return checkempty;
    }

    public void setCheckempty(Integer checkempty) {
        this.checkempty = checkempty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getShelfId() {
        return shelfId;
    }

    public void setShelfId(Long shelfId) {
        this.shelfId = shelfId;
    }

    public String getSavenum() {
        return savenum;
    }

    public void setSavenum(String savenum) {
        this.savenum = savenum;
    }

    @Override
    public String toString() {
        return "Positions{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", checkempty=" + checkempty +
                ", remark='" + remark + '\'' +
                ", shelfId=" + shelfId +
                ", savenum='" + savenum + '\'' +
                '}';
    }
}