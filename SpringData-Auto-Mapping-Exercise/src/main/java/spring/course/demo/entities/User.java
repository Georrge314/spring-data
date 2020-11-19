package spring.course.demo.entities;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    private long id;
    private String email;
    private String password;
    private String fullName;
    private boolean isAdministrator;
    private Set<Game> games;
    private Set<Order> orders;

    public User(String email, String password, String full_name) {
        this.email = email;
        this.password = password;
        this.fullName = full_name;
    }

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String full_name) {
        this.fullName = full_name;
    }

    @OneToMany(mappedBy = "user")
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    @Column(name = "is_admin")
    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }

    @OneToMany(mappedBy = "user")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
