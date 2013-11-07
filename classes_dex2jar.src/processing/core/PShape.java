package processing.core;

import java.lang.reflect.Array;
import java.util.HashMap;

public class PShape
  implements PConstants
{
  public static final int GEOMETRY = 3;
  public static final String INSIDE_BEGIN_END_ERROR = "%1$s can only be called outside beginShape() and endShape()";
  public static final String OUTSIDE_BEGIN_END_ERROR = "%1$s can only be called between beginShape() and endShape()";
  public static final int PATH = 2;
  public static final int PRIMITIVE = 1;
  protected int ambientColor;
  protected float calcA;
  protected int calcAi;
  protected boolean calcAlpha;
  protected float calcB;
  protected int calcBi;
  protected int calcColor;
  protected float calcG;
  protected int calcGi;
  protected float calcR;
  protected int calcRi;
  protected int childCount;
  protected PShape[] children;
  protected boolean close;
  public int colorMode;
  public float colorModeA;
  boolean colorModeDefault;
  boolean colorModeScale;
  public float colorModeX;
  public float colorModeY;
  public float colorModeZ;
  public float depth;
  protected int emissiveColor;
  protected int family;
  protected boolean fill;
  protected int fillColor;
  public float height;
  protected PImage image;
  protected boolean is3D = false;
  protected int kind;
  protected PMatrix matrix;
  protected String name;
  protected HashMap<String, PShape> nameTable;
  protected boolean openContour = false;
  protected boolean openShape = false;
  protected float[] params;
  protected PShape parent;
  protected boolean setAmbient;
  protected float shininess;
  protected int specularColor;
  protected boolean stroke;
  protected int strokeCap;
  protected int strokeColor;
  protected int strokeJoin;
  protected float strokeWeight;
  protected boolean style = true;
  protected int textureMode;
  protected boolean tint;
  protected int tintColor;
  protected int vertexCodeCount;
  protected int[] vertexCodes;
  protected int vertexCount;
  protected float[][] vertices;
  protected boolean visible = true;
  public float width;
  
  public PShape()
  {
    this.family = 0;
  }
  
  public PShape(int paramInt)
  {
    this.family = paramInt;
  }
  
  protected static void copyGeometry(PShape paramPShape1, PShape paramPShape2)
  {
    paramPShape2.beginShape(paramPShape1.getKind());
    copyMatrix(paramPShape1, paramPShape2);
    copyStyles(paramPShape1, paramPShape2);
    copyImage(paramPShape1, paramPShape2);
    if (paramPShape1.style) {
      for (int j = 0;; j++)
      {
        if (j >= paramPShape1.vertexCount)
        {
          paramPShape2.endShape();
          return;
        }
        float[] arrayOfFloat2 = paramPShape1.vertices[j];
        paramPShape2.fill((int)(255.0F * arrayOfFloat2[3]) << 24 | (int)(255.0F * arrayOfFloat2[4]) << 16 | (int)(255.0F * arrayOfFloat2[5]) << 8 | (int)(255.0F * arrayOfFloat2[6]));
        if (0.0F < PApplet.dist(arrayOfFloat2[9], arrayOfFloat2[10], arrayOfFloat2[11], 0.0F, 0.0F, 0.0F)) {
          paramPShape2.normal(arrayOfFloat2[9], arrayOfFloat2[10], arrayOfFloat2[11]);
        }
        paramPShape2.vertex(arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat2[2], arrayOfFloat2[7], arrayOfFloat2[8]);
      }
    }
    int i = 0;
    label184:
    float[] arrayOfFloat1;
    if (i < paramPShape1.vertexCount)
    {
      arrayOfFloat1 = paramPShape1.vertices[i];
      if (arrayOfFloat1[2] != 0.0F) {
        break label223;
      }
      paramPShape2.vertex(arrayOfFloat1[0], arrayOfFloat1[1]);
    }
    for (;;)
    {
      i++;
      break label184;
      break;
      label223:
      paramPShape2.vertex(arrayOfFloat1[0], arrayOfFloat1[1], arrayOfFloat1[2]);
    }
  }
  
  protected static void copyGroup(PApplet paramPApplet, PShape paramPShape1, PShape paramPShape2)
  {
    copyMatrix(paramPShape1, paramPShape2);
    copyStyles(paramPShape1, paramPShape2);
    copyImage(paramPShape1, paramPShape2);
    for (int i = 0;; i++)
    {
      if (i >= paramPShape1.childCount) {
        return;
      }
      paramPShape2.addChild(createShape(paramPApplet, paramPShape1.children[i]));
    }
  }
  
  protected static void copyImage(PShape paramPShape1, PShape paramPShape2)
  {
    if (paramPShape1.image != null) {
      paramPShape2.texture(paramPShape1.image);
    }
  }
  
  protected static void copyMatrix(PShape paramPShape1, PShape paramPShape2)
  {
    if (paramPShape1.matrix != null) {
      paramPShape2.applyMatrix(paramPShape1.matrix);
    }
  }
  
  protected static void copyPath(PShape paramPShape1, PShape paramPShape2)
  {
    copyMatrix(paramPShape1, paramPShape2);
    copyStyles(paramPShape1, paramPShape2);
    copyImage(paramPShape1, paramPShape2);
    paramPShape2.close = paramPShape1.close;
    paramPShape2.setPath(paramPShape1.vertexCount, paramPShape1.vertices, paramPShape1.vertexCodeCount, paramPShape1.vertexCodes);
  }
  
  protected static void copyPrimitive(PShape paramPShape1, PShape paramPShape2)
  {
    copyMatrix(paramPShape1, paramPShape2);
    copyStyles(paramPShape1, paramPShape2);
    copyImage(paramPShape1, paramPShape2);
  }
  
  protected static void copyStyles(PShape paramPShape1, PShape paramPShape2)
  {
    if (paramPShape1.stroke)
    {
      paramPShape2.stroke = true;
      paramPShape2.strokeColor = paramPShape1.strokeColor;
      paramPShape2.strokeWeight = paramPShape1.strokeWeight;
      paramPShape2.strokeCap = paramPShape1.strokeCap;
      paramPShape2.strokeJoin = paramPShape1.strokeJoin;
    }
    while (paramPShape1.fill)
    {
      paramPShape2.fill = true;
      paramPShape2.fillColor = paramPShape1.fillColor;
      return;
      paramPShape2.stroke = false;
    }
    paramPShape2.fill = false;
  }
  
  protected static PShape createShape(PApplet paramPApplet, PShape paramPShape)
  {
    PShape localPShape;
    if (paramPShape.family == 0)
    {
      localPShape = paramPApplet.createShape(0);
      copyGroup(paramPApplet, paramPShape, localPShape);
    }
    for (;;)
    {
      localPShape.setName(paramPShape.name);
      return localPShape;
      if (paramPShape.family == 1)
      {
        localPShape = paramPApplet.createShape(paramPShape.kind, paramPShape.params);
        copyPrimitive(paramPShape, localPShape);
      }
      else if (paramPShape.family == 3)
      {
        localPShape = paramPApplet.createShape(paramPShape.kind);
        copyGeometry(paramPShape, localPShape);
      }
      else
      {
        int i = paramPShape.family;
        localPShape = null;
        if (i == 2)
        {
          localPShape = paramPApplet.createShape(2);
          copyPath(paramPShape, localPShape);
        }
      }
    }
  }
  
  public void addChild(PShape paramPShape)
  {
    if (this.children == null) {
      this.children = new PShape[1];
    }
    if (this.childCount == this.children.length) {
      this.children = ((PShape[])PApplet.expand(this.children));
    }
    PShape[] arrayOfPShape = this.children;
    int i = this.childCount;
    this.childCount = (i + 1);
    arrayOfPShape[i] = paramPShape;
    paramPShape.parent = this;
    if (paramPShape.getName() != null) {
      addName(paramPShape.getName(), paramPShape);
    }
  }
  
  public void addChild(PShape paramPShape, int paramInt)
  {
    if (paramInt < this.childCount) {
      if (this.childCount == this.children.length) {
        this.children = ((PShape[])PApplet.expand(this.children));
      }
    }
    for (int i = -1 + this.childCount;; i--)
    {
      if (i < paramInt)
      {
        this.childCount = (1 + this.childCount);
        this.children[paramInt] = paramPShape;
        paramPShape.parent = this;
        if (paramPShape.getName() != null) {
          addName(paramPShape.getName(), paramPShape);
        }
        return;
      }
      this.children[(i + 1)] = this.children[i];
    }
  }
  
  public void addName(String paramString, PShape paramPShape)
  {
    if (this.parent != null)
    {
      this.parent.addName(paramString, paramPShape);
      return;
    }
    if (this.nameTable == null) {
      this.nameTable = new HashMap();
    }
    this.nameTable.put(paramString, paramPShape);
  }
  
  public void ambient(float paramFloat)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "ambient()" });
      return;
    }
    this.setAmbient = true;
    colorCalc(paramFloat);
    this.ambientColor = this.calcColor;
  }
  
  public void ambient(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "ambient()" });
      return;
    }
    this.setAmbient = true;
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    this.ambientColor = this.calcColor;
  }
  
  public void ambient(int paramInt)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "ambient()" });
      return;
    }
    this.setAmbient = true;
    colorCalc(paramInt);
    this.ambientColor = this.calcColor;
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    checkMatrix(2);
    this.matrix.apply(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    checkMatrix(3);
    this.matrix.apply(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramFloat13, paramFloat14, paramFloat15, paramFloat16);
  }
  
  public void applyMatrix(PMatrix2D paramPMatrix2D)
  {
    applyMatrix(paramPMatrix2D.m00, paramPMatrix2D.m01, 0.0F, paramPMatrix2D.m02, paramPMatrix2D.m10, paramPMatrix2D.m11, 0.0F, paramPMatrix2D.m12, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void applyMatrix(PMatrix3D paramPMatrix3D)
  {
    applyMatrix(paramPMatrix3D.m00, paramPMatrix3D.m01, paramPMatrix3D.m02, paramPMatrix3D.m03, paramPMatrix3D.m10, paramPMatrix3D.m11, paramPMatrix3D.m12, paramPMatrix3D.m13, paramPMatrix3D.m20, paramPMatrix3D.m21, paramPMatrix3D.m22, paramPMatrix3D.m23, paramPMatrix3D.m30, paramPMatrix3D.m31, paramPMatrix3D.m32, paramPMatrix3D.m33);
  }
  
  public void applyMatrix(PMatrix paramPMatrix)
  {
    if ((paramPMatrix instanceof PMatrix2D)) {
      applyMatrix((PMatrix2D)paramPMatrix);
    }
    while (!(paramPMatrix instanceof PMatrix3D)) {
      return;
    }
    applyMatrix((PMatrix3D)paramPMatrix);
  }
  
  public void beginContour()
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "beginContour()" });
      return;
    }
    if (this.family == 0)
    {
      PGraphics.showWarning("Cannot begin contour in GROUP shapes");
      return;
    }
    if (this.openContour)
    {
      PGraphics.showWarning("Already called beginContour().");
      return;
    }
    this.openContour = true;
  }
  
  public void beginShape()
  {
    beginShape(20);
  }
  
  public void beginShape(int paramInt)
  {
    this.kind = paramInt;
    this.openShape = true;
  }
  
  public void bezierDetail(int paramInt) {}
  
  public void bezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {}
  
  public void bezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9) {}
  
  protected void checkMatrix(int paramInt)
  {
    if (this.matrix == null) {
      if (paramInt == 2) {
        this.matrix = new PMatrix2D();
      }
    }
    while ((paramInt != 3) || (!(this.matrix instanceof PMatrix2D)))
    {
      return;
      this.matrix = new PMatrix3D();
      return;
    }
    this.matrix = new PMatrix3D(this.matrix);
  }
  
  protected void colorCalc(float paramFloat)
  {
    colorCalc(paramFloat, this.colorModeA);
  }
  
  protected void colorCalc(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 > this.colorModeX) {
      paramFloat1 = this.colorModeX;
    }
    if (paramFloat2 > this.colorModeA) {
      paramFloat2 = this.colorModeA;
    }
    if (paramFloat1 < 0.0F) {
      paramFloat1 = 0.0F;
    }
    if (paramFloat2 < 0.0F) {
      paramFloat2 = 0.0F;
    }
    if (this.colorModeScale) {
      paramFloat1 /= this.colorModeX;
    }
    this.calcR = paramFloat1;
    this.calcG = this.calcR;
    this.calcB = this.calcR;
    if (this.colorModeScale) {
      paramFloat2 /= this.colorModeA;
    }
    this.calcA = paramFloat2;
    this.calcRi = ((int)(255.0F * this.calcR));
    this.calcGi = ((int)(255.0F * this.calcG));
    this.calcBi = ((int)(255.0F * this.calcB));
    this.calcAi = ((int)(255.0F * this.calcA));
    this.calcColor = (this.calcAi << 24 | this.calcRi << 16 | this.calcGi << 8 | this.calcBi);
    if (this.calcAi != 255) {}
    for (boolean bool = true;; bool = false)
    {
      this.calcAlpha = bool;
      return;
    }
  }
  
  protected void colorCalc(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3, this.colorModeA);
  }
  
  protected void colorCalc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (paramFloat1 > this.colorModeX) {
      paramFloat1 = this.colorModeX;
    }
    if (paramFloat2 > this.colorModeY) {
      paramFloat2 = this.colorModeY;
    }
    if (paramFloat3 > this.colorModeZ) {
      paramFloat3 = this.colorModeZ;
    }
    if (paramFloat4 > this.colorModeA) {
      paramFloat4 = this.colorModeA;
    }
    if (paramFloat1 < 0.0F) {
      paramFloat1 = 0.0F;
    }
    if (paramFloat2 < 0.0F) {
      paramFloat2 = 0.0F;
    }
    if (paramFloat3 < 0.0F) {
      paramFloat3 = 0.0F;
    }
    if (paramFloat4 < 0.0F) {
      paramFloat4 = 0.0F;
    }
    switch (this.colorMode)
    {
    case 2: 
    default: 
      this.calcRi = ((int)(255.0F * this.calcR));
      this.calcGi = ((int)(255.0F * this.calcG));
      this.calcBi = ((int)(255.0F * this.calcB));
      this.calcAi = ((int)(255.0F * this.calcA));
      this.calcColor = (this.calcAi << 24 | this.calcRi << 16 | this.calcGi << 8 | this.calcBi);
      if (this.calcAi == 255) {
        break;
      }
    }
    for (boolean bool = true;; bool = false)
    {
      this.calcAlpha = bool;
      return;
      if (this.colorModeScale)
      {
        this.calcR = (paramFloat1 / this.colorModeX);
        this.calcG = (paramFloat2 / this.colorModeY);
        this.calcB = (paramFloat3 / this.colorModeZ);
        this.calcA = (paramFloat4 / this.colorModeA);
        break;
      }
      this.calcR = paramFloat1;
      this.calcG = paramFloat2;
      this.calcB = paramFloat3;
      this.calcA = paramFloat4;
      break;
      float f1 = paramFloat1 / this.colorModeX;
      float f2 = paramFloat2 / this.colorModeY;
      float f3 = paramFloat3 / this.colorModeZ;
      if (this.colorModeScale) {
        paramFloat4 /= this.colorModeA;
      }
      this.calcA = paramFloat4;
      if (f2 == 0.0F)
      {
        this.calcB = f3;
        this.calcG = f3;
        this.calcR = f3;
        break;
      }
      float f4 = 6.0F * (f1 - (int)f1);
      float f5 = f4 - (int)f4;
      float f6 = f3 * (1.0F - f2);
      float f7 = f3 * (1.0F - f2 * f5);
      float f8 = f3 * (1.0F - f2 * (1.0F - f5));
      switch ((int)f4)
      {
      default: 
        break;
      case 0: 
        this.calcR = f3;
        this.calcG = f8;
        this.calcB = f6;
        break;
      case 1: 
        this.calcR = f7;
        this.calcG = f3;
        this.calcB = f6;
        break;
      case 2: 
        this.calcR = f6;
        this.calcG = f3;
        this.calcB = f8;
        break;
      case 3: 
        this.calcR = f6;
        this.calcG = f7;
        this.calcB = f3;
        break;
      case 4: 
        this.calcR = f8;
        this.calcG = f6;
        this.calcB = f3;
        break;
      case 5: 
        this.calcR = f3;
        this.calcG = f6;
        this.calcB = f7;
        break;
      }
    }
  }
  
  protected void colorCalc(int paramInt)
  {
    if (((0xFF000000 & paramInt) == 0) && (paramInt <= this.colorModeX))
    {
      colorCalc(paramInt);
      return;
    }
    colorCalcARGB(paramInt, this.colorModeA);
  }
  
  protected void colorCalc(int paramInt, float paramFloat)
  {
    if (((0xFF000000 & paramInt) == 0) && (paramInt <= this.colorModeX))
    {
      colorCalc(paramInt, paramFloat);
      return;
    }
    colorCalcARGB(paramInt, paramFloat);
  }
  
  protected void colorCalcARGB(int paramInt, float paramFloat)
  {
    if (paramFloat == this.colorModeA)
    {
      this.calcAi = (0xFF & paramInt >> 24);
      this.calcColor = paramInt;
      this.calcRi = (0xFF & paramInt >> 16);
      this.calcGi = (0xFF & paramInt >> 8);
      this.calcBi = (paramInt & 0xFF);
      this.calcA = (this.calcAi / 255.0F);
      this.calcR = (this.calcRi / 255.0F);
      this.calcG = (this.calcGi / 255.0F);
      this.calcB = (this.calcBi / 255.0F);
      if (this.calcAi == 255) {
        break label166;
      }
    }
    label166:
    for (boolean bool = true;; bool = false)
    {
      this.calcAlpha = bool;
      return;
      this.calcAi = ((int)((0xFF & paramInt >> 24) * (paramFloat / this.colorModeA)));
      this.calcColor = (this.calcAi << 24 | 0xFFFFFF & paramInt);
      break;
    }
  }
  
  public void colorMode(int paramInt)
  {
    colorMode(paramInt, this.colorModeX, this.colorModeY, this.colorModeZ, this.colorModeA);
  }
  
  public void colorMode(int paramInt, float paramFloat)
  {
    colorMode(paramInt, paramFloat, paramFloat, paramFloat, paramFloat);
  }
  
  public void colorMode(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorMode(paramInt, paramFloat1, paramFloat2, paramFloat3, this.colorModeA);
  }
  
  public void colorMode(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    int i = 1;
    this.colorMode = paramInt;
    this.colorModeX = paramFloat1;
    this.colorModeY = paramFloat2;
    this.colorModeZ = paramFloat3;
    this.colorModeA = paramFloat4;
    boolean bool;
    if ((paramFloat4 == 1.0F) && (paramFloat1 == paramFloat2) && (paramFloat2 == paramFloat3) && (paramFloat3 == paramFloat4))
    {
      bool = false;
      this.colorModeScale = bool;
      if ((this.colorMode != i) || (this.colorModeA != 255.0F) || (this.colorModeX != 255.0F) || (this.colorModeY != 255.0F) || (this.colorModeZ != 255.0F)) {
        break label130;
      }
    }
    for (;;)
    {
      this.colorModeDefault = i;
      return;
      bool = i;
      break;
      label130:
      i = 0;
    }
  }
  
  public boolean contains(float paramFloat1, float paramFloat2)
  {
    if (this.family == 2)
    {
      boolean bool = false;
      int i = 0;
      int j = -1 + this.vertexCount;
      if (i >= this.vertexCount) {
        return bool;
      }
      int k;
      label49:
      int m;
      if (this.vertices[i][1] > paramFloat2)
      {
        k = 1;
        if (this.vertices[j][1] <= paramFloat2) {
          break label168;
        }
        m = 1;
        label66:
        if ((k != m) && (paramFloat1 < (this.vertices[j][0] - this.vertices[i][0]) * (paramFloat2 - this.vertices[i][1]) / (this.vertices[j][1] - this.vertices[i][1]) + this.vertices[i][0])) {
          if (!bool) {
            break label174;
          }
        }
      }
      label168:
      label174:
      for (bool = false;; bool = true)
      {
        int n = i + 1;
        j = i;
        i = n;
        break;
        k = 0;
        break label49;
        m = 0;
        break label66;
      }
    }
    throw new IllegalArgumentException("The contains() method is only implemented for paths.");
  }
  
  public void curveDetail(int paramInt) {}
  
  public void curveTightness(float paramFloat) {}
  
  public void curveVertex(float paramFloat1, float paramFloat2) {}
  
  public void curveVertex(float paramFloat1, float paramFloat2, float paramFloat3) {}
  
  public void disableStyle()
  {
    this.style = false;
    for (int i = 0;; i++)
    {
      if (i >= this.childCount) {
        return;
      }
      this.children[i].disableStyle();
    }
  }
  
  public void draw(PGraphics paramPGraphics)
  {
    if (this.visible)
    {
      pre(paramPGraphics);
      drawImpl(paramPGraphics);
      post(paramPGraphics);
    }
  }
  
  protected void drawGeometry(PGraphics paramPGraphics)
  {
    paramPGraphics.beginShape(this.kind);
    if (this.style) {
      for (int j = 0;; j++)
      {
        if (j >= this.vertexCount)
        {
          paramPGraphics.endShape();
          return;
        }
        paramPGraphics.vertex(this.vertices[j]);
      }
    }
    int i = 0;
    label51:
    float[] arrayOfFloat;
    if (i < this.vertexCount)
    {
      arrayOfFloat = this.vertices[i];
      if (arrayOfFloat[2] != 0.0F) {
        break label90;
      }
      paramPGraphics.vertex(arrayOfFloat[0], arrayOfFloat[1]);
    }
    for (;;)
    {
      i++;
      break label51;
      break;
      label90:
      paramPGraphics.vertex(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2]);
    }
  }
  
  protected void drawGroup(PGraphics paramPGraphics)
  {
    for (int i = 0;; i++)
    {
      if (i >= this.childCount) {
        return;
      }
      this.children[i].draw(paramPGraphics);
    }
  }
  
  public void drawImpl(PGraphics paramPGraphics)
  {
    if (this.family == 0) {
      drawGroup(paramPGraphics);
    }
    do
    {
      return;
      if (this.family == 1)
      {
        drawPrimitive(paramPGraphics);
        return;
      }
      if (this.family == 3)
      {
        drawGeometry(paramPGraphics);
        return;
      }
    } while (this.family != 2);
    drawPath(paramPGraphics);
  }
  
  protected void drawPath(PGraphics paramPGraphics)
  {
    if (this.vertices == null) {
      return;
    }
    int i = 0;
    paramPGraphics.beginShape();
    int i3;
    if (this.vertexCodeCount == 0) {
      if (this.vertices[0].length == 2)
      {
        i3 = 0;
        int i4 = this.vertexCount;
        i = 0;
        if (i3 >= i4)
        {
          label50:
          if (i != 0) {
            paramPGraphics.endContour();
          }
          if (!this.close) {
            break label733;
          }
        }
      }
    }
    label733:
    for (int m = 2;; m = 1)
    {
      paramPGraphics.endShape(m);
      return;
      paramPGraphics.vertex(this.vertices[i3][0], this.vertices[i3][1]);
      i3++;
      break;
      for (int i1 = 0;; i1++)
      {
        int i2 = this.vertexCount;
        i = 0;
        if (i1 >= i2) {
          break;
        }
        paramPGraphics.vertex(this.vertices[i1][0], this.vertices[i1][1], this.vertices[i1][2]);
      }
      int j = 0;
      if (this.vertices[0].length == 2)
      {
        int n = 0;
        if (n >= this.vertexCodeCount) {
          break label50;
        }
        switch (this.vertexCodes[n])
        {
        }
        for (;;)
        {
          n++;
          break;
          paramPGraphics.vertex(this.vertices[j][0], this.vertices[j][1]);
          j++;
          continue;
          paramPGraphics.quadraticVertex(this.vertices[(j + 0)][0], this.vertices[(j + 0)][1], this.vertices[(j + 1)][0], this.vertices[(j + 1)][1]);
          j += 2;
          continue;
          paramPGraphics.bezierVertex(this.vertices[(j + 0)][0], this.vertices[(j + 0)][1], this.vertices[(j + 1)][0], this.vertices[(j + 1)][1], this.vertices[(j + 2)][0], this.vertices[(j + 2)][1]);
          j += 3;
          continue;
          paramPGraphics.curveVertex(this.vertices[j][0], this.vertices[j][1]);
          j++;
          continue;
          if (i != 0) {
            paramPGraphics.endContour();
          }
          paramPGraphics.beginContour();
          i = 1;
        }
      }
      int k = 0;
      if (k >= this.vertexCodeCount) {
        break label50;
      }
      switch (this.vertexCodes[k])
      {
      }
      for (;;)
      {
        k++;
        break;
        paramPGraphics.vertex(this.vertices[j][0], this.vertices[j][1], this.vertices[j][2]);
        j++;
        continue;
        paramPGraphics.quadraticVertex(this.vertices[(j + 0)][0], this.vertices[(j + 0)][1], this.vertices[(j + 0)][2], this.vertices[(j + 1)][0], this.vertices[(j + 1)][1], this.vertices[(j + 0)][2]);
        j += 2;
        continue;
        paramPGraphics.bezierVertex(this.vertices[(j + 0)][0], this.vertices[(j + 0)][1], this.vertices[(j + 0)][2], this.vertices[(j + 1)][0], this.vertices[(j + 1)][1], this.vertices[(j + 1)][2], this.vertices[(j + 2)][0], this.vertices[(j + 2)][1], this.vertices[(j + 2)][2]);
        j += 3;
        continue;
        paramPGraphics.curveVertex(this.vertices[j][0], this.vertices[j][1], this.vertices[j][2]);
        j++;
        continue;
        if (i != 0) {
          paramPGraphics.endContour();
        }
        paramPGraphics.beginContour();
        i = 1;
      }
    }
  }
  
  protected void drawPrimitive(PGraphics paramPGraphics)
  {
    if (this.kind == 2) {
      paramPGraphics.point(this.params[0], this.params[1]);
    }
    do
    {
      return;
      if (this.kind == 4)
      {
        if (this.params.length == 4)
        {
          paramPGraphics.line(this.params[0], this.params[1], this.params[2], this.params[3]);
          return;
        }
        paramPGraphics.line(this.params[0], this.params[1], this.params[2], this.params[3], this.params[4], this.params[5]);
        return;
      }
      if (this.kind == 8)
      {
        paramPGraphics.triangle(this.params[0], this.params[1], this.params[2], this.params[3], this.params[4], this.params[5]);
        return;
      }
      if (this.kind == 16)
      {
        paramPGraphics.quad(this.params[0], this.params[1], this.params[2], this.params[3], this.params[4], this.params[5], this.params[6], this.params[7]);
        return;
      }
      if (this.kind == 30)
      {
        if (this.image != null)
        {
          paramPGraphics.imageMode(0);
          paramPGraphics.image(this.image, this.params[0], this.params[1], this.params[2], this.params[3]);
          return;
        }
        paramPGraphics.rectMode(0);
        paramPGraphics.rect(this.params[0], this.params[1], this.params[2], this.params[3]);
        return;
      }
      if (this.kind == 31)
      {
        paramPGraphics.ellipseMode(0);
        paramPGraphics.ellipse(this.params[0], this.params[1], this.params[2], this.params[3]);
        return;
      }
      if (this.kind == 32)
      {
        paramPGraphics.ellipseMode(0);
        paramPGraphics.arc(this.params[0], this.params[1], this.params[2], this.params[3], this.params[4], this.params[5]);
        return;
      }
      if (this.kind == 41)
      {
        if (this.params.length == 1)
        {
          paramPGraphics.box(this.params[0]);
          return;
        }
        paramPGraphics.box(this.params[0], this.params[1], this.params[2]);
        return;
      }
    } while (this.kind != 40);
    paramPGraphics.sphere(this.params[0]);
  }
  
  public void emissive(float paramFloat)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "emissive()" });
      return;
    }
    colorCalc(paramFloat);
    this.emissiveColor = this.calcColor;
  }
  
  public void emissive(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "emissive()" });
      return;
    }
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    this.emissiveColor = this.calcColor;
  }
  
  public void emissive(int paramInt)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "emissive()" });
      return;
    }
    colorCalc(paramInt);
    this.emissiveColor = this.calcColor;
  }
  
  public void enableStyle()
  {
    this.style = true;
    for (int i = 0;; i++)
    {
      if (i >= this.childCount) {
        return;
      }
      this.children[i].enableStyle();
    }
  }
  
  public void endContour()
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "endContour()" });
      return;
    }
    if (this.family == 0)
    {
      PGraphics.showWarning("Cannot end contour in GROUP shapes");
      return;
    }
    if (!this.openContour)
    {
      PGraphics.showWarning("Need to call beginContour() first.");
      return;
    }
    this.openContour = false;
  }
  
  public void endShape()
  {
    endShape(1);
  }
  
  public void endShape(int paramInt)
  {
    if (this.family == 0)
    {
      PGraphics.showWarning("Cannot end GROUP shape");
      return;
    }
    if (!this.openShape)
    {
      PGraphics.showWarning("Need to call beginShape() first");
      return;
    }
    this.openShape = false;
  }
  
  public void fill(float paramFloat)
  {
    if (!this.openShape) {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "fill()" });
    }
    do
    {
      return;
      this.fill = true;
      colorCalc(paramFloat);
      this.fillColor = this.calcColor;
    } while (this.setAmbient);
    this.ambientColor = this.fillColor;
  }
  
  public void fill(float paramFloat1, float paramFloat2)
  {
    if (!this.openShape) {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "fill()" });
    }
    do
    {
      return;
      this.fill = true;
      colorCalc(paramFloat1, paramFloat2);
      this.fillColor = this.calcColor;
    } while (this.setAmbient);
    this.ambientColor = this.fillColor;
  }
  
  public void fill(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!this.openShape) {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "fill()" });
    }
    do
    {
      return;
      this.fill = true;
      colorCalc(paramFloat1, paramFloat2, paramFloat3);
      this.fillColor = this.calcColor;
    } while (this.setAmbient);
    this.ambientColor = this.fillColor;
  }
  
  public void fill(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (!this.openShape) {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "fill()" });
    }
    do
    {
      return;
      this.fill = true;
      colorCalc(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      this.fillColor = this.calcColor;
    } while (this.setAmbient);
    this.ambientColor = this.fillColor;
  }
  
  public void fill(int paramInt)
  {
    if (!this.openShape) {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "fill()" });
    }
    do
    {
      return;
      this.fill = true;
      colorCalc(paramInt);
      this.fillColor = this.calcColor;
    } while (this.setAmbient);
    this.ambientColor = this.fillColor;
  }
  
  public void fill(int paramInt, float paramFloat)
  {
    if (!this.openShape) {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "fill()" });
    }
    do
    {
      return;
      this.fill = true;
      colorCalc(paramInt, paramFloat);
      this.fillColor = this.calcColor;
    } while (this.setAmbient);
    this.ambientColor = this.fillColor;
  }
  
  public PShape findChild(String paramString)
  {
    if (this.parent == null) {
      return getChild(paramString);
    }
    return this.parent.findChild(paramString);
  }
  
  public int getAmbient(int paramInt)
  {
    int i = (int)(255.0F * this.vertices[paramInt][25]);
    int j = (int)(255.0F * this.vertices[paramInt][26]);
    return (int)(255.0F * this.vertices[paramInt][27]) | 0xFF000000 | i << 16 | j << 8;
  }
  
  public PShape getChild(int paramInt)
  {
    return this.children[paramInt];
  }
  
  public PShape getChild(String paramString)
  {
    PShape localPShape;
    if ((this.name != null) && (this.name.equals(paramString))) {
      localPShape = this;
    }
    do
    {
      return localPShape;
      if (this.nameTable == null) {
        break;
      }
      localPShape = (PShape)this.nameTable.get(paramString);
    } while (localPShape != null);
    for (int i = 0;; i++)
    {
      if (i >= this.childCount) {
        return null;
      }
      localPShape = this.children[i].getChild(paramString);
      if (localPShape != null) {
        break;
      }
    }
  }
  
  public int getChildCount()
  {
    return this.childCount;
  }
  
  public int getChildIndex(PShape paramPShape)
  {
    for (int i = 0;; i++)
    {
      if (i >= this.childCount) {
        i = -1;
      }
      while (this.children[i] == paramPShape) {
        return i;
      }
    }
  }
  
  public PShape[] getChildren()
  {
    return this.children;
  }
  
  public float getDepth()
  {
    return this.depth;
  }
  
  public int getEmissive(int paramInt)
  {
    int i = (int)(255.0F * this.vertices[paramInt][32]);
    int j = (int)(255.0F * this.vertices[paramInt][33]);
    return (int)(255.0F * this.vertices[paramInt][34]) | 0xFF000000 | i << 16 | j << 8;
  }
  
  public int getFamily()
  {
    return this.family;
  }
  
  public int getFill(int paramInt)
  {
    if (this.image == null)
    {
      int i = (int)(255.0F * this.vertices[paramInt][6]);
      int j = (int)(255.0F * this.vertices[paramInt][3]);
      int k = (int)(255.0F * this.vertices[paramInt][4]);
      return (int)(255.0F * this.vertices[paramInt][5]) | i << 24 | j << 16 | k << 8;
    }
    return 0;
  }
  
  public float getHeight()
  {
    return this.height;
  }
  
  public int getKind()
  {
    return this.kind;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public PVector getNormal(int paramInt)
  {
    return getNormal(paramInt, null);
  }
  
  public PVector getNormal(int paramInt, PVector paramPVector)
  {
    if (paramPVector == null) {
      paramPVector = new PVector();
    }
    paramPVector.x = this.vertices[paramInt][9];
    paramPVector.y = this.vertices[paramInt][10];
    paramPVector.z = this.vertices[paramInt][11];
    return paramPVector;
  }
  
  public float getNormalX(int paramInt)
  {
    return this.vertices[paramInt][9];
  }
  
  public float getNormalY(int paramInt)
  {
    return this.vertices[paramInt][10];
  }
  
  public float getNormalZ(int paramInt)
  {
    return this.vertices[paramInt][11];
  }
  
  public float getParam(int paramInt)
  {
    return this.params[paramInt];
  }
  
  public float[] getParams()
  {
    return getParams(null);
  }
  
  public float[] getParams(float[] paramArrayOfFloat)
  {
    if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length != this.params.length)) {
      paramArrayOfFloat = new float[this.params.length];
    }
    PApplet.arrayCopy(this.params, paramArrayOfFloat);
    return paramArrayOfFloat;
  }
  
  public PShape getParent()
  {
    return this.parent;
  }
  
  public float getShininess(int paramInt)
  {
    return this.vertices[paramInt][31];
  }
  
  public int getSpecular(int paramInt)
  {
    int i = (int)(255.0F * this.vertices[paramInt][28]);
    int j = (int)(255.0F * this.vertices[paramInt][29]);
    return (int)(255.0F * this.vertices[paramInt][30]) | 0xFF000000 | i << 16 | j << 8;
  }
  
  public int getStroke(int paramInt)
  {
    int i = (int)(255.0F * this.vertices[paramInt][16]);
    int j = (int)(255.0F * this.vertices[paramInt][13]);
    int k = (int)(255.0F * this.vertices[paramInt][14]);
    return (int)(255.0F * this.vertices[paramInt][15]) | i << 24 | j << 16 | k << 8;
  }
  
  public float getStrokeWeight(int paramInt)
  {
    return this.vertices[paramInt][17];
  }
  
  public PShape getTessellation()
  {
    return null;
  }
  
  public float getTextureU(int paramInt)
  {
    return this.vertices[paramInt][7];
  }
  
  public float getTextureV(int paramInt)
  {
    return this.vertices[paramInt][8];
  }
  
  public int getTint(int paramInt)
  {
    if (this.image != null)
    {
      int i = (int)(255.0F * this.vertices[paramInt][6]);
      int j = (int)(255.0F * this.vertices[paramInt][3]);
      int k = (int)(255.0F * this.vertices[paramInt][4]);
      return (int)(255.0F * this.vertices[paramInt][5]) | i << 24 | j << 16 | k << 8;
    }
    return 0;
  }
  
  public PVector getVertex(int paramInt)
  {
    return getVertex(paramInt, null);
  }
  
  public PVector getVertex(int paramInt, PVector paramPVector)
  {
    if (paramPVector == null) {
      paramPVector = new PVector();
    }
    paramPVector.x = this.vertices[paramInt][0];
    paramPVector.y = this.vertices[paramInt][1];
    paramPVector.z = this.vertices[paramInt][2];
    return paramPVector;
  }
  
  public int getVertexCode(int paramInt)
  {
    return this.vertexCodes[paramInt];
  }
  
  public int getVertexCodeCount()
  {
    return this.vertexCodeCount;
  }
  
  public int[] getVertexCodes()
  {
    if (this.vertexCodes == null) {
      return null;
    }
    if (this.vertexCodes.length != this.vertexCodeCount) {
      this.vertexCodes = PApplet.subset(this.vertexCodes, 0, this.vertexCodeCount);
    }
    return this.vertexCodes;
  }
  
  public int getVertexCount()
  {
    return this.vertexCount;
  }
  
  public float getVertexX(int paramInt)
  {
    return this.vertices[paramInt][0];
  }
  
  public float getVertexY(int paramInt)
  {
    return this.vertices[paramInt][1];
  }
  
  public float getVertexZ(int paramInt)
  {
    return this.vertices[paramInt][2];
  }
  
  public float getWidth()
  {
    return this.width;
  }
  
  public boolean is2D()
  {
    return !this.is3D;
  }
  
  public void is3D(boolean paramBoolean)
  {
    this.is3D = paramBoolean;
  }
  
  public boolean is3D()
  {
    return this.is3D;
  }
  
  public boolean isClosed()
  {
    return this.close;
  }
  
  public boolean isVisible()
  {
    return this.visible;
  }
  
  public void noFill()
  {
    if (!this.openShape) {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "noFill()" });
    }
    do
    {
      return;
      this.fill = false;
      this.fillColor = 0;
    } while (this.setAmbient);
    this.ambientColor = this.fillColor;
  }
  
  public void noStroke()
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "noStroke()" });
      return;
    }
    this.stroke = false;
  }
  
  public void noTexture()
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "noTexture()" });
      return;
    }
    this.image = null;
  }
  
  public void noTint()
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "noTint()" });
      return;
    }
    this.tint = false;
  }
  
  public void normal(float paramFloat1, float paramFloat2, float paramFloat3) {}
  
  protected void post(PGraphics paramPGraphics)
  {
    if (this.matrix != null) {
      paramPGraphics.popMatrix();
    }
    if (this.style) {
      paramPGraphics.popStyle();
    }
  }
  
  protected void pre(PGraphics paramPGraphics)
  {
    if (this.matrix != null)
    {
      paramPGraphics.pushMatrix();
      paramPGraphics.applyMatrix(this.matrix);
    }
    if (this.style)
    {
      paramPGraphics.pushStyle();
      styles(paramPGraphics);
    }
  }
  
  public void quadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {}
  
  public void quadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {}
  
  public void removeChild(int paramInt)
  {
    PShape localPShape;
    if (paramInt < this.childCount) {
      localPShape = this.children[paramInt];
    }
    for (int i = paramInt;; i++)
    {
      if (i >= -1 + this.childCount)
      {
        this.childCount = (-1 + this.childCount);
        if ((localPShape.getName() != null) && (this.nameTable != null)) {
          this.nameTable.remove(localPShape.getName());
        }
        return;
      }
      this.children[i] = this.children[(i + 1)];
    }
  }
  
  public void resetMatrix()
  {
    checkMatrix(2);
    this.matrix.reset();
  }
  
  public void rotate(float paramFloat)
  {
    checkMatrix(2);
    this.matrix.rotate(paramFloat);
  }
  
  public void rotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    checkMatrix(3);
    float f1 = paramFloat2 * paramFloat2 + paramFloat3 * paramFloat3 + paramFloat4 * paramFloat4;
    if (Math.abs(f1 - 1.0F) > 1.0E-004F)
    {
      float f2 = PApplet.sqrt(f1);
      paramFloat2 /= f2;
      paramFloat3 /= f2;
      paramFloat4 /= f2;
    }
    this.matrix.rotate(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void rotateX(float paramFloat)
  {
    rotate(paramFloat, 1.0F, 0.0F, 0.0F);
  }
  
  public void rotateY(float paramFloat)
  {
    rotate(paramFloat, 0.0F, 1.0F, 0.0F);
  }
  
  public void rotateZ(float paramFloat)
  {
    rotate(paramFloat, 0.0F, 0.0F, 1.0F);
  }
  
  public void scale(float paramFloat)
  {
    checkMatrix(2);
    this.matrix.scale(paramFloat);
  }
  
  public void scale(float paramFloat1, float paramFloat2)
  {
    checkMatrix(2);
    this.matrix.scale(paramFloat1, paramFloat2);
  }
  
  public void scale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    checkMatrix(3);
    this.matrix.scale(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void setAmbient(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setAmbient()" });
    }
    for (;;)
    {
      return;
      for (int i = 0; i < this.vertices.length; i++) {
        setAmbient(i, paramInt);
      }
    }
  }
  
  public void setAmbient(int paramInt1, int paramInt2)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setAmbient()" });
      return;
    }
    this.vertices[paramInt1][25] = ((0xFF & paramInt2 >> 16) / 255.0F);
    this.vertices[paramInt1][26] = ((0xFF & paramInt2 >> 8) / 255.0F);
    this.vertices[paramInt1][27] = ((0xFF & paramInt2 >> 0) / 255.0F);
  }
  
  public void setEmissive(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setEmissive()" });
    }
    for (;;)
    {
      return;
      for (int i = 0; i < this.vertices.length; i++) {
        setEmissive(i, paramInt);
      }
    }
  }
  
  public void setEmissive(int paramInt1, int paramInt2)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setEmissive()" });
      return;
    }
    this.vertices[paramInt1][32] = ((0xFF & paramInt2 >> 16) / 255.0F);
    this.vertices[paramInt1][33] = ((0xFF & paramInt2 >> 8) / 255.0F);
    this.vertices[paramInt1][34] = ((0xFF & paramInt2 >> 0) / 255.0F);
  }
  
  public void setFill(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setFill()" });
    }
    for (;;)
    {
      return;
      for (int i = 0; i < this.vertices.length; i++) {
        setFill(i, paramInt);
      }
    }
  }
  
  public void setFill(int paramInt1, int paramInt2)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setFill()" });
    }
    while (this.image != null) {
      return;
    }
    this.vertices[paramInt1][6] = ((0xFF & paramInt2 >> 24) / 255.0F);
    this.vertices[paramInt1][3] = ((0xFF & paramInt2 >> 16) / 255.0F);
    this.vertices[paramInt1][4] = ((0xFF & paramInt2 >> 8) / 255.0F);
    this.vertices[paramInt1][5] = ((0xFF & paramInt2 >> 0) / 255.0F);
  }
  
  public void setFill(boolean paramBoolean)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setFill()" });
      return;
    }
    this.fill = paramBoolean;
  }
  
  public void setKind(int paramInt)
  {
    this.kind = paramInt;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setNormal(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setNormal()" });
      return;
    }
    this.vertices[paramInt][9] = paramFloat1;
    this.vertices[paramInt][10] = paramFloat2;
    this.vertices[paramInt][11] = paramFloat3;
  }
  
  protected void setParams(float[] paramArrayOfFloat)
  {
    if (this.params == null) {
      this.params = new float[paramArrayOfFloat.length];
    }
    if (paramArrayOfFloat.length != this.params.length)
    {
      PGraphics.showWarning("Wrong number of parameters");
      return;
    }
    PApplet.arrayCopy(paramArrayOfFloat, this.params);
  }
  
  public void setPath(int paramInt, float[][] paramArrayOfFloat)
  {
    setPath(paramInt, paramArrayOfFloat, 0, null);
  }
  
  protected void setPath(int paramInt1, float[][] paramArrayOfFloat, int paramInt2, int[] paramArrayOfInt)
  {
    if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length < paramInt1)) {
      break label10;
    }
    label10:
    while ((paramInt2 > 0) && ((paramArrayOfInt == null) || (paramArrayOfInt.length < paramInt2))) {
      return;
    }
    int i = paramArrayOfFloat[0].length;
    this.vertexCount = paramInt1;
    int[] arrayOfInt = { this.vertexCount, i };
    this.vertices = ((float[][])Array.newInstance(Float.TYPE, arrayOfInt));
    for (int j = 0;; j++)
    {
      if (j >= this.vertexCount)
      {
        this.vertexCodeCount = paramInt2;
        if (this.vertexCodeCount <= 0) {
          break;
        }
        this.vertexCodes = new int[this.vertexCodeCount];
        PApplet.arrayCopy(paramArrayOfInt, this.vertexCodes, this.vertexCodeCount);
        return;
      }
      PApplet.arrayCopy(paramArrayOfFloat[j], this.vertices[j]);
    }
  }
  
  public void setShininess(float paramFloat)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setShininess()" });
    }
    for (;;)
    {
      return;
      for (int i = 0; i < this.vertices.length; i++) {
        setShininess(i, paramFloat);
      }
    }
  }
  
  public void setShininess(int paramInt, float paramFloat)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setShininess()" });
      return;
    }
    this.vertices[paramInt][31] = paramFloat;
  }
  
  public void setSpecular(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setSpecular()" });
    }
    for (;;)
    {
      return;
      for (int i = 0; i < this.vertices.length; i++) {
        setSpecular(i, paramInt);
      }
    }
  }
  
  public void setSpecular(int paramInt1, int paramInt2)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setSpecular()" });
      return;
    }
    this.vertices[paramInt1][28] = ((0xFF & paramInt2 >> 16) / 255.0F);
    this.vertices[paramInt1][29] = ((0xFF & paramInt2 >> 8) / 255.0F);
    this.vertices[paramInt1][30] = ((0xFF & paramInt2 >> 0) / 255.0F);
  }
  
  public void setStroke(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStroke()" });
    }
    for (;;)
    {
      return;
      for (int i = 0; i < this.vertices.length; i++) {
        setStroke(i, paramInt);
      }
    }
  }
  
  public void setStroke(int paramInt1, int paramInt2)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStroke()" });
      return;
    }
    this.vertices[paramInt1][16] = ((0xFF & paramInt2 >> 24) / 255.0F);
    this.vertices[paramInt1][13] = ((0xFF & paramInt2 >> 16) / 255.0F);
    this.vertices[paramInt1][14] = ((0xFF & paramInt2 >> 8) / 255.0F);
    this.vertices[paramInt1][15] = ((0xFF & paramInt2 >> 0) / 255.0F);
  }
  
  public void setStroke(boolean paramBoolean)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStroke()" });
      return;
    }
    this.stroke = paramBoolean;
  }
  
  public void setStrokeCap(int paramInt)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStrokeCap()" });
      return;
    }
    this.strokeCap = paramInt;
  }
  
  public void setStrokeJoin(int paramInt)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStrokeJoin()" });
      return;
    }
    this.strokeJoin = paramInt;
  }
  
  public void setStrokeWeight(float paramFloat)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStrokeWeight()" });
    }
    for (;;)
    {
      return;
      for (int i = 0; i < this.vertices.length; i++) {
        setStrokeWeight(i, paramFloat);
      }
    }
  }
  
  public void setStrokeWeight(int paramInt, float paramFloat)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStrokeWeight()" });
      return;
    }
    this.vertices[paramInt][17] = paramFloat;
  }
  
  public void setTexture(PImage paramPImage)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTexture()" });
      return;
    }
    this.image = paramPImage;
  }
  
  public void setTextureMode(int paramInt)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTextureMode()" });
      return;
    }
    this.textureMode = paramInt;
  }
  
  public void setTextureUV(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTextureUV()" });
      return;
    }
    this.vertices[paramInt][7] = paramFloat1;
    this.vertices[paramInt][8] = paramFloat2;
  }
  
  public void setTint(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTint()" });
    }
    for (;;)
    {
      return;
      for (int i = 0; i < this.vertices.length; i++) {
        setFill(i, paramInt);
      }
    }
  }
  
  public void setTint(int paramInt1, int paramInt2)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTint()" });
    }
    while (this.image == null) {
      return;
    }
    this.vertices[paramInt1][6] = ((0xFF & paramInt2 >> 24) / 255.0F);
    this.vertices[paramInt1][3] = ((0xFF & paramInt2 >> 16) / 255.0F);
    this.vertices[paramInt1][4] = ((0xFF & paramInt2 >> 8) / 255.0F);
    this.vertices[paramInt1][5] = ((0xFF & paramInt2 >> 0) / 255.0F);
  }
  
  public void setTint(boolean paramBoolean)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTint()" });
      return;
    }
    this.tint = paramBoolean;
  }
  
  public void setVertex(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setVertex()" });
      return;
    }
    this.vertices[paramInt][0] = paramFloat1;
    this.vertices[paramInt][1] = paramFloat2;
  }
  
  public void setVertex(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setVertex()" });
      return;
    }
    this.vertices[paramInt][0] = paramFloat1;
    this.vertices[paramInt][1] = paramFloat2;
    this.vertices[paramInt][2] = paramFloat3;
  }
  
  public void setVertex(int paramInt, PVector paramPVector)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setVertex()" });
      return;
    }
    this.vertices[paramInt][0] = paramPVector.x;
    this.vertices[paramInt][1] = paramPVector.y;
    this.vertices[paramInt][2] = paramPVector.z;
  }
  
  public void setVisible(boolean paramBoolean)
  {
    this.visible = paramBoolean;
  }
  
  public void shininess(float paramFloat)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "shininess()" });
      return;
    }
    this.shininess = paramFloat;
  }
  
  protected void solid(boolean paramBoolean) {}
  
  public void specular(float paramFloat)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "specular()" });
      return;
    }
    colorCalc(paramFloat);
    this.specularColor = this.calcColor;
  }
  
  public void specular(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "specular()" });
      return;
    }
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    this.specularColor = this.calcColor;
  }
  
  public void specular(int paramInt)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "specular()" });
      return;
    }
    colorCalc(paramInt);
    this.specularColor = this.calcColor;
  }
  
  public void stroke(float paramFloat)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "stroke()" });
      return;
    }
    this.stroke = true;
    colorCalc(paramFloat);
    this.strokeColor = this.calcColor;
  }
  
  public void stroke(float paramFloat1, float paramFloat2)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "stroke()" });
      return;
    }
    this.stroke = true;
    colorCalc(paramFloat1, paramFloat2);
    this.strokeColor = this.calcColor;
  }
  
  public void stroke(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "stroke()" });
      return;
    }
    this.stroke = true;
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    this.strokeColor = this.calcColor;
  }
  
  public void stroke(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "stroke()" });
      return;
    }
    this.stroke = true;
    colorCalc(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    this.strokeColor = this.calcColor;
  }
  
  public void stroke(int paramInt)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "stroke()" });
      return;
    }
    this.stroke = true;
    colorCalc(paramInt);
    this.strokeColor = this.calcColor;
  }
  
  public void stroke(int paramInt, float paramFloat)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "stroke()" });
      return;
    }
    this.stroke = true;
    colorCalc(paramInt, paramFloat);
    this.strokeColor = this.calcColor;
  }
  
  public void strokeCap(int paramInt)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "strokeCap()" });
      return;
    }
    this.strokeCap = paramInt;
  }
  
  public void strokeJoin(int paramInt)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "strokeJoin()" });
      return;
    }
    this.strokeJoin = paramInt;
  }
  
  public void strokeWeight(float paramFloat)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "strokeWeight()" });
      return;
    }
    this.strokeWeight = paramFloat;
  }
  
  protected void styles(PGraphics paramPGraphics)
  {
    if (this.stroke)
    {
      paramPGraphics.stroke(this.strokeColor);
      paramPGraphics.strokeWeight(this.strokeWeight);
      paramPGraphics.strokeCap(this.strokeCap);
      paramPGraphics.strokeJoin(this.strokeJoin);
    }
    while (this.fill)
    {
      paramPGraphics.fill(this.fillColor);
      return;
      paramPGraphics.noStroke();
    }
    paramPGraphics.noFill();
  }
  
  public void texture(PImage paramPImage)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "texture()" });
      return;
    }
    this.image = paramPImage;
  }
  
  public void textureMode(int paramInt)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "textureMode()" });
      return;
    }
    this.textureMode = paramInt;
  }
  
  public void tint(float paramFloat)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "tint()" });
      return;
    }
    this.tint = true;
    colorCalc(paramFloat);
    this.tintColor = this.calcColor;
  }
  
  public void tint(float paramFloat1, float paramFloat2)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "tint()" });
      return;
    }
    this.tint = true;
    colorCalc(paramFloat1, paramFloat2);
    this.tintColor = this.calcColor;
  }
  
  public void tint(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "tint()" });
      return;
    }
    this.tint = true;
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    this.tintColor = this.calcColor;
  }
  
  public void tint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "tint()" });
      return;
    }
    this.tint = true;
    colorCalc(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    this.tintColor = this.calcColor;
  }
  
  public void tint(int paramInt)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "tint()" });
      return;
    }
    this.tint = true;
    colorCalc(paramInt);
    this.tintColor = this.calcColor;
  }
  
  public void tint(int paramInt, float paramFloat)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "tint()" });
      return;
    }
    this.tint = true;
    colorCalc(paramInt, paramFloat);
    this.tintColor = this.calcColor;
  }
  
  public void translate(float paramFloat1, float paramFloat2)
  {
    checkMatrix(2);
    this.matrix.translate(paramFloat1, paramFloat2);
  }
  
  public void translate(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    checkMatrix(3);
    this.matrix.translate(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void vertex(float paramFloat1, float paramFloat2) {}
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3) {}
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {}
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5) {}
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PShape
 * JD-Core Version:    0.7.0.1
 */