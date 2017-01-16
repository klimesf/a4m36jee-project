package cz.cvut.fel.a4m36jee.airlines.batch;

import cz.cvut.fel.a4m36jee.airlines.model.Flight;
import cz.cvut.fel.a4m36jee.airlines.service.FlightService;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Writer part of the CSV import batch job.
 *
 * @author kratoon
 */
@Named
public class CsvImportWriter extends AbstractItemWriter {

    @Inject
    private Logger logger;
    @Inject
    private FlightService flightService;

    @Override
    public void writeItems(List<Object> items) throws Exception {
        logger.info("[Flights CSV Import] Saving created Flight entities");
        if (items == null) {
            return;
        }
        for (Object item : items) {
            if (item == null) {
                continue;
            }
            Flight flight = (Flight) item;
            try {
                flightService.create(flight);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Cannot persist imported flight", e);
            }
        }
    }
}
