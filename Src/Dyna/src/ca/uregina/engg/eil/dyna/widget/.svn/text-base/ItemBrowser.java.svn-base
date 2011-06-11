/*
 * ItemBrowser.java
 *
 * Created on August 21, 2006, 4:17 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.widget;

import java.awt.BorderLayout;
import java.util.Enumeration;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * Shows a tree control for browsing hierarchical data.
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public abstract class ItemBrowser extends JPanel
{
    /**
     * The tree control.
     */
    protected JTree m_tree;
    
    /**
     * The model for the tree.
     */
    protected DefaultTreeModel m_treeModel;
    
    /**
     * The root node in the tree.
     */
    protected DefaultMutableTreeNode m_rootNode;
    
    /**
     * The data at the root node.
     */
    protected String m_root;
        
    /**
     * A panel for showing text and buttons above the tree control.
     */
    protected HeaderPanel m_headerPanel;
          
    
    /**
     * Creates a new instance of ItemBrowser
     * @param title The text to display at the top of the item browser
     * @param root The data for the root node
     */
    public ItemBrowser(String title, String root)
    {
        // Setup the header panel.        
        m_headerPanel = new HeaderPanel(title);
        
        // Setup tree.
        
        m_root = root;
        m_rootNode = new DefaultMutableTreeNode(m_root);        
                
        m_treeModel = new DefaultTreeModel(m_rootNode);
        
        m_tree = new JTree(m_treeModel); 
        m_tree.setEditable(false);
        m_tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        m_tree.setShowsRootHandles(true);
        //m_tree.setRootVisible(false);
        
        JScrollPane treePane = new JScrollPane(m_tree);
        
        // Layout the UI.
        
        setLayout(new BorderLayout());
        add(m_headerPanel, BorderLayout.NORTH);
        add(treePane, BorderLayout.CENTER);
    }
        
    /**
     * Adds a node to the tree.
     * @param parent The parent node
     * @param child The node data (child) to add
     * @param shouldBeVisible Show the node?
     * @return The added node
     */
    public DefaultMutableTreeNode addNode(DefaultMutableTreeNode parent,
                                        Object child,
                                        boolean shouldBeVisible) 
    {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
                        
        m_treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        // Make sure the node is visible.
        if (shouldBeVisible) 
        {            
            m_tree.scrollPathToVisible(new TreePath(childNode.getPath()));           
        }
        
        return childNode;
    }
    
    /**
     * Searches the tree for a particular node.  Search starts
     * at the specified node.
     * @param currNode The node at which to start searching
     * @param item The node (data) that we are looking for
     * @return <ul>
     *    <li>The node if it is found</li>
     *    <li>null if the node is not found</li>
     * </ul>
     */
    public DefaultMutableTreeNode findNode(DefaultMutableTreeNode currNode, String item)
    {
        if (currNode != null)
        {
            String currItem = (String) currNode.getUserObject();
            
            if (currItem == item)
            {
                // Found the node.
                return currNode;
            }
            else
            {
                // Search the nodes children.
                DefaultMutableTreeNode childNode = null;
                Enumeration childNodes = currNode.children();
                for (int i = 0; i < currNode.getChildCount(); i++)
                {
                    childNode = (DefaultMutableTreeNode) childNodes.nextElement();
                    childNode = findNode(childNode, item);
                    
                    if (childNode != null)
                        return childNode;
                }
            }
        }
        
        return null;
    }
       
}
