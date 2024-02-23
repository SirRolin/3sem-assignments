package week04.gls_tracking_system;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.*;
import week03.gls_tracking_system.Package;
import week03.gls_tracking_system.PackageDAO;
import week03.gls_tracking_system.Status;

public class PackageDAOUnitTest {
    private final static week03.gls_tracking_system.PackageDAO pDao = new PackageDAO();
    private static week03.gls_tracking_system.Package staticPack;
    private static week03.gls_tracking_system.Package packageToCleanUp;

    @BeforeAll
    public static void setupAll(){
        staticPack = new week03.gls_tracking_system.Package();
        staticPack.setTrackingNumber(Integer.toString(staticPack.hashCode()));
        pDao.create(staticPack);
    }

    @AfterAll
    public static void removeStatic(){
        pDao.delete(staticPack.getTrackingNumber());
        if(packageToCleanUp != null) {
            pDao.delete(packageToCleanUp.getTrackingNumber());
            packageToCleanUp = null;
        }
        staticPack = null;
    }

    @Test
    public void create(){
        packageToCleanUp = new week03.gls_tracking_system.Package();
        packageToCleanUp.setTrackingNumber(Integer.toString(packageToCleanUp.hashCode()));
        packageToCleanUp.setDeliveryStatus(Status.DELIVERED);
        Assertions.assertDoesNotThrow(() -> pDao.create(packageToCleanUp));
        week03.gls_tracking_system.Package failingPackage = new week03.gls_tracking_system.Package();
        Assertions.assertDoesNotThrow(() -> pDao.create(failingPackage));
    }
    @Test
    public void findPackage() {
        week03.gls_tracking_system.Package trackedPackage = pDao.findPackage(staticPack.getTrackingNumber());
        Assertions.assertEquals(staticPack.getId(), trackedPackage.getId());
        Assertions.assertEquals(staticPack.getTrackingNumber(), trackedPackage.getTrackingNumber());
    }

    @Test
    public void update() {
        Assertions.assertNotNull(pDao.update(staticPack));
    }

    @Test
    public void delete() {
        week03.gls_tracking_system.Package localPack = new Package();
        localPack.setTrackingNumber(Integer.toString(localPack.hashCode()));
        pDao.create(localPack);
        Assertions.assertNotNull(pDao.findPackage(localPack.getTrackingNumber()));
        pDao.delete(localPack.getTrackingNumber());
        Assertions.assertThrows(NoResultException.class, () -> pDao.findPackage(localPack.getTrackingNumber()));
    }

}
