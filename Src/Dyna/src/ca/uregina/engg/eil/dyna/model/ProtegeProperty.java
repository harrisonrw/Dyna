/*
 * ProtegeProperty.java
 *
 * Created on October 31, 2006, 7:14 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.model;

import ca.uregina.engg.eil.dyna.util.NULL;
import edu.stanford.smi.protege.model.Slot;
import edu.stanford.smi.protege.model.ValueType;

/**
 * Wrapper for Protege slot (property)
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class ProtegeProperty implements GenericProperty
{
    private Slot m_slot;
    
    /** Creates a new instance of ProtegeProperty */
    public ProtegeProperty(Slot slot)
    {
        m_slot = slot;
    }

    public String getName()
    {
        return m_slot.getName();
    }

    public ValueType getValueType()
    {
        return m_slot.getValueType();
    }

    public Object getObject()
    {
        return m_slot;
    }

    public Object getDefaultValue()
    {
        Object value = null;
        ValueType type = getValueType();
        
        if (type == ValueType.INTEGER)
        {
            value = 0;
        }
        else if (type == ValueType.FLOAT)
        {
            value = 0.0f;
        }
        else if (type == ValueType.STRING)
        {
            value = "";
        }
        else if (type == ValueType.BOOLEAN)
        {
            value = false;
        }
        else
        {
            value = new NULL();
        }
        
        return value;
    }
    
}
