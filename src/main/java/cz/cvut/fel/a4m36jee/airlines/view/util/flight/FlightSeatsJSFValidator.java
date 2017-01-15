package cz.cvut.fel.a4m36jee.airlines.view.util.flight;

import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Custom JSF validator for destination name.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "flightSeatsJSFValidator")
public class FlightSeatsJSFValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if(o != null){
            Integer count = (Integer) o;
            if(count < 1) {
                Messages.add(FacesMessage.SEVERITY_ERROR, "createFlightSeats", "Invalid count of seats! Seats count is less than allowable minimum of 1.");
            }
            if(count > 255) {
                Messages.add(FacesMessage.SEVERITY_ERROR, "createFlightSeats", "Invalid count of seats! Seats count is greater than allowable maximum of 500.");
            }
        }
    }

}
