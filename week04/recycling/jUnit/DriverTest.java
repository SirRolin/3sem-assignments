package week04.recycling.jUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import week04.recycling.dao.DriverDAOImpl;
import week04.recycling.dao.IDriverDAO;
import week04.recycling.model.Driver;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DriverTest {
    @Test
    void testConstructor(){
        Driver wt = new Driver("Jon","Doe", BigDecimal.valueOf(20.1d));
        assertNotNull(wt.getId());
        System.out.println("id: " + wt.getId());
        assertTrue(validateDriverId(wt.getId()));
    }
    public Boolean validateDriverId(String driverId) {
        return driverId.matches("[0-9][0-9][0-9][0-9][0-9][0-9]-[A-Z][A-Z]-[0-9][0-9][0-9][A-Z]");
    }


    @Test
    void fetchDriverWithHighestSalary() {
        IDriverDAO dao = new DriverDAOImpl();
        dao.saveDriver("Jon","Doe", BigDecimal.valueOf(20.1d));
        dao.saveDriver("Jon","Doe", BigDecimal.valueOf(20.5d));
        Driver answer = dao.fetchDriverWithHighestSalary();
        Assertions.assertEquals(BigDecimal.valueOf(2050L,2), answer.getSalary());
    }
}
