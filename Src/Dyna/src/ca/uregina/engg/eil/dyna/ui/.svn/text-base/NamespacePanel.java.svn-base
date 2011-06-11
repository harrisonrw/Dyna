/*
 * NamespacePanel.java
 *
 * Created on March 12, 2007, 7:49 PM
 */

package ca.uregina.engg.eil.dyna.ui;

import ca.uregina.engg.eil.dyna.model.Namespace;
import ca.uregina.engg.eil.dyna.model.NamespaceList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author  robert
 */
public class NamespacePanel extends javax.swing.JPanel
{
    private NamespaceList m_namespaceList;
    private int m_currRow;
    
    /** Creates new form NamespacePanel */
    public NamespacePanel(NamespaceList namespaceList)
    {
        initComponents();
        
        m_currRow = -1;
        m_namespaceList = new NamespaceList();
        
        // Fill the table with data.
        DefaultTableModel model = (DefaultTableModel) m_nsTable.getModel();        
        for (int i = 0; i < namespaceList.size(); i++)
        {
            Namespace ns = namespaceList.get(i);
                        
            model.addRow(new Object [] { ns.getPrefix(), ns.getURI() } );
        }
        
        // Add blank row.
        model.addRow(new Object [] { "", "" } );
        
        m_nsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel rowSM = m_nsTable.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() 
        {
                public void valueChanged(ListSelectionEvent e) 
                {
                    //Ignore extra messages.
                    if (e.getValueIsAdjusting()) return;

                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty()) 
                    {
                        m_currRow = -1;
                    } 
                    else 
                    {
                        m_currRow = lsm.getMinSelectionIndex();                        
                    }
                }
            });
        
        m_nsTable.addKeyListener(new KeyListener()
        {
            public void keyTyped(KeyEvent keyEvent)
            {
            }

            public void keyPressed(KeyEvent keyEvent)
            {
                
            }

            public void keyReleased(KeyEvent keyEvent)
            {
                int keyCode = keyEvent.getKeyCode();
                
                if (keyCode == keyEvent.VK_DELETE)
                {
                    DefaultTableModel model = (DefaultTableModel) m_nsTable.getModel();
                    
                    if (m_currRow != -1 && m_currRow != model.getRowCount()-1)                    
                    {
                        // Delete the current row.
                        model.removeRow(m_currRow);  
                        
                        // There might be some editing, which will mess up the delete,
                        // So cancel the editing.
                        TableCellEditor editor = m_nsTable.getCellEditor();
                        editor.cancelCellEditing();                        
                    }
                }
                else if (keyCode == keyEvent.VK_ENTER)
                {
                    // Add a new row if there aren't any blank ones left.
                    
                    DefaultTableModel model = (DefaultTableModel) m_nsTable.getModel();
                    
                    String prefix = (String) model.getValueAt(model.getRowCount()-1, 0);
                    String uri = (String) model.getValueAt(model.getRowCount()-1, 1);
                    
                    if (prefix != null && !prefix.equals("") && uri != null && !uri.equals(""))
                    {
                        model.addRow(new Object [] { "", "" });
                    }
                }
            }
            
        }
        
        );
                                    
    }
    
    public NamespaceList getNamespaceList()
    {
        return m_namespaceList;
    }
    
    public boolean transferDataFromWindow()
    { 
        boolean success = false;
        
        DefaultTableModel model = (DefaultTableModel) m_nsTable.getModel();
        
        for (int row = 0; row < model.getRowCount(); row++)
        {
            String prefix = (String) model.getValueAt(row, 0);
            String uri = (String) model.getValueAt(row, 1);

            if (prefix != null && !prefix.equals("") && uri != null && !uri.equals(""))
            {
                Namespace ns = new Namespace(prefix, uri);
                m_namespaceList.addNamespace(ns);
            }
        }
        
        success = true;
        
        return success;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        jScrollPane1 = new javax.swing.JScrollPane();
        m_nsTable = new javax.swing.JTable();

        m_nsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Prefix", "URI"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(m_nsTable);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable m_nsTable;
    // End of variables declaration//GEN-END:variables
    
}
