package processing.opengl;

import processing.core.PMatrix2D;

public class LineStroker
{
  private static final long ROUND_JOIN_INTERNAL_THRESHOLD = 1000000000L;
  private static final long ROUND_JOIN_THRESHOLD = 1000L;
  private int capStyle;
  private int color0;
  private int[] join;
  boolean joinSegment = false;
  private int joinStyle;
  private boolean joinToOrigin;
  private boolean lineToOrigin;
  private int lineWidth2;
  private int m00;
  private double m00_2_m01_2;
  private double m00_m10_m01_m11;
  private int m01;
  private int m10;
  private double m10_2_m11_2;
  private int m11;
  private int[] miter = new int[2];
  private long miterLimitSq;
  private int mx0;
  private int my0;
  private int numPenSegments;
  private int[] offset = new int[2];
  private int omx;
  private int omy;
  private LineStroker output;
  private int pcolor0;
  private boolean[] penIncluded;
  private int[] pen_dx;
  private int[] pen_dy;
  private int prev;
  private int px0;
  private int py0;
  private int[] reverse = new int[100];
  private int rindex;
  private long scaledLineWidth2;
  private int scolor0;
  private boolean started;
  private int sx0;
  private int sx1;
  private int sy0;
  private int sy1;
  private int x0;
  private int y0;
  
  public LineStroker() {}
  
  public LineStroker(LineStroker paramLineStroker, int paramInt1, int paramInt2, int paramInt3, int paramInt4, PMatrix2D paramPMatrix2D)
  {
    setOutput(paramLineStroker);
    setParameters(paramInt1, paramInt2, paramInt3, paramInt4, paramPMatrix2D);
  }
  
  private void computeMiter(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int[] paramArrayOfInt)
  {
    long l1 = paramInt1;
    long l2 = paramInt2;
    long l3 = paramInt3;
    long l4 = paramInt4;
    long l5 = paramInt5;
    long l6 = paramInt6;
    long l7 = paramInt7;
    long l8 = paramInt8;
    long l9 = l3 - l1;
    long l10 = l4 - l2;
    long l11 = l7 - l5;
    long l12 = l8 - l6;
    long l13 = l9 * l12 - l11 * l10 >> 16;
    if (l13 == 0L)
    {
      paramArrayOfInt[0] = paramInt1;
      paramArrayOfInt[1] = paramInt2;
      return;
    }
    long l14 = l7 * (l2 - l6) - l1 * l12 + l5 * (l8 - l2) >> 16;
    paramArrayOfInt[0] = ((int)(l1 + l9 * l14 / l13));
    paramArrayOfInt[1] = ((int)(l2 + l14 * l10 / l13));
  }
  
  private void computeOffset(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt)
  {
    long l1 = paramInt3 - paramInt1;
    long l2 = paramInt4 - paramInt2;
    label53:
    int k;
    int j;
    if ((this.m00 > 0) && (this.m00 == this.m11))
    {
      int m;
      int n;
      long l3;
      if (this.m01 == 0)
      {
        m = 1;
        if (this.m10 != 0) {
          break label102;
        }
        n = 1;
        if ((m & n) == 0) {
          break label138;
        }
        l3 = LinePath.hypot(l1, l2);
        if (l3 != 0L) {
          break label108;
        }
        k = 0;
        j = 0;
      }
      for (;;)
      {
        paramArrayOfInt[0] = j;
        paramArrayOfInt[1] = k;
        return;
        m = 0;
        break;
        label102:
        n = 0;
        break label53;
        label108:
        j = (int)(l2 * this.scaledLineWidth2 / l3);
        k = (int)(-(l1 * this.scaledLineWidth2) / l3);
      }
    }
    label138:
    double d1 = paramInt3 - paramInt1;
    double d2 = paramInt4 - paramInt2;
    if (this.m00 * this.m11 - this.m01 * this.m10 > 0.0D) {}
    for (int i = 1;; i = -1)
    {
      double d3 = LinePath.hypot(d2 * this.m00 - d1 * this.m10, d2 * this.m01 - d1 * this.m11);
      double d4 = i * this.lineWidth2 / (d3 * 65536.0D);
      double d5 = d2 * this.m00_2_m01_2 - d1 * this.m00_m10_m01_m11;
      double d6 = d2 * this.m00_m10_m01_m11 - d1 * this.m10_2_m11_2;
      j = (int)(d5 * d4);
      k = (int)(d6 * d4);
      break;
    }
  }
  
