package prescription.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import prescription.model.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
	List<Prescription> findByPrescriptionDateBetweenOrderByPrescriptionDate(LocalDate startDate, LocalDate endDate);


	
    @Query("SELECT p.prescriptionDate AS day, COUNT(p) AS prescriptionCount " +
            "FROM Prescription p " +
            "GROUP BY p.prescriptionDate " +
            "ORDER BY p.prescriptionDate")
     List<Object[]> generateReport();

     @Query("SELECT DATE(p.prescriptionDate) AS day, COUNT(p) AS count FROM Prescription p GROUP BY day ORDER BY day")
     List<Object[]> getDayWisePrescriptionCount();
}

