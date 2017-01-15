package cz.cvut.fel.a4m36jee.airlines.view.util.destination;

import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Custom JSF validator for destination longitude.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "destinationLonJSFValidator")
public class DestinationLonJSFValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if(o != null){
            Double lon = (Double) o;
            if(lon > 180) {
                Messages.add(FacesMessage.SEVERITY_ERROR, "createDestinationLon", "Invalid longitude! Longitude is greater than allowable maximum of 180.");
            }
            if(lon < -180) {
                Messages.add(FacesMessage.SEVERITY_ERROR, "createDestinationLon", "Invalid longitude! Longitude is less than allowable minimum of -180.");
            }
        }
    }

}