  private int computeRoundJoin(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean, int[] paramArrayOfInt)
  {
    boolean bool2;
    int i;
    if (paramInt7 == 0)
    {
      bool2 = side(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
      i = 0;
      label23:
      if (i >= this.numPenSegments) {
        break label113;
      }
      if (side(paramInt1 + this.pen_dx[i], paramInt2 + this.pen_dy[i], paramInt3, paramInt4, paramInt5, paramInt6) == bool2) {
        break label102;
      }
      this.penIncluded[i] = true;
    }
    for (;;)
    {
      i++;
      break label23;
      if (paramInt7 == 1) {}
      for (boolean bool1 = true;; bool1 = false)
      {
        bool2 = bool1;
        break;
      }
      label102:
      this.penIncluded[i] = false;
    }
    label113:
    int j = -1;
    int k = -1;
    for (int m = 0; m < this.numPenSegments; m++)
    {
      if ((this.penIncluded[m] != 0) && (this.penIncluded[((-1 + (m + this.numPenSegments)) % this.numPenSegments)] == 0)) {
        j = m;
      }
      if ((this.penIncluded[m] != 0) && (this.penIncluded[((m + 1) % this.numPenSegments)] == 0)) {
        k = m;
      }
    }
    if (k < j) {}
    for (int n = k + this.numPenSegments;; n = k)
    {
      int i1 = 0;
      int i2;
      int i3;
      label335:
      int i4;
      label343:
      int i5;
      if (j != -1)
      {
        i1 = 0;
        if (n != -1)
        {
          long l1 = paramInt1 + this.pen_dx[j] - paramInt3;
          long l2 = paramInt2 + this.pen_dy[j] - paramInt4;
          long l3 = paramInt1 + this.pen_dx[j] - paramInt5;
          long l4 = paramInt2 + this.pen_dy[j] - paramInt6;
          if (l1 * l1 + l2 * l2 <= l3 * l3 + l4 * l4) {
            break label428;
          }
          i2 = 1;
          if (i2 == 0) {
            break label434;
          }
          i3 = n;
          if (i2 == 0) {
            break label441;
          }
          i4 = -1;
          i5 = 0;
        }
      }
      for (;;)
      {
        int i6 = i3 % this.numPenSegments;
        int i7 = paramInt1 + this.pen_dx[i6];
        int i8 = paramInt2 + this.pen_dy[i6];
        int i9 = i5 + 1;
        paramArrayOfInt[i5] = i7;
        i5 = i9 + 1;
        paramArrayOfInt[i9] = i8;
        if (i2 != 0) {}
        for (int i10 = j;; i10 = n)
        {
          if (i3 != i10) {
            break label454;
          }
          i1 = i5;
          return i1 / 2;
          label428:
          i2 = 0;
          break;
          label434:
          i3 = j;
          break label335;
          label441:
          i4 = 1;
          break label343;
        }
        label454:
        i3 += i4;
      }
    }
  }
  
  private void drawMiter(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, boolean paramBoolean)
  {
    if ((paramInt9 == paramInt7) && (paramInt10 == paramInt8)) {}
    long l3;
    long l4;
    do
    {
      do
      {
        return;
      } while (((paramInt1 == paramInt3) && (paramInt2 == paramInt4)) || ((paramInt3 == paramInt5) && (paramInt4 == paramInt6)));
      if (paramBoolean)
      {
        paramInt7 = -paramInt7;
        paramInt8 = -paramInt8;
        paramInt9 = -paramInt9;
        paramInt10 = -paramInt10;
      }
      computeMiter(paramInt1 + paramInt7, paramInt2 + paramInt8, paramInt3 + paramInt7, paramInt4 + paramInt8, paramInt3 + paramInt9, paramInt4 + paramInt10, paramInt5 + paramInt9, paramInt6 + paramInt10, this.miter);
      long l1 = this.miter[0] - paramInt3;
      long l2 = this.miter[1] - paramInt4;
      l3 = l2 * this.m00 - l1 * this.m10 >> 16;
      l4 = l2 * this.m01 - l1 * this.m11 >> 16;
    } while (l3 * l3 + l4 * l4 >= this.miterLimitSq);
    emitLineTo(this.miter[0], this.miter[1], paramInt11, paramBoolean);
  }
  
  private void drawRoundJoin(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean1, boolean paramBoolean2, long paramLong)
  {
    if (((paramInt3 == 0) && (paramInt4 == 0)) || ((paramInt5 == 0) && (paramInt6 == 0))) {}
    for (;;)
    {
      return;
      long l1 = paramInt3 - paramInt5;
      long l2 = paramInt4 - paramInt6;
      if (l1 * l1 + l2 * l2 >= paramLong)
      {
        if (paramBoolean2)
        {
          paramInt3 = -paramInt3;
          paramInt4 = -paramInt4;
          paramInt5 = -paramInt5;
          paramInt6 = -paramInt6;
        }
        int i = computeRoundJoin(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4, paramInt1 + paramInt5, paramInt2 + paramInt6, paramInt7, paramBoolean1, this.join);
        for (int j = 0; j < i; j++) {
          emitLineTo(this.join[(j * 2)], this.join[(1 + j * 2)], paramInt8, paramBoolean2);
        }
      }
    }
  }
  
  private void emitClose()
  {
    this.output.close();
  }
  
  private void emitLineTo(int paramInt1, int paramInt2, int paramInt3)
  {
    this.output.lineTo(paramInt1, paramInt2, paramInt3);
  }
  
  private void emitLineTo(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      ensureCapacity(3 + this.rindex);
      int[] arrayOfInt1 = this.reverse;
      int i = this.rindex;
      this.rindex = (i + 1);
      arrayOfInt1[i] = paramInt1;
      int[] arrayOfInt2 = this.reverse;
      int j = this.rindex;
      this.rindex = (j + 1);
      arrayOfInt2[j] = paramInt2;
      int[] arrayOfInt3 = this.reverse;
      int k = this.rindex;
      this.rindex = (k + 1);
      arrayOfInt3[k] = paramInt3;
      return;
    }
    emitLineTo(paramInt1, paramInt2, paramInt3);
  }
  
