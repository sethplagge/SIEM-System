package com.siem.Alert;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Integer> {
    List<Alert> findByResolvedFalse();

    Optional<Alert> findByAlertTypeAndIpAddressAndResolved(
            String alertType,
            String ipAddress,
            boolean resolved
    );

}