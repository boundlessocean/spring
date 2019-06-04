package com.boundless.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class student {
//    @Length(min=2, max=5, message="{user.name.error}")
    @Null(message = "id 不能为空")
    private String id;
    @NotEmpty(message = "name no empty")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
