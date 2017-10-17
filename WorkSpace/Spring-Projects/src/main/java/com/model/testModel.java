package com.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class testModel {

    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
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

    @Override
    public String toString() {
        return "testModel{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
