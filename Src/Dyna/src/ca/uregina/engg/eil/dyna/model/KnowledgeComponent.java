/*
 * KnowledgeComponent.java
 *
 * Created on August 16, 2006, 3:08 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.model;

/**
 * Base class for knowledge components.
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class KnowledgeComponent 
{
    /**
     * Name of the knowledge component object.
     */
    protected String m_name;
    
    /**
     * Documentation for the knowledge component.
     */
    protected String m_documentation;
    
    /** Creates a new instance of KnowledgeComponent */
    public KnowledgeComponent() 
    {
        m_name = "KnowledgeComponent";
        m_documentation = "";
    }
    
    /**
     * Creates a new instance of KnowledgeComponent
     * @param name Name of the knowledge component object
     */
    public KnowledgeComponent(String name)
    {
        m_name = name;
        m_documentation = "";
    }
    
    /**
     * Returns name of the knowledge component object.
     * @return Name of the knowledge component object
     */
    public String getName()
    {
        return m_name;
    }
    
    /**
     * Sets the name of the knowledge component object.
     * @param name Name of the knowledge component object
     */
    public void setName(String name)
    {
        m_name = name;
    }
    
    /**
     * Returns documentation of the knowledge component.
     * @return Documentation of the knowledge component
     */
    public String getDocumentation()
    {
        return m_documentation;
    }
    
    /**
     * Sets documentation for the knowledge component.
     * @param documentation Documentation for the knowledge component
     */
    public void setDocumentation(String documentation)
    {
        m_documentation = documentation;
    }
    
    /**
     * Returns a string representation of the knowledge component object.
     * @return A string representation of the knowledge component object
     */
    public String toString() 
    {        
        return m_name;
    }
}
