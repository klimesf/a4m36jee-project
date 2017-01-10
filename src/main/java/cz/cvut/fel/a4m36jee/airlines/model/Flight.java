package cz.cvut.fel.a4m36jee.airlines.model;

import javax.persistence.Entity;
import javax.persistence.Id;
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

    private Double distance;

    private Double price;

    private Integer seats;

    private String name;

    private Destination from;

    private Destination to;

    private List<Reservation> reservations = new ArrayList<>();

}
