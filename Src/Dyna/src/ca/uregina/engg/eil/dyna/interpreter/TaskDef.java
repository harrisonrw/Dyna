/* Generated By:JJTree: Do not edit this line. TaskDef.java */

package ca.uregina.engg.eil.dyna.interpreter;

public class TaskDef extends SimpleNode {
  public TaskDef(int id) {
    super(id);
  }

  public TaskDef(TBLParser p, int id) {
    super(p, id);
  }
  
  public void interpret() throws ParseException
  {
      int numChildren = jjtGetNumChildren();

      for (int i = 0; i < numChildren; i++)
      {
            jjtGetChild(i).interpret();
      }
  }

}
