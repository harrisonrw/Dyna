/*
 * ProjectController.java
 *
 * Created on September 1, 2006, 11:13 AM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.controller;

import ca.uregina.engg.eil.dyna.model.Namespace;
import ca.uregina.engg.eil.dyna.model.NamespaceList;
import ca.uregina.engg.eil.dyna.model.OwlKB;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
import edu.stanford.smi.protegex.owl.model.OWLOntology;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Vector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.GenericKB;
import ca.uregina.engg.eil.dyna.model.Objective;
import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.util.DependencyList;
import ca.uregina.engg.eil.dyna.util.DynaXml;
import ca.uregina.engg.eil.dyna.util.StringSplitter;
import ca.uregina.engg.eil.dyna.util.XmlManager;
import ca.uregina.engg.eil.dyna.model.Project;
import ca.uregina.engg.eil.dyna.util.XmlManagerException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Handles the logic for the Project.
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class ProjectController implements TopicEventListener
{        
    private static ProjectController m_projectController = null;
    
    /**
     * The project.
     */
    private Project m_project;
    
    /**
     * The filename of the Dyna project dyn file.
     */
    private String m_projectFilename;
              
    private GenericKB m_kb;
           
    private PrintStream m_outputStream;
    private PrintStream m_testOutputStream;
    
    /** Creates a new instance of ProjectController */
    public ProjectController()
    {
        m_project = null; 
        m_projectFilename = "";
                        
        m_kb = null;
                
        m_outputStream = System.out;
        
        initEvents();
        
        
        
    }
    
    public static ProjectController getProjectController()
    {
        if (m_projectController == null)
        {
            m_projectController = new ProjectController();
        }
        
        return m_projectController;
    }
    
    private void initEvents()
    {
        // Project Command Events
        
        EventService.getEventService().addListener(EventService.COMMAND + 
                                                   EventService.PROJECT, this);
                
    }
            
    /**
     * Returns the project.
     * @return The project
     */
    public Project getProject()
    {
        return m_project;
    }
    
    /**
     * Sets the project.
     * @param project The project
     */
    public void setProject(Project project)
    {
        m_project = project;
    }
    
    /**
     * Returns the filename for the project.
     * @return The filename for the project
     */
    public String getProjectFilename()
    {
        return m_projectFilename;
    }
    
    /**
     * Sets the filename of the project.
     * @param projectFilename The filename of the project
     */
    public void setProjectFilename(String projectFilename)
    {
        m_projectFilename = projectFilename;
    }
               
    public GenericKB getKB()
    {
        return m_kb;
    }
    
    /**
     * Creates a new project and notifies the listeners.
     * @param projectName Name of the project
     */
    public void createProject(String projectName) throws Exception
    {                
        m_project = new Project(projectName);
                       
        if (m_kb instanceof OwlKB)
        {
            NamespaceList nsList = m_project.getNamespaceList();
            nsList.addNamespace(new Namespace("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#"));
            nsList.addNamespace(new Namespace("xsd", "http://www.w3.org/2001/XMLSchema#"));
            nsList.addNamespace(new Namespace("rdfs", "http://www.w3.org/2000/01/rdf-schema#"));
            nsList.addNamespace(new Namespace("owl", "http://www.w3.org/2002/07/owl#"));
            nsList.addNamespace(new Namespace("dyn", "http://142.3.27.23/~harrisonr/dynamic.owl#"));
                                  
            String myPrefix = (((OwlKB) m_kb).getBaseFilename()).charAt(0) + "1";
            String myUri = ((OwlKB) m_kb).getURI();
            nsList.addNamespace(new Namespace(myPrefix, myUri));            
        }
        
        
        fireCreateProjectFeedback(projectName);
    }
    
    /**
     * Opens a project from dyn file and notifies listeners.
     * @param filename Filename of project
     * @throws uregina.engg.eil.dyna.util.XmlManagerException Xml Manager Exception
     */
    public void openProject(String filename) throws XmlManagerException, Exception
    {
        TaskController taskController = TaskController.getTaskController();
        ObjectiveController objectiveController = ObjectiveController.getObjectiveController();
        
        Document doc = XmlManager.readXmlFile(filename);
        Element rootElement = (Element) doc.getFirstChild();
        
        if (rootElement == null)
        {
            throw new XmlManagerException( "Invalid XML file structure : missing root element " + DynaXml.PROJECT);
        }
                             
        m_project = new Project("Project");
                
        NodeList nodeList = rootElement.getChildNodes();
        removeEmptyTextNodes(nodeList);
        
        String projectName = XmlManager.getElementString((Element) nodeList.item(0), "ProjectName");        
        m_project.setName(projectName);
        
        String documentation = XmlManager.getElementString((Element) nodeList.item(1), "Documentation");
        m_project.setDocumentation(documentation);
        
        String url = XmlManager.getElementString((Element) nodeList.item(2), "Url");
        m_project.setURL(url);
        
        Element nsListElement = (Element) rootElement.getElementsByTagName("NamespaceList").item(0);
        xmlToNamespaceList(nsListElement);
        
        Vector<DependencyList> dependencyList = new Vector<DependencyList>();
        
        Element taskListElement =  (Element) rootElement.getElementsByTagName(DynaXml.TASK_LIST).item(0);
        taskController.xmlToTaskList(taskListElement, dependencyList);
                        
        // Set the task dependecies.
        for (int i = 0; i < dependencyList.size(); i++)
        {
            DependencyList dl = dependencyList.get(i);
            
            Task dlTask = dl.getTask();
            Task task = taskController.getTask(dlTask.getName());            
            Vector<String> depList = dl.getDepList();
            
            for (int j = 0; j < depList.size(); j++)
            {
                Task dep = taskController.getTask(depList.get(j));                
                taskController.createDependency(task, dep);
            }
        }
        
        
        Element objectiveListElement = (Element) rootElement.getElementsByTagName(DynaXml.OBJECTIVE_LIST).item(0);
        objectiveController.xmlToObjectiveList(objectiveListElement);
        
        m_projectFilename = filename;
                
        fireOpenProjectFeedback(m_project.getObjectives(), m_project.getTasks());
    }
    
    public void xmlToNamespaceList(Element nsListElement) throws XmlManagerException
    {
        removeEmptyTextNodes(nsListElement.getChildNodes());
        NodeList nodeList = nsListElement.getChildNodes();
        removeEmptyTextNodes(nodeList);
        
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Node nsNode = nodeList.item(i);
            
            NodeList nodeList2 = nsNode.getChildNodes();
            removeEmptyTextNodes(nodeList2);
            
            String prefix = XmlManager.getElementString((Element) nodeList2.item(0), "Prefix");
            String uri = XmlManager.getElementString((Element) nodeList2.item(1), "Uri");
        
            NamespaceList nsList = m_project.getNamespaceList();
            nsList.addNamespace(new Namespace(prefix, uri)); 
        }                              
    }
    
    /**
     * Closes project and notifies listeners.
     */
    public void closeProject() throws Exception
    {       
        fireCloseProjectFeedback();
                
        m_project = null;
        m_projectFilename = "";
    }
            
    /**
     * Saves the project to file and notifies the listeners.
     * @param filename Filename of the project
     * @throws uregina.engg.eil.dyna.util.XmlManagerException Xml Manager Exception
     */
    public void saveProject(String filename) throws XmlManagerException, Exception
    {
        TaskController taskController = TaskController.getTaskController();
        ObjectiveController objectiveController = ObjectiveController.getObjectiveController();
                        
        Document doc = XmlManager.createEmptyDocument();
        
            
        Element rootElement = XmlManager.createElement(doc, DynaXml.PROJECT, "");        
        rootElement.setAttribute("xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#"); // RDF
        doc.appendChild(rootElement);

        Element nameElement = XmlManager.createElement(doc, "ProjectName", m_project.getName());
        rootElement.appendChild(nameElement);
        
        Element docElement = XmlManager.createElement(doc, "Documentation", m_project.getDocumentation());
        rootElement.appendChild(docElement);
        
        Element urlElement = XmlManager.createElement(doc, "Url", m_project.getURL());
        rootElement.appendChild(urlElement);
        
        rootElement.appendChild(namespaceListToXml(doc));                        
        
        Element taskElement = taskController.taskListToXml(doc);
        Element objectiveElement = objectiveController.objectiveListToXml(doc);

        rootElement.appendChild(taskElement);
        rootElement.appendChild(objectiveElement);          
                           
        XmlManager.writeXmlToFile(doc, filename);
                 
        m_projectFilename = filename;
        
        fireSaveProjectFeedback();
    }
    
    public Element namespaceListToXml(Document doc)
    {
        Element nsListElement = null;
        
        try
        {
            nsListElement = XmlManager.createElement(doc, "NamespaceList", "");
            
            NamespaceList nsList = m_project.getNamespaceList();
            for (int i = 0; i < nsList.size(); i++)
            {
                Namespace ns = nsList.get(i);
                
                Element nsElement = XmlManager.createElement(doc, "Namespace", "");
                
                Element prefixElement = XmlManager.createElement(doc, "Prefix", ns.getPrefix());
                nsElement.appendChild(prefixElement);
                
                Element uriElement = XmlManager.createElement(doc, "Uri", ns.getURI());
                nsElement.appendChild(uriElement);
                
                nsListElement.appendChild(nsElement);                                
            }
        } 
        catch (XmlManagerException e)
        {
            e.printStackTrace();
        }
        
        return nsListElement;
    }
    
    public void importOwl(String filename) throws Exception
    {       
        FileInputStream is = new FileInputStream(filename);
        
        // Read the file into an OwlModel
        OWLModel owlModel = ProtegeOWL.createJenaOWLModelFromInputStream(is);
        
        // Extract the static knowledge.
        
        
        // Extract the dynamic knowledge.
        
    }
    
    public void exportOwl(String filename) throws XmlManagerException, Exception
    {
        TaskController taskController = TaskController.getTaskController();
        ObjectiveController objectiveController = ObjectiveController.getObjectiveController();
        
        Document doc = XmlManager.createEmptyDocument();
        
        String ontologyURI = ((OwlKB) m_kb).getURI();
        
        // Namespace.
        Element rootElement = XmlManager.createElement(doc, "rdf:RDF", "");     
        NamespaceList nsList = m_project.getNamespaceList();
        for (int i = 0; i < nsList.size(); i++)
        {
            Namespace ns = nsList.get(i);
            rootElement.setAttribute("xmlns:" + ns.getPrefix(), ns.getURI());
        }
               
        rootElement.setAttribute("xmlns", m_project.getURL());
        rootElement.setAttribute("xml:base", m_project.getURL() + "#");        	               
        doc.appendChild(rootElement);

        // OWL imports.        
        Element ontElement = XmlManager.createElement(doc, "owl:Ontology", "");
        ontElement.setAttribute("rdf:about", "");
         
        for (int j = 0; j < nsList.size(); j++)
        {
            Namespace ns = nsList.get(j);
            
            String prefix = ns.getPrefix();
            
            if (!(prefix.equals("rdf") || prefix.equals("rdfs") || 
                  prefix.equals("owl") || prefix.equals("xsd")))
            {
                Element impElement = XmlManager.createElement(doc, "owl:imports", "");
                                
                String uri = ns.getURI();
                String baseUri = uri.substring(0, uri.indexOf("#"));
                
                impElement.setAttribute("rdf:resource", baseUri);
                ontElement.appendChild(impElement);
            }
        }
                        
        rootElement.appendChild(ontElement);
                
        // Project.
        Element projectElement = XmlManager.createElement(doc, "dyn:Project", "");
        projectElement.setAttribute("rdf:ID", m_project.getName());
        
        Element projectNameElement = XmlManager.createElement(doc, "dyn:name", m_project.getName());
        projectNameElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        projectElement.appendChild(projectNameElement);
        
        Element projectDocElement = XmlManager.createElement(doc, "dyn:documentation", m_project.getDocumentation());
        projectDocElement.setAttribute("rdf:datatype", "http://www.w3.org/2001/XMLSchema#string");
        projectElement.appendChild(projectDocElement);
                
        taskController.taskListToOwl(projectElement, doc);        
        objectiveController.objectiveListToOwl(projectElement, doc);
                        
        rootElement.appendChild(projectElement);
        
        XmlManager.writeXmlToFile(doc, filename);
        
        fireExportOwlFeedback();
    }
    
    public void showProject() throws Exception
    {        
        fireShowProjectFeedback(m_project.getName(), m_project.getDocumentation(), m_project.getURL(),
                                m_project.getNamespaceList());
    }
    
    public void editProject(String projectName, String doc, String url, NamespaceList namespaceList) throws Exception
    {
        m_project.setName(projectName);
        m_project.setDocumentation(doc);
        m_project.setURL(url);
        
        NamespaceList theNSList = m_project.getNamespaceList();
        theNSList.clear();
        for (int i = 0; i < namespaceList.size(); i++)
        {
            theNSList.addNamespace(namespaceList.get(i));
        }
        
        fireEditProjectFeedback(m_project);
    }
    
    public GenericKB getKnowledgeBase()
    {
        return m_kb;
    }
    
    public void setKnowledgeBase(GenericKB kb)
    {
        m_kb = kb;
    }
    
    public void setOutputStream(PrintStream out)
    {
        m_outputStream = out;
    }
    
    public PrintStream getOutputStream()
    {
        return m_outputStream;
    }
    
    public void setTestOutputStream(PrintStream out)
    {
        m_testOutputStream = out;
    }
    
    public PrintStream getTestOutputStream()
    {
        return m_testOutputStream;
    }

    public void notify(TopicEvent event) throws Exception
    {
        String[] topic = StringSplitter.split(event.getTopic(), ".");
        Hashtable data = event.getData();
        
        if ((topic[0]+topic[1]).equals(EventService.COMMAND +
                                       EventService.PROJECT))
        {
            // Project Command Events.
            
            if (topic[2].equals(EventService.CREATE))
            {
                createProject((String) data.get(EventService.PROJECT_NAME));
            }
            else if (topic[2].equals(EventService.OPEN))
            {
                openProject((String) data.get(EventService.PROJECT_FILENAME));                
            }
            else if (topic[2].equals(EventService.CLOSE))
            {
                closeProject();
            }                        
            else if (topic[2].equals(EventService.SAVE))
            {
                saveProject((String) data.get(EventService.PROJECT_FILENAME));
            }
            else if (topic[2].equals(EventService.EXPORT))
            {
                if (topic[3].equals(EventService.OWL))
                {
                    exportOwl((String) data.get(EventService.PROJECT_FILENAME));
                }
            }
            else if (topic[2].equals(EventService.IMPORT))
            {
                if (topic[3].equals(EventService.OWL))
                {
                    importOwl((String) data.get(EventService.PROJECT_FILENAME));
                }
            }
            else if (topic[2].equals(EventService.SHOW))
            {
                showProject();
            }
            else if (topic[2].equals(EventService.EDIT))
            {
                editProject((String) data.get(EventService.PROJECT_NAME),
                            (String) data.get(EventService.DOCUMENTATION),
                            (String) data.get(EventService.URL),
                            (NamespaceList) data.get(EventService.NAMESPACE_LIST));
            }
        }
    }

    private void fireCreateProjectFeedback(String projectName) throws Exception
    {        
        // Create the Project Added event and fire it.
        
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.PROJECT_NAME, projectName);
                        
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.PROJECT +
                                          EventService.CREATE, 
                                          eventData);
        EventService.getEventService().fireEvent(event);    
    }

    private void fireOpenProjectFeedback(Vector<Objective> objectives, Vector<Task> tasks) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();
        eventData.put(EventService.OBJECTIVE_VECTOR, objectives);
        eventData.put(EventService.TASK_VECTOR, tasks);
                        
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.PROJECT +
                                          EventService.OPEN, 
                                          eventData);
        EventService.getEventService().fireEvent(event);  
    }

    private void fireCloseProjectFeedback() throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();        
                        
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.PROJECT +
                                          EventService.CLOSE, 
                                          eventData);
        EventService.getEventService().fireEvent(event); 
    }
    
    private void fireSaveProjectFeedback() throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();        
        
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.PROJECT +
                                          EventService.SAVE, 
                                          eventData);
        EventService.getEventService().fireEvent(event); 
    }
    
    private void fireExportOwlFeedback() throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();        
        
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.PROJECT +
                                          EventService.EXPORT +
                                          EventService.OWL,
                                          eventData);
        EventService.getEventService().fireEvent(event); 
    }

    private void fireShowProjectFeedback(String projectName, String doc, String url,
                                         NamespaceList namespaceList) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();        
        eventData.put(EventService.PROJECT_NAME, projectName);
        eventData.put(EventService.DOCUMENTATION, doc);
        eventData.put(EventService.URL, url);
        eventData.put(EventService.NAMESPACE_LIST, namespaceList);
        
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.PROJECT +
                                          EventService.SHOW,
                                          eventData);
        EventService.getEventService().fireEvent(event); 
    }
    
    private void fireEditProjectFeedback(Project project) throws Exception
    {
        Hashtable<String, Object> eventData = new Hashtable<String, Object>();        
        eventData.put(EventService.PROJECT_OBJ, project);
        
        TopicEvent event = new TopicEvent(this, 
                                          EventService.FEEDBACK + 
                                          EventService.PROJECT +
                                          EventService.EDIT,
                                          eventData);
        EventService.getEventService().fireEvent(event); 
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
}
