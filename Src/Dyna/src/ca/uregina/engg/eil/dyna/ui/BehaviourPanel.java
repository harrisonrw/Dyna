/*
 * BehaviourPanel.java
 *
 * Created on October 22, 2006, 9:27 PM
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
import javax.swing.JTextField;

/**
 * Editor for task behaviour
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class BehaviourPanel extends JPanel implements TopicEventListener
{
    
    private Task m_task;
    
    /** Creates new form BehaviourPanel */
    public BehaviourPanel(Task task)
    {
        m_task = task;
        
        initComponents();
        
        m_argCtrl.setInputVerifier(new ArgVerifier());
        m_codeCtrl.setInputVerifier(new BehaviourVerifier());
        m_returnCtrl.setInputVerifier(new RetVerifier());
        
        // Fill the controls with data.           
        m_argCtrl.setText(m_task.getArgsAsString());
        m_codeCtrl.setText(m_task.getBehaviour());
        m_returnCtrl.setText(m_task.getReturnId());
        
        initEvents();
    }
    
    private void initEvents()
    {
        
    }
            
    public void notify(TopicEvent event) throws Exception
    {
    }
    
    private void fireEditArgCommand(Task task, String args)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.ARG_LIST_STR, args);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TASK +
                                              EventService.ARGS +
                                              EventService.EDIT, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot edit task arguments! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireEditBehaviourCommand(Task task, String behaviour)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.CODE, behaviour);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TASK +
                                              EventService.BEHAVIOUR +
                                              EventService.EDIT, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot edit behaviour! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireEditRetCommand(Task task, String ret)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.TASK_RETURN, ret);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TASK +
                                              EventService.RETURN +
                                              EventService.EDIT, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                   
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot edit task return! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireRunBehaviourCommand(Task task)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TASK +
                                              EventService.BEHAVIOUR +
                                              EventService.RUN, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot run behaviour! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    class ArgVerifier extends InputVerifier
    {
        public boolean verify(JComponent input) 
         {
            boolean isValid = false;

            JTextField tf = (JTextField) input;
            
            //if (! tf.getText().equals(""))
            //{
                
                isValid = true;
                
                fireEditArgCommand(m_task, tf.getText());
            //}

            return isValid;
         }
    }
    
    class BehaviourVerifier extends InputVerifier
    {
        public boolean verify(JComponent input) 
         {
            boolean isValid = false;

            JTextArea ta = (JTextArea) input;
            
            //if (! ta.getText().equals(""))
            //{
                
                isValid = true;
                
                fireEditBehaviourCommand(m_task, ta.getText());
            //}

            return isValid;
         }
    }
    
    class RetVerifier extends InputVerifier
    {
        public boolean verify(JComponent input) 
         {
            boolean isValid = false;

            JTextField tf = (JTextField) input;
            
            //if (! ta.getText().equals(""))
            //{
                
                isValid = true;
                
                fireEditRetCommand(m_task, tf.getText());
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
        m_codeCtrl = new javax.swing.JTextArea();
        m_argLabel = new javax.swing.JLabel();
        m_argCtrl = new javax.swing.JTextField();
        m_behaviourLabel = new javax.swing.JLabel();
        m_returnLabel = new javax.swing.JLabel();
        m_returnCtrl = new javax.swing.JTextField();

        m_codeCtrl.setColumns(20);
        m_codeCtrl.setRows(5);
        jScrollPane1.setViewportView(m_codeCtrl);

        m_argLabel.setText("Inputs:");

        m_behaviourLabel.setText("Behaviour:");

        m_returnLabel.setText("Output:");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(m_argLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_argCtrl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(m_returnLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_returnCtrl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, m_behaviourLabel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_argLabel)
                    .add(m_argCtrl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(14, 14, 14)
                .add(m_behaviourLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .add(15, 15, 15)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_returnLabel)
                    .add(m_returnCtrl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
        
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField m_argCtrl;
    private javax.swing.JLabel m_argLabel;
    private javax.swing.JLabel m_behaviourLabel;
    private javax.swing.JTextArea m_codeCtrl;
    private javax.swing.JTextField m_returnCtrl;
    private javax.swing.JLabel m_returnLabel;
    // End of variables declaration//GEN-END:variables
    
}
