package processing.opengl;

import java.nio.IntBuffer;
import processing.core.PApplet;
import processing.core.PConstants;

public class FrameBuffer
  implements PConstants
{
  protected Texture[] colorBufferTex;
  protected int context;
  protected int depthBits;
  public int glDepth;
  public int glDepthStencil;
  public int glFbo;
  public int glMultisample;
  public int glStencil;
  public int height;
  protected boolean multisample;
  protected boolean noDepth;
  protected int nsamples;
  protected int numColorBuffers;
  protected boolean packedDepthStencil;
  protected PApplet parent;
  protected PGraphicsOpenGL pg;
  protected PGL pgl;
  protected IntBuffer pixelBuffer;
  protected boolean screenFb;
  protected int stencilBits;
  public int width;
  
  FrameBuffer(PApplet paramPApplet)
  {
    this.parent = paramPApplet;
    this.pg = ((PGraphicsOpenGL)paramPApplet.g);
    this.pgl = this.pg.pgl;
    this.context = this.pgl.createEmptyContext();
  }
  
  FrameBuffer(PApplet paramPApplet, int paramInt1, int paramInt2)
  {
    this(paramPApplet, paramInt1, paramInt2, 1, 1, 0, 0, false, false);
  }
  
  FrameBuffer(PApplet paramPApplet, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean1, boolean paramBoolean2)
  {
    this(paramPApplet);
    this.glFbo = 0;
    this.glDepth = 0;
    this.glStencil = 0;
    this.glDepthStencil = 0;
    this.glMultisample = 0;
    if (paramBoolean2)
    {
      paramInt4 = 0;
      paramInt3 = 0;
      paramInt6 = 0;
      paramInt5 = 0;
    }
    this.width = paramInt1;
    this.height = paramInt2;
    int i;
    if (1 < paramInt3)
    {
      this.multisample = true;
      this.nsamples = paramInt3;
      this.numColorBuffers = paramInt4;
      this.colorBufferTex = new Texture[this.numColorBuffers];
      i = 0;
      label94:
      if (i < this.numColorBuffers) {
        break label164;
      }
      if ((paramInt5 >= 1) || (paramInt6 >= 1)) {
        break label178;
      }
      this.depthBits = 0;
      this.stencilBits = 0;
      this.packedDepthStencil = false;
    }
    for (;;)
    {
      this.screenFb = paramBoolean2;
      allocate();
      this.noDepth = false;
      this.pixelBuffer = null;
      return;
      this.multisample = false;
      this.nsamples = 1;
      break;
      label164:
      this.colorBufferTex[i] = null;
      i++;
      break label94;
      label178:
      if (paramBoolean1)
      {
        this.depthBits = 24;
        this.stencilBits = 8;
        this.packedDepthStencil = true;
      }
      else
      {
        this.depthBits = paramInt5;
        this.stencilBits = paramInt6;
        this.packedDepthStencil = false;
      }
    }
  }
  
  FrameBuffer(PApplet paramPApplet, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this(paramPApplet, paramInt1, paramInt2, 1, 1, 0, 0, false, paramBoolean);
  }
  
  protected void allocate()
  {
    release();
    this.context = this.pgl.getCurrentContext();
    if (this.screenFb) {
      this.glFbo = 0;
    }
    do
    {
      return;
      this.glFbo = this.pg.createFrameBufferObject(this.context);
      if (this.multisample) {
        createColorBufferMultisample();
      }
      if (this.packedDepthStencil)
      {
        createPackedDepthStencilBuffer();
        return;
      }
      if (this.depthBits > 0) {
        createDepthBuffer();
      }
    } while (this.stencilBits <= 0);
    createStencilBuffer();
  }
  
  public void bind()
  {
    this.pgl.bindFramebuffer(36160, this.glFbo);
  }
  
  public void clear()
  {
    this.pg.pushFramebuffer();
    this.pg.setFramebuffer(this);
    this.pgl.clearDepth(1.0F);
    this.pgl.clearStencil(0);
    this.pgl.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
    this.pgl.clear(17664);
    this.pg.popFramebuffer();
  }
  
  protected boolean contextIsOutdated()
  {
    boolean bool;
    if (this.screenFb) {
      bool = false;
    }
    label157:
    for (;;)
    {
      return bool;
      if (this.pgl.contextIsCurrent(this.context)) {}
      for (bool = false;; bool = true)
      {
        if (!bool) {
          break label157;
        }
        this.pg.removeFrameBufferObject(this.glFbo, this.context);
        this.pg.removeRenderBufferObject(this.glDepth, this.context);
        this.pg.removeRenderBufferObject(this.glStencil, this.context);
        this.pg.removeRenderBufferObject(this.glDepthStencil, this.context);
        this.pg.removeRenderBufferObject(this.glMultisample, this.context);
        this.glFbo = 0;
        this.glDepth = 0;
        this.glStencil = 0;
        this.glDepthStencil = 0;
        this.glMultisample = 0;
        for (int i = 0; i < this.numColorBuffers; i++) {
          this.colorBufferTex[i] = null;
        }
        break;
      }
    }
  }
  
  public void copy(FrameBuffer paramFrameBuffer1, FrameBuffer paramFrameBuffer2)
  {
    this.pgl.bindFramebuffer(-1, this.glFbo);
    this.pgl.bindFramebuffer(-1, paramFrameBuffer1.glFbo);
    this.pgl.blitFramebuffer(0, 0, this.width, this.height, 0, 0, paramFrameBuffer1.width, paramFrameBuffer1.height, 16384, 9728);
    this.pgl.bindFramebuffer(-1, paramFrameBuffer2.glFbo);
    this.pgl.bindFramebuffer(-1, paramFrameBuffer2.glFbo);
  }
  
  protected void createColorBufferMultisample()
  {
    if (this.screenFb) {
      return;
    }
    this.pg.pushFramebuffer();
    this.pg.setFramebuffer(this);
    this.glMultisample = this.pg.createRenderBufferObject(this.context);
    this.pgl.bindRenderbuffer(36161, this.glMultisample);
    this.pgl.renderbufferStorageMultisample(36161, this.nsamples, -1, this.width, this.height);
    this.pgl.framebufferRenderbuffer(36160, 36064, 36161, this.glMultisample);
    this.pg.popFramebuffer();
  }
  
  protected void createDepthBuffer()
  {
    if (this.screenFb) {
      return;
    }
    if ((this.width == 0) || (this.height == 0)) {
      throw new RuntimeException("PFramebuffer: size undefined.");
    }
    this.pg.pushFramebuffer();
    this.pg.setFramebuffer(this);
    this.glDepth = this.pg.createRenderBufferObject(this.context);
    this.pgl.bindRenderbuffer(36161, this.glDepth);
    int i = 33189;
    if (this.depthBits == 16)
    {
      i = 33189;
      if (!this.multisample) {
        break label174;
      }
      this.pgl.renderbufferStorageMultisample(36161, this.nsamples, i, this.width, this.height);
    }
    for (;;)
    {
      this.pgl.framebufferRenderbuffer(36160, 36096, 36161, this.glDepth);
      this.pg.popFramebuffer();
      return;
      if (this.depthBits == 24)
      {
        i = 33190;
        break;
      }
      if (this.depthBits != 32) {
        break;
      }
      i = 33191;
      break;
      label174:
      this.pgl.renderbufferStorage(36161, i, this.width, this.height);
    }
  }
  
  protected void createPackedDepthStencilBuffer()
  {
    if (this.screenFb) {
      return;
    }
    if ((this.width == 0) || (this.height == 0)) {
      throw new RuntimeException("PFramebuffer: size undefined.");
    }
    this.pg.pushFramebuffer();
    this.pg.setFramebuffer(this);
    this.glDepthStencil = this.pg.createRenderBufferObject(this.context);
    this.pgl.bindRenderbuffer(36161, this.glDepthStencil);
    if (this.multisample) {
      this.pgl.renderbufferStorageMultisample(36161, this.nsamples, 35056, this.width, this.height);
    }
    for (;;)
    {
      this.pgl.framebufferRenderbuffer(36160, 36096, 36161, this.glDepthStencil);
      this.pgl.framebufferRenderbuffer(36160, 36128, 36161, this.glDepthStencil);
      this.pg.popFramebuffer();
      return;
      this.pgl.renderbufferStorage(36161, 35056, this.width, this.height);
    }
  }
  
  protected void createPixelBuffer()
  {
    this.pixelBuffer = IntBuffer.allocate(this.width * this.height);
    this.pixelBuffer.rewind();
  }
  
  protected void createStencilBuffer()
  {
    if (this.screenFb) {
      return;
    }
    if ((this.width == 0) || (this.height == 0)) {
      throw new RuntimeException("PFramebuffer: size undefined.");
    }
    this.pg.pushFramebuffer();
    this.pg.setFramebuffer(this);
    this.glStencil = this.pg.createRenderBufferObject(this.context);
    this.pgl.bindRenderbuffer(36161, this.glStencil);
    int i = 36166;
    if (this.stencilBits == 1)
    {
      i = 36166;
      if (!this.multisample) {
        break label172;
      }
      this.pgl.renderbufferStorageMultisample(36161, this.nsamples, i, this.width, this.height);
    }
    for (;;)
    {
      this.pgl.framebufferRenderbuffer(36160, 36128, 36161, this.glStencil);
      this.pg.popFramebuffer();
      return;
      if (this.stencilBits == 4)
      {
        i = 36167;
        break;
      }
      if (this.stencilBits != 8) {
        break;
      }
      i = 36168;
      break;
      label172:
      this.pgl.renderbufferStorage(36161, i, this.width, this.height);
    }
  }
  
  public void disableDepthTest()
  {
    this.noDepth = true;
  }
  
  protected void finalize()
    throws Throwable
  {
    try
    {
      if (!this.screenFb)
      {
        if (this.glFbo != 0) {
          this.pg.finalizeFrameBufferObject(this.glFbo, this.context);
        }
        if (this.glDepth != 0) {
          this.pg.finalizeRenderBufferObject(this.glDepth, this.context);
        }
        if (this.glStencil != 0) {
          this.pg.finalizeRenderBufferObject(this.glStencil, this.context);
        }
        if (this.glMultisample != 0) {
          this.pg.finalizeRenderBufferObject(this.glMultisample, this.context);
        }
        if (this.glDepthStencil != 0) {
          this.pg.finalizeRenderBufferObject(this.glDepthStencil, this.context);
        }
      }
      return;
    }
    finally
    {
      super.finalize();
    }
  }
  
  public void finish()
  {
    if (this.noDepth)
    {
      if (this.pg.getHint(-2)) {
        this.pgl.enable(2929);
      }
    }
    else {
      return;
    }
    this.pgl.disable(2929);
  }
  
  public int getDefaultDrawBuffer()
  {
    if (this.screenFb) {
      return this.pgl.getDefaultDrawBuffer();
    }
    return 36064;
  }
  
  public int getDefaultReadBuffer()
  {
    if (this.screenFb) {
      return this.pgl.getDefaultReadBuffer();
    }
    return 36064;
  }
  
  public IntBuffer getPixelBuffer()
  {
    return this.pixelBuffer;
  }
  
  public void getPixels(int[] paramArrayOfInt)
  {
    if (this.pixelBuffer != null)
    {
      this.pixelBuffer.get(paramArrayOfInt, 0, paramArrayOfInt.length);
      this.pixelBuffer.rewind();
    }
  }
  
  public boolean hasDepthBuffer()
  {
    return this.depthBits > 0;
  }
  
  public boolean hasStencilBuffer()
  {
    return this.stencilBits > 0;
  }
  
  public void readPixels()
  {
    if (this.pixelBuffer == null) {
      createPixelBuffer();
    }
    this.pixelBuffer.rewind();
    this.pgl.readPixels(0, 0, this.width, this.height, 6408, 5121, this.pixelBuffer);
  }
  
  protected void release()
  {
    if (this.screenFb) {}
    do
    {
      return;
      if (this.glFbo != 0)
      {
        this.pg.finalizeFrameBufferObject(this.glFbo, this.context);
        this.glFbo = 0;
      }
      if (this.glDepth != 0)
      {
        this.pg.finalizeRenderBufferObject(this.glDepth, this.context);
        this.glDepth = 0;
      }
      if (this.glStencil != 0)
      {
        this.pg.finalizeRenderBufferObject(this.glStencil, this.context);
        this.glStencil = 0;
      }
      if (this.glMultisample != 0)
      {
        this.pg.finalizeRenderBufferObject(this.glMultisample, this.context);
        this.glMultisample = 0;
      }
    } while (this.glDepthStencil == 0);
    this.pg.finalizeRenderBufferObject(this.glDepthStencil, this.context);
    this.glDepthStencil = 0;
  }
  
  public void setColorBuffer(Texture paramTexture)
  {
    setColorBuffers(new Texture[] { paramTexture }, 1);
  }
  
  public void setColorBuffers(Texture[] paramArrayOfTexture)
  {
    setColorBuffers(paramArrayOfTexture, paramArrayOfTexture.length);
  }
  
  public void setColorBuffers(Texture[] paramArrayOfTexture, int paramInt)
  {
    if (this.screenFb) {
      return;
    }
    if (this.numColorBuffers != PApplet.min(paramInt, paramArrayOfTexture.length)) {
      throw new RuntimeException("Wrong number of textures to set the color buffers.");
    }
    int i = 0;
    int j;
    if (i >= this.numColorBuffers)
    {
      this.pg.pushFramebuffer();
      this.pg.setFramebuffer(this);
      j = 0;
      label60:
      if (j < this.numColorBuffers) {
        break label112;
      }
    }
    for (int k = 0;; k++)
    {
      if (k >= this.numColorBuffers)
      {
        this.pgl.validateFramebuffer();
        this.pg.popFramebuffer();
        return;
        this.colorBufferTex[i] = paramArrayOfTexture[i];
        i++;
        break;
        label112:
        this.pgl.framebufferTexture2D(36160, 36064 + j, 3553, 0, 0);
        j++;
        break label60;
      }
      this.pgl.framebufferTexture2D(36160, 36064 + k, this.colorBufferTex[k].glTarget, this.colorBufferTex[k].glName, 0);
    }
  }
  
  public void setFBO(int paramInt)
  {
    if (this.screenFb) {
      this.glFbo = paramInt;
    }
  }
  
  public void swapColorBuffers()
  {
    int i = 0;
    if (i >= -1 + this.numColorBuffers)
    {
      this.pg.pushFramebuffer();
      this.pg.setFramebuffer(this);
    }
    for (int k = 0;; k++)
    {
      if (k >= this.numColorBuffers)
      {
        this.pgl.validateFramebuffer();
        this.pg.popFramebuffer();
        return;
        int j = i + 1;
        Texture localTexture = this.colorBufferTex[i];
        this.colorBufferTex[i] = this.colorBufferTex[j];
        this.colorBufferTex[j] = localTexture;
        i++;
        break;
      }
      this.pgl.framebufferTexture2D(36160, 36064 + k, this.colorBufferTex[k].glTarget, this.colorBufferTex[k].glName, 0);
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.FrameBuffer
 * JD-Core Version:    0.7.0.1
 */