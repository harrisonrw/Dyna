/*
 * TestCaseNameDlg.java
 *
 * Created on November 13, 2006, 10:36 PM
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
import javax.swing.JDialog;

/**
 * A dialog for entering name for a test case
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TestCaseNameDlg extends JDialog
{
    private String m_testName;
    
    /** Creates new form TestCaseNameDlg */
    public TestCaseNameDlg(Frame owner)
    {
        super(owner, "Add Test Case", true);
        
        initComponents();
        
        setSize(350, 150);
        
        m_testName = "";
    }
    
    public String getTestCaseName()
    {
        return m_testName;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        m_nameLabel = new javax.swing.JLabel();
        m_nameCtrl = new javax.swing.JTextField();
        m_okButton = new javax.swing.JButton();
        m_cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        m_nameLabel.setText("Test Case Name:");

        m_okButton.setText("OK");
        m_okButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onOK(evt);
            }
        });

        m_cancelButton.setText("Cancel");
        m_cancelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                onCancel(evt);
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
                        .add(m_nameLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_nameCtrl, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(m_okButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_nameLabel)
                    .add(m_nameCtrl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(19, 19, 19)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_cancelButton)
                    .add(m_okButton))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void onCancel(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onCancel
    {//GEN-HEADEREND:event_onCancel
        this.dispose();
    }//GEN-LAST:event_onCancel

    private void onOK(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onOK
    {//GEN-HEADEREND:event_onOK
        m_testName = m_nameCtrl.getText();
        
        this.dispose();
    }//GEN-LAST:event_onOK
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton m_cancelButton;
    private javax.swing.JTextField m_nameCtrl;
    private javax.swing.JLabel m_nameLabel;
    private javax.swing.JButton m_okButton;
    // End of variables declaration//GEN-END:variables
    
}