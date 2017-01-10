package cz.cvut.fel.a4m36jee.airlines.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Entity representing a user of the application.
 *
 * @author Ondřej Kratochvíl
 */
@Entity
public class User extends AbstractEntity {

    private String username;
    private String password;
    @OneToMany
    private Set<UserRole> roles;
}
