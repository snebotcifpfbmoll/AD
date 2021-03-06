package com.snebot.ad.workspace.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "catalog")
@XmlAccessorType(XmlAccessType.FIELD)
public class Catalog implements Serializable {
    @XmlElement(name = "name")
    private String name;

    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    private List<Product> products;

    @XmlElement(name = "total")
    private Double total = 0.0D;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Catalog) {
            Catalog catalog = (Catalog)obj;
            return StringUtils.equals(this.name, catalog.name);
        }
        return super.equals(obj);
    }
}