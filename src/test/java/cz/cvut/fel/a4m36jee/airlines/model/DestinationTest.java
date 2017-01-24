package cz.cvut.fel.a4m36jee.airlines.model;

import cz.cvut.fel.a4m36jee.airlines.TestUtils;
import cz.cvut.fel.a4m36jee.airlines.UnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;


/**
 * @author moravja8
 */
@Category(UnitTest.class)
@RunWith(Parameterized.class)
public class DestinationTest {

    private Boolean valid;
    private String name;
    private Double lat;
    private Double lon;

    @Parameterized.Parameters
    public static Collection<String[]> data() {
        return TestUtils.readCSVfileToCollection("src\\test\\resources\\testResources\\validatorImport.csv");
    }

    public DestinationTest(final String valid, final String name, final String lat, final String lon) {
        this.valid = valid.equals("valid");
        this.name = name;
        this.lat = Double.parseDouble(lat);
        this.lon = Double.parseDouble(lon);
    }

    @Test
    public void testValidation() {
        Destination destination = new Destination();
        destination.setLat(lat);
        destination.setLon(lon);
        destination.setName(name);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<Destination>> violations = validator.validate(destination);
        if(valid) {
            Assert.assertTrue(destination.toString(), violations.isEmpty());
        } else {
            if (violations.isEmpty()) {
                Assert.fail(destination.getName() + " " + destination.getLat() + " " + destination.getLon());
            }
            Assert.assertEquals(destination.toString(), 1, violations.size());
        }
    }
}
