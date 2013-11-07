package processing.opengl;

import java.io.IOException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PMatrix2D;
import processing.core.PMatrix3D;
import processing.core.PVector;

public class PShader
{
  protected static final int COLOR = 0;
  protected static final int LIGHT = 1;
  protected static final int LINE = 4;
  protected static final int POINT = 5;
  protected static final int TEXLIGHT = 3;
  protected static final int TEXTURE = 2;
  protected boolean bound;
  protected int context;
  protected int firstTexUnit;
  protected FloatBuffer floatBuffer;
  protected String fragmentFilename;
  protected String fragmentShaderSource;
  protected URL fragmentURL;
  public int glFragment;
  public int glProgram;
  public int glVertex;
  protected IntBuffer intBuffer;
  protected int lastTexUnit;
  protected PApplet parent;
  protected PGraphicsOpenGL pgCurrent;
  protected PGraphicsOpenGL pgMain;
  protected PGL pgl;
  protected HashMap<Integer, Texture> textures;
  protected HashMap<Integer, UniformValue> uniformValues = null;
  protected String vertexFilename;
  protected String vertexShaderSource;
  protected URL vertexURL;
  
  public PShader()
  {
    this.parent = null;
    this.pgMain = null;
    this.pgl = null;
    this.context = -1;
    this.vertexURL = null;
    this.fragmentURL = null;
    this.vertexFilename = null;
    this.fragmentFilename = null;
    this.glProgram = 0;
    this.glVertex = 0;
    this.glFragment = 0;
    this.firstTexUnit = 0;
    this.intBuffer = PGL.allocateIntBuffer(1);
    this.floatBuffer = PGL.allocateFloatBuffer(1);
    this.bound = false;
  }
  
  public PShader(PApplet paramPApplet)
  {
    this();
    this.parent = paramPApplet;
    this.pgMain = ((PGraphicsOpenGL)paramPApplet.g);
    this.pgl = this.pgMain.pgl;
    this.context = this.pgl.createEmptyContext();
  }
  
  public PShader(PApplet paramPApplet, String paramString1, String paramString2)
  {
    this.parent = paramPApplet;
    this.pgMain = ((PGraphicsOpenGL)paramPApplet.g);
    this.pgl = this.pgMain.pgl;
    this.vertexURL = null;
    this.fragmentURL = null;
    this.vertexFilename = paramString1;
    this.fragmentFilename = paramString2;
    this.glProgram = 0;
    this.glVertex = 0;
    this.glFragment = 0;
    this.intBuffer = PGL.allocateIntBuffer(1);
    this.floatBuffer = PGL.allocateFloatBuffer(1);
  }
  
  public PShader(PApplet paramPApplet, URL paramURL1, URL paramURL2)
  {
    this.parent = paramPApplet;
    this.pgMain = ((PGraphicsOpenGL)paramPApplet.g);
    this.pgl = this.pgMain.pgl;
    this.vertexURL = paramURL1;
    this.fragmentURL = paramURL2;
    this.vertexFilename = null;
    this.fragmentFilename = null;
    this.glProgram = 0;
    this.glVertex = 0;
    this.glFragment = 0;
    this.intBuffer = PGL.allocateIntBuffer(1);
    this.floatBuffer = PGL.allocateFloatBuffer(1);
  }
  
  public void bind()
  {
    init();
    this.pgl.useProgram(this.glProgram);
    this.bound = true;
    consumeUniforms();
    bindTextures();
  }
  
