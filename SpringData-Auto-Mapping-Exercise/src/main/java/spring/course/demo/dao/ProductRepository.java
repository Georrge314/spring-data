package spring.course.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.course.demo.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
