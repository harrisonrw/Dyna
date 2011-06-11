/*
 * TaskHierarchyPanel.java
 *
 * Created on August 12, 2006, 10:19 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.ui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.controller.TaskController;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.resource.Resource;
import ca.uregina.engg.eil.dyna.util.StringSplitter;
import ca.uregina.engg.eil.dyna.widget.HeaderPanel;
import ca.uregina.engg.eil.dyna.widget.ItemBrowser;

/**
 * Displays the tasks in a tree a provides buttons for manipulating them.
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TaskBrowser extends ItemBrowser implements TopicEventListener, Resource
{       
    /**
     * Name of the root node in the tree.
     */
    private static final String ROOT_NODE = "Tasks";
    
    /**
     * Show task button.
     */
    protected JButton m_showButton;
    
    /**
     * Add task button.
     */
    protected JButton m_addButton;
    
    /**
     * Delete task button.
     */
    protected JButton m_delButton;    
            
    /**
     * Creates a new instance of TaskHierarchyPanel
     * @param taskController The task controller
     */
    public TaskBrowser()
    {
        super(TASK_BROWSER_TITLE, ROOT_NODE);
                
        ImageIcon addIcon = new ImageIcon(TaskBrowser.class.getResource(ADD_TASK_IMG));
        ImageIcon viewIcon = new ImageIcon(TaskBrowser.class.getResource(SHOW_TASK_IMG));
        ImageIcon delIcon = new ImageIcon(TaskBrowser.class.getResource(DEL_TASK_IMG));
        
        m_showButton = new JButton(viewIcon);
        m_showButton.setSize(16, 16);
        m_showButton.setMargin(new Insets(0, 0, 0, 0));
        m_showButton.setBorderPainted(false);
        m_showButton.setRolloverEnabled(true);
        m_showButton.setToolTipText(SHOW_TASK_TT);    
        m_showButton.setEnabled(false);
        m_showButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                onShowTask(event);
            }
        });
        
        m_addButton = new JButton(addIcon);
        m_addButton.setSize(16, 16);
        m_addButton.setMargin(new Insets(0, 0, 0, 0));
        m_addButton.setBorderPainted(false);
        m_addButton.setRolloverEnabled(true);
        m_addButton.setToolTipText(ADD_TASK_TT);     
        m_addButton.setEnabled(false);
        m_addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                onAddTask(event);
            }
        });
        
        m_delButton = new JButton(delIcon);
        m_delButton.setSize(16, 16);
        m_delButton.setMargin(new Insets(0, 0, 0, 0));
        m_delButton.setBorderPainted(false);
        m_delButton.setRolloverEnabled(true);
        m_delButton.setToolTipText(DEL_TASK_TT);       
        m_delButton.setEnabled(false);
        m_delButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                onDelTask(event);
            }
        });
                
        m_headerPanel.add(m_showButton);
        m_headerPanel.add(m_addButton);
        m_headerPanel.add(m_delButton);       
        
        initEvents();
    }
    
    private void initEvents()
    {
        // Project Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.PROJECT +
                                                   EventService.CREATE, this);
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.PROJECT +
                                                   EventService.OPEN, this);
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.PROJECT +
                                                   EventService.CLOSE, this);
        
        // Task Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK +
                                                   EventService.CREATE, this);
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK +
                                                   EventService.DELETE, this);
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK +
                                                   EventService.RENAME, this);
        
    }
           
    /**
     * Handler for add task button pressed event.
     * @param event Add task button pressed event
     */
    private void onAddTask(ActionEvent event)
    {
        String parentTask = "";
        
        TreePath currentSelection = m_tree.getSelectionPath();
        if (currentSelection != null) 
        {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());

            parentTask = (String)currentNode.getUserObject();                       
        }
                
        fireCreateTaskCommand("Task" + java.lang.Math.random(), parentTask);
    }
    
    /**
     * Handler for delete task button pressed event.
     * @param event Delete task button pressed event
     */
    private void onDelTask(ActionEvent event)
    {
        TreePath currentSelection = m_tree.getSelectionPath();
        if (currentSelection != null) 
        {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());

            String task = (String)currentNode.getUserObject();

            fireDeleteTaskCommand(task);                        
        }
    }
    
    /**
     * Handler for show task button pressed event.
     * @param event Show task button pressed event
     */
    private void onShowTask(ActionEvent event)
    {
        TreePath currentSelection = m_tree.getSelectionPath();
        if (currentSelection != null) 
        {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());

            String task = (String)currentNode.getUserObject();
            
            fireOpenTaskCommand(task);
        }    
    }

    private void addSubTask(Task task)
    {
        Task parent = null;
        DefaultMutableTreeNode parentNode = null;
        
        // Find the parent node.
        if (task.getParent() != null)
        {
            parent = task.getParent();
            parentNode = findNode(m_rootNode, parent.getName());
        }
        else
        {
            parentNode = m_rootNode;
        }
            
        // Add the node.
        addNode(parentNode, task.getName(), true);
        
        // Add the sub nodes.
        Vector<Task> subList = task.getSubTaskList();
        for (int i = 0; i < subList.size(); i++)
        {
            addSubTask(subList.get(i));
        }
    }
    
    private void taskCreated(Task task, Task parentTask)
    {
        addSubTask(task);
    }
            
    private void taskDeleted(Task task)
    {                       
        // Find the node.
        DefaultMutableTreeNode taskNode = findNode(m_rootNode, task.getName());
                    
        m_treeModel.removeNodeFromParent(taskNode);                
        
        m_tree.scrollPathToVisible(new TreePath(m_rootNode.getPath()));
    }
    
    public void taskRenamed(Task task, String oldName)
    {
        DefaultMutableTreeNode node = findNode(m_rootNode, oldName);
        
        if (node != null)
        {                        
            node.setUserObject(task.getName());
            m_treeModel.nodeChanged(node);
        }
    }
            
    private void projectCreated()
    {
        // Enable the buttons.
        m_showButton.setEnabled(true);
        m_addButton.setEnabled(true);
        m_delButton.setEnabled(true);
    }
    
    private void projectOpened()
    {
        // Enable the buttons.
        m_showButton.setEnabled(true);
        m_addButton.setEnabled(true);
        m_delButton.setEnabled(true);
    }

    private void projectClosed()
    {
        // Create a blank tree model.
        m_root = "Tasks";
        m_rootNode = new DefaultMutableTreeNode(m_root);        
                                
        m_treeModel = new DefaultTreeModel(m_rootNode);
        m_tree.setModel(m_treeModel);
        
        // Disable the buttons.
        m_showButton.setEnabled(false);
        m_addButton.setEnabled(false);
        m_delButton.setEnabled(false);
    }

    public void notify(TopicEvent event) throws Exception
    {
        String[] topic = StringSplitter.split(event.getTopic(), ".");
        Hashtable data = event.getData();
        
        if ((topic[0]+topic[1]).equals(EventService.FEEDBACK +
                                       EventService.PROJECT))
        {
            // Project Feedback Events.
            
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
        }
        else if ((topic[0]+topic[1]).equals(EventService.FEEDBACK +
                                            EventService.TASK))
        {
            // Task Feedback events.
                        
            if (topic[2].contains(EventService.CREATE))
            {
                Task parentTask = null;
                if (data.equals(EventService.PARENT_TASK_OBJECT))
                {
                    parentTask = (Task) data.get(EventService.PARENT_TASK_OBJECT);
                }
                taskCreated((Task) data.get(EventService.TASK_OBJECT), parentTask);
            }
            else if (topic[2].equals(EventService.DELETE))
            {
                taskDeleted((Task) data.get(EventService.TASK_OBJECT));
            }
            else if (topic[2].equals(EventService.RENAME))
            {
                taskRenamed((Task) data.get(EventService.TASK_OBJECT),
                            (String) data.get(EventService.TASK_NAME));
            }
        }
    }

    private void fireCreateTaskCommand(String taskName, String parentTaskName)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_NAME, taskName);
            data.put(EventService.PARENT_TASK_NAME, parentTaskName);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TASK + 
                                              EventService.CREATE, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot create task! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }

    private void fireDeleteTaskCommand(String taskName)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_NAME, taskName);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TASK + 
                                              EventService.DELETE, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot delete task! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }

    private void fireOpenTaskCommand(String taskName)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_NAME, taskName);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TASK + 
                                              EventService.OPEN, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot open task! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
}
