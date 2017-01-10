package cz.cvut.fel.a4m36jee.airlines.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author klimefi1
 */
@Entity
public class Destination {

    @Id
    private Long id;

    private String name;

    private Double lat;

    private Double lon;

}
