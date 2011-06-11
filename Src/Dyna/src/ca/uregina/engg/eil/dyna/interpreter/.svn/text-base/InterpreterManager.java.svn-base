/*
 * InterpreterManager.java
 *
 * Created on November 19, 2006, 3:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ca.uregina.engg.eil.dyna.interpreter;

import java.util.Hashtable;

/**
 * Use this to ensure that only 1 interpreter is running at a time.
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class InterpreterManager
{
    private static InterpreterManager m_manager = null;
    
    private Hashtable<String, Interpreter> m_interpList;
    private String m_currInterpKey;
    
    /** Creates a new instance of InterpreterManager */
    public InterpreterManager()
    {
        m_interpList = new Hashtable<String, Interpreter>();
        m_currInterpKey = "";
    }
    
    public static InterpreterManager getInterpreterManager()
    {
        if (m_manager == null)
        {
            m_manager = new InterpreterManager();
        }
        
        return m_manager;
    }
    
    public void addInterpreter(String key, Interpreter interp)
    {
        m_interpList.put(key, interp);
    }
    
    public Interpreter getInterpreter()
    {
        return m_interpList.get(m_currInterpKey);
    }
    
    public String getInterpreterKey()
    {
        return m_currInterpKey;
    }
    
    public void switchInterpreter(String key)
    {
        m_currInterpKey = key;
    }
            
    
}
