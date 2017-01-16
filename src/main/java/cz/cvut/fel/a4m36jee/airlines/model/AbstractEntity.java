package cz.cvut.fel.a4m36jee.airlines.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author kratoon
 */
@MappedSuperclass
public class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date deleted;

    public Long getId() {
        return id;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(final Date deleted) {
        this.deleted = deleted;
    }
}
