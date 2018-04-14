package database.entity;

/**
 * <p>Abstract Entity class.</p>
 *
 * @author Kamil Cieślik
 * @version $Id: $Id
 */
public abstract class Entity {
    private Integer id;

    /**
     * <p>Constructor for Entity.</p>
     */
    Entity() {
    }

    /**
     * <p>Constructor for Entity.</p>
     *
     * @param id a {@link Integer} object.
     */
    Entity(Integer id) {
        this.id = id;
    }

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return a int.
     */
    public int getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id a {@link Integer} object.
     */
    public void setId(Integer id) {
        this.id = id;
    }
}

