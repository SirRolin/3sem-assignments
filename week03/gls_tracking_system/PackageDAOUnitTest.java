package week03.gls_tracking_system;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.*;

public class PackageDAOUnitTest {
    private final static PackageDAO pDao = new PackageDAO();
    private static Package staticPack;
    private static Package packageToCleanUp;

    @BeforeAll
    public static void setupAll(){
        staticPack = new Package();
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
        packageToCleanUp = new Package();
        packageToCleanUp.setTrackingNumber(Integer.toString(packageToCleanUp.hashCode()));
        packageToCleanUp.setDeliveryStatus(Status.DELIVERED);
        Assertions.assertDoesNotThrow(() -> pDao.create(packageToCleanUp));
        Package failingPackage = new Package();
        Assertions.assertDoesNotThrow(() -> pDao.create(failingPackage));
    }
    @Test
    public void findPackage() {
        Package trackedPackage = pDao.findPackage(staticPack.getTrackingNumber());
        Assertions.assertEquals(staticPack.getId(), trackedPackage.getId());
        Assertions.assertEquals(staticPack.getTrackingNumber(), trackedPackage.getTrackingNumber());
    }

    @Test
    public void update() {
        Assertions.assertNotNull(pDao.update(staticPack));
    }

    @Test
    public void delete() {
        Package localPack = new Package();
        localPack.setTrackingNumber(Integer.toString(localPack.hashCode()));
        pDao.create(localPack);
        Assertions.assertNotNull(pDao.findPackage(localPack.getTrackingNumber()));
        pDao.delete(localPack.getTrackingNumber());
        Assertions.assertThrows(NoResultException.class, () -> pDao.findPackage(localPack.getTrackingNumber()));
    }

}
