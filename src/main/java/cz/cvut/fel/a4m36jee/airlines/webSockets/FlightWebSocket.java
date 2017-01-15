package cz.cvut.fel.a4m36jee.airlines.webSockets;

import cz.cvut.fel.a4m36jee.airlines.event.ReservationCreated;
import cz.cvut.fel.a4m36jee.airlines.event.ReservationDeleted;
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
@ServerEndpoint("/newReservationWebSocket")
public class FlightWebSocket {

    @Inject
    private Logger logger;

    private static final Set<Session> SESSIONS = Collections.synchronizedSet(new HashSet<Session>());

    /**
     * Fires id of flight on which new reservation was created.
     * Message example: <pre>1-added</pre>
     * @param reservationCreated trigger
     */
    public void handleNewReservation(@Observes ReservationCreated reservationCreated) {
        final Reservation reservation = reservationCreated.getReservation();
        final String message = reservation.getFlight().getId().toString() + "-added";
        sendAll(message);
    }

    /**
     * Fires id of flight on which a reservation was deleted.
     * Message example: <pre>1-deleted</pre>
     * @param reservationDeleted trigger
     */
    public void handleDeletedReservation(@Observes ReservationDeleted reservationDeleted) {
        final Reservation reservation = reservationDeleted.getReservation();
        final String message = reservation.getFlight().getId().toString() + "-deleted";
        sendAll(message);
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
