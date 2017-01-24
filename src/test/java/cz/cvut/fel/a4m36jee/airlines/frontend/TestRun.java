package cz.cvut.fel.a4m36jee.airlines.frontend;

import cz.cvut.fel.a4m36jee.airlines.frontend.utils.DriverHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.transaction.Transactional;


@RunWith(Suite.class)
@Transactional
@Suite.SuiteClasses({
        LoginTest.class,
        FlightTest.class,
        ReservationTest.class,
        DestinationTest.class,
        BatchImportTest.class,
        LogoutTest.class
})
public class TestRun {

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", DriverHolder.CHROME_DRIVER_PATH);
        DriverHolder.driver = new ChromeDriver();
    }


    @AfterClass
    public static void tearDown() {
        DriverHolder.driver.quit();
    }
}