package spring.course.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.course.demo.entities.LoginUser;
import spring.course.demo.entities.User;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {
    LoginUser findByUser(User user);

    LoginUser findByUserEmail(String email);
}
