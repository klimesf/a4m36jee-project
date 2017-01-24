package cz.cvut.fel.a4m36jee.airlines.frontend;

import cz.cvut.fel.a4m36jee.airlines.frontend.utils.DriverHolder;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Test for destinations.
 *
 * @author slavion3
 */
public class DestinationTest {

    private WebDriver driver;

    private String destinationName = "Prague";

    @Before
    public void setup() {
        this.driver = DriverHolder.driver;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    public void detailDestination() {

        System.out.println("Detail destination ".concat(destinationName));

        driver.findElement(By.id("logoutListDestinationForm:homeReservationListFlightsBtn")).click();
        driver.findElement(By.id("selectEntityForm:indexDestination")).click();

        List<WebElement> webElementList = driver.findElements(By.cssSelector("table tr"));
        int id = -1;
        for(WebElement element : webElementList){
            if(element.getText().contains(destinationName)){
                driver.findElement(By.id("listDestinationsForm:destinationsTableListDestinations:"+id+":destinationDetailBtn")).click();
                break;
            }
            id++;
        }

    }

}
