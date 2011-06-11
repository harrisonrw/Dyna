/*
 * MyNode.java
 *
 * Created on September 17, 2006, 11:59 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import edu.stanford.smi.protege.model.KnowledgeBase;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Stack;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class MyNode
{    
    
    
    //protected static Hashtable<String, Object> m_symbolTable = new Hashtable<String, Object>();
    //protected static Hashtable<String, Object> m_instanceTable = new Hashtable<String, Object>();      
    //protected static Stack<Object> m_stack = new Stack<Object>();
            
    public void interpret() throws ParseException
    {
        // Override in child classes.
        throw new UnsupportedOperationException(); 
    }  
        
}
