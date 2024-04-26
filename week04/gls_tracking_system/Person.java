package week04.gls_tracking_system;

import jakarta.persistence.*;
import jakarta.xml.bind.ValidationException;
import lombok.*;
import week03.gls_tracking_system.Status;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@ToString
@Entity
public class Person {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String trackingNumber;

    private String senderName;

    private String receiverName;

    @Enumerated(EnumType.STRING)
    private Status deliveryStatus;

    //// for tracking time created
    @Setter(AccessLevel.NONE)
    private Timestamp created;

    //// for tracking last time updated
    @Setter(AccessLevel.NONE)
    private Timestamp last_updated;
    public Person() {
        deliveryStatus = Status.PENDING;
    }

    public Person(String trackingNumber, String senderName, String receiverName, Status deliveryStatus) {
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.deliveryStatus = deliveryStatus;
    }

    @PrePersist
    private void prePersist() throws ValidationException {
        created = Timestamp.from(Instant.now());
    }
    @PreUpdate
    private void preUpdate() throws ValidationException {
        last_updated = Timestamp.from(Instant.now());
    }
}
