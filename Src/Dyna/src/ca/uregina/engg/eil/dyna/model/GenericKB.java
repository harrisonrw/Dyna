/*
 * GenericKB.java
 *
 * Created on October 31, 2006, 6:42 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.model;

/**
 * A generic wrapper for knowledge bases.
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public interface GenericKB 
{
    public GenericInstance createInstance(String clsName, String instName);
    public boolean containsInstance(String instName);
    public GenericInstance getInstance(String instName);
    public GenericProperty[] getPropertyList(String clsName);
    public GenericProperty getProperty(String propertyName);
    public String[] getClassNames();
}
