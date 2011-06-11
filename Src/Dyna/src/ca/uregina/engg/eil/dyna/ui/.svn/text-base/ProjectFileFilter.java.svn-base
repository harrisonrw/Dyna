/*
 * ProjectFileFilter.java
 *
 * Created on September 4, 2006, 3:39 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.ui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class ProjectFileFilter extends FileFilter
{    
    private String m_desc;
    private String m_ext;
    
    /** Creates a new instance of ProjectFileFilter */
    public ProjectFileFilter(String desc, String ext)
    {
        m_desc = desc;
        m_ext = ext;
    }

    public String getExtension()
    {
        return m_ext;
    }
    
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }
    
        return f.getName().endsWith ("." + m_ext);
    }

    public String getDescription()
    {
        return m_desc + " (*." + m_ext + ")";
    }
    
}
