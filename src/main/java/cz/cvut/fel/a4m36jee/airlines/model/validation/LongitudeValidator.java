package cz.cvut.fel.a4m36jee.airlines.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author klimefi1
 */
public class LongitudeValidator implements ConstraintValidator<Longitude, Double> {
    @Override
    public void initialize(Longitude constraintAnnotation) {
        // Empty on purpose
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && value >= -180. && value <= 180.;
    }
}
