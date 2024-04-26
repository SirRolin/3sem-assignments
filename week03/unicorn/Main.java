package week03.unicorn;

import week03.unicorn.dao.UnicornDAO;

public class Main {
    public static void main(String[] args) {
        UnicornDAO dao = new UnicornDAO();

        // Create
        Unicorn newUnicorn = new Unicorn("Twilight Sparkle", 18, 9001);
        Unicorn createdUnicorn = dao.save(newUnicorn);

        // Read
        Unicorn foundUnicorn = dao.findByID(createdUnicorn.getId());
        System.out.println("Found Unicorn: " + foundUnicorn.getName());

        // Update
        int oldAge = foundUnicorn.getAge();
        foundUnicorn.setAge(21);
        Unicorn updatedUnicorn = dao.update(foundUnicorn);
        System.out.println("Updated Unicorn Age: " + oldAge + " to " + updatedUnicorn.getAge());
        System.out.println("Old objects Age: " + foundUnicorn.getAge());


        // Delete
        //System.out.println("Deleting Unicorn from database: " + dao.delete(createdUnicorn.getId()));

        // Get All
        System.out.println(dao.findAll());

        dao.close();
    }
}
