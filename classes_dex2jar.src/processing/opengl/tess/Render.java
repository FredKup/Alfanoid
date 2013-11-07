package processing.opengl.tess;

class Render
{
  private static final int SIGN_INCONSISTENT = 2;
  private static final boolean USE_OPTIMIZED_CODE_PATH;
  private static final RenderFan renderFan;
  private static final RenderStrip renderStrip;
  private static final RenderTriangle renderTriangle;
  
  static
  {
    if (!Render.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      renderFan = new RenderFan(null);
      renderStrip = new RenderStrip(null);
      renderTriangle = new RenderTriangle(null);
      return;
    }
  }
  
  private static GLUface AddToTrail(GLUface paramGLUface1, GLUface paramGLUface2)
  {
    paramGLUface1.trail = paramGLUface2;
    paramGLUface1.marked = true;
    return paramGLUface1;
  }
  
  static int ComputeNormal(GLUtessellatorImpl paramGLUtessellatorImpl, double[] paramArrayOfDouble, boolean paramBoolean)
  {
    CachedVertex[] arrayOfCachedVertex = paramGLUtessellatorImpl.cache;
    int i = paramGLUtessellatorImpl.cacheCount;
    double[] arrayOfDouble = new double[3];
    int j = 0;
    if (!paramBoolean)
    {
      paramArrayOfDouble[2] = 0.0D;
      paramArrayOfDouble[1] = 0.0D;
      paramArrayOfDouble[0] = 0.0D;
    }
    int k = 1;
    double d1 = arrayOfCachedVertex[k].coords[0] - arrayOfCachedVertex[0].coords[0];
    double d2 = arrayOfCachedVertex[k].coords[1] - arrayOfCachedVertex[0].coords[1];
    double d3 = arrayOfCachedVertex[k].coords[2] - arrayOfCachedVertex[0].coords[2];
    for (;;)
    {
      k++;
      if (k >= i) {
        return j;
      }
      double d4 = d1;
      double d5 = d2;
      double d6 = d3;
      d1 = arrayOfCachedVertex[k].coords[0] - arrayOfCachedVertex[0].coords[0];
      d2 = arrayOfCachedVertex[k].coords[1] - arrayOfCachedVertex[0].coords[1];
      d3 = arrayOfCachedVertex[k].coords[2] - arrayOfCachedVertex[0].coords[2];
      arrayOfDouble[0] = (d5 * d3 - d6 * d2);
      arrayOfDouble[1] = (d6 * d1 - d4 * d3);
      arrayOfDouble[2] = (d4 * d2 - d5 * d1);
      double d7 = arrayOfDouble[0] * paramArrayOfDouble[0] + arrayOfDouble[1] * paramArrayOfDouble[1] + arrayOfDouble[2] * paramArrayOfDouble[2];
      if (!paramBoolean)
      {
        if (d7 >= 0.0D)
        {
          paramArrayOfDouble[0] += arrayOfDouble[0];
          paramArrayOfDouble[1] += arrayOfDouble[1];
          paramArrayOfDouble[2] += arrayOfDouble[2];
        }
        else
        {
          paramArrayOfDouble[0] -= arrayOfDouble[0];
          paramArrayOfDouble[1] -= arrayOfDouble[1];
          paramArrayOfDouble[2] -= arrayOfDouble[2];
        }
      }
      else if (d7 != 0.0D) {
        if (d7 > 0.0D)
        {
          if (j < 0) {
            return 2;
          }
          j = 1;
        }
        else
        {
          if (j > 0) {
            return 2;
          }
          j = -1;
        }
      }
    }
  }
  
  private static void FreeTrail(GLUface paramGLUface)
  {
    for (;;)
    {
      if (paramGLUface == null) {
        return;
      }
      paramGLUface.marked = false;
      paramGLUface = paramGLUface.trail;
    }
  }
  
  private static boolean IsEven(long paramLong)
  {
    return (1L & paramLong) == 0L;
  }
  
  private static boolean Marked(GLUface paramGLUface)
  {
    return (!paramGLUface.inside) || (paramGLUface.marked);
  }
  
