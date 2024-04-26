package week03.lombok;

import org.junit.jupiter.api.Assertions;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("John", "Doe", 25);
        System.out.println(person); // This should print something like "Person(firstName=John, lastName=Doe, age=25)"

        person.setAge(26);
        Assertions.assertEquals(26, person.getAge());

        System.out.println(Person.builder().age(20).firstName("banjo").lastName("baggins").build());
    }
}
