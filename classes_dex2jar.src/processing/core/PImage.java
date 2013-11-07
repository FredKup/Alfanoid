package processing.core;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.HashMap;

public class PImage
  implements PConstants, Cloneable
{
  public static final int ALPHA_MASK = -16777216;
  public static final int BLUE_MASK = 255;
  public static final int GREEN_MASK = 65280;
  static final int PRECISIONB = 15;
  static final int PRECISIONF = 32768;
  static final int PREC_ALPHA_SHIFT = 9;
  static final int PREC_MAXVAL = 32767;
  static final int PREC_RED_SHIFT = 1;
  public static final int RED_MASK = 16711680;
  static final String TIFF_ERROR = "Error: Processing can only read its own TIFF files.";
  static byte[] TIFF_HEADER;
  private int a;
  private int b;
  protected Bitmap bitmap;
  private int[] blurKernel;
  private int blurKernelSize;
  private int[][] blurMult;
  private int blurRadius;
  private int cLL;
  private int cLR;
  private int cUL;
  private int cUR;
  protected HashMap<PGraphics, Object> cacheMap;
  public int format;
  private int fracU;
  private int fracV;
  private int g;
  public int height;
  private int ifU;
  private int ifV;
  private int ih1;
  private int iw;
  private int iw1;
  private int ll;
  private int lr;
  protected boolean modified;
  protected int mx1;
  protected int mx2;
  protected int my1;
  protected int my2;
  protected HashMap<PGraphics, Object> paramMap;
  public PApplet parent;
  public int[] pixels;
  private int r;
  private int sX;
  private int sY;
  protected String[] saveImageFormats;
  private int[] srcBuffer;
  private int srcXOffset;
  private int srcYOffset;
  private int u1;
  private int u2;
  private int ul;
  private int ur;
  private int v1;
  private int v2;
  public int width;
  
  static
  {
    byte[] arrayOfByte = new byte[''];
    arrayOfByte[0] = 77;
    arrayOfByte[1] = 77;
    arrayOfByte[3] = 42;
    arrayOfByte[7] = 8;
    arrayOfByte[9] = 9;
    arrayOfByte[11] = -2;
    arrayOfByte[13] = 4;
    arrayOfByte[17] = 1;
    arrayOfByte[22] = 1;
    arrayOfByte[25] = 3;
    arrayOfByte[29] = 1;
    arrayOfByte[34] = 1;
    arrayOfByte[35] = 1;
    arrayOfByte[37] = 3;
    arrayOfByte[41] = 1;
    arrayOfByte[46] = 1;
    arrayOfByte[47] = 2;
    arrayOfByte[49] = 3;
    arrayOfByte[53] = 3;
    arrayOfByte[57] = 122;
    arrayOfByte[58] = 1;
    arrayOfByte[59] = 6;
    arrayOfByte[61] = 3;
    arrayOfByte[65] = 1;
    arrayOfByte[67] = 2;
    arrayOfByte[70] = 1;
    arrayOfByte[71] = 17;
    arrayOfByte[73] = 4;
    arrayOfByte[77] = 1;
    arrayOfByte[80] = 3;
    arrayOfByte[82] = 1;
    arrayOfByte[83] = 21;
    arrayOfByte[85] = 3;
    arrayOfByte[89] = 1;
    arrayOfByte[91] = 3;
    arrayOfByte[94] = 1;
    arrayOfByte[95] = 22;
    arrayOfByte[97] = 3;
    arrayOfByte[101] = 1;
    arrayOfByte[106] = 1;
    arrayOfByte[107] = 23;
    arrayOfByte[109] = 4;
    arrayOfByte[113] = 1;
    arrayOfByte[123] = 8;
    arrayOfByte[125] = 8;
    arrayOfByte[127] = 8;
    TIFF_HEADER = arrayOfByte;
  }
  
  public PImage()
  {
    this.format = 2;
  }
  
  public PImage(int paramInt1, int paramInt2)
  {
    init(paramInt1, paramInt2, 1);
  }
  
  public PImage(int paramInt1, int paramInt2, int paramInt3)
  {
    init(paramInt1, paramInt2, paramInt3);
  }
  
  public PImage(Object paramObject)
  {
    Bitmap localBitmap = (Bitmap)paramObject;
    this.bitmap = localBitmap;
    this.width = localBitmap.getWidth();
    this.height = localBitmap.getHeight();
    this.pixels = null;
    if (localBitmap.hasAlpha()) {}
    for (int i = 2;; i = 1)
    {
      this.format = i;
      return;
    }
  }
  
  public static int blendColor(int paramInt1, int paramInt2, int paramInt3)
  {
    switch (paramInt3)
    {
    default: 
      paramInt2 = 0;
    case 0: 
      return paramInt2;
    case 1: 
      return blend_blend(paramInt1, paramInt2);
    case 2: 
      return blend_add_pin(paramInt1, paramInt2);
    case 4: 
      return blend_sub_pin(paramInt1, paramInt2);
    case 8: 
      return blend_lightest(paramInt1, paramInt2);
    case 16: 
      return blend_darkest(paramInt1, paramInt2);
    case 32: 
      return blend_difference(paramInt1, paramInt2);
    case 64: 
      return blend_exclusion(paramInt1, paramInt2);
    case 128: 
      return blend_multiply(paramInt1, paramInt2);
    case 256: 
      return blend_screen(paramInt1, paramInt2);
    case 1024: 
      return blend_hard_light(paramInt1, paramInt2);
    case 2048: 
      return blend_soft_light(paramInt1, paramInt2);
    case 512: 
      return blend_overlay(paramInt1, paramInt2);
    case 4096: 
      return blend_dodge(paramInt1, paramInt2);
    }
    return blend_burn(paramInt1, paramInt2);
  }
  
  private static int blend_add_pin(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF000000) >>> 24;
    return low(i + ((paramInt1 & 0xFF000000) >>> 24), 255) << 24 | 0xFF0000 & low((paramInt1 & 0xFF0000) + i * ((paramInt2 & 0xFF0000) >> 8), 16711680) | 0xFF00 & low((paramInt1 & 0xFF00) + i * ((paramInt2 & 0xFF00) >> 8), 65280) | low((paramInt1 & 0xFF) + (i * (paramInt2 & 0xFF) >> 8), 255);
  }
  
  private static int blend_blend(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF000000) >>> 24;
    return low(i + ((paramInt1 & 0xFF000000) >>> 24), 255) << 24 | 0xFF0000 & mix(paramInt1 & 0xFF0000, paramInt2 & 0xFF0000, i) | 0xFF00 & mix(paramInt1 & 0xFF00, paramInt2 & 0xFF00, i) | mix(paramInt1 & 0xFF, paramInt2 & 0xFF, i);
  }
  
  private static int blend_burn(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF000000) >>> 24;
    int j = (paramInt1 & 0xFF0000) >> 16;
    int k = (paramInt1 & 0xFF00) >> 8;
    int m = paramInt1 & 0xFF;
    int n = (paramInt2 & 0xFF0000) >> 16;
    int i1 = (paramInt2 & 0xFF00) >> 8;
    int i2 = paramInt2 & 0xFF;
    int i3;
    int i4;
    label73:
    int i5;
    if (n == 0)
    {
      i3 = 0;
      if (i1 != 0) {
        break label178;
      }
      i4 = 0;
      i5 = 0;
      if (i2 != 0) {
        break label202;
      }
    }
    for (;;)
    {
      return low(i + ((paramInt1 & 0xFF000000) >>> 24), 255) << 24 | peg(j + (i * (i3 - j) >> 8)) << 16 | peg(k + (i * (i4 - k) >> 8)) << 8 | peg(m + (i * (i5 - m) >> 8));
      i3 = 255 - peg((255 - j << 8) / n);
      break;
      label178:
      i4 = 255 - peg((255 - k << 8) / i1);
      break label73;
      label202:
      i5 = 255 - peg((255 - m << 8) / i2);
    }
  }
  
  private static int blend_darkest(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF000000) >>> 24;
    return low(i + ((paramInt1 & 0xFF000000) >>> 24), 255) << 24 | 0xFF0000 & mix(paramInt1 & 0xFF0000, low(paramInt1 & 0xFF0000, i * ((paramInt2 & 0xFF0000) >> 8)), i) | 0xFF00 & mix(paramInt1 & 0xFF00, low(paramInt1 & 0xFF00, i * ((paramInt2 & 0xFF00) >> 8)), i) | mix(paramInt1 & 0xFF, low(paramInt1 & 0xFF, i * (paramInt2 & 0xFF) >> 8), i);
  }
  
  private static int blend_difference(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF000000) >>> 24;
    int j = (paramInt1 & 0xFF0000) >> 16;
    int k = (paramInt1 & 0xFF00) >> 8;
    int m = paramInt1 & 0xFF;
    int n = (paramInt2 & 0xFF0000) >> 16;
    int i1 = (paramInt2 & 0xFF00) >> 8;
    int i2 = paramInt2 & 0xFF;
    int i3;
    int i4;
    if (j > n)
    {
      i3 = j - n;
      if (k <= i1) {
        break label180;
      }
      i4 = k - i1;
      label83:
      if (m <= i2) {
        break label190;
      }
    }
    label180:
    label190:
    for (int i5 = m - i2;; i5 = i2 - m)
    {
      return low(i + ((paramInt1 & 0xFF000000) >>> 24), 255) << 24 | peg(j + (i * (i3 - j) >> 8)) << 16 | peg(k + (i * (i4 - k) >> 8)) << 8 | peg(m + (i * (i5 - m) >> 8));
      i3 = n - j;
      break;
      i4 = i1 - k;
      break label83;
    }
  }
  
  private static int blend_dodge(int paramInt1, int paramInt2)
  {
    int i = (0xFF000000 & paramInt2) >>> 24;
    int j = (0xFF0000 & paramInt1) >> 16;
    int k = (0xFF00 & paramInt1) >> 8;
    int m = paramInt1 & 0xFF;
    int n = (0xFF0000 & paramInt2) >> 16;
    int i1 = (0xFF00 & paramInt2) >> 8;
    int i2 = paramInt2 & 0xFF;
    int i3;
    int i4;
    if (n == 255)
    {
      i3 = 255;
      if (i1 != 255) {
        break label189;
      }
      i4 = 255;
      label83:
      if (i2 != 255) {
        break label209;
      }
    }
    label189:
    label209:
    for (int i5 = 255;; i5 = peg((m << 8) / (255 - i2)))
    {
      return low(i + ((0xFF000000 & paramInt1) >>> 24), 255) << 24 | peg(j + (i * (i3 - j) >> 8)) << 16 | peg(k + (i * (i4 - k) >> 8)) << 8 | peg(m + (i * (i5 - m) >> 8));
      i3 = peg((j << 8) / (255 - n));
      break;
      i4 = peg((k << 8) / (255 - i1));
      break label83;
    }
  }
  
  private static int blend_exclusion(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF000000) >>> 24;
    int j = (paramInt1 & 0xFF0000) >> 16;
    int k = (paramInt1 & 0xFF00) >> 8;
    int m = paramInt1 & 0xFF;
    int n = (paramInt2 & 0xFF0000) >> 16;
    int i1 = (paramInt2 & 0xFF00) >> 8;
    int i2 = paramInt2 & 0xFF;
    int i3 = j + n - (j * n >> 7);
    int i4 = k + i1 - (k * i1 >> 7);
    int i5 = m + i2 - (m * i2 >> 7);
    return low(i + ((paramInt1 & 0xFF000000) >>> 24), 255) << 24 | peg(j + (i * (i3 - j) >> 8)) << 16 | peg(k + (i * (i4 - k) >> 8)) << 8 | peg(m + (i * (i5 - m) >> 8));
  }
  
  private static int blend_hard_light(int paramInt1, int paramInt2)
  {
    int i = (0xFF000000 & paramInt2) >>> 24;
    int j = (0xFF0000 & paramInt1) >> 16;
    int k = (0xFF00 & paramInt1) >> 8;
    int m = paramInt1 & 0xFF;
    int n = (0xFF0000 & paramInt2) >> 16;
    int i1 = (0xFF00 & paramInt2) >> 8;
    int i2 = paramInt2 & 0xFF;
    int i3;
    int i4;
    if (n < 128)
    {
      i3 = j * n >> 7;
      if (i1 >= 128) {
        break label208;
      }
      i4 = k * i1 >> 7;
      label92:
      if (i2 >= 128) {
        break label233;
      }
    }
    label208:
    label233:
    for (int i5 = m * i2 >> 7;; i5 = 255 - ((255 - m) * (255 - i2) >> 7))
    {
      return low(i + ((0xFF000000 & paramInt1) >>> 24), 255) << 24 | peg(j + (i * (i3 - j) >> 8)) << 16 | peg(k + (i * (i4 - k) >> 8)) << 8 | peg(m + (i * (i5 - m) >> 8));
      i3 = 255 - ((255 - j) * (255 - n) >> 7);
      break;
      i4 = 255 - ((255 - k) * (255 - i1) >> 7);
      break label92;
    }
  }
  
  private static int blend_lightest(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF000000) >>> 24;
    return low(i + ((paramInt1 & 0xFF000000) >>> 24), 255) << 24 | 0xFF0000 & high(paramInt1 & 0xFF0000, i * ((paramInt2 & 0xFF0000) >> 8)) | 0xFF00 & high(paramInt1 & 0xFF00, i * ((paramInt2 & 0xFF00) >> 8)) | high(paramInt1 & 0xFF, i * (paramInt2 & 0xFF) >> 8);
  }
  
  private static int blend_multiply(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF000000) >>> 24;
    int j = (paramInt1 & 0xFF0000) >> 16;
    int k = (paramInt1 & 0xFF00) >> 8;
    int m = paramInt1 & 0xFF;
    int n = (paramInt2 & 0xFF0000) >> 16;
    int i1 = (paramInt2 & 0xFF00) >> 8;
    int i2 = paramInt2 & 0xFF;
    int i3 = j * n >> 8;
    int i4 = k * i1 >> 8;
    int i5 = m * i2 >> 8;
    return low(i + ((paramInt1 & 0xFF000000) >>> 24), 255) << 24 | peg(j + (i * (i3 - j) >> 8)) << 16 | peg(k + (i * (i4 - k) >> 8)) << 8 | peg(m + (i * (i5 - m) >> 8));
  }
  
  private static int blend_overlay(int paramInt1, int paramInt2)
  {
    int i = (0xFF000000 & paramInt2) >>> 24;
    int j = (0xFF0000 & paramInt1) >> 16;
    int k = (0xFF00 & paramInt1) >> 8;
    int m = paramInt1 & 0xFF;
    int n = (0xFF0000 & paramInt2) >> 16;
    int i1 = (0xFF00 & paramInt2) >> 8;
    int i2 = paramInt2 & 0xFF;
    int i3;
    int i4;
    if (j < 128)
    {
      i3 = j * n >> 7;
      if (k >= 128) {
        break label207;
      }
      i4 = k * i1 >> 7;
      label91:
      if (m >= 128) {
        break label232;
      }
    }
    label207:
    label232:
    for (int i5 = m * i2 >> 7;; i5 = 255 - ((255 - m) * (255 - i2) >> 7))
    {
      return low(i + ((0xFF000000 & paramInt1) >>> 24), 255) << 24 | peg(j + (i * (i3 - j) >> 8)) << 16 | peg(k + (i * (i4 - k) >> 8)) << 8 | peg(m + (i * (i5 - m) >> 8));
      i3 = 255 - ((255 - j) * (255 - n) >> 7);
      break;
      i4 = 255 - ((255 - k) * (255 - i1) >> 7);
      break label91;
    }
  }
  
  private static int blend_screen(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF000000) >>> 24;
    int j = (paramInt1 & 0xFF0000) >> 16;
    int k = (paramInt1 & 0xFF00) >> 8;
    int m = paramInt1 & 0xFF;
    int n = (paramInt2 & 0xFF0000) >> 16;
    int i1 = (paramInt2 & 0xFF00) >> 8;
    int i2 = paramInt2 & 0xFF;
    int i3 = 255 - ((255 - j) * (255 - n) >> 8);
    int i4 = 255 - ((255 - k) * (255 - i1) >> 8);
    int i5 = 255 - ((255 - m) * (255 - i2) >> 8);
    return low(i + ((paramInt1 & 0xFF000000) >>> 24), 255) << 24 | peg(j + (i * (i3 - j) >> 8)) << 16 | peg(k + (i * (i4 - k) >> 8)) << 8 | peg(m + (i * (i5 - m) >> 8));
  }
  
  private static int blend_soft_light(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF000000) >>> 24;
    int j = (paramInt1 & 0xFF0000) >> 16;
    int k = (paramInt1 & 0xFF00) >> 8;
    int m = paramInt1 & 0xFF;
    int n = (paramInt2 & 0xFF0000) >> 16;
    int i1 = (paramInt2 & 0xFF00) >> 8;
    int i2 = paramInt2 & 0xFF;
    int i3 = (j * n >> 7) + (j * j >> 8) - (n * (j * j) >> 15);
    int i4 = (k * i1 >> 7) + (k * k >> 8) - (i1 * (k * k) >> 15);
    int i5 = (m * i2 >> 7) + (m * m >> 8) - (i2 * (m * m) >> 15);
    return low(i + ((paramInt1 & 0xFF000000) >>> 24), 255) << 24 | peg(j + (i * (i3 - j) >> 8)) << 16 | peg(k + (i * (i4 - k) >> 8)) << 8 | peg(m + (i * (i5 - m) >> 8));
  }
  
  private static int blend_sub_pin(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF000000) >>> 24;
    return low(i + ((paramInt1 & 0xFF000000) >>> 24), 255) << 24 | 0xFF0000 & high((paramInt1 & 0xFF0000) - i * ((paramInt2 & 0xFF0000) >> 8), 65280) | 0xFF00 & high((paramInt1 & 0xFF00) - i * ((paramInt2 & 0xFF00) >> 8), 255) | high((paramInt1 & 0xFF) - (i * (paramInt2 & 0xFF) >> 8), 0);
  }
  
  private void blit_resize(PImage paramPImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11)
  {
    if (paramInt1 < 0) {
      paramInt1 = 0;
    }
    if (paramInt2 < 0) {
      paramInt2 = 0;
    }
    int i = paramPImage.width;
    if (paramInt3 > i) {
      paramInt3 = paramPImage.width;
    }
    int j = paramPImage.height;
    if (paramInt4 > j) {
      paramInt4 = paramPImage.height;
    }
    int k = paramInt3 - paramInt1;
    int m = paramInt4 - paramInt2;
    int n = paramInt9 - paramInt7;
    int i1 = paramInt10 - paramInt8;
    if (1 == 0)
    {
      k++;
      m++;
    }
    if ((n > 0) && (i1 > 0) && (k > 0) && (m > 0) && (paramInt7 < paramInt5) && (paramInt8 < paramInt6))
    {
      int i2 = paramPImage.width;
      if (paramInt1 < i2)
      {
        int i3 = paramPImage.height;
        if (paramInt2 < i3) {
          break label145;
        }
      }
    }
    label145:
    int i4;
    int i5;
    label184:
    label203:
    int i8;
    int i9;
    int i10;
    label448:
    label502:
    int i13;
    label1687:
    label1772:
    label1908:
    do
    {
      int i15;
      do
      {
        int i17;
        do
        {
          int i19;
          do
          {
            int i21;
            do
            {
              int i23;
              do
              {
                int i25;
                do
                {
                  int i27;
                  do
                  {
                    int i39;
                    do
                    {
                      int i43;
                      do
                      {
                        int i45;
                        do
                        {
                          int i47;
                          do
                          {
                            int i49;
                            do
                            {
                              int i51;
                              do
                              {
                                int i53;
                                do
                                {
                                  int i55;
                                  do
                                  {
                                    int i57;
                                    do
                                    {
                                      int i6;
                                      int i7;
                                      int i69;
                                      do
                                      {
                                        return;
                                        i4 = (int)(32768.0F * (k / n));
                                        i5 = (int)(32768.0F * (m / i1));
                                        if (paramInt7 >= 0) {
                                          break;
                                        }
                                        i6 = i4 * -paramInt7;
                                        this.srcXOffset = i6;
                                        if (paramInt8 >= 0) {
                                          break label502;
                                        }
                                        i7 = i5 * -paramInt8;
                                        this.srcYOffset = i7;
                                        if (paramInt7 < 0)
                                        {
                                          n += paramInt7;
                                          paramInt7 = 0;
                                        }
                                        if (paramInt8 < 0)
                                        {
                                          i1 += paramInt8;
                                          paramInt8 = 0;
                                        }
                                        i8 = low(n, paramInt5 - paramInt7);
                                        i9 = low(i1, paramInt6 - paramInt8);
                                        i10 = paramInt7 + paramInt8 * paramInt5;
                                        this.srcBuffer = paramPImage.pixels;
                                        if (1 == 0) {
                                          break label1772;
                                        }
                                        this.iw = paramPImage.width;
                                        this.iw1 = (-1 + paramPImage.width);
                                        this.ih1 = (-1 + paramPImage.height);
                                        switch (paramInt11)
                                        {
                                        default: 
                                          return;
                                        case 0: 
                                          i69 = 0;
                                        }
                                      } while (i69 >= i9);
                                      filter_new_scanline();
                                      for (int i70 = 0;; i70++)
                                      {
                                        if (i70 >= i8)
                                        {
                                          i10 += paramInt5;
                                          this.srcYOffset = (i5 + this.srcYOffset);
                                          i69++;
                                          break label448;
                                          i6 = 32768 * paramInt1;
                                          break label184;
                                          i7 = 32768 * paramInt2;
                                          break label203;
                                          int i67 = 0;
                                          if (i67 >= i9) {
                                            break;
                                          }
                                          filter_new_scanline();
                                          for (int i68 = 0;; i68++)
                                          {
                                            if (i68 >= i8)
                                            {
                                              i10 += paramInt5;
                                              this.srcYOffset = (i5 + this.srcYOffset);
                                              i67++;
                                              break;
                                            }
                                            paramArrayOfInt[(i10 + i68)] = blend_blend(paramArrayOfInt[(i10 + i68)], filter_bilinear());
                                            this.sX = (i4 + this.sX);
                                          }
                                          int i65 = 0;
                                          if (i65 >= i9) {
                                            break;
                                          }
                                          filter_new_scanline();
                                          for (int i66 = 0;; i66++)
                                          {
                                            if (i66 >= i8)
                                            {
                                              i10 += paramInt5;
                                              this.srcYOffset = (i5 + this.srcYOffset);
                                              i65++;
                                              break;
                                            }
                                            paramArrayOfInt[(i10 + i66)] = blend_add_pin(paramArrayOfInt[(i10 + i66)], filter_bilinear());
                                            this.sX = (i4 + this.sX);
                                          }
                                          int i63 = 0;
                                          if (i63 >= i9) {
                                            break;
                                          }
                                          filter_new_scanline();
                                          for (int i64 = 0;; i64++)
                                          {
                                            if (i64 >= i8)
                                            {
                                              i10 += paramInt5;
                                              this.srcYOffset = (i5 + this.srcYOffset);
                                              i63++;
                                              break;
                                            }
                                            paramArrayOfInt[(i10 + i64)] = blend_sub_pin(paramArrayOfInt[(i10 + i64)], filter_bilinear());
                                            this.sX = (i4 + this.sX);
                                          }
                                          int i61 = 0;
                                          if (i61 >= i9) {
                                            break;
                                          }
                                          filter_new_scanline();
                                          for (int i62 = 0;; i62++)
                                          {
                                            if (i62 >= i8)
                                            {
                                              i10 += paramInt5;
                                              this.srcYOffset = (i5 + this.srcYOffset);
                                              i61++;
                                              break;
                                            }
                                            paramArrayOfInt[(i10 + i62)] = blend_lightest(paramArrayOfInt[(i10 + i62)], filter_bilinear());
                                            this.sX = (i4 + this.sX);
                                          }
                                          int i59 = 0;
                                          if (i59 >= i9) {
                                            break;
                                          }
                                          filter_new_scanline();
                                          for (int i60 = 0;; i60++)
                                          {
                                            if (i60 >= i8)
                                            {
                                              i10 += paramInt5;
                                              this.srcYOffset = (i5 + this.srcYOffset);
                                              i59++;
                                              break;
                                            }
                                            paramArrayOfInt[(i10 + i60)] = blend_darkest(paramArrayOfInt[(i10 + i60)], filter_bilinear());
                                            this.sX = (i4 + this.sX);
                                          }
                                        }
                                        paramArrayOfInt[(i10 + i70)] = filter_bilinear();
                                        this.sX = (i4 + this.sX);
                                      }
                                      i57 = 0;
                                    } while (i57 >= i9);
                                    filter_new_scanline();
                                    for (int i58 = 0;; i58++)
                                    {
                                      if (i58 >= i8)
                                      {
                                        i10 += paramInt5;
                                        this.srcYOffset = (i5 + this.srcYOffset);
                                        i57++;
                                        break;
                                      }
                                      paramArrayOfInt[(i10 + i58)] = blend_difference(paramArrayOfInt[(i10 + i58)], filter_bilinear());
                                      this.sX = (i4 + this.sX);
                                    }
                                    i55 = 0;
                                  } while (i55 >= i9);
                                  filter_new_scanline();
                                  for (int i56 = 0;; i56++)
                                  {
                                    if (i56 >= i8)
                                    {
                                      i10 += paramInt5;
                                      this.srcYOffset = (i5 + this.srcYOffset);
                                      i55++;
                                      break;
                                    }
                                    paramArrayOfInt[(i10 + i56)] = blend_exclusion(paramArrayOfInt[(i10 + i56)], filter_bilinear());
                                    this.sX = (i4 + this.sX);
                                  }
                                  i53 = 0;
                                } while (i53 >= i9);
                                filter_new_scanline();
                                for (int i54 = 0;; i54++)
                                {
                                  if (i54 >= i8)
                                  {
                                    i10 += paramInt5;
                                    this.srcYOffset = (i5 + this.srcYOffset);
                                    i53++;
                                    break;
                                  }
                                  paramArrayOfInt[(i10 + i54)] = blend_multiply(paramArrayOfInt[(i10 + i54)], filter_bilinear());
                                  this.sX = (i4 + this.sX);
                                }
                                i51 = 0;
                              } while (i51 >= i9);
                              filter_new_scanline();
                              for (int i52 = 0;; i52++)
                              {
                                if (i52 >= i8)
                                {
                                  i10 += paramInt5;
                                  this.srcYOffset = (i5 + this.srcYOffset);
                                  i51++;
                                  break;
                                }
                                paramArrayOfInt[(i10 + i52)] = blend_screen(paramArrayOfInt[(i10 + i52)], filter_bilinear());
                                this.sX = (i4 + this.sX);
                              }
                              i49 = 0;
                            } while (i49 >= i9);
                            filter_new_scanline();
                            for (int i50 = 0;; i50++)
                            {
                              if (i50 >= i8)
                              {
                                i10 += paramInt5;
                                this.srcYOffset = (i5 + this.srcYOffset);
                                i49++;
                                break;
                              }
                              paramArrayOfInt[(i10 + i50)] = blend_overlay(paramArrayOfInt[(i10 + i50)], filter_bilinear());
                              this.sX = (i4 + this.sX);
                            }
                            i47 = 0;
                          } while (i47 >= i9);
                          filter_new_scanline();
                          for (int i48 = 0;; i48++)
                          {
                            if (i48 >= i8)
                            {
                              i10 += paramInt5;
                              this.srcYOffset = (i5 + this.srcYOffset);
                              i47++;
                              break;
                            }
                            paramArrayOfInt[(i10 + i48)] = blend_hard_light(paramArrayOfInt[(i10 + i48)], filter_bilinear());
                            this.sX = (i4 + this.sX);
                          }
                          i45 = 0;
                        } while (i45 >= i9);
                        filter_new_scanline();
                        for (int i46 = 0;; i46++)
                        {
                          if (i46 >= i8)
                          {
                            i10 += paramInt5;
                            this.srcYOffset = (i5 + this.srcYOffset);
                            i45++;
                            break;
                          }
                          paramArrayOfInt[(i10 + i46)] = blend_soft_light(paramArrayOfInt[(i10 + i46)], filter_bilinear());
                          this.sX = (i4 + this.sX);
                        }
                        i43 = 0;
                      } while (i43 >= i9);
                      filter_new_scanline();
                      for (int i44 = 0;; i44++)
                      {
                        if (i44 >= i8)
                        {
                          i10 += paramInt5;
                          this.srcYOffset = (i5 + this.srcYOffset);
                          i43++;
                          break;
                        }
                        paramArrayOfInt[(i10 + i44)] = blend_dodge(paramArrayOfInt[(i10 + i44)], filter_bilinear());
                        this.sX = (i4 + this.sX);
                      }
                      int i41 = 0;
                      if (i41 < i9) {
                        filter_new_scanline();
                      }
                      for (int i42 = 0;; i42++)
                      {
                        if (i42 >= i8)
                        {
                          i10 += paramInt5;
                          this.srcYOffset = (i5 + this.srcYOffset);
                          i41++;
                          break label1687;
                          break;
                        }
                        paramArrayOfInt[(i10 + i42)] = blend_burn(paramArrayOfInt[(i10 + i42)], filter_bilinear());
                        this.sX = (i4 + this.sX);
                      }
                      switch (paramInt11)
                      {
                      default: 
                        return;
                      case 0: 
                        i39 = 0;
                      }
                    } while (i39 >= i9);
                    this.sX = this.srcXOffset;
                    this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
                    for (int i40 = 0;; i40++)
                    {
                      if (i40 >= i8)
                      {
                        i10 += paramInt5;
                        this.srcYOffset = (i5 + this.srcYOffset);
                        i39++;
                        break label1908;
                        int i37 = 0;
                        if (i37 >= i9) {
                          break;
                        }
                        this.sX = this.srcXOffset;
                        this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
                        for (int i38 = 0;; i38++)
                        {
                          if (i38 >= i8)
                          {
                            i10 += paramInt5;
                            this.srcYOffset = (i5 + this.srcYOffset);
                            i37++;
                            break;
                          }
                          paramArrayOfInt[(i10 + i38)] = blend_blend(paramArrayOfInt[(i10 + i38)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
                          this.sX = (i4 + this.sX);
                        }
                        int i35 = 0;
                        if (i35 >= i9) {
                          break;
                        }
                        this.sX = this.srcXOffset;
                        this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
                        for (int i36 = 0;; i36++)
                        {
                          if (i36 >= i8)
                          {
                            i10 += paramInt5;
                            this.srcYOffset = (i5 + this.srcYOffset);
                            i35++;
                            break;
                          }
                          paramArrayOfInt[(i10 + i36)] = blend_add_pin(paramArrayOfInt[(i10 + i36)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
                          this.sX = (i4 + this.sX);
                        }
                        int i33 = 0;
                        if (i33 >= i9) {
                          break;
                        }
                        this.sX = this.srcXOffset;
                        this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
                        for (int i34 = 0;; i34++)
                        {
                          if (i34 >= i8)
                          {
                            i10 += paramInt5;
                            this.srcYOffset = (i5 + this.srcYOffset);
                            i33++;
                            break;
                          }
                          paramArrayOfInt[(i10 + i34)] = blend_sub_pin(paramArrayOfInt[(i10 + i34)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
                          this.sX = (i4 + this.sX);
                        }
                        int i31 = 0;
                        if (i31 >= i9) {
                          break;
                        }
                        this.sX = this.srcXOffset;
                        this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
                        for (int i32 = 0;; i32++)
                        {
                          if (i32 >= i8)
                          {
                            i10 += paramInt5;
                            this.srcYOffset = (i5 + this.srcYOffset);
                            i31++;
                            break;
                          }
                          paramArrayOfInt[(i10 + i32)] = blend_lightest(paramArrayOfInt[(i10 + i32)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
                          this.sX = (i4 + this.sX);
                        }
                        int i29 = 0;
                        if (i29 >= i9) {
                          break;
                        }
                        this.sX = this.srcXOffset;
                        this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
                        for (int i30 = 0;; i30++)
                        {
                          if (i30 >= i8)
                          {
                            i10 += paramInt5;
                            this.srcYOffset = (i5 + this.srcYOffset);
                            i29++;
                            break;
                          }
                          paramArrayOfInt[(i10 + i30)] = blend_darkest(paramArrayOfInt[(i10 + i30)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
                          this.sX = (i4 + this.sX);
                        }
                      }
                      paramArrayOfInt[(i10 + i40)] = this.srcBuffer[(this.sY + (this.sX >> 15))];
                      this.sX = (i4 + this.sX);
                    }
                    i27 = 0;
                  } while (i27 >= i9);
                  this.sX = this.srcXOffset;
                  this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
                  for (int i28 = 0;; i28++)
                  {
                    if (i28 >= i8)
                    {
                      i10 += paramInt5;
                      this.srcYOffset = (i5 + this.srcYOffset);
                      i27++;
                      break;
                    }
                    paramArrayOfInt[(i10 + i28)] = blend_difference(paramArrayOfInt[(i10 + i28)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
                    this.sX = (i4 + this.sX);
                  }
                  i25 = 0;
                } while (i25 >= i9);
                this.sX = this.srcXOffset;
                this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
                for (int i26 = 0;; i26++)
                {
                  if (i26 >= i8)
                  {
                    i10 += paramInt5;
                    this.srcYOffset = (i5 + this.srcYOffset);
                    i25++;
                    break;
                  }
                  paramArrayOfInt[(i10 + i26)] = blend_exclusion(paramArrayOfInt[(i10 + i26)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
                  this.sX = (i4 + this.sX);
                }
                i23 = 0;
              } while (i23 >= i9);
              this.sX = this.srcXOffset;
              this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
              for (int i24 = 0;; i24++)
              {
                if (i24 >= i8)
                {
                  i10 += paramInt5;
                  this.srcYOffset = (i5 + this.srcYOffset);
                  i23++;
                  break;
                }
                paramArrayOfInt[(i10 + i24)] = blend_multiply(paramArrayOfInt[(i10 + i24)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
                this.sX = (i4 + this.sX);
              }
              i21 = 0;
            } while (i21 >= i9);
            this.sX = this.srcXOffset;
            this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
            for (int i22 = 0;; i22++)
            {
              if (i22 >= i8)
              {
                i10 += paramInt5;
                this.srcYOffset = (i5 + this.srcYOffset);
                i21++;
                break;
              }
              paramArrayOfInt[(i10 + i22)] = blend_screen(paramArrayOfInt[(i10 + i22)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
              this.sX = (i4 + this.sX);
            }
            i19 = 0;
          } while (i19 >= i9);
          this.sX = this.srcXOffset;
          this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
          for (int i20 = 0;; i20++)
          {
            if (i20 >= i8)
            {
              i10 += paramInt5;
              this.srcYOffset = (i5 + this.srcYOffset);
              i19++;
              break;
            }
            paramArrayOfInt[(i10 + i20)] = blend_overlay(paramArrayOfInt[(i10 + i20)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
            this.sX = (i4 + this.sX);
          }
          i17 = 0;
        } while (i17 >= i9);
        this.sX = this.srcXOffset;
        this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
        for (int i18 = 0;; i18++)
        {
          if (i18 >= i8)
          {
            i10 += paramInt5;
            this.srcYOffset = (i5 + this.srcYOffset);
            i17++;
            break;
          }
          paramArrayOfInt[(i10 + i18)] = blend_hard_light(paramArrayOfInt[(i10 + i18)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
          this.sX = (i4 + this.sX);
        }
        i15 = 0;
      } while (i15 >= i9);
      this.sX = this.srcXOffset;
      this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
      for (int i16 = 0;; i16++)
      {
        if (i16 >= i8)
        {
          i10 += paramInt5;
          this.srcYOffset = (i5 + this.srcYOffset);
          i15++;
          break;
        }
        paramArrayOfInt[(i10 + i16)] = blend_soft_light(paramArrayOfInt[(i10 + i16)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
        this.sX = (i4 + this.sX);
      }
      i13 = 0;
    } while (i13 >= i9);
    this.sX = this.srcXOffset;
    this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
    for (int i14 = 0;; i14++)
    {
      if (i14 >= i8)
      {
        i10 += paramInt5;
        this.srcYOffset = (i5 + this.srcYOffset);
        i13++;
        break;
      }
      paramArrayOfInt[(i10 + i14)] = blend_dodge(paramArrayOfInt[(i10 + i14)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
      this.sX = (i4 + this.sX);
    }
    int i11 = 0;
    label3591:
    if (i11 < i9)
    {
      this.sX = this.srcXOffset;
      this.sY = ((this.srcYOffset >> 15) * paramPImage.width);
    }
    for (int i12 = 0;; i12++)
    {
      if (i12 >= i8)
      {
        i10 += paramInt5;
        this.srcYOffset = (i5 + this.srcYOffset);
        i11++;
        break label3591;
        break;
      }
      paramArrayOfInt[(i10 + i12)] = blend_burn(paramArrayOfInt[(i10 + i12)], this.srcBuffer[(this.sY + (this.sX >> 15))]);
      this.sX = (i4 + this.sX);
    }
  }
  
  private int filter_bilinear()
  {
    this.fracU = (0x7FFF & this.sX);
    this.ifU = (32767 - this.fracU);
    this.ul = (this.ifU * this.ifV >> 15);
    this.ll = (this.ifU * this.fracV >> 15);
    this.ur = (this.fracU * this.ifV >> 15);
    this.lr = (this.fracU * this.fracV >> 15);
    this.u1 = (this.sX >> 15);
    this.u2 = low(1 + this.u1, this.iw1);
    this.cUL = this.srcBuffer[(this.v1 + this.u1)];
    this.cUR = this.srcBuffer[(this.v1 + this.u2)];
    this.cLL = this.srcBuffer[(this.v2 + this.u1)];
    this.cLR = this.srcBuffer[(this.v2 + this.u2)];
    this.r = (0xFF0000 & this.ul * ((0xFF0000 & this.cUL) >> 16) + this.ll * ((0xFF0000 & this.cLL) >> 16) + this.ur * ((0xFF0000 & this.cUR) >> 16) + this.lr * ((0xFF0000 & this.cLR) >> 16) << 1);
    this.g = (0xFF00 & this.ul * (0xFF00 & this.cUL) + this.ll * (0xFF00 & this.cLL) + this.ur * (0xFF00 & this.cUR) + this.lr * (0xFF00 & this.cLR) >>> 15);
    this.b = (this.ul * (0xFF & this.cUL) + this.ll * (0xFF & this.cLL) + this.ur * (0xFF & this.cUR) + this.lr * (0xFF & this.cLR) >>> 15);
    this.a = (0xFF000000 & this.ul * ((0xFF000000 & this.cUL) >>> 24) + this.ll * ((0xFF000000 & this.cLL) >>> 24) + this.ur * ((0xFF000000 & this.cUR) >>> 24) + this.lr * ((0xFF000000 & this.cLR) >>> 24) << 9);
    return this.a | this.r | this.g | this.b;
  }
  
  private void filter_new_scanline()
  {
    this.sX = this.srcXOffset;
    this.fracV = (0x7FFF & this.srcYOffset);
    this.ifV = (32767 - this.fracV);
    this.v1 = ((this.srcYOffset >> 15) * this.iw);
    this.v2 = (low(1 + (this.srcYOffset >> 15), this.ih1) * this.iw);
  }
  
  private static int high(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) {
      return paramInt1;
    }
    return paramInt2;
  }
  
  private boolean intersect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    int i = 1 + (paramInt3 - paramInt1);
    int j = 1 + (paramInt4 - paramInt2);
    int k = 1 + (paramInt7 - paramInt5);
    int m = 1 + (paramInt8 - paramInt6);
    if (paramInt5 < paramInt1)
    {
      k += paramInt5 - paramInt1;
      if (k > i) {
        k = i;
      }
      if (paramInt6 >= paramInt2) {
        break label120;
      }
      m += paramInt6 - paramInt2;
      if (m > j) {
        m = j;
      }
    }
    for (;;)
    {
      if ((k > 0) && (m > 0)) {
        break label143;
      }
      return false;
      int n = i + paramInt1 - paramInt5;
      if (k <= n) {
        break;
      }
      k = n;
      break;
      label120:
      int i1 = j + paramInt2 - paramInt6;
      if (m > i1) {
        m = i1;
      }
    }
    label143:
    return true;
  }
  
  protected static PImage loadTIFF(byte[] paramArrayOfByte)
  {
    PImage localPImage = null;
    if ((paramArrayOfByte[42] != paramArrayOfByte[102]) || (paramArrayOfByte[43] != paramArrayOfByte[103])) {
      System.err.println("Error: Processing can only read its own TIFF files.");
    }
    int m;
    for (;;)
    {
      return localPImage;
      int i = (0xFF & paramArrayOfByte[30]) << 8 | 0xFF & paramArrayOfByte[31];
      int j = (0xFF & paramArrayOfByte[42]) << 8 | 0xFF & paramArrayOfByte[43];
      int k = (0xFF & paramArrayOfByte[114]) << 24 | (0xFF & paramArrayOfByte[115]) << 16 | (0xFF & paramArrayOfByte[116]) << 8 | 0xFF & paramArrayOfByte[117];
      if (k != 3 * (i * j))
      {
        System.err.println("Error: Processing can only read its own TIFF files. (" + i + ", " + j + ")");
        return null;
      }
      m = 0;
      if (m < TIFF_HEADER.length) {
        break;
      }
      localPImage = new PImage(i, j, 1);
      int n = k / 3;
      int i1 = 0;
      int i7;
      for (int i2 = 768; i1 < n; i2 = i7)
      {
        int[] arrayOfInt = localPImage.pixels;
        int i3 = i2 + 1;
        int i4 = 0xFF000000 | (0xFF & paramArrayOfByte[i2]) << 16;
        int i5 = i3 + 1;
        int i6 = i4 | (0xFF & paramArrayOfByte[i3]) << 8;
        i7 = i5 + 1;
        arrayOfInt[i1] = (i6 | 0xFF & paramArrayOfByte[i5]);
        i1++;
      }
    }
    if ((m == 30) || (m == 31) || (m == 42) || (m == 43) || (m == 102) || (m == 103) || (m == 114) || (m == 115) || (m == 116) || (m == 117)) {}
    while (paramArrayOfByte[m] == TIFF_HEADER[m])
    {
      m++;
      break;
    }
    System.err.println("Error: Processing can only read its own TIFF files. (" + m + ")");
    return null;
  }
  
  private static int low(int paramInt1, int paramInt2)
  {
    if (paramInt1 < paramInt2) {
      return paramInt1;
    }
    return paramInt2;
  }
  
  private static int mix(int paramInt1, int paramInt2, int paramInt3)
  {
    return paramInt1 + (paramInt3 * (paramInt2 - paramInt1) >> 8);
  }
  
  private static int peg(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    while (paramInt <= 255) {
      return paramInt;
    }
    return 255;
  }
  
  public void blend(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    blend(this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9);
  }
  
  public void blend(PImage paramPImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    int i = paramInt1 + paramInt3;
    int j = paramInt2 + paramInt4;
    int k = paramInt5 + paramInt7;
    int m = paramInt6 + paramInt8;
    loadPixels();
    if (paramPImage == this) {
      if (intersect(paramInt1, paramInt2, i, j, paramInt5, paramInt6, k, m)) {
        blit_resize(get(paramInt1, paramInt2, i - paramInt1, j - paramInt2), 0, 0, -1 + (i - paramInt1), -1 + (j - paramInt2), this.pixels, this.width, this.height, paramInt5, paramInt6, k, m, paramInt9);
      }
    }
    for (;;)
    {
      updatePixels();
      return;
      blit_resize(paramPImage, paramInt1, paramInt2, i, j, this.pixels, this.width, this.height, paramInt5, paramInt6, k, m, paramInt9);
      continue;
      paramPImage.loadPixels();
      blit_resize(paramPImage, paramInt1, paramInt2, i, j, this.pixels, this.width, this.height, paramInt5, paramInt6, k, m, paramInt9);
    }
  }
  
  protected void blurARGB(float paramFloat)
  {
    int i = this.pixels.length;
    int[] arrayOfInt1 = new int[i];
    int[] arrayOfInt2 = new int[i];
    int[] arrayOfInt3 = new int[i];
    int[] arrayOfInt4 = new int[i];
    int j = 0;
    buildBlurKernel(paramFloat);
    int k = 0;
    int m = this.height;
    int i12;
    int i13;
    int i14;
    if (k >= m)
    {
      i12 = 0;
      i13 = -this.blurRadius;
      i14 = i13 * this.width;
    }
    label101:
    int i17;
    for (int i15 = 0;; i15++)
    {
      int i16 = this.height;
      if (i15 >= i16)
      {
        return;
        int n = 0;
        int i1 = this.width;
        if (n >= i1)
        {
          j += this.width;
          k++;
          break;
        }
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = n - this.blurRadius;
        int i8;
        if (i7 < 0)
        {
          i8 = -i7;
          i7 = 0;
        }
        label153:
        for (int i9 = i8;; i9++)
        {
          if (i9 >= this.blurKernelSize) {}
          while (i7 >= this.width)
          {
            int i11 = j + n;
            arrayOfInt4[i11] = (i3 / i2);
            arrayOfInt1[i11] = (i4 / i2);
            arrayOfInt2[i11] = (i5 / i2);
            arrayOfInt3[i11] = (i6 / i2);
            n++;
            break;
            if (i7 >= this.width) {
              break label101;
            }
            i8 = 0;
            break label153;
          }
          int i10 = this.pixels[(i7 + j)];
          int[] arrayOfInt5 = this.blurMult[i9];
          i3 += arrayOfInt5[((0xFF000000 & i10) >>> 24)];
          i4 += arrayOfInt5[((0xFF0000 & i10) >> 16)];
          i5 += arrayOfInt5[((0xFF00 & i10) >> 8)];
          i6 += arrayOfInt5[(i10 & 0xFF)];
          i2 += this.blurKernel[i9];
          i7++;
        }
      }
      i17 = 0;
      int i18 = this.width;
      if (i17 < i18) {
        break label389;
      }
      label362:
      i12 += this.width;
      i14 += this.width;
      i13++;
    }
    label389:
    int i19 = 0;
    int i20 = 0;
    int i21 = 0;
    int i22 = 0;
    int i23 = 0;
    int i25;
    int i27;
    int i26;
    if (i13 < 0)
    {
      i25 = -i13;
      i27 = i25;
      i26 = i17;
    }
    label422:
    for (int i28 = i27;; i28++)
    {
      if (i28 >= this.blurKernelSize) {}
      while (i25 >= this.height)
      {
        this.pixels[(i17 + i12)] = (i20 / i19 << 24 | i21 / i19 << 16 | i22 / i19 << 8 | i23 / i19);
        i17++;
        break;
        int i24 = this.height;
        if (i13 >= i24) {
          break label362;
        }
        i25 = i13;
        i26 = i17 + i14;
        i27 = 0;
        break label422;
      }
      int[] arrayOfInt6 = this.blurMult[i28];
      i20 += arrayOfInt6[arrayOfInt4[i26]];
      i21 += arrayOfInt6[arrayOfInt1[i26]];
      i22 += arrayOfInt6[arrayOfInt2[i26]];
      i23 += arrayOfInt6[arrayOfInt3[i26]];
      i19 += this.blurKernel[i28];
      i25++;
      i26 += this.width;
    }
  }
  
  protected void blurAlpha(float paramFloat)
  {
    int[] arrayOfInt = new int[this.pixels.length];
    int i = 0;
    buildBlurKernel(paramFloat);
    int j = 0;
    int i5;
    int i6;
    int i7;
    if (j >= this.height)
    {
      i5 = 0;
      i6 = -this.blurRadius;
      i7 = i6 * this.width;
    }
    label71:
    label112:
    int i9;
    for (int i8 = 0;; i8++)
    {
      if (i8 >= this.height)
      {
        return;
        int k = 0;
        if (k >= this.width)
        {
          i += this.width;
          j++;
          break;
        }
        int m = 0;
        int n = 0;
        int i1 = k - this.blurRadius;
        int i2;
        if (i1 < 0)
        {
          i2 = -i1;
          i1 = 0;
        }
        for (int i3 = i2;; i3++)
        {
          if (i3 >= this.blurKernelSize) {}
          while (i1 >= this.width)
          {
            arrayOfInt[(i + k)] = (n / m);
            k++;
            break;
            if (i1 >= this.width) {
              break label71;
            }
            i2 = 0;
            break label112;
          }
          int i4 = this.pixels[(i1 + i)];
          n += this.blurMult[i3][(i4 & 0xFF)];
          m += this.blurKernel[i3];
          i1++;
        }
      }
      i9 = 0;
      if (i9 < this.width) {
        break label256;
      }
      label229:
      i5 += this.width;
      i7 += this.width;
      i6++;
    }
    label256:
    int i10 = 0;
    int i11 = 0;
    int i12;
    int i14;
    int i13;
    if (i6 < 0)
    {
      i12 = -i6;
      i14 = i12;
      i13 = i9;
    }
    label280:
    for (int i15 = i14;; i15++)
    {
      if (i15 >= this.blurKernelSize) {}
      while (i12 >= this.height)
      {
        this.pixels[(i9 + i5)] = (i11 / i10);
        i9++;
        break;
        if (i6 >= this.height) {
          break label229;
        }
        i12 = i6;
        i13 = i9 + i7;
        i14 = 0;
        break label280;
      }
      i11 += this.blurMult[i15][arrayOfInt[i13]];
      i10 += this.blurKernel[i15];
      i12++;
      i13 += this.width;
    }
  }
  
  protected void blurRGB(float paramFloat)
  {
    int[] arrayOfInt1 = new int[this.pixels.length];
    int[] arrayOfInt2 = new int[this.pixels.length];
    int[] arrayOfInt3 = new int[this.pixels.length];
    int i = 0;
    buildBlurKernel(paramFloat);
    int j = 0;
    int k = this.height;
    int i9;
    int i10;
    int i11;
    if (j >= k)
    {
      i9 = 0;
      i10 = -this.blurRadius;
      i11 = i10 * this.width;
    }
    label97:
    int i14;
    for (int i12 = 0;; i12++)
    {
      int i13 = this.height;
      if (i12 >= i13)
      {
        return;
        int m = 0;
        if (m >= this.width)
        {
          i += this.width;
          j++;
          break;
        }
        int n = 0;
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = m - this.blurRadius;
        int i5;
        if (i4 < 0)
        {
          i5 = -i4;
          i4 = 0;
        }
        label146:
        for (int i6 = i5;; i6++)
        {
          if (i6 >= this.blurKernelSize) {}
          while (i4 >= this.width)
          {
            int i8 = i + m;
            arrayOfInt1[i8] = (i1 / n);
            arrayOfInt2[i8] = (i2 / n);
            arrayOfInt3[i8] = (i3 / n);
            m++;
            break;
            if (i4 >= this.width) {
              break label97;
            }
            i5 = 0;
            break label146;
          }
          int i7 = this.pixels[(i4 + i)];
          int[] arrayOfInt4 = this.blurMult[i6];
          i1 += arrayOfInt4[((0xFF0000 & i7) >> 16)];
          i2 += arrayOfInt4[((0xFF00 & i7) >> 8)];
          i3 += arrayOfInt4[(i7 & 0xFF)];
          n += this.blurKernel[i6];
          i4++;
        }
      }
      i14 = 0;
      if (i14 < this.width) {
        break label351;
      }
      label324:
      i9 += this.width;
      i11 += this.width;
      i10++;
    }
    label351:
    int i15 = 0;
    int i16 = 0;
    int i17 = 0;
    int i18 = 0;
    int i20;
    int i22;
    int i21;
    if (i10 < 0)
    {
      i20 = -i10;
      i22 = i20;
      i21 = i14;
    }
    label381:
    for (int i23 = i22;; i23++)
    {
      if (i23 >= this.blurKernelSize) {}
      while (i20 >= this.height)
      {
        this.pixels[(i14 + i9)] = (0xFF000000 | i16 / i15 << 16 | i17 / i15 << 8 | i18 / i15);
        i14++;
        break;
        int i19 = this.height;
        if (i10 >= i19) {
          break label324;
        }
        i20 = i10;
        i21 = i14 + i11;
        i22 = 0;
        break label381;
      }
      int[] arrayOfInt5 = this.blurMult[i23];
      i16 += arrayOfInt5[arrayOfInt1[i21]];
      i17 += arrayOfInt5[arrayOfInt2[i21]];
      i18 += arrayOfInt5[arrayOfInt3[i21]];
      i15 += this.blurKernel[i23];
      i20++;
      i21 += this.width;
    }
  }
  
  protected void buildBlurKernel(float paramFloat)
  {
    int i = (int)(3.5F * paramFloat);
    int j;
    int k;
    int i4;
    int[] arrayOfInt7;
    if (i < 1)
    {
      i = 1;
      if (this.blurRadius != i)
      {
        this.blurRadius = i;
        this.blurKernelSize = (1 + this.blurRadius << 1);
        this.blurKernel = new int[this.blurKernelSize];
        int[] arrayOfInt1 = { this.blurKernelSize, 256 };
        this.blurMult = ((int[][])Array.newInstance(Integer.TYPE, arrayOfInt1));
        j = 1;
        k = i - 1;
        if (j < i) {
          break label145;
        }
        int[] arrayOfInt6 = this.blurKernel;
        i4 = i * i;
        arrayOfInt6[i] = i4;
        arrayOfInt7 = this.blurMult[i];
      }
    }
    for (int i5 = 0;; i5++)
    {
      if (i5 >= 256)
      {
        return;
        if (i < 248) {
          break;
        }
        i = 248;
        break;
        label145:
        int[] arrayOfInt2 = this.blurKernel;
        int m = i + j;
        int[] arrayOfInt3 = this.blurKernel;
        int n = k * k;
        arrayOfInt3[k] = n;
        arrayOfInt2[m] = n;
        int[] arrayOfInt4 = this.blurMult[(i + j)];
        int[][] arrayOfInt = this.blurMult;
        int i1 = k - 1;
        int[] arrayOfInt5 = arrayOfInt[k];
        for (int i2 = 0;; i2++)
        {
          if (i2 >= 256)
          {
            j++;
            k = i1;
            break;
          }
          int i3 = n * i2;
          arrayOfInt5[i2] = i3;
          arrayOfInt4[i2] = i3;
        }
      }
      arrayOfInt7[i5] = (i4 * i5);
    }
  }
  
  protected void checkAlpha()
  {
    if (this.pixels == null) {}
    for (;;)
    {
      return;
      for (int i = 0; i < this.pixels.length; i++) {
        if ((0xFF000000 & this.pixels[i]) != -16777216)
        {
          this.format = 2;
          return;
        }
      }
    }
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    return get();
  }
  
  public void copy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    blend(this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, 0);
  }
  
  public void copy(PImage paramPImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    blend(paramPImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, 0);
  }
  
  protected void dilate(boolean paramBoolean)
  {
    int i = this.pixels.length;
    int[] arrayOfInt = new int[i];
    int j = 0;
    label450:
    int k;
    int m;
    if (!paramBoolean)
    {
      if (j >= i)
      {
        System.arraycopy(arrayOfInt, 0, this.pixels, 0, i);
        return;
      }
      int i17 = j;
      int i18 = j + this.width;
      int i35;
      for (int i19 = j;; i19 = i35)
      {
        if (i19 >= i18)
        {
          j = i19;
          break;
        }
        int i20 = this.pixels[i19];
        int i21 = i20;
        int i22 = i19 - 1;
        int i23 = i19 + 1;
        int i24 = i19 - this.width;
        int i25 = i19 + this.width;
        if (i22 < i17) {
          i22 = i19;
        }
        if (i23 >= i18) {
          i23 = i19;
        }
        if (i24 < 0) {
          i24 = 0;
        }
        if (i25 >= i) {
          i25 = i19;
        }
        int i26 = this.pixels[i24];
        int i27 = this.pixels[i22];
        int i28 = this.pixels[i25];
        int i29 = this.pixels[i23];
        int i30 = 77 * (0xFF & i21 >> 16) + 151 * (0xFF & i21 >> 8) + 28 * (i21 & 0xFF);
        int i31 = 77 * (0xFF & i27 >> 16) + 151 * (0xFF & i27 >> 8) + 28 * (i27 & 0xFF);
        int i32 = 77 * (0xFF & i29 >> 16) + 151 * (0xFF & i29 >> 8) + 28 * (i29 & 0xFF);
        int i33 = 77 * (0xFF & i26 >> 16) + 151 * (0xFF & i26 >> 8) + 28 * (i26 & 0xFF);
        int i34 = 77 * (0xFF & i28 >> 16) + 151 * (0xFF & i28 >> 8) + 28 * (i28 & 0xFF);
        if (i31 > i30)
        {
          i20 = i27;
          i30 = i31;
        }
        if (i32 > i30)
        {
          i20 = i29;
          i30 = i32;
        }
        if (i33 > i30)
        {
          i20 = i26;
          i30 = i33;
        }
        if (i34 > i30) {
          i20 = i28;
        }
        i35 = i19 + 1;
        arrayOfInt[i19] = i20;
      }
      k = j;
      m = j + this.width;
    }
    int i16;
    for (int n = j;; n = i16)
    {
      if (n >= m)
      {
        j = n;
        if (j < i) {
          break label450;
        }
        break;
      }
      int i1 = this.pixels[n];
      int i2 = i1;
      int i3 = n - 1;
      int i4 = n + 1;
      int i5 = n - this.width;
      int i6 = n + this.width;
      if (i3 < k) {
        i3 = n;
      }
      if (i4 >= m) {
        i4 = n;
      }
      if (i5 < 0) {
        i5 = 0;
      }
      if (i6 >= i) {
        i6 = n;
      }
      int i7 = this.pixels[i5];
      int i8 = this.pixels[i3];
      int i9 = this.pixels[i6];
      int i10 = this.pixels[i4];
      int i11 = 77 * (0xFF & i2 >> 16) + 151 * (0xFF & i2 >> 8) + 28 * (i2 & 0xFF);
      int i12 = 77 * (0xFF & i8 >> 16) + 151 * (0xFF & i8 >> 8) + 28 * (i8 & 0xFF);
      int i13 = 77 * (0xFF & i10 >> 16) + 151 * (0xFF & i10 >> 8) + 28 * (i10 & 0xFF);
      int i14 = 77 * (0xFF & i7 >> 16) + 151 * (0xFF & i7 >> 8) + 28 * (i7 & 0xFF);
      int i15 = 77 * (0xFF & i9 >> 16) + 151 * (0xFF & i9 >> 8) + 28 * (i9 & 0xFF);
      if (i12 < i11)
      {
        i1 = i8;
        i11 = i12;
      }
      if (i13 < i11)
      {
        i1 = i10;
        i11 = i13;
      }
      if (i14 < i11)
      {
        i1 = i7;
        i11 = i14;
      }
      if (i15 < i11) {
        i1 = i9;
      }
      i16 = n + 1;
      arrayOfInt[n] = i1;
    }
  }
  
  public void filter(int paramInt)
  {
    loadPixels();
    switch (paramInt)
    {
    }
    for (;;)
    {
      updatePixels();
      return;
      filter(11, 1.0F);
      continue;
      if (this.format == 4) {
        for (int i1 = 0;; i1++)
        {
          if (i1 >= this.pixels.length)
          {
            this.format = 1;
            break;
          }
          int i2 = 255 - this.pixels[i1];
          this.pixels[i1] = (i2 | 0xFF000000 | i2 << 16 | i2 << 8);
        }
      }
      for (int k = 0; k < this.pixels.length; k++)
      {
        int m = this.pixels[k];
        int n = 77 * (0xFF & m >> 16) + 151 * (0xFF & m >> 8) + 28 * (m & 0xFF) >> 8;
        this.pixels[k] = (n | m & 0xFF000000 | n << 16 | n << 8);
      }
      for (int j = 0; j < this.pixels.length; j++)
      {
        int[] arrayOfInt2 = this.pixels;
        arrayOfInt2[j] = (0xFFFFFF ^ arrayOfInt2[j]);
      }
      throw new RuntimeException("Use filter(POSTERIZE, int levels) instead of filter(POSTERIZE)");
      for (int i = 0;; i++)
      {
        if (i >= this.pixels.length)
        {
          this.format = 1;
          break;
        }
        int[] arrayOfInt1 = this.pixels;
        arrayOfInt1[i] = (0xFF000000 | arrayOfInt1[i]);
      }
      filter(16, 0.5F);
      continue;
      dilate(true);
      continue;
      dilate(false);
    }
  }
  
  public void filter(int paramInt, float paramFloat)
  {
    loadPixels();
    switch (paramInt)
    {
    default: 
    case 11: 
    case 12: 
    case 13: 
    case 14: 
    case 15: 
    case 16: 
      int i;
      int j;
      do
      {
        for (;;)
        {
          updatePixels();
          return;
          if (this.format == 4) {
            blurAlpha(paramFloat);
          } else if (this.format == 2) {
            blurARGB(paramFloat);
          } else {
            blurRGB(paramFloat);
          }
        }
        throw new RuntimeException("Use filter(GRAY) instead of filter(GRAY, param)");
        throw new RuntimeException("Use filter(INVERT) instead of filter(INVERT, param)");
        throw new RuntimeException("Use filter(OPAQUE) instead of filter(OPAQUE, param)");
        int i1 = (int)paramFloat;
        if ((i1 < 2) || (i1 > 255)) {
          throw new RuntimeException("Levels must be between 2 and 255 for filter(POSTERIZE, levels)");
        }
        int i2 = i1 - 1;
        for (int i3 = 0; i3 < this.pixels.length; i3++)
        {
          int i4 = 0xFF & this.pixels[i3] >> 16;
          int i5 = 0xFF & this.pixels[i3] >> 8;
          int i6 = 0xFF & this.pixels[i3];
          int i7 = 255 * (i4 * i1 >> 8) / i2;
          int i8 = 255 * (i5 * i1 >> 8) / i2;
          int i9 = 255 * (i6 * i1 >> 8) / i2;
          this.pixels[i3] = (i9 | 0xFF000000 & this.pixels[i3] | i7 << 16 | i8 << 8);
        }
        i = (int)(255.0F * paramFloat);
        j = 0;
      } while (j >= this.pixels.length);
      int k = Math.max((0xFF0000 & this.pixels[j]) >> 16, Math.max((0xFF00 & this.pixels[j]) >> 8, 0xFF & this.pixels[j]));
      int[] arrayOfInt = this.pixels;
      int m = 0xFF000000 & this.pixels[j];
      if (k < i) {}
      for (int n = 0;; n = 16777215)
      {
        arrayOfInt[j] = (n | m);
        j++;
        break;
      }
    case 17: 
      throw new RuntimeException("Use filter(ERODE) instead of filter(ERODE, param)");
    }
    throw new RuntimeException("Use filter(DILATE) instead of filter(DILATE, param)");
  }
  
  public int get(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
      return 0;
    }
    if (this.pixels == null) {
      return this.bitmap.getPixel(paramInt1, paramInt2);
    }
    switch (this.format)
    {
    case 3: 
    default: 
      return 0;
    case 1: 
      return 0xFF000000 | this.pixels[(paramInt1 + paramInt2 * this.width)];
    case 2: 
      return this.pixels[(paramInt1 + paramInt2 * this.width)];
    }
    return 0xFFFFFF | this.pixels[(paramInt1 + paramInt2 * this.width)] << 24;
  }
  
  public PImage get()
  {
    return get(0, 0, this.width, this.height);
  }
  
  public PImage get(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3;
    int j = paramInt4;
    int k = 0;
    int m = 0;
    if (paramInt1 < 0)
    {
      paramInt3 += paramInt1;
      k = -paramInt1;
      m = 1;
      paramInt1 = 0;
    }
    int n = 0;
    if (paramInt2 < 0)
    {
      paramInt4 += paramInt2;
      n = -paramInt2;
      m = 1;
      paramInt2 = 0;
    }
    if (paramInt1 + paramInt3 > this.width)
    {
      paramInt3 = this.width - paramInt1;
      m = 1;
    }
    if (paramInt2 + paramInt4 > this.height)
    {
      paramInt4 = this.height - paramInt2;
      m = 1;
    }
    if (paramInt3 < 0) {
      paramInt3 = 0;
    }
    if (paramInt4 < 0) {
      paramInt4 = 0;
    }
    int i1 = this.format;
    if ((m != 0) && (this.format == 1)) {
      i1 = 2;
    }
    PImage localPImage = new PImage(i, j, i1);
    localPImage.parent = this.parent;
    if ((paramInt3 > 0) && (paramInt4 > 0)) {
      getImpl(paramInt1, paramInt2, paramInt3, paramInt4, localPImage, k, n);
    }
    return localPImage;
  }
  
  protected void getImpl(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PImage paramPImage, int paramInt5, int paramInt6)
  {
    if (this.pixels == null) {
      this.bitmap.getPixels(paramPImage.pixels, paramInt5 + paramInt6 * paramPImage.width, paramPImage.width, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    for (;;)
    {
      return;
      int i = paramInt1 + paramInt2 * this.width;
      int j = paramInt5 + paramInt6 * paramPImage.width;
      for (int k = 0; k < paramInt4; k++)
      {
        System.arraycopy(this.pixels, i, paramPImage.pixels, j, paramInt3);
        i += this.width;
        j += paramPImage.width;
      }
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
  
  public Object getNative()
  {
    return this.bitmap;
  }
  
  public void init(int paramInt1, int paramInt2, int paramInt3)
  {
    this.width = paramInt1;
    this.height = paramInt2;
    this.pixels = new int[paramInt1 * paramInt2];
    this.format = paramInt3;
  }
  
  public boolean isModified()
  {
    return this.modified;
  }
  
  public void loadPixels()
  {
    if ((this.pixels == null) || (this.pixels.length != this.width * this.height)) {
      this.pixels = new int[this.width * this.height];
    }
    if (this.bitmap != null) {
      this.bitmap.getPixels(this.pixels, 0, this.width, 0, 0, this.width, this.height);
    }
    if (this.parent == null) {}
    do
    {
      Object localObject;
      do
      {
        return;
        localObject = this.parent.g.initCache(this);
      } while (localObject == null);
      try
      {
        Method localMethod2 = localObject.getClass().getMethod("loadPixels", new Class[] { [I.class });
        localMethod1 = localMethod2;
      }
      catch (Exception localException1)
      {
        for (;;)
        {
          Object[] arrayOfObject;
          Method localMethod1 = null;
        }
      }
    } while (localMethod1 == null);
    try
    {
      arrayOfObject = new Object[1];
      arrayOfObject[0] = this.pixels;
      localMethod1.invoke(localObject, arrayOfObject);
      return;
    }
    catch (Exception localException2)
    {
      localException2.printStackTrace();
      return;
    }
  }
  
  public void mask(PImage paramPImage)
  {
    if (paramPImage.pixels == null)
    {
      paramPImage.loadPixels();
      mask(paramPImage.pixels);
      paramPImage.pixels = null;
      return;
    }
    mask(paramPImage.pixels);
  }
  
  public void mask(int[] paramArrayOfInt)
  {
    loadPixels();
    if (paramArrayOfInt.length != this.pixels.length) {
      throw new RuntimeException("The PImage used with mask() must be the same size as the applet.");
    }
    for (int i = 0;; i++)
    {
      if (i >= this.pixels.length)
      {
        this.format = 2;
        updatePixels();
        return;
      }
      this.pixels[i] = ((0xFF & paramArrayOfInt[i]) << 24 | 0xFFFFFF & this.pixels[i]);
    }
  }
  
  public void resize(int paramInt1, int paramInt2)
  {
    if ((paramInt1 <= 0) && (paramInt2 <= 0)) {
      throw new IllegalArgumentException("width or height must be > 0 for resize");
    }
    if (paramInt1 == 0) {
      paramInt1 = (int)(paramInt2 / this.height * this.width);
    }
    for (;;)
    {
      this.bitmap = Bitmap.createScaledBitmap(this.bitmap, paramInt1, paramInt2, true);
      this.width = paramInt1;
      this.height = paramInt2;
      updatePixels();
      return;
      if (paramInt2 == 0) {
        paramInt2 = (int)(paramInt1 / this.width * this.height);
      }
    }
  }
  
  public boolean save(String paramString)
  {
    boolean bool1 = false;
    loadPixels();
    for (;;)
    {
      try
      {
        localBufferedOutputStream = new BufferedOutputStream(this.parent.createOutput(paramString), 16384);
        String str1 = paramString.toLowerCase();
        str2 = str1.substring(1 + str1.lastIndexOf('.'));
        if ((!str2.equals("jpg")) && (!str2.equals("jpeg"))) {
          continue;
        }
        bool1 = Bitmap.createBitmap(this.pixels, this.width, this.height, Bitmap.Config.ARGB_8888).compress(Bitmap.CompressFormat.JPEG, 100, localBufferedOutputStream);
        localBufferedOutputStream.flush();
        localBufferedOutputStream.close();
      }
      catch (IOException localIOException)
      {
        BufferedOutputStream localBufferedOutputStream;
        String str2;
        boolean bool2;
        boolean bool3;
        boolean bool5;
        boolean bool4;
        localIOException.printStackTrace();
        continue;
      }
      if (!bool1) {
        System.err.println("Could not write the image to " + paramString);
      }
      return bool1;
      bool2 = str2.equals("png");
      bool1 = false;
      if (bool2)
      {
        bool1 = Bitmap.createBitmap(this.pixels, this.width, this.height, Bitmap.Config.ARGB_8888).compress(Bitmap.CompressFormat.PNG, 100, localBufferedOutputStream);
      }
      else
      {
        bool3 = str2.equals("tga");
        bool1 = false;
        if (bool3)
        {
          bool1 = saveTGA(localBufferedOutputStream);
        }
        else
        {
          if (!str2.equals("tif"))
          {
            bool5 = str2.equals("tiff");
            bool1 = false;
            if (!bool5) {
              paramString = paramString + ".tif";
            }
          }
          bool4 = saveTIFF(localBufferedOutputStream);
          bool1 = bool4;
        }
      }
    }
  }
  
  protected boolean saveTGA(OutputStream paramOutputStream)
  {
    byte[] arrayOfByte = new byte[18];
    if (this.format == 4)
    {
      arrayOfByte[2] = 11;
      arrayOfByte[16] = 8;
      arrayOfByte[17] = 40;
    }
    int i;
    int k;
    int m;
    for (;;)
    {
      arrayOfByte[12] = ((byte)(0xFF & this.width));
      arrayOfByte[13] = ((byte)(this.width >> 8));
      arrayOfByte[14] = ((byte)(0xFF & this.height));
      arrayOfByte[15] = ((byte)(this.height >> 8));
      try
      {
        paramOutputStream.write(arrayOfByte);
        i = this.height * this.width;
        arrayOfInt = new int[''];
        int j = this.format;
        k = 0;
        if (j != 4) {
          break label830;
        }
        m = 0;
      }
      catch (IOException localIOException)
      {
        int[] arrayOfInt;
        int i3;
        localIOException.printStackTrace();
        return false;
      }
      paramOutputStream.flush();
      return true;
      if (this.format == 1)
      {
        arrayOfByte[2] = 10;
        arrayOfByte[16] = 24;
        arrayOfByte[17] = 32;
      }
      else
      {
        if (this.format != 2) {
          break;
        }
        arrayOfByte[2] = 10;
        arrayOfByte[16] = 32;
        arrayOfByte[17] = 40;
      }
    }
    throw new RuntimeException("Image format not recognized inside save()");
    int n = 1;
    int i1 = 0xFF & this.pixels[m];
    arrayOfInt[0] = i1;
    break label719;
    label222:
    int i2;
    label248:
    int i4;
    label278:
    label311:
    label345:
    label362:
    int i6;
    label383:
    int i7;
    if (i2 != 0)
    {
      paramOutputStream.write(0x80 | n - 1);
      paramOutputStream.write(i1);
      break label735;
      if (i1 == (0xFF & this.pixels[(m + n)]))
      {
        if (n != 128) {
          break label763;
        }
        break label745;
        paramOutputStream.write(n - 1);
        i3 = 0;
        while (i3 < n)
        {
          paramOutputStream.write(arrayOfInt[i3]);
          i3++;
          continue;
          i4 = 0xFF & this.pixels[(m + n)];
          if ((i1 == i4) || (n >= 128)) {
            break label785;
          }
          i1 = i4;
          arrayOfInt[n] = i4;
          n++;
          break label772;
          int i5 = this.pixels[k];
          arrayOfInt[0] = i5;
          i6 = 1;
          break label807;
          if (i7 == 0) {
            break label864;
          }
          paramOutputStream.write(0x80 | i6 - 1);
          paramOutputStream.write(i5 & 0xFF);
          paramOutputStream.write(0xFF & i5 >> 8);
          paramOutputStream.write(0xFF & i5 >> 16);
          if (this.format != 2) {
            break label823;
          }
          paramOutputStream.write(0xFF & i5 >>> 24);
          break label823;
          label460:
          if (i5 != this.pixels[(k + i6)]) {
            break label840;
          }
          if (i6 != 128) {
            break label858;
          }
          break label840;
          for (;;)
          {
            label485:
            label486:
            paramOutputStream.write(i6 - 1);
            if (this.format != 2) {
              break label889;
            }
            for (int i8 = 0; i8 < i6; i8++)
            {
              int i9 = arrayOfInt[i8];
              paramOutputStream.write(i9 & 0xFF);
              paramOutputStream.write(0xFF & i9 >> 8);
              paramOutputStream.write(0xFF & i9 >> 16);
              paramOutputStream.write(0xFF & i9 >>> 24);
            }
            label574:
            if ((i5 == this.pixels[(k + i6)]) || (i6 >= 128)) {
              break label880;
            }
            label600:
            i5 = this.pixels[(k + i6)];
            arrayOfInt[i6] = i5;
            i6++;
            break label867;
            if (i5 == this.pixels[(k + i6)]) {
              i6 -= 2;
            }
          }
        }
      }
    }
    for (;;)
    {
      if (i10 < i6)
      {
        int i11 = arrayOfInt[i10];
        paramOutputStream.write(i11 & 0xFF);
        paramOutputStream.write(0xFF & i11 >> 8);
        paramOutputStream.write(0xFF & i11 >> 16);
        i10++;
        continue;
        label709:
        if (m >= i) {
          break;
        }
        for (;;)
        {
          label719:
          if (m + n < i) {
            break label767;
          }
          i2 = 0;
          break label222;
          label735:
          m += n;
          break label709;
          break;
          label745:
          if (n > 1) {}
          for (i2 = 1;; i2 = 0) {
            break;
          }
          label763:
          n++;
        }
        label767:
        break label248;
        n = 1;
        label772:
        if (m + n < i) {
          break label311;
        }
        break label278;
        label785:
        if (n < 3) {
          break label345;
        }
        if (i1 != i4) {
          break label278;
        }
        n -= 2;
        break label278;
      }
      for (;;)
      {
        label807:
        if (k + i6 < i) {
          break label862;
        }
        i7 = 0;
        break label383;
        label823:
        k += i6;
        label830:
        if (k < i) {
          break label362;
        }
        break;
        label840:
        if (i6 > 1) {}
        for (i7 = 1;; i7 = 0) {
          break;
        }
        label858:
        i6++;
      }
      label862:
      break label460;
      label864:
      i6 = 1;
      label867:
      if (k + i6 < i) {
        break label574;
      }
      break label486;
      label880:
      if (i6 >= 3) {
        break label485;
      }
      break label600;
      label889:
      int i10 = 0;
    }
  }
  
  protected boolean saveTIFF(OutputStream paramOutputStream)
  {
    try
    {
      byte[] arrayOfByte = new byte[768];
      System.arraycopy(TIFF_HEADER, 0, arrayOfByte, 0, TIFF_HEADER.length);
      arrayOfByte[30] = ((byte)(0xFF & this.width >> 8));
      arrayOfByte[31] = ((byte)(0xFF & this.width));
      int i = (byte)(0xFF & this.height >> 8);
      arrayOfByte[102] = i;
      arrayOfByte[42] = i;
      int j = (byte)(0xFF & this.height);
      arrayOfByte[103] = j;
      arrayOfByte[43] = j;
      int k = 3 * (this.width * this.height);
      arrayOfByte[114] = ((byte)(0xFF & k >> 24));
      arrayOfByte[115] = ((byte)(0xFF & k >> 16));
      arrayOfByte[116] = ((byte)(0xFF & k >> 8));
      arrayOfByte[117] = ((byte)(k & 0xFF));
      paramOutputStream.write(arrayOfByte);
      for (int m = 0;; m++)
      {
        if (m >= this.pixels.length)
        {
          paramOutputStream.flush();
          return true;
        }
        paramOutputStream.write(0xFF & this.pixels[m] >> 16);
        paramOutputStream.write(0xFF & this.pixels[m] >> 8);
        paramOutputStream.write(0xFF & this.pixels[m]);
      }
      return false;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public void set(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.pixels == null) {
      this.bitmap.setPixel(paramInt1, paramInt2, paramInt3);
    }
    while ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
      return;
    }
    this.pixels[(paramInt1 + paramInt2 * this.width)] = paramInt3;
    updatePixelsImpl(paramInt1, paramInt2, 1, 1);
  }
  
  public void set(int paramInt1, int paramInt2, PImage paramPImage)
  {
    if (paramPImage.format == 4) {
      throw new IllegalArgumentException("set() not available for ALPHA images");
    }
    int i = paramPImage.width;
    int j = paramPImage.height;
    int k = 0;
    if (paramInt1 < 0)
    {
      k = 0 - paramInt1;
      i += paramInt1;
      paramInt1 = 0;
    }
    int m = 0;
    if (paramInt2 < 0)
    {
      m = 0 - paramInt2;
      j += paramInt2;
      paramInt2 = 0;
    }
    if (paramInt1 + i > this.width) {
      i = this.width - paramInt1;
    }
    if (paramInt2 + j > this.height) {
      j = this.height - paramInt2;
    }
    if ((i <= 0) || (j <= 0)) {
      return;
    }
    setImpl(paramPImage, k, m, i, j, paramInt1, paramInt2);
  }
  
  protected void setImpl(PImage paramPImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if (paramPImage.pixels == null) {
      paramPImage.loadPixels();
    }
    if (this.pixels == null)
    {
      if (!this.bitmap.isMutable()) {
        this.bitmap = this.bitmap.copy(Bitmap.Config.ARGB_8888, true);
      }
      int m = paramInt1 + paramInt2 * paramPImage.width;
      this.bitmap.setPixels(paramPImage.pixels, m, paramPImage.width, paramInt5, paramInt6, paramInt3, paramInt4);
      return;
    }
    int i = paramInt1 + paramInt2 * paramPImage.width;
    int j = paramInt5 + paramInt6 * this.width;
    for (int k = paramInt2;; k++)
    {
      if (k >= paramInt2 + paramInt4)
      {
        updatePixelsImpl(paramInt5, paramInt6, paramInt3, paramInt4);
        return;
      }
      System.arraycopy(paramPImage.pixels, i, this.pixels, j, paramInt3);
      i += paramPImage.width;
      j += this.width;
    }
  }
  
  public void setModified()
  {
    this.modified = true;
  }
  
  public void setModified(boolean paramBoolean)
  {
    this.modified = paramBoolean;
  }
  
  public void updatePixels()
  {
    updatePixelsImpl(0, 0, this.width, this.height);
  }
  
  public void updatePixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    updatePixelsImpl(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void updatePixelsImpl(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
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
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PImage
 * JD-Core Version:    0.7.0.1
 */