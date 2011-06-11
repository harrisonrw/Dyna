/*
 * Objective.java
 *
 * Created on August 16, 2006, 3:36 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import ca.uregina.engg.eil.dyna.util.TaskPriorityComparator;

/**
 * Representation of an objective.
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class Objective extends KnowledgeComponent
{
    /**
     * List of task priorities (tasks and their priorities) within this objective.
     */
    protected Vector<TaskPriority> m_taskList;
    
    /**
     * Creates a new instance of Objective
     * @param name Name of the objective
     */
    public Objective(String name)
    {
        super(name);
        
        m_taskList = new Vector<TaskPriority>();
    }
    
    /**
     * Return a list of task priorities.
     * @return List of task priorities
     */
    public Vector<TaskPriority> getTaskPriorityList()
    {
        return m_taskList;
    }
    
    /**
     * Adds a task priority to the objective.
     * @param taskPriority Task priority to add
     */
    public void addTaskPriority(TaskPriority taskPriority)
    {
        m_taskList.add(taskPriority);   
        Collections.sort(m_taskList, new TaskPriorityComparator());
    }
    
    /**
     * Deletes a task priority from objective
     * @param taskPriority Task priority to delete
     */
    public void deleteTaskPriority(TaskPriority taskPriority)
    {
        m_taskList.remove(taskPriority);
    }
    
    public void setTaskPriority(Task task, int priority)
    {
        TaskPriority tp = getTaskPriority(task);
        tp.setPriority(priority);
        
        Collections.sort(m_taskList, new TaskPriorityComparator());
    }
    
    /**
     * Returns a task priority given a task.
     * @param task Task which is contained in the objective task priority
     * @return Task priority that contains the task
     */
    public TaskPriority getTaskPriority(Task task)
    {                
        for (int i = 0; i < m_taskList.size(); i++)
        {
            TaskPriority tp = m_taskList.get(i);
            if (tp.getTask().getName().equals(task.getName()))
            {
                return tp;
            }
        }
        
        return null;
    }
    
    /**
     * Checks if a task is contained in the objective.
     * @param task Task which is contained in the objective task priority
     * @return True if task in objective, otherwise False
     */
    public boolean containsTask(Task task)
    {        
        for (int i = 0; i < m_taskList.size(); i++)
        {
            TaskPriority tp = m_taskList.get(i);
            if (tp.getTask().getName().equals(task.getName()))
            {
                return true;
            }
        }
        
        return false;
    }
}
