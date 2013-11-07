package processing.opengl;

import processing.core.PMatrix2D;

public class LinePath
{
  public static final int CAP_BUTT = 0;
  public static final int CAP_ROUND = 1;
  public static final int CAP_SQUARE = 2;
  static final int EXPAND_MAX = 500;
  static final int INIT_SIZE = 20;
  public static final int JOIN_BEVEL = 2;
  public static final int JOIN_MITER = 0;
  public static final int JOIN_ROUND = 1;
  public static final byte SEG_CLOSE = 2;
  public static final byte SEG_LINETO = 1;
  public static final byte SEG_MOVETO = 0;
  public static final int WIND_EVEN_ODD = 0;
  public static final int WIND_NON_ZERO = 1;
  private static float defaultMiterlimit = 10.0F;
  private static PMatrix2D identity = new PMatrix2D();
  protected float[] floatCoords;
  protected int numCoords;
  protected int numTypes;
  protected int[] pointColors;
  protected byte[] pointTypes;
  protected int windingRule;
  
  public LinePath()
  {
    this(1, 20);
  }
  
  public LinePath(int paramInt)
  {
    this(paramInt, 20);
  }
  
  public LinePath(int paramInt1, int paramInt2)
  {
    setWindingRule(paramInt1);
    this.pointTypes = new byte[paramInt2];
    this.floatCoords = new float[paramInt2 * 2];
    this.pointColors = new int[paramInt2];
  }
  
  static int FloatToS15_16(float paramFloat)
  {
    float f = 0.5F + 65536.0F * paramFloat;
    if (f <= -4.294967E+009F) {
      return -2147483648;
    }
    if (f >= 4.294967E+009F) {
      return 2147483647;
    }
    return (int)Math.floor(f);
  }
  
  static float S15_16ToFloat(int paramInt)
  {
    return paramInt / 65536.0F;
  }
  
  public static byte[] copyOf(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    int i = 0;
    if (i < arrayOfByte.length)
    {
      if (i > -1 + paramArrayOfByte.length) {
        arrayOfByte[i] = 0;
      }
      for (;;)
      {
        i++;
        break;
        arrayOfByte[i] = paramArrayOfByte[i];
      }
    }
    return arrayOfByte;
  }
  
  public static float[] copyOf(float[] paramArrayOfFloat, int paramInt)
  {
    float[] arrayOfFloat = new float[paramInt];
    int i = 0;
    if (i < arrayOfFloat.length)
    {
      if (i > -1 + paramArrayOfFloat.length) {
        arrayOfFloat[i] = 0.0F;
      }
      for (;;)
      {
        i++;
        break;
        arrayOfFloat[i] = paramArrayOfFloat[i];
      }
    }
    return arrayOfFloat;
  }
  
  public static int[] copyOf(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = new int[paramInt];
    int i = 0;
    if (i < arrayOfInt.length)
    {
      if (i > -1 + paramArrayOfInt.length) {
        arrayOfInt[i] = 0;
      }
      for (;;)
      {
        i++;
        break;
        arrayOfInt[i] = paramArrayOfInt[i];
      }
    }
    return arrayOfInt;
  }
  
  public static LinePath createStrokedPath(LinePath paramLinePath, float paramFloat, int paramInt1, int paramInt2)
  {
    return createStrokedPath(paramLinePath, paramFloat, paramInt1, paramInt2, defaultMiterlimit, null);
  }
  
  public static LinePath createStrokedPath(LinePath paramLinePath, float paramFloat1, int paramInt1, int paramInt2, float paramFloat2)
  {
    return createStrokedPath(paramLinePath, paramFloat1, paramInt1, paramInt2, paramFloat2, null);
  }
  
  public static LinePath createStrokedPath(LinePath paramLinePath, float paramFloat1, int paramInt1, int paramInt2, float paramFloat2, PMatrix2D paramPMatrix2D)
  {
    LinePath localLinePath = new LinePath();
    strokeTo(paramLinePath, paramFloat1, paramInt1, paramInt2, paramFloat2, paramPMatrix2D, new LineStroker()
    {
      public void close()
      {
        this.val$dest.closePath();
      }
      
      public void end() {}
      
      public void lineJoin() {}
      
      public void lineTo(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        this.val$dest.lineTo(LinePath.S15_16ToFloat(paramAnonymousInt1), LinePath.S15_16ToFloat(paramAnonymousInt2), paramAnonymousInt3);
      }
      
      public void moveTo(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        this.val$dest.moveTo(LinePath.S15_16ToFloat(paramAnonymousInt1), LinePath.S15_16ToFloat(paramAnonymousInt2), paramAnonymousInt3);
      }
    });
    return localLinePath;
  }
  
  public static double hypot(double paramDouble1, double paramDouble2)
  {
    return Math.sqrt(paramDouble1 * paramDouble1 + paramDouble2 * paramDouble2);
  }
  
