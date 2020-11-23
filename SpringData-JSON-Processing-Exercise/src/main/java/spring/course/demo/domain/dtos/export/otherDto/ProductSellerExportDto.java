package spring.course.demo.domain.dtos.export.otherDto;

import com.google.gson.annotations.Expose;
import spring.course.demo.domain.entity.User;

import java.math.BigDecimal;

public class ProductSellerExportDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private String seller;

    public ProductSellerExportDto() {
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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
