package cz.cvut.fel.a4m36jee.airlines.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * @author klimefi1
 */
@Entity
public class Destination extends AbstractEntity {

    @NotEmpty
    private String name;

    @NotNull
    private Double lat;

    @NotNull
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
