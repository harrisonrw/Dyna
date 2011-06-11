/*
 * KnowledgeComponentTest.java
 *
 * Created on September 4, 2006, 1:34 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class KnowledgeComponentTest extends TestCase
{
    private KnowledgeComponent m_kc = null;
    
    private KnowledgeComponent m_kcTest = null;
    
    private final String NAME = "Knowledge Component A";
    private final String DOCUMENTATION = "Test documentation A";
    
    private final String NAME_TEST = "Knowledge Component B";
    private final String DOCUMENTATION_TEST = "Test documentation B";
    
    /** Creates a new instance of KnowledgeComponentTest */
    public KnowledgeComponentTest(String testName)
    {
        super(testName);
    }
    
    protected void setUp() throws Exception 
    {
        m_kc = new KnowledgeComponent(NAME);
        m_kc.setDocumentation(DOCUMENTATION);
        
        m_kcTest = new KnowledgeComponent(NAME_TEST);
        m_kcTest.setDocumentation(DOCUMENTATION_TEST);
    }
    
    protected void tearDown() throws Exception 
    {
        m_kc = null;
        
        m_kcTest = null;                
    }
    
    public static Test suite() 
    {
        TestSuite suite = new TestSuite(KnowledgeComponentTest.class);
        
        return suite;
    }
    
    /**
    * Test of toString.
    */
    public void testToString() throws Exception
    {
        System.out.println("toString");
        
        assertTrue(m_kc.toString().equals(NAME));
    }
    
    /**
    * Test of getName.
    */
    public void testGetName() throws Exception
    {
        System.out.println("getName");
        
        assertTrue(m_kc.getName().equals(NAME));
    }
    
    /**
    * Test of setName.
    */
    public void testSetName() throws Exception 
    {
        System.out.println("setName");

        m_kc.setName(NAME_TEST);
        assertTrue(m_kc.getName().equals(NAME_TEST));

    }
    
    /**
    * Test of getDocumentation.
    */
    public void testGetDocumentation() throws Exception
    {
        System.out.println("getDocumentation");
        
        assertTrue(m_kc.getDocumentation().equals(DOCUMENTATION));
    }
    
    /**
    * Test of setDocumentation.
    */
    public void testSetDocumentation() throws Exception
    {
        System.out.print("setDocumentation");
        
        m_kc.setDocumentation(DOCUMENTATION_TEST);
        assertTrue(m_kc.getDocumentation().equals(DOCUMENTATION_TEST));
    }
    
}
