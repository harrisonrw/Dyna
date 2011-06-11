/*
 * TestInterpreter.java
 *
 * Created on November 19, 2006, 4:19 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import ca.uregina.engg.eil.dyna.controller.ProjectController;
import ca.uregina.engg.eil.dyna.controller.TaskController;
import ca.uregina.engg.eil.dyna.model.GenericProperty;
import ca.uregina.engg.eil.dyna.model.KnowledgeTestCase;
import ca.uregina.engg.eil.dyna.model.KnowledgeTestSuite;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.model.GenericInstance;
import ca.uregina.engg.eil.dyna.model.Task;

/**
 * An interpreter for Task Behaviour Language (TBL).  This interpreter
 * is used for running test cases.
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TestInterpreter extends Interpreter
{
    public static final String TEST_INTERPRETER = "TestInterpreter";
    public static final String PASSED = "Passed";
    public static final String FAILED = "Failed";
    
    
    protected String m_passFail;
    
    /** Creates a new instance of TestInterpreter */
    public TestInterpreter()
    {
        super();
        
        m_passFail = FAILED;
    }
    
    public String getPassFail()
    {
        return m_passFail;
    }
    
    public void runTestCase(Task task, KnowledgeTestCase testCase)
    {
        String code = buildTestCode(task, testCase);
        
        ProjectController projectController = ProjectController.getProjectController();
        PrintStream out = projectController.getOutputStream();
        PrintStream testOut = projectController.getTestOutputStream();
                       
        setKnowledgeBase(projectController.getKnowledgeBase());        
        setOut(out);
        setTestOut(testOut);
        
        initTestEnvironment(task, testCase);
        
        interpret(code);
        
        updateSymbolTable();
        
    }
    
    protected String buildTestCode(Task task, KnowledgeTestCase testCase)
    {
        String code = ""; //testCase.getName() + "()\n\n" + "test " + testCase.getName() + "()\n{";
                         
        // Add the object definitions to the code.
        Vector<GenericInstance> objectList = task.getObjects();
        for (int i = 0; i < objectList.size(); i++)
        {
            GenericInstance object = objectList.get(i);
            code += object.getClassName() + " " + object.getName() + "\n";
        }
        
        // Add the setup to the code.
        KnowledgeTestSuite testSuite = task.getTestSuite();
        code += testSuite.getSetup() + "\n";
                
        // Add the test case.
        code += testCase.getTestCode() + "\n"; //"\n}\n";
        
        return code;
    }
    
    protected void initTestEnvironment(Task task, KnowledgeTestCase testCase)
    {
        // Clear the stack.
        Stack.getStack().clear();
        
        // Clear the Call Stack.
        CallStack.getCallStack().clear();
        
        // Add the tasks to the symbol table.
        
        CallItem root = new CallItem(testCase);
        Hashtable<String, Object> symbolTable = root.getSymbolTable();
                        
        TaskController taskController = TaskController.getTaskController();
        Vector<Task> taskList = taskController.getTasks();
        for (int i = 0; i < taskList.size(); i++)
        {
            symbolTable.put(taskList.get(i).getName(), taskList.get(i));
        }
        
        // Add the objects and their attributes to the symbol table.
        Vector<GenericInstance> objectList = task.getObjects();
        for (int j = 0; j < objectList.size(); j++)
        {
            GenericInstance obj = objectList.get(j);
            
            GenericProperty[] propertyList = m_kb.getPropertyList(obj.getClassName());
            for (int k = 0; k < propertyList.length; k++)
            {                
                symbolTable.put(obj.getName() + "." + propertyList[k].getName(), propertyList[k]);
            }
            
            symbolTable.put(obj.getClassName(), obj);
        }
        
        // Push the root onto the call stack.
        CallStack.getCallStack().push(root, true);
        
    }
    
    public GenericInstance createInstance(String clsName, String instName)
    { 
        // Instances should not be created in the Test Case, but they can be used.
        // So only return instances.
        
        GenericInstance instance = null;
        if (m_kb.containsInstance(instName))
        {
            instance = m_kb.getInstance(instName);
        }
        
        return instance;
    }
    
    
    public void createDependency(String depName, Vector<Object> argList)
    {        
        //TaskController taskController = TaskController.getTaskController();
        //Task task = taskController.getTask(depName);
        
        // Store the info about the task.                              
        //m_stack.push(task);       
        //m_symbolTable.put(task.getName(), task);
                
        // Run behaviour in the Interpreter
        //runBehaviour(depName, argList);
        
        /*
        String code = "";
        
        // Define the arguments.        
        String[] aList = m_task.getArgs2();
        for (int a = 0; a < aList.length; a++)
        {
            code += aList[a] + "\n";
        }
                       
        // Set the values of the arguments.
        for (int c = 0; c < aList.length; c++)
        {
            String varName = aList[c].split("[ ]")[1];
            
            String value = argList.get(c).toString();
            if (argList.get(c) instanceof String)
            {
                value = "\"" + value + "\"";
            }
            
            code += varName + " = " + value + "\n";
        }
        
        // Add the object definitions to the code.
        Vector<GenericInstance> objectList = m_task.getObjects();
        for (int i = 0; i < objectList.size(); i++)
        {
            GenericInstance object = objectList.get(i);
            code += object.getClassName() + " " + object.getName() + "\n";
        }
        
        // Delete all dependecies for this task.
        m_task.deleteAllDependencies();
        fireDeleteAllDepFeedback(m_task);
        
        // Add the pre-conditions.
        code += m_task.getPreCondition() + "\n";
        
        // Add the task behaviour.
        code += m_task.getBehaviour() + "\n"; 
        
        // Return Statement.
        if (!m_task.getReturnId().equals(""))
        {
            code += "return " + m_task.getReturnId() + "\n";
        }
        
        
        InterpreterManager interpManager = InterpreterManager.getInterpreterManager();   
        String origInterp = interpManager.getInterpreterKey();
        
        // Switch to base interpreter.
        interpManager.switchInterpreter(Interpreter.INTERPRETER);
        
        Interpreter interp = interpManager.getInterpreter();
        interp.setKnowledgeBase(m_kb);
        interp.setTask(m_task);
        interp.setOut(m_out);
        interp.setTestOut(m_testOut);
        
        //m_out.println("Running Test Case");
        
        interp.interpret(code);
        
        // Switch back to original interpreter.
        //interpManager.switchInterpreter(TestInterpreter.TEST_INTERPRETER);
        interpManager.switchInterpreter(origInterp);
        
        // Update the symbol table with new values.
        
        Enumeration<String> keys = m_instanceTable.keys();        
        while (keys.hasMoreElements())
        {
            String key = (String) keys.nextElement();
            
            // Split the key into instance name and property name.
            String[] s = key.split("[.]");
            
            if (s.length == 2)
            {            
                GenericInstance inst = m_kb.getInstance(s[0]);                
                GenericProperty prop = m_kb.getProperty(s[1]);
                
                Object value = inst.getPropertyValue(prop);
                
                // Put the new value into symbol table.
                m_symbolTable.put(key, value);
            }
        }
         */
    }
    
    public void doAssert(boolean value)
    {
        if (value)
        {
            m_passFail = PASSED;
        }    
        else
        {
            m_passFail = FAILED;
        }
    }

    /*
    private void fireDeleteAllDepFeedback(Task task)
    {
        try
        {
            Hashtable<String, Object> eventData = new Hashtable<String, Object>();
            eventData.put(EventService.TASK_OBJECT, task);

            TopicEvent event = new TopicEvent(this, 
                                              EventService.FEEDBACK + 
                                              EventService.TASK +
                                              EventService.DEPENDENCY +                                         
                                              EventService.DELETE_ALL, 
                                              eventData);
            EventService.getEventService().fireEvent(event);
        
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
     */
    
}
