/*
 * XmlManager.java
 *
 * Created on May 15, 2006, 10:37 AM
 *
 * @author Daniel Obst
 *
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */

package ca.uregina.engg.eil.dyna.util;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


/**
 * XML class which offers utility methods for creating and processing XML 
 * documents.
 * @author Daniel Obst
 * @author Robert Harrison (June 30, 2006)
 * 
 * Energy Informatics Laboratory
 * Faculty of Engineering
 * University of Regina
 * http://www.uregina.ca/engg
 */
public class XmlManager
{
  
  /*************/
  /*  Members  */
  /*************/
  private static DocumentBuilderFactory m_factory = DocumentBuilderFactory.newInstance ();
  
  
  /**
   * Creates a new instance of XmlManager.
   */
  public XmlManager ()
  {
  }
  
  /**
   * Creates an empty XML document object.
   * @return org.w3c.dom.Document object.
   * @throws uregina.engg.co2.xml.XmlManagerException if there is a problem creating the empty document.
   */
  public static Document createEmptyDocument () throws XmlManagerException
  {
    return getXmlBuilder ().newDocument ();
  }
  
  /**
   * Attempt to get a builder object which will allow an XML document to be
   * created.
   * @return DocumentBuilder object.
   * @throws uregina.engg.co2.xml.XmlManagerException raised if the builder cannot be successfully created.
   */
  private static DocumentBuilder getXmlBuilder () throws XmlManagerException
  {
    try
    {
      m_factory.setIgnoringElementContentWhitespace ( true );
      return m_factory.newDocumentBuilder ();
    }
    catch ( ParserConfigurationException e )
    {
      throw new XmlManagerException ( e.getMessage (), e.getCause () );
    }
  }
  
  /**
   * Check and remove from illegal characters from the XML element name.  This
   * will replace all spaces with underscores.  Empty spaces will be removed from
   * the front and back of the name.
   * @param elemName name of the element.
   * @return the filtered string.
   */
  private static String filterElemName ( String elemName )
  {
    /*  Make sure element name contains a valid character sequence  */
    elemName = elemName.replaceAll ( "\"->*", "");
    elemName = elemName.replaceAll ( " ", "_" );
    
    if ( elemName.endsWith ( "_" ) )
      elemName = elemName.substring ( 0, elemName.length () - 1 );
    
    return elemName;
  }
  
  /**
   * Creates an XML element using the supplied tag name and string value.
   * @param doc org.w3c.dom.Document object.
   * @param elemName name of the XML element.
   * @param elemText string to store in the text field of the element.
   * @return org.w3c.dom.Element object.
   * @throws uregina.engg.co2.xml.XmlManagerException if the desired tag cannot be created.
   */
  public static Element createElement ( Document doc, String elemName, String elemText )
    throws XmlManagerException
  {
    elemName = filterElemName ( elemName );
    
   
    /*  Create the new Element object  */
    Element elem = null;
    
    try
    {
      elem = doc.createElement ( elemName );
      //elem.setTextContent ( elemText.replaceAll ( "[\"]", "" ) );
      elem.setTextContent(elemText);
    
      return elem;
    }
    catch ( DOMException e )
    {
      throw new XmlManagerException ( e.getMessage (), e.getCause () );
    }
    finally
    {
      elem = null;
    }
  }
  
  /**
   * Creates an XML element using the supplied tag name and double value.
   * @param doc org.w3c.dom.Document object.
   * @param elemName name of the XML element.
   * @param elemVal double to store in the text field of the element.
   * @return org.w3c.dom.Element object.
   * @throws uregina.engg.co2.xml.XmlManagerException if the desired tag cannot be created.
   */
  public static Element createElement ( Document doc, String elemName, double elemVal )
    throws XmlManagerException
  {
    return createElement ( doc, elemName, Double.toString ( elemVal ) );
  }
  
  /**
   * Creates an XML element using the supplied tag name and boolean value.
   * @param doc org.w3c.dom.Document object.
   * @param elemName name of the XML element.
   * @param elemVal boolean to store in the text field of the element.
   * @return org.w3c.dom.Element object.
   * @throws uregina.engg.co2.xml.XmlManagerException if the desired tag cannot be created.
   */
  public static Element createElement ( Document doc, String elemName, boolean elemVal )
    throws XmlManagerException
  {
    return createElement ( doc, elemName, Boolean.toString ( elemVal ) );
  }
  
