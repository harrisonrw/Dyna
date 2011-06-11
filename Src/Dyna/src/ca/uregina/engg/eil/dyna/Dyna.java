/*
 * Dyna.java
 *
 * Created on August 9, 2006, 8:30 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna;

import ca.uregina.engg.eil.dyna.model.Namespace;
import ca.uregina.engg.eil.dyna.model.NamespaceList;
import ca.uregina.engg.eil.dyna.ui.ProjectWindow;
import edu.stanford.smi.protege.model.DefaultKnowledgeBase;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.util.AllowableAction;
import edu.stanford.smi.protege.widget.AbstractTabWidget;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import ca.uregina.engg.eil.dyna.controller.ObjectiveController;
import ca.uregina.engg.eil.dyna.controller.ProjectController;
import ca.uregina.engg.eil.dyna.controller.TaskController;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.interpreter.Interpreter;
import ca.uregina.engg.eil.dyna.interpreter.InterpreterManager;
import ca.uregina.engg.eil.dyna.interpreter.TestInterpreter;
import ca.uregina.engg.eil.dyna.model.OwlKB;
import ca.uregina.engg.eil.dyna.model.ProtegeKB;
import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.resource.Resource;
import ca.uregina.engg.eil.dyna.ui.AboutDlg;
import ca.uregina.engg.eil.dyna.ui.ObjectiveBrowser;
import ca.uregina.engg.eil.dyna.ui.OutputPanel;
import ca.uregina.engg.eil.dyna.ui.ProjectFileChooser;
import ca.uregina.engg.eil.dyna.ui.TaskBrowser;
import ca.uregina.engg.eil.dyna.ui.Workspace;
import ca.uregina.engg.eil.dyna.util.BrowserLauncher;
import ca.uregina.engg.eil.dyna.util.StringSplitter;
import ca.uregina.engg.eil.dyna.util.XmlManagerException;


/**
 * Dyna Protege Plugin.  Dyna is for modeling dynamic knowledge (tasks
 * and objectives).
 *
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class Dyna extends AbstractTabWidget implements TopicEventListener, Resource
{                 
    /**
     * Displays the tasks in a tree control.
     */
    private TaskBrowser m_taskBrowser; 
    
    /**
     * Displays the objectives in a tree control.
     */
    private ObjectiveBrowser m_objectiveBrowser;
    
    /**
     * Window for puting MDI windows.
     */
    private Workspace m_workspace;

    /**
     * Window for displaying output.
     */
    private OutputPanel m_outputPanel;
    
    private OutputPanel m_testOutputPanel;
    
    /**
     * The menu.
     */
    private JMenu m_menu;
    
    /**
     * New Project menu.
     */
    private JMenuItem m_newProject;
    
    /**
     * Open Project menu.
     */
    private JMenuItem m_openProject;
    
    /**
     * Close Project menu.
     */
    private JMenuItem m_closeProject;
    
    /**
     * Save Project menu.
     */
    private JMenuItem m_saveProject;
    
    /**
     * Save As menu.
     */
    private JMenuItem m_saveAsProject;    
    
    // Export menu.
    private JMenu m_exportProject;
    private JMenuItem m_exportOwl;
    
    // Import menu.
    private JMenu m_importProject;
    private JMenuItem m_importOwl;
    
    private JMenuItem m_properties;
    
    /**
     * Documentation menu.
     */
    private JMenuItem m_docHelp;
    
    /**
     * About menu.
     */
    private JMenuItem m_aboutHelp;
    
    private String m_projectFilename;
    
    /** Creates a new instance of Dyna */
    public Dyna()
    {       
        m_projectFilename = "";
                
    }

    /**
     * Initializes the Dyna tab.
     */
    public void initialize()
    {
        // Initialize the tab text
        setLabel(PROGRAM_TITLE);
          
        createMenu();
           
        ProjectController projectController = ProjectController.getProjectController();
        TaskController taskController = TaskController.getTaskController();
        ObjectiveController objectiveController = ObjectiveController.getObjectiveController();
        
        m_taskBrowser = new TaskBrowser();
        m_objectiveBrowser = new ObjectiveBrowser();     
        m_workspace = new Workspace();
        m_outputPanel = new OutputPanel();
        m_testOutputPanel = new OutputPanel();
        
        // Create the tabbed panel for the task and objective browser.
        JTabbedPane browserTabbedPane = new JTabbedPane();
        browserTabbedPane.add(TASKS_TITLE, m_taskBrowser);
        browserTabbedPane.add(OBJECTIVES_TITLE, m_objectiveBrowser);
                                 
        // Create the output tabbed panel.
        JTabbedPane outputTabbedPane = new JTabbedPane();
        outputTabbedPane.add("Output", m_outputPanel);
        outputTabbedPane.add("Testing", m_testOutputPanel);
        
        // The browsers and workspace go in one split pane.
        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                               browserTabbedPane, m_workspace);
        splitPane1.setOneTouchExpandable(true);
        splitPane1.setDividerLocation(200);
        
        // The above split pane and the output panels go in the other split pane.
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                               splitPane1, outputTabbedPane);
        splitPane2.setOneTouchExpandable(true);
        splitPane2.setDividerLocation(500);
        
        // add the components to the tab widget
	setLayout(new BorderLayout());
        add(splitPane2, BorderLayout.CENTER);
        //add(m_workspace, BorderLayout.CENTER);
        //add(outputTabbedPane, BorderLayout.SOUTH);
               
        initEvents();
                                      
        // Set the knowledge base.
        KnowledgeBase kb = getKnowledgeBase();
        if (kb instanceof JenaOWLModel)
        {
            OwlKB owlKB = new OwlKB((OWLModel) kb);
            projectController.setKnowledgeBase(owlKB);
        }
        else if (kb instanceof DefaultKnowledgeBase)
        {
            ProtegeKB protegeKB = new ProtegeKB(kb);
            projectController.setKnowledgeBase(protegeKB);
        }
        
        
        projectController.setOutputStream(m_outputPanel.getOutputStream());
        projectController.setTestOutputStream(m_testOutputPanel.getOutputStream());
        
        // Setup the interpreters.
        InterpreterManager interpManager = InterpreterManager.getInterpreterManager();
        Interpreter interp = new Interpreter();
        TestInterpreter testInterp = new TestInterpreter();
        interpManager.addInterpreter(Interpreter.INTERPRETER, interp);
        interpManager.addInterpreter(TestInterpreter.TEST_INTERPRETER, testInterp);
               
    }
    
    private void initEvents()
    {
        // Project Command Events
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.PROJECT, this);
        
        
    }

    /**
     * Creates the menu items.
     */
    private void createMenu()
    {
        JMenuBar menubar = this.getMainWindowMenuBar();
        
        if (menubar != null)
        {
            // Check to see if the Dyna menu already exists.
            for (int i = 0; i < menubar.getMenuCount(); i++)
            {
                JMenu menu = menubar.getMenu(i);
                if (menu.getText().equals(PROGRAM_TITLE))
                    return; // Menu exists
            }
            
            // Build the menu.
            m_menu = new JMenu(PROGRAM_TITLE);
            m_menu.setMnemonic(PROGRAM_MNEMONIC);
            
            // --------------------------------------------
            // Project menu.
            
            // New Project.
            m_newProject = new JMenuItem(NEW_PROJECT_TITLE);
            m_newProject.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    onNewProject(event);
                }
            });
            m_menu.add(m_newProject);
            
            // Open Project.
            m_openProject = new JMenuItem(OPEN_PROJECT_TITLE);
            m_openProject.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    onOpenProject(event);
                }
            });
            m_menu.add(m_openProject);
            
            // Close Project.
            m_closeProject = new JMenuItem(CLOSE_PROJECT_TITLE);
            m_closeProject.setEnabled(false);
            m_closeProject.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    onCloseProject(event);
                }
            });
            m_menu.add(m_closeProject);
            
            m_menu.addSeparator();
            
            // Save Project.
            m_saveProject = new JMenuItem(SAVE_PROJECT_TITLE);
            m_saveProject.setEnabled(false);
            m_saveProject.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    onSaveProject(event);
                }
            });
            m_menu.add(m_saveProject);
            
            // Save Project As.
            m_saveAsProject = new JMenuItem(SAVE_PROJECT_AS_TITLE);
            m_saveAsProject.setEnabled(false);
            m_saveAsProject.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    onSaveProjectAs(event);
                }
            });
            m_menu.add(m_saveAsProject);
            
            m_menu.addSeparator();
            
            // --------------------------------------------
            // Import menu.
            
            m_importProject = new JMenu(IMPORT_PROJECT_TITLE);
            
            m_importOwl = new JMenuItem(IMPORT_OWL_TITLE);
            m_importOwl.setEnabled(true);
            m_importOwl.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    onImportOwl(event);
                }
            });
            m_importProject.add(m_importOwl);
            
            m_menu.add(m_importProject);
                       
            // --------------------------------------------
            // Export menu.
            
            m_exportProject = new JMenu(EXPORT_PROJECT_TITLE);
            
            m_exportOwl = new JMenuItem(EXPORT_OWL_TITLE);
            m_exportOwl.setEnabled(true);
            m_exportOwl.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    onExportOwl(event);
                }
            });
            m_exportProject.add(m_exportOwl);
            
            m_menu.add(m_exportProject);
            
            m_menu.addSeparator();
            
            // --------------------------------------------
            // Project properties menu.
            
            m_properties = new JMenuItem(PROPERTIES_TITLE);
            m_properties.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    onProperties(event);
                }
            });
            m_menu.add(m_properties);
            
            m_menu.addSeparator();
            
            // --------------------------------------------
            // Help menu.
            
            m_docHelp = new JMenuItem(DOCUMENTATION_TITLE);
            m_docHelp.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    onDocumentation(event);
                }
            });
            m_menu.add(m_docHelp);
            
            m_aboutHelp = new JMenuItem(ABOUT_TITLE);
            m_aboutHelp.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    onAbout(event);
                }
            });
            m_menu.add(m_aboutHelp);
                                    
            menubar.add(m_menu);
        }
    }
    
    /**
     * Handler for New Project menu item pressed.
     * @param event New Project menu item pressed
     */
    private void onNewProject(ActionEvent event)
    {    
        // Checking if close project menu item is enabled tells us if a project is already open.
        if (m_closeProject.isEnabled())
        {   
            if (confirmCloseProject())
            {
                fireCreateProjectCommand("New Project"); // TODO: get rid of project name
            }
        }
        else
        {
            fireCreateProjectCommand("New Project"); // TODO: get rid of project name
        }                            
    }
    
    /**
     * Handler for Open Project menu item pressed.
     * @param event Open Project menu item pressed
     */
    private void onOpenProject(ActionEvent event)
    {        
        
        // Checking if close project menu item is enabled tells us if a project is already open.
        if (m_closeProject.isEnabled())
        {   
            if (confirmCloseProject())
            {
                openProject(); 
            }
        }
        else
        {
            openProject();
        }
        
    }
    
    /**
     * Handler for Close menu item pressed.
     * @param event Close menu item pressed
     */
    private void onCloseProject(ActionEvent event)
    {
        boolean result = confirmCloseProject();
    }
    
    /**
     * Handler for Save menu item pressed.
     * @param event Save menu item pressed
     */
    private void onSaveProject(ActionEvent event)
    {
        saveProject(m_projectFilename);               
    }
    
    /**
     * Handler for Save As menu item pressed.
     * @param event Save As menu item pressed
     */
    private void onSaveProjectAs(ActionEvent event)
    {
        saveProject("");
    }
    
    private void onImportOwl(ActionEvent event)
    {
        importOwl();
    }
    
    private void onExportOwl(ActionEvent event)
    {
        exportOwl();
    }
    
    private void onProperties(ActionEvent event)
    {        
        fireShowProjectCommand();
    }
    
    /**
     * Handler for Documentation menu item pressed.
     * @param event Documentation menu item pressed
     */
    private void onDocumentation(ActionEvent event)
    {
        String currentDir = System.getProperty("user.dir");
        String docPath = currentDir + "/plugins/ca.uregina.engg.eil.dyna/doc/index.html";
        
        BrowserLauncher.openURL(docPath);
        
    }
    
    /**
     * Handler for About menu item pressed.
     * @param event About menu item pressed
     */
    private void onAbout(ActionEvent event)
    {
        AboutDlg dlg = new AboutDlg(javax.swing.JOptionPane.getFrameForComponent(this));
        dlg.setVisible(true);
    }
    
    /**
     * Confirm if the user wants to close the project.
     * @return <ul>
     *    <li>true : user chose to close project</li>
     *    <li>false : user chose not to close project</li>
     * </ul>
     */
    private boolean confirmCloseProject()
    {
        boolean closedProject = true;
        
        
        int result = JOptionPane.showConfirmDialog(null, 
                                                   "Save current project?", 
                                                   "Dyna", 
                                                   JOptionPane.YES_NO_CANCEL_OPTION, 
                                                   JOptionPane.QUESTION_MESSAGE);

        if (result != JOptionPane.CANCEL_OPTION)
        {
            if (result == JOptionPane.YES_OPTION)
            {
                saveProject(m_projectFilename);
            }

            // Now close the project.                
            fireCloseProjectCommand();    
        }
        else
        {
            closedProject = false;
        }
                        
        return closedProject;
    }
    
    private void openProject()
    {
        ProjectFileChooser projectFileChooser = new ProjectFileChooser(DYNA_FILE_DESC, DYNA_EXT);

        if (projectFileChooser.showOpenDialog(null) == ProjectFileChooser.APPROVE_OPTION)
        {
            String filename = projectFileChooser.getSelectedFile().getAbsolutePath();

            fireOpenProjectCommand(filename);
        }    
    }
    
    /**
     * Saves the project.
     */
    private void saveProject(String filename)
    {
        // Show a save file dialog if filename is blank.
        if (filename.equals(""))
        {
            ProjectFileChooser projectFileChooser = new ProjectFileChooser(DYNA_FILE_DESC, DYNA_EXT);

            if (projectFileChooser.showSaveDialog(null) == ProjectFileChooser.APPROVE_OPTION)
            {
                m_projectFilename = projectFileChooser.getSelectedFile().getAbsolutePath();  
                filename = m_projectFilename;
            }      
        }
        
        fireSaveProjectCommand(filename);     
    }
    
    private void importOwl()
    {
        ProjectFileChooser projectFileChooser = new ProjectFileChooser(OWL_FILE_DESC, OWL_EXT);

        if (projectFileChooser.showOpenDialog(null) == ProjectFileChooser.APPROVE_OPTION)
        {
            String filename = projectFileChooser.getSelectedFile().getAbsolutePath();  
            
            fireImportOwlCommand(filename);
        }   
    }
    
    private void exportOwl()
    {
        ProjectFileChooser projectFileChooser = new ProjectFileChooser(OWL_FILE_DESC, OWL_EXT);

        if (projectFileChooser.showSaveDialog(null) == ProjectFileChooser.APPROVE_OPTION)
        {
            String filename = projectFileChooser.getSelectedFile().getAbsolutePath();  
            
            fireExportOwlCommand(filename);
        }   
    }
    
    /**
     * Handler for Project Created event.     
     */
    private void projectCreated()
    {
        m_closeProject.setEnabled(true);
        m_saveProject.setEnabled(true);
        m_saveAsProject.setEnabled(true);
    }

    private void projectOpened()
    {
        m_closeProject.setEnabled(true);
        m_saveProject.setEnabled(true);
        m_saveAsProject.setEnabled(true);
    }
    
    /**
     * Handler for Project Closed event.    
     */
    public void projectClosed()
    {
        m_closeProject.setEnabled(false);
        m_saveProject.setEnabled(false);
        m_saveAsProject.setEnabled(false);
    }
    
    public void showProjectProperties(String projectName, String doc, String url,
                                      NamespaceList namespaceList)
    {
        ProjectWindow dlg = new ProjectWindow(javax.swing.JOptionPane.getFrameForComponent(this), 
                                        projectName, doc, url, namespaceList);
        dlg.setVisible(true);  
        
        if (dlg.wasOKPressed())
        {
            fireEditProjectCommand(dlg.getProjectName(), dlg.getDocumentation(), dlg.getURL(), dlg.getNamespaceList());
        }
    }
    
    public void notify(TopicEvent event) throws Exception
    {
        String[] topic = StringSplitter.split(event.getTopic(), ".");            
        Hashtable data = event.getData();
        
        if ((topic[0]+topic[1]).equals(EventService.FEEDBACK +
                                       EventService.PROJECT))
        {
            // Project Command Events.
            
            if (topic[2].equals(EventService.CREATE))
            {
                projectCreated();
            }
            else if (topic[2].equals(EventService.OPEN))
            {
                projectOpened();
            }
            else if (topic[2].equals(EventService.CLOSE))
            {
                projectClosed();
            }            
            else if (topic[2].equals(EventService.SAVE))
            {
                // Do nothing
            }
            else if (topic[2].equals(EventService.SHOW))
            {
                showProjectProperties((String) data.get(EventService.PROJECT_NAME),
                                      (String) data.get(EventService.DOCUMENTATION),
                                      (String) data.get(EventService.URL),
                                      (NamespaceList) data.get(EventService.NAMESPACE_LIST));
            }
            else if (topic[2].equals(EventService.CLEAR_OUTPUT))
            {
                m_outputPanel.clear();
                m_testOutputPanel.clear();
            }
            
        }
    }
    
    private void fireCreateProjectCommand(String projectName)
    {
        try
        {            
            Hashtable<String, Object> data = new Hashtable<String, Object>(1);
            data.put (EventService.PROJECT_NAME, projectName);            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.PROJECT + 
                                              EventService.CREATE, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot create new project! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void fireOpenProjectCommand(String filename)
    {
        // Fire open project command.
        try
        {
            Hashtable<String, Object> data = new Hashtable<String, Object>(1);
            data.put (EventService.PROJECT_FILENAME, filename);            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.PROJECT + 
                                              EventService.OPEN, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }       
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot open the project!" + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void fireCloseProjectCommand() 
    {
        try
        {                        
            // Fire close project command.
            Hashtable<String, Object> data = new Hashtable<String, Object>(1);
            data.put (EventService.PROJECT_NAME, "New Project");            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.PROJECT + 
                                              EventService.CLOSE, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot close project! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }          
    }
        
    private void fireSaveProjectCommand(String filename)
    {
        try
        {                        
            // Fire close project command.
            Hashtable<String, Object> data = new Hashtable<String, Object>(1);
            data.put(EventService.PROJECT_FILENAME, filename);
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.PROJECT + 
                                              EventService.SAVE, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot save project! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireImportOwlCommand(String filename)
    {
        try
        {                        
            // Fire import owl command.
            Hashtable<String, Object> data = new Hashtable<String, Object>(1);
            data.put(EventService.PROJECT_FILENAME, filename);
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.PROJECT + 
                                              EventService.IMPORT +
                                              EventService.OWL, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot import OWL! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireExportOwlCommand(String filename)
    {
        try
        {                        
            // Fire export owl command.
            Hashtable<String, Object> data = new Hashtable<String, Object>(1);
            data.put(EventService.PROJECT_FILENAME, filename);
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.PROJECT + 
                                              EventService.EXPORT +
                                              EventService.OWL, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot export to OWL! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireShowProjectCommand()
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>(1);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.PROJECT + 
                                              EventService.SHOW, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot show Project Properties! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    
    private void fireEditProjectCommand(String projectName, String doc, String url, NamespaceList namespaceList)
    {
        try
        {                        
            // Fire edit project command.
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.PROJECT_NAME, projectName);
            data.put(EventService.DOCUMENTATION, doc);
            data.put(EventService.URL, url);
            data.put(EventService.NAMESPACE_LIST, namespaceList);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.PROJECT + 
                                              EventService.EDIT, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot export to OWL! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
     
    /**
     * The main function for the Dyna plugin.
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        // Launch Protege (for debugging).
        edu.stanford.smi.protege.Application.main(args);               
    }


    
    
    
}
