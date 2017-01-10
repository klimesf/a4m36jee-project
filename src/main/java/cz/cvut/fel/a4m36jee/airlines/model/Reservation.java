package cz.cvut.fel.a4m36jee.airlines.model;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author klimefi1
 */
public class Reservation {

    @Id
    private Long id;

    private Integer seats;

    private String password;

    private Date created;

}
