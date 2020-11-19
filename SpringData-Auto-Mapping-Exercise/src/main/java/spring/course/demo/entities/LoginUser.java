package spring.course.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "login_users")
public class LoginUser {
    private long id;
    private User user;

    public LoginUser(User user) {
        this.user = user;
    }

    public LoginUser() {
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
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
