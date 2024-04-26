package week03.DAO_Exercise;

import org.junit.jupiter.api.Assertions;
import week03.student.Student;

public class Main {
    public static void main(String[] args) {
        studentDAO sdao = new studentDAO();
        Student st1 = new Student();
        st1.setEmail("testmail3@hotmail.com");

        //// Create
        sdao.create(st1);

        //// Read
        Student st1Copy = sdao.read(st1.getId());
        Assertions.assertNotNull(st1Copy);

        st1.setAge(99);

        //// Update
        sdao.update(st1);

        //// Delete
        sdao.delete(st1.getId());


        //// Question: Explain the benefits of using a DAO architecture for separating database access logic from business logic.
        //// Answer: For ease of UnitTesting, For making it clear what code's for the Database and what's for the program as well
        //// as being able to change Database without changing anything in the programs code.

    }
}
