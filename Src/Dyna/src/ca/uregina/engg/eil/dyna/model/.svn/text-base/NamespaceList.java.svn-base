/*
 * NamespaceList.java
 *
 * Created on March 12, 2007, 11:18 PM
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
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class NamespaceList
{
    protected Vector<Namespace> m_namespaces;
    
    /** Creates a new instance of NamespaceList */
    public NamespaceList()
    {
        m_namespaces = new Vector<Namespace>();
    }
    
    public void addNamespace(Namespace ns)
    {
        m_namespaces.add(ns);
    }
    
    public void removeNamespace(Namespace ns)
    {
        m_namespaces.remove(ns);
    }
    
    public void clear()
    {
        m_namespaces.clear();
    }
    
    public Vector<Namespace> getNamespaces()
    {
        return m_namespaces;
    }
    
    public Namespace get(int i)
    {
        return m_namespaces.get(i);
    }
    
    public int size()
    {
        return m_namespaces.size();
    }
    
    public boolean containsNamespace(String namespace)
    {
        boolean contains = false;
        
        for (int i = 0; i < m_namespaces.size(); i++)
        {
            Namespace ns = m_namespaces.get(i);
            
            if (ns.getURI().equals(namespace))
            {
                contains = true;
                break;
            }
        }
        
        return contains;
    }
    
    public Namespace getNamespaceByName(String name)
    {
        Namespace ns = null;
        
        for (int i = 0; i < m_namespaces.size(); i++)
        {
            Namespace temp = m_namespaces.get(i);
            
            if (temp.getURI().equals(name))
            {
                ns = temp;
                break;
            }
        }
        
        return ns;
    }
}
