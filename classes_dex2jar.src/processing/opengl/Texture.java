package processing.opengl;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class Texture
  implements PConstants
{
  protected static final int BILINEAR = 4;
  protected static final int LINEAR = 3;
  public static final int MAX_BUFFER_CACHE_SIZE = 3;
  protected static final int POINT = 2;
  protected static final int TEX2D = 0;
  protected static final int TEXRECT = 1;
  protected static final int TRILINEAR = 5;
  protected boolean bound;
  protected LinkedList<BufferData> bufferCache = null;
  protected Object bufferSource;
  protected int context;
  protected Method disposeBufferMethod;
  public int glFormat;
  public int glHeight;
  public int glMagFilter;
  public int glMinFilter;
  public int glName;
  public int glTarget;
  public int glWidth;
  public int glWrapS;
  public int glWrapT;
  public int height;
  protected boolean invertedX;
  protected boolean invertedY;
  protected float maxTexcoordU;
  protected float maxTexcoordV;
  protected boolean modified;
  protected int mx1;
  protected int mx2;
  protected int my1;
  protected int my2;
  protected PApplet parent;
  protected PGraphicsOpenGL pg;
  protected PGraphicsOpenGL pgDraw;
  protected PGL pgl;
  protected IntBuffer pixelBuffer = null;
  protected int[] rgbaPixels = null;
  protected FrameBuffer tempFbo = null;
  protected boolean usingMipmaps;
  protected boolean usingRepeat;
  public int width;
  
  public Texture(PApplet paramPApplet)
  {
    this.parent = paramPApplet;
    this.pg = ((PGraphicsOpenGL)paramPApplet.g);
    this.pgl = this.pg.pgl;
    this.context = this.pgl.createEmptyContext();
    this.pgDraw = null;
    this.glName = 0;
  }
  
  public Texture(PApplet paramPApplet, int paramInt1, int paramInt2)
  {
    this(paramPApplet, paramInt1, paramInt2, new Parameters());
  }
  
  public Texture(PApplet paramPApplet, int paramInt1, int paramInt2, Object paramObject)
  {
    this.parent = paramPApplet;
    this.pg = ((PGraphicsOpenGL)paramPApplet.g);
    this.pgl = this.pg.pgl;
    this.context = this.pgl.createEmptyContext();
    this.pgDraw = null;
    this.glName = 0;
    init(paramInt1, paramInt2, (Parameters)paramObject);
  }
  
  protected void allocate()
  {
    release();
    boolean bool = this.pgl.texturingIsEnabled(this.glTarget);
    int i = 0;
    if (!bool)
    {
      this.pgl.enableTexturing(this.glTarget);
      i = 1;
    }
    this.context = this.pgl.getCurrentContext();
    this.glName = this.pg.createTextureObject(this.context);
    this.pgl.bindTexture(this.glTarget, this.glName);
    this.pgl.texParameteri(this.glTarget, 10241, this.glMinFilter);
    this.pgl.texParameteri(this.glTarget, 10240, this.glMagFilter);
    this.pgl.texParameteri(this.glTarget, 10242, this.glWrapS);
    this.pgl.texParameteri(this.glTarget, 10243, this.glWrapT);
    if (PGraphicsOpenGL.anisoSamplingSupported) {
      this.pgl.texParameterf(this.glTarget, 34046, PGraphicsOpenGL.maxAnisoAmount);
    }
    this.pgl.texImage2D(this.glTarget, 0, this.glFormat, this.glWidth, this.glHeight, 0, 6408, 5121, null);
    this.pgl.initTexture(this.glTarget, 6408, this.width, this.height);
    this.pgl.bindTexture(this.glTarget, 0);
    if (i != 0) {
      this.pgl.disableTexturing(this.glTarget);
    }
    this.bound = false;
  }
  
  public boolean available()
  {
    return this.glName > 0;
  }
  
  public void bind()
  {
    if (!this.pgl.texturingIsEnabled(this.glTarget)) {
      this.pgl.enableTexturing(this.glTarget);
    }
    this.pgl.bindTexture(this.glTarget, this.glName);
    this.bound = true;
  }
  
  public boolean bound()
  {
    return this.bound;
  }
  
  protected boolean bufferUpdate()
  {
    try
    {
      localBufferData = (BufferData)this.bufferCache.remove(0);
      boolean bool = false;
      if (localBufferData != null)
      {
        if ((localBufferData.w != this.width) || (localBufferData.h != this.height)) {
          init(localBufferData.w, localBufferData.h);
        }
        setNative(localBufferData.rgbBuf, 0, 0, this.width, this.height);
        localBufferData.dispose();
        bool = true;
      }
      return bool;
    }
    catch (NoSuchElementException localNoSuchElementException)
    {
      for (;;)
      {
        PGraphics.showWarning("Don't have pixel data to copy to texture");
        BufferData localBufferData = null;
      }
    }
  }
  
  protected boolean bufferUpdate(int[] paramArrayOfInt)
  {
    try
    {
      localBufferData = (BufferData)this.bufferCache.remove(0);
      boolean bool = false;
      if (localBufferData != null)
      {
        if ((localBufferData.w != this.width) || (localBufferData.h != this.height)) {
          init(localBufferData.w, localBufferData.h);
        }
        setNative(localBufferData.rgbBuf, 0, 0, this.width, this.height);
        localBufferData.rgbBuf.get(paramArrayOfInt);
        convertToARGB(paramArrayOfInt);
        localBufferData.dispose();
        bool = true;
      }
      return bool;
    }
    catch (NoSuchElementException localNoSuchElementException)
    {
      for (;;)
      {
        PGraphics.showWarning("Don't have pixel data to copy to texture");
        BufferData localBufferData = null;
      }
    }
  }
  
  public void colorBufferOf(PGraphicsOpenGL paramPGraphicsOpenGL)
  {
    this.pgDraw = paramPGraphicsOpenGL;
  }
  
  protected boolean contextIsOutdated()
  {
    if (this.pgl.contextIsCurrent(this.context)) {}
    for (boolean bool = false;; bool = true)
    {
      if (bool)
      {
        this.pg.removeTextureObject(this.glName, this.context);
        this.glName = 0;
      }
      return bool;
    }
  }
  
  protected void convertToARGB(int[] paramArrayOfInt)
  {
    int i = 0;
    int j = 0;
    if (PGL.BIG_ENDIAN)
    {
      int i3 = 0;
      if (i3 >= this.height) {
        return;
      }
      int i4 = 0;
      for (;;)
      {
        if (i4 >= this.width)
        {
          i3++;
          break;
        }
        int i5 = j + 1;
        int i6 = paramArrayOfInt[j];
        int i7 = i + 1;
        paramArrayOfInt[i] = (i6 >> 8 | 0xFF000000 & i6 << 24);
        i4++;
        j = i5;
        i = i7;
      }
    }
    int k = 0;
    label88:
    int m;
    if (k < this.height) {
      m = 0;
    }
    for (;;)
    {
      if (m >= this.width)
      {
        k++;
        break label88;
        break;
      }
      int n = j + 1;
      int i1 = paramArrayOfInt[j];
      int i2 = i + 1;
      paramArrayOfInt[i] = ((i1 & 0xFF) << 16 | (0xFF0000 & i1) >> 16 | 0xFF00FF00 & i1);
      m++;
      j = n;
      i = i2;
    }
  }
  
  protected void convertToRGBA(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3)
  {
    if (PGL.BIG_ENDIAN) {
      switch (paramInt1)
      {
      }
    }
    for (;;)
    {
      return;
      for (int i4 = 0; i4 < paramArrayOfInt1.length; i4++) {
        paramArrayOfInt2[i4] = (0xFFFFFF00 | paramArrayOfInt1[i4]);
      }
      continue;
      for (int i3 = 0; i3 < paramArrayOfInt1.length; i3++) {
        paramArrayOfInt2[i3] = (0xFF | paramArrayOfInt1[i3] << 8);
      }
      continue;
      for (int i1 = 0; i1 < paramArrayOfInt1.length; i1++)
      {
        int i2 = paramArrayOfInt1[i1];
        paramArrayOfInt2[i1] = (i2 << 8 | 0xFF & i2 >> 24);
      }
      continue;
      switch (paramInt1)
      {
      case 3: 
      default: 
        return;
      case 1: 
        for (int m = 0; m < paramArrayOfInt1.length; m++)
        {
          int n = paramArrayOfInt1[m];
          paramArrayOfInt2[m] = (0xFF000000 | (n & 0xFF) << 16 | (n & 0xFF0000) >> 16 | 0xFF00 & n);
        }
      case 4: 
        for (int k = 0; k < paramArrayOfInt1.length; k++) {
          paramArrayOfInt2[k] = (0xFFFFFF | paramArrayOfInt1[k] << 24);
        }
      }
      for (int i = 0; i < paramArrayOfInt1.length; i++)
      {
        int j = paramArrayOfInt1[i];
        paramArrayOfInt2[i] = ((j & 0xFF) << 16 | (j & 0xFF0000) >> 16 | 0xFF00FF00 & j);
      }
    }
  }
  
  public void copyBufferFromSource(Object paramObject, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2)
  {
    if (this.bufferCache == null) {
      this.bufferCache = new LinkedList();
    }
    if (1 + this.bufferCache.size() <= 3)
    {
      this.bufferCache.add(new BufferData(paramObject, paramByteBuffer.asIntBuffer(), paramInt1, paramInt2));
      return;
    }
    try
    {
      this.disposeBufferMethod.invoke(this.bufferSource, new Object[] { paramObject });
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  protected void copyObject(Texture paramTexture)
  {
    release();
    this.width = paramTexture.width;
    this.height = paramTexture.height;
    this.parent = paramTexture.parent;
    this.pg = paramTexture.pg;
    this.glName = paramTexture.glName;
    this.glTarget = paramTexture.glTarget;
    this.glFormat = paramTexture.glFormat;
    this.glMinFilter = paramTexture.glMinFilter;
    this.glMagFilter = paramTexture.glMagFilter;
    this.glWidth = paramTexture.glWidth;
    this.glHeight = paramTexture.glHeight;
    this.usingMipmaps = paramTexture.usingMipmaps;
    this.usingRepeat = paramTexture.usingRepeat;
    this.maxTexcoordU = paramTexture.maxTexcoordU;
    this.maxTexcoordV = paramTexture.maxTexcoordV;
    this.invertedX = paramTexture.invertedX;
    this.invertedY = paramTexture.invertedY;
  }
  
  protected void copyTexture(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    if (this.tempFbo == null) {
      this.tempFbo = new FrameBuffer(this.parent, this.glWidth, this.glHeight);
    }
    this.tempFbo.setColorBuffer(this);
    this.tempFbo.disableDepthTest();
    this.pg.pushFramebuffer();
    this.pg.setFramebuffer(this.tempFbo);
    if (paramBoolean) {
      this.pgl.drawTexture(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, 0, 0, this.width, this.height);
    }
    for (;;)
    {
      this.pg.popFramebuffer();
      updateTexels(paramInt5, paramInt6, paramInt7, paramInt8);
      return;
      this.pgl.drawTexture(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt5, paramInt6, paramInt7, paramInt8);
    }
  }
  
  protected void copyTexture(Texture paramTexture, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    if (paramTexture == null) {
      throw new RuntimeException("Source texture is null");
    }
    if (this.tempFbo == null) {
      this.tempFbo = new FrameBuffer(this.parent, this.glWidth, this.glHeight);
    }
    this.tempFbo.setColorBuffer(this);
    this.tempFbo.disableDepthTest();
    this.pg.pushFramebuffer();
    this.pg.setFramebuffer(this.tempFbo);
    this.pgl.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
    this.pgl.clear(16384);
    if (paramBoolean) {
      this.pgl.drawTexture(paramTexture.glTarget, paramTexture.glName, paramTexture.glWidth, paramTexture.glHeight, paramInt1, paramInt2, paramInt3, paramInt4, 0, 0, this.width, this.height);
    }
    for (;;)
    {
      this.pg.popFramebuffer();
      updateTexels(paramInt1, paramInt2, paramInt3, paramInt4);
      return;
      this.pgl.drawTexture(paramTexture.glTarget, paramTexture.glName, paramTexture.glWidth, paramTexture.glHeight, paramInt1, paramInt2, paramInt3, paramInt4, paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  protected void finalize()
    throws Throwable
  {
    try
    {
      if (this.glName != 0) {
        this.pg.finalizeTextureObject(this.glName, this.context);
      }
      return;
    }
    finally
    {
      super.finalize();
    }
  }
  
  protected void flipArrayOnX(int[] paramArrayOfInt, int paramInt)
  {
    int i = 0;
    int j = paramInt * (-1 + this.width);
    int m;
    for (int k = 0;; k++)
    {
      if (k >= this.width / 2) {
        return;
      }
      m = 0;
      if (m < this.height) {
        break;
      }
      i += paramInt;
      j -= paramInt;
    }
    int n = i + paramInt * m * this.width;
    int i1 = j + paramInt * m * this.width;
    for (int i2 = 0;; i2++)
    {
      if (i2 >= paramInt)
      {
        m++;
        break;
      }
      int i3 = paramArrayOfInt[n];
      paramArrayOfInt[n] = paramArrayOfInt[i1];
      paramArrayOfInt[i1] = i3;
      n++;
      i1++;
    }
  }
  
  protected void flipArrayOnY(int[] paramArrayOfInt, int paramInt)
  {
    int i = 0;
    int j = paramInt * (-1 + this.height) * this.width;
    int k = 0;
    if (k >= this.height / 2) {
      return;
    }
    for (int m = 0;; m++)
    {
      if (m >= paramInt * this.width)
      {
        j -= 2 * (paramInt * this.width);
        k++;
        break;
      }
      int n = paramArrayOfInt[i];
      paramArrayOfInt[i] = paramArrayOfInt[j];
      paramArrayOfInt[j] = n;
      i++;
      j++;
    }
  }
  
  public void get(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      throw new RuntimeException("Trying to copy texture to null pixels array");
    }
    if (paramArrayOfInt.length != this.width * this.height) {
      throw new RuntimeException("Trying to copy texture to pixels array of wrong size");
    }
    if (this.tempFbo == null) {
      this.tempFbo = new FrameBuffer(this.parent, this.glWidth, this.glHeight);
    }
    this.tempFbo.setColorBuffer(this);
    this.pg.pushFramebuffer();
    this.pg.setFramebuffer(this.tempFbo);
    this.tempFbo.readPixels();
    this.pg.popFramebuffer();
    this.tempFbo.getPixels(paramArrayOfInt);
    convertToARGB(paramArrayOfInt);
    if (this.invertedX) {
      flipArrayOnX(paramArrayOfInt, 1);
    }
    if (this.invertedY) {
      flipArrayOnY(paramArrayOfInt, 1);
    }
  }
  
  public int getModifiedX1()
  {
    return this.mx1;
  }
  
  public int getModifiedX2()
  {
    return this.mx2;
  }
  
  public int getModifiedY1()
  {
    return this.my1;
  }
  
  public int getModifiedY2()
  {
    return this.my2;
  }
  
  public Parameters getParameters()
  {
    Parameters localParameters = new Parameters();
    if (this.glTarget == 3553) {
      localParameters.target = 0;
    }
    if (this.glFormat == 6407)
    {
      localParameters.format = 1;
      if ((this.glMagFilter != 9728) || (this.glMinFilter != 9728)) {
        break label136;
      }
      localParameters.sampling = 2;
      localParameters.mipmaps = false;
      label68:
      if (this.glWrapS != 33071) {
        break label301;
      }
      localParameters.wrapU = 0;
      label83:
      if (this.glWrapT != 33071) {
        break label319;
      }
      localParameters.wrapV = 0;
    }
    label136:
    label301:
    label319:
    while (this.glWrapT != 10497)
    {
      return localParameters;
      if (this.glFormat == 6408)
      {
        localParameters.format = 2;
        break;
      }
      if (this.glFormat != 6406) {
        break;
      }
      localParameters.format = 4;
      break;
      if ((this.glMagFilter == 9728) && (this.glMinFilter == 9729))
      {
        localParameters.sampling = 3;
        localParameters.mipmaps = false;
        break label68;
      }
      if ((this.glMagFilter == 9728) && (this.glMinFilter == 9985))
      {
        localParameters.sampling = 3;
        localParameters.mipmaps = true;
        break label68;
      }
      if ((this.glMagFilter == 9729) && (this.glMinFilter == 9729))
      {
        localParameters.sampling = 4;
        localParameters.mipmaps = false;
        break label68;
      }
      if ((this.glMagFilter == 9729) && (this.glMinFilter == 9985))
      {
        localParameters.sampling = 4;
        localParameters.mipmaps = true;
        break label68;
      }
      if ((this.glMagFilter != 9729) || (this.glMinFilter != 9987)) {
        break label68;
      }
      localParameters.sampling = 5;
      localParameters.mipmaps = true;
      break label68;
      if (this.glWrapS != 10497) {
        break label83;
      }
      localParameters.wrapU = 1;
      break label83;
    }
    localParameters.wrapV = 1;
    return localParameters;
  }
  
  protected void getSourceMethods()
  {
    try
    {
      this.disposeBufferMethod = this.bufferSource.getClass().getMethod("disposeBuffer", new Class[] { Object.class });
      return;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("Provided source object doesn't have a disposeBuffer method.");
    }
  }
  
  public boolean hasBufferSource()
  {
    return this.bufferSource != null;
  }
  
  public boolean hasBuffers()
  {
    return (this.bufferSource != null) && (this.bufferCache != null) && (this.bufferCache.size() > 0);
  }
  
  public void init(int paramInt1, int paramInt2)
  {
    if (this.glName > 0) {}
    for (Parameters localParameters = getParameters();; localParameters = new Parameters())
    {
      init(paramInt1, paramInt2, localParameters);
      return;
    }
  }
  
  public void init(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11)
  {
    this.width = paramInt1;
    this.height = paramInt2;
    this.glName = paramInt3;
    this.glTarget = paramInt4;
    this.glFormat = paramInt5;
    this.glWidth = paramInt6;
    this.glHeight = paramInt7;
    this.glMinFilter = paramInt8;
    this.glMagFilter = paramInt9;
    this.glWrapS = paramInt10;
    this.glWrapT = paramInt11;
    this.maxTexcoordU = (paramInt1 / paramInt6);
    this.maxTexcoordV = (paramInt2 / paramInt7);
    boolean bool1;
    if ((paramInt8 != 9985) && (paramInt8 != 9987))
    {
      bool1 = false;
      this.usingMipmaps = bool1;
      if ((paramInt10 == 10497) || (paramInt11 == 10497)) {
        break label140;
      }
    }
    label140:
    for (boolean bool2 = false;; bool2 = true)
    {
      this.usingRepeat = bool2;
      return;
      bool1 = true;
      break;
    }
  }
  
  public void init(int paramInt1, int paramInt2, Parameters paramParameters)
  {
    setParameters(paramParameters);
    setSize(paramInt1, paramInt2);
    allocate();
  }
  
  public void invertedX(boolean paramBoolean)
  {
    this.invertedX = paramBoolean;
  }
  
  public boolean invertedX()
  {
    return this.invertedX;
  }
  
  public void invertedY(boolean paramBoolean)
  {
    this.invertedY = paramBoolean;
  }
  
  public boolean invertedY()
  {
    return this.invertedY;
  }
  
  protected boolean isColorBuffer()
  {
    return this.pgDraw != null;
  }
  
  public boolean isModified()
  {
    return this.modified;
  }
  
  protected void loadPixels(int paramInt)
  {
    if ((this.rgbaPixels == null) || (this.rgbaPixels.length < paramInt)) {
      this.rgbaPixels = new int[paramInt];
    }
  }
  
  public void loadPixels(int[] paramArrayOfInt)
  {
    if (hasBuffers()) {
      bufferUpdate(paramArrayOfInt);
    }
    if (isModified()) {
      get(paramArrayOfInt);
    }
    setModified(false);
  }
  
  public float maxTexcoordU()
  {
    return this.maxTexcoordU;
  }
  
  public float maxTexcoordV()
  {
    return this.maxTexcoordV;
  }
  
  public void put(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    copyTexture(paramInt1, paramInt2, paramInt3, paramInt4, 0, 0, paramInt5, paramInt6, false);
  }
  
  public void put(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
  {
    copyTexture(paramInt1, paramInt2, paramInt3, paramInt4, paramInt7, paramInt8, paramInt9, paramInt10, false);
  }
  
  public void put(Texture paramTexture)
  {
    copyTexture(paramTexture, 0, 0, paramTexture.width, paramTexture.height, false);
  }
  
  public void put(Texture paramTexture, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    copyTexture(paramTexture, paramInt1, paramInt2, paramInt3, paramInt4, false);
  }
  
  protected void release()
  {
    if (this.glName != 0)
    {
      this.pg.finalizeTextureObject(this.glName, this.context);
      this.glName = 0;
    }
  }
  
  public void resize(int paramInt1, int paramInt2)
  {
    release();
    Texture localTexture = new Texture(this.parent, paramInt1, paramInt2, getParameters());
    localTexture.set(this);
    copyObject(localTexture);
    this.tempFbo = null;
  }
  
  public void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    copyTexture(paramInt1, paramInt2, paramInt3, paramInt4, 0, 0, paramInt5, paramInt6, true);
  }
  
  public void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
  {
    copyTexture(paramInt1, paramInt2, paramInt3, paramInt4, paramInt7, paramInt8, paramInt9, paramInt10, true);
  }
  
  public void set(PImage paramPImage)
  {
    set((Texture)this.pg.getCache(paramPImage));
  }
  
  public void set(PImage paramPImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    set((Texture)this.pg.getCache(paramPImage), paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void set(Texture paramTexture)
  {
    copyTexture(paramTexture, 0, 0, paramTexture.width, paramTexture.height, true);
  }
  
  public void set(Texture paramTexture, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    copyTexture(paramTexture, paramInt1, paramInt2, paramInt3, paramInt4, true);
  }
  
  public void set(int[] paramArrayOfInt)
  {
    set(paramArrayOfInt, 0, 0, this.width, this.height, 2);
  }
  
  public void set(int[] paramArrayOfInt, int paramInt)
  {
    set(paramArrayOfInt, 0, 0, this.width, this.height, paramInt);
  }
  
  public void set(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    set(paramArrayOfInt, paramInt1, paramInt2, paramInt3, paramInt4, 2);
  }
  
  public void set(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (paramArrayOfInt == null) {
      PGraphics.showWarning("The pixels array is null.");
    }
    do
    {
      return;
      if (paramArrayOfInt.length < paramInt3 * paramInt4)
      {
        PGraphics.showWarning("The pixel array has a length of " + paramArrayOfInt.length + ", but it should be at least " + paramInt3 * paramInt4);
        return;
      }
    } while (paramArrayOfInt.length == 0);
    boolean bool = this.pgl.texturingIsEnabled(this.glTarget);
    int i = 0;
    if (!bool)
    {
      this.pgl.enableTexturing(this.glTarget);
      i = 1;
    }
    this.pgl.bindTexture(this.glTarget, this.glName);
    if (this.usingMipmaps) {
      if (PGraphicsOpenGL.autoMipmapGenSupported)
      {
        loadPixels(paramInt3 * paramInt4);
        convertToRGBA(paramArrayOfInt, this.rgbaPixels, paramInt5, paramInt3, paramInt4);
        updatePixelBuffer(this.rgbaPixels);
        this.pgl.texSubImage2D(this.glTarget, 0, paramInt1, paramInt2, paramInt3, paramInt4, 6408, 5121, this.pixelBuffer);
        this.pgl.generateMipmap(this.glTarget);
      }
    }
    for (;;)
    {
      this.pgl.bindTexture(this.glTarget, 0);
      if (i != 0) {
        this.pgl.disableTexturing(this.glTarget);
      }
      updateTexels(paramInt1, paramInt2, paramInt3, paramInt4);
      return;
      loadPixels(paramInt3 * paramInt4);
      convertToRGBA(paramArrayOfInt, this.rgbaPixels, paramInt5, paramInt3, paramInt4);
      updatePixelBuffer(this.rgbaPixels);
      this.pgl.texSubImage2D(this.glTarget, 0, paramInt1, paramInt2, paramInt3, paramInt4, 6408, 5121, this.pixelBuffer);
      continue;
      loadPixels(paramInt3 * paramInt4);
      convertToRGBA(paramArrayOfInt, this.rgbaPixels, paramInt5, paramInt3, paramInt4);
      updatePixelBuffer(this.rgbaPixels);
      this.pgl.texSubImage2D(this.glTarget, 0, paramInt1, paramInt2, paramInt3, paramInt4, 6408, 5121, this.pixelBuffer);
    }
  }
  
  public void setBufferSource(Object paramObject)
  {
    this.bufferSource = paramObject;
    getSourceMethods();
  }
  
  public void setModified()
  {
    this.modified = true;
  }
  
  public void setModified(boolean paramBoolean)
  {
    this.modified = paramBoolean;
  }
  
  public void setNative(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramIntBuffer == null) {
      PGraphics.showWarning("The pixel buffer is null.");
    }
    do
    {
      return;
      if (paramIntBuffer.capacity() < paramInt3 * paramInt4)
      {
        PGraphics.showWarning("The pixel bufer has a length of " + paramIntBuffer.capacity() + ", but it should be at least " + paramInt3 * paramInt4);
        return;
      }
    } while (paramIntBuffer.capacity() == 0);
    boolean bool = this.pgl.texturingIsEnabled(this.glTarget);
    int i = 0;
    if (!bool)
    {
      this.pgl.enableTexturing(this.glTarget);
      i = 1;
    }
    this.pgl.bindTexture(this.glTarget, this.glName);
    if (this.usingMipmaps) {
      if (PGraphicsOpenGL.autoMipmapGenSupported)
      {
        this.pgl.texSubImage2D(this.glTarget, 0, paramInt1, paramInt2, paramInt3, paramInt4, 6408, 5121, paramIntBuffer);
        this.pgl.generateMipmap(this.glTarget);
      }
    }
    for (;;)
    {
      this.pgl.bindTexture(this.glTarget, 0);
      if (i != 0) {
        this.pgl.disableTexturing(this.glTarget);
      }
      updateTexels(paramInt1, paramInt2, paramInt3, paramInt4);
      return;
      this.pgl.texSubImage2D(this.glTarget, 0, paramInt1, paramInt2, paramInt3, paramInt4, 6408, 5121, paramIntBuffer);
      continue;
      this.pgl.texSubImage2D(this.glTarget, 0, paramInt1, paramInt2, paramInt3, paramInt4, 6408, 5121, paramIntBuffer);
    }
  }
  
  public void setNative(int[] paramArrayOfInt)
  {
    setNative(paramArrayOfInt, 0, 0, this.width, this.height);
  }
  
  public void setNative(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    updatePixelBuffer(paramArrayOfInt);
    setNative(this.pixelBuffer, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void setParameters(Parameters paramParameters)
  {
    int i = 1;
    if (paramParameters.target == 0)
    {
      this.glTarget = 3553;
      if (paramParameters.format != i) {
        break label157;
      }
      this.glFormat = 6407;
      if (paramParameters.sampling != 2) {
        break label204;
      }
      this.glMagFilter = 9728;
      this.glMinFilter = 9728;
      label53:
      if (paramParameters.wrapU != 0) {
        break label311;
      }
      this.glWrapS = 33071;
      label67:
      if (paramParameters.wrapV != 0) {
        break label340;
      }
      this.glWrapT = 33071;
      label81:
      if ((this.glMinFilter == 9985) || (this.glMinFilter == 9987)) {
        break label369;
      }
    }
    label157:
    label204:
    label340:
    label369:
    for (boolean bool = false;; bool = i)
    {
      this.usingMipmaps = bool;
      if ((this.glWrapS != 10497) && (this.glWrapT != 10497)) {
        i = 0;
      }
      this.usingRepeat = i;
      this.invertedX = false;
      this.invertedY = false;
      return;
      throw new RuntimeException("Unknown texture target");
      if (paramParameters.format == 2)
      {
        this.glFormat = 6408;
        break;
      }
      if (paramParameters.format == 4)
      {
        this.glFormat = 6406;
        break;
      }
      throw new RuntimeException("Unknown texture format");
      if (paramParameters.sampling == 3)
      {
        this.glMagFilter = 9728;
        if (paramParameters.mipmaps) {}
        this.glMinFilter = 9729;
        break label53;
      }
      if (paramParameters.sampling == 4)
      {
        this.glMagFilter = 9729;
        if (paramParameters.mipmaps) {}
        this.glMinFilter = 9729;
        break label53;
      }
      if (paramParameters.sampling == 5)
      {
        this.glMagFilter = 9729;
        if (paramParameters.mipmaps) {}
        this.glMinFilter = 9729;
        break label53;
      }
      throw new RuntimeException("Unknown texture filtering mode");
      if (paramParameters.wrapU == i)
      {
        this.glWrapS = 10497;
        break label67;
      }
      throw new RuntimeException("Unknown wrapping mode");
      if (paramParameters.wrapV == i)
      {
        this.glWrapT = 10497;
        break label81;
      }
      throw new RuntimeException("Unknown wrapping mode");
    }
  }
  
  protected void setSize(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
    if (PGraphicsOpenGL.npotTexSupported) {
      this.glWidth = paramInt1;
    }
    for (this.glHeight = paramInt2; (this.glWidth > PGraphicsOpenGL.maxTextureSize) || (this.glHeight > PGraphicsOpenGL.maxTextureSize); this.glHeight = PGL.nextPowerOfTwo(paramInt2))
    {
      this.glHeight = 0;
      this.glWidth = 0;
      throw new RuntimeException("Image width and height cannot be larger than " + PGraphicsOpenGL.maxTextureSize + " with this graphics card.");
      this.glWidth = PGL.nextPowerOfTwo(paramInt1);
    }
    this.maxTexcoordU = (this.width / this.glWidth);
    this.maxTexcoordV = (this.height / this.glHeight);
  }
  
  public void unbind()
  {
    if (this.pgl.textureIsBound(this.glTarget, this.glName))
    {
      if (this.pgl.texturingIsEnabled(this.glTarget)) {
        break label72;
      }
      this.pgl.enableTexturing(this.glTarget);
      this.pgl.bindTexture(this.glTarget, 0);
      this.pgl.disableTexturing(this.glTarget);
    }
    for (;;)
    {
      this.bound = false;
      return;
      label72:
      this.pgl.bindTexture(this.glTarget, 0);
    }
  }
  
  protected void updatePixelBuffer(int[] paramArrayOfInt)
  {
    this.pixelBuffer = PGL.updateIntBuffer(this.pixelBuffer, paramArrayOfInt, true);
  }
  
  public void updateTexels()
  {
    updateTexelsImpl(0, 0, this.width, this.height);
  }
  
  public void updateTexels(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    updateTexelsImpl(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void updateTexelsImpl(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt1 + paramInt3;
    int j = paramInt2 + paramInt4;
    if (!this.modified)
    {
      this.mx1 = PApplet.max(0, paramInt1);
      this.mx2 = PApplet.min(-1 + this.width, i);
      this.my1 = PApplet.max(0, paramInt2);
      this.my2 = PApplet.min(-1 + this.height, j);
      this.modified = true;
    }
    do
    {
      return;
      if (paramInt1 < this.mx1) {
        this.mx1 = PApplet.max(0, paramInt1);
      }
      if (paramInt1 > this.mx2) {
        this.mx2 = PApplet.min(-1 + this.width, paramInt1);
      }
      if (paramInt2 < this.my1) {
        this.my1 = PApplet.max(0, paramInt2);
      }
      if (paramInt2 > this.my2) {
        this.my2 = paramInt2;
      }
      if (i < this.mx1) {
        this.mx1 = PApplet.max(0, i);
      }
      if (i > this.mx2) {
        this.mx2 = PApplet.min(-1 + this.width, i);
      }
      if (j < this.my1) {
        this.my1 = PApplet.max(0, j);
      }
    } while (j <= this.my2);
    this.my2 = PApplet.min(-1 + this.height, j);
  }
  
  public void usingMipmaps(boolean paramBoolean, int paramInt)
  {
    if (paramBoolean) {
      if ((this.glMinFilter != 9985) && (this.glMinFilter != 9987))
      {
        if (paramInt != 2) {
          break label117;
        }
        this.glMagFilter = 9728;
        this.glMinFilter = 9728;
      }
    }
    for (this.usingMipmaps = true;; this.usingMipmaps = false)
    {
      bind();
      this.pgl.texParameteri(this.glTarget, 10241, this.glMinFilter);
      this.pgl.texParameteri(this.glTarget, 10240, this.glMagFilter);
      if ((this.usingMipmaps) && (PGraphicsOpenGL.autoMipmapGenSupported)) {
        this.pgl.generateMipmap(this.glTarget);
      }
      unbind();
      return;
      label117:
      if (paramInt == 3)
      {
        this.glMagFilter = 9728;
        this.glMinFilter = 9729;
        break;
      }
      if (paramInt == 4)
      {
        this.glMagFilter = 9729;
        this.glMinFilter = 9729;
        break;
      }
      if (paramInt == 5)
      {
        this.glMagFilter = 9729;
        this.glMinFilter = 9729;
        break;
      }
      throw new RuntimeException("Unknown texture filtering mode");
      if ((this.glMinFilter == 9985) || (this.glMinFilter == 9987)) {
        this.glMinFilter = 9729;
      }
    }
  }
  
  public boolean usingMipmaps()
  {
    return this.usingMipmaps;
  }
  
  public void usingRepeat(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.glWrapS = 10497;
      this.glWrapT = 10497;
    }
    for (this.usingRepeat = true;; this.usingRepeat = false)
    {
      bind();
      this.pgl.texParameteri(this.glTarget, 10242, this.glWrapS);
      this.pgl.texParameteri(this.glTarget, 10243, this.glWrapT);
      unbind();
      return;
      this.glWrapS = 33071;
      this.glWrapT = 33071;
    }
  }
  
  public boolean usingRepeat()
  {
    return this.usingRepeat;
  }
  
  protected class BufferData
  {
    int h;
    Object natBuf;
    IntBuffer rgbBuf;
    int w;
    
    BufferData(Object paramObject, IntBuffer paramIntBuffer, int paramInt1, int paramInt2)
    {
      this.natBuf = paramObject;
      this.rgbBuf = paramIntBuffer;
      this.w = paramInt1;
      this.h = paramInt2;
    }
    
    void dispose()
    {
      try
      {
        Method localMethod = Texture.this.disposeBufferMethod;
        Object localObject = Texture.this.bufferSource;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.natBuf;
        localMethod.invoke(localObject, arrayOfObject);
        this.natBuf = null;
        this.rgbBuf = null;
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public static class Parameters
  {
    public int format;
    public boolean mipmaps;
    public int sampling;
    public int target;
    public int wrapU;
    public int wrapV;
    
    public Parameters()
    {
      this.target = 0;
      this.format = 2;
      this.sampling = 4;
      this.mipmaps = true;
      this.wrapU = 0;
      this.wrapV = 0;
    }
    
    public Parameters(int paramInt)
    {
      this.target = 0;
      this.format = paramInt;
      this.sampling = 4;
      this.mipmaps = true;
      this.wrapU = 0;
      this.wrapV = 0;
    }
    
    public Parameters(int paramInt1, int paramInt2)
    {
      this.target = 0;
      this.format = paramInt1;
      this.sampling = paramInt2;
      this.mipmaps = true;
      this.wrapU = 0;
      this.wrapV = 0;
    }
    
    public Parameters(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this.target = 0;
      this.format = paramInt1;
      this.mipmaps = paramBoolean;
      if ((paramInt2 == 5) && (!paramBoolean)) {}
      for (this.sampling = 4;; this.sampling = paramInt2)
      {
        this.wrapU = 0;
        this.wrapV = 0;
        return;
      }
    }
    
    public Parameters(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
    {
      this.target = 0;
      this.format = paramInt1;
      this.mipmaps = paramBoolean;
      if ((paramInt2 == 5) && (!paramBoolean)) {}
      for (this.sampling = 4;; this.sampling = paramInt2)
      {
        this.wrapU = paramInt3;
        this.wrapV = paramInt3;
        return;
      }
    }
    
    public Parameters(Parameters paramParameters)
    {
      set(paramParameters);
    }
    
    public void set(int paramInt)
    {
      this.format = paramInt;
    }
    
    public void set(int paramInt1, int paramInt2)
    {
      this.format = paramInt1;
      this.sampling = paramInt2;
    }
    
    public void set(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      this.format = paramInt1;
      this.sampling = paramInt2;
      this.mipmaps = paramBoolean;
    }
    
    public void set(Parameters paramParameters)
    {
      this.target = paramParameters.target;
      this.format = paramParameters.format;
      this.sampling = paramParameters.sampling;
      this.mipmaps = paramParameters.mipmaps;
      this.wrapU = paramParameters.wrapU;
      this.wrapV = paramParameters.wrapV;
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.Texture
 * JD-Core Version:    0.7.0.1
 */