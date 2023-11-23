package prescription.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
//spring.jpa.properties.hibernate.temporal.java.time.LocalDate=jakarta.persistence.TemporalType.DATE

@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "prescription_seq")
    @SequenceGenerator(allocationSize = 1, name = "prescription_seq")
    private Long id;

    @NotNull(message = "Prescription date is required")
   // @Past(message = "Prescription date must be in the past")
    @Temporal(TemporalType.DATE)
    private LocalDate prescriptionDate;

    @NotBlank(message = "Patient name is required")
    private String patientName;

    private int patientAge;

    @NotBlank(message = "Patient gender is required")
    private String patientGender;

    private String diagnosis;

    private String medicines;

    //@Past(message = "Next visit date must be in the past")
    @Temporal(TemporalType.DATE)
    private LocalDate nextVisitDate;
    
    
    // getters and setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getPrescriptionDate() {
		return prescriptionDate;
	}
	public void setPrescriptionDate(LocalDate prescriptionDate) {
		this.prescriptionDate = prescriptionDate;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public int getPatientAge() {
		return patientAge;
	}
	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}
	public String getPatientGender() {
		return patientGender;
	}
	public void setPatientGender(String patientGender) {
		this.patientGender = patientGender;
	}
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public String getMedicines() {
		return medicines;
	}
	public void setMedicines(String medicines) {
		this.medicines = medicines;
	}
	public LocalDate getNextVisitDate() {
		return nextVisitDate;
	}
	public void setNextVisitDate(LocalDate nextVisitDate) {
		this.nextVisitDate = nextVisitDate;
	}
	
	public Prescription() {
		
	}
	
	
	
	public Prescription(LocalDate prescriptionDate, String patientName, int patientAge, String patientGender, String diagnosis, String medicines, LocalDate nextVisitDate) {
	    this.prescriptionDate = prescriptionDate;
	    this.patientName = patientName;
	    this.patientAge = patientAge;
	    this.patientGender = patientGender;
	    this.diagnosis = diagnosis;
	    this.medicines = medicines;
	    this.nextVisitDate = nextVisitDate;
	}

	
	@Override
	public String toString() {
		return "Prescription [id=" + id + ", prescriptionDate=" + prescriptionDate + ", patientName=" + patientName
				+ ", patientAge=" + patientAge + ", patientGender=" + patientGender + ", diagnosis=" + diagnosis
				+ ", medicines=" + medicines + ", nextVisitDate=" + nextVisitDate + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Prescription that = (Prescription) obj;
	    return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id);
	}

	

   
    
}
