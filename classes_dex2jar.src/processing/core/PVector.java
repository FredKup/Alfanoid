package processing.core;

import java.io.Serializable;

public class PVector
  implements Serializable
{
  private static final long serialVersionUID = -6717872085945400694L;
  protected transient float[] array;
  public float x;
  public float y;
  public float z;
  
  public PVector() {}
  
  public PVector(float paramFloat1, float paramFloat2)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.z = 0.0F;
  }
  
  public PVector(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.z = paramFloat3;
  }
  
  public static PVector add(PVector paramPVector1, PVector paramPVector2)
  {
    return add(paramPVector1, paramPVector2, null);
  }
  
  public static PVector add(PVector paramPVector1, PVector paramPVector2, PVector paramPVector3)
  {
    if (paramPVector3 == null) {
      return new PVector(paramPVector1.x + paramPVector2.x, paramPVector1.y + paramPVector2.y, paramPVector1.z + paramPVector2.z);
    }
    paramPVector3.set(paramPVector1.x + paramPVector2.x, paramPVector1.y + paramPVector2.y, paramPVector1.z + paramPVector2.z);
    return paramPVector3;
  }
  
  public static float angleBetween(PVector paramPVector1, PVector paramPVector2)
  {
    double d = (paramPVector1.x * paramPVector2.x + paramPVector1.y * paramPVector2.y + paramPVector1.z * paramPVector2.z) / (Math.sqrt(paramPVector1.x * paramPVector1.x + paramPVector1.y * paramPVector1.y + paramPVector1.z * paramPVector1.z) * Math.sqrt(paramPVector2.x * paramPVector2.x + paramPVector2.y * paramPVector2.y + paramPVector2.z * paramPVector2.z));
    if (d <= -1.0D) {
      return 3.141593F;
    }
    if (d >= 1.0D) {
      return 0.0F;
    }
    return (float)Math.acos(d);
  }
  
  public static PVector cross(PVector paramPVector1, PVector paramPVector2, PVector paramPVector3)
  {
    float f1 = paramPVector1.y * paramPVector2.z - paramPVector2.y * paramPVector1.z;
    float f2 = paramPVector1.z * paramPVector2.x - paramPVector2.z * paramPVector1.x;
    float f3 = paramPVector1.x * paramPVector2.y - paramPVector2.x * paramPVector1.y;
    if (paramPVector3 == null) {
      return new PVector(f1, f2, f3);
    }
    paramPVector3.set(f1, f2, f3);
    return paramPVector3;
  }
  
  public static float dist(PVector paramPVector1, PVector paramPVector2)
  {
    float f1 = paramPVector1.x - paramPVector2.x;
    float f2 = paramPVector1.y - paramPVector2.y;
    float f3 = paramPVector1.z - paramPVector2.z;
    return (float)Math.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
  }
  
  public static PVector div(PVector paramPVector, float paramFloat)
  {
    return div(paramPVector, paramFloat, null);
  }
  
  public static PVector div(PVector paramPVector1, float paramFloat, PVector paramPVector2)
  {
    if (paramPVector2 == null) {
      return new PVector(paramPVector1.x / paramFloat, paramPVector1.y / paramFloat, paramPVector1.z / paramFloat);
    }
    paramPVector2.set(paramPVector1.x / paramFloat, paramPVector1.y / paramFloat, paramPVector1.z / paramFloat);
    return paramPVector2;
  }
  
  public static PVector div(PVector paramPVector1, PVector paramPVector2)
  {
    return div(paramPVector1, paramPVector2, null);
  }
  
  public static PVector div(PVector paramPVector1, PVector paramPVector2, PVector paramPVector3)
  {
    if (paramPVector3 == null) {
      return new PVector(paramPVector1.x / paramPVector2.x, paramPVector1.y / paramPVector2.y, paramPVector1.z / paramPVector2.z);
    }
    paramPVector3.set(paramPVector1.x / paramPVector2.x, paramPVector1.y / paramPVector2.y, paramPVector1.z / paramPVector2.z);
    return paramPVector3;
  }
  
  public static float dot(PVector paramPVector1, PVector paramPVector2)
  {
    return paramPVector1.x * paramPVector2.x + paramPVector1.y * paramPVector2.y + paramPVector1.z * paramPVector2.z;
  }
  
  public static PVector fromAngle(float paramFloat)
  {
    return fromAngle(paramFloat, null);
  }
  
  public static PVector fromAngle(float paramFloat, PVector paramPVector)
  {
    if (paramPVector == null) {
      return new PVector((float)Math.cos(paramFloat), (float)Math.sin(paramFloat), 0.0F);
    }
    paramPVector.set((float)Math.cos(paramFloat), (float)Math.sin(paramFloat), 0.0F);
    return paramPVector;
  }
  
  public static PVector lerp(PVector paramPVector1, PVector paramPVector2, float paramFloat)
  {
    PVector localPVector = paramPVector1.get();
    localPVector.lerp(paramPVector2, paramFloat);
    return localPVector;
  }
  
  public static PVector mult(PVector paramPVector, float paramFloat)
  {
    return mult(paramPVector, paramFloat, null);
  }
  
  public static PVector mult(PVector paramPVector1, float paramFloat, PVector paramPVector2)
  {
    if (paramPVector2 == null) {
      return new PVector(paramFloat * paramPVector1.x, paramFloat * paramPVector1.y, paramFloat * paramPVector1.z);
    }
    paramPVector2.set(paramFloat * paramPVector1.x, paramFloat * paramPVector1.y, paramFloat * paramPVector1.z);
    return paramPVector2;
  }
  
  public static PVector mult(PVector paramPVector1, PVector paramPVector2)
  {
    return mult(paramPVector1, paramPVector2, null);
  }
  
  public static PVector mult(PVector paramPVector1, PVector paramPVector2, PVector paramPVector3)
  {
    if (paramPVector3 == null) {
      return new PVector(paramPVector1.x * paramPVector2.x, paramPVector1.y * paramPVector2.y, paramPVector1.z * paramPVector2.z);
    }
    paramPVector3.set(paramPVector1.x * paramPVector2.x, paramPVector1.y * paramPVector2.y, paramPVector1.z * paramPVector2.z);
    return paramPVector3;
  }
  
  public static PVector random2D()
  {
    return random2D(null, null);
  }
  
  public static PVector random2D(PApplet paramPApplet)
  {
    return random2D(null, paramPApplet);
  }
  
  public static PVector random2D(PVector paramPVector)
  {
    return random2D(paramPVector, null);
  }
  
  public static PVector random2D(PVector paramPVector, PApplet paramPApplet)
  {
    if (paramPApplet == null) {
      return fromAngle((float)(2.0D * (3.141592653589793D * Math.random())), paramPVector);
    }
    return fromAngle(paramPApplet.random(6.283186F), paramPVector);
  }
  
  public static PVector random3D()
  {
    return random3D(null, null);
  }
  
  public static PVector random3D(PApplet paramPApplet)
  {
    return random3D(null, paramPApplet);
  }
  
  public static PVector random3D(PVector paramPVector)
  {
    return random3D(paramPVector, null);
  }
  
  public static PVector random3D(PVector paramPVector, PApplet paramPApplet)
  {
    float f1;
    if (paramPApplet == null) {
      f1 = (float)(2.0D * (3.141592653589793D * Math.random()));
    }
    float f3;
    float f4;
    for (float f2 = (float)(2.0D * Math.random() - 1.0D);; f2 = paramPApplet.random(-1.0F, 1.0F))
    {
      f3 = (float)(Math.sqrt(1.0F - f2 * f2) * Math.cos(f1));
      f4 = (float)(Math.sqrt(1.0F - f2 * f2) * Math.sin(f1));
      if (paramPVector != null) {
        break;
      }
      return new PVector(f3, f4, f2);
      f1 = paramPApplet.random(6.283186F);
    }
    paramPVector.set(f3, f4, f2);
    return paramPVector;
  }
  
  public static PVector sub(PVector paramPVector1, PVector paramPVector2)
  {
    return sub(paramPVector1, paramPVector2, null);
  }
  
  public static PVector sub(PVector paramPVector1, PVector paramPVector2, PVector paramPVector3)
  {
    if (paramPVector3 == null) {
      return new PVector(paramPVector1.x - paramPVector2.x, paramPVector1.y - paramPVector2.y, paramPVector1.z - paramPVector2.z);
    }
    paramPVector3.set(paramPVector1.x - paramPVector2.x, paramPVector1.y - paramPVector2.y, paramPVector1.z - paramPVector2.z);
    return paramPVector3;
  }
  
  public void add(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.x = (paramFloat1 + this.x);
    this.y = (paramFloat2 + this.y);
    this.z = (paramFloat3 + this.z);
  }
  
  public void add(PVector paramPVector)
  {
    this.x += paramPVector.x;
    this.y += paramPVector.y;
    this.z += paramPVector.z;
  }
  
  public float[] array()
  {
    if (this.array == null) {
      this.array = new float[3];
    }
    this.array[0] = this.x;
    this.array[1] = this.y;
    this.array[2] = this.z;
    return this.array;
  }
  
  public PVector cross(PVector paramPVector)
  {
    return cross(paramPVector, null);
  }
  
  public PVector cross(PVector paramPVector1, PVector paramPVector2)
  {
    float f1 = this.y * paramPVector1.z - paramPVector1.y * this.z;
    float f2 = this.z * paramPVector1.x - paramPVector1.z * this.x;
    float f3 = this.x * paramPVector1.y - paramPVector1.x * this.y;
    if (paramPVector2 == null) {
      return new PVector(f1, f2, f3);
    }
    paramPVector2.set(f1, f2, f3);
    return paramPVector2;
  }
  
  public float dist(PVector paramPVector)
  {
    float f1 = this.x - paramPVector.x;
    float f2 = this.y - paramPVector.y;
    float f3 = this.z - paramPVector.z;
    return (float)Math.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
  }
  
  public void div(float paramFloat)
  {
    this.x /= paramFloat;
    this.y /= paramFloat;
    this.z /= paramFloat;
  }
  
  public void div(PVector paramPVector)
  {
    this.x /= paramPVector.x;
    this.y /= paramPVector.y;
    this.z /= paramPVector.z;
  }
  
  public float dot(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return paramFloat1 * this.x + paramFloat2 * this.y + paramFloat3 * this.z;
  }
  
  public float dot(PVector paramPVector)
  {
    return this.x * paramPVector.x + this.y * paramPVector.y + this.z * paramPVector.z;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PVector)) {}
    PVector localPVector;
    do
    {
      return false;
      localPVector = (PVector)paramObject;
    } while ((this.x != localPVector.x) || (this.y != localPVector.y) || (this.z != localPVector.z));
    return true;
  }
  
  public PVector get()
  {
    return new PVector(this.x, this.y, this.z);
  }
  
  public float[] get(float[] paramArrayOfFloat)
  {
    if (paramArrayOfFloat == null)
    {
      paramArrayOfFloat = new float[3];
      paramArrayOfFloat[0] = this.x;
      paramArrayOfFloat[1] = this.y;
      paramArrayOfFloat[2] = this.z;
    }
    do
    {
      return paramArrayOfFloat;
      if (paramArrayOfFloat.length >= 2)
      {
        paramArrayOfFloat[0] = this.x;
        paramArrayOfFloat[1] = this.y;
      }
    } while (paramArrayOfFloat.length < 3);
    paramArrayOfFloat[2] = this.z;
    return paramArrayOfFloat;
  }
  
  public int hashCode()
  {
    return 31 * (31 * (31 + Float.floatToIntBits(this.x)) + Float.floatToIntBits(this.y)) + Float.floatToIntBits(this.z);
  }
  
  public float heading()
  {
    return -1.0F * (float)Math.atan2(-this.y, this.x);
  }
  
  @Deprecated
  public float heading2D()
  {
    return heading();
  }
  
  public void lerp(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.x = PApplet.lerp(this.x, paramFloat1, paramFloat4);
    this.y = PApplet.lerp(this.y, paramFloat2, paramFloat4);
    this.z = PApplet.lerp(this.z, paramFloat3, paramFloat4);
  }
  
  public void lerp(PVector paramPVector, float paramFloat)
  {
    this.x = PApplet.lerp(this.x, paramPVector.x, paramFloat);
    this.y = PApplet.lerp(this.y, paramPVector.y, paramFloat);
    this.z = PApplet.lerp(this.z, paramPVector.z, paramFloat);
  }
  
  public void limit(float paramFloat)
  {
    if (magSq() > paramFloat * paramFloat)
    {
      normalize();
      mult(paramFloat);
    }
  }
  
  public float mag()
  {
    return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
  }
  
  public float magSq()
  {
    return this.x * this.x + this.y * this.y + this.z * this.z;
  }
  
  public void mult(float paramFloat)
  {
    this.x = (paramFloat * this.x);
    this.y = (paramFloat * this.y);
    this.z = (paramFloat * this.z);
  }
  
  public void mult(PVector paramPVector)
  {
    this.x *= paramPVector.x;
    this.y *= paramPVector.y;
    this.z *= paramPVector.z;
  }
  
  public PVector normalize(PVector paramPVector)
  {
    if (paramPVector == null) {
      paramPVector = new PVector();
    }
    float f = mag();
    if (f > 0.0F)
    {
      paramPVector.set(this.x / f, this.y / f, this.z / f);
      return paramPVector;
    }
    paramPVector.set(this.x, this.y, this.z);
    return paramPVector;
  }
  
  public void normalize()
  {
    float f = mag();
    if ((f != 0.0F) && (f != 1.0F)) {
      div(f);
    }
  }
  
  public void rotate(float paramFloat)
  {
    float f = this.x;
    this.x = (this.x * PApplet.cos(paramFloat) - this.y * PApplet.sin(paramFloat));
    this.y = (f * PApplet.sin(paramFloat) + this.y * PApplet.cos(paramFloat));
  }
  
  public void set(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
    this.z = paramFloat3;
  }
  
  public void set(PVector paramPVector)
  {
    this.x = paramPVector.x;
    this.y = paramPVector.y;
    this.z = paramPVector.z;
  }
  
  public void set(float[] paramArrayOfFloat)
  {
    if (paramArrayOfFloat.length >= 2)
    {
      this.x = paramArrayOfFloat[0];
      this.y = paramArrayOfFloat[1];
    }
    if (paramArrayOfFloat.length >= 3) {
      this.z = paramArrayOfFloat[2];
    }
  }
  
  public PVector setMag(PVector paramPVector, float paramFloat)
  {
    PVector localPVector = normalize(paramPVector);
    localPVector.mult(paramFloat);
    return localPVector;
  }
  
  public void setMag(float paramFloat)
  {
    normalize();
    mult(paramFloat);
  }
  
  public void sub(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.x -= paramFloat1;
    this.y -= paramFloat2;
    this.z -= paramFloat3;
  }
  
  public void sub(PVector paramPVector)
  {
    this.x -= paramPVector.x;
    this.y -= paramPVector.y;
    this.z -= paramPVector.z;
  }
  
  public String toString()
  {
    return "[ " + this.x + ", " + this.y + ", " + this.z + " ]";
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PVector
 * JD-Core Version:    0.7.0.1
 */