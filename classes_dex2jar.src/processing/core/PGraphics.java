package processing.core;

import android.graphics.Bitmap;
import android.graphics.Color;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.WeakHashMap;
import processing.opengl.PGL;
import processing.opengl.PShader;

public class PGraphics
  extends PImage
  implements PConstants
{
  public static final int A = 6;
  public static final int AB = 27;
  public static final int AG = 26;
  public static final int AR = 25;
  public static final int B = 5;
  public static final int BEEN_LIT = 35;
  public static final int DA = 6;
  public static final int DB = 5;
  protected static final int DEFAULT_STROKE_CAP = 2;
  protected static final int DEFAULT_STROKE_JOIN = 8;
  protected static final float DEFAULT_STROKE_WEIGHT = 1.0F;
  static final int DEFAULT_VERTICES = 512;
  public static final int DG = 4;
  public static final int DR = 3;
  public static final int EB = 34;
  public static final int EDGE = 12;
  public static final int EG = 33;
  public static final int ER = 32;
  public static final int G = 4;
  public static final int HAS_NORMAL = 36;
  static final int MATRIX_STACK_DEPTH = 32;
  protected static final int NORMAL_MODE_AUTO = 0;
  protected static final int NORMAL_MODE_SHAPE = 1;
  protected static final int NORMAL_MODE_VERTEX = 2;
  public static final int NX = 9;
  public static final int NY = 10;
  public static final int NZ = 11;
  public static final int R = 3;
  public static final int SA = 16;
  public static final int SB = 15;
  public static final int SG = 14;
  public static final int SHINE = 31;
  protected static final int SINCOS_LENGTH = 720;
  protected static final float SINCOS_PRECISION = 0.5F;
  public static final int SPB = 30;
  public static final int SPG = 29;
  public static final int SPR = 28;
  public static final int SR = 13;
  static final int STYLE_STACK_DEPTH = 64;
  public static final int SW = 17;
  public static final int TX = 18;
  public static final int TY = 19;
  public static final int TZ = 20;
  public static final int U = 7;
  public static final int V = 8;
  public static final int VERTEX_FIELD_COUNT = 37;
  public static final int VW = 24;
  public static final int VX = 21;
  public static final int VY = 22;
  public static final int VZ = 23;
  protected static final float[] cosLUT = new float[720];
  static float[] lerpColorHSB1;
  static float[] lerpColorHSB2;
  static float[] lerpColorHSB3;
  protected static final float[] sinLUT = new float[720];
  protected static HashMap<String, Object> warnings;
  public float ambientB;
  public int ambientColor;
  public float ambientG;
  public float ambientR;
  protected boolean autoNormal;
  protected float backgroundA;
  protected int backgroundAi;
  protected boolean backgroundAlpha;
  protected float backgroundB;
  protected int backgroundBi;
  public int backgroundColor = -3355444;
  protected float backgroundG;
  protected int backgroundGi;
  protected float backgroundR;
  protected int backgroundRi;
  protected PMatrix3D bezierBasisInverse;
  protected PMatrix3D bezierBasisMatrix;
  public int bezierDetail;
  protected PMatrix3D bezierDrawMatrix;
  protected boolean bezierInited;
  public Bitmap bitmap;
  int cacheHsbKey;
  float[] cacheHsbValue = new float[3];
  protected WeakHashMap<PImage, Object> cacheMap = new WeakHashMap();
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
  public int colorMode;
  public float colorModeA;
  boolean colorModeDefault;
  boolean colorModeScale;
  public float colorModeX;
  public float colorModeY;
  public float colorModeZ;
  protected PMatrix3D curveBasisMatrix;
  protected int curveDetail;
  protected PMatrix3D curveDrawMatrix;
  protected boolean curveInited;
  public float curveTightness;
  protected PMatrix3D curveToBezierMatrix;
  protected int curveVertexCount;
  protected float[][] curveVertices;
  public boolean edge;
  public int ellipseMode;
  public float emissiveB;
  public int emissiveColor;
  public float emissiveG;
  public float emissiveR;
  public boolean fill;
  protected float fillA;
  protected int fillAi;
  protected boolean fillAlpha;
  protected float fillB;
  protected int fillBi;
  public int fillColor = -1;
  protected float fillG;
  protected int fillGi;
  protected float fillR;
  protected int fillRi;
  protected int height1;
  protected boolean[] hints = new boolean[10];
  public int imageMode = 0;
  protected int normalMode;
  public float normalX;
  public float normalY;
  public float normalZ;
  protected String path;
  public int pixelCount;
  protected boolean primarySurface;
  protected int quality;
  protected PGraphics raw;
  public int rectMode;
  public boolean setAmbient;
  protected boolean settingsInited;
  protected int shape;
  public int shapeMode;
  public float shininess;
  public boolean smooth = false;
  public float specularB;
  public int specularColor;
  public float specularG;
  public float specularR;
  public int sphereDetailU;
  public int sphereDetailV;
  protected float[] sphereX;
  protected float[] sphereY;
  protected float[] sphereZ;
  public boolean stroke;
  protected float strokeA;
  protected int strokeAi;
  protected boolean strokeAlpha;
  protected float strokeB;
  protected int strokeBi;
  public int strokeCap = 2;
  public int strokeColor = -16777216;
  protected float strokeG;
  protected int strokeGi;
  public int strokeJoin = 8;
  protected float strokeR;
  protected int strokeRi;
  public float strokeWeight = 1.0F;
  PStyle[] styleStack = new PStyle[64];
  int styleStackDepth;
  public int textAlign = 21;
  public int textAlignY = 0;
  protected int textBreakCount;
  protected int[] textBreakStart;
  protected int[] textBreakStop;
  protected char[] textBuffer;
  public PFont textFont;
  public float textLeading;
  public int textMode = 4;
  public float textSize;
  protected char[] textWidthBuffer;
  public PImage textureImage;
  public int textureMode;
  public float textureU;
  public float textureV;
  public boolean tint;
  protected float tintA;
  protected int tintAi;
  protected boolean tintAlpha;
  protected float tintB;
  protected int tintBi;
  public int tintColor;
  protected float tintG;
  protected int tintGi;
  protected float tintR;
  protected int tintRi;
  protected int vertexCount;
  protected float[][] vertices;
  protected int width1;
  
  static
  {
    for (int i = 0;; i++)
    {
      if (i >= 720) {
        return;
      }
      sinLUT[i] = ((float)Math.sin(0.5F * (0.01745329F * i)));
      cosLUT[i] = ((float)Math.cos(0.5F * (0.01745329F * i)));
    }
  }
  
  public PGraphics()
  {
    int[] arrayOfInt = { 512, 37 };
    this.vertices = ((float[][])Array.newInstance(Float.TYPE, arrayOfInt));
    this.bezierInited = false;
    this.bezierDetail = 20;
    this.bezierBasisMatrix = new PMatrix3D(-1.0F, 3.0F, -3.0F, 1.0F, 3.0F, -6.0F, 3.0F, 0.0F, -3.0F, 3.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F);
    this.curveInited = false;
    this.curveDetail = 20;
    this.curveTightness = 0.0F;
    this.textBuffer = new char[8192];
    this.textWidthBuffer = new char[8192];
    this.edge = true;
    this.textureMode = 2;
    this.textureImage = null;
    this.sphereDetailU = 0;
    this.sphereDetailV = 0;
  }
  
  public static int lerpColor(int paramInt1, int paramInt2, float paramFloat, int paramInt3)
  {
    if (paramInt3 == 1)
    {
      float f2 = 0xFF & paramInt1 >> 24;
      float f3 = 0xFF & paramInt1 >> 16;
      float f4 = 0xFF & paramInt1 >> 8;
      float f5 = paramInt1 & 0xFF;
      float f6 = 0xFF & paramInt2 >> 24;
      float f7 = 0xFF & paramInt2 >> 16;
      float f8 = 0xFF & paramInt2 >> 8;
      float f9 = paramInt2 & 0xFF;
      return (int)(f2 + paramFloat * (f6 - f2)) << 24 | (int)(f3 + paramFloat * (f7 - f3)) << 16 | (int)(f4 + paramFloat * (f8 - f4)) << 8 | (int)(f5 + paramFloat * (f9 - f5));
    }
    if (paramInt3 == 3)
    {
      if (lerpColorHSB1 == null)
      {
        lerpColorHSB1 = new float[3];
        lerpColorHSB2 = new float[3];
        lerpColorHSB3 = new float[3];
      }
      float f1 = 0xFF & paramInt1 >> 24;
      int i = (int)(f1 + paramFloat * ((0xFF & paramInt2 >> 24) - f1)) << 24;
      Color.RGBToHSV(0xFF & paramInt1 >> 16, 0xFF & paramInt1 >> 8, paramInt1 & 0xFF, lerpColorHSB1);
      Color.RGBToHSV(0xFF & paramInt2 >> 16, 0xFF & paramInt2 >> 8, paramInt2 & 0xFF, lerpColorHSB2);
      lerpColorHSB3[0] = PApplet.lerp(lerpColorHSB1[0], lerpColorHSB2[0], paramFloat);
      lerpColorHSB3[1] = PApplet.lerp(lerpColorHSB1[1], lerpColorHSB2[1], paramFloat);
      lerpColorHSB3[2] = PApplet.lerp(lerpColorHSB1[2], lerpColorHSB2[2], paramFloat);
      return Color.HSVToColor(i, lerpColorHSB3);
    }
    return 0;
  }
  
  public static void showDepthWarning(String paramString)
  {
    showWarning(paramString + "() can only be used with a renderer that " + "supports 3D, such as P3D or OPENGL.");
  }
  
  public static void showDepthWarningXYZ(String paramString)
  {
    showWarning(paramString + "() with x, y, and z coordinates " + "can only be used with a renderer that " + "supports 3D, such as P3D or OPENGL. " + "Use a version without a z-coordinate instead.");
  }
  
  public static void showException(String paramString)
  {
    throw new RuntimeException(paramString);
  }
  
  public static void showMethodWarning(String paramString)
  {
    showWarning(paramString + "() is not available with this renderer.");
  }
  
  public static void showMissingWarning(String paramString)
  {
    showWarning(paramString + "(), or this particular variation of it, " + "is not available with this renderer.");
  }
  
  public static void showVariationWarning(String paramString)
  {
    showWarning(paramString + " is not available with this renderer.");
  }
  
  public static void showWarning(String paramString)
  {
    if (warnings == null) {
      warnings = new HashMap();
    }
    if (!warnings.containsKey(paramString))
    {
      System.err.println(paramString);
      warnings.put(paramString, new Object());
    }
  }
  
  public static void showWarning(String paramString, Object... paramVarArgs)
  {
    showWarning(String.format(paramString, paramVarArgs));
  }
  
  private boolean textSentence(char[] paramArrayOfChar, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    float f1 = 0.0F;
    int i = paramInt1;
    int j = paramInt1;
    int k = paramInt1;
    for (;;)
    {
      if (k > paramInt2) {
        return true;
      }
      if ((paramArrayOfChar[k] == ' ') || (k == paramInt2))
      {
        float f2 = textWidthImpl(paramArrayOfChar, j, k);
        if (f1 + f2 > paramFloat1)
        {
          if (f1 != 0.0F)
          {
            k = j;
            textSentenceBreak(i, k);
            label76:
            if ((k < paramInt2) && (paramArrayOfChar[k] == ' ')) {}
          }
          for (;;)
          {
            i = k;
            j = k;
            f1 = 0.0F;
            break;
            k++;
            break label76;
            do
            {
              k--;
              if (k == j) {
                return false;
              }
            } while (textWidthImpl(paramArrayOfChar, j, k) > paramFloat1);
            textSentenceBreak(i, k);
          }
        }
        if (k == paramInt2)
        {
          textSentenceBreak(i, k);
          k++;
        }
        else
        {
          f1 += f2 + paramFloat2;
          j = k + 1;
          k++;
        }
      }
      else
      {
        k++;
      }
    }
  }
  
  private void textSentenceBreak(int paramInt1, int paramInt2)
  {
    if (this.textBreakCount == this.textBreakStart.length)
    {
      this.textBreakStart = PApplet.expand(this.textBreakStart);
      this.textBreakStop = PApplet.expand(this.textBreakStop);
    }
    this.textBreakStart[this.textBreakCount] = paramInt1;
    this.textBreakStop[this.textBreakCount] = paramInt2;
    this.textBreakCount = (1 + this.textBreakCount);
  }
  
  protected void allocate() {}
  
  public final float alpha(int paramInt)
  {
    float f = 0xFF & paramInt >> 24;
    if (this.colorModeA == 255.0F) {
      return f;
    }
    return f / 255.0F * this.colorModeA;
  }
  
  public void ambient(float paramFloat)
  {
    colorCalc(paramFloat);
    ambientFromCalc();
  }
  
  public void ambient(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    ambientFromCalc();
  }
  
  public void ambient(int paramInt)
  {
    colorCalc(paramInt);
    ambientFromCalc();
  }
  
  protected void ambientFromCalc()
  {
    this.ambientColor = this.calcColor;
    this.ambientR = this.calcR;
    this.ambientG = this.calcG;
    this.ambientB = this.calcB;
    this.setAmbient = true;
  }
  
  public void ambientLight(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMethodWarning("ambientLight");
  }
  
  public void ambientLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMethodWarning("ambientLight");
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMissingWarning("applyMatrix");
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    showMissingWarning("applyMatrix");
  }
  
  public void applyMatrix(PMatrix2D paramPMatrix2D)
  {
    applyMatrix(paramPMatrix2D.m00, paramPMatrix2D.m01, paramPMatrix2D.m02, paramPMatrix2D.m10, paramPMatrix2D.m11, paramPMatrix2D.m12);
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
  
  public void arc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    arc(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, 0);
  }
  
  public void arc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt)
  {
    float f1 = paramFloat1;
    float f2 = paramFloat2;
    float f3 = paramFloat3;
    float f4 = paramFloat4;
    if (this.ellipseMode == 1)
    {
      f3 = paramFloat3 - paramFloat1;
      f4 = paramFloat4 - paramFloat2;
      if ((Float.isInfinite(paramFloat5)) || (Float.isInfinite(paramFloat6)) || (paramFloat6 <= paramFloat5)) {}
    }
    for (;;)
    {
      if (paramFloat5 >= 0.0F)
      {
        if (paramFloat6 - paramFloat5 > 6.283186F)
        {
          paramFloat5 = 0.0F;
          paramFloat6 = 6.283186F;
        }
        arcImpl(f1, f2, f3, f4, paramFloat5, paramFloat6, paramInt);
        return;
        if (this.ellipseMode == 2)
        {
          f1 = paramFloat1 - paramFloat3;
          f2 = paramFloat2 - paramFloat4;
          f3 = paramFloat3 * 2.0F;
          f4 = paramFloat4 * 2.0F;
          break;
        }
        if (this.ellipseMode != 3) {
          break;
        }
        f1 = paramFloat1 - paramFloat3 / 2.0F;
        f2 = paramFloat2 - paramFloat4 / 2.0F;
        break;
      }
      paramFloat5 += 6.283186F;
      paramFloat6 += 6.283186F;
    }
  }
  
  protected void arcImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt)
  {
    showMissingWarning("arc");
  }
  
  public void background(float paramFloat)
  {
    colorCalc(paramFloat);
    backgroundFromCalc();
  }
  
  public void background(float paramFloat1, float paramFloat2)
  {
    if (this.format == 1)
    {
      background(paramFloat1);
      return;
    }
    colorCalc(paramFloat1, paramFloat2);
    backgroundFromCalc();
  }
  
  public void background(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    backgroundFromCalc();
  }
  
  public void background(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    backgroundFromCalc();
  }
  
  public void background(int paramInt)
  {
    colorCalc(paramInt);
    backgroundFromCalc();
  }
  
  public void background(int paramInt, float paramFloat)
  {
    colorCalc(paramInt, paramFloat);
    backgroundFromCalc();
  }
  
  public void background(PImage paramPImage)
  {
    if ((paramPImage.width != this.width) || (paramPImage.height != this.height)) {
      throw new RuntimeException("background image must be the same size as your application");
    }
    if ((paramPImage.format != 1) && (paramPImage.format != 2)) {
      throw new RuntimeException("background images should be RGB or ARGB");
    }
    this.backgroundColor = 0;
    backgroundImpl(paramPImage);
  }
  
  protected void backgroundFromCalc()
  {
    this.backgroundR = this.calcR;
    this.backgroundG = this.calcG;
    this.backgroundB = this.calcB;
    float f;
    int i;
    if (this.format == 1)
    {
      f = this.colorModeA;
      this.backgroundA = f;
      this.backgroundRi = this.calcRi;
      this.backgroundGi = this.calcGi;
      this.backgroundBi = this.calcBi;
      if (this.format != 1) {
        break label119;
      }
      i = 255;
      label78:
      this.backgroundAi = i;
      if (this.format != 1) {
        break label127;
      }
    }
    label119:
    label127:
    for (boolean bool = false;; bool = this.calcAlpha)
    {
      this.backgroundAlpha = bool;
      this.backgroundColor = this.calcColor;
      backgroundImpl();
      return;
      f = this.calcA;
      break;
      i = this.calcAi;
      break label78;
    }
  }
  
  protected void backgroundImpl()
  {
    pushStyle();
    pushMatrix();
    resetMatrix();
    fill(this.backgroundColor);
    rect(0.0F, 0.0F, this.width, this.height);
    popMatrix();
    popStyle();
  }
  
  protected void backgroundImpl(PImage paramPImage)
  {
    set(0, 0, paramPImage);
  }
  
  public void beginCamera()
  {
    showMethodWarning("beginCamera");
  }
  
  public void beginContour()
  {
    showMissingWarning("beginContour");
  }
  
  public void beginDraw() {}
  
  public PGL beginPGL()
  {
    showMethodWarning("beginPGL");
    return null;
  }
  
  public void beginRaw(PGraphics paramPGraphics)
  {
    this.raw = paramPGraphics;
    paramPGraphics.beginDraw();
  }
  
  public void beginShape()
  {
    beginShape(20);
  }
  
  public void beginShape(int paramInt)
  {
    this.shape = paramInt;
  }
  
  public void bezier(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    beginShape();
    vertex(paramFloat1, paramFloat2);
    bezierVertex(paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
    endShape();
  }
  
  public void bezier(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
  {
    beginShape();
    vertex(paramFloat1, paramFloat2, paramFloat3);
    bezierVertex(paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12);
    endShape();
  }
  
  public void bezierDetail(int paramInt)
  {
    this.bezierDetail = paramInt;
    if (this.bezierDrawMatrix == null) {
      this.bezierDrawMatrix = new PMatrix3D();
    }
    splineForward(paramInt, this.bezierDrawMatrix);
    this.bezierDrawMatrix.apply(this.bezierBasisMatrix);
  }
  
  protected void bezierInit()
  {
    bezierDetail(this.bezierDetail);
    this.bezierInited = true;
  }
  
  protected void bezierInitCheck()
  {
    if (!this.bezierInited) {
      bezierInit();
    }
  }
  
  public float bezierPoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    float f = 1.0F - paramFloat5;
    return f * (f * (paramFloat1 * f)) + f * (f * (paramFloat5 * (3.0F * paramFloat2))) + f * (paramFloat5 * (paramFloat5 * (3.0F * paramFloat3))) + paramFloat5 * (paramFloat5 * (paramFloat4 * paramFloat5));
  }
  
  public float bezierTangent(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    return paramFloat5 * (3.0F * paramFloat5) * (paramFloat4 + (-paramFloat1 + 3.0F * paramFloat2 - 3.0F * paramFloat3)) + 6.0F * paramFloat5 * (paramFloat3 + (paramFloat1 - 2.0F * paramFloat2)) + 3.0F * (paramFloat2 + -paramFloat1);
  }
  
  public void bezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    bezierInitCheck();
    bezierVertexCheck();
    PMatrix3D localPMatrix3D = this.bezierDrawMatrix;
    float[] arrayOfFloat = this.vertices[(-1 + this.vertexCount)];
    float f1 = arrayOfFloat[0];
    float f2 = arrayOfFloat[1];
    float f3 = f1 * localPMatrix3D.m10 + paramFloat1 * localPMatrix3D.m11 + paramFloat3 * localPMatrix3D.m12 + paramFloat5 * localPMatrix3D.m13;
    float f4 = f1 * localPMatrix3D.m20 + paramFloat1 * localPMatrix3D.m21 + paramFloat3 * localPMatrix3D.m22 + paramFloat5 * localPMatrix3D.m23;
    float f5 = f1 * localPMatrix3D.m30 + paramFloat1 * localPMatrix3D.m31 + paramFloat3 * localPMatrix3D.m32 + paramFloat5 * localPMatrix3D.m33;
    float f6 = f2 * localPMatrix3D.m10 + paramFloat2 * localPMatrix3D.m11 + paramFloat4 * localPMatrix3D.m12 + paramFloat6 * localPMatrix3D.m13;
    float f7 = f2 * localPMatrix3D.m20 + paramFloat2 * localPMatrix3D.m21 + paramFloat4 * localPMatrix3D.m22 + paramFloat6 * localPMatrix3D.m23;
    float f8 = f2 * localPMatrix3D.m30 + paramFloat2 * localPMatrix3D.m31 + paramFloat4 * localPMatrix3D.m32 + paramFloat6 * localPMatrix3D.m33;
    for (int i = 0;; i++)
    {
      if (i >= this.bezierDetail) {
        return;
      }
      f1 += f3;
      f3 += f4;
      f4 += f5;
      f2 += f6;
      f6 += f7;
      f7 += f8;
      vertex(f1, f2);
    }
  }
  
  public void bezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    bezierInitCheck();
    bezierVertexCheck();
    PMatrix3D localPMatrix3D = this.bezierDrawMatrix;
    float[] arrayOfFloat = this.vertices[(-1 + this.vertexCount)];
    float f1 = arrayOfFloat[0];
    float f2 = arrayOfFloat[1];
    float f3 = arrayOfFloat[2];
    float f4 = f1 * localPMatrix3D.m10 + paramFloat1 * localPMatrix3D.m11 + paramFloat4 * localPMatrix3D.m12 + paramFloat7 * localPMatrix3D.m13;
    float f5 = f1 * localPMatrix3D.m20 + paramFloat1 * localPMatrix3D.m21 + paramFloat4 * localPMatrix3D.m22 + paramFloat7 * localPMatrix3D.m23;
    float f6 = f1 * localPMatrix3D.m30 + paramFloat1 * localPMatrix3D.m31 + paramFloat4 * localPMatrix3D.m32 + paramFloat7 * localPMatrix3D.m33;
    float f7 = f2 * localPMatrix3D.m10 + paramFloat2 * localPMatrix3D.m11 + paramFloat5 * localPMatrix3D.m12 + paramFloat8 * localPMatrix3D.m13;
    float f8 = f2 * localPMatrix3D.m20 + paramFloat2 * localPMatrix3D.m21 + paramFloat5 * localPMatrix3D.m22 + paramFloat8 * localPMatrix3D.m23;
    float f9 = f2 * localPMatrix3D.m30 + paramFloat2 * localPMatrix3D.m31 + paramFloat5 * localPMatrix3D.m32 + paramFloat8 * localPMatrix3D.m33;
    float f10 = f3 * localPMatrix3D.m10 + paramFloat3 * localPMatrix3D.m11 + paramFloat6 * localPMatrix3D.m12 + paramFloat9 * localPMatrix3D.m13;
    float f11 = f3 * localPMatrix3D.m20 + paramFloat3 * localPMatrix3D.m21 + paramFloat6 * localPMatrix3D.m22 + paramFloat9 * localPMatrix3D.m23;
    float f12 = f3 * localPMatrix3D.m30 + paramFloat3 * localPMatrix3D.m31 + paramFloat6 * localPMatrix3D.m32 + paramFloat9 * localPMatrix3D.m33;
    for (int i = 0;; i++)
    {
      if (i >= this.bezierDetail) {
        return;
      }
      f1 += f4;
      f4 += f5;
      f5 += f6;
      f2 += f7;
      f7 += f8;
      f8 += f9;
      f3 += f10;
      f10 += f11;
      f11 += f12;
      vertex(f1, f2, f3);
    }
  }
  
  protected void bezierVertexCheck()
  {
    bezierVertexCheck(this.shape, this.vertexCount);
  }
  
  protected void bezierVertexCheck(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt1 != 20)) {
      throw new RuntimeException("beginShape() or beginShape(POLYGON) must be used before bezierVertex() or quadraticVertex()");
    }
    if (paramInt2 == 0) {
      throw new RuntimeException("vertex() must be used at least oncebefore bezierVertex() or quadraticVertex()");
    }
  }
  
  public void blendMode(int paramInt)
  {
    showMissingWarning("blendMode");
  }
  
  public final float blue(int paramInt)
  {
    float f = paramInt & 0xFF;
    if (this.colorModeDefault) {
      return f;
    }
    return f / 255.0F * this.colorModeZ;
  }
  
  public void box(float paramFloat)
  {
    box(paramFloat, paramFloat, paramFloat);
  }
  
  public void box(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = -paramFloat1 / 2.0F;
    float f2 = paramFloat1 / 2.0F;
    float f3 = -paramFloat2 / 2.0F;
    float f4 = paramFloat2 / 2.0F;
    float f5 = -paramFloat3 / 2.0F;
    float f6 = paramFloat3 / 2.0F;
    beginShape(17);
    normal(0.0F, 0.0F, 1.0F);
    vertex(f1, f3, f5);
    vertex(f2, f3, f5);
    vertex(f2, f4, f5);
    vertex(f1, f4, f5);
    normal(1.0F, 0.0F, 0.0F);
    vertex(f2, f3, f5);
    vertex(f2, f3, f6);
    vertex(f2, f4, f6);
    vertex(f2, f4, f5);
    normal(0.0F, 0.0F, -1.0F);
    vertex(f2, f3, f6);
    vertex(f1, f3, f6);
    vertex(f1, f4, f6);
    vertex(f2, f4, f6);
    normal(-1.0F, 0.0F, 0.0F);
    vertex(f1, f3, f6);
    vertex(f1, f3, f5);
    vertex(f1, f4, f5);
    vertex(f1, f4, f6);
    normal(0.0F, 1.0F, 0.0F);
    vertex(f1, f3, f6);
    vertex(f2, f3, f6);
    vertex(f2, f3, f5);
    vertex(f1, f3, f5);
    normal(0.0F, -1.0F, 0.0F);
    vertex(f1, f4, f5);
    vertex(f2, f4, f5);
    vertex(f2, f4, f6);
    vertex(f1, f4, f6);
    endShape();
  }
  
  public void breakShape()
  {
    showWarning("This renderer cannot currently handle concave shapes, or shapes with holes.");
  }
  
  public final float brightness(int paramInt)
  {
    if (paramInt != this.cacheHsbKey)
    {
      Color.RGBToHSV(0xFF & paramInt >> 16, 0xFF & paramInt >> 8, paramInt & 0xFF, this.cacheHsbValue);
      this.cacheHsbKey = paramInt;
    }
    return this.cacheHsbValue[2] * this.colorModeZ;
  }
  
  public void camera()
  {
    showMissingWarning("camera");
  }
  
  public void camera(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    showMissingWarning("camera");
  }
  
  public boolean canDraw()
  {
    return true;
  }
  
  protected void checkSettings()
  {
    if (!this.settingsInited) {
      defaultSettings();
    }
  }
  
  public void clear()
  {
    background(0.0F, 0.0F, 0.0F, 0.0F);
  }
  
  public void clip(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (this.imageMode == 0)
    {
      if (paramFloat3 < 0.0F)
      {
        paramFloat1 += paramFloat3;
        paramFloat3 = -paramFloat3;
      }
      if (paramFloat4 < 0.0F)
      {
        paramFloat2 += paramFloat4;
        paramFloat4 = -paramFloat4;
      }
      clipImpl(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
    }
    do
    {
      return;
      if (this.imageMode == 1)
      {
        if (paramFloat3 < paramFloat1)
        {
          float f4 = paramFloat1;
          paramFloat1 = paramFloat3;
          paramFloat3 = f4;
        }
        if (paramFloat4 < paramFloat2)
        {
          float f3 = paramFloat2;
          paramFloat2 = paramFloat4;
          paramFloat4 = f3;
        }
        clipImpl(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
        return;
      }
    } while (this.imageMode != 3);
    if (paramFloat3 < 0.0F) {
      paramFloat3 = -paramFloat3;
    }
    if (paramFloat4 < 0.0F) {
      paramFloat4 = -paramFloat4;
    }
    float f1 = paramFloat1 - paramFloat3 / 2.0F;
    float f2 = paramFloat2 - paramFloat4 / 2.0F;
    clipImpl(f1, f2, f1 + paramFloat3, f2 + paramFloat4);
  }
  
  protected void clipImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    showMissingWarning("clip");
  }
  
  public final int color(float paramFloat)
  {
    colorCalc(paramFloat);
    return this.calcColor;
  }
  
  public final int color(float paramFloat1, float paramFloat2)
  {
    colorCalc(paramFloat1, paramFloat2);
    return this.calcColor;
  }
  
  public final int color(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    return this.calcColor;
  }
  
  public final int color(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    return this.calcColor;
  }
  
  public final int color(int paramInt)
  {
    if (((paramInt & 0xFF000000) == 0) && (paramInt <= this.colorModeX))
    {
      if (this.colorModeDefault)
      {
        if (paramInt > 255) {
          paramInt = 255;
        }
        for (;;)
        {
          return paramInt | 0xFF000000 | paramInt << 16 | paramInt << 8;
          if (paramInt < 0) {
            paramInt = 0;
          }
        }
      }
      colorCalc(paramInt);
    }
    for (;;)
    {
      return this.calcColor;
      colorCalcARGB(paramInt, this.colorModeA);
    }
  }
  
  public final int color(int paramInt, float paramFloat)
  {
    if (((0xFF000000 & paramInt) == 0) && (paramInt <= this.colorModeX)) {
      colorCalc(paramInt, paramFloat);
    }
    for (;;)
    {
      return this.calcColor;
      colorCalcARGB(paramInt, paramFloat);
    }
  }
  
  public final int color(int paramInt1, int paramInt2)
  {
    if (this.colorModeDefault)
    {
      if (paramInt1 > 255)
      {
        paramInt1 = 255;
        if (paramInt2 <= 255) {
          break label59;
        }
        paramInt2 = 255;
      }
      for (;;)
      {
        return paramInt1 | (paramInt2 & 0xFF) << 24 | paramInt1 << 16 | paramInt1 << 8;
        if (paramInt1 >= 0) {
          break;
        }
        paramInt1 = 0;
        break;
        label59:
        if (paramInt2 < 0) {
          paramInt2 = 0;
        }
      }
    }
    colorCalc(paramInt1, paramInt2);
    return this.calcColor;
  }
  
  public final int color(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.colorModeDefault)
    {
      if (paramInt1 > 255)
      {
        paramInt1 = 255;
        if (paramInt2 <= 255) {
          break label65;
        }
        paramInt2 = 255;
        label29:
        if (paramInt3 <= 255) {
          break label74;
        }
        paramInt3 = 255;
      }
      for (;;)
      {
        return paramInt3 | 0xFF000000 | paramInt1 << 16 | paramInt2 << 8;
        if (paramInt1 >= 0) {
          break;
        }
        paramInt1 = 0;
        break;
        label65:
        if (paramInt2 >= 0) {
          break label29;
        }
        paramInt2 = 0;
        break label29;
        label74:
        if (paramInt3 < 0) {
          paramInt3 = 0;
        }
      }
    }
    colorCalc(paramInt1, paramInt2, paramInt3);
    return this.calcColor;
  }
  
  public final int color(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.colorModeDefault)
    {
      if (paramInt4 > 255)
      {
        paramInt4 = 255;
        if (paramInt1 <= 255) {
          break label82;
        }
        paramInt1 = 255;
        label31:
        if (paramInt2 <= 255) {
          break label91;
        }
        paramInt2 = 255;
        label42:
        if (paramInt3 <= 255) {
          break label100;
        }
        paramInt3 = 255;
      }
      for (;;)
      {
        return paramInt3 | paramInt4 << 24 | paramInt1 << 16 | paramInt2 << 8;
        if (paramInt4 >= 0) {
          break;
        }
        paramInt4 = 0;
        break;
        label82:
        if (paramInt1 >= 0) {
          break label31;
        }
        paramInt1 = 0;
        break label31;
        label91:
        if (paramInt2 >= 0) {
          break label42;
        }
        paramInt2 = 0;
        break label42;
        label100:
        if (paramInt3 < 0) {
          paramInt3 = 0;
        }
      }
    }
    colorCalc(paramInt1, paramInt2, paramInt3, paramInt4);
    return this.calcColor;
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
        break label170;
      }
    }
    label170:
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
        break label134;
      }
    }
    for (;;)
    {
      this.colorModeDefault = i;
      return;
      bool = i;
      break;
      label134:
      i = 0;
    }
  }
  
  public PShape createShape()
  {
    showMissingWarning("createShape");
    return null;
  }
  
  public PShape createShape(int paramInt)
  {
    showMissingWarning("createShape");
    return null;
  }
  
  public PShape createShape(int paramInt, float... paramVarArgs)
  {
    showMissingWarning("createShape");
    return null;
  }
  
  public PShape createShape(PShape paramPShape)
  {
    showMissingWarning("createShape");
    return null;
  }
  
  public void curve(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    beginShape();
    curveVertex(paramFloat1, paramFloat2);
    curveVertex(paramFloat3, paramFloat4);
    curveVertex(paramFloat5, paramFloat6);
    curveVertex(paramFloat7, paramFloat8);
    endShape();
  }
  
  public void curve(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
  {
    beginShape();
    curveVertex(paramFloat1, paramFloat2, paramFloat3);
    curveVertex(paramFloat4, paramFloat5, paramFloat6);
    curveVertex(paramFloat7, paramFloat8, paramFloat9);
    curveVertex(paramFloat10, paramFloat11, paramFloat12);
    endShape();
  }
  
  public void curveDetail(int paramInt)
  {
    this.curveDetail = paramInt;
    curveInit();
  }
  
  protected void curveInit()
  {
    if (this.curveDrawMatrix == null)
    {
      this.curveBasisMatrix = new PMatrix3D();
      this.curveDrawMatrix = new PMatrix3D();
      this.curveInited = true;
    }
    float f = this.curveTightness;
    this.curveBasisMatrix.set((f - 1.0F) / 2.0F, (3.0F + f) / 2.0F, (-3.0F - f) / 2.0F, (1.0F - f) / 2.0F, 1.0F - f, (-5.0F - f) / 2.0F, 2.0F + f, (f - 1.0F) / 2.0F, (f - 1.0F) / 2.0F, 0.0F, (1.0F - f) / 2.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
    splineForward(this.curveDetail, this.curveDrawMatrix);
    if (this.bezierBasisInverse == null)
    {
      this.bezierBasisInverse = this.bezierBasisMatrix.get();
      this.bezierBasisInverse.invert();
      this.curveToBezierMatrix = new PMatrix3D();
    }
    this.curveToBezierMatrix.set(this.curveBasisMatrix);
    this.curveToBezierMatrix.preApply(this.bezierBasisInverse);
    this.curveDrawMatrix.apply(this.curveBasisMatrix);
  }
  
  protected void curveInitCheck()
  {
    if (!this.curveInited) {
      curveInit();
    }
  }
  
  public float curvePoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    curveInitCheck();
    float f1 = paramFloat5 * paramFloat5;
    float f2 = paramFloat5 * f1;
    PMatrix3D localPMatrix3D = this.curveBasisMatrix;
    return paramFloat1 * (f2 * localPMatrix3D.m00 + f1 * localPMatrix3D.m10 + paramFloat5 * localPMatrix3D.m20 + localPMatrix3D.m30) + paramFloat2 * (f2 * localPMatrix3D.m01 + f1 * localPMatrix3D.m11 + paramFloat5 * localPMatrix3D.m21 + localPMatrix3D.m31) + paramFloat3 * (f2 * localPMatrix3D.m02 + f1 * localPMatrix3D.m12 + paramFloat5 * localPMatrix3D.m22 + localPMatrix3D.m32) + paramFloat4 * (f2 * localPMatrix3D.m03 + f1 * localPMatrix3D.m13 + paramFloat5 * localPMatrix3D.m23 + localPMatrix3D.m33);
  }
  
  public float curveTangent(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    curveInitCheck();
    float f1 = 3.0F * (paramFloat5 * paramFloat5);
    float f2 = paramFloat5 * 2.0F;
    PMatrix3D localPMatrix3D = this.curveBasisMatrix;
    return paramFloat1 * (f1 * localPMatrix3D.m00 + f2 * localPMatrix3D.m10 + localPMatrix3D.m20) + paramFloat2 * (f1 * localPMatrix3D.m01 + f2 * localPMatrix3D.m11 + localPMatrix3D.m21) + paramFloat3 * (f1 * localPMatrix3D.m02 + f2 * localPMatrix3D.m12 + localPMatrix3D.m22) + paramFloat4 * (f1 * localPMatrix3D.m03 + f2 * localPMatrix3D.m13 + localPMatrix3D.m23);
  }
  
  public void curveTightness(float paramFloat)
  {
    this.curveTightness = paramFloat;
    curveInit();
  }
  
  public void curveVertex(float paramFloat1, float paramFloat2)
  {
    curveVertexCheck();
    float[] arrayOfFloat = this.curveVertices[this.curveVertexCount];
    arrayOfFloat[0] = paramFloat1;
    arrayOfFloat[1] = paramFloat2;
    this.curveVertexCount = (1 + this.curveVertexCount);
    if (this.curveVertexCount > 3) {
      curveVertexSegment(this.curveVertices[(-4 + this.curveVertexCount)][0], this.curveVertices[(-4 + this.curveVertexCount)][1], this.curveVertices[(-3 + this.curveVertexCount)][0], this.curveVertices[(-3 + this.curveVertexCount)][1], this.curveVertices[(-2 + this.curveVertexCount)][0], this.curveVertices[(-2 + this.curveVertexCount)][1], this.curveVertices[(-1 + this.curveVertexCount)][0], this.curveVertices[(-1 + this.curveVertexCount)][1]);
    }
  }
  
  public void curveVertex(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    curveVertexCheck();
    float[] arrayOfFloat = this.curveVertices[this.curveVertexCount];
    arrayOfFloat[0] = paramFloat1;
    arrayOfFloat[1] = paramFloat2;
    arrayOfFloat[2] = paramFloat3;
    this.curveVertexCount = (1 + this.curveVertexCount);
    if (this.curveVertexCount > 3) {
      curveVertexSegment(this.curveVertices[(-4 + this.curveVertexCount)][0], this.curveVertices[(-4 + this.curveVertexCount)][1], this.curveVertices[(-4 + this.curveVertexCount)][2], this.curveVertices[(-3 + this.curveVertexCount)][0], this.curveVertices[(-3 + this.curveVertexCount)][1], this.curveVertices[(-3 + this.curveVertexCount)][2], this.curveVertices[(-2 + this.curveVertexCount)][0], this.curveVertices[(-2 + this.curveVertexCount)][1], this.curveVertices[(-2 + this.curveVertexCount)][2], this.curveVertices[(-1 + this.curveVertexCount)][0], this.curveVertices[(-1 + this.curveVertexCount)][1], this.curveVertices[(-1 + this.curveVertexCount)][2]);
    }
  }
  
  protected void curveVertexCheck()
  {
    curveVertexCheck(this.shape);
  }
  
  protected void curveVertexCheck(int paramInt)
  {
    if (paramInt != 20) {
      throw new RuntimeException("You must use beginShape() or beginShape(POLYGON) before curveVertex()");
    }
    if (this.curveVertices == null)
    {
      int[] arrayOfInt1 = { 128, 3 };
      this.curveVertices = ((float[][])Array.newInstance(Float.TYPE, arrayOfInt1));
    }
    if (this.curveVertexCount == this.curveVertices.length)
    {
      int[] arrayOfInt2 = { this.curveVertexCount << 1, 3 };
      float[][] arrayOfFloat = (float[][])Array.newInstance(Float.TYPE, arrayOfInt2);
      System.arraycopy(this.curveVertices, 0, arrayOfFloat, 0, this.curveVertexCount);
      this.curveVertices = arrayOfFloat;
    }
    curveInitCheck();
  }
  
  protected void curveVertexSegment(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    float f1 = paramFloat3;
    float f2 = paramFloat4;
    PMatrix3D localPMatrix3D = this.curveDrawMatrix;
    float f3 = paramFloat1 * localPMatrix3D.m10 + paramFloat3 * localPMatrix3D.m11 + paramFloat5 * localPMatrix3D.m12 + paramFloat7 * localPMatrix3D.m13;
    float f4 = paramFloat1 * localPMatrix3D.m20 + paramFloat3 * localPMatrix3D.m21 + paramFloat5 * localPMatrix3D.m22 + paramFloat7 * localPMatrix3D.m23;
    float f5 = paramFloat1 * localPMatrix3D.m30 + paramFloat3 * localPMatrix3D.m31 + paramFloat5 * localPMatrix3D.m32 + paramFloat7 * localPMatrix3D.m33;
    float f6 = paramFloat2 * localPMatrix3D.m10 + paramFloat4 * localPMatrix3D.m11 + paramFloat6 * localPMatrix3D.m12 + paramFloat8 * localPMatrix3D.m13;
    float f7 = paramFloat2 * localPMatrix3D.m20 + paramFloat4 * localPMatrix3D.m21 + paramFloat6 * localPMatrix3D.m22 + paramFloat8 * localPMatrix3D.m23;
    float f8 = paramFloat2 * localPMatrix3D.m30 + paramFloat4 * localPMatrix3D.m31 + paramFloat6 * localPMatrix3D.m32 + paramFloat8 * localPMatrix3D.m33;
    int i = this.curveVertexCount;
    vertex(f1, f2);
    for (int j = 0;; j++)
    {
      if (j >= this.curveDetail)
      {
        this.curveVertexCount = i;
        return;
      }
      f1 += f3;
      f3 += f4;
      f4 += f5;
      f2 += f6;
      f6 += f7;
      f7 += f8;
      vertex(f1, f2);
    }
  }
  
  protected void curveVertexSegment(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
  {
    float f1 = paramFloat4;
    float f2 = paramFloat5;
    float f3 = paramFloat6;
    PMatrix3D localPMatrix3D = this.curveDrawMatrix;
    float f4 = paramFloat1 * localPMatrix3D.m10 + paramFloat4 * localPMatrix3D.m11 + paramFloat7 * localPMatrix3D.m12 + paramFloat10 * localPMatrix3D.m13;
    float f5 = paramFloat1 * localPMatrix3D.m20 + paramFloat4 * localPMatrix3D.m21 + paramFloat7 * localPMatrix3D.m22 + paramFloat10 * localPMatrix3D.m23;
    float f6 = paramFloat1 * localPMatrix3D.m30 + paramFloat4 * localPMatrix3D.m31 + paramFloat7 * localPMatrix3D.m32 + paramFloat10 * localPMatrix3D.m33;
    float f7 = paramFloat2 * localPMatrix3D.m10 + paramFloat5 * localPMatrix3D.m11 + paramFloat8 * localPMatrix3D.m12 + paramFloat11 * localPMatrix3D.m13;
    float f8 = paramFloat2 * localPMatrix3D.m20 + paramFloat5 * localPMatrix3D.m21 + paramFloat8 * localPMatrix3D.m22 + paramFloat11 * localPMatrix3D.m23;
    float f9 = paramFloat2 * localPMatrix3D.m30 + paramFloat5 * localPMatrix3D.m31 + paramFloat8 * localPMatrix3D.m32 + paramFloat11 * localPMatrix3D.m33;
    int i = this.curveVertexCount;
    float f10 = paramFloat3 * localPMatrix3D.m10 + paramFloat6 * localPMatrix3D.m11 + paramFloat9 * localPMatrix3D.m12 + paramFloat12 * localPMatrix3D.m13;
    float f11 = paramFloat3 * localPMatrix3D.m20 + paramFloat6 * localPMatrix3D.m21 + paramFloat9 * localPMatrix3D.m22 + paramFloat12 * localPMatrix3D.m23;
    float f12 = paramFloat3 * localPMatrix3D.m30 + paramFloat6 * localPMatrix3D.m31 + paramFloat9 * localPMatrix3D.m32 + paramFloat12 * localPMatrix3D.m33;
    vertex(f1, f2, f3);
    for (int j = 0;; j++)
    {
      if (j >= this.curveDetail)
      {
        this.curveVertexCount = i;
        return;
      }
      f1 += f4;
      f4 += f5;
      f5 += f6;
      f2 += f7;
      f7 += f8;
      f8 += f9;
      f3 += f10;
      f10 += f11;
      f11 += f12;
      vertex(f1, f2, f3);
    }
  }
  
  protected void defaultFontOrDeath(String paramString)
  {
    defaultFontOrDeath(paramString, 12.0F);
  }
  
  protected void defaultFontOrDeath(String paramString, float paramFloat)
  {
    if (this.parent != null)
    {
      this.textFont = this.parent.createDefaultFont(paramFloat);
      return;
    }
    throw new RuntimeException("Use textFont() before " + paramString + "()");
  }
  
  protected void defaultSettings()
  {
    smooth();
    colorMode(1, 255.0F);
    fill(255);
    stroke(0);
    strokeWeight(1.0F);
    strokeJoin(8);
    strokeCap(2);
    this.shape = 0;
    rectMode(0);
    ellipseMode(3);
    this.autoNormal = true;
    this.textFont = null;
    this.textSize = 12.0F;
    this.textLeading = 14.0F;
    this.textAlign = 21;
    this.textMode = 4;
    if (this.primarySurface) {
      background(this.backgroundColor);
    }
    this.settingsInited = true;
  }
  
  public void directionalLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMethodWarning("directionalLight");
  }
  
  public boolean displayable()
  {
    return true;
  }
  
  public void dispose() {}
  
  public void edge(boolean paramBoolean)
  {
    this.edge = paramBoolean;
  }
  
  public void ellipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramFloat1;
    float f2 = paramFloat2;
    float f3 = paramFloat3;
    float f4 = paramFloat4;
    if (this.ellipseMode == 1)
    {
      f3 = paramFloat3 - paramFloat1;
      f4 = paramFloat4 - paramFloat2;
    }
    for (;;)
    {
      if (f3 < 0.0F)
      {
        f1 += f3;
        f3 = -f3;
      }
      if (f4 < 0.0F)
      {
        f2 += f4;
        f4 = -f4;
      }
      ellipseImpl(f1, f2, f3, f4);
      return;
      if (this.ellipseMode == 2)
      {
        f1 = paramFloat1 - paramFloat3;
        f2 = paramFloat2 - paramFloat4;
        f3 = paramFloat3 * 2.0F;
        f4 = paramFloat4 * 2.0F;
      }
      else if (this.ellipseMode == 3)
      {
        f1 = paramFloat1 - paramFloat3 / 2.0F;
        f2 = paramFloat2 - paramFloat4 / 2.0F;
      }
    }
  }
  
  protected void ellipseImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {}
  
  public void ellipseMode(int paramInt)
  {
    this.ellipseMode = paramInt;
  }
  
  public void emissive(float paramFloat)
  {
    colorCalc(paramFloat);
    emissiveFromCalc();
  }
  
  public void emissive(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    emissiveFromCalc();
  }
  
  public void emissive(int paramInt)
  {
    colorCalc(paramInt);
    emissiveFromCalc();
  }
  
  protected void emissiveFromCalc()
  {
    this.emissiveColor = this.calcColor;
    this.emissiveR = this.calcR;
    this.emissiveG = this.calcG;
    this.emissiveB = this.calcB;
  }
  
  public void endCamera()
  {
    showMethodWarning("endCamera");
  }
  
  public void endContour()
  {
    showMissingWarning("endContour");
  }
  
  public void endDraw() {}
  
  public void endPGL()
  {
    showMethodWarning("endPGL");
  }
  
  public void endRaw()
  {
    if (this.raw != null)
    {
      flush();
      this.raw.endDraw();
      this.raw.dispose();
      this.raw = null;
    }
  }
  
  public void endShape()
  {
    endShape(1);
  }
  
  public void endShape(int paramInt) {}
  
  public void fill(float paramFloat)
  {
    colorCalc(paramFloat);
    fillFromCalc();
  }
  
  public void fill(float paramFloat1, float paramFloat2)
  {
    colorCalc(paramFloat1, paramFloat2);
    fillFromCalc();
  }
  
  public void fill(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    fillFromCalc();
  }
  
  public void fill(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    fillFromCalc();
  }
  
  public void fill(int paramInt)
  {
    colorCalc(paramInt);
    fillFromCalc();
  }
  
  public void fill(int paramInt, float paramFloat)
  {
    colorCalc(paramInt, paramFloat);
    fillFromCalc();
  }
  
  protected void fillFromCalc()
  {
    this.fill = true;
    this.fillR = this.calcR;
    this.fillG = this.calcG;
    this.fillB = this.calcB;
    this.fillA = this.calcA;
    this.fillRi = this.calcRi;
    this.fillGi = this.calcGi;
    this.fillBi = this.calcBi;
    this.fillAi = this.calcAi;
    this.fillColor = this.calcColor;
    this.fillAlpha = this.calcAlpha;
  }
  
  public void filter(PShader paramPShader)
  {
    showMissingWarning("filter");
  }
  
  public void flush() {}
  
  public void frustum(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMethodWarning("frustum");
  }
  
  public Object getCache(PImage paramPImage)
  {
    return this.cacheMap.get(paramPImage);
  }
  
  public PMatrix2D getMatrix(PMatrix2D paramPMatrix2D)
  {
    showMissingWarning("getMatrix");
    return null;
  }
  
  public PMatrix3D getMatrix(PMatrix3D paramPMatrix3D)
  {
    showMissingWarning("getMatrix");
    return null;
  }
  
  public PMatrix getMatrix()
  {
    showMissingWarning("getMatrix");
    return null;
  }
  
  public PGraphics getRaw()
  {
    return this.raw;
  }
  
  public PShader getShader(int paramInt)
  {
    showMissingWarning("getShader");
    return null;
  }
  
  public PStyle getStyle()
  {
    return getStyle(null);
  }
  
  public PStyle getStyle(PStyle paramPStyle)
  {
    if (paramPStyle == null) {
      paramPStyle = new PStyle();
    }
    paramPStyle.imageMode = this.imageMode;
    paramPStyle.rectMode = this.rectMode;
    paramPStyle.ellipseMode = this.ellipseMode;
    paramPStyle.shapeMode = this.shapeMode;
    paramPStyle.colorMode = this.colorMode;
    paramPStyle.colorModeX = this.colorModeX;
    paramPStyle.colorModeY = this.colorModeY;
    paramPStyle.colorModeZ = this.colorModeZ;
    paramPStyle.colorModeA = this.colorModeA;
    paramPStyle.tint = this.tint;
    paramPStyle.tintColor = this.tintColor;
    paramPStyle.fill = this.fill;
    paramPStyle.fillColor = this.fillColor;
    paramPStyle.stroke = this.stroke;
    paramPStyle.strokeColor = this.strokeColor;
    paramPStyle.strokeWeight = this.strokeWeight;
    paramPStyle.strokeCap = this.strokeCap;
    paramPStyle.strokeJoin = this.strokeJoin;
    paramPStyle.ambientR = this.ambientR;
    paramPStyle.ambientG = this.ambientG;
    paramPStyle.ambientB = this.ambientB;
    paramPStyle.specularR = this.specularR;
    paramPStyle.specularG = this.specularG;
    paramPStyle.specularB = this.specularB;
    paramPStyle.emissiveR = this.emissiveR;
    paramPStyle.emissiveG = this.emissiveG;
    paramPStyle.emissiveB = this.emissiveB;
    paramPStyle.shininess = this.shininess;
    paramPStyle.textFont = this.textFont;
    paramPStyle.textAlign = this.textAlign;
    paramPStyle.textAlignY = this.textAlignY;
    paramPStyle.textMode = this.textMode;
    paramPStyle.textSize = this.textSize;
    paramPStyle.textLeading = this.textLeading;
    return paramPStyle;
  }
  
  public final float green(int paramInt)
  {
    float f = 0xFF & paramInt >> 8;
    if (this.colorModeDefault) {
      return f;
    }
    return f / 255.0F * this.colorModeY;
  }
  
  public boolean haveRaw()
  {
    return this.raw != null;
  }
  
  public void hint(int paramInt)
  {
    if (paramInt > 0)
    {
      this.hints[paramInt] = true;
      return;
    }
    this.hints[(-paramInt)] = false;
  }
  
  public final float hue(int paramInt)
  {
    if (paramInt != this.cacheHsbKey)
    {
      Color.RGBToHSV(0xFF & paramInt >> 16, 0xFF & paramInt >> 8, paramInt & 0xFF, this.cacheHsbValue);
      this.cacheHsbKey = paramInt;
    }
    return this.cacheHsbValue[0] / 360.0F * this.colorModeX;
  }
  
  public void image(PImage paramPImage, float paramFloat1, float paramFloat2)
  {
    if ((paramPImage.width == -1) || (paramPImage.height == -1)) {}
    do
    {
      do
      {
        return;
      } while ((paramPImage.width == 0) || (paramPImage.height == 0));
      if ((this.imageMode == 0) || (this.imageMode == 1))
      {
        imageImpl(paramPImage, paramFloat1, paramFloat2, paramFloat1 + paramPImage.width, paramFloat2 + paramPImage.height, 0, 0, paramPImage.width, paramPImage.height);
        return;
      }
    } while (this.imageMode != 3);
    float f1 = paramFloat1 - paramPImage.width / 2;
    float f2 = paramFloat2 - paramPImage.height / 2;
    imageImpl(paramPImage, f1, f2, f1 + paramPImage.width, f2 + paramPImage.height, 0, 0, paramPImage.width, paramPImage.height);
  }
  
  public void image(PImage paramPImage, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    image(paramPImage, paramFloat1, paramFloat2, paramFloat3, paramFloat4, 0, 0, paramPImage.width, paramPImage.height);
  }
  
  public void image(PImage paramPImage, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramPImage.width == -1) || (paramPImage.height == -1)) {}
    do
    {
      return;
      if (this.imageMode == 0)
      {
        if (paramFloat3 < 0.0F)
        {
          paramFloat1 += paramFloat3;
          paramFloat3 = -paramFloat3;
        }
        if (paramFloat4 < 0.0F)
        {
          paramFloat2 += paramFloat4;
          paramFloat4 = -paramFloat4;
        }
        float f5 = paramFloat1 + paramFloat3;
        float f6 = paramFloat2 + paramFloat4;
        imageImpl(paramPImage, paramFloat1, paramFloat2, f5, f6, paramInt1, paramInt2, paramInt3, paramInt4);
        return;
      }
      if (this.imageMode == 1)
      {
        if (paramFloat3 < paramFloat1)
        {
          float f4 = paramFloat1;
          paramFloat1 = paramFloat3;
          paramFloat3 = f4;
        }
        if (paramFloat4 < paramFloat2)
        {
          float f3 = paramFloat2;
          paramFloat2 = paramFloat4;
          paramFloat4 = f3;
        }
        imageImpl(paramPImage, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1, paramInt2, paramInt3, paramInt4);
        return;
      }
    } while (this.imageMode != 3);
    if (paramFloat3 < 0.0F) {
      paramFloat3 = -paramFloat3;
    }
    if (paramFloat4 < 0.0F) {
      paramFloat4 = -paramFloat4;
    }
    float f1 = paramFloat1 - paramFloat3 / 2.0F;
    float f2 = paramFloat2 - paramFloat4 / 2.0F;
    imageImpl(paramPImage, f1, f2, f1 + paramFloat3, f2 + paramFloat4, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void imageImpl(PImage paramPImage, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    boolean bool1 = this.stroke;
    boolean bool2 = this.fill;
    int i = this.textureMode;
    this.stroke = false;
    this.fill = true;
    this.textureMode = 2;
    float f1 = this.fillR;
    float f2 = this.fillG;
    float f3 = this.fillB;
    float f4 = this.fillA;
    if (this.tint)
    {
      this.fillR = this.tintR;
      this.fillG = this.tintG;
      this.fillB = this.tintB;
    }
    for (this.fillA = this.tintA;; this.fillA = 1.0F)
    {
      beginShape(17);
      texture(paramPImage);
      vertex(paramFloat1, paramFloat2, paramInt1, paramInt2);
      vertex(paramFloat1, paramFloat4, paramInt1, paramInt4);
      vertex(paramFloat3, paramFloat4, paramInt3, paramInt4);
      vertex(paramFloat3, paramFloat2, paramInt3, paramInt2);
      endShape();
      this.stroke = bool1;
      this.fill = bool2;
      this.textureMode = i;
      this.fillR = f1;
      this.fillG = f2;
      this.fillB = f3;
      this.fillA = f4;
      return;
      this.fillR = 1.0F;
      this.fillG = 1.0F;
      this.fillB = 1.0F;
    }
  }
  
  public void imageMode(int paramInt)
  {
    if ((paramInt == 0) || (paramInt == 1) || (paramInt == 3))
    {
      this.imageMode = paramInt;
      return;
    }
    throw new RuntimeException("imageMode() only works with CORNER, CORNERS, or CENTER");
  }
  
  public Object initCache(PImage paramPImage)
  {
    return null;
  }
  
  public boolean is2D()
  {
    return true;
  }
  
  public boolean is3D()
  {
    return false;
  }
  
  public boolean isGL()
  {
    return false;
  }
  
  public int lerpColor(int paramInt1, int paramInt2, float paramFloat)
  {
    return lerpColor(paramInt1, paramInt2, paramFloat, this.colorMode);
  }
  
  public void lightFalloff(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMethodWarning("lightFalloff");
  }
  
  public void lightSpecular(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMethodWarning("lightSpecular");
  }
  
  public void lights()
  {
    showMethodWarning("lights");
  }
  
  public void line(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    beginShape(5);
    vertex(paramFloat1, paramFloat2);
    vertex(paramFloat3, paramFloat4);
    endShape();
  }
  
  public void line(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    beginShape(5);
    vertex(paramFloat1, paramFloat2, paramFloat3);
    vertex(paramFloat4, paramFloat5, paramFloat6);
    endShape();
  }
  
  public PShader loadShader(String paramString)
  {
    showMissingWarning("loadShader");
    return null;
  }
  
  public PShader loadShader(String paramString1, String paramString2)
  {
    showMissingWarning("loadShader");
    return null;
  }
  
  public PShape loadShape(String paramString)
  {
    showMissingWarning("loadShape");
    return null;
  }
  
  public float modelX(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMissingWarning("modelX");
    return 0.0F;
  }
  
  public float modelY(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMissingWarning("modelY");
    return 0.0F;
  }
  
  public float modelZ(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMissingWarning("modelZ");
    return 0.0F;
  }
  
  public void noClip()
  {
    showMissingWarning("noClip");
  }
  
  public void noFill()
  {
    this.fill = false;
  }
  
  public void noLights()
  {
    showMethodWarning("noLights");
  }
  
  public void noSmooth()
  {
    this.smooth = false;
  }
  
  public void noStroke()
  {
    this.stroke = false;
  }
  
  public void noTexture()
  {
    this.textureImage = null;
  }
  
  public void noTint()
  {
    this.tint = false;
  }
  
  public void normal(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.normalX = paramFloat1;
    this.normalY = paramFloat2;
    this.normalZ = paramFloat3;
    if (this.shape != 0)
    {
      if (this.normalMode != 0) {
        break label35;
      }
      this.normalMode = 1;
    }
    label35:
    while (this.normalMode != 1) {
      return;
    }
    this.normalMode = 2;
  }
  
  public void ortho()
  {
    showMissingWarning("ortho");
  }
  
  public void ortho(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    showMissingWarning("ortho");
  }
  
  public void ortho(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMissingWarning("ortho");
  }
  
  public void perspective()
  {
    showMissingWarning("perspective");
  }
  
  public void perspective(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    showMissingWarning("perspective");
  }
  
  public void point(float paramFloat1, float paramFloat2)
  {
    beginShape(3);
    vertex(paramFloat1, paramFloat2);
    endShape();
  }
  
  public void point(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    beginShape(3);
    vertex(paramFloat1, paramFloat2, paramFloat3);
    endShape();
  }
  
  public void pointLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMethodWarning("pointLight");
  }
  
  public void popMatrix()
  {
    showMethodWarning("popMatrix");
  }
  
  public void popStyle()
  {
    if (this.styleStackDepth == 0) {
      throw new RuntimeException("Too many popStyle() without enough pushStyle()");
    }
    this.styleStackDepth = (-1 + this.styleStackDepth);
    style(this.styleStack[this.styleStackDepth]);
  }
  
  public void printCamera()
  {
    showMethodWarning("printCamera");
  }
  
  public void printMatrix()
  {
    showMethodWarning("printMatrix");
  }
  
  public void printProjection()
  {
    showMethodWarning("printCamera");
  }
  
  public void pushMatrix()
  {
    showMethodWarning("pushMatrix");
  }
  
  public void pushStyle()
  {
    if (this.styleStackDepth == this.styleStack.length) {
      this.styleStack = ((PStyle[])PApplet.expand(this.styleStack));
    }
    if (this.styleStack[this.styleStackDepth] == null) {
      this.styleStack[this.styleStackDepth] = new PStyle();
    }
    PStyle[] arrayOfPStyle = this.styleStack;
    int i = this.styleStackDepth;
    this.styleStackDepth = (i + 1);
    getStyle(arrayOfPStyle[i]);
  }
  
  public void quad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    beginShape(17);
    vertex(paramFloat1, paramFloat2);
    vertex(paramFloat3, paramFloat4);
    vertex(paramFloat5, paramFloat6);
    vertex(paramFloat7, paramFloat8);
    endShape();
  }
  
  public void quadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    bezierVertexCheck();
    float[] arrayOfFloat = this.vertices[(-1 + this.vertexCount)];
    float f1 = arrayOfFloat[0];
    float f2 = arrayOfFloat[1];
    bezierVertex(f1 + 2.0F * (paramFloat1 - f1) / 3.0F, f2 + 2.0F * (paramFloat2 - f2) / 3.0F, paramFloat3 + 2.0F * (paramFloat1 - paramFloat3) / 3.0F, paramFloat4 + 2.0F * (paramFloat2 - paramFloat4) / 3.0F, paramFloat3, paramFloat4);
  }
  
  public void quadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    bezierVertexCheck();
    float[] arrayOfFloat = this.vertices[(-1 + this.vertexCount)];
    float f1 = arrayOfFloat[0];
    float f2 = arrayOfFloat[1];
    float f3 = arrayOfFloat[2];
    bezierVertex(f1 + 2.0F * (paramFloat1 - f1) / 3.0F, f2 + 2.0F * (paramFloat2 - f2) / 3.0F, f3 + 2.0F * (paramFloat3 - f3) / 3.0F, paramFloat4 + 2.0F * (paramFloat1 - paramFloat4) / 3.0F, paramFloat5 + 2.0F * (paramFloat2 - paramFloat5) / 3.0F, paramFloat6 + 2.0F * (paramFloat3 - paramFloat6) / 3.0F, paramFloat4, paramFloat5, paramFloat6);
  }
  
  protected void reapplySettings()
  {
    if (!this.settingsInited) {
      return;
    }
    colorMode(this.colorMode, this.colorModeX, this.colorModeY, this.colorModeZ);
    if (this.fill)
    {
      fill(this.fillColor);
      if (!this.stroke) {
        break label173;
      }
      stroke(this.strokeColor);
      strokeWeight(this.strokeWeight);
      strokeCap(this.strokeCap);
      strokeJoin(this.strokeJoin);
      label82:
      if (!this.tint) {
        break label180;
      }
      tint(this.tintColor);
      label97:
      if (!this.smooth) {
        break label187;
      }
      smooth();
    }
    for (;;)
    {
      if (this.textFont != null)
      {
        float f = this.textLeading;
        textFont(this.textFont, this.textSize);
        textLeading(f);
      }
      textMode(this.textMode);
      textAlign(this.textAlign, this.textAlignY);
      background(this.backgroundColor);
      return;
      noFill();
      break;
      label173:
      noStroke();
      break label82;
      label180:
      noTint();
      break label97;
      label187:
      noSmooth();
    }
  }
  
  public void rect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    switch (this.rectMode)
    {
    }
    for (;;)
    {
      if (paramFloat1 > paramFloat3)
      {
        float f4 = paramFloat1;
        paramFloat1 = paramFloat3;
        paramFloat3 = f4;
      }
      if (paramFloat2 > paramFloat4)
      {
        float f3 = paramFloat2;
        paramFloat2 = paramFloat4;
        paramFloat4 = f3;
      }
      rectImpl(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      return;
      paramFloat3 += paramFloat1;
      paramFloat4 += paramFloat2;
      continue;
      float f5 = paramFloat3;
      float f6 = paramFloat4;
      paramFloat3 = paramFloat1 + f5;
      paramFloat4 = paramFloat2 + f6;
      paramFloat1 -= f5;
      paramFloat2 -= f6;
      continue;
      float f1 = paramFloat3 / 2.0F;
      float f2 = paramFloat4 / 2.0F;
      paramFloat3 = paramFloat1 + f1;
      paramFloat4 = paramFloat2 + f2;
      paramFloat1 -= f1;
      paramFloat2 -= f2;
    }
  }
  
  public void rect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    rect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat5, paramFloat5, paramFloat5);
  }
  
  public void rect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    switch (this.rectMode)
    {
    }
    for (;;)
    {
      if (paramFloat1 > paramFloat3)
      {
        float f5 = paramFloat1;
        paramFloat1 = paramFloat3;
        paramFloat3 = f5;
      }
      if (paramFloat2 > paramFloat4)
      {
        float f4 = paramFloat2;
        paramFloat2 = paramFloat4;
        paramFloat4 = f4;
      }
      float f3 = PApplet.min((paramFloat3 - paramFloat1) / 2.0F, (paramFloat4 - paramFloat2) / 2.0F);
      if (paramFloat5 > f3) {
        paramFloat5 = f3;
      }
      if (paramFloat6 > f3) {
        paramFloat6 = f3;
      }
      if (paramFloat7 > f3) {
        paramFloat7 = f3;
      }
      if (paramFloat8 > f3) {
        paramFloat8 = f3;
      }
      rectImpl(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
      return;
      paramFloat3 += paramFloat1;
      paramFloat4 += paramFloat2;
      continue;
      float f6 = paramFloat3;
      float f7 = paramFloat4;
      paramFloat3 = paramFloat1 + f6;
      paramFloat4 = paramFloat2 + f7;
      paramFloat1 -= f6;
      paramFloat2 -= f7;
      continue;
      float f1 = paramFloat3 / 2.0F;
      float f2 = paramFloat4 / 2.0F;
      paramFloat3 = paramFloat1 + f1;
      paramFloat4 = paramFloat2 + f2;
      paramFloat1 -= f1;
      paramFloat2 -= f2;
    }
  }
  
  protected void rectImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    quad(paramFloat1, paramFloat2, paramFloat3, paramFloat2, paramFloat3, paramFloat4, paramFloat1, paramFloat4);
  }
  
  protected void rectImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    beginShape();
    if (paramFloat6 != 0.0F)
    {
      vertex(paramFloat3 - paramFloat6, paramFloat2);
      quadraticVertex(paramFloat3, paramFloat2, paramFloat3, paramFloat2 + paramFloat6);
      if (paramFloat7 == 0.0F) {
        break label133;
      }
      vertex(paramFloat3, paramFloat4 - paramFloat7);
      quadraticVertex(paramFloat3, paramFloat4, paramFloat3 - paramFloat7, paramFloat4);
      label61:
      if (paramFloat8 == 0.0F) {
        break label143;
      }
      vertex(paramFloat1 + paramFloat8, paramFloat4);
      quadraticVertex(paramFloat1, paramFloat4, paramFloat1, paramFloat4 - paramFloat8);
      label91:
      if (paramFloat5 == 0.0F) {
        break label153;
      }
      vertex(paramFloat1, paramFloat2 + paramFloat5);
      quadraticVertex(paramFloat1, paramFloat2, paramFloat1 + paramFloat5, paramFloat2);
    }
    for (;;)
    {
      endShape(2);
      return;
      vertex(paramFloat3, paramFloat2);
      break;
      label133:
      vertex(paramFloat3, paramFloat4);
      break label61;
      label143:
      vertex(paramFloat1, paramFloat4);
      break label91;
      label153:
      vertex(paramFloat1, paramFloat2);
    }
  }
  
  public void rectMode(int paramInt)
  {
    this.rectMode = paramInt;
  }
  
  public final float red(int paramInt)
  {
    float f = 0xFF & paramInt >> 16;
    if (this.colorModeDefault) {
      return f;
    }
    return f / 255.0F * this.colorModeX;
  }
  
  public void removeCache(PImage paramPImage)
  {
    this.cacheMap.remove(paramPImage);
  }
  
  public void requestDraw() {}
  
  public void resetMatrix()
  {
    showMethodWarning("resetMatrix");
  }
  
  public void resetShader()
  {
    showMissingWarning("resetShader");
  }
  
  public void resetShader(int paramInt)
  {
    showMissingWarning("resetShader");
  }
  
  public void rotate(float paramFloat)
  {
    showMissingWarning("rotate");
  }
  
  public void rotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    showMissingWarning("rotate");
  }
  
  public void rotateX(float paramFloat)
  {
    showMethodWarning("rotateX");
  }
  
  public void rotateY(float paramFloat)
  {
    showMethodWarning("rotateY");
  }
  
  public void rotateZ(float paramFloat)
  {
    showMethodWarning("rotateZ");
  }
  
  public final float saturation(int paramInt)
  {
    if (paramInt != this.cacheHsbKey)
    {
      Color.RGBToHSV(0xFF & paramInt >> 16, 0xFF & paramInt >> 8, paramInt & 0xFF, this.cacheHsbValue);
      this.cacheHsbKey = paramInt;
    }
    return this.cacheHsbValue[1] * this.colorModeY;
  }
  
  public void scale(float paramFloat)
  {
    showMissingWarning("scale");
  }
  
  public void scale(float paramFloat1, float paramFloat2)
  {
    showMissingWarning("scale");
  }
  
  public void scale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMissingWarning("scale");
  }
  
  public float screenX(float paramFloat1, float paramFloat2)
  {
    showMissingWarning("screenX");
    return 0.0F;
  }
  
  public float screenX(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMissingWarning("screenX");
    return 0.0F;
  }
  
  public float screenY(float paramFloat1, float paramFloat2)
  {
    showMissingWarning("screenY");
    return 0.0F;
  }
  
  public float screenY(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMissingWarning("screenY");
    return 0.0F;
  }
  
  public float screenZ(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMissingWarning("screenZ");
    return 0.0F;
  }
  
  public void setCache(PImage paramPImage, Object paramObject)
  {
    this.cacheMap.put(paramPImage, paramObject);
  }
  
  public void setFrameRate(float paramFloat) {}
  
  public void setMatrix(PMatrix2D paramPMatrix2D)
  {
    showMissingWarning("setMatrix");
  }
  
  public void setMatrix(PMatrix3D paramPMatrix3D)
  {
    showMissingWarning("setMatrix");
  }
  
  public void setMatrix(PMatrix paramPMatrix)
  {
    if ((paramPMatrix instanceof PMatrix2D)) {
      setMatrix((PMatrix2D)paramPMatrix);
    }
    while (!(paramPMatrix instanceof PMatrix3D)) {
      return;
    }
    setMatrix((PMatrix3D)paramPMatrix);
  }
  
  public void setParent(PApplet paramPApplet)
  {
    this.parent = paramPApplet;
  }
  
  public void setPath(String paramString)
  {
    this.path = paramString;
  }
  
  public void setPrimary(boolean paramBoolean)
  {
    this.primarySurface = paramBoolean;
    if (this.primarySurface) {
      this.format = 1;
    }
  }
  
  public void setSize(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
    this.width1 = (-1 + this.width);
    this.height1 = (-1 + this.height);
    allocate();
    reapplySettings();
  }
  
  public void shader(PShader paramPShader)
  {
    showMissingWarning("shader");
  }
  
  public void shader(PShader paramPShader, int paramInt)
  {
    showMissingWarning("shader");
  }
  
  public void shape(PShape paramPShape)
  {
    if (paramPShape.isVisible())
    {
      if (this.shapeMode == 3)
      {
        pushMatrix();
        translate(-paramPShape.getWidth() / 2.0F, -paramPShape.getHeight() / 2.0F);
      }
      paramPShape.draw(this);
      if (this.shapeMode == 3) {
        popMatrix();
      }
    }
  }
  
  public void shape(PShape paramPShape, float paramFloat1, float paramFloat2)
  {
    if (paramPShape.isVisible())
    {
      pushMatrix();
      if (this.shapeMode != 3) {
        break label49;
      }
      translate(paramFloat1 - paramPShape.getWidth() / 2.0F, paramFloat2 - paramPShape.getHeight() / 2.0F);
    }
    for (;;)
    {
      paramPShape.draw(this);
      popMatrix();
      return;
      label49:
      if ((this.shapeMode == 0) || (this.shapeMode == 1)) {
        translate(paramFloat1, paramFloat2);
      }
    }
  }
  
  protected void shape(PShape paramPShape, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMissingWarning("shape");
  }
  
  public void shape(PShape paramPShape, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (paramPShape.isVisible())
    {
      pushMatrix();
      if (this.shapeMode != 3) {
        break label63;
      }
      translate(paramFloat1 - paramFloat3 / 2.0F, paramFloat2 - paramFloat4 / 2.0F);
      scale(paramFloat3 / paramPShape.getWidth(), paramFloat4 / paramPShape.getHeight());
    }
    for (;;)
    {
      paramPShape.draw(this);
      popMatrix();
      return;
      label63:
      if (this.shapeMode == 0)
      {
        translate(paramFloat1, paramFloat2);
        scale(paramFloat3 / paramPShape.getWidth(), paramFloat4 / paramPShape.getHeight());
      }
      else if (this.shapeMode == 1)
      {
        float f1 = paramFloat3 - paramFloat1;
        float f2 = paramFloat4 - paramFloat2;
        translate(paramFloat1, paramFloat2);
        scale(f1 / paramPShape.getWidth(), f2 / paramPShape.getHeight());
      }
    }
  }
  
  protected void shape(PShape paramPShape, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMissingWarning("shape");
  }
  
  public void shapeMode(int paramInt)
  {
    this.shapeMode = paramInt;
  }
  
  public void shearX(float paramFloat)
  {
    showMissingWarning("shearX");
  }
  
  public void shearY(float paramFloat)
  {
    showMissingWarning("shearY");
  }
  
  public void shininess(float paramFloat)
  {
    this.shininess = paramFloat;
  }
  
  public void smooth()
  {
    this.smooth = true;
  }
  
  public void smooth(int paramInt)
  {
    this.smooth = true;
  }
  
  public void specular(float paramFloat)
  {
    colorCalc(paramFloat);
    specularFromCalc();
  }
  
  public void specular(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    specularFromCalc();
  }
  
  public void specular(int paramInt)
  {
    colorCalc(paramInt);
    specularFromCalc();
  }
  
  protected void specularFromCalc()
  {
    this.specularColor = this.calcColor;
    this.specularR = this.calcR;
    this.specularG = this.calcG;
    this.specularB = this.calcB;
  }
  
  public void sphere(float paramFloat)
  {
    if ((this.sphereDetailU < 3) || (this.sphereDetailV < 2)) {
      sphereDetail(30);
    }
    edge(false);
    beginShape(10);
    int i = 0;
    int j;
    int k;
    if (i >= this.sphereDetailU)
    {
      normal(0.0F, -paramFloat, 0.0F);
      vertex(0.0F, -paramFloat, 0.0F);
      normal(this.sphereX[0], this.sphereY[0], this.sphereZ[0]);
      vertex(paramFloat * this.sphereX[0], paramFloat * this.sphereY[0], paramFloat * this.sphereZ[0]);
      endShape();
      j = 0;
      k = 2;
      if (k < this.sphereDetailV) {
        break label292;
      }
      beginShape(10);
    }
    for (int i6 = 0;; i6++)
    {
      if (i6 >= this.sphereDetailU)
      {
        normal(this.sphereX[j], this.sphereY[j], this.sphereZ[j]);
        vertex(paramFloat * this.sphereX[j], paramFloat * this.sphereY[j], paramFloat * this.sphereZ[j]);
        normal(0.0F, 1.0F, 0.0F);
        vertex(0.0F, paramFloat, 0.0F);
        endShape();
        edge(true);
        return;
        normal(0.0F, -1.0F, 0.0F);
        vertex(0.0F, -paramFloat, 0.0F);
        normal(this.sphereX[i], this.sphereY[i], this.sphereZ[i]);
        vertex(paramFloat * this.sphereX[i], paramFloat * this.sphereY[i], paramFloat * this.sphereZ[i]);
        i++;
        break;
        label292:
        int m = j;
        int n = j;
        j += this.sphereDetailU;
        int i1 = j;
        beginShape(10);
        int i2 = 0;
        for (;;)
        {
          if (i2 >= this.sphereDetailU)
          {
            int i5 = j;
            normal(this.sphereX[m], this.sphereY[m], this.sphereZ[m]);
            vertex(paramFloat * this.sphereX[m], paramFloat * this.sphereY[m], paramFloat * this.sphereZ[m]);
            normal(this.sphereX[i5], this.sphereY[i5], this.sphereZ[i5]);
            vertex(paramFloat * this.sphereX[i5], paramFloat * this.sphereY[i5], paramFloat * this.sphereZ[i5]);
            endShape();
            k++;
            break;
          }
          normal(this.sphereX[n], this.sphereY[n], this.sphereZ[n]);
          float f1 = paramFloat * this.sphereX[n];
          float f2 = paramFloat * this.sphereY[n];
          float[] arrayOfFloat1 = this.sphereZ;
          int i3 = n + 1;
          vertex(f1, f2, paramFloat * arrayOfFloat1[n]);
          normal(this.sphereX[i1], this.sphereY[i1], this.sphereZ[i1]);
          float f3 = paramFloat * this.sphereX[i1];
          float f4 = paramFloat * this.sphereY[i1];
          float[] arrayOfFloat2 = this.sphereZ;
          int i4 = i1 + 1;
          vertex(f3, f4, paramFloat * arrayOfFloat2[i1]);
          i2++;
          i1 = i4;
          n = i3;
        }
      }
      int i7 = j + i6;
      normal(this.sphereX[i7], this.sphereY[i7], this.sphereZ[i7]);
      vertex(paramFloat * this.sphereX[i7], paramFloat * this.sphereY[i7], paramFloat * this.sphereZ[i7]);
      normal(0.0F, 1.0F, 0.0F);
      vertex(0.0F, paramFloat, 0.0F);
    }
  }
  
  public void sphereDetail(int paramInt)
  {
    sphereDetail(paramInt, paramInt);
  }
  
  public void sphereDetail(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 3) {
      paramInt1 = 3;
    }
    if (paramInt2 < 2) {
      paramInt2 = 2;
    }
    int i = this.sphereDetailU;
    if (paramInt1 == i)
    {
      int i4 = this.sphereDetailV;
      if (paramInt2 == i4) {
        return;
      }
    }
    float f1 = 720.0F / paramInt1;
    float[] arrayOfFloat1 = new float[paramInt1];
    float[] arrayOfFloat2 = new float[paramInt1];
    int m;
    float f2;
    float f3;
    int n;
    for (int j = 0;; j++)
    {
      if (j >= paramInt1)
      {
        int k = 2 + paramInt1 * (paramInt2 - 1);
        m = 0;
        this.sphereX = new float[k];
        this.sphereY = new float[k];
        this.sphereZ = new float[k];
        f2 = 360.0F / paramInt2;
        f3 = f2;
        n = 1;
        if (n < paramInt2) {
          break;
        }
        this.sphereDetailU = paramInt1;
        this.sphereDetailV = paramInt2;
        return;
      }
      arrayOfFloat1[j] = cosLUT[((int)(f1 * j) % 720)];
      arrayOfFloat2[j] = sinLUT[((int)(f1 * j) % 720)];
    }
    float f4 = sinLUT[((int)f3 % 720)];
    float f5 = cosLUT[((int)f3 % 720)];
    int i1 = 0;
    int i3;
    for (int i2 = m;; i2 = i3)
    {
      if (i1 >= paramInt1)
      {
        f3 += f2;
        n++;
        m = i2;
        break;
      }
      this.sphereX[i2] = (f4 * arrayOfFloat1[i1]);
      this.sphereY[i2] = f5;
      float[] arrayOfFloat3 = this.sphereZ;
      i3 = i2 + 1;
      arrayOfFloat3[i2] = (f4 * arrayOfFloat2[i1]);
      i1++;
    }
  }
  
  protected void splineForward(int paramInt, PMatrix3D paramPMatrix3D)
  {
    float f1 = 1.0F / paramInt;
    float f2 = f1 * f1;
    float f3 = f2 * f1;
    paramPMatrix3D.set(0.0F, 0.0F, 0.0F, 1.0F, f3, f2, f1, 0.0F, 6.0F * f3, 2.0F * f2, 0.0F, 0.0F, 6.0F * f3, 0.0F, 0.0F, 0.0F);
  }
  
  public void spotLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11)
  {
    showMethodWarning("spotLight");
  }
  
  public void stroke(float paramFloat)
  {
    colorCalc(paramFloat);
    strokeFromCalc();
  }
  
  public void stroke(float paramFloat1, float paramFloat2)
  {
    colorCalc(paramFloat1, paramFloat2);
    strokeFromCalc();
  }
  
  public void stroke(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    strokeFromCalc();
  }
  
  public void stroke(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    strokeFromCalc();
  }
  
  public void stroke(int paramInt)
  {
    colorCalc(paramInt);
    strokeFromCalc();
  }
  
  public void stroke(int paramInt, float paramFloat)
  {
    colorCalc(paramInt, paramFloat);
    strokeFromCalc();
  }
  
  public void strokeCap(int paramInt)
  {
    this.strokeCap = paramInt;
  }
  
  protected void strokeFromCalc()
  {
    this.stroke = true;
    this.strokeR = this.calcR;
    this.strokeG = this.calcG;
    this.strokeB = this.calcB;
    this.strokeA = this.calcA;
    this.strokeRi = this.calcRi;
    this.strokeGi = this.calcGi;
    this.strokeBi = this.calcBi;
    this.strokeAi = this.calcAi;
    this.strokeColor = this.calcColor;
    this.strokeAlpha = this.calcAlpha;
  }
  
  public void strokeJoin(int paramInt)
  {
    this.strokeJoin = paramInt;
  }
  
  public void strokeWeight(float paramFloat)
  {
    this.strokeWeight = paramFloat;
  }
  
  public void style(PStyle paramPStyle)
  {
    imageMode(paramPStyle.imageMode);
    rectMode(paramPStyle.rectMode);
    ellipseMode(paramPStyle.ellipseMode);
    shapeMode(paramPStyle.shapeMode);
    if (paramPStyle.tint)
    {
      tint(paramPStyle.tintColor);
      if (!paramPStyle.fill) {
        break label242;
      }
      fill(paramPStyle.fillColor);
      label62:
      if (!paramPStyle.stroke) {
        break label249;
      }
      stroke(paramPStyle.strokeColor);
    }
    for (;;)
    {
      strokeWeight(paramPStyle.strokeWeight);
      strokeCap(paramPStyle.strokeCap);
      strokeJoin(paramPStyle.strokeJoin);
      colorMode(1, 1.0F);
      ambient(paramPStyle.ambientR, paramPStyle.ambientG, paramPStyle.ambientB);
      emissive(paramPStyle.emissiveR, paramPStyle.emissiveG, paramPStyle.emissiveB);
      specular(paramPStyle.specularR, paramPStyle.specularG, paramPStyle.specularB);
      shininess(paramPStyle.shininess);
      colorMode(paramPStyle.colorMode, paramPStyle.colorModeX, paramPStyle.colorModeY, paramPStyle.colorModeZ, paramPStyle.colorModeA);
      if (paramPStyle.textFont != null)
      {
        textFont(paramPStyle.textFont, paramPStyle.textSize);
        textLeading(paramPStyle.textLeading);
      }
      textAlign(paramPStyle.textAlign, paramPStyle.textAlignY);
      textMode(paramPStyle.textMode);
      return;
      noTint();
      break;
      label242:
      noFill();
      break label62;
      label249:
      noStroke();
    }
  }
  
  public void text(char paramChar, float paramFloat1, float paramFloat2)
  {
    if (this.textFont == null) {
      defaultFontOrDeath("text");
    }
    if (this.textAlignY == 3) {
      paramFloat2 += textAscent() / 2.0F;
    }
    for (;;)
    {
      this.textBuffer[0] = paramChar;
      textLineAlignImpl(this.textBuffer, 0, 1, paramFloat1, paramFloat2);
      return;
      if (this.textAlignY == 101) {
        paramFloat2 += textAscent();
      } else if (this.textAlignY == 102) {
        paramFloat2 -= textDescent();
      }
    }
  }
  
  public void text(char paramChar, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat3 != 0.0F) {
      translate(0.0F, 0.0F, paramFloat3);
    }
    text(paramChar, paramFloat1, paramFloat2);
    if (paramFloat3 != 0.0F) {
      translate(0.0F, 0.0F, -paramFloat3);
    }
  }
  
  public void text(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    text(PApplet.nfs(paramFloat1, 0, 3), paramFloat2, paramFloat3);
  }
  
  public void text(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    text(PApplet.nfs(paramFloat1, 0, 3), paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void text(int paramInt, float paramFloat1, float paramFloat2)
  {
    text(String.valueOf(paramInt), paramFloat1, paramFloat2);
  }
  
  public void text(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    text(String.valueOf(paramInt), paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void text(String paramString, float paramFloat1, float paramFloat2)
  {
    if (this.textFont == null) {
      defaultFontOrDeath("text");
    }
    int i = paramString.length();
    if (i > this.textBuffer.length) {
      this.textBuffer = new char[i + 10];
    }
    paramString.getChars(0, i, this.textBuffer, 0);
    float f = 0.0F;
    int j = 0;
    label86:
    int k;
    if (j >= i)
    {
      if (this.textAlignY != 3) {
        break label148;
      }
      paramFloat2 += (textAscent() - f) / 2.0F;
      k = 0;
    }
    for (int m = 0;; m++)
    {
      if (m >= i)
      {
        if (k < i) {
          textLineAlignImpl(this.textBuffer, k, m, paramFloat1, paramFloat2);
        }
        return;
        if (this.textBuffer[j] == '\n') {
          f += this.textLeading;
        }
        j++;
        break;
        label148:
        if (this.textAlignY == 101)
        {
          paramFloat2 += textAscent();
          break label86;
        }
        if (this.textAlignY != 102) {
          break label86;
        }
        paramFloat2 -= f + textDescent();
        break label86;
      }
      if (this.textBuffer[m] == '\n')
      {
        textLineAlignImpl(this.textBuffer, k, m, paramFloat1, paramFloat2);
        k = m + 1;
        paramFloat2 += this.textLeading;
      }
    }
  }
  
  public void text(String paramString, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat3 != 0.0F) {
      translate(0.0F, 0.0F, paramFloat3);
    }
    text(paramString, paramFloat1, paramFloat2);
    if (paramFloat3 != 0.0F) {
      translate(0.0F, 0.0F, -paramFloat3);
    }
  }
  
  public void text(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (this.textFont == null) {
      defaultFontOrDeath("text");
    }
    float f3;
    float f4;
    int k;
    int m;
    label189:
    label196:
    float f5;
    label216:
    int i1;
    float f10;
    int i4;
    switch (this.rectMode)
    {
    case 1: 
    default: 
      if (paramFloat3 < paramFloat1)
      {
        float f12 = paramFloat1;
        paramFloat1 = paramFloat3;
        paramFloat3 = f12;
      }
      if (paramFloat4 < paramFloat2)
      {
        float f11 = paramFloat2;
        paramFloat2 = paramFloat4;
        paramFloat4 = f11;
      }
      f3 = paramFloat3 - paramFloat1;
      f4 = textWidth(' ');
      if (this.textBreakStart == null)
      {
        this.textBreakStart = new int[20];
        this.textBreakStop = new int[20];
      }
      this.textBreakCount = 0;
      int i = paramString.length();
      if (i + 1 > this.textBuffer.length) {
        this.textBuffer = new char[i + 1];
      }
      paramString.getChars(0, i, this.textBuffer, 0);
      char[] arrayOfChar = this.textBuffer;
      int j = i + 1;
      arrayOfChar[i] = '\n';
      k = 0;
      m = 0;
      if (m >= j)
      {
        f5 = paramFloat1;
        if (this.textAlign != 3) {
          break label437;
        }
        f5 += f3 / 2.0F;
        float f6 = paramFloat4 - paramFloat2;
        int n = 1 + PApplet.floor((f6 - (textAscent() + textDescent())) / this.textLeading);
        i1 = Math.min(this.textBreakCount, n);
        if (this.textAlignY != 3) {
          break label494;
        }
        float f9 = textAscent() + this.textLeading * (i1 - 1);
        f10 = paramFloat2 + textAscent() + (f6 - f9) / 2.0F;
        i4 = 0;
        label301:
        if (i4 < i1) {
          break label453;
        }
      }
      break;
    }
    for (;;)
    {
      return;
      paramFloat3 += paramFloat1;
      paramFloat4 += paramFloat2;
      break;
      float f13 = paramFloat3;
      float f14 = paramFloat4;
      paramFloat3 = paramFloat1 + f13;
      paramFloat4 = paramFloat2 + f14;
      paramFloat1 -= f13;
      paramFloat2 -= f14;
      break;
      float f1 = paramFloat3 / 2.0F;
      float f2 = paramFloat4 / 2.0F;
      paramFloat3 = paramFloat1 + f1;
      paramFloat4 = paramFloat2 + f2;
      paramFloat1 -= f1;
      paramFloat2 -= f2;
      break;
      if (this.textBuffer[m] == '\n')
      {
        if (!textSentence(this.textBuffer, k, m, f3, f4)) {
          break label196;
        }
        k = m + 1;
      }
      m++;
      break label189;
      label437:
      if (this.textAlign != 22) {
        break label216;
      }
      f5 = paramFloat3;
      break label216;
      label453:
      textLineAlignImpl(this.textBuffer, this.textBreakStart[i4], this.textBreakStop[i4], f5, f10);
      f10 += this.textLeading;
      i4++;
      break label301;
      label494:
      if (this.textAlignY == 102)
      {
        float f8 = paramFloat4 - textDescent() - this.textLeading * (i1 - 1);
        for (int i3 = 0; i3 < i1; i3++)
        {
          textLineAlignImpl(this.textBuffer, this.textBreakStart[i3], this.textBreakStop[i3], f5, f8);
          f8 += this.textLeading;
        }
      }
      else
      {
        float f7 = paramFloat2 + textAscent();
        for (int i2 = 0; i2 < i1; i2++)
        {
          textLineAlignImpl(this.textBuffer, this.textBreakStart[i2], this.textBreakStop[i2], f5, f7);
          f7 += this.textLeading;
        }
      }
    }
  }
  
  public void textAlign(int paramInt)
  {
    textAlign(paramInt, 0);
  }
  
  public void textAlign(int paramInt1, int paramInt2)
  {
    this.textAlign = paramInt1;
    this.textAlignY = paramInt2;
  }
  
  public float textAscent()
  {
    if (this.textFont == null) {
      defaultFontOrDeath("textAscent");
    }
    return this.textFont.ascent() * this.textSize;
  }
  
  protected void textCharImpl(char paramChar, float paramFloat1, float paramFloat2)
  {
    PFont.Glyph localGlyph = this.textFont.getGlyph(paramChar);
    if ((localGlyph != null) && (this.textMode == 4))
    {
      float f1 = localGlyph.height / this.textFont.size;
      float f2 = localGlyph.width / this.textFont.size;
      float f3 = localGlyph.leftExtent / this.textFont.size;
      float f4 = localGlyph.topExtent / this.textFont.size;
      float f5 = paramFloat1 + f3 * this.textSize;
      float f6 = paramFloat2 - f4 * this.textSize;
      float f7 = f5 + f2 * this.textSize;
      float f8 = f6 + f1 * this.textSize;
      textCharModelImpl(localGlyph.image, f5, f6, f7, f8, localGlyph.width, localGlyph.height);
    }
  }
  
  protected void textCharModelImpl(PImage paramPImage, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2)
  {
    boolean bool = this.tint;
    int i = this.tintColor;
    tint(this.fillColor);
    imageImpl(paramPImage, paramFloat1, paramFloat2, paramFloat3, paramFloat4, 0, 0, paramInt1, paramInt2);
    if (bool)
    {
      tint(i);
      return;
    }
    noTint();
  }
  
  public float textDescent()
  {
    if (this.textFont == null) {
      defaultFontOrDeath("textDescent");
    }
    return this.textFont.descent() * this.textSize;
  }
  
  public void textFont(PFont paramPFont)
  {
    if (paramPFont != null)
    {
      this.textFont = paramPFont;
      textSize(paramPFont.size);
      return;
    }
    throw new RuntimeException("A null PFont was passed to textFont()");
  }
  
  public void textFont(PFont paramPFont, float paramFloat)
  {
    textFont(paramPFont);
    textSize(paramFloat);
  }
  
  public void textLeading(float paramFloat)
  {
    this.textLeading = paramFloat;
  }
  
  protected void textLineAlignImpl(char[] paramArrayOfChar, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    if (this.textAlign == 3) {
      paramFloat1 -= textWidthImpl(paramArrayOfChar, paramInt1, paramInt2) / 2.0F;
    }
    for (;;)
    {
      textLineImpl(paramArrayOfChar, paramInt1, paramInt2, paramFloat1, paramFloat2);
      return;
      if (this.textAlign == 22) {
        paramFloat1 -= textWidthImpl(paramArrayOfChar, paramInt1, paramInt2);
      }
    }
  }
  
  protected void textLineImpl(char[] paramArrayOfChar, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    for (int i = paramInt1;; i++)
    {
      if (i >= paramInt2) {
        return;
      }
      textCharImpl(paramArrayOfChar[i], paramFloat1, paramFloat2);
      paramFloat1 += textWidth(paramArrayOfChar[i]);
    }
  }
  
  public void textMode(int paramInt)
  {
    if ((paramInt == 21) || (paramInt == 22))
    {
      showWarning("Since Processing beta, textMode() is now textAlign().");
      return;
    }
    if (paramInt == 256) {
      showWarning("textMode(SCREEN) has been removed from Processing 2.0.");
    }
    if (textModeCheck(paramInt))
    {
      this.textMode = paramInt;
      return;
    }
    String str = String.valueOf(paramInt);
    switch (paramInt)
    {
    }
    for (;;)
    {
      showWarning("textMode(" + str + ") is not supported by this renderer.");
      return;
      str = "MODEL";
      continue;
      str = "SHAPE";
    }
  }
  
  protected boolean textModeCheck(int paramInt)
  {
    return true;
  }
  
  public void textSize(float paramFloat)
  {
    if (this.textFont == null) {
      defaultFontOrDeath("textSize", paramFloat);
    }
    this.textSize = paramFloat;
    this.textLeading = (1.275F * (textAscent() + textDescent()));
  }
  
  public float textWidth(char paramChar)
  {
    this.textWidthBuffer[0] = paramChar;
    return textWidthImpl(this.textWidthBuffer, 0, 1);
  }
  
  public float textWidth(String paramString)
  {
    if (this.textFont == null) {
      defaultFontOrDeath("textWidth");
    }
    int i = paramString.length();
    if (i > this.textWidthBuffer.length) {
      this.textWidthBuffer = new char[i + 10];
    }
    paramString.getChars(0, i, this.textWidthBuffer, 0);
    float f = 0.0F;
    int j = 0;
    int k = 0;
    for (;;)
    {
      if (j >= i)
      {
        if (k < i) {
          f = Math.max(f, textWidthImpl(this.textWidthBuffer, k, j));
        }
        return f;
      }
      if (this.textWidthBuffer[j] == '\n')
      {
        f = Math.max(f, textWidthImpl(this.textWidthBuffer, k, j));
        k = j + 1;
      }
      j++;
    }
  }
  
  protected float textWidthImpl(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    float f = 0.0F;
    for (int i = paramInt1;; i++)
    {
      if (i >= paramInt2) {
        return f;
      }
      f += this.textFont.width(paramArrayOfChar[i]) * this.textSize;
    }
  }
  
  public void texture(PImage paramPImage)
  {
    this.textureImage = paramPImage;
  }
  
  public void textureMode(int paramInt)
  {
    this.textureMode = paramInt;
  }
  
  public void textureWrap(int paramInt)
  {
    showMissingWarning("textureWrap");
  }
  
  public void tint(float paramFloat)
  {
    colorCalc(paramFloat);
    tintFromCalc();
  }
  
  public void tint(float paramFloat1, float paramFloat2)
  {
    colorCalc(paramFloat1, paramFloat2);
    tintFromCalc();
  }
  
  public void tint(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    tintFromCalc();
  }
  
  public void tint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    tintFromCalc();
  }
  
  public void tint(int paramInt)
  {
    colorCalc(paramInt);
    tintFromCalc();
  }
  
  public void tint(int paramInt, float paramFloat)
  {
    colorCalc(paramInt, paramFloat);
    tintFromCalc();
  }
  
  protected void tintFromCalc()
  {
    this.tint = true;
    this.tintR = this.calcR;
    this.tintG = this.calcG;
    this.tintB = this.calcB;
    this.tintA = this.calcA;
    this.tintRi = this.calcRi;
    this.tintGi = this.calcGi;
    this.tintBi = this.calcBi;
    this.tintAi = this.calcAi;
    this.tintColor = this.calcColor;
    this.tintAlpha = this.calcAlpha;
  }
  
  public void translate(float paramFloat1, float paramFloat2)
  {
    showMissingWarning("translate");
  }
  
  public void translate(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMissingWarning("translate");
  }
  
  public void triangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    beginShape(9);
    vertex(paramFloat1, paramFloat2);
    vertex(paramFloat3, paramFloat4);
    vertex(paramFloat5, paramFloat6);
    endShape();
  }
  
  public void vertex(float paramFloat1, float paramFloat2)
  {
    vertexCheck();
    float[] arrayOfFloat = this.vertices[this.vertexCount];
    this.curveVertexCount = 0;
    arrayOfFloat[0] = paramFloat1;
    arrayOfFloat[1] = paramFloat2;
    arrayOfFloat[2] = 0.0F;
    int i;
    int j;
    label58:
    label104:
    float f1;
    if (this.edge)
    {
      i = 1;
      arrayOfFloat[12] = i;
      if (this.textureImage == null) {
        break label260;
      }
      j = 1;
      if ((this.fill) || (j != 0))
      {
        if (j != 0) {
          break label266;
        }
        arrayOfFloat[3] = this.fillR;
        arrayOfFloat[4] = this.fillG;
        arrayOfFloat[5] = this.fillB;
        arrayOfFloat[6] = this.fillA;
      }
      if (this.stroke)
      {
        arrayOfFloat[13] = this.strokeR;
        arrayOfFloat[14] = this.strokeG;
        arrayOfFloat[15] = this.strokeB;
        arrayOfFloat[16] = this.strokeA;
        arrayOfFloat[17] = this.strokeWeight;
      }
      arrayOfFloat[7] = this.textureU;
      arrayOfFloat[8] = this.textureV;
      if (!this.autoNormal) {
        break label387;
      }
      f1 = this.normalX * this.normalX + this.normalY * this.normalY + this.normalZ * this.normalZ;
      if (f1 >= 1.0E-004F) {
        break label325;
      }
      arrayOfFloat[36] = 0.0F;
    }
    for (;;)
    {
      arrayOfFloat[9] = this.normalX;
      arrayOfFloat[10] = this.normalY;
      arrayOfFloat[11] = this.normalZ;
      this.vertexCount = (1 + this.vertexCount);
      return;
      i = 0;
      break;
      label260:
      j = 0;
      break label58;
      label266:
      if (this.tint)
      {
        arrayOfFloat[3] = this.tintR;
        arrayOfFloat[4] = this.tintG;
        arrayOfFloat[5] = this.tintB;
        arrayOfFloat[6] = this.tintA;
        break label104;
      }
      arrayOfFloat[3] = 1.0F;
      arrayOfFloat[4] = 1.0F;
      arrayOfFloat[5] = 1.0F;
      arrayOfFloat[6] = 1.0F;
      break label104;
      label325:
      if (Math.abs(f1 - 1.0F) > 1.0E-004F)
      {
        float f2 = PApplet.sqrt(f1);
        this.normalX /= f2;
        this.normalY /= f2;
        this.normalZ /= f2;
      }
      arrayOfFloat[36] = 1.0F;
      continue;
      label387:
      arrayOfFloat[36] = 1.0F;
    }
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    vertexCheck();
    float[] arrayOfFloat1 = this.vertices[this.vertexCount];
    if ((this.shape == 20) && (this.vertexCount > 0))
    {
      float[] arrayOfFloat2 = this.vertices[(-1 + this.vertexCount)];
      if ((Math.abs(arrayOfFloat2[0] - paramFloat1) < 1.0E-004F) && (Math.abs(arrayOfFloat2[1] - paramFloat2) < 1.0E-004F) && (Math.abs(arrayOfFloat2[2] - paramFloat3) < 1.0E-004F)) {
        return;
      }
    }
    this.curveVertexCount = 0;
    arrayOfFloat1[0] = paramFloat1;
    arrayOfFloat1[1] = paramFloat2;
    arrayOfFloat1[2] = paramFloat3;
    int i;
    int j;
    label141:
    label191:
    float f1;
    if (this.edge)
    {
      i = 1;
      arrayOfFloat1[12] = i;
      if (this.textureImage == null) {
        break label358;
      }
      j = 1;
      if ((this.fill) || (j != 0))
      {
        if (j != 0) {
          break label364;
        }
        arrayOfFloat1[3] = this.fillR;
        arrayOfFloat1[4] = this.fillG;
        arrayOfFloat1[5] = this.fillB;
        arrayOfFloat1[6] = this.fillA;
      }
      if (this.stroke)
      {
        arrayOfFloat1[13] = this.strokeR;
        arrayOfFloat1[14] = this.strokeG;
        arrayOfFloat1[15] = this.strokeB;
        arrayOfFloat1[16] = this.strokeA;
        arrayOfFloat1[17] = this.strokeWeight;
      }
      arrayOfFloat1[7] = this.textureU;
      arrayOfFloat1[8] = this.textureV;
      if (!this.autoNormal) {
        break label494;
      }
      f1 = this.normalX * this.normalX + this.normalY * this.normalY + this.normalZ * this.normalZ;
      if (f1 >= 1.0E-004F) {
        break label431;
      }
      arrayOfFloat1[36] = 0.0F;
    }
    for (;;)
    {
      arrayOfFloat1[9] = this.normalX;
      arrayOfFloat1[10] = this.normalY;
      arrayOfFloat1[11] = this.normalZ;
      this.vertexCount = (1 + this.vertexCount);
      return;
      i = 0;
      break;
      label358:
      j = 0;
      break label141;
      label364:
      if (this.tint)
      {
        arrayOfFloat1[3] = this.tintR;
        arrayOfFloat1[4] = this.tintG;
        arrayOfFloat1[5] = this.tintB;
        arrayOfFloat1[6] = this.tintA;
        break label191;
      }
      arrayOfFloat1[3] = 1.0F;
      arrayOfFloat1[4] = 1.0F;
      arrayOfFloat1[5] = 1.0F;
      arrayOfFloat1[6] = 1.0F;
      break label191;
      label431:
      if (Math.abs(f1 - 1.0F) > 1.0E-004F)
      {
        float f2 = PApplet.sqrt(f1);
        this.normalX /= f2;
        this.normalY /= f2;
        this.normalZ /= f2;
      }
      arrayOfFloat1[36] = 1.0F;
      continue;
      label494:
      arrayOfFloat1[36] = 1.0F;
    }
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    vertexTexture(paramFloat3, paramFloat4);
    vertex(paramFloat1, paramFloat2);
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    vertexTexture(paramFloat4, paramFloat5);
    vertex(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void vertex(float[] paramArrayOfFloat)
  {
    vertexCheck();
    this.curveVertexCount = 0;
    System.arraycopy(paramArrayOfFloat, 0, this.vertices[this.vertexCount], 0, 37);
    this.vertexCount = (1 + this.vertexCount);
  }
  
  protected void vertexCheck()
  {
    if (this.vertexCount == this.vertices.length)
    {
      int[] arrayOfInt = { this.vertexCount << 1, 37 };
      float[][] arrayOfFloat = (float[][])Array.newInstance(Float.TYPE, arrayOfInt);
      System.arraycopy(this.vertices, 0, arrayOfFloat, 0, this.vertexCount);
      this.vertices = arrayOfFloat;
    }
  }
  
  protected void vertexTexture(float paramFloat1, float paramFloat2)
  {
    if (this.textureImage == null) {
      throw new RuntimeException("You must first call texture() before using u and v coordinates with vertex()");
    }
    if (this.textureMode == 2)
    {
      paramFloat1 /= this.textureImage.width;
      paramFloat2 /= this.textureImage.height;
    }
    this.textureU = paramFloat1;
    this.textureV = paramFloat2;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PGraphics
 * JD-Core Version:    0.7.0.1
 */