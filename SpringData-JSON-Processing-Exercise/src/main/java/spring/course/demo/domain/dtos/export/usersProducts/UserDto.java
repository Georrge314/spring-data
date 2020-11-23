package spring.course.demo.domain.dtos.export.usersProducts;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private ProductsAndCountDto soldProducts;

    public UserDto() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ProductsAndCountDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductsAndCountDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
