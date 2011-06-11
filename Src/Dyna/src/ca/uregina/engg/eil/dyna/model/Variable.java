/*
 * Variable.java
 *
 * Created on April 20, 2007, 10:56 AM
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
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class Variable
{
    protected String m_name;
    protected String m_type;
    
    /** Creates a new instance of Variable */
    public Variable(String name, String type)
    {
        m_name = name;
        m_type = type;
    }
    
    public void setName(String name)
    {
        m_name = name;
    }
    
    public String getName()
    {
        return m_name;
    }
    
    public void setType(String type)
    {
        m_type = type;
    }
    
    public String getType()
    {
        return m_type;
    }
    
    public String toString()
    {
        return m_type + " " + m_name;
    }
    
}
