/* Generated By:JJTree: Do not edit this line. TaskCall.java */

package ca.uregina.engg.eil.dyna.interpreter;

import ca.uregina.engg.eil.dyna.model.KnowledgeComponent;
import ca.uregina.engg.eil.dyna.model.Task;
import java.util.Hashtable;
import java.util.Vector;

public class TaskCall extends SimpleNode {
  public TaskCall(int id) {
    super(id);
  }

  public TaskCall(TBLParser p, int id) {
    super(p, id);
  }

  public void interpret() throws ParseException
  {
      /*
      Interpreter interp = InterpreterManager.getInterpreterManager().getInterpreter();
      Hashtable<String, Object> symbolTable = SymbolTableHelper.getSymbolTable();
      Stack stack = Stack.getStack();
      
      int numChildren = jjtGetNumChildren();
      
      jjtGetChild(0).interpret();
      
      // Get the name of the task.
      String name = (String) stack.pop();
      
      // Get the task.
      KnowledgeComponent k = (KnowledgeComponent) symbolTable.get(name);
      
      // Store the task in the stack.
      
      
      if (jjtGetChild(0) instanceof Id)
      {
            name = ((Id)jjtGetChild(0)).m_name;
            
            // Check that the task exists.
            TaskController taskController = TaskController.getTaskController();
            Task task = taskController.getTask(name);
            if (task != null)
            {
                // Task exists, so add it to the symbol table.
                stack.push(task);
                symbolTable.put(name, task);                
            }
            else
            {
                // throw error
                System.out.println("Task " + name + " not defined!");
            }
      }
      else
      {
          // throw error
          System.out.println("Invalid statement!");
      }
      
      
      
      
      
      
      
      
      // Get the arguments (if there are any).
      Vector<Object> argList = new Vector<Object>();
      for (int i = 1; i < numChildren; i++)
      {
          jjtGetChild(i).interpret();
          
          // Get the value from the stack and add it to arg list.
          Object arg = stack.pop();
          argList.add(arg);                    
      }
      
      
      
              
      interp.createDependency(name, argList);            
       */
  }
  
}
