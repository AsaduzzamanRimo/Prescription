package prescription.service;

import org.springframework.stereotype.Service;
import prescription.model.Prescription;
import prescription.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;


import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PrescriptionServiceImpl implements PrescriptionService{
	
    //@Autowired
    //private PrescriptionRepository prescriptionRepository;
	private final PrescriptionRepository prescriptionRepository;
    
    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    public List<Prescription> getPrescriptionsByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            return prescriptionRepository.findByPrescriptionDateBetweenOrderByPrescriptionDate(startDate, endDate);
        } catch (Exception e) {
            // Log the exception
            throw new RuntimeException("Error while retrieving prescriptions by date range", e);
        }
    }

    public Prescription createPrescription(Prescription prescription) {
        try {
        	
        	
        	
            // Implement validation logic if needed
            return prescriptionRepository.save(prescription);
        } catch (Exception e) {
            // Log the exception
            throw new RuntimeException("Error while creating prescription", e);
        }
    }
    
 
    private void validatePrescription(Prescription prescription) {
        // Validate prescription date
        LocalDate prescriptionDate = prescription.getPrescriptionDate();
        if (prescriptionDate == null || prescriptionDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Prescription date must be in the past");
        }

        // Validate next visit date
        LocalDate nextVisitDate = prescription.getNextVisitDate();
        if (nextVisitDate == null || nextVisitDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Next visit date must be in the past");
        }
    }
  
   


    public Prescription editPrescription(Long id, Prescription updatedPrescription) {
        try {
            // Implement validation and update logic
            Optional<Prescription> existingPrescription = prescriptionRepository.findById(id);

            if (existingPrescription.isPresent()) {
                Prescription prescription = existingPrescription.get();
                // Update fields with non-null values from updatedPrescription
                if (updatedPrescription.getPrescriptionDate() != null) {
                    prescription.setPrescriptionDate(updatedPrescription.getPrescriptionDate());
                }
                // Update other fields similarly
                // ...

                return prescriptionRepository.save(prescription);
            } else {
                // Handle not found scenario
                return null;
            }
        } catch (Exception e) {
            // Log the exception
            throw new RuntimeException("Error while editing prescription", e);
        }
    }
    
    public Prescription editPrescription(Prescription updatedPrescription) {
        // Retrieve the existing prescription from the database
        Prescription existingPrescription = prescriptionRepository.findById(updatedPrescription.getId())
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        // Update the fields with the new values
        existingPrescription.setPrescriptionDate(updatedPrescription.getPrescriptionDate());
        existingPrescription.setPatientName(updatedPrescription.getPatientName());
        existingPrescription.setPatientAge(updatedPrescription.getPatientAge());
        existingPrescription.setPatientGender(updatedPrescription.getPatientGender());
        existingPrescription.setDiagnosis(updatedPrescription.getDiagnosis());
        existingPrescription.setMedicines(updatedPrescription.getMedicines());
        existingPrescription.setNextVisitDate(updatedPrescription.getNextVisitDate());

        // Save the updated prescription back to the database
        return prescriptionRepository.save(existingPrescription);
    }
    
    

    public void deletePrescription(Long id) {
        try {
            // Implement validation logic if needed
            if (!prescriptionRepository.existsById(id)) {
                throw new NoSuchElementException("Prescription with ID " + id + " not found");
            }

            prescriptionRepository.deleteById(id);
        } catch (DataAccessException e) {
            // Log the exception or handle it according to your application's needs
            throw new RuntimeException("Error while deleting prescription", e);
        }
    }

    public List<Object[]> generatePrescriptionReport() {
        try {
            // Implement report generation logic
            return prescriptionRepository.generateReport(); // Assuming you have a custom query in the repository
        } catch (Exception e) {
            // Log the exception
            throw new RuntimeException("Error while generating prescription report", e);
        }
    }

    public Prescription getPrescriptionById(Long id) {
        try {
            Optional<Prescription> prescription = prescriptionRepository.findById(id);
            return prescription.orElse(null);
        } catch (Exception e) {
            // Log the exception
            throw new RuntimeException("Error while retrieving prescription by ID", e);
        }
    }
    
    public Prescription updatePrescription(Long id, Prescription updatedPrescription) {
        try {
            // Implement validation and update logic
            Optional<Prescription> existingPrescription = prescriptionRepository.findById(id);

            if (existingPrescription.isPresent()) {
                Prescription prescription = existingPrescription.get();
                // Update fields with non-null values from updatedPrescription
                if (updatedPrescription.getPrescriptionDate() != null) {
                    prescription.setPrescriptionDate(updatedPrescription.getPrescriptionDate());
                }
                // Update other fields similarly
                // ...

                return prescriptionRepository.save(prescription);
            } else {
                // Handle not found scenario
                return null;
            }
        } catch (Exception e) {
            // Log the exception
            throw new RuntimeException("Error while updating prescription", e);
        }
    }
    
    @Override
    public List<Object[]> getDayWisePrescriptionCount() {
        return prescriptionRepository.getDayWisePrescriptionCount();
    }
    
    @Override
    public List<Prescription> getAllPrescriptions() {
        try {
            return prescriptionRepository.findAll();
        } catch (Exception e) {
            // Log the exception
            throw new RuntimeException("Error while retrieving all prescriptions", e);
        }
    }

    
}
