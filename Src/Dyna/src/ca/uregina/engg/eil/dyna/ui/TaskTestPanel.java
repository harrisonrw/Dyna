/*
 * TaskTestPanel.java
 *
 * Created on November 13, 2006, 8:29 PM
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
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import ca.uregina.engg.eil.dyna.event.EventService;
import ca.uregina.engg.eil.dyna.event.TopicEvent;
import ca.uregina.engg.eil.dyna.event.TopicEventListener;
import ca.uregina.engg.eil.dyna.model.KnowledgeTestCase;
import ca.uregina.engg.eil.dyna.model.KnowledgeTestSuite;
import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.util.StringSplitter;

/**
 * Provides UI for creating and running test cases
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TaskTestPanel extends JPanel implements TopicEventListener
{
    private Task m_task;
    private KnowledgeTestSuite m_testSuite;
    
    /** Creates new form TaskTestPanel */
    public TaskTestPanel(Task task)
    {
        m_task = task;
        m_testSuite = m_task.getTestSuite();
        
        initComponents();
        
        DefaultListModel model = new DefaultListModel();
        
        // Add the setup to the list ctrl.
        model.addElement("Setup");
                
        // Fill the list ctrl with test cases.                   
        Vector<KnowledgeTestCase> testList = m_testSuite.getTestCaseList();
        for (int i = 0; i < testList.size(); i++)
        {
            model.addElement(testList.get(i));                      
        }
        
        
        m_testListCtrl.setModel(model);
        
        m_codeCtrl.setInputVerifier(new CodeVerifier());
        
        initEvents();
    }
    
    private void initEvents()
    {
        // Test Case Feedback Events.
        
        EventService.getEventService().addListener(EventService.FEEDBACK + 
                                                   EventService.TASK + 
                                                   EventService.TEST, this);
    }
    
    public void releaseResources()
    {
        EventService.getEventService().removeListener(EventService.FEEDBACK + 
                                                      EventService.TASK +
                                                      EventService.TEST, this);
    }
    
    private void testCaseCreated(Task task, KnowledgeTestCase testCase)
    {
        if (task.getName().equals(m_task.getName()))
        {    
            DefaultListModel model = (DefaultListModel) m_testListCtrl.getModel();
            model.addElement(testCase);
        } 
    }
    
    private void testCaseDeleted(Task task, KnowledgeTestCase testCase)
    {
        if (task.getName().equals(m_task.getName()))
        {    
            DefaultListModel model = (DefaultListModel) m_testListCtrl.getModel();
            model.removeElement(testCase);
        } 
    }
    
    private void testCaseEdited(Task task, KnowledgeTestCase testCase, String testCode)
    {
        if (task.getName().equals(m_task.getName()))
        {    
            //DefaultListModel model = (DefaultListModel) m_testListCtrl.getModel();
            //model.removeElement(testCase);
        } 
    }
    
    private void testCaseSetupEdited(Task task, String setup)
    {
        if (task.getName().equals(m_task.getName()))
        {    
            //DefaultListModel model = (DefaultListModel) m_testListCtrl.getModel();
            //model.removeElement(testCase);
        } 
    }
    
    public void notify(TopicEvent event) throws Exception 
    {
        String[] topic = StringSplitter.split(event.getTopic(), ".");
        Hashtable data = event.getData();
                
        if ((topic[0]+topic[1]+topic[2]).equals(EventService.FEEDBACK +
                                                EventService.TASK +
                                                EventService.TEST))
        {                        
            if (topic[3].equals(EventService.CREATE))
            {
                testCaseCreated((Task) data.get(EventService.TASK_OBJECT),
                                (KnowledgeTestCase) data.get(EventService.TEST_CASE_OBJECT));
            }   
            else if (topic[3].equals(EventService.DELETE))
            {
                testCaseDeleted((Task) data.get(EventService.TASK_OBJECT),
                                (KnowledgeTestCase) data.get(EventService.TEST_CASE_OBJECT));
            }
            else if (topic[3].equals(EventService.EDIT))
            {
                testCaseEdited((Task) data.get(EventService.TASK_OBJECT),
                               (KnowledgeTestCase) data.get(EventService.TEST_CASE_OBJECT),
                               (String) data.get(EventService.CODE));
            }
            else if (topic[3].equals(EventService.SETUP))
            {
                testCaseSetupEdited((Task) data.get(EventService.TASK_OBJECT),
                                    (String) data.get(EventService.CODE));
            }
        }   
    }
    
    private void fireCreateTestCaseCommand(Task task, String testName)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.TEST_NAME, testName);
                      
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND +                                               
                                              EventService.TASK +
                                              EventService.TEST +
                                              EventService.CREATE,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot create test case! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireDeleteTestCaseCommand(Task task, KnowledgeTestCase testCase)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.TEST_CASE_OBJECT, testCase);
                      
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND +                                               
                                              EventService.TASK +
                                              EventService.TEST +
                                              EventService.DELETE,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot delete test case! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireEditTestCaseCommand(Task task, KnowledgeTestCase testCase, String code)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);
            data.put(EventService.TEST_CASE_OBJECT, testCase);
            data.put(EventService.CODE, code);
                      
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND +                                               
                                              EventService.TASK +
                                              EventService.TEST +
                                              EventService.EDIT,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot edit test case! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireEditTestSetupCommand(Task task, String setup)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);            
            data.put(EventService.CODE, setup);
                      
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND +                                               
                                              EventService.TASK +
                                              EventService.TEST +
                                              EventService.SETUP,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot edit test case setup! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireRunTestCaseCommand(Task task, KnowledgeTestCase testCase)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);            
            data.put(EventService.TEST_CASE_OBJECT, testCase);
                      
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND +                                               
                                              EventService.TASK +
                                              EventService.TEST +
                                              EventService.RUN,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot run test case! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    private void fireRunAllTestCasesCommand(Task task)
    {
        try
        {                                    
            Hashtable<String, Object> data = new Hashtable<String, Object>();
            data.put(EventService.TASK_OBJECT, task);            
                     
            TopicEvent topicEvent = new TopicEvent(this, 
                                              EventService.COMMAND +                                               
                                              EventService.TASK +
                                              EventService.TEST +
                                              EventService.RUN_ALL,
                                              data);
            EventService.getEventService().fireEvent(topicEvent);                    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot run test case! " + e.getMessage(), "Alert", JOptionPane.ERROR_MESSAGE);
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
    
    class CodeVerifier extends InputVerifier
    {
        public boolean verify(JComponent input) 
         {
            boolean isValid = false;

            JTextArea ta = (JTextArea) input;
            
            //if (! ta.getText().equals(""))
            //{
                
                isValid = true;
                
                Object value = m_testListCtrl.getSelectedValue();
                
                if (value instanceof KnowledgeTestCase)
                {                                            
                    fireEditTestCaseCommand(m_task, (KnowledgeTestCase) value, ta.getText());
                }     
                else if (value instanceof String)
                {
                    fireEditTestSetupCommand(m_task, ta.getText());
                }
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
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        m_addButton = new javax.swing.JButton();
        m_delButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        m_testListCtrl = new javax.swing.JList();
        m_runButton = new javax.swing.JButton();
        m_runAllButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        m_codeCtrl = new javax.swing.JTextArea();

        jSplitPane1.setDividerLocation(160);
        m_addButton.setText("Add");
        m_addButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        m_addButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onAdd(evt);
            }
        });

        m_delButton.setText("Del");
        m_delButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        m_delButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onDelete(evt);
            }
        });

        m_testListCtrl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        m_testListCtrl.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                TaskTestPanel.this.valueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(m_testListCtrl);

        m_runButton.setText("Run");
        m_runButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        m_runButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onRun(evt);
            }
        });

        m_runAllButton.setText("Run All");
        m_runAllButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        m_runAllButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onRunAll(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(m_addButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_delButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_runButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_runAllButton)
                .addContainerGap(18, Short.MAX_VALUE))
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(m_addButton)
                        .add(m_delButton)
                        .add(m_runButton))
                    .add(m_runAllButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE))
        );
        jSplitPane1.setLeftComponent(jPanel1);

        m_codeCtrl.setColumns(20);
        m_codeCtrl.setRows(5);
        jScrollPane2.setViewportView(m_codeCtrl);

        jSplitPane1.setRightComponent(jScrollPane2);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void onRunAll(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onRunAll
    {//GEN-HEADEREND:event_onRunAll
        fireClearOutputCommand();
        
        fireRunAllTestCasesCommand(m_task);
    }//GEN-LAST:event_onRunAll

    private void onRun(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onRun
    {//GEN-HEADEREND:event_onRun
        KnowledgeTestCase testCase = (KnowledgeTestCase) m_testListCtrl.getSelectedValue();  
        
        if (testCase != null)
        {                
            fireClearOutputCommand();
            
            fireRunTestCaseCommand(m_task, testCase);
        } 
    }//GEN-LAST:event_onRun

    private void onDelete(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onDelete
    {//GEN-HEADEREND:event_onDelete
        KnowledgeTestCase testCase = (KnowledgeTestCase) m_testListCtrl.getSelectedValue();  
        
        if (testCase != null)
        {                
            fireDeleteTestCaseCommand(m_task, testCase);
        }     
    }//GEN-LAST:event_onDelete

    private void onAdd(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onAdd
    {//GEN-HEADEREND:event_onAdd
        TestCaseNameDlg dlg = new TestCaseNameDlg(javax.swing.JOptionPane.getFrameForComponent(this));
        dlg.setVisible(true);
        
        String testName = dlg.getTestCaseName();
        
        if (!testName.equals(""))
        {
            fireCreateTestCaseCommand(m_task, testName);                        
        }
    }//GEN-LAST:event_onAdd

    private void valueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_valueChanged
    {//GEN-HEADEREND:event_valueChanged
        // Clicking mouse can cause multiple valueChanged events to fire.
        // We are only interested in the final event (when values are no longer adjusting).
        if (evt.getValueIsAdjusting() == false) 
        {                        
            if (m_testListCtrl.getSelectedIndex() == -1) 
            {
                // No selection, disable fire button.
                //fireButton.setEnabled(false);
                //System.out.println("no");
                m_codeCtrl.setText("");
                
            } 
            else 
            {
                //Selection, enable the fire button.
                //fireButton.setEnabled(true);
                //System.out.println("yes");
                              
                Object value = m_testListCtrl.getSelectedValue();
                
                if (value instanceof KnowledgeTestCase)
                {
                    m_codeCtrl.setText(((KnowledgeTestCase) value).getTestCode());
                }
                else if (value instanceof String)
                {                
                    if (((String) value).equals("Setup"))
                    {                    
                        m_codeCtrl.setText(m_testSuite.getSetup());
                    }
                }
            }
        }
    }//GEN-LAST:event_valueChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton m_addButton;
    private javax.swing.JTextArea m_codeCtrl;
    private javax.swing.JButton m_delButton;
    private javax.swing.JButton m_runAllButton;
    private javax.swing.JButton m_runButton;
    private javax.swing.JList m_testListCtrl;
    // End of variables declaration//GEN-END:variables
    
}
