/*
 * ProjectGeneralPanel.java
 *
 * Created on March 12, 2007, 7:49 PM
 */

package ca.uregina.engg.eil.dyna.ui;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author  robert
 */
public class ProjectGeneralPanel extends javax.swing.JPanel
{
    
    protected String m_projectName;
    protected String m_doc;
    protected String m_url;
    
    /** Creates new form ProjectGeneralPanel */
    public ProjectGeneralPanel(String projectName, String doc, String url)
    {
        initComponents();
        
        //m_nameCtrl.setInputVerifier(new NameVerifier());
        //m_urlCtrl.setInputVerifier(new UrlVerifier());
        
        m_projectName = projectName;
        m_doc = doc;
        m_url = url;
        
        m_nameCtrl.setText(m_projectName);
        m_docCtrl.setText(m_doc);
        m_urlCtrl.setText(m_url);
    }
    
    public String getProjectName()
    {
        return m_projectName;
    }
    
    public String getDocumentation()
    {
        return m_doc;
    }
    
    public String getURL()
    {
        return m_url;
    }
    
    public boolean transferDataFromWindow()
    {        
        boolean success = false;
        
        if (!m_nameCtrl.getText().equals(""))
        {
            m_projectName = m_nameCtrl.getText();
            m_doc = m_docCtrl.getText();
            m_url = m_urlCtrl.getText();
            
            success = true;
        }
        else
        {
            JOptionPane.showMessageDialog(javax.swing.JOptionPane.getFrameForComponent(this),
                                          "Project Name Required!",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
        
        return success;
    }
       
    class NameVerifier extends InputVerifier
    {
        public boolean verify(JComponent input) 
         {
            boolean isValid = false;

            JTextField tf = (JTextField) input;
            
            if (!tf.getText().equals(""))
            {                
                isValid = true;                                
            }
                       
            return isValid;
         }
    }
    
    class UrlVerifier extends InputVerifier
    {
        public boolean verify(JComponent input) 
         {
            boolean isValid = false;

            JTextField tf = (JTextField) input;
            
            //if (!tf.getText().equals(""))
            //{                
                isValid = true;                                
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
        m_nameLbl = new javax.swing.JLabel();
        m_nameCtrl = new javax.swing.JTextField();
        m_docLbl = new javax.swing.JLabel();
        m_urlLbl = new javax.swing.JLabel();
        m_urlCtrl = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        m_docCtrl = new javax.swing.JTextArea();

        m_nameLbl.setText("Project Name:");

        m_docLbl.setText("Documentation:");

        m_urlLbl.setText("URL:");

        m_docCtrl.setColumns(20);
        m_docCtrl.setRows(5);
        jScrollPane1.setViewportView(m_docCtrl);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(m_docLbl)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                            .add(m_nameLbl)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(m_nameCtrl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 275, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1))
                    .add(layout.createSequentialGroup()
                        .add(m_urlLbl)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(m_urlCtrl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 332, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_nameLbl)
                    .add(m_nameCtrl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(19, 19, 19)
                .add(m_docLbl)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(19, 19, 19)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(m_urlLbl)
                    .add(m_urlCtrl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(92, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea m_docCtrl;
    private javax.swing.JLabel m_docLbl;
    private javax.swing.JTextField m_nameCtrl;
    private javax.swing.JLabel m_nameLbl;
    private javax.swing.JTextField m_urlCtrl;
    private javax.swing.JLabel m_urlLbl;
    // End of variables declaration//GEN-END:variables
    
}
