package cz.cvut.fel.a4m36jee.airlines.frontend;

import cz.cvut.fel.a4m36jee.airlines.frontend.utils.DriverHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Test for Logout.
 *
 * @author slavion3
 */
public class LogoutTest {

    private WebDriver driver;

    @Before
    public void setup() {
        this.driver = DriverHolder.driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void logoutTest() {
        System.out.println("Logout test");

        driver.findElement(By.id("logoutListFlightsForm:logout")).click();
        Assert.assertEquals("http://localhost:8080/airlines/logout",driver.getCurrentUrl());
    }

}
