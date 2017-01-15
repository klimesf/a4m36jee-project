package cz.cvut.fel.a4m36jee.airlines.controller;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * View resources for BATCH import.
 *
 * @author slavion3
 */
@Model
public class ImportDataController {

    @Inject
    private Logger logger;

    @Inject
    private FacesContext facesContext;

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
            FacesMessage facesMsg = new FacesMessage("File not selected!");
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
        }
        Part file = (Part) value;
        if (file.getSize() > 4084) { //TODO vyřešit povolenou velikost
            FacesMessage facesMsg = new FacesMessage("File is too big! Allowable size of file is 1024.");
            facesMsg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(facesMsg);
        }
        if (!"application/vnd.ms-excel".equals(file.getContentType())) { //TODO vyřešit contentType
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
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        try {
            //TODO call import
            logger.info("File is imported.");
            response.sendRedirect("/airlines/");
        } catch (Exception e) { //TODO exception
            logger.severe("Error during import!");
            response.sendRedirect("/airlines/error/");
            throw new WebApplicationException("Error during import!");
        }
    }

    public Part getImportedFile() {
        return importedFile;
    }

    public void setImportedFile(Part importedFile) {
        this.importedFile = importedFile;
    }

}

