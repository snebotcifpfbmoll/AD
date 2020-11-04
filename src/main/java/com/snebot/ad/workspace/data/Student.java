package com.snebot.ad.workspace.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "marshallWrapper")
@XmlAccessorType(XmlAccessType.FIELD)
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
