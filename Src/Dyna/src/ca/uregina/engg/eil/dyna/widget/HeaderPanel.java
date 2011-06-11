/*
 * HeaderPanel.java
 *
 * Created on August 13, 2006, 12:43 AM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.widget;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * For displaying a title and some buttons above some other panel.
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class HeaderPanel extends JPanel
{
    /**
     * The text to display in the header.
     */
    protected JLabel m_title;
    
    /**
     * Creates a new instance of HeaderPanel
     * @param title The text to display in the header
     */
    public HeaderPanel(String title)
    {
        super();
        
        m_title = new JLabel(title);
                
        add(m_title); 
    }
        
    
}
