/*
 * StringSplitter.java
 *
 * Created on November 15, 2006, 5:39 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.util;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class StringSplitter
{
    
    /** Creates a new instance of StringSplitter */
    public StringSplitter()
    {
    }
    
    // Returns a split string.  Each item contains it's regex
    public static String[] split(String str, String delim)
    {        
        // The param to slit is a regex.
        String[] s = str.split("[" + delim + "]");
        
        // Add the delim's back.        
        
        if (s[0].startsWith(delim))
            s[0] = delim + s[0];
          
        for (int i = 1; i < s.length; i++)
        {
            s[i] = delim + s[i];
        }
        
        return s;
    }
    
}
