package cz.cvut.fel.a4m36jee.airlines.controller;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * View resources for application information.
 * TODO: connect to service, count of user via websockets
 *
 * @author slavion3
 */
@Model
public class InfoController {

    @Inject
    private Logger logger;

    public int getNumberOfConnectedUsers() {
        logger.info("Getting count of connected users.");
        return 1;
    }

}
