/* Generated By:JJTree: Do not edit this line. MinusExpr.java */

/**
 * AST Node for minus (-) expression
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

public class MinusExpr extends SimpleNode {
  public MinusExpr(int id) {
    super(id);
  }

  public MinusExpr(TBLParser p, int id) {
    super(p, id);
  }

  public void interpret() throws ParseException
  {
     jjtGetChild(0).interpret();
     jjtGetChild(1).interpret();

     Interpreter interp = InterpreterManager.getInterpreterManager().getInterpreter();        
     Stack stack = Stack.getStack();
     
     if (stack.peek() instanceof Integer)
     {
         int rhs = ((Integer)stack.pop()).intValue();
         int lhs = ((Integer)stack.pop()).intValue();
         
         stack.push(new Integer(lhs - rhs));                          
     }
     else if (stack.peek() instanceof Float)
     {
         float rhs = ((Float)stack.pop()).floatValue();
         float lhs = ((Float)stack.pop()).floatValue();
         
         stack.push(new Float(lhs - rhs));          
     }
  }
  
}
