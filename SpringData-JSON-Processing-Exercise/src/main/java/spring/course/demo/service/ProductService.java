package spring.course.demo.service;

import java.io.IOException;

public interface ProductService {
    void seedProducts() throws IOException;

    void generateCategory();

    String getAllProductsInRange();
}
