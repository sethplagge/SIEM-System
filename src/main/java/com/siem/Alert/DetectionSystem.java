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

    public void attackDetector(SecurityEvent event) {

        LocalDateTime bruteForceEnd = LocalDateTime.now();
        LocalDateTime bruteForceStart = bruteForceEnd.minusMinutes(5);
        List<SecurityEvent> bruteForceEvent = securityEventRepository.findByEventTypeAndIpAddressAndTimestampBetween("LOGIN-FAILED", event.getIpAddress(), bruteForceStart, bruteForceEnd);

        if (bruteForceEvent.size() >= 3 && alertRepository.findByAlertTypeAndIpAddressAndResolved("Brute force attempt", event.getIpAddress(), false).isEmpty()) {
            Alert bruteForce = new Alert(
                    "Brute force attempt",
                    "small",
                    "3 failed login attempts",
                    event.getIpAddress(),
                    false
            );
            alertRepository.save(bruteForce);
        }
    }

}
