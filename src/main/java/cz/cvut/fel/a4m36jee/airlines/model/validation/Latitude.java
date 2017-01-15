package cz.cvut.fel.a4m36jee.airlines.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author klimefi1
 */
@Documented
@Constraint(validatedBy = LatitudeValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, PARAMETER })
@Retention(RUNTIME)
public @interface Latitude {
    String message() default "{cz.cvut.fel.a4m36jee.airlines.model.validation.Latitude.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
