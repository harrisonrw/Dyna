/*
 * DependencyList.java
 *
 * Created on September 3, 2006, 8:26 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.util;

import java.util.Vector;
import ca.uregina.engg.eil.dyna.model.Task;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class DependencyList
{
    protected Task m_task;
    protected Vector<String> m_depList;
    
    /** Creates a new instance of DependencyList */
    public DependencyList(Task task)
    {
        m_task = task;
        m_depList = new Vector<String>();
    }
    
    public Task getTask()
    {
        return m_task;
    }
    
    public Vector<String> getDepList()
    {
        return m_depList;
    }
    
    public void addDep(String depName)
    {
        m_depList.add(depName);
    }
    
}
