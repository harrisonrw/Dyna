/* Generated By:JJTree: Do not edit this line. AttributeRef.java */

/**
 * AST Node for attribute reference
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
import java.util.Hashtable;

public class AttributeRef extends SimpleNode implements TBLParserConstants {
  
  String m_name;
    
  public AttributeRef(int id) {
    super(id);
  }

  public AttributeRef(TBLParser p, int id) {
    super(p, id);
  }

  public void interpret() throws ParseException
  {
      //jjtGetChild(0).interpret();
      //jjtGetChild(1).interpret();
            
      CallStack callStack = CallStack.getCallStack();
      
      Hashtable<String, Object> symbolTable = SymbolTableHelper.getSymbolTable();    
      Stack stack = Stack.getStack();
      
      String clsName = ((Id)jjtGetChild(0)).m_name;
      String attrName = ((Id)jjtGetChild(1)).m_name;
                                    
      m_name = clsName + "." + attrName;
            
      Object value = symbolTable.get(m_name);
      
      if (value == null)
      {
          throw new ParseException("Undefined attribute name: " + m_name);   
      }
      else
      {                    
          stack.push(symbolTable.get(m_name));
      }
  }
  
}
