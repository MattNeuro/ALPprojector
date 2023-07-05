/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectorclient.controller;

import javax.swing.JFrame;
import projectorclient.view.ClientFrame;
import projectorclient.view.InterfaceListener;
import projectorclient.model.Feed;
import projectorclient.model.NetworkClient;


/**
 *
 * @author Matthijs
 */
public class Controller {

    public ClientFrame      gui;
    public Feed             feedThread;
    public NetworkClient    network;
    
    private static Controller instance;
    

    
    public static Controller getInstance () {
        if (instance == null)
            instance = new Controller();
        return instance;
    }
    
    
    private Controller () {
        this.gui        = new ClientFrame();
        this.feedThread = Feed.getInstance();
        this.network    = NetworkClient.getInstance();
    }

    
    public void startGui () throws Exception {
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setVisible(true);
        new InterfaceListener(); // Register hotkey listener.
        feedThread.updateCaptureAreaPreferences();
    }
    
    
    public void startFeed () throws Exception {
        feedThread.start();
    }
}