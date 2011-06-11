/*
 * EventService.java
 *
 * Created on October 21, 2006, 11:06 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.event;

import java.util.Vector;

/**
 * Based on Sean Wu's CO2 EventService.
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class EventService
{
    /*  Event Type */
    public static final String COMMAND = "Command";
    public static final String FEEDBACK = "Feedback";
    
    public static final String PROJECT = ".Project";
    public static final String TASK = ".Task";
    public static final String OBJECTIVE = ".Objective";
    public static final String BEHAVIOUR = ".Behaviour";
    public static final String TASK_PRIORITY = ".TskPrty";
    public static final String DEPENDENCY = ".Dependency";
    public static final String TSK_OBJECT = ".TskObj";
    public static final String PRE_CONDITION = ".PreCondition";
    public static final String TEST = ".Test";
    public static final String SETUP = ".Setup";
    public static final String ARGS = ".Args";
    public static final String RETURN = ".Return";
    
    public static final String CREATE = ".Create";    
    public static final String OPEN = ".Open";
    public static final String CLOSE = ".Close";    
    public static final String SAVE = ".Save";
    public static final String DELETE = ".Delete";
    public static final String DELETE_ALL = ".DeleteAll";
    public static final String RENAME = ".Rename";
    public static final String EDIT_DOC = ".EditDoc";
    public static final String EDIT = ".Edit";
    public static final String RUN = ".Run";
    public static final String RUN_ALL = ".RunAll";
    public static final String ADD = ".Add";
    public static final String CLEAR_OUTPUT = ".ClearOutput";
    public static final String EXPORT = ".Export";
    public static final String IMPORT = ".Import";
    public static final String OWL = ".Owl";    
    public static final String SHOW = ".Show";
            
        
    /* Constant Keys */
    public static final String PROJECT_NAME = "Project Name";
    public static final String PROJECT_FILENAME = "Project Filename";
    public static final String PROJECT_OBJ = "Project Object";
    public static final String OBJECT = "Object";
    public static final String TASK_VECTOR = "Task Vector";
    public static final String OBJECTIVE_VECTOR = "Objective Vector";
    public static final String TASK_NAME = "Task Name";
    public static final String PARENT_TASK_NAME = "Parent Task Name";
    public static final String TASK_OBJECT = "Task Object";
    public static final String TASK_OBJECT_NAME = "Task Object Name";
    public static final String PARENT_TASK_OBJECT = "Parent Task Object";
    public static final String DOCUMENTATION = "Documentation";
    public static final String CODE = "Code";
    public static final String OBJECTIVE_NAME = "Objective Name";
    public static final String OBJECTIVE_OBJECT = "Objective Object";
    public static final String PRIORITY = "Priority";
    public static final String TASK_PRIORITY_OBJECT = "Task Priority Object";   
    public static final String DEP_NAME = "Dep Name";
    public static final String DEP_OBJECT = "Dep Object";
    public static final String CLS_NAME = "Cls Name";
    public static final String TSK_OBJECT_OBJECT = "Tsk Object Object";
    public static final String TSK_OBJECT_NAME = "Tsk Obj Name";
    public static final String PRE_CONDITION_TEXT = "Pre Condition Text";
    public static final String TEST_NAME = "Test Name";
    public static final String TEST_CASE_OBJECT = "Test Case Object";    
    public static final String URL = "URL";
    public static final String NAMESPACE_LIST = "Namespace List";
    public static final String ARG_LIST_STR = "Arg List String";
    public static final String TASK_RETURN = "Task Return";
    public static final String MODIFIER = "Modifier";
    
    private static EventService m_service = null;
    private TopicNode m_root = new TopicNode();

    
    
    /** Creates a new instance of EventService */
    public EventService()
    {
        
    }
    
    public static EventService getEventService()
    {
        if (m_service == null)
        {
            m_service = new EventService ();
        }
        
        return m_service;
    }
    
    public synchronized void addListener (String topic, TopicEventListener listener)
    {
        m_root.addListener (topic, listener);                
    }
    
    public synchronized void removeListener (String topic, TopicEventListener listener)
    {
        m_root.removeListener(topic, listener);
    }
    
    public void fireEvent (TopicEvent event) throws Exception
    {
        Vector<TopicEventListener> list = null;
        list = m_root.getListenerList (event.getTopic ());
        for(int i = 0; i < list.size (); i ++)
        {            
            list.get (i).notify (event);
        }        
    }
    
    public void clear()
    {
        m_root = null;
        m_service = null;
    }
            
            
    
}
