package processing.data;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import processing.core.PApplet;

public class XML
  implements Serializable
{
  protected XML[] children;
  protected Node node;
  protected XML parent;
  
  protected XML() {}
  
  public XML(File paramFile)
    throws IOException, ParserConfigurationException, SAXException
  {
    this(paramFile, null);
  }
  
  public XML(File paramFile, String paramString)
    throws IOException, ParserConfigurationException, SAXException
  {
    this(PApplet.createReader(paramFile), paramString);
  }
  
  public XML(InputStream paramInputStream)
    throws IOException, ParserConfigurationException, SAXException
  {
    this(paramInputStream, null);
  }
  
  public XML(InputStream paramInputStream, String paramString)
    throws IOException, ParserConfigurationException, SAXException
  {
    this(PApplet.createReader(paramInputStream), paramString);
  }
  
  protected XML(Reader paramReader, String paramString)
    throws IOException, ParserConfigurationException, SAXException
  {
    DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
    try
    {
      localDocumentBuilderFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", Boolean.valueOf(false));
      label18:
      localDocumentBuilderFactory.setExpandEntityReferences(false);
      this.node = localDocumentBuilderFactory.newDocumentBuilder().parse(new InputSource(paramReader)).getDocumentElement();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      break label18;
    }
  }
  
  public XML(String paramString)
    throws ParserConfigurationException
  {
    this.node = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument().createElement(paramString);
    this.parent = null;
  }
  
  protected XML(XML paramXML, Node paramNode)
  {
    this.node = paramNode;
    this.parent = paramXML;
  }
  
  public static XML parse(String paramString)
    throws IOException, ParserConfigurationException, SAXException
  {
    return parse(paramString, null);
  }
  
  public static XML parse(String paramString1, String paramString2)
    throws IOException, ParserConfigurationException, SAXException
  {
    return new XML(new StringReader(paramString1), null);
  }
  
  public XML addChild(String paramString)
  {
    return appendChild(this.node.getOwnerDocument().createElement(paramString));
  }
  
  public XML addChild(XML paramXML)
  {
    return appendChild(this.node.getOwnerDocument().importNode((Node)paramXML.getNative(), true));
  }
  
  protected XML appendChild(Node paramNode)
  {
    this.node.appendChild(paramNode);
    XML localXML = new XML(this, paramNode);
    if (this.children != null) {
      this.children = ((XML[])PApplet.concat(this.children, new XML[] { localXML }));
    }
    return localXML;
  }
  
  protected void checkChildren()
  {
    NodeList localNodeList;
    int i;
    if (this.children == null)
    {
      localNodeList = this.node.getChildNodes();
      i = localNodeList.getLength();
      this.children = new XML[i];
    }
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return;
      }
      this.children[j] = new XML(this, localNodeList.item(j));
    }
  }
  
  /* Error */
  public String format(int paramInt)
  {
    // Byte code:
    //   0: invokestatic 164	javax/xml/transform/TransformerFactory:newInstance	()Ljavax/xml/transform/TransformerFactory;
    //   3: astore_3
    //   4: iconst_0
    //   5: istore 4
    //   7: iload_1
    //   8: iconst_m1
    //   9: if_icmpeq +13 -> 22
    //   12: aload_3
    //   13: ldc 166
    //   15: iload_1
    //   16: invokestatic 171	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   19: invokevirtual 172	javax/xml/transform/TransformerFactory:setAttribute	(Ljava/lang/String;Ljava/lang/Object;)V
    //   22: aload_3
    //   23: invokevirtual 176	javax/xml/transform/TransformerFactory:newTransformer	()Ljavax/xml/transform/Transformer;
    //   26: astore 5
    //   28: iload_1
    //   29: iconst_m1
    //   30: if_icmpeq +10 -> 40
    //   33: aload_0
    //   34: getfield 100	processing/data/XML:parent	Lprocessing/data/XML;
    //   37: ifnonnull +178 -> 215
    //   40: aload 5
    //   42: ldc 178
    //   44: ldc 180
    //   46: invokevirtual 186	javax/xml/transform/Transformer:setOutputProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   49: aload 5
    //   51: ldc 188
    //   53: ldc 190
    //   55: invokevirtual 186	javax/xml/transform/Transformer:setOutputProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   58: iload 4
    //   60: ifeq +14 -> 74
    //   63: aload 5
    //   65: ldc 192
    //   67: iload_1
    //   68: invokestatic 196	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   71: invokevirtual 186	javax/xml/transform/Transformer:setOutputProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   74: aload 5
    //   76: ldc 198
    //   78: ldc 200
    //   80: invokevirtual 186	javax/xml/transform/Transformer:setOutputProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   83: aload 5
    //   85: ldc 202
    //   87: ldc 180
    //   89: invokevirtual 186	javax/xml/transform/Transformer:setOutputProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   92: ldc 204
    //   94: invokestatic 210	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   97: astore 6
    //   99: new 212	java/io/StringWriter
    //   102: dup
    //   103: invokespecial 213	java/io/StringWriter:<init>	()V
    //   106: astore 7
    //   108: new 215	javax/xml/transform/stream/StreamResult
    //   111: dup
    //   112: aload 7
    //   114: invokespecial 218	javax/xml/transform/stream/StreamResult:<init>	(Ljava/io/Writer;)V
    //   117: astore 8
    //   119: aload 5
    //   121: new 220	javax/xml/transform/dom/DOMSource
    //   124: dup
    //   125: aload_0
    //   126: getfield 89	processing/data/XML:node	Lorg/w3c/dom/Node;
    //   129: invokespecial 223	javax/xml/transform/dom/DOMSource:<init>	(Lorg/w3c/dom/Node;)V
    //   132: aload 8
    //   134: invokevirtual 227	javax/xml/transform/Transformer:transform	(Ljavax/xml/transform/Source;Ljavax/xml/transform/Result;)V
    //   137: aload 7
    //   139: invokevirtual 231	java/io/StringWriter:toString	()Ljava/lang/String;
    //   142: aload 6
    //   144: invokestatic 235	processing/core/PApplet:split	(Ljava/lang/String;Ljava/lang/String;)[Ljava/lang/String;
    //   147: astore 9
    //   149: aload 9
    //   151: iconst_0
    //   152: aaload
    //   153: ldc 237
    //   155: invokevirtual 241	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   158: ifeq +36 -> 194
    //   161: iconst_2
    //   162: aload 9
    //   164: iconst_0
    //   165: aaload
    //   166: ldc 243
    //   168: invokevirtual 247	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   171: iadd
    //   172: istore 14
    //   174: aload 9
    //   176: iconst_0
    //   177: aaload
    //   178: invokevirtual 250	java/lang/String:length	()I
    //   181: iload 14
    //   183: if_icmpne +52 -> 235
    //   186: aload 9
    //   188: iconst_1
    //   189: invokestatic 254	processing/core/PApplet:subset	([Ljava/lang/String;I)[Ljava/lang/String;
    //   192: astore 9
    //   194: aload 9
    //   196: invokestatic 258	processing/core/PApplet:trim	([Ljava/lang/String;)[Ljava/lang/String;
    //   199: ldc_w 260
    //   202: invokestatic 264	processing/core/PApplet:join	([Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   205: astore 10
    //   207: iload_1
    //   208: iconst_m1
    //   209: if_icmpne +42 -> 251
    //   212: aload 10
    //   214: areturn
    //   215: aload 5
    //   217: ldc 178
    //   219: ldc_w 266
    //   222: invokevirtual 186	javax/xml/transform/Transformer:setOutputProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   225: goto -176 -> 49
    //   228: astore_2
    //   229: aload_2
    //   230: invokevirtual 269	java/lang/Exception:printStackTrace	()V
    //   233: aconst_null
    //   234: areturn
    //   235: aload 9
    //   237: iconst_0
    //   238: aload 9
    //   240: iconst_0
    //   241: aaload
    //   242: iload 14
    //   244: invokevirtual 272	java/lang/String:substring	(I)Ljava/lang/String;
    //   247: aastore
    //   248: goto -54 -> 194
    //   251: new 212	java/io/StringWriter
    //   254: dup
    //   255: invokespecial 213	java/io/StringWriter:<init>	()V
    //   258: astore 11
    //   260: new 215	javax/xml/transform/stream/StreamResult
    //   263: dup
    //   264: aload 11
    //   266: invokespecial 218	javax/xml/transform/stream/StreamResult:<init>	(Ljava/io/Writer;)V
    //   269: astore 12
    //   271: aload 5
    //   273: new 274	javax/xml/transform/stream/StreamSource
    //   276: dup
    //   277: new 107	java/io/StringReader
    //   280: dup
    //   281: aload 10
    //   283: invokespecial 109	java/io/StringReader:<init>	(Ljava/lang/String;)V
    //   286: invokespecial 275	javax/xml/transform/stream/StreamSource:<init>	(Ljava/io/Reader;)V
    //   289: aload 12
    //   291: invokevirtual 227	javax/xml/transform/Transformer:transform	(Ljavax/xml/transform/Source;Ljavax/xml/transform/Result;)V
    //   294: new 277	java/lang/StringBuilder
    //   297: dup
    //   298: ldc_w 279
    //   301: invokespecial 280	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   304: aload 6
    //   306: invokevirtual 284	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   309: aload 11
    //   311: invokevirtual 231	java/io/StringWriter:toString	()Ljava/lang/String;
    //   314: invokevirtual 284	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   317: invokevirtual 285	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   320: astore 13
    //   322: aload 13
    //   324: areturn
    //   325: astore 15
    //   327: iconst_1
    //   328: istore 4
    //   330: goto -308 -> 22
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	333	0	this	XML
    //   0	333	1	paramInt	int
    //   228	2	2	localException	java.lang.Exception
    //   3	20	3	localTransformerFactory	javax.xml.transform.TransformerFactory
    //   5	324	4	i	int
    //   26	246	5	localTransformer	javax.xml.transform.Transformer
    //   97	208	6	str1	String
    //   106	32	7	localStringWriter1	java.io.StringWriter
    //   117	16	8	localStreamResult1	javax.xml.transform.stream.StreamResult
    //   147	92	9	arrayOfString	String[]
    //   205	77	10	str2	String
    //   258	52	11	localStringWriter2	java.io.StringWriter
    //   269	21	12	localStreamResult2	javax.xml.transform.stream.StreamResult
    //   320	3	13	str3	String
    //   172	71	14	j	int
    //   325	1	15	localIllegalArgumentException	IllegalArgumentException
    // Exception table:
    //   from	to	target	type
    //   0	4	228	java/lang/Exception
    //   12	22	228	java/lang/Exception
    //   22	28	228	java/lang/Exception
    //   33	40	228	java/lang/Exception
    //   40	49	228	java/lang/Exception
    //   49	58	228	java/lang/Exception
    //   63	74	228	java/lang/Exception
    //   74	194	228	java/lang/Exception
    //   194	207	228	java/lang/Exception
    //   215	225	228	java/lang/Exception
    //   235	248	228	java/lang/Exception
    //   251	322	228	java/lang/Exception
    //   12	22	325	java/lang/IllegalArgumentException
  }
  
  public int getAttributeCount()
  {
    return this.node.getAttributes().getLength();
  }
  
  public XML getChild(int paramInt)
  {
    checkChildren();
    return this.children[paramInt];
  }
  
  public XML getChild(String paramString)
  {
    if ((paramString.length() > 0) && (paramString.charAt(0) == '/')) {
      throw new IllegalArgumentException("getChild() should not begin with a slash");
    }
    XML localXML;
    if (paramString.indexOf('/') != -1)
    {
      localXML = getChildRecursive(PApplet.split(paramString, '/'), 0);
      return localXML;
    }
    int i = getChildCount();
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return null;
      }
      localXML = getChild(j);
      String str = localXML.getName();
      if ((str != null) && (str.equals(paramString))) {
        break;
      }
    }
  }
  
  public int getChildCount()
  {
    checkChildren();
    return this.children.length;
  }
  
  protected XML getChildRecursive(String[] paramArrayOfString, int paramInt)
  {
    XML localXML;
    if (Character.isDigit(paramArrayOfString[paramInt].charAt(0)))
    {
      localXML = getChild(Integer.parseInt(paramArrayOfString[paramInt]));
      if (paramInt == -1 + paramArrayOfString.length) {
        return localXML;
      }
      return localXML.getChildRecursive(paramArrayOfString, paramInt + 1);
    }
    int i = getChildCount();
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return null;
      }
      localXML = getChild(j);
      String str = localXML.getName();
      if ((str != null) && (str.equals(paramArrayOfString[paramInt])))
      {
        if (paramInt == -1 + paramArrayOfString.length) {
          break;
        }
        return localXML.getChildRecursive(paramArrayOfString, paramInt + 1);
      }
    }
  }
  
  public XML[] getChildren()
  {
    checkChildren();
    return this.children;
  }
  
  public XML[] getChildren(String paramString)
  {
    if ((paramString.length() > 0) && (paramString.charAt(0) == '/')) {
      throw new IllegalArgumentException("getChildren() should not begin with a slash");
    }
    if (paramString.indexOf('/') != -1) {
      return getChildrenRecursive(PApplet.split(paramString, '/'), 0);
    }
    if (Character.isDigit(paramString.charAt(0)))
    {
      XML[] arrayOfXML2 = new XML[1];
      arrayOfXML2[0] = getChild(Integer.parseInt(paramString));
      return arrayOfXML2;
    }
    int i = getChildCount();
    XML[] arrayOfXML1 = new XML[i];
    int j = 0;
    int k = 0;
    if (j >= i) {
      return (XML[])PApplet.subset(arrayOfXML1, 0, k);
    }
    XML localXML = getChild(j);
    String str = localXML.getName();
    int m;
    if ((str != null) && (str.equals(paramString)))
    {
      m = k + 1;
      arrayOfXML1[k] = localXML;
    }
    for (;;)
    {
      j++;
      k = m;
      break;
      m = k;
    }
  }
  
  protected XML[] getChildrenRecursive(String[] paramArrayOfString, int paramInt)
  {
    XML[] arrayOfXML2;
    if (paramInt == -1 + paramArrayOfString.length) {
      arrayOfXML2 = getChildren(paramArrayOfString[paramInt]);
    }
    for (;;)
    {
      return arrayOfXML2;
      XML[] arrayOfXML1 = getChildren(paramArrayOfString[paramInt]);
      arrayOfXML2 = new XML[0];
      for (int i = 0; i < arrayOfXML1.length; i++) {
        arrayOfXML2 = (XML[])PApplet.concat(arrayOfXML2, arrayOfXML1[i].getChildrenRecursive(paramArrayOfString, paramInt + 1));
      }
    }
  }
  
  public String getContent()
  {
    return this.node.getTextContent();
  }
  
  public double getDouble(String paramString)
  {
    return getDouble(paramString, 0.0D);
  }
  
  public double getDouble(String paramString, double paramDouble)
  {
    String str = getString(paramString);
    if (str == null) {
      return paramDouble;
    }
    return Double.parseDouble(str);
  }
  
  public float getFloat(String paramString)
  {
    return getFloat(paramString, 0.0F);
  }
  
  public float getFloat(String paramString, float paramFloat)
  {
    String str = getString(paramString);
    if (str == null) {
      return paramFloat;
    }
    return Float.parseFloat(str);
  }
  
  public int getInt(String paramString)
  {
    return getInt(paramString, 0);
  }
  
  public int getInt(String paramString, int paramInt)
  {
    String str = getString(paramString);
    if (str == null) {
      return paramInt;
    }
    return Integer.parseInt(str);
  }
  
  public String getLocalName()
  {
    return this.node.getLocalName();
  }
  
  public long getLong(String paramString, long paramLong)
  {
    String str = getString(paramString);
    if (str == null) {
      return paramLong;
    }
    return Long.parseLong(str);
  }
  
  public String getName()
  {
    return this.node.getNodeName();
  }
  
  protected Object getNative()
  {
    return this.node;
  }
  
  public XML getParent()
  {
    return this.parent;
  }
  
  public String getString(String paramString)
  {
    return getString(paramString, null);
  }
  
  public String getString(String paramString1, String paramString2)
  {
    Node localNode = this.node.getAttributes().getNamedItem(paramString1);
    if (localNode == null) {
      return paramString2;
    }
    return localNode.getNodeValue();
  }
  
  public boolean hasAttribute(String paramString)
  {
    return this.node.getAttributes().getNamedItem(paramString) != null;
  }
  
  public boolean hasChildren()
  {
    checkChildren();
    return this.children.length > 0;
  }
  
  public String[] listAttributes()
  {
    NamedNodeMap localNamedNodeMap = this.node.getAttributes();
    String[] arrayOfString = new String[localNamedNodeMap.getLength()];
    for (int i = 0;; i++)
    {
      if (i >= arrayOfString.length) {
        return arrayOfString;
      }
      arrayOfString[i] = localNamedNodeMap.item(i).getNodeName();
    }
  }
  
  public String[] listChildren()
  {
    checkChildren();
    String[] arrayOfString = new String[this.children.length];
    for (int i = 0;; i++)
    {
      if (i >= this.children.length) {
        return arrayOfString;
      }
      arrayOfString[i] = this.children[i].getName();
    }
  }
  
  public void removeChild(XML paramXML)
  {
    this.node.removeChild(paramXML.node);
    this.children = null;
  }
  
  public boolean save(File paramFile, String paramString)
  {
    return save(PApplet.createWriter(paramFile));
  }
  
  protected boolean save(OutputStream paramOutputStream)
  {
    return save(PApplet.createWriter(paramOutputStream));
  }
  
  public boolean save(PrintWriter paramPrintWriter)
  {
    paramPrintWriter.print(format(2));
    paramPrintWriter.flush();
    return true;
  }
  
  public void setContent(String paramString)
  {
    this.node.setTextContent(paramString);
  }
  
  public void setDouble(String paramString, double paramDouble)
  {
    setString(paramString, String.valueOf(paramDouble));
  }
  
  public void setFloat(String paramString, float paramFloat)
  {
    setString(paramString, String.valueOf(paramFloat));
  }
  
  public void setInt(String paramString, int paramInt)
  {
    setString(paramString, String.valueOf(paramInt));
  }
  
  public void setLong(String paramString, long paramLong)
  {
    setString(paramString, String.valueOf(paramLong));
  }
  
  public void setName(String paramString)
  {
    this.node = this.node.getOwnerDocument().renameNode(this.node, null, paramString);
  }
  
  public void setString(String paramString1, String paramString2)
  {
    ((Element)this.node).setAttribute(paramString1, paramString2);
  }
  
  public String toString()
  {
    return format(2);
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.data.XML
 * JD-Core Version:    0.7.0.1
 */