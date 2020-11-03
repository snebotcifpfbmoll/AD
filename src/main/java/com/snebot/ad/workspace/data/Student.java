package com.snebot.ad.workspace.data;

import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private Integer age;
    private Float mark;

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Float getMark() {
        return mark;
    }

    public Student(String name, Integer age, Float mark) {
        this.name = name;
        this.age = age;
        this.mark = mark;
    }

    public Student() {
    }
}
