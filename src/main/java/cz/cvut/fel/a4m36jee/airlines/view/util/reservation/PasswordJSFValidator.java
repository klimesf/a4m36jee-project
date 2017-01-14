package cz.cvut.fel.a4m36jee.airlines.view.util.reservation;

import cz.cvut.fel.a4m36jee.airlines.model.Reservation;
import cz.cvut.fel.a4m36jee.airlines.service.ReservationService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

/**
 * Custom JSF validator for reservation password - delete operation.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "passwordJSFValidator")
public class PasswordJSFValidator implements Validator {

    @Inject
    ReservationService reservationService;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if(o == null) {
            return;
        }
        String pass = (String) o;
        String idString = (String) uiComponent.getAttributes().get("reservationId");
        Integer id = Integer.parseInt(idString);
        if(id == null) {
            FacesMessage facesMsg = new FacesMessage("Reservation not found!");
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
        }
        Reservation reservation = reservationService.get(id);
        if(reservation == null) {
            FacesMessage facesMsg = new FacesMessage("Reservation not found!");
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
        }
        if(!reservation.getPassword().equals(pass)){
            FacesMessage facesMsg = new FacesMessage("Wrong password!");
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);

        }

    }

}
