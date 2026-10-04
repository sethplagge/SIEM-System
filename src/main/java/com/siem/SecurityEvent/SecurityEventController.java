package com.siem.SecurityEvent;
import com.siem.Alert.*;

import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class SecurityEventController {

    private final SecurityEventRepository securityEventRepository;
    private final DetectionSystem detectionSystem;


    public SecurityEventController(SecurityEventRepository securityEventRepository, DetectionSystem detectionSystem) {
        this.securityEventRepository = securityEventRepository;
        this.detectionSystem = detectionSystem;
    }

    public SecurityEvent event(int id) {
        return securityEventRepository.findById(id).orElse(null);
    }

    @GetMapping("/event")
    public List<SecurityEvent> getEvents() {
        return securityEventRepository.findByDeletedFalse();
    }

    @PostMapping("/event")
    public void createEvent(@RequestBody SecurityEvent securityEvent, HttpServletRequest ip) {
        securityEvent.setIpAddress(ip.getRemoteAddr());
        securityEventRepository.save(securityEvent);
        detectionSystem.bruteForceAttack(securityEvent);
    }

    @DeleteMapping("/event/{eventID}")
    public String deleteEvent(@PathVariable int eventID) {
        SecurityEvent event = event(eventID);
        if(event!=null && !event.isDeleted()) {
            event.setDeleted(true);
            securityEventRepository.save(event);
            return "Event has been deleted";
        }
        return "Event does not exist";
    }

    @PutMapping("/event/{id}")
    public String restoreEvent(@PathVariable int id) {
        SecurityEvent event = event(id);
        if(event!=null && event.isDeleted()) {
            event.setDeleted(false);
            securityEventRepository.save(event);
            return "Event has been restored";
        }
        else if (event != null) {
            return "Event is not deleted";
        }
        return "Event does not exist";
    }

    //for testing only
    @GetMapping("/events")
    public List<SecurityEvent> getAllEvents() {
        return securityEventRepository.findAll();
    }

    //for testing only
    @DeleteMapping("/event")
    public void deleteAllEvents() {
        securityEventRepository.deleteAll();
    }

}
