package cz.cvut.fel.a4m36jee.airlines.view.util.flight;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import java.util.Date;

/**
 * JSF converter for flight date of departure.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "flightDateJSFConverter")
public class FlightDateJSFConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(s != null) {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");
            DateTime dateTime = null;
            try {
                dateTime = dtf.parseDateTime(s);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            if (dateTime != null) {
                return dateTime.toDate();
            }
            Messages.add(FacesMessage.SEVERITY_ERROR, "createFlightDateOfDeparture", "Invalid date format! Use ISO8601.");
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        Date date = (Date) o;
        return date.toString();
    }
}
