/*
 * Task.java
 *
 * Created on July 31, 2006, 11:29 PM
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
import java.util.Vector;

/**
 * Representation of a Task
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class Task extends KnowledgeComponent
{    
    /**
     * The parent of this task.
     */
    protected Task m_parentTask;
    
    /**
     * List of sub or child tasks.
     */
    protected Vector<Task> m_subTaskList;
    
    /**
     * List of tasks this task is dependent on.
     */
    protected Vector<Task> m_depList;
    
    protected Vector<Variable> m_args;
    protected String m_behaviour;
    protected Variable m_return;
    
    protected Vector<GenericInstance> m_objectList;
    
    protected String m_preCondition;
    
    protected KnowledgeTestSuite m_testSuite;
    
    /**
     * Creates a new instance of Task
     * @param name Name of the task
     */
    public Task(String name)
    {
        super(name);
        
        m_parentTask = null;
        m_subTaskList = new Vector<Task>();
        m_depList = new Vector<Task>();
        m_args = new Vector<Variable>();
        m_behaviour = "";
        m_return = null;
        m_objectList = new Vector<GenericInstance>();
        m_preCondition = "";
        m_testSuite = new KnowledgeTestSuite("ts_" + name);
    }
            
    public void setParent(Task parent)
    {
        m_parentTask = parent;
    }
    
    public Task getParent()
    {
        return m_parentTask;
    }
    
    /**
     * Adds a sub task.
     * @param subTask The sub task
     */
    public void addSubTask(Task subTask)
    {     
        subTask.setParent(this);
        m_subTaskList.add(subTask);
    }
    
    /**
     * Removes a sub task.
     * @param subTask The sub task
     */
    public void deleteSubTask(Task subTask)
    {
         m_subTaskList.remove(subTask);
    }
    
    /**
     * Returns a list of sub tasks.
     * @return List of sub tasks
     */
    public Vector<Task> getSubTaskList()
    {
        return m_subTaskList;
    }
    
    /**
     * Returns number of sub tasks.
     * @return Number of sub tasks
     */
    public int getSubTaskCount()
    {
        return m_subTaskList.size();
    }
    
    /**
     * Returns a sub task at the specified index.
     * @param i Index of sub task to return
     * @return Sub task
     */
    public Task getSubTaskAt(int i) 
    {
        return (Task)m_subTaskList.elementAt(i);
    }
    
    /**
     * Returns index of sub task.
     * @param subTask Sub task to find index of
     * @return Index of sub task
     */
    public int getIndexOfSubTask(Task subTask)
    {
        return m_subTaskList.indexOf(subTask);    
    }
    
    /**
     * Adds dependency.
     * @param dep Dependent task to add
     */
    public void addDependency(Task dep)
    {
        m_depList.add(dep);
    }
    
    /**
     * Deletes dependency.
     * @param dep Dependent task to delete
     */
    public void deleteDependency(Task dep)
    {
        m_depList.remove(dep);
    }
    
    public void deleteAllDependencies()
    {
        m_depList.clear();
    }
    
    /**
     * Returns list of dependencies.
     * @return List of dependencies
     */
    public Vector<Task> getDependencies()
    {
        return m_depList;
    }
       
    public void addArg(String name, String type)
    {
        Variable var = new Variable(name, type);
        m_args.add(var);        
    }
    
    public Vector<Variable> getArgs()
    {
        return m_args;
    }
    
    public String[] getArgs2()
    {
        String[] argList = new String[m_args.size()];
        
        for (int i = 0; i < m_args.size(); i ++)
        {
            argList[i] = m_args.get(i).toString();         
        }
        
        return argList;
    }
    
    public String getArgsAsString()
    {
        String strArgs = "";
        for (int i = 0; i < m_args.size(); i++)
        {
            if (i != 0)
            {
                strArgs += ", ";
            }
            
            strArgs += m_args.get(i).toString();
        }
        
        return strArgs;
    }
     
    public void clearArgs()
    {
        m_args.clear();
    }
    
    public void setBehaviour(String behaviour)
    {
        m_behaviour = behaviour;
    }
    
    public String getBehaviour()
    {
        return m_behaviour;
    }
    
    public void setReturn(String name, String type)
    {
        Variable var = new Variable(name, type);
        m_return = var;
    }
    
    public void setReturn(Variable ret)
    {
        m_return = ret;
    }
    
    public Variable getReturn()
    {
        return m_return;
    }
    
    public String getReturnId()
    {
        String r = "";
        
        if (m_return != null)
        {
            r = m_return.getName();
        }
        
        return r;
    }
    
    public void addObject(GenericInstance obj)
    {
        m_objectList.add(obj);
    }
        
    public void deleteObject(GenericInstance obj)
    {
        m_objectList.remove(obj);
    }
    
    public void deleteAllObjects()
    {
        m_objectList.clear();
    }
    
    public Vector<GenericInstance> getObjects()
    {
        return m_objectList;
    }
    
    public void setPreCondition(String preCondition)
    {
        m_preCondition = preCondition;
    }
    
    public String getPreCondition()
    {
        return m_preCondition;
    }
    
    public void setTestSuite(KnowledgeTestSuite testSuite)
    {
        m_testSuite = testSuite;
    }
    
    public KnowledgeTestSuite getTestSuite()
    {
        return m_testSuite;
    }
    
}
