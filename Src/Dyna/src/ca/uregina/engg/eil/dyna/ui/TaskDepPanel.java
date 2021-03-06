/*
 * TaskDepPanel.java
 *
 * Created on October 23, 2006, 2:55 PM
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
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.util.StringSplitter;

/**
 * Displays dependent tasks
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TaskDepPanel extends JPanel implements TopicEventListener
{
    private Task m_task;
    
    /** Creates new form TaskDepPanel */
    public TaskDepPanel(Task task) 
    {
        m_task = task;
        
        initComponents();
        
        // Fill the panel with data.
        DefaultTableModel model = (DefaultTableModel) m_depTable.getModel();
        Vector<Task> depList = m_task.getDependencies();
        for (int i = 0; i < depList.size(); i++)
        {            
            model.addRow(new Object [] { depList.get(i) } );
        }
        
        initEvents();
    }
    
    private void initEvents()
    {
        // Dependency Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK +
                                                   EventService.DEPENDENCY, this);
    }
    
    public void releaseResources()
    {
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.TASK +
                                                      EventService.DEPENDENCY, this);
    }
    
    private void dependencyCreated(Task task, Task dep)
    {
        if (task.getName().equals(m_task.getName()))
        {            
            DefaultTableModel model = (DefaultTableModel) m_depTable.getModel();
            model.addRow(new Object [] { dep } );
        } 
    }
    
    private void allDependeciesDeleted(Task task)
    {
        if (task.getName().equals(m_task.getName()))
        { 
            DefaultTableModel model = (DefaultTableModel) m_depTable.getModel();
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
                                               EventService.DEPENDENCY))
        {
            // Dependency Feedback Events.
            if (topic[3].equals(EventService.DELETE_ALL))
            {
                allDependeciesDeleted((Task) data.get(EventService.TASK_OBJECT));
            }
            else if (topic[3].equals(EventService.CREATE))
            {
                dependencyCreated((Task) data.get(EventService.TASK_OBJECT),
                                  (Task) data.get(EventService.DEP_OBJECT));
            }                 
        }   
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        m_depTable = new javax.swing.JTable();

        m_depTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Task Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(m_depTable);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable m_depTable;
    // End of variables declaration//GEN-END:variables
    
}
