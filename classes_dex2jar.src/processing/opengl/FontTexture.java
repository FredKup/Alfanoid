package processing.opengl;

import java.util.Arrays;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PFont.Glyph;
import processing.core.PImage;

class FontTexture
  implements PConstants
{
  protected int currentTex;
  protected PFont font;
  protected TextureInfo[] glyphTexinfos;
  protected PImage[] images = null;
  protected boolean is3D;
  protected int lastTex;
  protected int lineHeight;
  protected int maxTexHeight;
  protected int maxTexWidth;
  protected int offsetX;
  protected int offsetY;
  protected PApplet parent;
  protected PGraphicsOpenGL pg;
  protected PGL pgl;
  protected HashMap<PFont.Glyph, TextureInfo> texinfoMap;
  protected Texture[] textures = null;
  
  public FontTexture(PApplet paramPApplet, PFont paramPFont, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.parent = paramPApplet;
    this.font = paramPFont;
    this.pg = ((PGraphicsOpenGL)paramPApplet.g);
    this.pgl = this.pg.pgl;
    this.is3D = paramBoolean;
    initTexture(paramInt1, paramInt2);
  }
  
  public void addAllGlyphsToTexture()
  {
    for (int i = 0;; i++)
    {
      if (i >= this.font.getGlyphCount()) {
        return;
      }
      addToTexture(i, this.font.getGlyph(i));
    }
  }
  
  public boolean addTexture()
  {
    int i = this.maxTexWidth;
    int j;
    boolean bool;
    Texture localTexture;
    if ((-1 < this.currentTex) && (this.textures[this.currentTex].glHeight < this.maxTexHeight))
    {
      j = PApplet.min(2 * this.textures[this.currentTex].glHeight, this.maxTexHeight);
      bool = true;
      if (!this.is3D) {
        break label175;
      }
      localTexture = new Texture(this.parent, i, j, new Texture.Parameters(2, 4, false));
      label88:
      if (this.textures != null) {
        break label203;
      }
      this.textures = new Texture[1];
      this.textures[0] = localTexture;
      this.images = new PImage[1];
      this.images[0] = this.pg.wrapTexture(localTexture);
      this.currentTex = 0;
    }
    for (;;)
    {
      this.lastTex = this.currentTex;
      localTexture.bind();
      return bool;
      j = PApplet.min(PGraphicsOpenGL.maxTextureSize, 256, this.maxTexHeight / 4);
      bool = false;
      break;
      label175:
      localTexture = new Texture(this.parent, i, j, new Texture.Parameters(2, 3, false));
      break label88;
      label203:
      if (bool)
      {
        localTexture.put(this.textures[this.currentTex]);
        this.textures[this.currentTex] = localTexture;
        this.pg.setCache(this.images[this.currentTex], localTexture);
        this.images[this.currentTex].width = localTexture.width;
        this.images[this.currentTex].height = localTexture.height;
      }
      else
      {
        Texture[] arrayOfTexture = this.textures;
        this.textures = new Texture[1 + this.textures.length];
        PApplet.arrayCopy(arrayOfTexture, this.textures, arrayOfTexture.length);
        this.textures[arrayOfTexture.length] = localTexture;
        this.currentTex = (-1 + this.textures.length);
        PImage[] arrayOfPImage = this.images;
        this.images = new PImage[this.textures.length];
        PApplet.arrayCopy(arrayOfPImage, this.images, arrayOfPImage.length);
        this.images[arrayOfPImage.length] = this.pg.wrapTexture(localTexture);
      }
    }
  }
  
  public TextureInfo addToTexture(PFont.Glyph paramGlyph)
  {
    int i = this.glyphTexinfos.length;
    if (i == 0) {
      this.glyphTexinfos = new TextureInfo[1];
    }
    addToTexture(i, paramGlyph);
    return this.glyphTexinfos[i];
  }
  
  protected void addToTexture(int paramInt, PFont.Glyph paramGlyph)
  {
    int i = 1 + (1 + paramGlyph.width);
    int j = 1 + (1 + paramGlyph.height);
    int[] arrayOfInt1 = new int[i * j];
    int k = 0;
    int i8;
    int i9;
    if (PGL.BIG_ENDIAN)
    {
      Arrays.fill(arrayOfInt1, 0, i, -256);
      i8 = i;
      i9 = 0;
      int i10 = paramGlyph.height;
      if (i9 >= i10)
      {
        Arrays.fill(arrayOfInt1, i * (j - 1), j * i, -256);
        label83:
        if (i + this.offsetX > this.textures[this.currentTex].glWidth)
        {
          this.offsetX = 0;
          this.offsetY += this.lineHeight;
          this.lineHeight = 0;
        }
        this.lineHeight = Math.max(this.lineHeight, j);
        if (this.offsetY + this.lineHeight > this.textures[this.currentTex].glHeight)
        {
          if (!addTexture()) {
            break label529;
          }
          updateGlyphsTexCoords();
        }
      }
    }
    for (;;)
    {
      TextureInfo localTextureInfo = new TextureInfo(this.currentTex, this.offsetX, this.offsetY, i, j, arrayOfInt1);
      this.offsetX = (i + this.offsetX);
      if (paramInt == this.glyphTexinfos.length)
      {
        TextureInfo[] arrayOfTextureInfo = new TextureInfo[1 + this.glyphTexinfos.length];
        System.arraycopy(this.glyphTexinfos, 0, arrayOfTextureInfo, 0, this.glyphTexinfos.length);
        this.glyphTexinfos = arrayOfTextureInfo;
      }
      this.glyphTexinfos[paramInt] = localTextureInfo;
      this.texinfoMap.put(paramGlyph, localTextureInfo);
      return;
      int i11 = i8 + 1;
      arrayOfInt1[i8] = -256;
      int i12 = 0;
      int i14;
      for (int i13 = i11;; i13 = i14)
      {
        if (i12 >= paramGlyph.width)
        {
          int i16 = i13 + 1;
          arrayOfInt1[i13] = -256;
          i9++;
          i8 = i16;
          break;
        }
        i14 = i13 + 1;
        int[] arrayOfInt3 = paramGlyph.image.pixels;
        int i15 = k + 1;
        arrayOfInt1[i13] = (0xFFFFFF00 | arrayOfInt3[k]);
        i12++;
        k = i15;
      }
      Arrays.fill(arrayOfInt1, 0, i, 16777215);
      int m = i;
      int n = 0;
      int i1 = paramGlyph.height;
      if (n >= i1)
      {
        Arrays.fill(arrayOfInt1, i * (j - 1), j * i, 16777215);
        break label83;
      }
      int i2 = m + 1;
      arrayOfInt1[m] = 16777215;
      int i3 = 0;
      int i5;
      for (int i4 = i2;; i4 = i5)
      {
        if (i3 >= paramGlyph.width)
        {
          int i7 = i4 + 1;
          arrayOfInt1[i4] = 16777215;
          n++;
          m = i7;
          break;
        }
        i5 = i4 + 1;
        int[] arrayOfInt2 = paramGlyph.image.pixels;
        int i6 = k + 1;
        arrayOfInt1[i4] = (0xFFFFFF | arrayOfInt2[k] << 24);
        i3++;
        k = i6;
      }
      label529:
      this.offsetX = 0;
      this.offsetY = 0;
      this.lineHeight = 0;
    }
  }
  
  protected void allocate() {}
  
  public void begin()
  {
    setTexture(0);
  }
  
  public boolean contextIsOutdated()
  {
    boolean bool = false;
    int i = 0;
    if (i >= this.textures.length) {
      if (!bool) {}
    }
    for (int j = 0;; j++)
    {
      if (j >= this.textures.length)
      {
        return bool;
        if (this.textures[i].contextIsOutdated()) {
          bool = true;
        }
        i++;
        break;
      }
      this.pg.removeTextureObject(this.textures[j].glName, this.textures[j].context);
      this.textures[j].glName = 0;
    }
  }
  
  public void end()
  {
    for (int i = 0;; i++)
    {
      if (i >= this.textures.length) {
        return;
      }
      this.pgl.disableTexturing(this.textures[i].glTarget);
    }
  }
  
  public PImage getCurrentTexture()
  {
    return getTexture(this.currentTex);
  }
  
  public TextureInfo getTexInfo(PFont.Glyph paramGlyph)
  {
    return (TextureInfo)this.texinfoMap.get(paramGlyph);
  }
  
  public PImage getTexture(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.images.length)) {
      return this.images[paramInt];
    }
    return null;
  }
  
  protected void initTexture(int paramInt1, int paramInt2)
  {
    this.maxTexWidth = paramInt1;
    this.maxTexHeight = paramInt2;
    this.currentTex = -1;
    this.lastTex = -1;
    addTexture();
    this.offsetX = 0;
    this.offsetY = 0;
    this.lineHeight = 0;
    this.texinfoMap = new HashMap();
    this.glyphTexinfos = new TextureInfo[this.font.getGlyphCount()];
    addAllGlyphsToTexture();
  }
  
  public void setTexture(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.textures.length)) {
      this.currentTex = paramInt;
    }
  }
  
  public void updateGlyphsTexCoords()
  {
    for (int i = 0;; i++)
    {
      if (i >= this.glyphTexinfos.length) {
        return;
      }
      TextureInfo localTextureInfo = this.glyphTexinfos[i];
      if ((localTextureInfo != null) && (localTextureInfo.texIndex == this.currentTex)) {
        localTextureInfo.updateUV();
      }
    }
  }
  
  class TextureInfo
  {
    int[] crop;
    int height;
    int[] pixels;
    int texIndex;
    float u0;
    float u1;
    float v0;
    float v1;
    int width;
    
    TextureInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt)
    {
      this.texIndex = paramInt1;
      this.crop = new int[4];
      this.crop[0] = (paramInt2 + 1);
      this.crop[1] = (-2 + (paramInt5 + (paramInt3 + 1)));
      this.crop[2] = (paramInt4 - 2);
      this.crop[3] = (2 + -paramInt5);
      this.pixels = paramArrayOfInt;
      updateUV();
      updateTex();
    }
    
    void updateTex()
    {
      FontTexture.this.textures[this.texIndex].setNative(this.pixels, -1 + this.crop[0], -1 + (this.crop[1] + this.crop[3]), 2 + this.crop[2], 2 + -this.crop[3]);
    }
    
    void updateUV()
    {
      this.width = FontTexture.this.textures[this.texIndex].glWidth;
      this.height = FontTexture.this.textures[this.texIndex].glHeight;
      this.u0 = (this.crop[0] / this.width);
      this.u1 = (this.u0 + this.crop[2] / this.width);
      this.v0 = ((this.crop[1] + this.crop[3]) / this.height);
      this.v1 = (this.v0 - this.crop[3] / this.height);
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.FontTexture
 * JD-Core Version:    0.7.0.1
 */