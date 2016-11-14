package com.lissa.objects;

import com.lissa.utils.annotations.Column;
import com.lissa.utils.annotations.Table;
import com.lissa.utils.validation.Ignore;

import java.util.Date;

@Table(name = "User")
public class user extends Entity {
    @Column
    private String name;
    @Column
    private Boolean admin;
    @Column
    private Integer age;
    @Column
    private Double balance;
    @Ignore
    private Date creationDate;

    public user() {
        this.creationDate = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
