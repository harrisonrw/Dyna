/*
 * TopicEvent.java
 *
 * Created on Setp. 15, 2006, 5:57 PM
 *
 * @author Sean Wu
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.event;

import java.util.EventObject;
import java.util.Hashtable;

/*
 * TopicEvent.java
 *
 * Created on Setp. 15, 2006, 5:57 PM
 *
 * @author Sean Wu
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class TopicEvent extends EventObject
{
    private String topic = null;
    private Hashtable data = null;
    public TopicEvent (Object source, String topic, Hashtable data)
    {
        super (source);
        this.topic = topic;
        this.data = data;
    }
    public String getTopic ()
    {
        return topic;
    }
    
    public Hashtable getData ()
    {
        return data;
    }
}
