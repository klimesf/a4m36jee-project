package cz.cvut.fel.a4m36jee.airlines.view;

import javax.batch.operations.JobOperator;
import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;
import javax.batch.runtime.BatchRuntime;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * View resources for BATCH import.
 *
 * @author slavion3
 */
@ViewScoped
@ManagedBean(name = "importDataViewResource")
public class ImportDataViewResource {

    /**
     * Logger.
     */
    @Inject
    private Logger logger;

    /**
     * Imported file.
     */
    private Part importedFile;

    /**
     * CSV file validator.
     *
     * @param ctx   {@link FacesContext}
     * @param comp  {@link UIComponent}
     * @param value input File
     * @throws ValidatorException if file is not valid
     */
    public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
        if (value == null) {
            FacesMessage facesMsg = new FacesMessage("No file selected!");
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
        }
        Part file = (Part) value;
        if (file.getSize() > 4096) {
            FacesMessage facesMsg = new FacesMessage("File is too big! Maximum allowed size is 4096 bytes.");
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
        }
        if (!file.getContentType().matches("application/vnd\\.ms-excel|text/csv")) {
            FacesMessage facesMsg = new FacesMessage("Not a *.csv file!");
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
        }
    }

    /**
     * Import file.
     *
     * @throws WebApplicationException if error during import
     */
    public void importFile() throws IOException {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        try {
            JobOperator jobOperator = BatchRuntime.getJobOperator();
            Properties jobProperties = new Properties();
            jobProperties.put("is", getImportedFile().getInputStream());
            long jobId = jobOperator.start("importFlights", jobProperties);
            logger.info(String.format("Import job with id %d started.", jobId));
            response.sendRedirect("/airlines/");
        } catch (JobStartException | JobSecurityException e) {
            logger.severe("Error during import!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException("Error during import!", e);
        }
    }

    public Part getImportedFile() {
        return importedFile;
    }

    public void setImportedFile(Part importedFile) {
        this.importedFile = importedFile;
    }

}

