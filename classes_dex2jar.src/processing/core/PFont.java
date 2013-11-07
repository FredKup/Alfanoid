package processing.core;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class PFont
  implements PConstants
{
  public static char[] CHARSET;
  static final char[] EXTRA_CHARS = { 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 180, 181, 182, 183, 184, 186, 187, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 255, 258, 259, 260, 261, 262, 263, 268, 269, 270, 271, 272, 273, 280, 281, 282, 283, 305, 313, 314, 317, 318, 321, 322, 323, 324, 327, 328, 336, 337, 338, 339, 340, 341, 344, 345, 346, 347, 350, 351, 352, 353, 354, 355, 356, 357, 366, 367, 368, 369, 376, 377, 378, 379, 380, 381, 382, 402, 710, 711, 728, 729, 730, 731, 732, 733, 937, 960, 8211, 8212, 8216, 8217, 8218, 8220, 8221, 8222, 8224, 8225, 8226, 8230, 8240, 8249, 8250, 8260, 8364, 8482, 8706, 8710, 8719, 8721, 8730, 8734, 8747, 8776, 8800, 8804, 8805, 9674, -1793, -1279, -1278 };
  static String[] fontList;
  static HashMap<String, Typeface> typefaceMap;
  protected static Typeface[] typefaces;
  protected int ascent;
  protected int[] ascii;
  protected HashMap<PGraphics, Object> cacheMap;
  protected int descent;
  protected int glyphCount;
  protected Glyph[] glyphs;
  protected boolean lazy;
  Bitmap lazyBitmap;
  Canvas lazyCanvas;
  Paint lazyPaint;
  int[] lazySamples;
  protected String name;
  protected String psname;
  protected int size;
  protected boolean smooth;
  protected boolean subsetting;
  protected Typeface typeface;
  protected boolean typefaceSearched;
  
  static
  {
    CHARSET = new char[94 + EXTRA_CHARS.length];
    int i = 33;
    int j = 0;
    int m;
    if (i > 126) {
      m = 0;
    }
    int i1;
    for (int n = j;; n = i1)
    {
      if (m >= EXTRA_CHARS.length)
      {
        return;
        char[] arrayOfChar1 = CHARSET;
        int k = j + 1;
        arrayOfChar1[j] = ((char)i);
        i++;
        j = k;
        break;
      }
      char[] arrayOfChar2 = CHARSET;
      i1 = n + 1;
      arrayOfChar2[n] = EXTRA_CHARS[m];
      m++;
    }
  }
  
  public PFont() {}
  
  public PFont(Typeface paramTypeface, int paramInt, boolean paramBoolean)
  {
    this(paramTypeface, paramInt, paramBoolean, null);
  }
  
  public PFont(Typeface paramTypeface, int paramInt, boolean paramBoolean, char[] paramArrayOfChar)
  {
    this.typeface = paramTypeface;
    this.smooth = paramBoolean;
    this.name = "";
    this.psname = "";
    this.size = paramInt;
    this.glyphs = new Glyph[10];
    this.ascii = new int[''];
    Arrays.fill(this.ascii, -1);
    int j = paramInt * 3;
    this.lazyBitmap = Bitmap.createBitmap(j, j, Bitmap.Config.ARGB_8888);
    this.lazyCanvas = new Canvas(this.lazyBitmap);
    this.lazyPaint = new Paint();
    this.lazyPaint.setAntiAlias(paramBoolean);
    this.lazyPaint.setTypeface(paramTypeface);
    this.lazyPaint.setTextSize(paramInt);
    this.lazySamples = new int[j * j];
    if (paramArrayOfChar == null) {
      this.lazy = true;
    }
    for (;;)
    {
      if (this.ascent == 0)
      {
        new Glyph('d');
        if (this.ascent == 0) {
          this.ascent = PApplet.round(this.lazyPaint.ascent());
        }
      }
      if (this.descent == 0)
      {
        new Glyph('p');
        if (this.descent == 0) {
          this.descent = PApplet.round(this.lazyPaint.descent());
        }
      }
      return;
      Arrays.sort(paramArrayOfChar);
      this.glyphs = new Glyph[paramArrayOfChar.length];
      this.glyphCount = 0;
      int k = paramArrayOfChar.length;
      while (i < k)
      {
        Glyph localGlyph = new Glyph(paramArrayOfChar[i]);
        if (localGlyph.value < 128) {
          this.ascii[localGlyph.value] = this.glyphCount;
        }
        localGlyph.index = this.glyphCount;
        Glyph[] arrayOfGlyph = this.glyphs;
        int m = this.glyphCount;
        this.glyphCount = (m + 1);
        arrayOfGlyph[m] = localGlyph;
        i++;
      }
    }
  }
  
  public PFont(InputStream paramInputStream)
    throws IOException
  {
    DataInputStream localDataInputStream = new DataInputStream(paramInputStream);
    this.glyphCount = localDataInputStream.readInt();
    int i = localDataInputStream.readInt();
    this.size = localDataInputStream.readInt();
    localDataInputStream.readInt();
    this.ascent = localDataInputStream.readInt();
    this.descent = localDataInputStream.readInt();
    this.glyphs = new Glyph[this.glyphCount];
    this.ascii = new int[''];
    Arrays.fill(this.ascii, -1);
    for (int j = 0;; j++)
    {
      if (j >= this.glyphCount)
      {
        if ((this.ascent != 0) || (this.descent != 0)) {
          break;
        }
        throw new RuntimeException("Please use \"Create Font\" to re-create this font.");
      }
      Glyph localGlyph = new Glyph(localDataInputStream);
      if (localGlyph.value < 128) {
        this.ascii[localGlyph.value] = j;
      }
      localGlyph.index = j;
      this.glyphs[j] = localGlyph;
    }
    Glyph[] arrayOfGlyph = this.glyphs;
    int k = arrayOfGlyph.length;
    for (int m = 0;; m++)
    {
      if (m >= k)
      {
        if (i >= 10)
        {
          this.name = localDataInputStream.readUTF();
          this.psname = localDataInputStream.readUTF();
        }
        if (i == 11) {
          this.smooth = localDataInputStream.readBoolean();
        }
        return;
      }
      arrayOfGlyph[m].readBitmap(localDataInputStream);
    }
  }
  
  public static Object findNative(String paramString)
  {
    loadTypefaces();
    return typefaceMap.get(paramString);
  }
  
  public static String[] list()
  {
    loadTypefaces();
    return fontList;
  }
  
  public static void loadTypefaces()
  {
    if (typefaceMap == null)
    {
      typefaceMap = new HashMap();
      typefaceMap.put("Serif", Typeface.create(Typeface.SERIF, 0));
      typefaceMap.put("Serif-Bold", Typeface.create(Typeface.SERIF, 1));
      typefaceMap.put("Serif-Italic", Typeface.create(Typeface.SERIF, 2));
      typefaceMap.put("Serif-BoldItalic", Typeface.create(Typeface.SERIF, 3));
      typefaceMap.put("SansSerif", Typeface.create(Typeface.SANS_SERIF, 0));
      typefaceMap.put("SansSerif-Bold", Typeface.create(Typeface.SANS_SERIF, 1));
      typefaceMap.put("SansSerif-Italic", Typeface.create(Typeface.SANS_SERIF, 2));
      typefaceMap.put("SansSerif-BoldItalic", Typeface.create(Typeface.SANS_SERIF, 3));
      typefaceMap.put("Monospaced", Typeface.create(Typeface.MONOSPACE, 0));
      typefaceMap.put("Monospaced-Bold", Typeface.create(Typeface.MONOSPACE, 1));
      typefaceMap.put("Monospaced-Italic", Typeface.create(Typeface.MONOSPACE, 2));
      typefaceMap.put("Monospaced-BoldItalic", Typeface.create(Typeface.MONOSPACE, 3));
      fontList = new String[typefaceMap.size()];
      typefaceMap.keySet().toArray(fontList);
    }
  }
  
  protected void addGlyph(char paramChar)
  {
    Glyph localGlyph = new Glyph(paramChar);
    if (this.glyphCount == this.glyphs.length) {
      this.glyphs = ((Glyph[])PApplet.expand(this.glyphs));
    }
    if (this.glyphCount == 0)
    {
      localGlyph.index = 0;
      this.glyphs[this.glyphCount] = localGlyph;
      if (localGlyph.value < 128) {
        this.ascii[localGlyph.value] = 0;
      }
    }
    label269:
    for (;;)
    {
      this.glyphCount = (1 + this.glyphCount);
      return;
      if (this.glyphs[(-1 + this.glyphCount)].value < localGlyph.value)
      {
        this.glyphs[this.glyphCount] = localGlyph;
        if (localGlyph.value < 128) {
          this.ascii[localGlyph.value] = this.glyphCount;
        }
      }
      else
      {
        for (int i = 0;; i++)
        {
          if (i >= this.glyphCount) {
            break label269;
          }
          if (this.glyphs[i].value > paramChar) {
            for (int j = this.glyphCount;; j--)
            {
              if (j <= i)
              {
                localGlyph.index = i;
                this.glyphs[i] = localGlyph;
                if (paramChar >= '') {
                  break;
                }
                this.ascii[paramChar] = i;
                break;
              }
              this.glyphs[j] = this.glyphs[(j - 1)];
              if (this.glyphs[j].value < 128) {
                this.ascii[this.glyphs[j].value] = j;
              }
            }
          }
        }
      }
    }
  }
  
  public float ascent()
  {
    return this.ascent / this.size;
  }
  
  public float descent()
  {
    return this.descent / this.size;
  }
  
  public Object getCache(PGraphics paramPGraphics)
  {
    if (this.cacheMap == null) {
      return null;
    }
    return this.cacheMap.get(paramPGraphics);
  }
  
  public Glyph getGlyph(char paramChar)
  {
    int i = index(paramChar);
    if (i == -1) {
      return null;
    }
    return this.glyphs[i];
  }
  
  public Glyph getGlyph(int paramInt)
  {
    return this.glyphs[paramInt];
  }
  
  public int getGlyphCount()
  {
    return this.glyphCount;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public Object getNative()
  {
    if (this.subsetting) {
      return null;
    }
    return this.typeface;
  }
  
  public String getPostScriptName()
  {
    return this.psname;
  }
  
  public int getSize()
  {
    return this.size;
  }
  
  protected int index(char paramChar)
  {
    if (this.lazy)
    {
      int i = indexActual(paramChar);
      if (i != -1) {
        return i;
      }
      addGlyph(paramChar);
      return indexActual(paramChar);
    }
    return indexActual(paramChar);
  }
  
  protected int indexActual(char paramChar)
  {
    if (this.glyphCount == 0) {
      return -1;
    }
    if (paramChar < '') {
      return this.ascii[paramChar];
    }
    return indexHunt(paramChar, 0, -1 + this.glyphCount);
  }
  
  protected int indexHunt(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = (paramInt2 + paramInt3) / 2;
    if (paramInt1 == this.glyphs[i].value) {
      return i;
    }
    if (paramInt2 >= paramInt3) {
      return -1;
    }
    if (paramInt1 < this.glyphs[i].value) {
      return indexHunt(paramInt1, paramInt2, i - 1);
    }
    return indexHunt(paramInt1, i + 1, paramInt3);
  }
  
  public float kern(char paramChar1, char paramChar2)
  {
    return 0.0F;
  }
  
  public void removeCache(PGraphics paramPGraphics)
  {
    if (this.cacheMap != null) {
      this.cacheMap.remove(paramPGraphics);
    }
  }
  
  public void save(OutputStream paramOutputStream)
    throws IOException
  {
    DataOutputStream localDataOutputStream = new DataOutputStream(paramOutputStream);
    localDataOutputStream.writeInt(this.glyphCount);
    if ((this.name == null) || (this.psname == null))
    {
      this.name = "";
      this.psname = "";
    }
    localDataOutputStream.writeInt(11);
    localDataOutputStream.writeInt(this.size);
    localDataOutputStream.writeInt(0);
    localDataOutputStream.writeInt(this.ascent);
    localDataOutputStream.writeInt(this.descent);
    int i = 0;
    if (i >= this.glyphCount) {}
    for (int j = 0;; j++)
    {
      if (j >= this.glyphCount)
      {
        localDataOutputStream.writeUTF(this.name);
        localDataOutputStream.writeUTF(this.psname);
        localDataOutputStream.writeBoolean(this.smooth);
        localDataOutputStream.flush();
        return;
        this.glyphs[i].writeHeader(localDataOutputStream);
        i++;
        break;
      }
      this.glyphs[j].writeBitmap(localDataOutputStream);
    }
  }
  
  public void setCache(PGraphics paramPGraphics, Object paramObject)
  {
    if (this.cacheMap == null) {
      this.cacheMap = new HashMap();
    }
    this.cacheMap.put(paramPGraphics, paramObject);
  }
  
  public void setNative(Object paramObject)
  {
    this.typeface = ((Typeface)paramObject);
  }
  
  public void setSubsetting()
  {
    this.subsetting = true;
  }
  
  public float width(char paramChar)
  {
    if (paramChar == ' ') {
      return width('i');
    }
    int i = index(paramChar);
    if (i == -1) {
      return 0.0F;
    }
    return this.glyphs[i].setWidth / this.size;
  }
  
  public class Glyph
  {
    public boolean fromStream = false;
    public int height;
    public PImage image;
    public int index;
    public int leftExtent;
    public int setWidth;
    public int topExtent;
    public int value;
    public int width;
    
    protected Glyph() {}
    
    protected Glyph(char paramChar)
    {
      int i = 3 * PFont.this.size;
      PFont.this.lazyCanvas.drawColor(-1);
      PFont.this.lazyPaint.setColor(-16777216);
      PFont.this.lazyCanvas.drawText(String.valueOf(paramChar), PFont.this.size, 2 * PFont.this.size, PFont.this.lazyPaint);
      PFont.this.lazyBitmap.getPixels(PFont.this.lazySamples, 0, i, 0, 0, i, i);
      int j = 1000;
      int k = 0;
      int m = 1000;
      int n = 0;
      int i1 = 0;
      int i2 = 0;
      int[] arrayOfInt;
      int i4;
      if (i2 >= i)
      {
        if (i1 == 0)
        {
          m = 0;
          j = 0;
          n = 0;
          k = 0;
        }
        this.value = paramChar;
        this.height = (1 + (n - m));
        this.width = (1 + (k - j));
        this.setWidth = ((int)PFont.this.lazyPaint.measureText(String.valueOf(paramChar)));
        this.topExtent = (2 * PFont.this.size - m);
        this.leftExtent = (j - PFont.this.size);
        this.image = new PImage(this.width, this.height, 4);
        arrayOfInt = this.image.pixels;
        i4 = m;
        if (i4 > n)
        {
          if ((this.value == 100) && (PFont.this.ascent == 0)) {
            PFont.this.ascent = this.topExtent;
          }
          if ((this.value == 112) && (PFont.this.descent == 0)) {
            PFont.this.descent = (-this.topExtent + this.height);
          }
        }
      }
      else
      {
        for (int i3 = 0;; i3++)
        {
          if (i3 >= i)
          {
            i2++;
            break;
          }
          if ((0xFF & PFont.this.lazySamples[(i3 + i2 * i)]) != 255)
          {
            if (i3 < j) {
              j = i3;
            }
            if (i2 < m) {
              m = i2;
            }
            if (i3 > k) {
              k = i3;
            }
            if (i2 > n) {
              n = i2;
            }
            i1 = 1;
          }
        }
      }
      for (int i5 = j;; i5++)
      {
        if (i5 > k)
        {
          i4++;
          break;
        }
        int i6 = 255 - (0xFF & PFont.this.lazySamples[(i5 + i4 * i)]);
        arrayOfInt[((i4 - m) * this.width + (i5 - j))] = i6;
      }
    }
    
    protected Glyph(DataInputStream paramDataInputStream)
      throws IOException
    {
      readHeader(paramDataInputStream);
    }
    
    protected void readBitmap(DataInputStream paramDataInputStream)
      throws IOException
    {
      this.image = new PImage(this.width, this.height, 4);
      byte[] arrayOfByte = new byte[this.width * this.height];
      paramDataInputStream.readFully(arrayOfByte);
      int i = this.width;
      int j = this.height;
      int[] arrayOfInt = this.image.pixels;
      int k = 0;
      if (k >= j)
      {
        this.fromStream = true;
        return;
      }
      for (int m = 0;; m++)
      {
        if (m >= i)
        {
          k++;
          break;
        }
        arrayOfInt[(m + k * this.width)] = (0xFF & arrayOfByte[(m + k * i)]);
      }
    }
    
    protected void readHeader(DataInputStream paramDataInputStream)
      throws IOException
    {
      this.value = paramDataInputStream.readInt();
      this.height = paramDataInputStream.readInt();
      this.width = paramDataInputStream.readInt();
      this.setWidth = paramDataInputStream.readInt();
      this.topExtent = paramDataInputStream.readInt();
      this.leftExtent = paramDataInputStream.readInt();
      paramDataInputStream.readInt();
      if ((this.value == 100) && (PFont.this.ascent == 0)) {
        PFont.this.ascent = this.topExtent;
      }
      if ((this.value == 112) && (PFont.this.descent == 0)) {
        PFont.this.descent = (-this.topExtent + this.height);
      }
    }
    
    protected void writeBitmap(DataOutputStream paramDataOutputStream)
      throws IOException
    {
      int[] arrayOfInt = this.image.pixels;
      int i = 0;
      if (i >= this.height) {
        return;
      }
      for (int j = 0;; j++)
      {
        if (j >= this.width)
        {
          i++;
          break;
        }
        paramDataOutputStream.write(0xFF & arrayOfInt[(j + i * this.width)]);
      }
    }
    
    protected void writeHeader(DataOutputStream paramDataOutputStream)
      throws IOException
    {
      paramDataOutputStream.writeInt(this.value);
      paramDataOutputStream.writeInt(this.height);
      paramDataOutputStream.writeInt(this.width);
      paramDataOutputStream.writeInt(this.setWidth);
      paramDataOutputStream.writeInt(this.topExtent);
      paramDataOutputStream.writeInt(this.leftExtent);
      paramDataOutputStream.writeInt(0);
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PFont
 * JD-Core Version:    0.7.0.1
 */