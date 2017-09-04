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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BetType)) return false;

        BetType betType = (BetType) o;

        return name != null ? name.equals(betType.name) : betType.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BetType{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
