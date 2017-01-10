package cz.cvut.fel.a4m36jee.airlines.event;

import cz.cvut.fel.a4m36jee.airlines.model.Destination;

/**
 * @author klimefi1
 */
public class DestinationCreated {

    private Destination destination;

    public DestinationCreated(Destination destination) {
        this.destination = destination;
    }

    public Destination getDestination() {
        return destination;
    }

}
