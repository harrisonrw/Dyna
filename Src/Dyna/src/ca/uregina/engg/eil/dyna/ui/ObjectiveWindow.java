/*
 * ObjectiveWindow.java
 *
 * Created on August 21, 2006, 10:23 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.ui;

import java.awt.Frame;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import ca.uregina.engg.eil.dyna.controller.TaskController;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.Objective;
import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.model.TaskPriority;
import ca.uregina.engg.eil.dyna.util.StringSplitter;

/**
 * Displays data for an objective
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class ObjectiveWindow extends JInternalFrame implements TopicEventListener,
                                                               TableModelListener
{
    private Objective m_objective;    
    
    private boolean m_fireEditTaskPriorityCmd;
       
    /**
     * Creates new form ObjectiveWindow
     */
    public ObjectiveWindow(Objective objective)
    {
        super("Objective : " + objective.getName(), true, true, true, true);
        
        m_objective = objective;
        m_fireEditTaskPriorityCmd = false;
        
        initComponents();
               
        // Set Verifiers.
        m_nameCtrl.setInputVerifier(new NameVerifier());
        m_docCtrl.setInputVerifier(new DocumentationVerifier());
        
        // Fill the controls with data.        
        m_nameCtrl.setText(m_objective.getName());
        m_docCtrl.setText(m_objective.getDocumentation());     
        
        DefaultTableModel model = (DefaultTableModel) m_taskTable.getModel();  
        model.addTableModelListener(this);
        //TableSorter sorter = new TableSorter(model);
        
        Vector<TaskPriority> tpList = objective.getTaskPriorityList();
        for (int i = 0; i < tpList.size(); i++)
        {            
            TaskPriority tp = tpList.get(i);
            model.addRow(new Object [] { tp.getTask(), tp.getPriority() } );
        }              
        
        initEvents();
        
        m_fireEditTaskPriorityCmd = true;
    }
    
    private void initEvents()
    {
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.PROJECT +
                                                   EventService.CLOSE, this);
        
        // Objective Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.OBJECTIVE +
                                                   EventService.DELETE, this);
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.OBJECTIVE +
                                                   EventService.RENAME, this);
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.OBJECTIVE +
                                                   EventService.EDIT_DOC, this);
                
        EventService.getEventService().addListener(EventService.FEEDBACK +  
                                                   EventService.OBJECTIVE +
                                                   EventService.TASK_PRIORITY, this);     
    }
    
    
    public void tableChanged(TableModelEvent e) 
    {
        if (m_fireEditTaskPriorityCmd)
        {                    
            int row = e.getFirstRow();
                        
            TableModel model = (TableModel)e.getSource();
            
            if (row < model.getRowCount())
            {
                Task task = (Task) model.getValueAt(row, 0);
                int priority = ((Integer) model.getValueAt(row, 1)).intValue();

                fireEditTaskPriorityCommand(m_objective, task, priority);
            }                        
        }               
    }     
    
    private void releaseResources()
    {
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.PROJECT +
                                                      EventService.CLOSE, this);
        
        // Objective Feedback Events.
        
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.OBJECTIVE +
                                                      EventService.DELETE, this);
        
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.OBJECTIVE +
                                                      EventService.RENAME, this);
        
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.OBJECTIVE +
                                                      EventService.EDIT_DOC, this);
        
        EventService.getEventService().removeListener(EventService.FEEDBACK +  
                                                      EventService.OBJECTIVE +
                                                      EventService.TASK_PRIORITY, this);
                
    }
    
    public Objective getObjective()
    {
        return m_objective;
    }
        
    public void objectiveDeleted(Objective objective)
    {        
        if (objective.getName().equals(m_objective.getName()))
        {
            releaseResources();
            
            // Remove this window.
            dispose();
        }                            
    }

    public void objectiveRenamed(Objective objective, String oldName)
    {        
        if (oldName.equals(m_nameCtrl.getText()))
        {        
            m_nameCtrl.setText(objective.getName());
        }                
    }

    public void objectiveDocEdited(Objective objective, String documentation)
    {        
        if (objective.getName().equals(m_objective.getName()))
        {
            m_docCtrl.setText(documentation);
        }
    }

    public void taskPriorityCreated(Objective objective, TaskPriority taskPriority)
    {          
        m_fireEditTaskPriorityCmd = false;
        
        if (objective.getName().equals(m_objective.getName()))
        {                    
            DefaultTableModel model = (DefaultTableModel) m_taskTable.getModel();
            
                        
            // Clear the table.
            while (model.getRowCount() > 0)
            {
                model.removeRow(0);
            }
            
            // Re-add everything to the table.
            Vector<TaskPriority> tpList = objective.getTaskPriorityList();
            for (int i = 0; i < tpList.size(); i++)
            {            
                TaskPriority tp = tpList.get(i);
                model.addRow(new Object [] { tp.getTask(), tp.getPriority() } );                        
            }
        }
        
        m_fireEditTaskPriorityCmd = true;
    }
        
    public void taskPriorityDeleted(Objective objective, TaskPriority taskPriority)
    {        
        if (objective.getName().equals(m_objective.getName()))
        {                    
            DefaultTableModel model = (DefaultTableModel) m_taskTable.getModel();

            for (int row = 0; row < model.getRowCount(); row++)
            {
                Task task = (Task) model.getValueAt(row, 0);

                if (task.getName().equals(taskPriority.getTask().getName()))
                {
                    model.removeRow(row);  
                    break;
                }
            }        
        }
    }
    
    public void taskPriorityEdited(Objective objective, Task task, int priority)
    {
        m_fireEditTaskPriorityCmd = false;
        
        if (objective.getName().equals(m_objective.getName()))
        {                    
            DefaultTableModel model = (DefaultTableModel) m_taskTable.getModel();
                                    
            // Clear the table.
            while (model.getRowCount() > 0)
            {
                model.removeRow(0);
            }
            
            // Re-add everything to the table.
            Vector<TaskPriority> tpList = objective.getTaskPriorityList();
            for (int i = 0; i < tpList.size(); i++)
            {            
                TaskPriority tp = tpList.get(i);
                model.addRow(new Object [] { tp.getTask(), tp.getPriority() } );                        
            }
        }
        
        m_fireEditTaskPriorityCmd = true;
    }
    
    // TaskListener event Handlers
    /*    
    public void taskDeleted(TaskEvent event)
    {        
        Task task = (Task) event.getTask();
         
        DefaultTableModel model = (DefaultTableModel) m_taskTable.getModel();
        
        for (int row = 0; row < model.getRowCount(); row++)
        {
            Task t = (Task) model.getValueAt(row, 0);
        
            if (t.getName().equals(task.getName()))
            {
                model.removeRow(row);  
                break;
            }
        }
    }
     */
    
    public void projectClosed()
    {
        releaseResources();
        dispose();
    }
        
    public void notify(TopicEvent event) throws Exception
    {
        String[] topic = StringSplitter.split(event.getTopic(), ".");
        Hashtable data = event.getData();
                
        if ((topic[0]+topic[1]).equals(EventService.FEEDBACK +
                                       EventService.OBJECTIVE))
        {
            // Objective Feedback Events.
                                
            if (topic[2].equals(EventService.DELETE))
            {
                objectiveDeleted((Objective) data.get(EventService.OBJECTIVE_OBJECT));
            }         
            else if (topic[2].equals(EventService.RENAME))
            {
                objectiveRenamed((Objective) data.get(EventService.OBJECTIVE_OBJECT),
                                 (String) data.get(EventService.OBJECTIVE_NAME));
            }
            else if (topic[2].equals(EventService.EDIT_DOC))
            {
                objectiveDocEdited((Objective) data.get(EventService.OBJECTIVE_OBJECT),
                                   (String) data.get(EventService.DOCUMENTATION));
            }         
            else if (topic[2].equals(EventService.TASK_PRIORITY))
            {
                if (topic[3].equals(EventService.CREATE))
                {
                    taskPriorityCreated((Objective) data.get(EventService.OBJECTIVE_OBJECT),
                                        (TaskPriority) data.get(EventService.TASK_PRIORITY_OBJECT));
                }     
                else if (topic[3].equals(EventService.DELETE))
                {
                    taskPriorityDeleted((Objective) data.get(EventService.OBJECTIVE_OBJECT),
                                        (TaskPriority) data.get(EventService.TASK_PRIORITY_OBJECT));
                }  
                else if (topic[3].equals(EventService.EDIT))
                {
                    taskPriorityEdited((Objective) data.get(EventService.OBJECTIVE_OBJECT),
                                       (Task) data.get(EventService.TASK_OBJECT),
                                       ((Integer) data.get(EventService.PRIORITY)).intValue());
                }
            }
        }    
        else if ((topic[0]+topic[1]).equals(EventService.FEEDBACK +
                                            EventService.PROJECT))
        {
            if (topic[2].equals(EventService.CLOSE))
            {
                projectClosed();
            }     
        }
    }
    
    private void fireRenameObjectiveCommand(Objective objective, String newName)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.OBJECTIVE_OBJECT, objective);
            data.put(EventService.OBJECTIVE_NAME, newName);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.OBJECTIVE + 
                                              EventService.RENAME, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot rename objective! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireEditDocCommand(Objective objective, String documentation)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.OBJECTIVE_OBJECT, objective);
            data.put(EventService.DOCUMENTATION, documentation);
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND + 
                                              EventService.OBJECTIVE + 
                                              EventService.EDIT_DOC, 
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot edit documentation! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireCreateTaskPriorityCommand(Objective objective, Task task, int priority)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.OBJECTIVE_OBJECT, objective);
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.PRIORITY, new Integer(priority));
            
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND +  
                                              EventService.OBJECTIVE +
                                              EventService.TASK_PRIORITY +
                                              EventService.CREATE,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot create task priority! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireDeleteTaskPriorityCommand(Objective objective, Task task)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.OBJECTIVE_OBJECT, objective);
            data.put(EventService.TASK_OBJECT, task);
                        
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND +    
                                              EventService.OBJECTIVE +
                                              EventService.TASK_PRIORITY +
                                              EventService.DELETE,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot delete task priority! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireEditTaskPriorityCommand(Objective objective, Task task, int priority)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.OBJECTIVE_OBJECT, objective);
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.PRIORITY, new Integer(priority));
                        
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND +    
                                              EventService.OBJECTIVE +
                                              EventService.TASK_PRIORITY +
                                              EventService.EDIT,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot edit task priority! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Cannot open task priority! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireRunTestCommand(Objective objective)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.OBJECTIVE_OBJECT, objective);            
                     
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND +                                               
                                              EventService.OBJECTIVE +
                                              EventService.TEST +
                                              EventService.RUN,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot test objective! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireClearOutputCommand() 
    {
        try
        {                    
            Hashtable<String, Object> data = new Hashtable<String, Object>(1);        

            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.FEEDBACK +                                               
                                              EventService.PROJECT +
                                              EventService.CLEAR_OUTPUT,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot clear output! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    class NameVerifier extends InputVerifier 
    {
         public boolean verify(JComponent input) 
         {
            boolean isValid = false;

            JTextField tf = (JTextField) input;
            
            if (! tf.getText().equals(""))
            {
                
                isValid = true;
                                
                fireRenameObjectiveCommand(m_objective, tf.getText());
            }

            return isValid;
         }
     }
    
    class DocumentationVerifier extends InputVerifier
    {
        public boolean verify(JComponent input) 
         {
            boolean isValid = false;

            JTextArea ta = (JTextArea) input;
            
            //if (! ta.getText().equals(""))
            //{
                
                isValid = true;
                
                fireEditDocCommand(m_objective, ta.getText());                
            //}

            return isValid;
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
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        m_nameLabel = new javax.swing.JLabel();
        m_nameCtrl = new javax.swing.JTextField();
        m_docLabel = new javax.swing.JLabel();
        m_taskLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        m_docCtrl = new javax.swing.JTextArea();
        m_showTaskButton = new javax.swing.JButton();
        m_addTaskButton = new javax.swing.JButton();
        m_delTaskButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        m_taskTable = new javax.swing.JTable();
        m_testButton = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setVisible(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener()
        {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt)
            {
                onClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt)
            {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt)
            {
            }
        });

        m_nameLabel.setText("Name:");

        m_docLabel.setText("Documentation:");

        m_taskLabel.setText("Tasks:");

        m_docCtrl.setColumns(20);
        m_docCtrl.setRows(5);
        jScrollPane2.setViewportView(m_docCtrl);

        m_showTaskButton.setText("Show");
        m_showTaskButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onShowTaskPriority(evt);
            }
        });

        m_addTaskButton.setText("Add");
        m_addTaskButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onAddTaskPriority(evt);
            }
        });

        m_delTaskButton.setText("Del");
        m_delTaskButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onDelTaskPriority(evt);
            }
        });

        m_taskTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Task", "Priority"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean []
            {
                false, true
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
        jScrollPane4.setViewportView(m_taskTable);

        m_testButton.setText("Run Tests");
        m_testButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onTest(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(m_nameLabel)
                            .add(m_docLabel)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane2)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, m_nameCtrl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(layout.createSequentialGroup()
                                .add(m_taskLabel)
                                .add(68, 68, 68)
                                .add(m_showTaskButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(m_addTaskButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(m_delTaskButton))
                            .add(jScrollPane4, 0, 0, Short.MAX_VALUE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, m_testButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_nameLabel)
                    .add(m_taskLabel)
                    .add(m_addTaskButton)
                    .add(m_delTaskButton)
                    .add(m_showTaskButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(layout.createSequentialGroup()
                        .add(m_nameCtrl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(16, 16, 16)
                        .add(m_docLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 178, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jScrollPane4, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_testButton)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onTest(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onTest
    {//GEN-HEADEREND:event_onTest
        fireClearOutputCommand();
        
        fireRunTestCommand(m_objective);
    }//GEN-LAST:event_onTest

    private void onClosing(javax.swing.event.InternalFrameEvent evt)//GEN-FIRST:event_onClosing
    {//GEN-HEADEREND:event_onClosing
        releaseResources();
    }//GEN-LAST:event_onClosing

    private void onShowTaskPriority(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onShowTaskPriority
    {//GEN-HEADEREND:event_onShowTaskPriority
        // Show the task instead of task priority.
        int row = m_taskTable.getSelectedRow();
        
        if (row != -1)
        {
            DefaultTableModel model = (DefaultTableModel) m_taskTable.getModel();
            Task task = (Task) model.getValueAt(row, 0);
            
            fireOpenTaskCommand(task.getName());            
        }
    }//GEN-LAST:event_onShowTaskPriority

    private void onDelTaskPriority(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onDelTaskPriority
    {//GEN-HEADEREND:event_onDelTaskPriority
        int row = m_taskTable.getSelectedRow();
        
        if (row != -1)
        {
            DefaultTableModel model = (DefaultTableModel) m_taskTable.getModel();
            Task task = (Task) model.getValueAt(row, 0);
                        
            fireDeleteTaskPriorityCommand(m_objective, task);
        }                
    }//GEN-LAST:event_onDelTaskPriority

    private void onAddTaskPriority(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onAddTaskPriority
    {//GEN-HEADEREND:event_onAddTaskPriority
        
        TaskPriorityDlg dlg = new TaskPriorityDlg(javax.swing.JOptionPane.getFrameForComponent( this ), TaskController.getTaskController().getTasks());
        dlg.setVisible(true);
        
        Task task = dlg.getSelectedTask();
        int priority = dlg.getPriority();   
        
        if (task != null)
        {
            fireCreateTaskPriorityCommand(m_objective, task, priority);            
        }
         
    }//GEN-LAST:event_onAddTaskPriority
    
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton m_addTaskButton;
    private javax.swing.JButton m_delTaskButton;
    private javax.swing.JTextArea m_docCtrl;
    private javax.swing.JLabel m_docLabel;
    private javax.swing.JTextField m_nameCtrl;
    private javax.swing.JLabel m_nameLabel;
    private javax.swing.JButton m_showTaskButton;
    private javax.swing.JLabel m_taskLabel;
    private javax.swing.JTable m_taskTable;
    private javax.swing.JButton m_testButton;
    // End of variables declaration//GEN-END:variables
    
}
