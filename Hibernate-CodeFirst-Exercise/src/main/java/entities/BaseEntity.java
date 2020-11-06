package entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    private int id;

    protected BaseEntity() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
}
