package week01.opgave3;

public class Employee {
    public String name;
    public int age;
    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + " age " + age;
    }
}
