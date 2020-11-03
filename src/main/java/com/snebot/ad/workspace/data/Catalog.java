package com.snebot.ad.workspace.data;

import com.snebot.ad.workspace.helper.MarshallingWrapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Catalog extends MarshallingWrapper {
    @XmlElement(name = "name")
    private String name;

    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    private List<Product> products;

    @XmlElement(name = "total")
    private Double total;

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotal() {
        double total = 0;

        if (products != null) {
            for (Product product : products) {
                if (product != null) {
                    total += product.getValue();
                }
            }
        }

        setTotal(total);

        return getTotal();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}