/* Generated By:JJTree: Do not edit this line. AssertStmt.java */

/**
 * AST Node for "assert" statement
 *
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import java.io.PrintStream;
import java.util.Hashtable;

public class AssertStmt extends SimpleNode {
  public AssertStmt(int id) {
    super(id);
  }

  public AssertStmt(TBLParser p, int id) {
    super(p, id);
  }

  public void interpret() throws ParseException
  {
      Hashtable<String, Object> symbolTable = SymbolTableHelper.getSymbolTable();
      
     jjtGetChild(0).interpret();
        
     Interpreter interp = InterpreterManager.getInterpreterManager().getInterpreter();
     Stack stack = Stack.getStack();
     
     interp.doAssert(((Boolean)stack.pop()).booleanValue());          
     
  }
  
}
