package week04.dolphin;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    // Relationer 1:1

    @OneToOne(mappedBy="person", cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private PersonDetail personDetail;

    public Person(String name)
    {
        this.name = name;
    }

    // Relationer 1:m

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private Set<Fee> fees = new HashSet<>();

    // Bi-directional update

    public void addPersonDetail(PersonDetail personDetail)
    {
        this.personDetail = personDetail;
        if (personDetail != null)
        {
            personDetail.setPerson(this);
        }
    }

    public Fee addFee(Fee fee)
    {
        this.fees.add(fee);
        if (fee != null)
        {
            fee.setPerson(this);
        }
        return fee;
    }

    // US-1
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "notee", cascade = CascadeType.ALL)
    private Set<Note> notes = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "noterer", cascade = CascadeType.ALL)
    private Set<Note> notesWritten = new HashSet<>();

    public Note addNote(Person writer, String note){
        Note writing = new Note();
        writing.setNote(note);
        writing.setNoterer(writer);
        writing.setNotee(this);
        notes.add(writing);
        return writing;
    }
}
