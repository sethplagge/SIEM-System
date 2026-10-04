package com.siem.Alert;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int AlertID;

    private String eventType;
    private String severity;
    private String notes;
    private String ipAddress;
    private LocalDateTime timestamp;
    private boolean resolved;

    @PrePersist
    private void setTime(){
        timestamp = LocalDateTime.now();
    }

    public Alert(String eventType, String severity, String notes, String ipAddress, boolean resolved) {
        this.eventType = eventType;
        this.severity = severity;
        this.notes = notes;
        this.ipAddress = ipAddress;
        this.resolved = resolved;
    }

}