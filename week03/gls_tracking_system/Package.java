package week03.gls_tracking_system;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@ToString
@Entity
public class Package {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String trackingNumber;

    private String senderName;

    private String receiverName;

    @Enumerated(EnumType.STRING)
    private Status deliveryStatus;

    //// for tracking last time updated or created
    @Setter(AccessLevel.NONE)
    private Timestamp last_updated;
    public Package() {
        deliveryStatus = Status.PENDING;
    }

    public Package(String trackingNumber, String senderName, String receiverName, Status deliveryStatus) {
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.deliveryStatus = deliveryStatus;
    }

    @PrePersist
    @PreUpdate
    private void preUpdate(){
        last_updated = Timestamp.from(Instant.now());
    }
}
