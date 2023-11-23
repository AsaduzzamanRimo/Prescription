package prescription.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import prescription.model.Prescription;
import prescription.service.PrescriptionServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionRestController {
    @Autowired
    private PrescriptionServiceImpl prescriptionService;
    
    
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        return new ResponseEntity<>(prescriptions, HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<Prescription>> showPrescriptions(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            if (startDate == null || endDate == null) {
                // Set default date range (current month)
                startDate = LocalDate.now().withDayOfMonth(1);
                endDate = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);
            }

            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDateRange(startDate, endDate);
            return ResponseEntity.ok(prescriptions);
        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                         .body(new ArrayList<Prescription>());
        }
    }
   

    
/*
    @GetMapping
    public List<Prescription> getPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }
*/
    @GetMapping("/{id}")
    public Prescription getPrescriptionById(@PathVariable Long id) {
        return prescriptionService.getPrescriptionById(id);
    }

    @PostMapping
    public Prescription createPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.createPrescription(prescription);
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<Prescription> editPrescription(@PathVariable Long id) {
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            return ResponseEntity.ok(prescription);
        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
/*
    
    @PutMapping("/{id}")
    public Prescription updatePrescription(@PathVariable Long id, @RequestBody Prescription updatedPrescription) {
        return prescriptionService.updatePrescription(id, updatedPrescription);
    }
*/
    
    @PutMapping("/edit")
    public ResponseEntity<String> editPrescription(@RequestBody Prescription prescription) {
        try {
            // Implement validation if needed
            prescriptionService.editPrescription(prescription);
            return ResponseEntity.ok("Prescription updated successfully.");
        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating prescription.");
        }
    }
/*
    @DeleteMapping("/{id}")
    public void deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
    }
}
*/
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrescription(@PathVariable Long id) {
        try {
            prescriptionService.deletePrescription(id);
            return ResponseEntity.ok("Prescription deleted successfully.");
        } catch (Exception e) {
            // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting prescription: " + e.getMessage());
        }
    }
    
    @GetMapping("/report-day-wise")
    public ResponseEntity<List<Object[]>> dayWisePrescriptionReport() {
        try {
            List<Object[]> reportData = prescriptionService.getDayWisePrescriptionCount();
            return new ResponseEntity<>(reportData, HttpStatus.OK);
        } catch (Exception e) {
            // Log the exception or handle it as per your requirement
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
