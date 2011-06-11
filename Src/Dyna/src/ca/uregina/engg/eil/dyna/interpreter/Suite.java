/* Generated By:JJTree: Do not edit this line. Suite.java */

/**
 * AST Node for a suite (stuff between { } )
 *
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import edu.stanford.smi.protege.model.KnowledgeBase;
import java.io.PrintStream;

public class Suite extends SimpleNode {
  public Suite(int id) {
    super(id);
  }

  public Suite(TBLParser p, int id) {
    super(p, id);
  }

  public void interpret() throws ParseException
  {              
        Interpreter interp = InterpreterManager.getInterpreterManager().getInterpreter();
        
        CallStack callStack = CallStack.getCallStack();
        
        CallItem item = null;
        
        CallItem parent = callStack.getTop();
        if (parent != null)
        {
            item = new CallItem(parent);
        }
        else
        {
            item = new CallItem();
        }
                
        callStack.push(item);
        
        int numChildren = jjtGetNumChildren();

        for (int i = 0; i < numChildren; i++)
        {
            jjtGetChild(i).interpret();
        }
        
        callStack.pop();
  }
  
}
