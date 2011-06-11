/*
 * TaskController.java
 *
 * Created on August 11, 2006, 2:51 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.controller;

import ca.uregina.engg.eil.dyna.interpreter.Stack;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.interpreter.Interpreter;
import ca.uregina.engg.eil.dyna.interpreter.InterpreterManager;
import ca.uregina.engg.eil.dyna.interpreter.TestInterpreter;
import ca.uregina.engg.eil.dyna.model.*;
import ca.uregina.engg.eil.dyna.util.DependencyList;
import ca.uregina.engg.eil.dyna.util.DynaXml;
import ca.uregina.engg.eil.dyna.util.StringSplitter;
import ca.uregina.engg.eil.dyna.util.XmlManager;
import ca.uregina.engg.eil.dyna.util.XmlManagerException;

/**
 * Handles task logic.
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TaskController implements TopicEventListener
{    
       
    private static TaskController m_taskController = null;
            
    /**
     * Creates a new instance of TaskController
     * @param projectController Handler for project logic
     */
    public TaskController()
    {                        
        initEvents();             
    }
    
    public static TaskController getTaskController()
    {
        if (m_taskController == null)
        {
            m_taskController = new TaskController();
        }
        
        return m_taskController;
    }
    
    private void initEvents()
    {
        // Task Command Events.
        
        EventService.getEventService().addListener(EventService.COMMAND + 
                                                   EventService.TASK, this);
        
        // Dependency Command Events.
        
        //EventService.getEventService().addListener(EventService.COMMAND + 
        //                                           EventService.DEPENDENCY, this);
        
        // Task Object Command Events.
        
        //EventService.getEventService().addListener(EventService.COMMAND +
        //                                           EventService.TSK_OBJECT, this);
    }
                            
    /**
     * Adds sub task and notifies listeners.
     * @param task Task to add
     * @param parent Parent of task to add (if no parent, then pass null)
     */
    public void createTask(Task task, Task parent) throws Exception
    {
        ProjectController projectController = ProjectController.getProjectController();
        
        Vector<Task> tasks = projectController.getProject().getTasks();
        
        if (parent != null)
        {
            parent.addSubTask(task);
        }
        else
        {        
            tasks.add(task);
        }
                
        fireCreateTaskFeedback(task, parent);
    }
    
    /**
     * Adds sub task (uses names of tasks) and notifies listeners.
     * @param taskName Name of task to add
     * @param parentTaskName Name of parent task (pass "" if no parent)
     */
    public void createTask(String taskName, String parentTaskName) throws Exception
    {
        Task parent = null;
        if (parentTaskName != "")
        {
            parent = getTask(parentTaskName);
        }
        
        createTask(new Task(taskName), parent);
    }
            
    /**
     * Renames the task and notifies listeners.
     * @param task Task to rename
     * @param newName New name of task
     */
    public void renameTask(Task task, String newName) throws Exception
    {
        String oldName = task.getName();                
        task.setName(newName);
        
        fireRenameTaskFeedback(task, oldName);                
    }
    
    /**
     * Return tasks
     * @return List of tasks
     */
    public Vector<Task> getTasks()
    {
        ProjectController projectController = ProjectController.getProjectController();
        return projectController.getProject().getTasks();
    }

    /**
     * Deletes a task and notifies listeners.
     * @param task Task to delete
     */
    public void deleteTask(Task task) throws Exception
    {
        Task parent = task.getParent();
        if (parent != null)
        {
            parent.deleteSubTask(task);
        }
        else
        {
            ProjectController projectController = ProjectController.getProjectController();
            Vector<Task> taskList = projectController.getProject().getTasks();        
            taskList.remove(task);                
        }
                
        fireDeleteTaskFeedback(task);
    }
    
    /**
     * Deletes a task.
     * @param taskName Name of task to delete
     */
    public void deleteTask(String taskName) throws Exception
    {
        Task task = getTask(taskName);
        deleteTask(task);                                
    }
                
    /**
     * Show a task.
     * @param task Task to show
     */
    private void openTask(Task task) throws Exception
    {                  
        fireOpenTaskFeedback(task);
    }
    
    /**
     * Shows a task.
     * @param taskName Name of task to show
     */
    private void openTask(String taskName) throws Exception
    {
        Task task = getTask(taskName);
        openTask(task);                
    }
    
    /**
     * Edits documentation and notifies listeners.
     * @param task Task whose documentation to edit
     * @param documentation New documetation
     */
    public void editDoc(Task task, String documentation) throws Exception
    {
        task.setDocumentation(documentation);         
        fireEditDocFeedback(task, documentation);
    }
    
    private void createDependency(String taskName, String depName) throws Exception
    {
        Task task = getTask(taskName);
        Task dep = getTask(depName);
        
        createDependency(task, dep);
    }
    
    /**
     * Adds dependency to task and notifies listeners.
     * @param task Task to add dependency to
     * @param dep The dependency
     */
    public void createDependency(Task task, Task dep) throws Exception
    {               
        task.addDependency(dep);        
        
        fireCreateDependencyFeedback(task, dep);
    }
    
    /**
     * Deletes a dependency and notifies listeners.
     * @param task Task to delete dependency from
     * @param dep The dependency to delete
     */
    public void deleteDependency(Task task, Task dep)
    {        
        task.deleteDependency(dep);        
        //postEvent(m_listeners, task, TaskEvent.DEP_DELETED, dep, null, null);
    }
       
    public void editArgs(Task task, String args) throws Exception
    {
        task.clearArgs();
        
        // int a, string b
        
        if (!args.equals(""))
        {
            // Split the args.
            String[] s1 = args.split("[,]");

            for (int i = 0; i < s1.length; i++)
            {
                // Split the arg.
                String s2 = s1[i].trim();
                String[] s3 = s2.split("[ ]+");

                String type = s3[0];
                String name = s3[1];

                task.addArg(name, type);
            }
        }
        
        fireEditArgsFeedback(task);
    }
    
    public void editBehaviour(Task task, String behaviour) throws Exception
    {
        task.setBehaviour(behaviour);
        
        fireEditBehaviourFeedback(task, behaviour);
    }
    
    public void createObject(String taskName, String objectName, String clsName) throws Exception
    {
        Task task = getTask(taskName);
        
        ProjectController projectController = ProjectController.getProjectController();
        GenericKB kb = projectController.getKnowledgeBase();
        
        GenericInstance object = null;
        
        if (kb.containsInstance(objectName))
        {
            object = kb.getInstance(objectName);
        }
        else
        {
            object = kb.createInstance(clsName, objectName);
        }
        
        task.addObject(object);
        
        // If it's an array, then create a hashtable for it.
        
        fireCreateObjectFeedback(task, object);
    }
        
    public void addObject(String taskName, GenericInstance object) throws Exception
    {
        Task task = getTask(taskName);
        
        task.addObject(object);
        
        fireCreateObjectFeedback(task, object);
    }
    
    public void deleteObject(Task task, GenericInstance obj) throws Exception
    {
        task.deleteObject(obj);
        
        fireDeleteObjectFeedback(task, obj);
    }
    
    public void editPreCondition(Task task, String preCondition) throws Exception
    {
        task.setPreCondition(preCondition);
        
        fireEditPreConditionFeedback(task, preCondition);                
    }
    
    public void editReturn(Task task, String ret) throws Exception
    {        
        task.setReturn(ret, "");
        
        fireEditReturnFeedback(task);
    }
    
    public void createTestCase(Task task, String testName) throws Exception
    {
        KnowledgeTestSuite testSuite = task.getTestSuite();
        KnowledgeTestCase testCase = new KnowledgeTestCase(testName);
        testSuite.addTestCase(testCase);
        
        fireCreateTestCaseFeedback(task, testCase);
    }
    
    public void deleteTestCase(Task task, KnowledgeTestCase testCase) throws Exception
    {
        KnowledgeTestSuite testSuite = task.getTestSuite();
        testSuite.deleteTestCase(testCase);
        
        fireDeleteTestCaseFeedback(task, testCase);
    }
    
    public void editTestCase(Task task, KnowledgeTestCase testCase, String code) throws Exception
    {
        testCase.setTestCode(code);
        
        fireEditTestCaseFeedback(task, testCase, code);
    }
    
    public void editTestCaseSetup(Task task, String setup) throws Exception
    {
        KnowledgeTestSuite testSuite = task.getTestSuite();
        testSuite.setSetup(setup);
        
        fireEditTestCaseSetupFeedback(task, setup);
    }
               
    public void runTestCase(Task task, KnowledgeTestCase testCase) throws Exception
    {         
        InterpreterManager interpManager = InterpreterManager.getInterpreterManager();
        interpManager.switchInterpreter(TestInterpreter.TEST_INTERPRETER);        
        TestInterpreter testInterp = (TestInterpreter) interpManager.getInterpreter();
        
        testInterp.runTestCase(task, testCase);
        
        PrintStream testOut = testInterp.getTestOut();                
        testOut.println("    " + testCase.getName() + " : " + testInterp.getPassFail());
        
    }
            
    public void runAllTestCases(Task task) throws Exception
    {
        ProjectController projectController = ProjectController.getProjectController();
        PrintStream out = projectController.getOutputStream();
        PrintStream testOut = projectController.getTestOutputStream();
        testOut.println("Testing " + task.getName() + " ...");
        
        KnowledgeTestSuite testSuite = task.getTestSuite();
        Vector<KnowledgeTestCase> testCaseList = testSuite.getTestCaseList();
        
        for (int i = 0; i < testCaseList.size(); i++)
        {
            runTestCase(task, testCaseList.get(i));
        }
    }
    
    /*
    public void runBehaviour(Task task) throws Exception
    {
        // TODO: is this even run anymore?
        
        String behaviour = ""; //task.getBehaviour();
                        
        // Add the object definitions to the behaviour.
        Vector<GenericInstance> objectList = task.getObjects();
        for (int i = 0; i < objectList.size(); i++)
        {
            GenericInstance object = objectList.get(i);
            behaviour += object.getClassName() + " " + object.getName() + "\n"; // + behaviour;
        }
        
        // Delete all objects for this task.
        task.deleteAllObjects();
        fireDeleteAllTaskObjFeedback(task);
        
        // Delete all dependecies for this task.
        task.deleteAllDependencies();
        fireDeleteAllDepFeedback(task);
        
        // Now add the pre-conditions.
        behaviour += task.getPreCondition() + "\n";
        
        // Finally add the actual task behaviour.
        behaviour += task.getBehaviour(); 
        
        ProjectController projectController = ProjectController.getProjectController();
        
        InterpreterManager interpManager = InterpreterManager.getInterpreterManager();
        interpManager.switchInterpreter(Interpreter.INTERPRETER);
        
        Interpreter interp = interpManager.getInterpreter();
        interp.setKnowledgeBase(projectController.getKnowledgeBase());        
        interp.setOut(projectController.getOutputStream());
        
        interp.interpret(behaviour);
        
        // TODO: fire feedback?
    }
     */
    
    /**
     * Returns a task.
     * @param taskName Name of task to return
     * @return Tbe task
     */
    public Task getTask(String taskName)
    {       
        ProjectController projectController = ProjectController.getProjectController();
        Vector<Task> taskList = projectController.getProject().getTasks();
                    
        Task t = null;
        for (int i = 0; i < taskList.size(); i++)
        {
            t = getTask(taskName, taskList.get(i));
                        
            if (t != null)
            {
                break;
            }            
        }
        
        return t;          
    }    
        
    /**
     * Returns a task.
     * @param taskName Name of task to return
     * @param parent Parent of task to return
     * @return The task
     */
    public Task getTask(String taskName, Task parent)
    {
        Task t = null;
        
        if (parent.getName().equals(taskName))
        {
            t = parent;
        }
        else
        {
            Vector<Task> taskList = parent.getSubTaskList();
            for (int i = 0; i < taskList.size(); i++)
            {
                t = getTask(taskName, taskList.get(i));
                
                if (t != null)
                    break;
            }
        }
                        
        return t;
    }
    
    public void taskListToOwl(Element element, Document doc) throws XmlManagerException
    {
        ProjectController projectController = ProjectController.getProjectController();
        Vector<Task> taskList = projectController.getProject().getTasks();
                       
        for (int i = 0; i < taskList.size(); i++)
        {
            Element taskListElement = XmlManager.createElement(doc, "dyn:taskList", "");
                        
            Task task = taskList.get(i);
            taskListElement.appendChild(taskToOwl(task, doc));                     
            
            element.appendChild(taskListElement);
        }
    }
    
    public Element taskToOwl(Task task, Document doc) throws XmlManagerException
    {
        Element taskElement = null;
        
        try
        {            
            taskElement = XmlManager.createElement(doc, "dyn:"+DynaXml.TASK, "");            
            taskElement.setAttribute("rdf:ID", task.getName());
                   
            // Task Name.
            Element taskNameElement = XmlManager.createElement(doc, "dyn:name", task.getName());
            taskNameElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");            
            taskElement.appendChild(taskNameElement);
            
            // Task Documentation.
            Element taskDocElement = XmlManager.createElement(doc, "dyn:documentation", task.getDocumentation());
            taskDocElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
            taskElement.appendChild(taskDocElement);
            
            // Sub tasks.                        
            Vector<Task> subList = task.getSubTaskList();
            for (int k = 0; k < subList.size(); k++)
            {
                Element subElement = XmlManager.createElement(doc, "dyn:taskList", "");
                
                Task subTask = subList.get(k);
                subElement.appendChild(taskToOwl(subTask, doc));  
                
                taskElement.appendChild(subElement);
            }      
            
            // Dependencies.                             
            Vector<Task> depList = task.getDependencies();
            for (int i = 0; i < depList.size(); i++)
            {                     
                Element depListElement = XmlManager.createElement(doc, "dyn:depList", "");
                            
                Task dep = depList.get(i);
                Element t = XmlManager.createElement(doc, "dyn:task", "");
                t.setAttribute("rdf:resource", "#" + dep.getName());
                
                depListElement.appendChild(t);
                
                taskElement.appendChild(depListElement);
            }
            
            // Behaviour.
            Element behaviourElement = XmlManager.createElement(doc, "dyn:behaviour", task.getBehaviour());
            behaviourElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
            taskElement.appendChild(behaviourElement);
                        
            // Objects.                                    
            Vector<GenericInstance> objList = task.getObjects();
            for (int j = 0; j < objList.size(); j++)
            {                
                GenericInstance obj = objList.get(j);
                
                Element e = XmlManager.createElement(doc, "dyn:objectList", "");                     
                e.setAttribute("rdf:resource", obj.getURI());
                               
                taskElement.appendChild(e);                                      
            }
            
            // Preconditions.
            Element preCondElement = XmlManager.createElement(doc, "dyn:preCondition", task.getPreCondition());
            preCondElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");            
            taskElement.appendChild(preCondElement);
            
            
            // TestSuite.
            KnowledgeTestSuite testSuite = task.getTestSuite();
            
            Element testSuiteElement = XmlManager.createElement(doc, "dyn:testSuite", "");            
            
            Element knowledgeTestSuiteElement = XmlManager.createElement(doc, "dyn:KnowledgeTestSuite", "");
            knowledgeTestSuiteElement.setAttribute("rdf:ID", testSuite.getName());
            
            Element tsNameElement = XmlManager.createElement(doc, "dyn:name", testSuite.getName());
            tsNameElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
            knowledgeTestSuiteElement.appendChild(tsNameElement);
            
            Element tsDocElement = XmlManager.createElement(doc, "dyn:documentation", testSuite.getDocumentation());
            tsDocElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
            knowledgeTestSuiteElement.appendChild(tsDocElement);
            
            Element tsSetupElement = XmlManager.createElement(doc, "dyn:setup", testSuite.getSetup());
            tsSetupElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
            knowledgeTestSuiteElement.appendChild(tsSetupElement);
            
            // Test Cases.
            Vector<KnowledgeTestCase> testCaseList = testSuite.getTestCaseList();
            for (int k = 0; k < testCaseList.size(); k++)
            {
                KnowledgeTestCase testCase = testCaseList.get(k);
                    
                Element testCaseListElement = XmlManager.createElement(doc, "dyn:testCaseList", "");
                
                Element testCaseElement = XmlManager.createElement(doc, "dyn:KnowledgeTestCase", "");
                testCaseElement.setAttribute("rdf:ID", testCase.getName());
                
                Element tcNameElement = XmlManager.createElement(doc, "dyn:name", testCase.getName());
                tcNameElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
                testCaseElement.appendChild(tcNameElement);
                
                Element tcDocElement = XmlManager.createElement(doc, "dyn:documentation", testCase.getDocumentation());
                tcDocElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
                testCaseElement.appendChild(tcDocElement);
                
                Element tcCodeElement = XmlManager.createElement(doc, "dyn:testCode", testCase.getTestCode());
                tcCodeElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
                testCaseElement.appendChild(tcCodeElement);
                
                testCaseListElement.appendChild(testCaseElement);
                
                knowledgeTestSuiteElement.appendChild(testCaseListElement);
            }
            
            testSuiteElement.appendChild(knowledgeTestSuiteElement);
            
            taskElement.appendChild(testSuiteElement);
                                                                        
        }
        catch (DOMException e)
        {            
            taskElement = null;
            throw new XmlManagerException(e.getMessage (), e.getCause ());
        }
        
        return taskElement;
    }

    /**
     * Returns XML representation of the list of tasks.
     * @param doc XML document
     * @return XML task list element
     * @throws uregina.engg.eil.dyna.util.XmlManagerException Xml Manager Exception
     */
    public Element taskListToXml(Document doc) throws XmlManagerException
    {
        ProjectController projectController = ProjectController.getProjectController();
        Vector<Task> taskList = projectController.getProject().getTasks();
        
        Element taskListElement = XmlManager.createElement(doc, DynaXml.TASK_LIST, "");
                
        for (int i = 0; i < taskList.size(); i++)
        {
            Task task = taskList.get(i);            
            taskListElement.appendChild(taskToXml(task, doc));            
        }
                        
        return taskListElement;
    }
    
    /**
     * Returns XML representation of task.
     * @param task Task to convert to XML
     * @param doc XML document
     * @return XML representation of task
     * @throws uregina.engg.eil.dyna.util.XmlManagerException Xml Manager Exception
     */
    public Element taskToXml(Task task, Document doc) throws XmlManagerException
    {
        Element taskElement = null;
    
        try
        {
            taskElement = XmlManager.createElement( doc, DynaXml.TASK, "");
            
            taskElement.appendChild(XmlManager.createElement(doc, DynaXml.TASK_NAME, task.getName()));
            taskElement.appendChild(XmlManager.createElement(doc, DynaXml.TASK_DOCUMENTATION, task.getDocumentation()));
                   
            
            // Sub tasks.            
            Element subElement = XmlManager.createElement(doc, DynaXml.TASK_SUB_TASK_LIST, "");
            Vector<Task> subList = task.getSubTaskList();
            for (int k = 0; k < subList.size(); k++)
            {
                Task subTask = subList.get(k);
                subElement.appendChild(taskToXml(subTask, doc));                
            }      
            taskElement.appendChild(subElement);
            
            // Dependencies.     
            
            Element depListElement = XmlManager.createElement(doc, DynaXml.TASK_DEPENDENCY_LIST, "");
            
            Vector<Task> depList = task.getDependencies();
            for (int i = 0; i < depList.size(); i++)
            {                
                Task dep = depList.get(i);
                depListElement.appendChild(XmlManager.createElement(doc, DynaXml.TASK_DEPENDENCY, dep.getName()));
            }
            
            taskElement.appendChild(depListElement);
            
            // Task Behaviour Arguments.
            
            Element argListElement = XmlManager.createElement(doc, DynaXml.TASK_ARG_LIST, "");
            
            Vector<Variable> argList = task.getArgs();
            for (int n = 0; n < argList.size(); n++)
            {
                Variable var = argList.get(n);
                
                Element e = XmlManager.createElement(doc, DynaXml.TASK_ARG, "");
                
                e.appendChild(XmlManager.createElement(doc, DynaXml.VAR_TYPE, var.getType()));
                e.appendChild(XmlManager.createElement(doc, DynaXml.VAR_NAME, var.getName()));
                
                argListElement.appendChild(e);
            }
            
            taskElement.appendChild(argListElement);
            
            
            // Task Behaviour Return.
            taskElement.appendChild(XmlManager.createElement(doc, DynaXml.TASK_RETURN, task.getReturnId()));
            
                                    
            // Behaviour.
            taskElement.appendChild(XmlManager.createElement(doc, DynaXml.BEHAVIOUR, task.getBehaviour()));  
            
            // Objects.
            
            Element objListElement = XmlManager.createElement(doc, DynaXml.OBJECT_LIST, "");
            
            Vector<GenericInstance> objList = task.getObjects();
            for (int j = 0; j < objList.size(); j++)
            {
                GenericInstance obj = objList.get(j);
                
                Element e = XmlManager.createElement(doc, DynaXml.OBJECT, "");                
                e.setAttribute(DynaXml.RESOURCE, obj.getURI());
                               
                objListElement.appendChild(e);                
            }
            
            taskElement.appendChild(objListElement);
            
            // Preconditions.
            taskElement.appendChild(XmlManager.createElement(doc, DynaXml.PRE_CONDITION, task.getPreCondition()));
            
            
            
            // Test Suite.
            
            Element testSuiteElement = XmlManager.createElement(doc, DynaXml.TEST_SUITE, "");
            
            KnowledgeTestSuite testSuite = task.getTestSuite();
            
            // Test Case Setup.
            Element setupElement = XmlManager.createElement(doc, DynaXml.TEST_SETUP, testSuite.getSetup());
            testSuiteElement.appendChild(setupElement);
            
            // Test Cases
            
            Element testCaseListElement = XmlManager.createElement(doc, DynaXml.TEST_CASE_LIST, "");
            
            Vector<KnowledgeTestCase> testCaseList = testSuite.getTestCaseList();
            for (int m = 0; m < testCaseList.size(); m++)
            {
                KnowledgeTestCase testCase = testCaseList.get(m);
                
                Element testCaseElement = XmlManager.createElement(doc, DynaXml.TEST_CASE, "");
                
                Element testNameElement = XmlManager.createElement(doc, DynaXml.TEST_CASE_NAME, testCase.getName());
                testCaseElement.appendChild(testNameElement);
                
                Element testCodeElement = XmlManager.createElement(doc, DynaXml.TEST_CASE_CODE, testCase.getTestCode());
                testCaseElement.appendChild(testCodeElement);
                
                testCaseListElement.appendChild(testCaseElement);
            }
            
            testSuiteElement.appendChild(testCaseListElement);
            
            taskElement.appendChild(testSuiteElement);
             
        }
        catch (DOMException e)
        {            
            taskElement = null;
            throw new XmlManagerException(e.getMessage (), e.getCause ());
        }

        return taskElement;
    }
    
    /**
     * Converts XML to task list.
     * @param taskListElement XML task list element
     * @param dependencyList List of task dependencies found in the xml document
     * @throws uregina.engg.eil.dyna.util.XmlManagerException Xml Manager Exception
     */
    public void xmlToTaskList(Element taskListElement, Vector<DependencyList> dependencyList) throws XmlManagerException, Exception
    {
        if (taskListElement == null)
        {
            throw new XmlManagerException("Invalid XML file structure : missing root element " + DynaXml.TASK_LIST);
        }
        
        NodeList nodeList = taskListElement.getChildNodes();
        removeEmptyTextNodes(nodeList);
        for (int i = 0; i < nodeList.getLength(); i++)
        {            
            Node n = nodeList.item(i);
            Task task = xmlToTask((Element) n, dependencyList);
            createTask(task, null);
        }                            
    }
    
    /**
     * Converts XML to task.
     * @param taskElement XML task element
     * @param dependencyList List of task dependencies found in the xml document
     * @return A task
     * @throws uregina.engg.eil.dyna.util.XmlManagerException Xml Manager Exception
     */
    public Task xmlToTask(Element taskElement, Vector<DependencyList> dependencyList) throws XmlManagerException
    {        
        Project project = ProjectController.getProjectController().getProject();
        NamespaceList nsList = project.getNamespaceList();
        GenericKB kb = ProjectController.getProjectController().getKB();
        
        removeEmptyTextNodes(taskElement.getChildNodes());
        NodeList nodeList = taskElement.getChildNodes();
        
        String name = XmlManager.getElementString((Element) nodeList.item(0), DynaXml.TASK_NAME);
        String documentation = XmlManager.getElementString((Element) nodeList.item(1), DynaXml.TASK_DOCUMENTATION);
                        
        Task task = new Task(name);
        task.setDocumentation(documentation);
                
        // Sub Tasks.
        Element subElement = (Element) nodeList.item(2);
        NodeList subNodeList = subElement.getChildNodes();
        removeEmptyTextNodes(subNodeList);
        for (int i = 0; i < subNodeList.getLength(); i++)
        {
            Node n = subNodeList.item(i);
            Task subTask = xmlToTask((Element) n, dependencyList);
            task.addSubTask(subTask);
        }     
        
        // Dependencies.
        DependencyList dl = new DependencyList(task);
        Element depListElement = (Element) nodeList.item(3);
        NodeList depNodeList = depListElement.getChildNodes();
        removeEmptyTextNodes(depNodeList);
        for (int k = 0; k < depNodeList.getLength(); k++)
        {
            Node n = depNodeList.item(k);
            
            String dep = XmlManager.getElementString((Element) n, DynaXml.TASK_DEPENDENCY);                        
            
            dl.addDep(dep);
        }        
        dependencyList.add(dl);
        
        
        // Task Behaviour Arguments.
        Element argListElement = (Element) nodeList.item(4);
        NodeList argNodeList = argListElement.getChildNodes();
        removeEmptyTextNodes(argNodeList);
        for (int n = 0; n < argNodeList.getLength(); n++)
        {
            Node argNode = argNodeList.item(n);
            
            NodeList varNodeList = argNode.getChildNodes();
            removeEmptyTextNodes(varNodeList);
            
            Node typeNode = varNodeList.item(0);
            String argType = XmlManager.getElementString((Element) typeNode, DynaXml.VAR_TYPE);
            
            Node nameNode = varNodeList.item(1);
            String argName = XmlManager.getElementString((Element) nameNode, DynaXml.VAR_NAME);
            
            task.addArg(argName, argType);
        }
        
        
        // Task Behaviour Return.
        String retName = XmlManager.getElementString((Element) nodeList.item(5), DynaXml.TASK_RETURN);                
        task.setReturn(retName, "");
        
        
        // Behaviour.
        String behaviour = XmlManager.getElementString((Element) nodeList.item(6), DynaXml.BEHAVIOUR);        
        task.setBehaviour(behaviour);
               
        // Objects.
        Element objListElement = (Element) nodeList.item(7);
        NodeList objNodeList = objListElement.getChildNodes();
        removeEmptyTextNodes(objNodeList);
        for (int j = 0; j < objNodeList.getLength(); j++)
        {
            Node n = objNodeList.item(j);
                        
            String prefix = "";
            
            // Get the resource string.
            String res = ((Element) n).getAttribute(DynaXml.RESOURCE);
            
            // Get the prefix for the object name.
            String namespace = res.substring(0, res.lastIndexOf("#") + 1);
            if (nsList.containsNamespace(namespace))
            {
                Namespace ns = nsList.getNamespaceByName(namespace);
                
                if (kb instanceof OwlKB)
                {
                    String kbUri = ((OwlKB) kb).getURI();
                    String namespaceShort = namespace.substring(0, namespace.lastIndexOf("#") + 1);
                    if (!(kbUri.equals(namespaceShort)))
                    {
                        prefix = ns.getPrefix() + ":";
                    }
                }
                
            }
                        
            // Parse the resource string to get the name of the object.
            String objName = prefix + res.substring(res.lastIndexOf("#") + 1);
            
            // Get the instance from the KB.            
            GenericInstance obj = kb.getInstance(objName);
            
            // Add the instance to the task.
            task.addObject(obj);
        }
        
        // Precondition.
        String preCondition = XmlManager.getElementString((Element) nodeList.item(8), DynaXml.PRE_CONDITION);
        task.setPreCondition(preCondition);
        
        
        // Test Suite.
        KnowledgeTestSuite testSuite = task.getTestSuite();
        
        Element testSuiteElement = (Element) nodeList.item(9);                
        NodeList testSuiteNodeList = testSuiteElement.getChildNodes();
        removeEmptyTextNodes(testSuiteNodeList);
        
        // Test Setup.        
        String setup = XmlManager.getElementString((Element) testSuiteNodeList.item(0), DynaXml.TEST_SETUP);
        testSuite.setSetup(setup);
        
        // Test Cases.
        Element testCaseListElement = (Element) testSuiteNodeList.item(1);
        NodeList testCaseListNodeList = testCaseListElement.getChildNodes();
        removeEmptyTextNodes(testCaseListNodeList);
        
        for (int m = 0; m < testCaseListNodeList.getLength(); m++)
        {
            Element testCaseElement = (Element) testCaseListNodeList.item(m);
            
            NodeList testCaseNodeList = testCaseElement.getChildNodes();
            removeEmptyTextNodes(testCaseNodeList);
            
            String testName = XmlManager.getElementString((Element) testCaseNodeList.item(0), DynaXml.TEST_CASE_NAME);
            String testCode = XmlManager.getElementString((Element) testCaseNodeList.item(1), DynaXml.TEST_CASE_CODE);
            
            KnowledgeTestCase testCase = new KnowledgeTestCase(testName);
            testCase.setTestCode(testCode);
            
            testSuite.addTestCase(testCase);
        }
        
        
        
        return task;                
    }
    
    
    
    /**
     * Attempt to remove same level, empty text nodes.
     * @param list possibly empty list of nodes.
     */
    public static void removeEmptyTextNodes(NodeList list)
    {
        for ( int index = 0; index < list.getLength (); index++ )
        {
            Node n = list.item ( index );
            
            if ( n.TEXT_NODE == n.getNodeType () )
                n.getParentNode ().removeChild ( n );
        }
    }

    public void notify(TopicEvent event) throws Exception
    {
        String[] topic = StringSplitter.split(event.getTopic(), ".");        
        Hashtable data = event.getData();
                
        if ((topic[0]+topic[1]).equals(EventService.COMMAND +
                                       EventService.TASK))
        {
                            
            if (topic[2].equals(EventService.CREATE))
            {
                createTask((String) data.get(EventService.TASK_NAME),
                           (String) data.get(EventService.PARENT_TASK_NAME));
            }
            else if (topic[2].equals(EventService.OPEN))
            {
                openTask((String) data.get(EventService.TASK_NAME));
            }                
            else if (topic[2].equals(EventService.DELETE))
            {
                deleteTask((String) data.get(EventService.TASK_NAME));
            }
            else if (topic[2].equals(EventService.RENAME))
            {
                renameTask((Task) data.get(EventService.TASK_OBJECT),
                           (String) data.get(EventService.TASK_NAME)); // new name of task
            }
            else if (topic[2].equals(EventService.EDIT_DOC))
            {
                editDoc((Task) data.get(EventService.TASK_OBJECT),
                        (String) data.get(EventService.DOCUMENTATION));
            }
            else if (topic[2].equals(EventService.BEHAVIOUR))
            {
                if (topic[3].equals(EventService.EDIT))
                {
                    editBehaviour((Task) data.get(EventService.TASK_OBJECT),
                                  (String) data.get(EventService.CODE));
                }
                else if (topic[3].equals(EventService.RUN))
                {
                    //runBehaviour((Task) data.get(EventService.TASK_OBJECT));
                    System.out.println("WARNING: Task Behaviour cannot be run on it's own! (TaskController)");
                }
            }
            else if (topic[2].equals(EventService.ARGS))
            {
                if (topic[3].equals(EventService.EDIT))
                {
                    editArgs((Task) data.get(EventService.TASK_OBJECT),
                             (String) data.get(EventService.ARG_LIST_STR));
                }
            }
            else if (topic[2].equals(EventService.RETURN))
            {
                if (topic[3].equals(EventService.EDIT))
                {
                    editReturn((Task) data.get(EventService.TASK_OBJECT),
                               (String) data.get(EventService.TASK_RETURN));
                }
            }
            else if (topic[2].equals(EventService.PRE_CONDITION))
            {
                if (topic[3].equals(EventService.EDIT))
                {
                    editPreCondition((Task) data.get(EventService.TASK_OBJECT),
                                     (String) data.get(EventService.PRE_CONDITION_TEXT));
                }
            }
            else if (topic[2].equals(EventService.DEPENDENCY))
            {
                if (topic[3].equals(EventService.CREATE))
                {
                    createDependency((String) data.get(EventService.TASK_NAME),
                                     (String) data.get(EventService.DEP_NAME));
                }
            }
            else if (topic[2].equals(EventService.TSK_OBJECT))
            {
                if (topic[3].equals(EventService.CREATE))
                {
                    createObject((String) data.get(EventService.TASK_NAME),                             
                                 (String) data.get(EventService.TSK_OBJECT_NAME),
                                 (String) data.get(EventService.CLS_NAME));
                }
                else if (topic[3].equals(EventService.ADD))
                {                       
                    addObject((String) data.get(EventService.TASK_NAME),                             
                              (GenericInstance) data.get(EventService.TSK_OBJECT_OBJECT));
                }
                else if (topic[3].equals(EventService.DELETE))
                {
                    deleteObject((Task) data.get(EventService.TASK_OBJECT),                             
                                 (GenericInstance) data.get(EventService.TSK_OBJECT_OBJECT));
                }
            }
            else if (topic[2].equals(EventService.TEST))
            {
                if (topic[3].equals(EventService.CREATE))
                {
                    createTestCase((Task) data.get(EventService.TASK_OBJECT),
                                   (String) data.get(EventService.TEST_NAME));
                }
                else if (topic[3].equals(EventService.DELETE))
                {
                    deleteTestCase((Task) data.get(EventService.TASK_OBJECT),
                                   (KnowledgeTestCase) data.get(EventService.TEST_CASE_OBJECT));
                }
                else if (topic[3].equals(EventService.EDIT))
                {
                    editTestCase((Task) data.get(EventService.TASK_OBJECT),
                                 (KnowledgeTestCase) data.get(EventService.TEST_CASE_OBJECT),
                                 (String) data.get(EventService.CODE));
                }
                else if (topic[3].equals(EventService.SETUP))
                {
                    editTestCaseSetup((Task) data.get(EventService.TASK_OBJECT),
                                      (String) data.get(EventService.CODE));
                }
                else if (topic[3].equals(EventService.RUN))
                {                                        
                    runTestCase((Task) data.get(EventService.TASK_OBJECT),
                                (KnowledgeTestCase) data.get(EventService.TEST_CASE_OBJECT));
                }
                else if (topic[3].equals(EventService.RUN_ALL))
                {                    
                    runAllTestCases((Task) data.get(EventService.TASK_OBJECT));
                }
            }        
                            
        }
    }

    private void fireCreateTaskFeedback(Task task, Task parent) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
        if (parent != null)
        {
            eventData.put(EventService.PARENT_TASK_OBJECT, parent);
        }
                        
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK +
                                          EventService.CREATE, 
                                          eventData);
        EventService.getEventService().fireEvent(event);    
                
    }

    private void fireDeleteTaskFeedback(Task task) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK +
                                          EventService.DELETE, 
                                          eventData);
        EventService.getEventService().fireEvent(event);   
    }

    private void fireOpenTaskFeedback(Task task) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK +
                                          EventService.OPEN, 
                                          eventData);
        EventService.getEventService().fireEvent(event);  
    }

    private void fireRenameTaskFeedback(Task task, String oldName) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
        eventData.put(EventService.TASK_NAME, oldName);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK +
                                          EventService.RENAME, 
                                          eventData);
        EventService.getEventService().fireEvent(event);  
    }

    private void fireEditDocFeedback(Task task, String documentation) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
        eventData.put(EventService.DOCUMENTATION, documentation);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK +
                                          EventService.EDIT_DOC, 
                                          eventData);
        EventService.getEventService().fireEvent(event);  
    }

    private void fireEditBehaviourFeedback(Task task, String behaviour) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
        eventData.put(EventService.CODE, behaviour);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK +
                                          EventService.BEHAVIOUR +
                                          EventService.EDIT, 
                                          eventData);
        EventService.getEventService().fireEvent(event);
    }

    private void fireCreateDependencyFeedback(Task task, Task dep) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
        eventData.put(EventService.DEP_OBJECT, dep);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK +
                                          EventService.DEPENDENCY +                                         
                                          EventService.CREATE, 
                                          eventData);
        EventService.getEventService().fireEvent(event);
    }

    private void fireDeleteAllDepFeedback(Task task) throws Exception
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

    private void fireDeleteAllTaskObjFeedback(Task task) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
                              
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK +
                                          EventService.TSK_OBJECT +                                         
                                          EventService.DELETE_ALL, 
                                          eventData);
        EventService.getEventService().fireEvent(event);
    }

    private void fireCreateObjectFeedback(Task task, GenericInstance object) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
        eventData.put(EventService.TSK_OBJECT_OBJECT, object);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK + 
                                          EventService.TSK_OBJECT +                                         
                                          EventService.CREATE, 
                                          eventData);
        EventService.getEventService().fireEvent(event);
    }

    private void fireDeleteObjectFeedback(Task task, GenericInstance obj) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
        eventData.put(EventService.TSK_OBJECT_OBJECT, obj);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK + 
                                          EventService.TSK_OBJECT +                                         
                                          EventService.DELETE, 
                                          eventData);
        EventService.getEventService().fireEvent(event);
    }

    private void fireEditPreConditionFeedback(Task task, String preCondition) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
        eventData.put(EventService.PRE_CONDITION_TEXT, preCondition);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK +
                                          EventService.PRE_CONDITION +
                                          EventService.EDIT, 
                                          eventData);
        EventService.getEventService().fireEvent(event);
    }

    private void fireCreateTestCaseFeedback(Task task, KnowledgeTestCase testCase) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
        eventData.put(EventService.TEST_CASE_OBJECT, testCase);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK +
                                          EventService.TEST +
                                          EventService.CREATE, 
                                          eventData);
        EventService.getEventService().fireEvent(event);
    }

    private void fireDeleteTestCaseFeedback(Task task, KnowledgeTestCase testCase) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.TASK_OBJECT, task);
        eventData.put(EventService.TEST_CASE_OBJECT, testCase);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.TASK +
                                          EventService.TEST +
                                          EventService.DELETE, 
                                          eventData);
        EventService.getEventService().fireEvent(event);
    }

    private void fireEditTestCaseFeedback(Task task, KnowledgeTestCase testCase, String code) throws Exception
    {
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.TASK_OBJECT, task);
        data.put(EventService.TEST_CASE_OBJECT, testCase);
        data.put(EventService.CODE, code);

        TopicEvent topicEvent = new TopicEvent(this, 
                                          EventService.FEEDBACK +                                               
                                          EventService.TASK +
                                          EventService.TEST +
                                          EventService.EDIT,
                                          data);
        EventService.getEventService().fireEvent(topicEvent);
    }

    private void fireEditTestCaseSetupFeedback(Task task, String setup) throws Exception
    {
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.TASK_OBJECT, task);       
        data.put(EventService.CODE, setup);

        TopicEvent topicEvent = new TopicEvent(this, 
                                          EventService.FEEDBACK +                                               
                                          EventService.TASK +
                                          EventService.TEST +
                                          EventService.SETUP,
                                          data);
        EventService.getEventService().fireEvent(topicEvent);
    }
    
    private void fireEditArgsFeedback(Task task) throws Exception
    {
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.TASK_OBJECT, task);       
        
        TopicEvent topicEvent = new TopicEvent(this, 
                                          EventService.FEEDBACK +                                               
                                          EventService.TASK +
                                          EventService.ARGS +
                                          EventService.EDIT,
                                          data);
        EventService.getEventService().fireEvent(topicEvent);
    }

    private void fireEditReturnFeedback(Task task) throws Exception
    {
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.TASK_OBJECT, task);       
        
        TopicEvent topicEvent = new TopicEvent(this, 
                                          EventService.FEEDBACK +                                               
                                          EventService.TASK +
                                          EventService.RETURN +
                                          EventService.EDIT,
                                          data);
        EventService.getEventService().fireEvent(topicEvent);
    }
        
    
}
