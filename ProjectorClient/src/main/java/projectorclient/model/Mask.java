package projectorclient.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_BYTE_BINARY;
import java.awt.image.DataBufferByte;
import java.util.BitSet;
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
    
    public void setSpotSize (int newSize) {
        this.spotSize = newSize;
    }
    
    
    /**
     *  Convert this mask into a bitmask. To do so, we first create a binary
     *  buffered image, then create a graphics2D object from that which we can
     *  use to draw the shape of each oval.
     *  
     * @return 
     */
    public byte[] asBitMask () {
        BufferedImage source    = new BufferedImage(1024, 768, TYPE_BYTE_BINARY);
        Graphics2D graphics     = source.createGraphics();
        for (MaskSpot spot : this)
            graphics.fillOval(spot.x,spot.y, spot.size, spot.size);

        return ((DataBufferByte) source.getRaster().getDataBuffer()).getData();
    }
    
    
    
    public void addSpot (int x, int y) {
        int xCenter = x - (spotSize / 2);
        int yCenter = y - (spotSize / 2);
        
        MaskSpot newSpot = new MaskSpot(xCenter, yCenter, spotSize);
        super.add(newSpot);
    }
}