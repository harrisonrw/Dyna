/*
 * LeveledStack.java
 *
 * Created on May 3, 2007, 1:38 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.util;

import java.util.Stack;
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
public class LeveledStack
{
    
    protected Vector<Stack<Object>> m_stackList;
    
    /** Creates a new instance of LeveledStack */
    public LeveledStack()
    {
        m_stackList = new Vector<Stack<Object>>();        
    }
        
    
    public void push(Object val)
    {
        Stack<Object> stack = null;
        
        if (m_stackList.size() == 0)
        {
            stack = new Stack<Object>();                        
        }
        else
        {
            stack = m_stackList.get(m_stackList.size() - 1);
        }
        
        stack.push(val);
    }
    
    public Object pop()
    {
        Object val = null;
        
        Stack<Object> stack = null;
        
        if (m_stackList.size() > 0)
        {                                          
            stack = m_stackList.get(m_stackList.size() - 1);            
            val = stack.pop();
        }
                        
        return val;
    }
    
    public void push_level()
    {
        Stack<Object> stack = new Stack<Object>();
        m_stackList.add(stack);
    }
    
    public void pop_level()
    {
        m_stackList.remove(m_stackList.size() - 1);
    }
    
    
}
