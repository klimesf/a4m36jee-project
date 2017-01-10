package cz.cvut.fel.a4m36jee.airlines.model;

import cz.cvut.fel.a4m36jee.airlines.enums.UserRole;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity representing a user of the application.
 *
 * @author Ondřej Kratochvíl
 */
@Entity
public class User extends AbstractEntity {

    @Column(unique = true)
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
