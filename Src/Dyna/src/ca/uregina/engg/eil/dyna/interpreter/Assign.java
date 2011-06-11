/* Generated By:JJTree: Do not edit this line. Assign.java */

/**
 * AST Node for assignment statement
 *
 * @author Robert Harrison
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.interpreter;

import edu.stanford.smi.protege.model.Cls;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Slot;
import java.io.PrintStream;
import ca.uregina.engg.eil.dyna.model.GenericInstance;
import ca.uregina.engg.eil.dyna.model.GenericKB;
import ca.uregina.engg.eil.dyna.model.GenericProperty;
import java.util.Hashtable;

public class Assign extends SimpleNode {
    
  Interpreter m_interp;  
  InstanceTable m_instanceTable;    
  Hashtable<String, Object> m_symbolTable;    
  Stack m_stack;
    
  public Assign(int id) {
    super(id);
  }

  public Assign(TBLParser p, int id) {
    super(p, id);
  }

  public void interpret() throws ParseException
  {     
     jjtGetChild(0).interpret();
     jjtGetChild(1).interpret();
          
     m_interp = InterpreterManager.getInterpreterManager().getInterpreter();
     m_instanceTable = InstanceTable.getInstanceTable();     
     m_symbolTable = SymbolTableHelper.getSymbolTable();    
     m_stack = Stack.getStack();
     
     String name = "";
     
     if (jjtGetChild(0) instanceof Id)
     {         
        name = ((Id)jjtGetChild(0)).m_name;    
        
        Object newVal = m_stack.pop();
        m_stack.pop();

        m_stack.push(newVal);
        m_symbolTable.put(name, newVal); 
     }          
     else if (jjtGetChild(0) instanceof AttributeRef)
     {
        name = ((AttributeRef)jjtGetChild(0)).m_name;
        
        
        /*
        // Get the instance.
        GenericInstance instance = (GenericInstance) m_instanceTable.get(name);
        
        // Get the slot.
        int i = name.lastIndexOf(".");
        String slotName = name.substring(i+1);  
                
        GenericKB kb = interp.getKnowledgeBase();
        
        GenericProperty property = kb.getProperty(slotName);        
         */
        
        // Set the value of the slot.
                
        setPropertyValue(jjtGetChild(1), name);
        
        /*
        if (jjtGetChild(1) instanceof IntLiteral)
        {
            int newVal = ((Integer) stack.pop()).intValue();
            stack.pop(); // Pop the old value.
            
            instance.setPropertyValue(property, newVal);
                       
            stack.push(newVal);
            symbolTable.put(name, newVal); 
        }
        else if (jjtGetChild(1) instanceof FloatLiteral)
        {            
            float newVal = ((Float) stack.pop()).floatValue();
            stack.pop(); // Pop the old value.
                                             
            instance.setPropertyValue(property, newVal);
                             
            stack.push(newVal);
            symbolTable.put(name, newVal); 
        }
        else if (jjtGetChild(1) instanceof True)
        {         
            stack.pop();
            stack.pop();
            
            instance.setPropertyValue(property, true);
                        
            stack.push(true);
            symbolTable.put(name, true); 
        }
        else if (jjtGetChild(1) instanceof False)
        {      
            stack.pop();
            stack.pop();
            
            instance.setPropertyValue(property, false);
                        
            stack.push(false);
            symbolTable.put(name, false); 
        }        
        else if (jjtGetChild(1) instanceof StringLiteral)
        {
            String newVal = (String) stack.pop();
            stack.pop();
                                                              
            instance.setPropertyValue(property, newVal);
            
            stack.push(newVal);
            symbolTable.put(name, newVal); 
        }
        else if (jjtGetChild(1) instanceof Id)
        {
            Object newVal = stack.pop();
            stack.pop();
                        
            instance.setPropertyValue(property, newVal);
            
            stack.push(newVal);
            symbolTable.put(name, newVal);                        
        }             
        else if (jjtGetChild(1) instanceof TaskCall)
        {
            Object newVal = stack.pop();
            stack.pop();
                        
            instance.setPropertyValue(property, newVal);
            
            stack.push(newVal);
            symbolTable.put(name, newVal);     
        }
         */
     }         
         
  }  
  
  private void setPropertyValue(Object rhs, String name)
  {
    // Get the instance.
    GenericInstance instance = (GenericInstance) m_instanceTable.get(name);

    // Get the slot.
    int i = name.lastIndexOf(".");
    String slotName = name.substring(i+1);  

    GenericKB kb = m_interp.getKnowledgeBase();

    GenericProperty property = kb.getProperty(slotName);  
      
    if (rhs instanceof IntLiteral)
    {
        int newVal = ((Integer) m_stack.pop()).intValue();
        m_stack.pop(); // Pop the old value.

        instance.setPropertyValue(property, newVal);

        m_stack.push(newVal);
        m_symbolTable.put(name, newVal); 
    }
    else if (rhs instanceof FloatLiteral)
    {            
        float newVal = ((Float) m_stack.pop()).floatValue();
        m_stack.pop(); // Pop the old value.

        instance.setPropertyValue(property, newVal);

        m_stack.push(newVal);
        m_symbolTable.put(name, newVal); 
    }
    else if (rhs instanceof True)
    {         
        m_stack.pop();
        m_stack.pop();

        instance.setPropertyValue(property, true);

        m_stack.push(true);
        m_symbolTable.put(name, true); 
    }
    else if (rhs instanceof False)
    {      
        m_stack.pop();
        m_stack.pop();

        instance.setPropertyValue(property, false);

        m_stack.push(false);
        m_symbolTable.put(name, false); 
    }        
    else if (rhs instanceof StringLiteral)
    {
        String newVal = (String) m_stack.pop();
        m_stack.pop();

        instance.setPropertyValue(property, newVal);

        m_stack.push(newVal);
        m_symbolTable.put(name, newVal); 
    }
    else if (rhs instanceof Id)
    {
        Object newVal = m_stack.pop();
        m_stack.pop();

        instance.setPropertyValue(property, newVal);

        m_stack.push(newVal);
        m_symbolTable.put(name, newVal);                        
    }             
    else if (rhs instanceof TaskCall)
    {
        Object newVal = m_stack.pop();
        m_stack.pop();

        instance.setPropertyValue(property, newVal);

        m_stack.push(newVal);
        m_symbolTable.put(name, newVal);     
    }
    else
    {
        Object newVal = m_stack.pop();
        m_stack.pop();
        
        instance.setPropertyValue(property, newVal);

        m_stack.push(newVal);
        m_symbolTable.put(name, newVal); 
    }
  }
}