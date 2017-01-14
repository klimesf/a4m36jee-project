package cz.cvut.fel.a4m36jee.airlines.batch;

import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reader part of the CSV import batch job.
 *
 * @author Ondřej Kratochvíl
 */
@Named
public class CsvImportReader extends AbstractItemReader {

    @Inject
    private Logger logger;
    private BufferedReader reader;

    @Override
    public Object readItem() throws Exception {
        logger.info("[Flights CSV Import] Reading line from CSV");
        try {
            return reader.readLine();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading CSV", e);
        }
        return null;
    }

    @Override
    public void open(Serializable checkpoint) throws Exception {
        reader = new BufferedReader(
                new InputStreamReader(
                        this.getClass().getClassLoader().getResourceAsStream("/META-INF/flightsImport.csv")
                )
        );
    }
}
