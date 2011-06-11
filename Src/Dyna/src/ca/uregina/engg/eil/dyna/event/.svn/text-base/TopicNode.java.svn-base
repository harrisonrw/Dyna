/*
 * TopicNode.java
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

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/*
 * TopicNode.java
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
public class TopicNode
{
    private TopicNode parent;
    private Hashtable<String, Object> children;
    private Vector<TopicEventListener> listenerList;
    
    
    /** Creates a new instance of TopicNode */
    public TopicNode ()
    {
        children = new Hashtable<String, Object>(1);
        listenerList = new Vector<TopicEventListener>(0);
    }
    
    public synchronized void addListener(TopicEventListener listener)
    {
        listenerList.addElement (listener);
    }
    
    public synchronized void addListener (String topic, TopicEventListener listener)
    {
       if(topic == null)
       {
           addListener(listener);
       }
       else
       {
           String[] topics = topic.split ("[.]", 2);
           TopicNode node;
           if(!children.containsKey (topics[0]))
           {
               node = new TopicNode();
               children.put(topics[0], node);
           }
               
           else
               node = (TopicNode)children.get (topics[0]);
           
           if(topics.length > 1)
               node.addListener (topics[1], listener);
           else
               node.addListener (null, listener);
       }
    }
    
    public synchronized void removeListener(String topic, TopicEventListener listener)
    {
        if (topic != null)
        {
            String topics[] = topic.split ("[.]", 2);
            
            if (children.containsKey(topics[0]))
            {
                TopicNode child = (TopicNode) children.get(topics[0]);
                
                if (topics.length > 1)
                {
                    child.removeListener(topics[1], listener);
                }
                else
                {
                    child.removeListener(null, listener);
                }
            }
            
        }
        else
        {
            listenerList.remove(listener);
        }
    }
    
    public synchronized Vector<TopicEventListener> getListenerList(String topic)
    {
        Vector<TopicEventListener> listeners = new Vector<TopicEventListener>();
        
        if (topic != null)
        {
            String topics[] = topic.split("[.]", 2);
                        
            if (children.containsKey(topics[0]))
            {
                // Add this child's listeners to the list.
                TopicNode child = (TopicNode) children.get(topics[0]);
                listeners = child.getListenerList(null);
                
                // Now append the child's children to the list.
                if (topics.length > 1)
                {
                    Vector<TopicEventListener> childListeners = child.getListenerList(topics[1]);
                    
                    for (int i = 0; i < childListeners.size(); i++)
                    {
                        //listeners.addAll(child.getListenerList(topics[1]));
                        
                        TopicEventListener childListener = childListeners.get(i);
                        if (!listeners.contains(childListener))
                        {                            
                            listeners.add(childListener);
                        }
                    }
                }
            }
            
        }
        else
        {
            return listenerList;
        }
                        
        return listeners;
        
        /*
        if(topic != null)
        {
            String topics[] = topic.split ("[.]", 2);
            if(children.containsKey (topics[0]))
            {
                
                
                if(topics.length > 1)
                    return ((TopicNode)children.get (topics[0])).getListenerList (topics[1]);
                else
                    return ((TopicNode)children.get (topics[0])).getListenerList (null);
            }
            else
            {
                return new Vector<TopicEventListener>();
            }
        }
        else
        {
            Enumeration nodes = children.elements ();
            Vector<TopicEventListener> returnList = listenerList;
            while(nodes.hasMoreElements ())
            {
                returnList.addAll (((TopicNode)nodes.nextElement ()).getListenerList (null));
            }

            return returnList;
        }
         */
    }

    
}
