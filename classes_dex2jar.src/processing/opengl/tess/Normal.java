package processing.opengl.tess;

class Normal
{
  static boolean SLANTED_SWEEP;
  static double S_UNIT_X = 1.0D;
  static double S_UNIT_Y = 0.0D;
  private static final boolean TRUE_PROJECT;
  
  static
  {
    if (!Normal.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      SLANTED_SWEEP = false;
      if (!SLANTED_SWEEP) {
        break;
      }
      S_UNIT_X = 0.5094153956495539D;
      S_UNIT_Y = 0.8605207462201063D;
      return;
    }
  }
  
  static void CheckOrientation(GLUtessellatorImpl paramGLUtessellatorImpl)
  {
    GLUface localGLUface1 = paramGLUtessellatorImpl.mesh.fHead;
    GLUvertex localGLUvertex1 = paramGLUtessellatorImpl.mesh.vHead;
    double d = 0.0D;
    GLUface localGLUface2 = localGLUface1.next;
    if (localGLUface2 == localGLUface1) {
      if (d >= 0.0D) {}
    }
    for (GLUvertex localGLUvertex2 = localGLUvertex1.next;; localGLUvertex2 = localGLUvertex2.next)
    {
      if (localGLUvertex2 == localGLUvertex1)
      {
        paramGLUtessellatorImpl.tUnit[0] = (-paramGLUtessellatorImpl.tUnit[0]);
        paramGLUtessellatorImpl.tUnit[1] = (-paramGLUtessellatorImpl.tUnit[1]);
        paramGLUtessellatorImpl.tUnit[2] = (-paramGLUtessellatorImpl.tUnit[2]);
        return;
        GLUhalfEdge localGLUhalfEdge = localGLUface2.anEdge;
        if (localGLUhalfEdge.winding <= 0) {}
        for (;;)
        {
          localGLUface2 = localGLUface2.next;
          break;
          do
          {
            d += (localGLUhalfEdge.Org.s - localGLUhalfEdge.Sym.Org.s) * (localGLUhalfEdge.Org.t + localGLUhalfEdge.Sym.Org.t);
            localGLUhalfEdge = localGLUhalfEdge.Lnext;
          } while (localGLUhalfEdge != localGLUface2.anEdge);
        }
      }
      localGLUvertex2.t = (-localGLUvertex2.t);
    }
  }
  
  static void ComputeNormal(GLUtessellatorImpl paramGLUtessellatorImpl, double[] paramArrayOfDouble)
  {
    GLUvertex localGLUvertex1 = paramGLUtessellatorImpl.mesh.vHead;
    double[] arrayOfDouble1 = new double[3];
    double[] arrayOfDouble2 = new double[3];
    GLUvertex[] arrayOfGLUvertex1 = new GLUvertex[3];
    GLUvertex[] arrayOfGLUvertex2 = new GLUvertex[3];
    double[] arrayOfDouble3 = new double[3];
    double[] arrayOfDouble4 = new double[3];
    double[] arrayOfDouble5 = new double[3];
    arrayOfDouble1[2] = -2.0E+150D;
    arrayOfDouble1[1] = -2.0E+150D;
    arrayOfDouble1[0] = -2.0E+150D;
    arrayOfDouble2[2] = 2.0E+150D;
    arrayOfDouble2[1] = 2.0E+150D;
    arrayOfDouble2[0] = 2.0E+150D;
    GLUvertex localGLUvertex2 = localGLUvertex1.next;
    int j;
    if (localGLUvertex2 == localGLUvertex1)
    {
      boolean bool = arrayOfDouble1[1] - arrayOfDouble2[1] < arrayOfDouble1[0] - arrayOfDouble2[0];
      j = 0;
      if (bool) {
        j = 1;
      }
      if (arrayOfDouble1[2] - arrayOfDouble2[2] > arrayOfDouble1[j] - arrayOfDouble2[j]) {
        j = 2;
      }
      if (arrayOfDouble2[j] >= arrayOfDouble1[j])
      {
        paramArrayOfDouble[0] = 0.0D;
        paramArrayOfDouble[1] = 0.0D;
        paramArrayOfDouble[2] = 1.0D;
      }
    }
    else
    {
      for (int i = 0;; i++)
      {
        if (i >= 3)
        {
          localGLUvertex2 = localGLUvertex2.next;
          break;
        }
        double d1 = localGLUvertex2.coords[i];
        if (d1 < arrayOfDouble2[i])
        {
          arrayOfDouble2[i] = d1;
          arrayOfGLUvertex1[i] = localGLUvertex2;
        }
        if (d1 > arrayOfDouble1[i])
        {
          arrayOfDouble1[i] = d1;
          arrayOfGLUvertex2[i] = localGLUvertex2;
        }
      }
    }
    double d2 = 0.0D;
    GLUvertex localGLUvertex3 = arrayOfGLUvertex1[j];
    GLUvertex localGLUvertex4 = arrayOfGLUvertex2[j];
    arrayOfDouble3[0] = (localGLUvertex3.coords[0] - localGLUvertex4.coords[0]);
    arrayOfDouble3[1] = (localGLUvertex3.coords[1] - localGLUvertex4.coords[1]);
    arrayOfDouble3[2] = (localGLUvertex3.coords[2] - localGLUvertex4.coords[2]);
    for (GLUvertex localGLUvertex5 = localGLUvertex1.next;; localGLUvertex5 = localGLUvertex5.next)
    {
      if (localGLUvertex5 == localGLUvertex1)
      {
        if (d2 > 0.0D) {
          break;
        }
        paramArrayOfDouble[2] = 0.0D;
        paramArrayOfDouble[1] = 0.0D;
        paramArrayOfDouble[0] = 0.0D;
        paramArrayOfDouble[LongAxis(arrayOfDouble3)] = 1.0D;
        return;
      }
      arrayOfDouble4[0] = (localGLUvertex5.coords[0] - localGLUvertex4.coords[0]);
      arrayOfDouble4[1] = (localGLUvertex5.coords[1] - localGLUvertex4.coords[1]);
      arrayOfDouble4[2] = (localGLUvertex5.coords[2] - localGLUvertex4.coords[2]);
      arrayOfDouble5[0] = (arrayOfDouble3[1] * arrayOfDouble4[2] - arrayOfDouble3[2] * arrayOfDouble4[1]);
      arrayOfDouble5[1] = (arrayOfDouble3[2] * arrayOfDouble4[0] - arrayOfDouble3[0] * arrayOfDouble4[2]);
      arrayOfDouble5[2] = (arrayOfDouble3[0] * arrayOfDouble4[1] - arrayOfDouble3[1] * arrayOfDouble4[0]);
      double d3 = arrayOfDouble5[0] * arrayOfDouble5[0] + arrayOfDouble5[1] * arrayOfDouble5[1] + arrayOfDouble5[2] * arrayOfDouble5[2];
      if (d3 > d2)
      {
        d2 = d3;
        paramArrayOfDouble[0] = arrayOfDouble5[0];
        paramArrayOfDouble[1] = arrayOfDouble5[1];
        paramArrayOfDouble[2] = arrayOfDouble5[2];
      }
    }
  }
  
