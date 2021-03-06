package cz.cvut.fel.a4m36jee.airlines.rest.resource;

import cz.cvut.fel.a4m36jee.airlines.dao.ReservationDAO;
import cz.cvut.fel.a4m36jee.airlines.filter.Secured;
import cz.cvut.fel.a4m36jee.airlines.jms.MessageProducer;
import cz.cvut.fel.a4m36jee.airlines.model.Reservation;
import cz.cvut.fel.a4m36jee.airlines.service.ReservationService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author klimefi1, moravja8
 */
@Path("/reservations")
@RequestScoped
public class ReservationResource {

    @Inject
    private Logger logger;

    @Inject
    private Validator validator;

    @Inject
    private ReservationService reservationService;

    /**
     * Lists all Reservation entities.
     *
     * @return List of Ts
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured(roles = {"ADMIN", "EMPLOYEE"})
    public List<Reservation> list() {
        return reservationService.list();
    }

    /**
     * Retrieves Reservation entity by the given id
     *
     * @param id The identifier
     * @return The Reservation entity
     */
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured(roles = {"ADMIN", "EMPLOYEE"})
    public Reservation get(@PathParam("id") long id) {
        Reservation destination = reservationService.get(id);
        if (destination == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return destination;
    }

    /**
     * Validates and creates new Reservation entity.
     *
     * @param entity The entity
     * @return JAX-RS Response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Secured(roles = {"USER", "ADMIN", "EMPLOYEE"})
    public Response create(Reservation entity) {
        Response.ResponseBuilder builder = null;

        try {
            validate(entity);
            reservationService.create(entity);
            logger.fine("Created a new entity.");
            builder = Response.ok();

        } catch (ConstraintViolationException ce) {
            builder = createViolationResponse(ce.getConstraintViolations());

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    /**
     * Creates a JAX-RS HTTP response which shows constraint violations.
     *
     * @param violations Set of violations
     * @return The JAX-RS response
     */
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        logger.fine("Validation completed. Violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    /**
     * Validates Reservation entity.
     *
     * @param destination The entity to validate
     * @throws ConstraintViolationException Thrown if the entity violates any constraints
     */
    private void validate(Reservation destination) throws ConstraintViolationException {
        Set<ConstraintViolation<Reservation>> violations = validator.validate(destination);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

}
