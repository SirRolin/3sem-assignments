package week01.opgave4;

import week01.opgave3.Employee;

import java.time.*;

public class EmployeeWithDates extends Employee {
    public java.time.LocalDate birthday;
    public EmployeeWithDates(String name, LocalDate birthday) {
        super(name, getAge(birthday));
        this.birthday = birthday;
        ////in case I want to test it
        //System.out.println("this zone " + ZonedDateTime.now());
        //System.out.println("birthday " + birthday.toInstant().atOffset(ZonedDateTime.now().getOffset()));
    }

    public static int getAge(LocalDate date) {
        LocalDate now = LocalDate.now();
        int result = (LocalDateTime.now().getYear() - date.getYear());
        Month nowMonth = now.getMonth();
        Month dateMonth = date.getMonth();
        switch(nowMonth.compareTo(dateMonth)) {
            case -1 -> {
                result -= 1;
            }
            case 0 -> {
                if (now.getDayOfMonth() < date.getDayOfMonth()){
                    result -= 1;
                }
            }
        }
        return result;
    }
}
