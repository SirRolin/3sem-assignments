package week04.recycling.jUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import week04.recycling.config.HibernateConfig;
import week04.recycling.dao.DriverDAOImpl;
import week04.recycling.dao.IDriverDAO;
import week04.recycling.dao.IWasteTruckDAO;
import week04.recycling.dao.WasteTruckDAOImpl;
import week04.recycling.model.Driver;
import week04.recycling.model.WasteTruck;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WasteTruckTest {


    private static IWasteTruckDAO dao;
    private static int createdTruckID;
    @BeforeAll
    static void beforeAll(){
        HibernateConfig.isDevState = true;
        dao = new WasteTruckDAOImpl();
        createdTruckID = dao.saveWasteTruck("sad", "sadas", 12);
    }

    @Test
    void saveWasteTruck() {
        int wtnum = dao.saveWasteTruck("sad", "sadas", 12);
        assertTrue(wtnum >= 0);
        WasteTruck wt = dao.getWasteTruckById(wtnum);
        Assertions.assertNotNull(wt);
    }

    @Test
    void getWasteTruckById() {
        WasteTruck wt = dao.getWasteTruckById(createdTruckID);
        Assertions.assertNotNull(wt);
    }

    @Test
    void setWasteTruckAvailable() {
        int wtnum = dao.saveWasteTruck("sad", "sadas", 12);
        WasteTruck wt = dao.getWasteTruckById(wtnum);
        dao.setWasteTruckAvailable(wt, true);
        assertTrue(dao.getWasteTruckById(wtnum).is_available());
    }

    @Test
    void addDriverToWasteTruck() {
        int wtnum = dao.saveWasteTruck("sap", "brannigan", 2);
        WasteTruck wt = dao.getWasteTruckById(wtnum);
        Driver driv = new Driver("name1","surname1", BigDecimal.valueOf(15));
        dao.addDriverToWasteTruck(wt, driv);
    }

    @Test
    void deleteWasteTruck() {
        int wtnum = dao.saveWasteTruck("sap2", "brannigan2", 3);
        WasteTruck wt = dao.getWasteTruckById(wtnum);
        dao.deleteWasteTruck(wtnum);
        Assertions.assertNull(dao.getWasteTruckById(wtnum));
    }

    @Test
    void removeDriverFromWasteTruck() {
        int wtnum = dao.saveWasteTruck("sad3", "sadas3", 12);
        WasteTruck wt = dao.getWasteTruckById(wtnum);
        IDriverDAO driverDao = new DriverDAOImpl();
        String drivId = driverDao.saveDriver("herrald","mandate", BigDecimal.valueOf(15));
        Driver driv = driverDao.getDriverById(drivId);
        dao.addDriverToWasteTruck(wt, driv);
        assertFalse(wt.is_available());
        Assertions.assertNotNull(driv.getId());
        dao.removeDriverFromWasteTruck(wt, driv.getId());
        assertTrue(wt.is_available());
    }

    @Test
    void getAllAvailableTrucks() {
        List<WasteTruck> wts = dao.getAllAvailableTrucks();
        assertFalse(wts.isEmpty());
        assertTrue(wts.stream().allMatch(WasteTruck::is_available));
    }
}
