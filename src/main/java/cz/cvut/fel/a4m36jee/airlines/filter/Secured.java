package cz.cvut.fel.a4m36jee.airlines.filter;

import javax.ws.rs.NameBinding;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation for specifying secured API endpoints.
 *
 * @author Ondřej Kratochvíl
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Secured {

}