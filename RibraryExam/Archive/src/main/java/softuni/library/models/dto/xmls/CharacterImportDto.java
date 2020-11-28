package softuni.library.models.dto.xmls;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import softuni.library.util.adaptor.LocalDateAdaptor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "character")
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterImportDto {
    @XmlElement
    private int age;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdaptor.class)
    private LocalDate birthday;
    @XmlElement(name = "first-name")
    private String firstName;
    @XmlElement(name = "last-name")
    private String lastName;
    @XmlElement(name = "middle-name")
    private String middleName;
    @XmlElement
    private String role;
    @XmlElement
    private BookImportDto book;

    public CharacterImportDto() {
    }

    @Positive
    @Range(min = 10, max = 66)
    @NotNull
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @NotNull
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Length(min = 3)
    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Length(min = 3)
    @NotNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Length(min = 5)
    @NotNull
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BookImportDto getBook() {
        return book;
    }

    public void setBook(BookImportDto book) {
        this.book = book;
    }
}
