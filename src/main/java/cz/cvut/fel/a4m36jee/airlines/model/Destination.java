package cz.cvut.fel.a4m36jee.airlines.model;

import cz.cvut.fel.a4m36jee.airlines.model.validation.Latitude;
import cz.cvut.fel.a4m36jee.airlines.model.validation.Longitude;
import org.hibernate.validator.constraints.NotEmpty;

import javax.faces.bean.ManagedBean;
import javax.persistence.Entity;

/**
 * @author klimefi1
 */
@Entity
@ManagedBean(name = "destination")
public class Destination extends AbstractEntity {

    @NotEmpty(message="Name is absent!")
    private String name;

    @Latitude(message="Latitude is absent!")
    private Double lat;

    @Longitude(message="Longitude is absent!")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Destination that = (Destination) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (lat != null ? !lat.equals(that.lat) : that.lat != null) return false;
        return lon != null ? lon.equals(that.lon) : that.lon == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (lon != null ? lon.hashCode() : 0);
        return result;
    }
}
