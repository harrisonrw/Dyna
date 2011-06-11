/*
 * CallStack.java
 *
 * Created on May 8, 2007, 12:52 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import ca.uregina.engg.eil.dyna.model.Task;
import java.util.Enumeration;
import java.util.Hashtable;
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
public class CallStack
{
    
    private static CallStack m_callStack = null;
    
    // We use a list so we can iterate through the items.
    private Vector<CallItem> m_list;
    
    /**
     * Creates a new instance of CallStack
     */
    public CallStack()
    {
        m_list = new Vector<CallItem>();
    }
    
    public static CallStack getCallStack()
    {
        if (m_callStack == null)
        {
            m_callStack = new CallStack();
        }
        
        return m_callStack;
    }
    
    public void push(CallItem item, boolean copyBelow)
    {
        if (copyBelow)
        {
            // If the top is the same as this item, then add top's symbol table to the item's
            CallItem top = getTop();
            if (top != null)
            {
                if (top.getItem() != null && item.getItem() != null)
                {
                    
                     // Are we in the same function?
                    boolean sameFunc = top.getItem().getName().equals(item.getItem().getName());
                    
                    // Add symbol table.
                    Hashtable<String, Object> topSymbolTable = top.getSymbolTable();
                    Hashtable<String, Object> symbolTable = item.getSymbolTable();

                    Enumeration<String> keys = topSymbolTable.keys();        
                    while (keys.hasMoreElements())
                    {
                        String key = (String) keys.nextElement();

                        if (sameFunc || (!sameFunc && key.contains(".")))
                        {
                            // Put the new value into symbol table.
                            symbolTable.put(key, topSymbolTable.get(key));
                        }
                    }

                   
                }
            }
        }
        
        m_list.add(item);
    }
    
    public void push(CallItem item)
    {
        push(item, false);               
    }
    
    public void pop()
    {
        CallItem top = m_list.lastElement();
        
        // If the top is the same as the item below, update the matching keys in the symbol table.
        // Also update any objects in the symbol table.
        if (m_list.size() > 1)
        {
            CallItem newTop = m_list.elementAt(m_list.size() - 2);
               
            if (newTop != null)
            {
                Hashtable<String, Object> topSymbolTable = top.getSymbolTable();
                Hashtable<String, Object> symbolTable = newTop.getSymbolTable();
                
                // Are we in the same function?
                boolean sameFunc = newTop.getItem().getName().equals(top.getItem().getName());
                
                Enumeration<String> keys = topSymbolTable.keys();        
                while (keys.hasMoreElements())
                {
                    String key = (String) keys.nextElement();
                        
                    if ((sameFunc || (!sameFunc && key.contains("."))) && symbolTable.containsKey(key))
                    {                
                        // Update the value in the symbol table.
                        symbolTable.put(key, topSymbolTable.get(key));
                    }
                }
            }
                        
        }
        
        
        m_list.remove(top);
    }
    
    public CallItem getTop()
    {
        if (m_list.size() >= 1)
        {
            return m_list.lastElement();
        }
        
        return null;                          
    }
    
    public Vector<CallItem> getItems()
    {
        return m_list;
    }
    
    public void clear()
    {
        m_list.clear();
    }
}
