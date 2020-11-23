package spring.course.demo.service;

import java.io.IOException;

public interface CategoryService {
    void seedCategory() throws IOException;

    String categoriesByProducts();
}
