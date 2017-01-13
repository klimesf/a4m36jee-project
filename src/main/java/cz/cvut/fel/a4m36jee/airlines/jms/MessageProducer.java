package cz.cvut.fel.a4m36jee.airlines.jms;

import cz.cvut.fel.a4m36jee.airlines.model.Reservation;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JMS producer for sending messages to the queue.
 *
 * @author Ondřej Kratochvíl
 */
@Singleton
public class MessageProducer {

    private static final String RESERVATION_QUEUE_NAME = "reservationQueue";

    private ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
    @Inject
    private Logger logger;

    public void sendReservationCreatedMessage(Reservation reservation) {
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(RESERVATION_QUEUE_NAME);
            javax.jms.MessageProducer producer = session.createProducer(queue);
            // toDo: send actually useful messageD
            TextMessage message = session.createTextMessage(String.valueOf(reservation.getId()));
            producer.send(queue, message);
        } catch (JMSException e) {
            logger.log(Level.SEVERE, "Cannot open JMS connection", e);
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
                logger.log(Level.SEVERE, "Cannot close JMS connection", e);
            }
        }
    }
}
