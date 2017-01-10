package cz.cvut.fel.a4m36jee.airlines.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author klimefi1
 */
@Entity
public class Destination {

    @Id
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

}
