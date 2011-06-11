/*
 * Workspace.java
 *
 * Created on August 20, 2006, 7:54 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.ui;

import java.beans.PropertyVetoException;
import java.util.Hashtable;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import ca.uregina.engg.eil.dyna.controller.ObjectiveController;
import ca.uregina.engg.eil.dyna.controller.TaskController;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.Objective;
import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.util.StringSplitter;

/**
 * An area to hold child windows.
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class Workspace extends JDesktopPane implements TopicEventListener
{   
    
    /** Creates a new instance of Workspace */
    public Workspace()
    {
        super();
                                
        initEvents();
    }
    
    private void initEvents()
    {
        // Task Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK +
                                                   EventService.OPEN, this);
        
        // Objective Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.OBJECTIVE +
                                                   EventService.OPEN, this);
    }

    public void openTask(Task task)
    {        
        // Deselect selected window.
        JInternalFrame window = getSelectedFrame();
        try
        {
            if (window != null)
                window.setSelected(false);
        } 
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        
        // Add the task window.
        TaskWindow taskWindow = new TaskWindow(task);                 
        add(taskWindow);
        
                
        // Select the new task window.
        taskWindow.moveToFront();
        setSelectedFrame(taskWindow);
        try
        {
            taskWindow.setSelected(true);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
                
    }                        
        
    public void openObjective(Objective objective)
    {
        // Deselect selected window.
        JInternalFrame window = getSelectedFrame();
        try
        {
            if (window != null)
                window.setSelected(false);
        } 
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        
        // Add the objective window.
        ObjectiveWindow objectiveWindow = new ObjectiveWindow(objective);        
        add(objectiveWindow); 
            
        // Select the new task window.
        objectiveWindow.moveToFront();
        setSelectedFrame(objectiveWindow);
        try
        {
            objectiveWindow.setSelected(true);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }                
    }
       
    public void notify(TopicEvent event) throws Exception
    {
        String[] topic = StringSplitter.split(event.getTopic(), ".");
        Hashtable data = event.getData();
        
        if ((topic[0]+topic[1]).equals(EventService.FEEDBACK +
                                       EventService.TASK))
        {
            // Task Feedback Events.
            
            if (topic[2].equals(EventService.OPEN))
            {
                openTask((Task) data.get(EventService.TASK_OBJECT));
            }                             
        }   
        else if ((topic[0]+topic[1]).equals(EventService.FEEDBACK +
                                            EventService.OBJECTIVE))
        {
            // Objective Feedback Events.
            
            if (topic[2].equals(EventService.OPEN))
            {
                openObjective((Objective) data.get(EventService.OBJECTIVE_OBJECT));
            }                             
        }  
    }
    
    
}
