/*
 * Pair.java
 *
 * Created on May 3, 2007, 7:24 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.util;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class Pair
{
    protected Object m_first;
    protected Object m_second;
    
    /** Creates a new instance of Pair */
    public Pair()
    {
        m_first = null;
        m_second = null;
    }
    
    public Pair(Object first, Object second)
    {
        m_first = first;
        m_second = second;
    }
    
    public Object first()
    {
        return m_first;
    }
    
    public Object second()
    {
        return m_second;
    }
    
    public void setFirst(Object first)
    {
        m_first = first;
    }
    
    public void setSecond(Object second)
    {
        m_second = second;
    }
    
}
