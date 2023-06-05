package projectorclient.view;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author Matthijs
 */
public class InterfaceListener {
    
    
    
    public InterfaceListener () {
        try
        {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new NativeKeyListener()
            {

                @Override
                public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
                    System.out.println("Key typed event");
                }

                @Override
                public void nativeKeyReleased(NativeKeyEvent nativeEvent)
                {
                    String keyText=NativeKeyEvent.getKeyText(nativeEvent.getKeyCode());
                    System.out.println("User typed: "+keyText);
                }

                @Override
                public void nativeKeyPressed(NativeKeyEvent nativeEvent)
                {   
                }
             });
        }
        catch (NativeHookException e)
        {
            e.printStackTrace();
        }        
    }
        
        
    
}
