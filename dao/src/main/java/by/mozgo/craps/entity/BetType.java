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
    private Integer id;
    private String name;

    public BetType(Integer id, String name){
        this.id = id;
        this.name = name;
    }
}
