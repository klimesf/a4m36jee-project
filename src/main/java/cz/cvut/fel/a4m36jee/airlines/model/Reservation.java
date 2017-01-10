package cz.cvut.fel.a4m36jee.airlines.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author klimefi1
 */
public class Reservation extends AbstractEntity {

    @NotNull
    private Integer seats;

    @NotEmpty
    private String password;

    @NotNull
    private Date created;

}