  static FaceCount MaximumFan(GLUhalfEdge paramGLUhalfEdge)
  {
    FaceCount localFaceCount = new FaceCount(0L, null, renderFan);
    GLUface localGLUface = null;
    GLUhalfEdge localGLUhalfEdge1 = paramGLUhalfEdge;
    if (Marked(localGLUhalfEdge1.Lface)) {}
    for (GLUhalfEdge localGLUhalfEdge2 = paramGLUhalfEdge;; localGLUhalfEdge2 = localGLUhalfEdge2.Sym.Lnext)
    {
      if (Marked(localGLUhalfEdge2.Sym.Lface))
      {
        localFaceCount.eStart = localGLUhalfEdge2;
        FreeTrail(localGLUface);
        return localFaceCount;
        localGLUface = AddToTrail(localGLUhalfEdge1.Lface, localGLUface);
        localFaceCount.size = (1L + localFaceCount.size);
        localGLUhalfEdge1 = localGLUhalfEdge1.Onext;
        break;
      }
      localGLUface = AddToTrail(localGLUhalfEdge2.Sym.Lface, localGLUface);
      localFaceCount.size = (1L + localFaceCount.size);
    }
  }
  
  static FaceCount MaximumStrip(GLUhalfEdge paramGLUhalfEdge)
  {
    FaceCount localFaceCount = new FaceCount(0L, null, renderStrip);
    long l1 = 0L;
    long l2 = 0L;
    GLUface localGLUface = null;
    GLUhalfEdge localGLUhalfEdge1 = paramGLUhalfEdge;
    label35:
    GLUhalfEdge localGLUhalfEdge3;
    label42:
    label56:
    GLUhalfEdge localGLUhalfEdge4;
    if (Marked(localGLUhalfEdge1.Lface))
    {
      GLUhalfEdge localGLUhalfEdge2 = localGLUhalfEdge1;
      localGLUhalfEdge3 = paramGLUhalfEdge;
      if (!Marked(localGLUhalfEdge3.Sym.Lface)) {
        break label159;
      }
      localGLUhalfEdge4 = localGLUhalfEdge3;
      localFaceCount.size = (l2 + l1);
      if (!IsEven(l2)) {
        break label237;
      }
      localFaceCount.eStart = localGLUhalfEdge2.Sym;
    }
    for (;;)
    {
      FreeTrail(localGLUface);
      return localFaceCount;
      localGLUface = AddToTrail(localGLUhalfEdge1.Lface, localGLUface);
      l2 += 1L;
      localGLUhalfEdge1 = localGLUhalfEdge1.Lnext.Sym;
      if (Marked(localGLUhalfEdge1.Lface)) {
        break label35;
      }
      localGLUface = AddToTrail(localGLUhalfEdge1.Lface, localGLUface);
      l2 += 1L;
      localGLUhalfEdge1 = localGLUhalfEdge1.Onext;
      break;
      label159:
      localGLUface = AddToTrail(localGLUhalfEdge3.Sym.Lface, localGLUface);
      l1 += 1L;
      localGLUhalfEdge3 = localGLUhalfEdge3.Sym.Lnext;
      if (Marked(localGLUhalfEdge3.Sym.Lface)) {
        break label56;
      }
      localGLUface = AddToTrail(localGLUhalfEdge3.Sym.Lface, localGLUface);
      l1 += 1L;
      localGLUhalfEdge3 = localGLUhalfEdge3.Sym.Onext.Sym;
      break label42;
      label237:
      if (IsEven(l1))
      {
        localFaceCount.eStart = localGLUhalfEdge4;
      }
      else
      {
        localFaceCount.size -= 1L;
        localFaceCount.eStart = localGLUhalfEdge4.Onext;
      }
    }
  }
  
  static void RenderLonelyTriangles(GLUtessellatorImpl paramGLUtessellatorImpl, GLUface paramGLUface)
  {
    int i = -1;
    paramGLUtessellatorImpl.callBeginOrBeginData(4);
    if (paramGLUface == null)
    {
      paramGLUtessellatorImpl.callEndOrEndData();
      return;
    }
    GLUhalfEdge localGLUhalfEdge = paramGLUface.anEdge;
    label21:
    int j;
    if (paramGLUtessellatorImpl.flagBoundary)
    {
      if (localGLUhalfEdge.Sym.Lface.inside) {
        break label98;
      }
      j = 1;
      label44:
      if (i != j)
      {
        i = j;
        if (i == 0) {
          break label104;
        }
      }
    }
    label98:
    label104:
    for (boolean bool = true;; bool = false)
    {
      paramGLUtessellatorImpl.callEdgeFlagOrEdgeFlagData(bool);
      paramGLUtessellatorImpl.callVertexOrVertexData(localGLUhalfEdge.Org.data);
      localGLUhalfEdge = localGLUhalfEdge.Lnext;
      if (localGLUhalfEdge != paramGLUface.anEdge) {
        break label21;
      }
      paramGLUface = paramGLUface.trail;
      break;
      j = 0;
      break label44;
    }
  }
  
