/*
 * ProjectWindow.java
 *
 * Created on March 12, 2007, 3:12 PM
 */

package ca.uregina.engg.eil.dyna.ui;

import ca.uregina.engg.eil.dyna.model.Namespace;
import ca.uregina.engg.eil.dyna.model.NamespaceList;
import java.awt.Frame;
import java.util.Vector;

/**
 *
 * @author  harrisonr
 */
public class ProjectWindow extends javax.swing.JDialog
{        
    protected boolean m_okPressed;

    private ProjectGeneralPanel m_generalPanel;
    private NamespacePanel m_namespacePanel;
    
    /**
     * Creates new form ProjectWindow
     */
    public ProjectWindow(Frame owner, String projectName, String doc, String url,
                         NamespaceList namespaceList)
    {
        super(owner, "Project Properties", true);
        initComponents();
        
        m_generalPanel = new ProjectGeneralPanel(projectName, doc, url);
        m_tabbedPane.add("General", m_generalPanel);
        
        m_namespacePanel = new NamespacePanel(namespaceList);
        m_tabbedPane.add("Namespace", m_namespacePanel);
        
        m_okPressed = false;
    }
            
    public boolean wasOKPressed()
    {
        return m_okPressed;
    }
    
    public String getProjectName()
    {
        return m_generalPanel.getProjectName();
    }
    
    public String getDocumentation()
    {
        return m_generalPanel.getDocumentation();
    }
    
    public String getURL()
    {
        return m_generalPanel.getURL();
    }
    
    public NamespaceList getNamespaceList()
    {
        return m_namespacePanel.getNamespaceList();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        m_okButton = new javax.swing.JButton();
        m_cancelButton = new javax.swing.JButton();
        m_tabbedPane = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(271, Short.MAX_VALUE)
                .add(m_okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(m_cancelButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 83, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(m_tabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(m_tabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_cancelButton)
                    .add(m_okButton))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onOK(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onOK
    {//GEN-HEADEREND:event_onOK
        boolean success = m_generalPanel.transferDataFromWindow() &&
                          m_namespacePanel.transferDataFromWindow();
        
        if (success)
        {
            m_okPressed = true;
        
            this.dispose();
        }
    }//GEN-LAST:event_onOK

    private void onCancel(java.awt.event.ActionEvent evt)//GEN-FIRST:event_onCancel
    {//GEN-HEADEREND:event_onCancel
        this.dispose();
    }//GEN-LAST:event_onCancel
    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton m_cancelButton;
    private javax.swing.JButton m_okButton;
    private javax.swing.JTabbedPane m_tabbedPane;
    // End of variables declaration//GEN-END:variables
    
}
