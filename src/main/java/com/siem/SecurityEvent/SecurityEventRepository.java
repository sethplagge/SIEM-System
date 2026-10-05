package com.siem.SecurityEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDateTime;

public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Integer>{

    List<SecurityEvent> findByDeletedFalse();

    List<SecurityEvent> findByEventTypeAndIpAddressAndTimestampBetween(
            String eventType,
            String ipAddress,
            LocalDateTime start, LocalDateTime end
    );

    List<SecurityEvent> findByCountryAndIpAddressAndTimestampBetween(
            String Country,
            String ipAddress,
            LocalDateTime start, LocalDateTime end
    );

}