  static void RenderMaximumFaceGroup(GLUtessellatorImpl paramGLUtessellatorImpl, GLUface paramGLUface)
  {
    GLUhalfEdge localGLUhalfEdge = paramGLUface.anEdge;
    Object localObject = new FaceCount();
    new FaceCount();
    ((FaceCount)localObject).size = 1L;
    ((FaceCount)localObject).eStart = localGLUhalfEdge;
    ((FaceCount)localObject).render = renderTriangle;
    if (!paramGLUtessellatorImpl.flagBoundary)
    {
      FaceCount localFaceCount1 = MaximumFan(localGLUhalfEdge);
      if (localFaceCount1.size > ((FaceCount)localObject).size) {
        localObject = localFaceCount1;
      }
      FaceCount localFaceCount2 = MaximumFan(localGLUhalfEdge.Lnext);
      if (localFaceCount2.size > ((FaceCount)localObject).size) {
        localObject = localFaceCount2;
      }
      FaceCount localFaceCount3 = MaximumFan(localGLUhalfEdge.Onext.Sym);
      if (localFaceCount3.size > ((FaceCount)localObject).size) {
        localObject = localFaceCount3;
      }
      FaceCount localFaceCount4 = MaximumStrip(localGLUhalfEdge);
      if (localFaceCount4.size > ((FaceCount)localObject).size) {
        localObject = localFaceCount4;
      }
      FaceCount localFaceCount5 = MaximumStrip(localGLUhalfEdge.Lnext);
      if (localFaceCount5.size > ((FaceCount)localObject).size) {
        localObject = localFaceCount5;
      }
      FaceCount localFaceCount6 = MaximumStrip(localGLUhalfEdge.Onext.Sym);
      if (localFaceCount6.size > ((FaceCount)localObject).size) {
        localObject = localFaceCount6;
      }
    }
    ((FaceCount)localObject).render.render(paramGLUtessellatorImpl, ((FaceCount)localObject).eStart, ((FaceCount)localObject).size);
  }
  
  public static void __gl_renderBoundary(GLUtessellatorImpl paramGLUtessellatorImpl, GLUmesh paramGLUmesh)
  {
    for (GLUface localGLUface = paramGLUmesh.fHead.next;; localGLUface = localGLUface.next)
    {
      if (localGLUface == paramGLUmesh.fHead) {
        return;
      }
      if (localGLUface.inside)
      {
        paramGLUtessellatorImpl.callBeginOrBeginData(2);
        GLUhalfEdge localGLUhalfEdge = localGLUface.anEdge;
        do
        {
          paramGLUtessellatorImpl.callVertexOrVertexData(localGLUhalfEdge.Org.data);
          localGLUhalfEdge = localGLUhalfEdge.Lnext;
        } while (localGLUhalfEdge != localGLUface.anEdge);
        paramGLUtessellatorImpl.callEndOrEndData();
      }
    }
  }
  
  public static boolean __gl_renderCache(GLUtessellatorImpl paramGLUtessellatorImpl)
  {
    double[] arrayOfDouble = new double[3];
    if (paramGLUtessellatorImpl.cacheCount < 3) {}
    int i;
    do
    {
      return true;
      arrayOfDouble[0] = paramGLUtessellatorImpl.normal[0];
      arrayOfDouble[1] = paramGLUtessellatorImpl.normal[1];
      arrayOfDouble[2] = paramGLUtessellatorImpl.normal[2];
      if ((arrayOfDouble[0] == 0.0D) && (arrayOfDouble[1] == 0.0D) && (arrayOfDouble[2] == 0.0D)) {
        ComputeNormal(paramGLUtessellatorImpl, arrayOfDouble, false);
      }
      i = ComputeNormal(paramGLUtessellatorImpl, arrayOfDouble, true);
      if (i == 2) {
        return false;
      }
    } while (i == 0);
    return false;
  }
  
