package cz.cvut.fel.a4m36jee.airlines.controller.util.reservation;

import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Custom JSF validator for reservation password.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "reservationPasswordJSFValidator")
public class ReservationPasswordJSFValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if (o == null) {
            return;
        }
        String password = (String) o;
        if (password.length() < 6) {
            Messages.add(FacesMessage.SEVERITY_ERROR, "createReservationPassword", "Invalid length of password! Length is less than allowable minimum of 6.");
            return;
        }
        if (password.length() > 50) {
            Messages.add(FacesMessage.SEVERITY_ERROR, "createReservationPassword", "Invalid length of password! Length is greater than allowable maximum of 50.");
        }
    }
}
