package com.siem.SecurityEvent;

import java.net.InetAddress;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SecurityEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int EventID;

    private String eventType;
    private String username;
    private String ipAddress;
    private String message;
    private LocalDateTime timestamp;
    private String country;
    private boolean deleted;

    @PrePersist
    private void setTime(){
        timestamp = LocalDateTime.now();
    }

}