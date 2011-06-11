/*
 * GarbageCollector.java
 *
 * Created on May 3, 2007, 7:44 PM
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

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class GarbageCollector
{
    
    /** Creates a new instance of GarbageCollector */
    public GarbageCollector()
    {
    }
    
    public void cleanStack(Stack stack, int scopeIndex)
    {
        boolean isClean = false;
        
        while (!isClean && !stack.empty())
        {
            Value v = (Value) stack.peek2();
            if (v.scopeIndex() >= scopeIndex)
            {
                stack.pop();
            }
            else
            {
                isClean = true;
            }
        }
    }
    
    /*
    public void cleanSymbolTable(SymbolTable symbolTable, int scopeIndex)
    {
        boolean isClean = false;
        
        Enumeration<String> keys = symbolTable.keys();        
        while (keys.hasMoreElements())
        {
            String key = (String) keys.nextElement();
            
            Value v = (Value) symbolTable.get2(key);
            
            if (v.scopeIndex() >= scopeIndex)
            {
                symbolTable.remove(key);
            }
        }
    }
     */
    
}
