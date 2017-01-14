package cz.cvut.fel.a4m36jee.airlines.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author klimefi1
 */
@Entity
@ManagedBean(name = "reservation")
public class Reservation extends AbstractEntity {

    @NotNull
    private Integer seat;

    @NotEmpty
    private String password;

    @NotNull
    private Date created;

    @ManyToOne
    private Flight flight;


    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seats) {
        this.seat = seats;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreated() {
        return new Date(created.getTime());
    }

    public void setCreated(Date created) {
        this.created = new Date(created.getTime());
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

}
