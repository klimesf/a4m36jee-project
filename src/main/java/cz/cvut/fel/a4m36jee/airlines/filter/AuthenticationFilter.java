package cz.cvut.fel.a4m36jee.airlines.filter;

import cz.cvut.fel.a4m36jee.airlines.dao.UserDAO;
import cz.cvut.fel.a4m36jee.airlines.model.User;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Filter for authentication and authorization via Basic auth method.
 *
 * @author Ondřej Kratochvíl
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private Logger logger;
    @Inject
    private UserDAO userDAO;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }
        try {
            String base64hash = authorizationHeader.substring("Basic".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64hash));
            String[] split = credentials.split(":");
            List<User> users = userDAO.findBy("username", split[0]);
            if (users == null || users.isEmpty()) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                logger.log(Level.INFO, "Invalid user");
                return;
//            throw new NotAuthorizedException("Invalid user");
            }
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(split[1].getBytes());
            byte[] byteData = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            if (!users.get(0).getPassword().equals(sb.toString())) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                logger.log(Level.INFO, "Invalid password");
//                throw new NotAuthorizedException("Invalid password");
            }
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "No such algorithm: MD5", e);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Cannot create MD5 hash from received password or decode base64", e);
        }
    }
}
