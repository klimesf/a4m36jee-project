package cz.cvut.fel.a4m36jee.airlines.controller.util.flight;

import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.service.DestinationService;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

/**
 * JSF converter for flight destination.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "flightDestinationJSFConverter")
public class FlightDestinationJSFConverter implements Converter {

    @Inject
    DestinationService destinationService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null) {
            return new Destination();
        }

        Long id = Long.parseLong(s);
        Destination destination = destinationService.get(id);
        if (destination == null) {
            Messages.add(FacesMessage.SEVERITY_ERROR, "", "Destination not found!");
            return null;
        }

        return destination;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Destination destination = (Destination) o;
        return destination.getId().toString();
    }

}
