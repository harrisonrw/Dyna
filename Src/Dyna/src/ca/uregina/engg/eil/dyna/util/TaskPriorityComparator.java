/*
 * TaskPriorityComparator.java
 *
 * Created on November 20, 2006, 4:02 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.util;

import java.util.Comparator;
import ca.uregina.engg.eil.dyna.model.TaskPriority;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TaskPriorityComparator implements Comparator<TaskPriority>
{
        
    public int compare(TaskPriority tp1, TaskPriority tp2)
    {
        int c = 0;
                
        if (tp1.getPriority() < tp2.getPriority())
        {
            c = -1;
        }
        else if (tp1.getPriority() > tp2.getPriority())
        {
            c = 1;
        }
        
        return c;
    }
}
