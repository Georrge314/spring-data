package spring.course.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.course.demo.service.CategoryService;
import spring.course.demo.service.ProductService;
import spring.course.demo.service.UserService;

@Component
public class AppController implements CommandLineRunner {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public AppController(UserService userService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.seedUsers();
        productService.seedProducts();
        categoryService.seedCategory();
        productService.generateCategory();

        //1 Query
        System.out.println(productService.getAllProductsInRange());
        //2 Query
        System.out.println(userService.getUsersSoldProducts());
        //3 Query
        System.out.println(categoryService.categoriesByProducts());
        //4 Query
        System.out.println(userService.getUsersAndProducts());
    }
}
