package cz.cvut.fel.a4m36jee.airlines.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author klimefi1
 */
@Entity
public class Flight extends AbstractEntity {

    private Date date;

    @NotNull
    private Double price;

    @NotNull
    private Integer seats;

    @NotEmpty
    private String name;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Destination from;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Destination to;

    /**
     * Actual number of free seats on flight.
     *
     * Field is not persistent. It is loaded on get in service and updated each time
     * reservation is created.
     */
    @Transient
    private Integer freeSeats;

    public Date getDate() {
        return new Date(date.getTime());
    }

    public void setDate(Date date) {
        this.date = new Date(date.getTime());
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

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(final Integer freeSeats) {
        this.freeSeats = freeSeats;
    }
}
