/*
 * ProtegeKB.java
 *
 * Created on October 31, 2006, 6:52 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.model;

import edu.stanford.smi.protege.model.Cls;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Slot;
import edu.stanford.smi.protege.model.ValueType;
import java.util.Collection;
import java.util.Vector;

/**
 * Wrapper for Protege knowledge base
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class ProtegeKB implements GenericKB
{
    private KnowledgeBase m_kb;
    
    /** Creates a new instance of ProtegeKB */
    public ProtegeKB(KnowledgeBase kb)
    {
        m_kb = kb;
    }

    public GenericInstance createInstance(String clsName, String instName)
    {
        Cls cls = m_kb.getCls(clsName);
        Instance instance = cls.createDirectInstance(instName);
                
        // Set slots.
        Object[] slotList = cls.getTemplateSlots().toArray();
        for (int i = 0; i < slotList.length; i++)
        {                      
            Slot slot = (Slot) slotList[i];
            ValueType type = slot.getValueType();
            
            Object value = null;
                
            if (type == ValueType.INTEGER)
            {
                value = new Integer(0);
            }
            else if (type == ValueType.FLOAT)
            {
                value = new Float(0.0f);
            }
            else if (type == ValueType.BOOLEAN)
            {
                value = new Boolean(false);
            }
            else if (type == ValueType.STRING)
            {
                value = new String("");
            }
            else if (type == ValueType.CLS)
            {
                
            }
            
            instance.setDirectOwnSlotValue(slot, value);            
        }   
        
        ProtegeInstance protegeInstance = new ProtegeInstance(instance);
        return protegeInstance;        
    }

    public GenericProperty[] getPropertyList(String clsName)
    {
        Cls cls = m_kb.getCls(clsName);
        //m_kb.getSuperclasses();
        Object[] objects = cls.getTemplateSlots().toArray(); //getOwnSlots().toArray();
                        
        ProtegeProperty[] slots = null;
        
        if (objects.length > 0)
        {
            slots = new ProtegeProperty[objects.length];

            for (int i = 0; i < objects.length; i++)
            {
                slots[i] = new ProtegeProperty((Slot)objects[i]);
            }        
        }
        
        return slots;     
    }

    public GenericProperty getProperty(String propertyName)
    {
        Slot slot = m_kb.getSlot(propertyName);
        
        ProtegeProperty protegeProperty = new ProtegeProperty(slot);
        return protegeProperty;
    }

    public boolean containsInstance(String instName) 
    {
        boolean exists = false;
        
        Instance instance = m_kb.getInstance(instName);
        
        if (instance != null)
        {
            exists = true;
        }
        
        return exists;
    }

    public GenericInstance getInstance(String instName)
    {
        ProtegeInstance instance = new ProtegeInstance(m_kb.getInstance(instName));
        return instance;
    }

    public String[] getClassNames()
    {
        String[] classList = null;
        
        Collection c = m_kb.getClses();
        
        // The collection contains all the system classes.  
        // Remove all the system classes.
        Object[] protegeClassList = c.toArray();
        Vector<String> classVector = new Vector<String>();
        for (int j = 0; j < protegeClassList.length; j++)
        {
            Cls cls = (Cls) protegeClassList[j];
            char firstChar = cls.getName().charAt(0);
            if (firstChar != ':')
            {
                classVector.add(cls.getName());
            }
        }
                
        classList = new String[classVector.size()];
        
        for (int i = 0; i < classVector.size(); i++)
        {
            classList[i] = classVector.get(i);
        }
        
        return classList;               
    }
    
}
