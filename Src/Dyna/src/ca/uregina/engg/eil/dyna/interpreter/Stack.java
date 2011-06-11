/*
 * Stack.java
 *
 * Created on May 4, 2007, 12:35 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class Stack
{
    private static Stack m_s = null;
    
    protected java.util.Stack<Value> m_stack;
    protected int m_scopeIndex;
    
    /** Creates a new instance of Stack */
    public Stack()
    {
        m_stack = new java.util.Stack<Value>();
        m_scopeIndex = 0;
    }
    
    public static Stack getStack()
    {
        if (m_s == null)
        {
            m_s = new Stack();
        }
        
        return m_s;
    }
    
    public void setScopeIndex(int scopeIndex)
    {
        m_scopeIndex = scopeIndex;
    }
    
    public void push(Object value)
    {
        m_stack.push(new Value(value, m_scopeIndex));                
    }
    
    public Object pop()
    {
        Object value = null;
        
        if (!m_stack.empty())
        {        
            Value v = m_stack.pop();

            if (v != null)
            {
                value = v.value();
            }
        }
        
        return value;
    }
    
    public Object peek()
    {
        Object value = null;
        
        Value v = m_stack.peek();
        
        if (v != null)
        {
            value = v.value();
        }
        
        return value;
    }
    
    public Object peek2()
    {
        return m_stack.peek();
    }
    
    public void clear()
    {
        m_stack.clear();
    }
    
    public boolean empty()
    {
        return m_stack.empty();
    }
}
