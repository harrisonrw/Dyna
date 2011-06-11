/*
 * TaskPriority.java
 *
 * Created on August 16, 2006, 3:39 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.model;


/**
 * Representation of a Task Priority.
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TaskPriority extends KnowledgeComponent
{
    /**
     * The Task.
     */
    protected Task m_task;
    
    /**
     * Priority of the Task.
     */
    protected int m_priority;
    
    /**
     * Creates a new instance of TaskPriority
     * @param task The task
     * @param priority The task's priority
     */
    public TaskPriority(Task task, int priority)
    {
        // Task Priority does not have any idenfier.  In Dyna, task name is used to identify
        // task priority in the Objective's Task Priority list box.  Each OWL Individual
        // requires it's own ID.  So task name cannot be used as it is already used
        // for a Task.  A simple solution to this problem is to use the task with with
        // the prefix "tp_" to identify task priority.  For example, "tp_someTask".
        super("tp_" + task.getName());
        
        m_task = task;
        m_priority = priority;
    }
    
    /**
     * Returns task
     * @return The task
     */
    public Task getTask()
    {
        return m_task;
    }
    
    /**
     * Sets the task.
     * @param task The task
     */
    public void setTask(Task task)
    {
        m_task = task;
    }
    
    /**
     * Returns the task's priority.
     * @return The task's priority
     */
    public int getPriority()
    {
        return m_priority;
    }
    
    /**
     * Sets the task's priority.
     * @param priority The task's priority
     */
    public void setPriority(int priority)
    {
        m_priority = priority;
    }
                   
}
