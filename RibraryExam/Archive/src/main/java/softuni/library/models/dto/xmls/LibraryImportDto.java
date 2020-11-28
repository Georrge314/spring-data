package softuni.library.models.dto.xmls;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.NonNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "library")
@XmlAccessorType(XmlAccessType.FIELD)
public class LibraryImportDto {
    @XmlElement
    private String name;
    @XmlElement
    private String location;
    @XmlElement
    private int rating;
    @XmlElement
    private BookImportDto book;

    public LibraryImportDto() {
    }

    @Length(min = 3)
    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 5)
    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Range(min = 1, max = 10)
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public BookImportDto getBook() {
        return book;
    }

    public void setBook(BookImportDto book) {
        this.book = book;
    }
}
