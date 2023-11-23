package prescription.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prescription.model.Prescription;
import prescription.service.PrescriptionServiceImpl;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {
    @Autowired
    private PrescriptionServiceImpl prescriptionService;

    @GetMapping("/list")
    public String showPrescriptions(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        try {
            if (startDate == null || endDate == null) {
                // Set default date range (current month)
                startDate = LocalDate.now().withDayOfMonth(1);
                endDate = LocalDate.now().plusMonths(1).withDayOfMonth(1).minusDays(1);
            }

            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDateRange(startDate, endDate);
            model.addAttribute("prescriptions", prescriptions);
            
            // Fetch day-wise prescription count
            List<Object[]> dayWisePrescriptionCount = prescriptionService.getDayWisePrescriptionCount();
            model.addAttribute("dayWisePrescriptionCount", dayWisePrescriptionCount);

            return "prescription-list";
        } catch (Exception e) {
            // Log the exception
            model.addAttribute("error", "Error while loading prescription list: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }

    /*
    @GetMapping("/list")
    public String showPrescriptions(Model model) {
        try {
            // By default, show prescriptions for the current month
            LocalDate startDate = LocalDate.now().withDayOfMonth(1);
            LocalDate endDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByDateRange(startDate, endDate);
            model.addAttribute("prescriptions", prescriptions);

            return "prescription-list";
        } catch (Exception e) {
            // Log the exception
            model.addAttribute("error", "Error while loading prescription list: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        try {
            model.addAttribute("prescription", new Prescription());
            return "prescription-form";
        } catch (Exception e) {
            // Log the exception
            model.addAttribute("error", "Error while loading create form: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }

    @PostMapping("/create")
    public String createPrescription(@ModelAttribute Prescription prescription, RedirectAttributes redirectAttributes) {
        try {
            // Implement validation if needed
            prescriptionService.createPrescription(prescription);
            redirectAttributes.addFlashAttribute("message", "Prescription created successfully.");
            return "redirect:/prescriptions/list";
        } catch (Exception e) {
            // Log the exception
            redirectAttributes.addFlashAttribute("error", "Error creating prescription: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }

 */
    
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        try {
            model.addAttribute("prescription", new Prescription());
            return "prescription-form";
        } catch (Exception e) {
            // Log the exception (you can use a logging framework like SLF4J)
            e.printStackTrace();
            model.addAttribute("error", "Error while loading create form.");
            return "redirect:/prescriptions/list";
        }
    }

    @PostMapping("/create")
    public String createPrescription(@ModelAttribute Prescription prescription, RedirectAttributes redirectAttributes) {
        try {
            // Implement validation if needed
            prescriptionService.createPrescription(prescription);
            redirectAttributes.addFlashAttribute("message", "Prescription created successfully.");
            return "redirect:/prescriptions/list";
        } catch (Exception e) {
            // Log the exception (you can use a logging framework like SLF4J)
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error creating prescription.");
            return "redirect:/prescriptions/list";
        }
    }

    
   /* 
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            model.addAttribute("prescription", prescription);
            return "prescription-form";
        } catch (Exception e) {
            // Log the exception
            model.addAttribute("error", "Error while loading edit form: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String editPrescription(@PathVariable Long id, @ModelAttribute Prescription updatedPrescription,
                                   RedirectAttributes redirectAttributes) {
        try {
            // Implement validation if needed
            prescriptionService.editPrescription(id, updatedPrescription);
            redirectAttributes.addFlashAttribute("message", "Prescription updated successfully.");
            return "redirect:/prescriptions/list";
        } catch (Exception e) {
            // Log the exception
            redirectAttributes.addFlashAttribute("error", "Error updating prescription: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }
*/
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            model.addAttribute("prescription", prescription);
            return "prescription-edit-form";
        } catch (Exception e) {
            // Handle the exception and redirect as needed
            model.addAttribute("error", "Error while loading edit form: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }

    
    @PostMapping("/edit")
    public String editPrescription(@ModelAttribute Prescription prescription, RedirectAttributes redirectAttributes) {
        try {
            // Implement validation if needed
            prescriptionService.editPrescription(prescription);
            redirectAttributes.addFlashAttribute("message", "Prescription updated successfully.");
            return "redirect:/prescriptions/list";
        } catch (Exception e) {
            // Handle the exception and redirect as needed
            redirectAttributes.addFlashAttribute("error", "Error updating prescription: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }


    
    
    /*
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            model.addAttribute("prescription", prescription);
            return "delete-confirmation";
        } catch (Exception e) {
            // Log the exception
            model.addAttribute("error", "Error while loading delete confirmation: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }

    @PostMapping("/delete/{id}")
    public String deletePrescription(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            prescriptionService.deletePrescription(id);
            redirectAttributes.addFlashAttribute("message", "Prescription deleted successfully.");
            return "redirect:/prescriptions/list";
        } catch (Exception e) {
            // Log the exception
            redirectAttributes.addFlashAttribute("error", "Error deleting prescription: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }
    */
    
    @GetMapping("/delete/{id}")
    public String showDeleteForm(@PathVariable Long id, Model model) {
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            model.addAttribute("prescription", prescription);
            return "delete-prescription";
        } catch (Exception e) {
            // Log the exception
            model.addAttribute("error", "Error while loading delete form: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }

    @PostMapping("/delete")
    public String deletePrescription(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            prescriptionService.deletePrescription(id);
            redirectAttributes.addFlashAttribute("message", "Prescription deleted successfully.");
            return "redirect:/prescriptions/list";
        } catch (Exception e) {
            // Log the exception
            redirectAttributes.addFlashAttribute("error", "Error deleting prescription: " + e.getMessage());
            return "redirect:/prescriptions/list";
        }
    }
    
    @GetMapping("/reports/day-wise")
    public String dayWisePrescriptionReport(Model model) {
        try {
            List<Object[]> reportData = prescriptionService.getDayWisePrescriptionCount();
            model.addAttribute("reportData", reportData);
            return "day-wise-report";
        } catch (Exception e) {
            // Log the exception or handle it as per your requirement
            model.addAttribute("error", "Error generating day-wise prescription report: " + e.getMessage());
            return "error-page"; // You can create a separate error page
        }
    }

   
}

    
   

