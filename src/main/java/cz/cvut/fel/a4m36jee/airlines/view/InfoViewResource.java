package cz.cvut.fel.a4m36jee.airlines.view;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * View resources for application information.
 *
 * @author slavion3
 */
@RequestScoped
@ManagedBean(name = "infoViewResource")
public class InfoViewResource {
    //TODO add API resources, connect to service, count of user via websockets
    /**
     * Logger.
     */
    @Inject
    private Logger logger;

    public int getNumberOfConnectedUsers() {
        logger.info("Getting count of connected users.");
        return 1;
    }

}
