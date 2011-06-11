/*
 * TextAreaOutputStream.java
 *
 * Created on October 15, 2006, 1:17 PM
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
import javax.swing.JTextArea;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TextAreaOutputStream extends OutputStream
{
    private JTextArea m_textArea;
    
    /** Creates a new instance of TextAreaOutputStream */
    public TextAreaOutputStream(JTextArea textArea)
    {
        m_textArea = textArea;
    }

    public void write(int b) throws IOException
    {
        m_textArea.append(String.valueOf((char)b));
    }
    
    public void println(Object x)
    {
        m_textArea.append("\n" + x.toString());
    }
    
    public void println(String x)
    {
        m_textArea.append("\n" + x);
    }
    
    public void println(boolean x)
    {
        m_textArea.append("\n" + (new Boolean(x)).toString());
    }
    
    public void println(int x)
    {
        m_textArea.append("\n" + (new Integer(x)).toString());
    }
    
    public void println(float x)
    {
        m_textArea.append("\n" + (new Float(x)).toString());
    }
    
}
