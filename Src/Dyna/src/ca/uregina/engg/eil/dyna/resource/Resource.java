/*
 * Resource.java
 *
 * Created on August 21, 2006, 2:25 PM
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.resource;

import java.awt.event.KeyEvent;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public interface Resource
{
    
    public static final String PROGRAM_TITLE = "Dyna";
    public static Integer PROGRAM_MNEMONIC = new Integer(KeyEvent.VK_D);
    
    // ----------------------------------------------------
    // Project
    
    public static final String NEW_PROJECT_TITLE = "New Project";
    public static final String OPEN_PROJECT_TITLE = "Open Project";
    public static final String CLOSE_PROJECT_TITLE = "Close";
    public static final String SAVE_PROJECT_TITLE = "Save";
    public static final String SAVE_PROJECT_AS_TITLE = "Save As";
    
    public static final String IMPORT_PROJECT_TITLE = "Import";
    public static final String IMPORT_OWL_TITLE = "OWL";
    
    public static final String EXPORT_PROJECT_TITLE = "Export";
    public static final String EXPORT_OWL_TITLE = "OWL";
    
    public static final String PROPERTIES_TITLE = "Project Properties";
        
    // Project tooltips.
    public static final String NEW_PROJECT_TT = "Create new Dyna project";
    public static final String OPEN_PROJECT_TT = "Open Dyna project";
    public static final String CLOSE_PROJECT_TT = "Close Dyna project";
    public static final String SAVE_PROJECT_TT = "Save Dyna project";
    public static final String SAVE_PROJECT_AS_TT = "Save Dyna project as";
    
    // Project action commands.
    public static final String NEW_PROJECT_AC = "NewProject";
    public static final String OPEN_PROJECT_AC = "OpenProject";
    public static final String CLOSE_PROJECT_AC = "CloseProject";
    public static final String SAVE_PROJECT_AC = "SaveProject";
    public static final String SAVE_PROJECT_AS_AC = "SaveProjectAs";
    
    public static final String DYNA_FILE_DESC = "Dyna project file";
    public static final String OWL_FILE_DESC = "Web Ontology Language file";
    
    public static final String DYNA_EXT = "dyn";
    public static final String OWL_EXT = "owl";
    
    // ----------------------------------------------------
    // Task
    
    public static final String TASKS_TITLE = "Tasks";
    
    public static final String TASK_BROWSER_TITLE = "Task Browser";
    
    // Task images
    public static final String SHOW_TASK_IMG = "viewTask.gif";
    public static final String ADD_TASK_IMG = "addTask.gif";
    public static final String DEL_TASK_IMG = "delTask.gif";
    
    // Task tooltips.
    public static final String SHOW_TASK_TT = "Show Task";
    public static final String ADD_TASK_TT = "Add Task";
    public static final String DEL_TASK_TT = "Delete Task";
    
    // Task Action Commands.
    public static final String SHOW_TASK_AC = "ShowTask";
    public static final String ADD_TASK_AC = "AddTask";
    public static final String DEL_TASK_AC = "DelTask";
    
    // ----------------------------------------------------
    // Objective
    
    public static final String OBJECTIVES_TITLE = "Objectives";
    
    public static final String OBJECTIVE_BROWSER_TITLE = "Objective Browser";
    
    // Task images
    public static final String SHOW_OBJECTIVE_IMG = "viewObjective.gif";
    public static final String ADD_OBJECTIVE_IMG = "addObjective.gif";
    public static final String DEL_OBJECTIVE_IMG = "delObjective.gif";
    
    // Task tooltips.
    public static final String SHOW_OBJECTIVE_TT = "Show Objective";
    public static final String ADD_OBJECTIVE_TT = "Add Objective";
    public static final String DEL_OBJECTIVE_TT = "Delete Objective";
    
    // Task Action Commands.
    public static final String SHOW_OBJECTIVE_AC = "ShowObjective";
    public static final String ADD_OBJECTIVE_AC = "AddObjective";
    public static final String DEL_OBJECTIVE_AC = "DelObjective";
    
    // ----------------------------------------------------
    // Algorithm
    
    public static final String ADD_ALG_AC = "AddAlg";
    
    // ----------------------------------------------------
    // Help
    
    public static final String HELP_TITLE = "Help";
    public static final String ABOUT_TITLE = "About Dyna";
    public static final String DOCUMENTATION_TITLE = "Documentation";
    
    // Help tooltips.
    public static final String HELP_TT = "Help";
    public static final String ABOUT_TT = "Show info about Dyna";
    public static final String DOCUMENTATION_TT = "Show documenation";
    
    // Help action commands.
    public static final String HELP_AC = "Help";
    public static final String ABOUT_AC = "About";
    public static final String DOCUMENTATION_AC = "Documentation";
}
