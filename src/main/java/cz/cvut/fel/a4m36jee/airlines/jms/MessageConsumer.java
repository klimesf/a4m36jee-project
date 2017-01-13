package cz.cvut.fel.a4m36jee.airlines.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
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

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                // toDo: replace by actual logic
                System.out.println("Received message: " + ((TextMessage) message).getText());
            } catch (JMSException e) {
                logger.log(Level.SEVERE, "Cannot retrieve text from the message", e);
            }
        } else {
            logger.log(Level.WARNING,
                    "Incorrect message type, expected: javax.jms.TextMessage, received: " + message.getClass());
        }
    }
}