  private void emitMoveTo(int paramInt1, int paramInt2, int paramInt3)
  {
    this.output.moveTo(paramInt1, paramInt2, paramInt3);
  }
  
  private void ensureCapacity(int paramInt)
  {
    if (this.reverse.length < paramInt)
    {
      int[] arrayOfInt = new int[Math.max(paramInt, 6 * this.reverse.length / 5)];
      System.arraycopy(this.reverse, 0, arrayOfInt, 0, this.rindex);
      this.reverse = arrayOfInt;
    }
  }
  
  private void finish()
  {
    if (this.capStyle == 1) {
      drawRoundJoin(this.x0, this.y0, this.omx, this.omy, -this.omx, -this.omy, 1, this.color0, false, false, 1000L);
    }
    for (;;)
    {
      for (int k = -3 + this.rindex; k >= 0; k -= 3) {
        emitLineTo(this.reverse[k], this.reverse[(k + 1)], this.reverse[(k + 2)]);
      }
      if (this.capStyle == 2)
      {
        long l1 = this.px0 - this.x0;
        long l2 = this.py0 - this.y0;
        long l3 = lineLength(l1, l2);
        if (0L < l3)
        {
          long l4 = 65536L * this.lineWidth2 / l3;
          int i = this.x0 - (int)(l1 * l4 >> 16);
          int j = this.y0 - (int)(l2 * l4 >> 16);
          emitLineTo(i + this.omx, j + this.omy, this.color0);
          emitLineTo(i - this.omx, j - this.omy, this.color0);
        }
      }
    }
    this.rindex = 0;
    if (this.capStyle == 1) {
      drawRoundJoin(this.sx0, this.sy0, -this.mx0, -this.my0, this.mx0, this.my0, 1, this.scolor0, false, false, 1000L);
    }
    for (;;)
    {
      emitClose();
      this.joinSegment = false;
      return;
      if (this.capStyle == 2)
      {
        long l5 = this.sx1 - this.sx0;
        long l6 = this.sy1 - this.sy0;
        long l7 = lineLength(l5, l6);
        if (0L < l7)
        {
          long l8 = 65536L * this.lineWidth2 / l7;
          int m = this.sx0 - (int)(l5 * l8 >> 16);
          int n = this.sy0 - (int)(l6 * l8 >> 16);
          emitLineTo(m - this.mx0, n - this.my0, this.scolor0);
          emitLineTo(m + this.mx0, n + this.my0, this.scolor0);
        }
      }
    }
  }
  
