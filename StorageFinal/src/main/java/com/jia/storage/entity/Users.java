package com.jia.storage.entity;

import java.io.Serializable;
import java.util.Date;

public class Users implements Serializable {
    private Long id;

    private String username;

    private String password;

    private Date birth;

    private String telephone;

    private String remark;

    private Long roleId;

    private String email;

    private String roleName;

    public Users(Long id, String username, String password, Date birth, String telephone, String remark, Long roleId, String email, String roleName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.birth = birth;
        this.telephone = telephone;
        this.remark = remark;
        this.roleId = roleId;
        this.email = email;
        this.roleName = roleName;
    }

    public Users() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birth=" + birth +
                ", telephone='" + telephone + '\'' +
                ", remark='" + remark + '\'' +
                ", roleId=" + roleId +
                ", email='" + email + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}