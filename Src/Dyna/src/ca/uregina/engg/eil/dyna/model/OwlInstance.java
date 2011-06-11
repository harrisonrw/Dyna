/*
 * OwlInstance.java
 *
 * Created on October 31, 2006, 8:24 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.model;

import edu.stanford.smi.protegex.owl.model.OWLClass;
import edu.stanford.smi.protegex.owl.model.OWLDatatypeProperty;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;
import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLDatatypeProperty;
import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLIndividual;
import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLObjectProperty;
import java.util.Collection;

/**
 * Wrapper for Protege-OWL instances.
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class OwlInstance implements GenericInstance
{
    private OWLIndividual m_individual;
    
    /** Creates a new instance of OwlInstance */
    public OwlInstance(OWLIndividual individual)
    {
        m_individual = individual;
    }

    public void setPropertyValue(GenericProperty property, Object value)
    {
        Object propObject = property.getObject();               
        
        if (propObject instanceof OWLDatatypeProperty)
        {
            OWLDatatypeProperty dp = (OWLDatatypeProperty) propObject;
            m_individual.setPropertyValue(dp, value);            
        }
        else if (propObject instanceof DefaultOWLDatatypeProperty)
        {
            DefaultOWLDatatypeProperty dp = (DefaultOWLDatatypeProperty) propObject;
            m_individual.setPropertyValue(dp, value); 
        }
        else if (propObject instanceof OWLObjectProperty)
        {
            OWLObjectProperty objProp = (OWLObjectProperty) propObject;
            
            OwlInstance inst = (OwlInstance) value;
            OWLIndividual ind = inst.getIndividual();
            
            m_individual.setPropertyValue(objProp, ind); 
        }
        else if (propObject instanceof DefaultOWLObjectProperty)
        {
            DefaultOWLObjectProperty objProp = (DefaultOWLObjectProperty) propObject;
            
            OwlInstance inst = (OwlInstance) value;
            OWLIndividual ind = inst.getIndividual();
            
            m_individual.setPropertyValue(objProp, ind); 
        }
        
    }
    
    public Object getPropertyValue(GenericProperty property)
    {
        Object value = null;
        
        Object propObject = property.getObject();
        
        if (propObject instanceof OWLDatatypeProperty)
        {
            OWLDatatypeProperty dp = (OWLDatatypeProperty) propObject;
            value = m_individual.getPropertyValue(dp);                        
        }
        else if (propObject instanceof DefaultOWLDatatypeProperty)
        {
            DefaultOWLDatatypeProperty dp = (DefaultOWLDatatypeProperty) propObject;
            value = m_individual.getPropertyValue(dp);              
        }
        else if (propObject instanceof OWLObjectProperty)
        {
            OWLObjectProperty objProp = (OWLObjectProperty) propObject;      
            
            Object indValue = m_individual.getPropertyValue(objProp);
                                    
            if (indValue instanceof OWLIndividual)
            {
                OwlKB kb = new OwlKB(((OWLIndividual) indValue).getOWLModel());
                value = kb.getInstance(((OWLIndividual) indValue).getName());
            }
            else if (indValue instanceof DefaultOWLIndividual)
            {
                OwlKB kb = new OwlKB(((DefaultOWLIndividual) indValue).getOWLModel());
                value = kb.getInstance(((DefaultOWLIndividual) indValue).getName());
            }
                        
                        
        }
        else if (propObject instanceof DefaultOWLObjectProperty)
        {
            DefaultOWLObjectProperty objProp = (DefaultOWLObjectProperty) propObject;
            
            Object indValue = m_individual.getPropertyValue(objProp);
                                    
            if (indValue instanceof OWLIndividual)
            {
                OwlKB kb = new OwlKB(((OWLIndividual) indValue).getOWLModel());
                value = kb.getInstance(((OWLIndividual) indValue).getName());
            }
            else if (indValue instanceof DefaultOWLIndividual)
            {
                OwlKB kb = new OwlKB(((DefaultOWLIndividual) indValue).getOWLModel());
                value = kb.getInstance(((DefaultOWLIndividual) indValue).getName());
            }
        }
        
        
        if (value == null)
        {
            value = property.getDefaultValue();
        }
         
        
       return value;
    }

    public String getURI()
    {
        return m_individual.getURI();
    }

    public String getClassName()
    {
        // Protege-OWL instances can have multiple types.
        // To simplify, we only support 1 type.
        
        Collection c = m_individual.getProtegeTypes();       
        Object[] classes = c.toArray();
        String className = ((OWLClass) classes[0]).getName();
        
        return className; 
    }
    
    public String getName()
    {       
        return m_individual.getName();
    }

    public String getNamespace()
    {
        return m_individual.getNamespace();
    }
    
    public String toString()
    {
        return m_individual.getName();
    }
    
    public OWLIndividual getIndividual()
    {        
        return m_individual;
    }
    
    public Object getType()
    {
        Collection c = m_individual.getProtegeTypes();  
        Object[] classes = c.toArray();
        
        return classes[0]; // OWLClass                           
    }
}
