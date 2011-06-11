/*
 * ObjectiveController.java
 *
 * Created on August 21, 2006, 4:42 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.controller;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.Objective;
import ca.uregina.engg.eil.dyna.model.Project;
import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.model.TaskPriority;
import ca.uregina.engg.eil.dyna.util.DynaXml;
import ca.uregina.engg.eil.dyna.util.StringSplitter;
import ca.uregina.engg.eil.dyna.util.XmlManager;
import ca.uregina.engg.eil.dyna.util.XmlManagerException;

/**
 * Handles objective logic.
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class ObjectiveController implements TopicEventListener
{
    private static ObjectiveController m_objectiveController = null;
    
    /**
     * Creates a new instance of ObjectiveController     
     */
    public ObjectiveController()
    {
        initEvents(); 
    }
    
    public static ObjectiveController getObjectiveController()
    {
        if (m_objectiveController == null)
        {
            m_objectiveController = new ObjectiveController();
        }
        
        return m_objectiveController;
    }
    
    private void initEvents()
    {
        // Objective Command Events.
        
        EventService.getEventService().addListener(EventService.COMMAND + 
                                                   EventService.OBJECTIVE, this);
        
        //EventService.getEventService().addListener(EventService.COMMAND + 
        //                                           EventService.TASK_PRIORITY, this);
        
        // Task Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK +
                                                   EventService.DELETE, this);
                
    }
            
    /**
     * Adds an objective and notifies listeners.
     * @param objective Objective to add
     */
    public void createObjective(Objective objective) throws Exception
    {
        ProjectController projectController = ProjectController.getProjectController();
        
        Vector<Objective> objectives = projectController.getProject().getObjectives();
        
        objectives.add(objective);
        
        fireCreateObjectiveFeedback(objective);
    }
    
    /**
     * Adds an objective and notifies listeners.
     * @param objectiveName Name of objective.
     */
    public void createObjective(String objectiveName) throws Exception
    {
        createObjective(new Objective(objectiveName));
    }
    
    /**
     * Deletes objective and notifies listeners.
     * @param objective Objective to delete.
     */
    public void deleteObjective(Objective objective) throws Exception
    {      
        ProjectController projectController = ProjectController.getProjectController();
        
        Vector<Objective> objectives = projectController.getProject().getObjectives();
        
        objectives.remove(objective);        
    
        fireDeleteObjectiveFeedback(objective);
    }
    
    /**
     * Renames objective and notifies listeners.
     * @param objective Objective to rename
     * @param newName New name of objective
     */
    public void renameObjective(Objective objective, String newName) throws Exception
    {
        String oldName = objective.getName();        
        objective.setName(newName);                
        
        fireRenameObjectiveFeedback(objective, oldName);
    }
    
    /**
     * Returns list of objectives in the project.
     * @return List of objectives in the project
     */
    public Vector<Objective> getObjectives()
    {
        ProjectController projectController = ProjectController.getProjectController();
        return projectController.getProject().getObjectives();
    }

    /**
     * Deletes objective and notifies listeners.
     * @param objective Name of objective to delete
     */
    public void deleteObjective(String objective) throws Exception
    {
        ProjectController projectController = ProjectController.getProjectController();
        
        Vector<Objective> objectives = projectController.getProject().getObjectives();
        
        // Find the objective to delete.
        for (int i = 0; i < objectives.size(); i++)
        {
            Objective obj = (Objective) objectives.get(i);
            
            if (obj.getName().equals(objective))
            {
                deleteObjective(obj);
                break;
            }
        }
    }
    
    /**
     * Show objective.
     * @param objective Objective to show
     */
    public void openObjective(Objective objective) throws Exception
    {
        fireOpenObjectiveFeedback(objective);
    }
    
    /**
     * Show objective.
     * @param objectiveName Name of objective to show
     */
    public void openObjective(String objectiveName) throws Exception
    {
        ProjectController projectController = ProjectController.getProjectController();
        
        Vector<Objective> objectives = projectController.getProject().getObjectives();
        
        // Find the objective to show.
        for (int i = 0; i < objectives.size(); i++)
        {
            Objective obj = (Objective) objectives.get(i);
            
            if (obj.getName().equals(objectiveName))
            {                
                openObjective(obj);
                
                break;
            }
        }
    }

    /**
     * Edit documentation and notify listeners.
     * @param objective Objective whose documentation to edit
     * @param documentation The new documentation
     */
    public void editDoc(Objective objective, String documentation) throws Exception
    {
        ProjectController projectController = ProjectController.getProjectController();
        
        Vector<Objective> objectives = projectController.getProject().getObjectives();
        
        for (int i = 0; i < objectives.size(); i++)
        {
            Objective obj = (Objective) objectives.get(i);
            
            if (obj.getName().equals(objective.getName()))
            {
                obj.setDocumentation(documentation);
                
                fireEditDocFeedback(obj, documentation);
                break;
            }
        }
    }
    
    /**
     * Adds task priority and notifies listeners.
     * @param objective Objective to add task priority to
     * @param task Task component of task priority
     * @param priority The priority of the task
     */
    public void createTaskPriority(Objective objective, Task task, int priority) throws Exception
    {
        TaskPriority taskPriority = new TaskPriority(task, priority);
        objective.addTaskPriority(taskPriority);              
        
        fireCreateTaskPriorityFeedback(objective, taskPriority);
    }
    
    public void createTaskPriority2(String name, String doc, Objective objective, Task task, int priority) throws Exception
    {
        TaskPriority taskPriority = new TaskPriority(task, priority);
        taskPriority.setName(name);
        taskPriority.setDocumentation(doc);
        
        objective.addTaskPriority(taskPriority);              
        
        fireCreateTaskPriorityFeedback(objective, taskPriority);
    }
    
    /**
     * Deletes task priority and notifies listeners.
     * @param objective Objective containing task priority to delete
     * @param task Task identifying task priority
     */
    public void deleteTaskPriority(Objective objective, Task task) throws Exception
    {                        
        Vector<TaskPriority> tpList = objective.getTaskPriorityList();
        
        for (int i = 0; i < tpList.size(); i++)
        {
            TaskPriority tp = tpList.get(i);
            if (tp.getTask().getName().equals(task.getName()))
            {
                objective.deleteTaskPriority(tp);
                                
                fireDeleteTaskPriorityFeedback(objective, tp);
                
                break;
            }
        }
    }
    
    public void editTaskPriority(Objective objective, Task task, int priority) throws Exception
    {
        objective.setTaskPriority(task, priority);
        //TaskPriority tp = objective.getTaskPriority(task);
        //tp.setPriority(priority);
        
        fireEditTaskPriorityFeedback(objective, task, priority);
    }

    /**
     * Handler for task deleted event.
     * @param event Task deleted event
     */    
    public void taskDeleted(Task task) throws Exception
    {
        ProjectController projectController = ProjectController.getProjectController();
        
        Vector<Objective> objectives = projectController.getProject().getObjectives();
        
        // Delete all task priorities that match the deleted task.
        
        for (int i = 0; i < objectives.size(); i++)
        {
            Objective objective = objectives.get(i);
            TaskPriority tp = objective.getTaskPriority(task);
            if (tp != null)
            {            
                deleteTaskPriority(objective, task);
            }
        }
    }  
    
    public void runTests(Objective objective) throws Exception
    {
        Vector<TaskPriority> tpList = objective.getTaskPriorityList();
        
        for (int i = 0; i < tpList.size(); i++)
        {
            TaskPriority tp = tpList.get(i);
            
            fireRunAllTaskTestsCommand(tp.getTask());
        }
    }
             
    public void objectiveListToOwl(Element element, Document doc) throws XmlManagerException
    {
        ProjectController projectController = ProjectController.getProjectController();
        Vector<Objective> objectiveList = projectController.getProject().getObjectives();
                       
        for (int i = 0; i < objectiveList.size(); i++)
        {
            Element objectiveListElement = XmlManager.createElement(doc, "dyn:objectiveList", "");
                        
            Objective objective = objectiveList.get(i);
            objectiveListElement.appendChild(objectiveToOwl(objective, doc));                     
            
            element.appendChild(objectiveListElement);
        }
    }
    
    public Element objectiveToOwl(Objective objective, Document doc) throws XmlManagerException
    {
        Element objectiveElement = null;
        
        try
        {
            objectiveElement = XmlManager.createElement(doc, "dyn:Objective", "");            
            objectiveElement.setAttribute("rdf:ID", objective.getName());
                   
            // Objective Name.
            Element objectiveNameElement = XmlManager.createElement(doc, "dyn:name", objective.getName());
            objectiveNameElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");            
            objectiveElement.appendChild(objectiveNameElement);
            
            // Objective Documentation.
            Element objectiveDocElement = XmlManager.createElement(doc, "dyn:documentation", objective.getDocumentation());
            objectiveDocElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
            objectiveElement.appendChild(objectiveDocElement);
            
            // Task Priority List.                        
            Vector<TaskPriority> tpList = objective.getTaskPriorityList();
            for (int k = 0; k < tpList.size(); k++)
            {
                Element tpListElement = XmlManager.createElement(doc, "dyn:taskPriorityList", "");
                
                TaskPriority tp = tpList.get(k);
                tpListElement.appendChild(taskPriorityToOwl(tp, doc));  
                
                objectiveElement.appendChild(tpListElement);
            }  
        
        }
        catch (DOMException e)
        {            
            objectiveElement = null;
            throw new XmlManagerException(e.getMessage(), e.getCause());
        }
        
        return objectiveElement;
    }
    
    public Element taskPriorityToOwl(TaskPriority tp, Document doc) throws XmlManagerException
    {
        Element tpElement = null;
        
        try
        {
            tpElement = XmlManager.createElement(doc, "dyn:TaskPriority", "");                                                
            tpElement.setAttribute("rdf:ID", tp.getName());
            
            // Name.
            Element nameElement = XmlManager.createElement(doc, "dyn:name", tp.getName());
            nameElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
            tpElement.appendChild(nameElement);
            
            // Documentation.
            Element docElement = XmlManager.createElement(doc, "dyn:documentation", tp.getDocumentation());
            docElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
            tpElement.appendChild(docElement);
            
            // Task.
            Element taskElement = XmlManager.createElement(doc, "dyn:task", "");
            taskElement.setAttribute("rdf:resource", "#" + tp.getTask().getName());
            tpElement.appendChild(taskElement);
            
            // Priority.
            Element priorityElement = XmlManager.createElement(doc, "dyn:priority", tp.getPriority());
            priorityElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#int");            
            tpElement.appendChild(priorityElement);                        
        }
        catch (DOMException e)
        {            
            tpElement = null;
            throw new XmlManagerException(e.getMessage(), e.getCause());
        }
        
        return tpElement;
    }
    
    /**
     * Converts objective list into xml.
     * @param doc XML document
     * @return XML objective list
     * @throws uregina.engg.eil.dyna.util.XmlManagerException Xml Manager Exception
     */
    public Element objectiveListToXml(Document doc) throws XmlManagerException
    {
        ProjectController projectController = ProjectController.getProjectController();
        
        Vector<Objective> objectiveList = projectController.getProject().getObjectives();
        
        Element objListElement = XmlManager.createElement(doc, DynaXml.OBJECTIVE_LIST, "");
                
        for (int i = 0; i < objectiveList.size(); i++)
        {
            Objective obj = objectiveList.get(i);
            objListElement.appendChild(objectiveToXml(obj, doc));            
        }
                        
        return objListElement;
    }

    /**
     * Converts objective into xml.
     * @param objective Objective to convert to xml
     * @param doc XML document
     * @return XML objective
     * @throws uregina.engg.eil.dyna.util.XmlManagerException Xml Manager Exception
     */
    public Element objectiveToXml(Objective objective, Document doc) throws XmlManagerException
    {
        Element objectiveElement = null;
    
        try
        {
            objectiveElement = XmlManager.createElement(doc, DynaXml.OBJECTIVE, "");
                        
            objectiveElement.appendChild(XmlManager.createElement(doc, DynaXml.OBJECTIVE_NAME, objective.getName()));
            objectiveElement.appendChild(XmlManager.createElement(doc, DynaXml.OBJECTIVE_DOCUMENTATION, objective.getDocumentation()));
            
            Vector<TaskPriority> tpList = objective.getTaskPriorityList();            
            Element tpListElement = XmlManager.createElement(doc, DynaXml.TASK_PRIORITY_LIST, "");
                        
            for (int i = 0; i < tpList.size(); i++)
            {
                TaskPriority tp = tpList.get(i);
                
                Element tpElement = XmlManager.createElement(doc, DynaXml.TASK_PRIORITY, "");
                
                tpElement.appendChild(XmlManager.createElement(doc, "Name", tp.getName()));
                tpElement.appendChild(XmlManager.createElement(doc, "Documentation", tp.getDocumentation()));
                tpElement.appendChild(XmlManager.createElement(doc, DynaXml.TASK_PRIORITY_TASK, tp.getTask().getName()));
                tpElement.appendChild(XmlManager.createElement(doc, DynaXml.TASK_PRIORITY_PRIORITY, tp.getPriority()));
                
                tpListElement.appendChild(tpElement);
                
            }
            
            objectiveElement.appendChild(tpListElement);
        }
        catch (DOMException e)
        {            
            objectiveElement = null;
            throw new XmlManagerException(e.getMessage (), e.getCause ());
        }

        return objectiveElement;
    }
    
    /**
     * Converts xml to objective list.
     * @param objectiveListElement XML objective list element
     * @throws uregina.engg.eil.dyna.util.XmlManagerException Xml Manager Exception
     */
    public void xmlToObjectiveList(Element objectiveListElement) throws XmlManagerException, Exception
    {
        if (objectiveListElement == null)
        {
            throw new XmlManagerException("Invalid XML file structure : missing root element " + DynaXml.OBJECTIVE_LIST);
        }
        
        NodeList nodeList = objectiveListElement.getChildNodes();
        removeEmptyTextNodes(nodeList);
        for (int i = 0; i < nodeList.getLength(); i++)
        {            
            Node n = nodeList.item(i);
            Objective objective = xmlToObjective((Element) n);
            createObjective(objective);
        }                            
    }
    
    /**
     * Converts xml to objective.
     * @param objectiveElement XML objective element
     * @return An objective
     * @throws uregina.engg.eil.dyna.util.XmlManagerException Xml Manager Exception
     */
    public Objective xmlToObjective(Element objectiveElement) throws XmlManagerException, Exception
    {
        ProjectController projectController = ProjectController.getProjectController();
        
        removeEmptyTextNodes(objectiveElement.getChildNodes());
        NodeList nodeList = objectiveElement.getChildNodes();
        
        String name = XmlManager.getElementString((Element) nodeList.item(0), DynaXml.OBJECTIVE_NAME);
        String documentation = XmlManager.getElementString((Element) nodeList.item(1), DynaXml.OBJECTIVE_DOCUMENTATION);
                        
        Objective objective = new Objective(name);
        objective.setDocumentation(documentation);
        
        // Task Priority.
        Element tpListElement = (Element) nodeList.item(2);
        NodeList tpNodeList = tpListElement.getChildNodes();
        removeEmptyTextNodes(tpNodeList);
        for (int i = 0; i < tpNodeList.getLength(); i++)
        {
            Node n = tpNodeList.item(i);
            
            removeEmptyTextNodes(n.getChildNodes());
            NodeList nList = n.getChildNodes();
        
            String tpName = XmlManager.getElementString((Element) nList.item(0), "Name");
            String tpDoc = XmlManager.getElementString((Element) nList.item(1), "Documentation");
            
            String taskName = XmlManager.getElementString((Element) nList.item(2), DynaXml.TASK_PRIORITY_TASK);
            int priority = (int) XmlManager.getElementDouble((Element) nList.item(3), DynaXml.TASK_PRIORITY_PRIORITY);
                        
            createTaskPriority2(tpName, tpDoc, objective, projectController.getProject().getTask(taskName), priority);
        }        
        
        return objective;
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
                                       EventService.OBJECTIVE))        
        {
            // Objective Command Events.
                                     
            if (topic[2].equals(EventService.CREATE))
            {
                createObjective((String) data.get(EventService.OBJECTIVE_NAME));
            }
            else if (topic[2].equals(EventService.OPEN))
            {
                openObjective((String) data.get(EventService.OBJECTIVE_NAME));              
            }                                 
            else if (topic[2].equals(EventService.DELETE))
            {
                deleteObjective((String) data.get(EventService.OBJECTIVE_NAME));
            }
            else if (topic[2].equals(EventService.RENAME))
            {
                renameObjective((Objective) data.get(EventService.OBJECTIVE_OBJECT),
                           (String) data.get(EventService.OBJECTIVE_NAME)); // new name of objective
            }
            else if (topic[2].equals(EventService.EDIT_DOC))
            {
                editDoc((Objective) data.get(EventService.OBJECTIVE_OBJECT),
                        (String) data.get(EventService.DOCUMENTATION));
            }        
            else if (topic[2].equals(EventService.TASK_PRIORITY))
            {
                if (topic[3].equals(EventService.CREATE))
                {
                    createTaskPriority((Objective) data.get(EventService.OBJECTIVE_OBJECT),
                                       (Task) data.get(EventService.TASK_OBJECT),
                                       ((Integer) data.get(EventService.PRIORITY)).intValue());
                }
                else if (topic[3].equals(EventService.DELETE))
                {
                    deleteTaskPriority((Objective) data.get(EventService.OBJECTIVE_OBJECT),
                                       (Task) data.get(EventService.TASK_OBJECT));
                }
                else if (topic[3].equals(EventService.EDIT))
                {
                    editTaskPriority((Objective) data.get(EventService.OBJECTIVE_OBJECT),
                                     (Task) data.get(EventService.TASK_OBJECT),
                                     ((Integer) data.get(EventService.PRIORITY)).intValue());
                }
            }    
            else if (topic[2].equals(EventService.TEST))
            {
                if (topic[3].equals(EventService.RUN))
                {
                    runTests((Objective) data.get(EventService.OBJECTIVE_OBJECT));
                }
            }
        }
        else if ((topic[0]+topic[1]).equals(EventService.FEEDBACK +
                                            EventService.TASK))
        {
            if (topic[2].equals(EventService.DELETE))
            {
                taskDeleted((Task) data.get(EventService.TASK_OBJECT));
            }
        }
    }

    private void fireCreateObjectiveFeedback(Objective objective) throws Exception
    {        
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.OBJECTIVE_OBJECT, objective);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.OBJECTIVE +
                                          EventService.CREATE, 
                                          data);
        EventService.getEventService().fireEvent(event);    
    }

    private void fireDeleteObjectiveFeedback(Objective objective) throws Exception
    {
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.OBJECTIVE_OBJECT, objective);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.OBJECTIVE +
                                          EventService.DELETE, 
                                          data);
        EventService.getEventService().fireEvent(event);   
    }

    private void fireOpenObjectiveFeedback(Objective objective) throws Exception
    {
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.OBJECTIVE_OBJECT, objective);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.OBJECTIVE +
                                          EventService.OPEN, 
                                          data);
        EventService.getEventService().fireEvent(event);
    }

    private void fireRenameObjectiveFeedback(Objective objective, String oldName) throws Exception
    {
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.OBJECTIVE_OBJECT, objective);
        data.put(EventService.OBJECTIVE_NAME, oldName);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.OBJECTIVE +
                                          EventService.RENAME, 
                                          data);
        EventService.getEventService().fireEvent(event);
    }

    private void fireEditDocFeedback(Objective objective, String documentation) throws Exception
    {
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.OBJECTIVE_OBJECT, objective);
        data.put(EventService.DOCUMENTATION, documentation);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.OBJECTIVE +
                                          EventService.EDIT_DOC, 
                                          data);
        EventService.getEventService().fireEvent(event);
    }

    private void fireCreateTaskPriorityFeedback(Objective objective, TaskPriority taskPriority) throws Exception
    {
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.OBJECTIVE_OBJECT, objective);
        data.put(EventService.TASK_PRIORITY_OBJECT, taskPriority);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK +   
                                          EventService.OBJECTIVE +
                                          EventService.TASK_PRIORITY +
                                          EventService.CREATE, 
                                          data);
        EventService.getEventService().fireEvent(event);
    }

    private void fireDeleteTaskPriorityFeedback(Objective objective, 
                                                TaskPriority taskPriority) throws Exception
    {
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.OBJECTIVE_OBJECT, objective);
        data.put(EventService.TASK_PRIORITY_OBJECT, taskPriority);
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK +    
                                          EventService.OBJECTIVE +
                                          EventService.TASK_PRIORITY +
                                          EventService.DELETE, 
                                          data);
        EventService.getEventService().fireEvent(event);
    }
    
    private void fireEditTaskPriorityFeedback(Objective objective, Task task, int priority) throws Exception
    {
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.OBJECTIVE_OBJECT, objective);
        data.put(EventService.TASK_OBJECT, task);
        data.put(EventService.PRIORITY, new Integer(priority));
                                
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK +   
                                          EventService.OBJECTIVE +
                                          EventService.TASK_PRIORITY +
                                          EventService.EDIT, 
                                          data);
        EventService.getEventService().fireEvent(event);
    }

    private void fireRunAllTaskTestsCommand(Task task) throws Exception
    {                                           
        Hashtable<String, Object> data = new Hashtable<String, Object>();
        data.put(EventService.TASK_OBJECT, task);            

        TopicEvent topicEvent = new TopicEvent(this, 
                                          EventService.COMMAND +                                               
                                          EventService.TASK +
                                          EventService.TEST +
                                          EventService.RUN_ALL,
                                          data);
        EventService.getEventService().fireEvent(topicEvent);                    
       
    }
    
    
}
