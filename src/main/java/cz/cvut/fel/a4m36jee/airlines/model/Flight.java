package cz.cvut.fel.a4m36jee.airlines.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue
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

    public Long getId() {
        return id;
    }

    public Date getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(Date dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Destination getFrom() {
        return from;
    }

    public void setFrom(Destination from) {
        this.from = from;
    }

    public Destination getTo() {
        return to;
    }

    public void setTo(Destination to) {
        this.to = to;
    }

}
