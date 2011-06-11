/* Generated By:JJTree: Do not edit this line. VarDeclaration.java */

/**
 * AST Node for variable declaration
 *
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import ca.uregina.engg.eil.dyna.util.NULL;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Slot;
import edu.stanford.smi.protege.model.ValueType;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Vector;
import ca.uregina.engg.eil.dyna.model.GenericInstance;
import ca.uregina.engg.eil.dyna.model.GenericKB;
import ca.uregina.engg.eil.dyna.model.GenericProperty;

public class VarDeclaration extends SimpleNode implements TBLParserConstants {
    
  String m_name;
    
  public VarDeclaration(int id) {
    super(id);
  }

  public VarDeclaration(TBLParser p, int id) {
    super(p, id);
  }
  
  public void interpret() throws ParseException
  {
      jjtGetChild(0).interpret();
                                       
      Interpreter interp = InterpreterManager.getInterpreterManager().getInterpreter();  
      InstanceTable instanceTable = InstanceTable.getInstanceTable();
      Hashtable<String, Object> symbolTable = SymbolTableHelper.getSymbolTable();
    
      if (jjtGetChild(0) instanceof StdDataType)
      {
          StdDataType dataType = (StdDataType) jjtGetChild(0);
          
          if (dataType.m_type == INT)
          {
              declareInt();                            
          }
          else if (dataType.m_type == FLOAT)
          {
              declareFloat();              
          }
          else if (dataType.m_type == BOOLEAN)
          {              
              declareBoolean();
          }
          else if (dataType.m_type == STRING)
          {
              declareString();              
          }
      }
      else
      {          
          declareCustom();                    
      }
  }
      
  private void declareInt()
  {
      Interpreter interp = InterpreterManager.getInterpreterManager().getInterpreter();  
      InstanceTable instanceTable = InstanceTable.getInstanceTable();
      Hashtable<String, Object> symbolTable = SymbolTableHelper.getSymbolTable();
           
      // 0 = default integer value
      symbolTable.put(m_name, new Integer(0));
     
  }
  
  private void declareFloat()
  {
      Interpreter interp = InterpreterManager.getInterpreterManager().getInterpreter();  
      InstanceTable instanceTable = InstanceTable.getInstanceTable();
      Hashtable<String, Object> symbolTable = SymbolTableHelper.getSymbolTable();
            
      // 0.0f = default value value
      symbolTable.put(m_name, new Float(0.0f));
      
  }
  
  private void declareBoolean()
  {
      Interpreter interp = InterpreterManager.getInterpreterManager().getInterpreter();  
      InstanceTable instanceTable = InstanceTable.getInstanceTable();
      Hashtable<String, Object> symbolTable = SymbolTableHelper.getSymbolTable();
                     
      symbolTable.put(m_name, new Boolean(false));      
  }
  
  private void declareString()
  {
      Interpreter interp = InterpreterManager.getInterpreterManager().getInterpreter();  
      InstanceTable instanceTable = InstanceTable.getInstanceTable();
      Hashtable<String, Object> symbolTable = SymbolTableHelper.getSymbolTable();
                     
      symbolTable.put(m_name, new String(""));     
  }
  
  private void declareCustom()
  {
      Interpreter interp = InterpreterManager.getInterpreterManager().getInterpreter();  
      InstanceTable instanceTable = InstanceTable.getInstanceTable();
      Hashtable<String, Object> symbolTable = SymbolTableHelper.getSymbolTable();
      
      CustomDataType dataType = (CustomDataType) jjtGetChild(0);
                      
      GenericInstance instance = interp.createInstance(dataType.m_name, m_name);
      GenericKB kb = interp.getKnowledgeBase();

      // Add the instance to the symbol table.
      symbolTable.put(m_name, instance);

      // Put all of the instance's, slots in the m_symbolTable         
     GenericProperty[] propertyList = kb.getPropertyList(dataType.m_name);
     if (propertyList != null)
     {
          for (int i = 0; i < propertyList.length; i++)
          {              
              String fullSlotName = m_name + "." + propertyList[i].getName(); 

              instanceTable.put(fullSlotName, instance);    
              Object value = instance.getPropertyValue(propertyList[i]);
              if (value == null)
              {
                  symbolTable.put(fullSlotName, new NULL());
              }
              else
              {
                  symbolTable.put(fullSlotName, value);
              }                                                
          }
     }      
  }

}
