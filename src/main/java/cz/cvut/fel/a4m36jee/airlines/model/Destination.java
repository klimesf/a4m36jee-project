package cz.cvut.fel.a4m36jee.airlines.model;

import cz.cvut.fel.a4m36jee.airlines.model.validation.Latitude;
import cz.cvut.fel.a4m36jee.airlines.model.validation.Longitude;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;

/**
 * @author klimefi1
 */
@Entity
public class Destination extends AbstractEntity {

    @NotEmpty
    private String name;

    @Latitude
    private Double lat;

    @Longitude
    private Double lon;

    public Destination() {
    }

    public Destination(String name, Double lat, Double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

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
