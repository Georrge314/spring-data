import entities.fourthTask.*;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class Engine implements Runnable {

    private EntityManager entityManager;
    private BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        Patient patient;
        Diagnose diagnose;
        Medicament medicament;
        Visitation visitation;
        Hospital hospital;

        try {
            entityManager.getTransaction().begin();

            System.out.println("Enter patient first name:");
            String firstName = reader.readLine();
            System.out.println("Enter patient last name:");
            String lastName = reader.readLine();
            System.out.println("Enter address:");
            String address = reader.readLine();
            System.out.println("Enter patient email:");
            String email = reader.readLine();
            System.out.println("Enter patient year of birth:");
            int year = Integer.parseInt(reader.readLine());
            System.out.println("Enter patient mouth of birth:");
            int mouth = Integer.parseInt(reader.readLine());
            System.out.println("Enter patient day of birth:");
            int day = Integer.parseInt(reader.readLine());
            LocalDate dateOfBirth = LocalDate.of(year, mouth, day);
            System.out.println("Enter medicalInsurance: 'yes' or 'no'");
            boolean medicalInsurance = reader.readLine().equals("yes");

            patient = new Patient(firstName, lastName, address, email, dateOfBirth, medicalInsurance);
            entityManager.persist(patient);

            System.out.println("Enter diagnose name:");
            String diagnoseName = reader.readLine();
            System.out.println("Enter comments:");
            String diagnoseComments = reader.readLine();

            diagnose = new Diagnose(diagnoseName, diagnoseComments);
            entityManager.persist(diagnose);

            System.out.println("Enter medicament for the patient");
            String medicamentName = reader.readLine();

            medicament = new Medicament(medicamentName);
            entityManager.persist(medicament);

            System.out.println("Enter comments about visitation:");
            String visitationComments = reader.readLine();

            visitation = new Visitation(LocalDate.now(), visitationComments);
            entityManager.persist(visitation);

            hospital = new Hospital(patient,visitation,diagnose,medicament);
            entityManager.persist(hospital);
            entityManager.getTransaction().commit();
        } catch (IOException exception) {
            System.out.println("Invalid input! Rerun the program");
        }

    }
}
