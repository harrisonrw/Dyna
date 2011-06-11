/*
 * TaskWindow.java
 *
 * Created on October 21, 2006, 10:31 PM
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
import javax.swing.JInternalFrame;
import ca.uregina.engg.eil.dyna.controller.ProjectController;
import ca.uregina.engg.eil.dyna.controller.TaskController;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.resource.Resource;
import ca.uregina.engg.eil.dyna.util.StringSplitter;

/**
 * Displays data about a task
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TaskWindow extends JInternalFrame implements TopicEventListener, Resource
{
    private Task m_task;    
    
    private TaskGeneralPanel m_generalPanel;
    private BehaviourPanel m_behaviourPanel;
    private TaskDepPanel m_depPanel;
    private ObjectPanel m_objectPanel;
    private PreCondPanel m_preCondPanel;
    private TaskTestPanel m_testPanel;
    
    /** Creates new form TaskWindow */
    public TaskWindow(Task task)
    {
        super("Task : " + task.getName(), true, true, true, true);
        
        m_task = task;        
        
        initComponents();
        
        m_generalPanel = new TaskGeneralPanel(m_task);
        m_tabbedPane.add("General", m_generalPanel);
            
        m_behaviourPanel = new BehaviourPanel(m_task);
        m_tabbedPane.add("Behaviour", m_behaviourPanel);
        
        m_depPanel = new TaskDepPanel(m_task);
        m_tabbedPane.add("Dependencies", m_depPanel);
        
        m_objectPanel = new ObjectPanel(m_task);
        m_tabbedPane.add("Objects", m_objectPanel);
        
        m_preCondPanel = new PreCondPanel(m_task);
        m_tabbedPane.add("Preconditions", m_preCondPanel);
        
        m_testPanel = new TaskTestPanel(m_task);
        m_tabbedPane.add("Testing", m_testPanel);
        
        initEvents();
    }
    
    private void initEvents()
    {
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.PROJECT +
                                                   EventService.CLOSE, this);
        
        // Task Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK +
                                                   EventService.DELETE, this);
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK +
                                                   EventService.RENAME, this);
        
        
    }
    
    public Task getTask()
    {
        return m_task;
    }
    
    private void releaseResources()
    {
        m_generalPanel.releaseResources();
        m_depPanel.releaseResources();
        m_objectPanel.releaseResources();
        m_testPanel.releaseResources();
        
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.PROJECT +
                                                      EventService.CLOSE, this);
        
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.TASK +
                                                      EventService.DELETE, this); 
        
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.TASK +
                                                      EventService.RENAME, this);
    }
        
    private void taskDeleted(Task task)
    {
        if (task.getName().equals(m_task.getName()) || (m_task.getParent() != null && task.getName().equals(m_task.getParent().getName())))
        {
            releaseResources();
            dispose();
        }   
    }
       
    private void taskRenamed(Task task, String oldName)
    {
        String title = this.getTitle();
        String taskName = title.substring(7); // Name of task begins at char index 7.
        
        if (oldName.equals(taskName))
        {
            this.setTitle("Task : " + task.getName());                 
        }
    }
    
    private void projectClosed()
    {
        releaseResources();
        dispose();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        m_tabbedPane = new javax.swing.JTabbedPane();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setVisible(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener()
        {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt)
            {
                onClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt)
            {
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(m_tabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(m_tabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onClosing(javax.swing.event.InternalFrameEvent evt)//GEN-FIRST:event_onClosing
    {//GEN-HEADEREND:event_onClosing
        releaseResources();
    }//GEN-LAST:event_onClosing
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                ProjectController projectController = new ProjectController();
                
                Task task = new Task("Test");
                
                new TaskWindow(task).setVisible(true);
            }
        });
    }

    public void notify(TopicEvent event) throws Exception
    {
        String[] topic = StringSplitter.split(event.getTopic(), ".");
        Hashtable data = event.getData();
                
        if ((topic[0]+topic[1]).equals(EventService.FEEDBACK +
                                       EventService.TASK))
        {
            // Task Feedback Events.
            
            if (topic[2].equals(EventService.DELETE))
            {
                taskDeleted((Task) data.get(EventService.TASK_OBJECT));
            }     
            else if (topic[2].equals(EventService.RENAME))
            {
                taskRenamed((Task) data.get(EventService.TASK_OBJECT),
                            (String) data.get(EventService.TASK_NAME));
            }
        }   
        else if ((topic[0]+topic[1]).equals(EventService.FEEDBACK +
                                            EventService.PROJECT))
        {
            if (topic[2].equals(EventService.CLOSE))
            {
                projectClosed();
            }     
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane m_tabbedPane;
    // End of variables declaration//GEN-END:variables
    
}
