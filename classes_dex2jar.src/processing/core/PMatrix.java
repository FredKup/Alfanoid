package processing.core;

public abstract interface PMatrix
{
  public abstract void apply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
  
  public abstract void apply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16);
  
  public abstract void apply(PMatrix2D paramPMatrix2D);
  
  public abstract void apply(PMatrix3D paramPMatrix3D);
  
  public abstract void apply(PMatrix paramPMatrix);
  
  public abstract float determinant();
  
  public abstract PMatrix get();
  
  public abstract float[] get(float[] paramArrayOfFloat);
  
  public abstract boolean invert();
  
  public abstract PVector mult(PVector paramPVector1, PVector paramPVector2);
  
  public abstract float[] mult(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2);
  
  public abstract void preApply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
  
  public abstract void preApply(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16);
  
  public abstract void preApply(PMatrix2D paramPMatrix2D);
  
  public abstract void preApply(PMatrix3D paramPMatrix3D);
  
  public abstract void reset();
  
  public abstract void rotate(float paramFloat);
  
  public abstract void rotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  public abstract void rotateX(float paramFloat);
  
  public abstract void rotateY(float paramFloat);
  
  public abstract void rotateZ(float paramFloat);
  
  public abstract void scale(float paramFloat);
  
  public abstract void scale(float paramFloat1, float paramFloat2);
  
  public abstract void scale(float paramFloat1, float paramFloat2, float paramFloat3);
  
  public abstract void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
  
  public abstract void set(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16);
  
  public abstract void set(PMatrix paramPMatrix);
  
  public abstract void set(float[] paramArrayOfFloat);
  
  public abstract void shearX(float paramFloat);
  
  public abstract void shearY(float paramFloat);
  
  public abstract void translate(float paramFloat1, float paramFloat2);
  
  public abstract void translate(float paramFloat1, float paramFloat2, float paramFloat3);
  
  public abstract void transpose();
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PMatrix
 * JD-Core Version:    0.7.0.1
 */