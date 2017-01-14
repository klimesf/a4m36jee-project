package cz.cvut.fel.a4m36jee.airlines.webSockets;

import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Web socket for flights.
 *
 * Web socket updates number of free seats on flight.
 *
 * It sends id of flight on which new reservation was created. Client itself determines, whether it is
 * actually viewed flight.
 *
 * @author moravja8.
 */
@Stateless
@ServerEndpoint("flightsWebsocket")
public class FlightWebSocket {

    private final Logger logger;

    private static final Set<Session> SESSIONS = Collections.synchronizedSet(new HashSet<Session>());

    @Inject
    public FlightWebSocket(final Logger logger) {
        this.logger = logger;
    }

    /**
     * Fires id of flight on which new reservation was created.
     * @param reservationCreated trigger
     */
    public void handleNewReservation(@Observes ReservationCreated reservationCreated) {
        final Reservation reservation = reservationCreated.getReservation();
        sendAll(reservation.getFlight().getId().toString());
    }

    /**
     * Sends message to all sessions.
     * @param message message
     */
    private static void sendAll(String message) {
        synchronized (SESSIONS) {
            for (Session session : SESSIONS) {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(message);
                }
            }
        }
    }

    /**
     * Consumes message sent from client.
     * No messages are expected.
     * @param message message
     */
    @OnMessage
    public void messageReceiver(String message) {
        logger.info("Web socket received message:" + message);
    }

    /**
     * Registers new session.
     * @param session session
     */
    @OnOpen
    public void onOpen(Session session) {
        logger.info("Websocket registered new session with id " + session.getId());
        SESSIONS.add(session);
        logger.info("Websocket notification list size" + SESSIONS.size());
    }
}
