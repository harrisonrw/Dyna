/*
 * ObjectPanel.java
 *
 * Created on October 23, 2006, 3:09 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.ui;

import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import ca.uregina.engg.eil.dyna.controller.ProjectController;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.GenericInstance;
import ca.uregina.engg.eil.dyna.model.GenericKB;
import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.util.StringSplitter;

/**
 * Displays objects used in a task.
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class ObjectPanel extends JPanel implements TopicEventListener
{
    
    private Task m_task;
    
    /** Creates new form ObjectPanel */
    public ObjectPanel(Task task) 
    {
        m_task = task;
        
        initComponents();
        
        // Fill the table with data.
        DefaultTableModel model = (DefaultTableModel) m_objTable.getModel();
        Vector<GenericInstance> objList = m_task.getObjects();
        for (int i = 0; i < objList.size(); i++)
        {
            GenericInstance obj = objList.get(i);
                        
            model.addRow(new Object [] { obj, obj.getClassName(), obj.getNamespace() } );
        }
        
        initEvents();
    }
    
    private void initEvents()
    {
        // Task Object Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK +
                                                   EventService.TSK_OBJECT, this);
    }
    
    public void releaseResources()
    {
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.TASK +
                                                      EventService.TSK_OBJECT, this);
    }
    
    private void objectCreated(Task task, GenericInstance obj)
    {
        if (task.getName().equals(m_task.getName()))
        {            
            DefaultTableModel model = (DefaultTableModel) m_objTable.getModel();
            
            // Check if object is already in the table.
            boolean objExists = false;
            for (int row = 0; row < model.getRowCount(); row++)
            {
                GenericInstance inst = (GenericInstance) model.getValueAt(row, 0);

                if (inst.getName().equals(obj.getName()))
                {
                    objExists = true;
                    break;
                }
            }
            
            if (!objExists)
            {
                model.addRow(new Object[] { obj, obj.getClassName(), obj.getNamespace() } );
            }
        } 
    }
    
    private void objectDeleted(Task task, GenericInstance obj)
    {
        if (task.getName().equals(m_task.getName()))
        { 
            DefaultTableModel model = (DefaultTableModel) m_objTable.getModel();
            
            for (int row = 0; row < model.getRowCount(); row++)
            {
                GenericInstance inst = (GenericInstance) model.getValueAt(row, 0);

                if (inst.getName().equals(obj.getName()))
                {
                    model.removeRow(row);  
                    break;
                }
            }    
        }
    }
    
    private void allObjectsDeleted(Task task)
    {
        if (task.getName().equals(m_task.getName()))
        { 
            DefaultTableModel model = (DefaultTableModel) m_objTable.getModel();
            while (model.getRowCount() > 0)
            {
                model.removeRow(0);
            }
        }
    }
    
    public void notify(TopicEvent event) throws Exception 
    {
        String[] topic = StringSplitter.split(event.getTopic(), ".");      
        Hashtable data = event.getData();
                
        if ((topic[0]+topic[1]+topic[2]).equals(EventService.FEEDBACK +
                                                EventService.TASK +
                                                EventService.TSK_OBJECT))
        {
            // Task Object Feedback Events.
            if (topic[3].equals(EventService.DELETE_ALL))
            {
                allObjectsDeleted((Task) data.get(EventService.TASK_OBJECT));
            }
            else if (topic[3].equals(EventService.DELETE))
            {
                objectDeleted((Task) data.get(EventService.TASK_OBJECT),
                              (GenericInstance) data.get(EventService.TSK_OBJECT_OBJECT));
            }
            else if (topic[3].equals(EventService.CREATE))
            {
                objectCreated((Task) data.get(EventService.TASK_OBJECT),
                              (GenericInstance) data.get(EventService.TSK_OBJECT_OBJECT));
            }                 
        }   
    }
    
    private void fireCreateObjectCommand(String taskName, String objName, String className)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_NAME, taskName);
            data.put(EventService.TSK_OBJECT_NAME, objName);
            data.put(EventService.CLS_NAME, className);
                                    
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.TASK +
                                              EventService.TSK_OBJECT +
                                              EventService.CREATE,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot create task object! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireDeleteObjectCommand(Task task, GenericInstance obj)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.TSK_OBJECT_OBJECT, obj);
                        
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND +   
                                              EventService.TASK +
                                              EventService.TSK_OBJECT +
                                              EventService.DELETE,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot delete task object! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
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
        m_objTable = new javax.swing.JTable();
        m_addButton = new javax.swing.JButton();
        m_delButton = new javax.swing.JButton();

        m_objTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Object Name", "Class", "Namespace"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                true, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(m_objTable);

        m_addButton.setText("Add");
        m_addButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onAdd(evt);
            }
        });

        m_delButton.setText("Del");
        m_delButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onDelete(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(m_addButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_delButton)))
                .add(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_addButton)
                    .add(m_delButton))
                .add(14, 14, 14)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 291, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void onAdd(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onAdd
    {//GEN-HEADEREND:event_onAdd
        ProjectController projectController = ProjectController.getProjectController();
        GenericKB kb = projectController.getKnowledgeBase();
        
        String[] classList = kb.getClassNames();
        
        ObjectDlg dlg = new ObjectDlg(javax.swing.JOptionPane.getFrameForComponent( this ), classList);
        dlg.setVisible(true);
        
        String objectName = dlg.getObjectName();
        String className = dlg.getClassName();        
        
        if (!objectName.equals("") && !className.equals(""))
        {
            fireCreateObjectCommand(m_task.getName(), objectName, className);            
        }
         
    }//GEN-LAST:event_onAdd

    private void onDelete(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onDelete
    {//GEN-HEADEREND:event_onDelete
        // Fire event to delete selected object.
        
        int row = m_objTable.getSelectedRow();
        
        if (row != -1)
        {
            DefaultTableModel model = (DefaultTableModel) m_objTable.getModel();
            GenericInstance obj = (GenericInstance) model.getValueAt(row, 0);
                        
            fireDeleteObjectCommand(m_task, obj);
        }     
    }//GEN-LAST:event_onDelete
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton m_addButton;
    private javax.swing.JButton m_delButton;
    private javax.swing.JTable m_objTable;
    // End of variables declaration//GEN-END:variables
    
}
