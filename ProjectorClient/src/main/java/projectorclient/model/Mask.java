package projectorclient.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_BYTE_BINARY;
import java.awt.image.DataBufferByte;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;

/**
 *  Keep track of the masked areas, indicating areas that will be
 * illuminated by the DMD.Users can freely adjust the mask through the GUI.
 * 
 * 
 * @author Matthijs
 */
public class Mask extends LinkedList<MaskSpot> {
    
    private int                     spotSize = 50;   
    
    
    protected Mask () {
        // Ensure only local classes can instantiate.
    }
    
    
    /**
     *  Change the default spot size.
     * 
     *  After changing this value, all new mask spots will have the new spot
     *  size.
     * 
     *  @param newSize 
     */
    public void setSpotSize (int newSize) {
        this.spotSize = newSize;
    }
    
    public int getSpotSize () {
        return this.spotSize;
    }
    
    
    
    /**
     *  Convert this mask into a bitmask. 
     * 
     *  To do so, we first create a binary buffered image, then create a
     *  graphics2D object from that which we can use to draw the shape of each
     *  oval, as well as additional drawing operations we may need to perform.
     *  
     *  @return 
     */
    public byte[] asBitMask () {
        Feed feed = Feed.getInstance();
        
        BufferedImage source    = new BufferedImage(feed.width, feed.height, TYPE_BYTE_BINARY);
        Graphics2D graphics     = source.createGraphics();
        for (MaskSpot spot : this)
            graphics.fillOval(spot.x,spot.y, spot.size, spot.size);
        
        // check if we need to create grid lines on our mask:
        if (feed.getGridLinesActive())
            feed.addGridLines(graphics);
        
        // In case we use deinterlacing, we remove every other pixel
        if (feed.getDeinterlaceActive())
            deinterlaceGraphics(graphics);

        return ((DataBufferByte) source.getRaster().getDataBuffer()).getData();
    }
    
    
    /**
     *  Add a new spot to the mask.
     * 
     *  This would normally be called whenever the user clicks on the mask
     *  interface. The new spot is created at the point where the user clicked,
     *  and with the width and height set to the current size of new spots.
     * 
     *  @param x
     *  @param y 
     */
    public void addSpot (int x, int y) {
        int xCenter = x - (spotSize / 2);
        int yCenter = y - (spotSize / 2);
        
        MaskSpot newSpot = new MaskSpot(xCenter, yCenter, spotSize);
        super.add(newSpot);
    }
    
    
    public void removeSpot (int x, int y) {
        try {        
            for (MaskSpot spot : this) {
                int radius = spot.size / 2;

                // calculate euclidian distance to spot
                int dX          = (spot.x + radius) - x;
                int dY          = (spot.y + radius) - y;
                int distance    = (int) Math.sqrt((dX * dX) + (dY * dY));

                // If we clicked on the spot, remove it.
                if (distance < radius)
                        super.remove(spot);
            }
        } catch (ConcurrentModificationException e) { 
            // We are modifying the LinkedList while we iterate over it. Ignore it.
        }
    }
    
    
    /**
     *  Reduce the amount of light going to the brain.
     * 
     *  This deinterlaces our project image by effectively removing every 
     *  second horizontal and vertical row of pixels. The net effect is a
     *  75% reduction in reflected light (in theory, anyway). This makes it
     *  extremely easy to switch between low and high light power without
     *  adjusting the laser.
     * 
     *  @param graphics 
     */
    private void deinterlaceGraphics (Graphics2D graphics) {
        Feed feed       = Feed.getInstance();
        Color oldColor  = graphics.getColor();
        graphics.setColor(new Color(1f, 1f, 1f, 1.0f)); // full white pixels should be off?
        
        // draw horizontal lines
        for (int i = 0; i < feed.height; i += 2) 
            graphics.drawLine(0, i, feed.width, i);
        // draw vertical lines
        for (int i = 0; i < feed.width; i += 2)
            graphics.drawLine(i, 0, i, feed.height);
        
        // reset color back to the original
        graphics.setColor(oldColor);
    }
}