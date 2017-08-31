package by.mozgo.craps.entity;

/**
 * Data entity base class
 *
 * @author Mozgo Andrei
 */
public abstract class AbstractEntity {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}

