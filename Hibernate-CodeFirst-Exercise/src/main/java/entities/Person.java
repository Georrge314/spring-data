package entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class Person extends BaseEntity {
    private String firstName;
    private String lastName;
    private String phoneNumber;

    protected Person() {
    }

    @Column(name = "first_name", nullable = false)
    protected String getFirstName() {
        return firstName;
    }

    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    protected String getLastName() {
        return lastName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "phone_number")
    protected String getPhoneNumber() {
        return phoneNumber;
    }

    protected void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