  public static int hypot(int paramInt1, int paramInt2)
  {
    return (int)(128L + lsqrt(paramInt1 * paramInt1 + paramInt2 * paramInt2) >> 8);
  }
  
  public static long hypot(long paramLong1, long paramLong2)
  {
    return 128L + lsqrt(paramLong1 * paramLong1 + paramLong2 * paramLong2) >> 8;
  }
  
  public static int isqrt(int paramInt)
  {
    int i = 0;
    int j = 23;
    int k = 0;
    for (;;)
    {
      k = k << 2 | paramInt >>> 30;
      paramInt <<= 2;
      i <<= 1;
      int m = 1 + (i << 1);
      if (k >= m)
      {
        k -= m;
        i++;
      }
      int n = j - 1;
      if (j == 0) {
        return i;
      }
      j = n;
    }
  }
  
  public static long lsqrt(long paramLong)
  {
    long l1 = 0L;
    long l2 = 0L;
    int j;
    for (int i = 39;; i = j)
    {
      l1 = l1 << 2 | paramLong >>> 62;
      paramLong <<= 2;
      l2 <<= 1;
      long l3 = 1L + (l2 << 1);
      if (l1 >= l3)
      {
        l1 -= l3;
        l2 += 1L;
      }
      j = i - 1;
      if (i == 0) {
        return l2;
      }
    }
  }
  
  private static void pathTo(PathIterator paramPathIterator, LineStroker paramLineStroker)
  {
    float[] arrayOfFloat = new float[6];
    if (!paramPathIterator.isDone())
    {
      switch (paramPathIterator.currentSegment(arrayOfFloat))
      {
      default: 
        throw new InternalError("unknown flattened segment type");
      case 0: 
        int j = (int)arrayOfFloat[2] << 24 | (int)arrayOfFloat[3] << 16 | (int)arrayOfFloat[4] << 8 | (int)arrayOfFloat[5];
        paramLineStroker.moveTo(FloatToS15_16(arrayOfFloat[0]), FloatToS15_16(arrayOfFloat[1]), j);
      }
      for (;;)
      {
        paramPathIterator.next();
        break;
        int i = (int)arrayOfFloat[2] << 24 | (int)arrayOfFloat[3] << 16 | (int)arrayOfFloat[4] << 8 | (int)arrayOfFloat[5];
        paramLineStroker.lineJoin();
        paramLineStroker.lineTo(FloatToS15_16(arrayOfFloat[0]), FloatToS15_16(arrayOfFloat[1]), i);
        continue;
        paramLineStroker.lineJoin();
        paramLineStroker.close();
      }
    }
    paramLineStroker.end();
  }
  
  private static void strokeTo(LinePath paramLinePath, float paramFloat1, int paramInt1, int paramInt2, float paramFloat2, PMatrix2D paramPMatrix2D, LineStroker paramLineStroker)
  {
    int i = FloatToS15_16(paramFloat1);
    int j = FloatToS15_16(paramFloat2);
    if (paramPMatrix2D == null) {}
    for (PMatrix2D localPMatrix2D = identity;; localPMatrix2D = paramPMatrix2D)
    {
      LineStroker localLineStroker = new LineStroker(paramLineStroker, i, paramInt1, paramInt2, j, localPMatrix2D);
      pathTo(paramLinePath.getPathIterator(), localLineStroker);
      return;
    }
  }
  
  public final void closePath()
  {
    if ((this.numTypes == 0) || (this.pointTypes[(-1 + this.numTypes)] != 2))
    {
      needRoom(false, 0);
      byte[] arrayOfByte = this.pointTypes;
      int i = this.numTypes;
      this.numTypes = (i + 1);
      arrayOfByte[i] = 2;
    }
  }
  
  public PathIterator getPathIterator()
  {
    return new PathIterator(this);
  }
  
  public final int getWindingRule()
  {
    return this.windingRule;
  }
  
  public final void lineTo(float paramFloat1, float paramFloat2, int paramInt)
  {
    needRoom(true, 1);
    byte[] arrayOfByte = this.pointTypes;
    int i = this.numTypes;
    this.numTypes = (i + 1);
    arrayOfByte[i] = 1;
    float[] arrayOfFloat1 = this.floatCoords;
    int j = this.numCoords;
    this.numCoords = (j + 1);
    arrayOfFloat1[j] = paramFloat1;
    float[] arrayOfFloat2 = this.floatCoords;
    int k = this.numCoords;
    this.numCoords = (k + 1);
    arrayOfFloat2[k] = paramFloat2;
    this.pointColors[(-1 + this.numCoords / 2)] = paramInt;
  }
  
