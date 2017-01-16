package cz.cvut.fel.a4m36jee.airlines.jms;

import cz.cvut.fel.a4m36jee.airlines.service.ReservationService;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JMS consumer for receiving messages from the queue.
 *
 * @author Ondřej Kratochvíl
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "reservationQueue")
})
public class MessageConsumer implements MessageListener {

    @Inject
    private Logger logger;
    @Inject
    private ReservationService reservationService;

    @Override
    public void onMessage(Message message) {
        if (message instanceof MapMessage) {
            MapMessage mapMessage = (MapMessage) message;
            try {
                sendEmail(mapMessage);
            } catch (JMSException e) {
                logger.log(Level.SEVERE, "Cannot retrieve text from the message", e);
            }
        } else {
            logger.log(Level.WARNING,
                    "Incorrect message type, expected: javax.jms.MapMessage, received: " + message.getClass());
        }
    }

    /**
     * Sends an email summarising the reservation. The behaviour is mocked and the email body is only printed
     * to stdout.
     *
     * @param mapMessage polled message from the queue
     */
    private void sendEmail(MapMessage mapMessage) throws JMSException {
        int seat = mapMessage.getInt("seat");
        double price = mapMessage.getDouble("price");
        String from = mapMessage.getString("from");
        String to = mapMessage.getString("to");
        String date = mapMessage.getString("date");
        StringBuilder sb = new StringBuilder();
        sb.append("\nDear user,\nyour reservation was successfully completed. Flight summary:\n\n")
                .append("From: ").append(from).append("\n")
                .append("To: ").append(to).append("\n")
                .append("Seat no.: ").append(seat).append("\n")
                .append("Departure: ").append(date).append("\n")
                .append("Total price: ").append(price).append("\n\n")
                .append("Thank you for using FEL airlines!");
        // the email should be sent now
        System.out.println(sb.toString());
    }
}
