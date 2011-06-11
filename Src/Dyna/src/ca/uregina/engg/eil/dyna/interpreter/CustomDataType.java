/* Generated By:JJTree: Do not edit this line. CustomDataType.java */

/**
 * AST Node for custom data type (classes)
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

public class CustomDataType extends SimpleNode {
  
  int m_type;
  String m_name;
    
  public CustomDataType(int id) {
    super(id);
  }

  public CustomDataType(TBLParser p, int id) {
    super(p, id);
  }

  public void interpret() throws ParseException
  {
      
  }
  
}
