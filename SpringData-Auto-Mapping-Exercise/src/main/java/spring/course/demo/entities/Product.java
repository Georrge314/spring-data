package spring.course.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    private long id;
    private Order order;

    public Product(long id, Order order) {
        this.id = id;
        this.order = order;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
