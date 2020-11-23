package spring.course.demo.domain.dtos.export.usersProducts;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ProductExportDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public ProductExportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
