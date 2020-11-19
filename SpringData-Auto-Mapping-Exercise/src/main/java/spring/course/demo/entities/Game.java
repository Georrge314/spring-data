package spring.course.demo.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "games")
public class Game {
    private long id;
    private String title;
    private String trailer;
    private String image;
    private double size;
    private double price;
    private String description;
    private LocalDate releaseDate;
    private User user;


    public Game(String title, String trailer, String image, double size,
                double price, String description, LocalDate releaseDate) {
        this.title = title;
        this.trailer = trailer;
        this.image = image;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public Game(String title, String trailer, String image, long size, double price,
                String description, LocalDate releaseDate, User user) {
        this.title = title;
        this.trailer = trailer;
        this.image = image;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
        this.user = user;
    }

    public Game() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "trailer")
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Column(name = "image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "size")
    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "release_date")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
