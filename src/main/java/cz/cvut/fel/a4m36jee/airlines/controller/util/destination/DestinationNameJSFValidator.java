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
 * Custom JSF validator for destination name.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "destinationNameJSFValidator")
public class DestinationNameJSFValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (o == null) {
            return;
        }
        String name = (String) o;
        if (name.length() < 2) {
            Messages.add(FacesMessage.SEVERITY_ERROR, "createDestinationName", "Invalid length of name! Length is less than allowable minimum of 2.");
            return;
        }
        if (name.length() > 255) {
            Messages.add(FacesMessage.SEVERITY_ERROR, "createDestinationName", "Invalid length of name! Length is greater than allowable maximum of 255.");
        }
    }
}
