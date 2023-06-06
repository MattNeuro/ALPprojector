package projectorclient;

import projectorclient.controller.Controller;

/**
 *  Projector client.
 * 
 *  This handles the client-side operations of controlling a digital micromirror
 *  device based on live input from a camera. To this end, it will screen-grab
 *  the camera feed and enable drawing operations via the GUI to demark 
 *  cell boundaries.
 * 
 *  @author Matthijs
 */
public class ALPClient {

    /**
     *  Start the projector client.
     * 
     *  This instantiates a new controller, then attempts to create a GUI
     *  and initialize the model.
     * 
     *  @param args 
     */
    public static void main(String[] args) {
        Controller c = Controller.getInstance();

        try {
            c.startGui();       // Initialize and show the GUI
            c.startFeed();      // Start grabbing the camera feed
        } catch (Exception e) { 
            System.out.println("Fatal error: " + e.getMessage());
        }
    }
}