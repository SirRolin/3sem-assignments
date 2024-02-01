package week01.opgave3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {

public static void main(String[] args){
    List<String> randomNames =  Arrays.asList("John", "Jane", "Jack", "Joe", "Jill");
    ArrayList<Employee> listOfEmployees = new ArrayList<>();
    listOfEmployees.add(new Employee("Maria", 19));
    listOfEmployees.add(new Employee("Serafine", 64));
    listOfEmployees.add(new Employee("George", 41));
    listOfEmployees.add(new Employee("Morris", 4));

    //// first Predicate
    Predicate<Integer> divBySeven = a -> a % 7 == 0;
    System.out.println("Is 7 dividable by 7? " + (divBySeven.test(7) ? "true" : "false"));

    //// Supplier to create a random employee
    Supplier<Employee> generateNewEmployee = () -> {
        Random rng = new Random();
        Employee newEmp = new Employee(randomNames.get(rng.nextInt(randomNames.size())), rng.nextInt(115));
        listOfEmployees.add(newEmp);
        return newEmp;
    };
    System.out.println("Our New Employee is: " + generateNewEmployee.get());

    //// Consumer to print the list
    Consumer<List<Employee>> printEmployees = list -> list.forEach(System.out::println);
    System.out.println("List of employees: ");
    printEmployees.accept(listOfEmployees);
    System.out.println();

    //// Function to get Only the names
    Function<List<Employee>, List<String>> namesFromEmployeeList = list -> new ArrayList<>((list.stream().map(x -> x.name).collect(Collectors.toList())));
    System.out.println("Only the names: " + namesFromEmployeeList.apply(listOfEmployees));
    System.out.println();

    //// second Predicate to predict the employee age
    Predicate<Employee> isOlderThan18 = employee -> employee.age > 18;
    for (Employee e: listOfEmployees) {
        System.out.println("is " + e + " older than 18? " + isOlderThan18.test(e));
    }

}

}
