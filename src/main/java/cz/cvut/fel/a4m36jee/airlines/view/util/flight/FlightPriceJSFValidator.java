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
@ManagedBean(name = "flightPriceJSFValidator")
public class FlightPriceJSFValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if(o != null){
            Double price = (Double) o;
            if(price < 1) {
                Messages.add(FacesMessage.SEVERITY_ERROR, "createFlightPrice", "Invalid price! Length is less than allowable minimum of 1.");
            }
            if(price > Double.MAX_VALUE) {
                Messages.add(FacesMessage.SEVERITY_ERROR, "createFlightPrice", "Invalid prive! Length is greater than allowable maximum of " + Double.MAX_VALUE + ".");
            }
        }
    }

}
