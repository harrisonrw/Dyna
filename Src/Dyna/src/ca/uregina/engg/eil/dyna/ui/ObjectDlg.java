/*
 * ObjectDlg.java
 *
 * Created on November 9, 2006, 2:02 PM
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
import javax.swing.DefaultListModel;
import javax.swing.JDialog;

/**
 * Allows user to choose a class and enter a name for an object
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/enggr
 */
public class ObjectDlg extends JDialog
{
    
    private String m_objectName;
    private String m_className;
   
    /** Creates new form ObjectDlg */
    public ObjectDlg(Frame owner, String[] classList)
    {
        super(owner, "Add Object to Task", true);
        
        initComponents();
        
        setSize(350, 300);
       
        // Fill the list ctrl with classes.           
        DefaultListModel model = new DefaultListModel(); //m_classListCtrl.getModel();
        for (int i = 0; i < classList.length; i++)
        {
            model.addElement(classList[i]);                      
        }
        
        m_classListCtrl.setModel(model);
        
        m_objectName = "";
        m_className = "";
    }
    
    public String getObjectName()
    {
        return m_objectName;
    }
    
    public String getClassName()
    {
        return m_className;
    }
        
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_nameLabel = new javax.swing.JLabel();
        m_nameCtrl = new javax.swing.JTextField();
        m_classLabel = new javax.swing.JLabel();
        m_okButton = new javax.swing.JButton();
        m_cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        m_classListCtrl = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        m_nameLabel.setText("Object Name:");

        m_classLabel.setText("Class:");

        m_okButton.setText("OK");
        m_okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onOK(evt);
            }
        });

        m_cancelButton.setText("Cancel");
        m_cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onCancel(evt);
            }
        });

        m_classListCtrl.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(m_classListCtrl);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(m_okButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_cancelButton))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(m_nameLabel)
                            .add(m_classLabel))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(m_nameCtrl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_nameLabel)
                    .add(m_nameCtrl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(m_classLabel)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 18, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_cancelButton)
                    .add(m_okButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void onCancel(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onCancel
    {//GEN-HEADEREND:event_onCancel
        this.dispose();
    }//GEN-LAST:event_onCancel

    private void onOK(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onOK
    {//GEN-HEADEREND:event_onOK
        m_className = (String) m_classListCtrl.getSelectedValue();                
        m_objectName = m_nameCtrl.getText();
                
        this.dispose();
    }//GEN-LAST:event_onOK
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton m_cancelButton;
    private javax.swing.JLabel m_classLabel;
    private javax.swing.JList m_classListCtrl;
    private javax.swing.JTextField m_nameCtrl;
    private javax.swing.JLabel m_nameLabel;
    private javax.swing.JButton m_okButton;
    // End of variables declaration//GEN-END:variables
    
}
