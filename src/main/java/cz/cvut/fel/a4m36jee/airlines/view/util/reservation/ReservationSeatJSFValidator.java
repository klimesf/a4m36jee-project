package cz.cvut.fel.a4m36jee.airlines.view.util.reservation;

import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;
import cz.cvut.fel.a4m36jee.airlines.service.FlightService;
import cz.cvut.fel.a4m36jee.airlines.service.ReservationService;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import java.util.List;

/**
 * Custom JSF validator for reservation seat.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "reservationSeatJSFValidator")
public class ReservationSeatJSFValidator implements Validator {

    @Inject
    FlightService flightService;

    @Inject
    ReservationService reservationService;

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if(o == null) {
            return;
        }
        Integer seatNumber = (Integer) o;
        Long id = (Long) uiComponent.getAttributes().get("flightId");
        if(id != null) {
            Flight flight = flightService.get(id);
            if(flight == null) {
                Messages.add(FacesMessage.SEVERITY_ERROR, "createReservationSeat", "Invalid flight! Flight not found.");
            }
            if(flight.getSeats() < seatNumber || seatNumber <= 0){
                Messages.add(FacesMessage.SEVERITY_ERROR, "createReservationSeat", "Invalid seat number! Seat number is allowable between 1 - " + flight.getSeats() +".");
            }
            List<Reservation> reservationList = reservationService.listByFlightId(id);
            for (Reservation reservation : reservationList) {
                if(reservation.getSeat() == seatNumber){
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seat is already reserved.",""));
                }
            }
        } else {
            Messages.add(FacesMessage.SEVERITY_ERROR, "createReservationSeat", "Invalid flight! Flight not found.");
        }
    }

}
