package entities.fifthTask;

import entities.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "billing_detail_type")
public abstract class BillingDetail extends BaseEntity {
    private int number;
    private String owner;
    private Set<User> user;

    protected BillingDetail(int number, String owner) {
        this.number = number;
        this.owner = owner;
    }


    protected int getNumber() {
        return number;
    }

    protected void setNumber(int number) {
        this.number = number;
    }

    protected String getOwner() {
        return owner;
    }

    protected void setOwner(String owner) {
        this.owner = owner;
    }

    @OneToMany(mappedBy = "billingDetail", targetEntity = User.class)
    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }
}