  /**
   * Retrieve the string contents of an XML element.
   * @param elem org.w3c.dom.Element object.
   * @param elemName name to check the element name against.
   * @return A possibly empty string.
   * @throws uregina.engg.co2.xml.XmlManagerException if the specified name and element name do not match.
   */
  public static String getElementString ( Element elem, String elemName )
    throws XmlManagerException
  {
    if ( ! elemName.equals ( elem.getNodeName () ) )
      throw new XmlManagerException ( "Found " + elem.getNodeName () + " instead of " + elemName );
    
    return elem.getTextContent ();
  }
  
  /**
   * Retrieve the double contents of an XML element.
   * @param elem org.w3c.dom.Element object.
   * @param elemName name to check the element name against.
   * @return A double value which may be +/- in value.
   * @throws uregina.engg.co2.xml.XmlManagerException if the specified name and element name do not match or if a double 
   * value could not be successfully retrieved.
   */
  public static double getElementDouble ( Element elem, String elemName )
    throws XmlManagerException
  {
    try
    {
      return Double.parseDouble ( getElementString ( elem, elemName ) );
    }
    catch ( NumberFormatException e )
    {
      throw new XmlManagerException ( e.getMessage (), e.getCause () );
    }
  }
  
  /**
   * Retrieve the boolean contents of an XML element.
   * @param elem org.w3c.dom.Element object.
   * @param elemName name to check the element name against.
   * @return A boolean value which may be true or false.
   * @throws uregina.engg.co2.xml.XmlManagerException if the specified name and element name do not match or if a double 
   * value could not be successfully retrieved.
   */
  public static boolean getElementBoolean ( Element elem, String elemName )
    throws XmlManagerException
  {
    String val = getElementString ( elem, elemName );
    
    if ( val.equals ( "true" ) )
      return true;
    
    else if ( val.equals ( "false" ) )
      return false;
    
    else
      throw new XmlManagerException ( "Invalid boolean value : " + val );
  }
  
  /**
   * Write the contents of the Document to System.out.
   * @param doc org.w3c.dom.Document object.
   * @throws uregina.engg.co2.xml.XmlManagerException if there is a problem displaying the XML tree.
   */
  public static void displayXml ( Document doc ) throws XmlManagerException
  {
    writeXml ( doc, new StreamResult ( System.out ) );
  }
  
  /**
   * Read the contents of an XML file into a Document object.
   * @param fileName XML file name.
   * @throws uregina.engg.co2.xml.XmlManagerException raised if the XML file could not be properly parsed or does not exist.
   * @return Document object which contain child elements.
   */
  public static Document readXmlFile ( String fileName )
    throws XmlManagerException
  {
    DocumentBuilder builder = getXmlBuilder ();
 
    try
    {
      return builder.parse ( new File ( fileName ) );
    }
    catch ( SAXException e )
    {
      throw new XmlManagerException ( e.getMessage (), e.getCause () );
    }
    catch ( IOException e )
    {
      throw new XmlManagerException ( e.getMessage (), e.getCause () );
    }
  }
  
  /**
   * Write the contents of the Document to a specified file.
   * @param doc org.w3c.dom.Document object.
   * @param fileName path to file where the XML tree will be written to.
   * @throws uregina.engg.co2.xml.XmlManagerException if there is a problem writing to the specified file.
   */
  public static void writeXmlToFile ( Document doc, String fileName )
    throws XmlManagerException
  {
    File hOut = new File ( fileName );
    StreamResult result = new StreamResult ( hOut );

    writeXml ( doc, result );
  }
  
  /**
   * Main XML output method which will write the contents of a Document to an
   * output stream.
   * @param doc org.w3c.dom.Document object.
   * @param result javax.xml.transform.stream.StreamResult object.
   * @throws uregina.engg.co2.xml.XmlManagerException if the XML document could not be written to the output stream.
   */
  private static void writeXml ( Document doc, StreamResult result )
    throws XmlManagerException
  {
    DOMSource source = new DOMSource ( doc );
    
    try
    {
      TransformerFactory tFactory = TransformerFactory.newInstance ();
      Transformer transformer = tFactory.newTransformer ();
      
      transformer.setOutputProperty ( OutputKeys.INDENT, "yes" );
      transformer.transform ( source, result );
    }
    catch ( TransformerFactoryConfigurationError e )
    {
      throw new XmlManagerException ( e.getMessage (), e.getCause () );
    }
    catch ( TransformerConfigurationException e )
    {
      throw new XmlManagerException ( e.getMessage (), e.getCause () );
    }
    catch ( TransformerException e )
    {
      throw new XmlManagerException ( e.getMessage (), e.getCause () );
    }
    catch ( IllegalArgumentException e )
    {
      throw new XmlManagerException ( e.getMessage (), e.getCause () );
    }
  }
  
}
