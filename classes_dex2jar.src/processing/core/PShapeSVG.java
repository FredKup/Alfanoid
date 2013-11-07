package processing.core;

import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.HashMap;
import processing.data.XML;

public class PShapeSVG
  extends PShape
{
  XML element;
  Gradient fillGradient;
  Shader fillGradientPaint;
  String fillName;
  float fillOpacity;
  float opacity;
  Gradient strokeGradient;
  Shader strokeGradientPaint;
  String strokeName;
  float strokeOpacity;
  
  protected PShapeSVG(PShapeSVG paramPShapeSVG, XML paramXML, boolean paramBoolean)
  {
    this.parent = paramPShapeSVG;
    label118:
    String[] arrayOfString;
    boolean bool2;
    if (paramPShapeSVG == null)
    {
      this.stroke = false;
      this.strokeColor = -16777216;
      this.strokeWeight = 1.0F;
      this.strokeCap = 1;
      this.strokeJoin = 8;
      this.strokeGradient = null;
      this.strokeGradientPaint = null;
      this.strokeName = null;
      this.fill = true;
      this.fillColor = -16777216;
      this.fillGradient = null;
      this.fillGradientPaint = null;
      this.fillName = null;
      this.strokeOpacity = 1.0F;
      this.fillOpacity = 1.0F;
      this.opacity = 1.0F;
      this.element = paramXML;
      this.name = paramXML.getString("id");
      if (this.name != null)
      {
        arrayOfString = PApplet.match(this.name, "_x([A-Za-z0-9]{2})_");
        if (arrayOfString != null) {
          break label315;
        }
      }
      boolean bool1 = paramXML.getString("display", "inline").equals("none");
      bool2 = false;
      if (!bool1) {
        break label358;
      }
    }
    for (;;)
    {
      this.visible = bool2;
      String str = paramXML.getString("transform");
      if (str != null) {
        this.matrix = parseTransform(str);
      }
      if (paramBoolean)
      {
        parseColors(paramXML);
        parseChildren(paramXML);
      }
      return;
      this.stroke = paramPShapeSVG.stroke;
      this.strokeColor = paramPShapeSVG.strokeColor;
      this.strokeWeight = paramPShapeSVG.strokeWeight;
      this.strokeCap = paramPShapeSVG.strokeCap;
      this.strokeJoin = paramPShapeSVG.strokeJoin;
      this.strokeGradient = paramPShapeSVG.strokeGradient;
      this.strokeGradientPaint = paramPShapeSVG.strokeGradientPaint;
      this.strokeName = paramPShapeSVG.strokeName;
      this.fill = paramPShapeSVG.fill;
      this.fillColor = paramPShapeSVG.fillColor;
      this.fillGradient = paramPShapeSVG.fillGradient;
      this.fillGradientPaint = paramPShapeSVG.fillGradientPaint;
      this.fillName = paramPShapeSVG.fillName;
      this.opacity = paramPShapeSVG.opacity;
      break;
      label315:
      char c = (char)PApplet.unhex(arrayOfString[1]);
      this.name = this.name.replace(arrayOfString[0], c);
      break label118;
      label358:
      bool2 = true;
    }
  }
  
  public PShapeSVG(XML paramXML)
  {
    this(null, paramXML, true);
    if (!paramXML.getName().equals("svg")) {
      throw new RuntimeException("root is not <svg>, it's <" + paramXML.getName() + ">");
    }
    String str1 = paramXML.getString("viewBox");
    if (str1 != null)
    {
      int[] arrayOfInt = PApplet.parseInt(PApplet.splitTokens(str1));
      this.width = arrayOfInt[2];
      this.height = arrayOfInt[3];
    }
    String str2 = paramXML.getString("width");
    String str3 = paramXML.getString("height");
    if (str2 != null)
    {
      this.width = parseUnitSize(str2);
      this.height = parseUnitSize(str3);
    }
    while ((this.width != 0.0F) && (this.height != 0.0F)) {
      return;
    }
    PGraphics.showWarning("The width and/or height is not readable in the <svg> tag of this file.");
    this.width = 1.0F;
    this.height = 1.0F;
  }
  
  protected static float getFloatWithUnit(XML paramXML, String paramString)
  {
    String str = paramXML.getString(paramString);
    if (str == null) {
      return 0.0F;
    }
    return parseUnitSize(str);
  }
  
  private void parsePathCode(int paramInt)
  {
    if (this.vertexCodeCount == this.vertexCodes.length) {
      this.vertexCodes = PApplet.expand(this.vertexCodes);
    }
    int[] arrayOfInt = this.vertexCodes;
    int i = this.vertexCodeCount;
    this.vertexCodeCount = (i + 1);
    arrayOfInt[i] = paramInt;
  }
  
  private void parsePathCurveto(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    parsePathCode(1);
    parsePathVertex(paramFloat1, paramFloat2);
    parsePathVertex(paramFloat3, paramFloat4);
    parsePathVertex(paramFloat5, paramFloat6);
  }
  
  private void parsePathLineto(float paramFloat1, float paramFloat2)
  {
    parsePathCode(0);
    parsePathVertex(paramFloat1, paramFloat2);
  }
  
  private void parsePathMoveto(float paramFloat1, float paramFloat2)
  {
    if (this.vertexCount > 0) {
      parsePathCode(4);
    }
    parsePathCode(0);
    parsePathVertex(paramFloat1, paramFloat2);
  }
  
  private void parsePathQuadto(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    parsePathCode(2);
    parsePathVertex(paramFloat1, paramFloat2);
    parsePathVertex(paramFloat3, paramFloat4);
  }
  
  private void parsePathVertex(float paramFloat1, float paramFloat2)
  {
    if (this.vertexCount == this.vertices.length)
    {
      int[] arrayOfInt = { this.vertexCount << 1, 2 };
      float[][] arrayOfFloat = (float[][])Array.newInstance(Float.TYPE, arrayOfInt);
      System.arraycopy(this.vertices, 0, arrayOfFloat, 0, this.vertexCount);
      this.vertices = arrayOfFloat;
    }
    this.vertices[this.vertexCount][0] = paramFloat1;
    this.vertices[this.vertexCount][1] = paramFloat2;
    this.vertexCount = (1 + this.vertexCount);
  }
  
  protected static int parseRGB(String paramString)
  {
    int[] arrayOfInt = PApplet.parseInt(PApplet.splitTokens(paramString.substring(1 + paramString.indexOf('('), paramString.indexOf(')')), ", "));
    return arrayOfInt[0] << 16 | arrayOfInt[1] << 8 | arrayOfInt[2];
  }
  
  protected static PMatrix2D parseSingleTransform(String paramString)
  {
    String[] arrayOfString = PApplet.match(paramString, "[,\\s]*(\\w+)\\((.*)\\)");
    if (arrayOfString == null)
    {
      System.err.println("Could not parse transform " + paramString);
      return null;
    }
    float[] arrayOfFloat = PApplet.parseFloat(PApplet.splitTokens(arrayOfString[2], ", "));
    if (arrayOfString[1].equals("matrix")) {
      return new PMatrix2D(arrayOfFloat[0], arrayOfFloat[2], arrayOfFloat[4], arrayOfFloat[1], arrayOfFloat[3], arrayOfFloat[5]);
    }
    if (arrayOfString[1].equals("translate"))
    {
      float f6 = arrayOfFloat[0];
      if (arrayOfFloat.length == 2) {}
      for (float f7 = arrayOfFloat[1];; f7 = arrayOfFloat[0]) {
        return new PMatrix2D(1.0F, 0.0F, f6, 0.0F, 1.0F, f7);
      }
    }
    if (arrayOfString[1].equals("scale"))
    {
      float f4 = arrayOfFloat[0];
      if (arrayOfFloat.length == 2) {}
      for (float f5 = arrayOfFloat[1];; f5 = arrayOfFloat[0]) {
        return new PMatrix2D(f4, 0.0F, 0.0F, 0.0F, f5, 0.0F);
      }
    }
    if (arrayOfString[1].equals("rotate"))
    {
      float f1 = arrayOfFloat[0];
      if (arrayOfFloat.length == 1)
      {
        float f2 = PApplet.cos(f1);
        float f3 = PApplet.sin(f1);
        return new PMatrix2D(f2, -f3, 0.0F, f3, f2, 0.0F);
      }
      if (arrayOfFloat.length == 3)
      {
        PMatrix2D localPMatrix2D = new PMatrix2D(0.0F, 1.0F, arrayOfFloat[1], 1.0F, 0.0F, arrayOfFloat[2]);
        localPMatrix2D.rotate(arrayOfFloat[0]);
        localPMatrix2D.translate(-arrayOfFloat[1], -arrayOfFloat[2]);
        return localPMatrix2D;
      }
    }
    else
    {
      if (arrayOfString[1].equals("skewX")) {
        return new PMatrix2D(1.0F, 0.0F, 1.0F, PApplet.tan(arrayOfFloat[0]), 0.0F, 0.0F);
      }
      if (arrayOfString[1].equals("skewY")) {
        return new PMatrix2D(1.0F, 0.0F, 1.0F, 0.0F, PApplet.tan(arrayOfFloat[0]), 0.0F);
      }
    }
    return null;
  }
  
  protected static HashMap<String, String> parseStyleAttributes(String paramString)
  {
    HashMap localHashMap = new HashMap();
    String[] arrayOfString1 = paramString.split(";");
    for (int i = 0;; i++)
    {
      if (i >= arrayOfString1.length) {
        return localHashMap;
      }
      String[] arrayOfString2 = arrayOfString1[i].split(":");
      localHashMap.put(arrayOfString2[0], arrayOfString2[1]);
    }
  }
  
  protected static PMatrix2D parseTransform(String paramString)
  {
    String str = paramString.trim();
    Object localObject = null;
    int i = 0;
    int j = str.indexOf(')', i);
    if (j == -1) {
      return localObject;
    }
    PMatrix2D localPMatrix2D = parseSingleTransform(str.substring(i, j + 1));
    if (localObject == null) {
      localObject = localPMatrix2D;
    }
    for (;;)
    {
      i = j + 1;
      break;
      ((PMatrix2D)localObject).apply(localPMatrix2D);
    }
  }
  
  protected static float parseUnitSize(String paramString)
  {
    int i = -2 + paramString.length();
    if (paramString.endsWith("pt")) {
      return 1.25F * PApplet.parseFloat(paramString.substring(0, i));
    }
    if (paramString.endsWith("pc")) {
      return 15.0F * PApplet.parseFloat(paramString.substring(0, i));
    }
    if (paramString.endsWith("mm")) {
      return 3.543307F * PApplet.parseFloat(paramString.substring(0, i));
    }
    if (paramString.endsWith("cm")) {
      return 35.433071F * PApplet.parseFloat(paramString.substring(0, i));
    }
    if (paramString.endsWith("in")) {
      return 90.0F * PApplet.parseFloat(paramString.substring(0, i));
    }
    if (paramString.endsWith("px")) {
      return PApplet.parseFloat(paramString.substring(0, i));
    }
    return PApplet.parseFloat(paramString);
  }
  
  protected Shader calcGradientPaint(Gradient paramGradient)
  {
    int[] arrayOfInt = new int[paramGradient.count];
    int i = (int)(255.0F * this.opacity) << 24;
    for (int j = 0;; j++)
    {
      if (j >= paramGradient.count)
      {
        if (!(paramGradient instanceof LinearGradient)) {
          break;
        }
        LinearGradient localLinearGradient = (LinearGradient)paramGradient;
        return new LinearGradient(localLinearGradient.x1, localLinearGradient.y1, localLinearGradient.x2, localLinearGradient.y2, arrayOfInt, localLinearGradient.offset, Shader.TileMode.CLAMP);
      }
      arrayOfInt[j] = (i | 0xFFFFFF & paramGradient.color[j]);
    }
    if ((paramGradient instanceof RadialGradient))
    {
      RadialGradient localRadialGradient = (RadialGradient)paramGradient;
      return new RadialGradient(localRadialGradient.cx, localRadialGradient.cy, localRadialGradient.r, arrayOfInt, localRadialGradient.offset, Shader.TileMode.CLAMP);
    }
    return null;
  }
  
  public PShape getChild(String paramString)
  {
    PShape localPShape = super.getChild(paramString);
    if (localPShape == null) {
      localPShape = super.getChild(paramString.replace(' ', '_'));
    }
    if (localPShape != null)
    {
      localPShape.width = this.width;
      localPShape.height = this.height;
    }
    return localPShape;
  }
  
  protected PShape parseChild(XML paramXML)
  {
    String str = paramXML.getName();
    PShapeSVG localPShapeSVG = null;
    if (str != null)
    {
      if (!str.equals("g")) {
        break label34;
      }
      localPShapeSVG = new PShapeSVG(this, paramXML, true);
    }
    for (;;)
    {
      return localPShapeSVG;
      label34:
      if (str.equals("defs"))
      {
        localPShapeSVG = new PShapeSVG(this, paramXML, true);
      }
      else if (str.equals("line"))
      {
        localPShapeSVG = new PShapeSVG(this, paramXML, true);
        localPShapeSVG.parseLine();
      }
      else if (str.equals("circle"))
      {
        localPShapeSVG = new PShapeSVG(this, paramXML, true);
        localPShapeSVG.parseEllipse(true);
      }
      else if (str.equals("ellipse"))
      {
        localPShapeSVG = new PShapeSVG(this, paramXML, true);
        localPShapeSVG.parseEllipse(false);
      }
      else if (str.equals("rect"))
      {
        localPShapeSVG = new PShapeSVG(this, paramXML, true);
        localPShapeSVG.parseRect();
      }
      else if (str.equals("polygon"))
      {
        localPShapeSVG = new PShapeSVG(this, paramXML, true);
        localPShapeSVG.parsePoly(true);
      }
      else if (str.equals("polyline"))
      {
        localPShapeSVG = new PShapeSVG(this, paramXML, true);
        localPShapeSVG.parsePoly(false);
      }
      else if (str.equals("path"))
      {
        localPShapeSVG = new PShapeSVG(this, paramXML, true);
        localPShapeSVG.parsePath();
      }
      else
      {
        if (str.equals("radialGradient")) {
          return new RadialGradient(this, paramXML);
        }
        if (str.equals("linearGradient")) {
          return new LinearGradient(this, paramXML);
        }
        if (str.equals("font")) {
          return new Font(this, paramXML);
        }
        if (str.equals("metadata")) {
          return null;
        }
        if (str.equals("text"))
        {
          PGraphics.showWarning("Text and fonts in SVG files are not currently supported, convert text to outlines instead.");
          localPShapeSVG = null;
        }
        else if (str.equals("filter"))
        {
          PGraphics.showWarning("Filters are not supported.");
          localPShapeSVG = null;
        }
        else if (str.equals("mask"))
        {
          PGraphics.showWarning("Masks are not supported.");
          localPShapeSVG = null;
        }
        else if (str.equals("pattern"))
        {
          PGraphics.showWarning("Patterns are not supported.");
          localPShapeSVG = null;
        }
        else
        {
          boolean bool1 = str.equals("stop");
          localPShapeSVG = null;
          if (!bool1)
          {
            boolean bool2 = str.equals("sodipodi:namedview");
            localPShapeSVG = null;
            if (!bool2)
            {
              PGraphics.showWarning("Ignoring <" + str + "> tag.");
              localPShapeSVG = null;
            }
          }
        }
      }
    }
  }
  
  protected void parseChildren(XML paramXML)
  {
    XML[] arrayOfXML = paramXML.getChildren();
    this.children = new PShape[arrayOfXML.length];
    this.childCount = 0;
    int i = arrayOfXML.length;
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        this.children = ((PShape[])PApplet.subset(this.children, 0, this.childCount));
        return;
      }
      PShape localPShape = parseChild(arrayOfXML[j]);
      if (localPShape != null) {
        addChild(localPShape);
      }
    }
  }
  
  protected void parseColors(XML paramXML)
  {
    if (paramXML.hasAttribute("opacity")) {
      setOpacity(paramXML.getString("opacity"));
    }
    if (paramXML.hasAttribute("stroke")) {
      setColor(paramXML.getString("stroke"), false);
    }
    if (paramXML.hasAttribute("stroke-opacity")) {
      setStrokeOpacity(paramXML.getString("stroke-opacity"));
    }
    if (paramXML.hasAttribute("stroke-width")) {
      setStrokeWeight(paramXML.getString("stroke-width"));
    }
    if (paramXML.hasAttribute("stroke-linejoin")) {
      setStrokeJoin(paramXML.getString("stroke-linejoin"));
    }
    if (paramXML.hasAttribute("stroke-linecap")) {
      setStrokeCap(paramXML.getString("stroke-linecap"));
    }
    if (paramXML.hasAttribute("fill")) {
      setColor(paramXML.getString("fill"), true);
    }
    if (paramXML.hasAttribute("fill-opacity")) {
      setFillOpacity(paramXML.getString("fill-opacity"));
    }
    String[] arrayOfString1;
    int i;
    if (paramXML.hasAttribute("style"))
    {
      arrayOfString1 = PApplet.splitTokens(paramXML.getString("style"), ";");
      i = 0;
      if (i < arrayOfString1.length) {}
    }
    else
    {
      return;
    }
    String[] arrayOfString2 = PApplet.splitTokens(arrayOfString1[i], ":");
    arrayOfString2[0] = PApplet.trim(arrayOfString2[0]);
    if (arrayOfString2[0].equals("fill")) {
      setColor(arrayOfString2[1], true);
    }
    for (;;)
    {
      i++;
      break;
      if (arrayOfString2[0].equals("fill-opacity")) {
        setFillOpacity(arrayOfString2[1]);
      } else if (arrayOfString2[0].equals("stroke")) {
        setColor(arrayOfString2[1], false);
      } else if (arrayOfString2[0].equals("stroke-width")) {
        setStrokeWeight(arrayOfString2[1]);
      } else if (arrayOfString2[0].equals("stroke-linecap")) {
        setStrokeCap(arrayOfString2[1]);
      } else if (arrayOfString2[0].equals("stroke-linejoin")) {
        setStrokeJoin(arrayOfString2[1]);
      } else if (arrayOfString2[0].equals("stroke-opacity")) {
        setStrokeOpacity(arrayOfString2[1]);
      } else if (arrayOfString2[0].equals("opacity")) {
        setOpacity(arrayOfString2[1]);
      }
    }
  }
  
  protected void parseEllipse(boolean paramBoolean)
  {
    this.kind = 31;
    this.family = 1;
    this.params = new float[4];
    this.params[0] = getFloatWithUnit(this.element, "cx");
    this.params[1] = getFloatWithUnit(this.element, "cy");
    float f2;
    float f1;
    if (paramBoolean)
    {
      f2 = getFloatWithUnit(this.element, "r");
      f1 = f2;
    }
    for (;;)
    {
      float[] arrayOfFloat1 = this.params;
      arrayOfFloat1[0] -= f1;
      float[] arrayOfFloat2 = this.params;
      arrayOfFloat2[1] -= f2;
      this.params[2] = (f1 * 2.0F);
      this.params[3] = (f2 * 2.0F);
      return;
      f1 = getFloatWithUnit(this.element, "rx");
      f2 = getFloatWithUnit(this.element, "ry");
    }
  }
  
  protected void parseLine()
  {
    this.kind = 4;
    this.family = 1;
    float[] arrayOfFloat = new float[4];
    arrayOfFloat[0] = getFloatWithUnit(this.element, "x1");
    arrayOfFloat[1] = getFloatWithUnit(this.element, "y1");
    arrayOfFloat[2] = getFloatWithUnit(this.element, "x2");
    arrayOfFloat[3] = getFloatWithUnit(this.element, "y2");
    this.params = arrayOfFloat;
  }
  
  protected void parsePath()
  {
    this.family = 2;
    this.kind = 0;
    String str1 = this.element.getString("d");
    if ((str1 == null) || (PApplet.trim(str1).length() == 0)) {
      return;
    }
    char[] arrayOfChar = str1.toCharArray();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int j = 0;
    label55:
    int k = arrayOfChar.length;
    String[] arrayOfString;
    float f1;
    float f2;
    int n;
    int i1;
    int i2;
    float f3;
    float f4;
    if (j >= k)
    {
      arrayOfString = PApplet.splitTokens(localStringBuffer.toString(), "| \t\n\r\f ");
      int[] arrayOfInt = { arrayOfString.length, 2 };
      this.vertices = ((float[][])Array.newInstance(Float.TYPE, arrayOfInt));
      this.vertexCodes = new int[arrayOfString.length];
      f1 = 0.0F;
      f2 = 0.0F;
      n = 0;
      i1 = 0;
      i2 = 0;
      f3 = 0.0F;
      f4 = 0.0F;
    }
    for (;;)
    {
      int i3 = arrayOfString.length;
      if (n >= i3) {
        break;
      }
      int i4 = arrayOfString[n].charAt(0);
      if (((i4 >= 48) && (i4 <= 57)) || ((i4 == 45) && (i1 != 0)))
      {
        i4 = i1;
        n--;
      }
      for (;;)
      {
        switch (i4)
        {
        default: 
          String str2 = PApplet.join(PApplet.subset(arrayOfString, 0, n), ",");
          String str3 = PApplet.join(PApplet.subset(arrayOfString, n), ",");
          System.err.println("parsed: " + str2);
          System.err.println("unparsed: " + str3);
          if ((!arrayOfString[n].equals("a")) && (!arrayOfString[n].equals("A"))) {
            break label2122;
          }
          throw new RuntimeException("Sorry, elliptical arc support for SVG files is not yet implemented (See issue 130 for updates)");
          char c = arrayOfChar[j];
          int m;
          if ((c != 'M') && (c != 'm') && (c != 'L') && (c != 'l') && (c != 'H') && (c != 'h') && (c != 'V') && (c != 'v') && (c != 'C') && (c != 'c') && (c != 'S') && (c != 's') && (c != 'Q') && (c != 'q') && (c != 'T') && (c != 't') && (c != 'Z') && (c != 'z'))
          {
            m = 0;
            if (c != ',') {}
          }
          else
          {
            m = 1;
            if (j != 0) {
              localStringBuffer.append("|");
            }
          }
          if ((c == 'Z') || (c == 'z')) {
            m = 0;
          }
          if ((c == '-') && (i == 0) && ((j == 0) || (arrayOfChar[(j - 1)] != 'e'))) {
            localStringBuffer.append("|");
          }
          if (c != ',') {
            localStringBuffer.append(c);
          }
          if ((m != 0) && (c != ',') && (c != '-')) {
            localStringBuffer.append("|");
          }
          i = m;
          j++;
          break label55;
          i1 = i4;
        }
      }
      f1 = PApplet.parseFloat(arrayOfString[(n + 1)]);
      f2 = PApplet.parseFloat(arrayOfString[(n + 2)]);
      f3 = f1;
      f4 = f2;
      parsePathMoveto(f1, f2);
      i1 = 76;
      n += 3;
      continue;
      f1 += PApplet.parseFloat(arrayOfString[(n + 1)]);
      f2 += PApplet.parseFloat(arrayOfString[(n + 2)]);
      parsePathMoveto(f1, f2);
      i1 = 108;
      n += 3;
      continue;
      f1 = PApplet.parseFloat(arrayOfString[(n + 1)]);
      f2 = PApplet.parseFloat(arrayOfString[(n + 2)]);
      parsePathLineto(f1, f2);
      n += 3;
      continue;
      f1 += PApplet.parseFloat(arrayOfString[(n + 1)]);
      f2 += PApplet.parseFloat(arrayOfString[(n + 2)]);
      parsePathLineto(f1, f2);
      n += 3;
      continue;
      f1 = PApplet.parseFloat(arrayOfString[(n + 1)]);
      parsePathLineto(f1, f2);
      n += 2;
      continue;
      f1 += PApplet.parseFloat(arrayOfString[(n + 1)]);
      parsePathLineto(f1, f2);
      n += 2;
      continue;
      f2 = PApplet.parseFloat(arrayOfString[(n + 1)]);
      parsePathLineto(f1, f2);
      n += 2;
      continue;
      f2 += PApplet.parseFloat(arrayOfString[(n + 1)]);
      parsePathLineto(f1, f2);
      n += 2;
      continue;
      float f55 = PApplet.parseFloat(arrayOfString[(n + 1)]);
      float f56 = PApplet.parseFloat(arrayOfString[(n + 2)]);
      float f57 = PApplet.parseFloat(arrayOfString[(n + 3)]);
      float f58 = PApplet.parseFloat(arrayOfString[(n + 4)]);
      float f59 = PApplet.parseFloat(arrayOfString[(n + 5)]);
      float f60 = PApplet.parseFloat(arrayOfString[(n + 6)]);
      parsePathCurveto(f55, f56, f57, f58, f59, f60);
      f1 = f59;
      f2 = f60;
      n += 7;
      i2 = 1;
      continue;
      float f49 = f1 + PApplet.parseFloat(arrayOfString[(n + 1)]);
      float f50 = f2 + PApplet.parseFloat(arrayOfString[(n + 2)]);
      float f51 = f1 + PApplet.parseFloat(arrayOfString[(n + 3)]);
      float f52 = f2 + PApplet.parseFloat(arrayOfString[(n + 4)]);
      float f53 = f1 + PApplet.parseFloat(arrayOfString[(n + 5)]);
      float f54 = f2 + PApplet.parseFloat(arrayOfString[(n + 6)]);
      parsePathCurveto(f49, f50, f51, f52, f53, f54);
      f1 = f53;
      f2 = f54;
      n += 7;
      i2 = 1;
      continue;
      float f43;
      if (i2 == 0) {
        f43 = f1;
      }
      float f40;
      float f42;
      for (float f44 = f2;; f44 = f42 + (f42 - f40))
      {
        float f45 = PApplet.parseFloat(arrayOfString[(n + 1)]);
        float f46 = PApplet.parseFloat(arrayOfString[(n + 2)]);
        float f47 = PApplet.parseFloat(arrayOfString[(n + 3)]);
        float f48 = PApplet.parseFloat(arrayOfString[(n + 4)]);
        parsePathCurveto(f43, f44, f45, f46, f47, f48);
        f1 = f47;
        f2 = f48;
        n += 5;
        i2 = 1;
        break;
        float f39 = this.vertices[(-2 + this.vertexCount)][0];
        f40 = this.vertices[(-2 + this.vertexCount)][1];
        float f41 = this.vertices[(-1 + this.vertexCount)][0];
        f42 = this.vertices[(-1 + this.vertexCount)][1];
        f43 = f41 + (f41 - f39);
      }
      float f33;
      if (i2 == 0) {
        f33 = f1;
      }
      float f30;
      float f32;
      for (float f34 = f2;; f34 = f32 + (f32 - f30))
      {
        float f35 = f1 + PApplet.parseFloat(arrayOfString[(n + 1)]);
        float f36 = f2 + PApplet.parseFloat(arrayOfString[(n + 2)]);
        float f37 = f1 + PApplet.parseFloat(arrayOfString[(n + 3)]);
        float f38 = f2 + PApplet.parseFloat(arrayOfString[(n + 4)]);
        parsePathCurveto(f33, f34, f35, f36, f37, f38);
        f1 = f37;
        f2 = f38;
        n += 5;
        i2 = 1;
        break;
        float f29 = this.vertices[(-2 + this.vertexCount)][0];
        f30 = this.vertices[(-2 + this.vertexCount)][1];
        float f31 = this.vertices[(-1 + this.vertexCount)][0];
        f32 = this.vertices[(-1 + this.vertexCount)][1];
        f33 = f31 + (f31 - f29);
      }
      float f25 = PApplet.parseFloat(arrayOfString[(n + 1)]);
      float f26 = PApplet.parseFloat(arrayOfString[(n + 2)]);
      float f27 = PApplet.parseFloat(arrayOfString[(n + 3)]);
      float f28 = PApplet.parseFloat(arrayOfString[(n + 4)]);
      parsePathQuadto(f25, f26, f27, f28);
      f1 = f27;
      f2 = f28;
      n += 5;
      i2 = 1;
      continue;
      float f21 = f1 + PApplet.parseFloat(arrayOfString[(n + 1)]);
      float f22 = f2 + PApplet.parseFloat(arrayOfString[(n + 2)]);
      float f23 = f1 + PApplet.parseFloat(arrayOfString[(n + 3)]);
      float f24 = f2 + PApplet.parseFloat(arrayOfString[(n + 4)]);
      parsePathQuadto(f21, f22, f23, f24);
      f1 = f23;
      f2 = f24;
      n += 5;
      i2 = 1;
      continue;
      float f17;
      if (i2 == 0) {
        f17 = f1;
      }
      float f14;
      float f16;
      for (float f18 = f2;; f18 = f16 + (f16 - f14))
      {
        float f19 = PApplet.parseFloat(arrayOfString[(n + 1)]);
        float f20 = PApplet.parseFloat(arrayOfString[(n + 2)]);
        parsePathQuadto(f17, f18, f19, f20);
        f1 = f19;
        f2 = f20;
        n += 3;
        i2 = 1;
        break;
        float f13 = this.vertices[(-2 + this.vertexCount)][0];
        f14 = this.vertices[(-2 + this.vertexCount)][1];
        float f15 = this.vertices[(-1 + this.vertexCount)][0];
        f16 = this.vertices[(-1 + this.vertexCount)][1];
        f17 = f15 + (f15 - f13);
      }
      float f9;
      if (i2 == 0) {
        f9 = f1;
      }
      float f6;
      float f8;
      for (float f10 = f2;; f10 = f8 + (f8 - f6))
      {
        float f11 = f1 + PApplet.parseFloat(arrayOfString[(n + 1)]);
        float f12 = f2 + PApplet.parseFloat(arrayOfString[(n + 2)]);
        parsePathQuadto(f9, f10, f11, f12);
        f1 = f11;
        f2 = f12;
        n += 3;
        i2 = 1;
        break;
        float f5 = this.vertices[(-2 + this.vertexCount)][0];
        f6 = this.vertices[(-2 + this.vertexCount)][1];
        float f7 = this.vertices[(-1 + this.vertexCount)][0];
        f8 = this.vertices[(-1 + this.vertexCount)][1];
        f9 = f7 + (f7 - f5);
      }
      f1 = f3;
      f2 = f4;
      this.close = true;
      n++;
    }
    label2122:
    throw new RuntimeException("shape command not handled: " + arrayOfString[n]);
  }
  
  protected void parsePoly(boolean paramBoolean)
  {
    this.family = 2;
    this.close = paramBoolean;
    String str = this.element.getString("points");
    String[] arrayOfString1;
    if (str != null)
    {
      arrayOfString1 = PApplet.splitTokens(str);
      this.vertexCount = arrayOfString1.length;
      int[] arrayOfInt = { this.vertexCount, 2 };
      this.vertices = ((float[][])Array.newInstance(Float.TYPE, arrayOfInt));
    }
    for (int i = 0;; i++)
    {
      if (i >= this.vertexCount) {
        return;
      }
      String[] arrayOfString2 = PApplet.split(arrayOfString1[i], ',');
      this.vertices[i][0] = Float.valueOf(arrayOfString2[0]).floatValue();
      this.vertices[i][1] = Float.valueOf(arrayOfString2[1]).floatValue();
    }
  }
  
  protected void parseRect()
  {
    this.kind = 30;
    this.family = 1;
    float[] arrayOfFloat = new float[4];
    arrayOfFloat[0] = getFloatWithUnit(this.element, "x");
    arrayOfFloat[1] = getFloatWithUnit(this.element, "y");
    arrayOfFloat[2] = getFloatWithUnit(this.element, "width");
    arrayOfFloat[3] = getFloatWithUnit(this.element, "height");
    this.params = arrayOfFloat;
  }
  
  public void print()
  {
    PApplet.println(this.element.toString());
  }
  
  void setColor(String paramString, boolean paramBoolean)
  {
    int i = 0xFF000000 & this.fillColor;
    boolean bool1 = true;
    int j = 0;
    String str = "";
    Gradient localGradient = null;
    Shader localShader = null;
    if (paramString.equals("none")) {
      bool1 = false;
    }
    while (paramBoolean)
    {
      this.fill = bool1;
      this.fillColor = j;
      this.fillName = str;
      this.fillGradient = localGradient;
      this.fillGradientPaint = localShader;
      return;
      if (paramString.equals("black"))
      {
        j = i;
        localGradient = null;
        localShader = null;
      }
      else if (paramString.equals("white"))
      {
        j = i | 0xFFFFFF;
        localGradient = null;
        localShader = null;
      }
      else if (paramString.startsWith("#"))
      {
        if (paramString.length() == 4) {
          paramString = paramString.replaceAll("^#(.)(.)(.)$", "#$1$1$2$2$3$3");
        }
        j = i | 0xFFFFFF & Integer.parseInt(paramString.substring(1), 16);
        localGradient = null;
        localShader = null;
      }
      else if (paramString.startsWith("rgb"))
      {
        j = i | parseRGB(paramString);
        localGradient = null;
        localShader = null;
      }
      else
      {
        boolean bool2 = paramString.startsWith("url(#");
        j = 0;
        localGradient = null;
        localShader = null;
        if (bool2)
        {
          str = paramString.substring(5, -1 + paramString.length());
          PShape localPShape = findChild(str);
          if ((localPShape instanceof Gradient))
          {
            localGradient = (Gradient)localPShape;
            localShader = calcGradientPaint(localGradient);
            j = 0;
          }
          else
          {
            System.err.println("url " + str + " refers to unexpected data: " + localPShape);
            j = 0;
            localGradient = null;
            localShader = null;
          }
        }
      }
    }
    this.stroke = bool1;
    this.strokeColor = j;
    this.strokeName = str;
    this.strokeGradient = localGradient;
    this.strokeGradientPaint = localShader;
  }
  
  void setFillOpacity(String paramString)
  {
    this.fillOpacity = PApplet.parseFloat(paramString);
    this.fillColor = ((int)(255.0F * this.fillOpacity) << 24 | 0xFFFFFF & this.fillColor);
  }
  
  void setOpacity(String paramString)
  {
    this.opacity = PApplet.parseFloat(paramString);
    this.strokeColor = ((int)(255.0F * this.opacity) << 24 | 0xFFFFFF & this.strokeColor);
    this.fillColor = ((int)(255.0F * this.opacity) << 24 | 0xFFFFFF & this.fillColor);
  }
  
  void setStrokeCap(String paramString)
  {
    if (!paramString.equals("inherit"))
    {
      if (!paramString.equals("butt")) {
        break label26;
      }
      this.strokeCap = 1;
    }
    label26:
    do
    {
      return;
      if (paramString.equals("round"))
      {
        this.strokeCap = 2;
        return;
      }
    } while (!paramString.equals("square"));
    this.strokeCap = 4;
  }
  
  void setStrokeJoin(String paramString)
  {
    if (!paramString.equals("inherit"))
    {
      if (!paramString.equals("miter")) {
        break label27;
      }
      this.strokeJoin = 8;
    }
    label27:
    do
    {
      return;
      if (paramString.equals("round"))
      {
        this.strokeJoin = 2;
        return;
      }
    } while (!paramString.equals("bevel"));
    this.strokeJoin = 32;
  }
  
  void setStrokeOpacity(String paramString)
  {
    this.strokeOpacity = PApplet.parseFloat(paramString);
    this.strokeColor = ((int)(255.0F * this.strokeOpacity) << 24 | 0xFFFFFF & this.strokeColor);
  }
  
  void setStrokeWeight(String paramString)
  {
    this.strokeWeight = parseUnitSize(paramString);
  }
  
  protected void styles(PGraphics paramPGraphics)
  {
    super.styles(paramPGraphics);
    if ((paramPGraphics instanceof PGraphicsAndroid2D))
    {
      PGraphicsAndroid2D localPGraphicsAndroid2D = (PGraphicsAndroid2D)paramPGraphics;
      if (this.strokeGradient != null) {
        localPGraphicsAndroid2D.strokePaint.setShader(this.strokeGradientPaint);
      }
      if (this.fillGradient != null) {
        localPGraphicsAndroid2D.fillPaint.setShader(this.fillGradientPaint);
      }
    }
  }
  
  public class Font
    extends PShapeSVG
  {
    public PShapeSVG.FontFace face;
    public int glyphCount;
    public PShapeSVG.FontGlyph[] glyphs;
    int horizAdvX;
    public PShapeSVG.FontGlyph missingGlyph;
    public HashMap<String, PShapeSVG.FontGlyph> namedGlyphs;
    public HashMap<Character, PShapeSVG.FontGlyph> unicodeGlyphs;
    
    public Font(PShapeSVG paramPShapeSVG, XML paramXML)
    {
      super(paramXML, false);
      XML[] arrayOfXML = paramXML.getChildren();
      this.horizAdvX = paramXML.getInt("horiz-adv-x", 0);
      this.namedGlyphs = new HashMap();
      this.unicodeGlyphs = new HashMap();
      this.glyphCount = 0;
      this.glyphs = new PShapeSVG.FontGlyph[arrayOfXML.length];
      int i = 0;
      if (i >= arrayOfXML.length) {
        return;
      }
      String str = arrayOfXML[i].getName();
      XML localXML = arrayOfXML[i];
      if (str != null)
      {
        if (!str.equals("glyph")) {
          break label218;
        }
        PShapeSVG.FontGlyph localFontGlyph = new PShapeSVG.FontGlyph(this, this, localXML, this);
        if (localFontGlyph.isLegit())
        {
          if (localFontGlyph.name != null) {
            this.namedGlyphs.put(localFontGlyph.name, localFontGlyph);
          }
          if (localFontGlyph.unicode != 0) {
            this.unicodeGlyphs.put(new Character(localFontGlyph.unicode), localFontGlyph);
          }
        }
        PShapeSVG.FontGlyph[] arrayOfFontGlyph = this.glyphs;
        int j = this.glyphCount;
        this.glyphCount = (j + 1);
        arrayOfFontGlyph[j] = localFontGlyph;
      }
      for (;;)
      {
        i++;
        break;
        label218:
        if (str.equals("missing-glyph")) {
          this.missingGlyph = new PShapeSVG.FontGlyph(this, this, localXML, this);
        } else if (str.equals("font-face")) {
          this.face = new PShapeSVG.FontFace(this, this, localXML);
        } else {
          System.err.println("Ignoring " + str + " inside <font>");
        }
      }
    }
    
    public void drawChar(PGraphics paramPGraphics, char paramChar, float paramFloat1, float paramFloat2, float paramFloat3)
    {
      paramPGraphics.pushMatrix();
      float f = paramFloat3 / this.face.unitsPerEm;
      paramPGraphics.translate(paramFloat1, paramFloat2);
      paramPGraphics.scale(f, -f);
      PShapeSVG.FontGlyph localFontGlyph = (PShapeSVG.FontGlyph)this.unicodeGlyphs.get(new Character(paramChar));
      if (localFontGlyph != null) {
        paramPGraphics.shape(localFontGlyph);
      }
      paramPGraphics.popMatrix();
    }
    
    protected void drawShape() {}
    
    public void drawString(PGraphics paramPGraphics, String paramString, float paramFloat1, float paramFloat2, float paramFloat3)
    {
      paramPGraphics.pushMatrix();
      float f = paramFloat3 / this.face.unitsPerEm;
      paramPGraphics.translate(paramFloat1, paramFloat2);
      paramPGraphics.scale(f, -f);
      char[] arrayOfChar = paramString.toCharArray();
      int i = 0;
      if (i >= arrayOfChar.length)
      {
        paramPGraphics.popMatrix();
        return;
      }
      PShapeSVG.FontGlyph localFontGlyph = (PShapeSVG.FontGlyph)this.unicodeGlyphs.get(new Character(arrayOfChar[i]));
      if (localFontGlyph != null)
      {
        localFontGlyph.draw(paramPGraphics);
        paramPGraphics.translate(localFontGlyph.horizAdvX, 0.0F);
      }
      for (;;)
      {
        i++;
        break;
        System.err.println("'" + arrayOfChar[i] + "' not available.");
      }
    }
    
    public float textWidth(String paramString, float paramFloat)
    {
      float f = 0.0F;
      char[] arrayOfChar = paramString.toCharArray();
      for (int i = 0;; i++)
      {
        if (i >= arrayOfChar.length) {
          return f * paramFloat;
        }
        PShapeSVG.FontGlyph localFontGlyph = (PShapeSVG.FontGlyph)this.unicodeGlyphs.get(new Character(arrayOfChar[i]));
        if (localFontGlyph != null) {
          f += localFontGlyph.horizAdvX / this.face.unitsPerEm;
        }
      }
    }
  }
  
  class FontFace
    extends PShapeSVG
  {
    int ascent;
    int[] bbox;
    int descent;
    String fontFamily;
    String fontStretch;
    int fontWeight;
    int horizOriginX;
    int horizOriginY;
    int[] panose1;
    int underlinePosition;
    int underlineThickness;
    int unitsPerEm;
    int vertAdvY;
    int vertOriginX;
    int vertOriginY;
    
    public FontFace(PShapeSVG paramPShapeSVG, XML paramXML)
    {
      super(paramXML, true);
      this.unitsPerEm = paramXML.getInt("units-per-em", 1000);
    }
    
    protected void drawShape() {}
  }
  
  public class FontGlyph
    extends PShapeSVG
  {
    int horizAdvX;
    public String name;
    char unicode;
    
    public FontGlyph(PShapeSVG paramPShapeSVG, XML paramXML, PShapeSVG.Font paramFont)
    {
      super(paramXML, true);
      super.parsePath();
      this.name = paramXML.getString("glyph-name");
      String str = paramXML.getString("unicode");
      this.unicode = '\000';
      if (str != null)
      {
        if (str.length() != 1) {
          break label83;
        }
        this.unicode = str.charAt(0);
      }
      while (paramXML.hasAttribute("horiz-adv-x"))
      {
        this.horizAdvX = paramXML.getInt("horiz-adv-x");
        return;
        label83:
        System.err.println("unicode for " + this.name + " is more than one char: " + str);
      }
      this.horizAdvX = paramFont.horizAdvX;
    }
    
    protected boolean isLegit()
    {
      return this.vertexCount != 0;
    }
  }
  
  static class Gradient
    extends PShapeSVG
  {
    int[] color;
    int count;
    float[] offset;
    Matrix transform;
    
    public Gradient(PShapeSVG paramPShapeSVG, XML paramXML)
    {
      super(paramXML, true);
      XML[] arrayOfXML = paramXML.getChildren();
      this.offset = new float[arrayOfXML.length];
      this.color = new int[arrayOfXML.length];
      for (int i = 0;; i++)
      {
        if (i >= arrayOfXML.length)
        {
          this.offset = PApplet.subset(this.offset, 0, this.count);
          this.color = PApplet.subset(this.color, 0, this.count);
          return;
        }
        XML localXML = arrayOfXML[i];
        if (localXML.getName().equals("stop"))
        {
          String str1 = localXML.getString("offset");
          float f = 1.0F;
          if (str1.endsWith("%"))
          {
            f = 100.0F;
            str1 = str1.substring(0, -1 + str1.length());
          }
          this.offset[this.count] = (PApplet.parseFloat(str1) / f);
          HashMap localHashMap = parseStyleAttributes(localXML.getString("style"));
          String str2 = (String)localHashMap.get("stop-color");
          if (str2 == null) {
            str2 = "#000000";
          }
          String str3 = (String)localHashMap.get("stop-opacity");
          if (str3 == null) {
            str3 = "1";
          }
          int j = (int)(255.0F * PApplet.parseFloat(str3));
          this.color[this.count] = (j << 24 | Integer.parseInt(str2.substring(1), 16));
          this.count = (1 + this.count);
        }
      }
    }
  }
  
  class LinearGradient
    extends PShapeSVG.Gradient
  {
    float x1;
    float x2;
    float y1;
    float y2;
    
    public LinearGradient(PShapeSVG paramPShapeSVG, XML paramXML)
    {
      super(paramXML);
      this.x1 = getFloatWithUnit(paramXML, "x1");
      this.y1 = getFloatWithUnit(paramXML, "y1");
      this.x2 = getFloatWithUnit(paramXML, "x2");
      this.y2 = getFloatWithUnit(paramXML, "y2");
      String str = paramXML.getString("gradientTransform");
      if (str != null)
      {
        float[] arrayOfFloat1 = parseTransform(str).get(null);
        this.transform = new Matrix();
        Matrix localMatrix = this.transform;
        float[] arrayOfFloat2 = new float[9];
        arrayOfFloat2[0] = arrayOfFloat1[0];
        arrayOfFloat2[1] = arrayOfFloat1[1];
        arrayOfFloat2[2] = arrayOfFloat1[2];
        arrayOfFloat2[3] = arrayOfFloat1[3];
        arrayOfFloat2[4] = arrayOfFloat1[4];
        arrayOfFloat2[5] = arrayOfFloat1[5];
        arrayOfFloat2[6] = 0.0F;
        arrayOfFloat2[7] = 0.0F;
        arrayOfFloat2[8] = 1.0F;
        localMatrix.setValues(arrayOfFloat2);
        float[] arrayOfFloat3 = new float[2];
        arrayOfFloat3[0] = this.x1;
        arrayOfFloat3[1] = this.y1;
        float[] arrayOfFloat4 = new float[2];
        arrayOfFloat4[0] = this.x2;
        arrayOfFloat4[1] = this.y2;
        this.transform.mapPoints(arrayOfFloat3);
        this.transform.mapPoints(arrayOfFloat4);
        this.x1 = arrayOfFloat3[0];
        this.y1 = arrayOfFloat3[1];
        this.x2 = arrayOfFloat4[0];
        this.y2 = arrayOfFloat4[1];
      }
    }
  }
  
  class RadialGradient
    extends PShapeSVG.Gradient
  {
    float cx;
    float cy;
    float r;
    
    public RadialGradient(PShapeSVG paramPShapeSVG, XML paramXML)
    {
      super(paramXML);
      this.cx = getFloatWithUnit(paramXML, "cx");
      this.cy = getFloatWithUnit(paramXML, "cy");
      this.r = getFloatWithUnit(paramXML, "r");
      String str = paramXML.getString("gradientTransform");
      if (str != null)
      {
        float[] arrayOfFloat1 = parseTransform(str).get(null);
        this.transform = new Matrix();
        Matrix localMatrix = this.transform;
        float[] arrayOfFloat2 = new float[9];
        arrayOfFloat2[0] = arrayOfFloat1[0];
        arrayOfFloat2[1] = arrayOfFloat1[1];
        arrayOfFloat2[2] = arrayOfFloat1[2];
        arrayOfFloat2[3] = arrayOfFloat1[3];
        arrayOfFloat2[4] = arrayOfFloat1[4];
        arrayOfFloat2[5] = arrayOfFloat1[5];
        arrayOfFloat2[6] = 0.0F;
        arrayOfFloat2[7] = 0.0F;
        arrayOfFloat2[8] = 1.0F;
        localMatrix.setValues(arrayOfFloat2);
        float[] arrayOfFloat3 = new float[2];
        arrayOfFloat3[0] = this.cx;
        arrayOfFloat3[1] = this.cy;
        float[] arrayOfFloat4 = new float[2];
        arrayOfFloat4[0] = (this.cx + this.r);
        arrayOfFloat4[1] = this.cy;
        this.transform.mapPoints(arrayOfFloat3);
        this.transform.mapPoints(arrayOfFloat4);
        this.cx = arrayOfFloat3[0];
        this.cy = arrayOfFloat3[1];
        this.r = (arrayOfFloat4[0] - arrayOfFloat3[0]);
      }
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PShapeSVG
 * JD-Core Version:    0.7.0.1
 */