/* Generated By:JJTree: Do not edit this line. IfStmt.java */

/**
 * AST Node for "if" statement
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

public class IfStmt extends SimpleNode {
  public IfStmt(int id) {
    super(id);
  }

  public IfStmt(TBLParser p, int id) {
    super(p, id);
  }

  public void interpret() throws ParseException
  { 
      int numChildren = jjtGetNumChildren();
      
      jjtGetChild(0).interpret();
      
      Interpreter interp = InterpreterManager.getInterpreterManager().getInterpreter();     
      Stack stack = Stack.getStack();
                  
      if (((Boolean)stack.pop()).booleanValue())
      {
          jjtGetChild(1).interpret();
      }         
      else if (numChildren >= 3)
      {
          for (int i = 2; i < numChildren; i++)
            jjtGetChild(i).interpret();
      }
  }
  
}