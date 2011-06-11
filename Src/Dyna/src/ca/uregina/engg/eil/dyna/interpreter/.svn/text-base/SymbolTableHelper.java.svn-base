/*
 * SymbolTableHelper.java
 *
 * Created on May 18, 2007, 1:46 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

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
public class SymbolTableHelper
{
    //private static SymbolTableHelper m_symbolTableHelper = null;
    
    /**
     * Creates a new instance of SymbolTableHelper
     */
    public SymbolTableHelper()
    {
    }
    
    /*
    public static SymbolTableHelper getSymbolTableHelper()
    {
        if (m_symbolTableHelper == null)
        {
            m_symbolTableHelper = new SymbolTableHelper();
        }
        
        return m_symbolTableHelper;
    }
     */
    
    public static Hashtable<String, Object> getSymbolTable()
    {
        CallStack callStack = CallStack.getCallStack();
        return callStack.getTop().getSymbolTable();
    }
    
}
