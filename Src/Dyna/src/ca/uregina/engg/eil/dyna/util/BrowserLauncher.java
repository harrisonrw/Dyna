/*
 * BrowserLauncher.java
 *
 * Created on November 15, 2006, 10:50 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.util;

import java.lang.reflect.Method;
import javax.swing.JOptionPane;

/**
 * Launches the OS's default web browser.  From Bare Bones Browser Launch.
 * See http://www.centerkey.com/java/browser/
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class BrowserLauncher
{
    // Operating Systems.
    private static final String MAC = "Max OS";
    private static final String WINDOWS = "Windows";
    
    // Browsers.
    private static final String FIREFOX = "firefox";
    private static final String OPERA = "opera";
    private static final String KONQUEROR = "konqueror";
    private static final String MOZILLA = "mozilla";
    private static final String NETSCAPE = "netscape";
    
    // Error messages.
    private static final String LAUNCH_ERROR = "Error attempting to launch web browser";
    private static final String NO_BROWSER_ERROR = "Could not find web browser";
    
    public static void openURL(String url) 
    { 
        String osName = System.getProperty("os.name"); 
        
        try 
        { 
            if (osName.startsWith(MAC)) 
            { 
                Class fileMgr = Class.forName("com.apple.eio.FileManager"); 
                Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] {String.class}); 
                openURL.invoke(null, new Object[] {url}); 
            } 
            else if (osName.startsWith(WINDOWS)) 
            {                
                Runtime.getRuntime().exec("cmd /c start " + url);
            }
            else 
            { 
                // Unix or Linux
                String[] browsers = { FIREFOX, OPERA, KONQUEROR, MOZILLA, NETSCAPE }; 
                String browser = null; 
                
                for (int i = 0; i < browsers.length && browser == null; i++) 
                {
                    if (Runtime.getRuntime().exec( new String[] {"which", browsers[i]}).waitFor() == 0) 
                    {
                        browser = browsers[i]; 
                        if (browser == null) 
                        {
                            throw new Exception(NO_BROWSER_ERROR); 
                        }
                        else 
                        {
                            Runtime.getRuntime().exec(new String[] {browser, url}); 
                        } 
                    }
                }
            }
            
        }
        catch (Exception e) 
        { 
            JOptionPane.showMessageDialog(null, LAUNCH_ERROR + ":\n" + e.getLocalizedMessage()); 
        } 
    }
}
