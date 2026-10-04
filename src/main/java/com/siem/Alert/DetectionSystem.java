package com.siem.Alert;
import com.siem.SecurityEvent.*;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DetectionSystem {

    private final SecurityEventRepository securityEventRepository;
    private final AlertRepository alertRepository;

    public DetectionSystem(SecurityEventRepository securityEventRepository, AlertRepository alertRepository) {
        this.securityEventRepository = securityEventRepository;
        this.alertRepository = alertRepository;
    }

    public void bruteForceAttack(SecurityEvent event) {

        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusMinutes(5);

        List<SecurityEvent> bruteForceEvents = securityEventRepository.findByEventTypeAndIpAddressAndTimestampBetween(
                "LOGIN-FAILED",
                event.getIpAddress(),
                start,
                end
        );

        if (bruteForceEvents.size() >= 3) {
            Alert bruteForce = new Alert(
                    "Brute force attempt",
                    "medium",
                    "",
                    event.getIpAddress(),
                    LocalDateTime.now(),
                    false
            );
            alertRepository.save(bruteForce);
        }
    }

}