  private boolean isCCW(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = paramInt3 - paramInt1;
    int j = paramInt4 - paramInt2;
    int k = paramInt5 - paramInt3;
    int m = paramInt6 - paramInt4;
    return i * m < j * k;
  }
  
  private void lineToImpl(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    computeOffset(this.x0, this.y0, paramInt1, paramInt2, this.offset);
    int i = this.offset[0];
    int j = this.offset[1];
    if (!this.started)
    {
      emitMoveTo(i + this.x0, j + this.y0, this.color0);
      this.sx1 = paramInt1;
      this.sy1 = paramInt2;
      this.mx0 = i;
      this.my0 = j;
      this.started = true;
      emitLineTo(i + this.x0, j + this.y0, this.color0, false);
      emitLineTo(paramInt1 + i, paramInt2 + j, paramInt3, false);
      emitLineTo(this.x0 - i, this.y0 - j, this.color0, true);
      emitLineTo(paramInt1 - i, paramInt2 - j, paramInt3, true);
      this.omx = i;
      this.omy = j;
      this.px0 = this.x0;
      this.py0 = this.y0;
      this.pcolor0 = this.color0;
      this.x0 = paramInt1;
      this.y0 = paramInt2;
      this.color0 = paramInt3;
      this.prev = 1;
      return;
    }
    boolean bool1 = isCCW(this.px0, this.py0, this.x0, this.y0, paramInt1, paramInt2);
    label297:
    int k;
    int m;
    int n;
    if (paramBoolean) {
      if (this.joinStyle == 0)
      {
        drawMiter(this.px0, this.py0, this.x0, this.y0, paramInt1, paramInt2, this.omx, this.omy, i, j, this.color0, bool1);
        k = this.x0;
        m = this.y0;
        n = this.color0;
        if (bool1) {
          break label422;
        }
      }
    }
    label422:
    for (boolean bool2 = true;; bool2 = false)
    {
      emitLineTo(k, m, n, bool2);
      break;
      if (this.joinStyle != 1) {
        break label297;
      }
      drawRoundJoin(this.x0, this.y0, this.omx, this.omy, i, j, 0, this.color0, false, bool1, 1000L);
      break label297;
      drawRoundJoin(this.x0, this.y0, this.omx, this.omy, i, j, 0, this.color0, false, bool1, 1000000000L);
      break label297;
    }
  }
  
  private boolean side(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    long l1 = paramInt1;
    long l2 = paramInt2;
    long l3 = paramInt3;
    long l4 = paramInt4;
    long l5 = paramInt5;
    long l6 = paramInt6;
    return l1 * (l4 - l6) + l2 * (l5 - l3) + (l3 * l6 - l5 * l4) > 0L;
  }
  
