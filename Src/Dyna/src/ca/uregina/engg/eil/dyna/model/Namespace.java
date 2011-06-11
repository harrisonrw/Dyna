/*
 * Namespace.java
 *
 * Created on March 12, 2007, 8:42 PM
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
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class Namespace
{
    private String m_prefix;
    private String m_uri;
    
    /** Creates a new instance of Namespace */
    public Namespace(String prefix, String uri)
    {
        m_prefix = prefix;
        m_uri = uri;
    }
    
    public void setPrefix(String prefix)
    {
        m_prefix = prefix;
    }
    
    public String getPrefix()
    {
        return m_prefix;
    }
    
    public void setURI(String uri)
    {
        m_uri = uri;
    }
    
    public String getURI()
    {
        return m_uri;
    }
    
}
