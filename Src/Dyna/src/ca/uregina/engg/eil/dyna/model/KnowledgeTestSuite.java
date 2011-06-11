/*
 * KnowledgeTestSuite.java
 *
 * Created on November 13, 2006, 9:48 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.model;

import java.util.Vector;

/**
 * Container for the test setup and a list of test cases.
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class KnowledgeTestSuite extends KnowledgeComponent
{
    protected String m_setup;
    protected Vector<KnowledgeTestCase> m_testCaseList;
    
    /** Creates a new instance of KnowledgeTestSuite */
    public KnowledgeTestSuite(String name)
    {
        super(name);
        
        m_setup = "";
        m_testCaseList = new Vector<KnowledgeTestCase>();
    }
    
    public String toString()
    {
        return m_name;
    }
    
    public void setSetup(String setup)
    {
        m_setup = setup;
    }
    
    public String getSetup()
    {
        return m_setup;
    }
    
    public void addTestCase(KnowledgeTestCase testCase)
    {
        m_testCaseList.add(testCase);
    }
    
    public void deleteTestCase(KnowledgeTestCase testCase)
    {
        m_testCaseList.remove(testCase);
    }
    
    public Vector<KnowledgeTestCase> getTestCaseList()
    {
        return m_testCaseList;
    }
    
}