  public void close()
  {
    if (this.lineToOrigin) {
      this.lineToOrigin = false;
    }
    if (!this.started)
    {
      finish();
      return;
    }
    computeOffset(this.x0, this.y0, this.sx0, this.sy0, this.offset);
    int i = this.offset[0];
    int j = this.offset[1];
    boolean bool1 = isCCW(this.px0, this.py0, this.x0, this.y0, this.sx0, this.sy0);
    if (this.joinSegment) {
      if (this.joinStyle == 0)
      {
        drawMiter(this.px0, this.py0, this.x0, this.y0, this.sx0, this.sy0, this.omx, this.omy, i, j, this.pcolor0, bool1);
        emitLineTo(i + this.x0, j + this.y0, this.color0);
        emitLineTo(i + this.sx0, j + this.sy0, this.scolor0);
        boolean bool2 = isCCW(this.x0, this.y0, this.sx0, this.sy0, this.sx1, this.sy1);
        if (!bool2)
        {
          if (this.joinStyle != 0) {
            break label551;
          }
          drawMiter(this.x0, this.y0, this.sx0, this.sy0, this.sx1, this.sy1, i, j, this.mx0, this.my0, this.color0, false);
        }
        label273:
        emitLineTo(this.sx0 + this.mx0, this.sy0 + this.my0, this.scolor0);
        emitLineTo(this.sx0 - this.mx0, this.sy0 - this.my0, this.scolor0);
        if (bool2)
        {
          if (this.joinStyle != 0) {
            break label594;
          }
          drawMiter(this.x0, this.y0, this.sx0, this.sy0, this.sx1, this.sy1, -i, -j, -this.mx0, -this.my0, this.color0, false);
        }
      }
    }
    for (;;)
    {
      emitLineTo(this.sx0 - i, this.sy0 - j, this.scolor0);
      emitLineTo(this.x0 - i, this.y0 - j, this.color0);
      for (int k = -3 + this.rindex; k >= 0; k -= 3) {
        emitLineTo(this.reverse[k], this.reverse[(k + 1)], this.reverse[(k + 2)]);
      }
      if (this.joinStyle != 1) {
        break;
      }
      drawRoundJoin(this.x0, this.y0, this.omx, this.omy, i, j, 0, this.color0, false, bool1, 1000L);
      break;
      drawRoundJoin(this.x0, this.y0, this.omx, this.omy, i, j, 0, this.color0, false, bool1, 1000000000L);
      break;
      label551:
      if (this.joinStyle != 1) {
        break label273;
      }
      drawRoundJoin(this.sx0, this.sy0, i, j, this.mx0, this.my0, 0, this.scolor0, false, false, 1000L);
      break label273;
      label594:
      if (this.joinStyle == 1) {
        drawRoundJoin(this.sx0, this.sy0, -i, -j, -this.mx0, -this.my0, 0, this.scolor0, true, false, 1000L);
      }
    }
    this.x0 = this.sx0;
    this.y0 = this.sy0;
    this.rindex = 0;
    this.started = false;
    this.joinSegment = false;
    this.prev = 2;
    emitClose();
  }
  
  public void end()
  {
    if (this.lineToOrigin)
    {
      lineToImpl(this.sx0, this.sy0, this.scolor0, this.joinToOrigin);
      this.lineToOrigin = false;
    }
    if (this.prev == 1) {
      finish();
    }
    this.output.end();
    this.joinSegment = false;
    this.prev = 0;
  }
  
  public void lineJoin()
  {
    this.joinSegment = true;
  }
  
  long lineLength(long paramLong1, long paramLong2)
  {
    long l = this.m00 * this.m11 - this.m01 * this.m10 >> 16;
    return (int)LinePath.hypot((paramLong2 * this.m00 - paramLong1 * this.m10) / l, (paramLong2 * this.m01 - paramLong1 * this.m11) / l);
  }
  
