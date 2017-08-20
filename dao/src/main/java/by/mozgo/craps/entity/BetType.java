package by.mozgo.craps.entity;

import java.io.Serializable;

/**
 * Created by Andrei Mozgo. 2017.
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
