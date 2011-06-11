/*
 * KnowledgeTestCase.java
 *
 * Created on November 13, 2006, 9:53 PM
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
 * Container for a test case.
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class KnowledgeTestCase extends KnowledgeComponent
{
    protected String m_testCode;
    
    /** Creates a new instance of KnowledgeTestCase */
    public KnowledgeTestCase(String name)
    {
        super(name);
        
        m_testCode = "";
    }
    
    public String toString()
    {
        return m_name;
    }
    
    public void setTestCode(String testCode)
    {
        m_testCode = testCode;
    }
    
    public String getTestCode()
    {
        return m_testCode;
    }
    
}
