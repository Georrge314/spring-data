package spring.course.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.course.demo.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
