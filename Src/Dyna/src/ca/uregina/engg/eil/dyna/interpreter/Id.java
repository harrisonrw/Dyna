/* Generated By:JJTree: Do not edit this line. Id.java */

/**
 * AST Node for an identifier (name of variable or instance)
 *
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import ca.uregina.engg.eil.dyna.model.Task;
import ca.uregina.engg.eil.dyna.util.NULL;
import edu.stanford.smi.protege.model.KnowledgeBase;
import java.io.PrintStream;
import java.util.Hashtable;

public class Id extends SimpleNode {
  
    public String m_name;
    
    public Id(int id) {
    super(id);
  }

  public Id(TBLParser p, int id) {
    super(p, id);
  }

  public void interpret() throws ParseException
  {   
      
      
      Hashtable<String, Object> symbolTable = SymbolTableHelper.getSymbolTable();
      Stack stack = Stack.getStack();
      
      Object symbol = symbolTable.get(m_name);
      
      if (symbol != null)
      {      
          stack.push(symbol);            
      }
      else
      {                    
          String msg = "ERROR " + m_name + " not defined";
          throw new ParseException(msg);
      }
       
  }  
}