  private static double Dot(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
  {
    return paramArrayOfDouble1[0] * paramArrayOfDouble2[0] + paramArrayOfDouble1[1] * paramArrayOfDouble2[1] + paramArrayOfDouble1[2] * paramArrayOfDouble2[2];
  }
  
  static int LongAxis(double[] paramArrayOfDouble)
  {
    boolean bool = Math.abs(paramArrayOfDouble[1]) < Math.abs(paramArrayOfDouble[0]);
    int i = 0;
    if (bool) {
      i = 1;
    }
    if (Math.abs(paramArrayOfDouble[2]) > Math.abs(paramArrayOfDouble[i])) {
      i = 2;
    }
    return i;
  }
  
  static void Normalize(double[] paramArrayOfDouble)
  {
    double d1 = paramArrayOfDouble[0] * paramArrayOfDouble[0] + paramArrayOfDouble[1] * paramArrayOfDouble[1] + paramArrayOfDouble[2] * paramArrayOfDouble[2];
    assert (d1 > 0.0D);
    double d2 = Math.sqrt(d1);
    paramArrayOfDouble[0] /= d2;
    paramArrayOfDouble[1] /= d2;
    paramArrayOfDouble[2] /= d2;
  }
  
  public static void __gl_projectPolygon(GLUtessellatorImpl paramGLUtessellatorImpl)
  {
    GLUvertex localGLUvertex1 = paramGLUtessellatorImpl.mesh.vHead;
    double[] arrayOfDouble1 = new double[3];
    arrayOfDouble1[0] = paramGLUtessellatorImpl.normal[0];
    arrayOfDouble1[1] = paramGLUtessellatorImpl.normal[1];
    arrayOfDouble1[2] = paramGLUtessellatorImpl.normal[2];
    boolean bool1 = arrayOfDouble1[0] < 0.0D;
    int i = 0;
    if (!bool1)
    {
      boolean bool2 = arrayOfDouble1[1] < 0.0D;
      i = 0;
      if (!bool2)
      {
        boolean bool3 = arrayOfDouble1[2] < 0.0D;
        i = 0;
        if (!bool3)
        {
          ComputeNormal(paramGLUtessellatorImpl, arrayOfDouble1);
          i = 1;
        }
      }
    }
    double[] arrayOfDouble2 = paramGLUtessellatorImpl.sUnit;
    double[] arrayOfDouble3 = paramGLUtessellatorImpl.tUnit;
    int j = LongAxis(arrayOfDouble1);
    arrayOfDouble2[j] = 0.0D;
    arrayOfDouble2[((j + 1) % 3)] = S_UNIT_X;
    arrayOfDouble2[((j + 2) % 3)] = S_UNIT_Y;
    arrayOfDouble3[j] = 0.0D;
    int k = (j + 1) % 3;
    double d1;
    double d2;
    if (arrayOfDouble1[j] > 0.0D)
    {
      d1 = -S_UNIT_Y;
      arrayOfDouble3[k] = d1;
      int m = (j + 2) % 3;
      if (arrayOfDouble1[j] <= 0.0D) {
        break label233;
      }
      d2 = S_UNIT_X;
      label196:
      arrayOfDouble3[m] = d2;
    }
    for (GLUvertex localGLUvertex2 = localGLUvertex1.next;; localGLUvertex2 = localGLUvertex2.next)
    {
      if (localGLUvertex2 == localGLUvertex1)
      {
        if (i != 0) {
          CheckOrientation(paramGLUtessellatorImpl);
        }
        return;
        d1 = S_UNIT_Y;
        break;
        label233:
        d2 = -S_UNIT_X;
        break label196;
      }
      localGLUvertex2.s = Dot(localGLUvertex2.coords, arrayOfDouble2);
      localGLUvertex2.t = Dot(localGLUvertex2.coords, arrayOfDouble3);
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.Normal
 * JD-Core Version:    0.7.0.1
 */