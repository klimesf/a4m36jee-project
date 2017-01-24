package cz.cvut.fel.a4m36jee.airlines.frontend;

import cz.cvut.fel.a4m36jee.airlines.frontend.utils.DriverHolder;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Test for login.
 *
 * @author slavion3
 */
public class LoginTest {

    private WebDriver driver;

    @Before
    public void setup() {
        this.driver = DriverHolder.driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        driver.get("http://localhost:8080/airlines/");
        driver.findElement(By.id("selectEntityForm:indexFlight")).click();

    }

    @Test
    public void startTest() {

        System.out.println("Login test");
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.id("login")).click();

    }
}
