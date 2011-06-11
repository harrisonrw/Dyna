/*
 * ObjectiveBrowser.java
 *
 * Created on August 21, 2006, 2:52 PM
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import ca.uregina.engg.eil.dyna.controller.ObjectiveController;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.Objective;
import ca.uregina.engg.eil.dyna.resource.Resource;
import ca.uregina.engg.eil.dyna.util.StringSplitter;
import ca.uregina.engg.eil.dyna.widget.HeaderPanel;
import ca.uregina.engg.eil.dyna.widget.ItemBrowser;

/**
 * Displays the objectives in a tree and provides buttons for manipulating the objectives.
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class ObjectiveBrowser extends ItemBrowser implements TopicEventListener, Resource
{
    /**
     * Name of the root node in the tree.
     */
    private static final String ROOT_NODE = "Objectives";
    
    /**
     * Show objective button.
     */
    private JButton m_showButton;
    
    /**
     * Add objective button.
     */
    private JButton m_addButton;
    
    /**
     * Delete objective button.
     */
    private JButton m_delButton;    
                   
    /**
     * Creates a new instance of ObjectiveBrowser
     * @param objectiveController The objective controller
     */
    public ObjectiveBrowser()
    {
        super(OBJECTIVE_BROWSER_TITLE, ROOT_NODE);
                
        ImageIcon addIcon = new ImageIcon(ObjectiveBrowser.class.getResource(ADD_OBJECTIVE_IMG));
        ImageIcon viewIcon = new ImageIcon(ObjectiveBrowser.class.getResource(SHOW_OBJECTIVE_IMG));
        ImageIcon delIcon = new ImageIcon(ObjectiveBrowser.class.getResource(DEL_OBJECTIVE_IMG));
        
        m_showButton = new JButton(viewIcon);
        m_showButton.setSize(16, 16);
        m_showButton.setMargin(new Insets(0, 0, 0, 0));
        m_showButton.setBorderPainted(false);
        m_showButton.setRolloverEnabled(true);
        m_showButton.setToolTipText(SHOW_OBJECTIVE_TT); 
        m_showButton.setEnabled(false);
        m_showButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                onShowObjective(event);
            }
        });
        
        m_addButton = new JButton(addIcon);
        m_addButton.setSize(16, 16);
        m_addButton.setMargin(new Insets(0, 0, 0, 0));
        m_addButton.setBorderPainted(false);
        m_addButton.setRolloverEnabled(true);
        m_addButton.setToolTipText(ADD_OBJECTIVE_TT);   
        m_addButton.setEnabled(false);
        m_addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                onAddObjective(event);
            }
        });
        
        m_delButton = new JButton(delIcon);
        m_delButton.setSize(16, 16);
        m_delButton.setMargin(new Insets(0, 0, 0, 0));
        m_delButton.setBorderPainted(false);
        m_delButton.setRolloverEnabled(true);
        m_delButton.setToolTipText(DEL_OBJECTIVE_TT);    
        m_delButton.setEnabled(false);
        m_delButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                onDelObjective(event);
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
        
        // Objective Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.OBJECTIVE +
                                                   EventService.CREATE, this);
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.OBJECTIVE +
                                                   EventService.DELETE, this);
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.OBJECTIVE +
                                                   EventService.RENAME, this);
    }
    
    /**
     * Handler for show objective button pressed event.
     * @param event Show objective button pressed event
     */
    private void onShowObjective(ActionEvent event)
    {
        TreePath currentSelection = m_tree.getSelectionPath();
        if (currentSelection != null) 
        {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());

            String objective = (String)currentNode.getUserObject();
            
            fireOpenObjectiveCommand(objective);
        } 
    }
    
    /**
     * Handler for delete objective button pressed event.
     * @param event Delete objective button pressed event
     */
    private void onDelObjective(ActionEvent event)
    {
        TreePath currentSelection = m_tree.getSelectionPath();
        if (currentSelection != null) 
        {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)(currentSelection.getLastPathComponent());

            String objective = (String)currentNode.getUserObject();

            fireDeleteObjectiveCommand(objective);            
        } 
    }
    
    /**
     * Handler for add objective button pressed event.
     * @param event Add objective button pressed event
     */
    private void onAddObjective(ActionEvent event)
    {
        fireCreateObjectiveCommand("Objective" + java.lang.Math.random());                
    }
    
    /**
     * Handler for objective added event.
     * @param event Objective added event
     */
    public void objectiveCreated(Objective objective)
    {                                   
        addNode(m_rootNode, objective.getName(), true);
    }

    /**
     * Handler for objective deleted event.
     * @param event Objective that was deleted
     */
    public void objectiveDeleted(Objective objective)
    {        
        // Find the node.
        DefaultMutableTreeNode objectiveNode = findNode(m_rootNode, objective.getName());
                    
        m_treeModel.removeNodeFromParent(objectiveNode);                
        
        m_tree.scrollPathToVisible(new TreePath(m_rootNode.getPath()));
    }

    /**
     * Handler for objective renamed event.
     * @param event Objective renamed event
     */
    public void objectiveRenamed(Objective objective, String oldName)
    {
        DefaultMutableTreeNode node = findNode(m_rootNode, oldName);
        
        if (node != null)
        {                        
            node.setUserObject(objective.getName());
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
        m_root = "Objectives";
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
                                            EventService.OBJECTIVE))
        {
            // Objective Feedback Events.
            
            if (topic[2].contains(EventService.CREATE))
            {                                
                objectiveCreated((Objective) data.get(EventService.OBJECTIVE_OBJECT));
            }
            else if (topic[2].contains(EventService.DELETE))
            {
                objectiveDeleted((Objective) data.get(EventService.OBJECTIVE_OBJECT));
            }
            else if (topic[2].contains(EventService.RENAME))
            {
                objectiveRenamed((Objective) data.get(EventService.OBJECTIVE_OBJECT),
                                 (String) data.get(EventService.OBJECTIVE_NAME));
            }
        }
    }
    
    private void fireCreateObjectiveCommand(String objectiveName)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.OBJECTIVE_NAME, objectiveName);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.OBJECTIVE + 
                                              EventService.CREATE, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot create objective! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }

    private void fireDeleteObjectiveCommand(String objectiveName)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.OBJECTIVE_NAME, objectiveName);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.OBJECTIVE + 
                                              EventService.DELETE, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot delete objective! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }

    private void fireOpenObjectiveCommand(String objectiveName)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.OBJECTIVE_NAME, objectiveName);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.OBJECTIVE + 
                                              EventService.OPEN, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot open objective! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
