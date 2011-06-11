/*
 * OwlKB.java
 *
 * Created on October 31, 2006, 7:58 PM
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
import com.hp.hpl.jena.rdf.model.RDFNode;
import edu.stanford.smi.protege.model.ValueType;
import edu.stanford.smi.protegex.owl.model.OWLClass;
import edu.stanford.smi.protegex.owl.model.OWLDatatypeProperty;
import edu.stanford.smi.protegex.owl.model.OWLIndividual;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLObjectProperty;
import edu.stanford.smi.protegex.owl.model.OWLProperty;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLDatatypeProperty;
import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLObjectProperty;
import edu.stanford.smi.protegex.owl.model.impl.DefaultRDFProperty;
import java.util.Collection;

/**
 * Wrapper for Protege-OWL database.
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class OwlKB implements GenericKB
{
    private OWLModel m_owlModel;
    
    /** Creates a new instance of OwlKB */
    public OwlKB(OWLModel owlModel)
    {
        m_owlModel = owlModel;              
    }

    public GenericInstance createInstance(String clsName, String instName)
    {        
        OWLNamedClass cls = m_owlModel.getOWLNamedClass(clsName);
        OWLIndividual individual = cls.createOWLIndividual(instName);
                        
        // Set the properties.
        Object[] propertyList = cls.getAssociatedProperties().toArray();
        for (int i = 0; i < propertyList.length; i++)
        {
            if (propertyList[i] instanceof OWLDatatypeProperty)
            {
                setOWLDatatypeProperty(individual, (OWLDatatypeProperty) propertyList[i]);
            }
            else if (propertyList[i] instanceof DefaultOWLDatatypeProperty)
            {
                setDefaultOWLDatatypeProperty(individual, (DefaultOWLDatatypeProperty) propertyList[i]);
            }   
            else if (propertyList[i] instanceof OWLObjectProperty)
            {
                setOWLObjectProperty(individual, (OWLObjectProperty) propertyList[i]);
            }
            else if (propertyList[i] instanceof DefaultOWLObjectProperty)
            {
                setDefaultOWLObjectProperty(individual, (DefaultOWLObjectProperty) propertyList[i]);
            }
        }
        
        OwlInstance owlInstance = new OwlInstance(individual);
                
        return owlInstance; 
    }

    public GenericProperty[] getPropertyList(String clsName)
    {
        // TODO: get object properties too
        
        OWLNamedClass cls = m_owlModel.getOWLNamedClass(clsName);
        
        //Object[] objects = m_owlModel.getRDFProperties().toArray();
        Object[] objects = cls.getAssociatedProperties().toArray();
        //Object[] objects = cls.getTemplateSlots().toArray(); //getOwnSlots().toArray();
                        
        OwlProperty[] propertyList = null;
        
        if (objects.length > 0)
        {
            propertyList = new OwlProperty[objects.length];

            for (int i = 0; i < objects.length; i++)
            {                
                if (objects[i] instanceof OWLDatatypeProperty)
                {                
                    propertyList[i] = new OwlProperty((OWLDatatypeProperty)objects[i]);
                }
                else if (objects[i] instanceof DefaultOWLDatatypeProperty)
                {
                    propertyList[i] = new OwlProperty((DefaultOWLDatatypeProperty)objects[i]);
                }
                else if (objects[i] instanceof OWLObjectProperty)
                {
                    propertyList[i] = new OwlProperty((OWLObjectProperty)objects[i]);
                }
                else if (objects[i] instanceof DefaultOWLObjectProperty)
                {
                    propertyList[i] = new OwlProperty((DefaultOWLObjectProperty)objects[i]);
                }
            }        
        }
        
        return propertyList;     
    }

    public GenericProperty getProperty(String propertyName)
    {        
        OWLProperty p = m_owlModel.getOWLProperty(propertyName);
        
        ca.uregina.engg.eil.dyna.model.OwlProperty owlProperty = null;
        
        if (p instanceof OWLDatatypeProperty)
        {
            owlProperty = new ca.uregina.engg.eil.dyna.model.OwlProperty((OWLDatatypeProperty) p);
        }
        else if (p instanceof OWLObjectProperty)
        {
            owlProperty = new ca.uregina.engg.eil.dyna.model.OwlProperty((OWLObjectProperty) p);
        }
        
        return owlProperty;
    }

    public boolean containsInstance(String instName)
    {
        boolean exists = false;
        
        OWLIndividual individual = m_owlModel.getOWLIndividual(instName);
        
        if (individual != null)
        {
            exists = true;
        }
        
        return exists;
    }

    public GenericInstance getInstance(String instName)
    {
        OwlInstance instance = new OwlInstance(m_owlModel.getOWLIndividual(instName));
        return instance;
    }

    public String[] getClassNames()
    {
        String[] classList = null;
                
        Collection c = m_owlModel.getUserDefinedOWLNamedClasses(); 
                
        Object[] owlClassList = c.toArray();
        
        classList = new String[owlClassList.length];
        
        for (int i = 0; i < owlClassList.length; i++)
        {
            classList[i] = ((OWLNamedClass) owlClassList[i]).getName();
        }
        
        return classList;
    }
    
    private void setOWLDatatypeProperty(OWLIndividual individual, OWLDatatypeProperty property)
    {
        ValueType type = m_owlModel.getOWLValueType(property.getRange().getURI());
        Object value = getInitialValue(type);
        
        individual.setPropertyValue(property, value); 
    }
    
    private void setDefaultOWLDatatypeProperty(OWLIndividual individual, DefaultOWLDatatypeProperty property)
    {
        ValueType type = m_owlModel.getOWLValueType(property.getRange().getURI());
        Object value = getInitialValue(type);
        
        individual.setPropertyValue(property, value); 
    }
    
    private void setOWLObjectProperty(OWLIndividual individual, OWLObjectProperty property)
    {        
        //Object value = new NULL();
                        
    }
    
    private void setDefaultOWLObjectProperty(OWLIndividual individual, DefaultOWLObjectProperty property)
    {
        
    }
    
    private Object getInitialValue(ValueType type)
    {
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
        
        return value;
    }
    
    public String getURI()
    {                
        String uri = m_owlModel.getURIForResourceName("Ontology");
        
        String baseURI = uri.substring(0, uri.indexOf("#") + 1);
        
        return baseURI;
    }
    
    public String getBaseFilename()
    {
        String uri = getURI();
        
        String filename = uri.substring(uri.lastIndexOf("/") + 1, uri.length() - 1);
        
        return filename;
    }

    
               
}
