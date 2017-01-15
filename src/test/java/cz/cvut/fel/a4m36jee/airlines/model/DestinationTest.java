//package cz.cvut.fel.a4m36jee.airlines.model;
//
//import cz.cvut.fel.a4m36jee.airlines.UnitTest;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.experimental.categories.Category;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//
//
///**
// * @author klimefi1
// */
//@Category(UnitTest.class)
//public class DestinationTest {
//
//    private final List<Destination> valid = Arrays.asList(
//            new Destination("Valid name", -90., -180.),
//            new Destination("Valid name", 90., 180.)
//    );
//
//    private final List<Destination> invalid = Arrays.asList(
////            new Destination("Valid name", -91., 0.),
////            new Destination("Valid name", 91., 0.),
////            new Destination("Valid name", 0., -181.),
////            new Destination("Valid name", 0., 181.),
//            new Destination("", 0., 0.)
////            new Destination(null, 0., 0.)
//    );
//
//    @Test
//    public void testValidation() {
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//
//        // Test valid instances
//        for (Destination destination : valid) {
//            Set<ConstraintViolation<Destination>> violations = validator.validate(destination);
//            Assert.assertTrue(destination.toString(), violations.isEmpty());
//        }
//        // Test invalid instances
//        for (Destination destination : invalid) {
//            Set<ConstraintViolation<Destination>> violations = validator.validate(destination);
//            if (violations.isEmpty()) {
//                Assert.fail(destination.getName() + " " + destination.getLat() + " " + destination.getLon());
//            }
//            Assert.assertEquals(destination.toString(), 1, violations.size());
//        }
//    }
//
//}
