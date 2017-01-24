package cz.cvut.fel.a4m36jee.airlines.rest.security;

import javax.inject.Inject;
import javax.interceptor.AroundTimeout;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Parameter;
import java.util.logging.Logger;

/**
 * Interceptor for REST authentifiaction.
 *
 * Both username and password are part of the request headers.
 *
 * Password is encrypted in base64.
 *
 * @author moravja8
 */
public class RestAuthentificationInterceptor {

    @Inject
    private Logger logger;

    @AroundTimeout
    public void authenticateUserViaRest(InvocationContext ctx) {

        Parameter[] parameters = ctx.getMethod().getParameters();
        String usernameParameter = parameters[0].getName();
        String passwordParameter = parameters[0].getName();

        String username = (String) ctx.getParameters()[0];
        String password = (String) ctx.getParameters()[1];

        logger.info("INTERCEPTOR>>>" + username);
        logger.info("INTERCEPTOR>>>" + usernameParameter);
        logger.info("INTERCEPTOR>>>" + password);
        logger.info("INTERCEPTOR>>>" + passwordParameter);

    }


}
