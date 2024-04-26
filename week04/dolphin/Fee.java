package week04.dolphin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Fee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private int amount;
    private LocalDate payDate;

    @ManyToOne
    @MapsId
    private Person person;

    public Fee(int amount, LocalDate payDate)
    {
        this.amount = amount;
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return Id + ", " + person.getId() + " - " + amount + " on " + payDate.toString();
    }
}
