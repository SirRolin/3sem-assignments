package week04.dolphin;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DolphinDAOTest {

    static DolphinDAO dao;
    static Set<Person> startingPeople;
    static Set<Note> startingNotes;
    static Set<Fee> startingFees;

    @BeforeEach
    void beforeEach() {
        dao = new DolphinDAO(true);
        Person p1 = new Person("Galio");
        Person p2 = new Person("Avian");
        startingPeople = Set.of(p1, p2);
        p2.addPersonDetail(new PersonDetail("Algade 5", 4300, "Holbæk", 45));
        p1.addPersonDetail(new PersonDetail("Algade 5", 4300, "Holbæk", 42));

        startingPeople.forEach(dao::create);

        //// Adding a Fee to everyone
        //// US 2
        startingFees = startingPeople.stream()
                .map((p) -> p.addFee(new Fee(5, LocalDate.of(2002, 6, 29))))
                .collect(Collectors.toSet());

        //// Add notes to everyone except Galio, Galio is the one adding the notes
        //// US 3
        startingNotes = startingPeople.stream()
                .map((p) -> p.addNote(p1, "this is a note"))
                .collect(Collectors.toSet());
    }

    @AfterEach
    void afterEach() {
        dao.emf.close();
    }

    @Test
    void create() {
        int people = dao.listAllPeople().size();
        dao.create(new Person("TheFatRat"));
        Assertions.assertEquals(people + 1, dao.listAllPeople().size());
    }

    @Test
    void findPerson() {
        Assertions.assertEquals("Avian", dao.findPerson("Avian").getName());
    }

    @Test
    void update() {
        Person avian = dao.findPerson("Avian");
        Assertions.assertEquals("Avian", avian.getName());
        avian.setName("TheFatRat");
        dao.update(avian);
        Assertions.assertEquals("TheFatRat", dao.findPerson("TheFatRat").getName());
    }

    @Test
    void delete() {
        int people = dao.listAllPeople().size();
        dao.delete("Avian");
        Assertions.assertEquals(people - 1, dao.listAllPeople().size());
    }

    @Test
    void listAllPeople() {
        Assertions.assertEquals(startingPeople.size(), dao.listAllPeople().size());
    }

    @Test
    void getTotalFeesAmountByPerson() {
        Assertions.assertEquals(startingFees.stream()
                        .filter((p) -> Objects.equals(p.getPerson().getName(), "Avian"))
                        .mapToInt(Fee::getAmount).sum(),
                dao.getTotalFeesAmountByPerson(
                        dao.findPerson("Avian")));
    }

    @Test
    void getAllNotesOnPerson() {
        Set<Note> notes = dao.getAllNotesOnPerson(dao.findPerson("Avian"));
        Set<Note> fetchedNotes = startingNotes.stream()
                .filter((p) -> Objects.equals(p.getNotee().getName(), "Avian"))
                .collect(Collectors.toSet());
        Assertions.assertEquals(fetchedNotes.toString(), notes.toString());
        System.out.println(fetchedNotes);
        System.out.println(notes);
        System.out.println(startingNotes);
    }

    @Test
    void getAllNotesWithDetails(){
        System.out.println(dao.getAllNotesWithDetail());
        System.out.println(startingPeople);
    }
}