  protected void bindTextures()
  {
    Iterator localIterator;
    if (this.textures != null) {
      localIterator = this.textures.keySet().iterator();
    }
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      int i = ((Integer)localIterator.next()).intValue();
      Texture localTexture = (Texture)this.textures.get(Integer.valueOf(i));
      this.pgl.activeTexture(33984 + i);
      localTexture.bind();
    }
  }
  
  public boolean bound()
  {
    return this.bound;
  }
  
  protected boolean compileFragmentShader()
  {
    this.glFragment = this.pgMain.createGLSLFragShaderObject(this.context);
    this.pgl.shaderSource(this.glFragment, this.fragmentShaderSource);
    this.pgl.compileShader(this.glFragment);
    this.pgl.getShaderiv(this.glFragment, 35713, this.intBuffer);
    if (this.intBuffer.get(0) == 0) {}
    for (int i = 0; i == 0; i = 1)
    {
      PGraphics.showException("Cannot compile fragment shader:\n" + this.pgl.getShaderInfoLog(this.glFragment));
      return false;
    }
    return true;
  }
  
  protected boolean compileVertexShader()
  {
    this.glVertex = this.pgMain.createGLSLVertShaderObject(this.context);
    this.pgl.shaderSource(this.glVertex, this.vertexShaderSource);
    this.pgl.compileShader(this.glVertex);
    this.pgl.getShaderiv(this.glVertex, 35713, this.intBuffer);
    if (this.intBuffer.get(0) == 0) {}
    for (int i = 0; i == 0; i = 1)
    {
      PGraphics.showException("Cannot compile vertex shader:\n" + this.pgl.getShaderInfoLog(this.glVertex));
      return false;
    }
    return true;
  }
  
  protected void consumeUniforms()
  {
    Iterator localIterator;
    if ((this.uniformValues != null) && (this.uniformValues.size() > 0))
    {
      this.lastTexUnit = this.firstTexUnit;
      localIterator = this.uniformValues.keySet().iterator();
    }
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        this.uniformValues.clear();
        return;
      }
      Integer localInteger = (Integer)localIterator.next();
      UniformValue localUniformValue = (UniformValue)this.uniformValues.get(localInteger);
      if (localUniformValue.type == 0)
      {
        int[] arrayOfInt8 = (int[])localUniformValue.value;
        this.pgl.uniform1i(localInteger.intValue(), arrayOfInt8[0]);
      }
      else if (localUniformValue.type == 1)
      {
        int[] arrayOfInt7 = (int[])localUniformValue.value;
        this.pgl.uniform2i(localInteger.intValue(), arrayOfInt7[0], arrayOfInt7[1]);
      }
      else if (localUniformValue.type == 2)
      {
        int[] arrayOfInt6 = (int[])localUniformValue.value;
        this.pgl.uniform3i(localInteger.intValue(), arrayOfInt6[0], arrayOfInt6[1], arrayOfInt6[2]);
      }
      else if (localUniformValue.type == 3)
      {
        int[] arrayOfInt5 = (int[])localUniformValue.value;
        this.pgl.uniform4i(localInteger.intValue(), arrayOfInt5[0], arrayOfInt5[1], arrayOfInt5[2], arrayOfInt5[4]);
      }
      else if (localUniformValue.type == 4)
      {
        float[] arrayOfFloat8 = (float[])localUniformValue.value;
        this.pgl.uniform1f(localInteger.intValue(), arrayOfFloat8[0]);
      }
      else if (localUniformValue.type == 5)
      {
        float[] arrayOfFloat7 = (float[])localUniformValue.value;
        this.pgl.uniform2f(localInteger.intValue(), arrayOfFloat7[0], arrayOfFloat7[1]);
      }
      else if (localUniformValue.type == 6)
      {
        float[] arrayOfFloat6 = (float[])localUniformValue.value;
        this.pgl.uniform3f(localInteger.intValue(), arrayOfFloat6[0], arrayOfFloat6[1], arrayOfFloat6[2]);
      }
      else if (localUniformValue.type == 7)
      {
        float[] arrayOfFloat5 = (float[])localUniformValue.value;
        this.pgl.uniform4f(localInteger.intValue(), arrayOfFloat5[0], arrayOfFloat5[1], arrayOfFloat5[2], arrayOfFloat5[3]);
      }
      else if (localUniformValue.type == 8)
      {
        int[] arrayOfInt4 = (int[])localUniformValue.value;
        updateIntBuffer(arrayOfInt4);
        this.pgl.uniform1iv(localInteger.intValue(), arrayOfInt4.length, this.intBuffer);
      }
      else if (localUniformValue.type == 9)
      {
        int[] arrayOfInt3 = (int[])localUniformValue.value;
        updateIntBuffer(arrayOfInt3);
        this.pgl.uniform2iv(localInteger.intValue(), arrayOfInt3.length / 2, this.intBuffer);
      }
      else if (localUniformValue.type == 10)
      {
        int[] arrayOfInt2 = (int[])localUniformValue.value;
        updateIntBuffer(arrayOfInt2);
        this.pgl.uniform3iv(localInteger.intValue(), arrayOfInt2.length / 3, this.intBuffer);
      }
      else if (localUniformValue.type == 11)
      {
        int[] arrayOfInt1 = (int[])localUniformValue.value;
        updateIntBuffer(arrayOfInt1);
        this.pgl.uniform4iv(localInteger.intValue(), arrayOfInt1.length / 4, this.intBuffer);
      }
      else if (localUniformValue.type == 12)
      {
        float[] arrayOfFloat4 = (float[])localUniformValue.value;
        updateFloatBuffer(arrayOfFloat4);
        this.pgl.uniform1fv(localInteger.intValue(), arrayOfFloat4.length, this.floatBuffer);
      }
      else if (localUniformValue.type == 13)
      {
        float[] arrayOfFloat3 = (float[])localUniformValue.value;
        updateFloatBuffer(arrayOfFloat3);
        this.pgl.uniform2fv(localInteger.intValue(), arrayOfFloat3.length / 2, this.floatBuffer);
      }
      else if (localUniformValue.type == 14)
      {
        float[] arrayOfFloat2 = (float[])localUniformValue.value;
        updateFloatBuffer(arrayOfFloat2);
        this.pgl.uniform3fv(localInteger.intValue(), arrayOfFloat2.length / 3, this.floatBuffer);
      }
      else if (localUniformValue.type == 15)
      {
        float[] arrayOfFloat1 = (float[])localUniformValue.value;
        updateFloatBuffer(arrayOfFloat1);
        this.pgl.uniform4fv(localInteger.intValue(), arrayOfFloat1.length / 4, this.floatBuffer);
      }
      else if (localUniformValue.type == 16)
      {
        updateFloatBuffer((float[])localUniformValue.value);
        this.pgl.uniformMatrix2fv(localInteger.intValue(), 1, false, this.floatBuffer);
      }
      else if (localUniformValue.type == 17)
      {
        updateFloatBuffer((float[])localUniformValue.value);
        this.pgl.uniformMatrix3fv(localInteger.intValue(), 1, false, this.floatBuffer);
      }
      else if (localUniformValue.type == 18)
      {
        updateFloatBuffer((float[])localUniformValue.value);
        this.pgl.uniformMatrix4fv(localInteger.intValue(), 1, false, this.floatBuffer);
      }
      else if (localUniformValue.type == 19)
      {
        PImage localPImage = (PImage)localUniformValue.value;
        Texture localTexture = this.pgMain.getTexture(localPImage);
        this.pgl.uniform1i(localInteger.intValue(), this.lastTexUnit);
        if (this.textures == null) {
          this.textures = new HashMap();
        }
        this.textures.put(Integer.valueOf(this.lastTexUnit), localTexture);
        this.lastTexUnit = (1 + this.lastTexUnit);
      }
    }
  }
  
  protected boolean contextIsOutdated()
  {
    if (this.pgl.contextIsCurrent(this.context)) {}
    for (boolean bool = false;; bool = true)
    {
      if (bool)
      {
        this.pgMain.removeGLSLProgramObject(this.glProgram, this.context);
        this.pgMain.removeGLSLVertShaderObject(this.glVertex, this.context);
        this.pgMain.removeGLSLFragShaderObject(this.glFragment, this.context);
        this.glProgram = 0;
        this.glVertex = 0;
        this.glFragment = 0;
      }
      return bool;
    }
  }
  
  protected void finalize()
    throws Throwable
  {
    try
    {
      if (this.glVertex != 0) {
        this.pgMain.finalizeGLSLVertShaderObject(this.glVertex, this.context);
      }
      if (this.glFragment != 0) {
        this.pgMain.finalizeGLSLFragShaderObject(this.glFragment, this.context);
      }
      if (this.glProgram != 0) {
        this.pgMain.finalizeGLSLProgramObject(this.glProgram, this.context);
      }
      return;
    }
    finally
    {
      super.finalize();
    }
  }
  
  protected int getAttributeLoc(String paramString)
  {
    init();
    return this.pgl.getAttribLocation(this.glProgram, paramString);
  }
  
  protected int getUniformLoc(String paramString)
  {
    init();
    return this.pgl.getUniformLocation(this.glProgram, paramString);
  }
  
  protected void init()
  {
    boolean bool1;
    boolean bool2;
    label72:
    int i;
    label186:
    int k;
    if ((this.glProgram == 0) || (contextIsOutdated()))
    {
      this.context = this.pgl.getCurrentContext();
      this.glProgram = this.pgMain.createGLSLProgramObject(this.context);
      if (this.vertexFilename == null) {
        break label304;
      }
      bool1 = loadVertexShader(this.vertexFilename);
      if (this.fragmentFilename == null) {
        break label334;
      }
      bool2 = loadFragmentShader(this.fragmentFilename);
      boolean bool3 = true;
      if (bool1) {
        bool3 = compileVertexShader();
      }
      boolean bool4 = true;
      if (bool2) {
        bool4 = compileFragmentShader();
      }
      if ((bool3) && (bool4))
      {
        if (bool1) {
          this.pgl.attachShader(this.glProgram, this.glVertex);
        }
        if (bool2) {
          this.pgl.attachShader(this.glProgram, this.glFragment);
        }
        this.pgl.linkProgram(this.glProgram);
        this.pgl.getProgramiv(this.glProgram, 35714, this.intBuffer);
        if (this.intBuffer.get(0) != 0) {
          break label364;
        }
        i = 0;
        if (i == 0) {
          PGraphics.showException("Cannot link shader program:\n" + this.pgl.getProgramInfoLog(this.glProgram));
        }
        this.pgl.validateProgram(this.glProgram);
        this.pgl.getProgramiv(this.glProgram, 35715, this.intBuffer);
        int j = this.intBuffer.get(0);
        k = 0;
        if (j != 0) {
          break label370;
        }
      }
    }
    for (;;)
    {
      if (k == 0) {
        PGraphics.showException("Cannot validate shader program:\n" + this.pgl.getProgramInfoLog(this.glProgram));
      }
      return;
      label304:
      if (this.vertexURL != null)
      {
        bool1 = loadVertexShader(this.vertexURL);
        break;
      }
      PGraphics.showException("Vertex shader filenames and URLs are both null!");
      bool1 = false;
      break;
      label334:
      if (this.fragmentURL != null)
      {
        bool2 = loadFragmentShader(this.fragmentURL);
        break label72;
      }
      PGraphics.showException("Fragment shader filenames and URLs are both null!");
      bool2 = false;
      break label72;
      label364:
      i = 1;
      break label186;
      label370:
      k = 1;
    }
  }
  
  protected void loadAttributes() {}
  
  protected boolean loadFragmentShader(String paramString)
  {
    this.fragmentShaderSource = PApplet.join(this.parent.loadStrings(paramString), "\n");
    return this.fragmentShaderSource != null;
  }
  
  protected boolean loadFragmentShader(URL paramURL)
  {
    try
    {
      this.fragmentShaderSource = PApplet.join(PApplet.loadStrings(paramURL.openStream()), "\n");
      String str = this.fragmentShaderSource;
      boolean bool = false;
      if (str != null) {
        bool = true;
      }
      return bool;
    }
    catch (IOException localIOException)
    {
      PGraphics.showException("Cannot load fragment shader " + paramURL.getFile());
    }
    return false;
  }
  
  protected void loadUniforms() {}
  
  protected boolean loadVertexShader(String paramString)
  {
    this.vertexShaderSource = PApplet.join(this.parent.loadStrings(paramString), "\n");
    return this.vertexShaderSource != null;
  }
  
  protected boolean loadVertexShader(URL paramURL)
  {
    try
    {
      this.vertexShaderSource = PApplet.join(PApplet.loadStrings(paramURL.openStream()), "\n");
      String str = this.vertexShaderSource;
      boolean bool = false;
      if (str != null) {
        bool = true;
      }
      return bool;
    }
    catch (IOException localIOException)
    {
      PGraphics.showException("Cannot load vertex shader " + paramURL.getFile());
    }
    return false;
  }
  
  protected void release()
  {
    if (this.glVertex != 0)
    {
      this.pgMain.deleteGLSLVertShaderObject(this.glVertex, this.context);
      this.glVertex = 0;
    }
    if (this.glFragment != 0)
    {
      this.pgMain.deleteGLSLFragShaderObject(this.glFragment, this.context);
      this.glFragment = 0;
    }
    if (this.glProgram != 0)
    {
      this.pgMain.deleteGLSLProgramObject(this.glProgram, this.context);
      this.glProgram = 0;
    }
  }
  
  public void set(String paramString, float paramFloat)
  {
    setUniformImpl(paramString, 4, new float[] { paramFloat });
  }
  
  public void set(String paramString, float paramFloat1, float paramFloat2)
  {
    setUniformImpl(paramString, 5, new float[] { paramFloat1, paramFloat2 });
  }
  
  public void set(String paramString, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    setUniformImpl(paramString, 6, new float[] { paramFloat1, paramFloat2, paramFloat3 });
  }
  
  public void set(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    setUniformImpl(paramString, 7, new float[] { paramFloat1, paramFloat2, paramFloat3, paramFloat4 });
  }
  
  public void set(String paramString, int paramInt)
  {
    setUniformImpl(paramString, 0, new int[] { paramInt });
  }
  
  public void set(String paramString, int paramInt1, int paramInt2)
  {
    setUniformImpl(paramString, 1, new int[] { paramInt1, paramInt2 });
  }
  
  public void set(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    setUniformImpl(paramString, 2, new int[] { paramInt1, paramInt2, paramInt3 });
  }
  
  public void set(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    setUniformImpl(paramString, 3, new int[] { paramInt1, paramInt2, paramInt3 });
  }
  
  public void set(String paramString, PImage paramPImage)
  {
    setUniformImpl(paramString, 19, paramPImage);
  }
  
  public void set(String paramString, PMatrix2D paramPMatrix2D)
  {
    float[] arrayOfFloat = new float[4];
    arrayOfFloat[0] = paramPMatrix2D.m00;
    arrayOfFloat[1] = paramPMatrix2D.m01;
    arrayOfFloat[2] = paramPMatrix2D.m10;
    arrayOfFloat[3] = paramPMatrix2D.m11;
    setUniformImpl(paramString, 16, arrayOfFloat);
  }
  
  public void set(String paramString, PMatrix3D paramPMatrix3D)
  {
    set(paramString, paramPMatrix3D, false);
  }
  
  public void set(String paramString, PMatrix3D paramPMatrix3D, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      float[] arrayOfFloat2 = new float[9];
      arrayOfFloat2[0] = paramPMatrix3D.m00;
      arrayOfFloat2[1] = paramPMatrix3D.m01;
      arrayOfFloat2[2] = paramPMatrix3D.m02;
      arrayOfFloat2[3] = paramPMatrix3D.m10;
      arrayOfFloat2[4] = paramPMatrix3D.m11;
      arrayOfFloat2[5] = paramPMatrix3D.m12;
      arrayOfFloat2[6] = paramPMatrix3D.m20;
      arrayOfFloat2[7] = paramPMatrix3D.m21;
      arrayOfFloat2[8] = paramPMatrix3D.m22;
      setUniformImpl(paramString, 17, arrayOfFloat2);
      return;
    }
    float[] arrayOfFloat1 = new float[16];
    arrayOfFloat1[0] = paramPMatrix3D.m00;
    arrayOfFloat1[1] = paramPMatrix3D.m01;
    arrayOfFloat1[2] = paramPMatrix3D.m02;
    arrayOfFloat1[3] = paramPMatrix3D.m03;
    arrayOfFloat1[4] = paramPMatrix3D.m10;
    arrayOfFloat1[5] = paramPMatrix3D.m11;
    arrayOfFloat1[6] = paramPMatrix3D.m12;
    arrayOfFloat1[7] = paramPMatrix3D.m13;
    arrayOfFloat1[8] = paramPMatrix3D.m20;
    arrayOfFloat1[9] = paramPMatrix3D.m21;
    arrayOfFloat1[10] = paramPMatrix3D.m22;
    arrayOfFloat1[11] = paramPMatrix3D.m23;
    arrayOfFloat1[12] = paramPMatrix3D.m30;
    arrayOfFloat1[13] = paramPMatrix3D.m31;
    arrayOfFloat1[14] = paramPMatrix3D.m32;
    arrayOfFloat1[15] = paramPMatrix3D.m33;
    setUniformImpl(paramString, 18, arrayOfFloat1);
  }
  
  public void set(String paramString, PVector paramPVector)
  {
    float[] arrayOfFloat = new float[3];
    arrayOfFloat[0] = paramPVector.x;
    arrayOfFloat[1] = paramPVector.y;
    arrayOfFloat[2] = paramPVector.z;
    setUniformImpl(paramString, 6, arrayOfFloat);
  }
  
  public void set(String paramString, float[] paramArrayOfFloat)
  {
    set(paramString, paramArrayOfFloat, 1);
  }
  
  public void set(String paramString, float[] paramArrayOfFloat, int paramInt)
  {
    if (paramInt == 1)
    {
      setUniformImpl(paramString, 12, paramArrayOfFloat);
      return;
    }
    if (paramInt == 2)
    {
      setUniformImpl(paramString, 13, paramArrayOfFloat);
      return;
    }
    if (paramInt == 3)
    {
      setUniformImpl(paramString, 14, paramArrayOfFloat);
      return;
    }
    if (paramInt == 4)
    {
      setUniformImpl(paramString, 15, paramArrayOfFloat);
      return;
    }
    if (4 < paramInt)
    {
      PGraphics.showWarning("Only up to 4 coordinates per element are supported.");
      return;
    }
    PGraphics.showWarning("Wrong number of coordinates: it is negative!");
  }
  
  public void set(String paramString, int[] paramArrayOfInt)
  {
    set(paramString, paramArrayOfInt, 1);
  }
  
  public void set(String paramString, int[] paramArrayOfInt, int paramInt)
  {
    if (paramInt == 1)
    {
      setUniformImpl(paramString, 8, paramArrayOfInt);
      return;
    }
    if (paramInt == 2)
    {
      setUniformImpl(paramString, 9, paramArrayOfInt);
      return;
    }
    if (paramInt == 3)
    {
      setUniformImpl(paramString, 10, paramArrayOfInt);
      return;
    }
    if (paramInt == 4)
    {
      setUniformImpl(paramString, 11, paramArrayOfInt);
      return;
    }
    if (4 < paramInt)
    {
      PGraphics.showWarning("Only up to 4 coordinates per element are supported.");
      return;
    }
    PGraphics.showWarning("Wrong number of coordinates: it is negative!");
  }
  
  protected void setAttributeVBO(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5, int paramInt6)
  {
    if (-1 < paramInt1)
    {
      this.pgl.bindBuffer(34962, paramInt2);
      this.pgl.vertexAttribPointer(paramInt1, paramInt3, paramInt4, paramBoolean, paramInt5, paramInt6);
    }
  }
  
  public void setFragmentShader(String paramString)
  {
    this.fragmentFilename = paramString;
  }
  
  public void setFragmentShader(URL paramURL)
  {
    this.fragmentURL = paramURL;
  }
  
  protected void setRenderer(PGraphicsOpenGL paramPGraphicsOpenGL)
  {
    this.pgCurrent = paramPGraphicsOpenGL;
  }
  
  protected void setUniformImpl(String paramString, int paramInt, Object paramObject)
  {
    int i = getUniformLoc(paramString);
    if (-1 < i)
    {
      if (this.uniformValues == null) {
        this.uniformValues = new HashMap();
      }
      this.uniformValues.put(Integer.valueOf(i), new UniformValue(paramInt, paramObject));
      return;
    }
    PGraphics.showWarning("The shader doesn't have a uniform called \"" + paramString + "\"");
  }
  
  protected void setUniformMatrix(int paramInt, float[] paramArrayOfFloat)
  {
    if (-1 < paramInt)
    {
      updateFloatBuffer(paramArrayOfFloat);
      if (paramArrayOfFloat.length != 4) {
        break label31;
      }
      this.pgl.uniformMatrix2fv(paramInt, 1, false, this.floatBuffer);
    }
    label31:
    do
    {
      return;
      if (paramArrayOfFloat.length == 9)
      {
        this.pgl.uniformMatrix3fv(paramInt, 1, false, this.floatBuffer);
        return;
      }
    } while (paramArrayOfFloat.length != 16);
    this.pgl.uniformMatrix4fv(paramInt, 1, false, this.floatBuffer);
  }
  
  protected void setUniformTex(int paramInt, Texture paramTexture)
  {
    paramTexture.bind();
  }
  
  protected void setUniformValue(int paramInt, float paramFloat)
  {
    if (-1 < paramInt) {
      this.pgl.uniform1f(paramInt, paramFloat);
    }
  }
  
  protected void setUniformValue(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (-1 < paramInt) {
      this.pgl.uniform2f(paramInt, paramFloat1, paramFloat2);
    }
  }
  
  protected void setUniformValue(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (-1 < paramInt) {
      this.pgl.uniform3f(paramInt, paramFloat1, paramFloat2, paramFloat3);
    }
  }
  
  protected void setUniformValue(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (-1 < paramInt) {
      this.pgl.uniform4f(paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    }
  }
  
  protected void setUniformValue(int paramInt1, int paramInt2)
  {
    if (-1 < paramInt1) {
      this.pgl.uniform1i(paramInt1, paramInt2);
    }
  }
  
  protected void setUniformValue(int paramInt1, int paramInt2, int paramInt3)
  {
    if (-1 < paramInt1) {
      this.pgl.uniform2i(paramInt1, paramInt2, paramInt3);
    }
  }
  
  protected void setUniformValue(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (-1 < paramInt1) {
      this.pgl.uniform3i(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  protected void setUniformValue(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (-1 < paramInt1) {
      this.pgl.uniform4i(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    }
  }
  
  protected void setUniformVector(int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3)
  {
    if (-1 < paramInt1)
    {
      updateFloatBuffer(paramArrayOfFloat);
      if (paramInt2 != 1) {
        break label30;
      }
      this.pgl.uniform1fv(paramInt1, paramInt3, this.floatBuffer);
    }
    label30:
    do
    {
      return;
      if (paramInt2 == 2)
      {
        this.pgl.uniform2fv(paramInt1, paramInt3, this.floatBuffer);
        return;
      }
      if (paramInt2 == 3)
      {
        this.pgl.uniform3fv(paramInt1, paramInt3, this.floatBuffer);
        return;
      }
    } while (paramInt2 != 4);
    this.pgl.uniform4fv(paramInt1, paramInt3, this.floatBuffer);
  }
  
  protected void setUniformVector(int paramInt1, int[] paramArrayOfInt, int paramInt2, int paramInt3)
  {
    if (-1 < paramInt1)
    {
      updateIntBuffer(paramArrayOfInt);
      if (paramInt2 != 1) {
        break label30;
      }
      this.pgl.uniform1iv(paramInt1, paramInt3, this.intBuffer);
    }
    label30:
    do
    {
      return;
      if (paramInt2 == 2)
      {
        this.pgl.uniform2iv(paramInt1, paramInt3, this.intBuffer);
        return;
      }
      if (paramInt2 == 3)
      {
        this.pgl.uniform3iv(paramInt1, paramInt3, this.intBuffer);
        return;
      }
    } while (paramInt2 != 4);
    this.pgl.uniform3iv(paramInt1, paramInt3, this.intBuffer);
  }
  
  public void setVertexShader(String paramString)
  {
    this.vertexFilename = paramString;
  }
  
  public void setVertexShader(URL paramURL)
  {
    this.vertexURL = paramURL;
  }
  
  public void unbind()
  {
    unbindTextures();
    this.pgl.useProgram(0);
    this.bound = false;
  }
  
  protected void unbindTextures()
  {
    Iterator localIterator;
    if (this.textures != null) {
      localIterator = this.textures.keySet().iterator();
    }
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        this.pgl.activeTexture(33984);
        return;
      }
      int i = ((Integer)localIterator.next()).intValue();
      Texture localTexture = (Texture)this.textures.get(Integer.valueOf(i));
      this.pgl.activeTexture(33984 + i);
      localTexture.unbind();
    }
  }
  
  protected void updateFloatBuffer(float[] paramArrayOfFloat)
  {
    this.floatBuffer = PGL.updateFloatBuffer(this.floatBuffer, paramArrayOfFloat, false);
  }
  
  protected void updateIntBuffer(int[] paramArrayOfInt)
  {
    this.intBuffer = PGL.updateIntBuffer(this.intBuffer, paramArrayOfInt, false);
  }
  
  protected class UniformValue
  {
    static final int FLOAT1 = 4;
    static final int FLOAT1VEC = 12;
    static final int FLOAT2 = 5;
    static final int FLOAT2VEC = 13;
    static final int FLOAT3 = 6;
    static final int FLOAT3VEC = 14;
    static final int FLOAT4 = 7;
    static final int FLOAT4VEC = 15;
    static final int INT1 = 0;
    static final int INT1VEC = 8;
    static final int INT2 = 1;
    static final int INT2VEC = 9;
    static final int INT3 = 2;
    static final int INT3VEC = 10;
    static final int INT4 = 3;
    static final int INT4VEC = 11;
    static final int MAT2 = 16;
    static final int MAT3 = 17;
    static final int MAT4 = 18;
    static final int SAMPLER2D = 19;
    int type;
    Object value;
    
    UniformValue(int paramInt, Object paramObject)
    {
      this.type = paramInt;
      this.value = paramObject;
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.PShader
 * JD-Core Version:    0.7.0.1
 */