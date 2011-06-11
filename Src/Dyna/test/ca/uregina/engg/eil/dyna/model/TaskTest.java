/*
 * TaskTest.java
 *
 * Created on September 6, 2006, 1:14 AM
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
public class TaskTest extends TestCase
{
    private Task m_task = null;
    private Task m_parentTask = null;
        
    private Task m_taskTest = null;
    private Task m_parentTaskTest = null;
        
    private final String TASK_NAME = "Task A";    
    private final String PARENT_TASK_NAME = "Parent Task A";
    
    private final String TASK_NAME_TEST = "Task B";
    private final String PARENT_TASK_NAME_TEST = "Parent Task B";
    
    
    /** Creates a new instance of TaskTest */
    public TaskTest(String testName)
    {
        super(testName);
    }
    
    protected void setUp() throws Exception 
    {                
        m_task = new Task(TASK_NAME);
        m_parentTask = new Task(PARENT_TASK_NAME);
        //m_parentTask.addSubTask(m_task);
        
        m_taskTest = new Task(TASK_NAME_TEST);
        m_parentTaskTest = new Task(PARENT_TASK_NAME_TEST);
    }
    
    protected void tearDown() throws Exception 
    {                
        m_task = null;
        m_parentTask = null;
        
        m_taskTest = null;
        m_parentTaskTest = null;
    }
    
    public static Test suite() 
    {
        TestSuite suite = new TestSuite(TaskTest.class);
        
        return suite;
    }
    
    public void testGetSubTaskCount() throws Exception
    {
        System.out.println("getSubTaskCount");
        
        m_parentTask.addSubTask(m_task);
        assertTrue(m_parentTask.getSubTaskCount() == 1);   
    }
    
    public void testAddSubTask() throws Exception
    {
        System.out.println("addSubTask");
        
        m_parentTask.addSubTask(m_task);
        assertTrue(m_parentTask.getSubTaskAt(0) == m_task);
    }
    
    public void testDeleteSubTask() throws Exception
    {
        System.out.println("deleteSubTask");
        
        m_parentTask.addSubTask(m_task);
        m_parentTask.deleteSubTask(m_task);
        assertTrue(m_parentTask.getSubTaskCount() == 0);
    }
    
    public void testGetParent() throws Exception
    {      
        System.out.println("getParent");
        
        m_parentTask.addSubTask(m_task);
        assertTrue(m_task.getParent() == m_parentTask);
    }
    
}
