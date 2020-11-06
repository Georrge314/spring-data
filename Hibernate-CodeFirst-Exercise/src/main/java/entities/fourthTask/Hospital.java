package entities.fourthTask;


import entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "hospital_data")
public class Hospital extends BaseEntity {
    private Patient patient;
    private Visitation visitation;
    private Diagnose diagnose;
    private Medicament medicament;

    public Hospital(Patient patient, Visitation visitation, Diagnose diagnose, Medicament medicament) {
        this.patient = patient;
        this.visitation = visitation;
        this.diagnose = diagnose;
        this.medicament = medicament;
    }

    public Hospital() {

    }

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ManyToOne
    @JoinColumn(name = "visitation_id", referencedColumnName = "id")
    public Visitation getVisitation() {
        return visitation;
    }

    public void setVisitation(Visitation visitation) {
        this.visitation = visitation;
    }

    @ManyToOne
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }

    
    @ManyToOne
    @JoinColumn(name = "medicament_id", referencedColumnName = "id")
    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }
}
