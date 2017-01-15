package cz.cvut.fel.a4m36jee.airlines.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author klimefi1
 */
public class LatitudeValidator implements ConstraintValidator<Latitude, Double> {
    @Override
    public void initialize(Latitude constraintAnnotation) {
        // Empty on purpose
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && value >= -90. && value <= 90.;
    }
}
