/*
 * ProjectControllerTest.java
 *
 * Created on September 4, 2006, 2:45 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.controller;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ca.uregina.engg.eil.dyna.model.Project;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class ProjectControllerTest extends TestCase
{
    private ProjectController m_projectController = null;
    private Project m_project = null;
    
    private ProjectController m_projectControllerTest = null;
    
    private final String PROJECT = "Test Project A";
    
    /** Creates a new instance of ProjectControllerTest */
    public ProjectControllerTest(String testName)
    {
        super(testName);
    }
    
    protected void setUp() throws Exception 
    {
        m_projectController = new ProjectController();
        
        m_project = new Project(PROJECT);
        m_projectController.setProject(m_project);
    }
    
    protected void tearDown() throws Exception 
    {
        m_projectController = null;
        
        m_projectControllerTest = null;
    }
    
    public static Test suite() 
    {
        TestSuite suite = new TestSuite(ProjectControllerTest.class);
        
        return suite;
    }
    
    /**
    * Test of getProject.
    */
    public void testGetProject() throws Exception
    {
        System.out.println("getProject");
                
        assertTrue(m_projectController.getProject().getName().equals(PROJECT));
    }
    
}
