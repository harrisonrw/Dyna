/*
 * CallItem.java
 *
 * Created on May 9, 2007, 9:17 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import ca.uregina.engg.eil.dyna.model.KnowledgeComponent;
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
public class CallItem
{
    
    private KnowledgeComponent m_item;
    private Hashtable<String, Object> m_symbolTable;
    
    /**
     * Creates a new instance of CallItem
     */
    public CallItem()
    {
        m_item = null;
        m_symbolTable = new Hashtable<String, Object>();
    }
    
    public CallItem(KnowledgeComponent item)
    {
        m_item = item;
        m_symbolTable = new Hashtable<String, Object>();
    }
    
    // Deep Copy constructer.
    public CallItem(CallItem callItem)
    {                
        m_item = callItem.getItem();
        m_symbolTable = new Hashtable<String, Object>();
        
        // Copy the symbol table.
        Hashtable<String, Object> symbolTable2 = callItem.getSymbolTable();
        Enumeration<String> keys = symbolTable2.keys();        
        while (keys.hasMoreElements())
        {
            String key = (String) keys.nextElement();

            // Put the new value into symbol table.
            m_symbolTable.put(key, symbolTable2.get(key));
        }
    }
    
    public void setItem(KnowledgeComponent item)
    {
        m_item = item;
    }
    
    public KnowledgeComponent getItem()
    {
        return m_item;       
    }
    
    public Hashtable<String, Object> getSymbolTable()
    {
        return m_symbolTable;
    }
    
    
}
