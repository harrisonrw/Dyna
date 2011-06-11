/*
 * TaskGeneralPanel.java
 *
 * Created on October 22, 2006, 8:39 PM
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.util.StringSplitter;

/**
 * Displays general info for a task
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TaskGeneralPanel extends GeneralPanel implements TopicEventListener
{
    private Task m_task;
    
    
    /** Creates a new instance of TaskGeneralPanel */
    public TaskGeneralPanel(Task task)
    {
        super();
        
        m_task = task;
        
        // Set Verifiers.
        m_nameCtrl.setInputVerifier(new NameVerifier());
        m_docCtrl.setInputVerifier(new DocumentationVerifier());
        
        // Fill the controls with data.        
        m_nameCtrl.setText(m_task.getName());
        m_docCtrl.setText(m_task.getDocumentation()); 
        
        initEvents();
    }
    
    private void initEvents()
    {
        // Task Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK +
                                                   EventService.RENAME, this);
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK +
                                                   EventService.EDIT_DOC, this);
    }
    
    public void releaseResources()
    {
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.TASK +
                                                      EventService.RENAME, this);
        
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.TASK +
                                                      EventService.EDIT_DOC, this);
    }

    public void notify(TopicEvent event) throws Exception
    {
        String[] topic = StringSplitter.split(event.getTopic(), ".");
        Hashtable data = event.getData();
                
        if ((topic[0]+topic[1]).equals(EventService.FEEDBACK +
                                       EventService.TASK))
        {
            // Task Feedback Events.
            
            if (topic[2].equals(EventService.RENAME))
            {
                taskRenamed((Task) data.get(EventService.TASK_OBJECT),  // Modified task
                            (String) data.get(EventService.TASK_NAME)); // Old task name.
            }    
            else if (topic[2].equals(EventService.EDIT_DOC))
            {
                docEdited((Task) data.get(EventService.TASK_OBJECT),  
                          (String) data.get(EventService.DOCUMENTATION));
            }
        }   
        
    }
    
    private void taskRenamed(Task task, String oldTaskName)
    {
        if (oldTaskName.equals(m_nameCtrl.getText()))
        {
            m_nameCtrl.setText(task.getName());        
        }
    }
    
    private void docEdited(Task task, String documentation)
    {        
        if (task.getName().equals(m_task.getName()))
        {
            m_docCtrl.setText(documentation);                        
        }
    }
    
    private void fireRenameTaskCommand(Task task, String newName)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.TASK_NAME, newName);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TASK + 
                                              EventService.RENAME, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot rename task! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireEditDocCommand(Task task, String documentation)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.DOCUMENTATION, documentation);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TASK + 
                                              EventService.EDIT_DOC, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot edit documentation! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    class NameVerifier extends InputVerifier 
    {
         public boolean verify(JComponent input) 
         {
            boolean isValid = false;

            JTextField tf = (JTextField) input;
            
            if (! tf.getText().equals(""))
            {
                
                isValid = true;
                                
                fireRenameTaskCommand(m_task, tf.getText());
            }

            return isValid;
         }
     }
    
    class DocumentationVerifier extends InputVerifier
    {
        public boolean verify(JComponent input) 
         {
            boolean isValid = false;

            JTextArea ta = (JTextArea) input;
            
            //if (! ta.getText().equals(""))
            //{
                
                isValid = true;
                                
                fireEditDocCommand(m_task, ta.getText());
            //}

            return isValid;
         }
    }
    
}
