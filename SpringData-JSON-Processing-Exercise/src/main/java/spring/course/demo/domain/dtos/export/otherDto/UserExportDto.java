package spring.course.demo.domain.dtos.export.otherDto;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserExportDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Set<ProductBuyerExportDto> soldProducts;

    public UserExportDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<ProductBuyerExportDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<ProductBuyerExportDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
