package spring.course.demo.service;

import java.io.IOException;

public interface UserService {
    void seedUsers() throws IOException;

    String getUsersSoldProducts();

    String getUsersAndProducts();
}
