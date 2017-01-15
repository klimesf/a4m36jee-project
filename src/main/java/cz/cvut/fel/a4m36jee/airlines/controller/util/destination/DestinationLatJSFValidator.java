package cz.cvut.fel.a4m36jee.airlines.controller.util.destination;

import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Custom JSF validator for destination latitude.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "destinationLatJSFValidator")
public class DestinationLatJSFValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (o == null) {
            return;
        }
        double lat = (double) o;
        if (lat > 90) {
            Messages.add(FacesMessage.SEVERITY_ERROR, "createDestinationLat", "Invalid Latitude! Latitude is greater than allowable maximum of 90.");
            return;
        }
        if (lat < -90) {
            Messages.add(FacesMessage.SEVERITY_ERROR, "createDestinationLat", "Invalid Latitude! Latitude is less than allowable minimum of -90.");
        }
    }
}
