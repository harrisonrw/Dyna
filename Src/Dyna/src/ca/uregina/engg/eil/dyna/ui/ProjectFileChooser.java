/*
 * ProjectFileChooser.java
 *
 * Created on September 4, 2006, 3:37 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.ui;

import javax.swing.JFileChooser;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class ProjectFileChooser extends JFileChooser
{
    private ProjectFileFilter m_filter;
    
    /** Creates a new instance of ProjectFileChooser */
    public ProjectFileChooser(String desc, String ext)
    {
        super();
        
        m_filter = new ProjectFileFilter(desc, ext);
        
        setAcceptAllFileFilterUsed(false);
        setFileFilter(m_filter);
        setMultiSelectionEnabled(false);
    }
    
    public String getExtension()
    {
        return m_filter.getExtension();
    }
    
    /**
    * Verify the file name to make sure that the proper project file extension is used.
    * @param fileName File name to check for project file extension
    * @return Possibly updated file name containing the project file extension
    */
    public static String validateProjectFileName(String fileName, String ext)
    {
        if (fileName.endsWith("." + ext))
        {
            return fileName;
        }

        return fileName + "." + ext;
    }  
    
}
