/*
 * OutputPanel.java
 *
 * Created on October 15, 2006, 12:33 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.ui;

import java.awt.BorderLayout;
import java.io.PrintStream;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import ca.uregina.engg.eil.dyna.controller.TaskController;
import ca.uregina.engg.eil.dyna.widget.TextAreaOutputStream;

/**
 * Redirect standard output to this panel
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class OutputPanel extends JPanel
{
    //private TaskController m_taskController;
    
    private JTextArea m_outputCtrl = null;
    
    private PrintStream m_outputStream;
    
    /** Creates a new instance of OutputPanel */
    public OutputPanel()
    {
        super();
        
        //m_taskController = taskController;
        
        initComponents();
        
        // For writing to the output ctrl.
        m_outputStream = new PrintStream(new TextAreaOutputStream(m_outputCtrl));
                
    }
    
    private void initComponents()
    {
        JScrollPane scrollPane = new JScrollPane();
        //setResizable(true);
        
        m_outputCtrl = new JTextArea();
        
        //m_outputCtrl.setColumns(100);
        m_outputCtrl.setRows(10);
        scrollPane.setViewportView(m_outputCtrl);
        
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public PrintStream getOutputStream()
    {
        return m_outputStream;
    }
    
    public void clear()
    {
        m_outputCtrl.setText("");
    }
}
