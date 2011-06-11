/*
 * PreCondPanel.java
 *
 * Created on November 10, 2006, 1:52 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.ui;

import java.util.Hashtable;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.Task;

/**
 * Editor for task pre-conditions
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class PreCondPanel extends JPanel implements TopicEventListener
{
    private Task m_task;
    
    /** Creates new form PreCondPanel */
    public PreCondPanel(Task task)
    {
        m_task = task;
        
        initComponents();
        
        m_preCondCtrl.setInputVerifier(new PreCondVerifier());
        
        // Fill the controls with data.                
        m_preCondCtrl.setText(m_task.getPreCondition());
        
        initEvents();
    }
    
    private void initEvents()
    {
        
    }
    
    public void notify(TopicEvent event) throws Exception
    {
    }
    
    private void fireEditPreConditionCommand(Task task, String preCondition)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.PRE_CONDITION_TEXT, preCondition);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TASK +
                                              EventService.PRE_CONDITION +
                                              EventService.EDIT, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot edit pre-condition! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    class PreCondVerifier extends InputVerifier
    {
        public boolean verify(JComponent input) 
         {
            boolean isValid = false;

            JTextArea ta = (JTextArea) input;
            
            //if (! ta.getText().equals(""))
            //{
                
                isValid = true;
                
                fireEditPreConditionCommand(m_task, ta.getText());
            //}

            return isValid;
         }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        jScrollPane1 = new javax.swing.JScrollPane();
        m_preCondCtrl = new javax.swing.JTextArea();

        m_preCondCtrl.setColumns(20);
        m_preCondCtrl.setRows(5);
        jScrollPane1.setViewportView(m_preCondCtrl);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea m_preCondCtrl;
    // End of variables declaration//GEN-END:variables
    
}
