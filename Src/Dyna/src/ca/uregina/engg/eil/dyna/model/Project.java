/*
 * Project.java
 *
 * Created on September 1, 2006, 11:13 AM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.model;

import java.util.Vector;

/**
 * Contains data for a Dyna project.
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class Project extends KnowledgeComponent
{
    protected Vector<Task> m_tasks;
    protected Vector<Objective> m_objectives;
    protected String m_url;
    protected NamespaceList m_namespaceList;
    
    /** Creates a new instance of Project */
    public Project(String name)
    {
        super(name);
        
        m_tasks = new Vector<Task>();
        m_objectives = new Vector<Objective>();
        m_url = "";
        m_namespaceList = new NamespaceList();
    }
    
    public Vector<Task> getTasks()
    {
        return m_tasks;
    }
    
    public Vector<Objective> getObjectives()
    {
        return m_objectives;
    }
    
    public Task getTask(String taskName)
    {
        Task task = null;
        for (int i = 0; i < m_tasks.size(); i++)
        {
            task = getTask(taskName, m_tasks.get(i));
                        
            if (task != null)
            {
                break;
            }            
        }
        
        return task;      
    }
    
    public Task getTask(String taskName, Task parent)
    {
        Task task = null;
        
        if (parent.getName().equals(taskName))
        {
            task = parent;
        }
        else
        {
            Vector<Task> taskList = parent.getSubTaskList();
            for (int i = 0; i < taskList.size(); i++)
            {
                task = getTask(taskName, taskList.get(i));
                
                if (task != null)
                    break;
            }
        }
                        
        return task;
    }
    
    public void setURL(String url)
    {
        m_url = url;
    }
    
    public String getURL()
    {
        return m_url;
    }
    
    public NamespaceList getNamespaceList()
    {
        return m_namespaceList;
    }
    
    public void setNamespaceList(NamespaceList namespaceList)
    {
        m_namespaceList = namespaceList;
    }
    
}
