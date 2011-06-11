/*
 * InstanceTable.java
 *
 * Created on May 4, 2007, 1:37 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class InstanceTable
{
    private static InstanceTable m_instanceTable = null;
    
    protected Hashtable<String, Value> m_table;
    protected int m_scopeIndex;
    
    /** Creates a new instance of InstanceTable */
    public InstanceTable()
    {
        m_table = new Hashtable<String, Value>();
        m_scopeIndex = 0;
    }
    
    public static InstanceTable getInstanceTable()
    {
        if (m_instanceTable == null)
        {
            m_instanceTable = new InstanceTable();
        }
        
        return m_instanceTable;
    }
    
    public void setScopeIndex(int scopeIndex)
    {
        m_scopeIndex = scopeIndex;
    }
    
    public void put(String key, Object value)
    {
        m_table.put(key, new Value(value, m_scopeIndex));
    }
    
    public Object get(String key)
    {
        Object obj = null;
        
        Value value = m_table.get(key);
        if (value != null)
        {
            obj = value.first();
        }
        
        return obj;
    }
    
    public Enumeration<String> keys()
    {
         return m_table.keys(); 
    }
    
    
}
