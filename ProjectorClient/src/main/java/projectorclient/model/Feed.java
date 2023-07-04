package projectorclient.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import projectorclient.controller.Controller;
import projectorclient.view.ClientFrame;

/**
 *  Handle the camera feed. 
 * 
 * This retrieves the current camera image, applies a potential image mask to
 * it, and subsequently displays it on our GUI.
 *
 * @author Matthijs
 */
public class Feed extends Thread {
    
    
    private boolean     active          = true;
    private Rectangle   captureArea;
    private Mask        mask;
    private int         captureWidth    = 1024,
                        captureHeight   = 768;
    private static int  width           = 1024,
                        height          = 768;
    private static Feed instance;   // Singleton instance.

    
    /**
     *  Singleton pattern, we don't want to create multiple feeds.
     * 
     *  @param  parent  Controller.
     *  @return The Feed instance.
     */
    public static Feed getInstance () {
        if (instance == null)
            instance = new Feed();            
        return instance;
    }  

    
    /**
     *  Update the screen area that is captured.
     * 
     *  The user can adjust which area of the screen is captured by adjusting
     *  text-boxes for offsets in X and Y, and the width and height of the 
     *  area to be captured. This enables the user to match the overlay image to
     *  their camera image, regardless of where that camera image is displayed.
     * 
     *  This should be stored as a user-preference, and reloaded at next 
     *  start-up.
     */
    public void updateCaptureArea () {
        int offsetX     = Integer.parseInt(Controller.getInstance().gui.captureX.getText());
        int offsetY     = Integer.parseInt(Controller.getInstance().gui.captureY.getText());
        captureWidth    = Integer.parseInt(Controller.getInstance().gui.captureWidth.getText());
        captureHeight   = Integer.parseInt(Controller.getInstance().gui.captureHeight.getText());
        captureArea = new Rectangle(offsetX, offsetY, captureWidth, captureHeight);
        System.out.println("Updating capture area.");
    }
    
    public void clearMask () {
        mask.clear();
    }
    public void fillMask () {
        mask.add(new MaskSpot(-500, -500, 2000));
    }
    public void addMaskSpot (int x, int y) {
        mask.addSpot(x, y);
    }
    public void setMaskSpotSize (int newSize) {
            mask.setSpotSize(newSize);
    }
    
    
    public void setActive (boolean newState) {
        this.active = newState;
    }
    
    
    public byte[] getBitMask() {
        return mask.asBitMask();
    }
    
    
    
    public void run () {
        while (this.active) {
            try {
                BufferedImage capture   = new Robot().createScreenCapture(captureArea);
                addMaskOverlay(capture);
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println("Fatal error: " +  e.getMessage());
            }
        }
    }
    

    /**
     *  Private constructor. Set default values.
     * 
     *  Ideally, we store and retrieve these as a user setting at some point.
     */
    private Feed () {
        captureArea = new Rectangle(-100, 100, 1024, 768);    // Arbitrary
        mask        = new Mask();
        System.out.println("New feed created.");
    }
    

    /**
     *  Add a mask overlay over the captured image, then display it. 
     * 
     *  This will process the MaskSpots active in the Mask class, and draw a
     * semi-transparent filled circle on top of the captured image for each spot.
     * In addition, it will draw an empty circle where the mouse cursor was last 
     * detected by the JLabel element. 
     * 
     * The JLabel element is responsible for updating the mouse location whenever
     * a mouse movement event occurs. This location is stored in the gui for use
     * here.
     * 
     * Since we draw the mouse outline slightly thicker, account for this 
     * line-thickness when drawing the circle.
     * 
     *  @param capture The buffered image from our screen capture.
     */
    private void addMaskOverlay (BufferedImage capture) {
        int strokeWidth         = 4; // seems about right
        ClientFrame gui         = Controller.getInstance().gui;
        BufferedImage output    = new BufferedImage(width, height, capture.getType()); // new image, based on captured image
        Graphics2D graphics     = output.createGraphics();
        
        graphics.setColor(new Color(1f, 0f, 0f, 0.5f));
        graphics.setStroke(new BasicStroke(strokeWidth));
        
        // By drawing the output image based on the captured image, we apply a width and height scaling.
        graphics.drawImage(capture,0,0,width, height, null);
        
        // For each spot, draw a filled oval
        for (MaskSpot spot : mask)
            graphics.fillOval(spot.x,spot.y, spot.size, spot.size);

        // Draw the mouse cursor        
        int offset = (mask.getSpotSize() / 2) - (strokeWidth / 2);
        graphics.drawOval(gui.mouseX - offset, gui.mouseY - offset, mask.getSpotSize() - strokeWidth, mask.getSpotSize() - strokeWidth);

        // We are done drawing, display the new graphics:
        gui.displayLabel.setIcon(new ImageIcon(output));
    }
}