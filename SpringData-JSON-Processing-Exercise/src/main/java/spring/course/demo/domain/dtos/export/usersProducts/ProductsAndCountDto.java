package spring.course.demo.domain.dtos.export.usersProducts;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class ProductsAndCountDto {
    @Expose
    private int count;
    @Expose
    private Set<ProductExportDto> products;

    public ProductsAndCountDto() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<ProductExportDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductExportDto> products) {
        this.products = products;
    }
}
