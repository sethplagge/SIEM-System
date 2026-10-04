package com.securitymonitor;

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
    private String sourceIp;
    private String message;
    private LocalDateTime timestamp;
}