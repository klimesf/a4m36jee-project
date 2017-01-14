package cz.cvut.fel.a4m36jee.airlines.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author klimefi1
 */
@Entity
@ManagedBean(name = "destination")
public class Destination extends AbstractEntity {

    @NotEmpty(message="Name is absent!")
    private String name;

    @NotNull(message="Latitude is absent!")
    private Double lat;

    @NotNull(message="Longitude is absent!")
    private Double lon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

}
