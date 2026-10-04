package com.siem.Alert;

import com.siem.SecurityEvent.SecurityEvent;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlertController {

    private final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public Alert alert(int id) {
        return alertRepository.findById(id).orElse(null);
    }

    @GetMapping("/alert")
    public List<Alert> getAlerts() {
        return alertRepository.findByResolvedFalse();
    }

    @DeleteMapping("/alert/{id}")
    public String resolveAlert(@PathVariable int id) {
        Alert alert = alert(id);
        if(alert!=null && !alert.isResolved()) {
            alert.setResolved(true);
            alertRepository.save(alert);
            return "Alert has been resolved";
        }
        return "Alert does not exist";
    }

    @PutMapping("/alert/restore/{id}")
    public String unresolveAlert(@PathVariable int id) {
        Alert alert = alert(id);
        if(alert!=null && alert.isResolved()) {
            alert.setResolved(false);
            alertRepository.save(alert);
            return "Alert has been unresolved";
        }
        else if (alert!=null) {
            return "Alert is still unresolved";
        }
        return "Alert does not exist";
    }

    @PutMapping("/alert/document/{id}")
    public String addNotesToAlert(@RequestBody Alert alert, @PathVariable int id) {
        Alert oldAlert = alert(id);
        oldAlert.setNotes(alert.getNotes());
        alertRepository.save(oldAlert);
        return "notes added";
    }

    //for testing only
    @DeleteMapping("/alert")
    public void deleteAllAlerts() {
        alertRepository.deleteAll();
    }

}
