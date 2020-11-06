package entities.thirdTask;

import entities.Person;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends Person {
    private String email;
    private double salaryPerHour;
    private Set<Course> courses;

    public Teacher() {
    }

    @Column(name = "email", length = 20)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "salary_per_hour")
    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    @OneToMany(mappedBy = "teacher", targetEntity = Course.class)
    public Set<Course> getCourses() {
        return courses;
    }


    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
