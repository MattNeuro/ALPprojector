package projectorclient.model;

/**
 *  Singular circular mask
 * 
 *  Contains an X, Y and Size
 * 
 *  @author Matthijs
 */
public class MaskSpot {
    
    public int x, y, size;
    
    public MaskSpot (int x, int y, int size) {
        this.x      = x;
        this.y      = y;
        this.size   = size;
    }
}