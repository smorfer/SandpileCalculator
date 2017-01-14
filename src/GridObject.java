/**
 * Abstract class for the Sandpiles and the Gridboundaries.
 * Is used so the collapse method can be applied, independend of the Class,
 * and so that sandpiles are getting consumed at the borders.
 */
public abstract class GridObject {
    public abstract void collapse();
    public abstract boolean checkOverflow();
    public abstract void receive();
}
