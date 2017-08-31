package by.mozgo.craps.entity;

import java.io.Serializable;

/**
 * The persistent class for the bet_type database table.
 *
 * @author Mozgo Andrei
 *
 */
public class BetType extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;

    public BetType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
