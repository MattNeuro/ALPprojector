package projectorclient.model;

import java.io.OutputStream;
import java.net.Socket;
import java.util.BitSet;

/**
 *
 * @author Matthijs
 */
public class NetworkClient {
    
    private final   String          host        = "localhost";
    private final   int             port        = 8041;    

    private static  NetworkClient   instance;
    
    private         Socket          socket;
    
    
    public static NetworkClient getInstance () {
        if (instance == null)
            instance = new NetworkClient();
        return instance;
    }
    
    
    /**
     *  Attempts to upload the drawn image mask to the DMD.
     * 
     *  This will first check to see if we have a valid connection, and if not,
     * try to reconnect to the DMD controller service. If that fails, this will
     * throw an exception since we should inform the user that we could not
     * upload the mask as requested.
     * 
     * @return
     * @throws Exception 
     */
    public boolean uploadMask () throws Exception {
        if (socket == null || socket.isClosed()) 
            attemptConnection();
        
        // Assume we have an open connection here, otherwise an exception should have been thrown.
        socket.setSendBufferSize(64);
        OutputStream output = socket.getOutputStream();
        
        byte[] data = Feed.getInstance().getBitMask();        
        //BitSet bitset = BitSet.valueOf(data);
        
        System.out.println("Ready to write " + data.length + " bytes");
        
        output.write(data);
        
        output.flush();  
        
        return true;
    }
    
    
    
    private NetworkClient () {
        try {
            attemptConnection();
        } catch (Exception e) {
            System.out.println("Fatal error: could not connect to host, " + e.getMessage());
        }
    }
    
    
    private void attemptConnection () throws Exception {
        // Make sure we close the socket before attempting to re-connect:
        if (this.socket != null)
            this.socket.close();
        
        this.socket = new Socket(host, port);
    }
}