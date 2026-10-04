package com.securitymonitor;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class SecurityEventController {

    private final SecurityEventRepository securityEventRepository;

    public SecurityEventController(SecurityEventRepository securityEventRepository) {
        this.securityEventRepository = securityEventRepository;
    }

    @GetMapping("/event")
    public List<SecurityEvent> getEvents() {
        return securityEventRepository.findAll();
    }

    @PostMapping("/event")
    public void createEvent(@RequestBody SecurityEvent securityEvent) {
            securityEventRepository.save(securityEvent);
    }


}
