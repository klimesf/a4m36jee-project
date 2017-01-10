package cz.cvut.fel.a4m36jee.airlines.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author klimefi1
 */
@Entity
public class Flight {

    @Id
    private Long id;

    private Date dateOfDeparture;

    @NotNull
    private Double distance;

    @NotNull
    private Double price;

    @NotNull
    private Integer seats;

    @NotEmpty
    private String name;

    @NotNull
    @ManyToOne
    private Destination from;

    @NotNull
    @ManyToOne
    private Destination to;

}
