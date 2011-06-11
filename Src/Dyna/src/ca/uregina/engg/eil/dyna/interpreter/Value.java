/*
 * Value.java
 *
 * Created on May 3, 2007, 7:27 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import ca.uregina.engg.eil.dyna.util.Pair;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class Value extends Pair
{
    
    /** Creates a new instance of Value */
    public Value(Object value, int scopeIndex)
    {
        super(value, scopeIndex);
    }
    
    public Object value()
    {
        return m_first;
    }
    
    public int scopeIndex()
    {
        return ((Integer) m_second).intValue();
    }
    
    public void setValue(Object value)
    {
        m_first = value;
    }
           
    public void setScopeIndex(int scopeIndex)
    {
        m_second = scopeIndex;
    }
}
