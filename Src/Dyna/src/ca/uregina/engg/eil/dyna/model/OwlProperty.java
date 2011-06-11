/*
 * OwlProperty.java
 *
 * Created on October 31, 2006, 8:37 PM
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
import edu.stanford.smi.protege.model.ValueType;
import edu.stanford.smi.protegex.owl.model.OWLDatatypeProperty;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;
import edu.stanford.smi.protegex.owl.model.OWLProperty;
import edu.stanford.smi.protegex.owl.model.RDFResource;
import edu.stanford.smi.protegex.owl.model.RDFSClass;
import edu.stanford.smi.protegex.owl.model.RDFSDatatype;
import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLDataRange;

/**
 * Wrapper for Protege-OWL property
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class OwlProperty implements GenericProperty
{
    private static final int DATA_PROPERTY = 0;
    private static final int OBJECT_PROPERTY = 1;
    
    private int m_type;        
    private Object m_property;
    
    /** Creates a new instance of OwlProperty */
    public OwlProperty(OWLDatatypeProperty property)
    {
        m_property = property;
        m_type = DATA_PROPERTY;
    }
    
    public OwlProperty(OWLObjectProperty property)
    {
        m_property = property;
        m_type = OBJECT_PROPERTY;
    }

    public String getName()
    {                    
        return ((OWLProperty) m_property).getName();
    }

    public ValueType getValueType()
    {
        // TODO: convert domain into int, float, etc
        OWLModel owlModel = ((OWLProperty)m_property).getOWLModel();
        
        ValueType type = null;
        
        if (m_property instanceof OWLDatatypeProperty)
        {
            //type = owlModel.getOWLValueType(((OWLDatatypeProperty)m_property).getRange().getURI());
            RDFResource rdfType = ((OWLDatatypeProperty) m_property).getRange();            
            String name = rdfType.getName();
            
            String b = rdfType.getBrowserText();
            
            if (name.equals("xsd:string"))
                type = ValueType.STRING;
            else if (name.equals("xsd:integer"))
                type = ValueType.INTEGER;
            else if (name.equals("xsd:float"))
                type = ValueType.FLOAT;
            else if (name.equals("xsd:boolean"))
                type = ValueType.BOOLEAN;                        
        }
        
        //owlModel.getOWLValueType();
        //DefaultOWLDataRange res = new DefaultOWLDataRange(((OWLProperty)m_property).getRange());
        //System.out.println(res.getURI());
        
        //return owlModel.getOWLValueType(((OWLProperty)m_property).getRange().getURI());
        return type;
                
    }

    public Object getObject()
    {
        return m_property;
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
            value = ""; //new NULL();
        }
        
        return value;
    }
    
}
