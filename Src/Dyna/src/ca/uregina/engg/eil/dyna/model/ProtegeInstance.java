/*
 * ProtegeInstance.java
 *
 * Created on October 31, 2006, 6:59 PM
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
import edu.stanford.smi.protege.model.Slot;
import edu.stanford.smi.protege.model.ValueType;

/**
 * Wrapper for Protege instance.
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class ProtegeInstance implements GenericInstance
{
    private Instance m_instance;
    
    /** Creates a new instance of ProtegeInstance */
    public ProtegeInstance(Instance instance)
    {
        m_instance = instance;
    }

    public void setPropertyValue(GenericProperty property, Object value)
    {
        Slot slot = (Slot) property.getObject();
        if (property.getValueType() == ValueType.CLS)
        {
            ProtegeInstance proInst = (ProtegeInstance) value;
            m_instance.setOwnSlotValue(slot, proInst.getInstance());
        }
        else
        {
            m_instance.setOwnSlotValue(slot, value);
        }
    }
    
    public Object getPropertyValue(GenericProperty property)
    {
        Slot slot = (Slot) property.getObject();
        
        Object value = m_instance.getOwnSlotValue(slot);
        
        if (property.getValueType() == ValueType.CLS)
        {            
            ProtegeKB kb = new ProtegeKB(slot.getKnowledgeBase());
            value = kb.getInstance(((Instance) value).getName());            
        }        
        
        return value;
    }

    public String getURI()
    {
        // Protege does not have URI's, so this is a workaround.
        return "#" + m_instance.getName();
    }

    public String getClassName()
    {        
        Cls cls = m_instance.getDirectType();        
        String className = cls.getName();
        
        return className; 
    }
    
    public String getName()
    {
        return m_instance.getName();
    }

    public String getNamespace()
    {
        return "";
    }
    
    public Instance getInstance()
    {
        return m_instance;
    }
    
    public String toString()
    {
        return m_instance.getName();
    }
    
    public Object getType()
    {
        return m_instance.getDirectType();
    }
}
