package com.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class DataModel {
    private String name;
    @NotNull
    @NotEmpty
    private String phone;
    @NotNull
    @NotEmpty
    private String channel;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone( String phone ) {
        this.phone = phone;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel( String channel ) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
