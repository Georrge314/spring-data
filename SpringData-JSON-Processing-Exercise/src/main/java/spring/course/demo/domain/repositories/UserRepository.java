package spring.course.demo.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.course.demo.domain.entity.User;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    @Query("SELECT u FROM User u WHERE u.soldProducts.size >= 1 order by u.lastName, u.firstName")
    Set<User> findAllUsersWithSoldProductsGreaterThanOne();

    @Query("SELECT u FROM User u WHERE u.soldProducts.size >= 1 order by u.soldProducts.size DESC, u.lastName")
    Set<User> findAllUsersWithSoldProductsGreaterThanOneTwo();
}
