package processing.core;

import java.io.PrintStream;

public class PMatrix2D
  implements PMatrix
{
  public float m00;
  public float m01;
  public float m02;
  public float m10;
  public float m11;
  public float m12;
  
  public PMatrix2D()
  {
    reset();
  }
  
  public PMatrix2D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    set(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public PMatrix2D(PMatrix paramPMatrix)
  {
    set(paramPMatrix);
  }
  
  private final float abs(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      paramFloat = -paramFloat;
    }
    return paramFloat;
  }
  
  private final float cos(float paramFloat)
  {
    return (float)Math.cos(paramFloat);
  }
  
  private final float max(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 > paramFloat2) {
      return paramFloat1;
    }
    return paramFloat2;
  }
  
  private final float sin(float paramFloat)
  {
    return (float)Math.sin(paramFloat);
  }
  
  private final float tan(float paramFloat)
  {
    return (float)Math.tan(paramFloat);
  }
  
  public void apply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    float f1 = this.m00;
    float f2 = this.m01;
    this.m00 = (paramFloat1 * f1 + paramFloat4 * f2);
    this.m01 = (paramFloat2 * f1 + paramFloat5 * f2);
    this.m02 += paramFloat3 * f1 + paramFloat6 * f2;
    float f3 = this.m10;
    float f4 = this.m11;
    this.m10 = (paramFloat1 * f3 + paramFloat4 * f4);
    this.m11 = (paramFloat2 * f3 + paramFloat5 * f4);
    this.m12 += paramFloat3 * f3 + paramFloat6 * f4;
  }
  
  public void apply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    throw new IllegalArgumentException("Cannot use this version of apply() on a PMatrix2D.");
  }
  
  public void apply(PMatrix2D paramPMatrix2D)
  {
    apply(paramPMatrix2D.m00, paramPMatrix2D.m01, paramPMatrix2D.m02, paramPMatrix2D.m10, paramPMatrix2D.m11, paramPMatrix2D.m12);
  }
  
  public void apply(PMatrix3D paramPMatrix3D)
  {
    throw new IllegalArgumentException("Cannot use apply(PMatrix3D) on a PMatrix2D.");
  }
  
  public void apply(PMatrix paramPMatrix)
  {
    if ((paramPMatrix instanceof PMatrix2D)) {
      apply((PMatrix2D)paramPMatrix);
    }
    while (!(paramPMatrix instanceof PMatrix3D)) {
      return;
    }
    apply((PMatrix3D)paramPMatrix);
  }
  
  public float determinant()
  {
    return this.m00 * this.m11 - this.m01 * this.m10;
  }
  
  public PMatrix2D get()
  {
    PMatrix2D localPMatrix2D = new PMatrix2D();
    localPMatrix2D.set(this);
    return localPMatrix2D;
  }
  
  public float[] get(float[] paramArrayOfFloat)
  {
    if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length != 6)) {
      paramArrayOfFloat = new float[6];
    }
    paramArrayOfFloat[0] = this.m00;
    paramArrayOfFloat[1] = this.m01;
    paramArrayOfFloat[2] = this.m02;
    paramArrayOfFloat[3] = this.m10;
    paramArrayOfFloat[4] = this.m11;
    paramArrayOfFloat[5] = this.m12;
    return paramArrayOfFloat;
  }
  
  public boolean invert()
  {
    float f1 = determinant();
    if (Math.abs(f1) <= 1.4E-45F) {
      return false;
    }
    float f2 = this.m00;
    float f3 = this.m01;
    float f4 = this.m02;
    float f5 = this.m10;
    float f6 = this.m11;
    float f7 = this.m12;
    this.m00 = (f6 / f1);
    this.m10 = (-f5 / f1);
    this.m01 = (-f3 / f1);
    this.m11 = (f2 / f1);
    this.m02 = ((f3 * f7 - f6 * f4) / f1);
    this.m12 = ((f5 * f4 - f2 * f7) / f1);
    return true;
  }
  
  protected boolean isIdentity()
  {
    return (this.m00 == 1.0F) && (this.m01 == 0.0F) && (this.m02 == 0.0F) && (this.m10 == 0.0F) && (this.m11 == 1.0F) && (this.m12 == 0.0F);
  }
  
  protected boolean isWarped()
  {
    return (this.m00 != 1.0F) || (this.m01 != 0.0F) || (this.m10 != 0.0F) || (this.m11 != 1.0F);
  }
  
  public PVector mult(PVector paramPVector1, PVector paramPVector2)
  {
    if (paramPVector2 == null) {
      paramPVector2 = new PVector();
    }
    paramPVector2.x = (this.m00 * paramPVector1.x + this.m01 * paramPVector1.y + this.m02);
    paramPVector2.y = (this.m10 * paramPVector1.x + this.m11 * paramPVector1.y + this.m12);
    return paramPVector2;
  }
  
  public float[] mult(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    if ((paramArrayOfFloat2 == null) || (paramArrayOfFloat2.length != 2)) {
      paramArrayOfFloat2 = new float[2];
    }
    if (paramArrayOfFloat1 == paramArrayOfFloat2)
    {
      float f1 = this.m00 * paramArrayOfFloat1[0] + this.m01 * paramArrayOfFloat1[1] + this.m02;
      float f2 = this.m10 * paramArrayOfFloat1[0] + this.m11 * paramArrayOfFloat1[1] + this.m12;
      paramArrayOfFloat2[0] = f1;
      paramArrayOfFloat2[1] = f2;
      return paramArrayOfFloat2;
    }
    paramArrayOfFloat2[0] = (this.m00 * paramArrayOfFloat1[0] + this.m01 * paramArrayOfFloat1[1] + this.m02);
    paramArrayOfFloat2[1] = (this.m10 * paramArrayOfFloat1[0] + this.m11 * paramArrayOfFloat1[1] + this.m12);
    return paramArrayOfFloat2;
  }
  
  public float multX(float paramFloat1, float paramFloat2)
  {
    return paramFloat1 * this.m00 + paramFloat2 * this.m01 + this.m02;
  }
  
  public float multY(float paramFloat1, float paramFloat2)
  {
    return paramFloat1 * this.m10 + paramFloat2 * this.m11 + this.m12;
  }
  
  public void preApply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    float f1 = this.m02;
    float f2 = this.m12;
    float f3 = paramFloat3 + (f1 * paramFloat1 + f2 * paramFloat2);
    float f4 = paramFloat6 + (f1 * paramFloat4 + f2 * paramFloat5);
    this.m02 = f3;
    this.m12 = f4;
    float f5 = this.m00;
    float f6 = this.m10;
    this.m00 = (f5 * paramFloat1 + f6 * paramFloat2);
    this.m10 = (f5 * paramFloat4 + f6 * paramFloat5);
    float f7 = this.m01;
    float f8 = this.m11;
    this.m01 = (f7 * paramFloat1 + f8 * paramFloat2);
    this.m11 = (f7 * paramFloat4 + f8 * paramFloat5);
  }
  
  public void preApply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    throw new IllegalArgumentException("Cannot use this version of preApply() on a PMatrix2D.");
  }
  
  public void preApply(PMatrix2D paramPMatrix2D)
  {
    preApply(paramPMatrix2D.m00, paramPMatrix2D.m01, paramPMatrix2D.m02, paramPMatrix2D.m10, paramPMatrix2D.m11, paramPMatrix2D.m12);
  }
  
  public void preApply(PMatrix3D paramPMatrix3D)
  {
    throw new IllegalArgumentException("Cannot use preApply(PMatrix3D) on a PMatrix2D.");
  }
  
  public void print()
  {
    int i = (int)abs(max(PApplet.max(abs(this.m00), abs(this.m01), abs(this.m02)), PApplet.max(abs(this.m10), abs(this.m11), abs(this.m12))));
    int j = 1;
    if ((Float.isNaN(i)) || (Float.isInfinite(i))) {
      j = 5;
    }
    for (;;)
    {
      System.out.println(PApplet.nfs(this.m00, j, 4) + " " + PApplet.nfs(this.m01, j, 4) + " " + PApplet.nfs(this.m02, j, 4));
      System.out.println(PApplet.nfs(this.m10, j, 4) + " " + PApplet.nfs(this.m11, j, 4) + " " + PApplet.nfs(this.m12, j, 4));
      System.out.println();
      return;
      do
      {
        j++;
        i /= 10;
      } while (i != 0);
    }
  }
  
  public void reset()
  {
    set(1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F);
  }
  
  public void rotate(float paramFloat)
  {
    float f1 = sin(paramFloat);
    float f2 = cos(paramFloat);
    float f3 = this.m00;
    float f4 = this.m01;
    this.m00 = (f2 * f3 + f1 * f4);
    this.m01 = (f3 * -f1 + f2 * f4);
    float f5 = this.m10;
    float f6 = this.m11;
    this.m10 = (f2 * f5 + f1 * f6);
    this.m11 = (f5 * -f1 + f2 * f6);
  }
  
  public void rotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    throw new IllegalArgumentException("Cannot use this version of rotate() on a PMatrix2D.");
  }
  
  public void rotateX(float paramFloat)
  {
    throw new IllegalArgumentException("Cannot use rotateX() on a PMatrix2D.");
  }
  
  public void rotateY(float paramFloat)
  {
    throw new IllegalArgumentException("Cannot use rotateY() on a PMatrix2D.");
  }
  
  public void rotateZ(float paramFloat)
  {
    rotate(paramFloat);
  }
  
  public void scale(float paramFloat)
  {
    scale(paramFloat, paramFloat);
  }
  
  public void scale(float paramFloat1, float paramFloat2)
  {
    this.m00 = (paramFloat1 * this.m00);
    this.m01 = (paramFloat2 * this.m01);
    this.m10 = (paramFloat1 * this.m10);
    this.m11 = (paramFloat2 * this.m11);
  }
  
  public void scale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    throw new IllegalArgumentException("Cannot use this version of scale() on a PMatrix2D.");
  }
  
  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.m00 = paramFloat1;
    this.m01 = paramFloat2;
    this.m02 = paramFloat3;
    this.m10 = paramFloat4;
    this.m11 = paramFloat5;
    this.m12 = paramFloat6;
  }
  
  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16) {}
  
  public void set(PMatrix3D paramPMatrix3D) {}
  
  public void set(PMatrix paramPMatrix)
  {
    if ((paramPMatrix instanceof PMatrix2D))
    {
      PMatrix2D localPMatrix2D = (PMatrix2D)paramPMatrix;
      set(localPMatrix2D.m00, localPMatrix2D.m01, localPMatrix2D.m02, localPMatrix2D.m10, localPMatrix2D.m11, localPMatrix2D.m12);
      return;
    }
    throw new IllegalArgumentException("PMatrix2D.set() only accepts PMatrix2D objects.");
  }
  
  public void set(float[] paramArrayOfFloat)
  {
    this.m00 = paramArrayOfFloat[0];
    this.m01 = paramArrayOfFloat[1];
    this.m02 = paramArrayOfFloat[2];
    this.m10 = paramArrayOfFloat[3];
    this.m11 = paramArrayOfFloat[4];
    this.m12 = paramArrayOfFloat[5];
  }
  
  public void shearX(float paramFloat)
  {
    apply(1.0F, 0.0F, 1.0F, tan(paramFloat), 0.0F, 0.0F);
  }
  
  public void shearY(float paramFloat)
  {
    apply(1.0F, 0.0F, 1.0F, 0.0F, tan(paramFloat), 0.0F);
  }
  
  public void translate(float paramFloat1, float paramFloat2)
  {
    this.m02 = (paramFloat1 * this.m00 + paramFloat2 * this.m01 + this.m02);
    this.m12 = (paramFloat1 * this.m10 + paramFloat2 * this.m11 + this.m12);
  }
  
  public void translate(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    throw new IllegalArgumentException("Cannot use translate(x, y, z) on a PMatrix2D.");
  }
  
  public void transpose() {}
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PMatrix2D
 * JD-Core Version:    0.7.0.1
 */