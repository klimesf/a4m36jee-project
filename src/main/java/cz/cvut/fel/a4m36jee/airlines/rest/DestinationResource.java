package cz.cvut.fel.a4m36jee.airlines.rest;

import cz.cvut.fel.a4m36jee.airlines.model.Destination;
import cz.cvut.fel.a4m36jee.airlines.service.DestinationService;

import javax.annotation.security.RolesAllowed;
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
@Path("/destinations")
@RequestScoped
public class DestinationResource {

    @Inject
    private Logger logger;

    @Inject
    private Validator validator;

    @Inject
    private DestinationService destinationService;

    /**
     * Lists all Destination entities.
     *
     * @return List of Ts
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Destination> list() {
        return destinationService.list();
    }

    /**
     * Retrieves Destination entity by the given id
     *
     * @param id The identifier
     * @return The Destination entity
     */
    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Destination get(@PathParam("id") long id) {
        Destination destination = destinationService.get(id);
        if (destination == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return destination;
    }

    /**
     * Validates and creates new Destination entity.
     *
     * @param entity The entity
     * @return JAX-RS Response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed("ADMIN")
    public Response create(Destination entity) {
        Response.ResponseBuilder builder = null;

        try {
            validate(entity);
            destinationService.create(entity);
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
     * Validates Destination entity.
     *
     * @param destination The entity to validate
     * @throws ConstraintViolationException Thrown if the entity violates any constraints
     */
    private void validate(Destination destination) throws ConstraintViolationException {
        Set<ConstraintViolation<Destination>> violations = validator.validate(destination);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }

}
