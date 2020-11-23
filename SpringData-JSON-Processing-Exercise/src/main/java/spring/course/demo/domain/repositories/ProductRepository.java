package spring.course.demo.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.course.demo.domain.entity.Product;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Set<Product> findAllByBuyerIsNullAndPriceBetweenOrderByPrice(BigDecimal upperBound, BigDecimal lowerBound);
}
