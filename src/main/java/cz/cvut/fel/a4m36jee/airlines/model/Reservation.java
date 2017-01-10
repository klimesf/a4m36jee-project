package cz.cvut.fel.a4m36jee.airlines.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author klimefi1
 */
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Integer seats;

    @NotEmpty
    private String password;

    @NotNull
    private Date created;

    @ManyToOne
    private Flight flight;

    public Long getId() {
        return id;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

}
