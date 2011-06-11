/*
 * Interpreter.java
 *
 * Created on September 16, 2006, 6:27 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import ca.uregina.engg.eil.dyna.controller.TaskController;
import ca.uregina.engg.eil.dyna.model.GenericProperty;
import ca.uregina.engg.eil.dyna.model.KnowledgeComponent;
import edu.stanford.smi.protege.model.Cls;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Slot;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.model.GenericInstance;
import ca.uregina.engg.eil.dyna.model.GenericKB;
import ca.uregina.engg.eil.dyna.model.Task;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class Interpreter implements TBLParserTreeConstants
{     
    public static final String INTERPRETER = "Interpreter";
        
    protected GenericKB m_kb;    
    protected PrintStream m_out; 
    protected PrintStream m_testOut;
        
    protected CallStack m_callStack;
    protected InstanceTable m_instanceTable;
    protected Stack m_stack;
       
    protected GarbageCollector m_garbageCollector;
         
    /**
     * Creates a new instance of Interpreter
     */
    public Interpreter()
    {
        m_kb = null;
        //m_task = null;
        
        m_out = System.out;   
        m_testOut = System.out;
        
        m_callStack = CallStack.getCallStack();
        m_instanceTable = InstanceTable.getInstanceTable();      
        m_stack = Stack.getStack();
        
        m_garbageCollector = new GarbageCollector();                      
    }
                                   
    public void setKnowledgeBase(GenericKB kb)
    {
        m_kb = kb;
    }
    
    public GenericKB getKnowledgeBase()
    {
        return m_kb;
    }
    
    public CallStack getCallStack()
    {
        return m_callStack;
    }
    
    public Hashtable<String, Object> getSymbolTable()
    {
        return m_callStack.getTop().getSymbolTable();
    }
    
    public void setOut(PrintStream out)
    {
        m_out = out;
    }
    
    public PrintStream getOut()
    {
        return m_out;
    }
    
    public void setTestOut(PrintStream out)
    {
        m_testOut = out;
    }
    
    public PrintStream getTestOut()
    {
        return m_testOut;
    }
                       
    public void interpret(String code)
    {                  
        TBLParser parser = new TBLParser(new StringReader("\n" + code));
        
        try 
        {                               
            parser.Start();            
            parser.jjtree.rootNode().interpret();                            
        }
        catch (ParseException e) 
        {             
            m_out.print(e.getMessage()); 
            m_out.println();
        }
        catch (TokenMgrError e)
        {
            System.out.println(e.getMessage());
        }                                        
    }
    
    public GenericInstance createInstance(String clsName, String instName)
    {        
          // Get the instance from the KB.  If instance does not exist, then create it.
          GenericInstance instance = null;
          if (m_kb.containsInstance(instName))
          {
              instance = m_kb.getInstance(instName);
          }
          else
          {
              instance = m_kb.createInstance(clsName, instName);              
          }
          
          Task task = (Task) m_callStack.getTop().getItem();
          
          fireAddTaskObject(task.getName(), instance);
          
          return instance;
    }
                  
    public void runTask(KnowledgeComponent parent, String taskName, Vector<Object> argList)
    {
        // Get the parent for which we are running the task from.
        //KnowledgeComponent parent = m_callStack.getTop().getItem();
        
        // Get the dependent task.
        TaskController taskController = TaskController.getTaskController();                
        Task task = taskController.getTask(taskName);
        
        // Delete all dependecies for the dependent task.        
        task.deleteAllDependencies();
        fireDeleteAllDepFeedback(task);
        
        // Build the code for the dependency.
        String code = buildCode(task, argList);
        
        Hashtable<String, Object> symbolTable = SymbolTableHelper.getSymbolTable();
        
        // Add all the task objects to the symbol table.
        Vector<GenericInstance> objectList = task.getObjects();
        for (int j = 0; j < objectList.size(); j++)
        {
            GenericInstance obj = objectList.get(j);
            symbolTable.put(obj.getClassName(), obj);
        }
        
        
        // Add all the tasks to the symbol table.        
        Vector<Task> taskList = taskController.getTasks();
        for (int i = 0; i < taskList.size(); i++)
        {
            Task t = taskList.get(i);
            symbolTable.put(t.getName(), t);
        }
        
        InterpreterManager interpManager = InterpreterManager.getInterpreterManager();   
        String origInterp = interpManager.getInterpreterKey();
        
        // Switch to base interpreter.
        interpManager.switchInterpreter(Interpreter.INTERPRETER);
        
        Interpreter interp = interpManager.getInterpreter();
        interp.setKnowledgeBase(m_kb);        
        interp.setOut(m_out);
        interp.setTestOut(m_testOut);
                        
        
        interp.interpret(code);
        
        
        // Switch back to original interpreter.   
        // TODO: may have to move this down.
        interpManager.switchInterpreter(origInterp);
        
        
        updateSymbolTable();
        
        if (parent instanceof Task)
        {
            fireCreateDependencyCommand(parent.getName(), task.getName());               
        }
    }
    
    /*
    public void runBehaviour(String taskName, Vector<Object> argList)
    {
        TaskController taskController = TaskController.getTaskController();        
        Task task = taskController.getTask(taskName);
        
        String code = "task " + taskName + "(";
        
        // Define the arguments.
        String[] aList = task.getArgs2();
        for (int a = 0; a < aList.length; a++)
        {
            code += aList[a];
            
            if (a < aList.length - 1)
            {
                code += ", ";
            }
        }
        
        code += ")\n{\n";
                
                       
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
        //Vector<GenericInstance> objectList = m_task.getObjects();
        Vector<GenericInstance> objectList = task.getObjects();
        for (int i = 0; i < objectList.size(); i++)
        {
            GenericInstance object = objectList.get(i);
            code += object.getClassName() + " " + object.getName() + "\n";
        }
        
        // Delete all dependecies for this task.
        //m_task.deleteAllDependencies();
        //fireDeleteAllDepFeedback(m_task);
        task.deleteAllDependencies();
        fireDeleteAllDepFeedback(task);
        
        // Add the pre-conditions.
        code += task.getPreCondition() + "\n";
        
        // Add the task behaviour.
        code += task.getBehaviour() + "\n"; 
        
        // Return Statement.
        if (!task.getReturnId().equals(""))
        {
            code += "return " + task.getReturnId() + "\n";
        }
        
        
        code += "}\n";
        
        InterpreterManager interpManager = InterpreterManager.getInterpreterManager();   
        String origInterp = interpManager.getInterpreterKey();
        
        // Switch to base interpreter.
        interpManager.switchInterpreter(Interpreter.INTERPRETER);
        
        Interpreter interp = interpManager.getInterpreter();
        interp.setKnowledgeBase(m_kb);
        //interp.setTask(task);
        interp.setOut(m_out);
        interp.setTestOut(m_testOut);
                        
        interp.interpret(code);
        
        // Cleanup.
        //interp.setScopeIndex(interp.getScopeIndex() - 1);
        
        // Switch back to original interpreter.
        //interpManager.switchInterpreter(TestInterpreter.TEST_INTERPRETER);
        interpManager.switchInterpreter(origInterp);
        
        // Update the symbol table with new values.
        CallItem item = m_callStack.getTop(); 
        Hashtable<String, Object> symbolTable = item.getSymbolTable();
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
                symbolTable.put(key, value);
            }
        }
    }
    */
    
    protected String buildCode(Task task, Vector<Object> argList)
    {        
        String code = "task " + task.getName() + "(";
        
        // Define the arguments.
        String[] strArgList = task.getArgs2();
        for (int a = 0; a < strArgList.length; a++)
        {
            code += strArgList[a];
            
            if (a < strArgList.length - 1)
            {
                code += ", ";
            }
        }
        
        code += ")\n{\n";
        
        // Set the values of the arguments.
        for (int c = 0; c < strArgList.length; c++)
        {
            String varName = strArgList[c].split("[ ]")[1];
            
            String value = argList.get(c).toString();
            if (argList.get(c) instanceof String)
            {
                value = "\"" + value + "\"";
            }
            
            code += varName + " = " + value + "\n";
        }
        
        // Add the object definitions to the code.        
        Vector<GenericInstance> objectList = task.getObjects();
        for (int i = 0; i < objectList.size(); i++)
        {
            GenericInstance object = objectList.get(i);
            code += object.getClassName() + " " + object.getName() + "\n";
        }
        
        // Add the pre-conditions.
        code += task.getPreCondition() + "\n";
        
        // Add the task behaviour.
        code += task.getBehaviour() + "\n"; 
        
        // Return Statement.
        if (!task.getReturnId().equals(""))
        {
            code += "return " + task.getReturnId() + "\n";
        }
        
        
        code += "}\n";
        
        return code;
    }
    
    protected void updateSymbolTable()
    {
        // Update the symbol table with new values.
        
        CallItem item = m_callStack.getTop(); 
        Hashtable<String, Object> symbolTable = item.getSymbolTable();
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
                symbolTable.put(key, value);
            }
        }
    }
    
    public void doAssert(boolean value)
    {
        if (!value)
        {
            m_out.println("Assert Failed!");
        }        
    }

    private void fireAddTaskObject(String taskName, GenericInstance object)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_NAME, taskName);            
            data.put(EventService.TSK_OBJECT_OBJECT, object);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TSK_OBJECT + 
                                              EventService.ADD, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, "Cannot create task object! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
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

    private void fireCreateDependencyCommand(String taskName, String depName)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_NAME, taskName);
            data.put(EventService.DEP_NAME, depName);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.DEPENDENCY + 
                                              EventService.CREATE, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();            
        }   
    }
        
            
}
