package processing.opengl.tess;

class Geom
{
  static final double EPSILON = 1.E-005D;
  static final double ONE_MINUS_EPSILON = 0.9999900000000001D;
  
  static
  {
    if (!Geom.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  static double EdgeCos(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
  {
    double d1 = paramGLUvertex2.s - paramGLUvertex1.s;
    double d2 = paramGLUvertex2.t - paramGLUvertex1.t;
    double d3 = paramGLUvertex3.s - paramGLUvertex1.s;
    double d4 = paramGLUvertex3.t - paramGLUvertex1.t;
    double d5 = d1 * d3 + d2 * d4;
    double d6 = Math.sqrt(d1 * d1 + d2 * d2) * Math.sqrt(d3 * d3 + d4 * d4);
    if (d6 > 0.0D) {
      d5 /= d6;
    }
    return d5;
  }
  
  static double EdgeEval(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
  {
    double d1 = 0.0D;
    assert ((VertLeq(paramGLUvertex1, paramGLUvertex2)) && (VertLeq(paramGLUvertex2, paramGLUvertex3)));
    double d2 = paramGLUvertex2.s - paramGLUvertex1.s;
    double d3 = paramGLUvertex3.s - paramGLUvertex2.s;
    if (d2 + d3 > d1)
    {
      if (d2 < d3) {
        d1 = paramGLUvertex2.t - paramGLUvertex1.t + (paramGLUvertex1.t - paramGLUvertex3.t) * (d2 / (d2 + d3));
      }
    }
    else {
      return d1;
    }
    return paramGLUvertex2.t - paramGLUvertex3.t + (paramGLUvertex3.t - paramGLUvertex1.t) * (d3 / (d2 + d3));
  }
  
  static boolean EdgeGoesLeft(GLUhalfEdge paramGLUhalfEdge)
  {
    return VertLeq(paramGLUhalfEdge.Sym.Org, paramGLUhalfEdge.Org);
  }
  
  static boolean EdgeGoesRight(GLUhalfEdge paramGLUhalfEdge)
  {
    return VertLeq(paramGLUhalfEdge.Org, paramGLUhalfEdge.Sym.Org);
  }
  
  static void EdgeIntersect(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3, GLUvertex paramGLUvertex4, GLUvertex paramGLUvertex5)
  {
    if (!VertLeq(paramGLUvertex1, paramGLUvertex2))
    {
      GLUvertex localGLUvertex8 = paramGLUvertex1;
      paramGLUvertex1 = paramGLUvertex2;
      paramGLUvertex2 = localGLUvertex8;
    }
    if (!VertLeq(paramGLUvertex3, paramGLUvertex4))
    {
      GLUvertex localGLUvertex7 = paramGLUvertex3;
      paramGLUvertex3 = paramGLUvertex4;
      paramGLUvertex4 = localGLUvertex7;
    }
    if (!VertLeq(paramGLUvertex1, paramGLUvertex3))
    {
      GLUvertex localGLUvertex5 = paramGLUvertex1;
      paramGLUvertex1 = paramGLUvertex3;
      paramGLUvertex3 = localGLUvertex5;
      GLUvertex localGLUvertex6 = paramGLUvertex2;
      paramGLUvertex2 = paramGLUvertex4;
      paramGLUvertex4 = localGLUvertex6;
    }
    if (!VertLeq(paramGLUvertex3, paramGLUvertex2)) {
      paramGLUvertex5.s = ((paramGLUvertex3.s + paramGLUvertex2.s) / 2.0D);
    }
    for (;;)
    {
      if (!TransLeq(paramGLUvertex1, paramGLUvertex2))
      {
        GLUvertex localGLUvertex4 = paramGLUvertex1;
        paramGLUvertex1 = paramGLUvertex2;
        paramGLUvertex2 = localGLUvertex4;
      }
      if (!TransLeq(paramGLUvertex3, paramGLUvertex4))
      {
        GLUvertex localGLUvertex3 = paramGLUvertex3;
        paramGLUvertex3 = paramGLUvertex4;
        paramGLUvertex4 = localGLUvertex3;
      }
      if (!TransLeq(paramGLUvertex1, paramGLUvertex3))
      {
        GLUvertex localGLUvertex1 = paramGLUvertex3;
        paramGLUvertex3 = paramGLUvertex1;
        paramGLUvertex1 = localGLUvertex1;
        GLUvertex localGLUvertex2 = paramGLUvertex4;
        paramGLUvertex4 = paramGLUvertex2;
        paramGLUvertex2 = localGLUvertex2;
      }
      if (TransLeq(paramGLUvertex3, paramGLUvertex2)) {
        break;
      }
      paramGLUvertex5.t = ((paramGLUvertex3.t + paramGLUvertex2.t) / 2.0D);
      return;
      if (VertLeq(paramGLUvertex2, paramGLUvertex4))
      {
        double d7 = EdgeEval(paramGLUvertex1, paramGLUvertex3, paramGLUvertex2);
        double d8 = EdgeEval(paramGLUvertex3, paramGLUvertex2, paramGLUvertex4);
        if (d7 + d8 < 0.0D)
        {
          d7 = -d7;
          d8 = -d8;
        }
        paramGLUvertex5.s = Interpolate(d7, paramGLUvertex3.s, d8, paramGLUvertex2.s);
      }
      else
      {
        double d1 = EdgeSign(paramGLUvertex1, paramGLUvertex3, paramGLUvertex2);
        double d2 = -EdgeSign(paramGLUvertex1, paramGLUvertex4, paramGLUvertex2);
        if (d1 + d2 < 0.0D)
        {
          d1 = -d1;
          d2 = -d2;
        }
        paramGLUvertex5.s = Interpolate(d1, paramGLUvertex3.s, d2, paramGLUvertex4.s);
      }
    }
    if (TransLeq(paramGLUvertex2, paramGLUvertex4))
    {
      double d5 = TransEval(paramGLUvertex1, paramGLUvertex3, paramGLUvertex2);
      double d6 = TransEval(paramGLUvertex3, paramGLUvertex2, paramGLUvertex4);
      if (d5 + d6 < 0.0D)
      {
        d5 = -d5;
        d6 = -d6;
      }
      paramGLUvertex5.t = Interpolate(d5, paramGLUvertex3.t, d6, paramGLUvertex2.t);
      return;
    }
    double d3 = TransSign(paramGLUvertex1, paramGLUvertex3, paramGLUvertex2);
    double d4 = -TransSign(paramGLUvertex1, paramGLUvertex4, paramGLUvertex2);
    if (d3 + d4 < 0.0D)
    {
      d3 = -d3;
      d4 = -d4;
    }
    paramGLUvertex5.t = Interpolate(d3, paramGLUvertex3.t, d4, paramGLUvertex4.t);
  }
  
  static double EdgeSign(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
  {
    double d1 = 0.0D;
    assert ((VertLeq(paramGLUvertex1, paramGLUvertex2)) && (VertLeq(paramGLUvertex2, paramGLUvertex3)));
    double d2 = paramGLUvertex2.s - paramGLUvertex1.s;
    double d3 = paramGLUvertex3.s - paramGLUvertex2.s;
    if (d2 + d3 > d1) {
      d1 = d2 * (paramGLUvertex2.t - paramGLUvertex3.t) + d3 * (paramGLUvertex2.t - paramGLUvertex1.t);
    }
    return d1;
  }
  
  static double Interpolate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    if (paramDouble1 < 0.0D) {
      paramDouble1 = 0.0D;
    }
    if (paramDouble3 < 0.0D) {
      paramDouble3 = 0.0D;
    }
    if (paramDouble1 <= paramDouble3)
    {
      if (paramDouble3 == 0.0D) {
        return (paramDouble2 + paramDouble4) / 2.0D;
      }
      return paramDouble2 + (paramDouble4 - paramDouble2) * (paramDouble1 / (paramDouble1 + paramDouble3));
    }
    return paramDouble4 + (paramDouble2 - paramDouble4) * (paramDouble3 / (paramDouble1 + paramDouble3));
  }
  
  static double TransEval(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
  {
    double d1 = 0.0D;
    assert ((TransLeq(paramGLUvertex1, paramGLUvertex2)) && (TransLeq(paramGLUvertex2, paramGLUvertex3)));
    double d2 = paramGLUvertex2.t - paramGLUvertex1.t;
    double d3 = paramGLUvertex3.t - paramGLUvertex2.t;
    if (d2 + d3 > d1)
    {
      if (d2 < d3) {
        d1 = paramGLUvertex2.s - paramGLUvertex1.s + (paramGLUvertex1.s - paramGLUvertex3.s) * (d2 / (d2 + d3));
      }
    }
    else {
      return d1;
    }
    return paramGLUvertex2.s - paramGLUvertex3.s + (paramGLUvertex3.s - paramGLUvertex1.s) * (d3 / (d2 + d3));
  }
  
  static boolean TransLeq(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2)
  {
    return (paramGLUvertex1.t < paramGLUvertex2.t) || ((paramGLUvertex1.t == paramGLUvertex2.t) && (paramGLUvertex1.s <= paramGLUvertex2.s));
  }
  
  static double TransSign(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
  {
    double d1 = 0.0D;
    assert ((TransLeq(paramGLUvertex1, paramGLUvertex2)) && (TransLeq(paramGLUvertex2, paramGLUvertex3)));
    double d2 = paramGLUvertex2.t - paramGLUvertex1.t;
    double d3 = paramGLUvertex3.t - paramGLUvertex2.t;
    if (d2 + d3 > d1) {
      d1 = d2 * (paramGLUvertex2.s - paramGLUvertex3.s) + d3 * (paramGLUvertex2.s - paramGLUvertex1.s);
    }
    return d1;
  }
  
  static boolean VertCCW(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3)
  {
    return paramGLUvertex1.s * (paramGLUvertex2.t - paramGLUvertex3.t) + paramGLUvertex2.s * (paramGLUvertex3.t - paramGLUvertex1.t) + paramGLUvertex3.s * (paramGLUvertex1.t - paramGLUvertex2.t) >= 0.0D;
  }
  
  static boolean VertEq(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2)
  {
    return (paramGLUvertex1.s == paramGLUvertex2.s) && (paramGLUvertex1.t == paramGLUvertex2.t);
  }
  
  static double VertL1dist(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2)
  {
    return Math.abs(paramGLUvertex1.s - paramGLUvertex2.s) + Math.abs(paramGLUvertex1.t - paramGLUvertex2.t);
  }
  
  static boolean VertLeq(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2)
  {
    return (paramGLUvertex1.s < paramGLUvertex2.s) || ((paramGLUvertex1.s == paramGLUvertex2.s) && (paramGLUvertex1.t <= paramGLUvertex2.t));
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.Geom
 * JD-Core Version:    0.7.0.1
 */