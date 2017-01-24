package cz.cvut.fel.a4m36jee.airlines.frontend;

import cz.cvut.fel.a4m36jee.airlines.frontend.utils.DriverHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Test for reservations.
 *
 * @author slavion3
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReservationTest {

    private WebDriver driver;

    private String password = "password";
    private String seat = "96";

    @Before
    public void setup() {
        this.driver = DriverHolder.driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void createReservationTest() {
        System.out.println("Create reservation test - ".concat(seat));

        driver.findElement(By.id("listFlightsForm:reservationsListFlightsBtn")).click();
        driver.findElement(By.id("listReservationFlightsForm:createReservationListFlightsBtn")).click();
        driver.findElement(By.id("createReservationForm:createReservationSeat")).sendKeys(seat);
        driver.findElement(By.id("createReservationForm:createReservationPassword")).sendKeys(password);
        driver.findElement(By.id("createReservationForm:createReservationBtn")).click();

        List<WebElement> webElementList = driver.findElements(By.cssSelector("table tr"));
        boolean successfullyAdded = false;
        for(WebElement element : webElementList){
            if(element.getText().contains(seat)){
                successfullyAdded = true;
            }
        }
        Assert.assertTrue(successfullyAdded);
    }

    @Test
    public void deleteReservationTest() {
        System.out.println("Delete reservation test - ".concat(seat));

        List<WebElement> webElementList = driver.findElements(By.cssSelector("table tr"));
        int id = -1;
        for(WebElement element : webElementList){
            if(element.getText().contains(seat)){
                driver.findElement(By.id("listReservationFlightsForm:reservationFlightsTableListFlights:"+id+":deleteReservationListFlightsBtn")).click();
                break;
            }
            id++;
        }

        driver.findElement(By.id("deleteReservationForm:deleteReservationPassword")).sendKeys(password);
        driver.findElement(By.id("deleteReservationForm:deleteReservationBtn")).click();

        List<WebElement> webElementList2 = driver.findElements(By.cssSelector("table tr"));
        boolean successfullyRemoved = true;
        for(WebElement element : webElementList2){
            if(element.getText().contains(seat)){
                successfullyRemoved = false;
            }
        }

        Assert.assertTrue(successfullyRemoved);
    }

}