  public void lineTo(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.lineToOrigin)
    {
      if ((paramInt1 == this.sx0) && (paramInt2 == this.sy0)) {
        return;
      }
      lineToImpl(this.sx0, this.sy0, this.scolor0, this.joinToOrigin);
      this.lineToOrigin = false;
    }
    do
    {
      lineToImpl(paramInt1, paramInt2, paramInt3, this.joinSegment);
      this.joinSegment = false;
      return;
      if ((paramInt1 == this.x0) && (paramInt2 == this.y0)) {
        break;
      }
    } while ((paramInt1 != this.sx0) || (paramInt2 != this.sy0));
    this.lineToOrigin = true;
    this.joinToOrigin = this.joinSegment;
    this.joinSegment = false;
  }
  
  public void moveTo(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.lineToOrigin)
    {
      lineToImpl(this.sx0, this.sy0, this.scolor0, this.joinToOrigin);
      this.lineToOrigin = false;
    }
    if (this.prev == 1) {
      finish();
    }
    this.x0 = paramInt1;
    this.sx0 = paramInt1;
    this.y0 = paramInt2;
    this.sy0 = paramInt2;
    this.color0 = paramInt3;
    this.scolor0 = paramInt3;
    this.rindex = 0;
    this.started = false;
    this.joinSegment = false;
    this.prev = 0;
  }
  
  public void setOutput(LineStroker paramLineStroker)
  {
    this.output = paramLineStroker;
  }
  
  public void setParameters(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PMatrix2D paramPMatrix2D)
  {
    this.m00 = LinePath.FloatToS15_16(paramPMatrix2D.m00);
    this.m01 = LinePath.FloatToS15_16(paramPMatrix2D.m01);
    this.m10 = LinePath.FloatToS15_16(paramPMatrix2D.m10);
    this.m11 = LinePath.FloatToS15_16(paramPMatrix2D.m11);
    this.lineWidth2 = (paramInt1 >> 1);
    this.scaledLineWidth2 = (this.m00 * this.lineWidth2 >> 16);
    this.capStyle = paramInt2;
    this.joinStyle = paramInt3;
    this.m00_2_m01_2 = (this.m00 * this.m00 + this.m01 * this.m01);
    this.m10_2_m11_2 = (this.m10 * this.m10 + this.m11 * this.m11);
    this.m00_m10_m01_m11 = (this.m00 * this.m10 + this.m01 * this.m11);
    double d1 = this.m00 / 65536.0D;
    double d2 = this.m01 / 65536.0D;
    double d3 = this.m10 / 65536.0D;
    double d4 = this.m11 / 65536.0D;
    double d5 = d1 * d4 - d2 * d3;
    if (paramInt3 == 0)
    {
      double d10 = d5 * (paramInt4 / 65536.0D * (this.lineWidth2 / 65536.0D));
      this.miterLimitSq = ((65536.0D * (65536.0D * (d10 * d10))));
    }
    this.numPenSegments = ((int)(3.14159F * paramInt1 / 65536.0F));
    if ((this.pen_dx == null) || (this.pen_dx.length < this.numPenSegments))
    {
      this.pen_dx = new int[this.numPenSegments];
      this.pen_dy = new int[this.numPenSegments];
      this.penIncluded = new boolean[this.numPenSegments];
      this.join = new int[2 * this.numPenSegments];
    }
    for (int i = 0; i < this.numPenSegments; i++)
    {
      double d6 = paramInt1 / 2.0D;
      double d7 = 3.141592653589793D * (i * 2) / this.numPenSegments;
      double d8 = Math.cos(d7);
      double d9 = Math.sin(d7);
      this.pen_dx[i] = ((int)(d6 * (d1 * d8 + d2 * d9)));
      this.pen_dy[i] = ((int)(d6 * (d8 * d3 + d9 * d4)));
    }
    this.prev = 2;
    this.rindex = 0;
    this.started = false;
    this.lineToOrigin = false;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.LineStroker
 * JD-Core Version:    0.7.0.1
 */