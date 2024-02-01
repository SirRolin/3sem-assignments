package week01.opgave4;

import week01.opgave3.Employee;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.*;

public class Main {

    //// for at kunne generere tilfældige employees

    //// opgave 2

    public static void main(String[] args) {
        EmployeeWithDates james = new EmployeeWithDates("James's", LocalDate.now().minusYears(1).minusDays(-1));
        EmployeeWithDates boris = new EmployeeWithDates("James's older brother", LocalDate.now().minusYears(2).minusDays(1));

        //// Opgave 1
        //// en generator til random employeees
        Function<String, EmployeeWithDates> generateEmployee = (name) -> {
            Random rng = new Random();
            LocalDate date = LocalDate.now().minusYears(rng.nextInt(115)).minusDays(rng.nextInt(365));
            return new EmployeeWithDates(name, date);
        };
        //// udregningerne er i employeesWithDates
        //// Showing that the age is calculated in EmployeeWithDates
        System.out.println(james);
        System.out.println(boris);


        ArrayList<EmployeeWithDates> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(james);
        listOfEmployees.add(boris);
        listOfEmployees.add(generateEmployee.apply("Molly"));
        listOfEmployees.add(generateEmployee.apply("Jasper"));

        //// Opgave 2
        Function<List<EmployeeWithDates>, Float> getAvgAge = (list) -> {
            float avg = 0;
            int count = 1;
            for (Employee emp: list) {
                switch(count){
                    case 1 -> {
                        avg = emp.age;
                    }
                    default -> {
                        //// an old formula I made to calculate average WITHOUT getting integer overflows even with millions of items in the list.
                        avg += (emp.age - avg) / count;
                    }
                }
                count++;
            }
            return avg;
        };
        System.out.println(getAvgAge.apply(listOfEmployees));


        //// Opgave 3 & 4
        BiFunction<ArrayList<EmployeeWithDates>, Integer, ArrayList<EmployeeWithDates>> getEmployeesAtMonth = (emp, month) -> {
            ArrayList<EmployeeWithDates> result = new ArrayList<>();
            for (EmployeeWithDates e: emp) {
                if(e.birthday.getMonth().getValue() == month) {
                    result.add(e);
                }
            }
            return result;
        };
        //// prints all months out so we can see them grouped
        for (int i = 0; i < 12; i++) {
            getEmployeesAtMonth.andThen((e) -> {
                if(e.size()>0)
                    System.out.println("employees in month: " + e.get(0).birthday.getMonth());
                e.forEach(System.out::println);
                return e.size();
            }).apply(listOfEmployees, i);
        }

        //// Opgave 5
        Function<ArrayList<EmployeeWithDates>, ArrayList<EmployeeWithDates>> thisMonth = (list) ->
                getEmployeesAtMonth.apply(list, LocalDate.now().getMonth().getValue());

        ArrayList<EmployeeWithDates> thisMonthsEmployees = thisMonth.apply(listOfEmployees);
        if(!thisMonthsEmployees.isEmpty()){
            System.out.println("This month - " + LocalDate.now().getMonth() + ":");
            thisMonthsEmployees.forEach(System.out::println);
        }

    }

}