  public static void __gl_renderMesh(GLUtessellatorImpl paramGLUtessellatorImpl, GLUmesh paramGLUmesh)
  {
    paramGLUtessellatorImpl.lonelyTriList = null;
    GLUface localGLUface1 = paramGLUmesh.fHead.next;
    if (localGLUface1 == paramGLUmesh.fHead) {}
    for (GLUface localGLUface2 = paramGLUmesh.fHead.next;; localGLUface2 = localGLUface2.next)
    {
      if (localGLUface2 == paramGLUmesh.fHead)
      {
        if (paramGLUtessellatorImpl.lonelyTriList != null)
        {
          RenderLonelyTriangles(paramGLUtessellatorImpl, paramGLUtessellatorImpl.lonelyTriList);
          paramGLUtessellatorImpl.lonelyTriList = null;
        }
        return;
        localGLUface1.marked = false;
        localGLUface1 = localGLUface1.next;
        break;
      }
      if ((localGLUface2.inside) && (!localGLUface2.marked))
      {
        RenderMaximumFaceGroup(paramGLUtessellatorImpl, localGLUface2);
        assert (localGLUface2.marked);
      }
    }
  }
  
  private static class FaceCount
  {
    GLUhalfEdge eStart;
    Render.renderCallBack render;
    long size;
    
    public FaceCount() {}
    
    public FaceCount(long paramLong, GLUhalfEdge paramGLUhalfEdge, Render.renderCallBack paramrenderCallBack)
    {
      this.size = paramLong;
      this.eStart = paramGLUhalfEdge;
      this.render = paramrenderCallBack;
    }
  }
  
  private static class RenderFan
    implements Render.renderCallBack
  {
    static
    {
      if (!Render.class.desiredAssertionStatus()) {}
      for (boolean bool = true;; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }
    
    public void render(GLUtessellatorImpl paramGLUtessellatorImpl, GLUhalfEdge paramGLUhalfEdge, long paramLong)
    {
      paramGLUtessellatorImpl.callBeginOrBeginData(6);
      paramGLUtessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Org.data);
      paramGLUtessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Sym.Org.data);
      for (;;)
      {
        if (Render.Marked(paramGLUhalfEdge.Lface))
        {
          if (($assertionsDisabled) || (paramLong == 0L)) {
            break;
          }
          throw new AssertionError();
        }
        paramGLUhalfEdge.Lface.marked = true;
        paramLong -= 1L;
        paramGLUhalfEdge = paramGLUhalfEdge.Onext;
        paramGLUtessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Sym.Org.data);
      }
      paramGLUtessellatorImpl.callEndOrEndData();
    }
  }
  
  private static class RenderStrip
    implements Render.renderCallBack
  {
    static
    {
      if (!Render.class.desiredAssertionStatus()) {}
      for (boolean bool = true;; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }
    
    public void render(GLUtessellatorImpl paramGLUtessellatorImpl, GLUhalfEdge paramGLUhalfEdge, long paramLong)
    {
      paramGLUtessellatorImpl.callBeginOrBeginData(5);
      paramGLUtessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Org.data);
      paramGLUtessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Sym.Org.data);
      for (;;)
      {
        if (Render.Marked(paramGLUhalfEdge.Lface)) {}
        GLUhalfEdge localGLUhalfEdge;
        do
        {
          if (($assertionsDisabled) || (paramLong == 0L)) {
            break;
          }
          throw new AssertionError();
          paramGLUhalfEdge.Lface.marked = true;
          paramLong -= 1L;
          localGLUhalfEdge = paramGLUhalfEdge.Lnext.Sym;
          paramGLUtessellatorImpl.callVertexOrVertexData(localGLUhalfEdge.Org.data);
        } while (Render.Marked(localGLUhalfEdge.Lface));
        localGLUhalfEdge.Lface.marked = true;
        paramLong -= 1L;
        paramGLUhalfEdge = localGLUhalfEdge.Onext;
        paramGLUtessellatorImpl.callVertexOrVertexData(paramGLUhalfEdge.Sym.Org.data);
      }
      paramGLUtessellatorImpl.callEndOrEndData();
    }
  }
  
  private static class RenderTriangle
    implements Render.renderCallBack
  {
    static
    {
      if (!Render.class.desiredAssertionStatus()) {}
      for (boolean bool = true;; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }
    
    public void render(GLUtessellatorImpl paramGLUtessellatorImpl, GLUhalfEdge paramGLUhalfEdge, long paramLong)
    {
      assert (paramLong == 1L);
      paramGLUtessellatorImpl.lonelyTriList = Render.AddToTrail(paramGLUhalfEdge.Lface, paramGLUtessellatorImpl.lonelyTriList);
    }
  }
  
  private static abstract interface renderCallBack
  {
    public abstract void render(GLUtessellatorImpl paramGLUtessellatorImpl, GLUhalfEdge paramGLUhalfEdge, long paramLong);
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.Render
 * JD-Core Version:    0.7.0.1
 */