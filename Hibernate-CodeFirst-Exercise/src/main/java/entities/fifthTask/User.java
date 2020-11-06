package entities.fifthTask;

import entities.Person;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends Person {
   private String email;
   private String password;
   private BillingDetail billingDetail;

    public User() {
    }

    public User(String email, String password, BillingDetail billingDetail) {
        this.email = email;
        this.password = password;
        this.billingDetail = billingDetail;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @ManyToOne
    @JoinColumn(name = "billingDetail_id", referencedColumnName = "id")
    public BillingDetail getBillingDetail() {
        return billingDetail;
    }

    public void setBillingDetail(BillingDetail billingDetail) {
        this.billingDetail = billingDetail;
    }
}
