package projectorclient.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.prefs.Preferences;
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
    
    
    private boolean             active          = true;
    private boolean             gridLines       = false;
    private Rectangle           captureArea;
    private Mask                mask;
    private int                 captureWidth    = 1024,
                                captureHeight   = 768;
    private static int          width           = 1024,
                                height          = 768;
    private static Feed         instance;   // Singleton instance.
    private static Preferences  preferences;

    
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
        captureArea     = new Rectangle(offsetX, offsetY, captureWidth, captureHeight);
        System.out.println("Updating capture area.");
        updateCaptureAreaPreferences();
    }
    

    /**
     *  Store capture area preferences.
     * 
     *  This will store the offsets and width / height of the capture
     *  area, so that when we relaunch the application later, we can reload
     *  those same coordinates.
     */
    public void updateCaptureAreaPreferences () {
        preferences.putInt("width",     captureArea.width);
        preferences.putInt("height",    captureArea.height);
        preferences.putInt("offsetX",   captureArea.x);
        preferences.putInt("offsetY",   captureArea.y);
        
        // Make sure the GUI matches our preferences.
        Controller.getInstance().gui.updateAreaPreferences(captureArea); 
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
    public void setGridLinesActive (boolean gridLinesActive) {
        this.gridLines = gridLinesActive;
    }
    public void setActive (boolean newState) {
        this.active = newState;
    }
    
    public boolean getGridLinesActive () {
            return this.gridLines;
    }
    
    
    /**
     *  Retrieve a bitmask where drawn areas are 1 and empty areas are 0
     * 
     *  This bitmask is used by the network client to instruct the ALP
     *  controller on which mirrors to flip.
     * 
     *  @return 
     */
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
        // Retrieve user preferences. Use default values as fallback.
        preferences     = Preferences.userNodeForPackage(projectorclient.model.Feed.class);
        int offsetX     = preferences.getInt("offsetX",  100);
        int offsetY     = preferences.getInt("offsetY",  100);
        captureWidth    = preferences.getInt("width",    1024);
        captureHeight   = preferences.getInt("height",   768);
        
        captureArea = new Rectangle(offsetX, offsetY, captureWidth, captureHeight);
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
        
        graphics.setColor(new Color(1f, 0f, 0f, 0.2f));
        graphics.setStroke(new BasicStroke(strokeWidth));
        
        // By drawing the output image based on the captured image, we apply a width and height scaling.
        graphics.drawImage(capture,0,0,width, height, null);
        
        // For each spot, draw a filled oval
        for (MaskSpot spot : mask)
            graphics.fillOval(spot.x,spot.y, spot.size, spot.size);

        // Draw the mouse cursor        
        int offset = (mask.getSpotSize() / 2) - (strokeWidth / 2);
        graphics.drawOval(gui.mouseX - offset, gui.mouseY - offset, mask.getSpotSize() - strokeWidth, mask.getSpotSize() - strokeWidth);

        // Draw grid-lines if requested
        if (gridLines) {
            graphics.setColor(new Color(0f, 1f, 0f, 0.5f));
            addGridLines(graphics);
        }
        
        // We are done drawing, display the new graphics:
        gui.displayLabel.setIcon(new ImageIcon(output));
    }
    
    
    
    /**
     *  Apply grid-lines to the image before displaying it
     * 
     *  @param graphics The graphics object to draw grid lines on.
     */
    public void addGridLines (Graphics2D graphics) {
        int strokeWidth = 4;
        graphics.setStroke(new BasicStroke(strokeWidth));
        
        // Draw four lines around the perimiter
        graphics.drawLine(0, 0, width, 0);
        graphics.drawLine(0, height, width, height);
        graphics.drawLine(0, 0, 0, height);
        graphics.drawLine(width - (strokeWidth/2), 0, width - (strokeWidth/2), height);
        
        // Draw 2 horizontal and 3 vertical dividing lines
        graphics.drawLine(0, height / 3, width, height / 3);
        graphics.drawLine(0, height - (height / 3), width, height - (height / 3));
        
        graphics.drawLine( width / 4, 0, width / 4, height);
        graphics.drawLine( width / 2, 0, width / 2, height);
        graphics.drawLine( width - (width / 4), 0, width - (width / 4), height);
    }
}