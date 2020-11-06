package entities;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        // first take entities out externally then pur first one and run the program
        // as each time you put the new entity and remove the old

        // In this method put your unit name from persistence.xml file, run and see the new database!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("yourLocationUnit");
    }
}
