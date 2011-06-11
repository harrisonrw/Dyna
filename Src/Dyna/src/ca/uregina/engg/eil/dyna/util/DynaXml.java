/*
 * DynaXml.java
 *
 * Created on September 1, 2006, 8:43 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.util;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public interface DynaXml
{
    public static final String PROJECT = "DynaProject";
    
    public static final String TASK_LIST = "TaskList";
    public static final String TASK = "Task";
    public static final String TASK_NAME = "Name";
    public static final String TASK_DOCUMENTATION = "Documentation";
    public static final String TASK_SUB_TASK_LIST = "SubTaskList";
    public static final String TASK_SUB_TASK = "SubTask";
    public static final String TASK_DEPENDENCY_LIST = "DependencyList";
    public static final String TASK_DEPENDENCY = "Dependency";        
    public static final String BEHAVIOUR = "Behaviour";   
    
    public static final String OBJECTIVE_LIST = "ObjectiveList";
    public static final String OBJECTIVE = "Objective";
    public static final String OBJECTIVE_NAME = "Name";
    public static final String OBJECTIVE_DOCUMENTATION = "Documentation";
    
    public static final String TASK_PRIORITY_LIST = "TaskPriorityList";
    public static final String TASK_PRIORITY = "TaskPriority";
    public static final String TASK_PRIORITY_TASK = "Task";
    public static final String TASK_PRIORITY_PRIORITY = "Priority";
    
    public static final String TASK_ARG_LIST = "TaskArgList";
    public static final String TASK_ARG = "TaskArg";
    public static final String TASK_RETURN = "TaskReturn";
    public static final String VAR_TYPE = "VarType";
    public static final String VAR_NAME = "VarName";
    
    public static final String OBJECT_LIST = "ObjectList";
    public static final String OBJECT = "Object";
    public static final String RESOURCE = "rdf:resource";
    
    public static final String PRE_CONDITION = "PreCondition";
    
    public static final String TEST_SUITE = "TestSuite";
    public static final String TEST_SETUP = "TestSetup";
    public static final String TEST_CASE_LIST = "TestCaseList";
    public static final String TEST_CASE = "TestCase";
    public static final String TEST_CASE_NAME = "TestCaseName";
    public static final String TEST_CASE_CODE = "TestCaseCode";
    
}
