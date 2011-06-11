/*
 * TaskObject.java
 *
 * Created on August 27, 2006, 4:40 PM
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
 * Representation of an instance (object) of a class.
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TaskObject extends KnowledgeComponent
{
    /**
     * The type of the object.
     */
    protected String m_class;
    
    /**
     * Where the class is defined (ex. project project).
     */
    protected String m_namespace;
            
    /**
     * Creates a new instance of TaskObject
     * @param objectName Name of the object
     * @param cls Name of the class the object belongs to
     * @param namespace Where the class is defined (ex. protege project)
     */
    public TaskObject(String objectName, String cls, String namespace)
    {
        super(objectName);
        
        m_class = cls;
        m_namespace = namespace;
    }
    
    /**
     * Returns the name of the class.
     * @return Name of the class
     */
    public String getCls()
    {
        return m_class;
    }
    
    /**
     * Returns the namespace of the class.
     * @return The namespace of the class
     */
    public String getNamespace()
    {
        return m_namespace;
    }
    
}
