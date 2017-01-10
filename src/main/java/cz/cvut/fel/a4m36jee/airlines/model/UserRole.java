package cz.cvut.fel.a4m36jee.airlines.model;

import javax.persistence.Entity;

/**
 * Entity representing a single role of a {@link User}.
 *
 * @author Ondřej Kratochvíl
 */
@Entity
public class UserRole extends AbstractEntity {

    private String name;
}
