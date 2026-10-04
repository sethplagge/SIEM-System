package com.securitymonitor;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Integer>{

    List<SecurityEvent> findByDeletedFalse();

}
