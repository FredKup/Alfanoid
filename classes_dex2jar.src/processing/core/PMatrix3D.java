package processing.core;

import java.io.PrintStream;

public final class PMatrix3D
  implements PMatrix
{
  protected PMatrix3D inverseCopy;
  public float m00;
  public float m01;
  public float m02;
  public float m03;
  public float m10;
  public float m11;
  public float m12;
  public float m13;
  public float m20;
  public float m21;
  public float m22;
  public float m23;
  public float m30;
  public float m31;
  public float m32;
  public float m33;
  
  public PMatrix3D()
  {
    reset();
  }
  
  public PMatrix3D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    set(paramFloat1, paramFloat2, paramFloat3, 0.0F, paramFloat4, paramFloat5, paramFloat6, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public PMatrix3D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    set(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramFloat13, paramFloat14, paramFloat15, paramFloat16);
  }
  
  public PMatrix3D(PMatrix paramPMatrix)
  {
    set(paramPMatrix);
  }
  
  private static final float abs(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      paramFloat = -paramFloat;
    }
    return paramFloat;
  }
  
  private static final float cos(float paramFloat)
  {
    return (float)Math.cos(paramFloat);
  }
  
  private float determinant3x3(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    return paramFloat1 * (paramFloat5 * paramFloat9 - paramFloat6 * paramFloat8) + paramFloat2 * (paramFloat6 * paramFloat7 - paramFloat4 * paramFloat9) + paramFloat3 * (paramFloat4 * paramFloat8 - paramFloat5 * paramFloat7);
  }
  
  private static final float max(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 > paramFloat2) {
      return paramFloat1;
    }
    return paramFloat2;
  }
  
  private static final float sin(float paramFloat)
  {
    return (float)Math.sin(paramFloat);
  }
  
  public void apply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    apply(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4, paramFloat5, 0.0F, paramFloat6, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void apply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    float f1 = paramFloat1 * this.m00 + paramFloat5 * this.m01 + paramFloat9 * this.m02 + paramFloat13 * this.m03;
    float f2 = paramFloat2 * this.m00 + paramFloat6 * this.m01 + paramFloat10 * this.m02 + paramFloat14 * this.m03;
    float f3 = paramFloat3 * this.m00 + paramFloat7 * this.m01 + paramFloat11 * this.m02 + paramFloat15 * this.m03;
    float f4 = paramFloat4 * this.m00 + paramFloat8 * this.m01 + paramFloat12 * this.m02 + paramFloat16 * this.m03;
    float f5 = paramFloat1 * this.m10 + paramFloat5 * this.m11 + paramFloat9 * this.m12 + paramFloat13 * this.m13;
    float f6 = paramFloat2 * this.m10 + paramFloat6 * this.m11 + paramFloat10 * this.m12 + paramFloat14 * this.m13;
    float f7 = paramFloat3 * this.m10 + paramFloat7 * this.m11 + paramFloat11 * this.m12 + paramFloat15 * this.m13;
    float f8 = paramFloat4 * this.m10 + paramFloat8 * this.m11 + paramFloat12 * this.m12 + paramFloat16 * this.m13;
    float f9 = paramFloat1 * this.m20 + paramFloat5 * this.m21 + paramFloat9 * this.m22 + paramFloat13 * this.m23;
    float f10 = paramFloat2 * this.m20 + paramFloat6 * this.m21 + paramFloat10 * this.m22 + paramFloat14 * this.m23;
    float f11 = paramFloat3 * this.m20 + paramFloat7 * this.m21 + paramFloat11 * this.m22 + paramFloat15 * this.m23;
    float f12 = paramFloat4 * this.m20 + paramFloat8 * this.m21 + paramFloat12 * this.m22 + paramFloat16 * this.m23;
    float f13 = paramFloat1 * this.m30 + paramFloat5 * this.m31 + paramFloat9 * this.m32 + paramFloat13 * this.m33;
    float f14 = paramFloat2 * this.m30 + paramFloat6 * this.m31 + paramFloat10 * this.m32 + paramFloat14 * this.m33;
    float f15 = paramFloat3 * this.m30 + paramFloat7 * this.m31 + paramFloat11 * this.m32 + paramFloat15 * this.m33;
    float f16 = paramFloat4 * this.m30 + paramFloat8 * this.m31 + paramFloat12 * this.m32 + paramFloat16 * this.m33;
    this.m00 = f1;
    this.m01 = f2;
    this.m02 = f3;
    this.m03 = f4;
    this.m10 = f5;
    this.m11 = f6;
    this.m12 = f7;
    this.m13 = f8;
    this.m20 = f9;
    this.m21 = f10;
    this.m22 = f11;
    this.m23 = f12;
    this.m30 = f13;
    this.m31 = f14;
    this.m32 = f15;
    this.m33 = f16;
  }
  
  public void apply(PMatrix2D paramPMatrix2D)
  {
    apply(paramPMatrix2D.m00, paramPMatrix2D.m01, 0.0F, paramPMatrix2D.m02, paramPMatrix2D.m10, paramPMatrix2D.m11, 0.0F, paramPMatrix2D.m12, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void apply(PMatrix3D paramPMatrix3D)
  {
    apply(paramPMatrix3D.m00, paramPMatrix3D.m01, paramPMatrix3D.m02, paramPMatrix3D.m03, paramPMatrix3D.m10, paramPMatrix3D.m11, paramPMatrix3D.m12, paramPMatrix3D.m13, paramPMatrix3D.m20, paramPMatrix3D.m21, paramPMatrix3D.m22, paramPMatrix3D.m23, paramPMatrix3D.m30, paramPMatrix3D.m31, paramPMatrix3D.m32, paramPMatrix3D.m33);
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
    return this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33) - this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33) + this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33) - this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
  }
  
  public PMatrix3D get()
  {
    PMatrix3D localPMatrix3D = new PMatrix3D();
    localPMatrix3D.set(this);
    return localPMatrix3D;
  }
  
  public float[] get(float[] paramArrayOfFloat)
  {
    if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length != 16)) {
      paramArrayOfFloat = new float[16];
    }
    paramArrayOfFloat[0] = this.m00;
    paramArrayOfFloat[1] = this.m01;
    paramArrayOfFloat[2] = this.m02;
    paramArrayOfFloat[3] = this.m03;
    paramArrayOfFloat[4] = this.m10;
    paramArrayOfFloat[5] = this.m11;
    paramArrayOfFloat[6] = this.m12;
    paramArrayOfFloat[7] = this.m13;
    paramArrayOfFloat[8] = this.m20;
    paramArrayOfFloat[9] = this.m21;
    paramArrayOfFloat[10] = this.m22;
    paramArrayOfFloat[11] = this.m23;
    paramArrayOfFloat[12] = this.m30;
    paramArrayOfFloat[13] = this.m31;
    paramArrayOfFloat[14] = this.m32;
    paramArrayOfFloat[15] = this.m33;
    return paramArrayOfFloat;
  }
  
  protected boolean invApply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    if (this.inverseCopy == null) {
      this.inverseCopy = new PMatrix3D();
    }
    this.inverseCopy.set(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramFloat13, paramFloat14, paramFloat15, paramFloat16);
    if (!this.inverseCopy.invert()) {
      return false;
    }
    preApply(this.inverseCopy);
    return true;
  }
  
  protected void invRotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = cos(-paramFloat1);
    float f2 = sin(-paramFloat1);
    float f3 = 1.0F - f1;
    preApply(f1 + paramFloat2 * (f3 * paramFloat2), paramFloat3 * (f3 * paramFloat2) - f2 * paramFloat4, paramFloat4 * (f3 * paramFloat2) + f2 * paramFloat3, 0.0F, paramFloat3 * (f3 * paramFloat2) + f2 * paramFloat4, f1 + paramFloat3 * (f3 * paramFloat3), paramFloat4 * (f3 * paramFloat3) - f2 * paramFloat2, 0.0F, paramFloat4 * (f3 * paramFloat2) - f2 * paramFloat3, paramFloat4 * (f3 * paramFloat3) + f2 * paramFloat2, f1 + paramFloat4 * (f3 * paramFloat4), 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  protected void invRotateX(float paramFloat)
  {
    float f1 = cos(-paramFloat);
    float f2 = sin(-paramFloat);
    preApply(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, f1, -f2, 0.0F, 0.0F, f2, f1, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  protected void invRotateY(float paramFloat)
  {
    float f1 = cos(-paramFloat);
    float f2 = sin(-paramFloat);
    preApply(f1, 0.0F, f2, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -f2, 0.0F, f1, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  protected void invRotateZ(float paramFloat)
  {
    float f1 = cos(-paramFloat);
    float f2 = sin(-paramFloat);
    preApply(f1, -f2, 0.0F, 0.0F, f2, f1, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  protected void invScale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    preApply(1.0F / paramFloat1, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F / paramFloat2, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F / paramFloat3, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  protected void invTranslate(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    preApply(1.0F, 0.0F, 0.0F, -paramFloat1, 0.0F, 1.0F, 0.0F, -paramFloat2, 0.0F, 0.0F, 1.0F, -paramFloat3, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public boolean invert()
  {
    float f1 = determinant();
    if (f1 == 0.0F) {
      return false;
    }
    float f2 = determinant3x3(this.m11, this.m12, this.m13, this.m21, this.m22, this.m23, this.m31, this.m32, this.m33);
    float f3 = -determinant3x3(this.m10, this.m12, this.m13, this.m20, this.m22, this.m23, this.m30, this.m32, this.m33);
    float f4 = determinant3x3(this.m10, this.m11, this.m13, this.m20, this.m21, this.m23, this.m30, this.m31, this.m33);
    float f5 = -determinant3x3(this.m10, this.m11, this.m12, this.m20, this.m21, this.m22, this.m30, this.m31, this.m32);
    float f6 = -determinant3x3(this.m01, this.m02, this.m03, this.m21, this.m22, this.m23, this.m31, this.m32, this.m33);
    float f7 = determinant3x3(this.m00, this.m02, this.m03, this.m20, this.m22, this.m23, this.m30, this.m32, this.m33);
    float f8 = -determinant3x3(this.m00, this.m01, this.m03, this.m20, this.m21, this.m23, this.m30, this.m31, this.m33);
    float f9 = determinant3x3(this.m00, this.m01, this.m02, this.m20, this.m21, this.m22, this.m30, this.m31, this.m32);
    float f10 = determinant3x3(this.m01, this.m02, this.m03, this.m11, this.m12, this.m13, this.m31, this.m32, this.m33);
    float f11 = -determinant3x3(this.m00, this.m02, this.m03, this.m10, this.m12, this.m13, this.m30, this.m32, this.m33);
    float f12 = determinant3x3(this.m00, this.m01, this.m03, this.m10, this.m11, this.m13, this.m30, this.m31, this.m33);
    float f13 = -determinant3x3(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m30, this.m31, this.m32);
    float f14 = -determinant3x3(this.m01, this.m02, this.m03, this.m11, this.m12, this.m13, this.m21, this.m22, this.m23);
    float f15 = determinant3x3(this.m00, this.m02, this.m03, this.m10, this.m12, this.m13, this.m20, this.m22, this.m23);
    float f16 = -determinant3x3(this.m00, this.m01, this.m03, this.m10, this.m11, this.m13, this.m20, this.m21, this.m23);
    float f17 = determinant3x3(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22);
    this.m00 = (f2 / f1);
    this.m01 = (f6 / f1);
    this.m02 = (f10 / f1);
    this.m03 = (f14 / f1);
    this.m10 = (f3 / f1);
    this.m11 = (f7 / f1);
    this.m12 = (f11 / f1);
    this.m13 = (f15 / f1);
    this.m20 = (f4 / f1);
    this.m21 = (f8 / f1);
    this.m22 = (f12 / f1);
    this.m23 = (f16 / f1);
    this.m30 = (f5 / f1);
    this.m31 = (f9 / f1);
    this.m32 = (f13 / f1);
    this.m33 = (f17 / f1);
    return true;
  }
  
  public PVector mult(PVector paramPVector1, PVector paramPVector2)
  {
    if (paramPVector2 == null) {
      paramPVector2 = new PVector();
    }
    paramPVector2.set(this.m00 * paramPVector1.x + this.m01 * paramPVector1.y + this.m02 * paramPVector1.z + this.m03, this.m10 * paramPVector1.x + this.m11 * paramPVector1.y + this.m12 * paramPVector1.z + this.m13, this.m20 * paramPVector1.x + this.m21 * paramPVector1.y + this.m22 * paramPVector1.z + this.m23);
    return paramPVector2;
  }
  
  public float[] mult(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    if ((paramArrayOfFloat2 == null) || (paramArrayOfFloat2.length < 3)) {
      paramArrayOfFloat2 = new float[3];
    }
    if (paramArrayOfFloat1 == paramArrayOfFloat2) {
      throw new RuntimeException("The source and target vectors used in PMatrix3D.mult() cannot be identical.");
    }
    if (paramArrayOfFloat2.length == 3)
    {
      paramArrayOfFloat2[0] = (this.m00 * paramArrayOfFloat1[0] + this.m01 * paramArrayOfFloat1[1] + this.m02 * paramArrayOfFloat1[2] + this.m03);
      paramArrayOfFloat2[1] = (this.m10 * paramArrayOfFloat1[0] + this.m11 * paramArrayOfFloat1[1] + this.m12 * paramArrayOfFloat1[2] + this.m13);
      paramArrayOfFloat2[2] = (this.m20 * paramArrayOfFloat1[0] + this.m21 * paramArrayOfFloat1[1] + this.m22 * paramArrayOfFloat1[2] + this.m23);
    }
    while (paramArrayOfFloat2.length <= 3) {
      return paramArrayOfFloat2;
    }
    paramArrayOfFloat2[0] = (this.m00 * paramArrayOfFloat1[0] + this.m01 * paramArrayOfFloat1[1] + this.m02 * paramArrayOfFloat1[2] + this.m03 * paramArrayOfFloat1[3]);
    paramArrayOfFloat2[1] = (this.m10 * paramArrayOfFloat1[0] + this.m11 * paramArrayOfFloat1[1] + this.m12 * paramArrayOfFloat1[2] + this.m13 * paramArrayOfFloat1[3]);
    paramArrayOfFloat2[2] = (this.m20 * paramArrayOfFloat1[0] + this.m21 * paramArrayOfFloat1[1] + this.m22 * paramArrayOfFloat1[2] + this.m23 * paramArrayOfFloat1[3]);
    paramArrayOfFloat2[3] = (this.m30 * paramArrayOfFloat1[0] + this.m31 * paramArrayOfFloat1[1] + this.m32 * paramArrayOfFloat1[2] + this.m33 * paramArrayOfFloat1[3]);
    return paramArrayOfFloat2;
  }
  
  public float multW(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return paramFloat1 * this.m30 + paramFloat2 * this.m31 + paramFloat3 * this.m32 + this.m33;
  }
  
  public float multW(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return paramFloat1 * this.m30 + paramFloat2 * this.m31 + paramFloat3 * this.m32 + paramFloat4 * this.m33;
  }
  
  public float multX(float paramFloat1, float paramFloat2)
  {
    return paramFloat1 * this.m00 + paramFloat2 * this.m01 + this.m03;
  }
  
  public float multX(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return paramFloat1 * this.m00 + paramFloat2 * this.m01 + paramFloat3 * this.m02 + this.m03;
  }
  
  public float multX(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return paramFloat1 * this.m00 + paramFloat2 * this.m01 + paramFloat3 * this.m02 + paramFloat4 * this.m03;
  }
  
  public float multY(float paramFloat1, float paramFloat2)
  {
    return paramFloat1 * this.m10 + paramFloat2 * this.m11 + this.m13;
  }
  
  public float multY(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return paramFloat1 * this.m10 + paramFloat2 * this.m11 + paramFloat3 * this.m12 + this.m13;
  }
  
  public float multY(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return paramFloat1 * this.m10 + paramFloat2 * this.m11 + paramFloat3 * this.m12 + paramFloat4 * this.m13;
  }
  
  public float multZ(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return paramFloat1 * this.m20 + paramFloat2 * this.m21 + paramFloat3 * this.m22 + this.m23;
  }
  
  public float multZ(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return paramFloat1 * this.m20 + paramFloat2 * this.m21 + paramFloat3 * this.m22 + paramFloat4 * this.m23;
  }
  
  public void preApply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    preApply(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4, paramFloat5, 0.0F, paramFloat6, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void preApply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    float f1 = paramFloat1 * this.m00 + paramFloat2 * this.m10 + paramFloat3 * this.m20 + paramFloat4 * this.m30;
    float f2 = paramFloat1 * this.m01 + paramFloat2 * this.m11 + paramFloat3 * this.m21 + paramFloat4 * this.m31;
    float f3 = paramFloat1 * this.m02 + paramFloat2 * this.m12 + paramFloat3 * this.m22 + paramFloat4 * this.m32;
    float f4 = paramFloat1 * this.m03 + paramFloat2 * this.m13 + paramFloat3 * this.m23 + paramFloat4 * this.m33;
    float f5 = paramFloat5 * this.m00 + paramFloat6 * this.m10 + paramFloat7 * this.m20 + paramFloat8 * this.m30;
    float f6 = paramFloat5 * this.m01 + paramFloat6 * this.m11 + paramFloat7 * this.m21 + paramFloat8 * this.m31;
    float f7 = paramFloat5 * this.m02 + paramFloat6 * this.m12 + paramFloat7 * this.m22 + paramFloat8 * this.m32;
    float f8 = paramFloat5 * this.m03 + paramFloat6 * this.m13 + paramFloat7 * this.m23 + paramFloat8 * this.m33;
    float f9 = paramFloat9 * this.m00 + paramFloat10 * this.m10 + paramFloat11 * this.m20 + paramFloat12 * this.m30;
    float f10 = paramFloat9 * this.m01 + paramFloat10 * this.m11 + paramFloat11 * this.m21 + paramFloat12 * this.m31;
    float f11 = paramFloat9 * this.m02 + paramFloat10 * this.m12 + paramFloat11 * this.m22 + paramFloat12 * this.m32;
    float f12 = paramFloat9 * this.m03 + paramFloat10 * this.m13 + paramFloat11 * this.m23 + paramFloat12 * this.m33;
    float f13 = paramFloat13 * this.m00 + paramFloat14 * this.m10 + paramFloat15 * this.m20 + paramFloat16 * this.m30;
    float f14 = paramFloat13 * this.m01 + paramFloat14 * this.m11 + paramFloat15 * this.m21 + paramFloat16 * this.m31;
    float f15 = paramFloat13 * this.m02 + paramFloat14 * this.m12 + paramFloat15 * this.m22 + paramFloat16 * this.m32;
    float f16 = paramFloat13 * this.m03 + paramFloat14 * this.m13 + paramFloat15 * this.m23 + paramFloat16 * this.m33;
    this.m00 = f1;
    this.m01 = f2;
    this.m02 = f3;
    this.m03 = f4;
    this.m10 = f5;
    this.m11 = f6;
    this.m12 = f7;
    this.m13 = f8;
    this.m20 = f9;
    this.m21 = f10;
    this.m22 = f11;
    this.m23 = f12;
    this.m30 = f13;
    this.m31 = f14;
    this.m32 = f15;
    this.m33 = f16;
  }
  
  public void preApply(PMatrix2D paramPMatrix2D)
  {
    preApply(paramPMatrix2D.m00, paramPMatrix2D.m01, 0.0F, paramPMatrix2D.m02, paramPMatrix2D.m10, paramPMatrix2D.m11, 0.0F, paramPMatrix2D.m12, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void preApply(PMatrix3D paramPMatrix3D)
  {
    preApply(paramPMatrix3D.m00, paramPMatrix3D.m01, paramPMatrix3D.m02, paramPMatrix3D.m03, paramPMatrix3D.m10, paramPMatrix3D.m11, paramPMatrix3D.m12, paramPMatrix3D.m13, paramPMatrix3D.m20, paramPMatrix3D.m21, paramPMatrix3D.m22, paramPMatrix3D.m23, paramPMatrix3D.m30, paramPMatrix3D.m31, paramPMatrix3D.m32, paramPMatrix3D.m33);
  }
  
  public void print()
  {
    int i = (int)Math.abs(max(max(max(max(abs(this.m00), abs(this.m01)), max(abs(this.m02), abs(this.m03))), max(max(abs(this.m10), abs(this.m11)), max(abs(this.m12), abs(this.m13)))), max(max(max(abs(this.m20), abs(this.m21)), max(abs(this.m22), abs(this.m23))), max(max(abs(this.m30), abs(this.m31)), max(abs(this.m32), abs(this.m33))))));
    int j = 1;
    if ((Float.isNaN(i)) || (Float.isInfinite(i))) {
      j = 5;
    }
    for (;;)
    {
      System.out.println(PApplet.nfs(this.m00, j, 4) + " " + PApplet.nfs(this.m01, j, 4) + " " + PApplet.nfs(this.m02, j, 4) + " " + PApplet.nfs(this.m03, j, 4));
      System.out.println(PApplet.nfs(this.m10, j, 4) + " " + PApplet.nfs(this.m11, j, 4) + " " + PApplet.nfs(this.m12, j, 4) + " " + PApplet.nfs(this.m13, j, 4));
      System.out.println(PApplet.nfs(this.m20, j, 4) + " " + PApplet.nfs(this.m21, j, 4) + " " + PApplet.nfs(this.m22, j, 4) + " " + PApplet.nfs(this.m23, j, 4));
      System.out.println(PApplet.nfs(this.m30, j, 4) + " " + PApplet.nfs(this.m31, j, 4) + " " + PApplet.nfs(this.m32, j, 4) + " " + PApplet.nfs(this.m33, j, 4));
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
    set(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void rotate(float paramFloat)
  {
    rotateZ(paramFloat);
  }
  
  public void rotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramFloat2 * paramFloat2 + paramFloat3 * paramFloat3 + paramFloat4 * paramFloat4;
    if (f1 < 1.0E-004F) {
      return;
    }
    if (Math.abs(f1 - 1.0F) > 1.0E-004F)
    {
      float f5 = PApplet.sqrt(f1);
      paramFloat2 /= f5;
      paramFloat3 /= f5;
      paramFloat4 /= f5;
    }
    float f2 = cos(paramFloat1);
    float f3 = sin(paramFloat1);
    float f4 = 1.0F - f2;
    apply(f2 + paramFloat2 * (f4 * paramFloat2), paramFloat3 * (f4 * paramFloat2) - f3 * paramFloat4, paramFloat4 * (f4 * paramFloat2) + f3 * paramFloat3, 0.0F, paramFloat3 * (f4 * paramFloat2) + f3 * paramFloat4, f2 + paramFloat3 * (f4 * paramFloat3), paramFloat4 * (f4 * paramFloat3) - f3 * paramFloat2, 0.0F, paramFloat4 * (f4 * paramFloat2) - f3 * paramFloat3, paramFloat4 * (f4 * paramFloat3) + f3 * paramFloat2, f2 + paramFloat4 * (f4 * paramFloat4), 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void rotateX(float paramFloat)
  {
    float f1 = cos(paramFloat);
    float f2 = sin(paramFloat);
    apply(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, f1, -f2, 0.0F, 0.0F, f2, f1, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void rotateY(float paramFloat)
  {
    float f1 = cos(paramFloat);
    float f2 = sin(paramFloat);
    apply(f1, 0.0F, f2, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -f2, 0.0F, f1, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void rotateZ(float paramFloat)
  {
    float f1 = cos(paramFloat);
    float f2 = sin(paramFloat);
    apply(f1, -f2, 0.0F, 0.0F, f2, f1, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void scale(float paramFloat)
  {
    scale(paramFloat, paramFloat, paramFloat);
  }
  
  public void scale(float paramFloat1, float paramFloat2)
  {
    scale(paramFloat1, paramFloat2, 1.0F);
  }
  
  public void scale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.m00 = (paramFloat1 * this.m00);
    this.m01 = (paramFloat2 * this.m01);
    this.m02 = (paramFloat3 * this.m02);
    this.m10 = (paramFloat1 * this.m10);
    this.m11 = (paramFloat2 * this.m11);
    this.m12 = (paramFloat3 * this.m12);
    this.m20 = (paramFloat1 * this.m20);
    this.m21 = (paramFloat2 * this.m21);
    this.m22 = (paramFloat3 * this.m22);
    this.m30 = (paramFloat1 * this.m30);
    this.m31 = (paramFloat2 * this.m31);
    this.m32 = (paramFloat3 * this.m32);
  }
  
  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    set(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4, paramFloat5, 0.0F, paramFloat6, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    this.m00 = paramFloat1;
    this.m01 = paramFloat2;
    this.m02 = paramFloat3;
    this.m03 = paramFloat4;
    this.m10 = paramFloat5;
    this.m11 = paramFloat6;
    this.m12 = paramFloat7;
    this.m13 = paramFloat8;
    this.m20 = paramFloat9;
    this.m21 = paramFloat10;
    this.m22 = paramFloat11;
    this.m23 = paramFloat12;
    this.m30 = paramFloat13;
    this.m31 = paramFloat14;
    this.m32 = paramFloat15;
    this.m33 = paramFloat16;
  }
  
  public void set(PMatrix paramPMatrix)
  {
    if ((paramPMatrix instanceof PMatrix3D))
    {
      PMatrix3D localPMatrix3D = (PMatrix3D)paramPMatrix;
      set(localPMatrix3D.m00, localPMatrix3D.m01, localPMatrix3D.m02, localPMatrix3D.m03, localPMatrix3D.m10, localPMatrix3D.m11, localPMatrix3D.m12, localPMatrix3D.m13, localPMatrix3D.m20, localPMatrix3D.m21, localPMatrix3D.m22, localPMatrix3D.m23, localPMatrix3D.m30, localPMatrix3D.m31, localPMatrix3D.m32, localPMatrix3D.m33);
      return;
    }
    PMatrix2D localPMatrix2D = (PMatrix2D)paramPMatrix;
    set(localPMatrix2D.m00, localPMatrix2D.m01, 0.0F, localPMatrix2D.m02, localPMatrix2D.m10, localPMatrix2D.m11, 0.0F, localPMatrix2D.m12, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void set(float[] paramArrayOfFloat)
  {
    if (paramArrayOfFloat.length == 6) {
      set(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2], paramArrayOfFloat[3], paramArrayOfFloat[4], paramArrayOfFloat[5]);
    }
    while (paramArrayOfFloat.length != 16) {
      return;
    }
    this.m00 = paramArrayOfFloat[0];
    this.m01 = paramArrayOfFloat[1];
    this.m02 = paramArrayOfFloat[2];
    this.m03 = paramArrayOfFloat[3];
    this.m10 = paramArrayOfFloat[4];
    this.m11 = paramArrayOfFloat[5];
    this.m12 = paramArrayOfFloat[6];
    this.m13 = paramArrayOfFloat[7];
    this.m20 = paramArrayOfFloat[8];
    this.m21 = paramArrayOfFloat[9];
    this.m22 = paramArrayOfFloat[10];
    this.m23 = paramArrayOfFloat[11];
    this.m30 = paramArrayOfFloat[12];
    this.m31 = paramArrayOfFloat[13];
    this.m32 = paramArrayOfFloat[14];
    this.m33 = paramArrayOfFloat[15];
  }
  
  public void shearX(float paramFloat)
  {
    apply(1.0F, (float)Math.tan(paramFloat), 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void shearY(float paramFloat)
  {
    apply(1.0F, 0.0F, 0.0F, 0.0F, (float)Math.tan(paramFloat), 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void translate(float paramFloat1, float paramFloat2)
  {
    translate(paramFloat1, paramFloat2, 0.0F);
  }
  
  public void translate(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.m03 += paramFloat1 * this.m00 + paramFloat2 * this.m01 + paramFloat3 * this.m02;
    this.m13 += paramFloat1 * this.m10 + paramFloat2 * this.m11 + paramFloat3 * this.m12;
    this.m23 += paramFloat1 * this.m20 + paramFloat2 * this.m21 + paramFloat3 * this.m22;
    this.m33 += paramFloat1 * this.m30 + paramFloat2 * this.m31 + paramFloat3 * this.m32;
  }
  
  public void transpose()
  {
    float f1 = this.m01;
    this.m01 = this.m10;
    this.m10 = f1;
    float f2 = this.m02;
    this.m02 = this.m20;
    this.m20 = f2;
    float f3 = this.m03;
    this.m03 = this.m30;
    this.m30 = f3;
    float f4 = this.m12;
    this.m12 = this.m21;
    this.m21 = f4;
    float f5 = this.m13;
    this.m13 = this.m31;
    this.m31 = f5;
    float f6 = this.m23;
    this.m23 = this.m32;
    this.m32 = f6;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PMatrix3D
 * JD-Core Version:    0.7.0.1
 */