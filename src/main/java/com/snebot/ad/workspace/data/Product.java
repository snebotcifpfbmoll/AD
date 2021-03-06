package com.snebot.ad.workspace.data;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Product() {}

    public Product(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product product = (Product)obj;
            return this.name.equals(product.getName()) &&
                    this.value == product.getValue();
        }
        return super.equals(obj);
    }
}
