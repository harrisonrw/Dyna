/*
 * TreePane.java
 *
 * Created on August 12, 2006, 10:01 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.widget;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TreePane extends JScrollPane
{
    protected JTree m_tree;
    
    /**
     * Creates a new instance of TreePane
     */
    public TreePane(JTree tree)
    {        
        super(tree);
        m_tree = tree;
    }
    
    public JTree getTree()
    {
        return m_tree;
    }
    
}
