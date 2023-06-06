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
                    int oldSize = ClientFrame.maskSizeSlider.getValue();
                    
                    if (keyText.equalsIgnoreCase("H"))
                        ClientFrame.maskSizeSlider.setValue((int) (oldSize * 1.5));
                    if (keyText.equalsIgnoreCase("G"))
                        ClientFrame.maskSizeSlider.setValue((int) (oldSize * 0.75));
                }

                @Override
                public void nativeKeyPressed(NativeKeyEvent nativeEvent)
                {   
                }
             });
        }
        catch (NativeHookException e)
        {
            //e.printStackTrace();
        }        
    }
        
        
    
}