  public final void moveTo(float paramFloat1, float paramFloat2, int paramInt)
  {
    if ((this.numTypes > 0) && (this.pointTypes[(-1 + this.numTypes)] == 0))
    {
      this.floatCoords[(-2 + this.numCoords)] = paramFloat1;
      this.floatCoords[(-1 + this.numCoords)] = paramFloat2;
      this.pointColors[(this.numCoords / 2)] = paramInt;
      return;
    }
    needRoom(false, 1);
    byte[] arrayOfByte = this.pointTypes;
    int i = this.numTypes;
    this.numTypes = (i + 1);
    arrayOfByte[i] = 0;
    float[] arrayOfFloat1 = this.floatCoords;
    int j = this.numCoords;
    this.numCoords = (j + 1);
    arrayOfFloat1[j] = paramFloat1;
    float[] arrayOfFloat2 = this.floatCoords;
    int k = this.numCoords;
    this.numCoords = (k + 1);
    arrayOfFloat2[k] = paramFloat2;
    this.pointColors[(-1 + this.numCoords / 2)] = paramInt;
  }
  
  void needRoom(boolean paramBoolean, int paramInt)
  {
    if ((paramBoolean) && (this.numTypes == 0)) {
      throw new RuntimeException("missing initial moveto in path definition");
    }
    int i = this.pointTypes.length;
    if (this.numTypes >= i) {
      if (i <= 500) {
        break label197;
      }
    }
    label183:
    label190:
    label197:
    for (int i1 = 500;; i1 = i)
    {
      this.pointTypes = copyOf(this.pointTypes, i1 + i);
      int j = this.floatCoords.length;
      if (this.numCoords + paramInt * 2 > j) {
        if (j <= 1000) {
          break label190;
        }
      }
      for (int n = 1000;; n = j)
      {
        if (n < paramInt * 2) {
          n = paramInt * 2;
        }
        this.floatCoords = copyOf(this.floatCoords, n + j);
        int k = this.pointColors.length;
        if (paramInt + this.numCoords > k) {
          if (k <= 500) {
            break label183;
          }
        }
        for (int m = 500;; m = k)
        {
          if (m < paramInt) {}
          for (;;)
          {
            this.pointColors = copyOf(this.pointColors, k + paramInt);
            return;
            paramInt = m;
          }
        }
      }
    }
  }
  
  public final void reset()
  {
    this.numCoords = 0;
    this.numTypes = 0;
  }
  
  public final void setWindingRule(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 1)) {
      throw new IllegalArgumentException("winding rule must be WIND_EVEN_ODD or WIND_NON_ZERO");
    }
    this.windingRule = paramInt;
  }
  
  public static class PathIterator
  {
    static final int[] curvecoords = { 2, 2, 0 };
    int colorIdx;
    float[] floatCoords;
    LinePath path;
    int pointIdx;
    int typeIdx;
    
    PathIterator(LinePath paramLinePath)
    {
      this.path = paramLinePath;
      this.floatCoords = paramLinePath.floatCoords;
    }
    
    public int currentSegment(double[] paramArrayOfDouble)
    {
      int i = this.path.pointTypes[this.typeIdx];
      int j = curvecoords[i];
      if (j > 0)
      {
        for (int k = 0; k < j; k++) {
          paramArrayOfDouble[k] = this.floatCoords[(k + this.pointIdx)];
        }
        int m = this.path.pointColors[this.colorIdx];
        paramArrayOfDouble[(j + 0)] = (0xFF & m >> 24);
        paramArrayOfDouble[(j + 1)] = (0xFF & m >> 16);
        paramArrayOfDouble[(j + 2)] = (0xFF & m >> 8);
        paramArrayOfDouble[(j + 3)] = (0xFF & m >> 0);
      }
      return i;
    }
    
    public int currentSegment(float[] paramArrayOfFloat)
    {
      int i = this.path.pointTypes[this.typeIdx];
      int j = curvecoords[i];
      if (j > 0)
      {
        System.arraycopy(this.floatCoords, this.pointIdx, paramArrayOfFloat, 0, j);
        int k = this.path.pointColors[this.colorIdx];
        paramArrayOfFloat[(j + 0)] = (0xFF & k >> 24);
        paramArrayOfFloat[(j + 1)] = (0xFF & k >> 16);
        paramArrayOfFloat[(j + 2)] = (0xFF & k >> 8);
        paramArrayOfFloat[(j + 3)] = (0xFF & k >> 0);
      }
      return i;
    }
    
    public int getWindingRule()
    {
      return this.path.getWindingRule();
    }
    
    public boolean isDone()
    {
      return this.typeIdx >= this.path.numTypes;
    }
    
    public void next()
    {
      byte[] arrayOfByte = this.path.pointTypes;
      int i = this.typeIdx;
      this.typeIdx = (i + 1);
      int j = arrayOfByte[i];
      this.pointIdx += curvecoords[j];
      this.colorIdx = (1 + this.colorIdx);
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.LinePath
 * JD-Core Version:    0.7.0.1
 */