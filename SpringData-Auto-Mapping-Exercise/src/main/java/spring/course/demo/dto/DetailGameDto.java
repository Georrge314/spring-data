package spring.course.demo.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DetailGameDto {
    private String title;
    private double price;
    private String description;
    private LocalDate releaseDate;

    public DetailGameDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return releaseDate.format(formatter);
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return String.format("Title: %s%nPrice: %.2f%nDescription: %s%n%nRelease Date: %s",
                title,
                price,
                description,
                getReleaseDate());
    }
}
