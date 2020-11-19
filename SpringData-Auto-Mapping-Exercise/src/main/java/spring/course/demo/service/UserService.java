package spring.course.demo.service;

public interface UserService {
    String registerUser(String email, String password, String confirmPass, String fullName);

    String loginUser(String email, String password);

    String logout(String email);

    void setAdministration();
}
