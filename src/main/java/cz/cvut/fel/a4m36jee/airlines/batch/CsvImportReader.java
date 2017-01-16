package cz.cvut.fel.a4m36jee.airlines.batch;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reader part of the CSV import batch job.
 *
 * @author kratoon
 */
@Named
public class CsvImportReader extends AbstractItemReader {

    @Inject
    private Logger logger;
    @Inject
    private JobContext jobContext;
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
        Properties runtimeParams = BatchRuntime.getJobOperator().getParameters(jobContext.getExecutionId() );
        InputStream is = ((InputStream) runtimeParams.get("is"));
        reader = new BufferedReader(new InputStreamReader(is));
    }
}
