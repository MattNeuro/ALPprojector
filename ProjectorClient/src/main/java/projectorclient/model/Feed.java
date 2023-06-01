package projectorclient.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import projectorclient.controller.Controller;

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
        int offsetX = Integer.parseInt(Controller.getInstance().gui.captureX.getText());
        int offsetY = Integer.parseInt(Controller.getInstance().gui.captureY.getText());
        int width   = Integer.parseInt(Controller.getInstance().gui.captureWidth.getText());
        int height  = Integer.parseInt(Controller.getInstance().gui.captureHeight.getText());
        captureArea = new Rectangle(offsetX, offsetY, width, height);
        System.out.println("Updating capture area.");
    }
    
    
    public void addMaskSpot (int x, int y) {
        mask.addSpot(x, y);
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
                Controller.getInstance().gui.displayLabel.setIcon(new ImageIcon(capture));
                Thread.sleep(50);
            } catch (Exception e) {
                System.out.println("Fatal error: " +  e.getMessage());
            }
        }
    }
    

    /**
     *  Private constructor. Set default values.
     * 
     */
    private Feed () {
        captureArea = new Rectangle(-100, 100, 1024, 768);    // Arbitrary
        mask        = new Mask();
        System.out.println("New feed created.");
    }
    

    /**
     *  Add a mask overlay over the captured image. 
     * 
     *  @param capture 
     */
    private void addMaskOverlay (BufferedImage capture) {
        Graphics2D graphics = capture.createGraphics();        
        graphics.setColor(new Color(1f, 0f, 0f, 0.5f));
        
        for (MaskSpot spot : mask)
            graphics.fillOval(spot.x,spot.y, spot.size, spot.size);
    }
}