/*
 * StringOutputStream.java
 *
 * Created on October 19, 2006, 1:10 AM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.widget;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class StringOutputStream extends OutputStream
{
    private String m_buffer;    
    public String m_value;
    
    /** Creates a new instance of StringOutputStream */
    public StringOutputStream(String buffer)
    {
        m_buffer = buffer;   
        m_value = "";
    }

    public void write(int b) throws IOException
    {
        m_buffer += String.valueOf((char)b);        
    }
    
    public void println(Object x)
    {
         m_buffer += "\n" + x.toString();           
    }
    
    public void println(String x)
    {
         m_buffer += "\n" + x;         
    }
    
    public void println(boolean x)
    {
         m_buffer += "\n" + (new Boolean(x)).toString();         
    }
    
    public void println(int x)
    {
         m_buffer += "\n" + (new Integer(x)).toString();  
         m_value = (new Integer(x)).toString();         
    }
    
    public void println(float x)
    {
         m_buffer += "\n" + (new Float(x)).toString();         
    }
    
    public String getBuffer()
    {
        return m_buffer;
    }
    
   
    
}
