package prescription.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import prescription.model.Prescription;
@Service
public interface PrescriptionService {
	
	List<Object[]> getDayWisePrescriptionCount();
	List<Prescription> getAllPrescriptions();
	
}
