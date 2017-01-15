package cz.cvut.fel.a4m36jee.airlines.batch;

import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.service.DestinationService;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Inject;
import javax.inject.Named;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Processor part of the CSV import batch job.
 *
 * @author Ondřej Kratochvíl
 */
@Named
public class CsvImportProcessor implements ItemProcessor {

    @Inject
    private Logger logger;
    @Inject
    private DestinationService destinationService;

    private final static String DATE_FORMAT = "d-MM-yyyy HH:mm";

    @Override
    public Flight processItem(Object item) throws Exception {
        logger.info("[Flights CSV Import] Creating new Flight entity");
        if (item == null) {
            return null;
        }
        try {
            StringTokenizer tokens = new StringTokenizer((String) item, ",");
            Flight flight = new Flight();
            flight.setDate(new SimpleDateFormat(DATE_FORMAT).parse(tokens.nextToken()));
            flight.setPrice(Double.valueOf(tokens.nextToken()));
            flight.setSeats(Integer.valueOf(tokens.nextToken()));
            flight.setName(tokens.nextToken());
            List<Destination> fromDestinations = destinationService.findByName(tokens.nextToken());
            if (fromDestinations == null || fromDestinations.isEmpty()) {
                return null;
            }
            flight.setFrom(fromDestinations.get(0));
            List<Destination> toDestinations = destinationService.findByName(tokens.nextToken());
            if (toDestinations == null || toDestinations.isEmpty()) {
                return null;
            }
            flight.setTo(toDestinations.get(0));
            return flight;
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("Cannot create new Flight entity from line '%s'", (String) item), e);
        }
        return null;
    }
}
