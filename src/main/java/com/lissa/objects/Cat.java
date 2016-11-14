package com.lissa.objects;

import com.lissa.utils.annotations.Column;
import com.lissa.utils.annotations.Table;

@Table
public class Cat extends Entity {

    @Column(name = "NAME")
    private String name;
    private int age;

    public String getName() {
         return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
