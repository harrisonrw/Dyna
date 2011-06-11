/*
 * InterpreterTest.java
 *
 * Created on September 16, 2006, 4:58 PM
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
import edu.stanford.smi.protege.model.KnowledgeBaseFactory;
import edu.stanford.smi.protege.model.Project;
import edu.stanford.smi.protege.storage.clips.ClipsKnowledgeBaseFactory;
import edu.stanford.smi.protege.test.AbstractTestCase;
import edu.stanford.smi.protege.util.Log;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ca.uregina.engg.eil.dyna.interpreter.Interpreter;
import ca.uregina.engg.eil.dyna.widget.StringOutputStream;

/**
 *
 * @author Robert Harrison
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class InterpreterTest extends TestCase
{
    private Interpreter m_interpreter = null;
    private Project m_project = null;   
    private PrintStream m_out = null;
    private StringOutputStream m_strOutStream = null;
    
    // Addition
    private static final String ADD_INT = "print 2 + 3";
    private static final String ADD_INT_RESULT = "5";
    private static final String ADD_FLOAT = "print 2.1 + 3.2";
    private static final String ADD_FLOAT_RESULT = "5.3";
    
    // Multiplication
    private static final String MUL_INT = "print 5 * 5";
    private static final String MUL_INT_RESULT = "25";
    private static final String MUL_FLOAT = "print 1.2 * 5.4";
    private static final String MUL_FLOAT_RESULT = "6.4800005";
         
    // Equal-Equal Condition
    private static final String EQEQUAL_TRUE = "print 1 == 1";                                             
    private static final String EQEQUAL_TRUE_RESULT = "true";
    
    // If Statement
    private static final String IF_STMT = "boolean a\n" +
                                          "a = true\n" +
                                          "if (a == true)\n" +
                                          "{ print 1 }";
    private static final String IF_STMT_RESULT = "1";
    
    // If-Else Statement
    private static final String IF_ELSE_STMT = "boolean a\n" +
                                               "a = false\n" +
                                               "if (a == true)\n" +
                                               "{ print 1 }\n" +
                                               "else { print 0 } ";
    private static final String IF_ELSE_STMT_RESULT = "0";
    
    
    /**
     * Creates a new instance of InterpreterTest
     */
    public InterpreterTest(String testName)
    {
        super(testName);
    }
    
    protected void setUp() throws Exception 
    {        
        m_project = createProject();        
        m_strOutStream = new StringOutputStream(new String());
        m_out = new PrintStream(m_strOutStream, true);
        
        m_interpreter = new Interpreter(m_project.getKnowledgeBase(), m_out);
    }
    
    protected void tearDown() throws Exception
    {
        m_interpreter = null;
        m_project = null;
        m_out = null;
        m_strOutStream = null;
    }
    
    protected Project createProject() 
    {
        Collection errors = new ArrayList();
        Project project = Project.createNewProject(new ClipsKnowledgeBaseFactory(), errors);
        checkErrors(errors);
        return project;
    }
    
    protected void checkErrors(Collection errors) 
    {
        if (!errors.isEmpty()) 
        {
            Iterator i = errors.iterator();
            while (i.hasNext()) 
            {
                Object o = i.next();
                Log.getLogger().warning(o.toString());
            }
            fail();
        }
    }
    
    protected String normalize(String buffer)
    {
        String buffer2 = buffer.replace("\n", "");
        String buffer3 = buffer2.replace("\r", "");
        return buffer3;
    }
    
    public static Test suite() 
    {
        TestSuite suite = new TestSuite(InterpreterTest.class);
        
        return suite;
    }
    
    public void testAddIntExpr() throws Exception
    {
        System.out.println("AddIntExpr");
               
        m_interpreter.interpret(ADD_INT);
        
        String buffer = normalize(m_strOutStream.getBuffer());
                
        assertTrue(buffer.equals(ADD_INT_RESULT));        
    }
    
    public void testAddFloatExpr() throws Exception
    {
        System.out.println("AddFloatExpr");
               
        m_interpreter.interpret(ADD_FLOAT);
        
        String buffer = normalize(m_strOutStream.getBuffer());
                
        assertTrue(buffer.equals(ADD_FLOAT_RESULT));        
    }
    
    public void testMulIntExpr() throws Exception
    {
        System.out.println("MultIntExpr");
               
        m_interpreter.interpret(MUL_INT);
        
        String buffer = normalize(m_strOutStream.getBuffer());
                
        assertTrue(buffer.equals(MUL_INT_RESULT));        
    }
    
    public void testMulFloatExpr() throws Exception
    {
        System.out.println("MultFloatExpr");
               
        m_interpreter.interpret(MUL_FLOAT);
        
        String buffer = normalize(m_strOutStream.getBuffer());
        
        assertTrue(buffer.equals(MUL_FLOAT_RESULT));        
    }
    
    public void testEqEqualTrue() throws Exception
    {
        System.out.println("EqEqualTrue");
               
        m_interpreter.interpret(EQEQUAL_TRUE);
        
        String buffer = normalize(m_strOutStream.getBuffer());
        
        assertTrue(buffer.equals(EQEQUAL_TRUE_RESULT));        
    }
    
    public void testIfStmt() throws Exception
    {
        System.out.println("IfStmt");
               
        m_interpreter.interpret(IF_STMT);
        
        String buffer = normalize(m_strOutStream.getBuffer());
        
        assertTrue(buffer.equals(IF_STMT_RESULT));        
    }
    
    public void testIfElseStmt() throws Exception
    {
        System.out.println("IfElseStmt");
               
        m_interpreter.interpret(IF_ELSE_STMT);
        
        String buffer = normalize(m_strOutStream.getBuffer());
        
        assertTrue(buffer.equals(IF_ELSE_STMT_RESULT));        
    }

    
}
