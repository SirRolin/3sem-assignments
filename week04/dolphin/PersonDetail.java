package week04.dolphin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PersonDetail
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String Address;
    private int zip;
    private String city;
    private int age;

    // Relationer 1:1

    @OneToOne
    @MapsId
    private Person person;

    public PersonDetail(String address, int zip, String city, int age)
    {
        Address = address;
        this.zip = zip;
        this.city = city;
        this.age = age;
    }

    @Override
    public String toString() {
        return id + ", " + person.getId() + " - " + Address + ", " + city + " " + zip + " - age: " + age;
    }
}
