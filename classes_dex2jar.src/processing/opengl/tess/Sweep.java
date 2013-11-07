package processing.opengl.tess;

class Sweep
{
  private static final double SENTINEL_COORD = 4.0E+150D;
  private static final boolean TOLERANCE_NONZERO;
  
  static
  {
    if (!Sweep.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  static ActiveRegion AddRegionBelow(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion, GLUhalfEdge paramGLUhalfEdge)
  {
    ActiveRegion localActiveRegion = new ActiveRegion();
    if (localActiveRegion == null) {
      throw new RuntimeException();
    }
    localActiveRegion.eUp = paramGLUhalfEdge;
    localActiveRegion.nodeUp = Dict.dictInsertBefore(paramGLUtessellatorImpl.dict, paramActiveRegion.nodeUp, localActiveRegion);
    if (localActiveRegion.nodeUp == null) {
      throw new RuntimeException();
    }
    localActiveRegion.fixUpperEdge = false;
    localActiveRegion.sentinel = false;
    localActiveRegion.dirty = false;
    paramGLUhalfEdge.activeRegion = localActiveRegion;
    return localActiveRegion;
  }
  
  static void AddRightEdges(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion, GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2, GLUhalfEdge paramGLUhalfEdge3, boolean paramBoolean)
  {
    int i = 1;
    GLUhalfEdge localGLUhalfEdge1 = paramGLUhalfEdge1;
    do
    {
      assert (Geom.VertLeq(localGLUhalfEdge1.Org, localGLUhalfEdge1.Sym.Org));
      AddRegionBelow(paramGLUtessellatorImpl, paramActiveRegion, localGLUhalfEdge1.Sym);
      localGLUhalfEdge1 = localGLUhalfEdge1.Onext;
    } while (localGLUhalfEdge1 != paramGLUhalfEdge2);
    if (paramGLUhalfEdge3 == null) {
      paramGLUhalfEdge3 = RegionBelow(paramActiveRegion).eUp.Sym.Onext;
    }
    Object localObject1 = paramActiveRegion;
    Object localObject2 = paramGLUhalfEdge3;
    for (;;)
    {
      ActiveRegion localActiveRegion = RegionBelow((ActiveRegion)localObject1);
      GLUhalfEdge localGLUhalfEdge2 = localActiveRegion.eUp.Sym;
      if (localGLUhalfEdge2.Org != ((GLUhalfEdge)localObject2).Org)
      {
        ((ActiveRegion)localObject1).dirty = true;
        if (($assertionsDisabled) || (((ActiveRegion)localObject1).windingNumber - localGLUhalfEdge2.winding == localActiveRegion.windingNumber)) {
          break;
        }
        throw new AssertionError();
      }
      if (localGLUhalfEdge2.Onext != localObject2)
      {
        if (!Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym.Lnext, localGLUhalfEdge2)) {
          throw new RuntimeException();
        }
        if (!Mesh.__gl_meshSplice(((GLUhalfEdge)localObject2).Sym.Lnext, localGLUhalfEdge2)) {
          throw new RuntimeException();
        }
      }
      localActiveRegion.windingNumber = (((ActiveRegion)localObject1).windingNumber - localGLUhalfEdge2.winding);
      localActiveRegion.inside = IsWindingInside(paramGLUtessellatorImpl, localActiveRegion.windingNumber);
      ((ActiveRegion)localObject1).dirty = true;
      if ((i == 0) && (CheckForRightSplice(paramGLUtessellatorImpl, (ActiveRegion)localObject1)))
      {
        AddWinding(localGLUhalfEdge2, (GLUhalfEdge)localObject2);
        DeleteRegion(paramGLUtessellatorImpl, (ActiveRegion)localObject1);
        if (!Mesh.__gl_meshDelete((GLUhalfEdge)localObject2)) {
          throw new RuntimeException();
        }
      }
      localObject1 = localActiveRegion;
      localObject2 = localGLUhalfEdge2;
      i = 0;
    }
    if (paramBoolean) {
      WalkDirtyRegions(paramGLUtessellatorImpl, (ActiveRegion)localObject1);
    }
  }
  
  static void AddSentinel(GLUtessellatorImpl paramGLUtessellatorImpl, double paramDouble)
  {
    ActiveRegion localActiveRegion = new ActiveRegion();
    if (localActiveRegion == null) {
      throw new RuntimeException();
    }
    GLUhalfEdge localGLUhalfEdge = Mesh.__gl_meshMakeEdge(paramGLUtessellatorImpl.mesh);
    if (localGLUhalfEdge == null) {
      throw new RuntimeException();
    }
    localGLUhalfEdge.Org.s = 4.0E+150D;
    localGLUhalfEdge.Org.t = paramDouble;
    localGLUhalfEdge.Sym.Org.s = -4.0E+150D;
    localGLUhalfEdge.Sym.Org.t = paramDouble;
    paramGLUtessellatorImpl.event = localGLUhalfEdge.Sym.Org;
    localActiveRegion.eUp = localGLUhalfEdge;
    localActiveRegion.windingNumber = 0;
    localActiveRegion.inside = false;
    localActiveRegion.fixUpperEdge = false;
    localActiveRegion.sentinel = true;
    localActiveRegion.dirty = false;
    localActiveRegion.nodeUp = Dict.dictInsert(paramGLUtessellatorImpl.dict, localActiveRegion);
    if (localActiveRegion.nodeUp == null) {
      throw new RuntimeException();
    }
  }
  
  private static void AddWinding(GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2)
  {
    paramGLUhalfEdge1.winding += paramGLUhalfEdge2.winding;
    GLUhalfEdge localGLUhalfEdge = paramGLUhalfEdge1.Sym;
    localGLUhalfEdge.winding += paramGLUhalfEdge2.Sym.winding;
  }
  
  static void CallCombine(GLUtessellatorImpl paramGLUtessellatorImpl, GLUvertex paramGLUvertex, Object[] paramArrayOfObject, float[] paramArrayOfFloat, boolean paramBoolean)
  {
    double[] arrayOfDouble = new double[3];
    arrayOfDouble[0] = paramGLUvertex.coords[0];
    arrayOfDouble[1] = paramGLUvertex.coords[1];
    arrayOfDouble[2] = paramGLUvertex.coords[2];
    Object[] arrayOfObject = new Object[1];
    paramGLUtessellatorImpl.callCombineOrCombineData(arrayOfDouble, paramArrayOfObject, paramArrayOfFloat, arrayOfObject);
    paramGLUvertex.data = arrayOfObject[0];
    if (paramGLUvertex.data == null)
    {
      if (paramBoolean) {
        break label79;
      }
      paramGLUvertex.data = paramArrayOfObject[0];
    }
    label79:
    while (paramGLUtessellatorImpl.fatalError) {
      return;
    }
    paramGLUtessellatorImpl.callErrorOrErrorData(100156);
    paramGLUtessellatorImpl.fatalError = true;
  }
  
  static boolean CheckForIntersect(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion)
  {
    ActiveRegion localActiveRegion1 = RegionBelow(paramActiveRegion);
    GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion.eUp;
    GLUhalfEdge localGLUhalfEdge2 = localActiveRegion1.eUp;
    GLUvertex localGLUvertex1 = localGLUhalfEdge1.Org;
    GLUvertex localGLUvertex2 = localGLUhalfEdge2.Org;
    GLUvertex localGLUvertex3 = localGLUhalfEdge1.Sym.Org;
    GLUvertex localGLUvertex4 = localGLUhalfEdge2.Sym.Org;
    GLUvertex localGLUvertex5 = new GLUvertex();
    assert (!Geom.VertEq(localGLUvertex4, localGLUvertex3));
    assert (Geom.EdgeSign(localGLUvertex3, paramGLUtessellatorImpl.event, localGLUvertex1) <= 0.0D);
    assert (Geom.EdgeSign(localGLUvertex4, paramGLUtessellatorImpl.event, localGLUvertex2) >= 0.0D);
    assert ((localGLUvertex1 != paramGLUtessellatorImpl.event) && (localGLUvertex2 != paramGLUtessellatorImpl.event));
    assert ((!paramActiveRegion.fixUpperEdge) && (!localActiveRegion1.fixUpperEdge));
    if (localGLUvertex1 == localGLUvertex2) {
      return false;
    }
    if (Math.min(localGLUvertex1.t, localGLUvertex3.t) > Math.max(localGLUvertex2.t, localGLUvertex4.t)) {
      return false;
    }
    if (Geom.VertLeq(localGLUvertex1, localGLUvertex2))
    {
      if (Geom.EdgeSign(localGLUvertex4, localGLUvertex1, localGLUvertex2) > 0.0D) {
        return false;
      }
    }
    else if (Geom.EdgeSign(localGLUvertex3, localGLUvertex2, localGLUvertex1) < 0.0D) {
      return false;
    }
    DebugEvent(paramGLUtessellatorImpl);
    Geom.EdgeIntersect(localGLUvertex3, localGLUvertex1, localGLUvertex4, localGLUvertex2, localGLUvertex5);
    assert (Math.min(localGLUvertex1.t, localGLUvertex3.t) <= localGLUvertex5.t);
    assert (localGLUvertex5.t <= Math.max(localGLUvertex2.t, localGLUvertex4.t));
    assert (Math.min(localGLUvertex4.s, localGLUvertex3.s) <= localGLUvertex5.s);
    assert (localGLUvertex5.s <= Math.max(localGLUvertex2.s, localGLUvertex1.s));
    if (Geom.VertLeq(localGLUvertex5, paramGLUtessellatorImpl.event))
    {
      localGLUvertex5.s = paramGLUtessellatorImpl.event.s;
      localGLUvertex5.t = paramGLUtessellatorImpl.event.t;
    }
    if (Geom.VertLeq(localGLUvertex1, localGLUvertex2)) {}
    for (GLUvertex localGLUvertex6 = localGLUvertex1;; localGLUvertex6 = localGLUvertex2)
    {
      if (Geom.VertLeq(localGLUvertex6, localGLUvertex5))
      {
        localGLUvertex5.s = localGLUvertex6.s;
        localGLUvertex5.t = localGLUvertex6.t;
      }
      if ((!Geom.VertEq(localGLUvertex5, localGLUvertex1)) && (!Geom.VertEq(localGLUvertex5, localGLUvertex2))) {
        break;
      }
      CheckForRightSplice(paramGLUtessellatorImpl, paramActiveRegion);
      return false;
    }
    if (((!Geom.VertEq(localGLUvertex3, paramGLUtessellatorImpl.event)) && (Geom.EdgeSign(localGLUvertex3, paramGLUtessellatorImpl.event, localGLUvertex5) >= 0.0D)) || ((!Geom.VertEq(localGLUvertex4, paramGLUtessellatorImpl.event)) && (Geom.EdgeSign(localGLUvertex4, paramGLUtessellatorImpl.event, localGLUvertex5) <= 0.0D)))
    {
      if (localGLUvertex4 == paramGLUtessellatorImpl.event)
      {
        if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge1.Sym) == null) {
          throw new RuntimeException();
        }
        if (!Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym, localGLUhalfEdge1)) {
          throw new RuntimeException();
        }
        ActiveRegion localActiveRegion5 = TopLeftRegion(paramActiveRegion);
        if (localActiveRegion5 == null) {
          throw new RuntimeException();
        }
        GLUhalfEdge localGLUhalfEdge4 = RegionBelow(localActiveRegion5).eUp;
        FinishLeftRegions(paramGLUtessellatorImpl, RegionBelow(localActiveRegion5), localActiveRegion1);
        AddRightEdges(paramGLUtessellatorImpl, localActiveRegion5, localGLUhalfEdge4.Sym.Lnext, localGLUhalfEdge4, localGLUhalfEdge4, true);
        return true;
      }
      if (localGLUvertex3 == paramGLUtessellatorImpl.event)
      {
        if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge2.Sym) == null) {
          throw new RuntimeException();
        }
        if (!Mesh.__gl_meshSplice(localGLUhalfEdge1.Lnext, localGLUhalfEdge2.Sym.Lnext)) {
          throw new RuntimeException();
        }
        ActiveRegion localActiveRegion4 = TopRightRegion(paramActiveRegion);
        GLUhalfEdge localGLUhalfEdge3 = RegionBelow(localActiveRegion4).eUp.Sym.Onext;
        paramActiveRegion.eUp = localGLUhalfEdge2.Sym.Lnext;
        AddRightEdges(paramGLUtessellatorImpl, localActiveRegion4, FinishLeftRegions(paramGLUtessellatorImpl, paramActiveRegion, null).Onext, localGLUhalfEdge1.Sym.Onext, localGLUhalfEdge3, true);
        return true;
      }
      if (Geom.EdgeSign(localGLUvertex3, paramGLUtessellatorImpl.event, localGLUvertex5) >= 0.0D)
      {
        ActiveRegion localActiveRegion3 = RegionAbove(paramActiveRegion);
        paramActiveRegion.dirty = true;
        localActiveRegion3.dirty = true;
        if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge1.Sym) == null) {
          throw new RuntimeException();
        }
        localGLUhalfEdge1.Org.s = paramGLUtessellatorImpl.event.s;
        localGLUhalfEdge1.Org.t = paramGLUtessellatorImpl.event.t;
      }
      if (Geom.EdgeSign(localGLUvertex4, paramGLUtessellatorImpl.event, localGLUvertex5) <= 0.0D)
      {
        localActiveRegion1.dirty = true;
        paramActiveRegion.dirty = true;
        if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge2.Sym) == null) {
          throw new RuntimeException();
        }
        localGLUhalfEdge2.Org.s = paramGLUtessellatorImpl.event.s;
        localGLUhalfEdge2.Org.t = paramGLUtessellatorImpl.event.t;
      }
      return false;
    }
    if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge1.Sym) == null) {
      throw new RuntimeException();
    }
    if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge2.Sym) == null) {
      throw new RuntimeException();
    }
    if (!Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym.Lnext, localGLUhalfEdge1)) {
      throw new RuntimeException();
    }
    localGLUhalfEdge1.Org.s = localGLUvertex5.s;
    localGLUhalfEdge1.Org.t = localGLUvertex5.t;
    localGLUhalfEdge1.Org.pqHandle = paramGLUtessellatorImpl.pq.pqInsert(localGLUhalfEdge1.Org);
    if (localGLUhalfEdge1.Org.pqHandle == 9223372036854775807L)
    {
      paramGLUtessellatorImpl.pq.pqDeletePriorityQ();
      paramGLUtessellatorImpl.pq = null;
      throw new RuntimeException();
    }
    GetIntersectData(paramGLUtessellatorImpl, localGLUhalfEdge1.Org, localGLUvertex1, localGLUvertex3, localGLUvertex2, localGLUvertex4);
    ActiveRegion localActiveRegion2 = RegionAbove(paramActiveRegion);
    localActiveRegion1.dirty = true;
    paramActiveRegion.dirty = true;
    localActiveRegion2.dirty = true;
    return false;
  }
  
  static boolean CheckForLeftSplice(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion)
  {
    ActiveRegion localActiveRegion1 = RegionBelow(paramActiveRegion);
    GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion.eUp;
    GLUhalfEdge localGLUhalfEdge2 = localActiveRegion1.eUp;
    assert (!Geom.VertEq(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Sym.Org));
    GLUhalfEdge localGLUhalfEdge4;
    if (Geom.VertLeq(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Sym.Org))
    {
      if (Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Sym.Org, localGLUhalfEdge1.Org) < 0.0D) {
        return false;
      }
      ActiveRegion localActiveRegion2 = RegionAbove(paramActiveRegion);
      paramActiveRegion.dirty = true;
      localActiveRegion2.dirty = true;
      localGLUhalfEdge4 = Mesh.__gl_meshSplitEdge(localGLUhalfEdge1);
      if (localGLUhalfEdge4 == null) {
        throw new RuntimeException();
      }
      if (!Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym, localGLUhalfEdge4)) {
        throw new RuntimeException();
      }
    }
    GLUhalfEdge localGLUhalfEdge3;
    for (localGLUhalfEdge4.Lface.inside = paramActiveRegion.inside;; localGLUhalfEdge3.Sym.Lface.inside = paramActiveRegion.inside)
    {
      return true;
      if (Geom.EdgeSign(localGLUhalfEdge2.Sym.Org, localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Org) > 0.0D) {
        break;
      }
      localActiveRegion1.dirty = true;
      paramActiveRegion.dirty = true;
      localGLUhalfEdge3 = Mesh.__gl_meshSplitEdge(localGLUhalfEdge2);
      if (localGLUhalfEdge3 == null) {
        throw new RuntimeException();
      }
      if (!Mesh.__gl_meshSplice(localGLUhalfEdge1.Lnext, localGLUhalfEdge2.Sym)) {
        throw new RuntimeException();
      }
    }
  }
  
  static boolean CheckForRightSplice(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion)
  {
    ActiveRegion localActiveRegion1 = RegionBelow(paramActiveRegion);
    GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion.eUp;
    GLUhalfEdge localGLUhalfEdge2 = localActiveRegion1.eUp;
    if (Geom.VertLeq(localGLUhalfEdge1.Org, localGLUhalfEdge2.Org))
    {
      if (Geom.EdgeSign(localGLUhalfEdge2.Sym.Org, localGLUhalfEdge1.Org, localGLUhalfEdge2.Org) > 0.0D) {
        return false;
      }
      if (!Geom.VertEq(localGLUhalfEdge1.Org, localGLUhalfEdge2.Org))
      {
        if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge2.Sym) == null) {
          throw new RuntimeException();
        }
        if (!Mesh.__gl_meshSplice(localGLUhalfEdge1, localGLUhalfEdge2.Sym.Lnext)) {
          throw new RuntimeException();
        }
        localActiveRegion1.dirty = true;
        paramActiveRegion.dirty = true;
      }
    }
    do
    {
      for (;;)
      {
        return true;
        if (localGLUhalfEdge1.Org != localGLUhalfEdge2.Org)
        {
          paramGLUtessellatorImpl.pq.pqDelete(localGLUhalfEdge1.Org.pqHandle);
          SpliceMergeVertices(paramGLUtessellatorImpl, localGLUhalfEdge2.Sym.Lnext, localGLUhalfEdge1);
        }
      }
      if (Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Org, localGLUhalfEdge1.Org) < 0.0D) {
        break;
      }
      ActiveRegion localActiveRegion2 = RegionAbove(paramActiveRegion);
      paramActiveRegion.dirty = true;
      localActiveRegion2.dirty = true;
      if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge1.Sym) == null) {
        throw new RuntimeException();
      }
    } while (Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym.Lnext, localGLUhalfEdge1));
    throw new RuntimeException();
  }
  
  static void ComputeWinding(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion)
  {
    paramActiveRegion.windingNumber = (RegionAbove(paramActiveRegion).windingNumber + paramActiveRegion.eUp.winding);
    paramActiveRegion.inside = IsWindingInside(paramGLUtessellatorImpl, paramActiveRegion.windingNumber);
  }
  
  static void ConnectLeftDegenerate(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion, GLUvertex paramGLUvertex)
  {
    GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion.eUp;
    if (Geom.VertEq(localGLUhalfEdge1.Org, paramGLUvertex))
    {
      if (!$assertionsDisabled) {
        throw new AssertionError();
      }
      SpliceMergeVertices(paramGLUtessellatorImpl, localGLUhalfEdge1, paramGLUvertex.anEdge);
      return;
    }
    if (!Geom.VertEq(localGLUhalfEdge1.Sym.Org, paramGLUvertex))
    {
      if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge1.Sym) == null) {
        throw new RuntimeException();
      }
      if (paramActiveRegion.fixUpperEdge)
      {
        if (!Mesh.__gl_meshDelete(localGLUhalfEdge1.Onext)) {
          throw new RuntimeException();
        }
        paramActiveRegion.fixUpperEdge = false;
      }
      if (!Mesh.__gl_meshSplice(paramGLUvertex.anEdge, localGLUhalfEdge1)) {
        throw new RuntimeException();
      }
      SweepEvent(paramGLUtessellatorImpl, paramGLUvertex);
      return;
    }
    if (!$assertionsDisabled) {
      throw new AssertionError();
    }
    ActiveRegion localActiveRegion1 = TopRightRegion(paramActiveRegion);
    ActiveRegion localActiveRegion2 = RegionBelow(localActiveRegion1);
    GLUhalfEdge localGLUhalfEdge2 = localActiveRegion2.eUp.Sym;
    GLUhalfEdge localGLUhalfEdge3 = localGLUhalfEdge2.Onext;
    GLUhalfEdge localGLUhalfEdge4 = localGLUhalfEdge3;
    if (localActiveRegion2.fixUpperEdge)
    {
      assert (localGLUhalfEdge4 != localGLUhalfEdge2);
      DeleteRegion(paramGLUtessellatorImpl, localActiveRegion2);
      if (!Mesh.__gl_meshDelete(localGLUhalfEdge2)) {
        throw new RuntimeException();
      }
      localGLUhalfEdge2 = localGLUhalfEdge4.Sym.Lnext;
    }
    if (!Mesh.__gl_meshSplice(paramGLUvertex.anEdge, localGLUhalfEdge2)) {
      throw new RuntimeException();
    }
    if (!Geom.EdgeGoesLeft(localGLUhalfEdge4)) {
      localGLUhalfEdge4 = null;
    }
    AddRightEdges(paramGLUtessellatorImpl, localActiveRegion1, localGLUhalfEdge2.Onext, localGLUhalfEdge3, localGLUhalfEdge4, true);
  }
  
  static void ConnectLeftVertex(GLUtessellatorImpl paramGLUtessellatorImpl, GLUvertex paramGLUvertex)
  {
    ActiveRegion localActiveRegion1 = new ActiveRegion();
    localActiveRegion1.eUp = paramGLUvertex.anEdge.Sym;
    ActiveRegion localActiveRegion2 = (ActiveRegion)Dict.dictKey(Dict.dictSearch(paramGLUtessellatorImpl.dict, localActiveRegion1));
    ActiveRegion localActiveRegion3 = RegionBelow(localActiveRegion2);
    GLUhalfEdge localGLUhalfEdge1 = localActiveRegion2.eUp;
    GLUhalfEdge localGLUhalfEdge2 = localActiveRegion3.eUp;
    if (Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, paramGLUvertex, localGLUhalfEdge1.Org) == 0.0D)
    {
      ConnectLeftDegenerate(paramGLUtessellatorImpl, localActiveRegion2, paramGLUvertex);
      return;
    }
    ActiveRegion localActiveRegion4;
    if (Geom.VertLeq(localGLUhalfEdge2.Sym.Org, localGLUhalfEdge1.Sym.Org)) {
      localActiveRegion4 = localActiveRegion2;
    }
    while ((localActiveRegion2.inside) || (localActiveRegion4.fixUpperEdge))
    {
      GLUhalfEdge localGLUhalfEdge4;
      if (localActiveRegion4 == localActiveRegion2)
      {
        localGLUhalfEdge4 = Mesh.__gl_meshConnect(paramGLUvertex.anEdge.Sym, localGLUhalfEdge1.Lnext);
        if (localGLUhalfEdge4 == null)
        {
          throw new RuntimeException();
          localActiveRegion4 = localActiveRegion3;
        }
      }
      else
      {
        GLUhalfEdge localGLUhalfEdge3 = Mesh.__gl_meshConnect(localGLUhalfEdge2.Sym.Onext.Sym, paramGLUvertex.anEdge);
        if (localGLUhalfEdge3 == null) {
          throw new RuntimeException();
        }
        localGLUhalfEdge4 = localGLUhalfEdge3.Sym;
      }
      if (localActiveRegion4.fixUpperEdge)
      {
        if (!FixUpperEdge(localActiveRegion4, localGLUhalfEdge4)) {
          throw new RuntimeException();
        }
      }
      else {
        ComputeWinding(paramGLUtessellatorImpl, AddRegionBelow(paramGLUtessellatorImpl, localActiveRegion2, localGLUhalfEdge4));
      }
      SweepEvent(paramGLUtessellatorImpl, paramGLUvertex);
      return;
    }
    AddRightEdges(paramGLUtessellatorImpl, localActiveRegion2, paramGLUvertex.anEdge, paramGLUvertex.anEdge, null, true);
  }
  
  static void ConnectRightVertex(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion, GLUhalfEdge paramGLUhalfEdge)
  {
    GLUhalfEdge localGLUhalfEdge1 = paramGLUhalfEdge.Onext;
    ActiveRegion localActiveRegion = RegionBelow(paramActiveRegion);
    GLUhalfEdge localGLUhalfEdge2 = paramActiveRegion.eUp;
    GLUhalfEdge localGLUhalfEdge3 = localActiveRegion.eUp;
    if (localGLUhalfEdge2.Sym.Org != localGLUhalfEdge3.Sym.Org) {
      CheckForIntersect(paramGLUtessellatorImpl, paramActiveRegion);
    }
    boolean bool = Geom.VertEq(localGLUhalfEdge2.Org, paramGLUtessellatorImpl.event);
    int i = 0;
    if (bool)
    {
      if (!Mesh.__gl_meshSplice(localGLUhalfEdge1.Sym.Lnext, localGLUhalfEdge2)) {
        throw new RuntimeException();
      }
      paramActiveRegion = TopLeftRegion(paramActiveRegion);
      if (paramActiveRegion == null) {
        throw new RuntimeException();
      }
      localGLUhalfEdge1 = RegionBelow(paramActiveRegion).eUp;
      FinishLeftRegions(paramGLUtessellatorImpl, RegionBelow(paramActiveRegion), localActiveRegion);
      i = 1;
    }
    if (Geom.VertEq(localGLUhalfEdge3.Org, paramGLUtessellatorImpl.event))
    {
      GLUhalfEdge localGLUhalfEdge9 = localGLUhalfEdge3.Sym.Lnext;
      if (!Mesh.__gl_meshSplice(paramGLUhalfEdge, localGLUhalfEdge9)) {
        throw new RuntimeException();
      }
      paramGLUhalfEdge = FinishLeftRegions(paramGLUtessellatorImpl, localActiveRegion, null);
      i = 1;
    }
    if (i != 0)
    {
      GLUhalfEdge localGLUhalfEdge8 = paramGLUhalfEdge.Onext;
      AddRightEdges(paramGLUtessellatorImpl, paramActiveRegion, localGLUhalfEdge8, localGLUhalfEdge1, localGLUhalfEdge1, true);
      return;
    }
    if (Geom.VertLeq(localGLUhalfEdge3.Org, localGLUhalfEdge2.Org)) {}
    GLUhalfEdge localGLUhalfEdge5;
    for (GLUhalfEdge localGLUhalfEdge4 = localGLUhalfEdge3.Sym.Lnext;; localGLUhalfEdge4 = localGLUhalfEdge2)
    {
      localGLUhalfEdge5 = Mesh.__gl_meshConnect(paramGLUhalfEdge.Onext.Sym, localGLUhalfEdge4);
      if (localGLUhalfEdge5 != null) {
        break;
      }
      throw new RuntimeException();
    }
    GLUhalfEdge localGLUhalfEdge6 = localGLUhalfEdge5.Onext;
    GLUhalfEdge localGLUhalfEdge7 = localGLUhalfEdge5.Onext;
    AddRightEdges(paramGLUtessellatorImpl, paramActiveRegion, localGLUhalfEdge5, localGLUhalfEdge6, localGLUhalfEdge7, false);
    localGLUhalfEdge5.Sym.activeRegion.fixUpperEdge = true;
    WalkDirtyRegions(paramGLUtessellatorImpl, paramActiveRegion);
  }
  
  private static void DebugEvent(GLUtessellatorImpl paramGLUtessellatorImpl) {}
  
  static void DeleteRegion(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion)
  {
    if ((paramActiveRegion.fixUpperEdge) && (!$assertionsDisabled) && (paramActiveRegion.eUp.winding != 0)) {
      throw new AssertionError();
    }
    paramActiveRegion.eUp.activeRegion = null;
    Dict.dictDelete(paramGLUtessellatorImpl.dict, paramActiveRegion.nodeUp);
  }
  
  static void DoneEdgeDict(GLUtessellatorImpl paramGLUtessellatorImpl)
  {
    int i = 0;
    for (;;)
    {
      ActiveRegion localActiveRegion = (ActiveRegion)Dict.dictKey(Dict.dictMin(paramGLUtessellatorImpl.dict));
      if (localActiveRegion == null)
      {
        Dict.dictDeleteDict(paramGLUtessellatorImpl.dict);
        return;
      }
      if (!localActiveRegion.sentinel)
      {
        assert (localActiveRegion.fixUpperEdge);
        if (!$assertionsDisabled)
        {
          i++;
          if (i != 1) {
            throw new AssertionError();
          }
        }
      }
      assert (localActiveRegion.windingNumber == 0);
      DeleteRegion(paramGLUtessellatorImpl, localActiveRegion);
    }
  }
  
  static void DonePriorityQ(GLUtessellatorImpl paramGLUtessellatorImpl)
  {
    paramGLUtessellatorImpl.pq.pqDeletePriorityQ();
  }
  
  static boolean EdgeLeq(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion1, ActiveRegion paramActiveRegion2)
  {
    GLUvertex localGLUvertex = paramGLUtessellatorImpl.event;
    GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion1.eUp;
    GLUhalfEdge localGLUhalfEdge2 = paramActiveRegion2.eUp;
    if (localGLUhalfEdge1.Sym.Org == localGLUvertex)
    {
      if (localGLUhalfEdge2.Sym.Org == localGLUvertex)
      {
        if (Geom.VertLeq(localGLUhalfEdge1.Org, localGLUhalfEdge2.Org)) {
          return Geom.EdgeSign(localGLUhalfEdge2.Sym.Org, localGLUhalfEdge1.Org, localGLUhalfEdge2.Org) <= 0.0D;
        }
        return Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Org, localGLUhalfEdge1.Org) >= 0.0D;
      }
      return Geom.EdgeSign(localGLUhalfEdge2.Sym.Org, localGLUvertex, localGLUhalfEdge2.Org) <= 0.0D;
    }
    if (localGLUhalfEdge2.Sym.Org == localGLUvertex) {
      return Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, localGLUvertex, localGLUhalfEdge1.Org) >= 0.0D;
    }
    return Geom.EdgeEval(localGLUhalfEdge1.Sym.Org, localGLUvertex, localGLUhalfEdge1.Org) >= Geom.EdgeEval(localGLUhalfEdge2.Sym.Org, localGLUvertex, localGLUhalfEdge2.Org);
  }
  
  static GLUhalfEdge FinishLeftRegions(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion1, ActiveRegion paramActiveRegion2)
  {
    Object localObject = paramActiveRegion1;
    GLUhalfEdge localGLUhalfEdge1 = paramActiveRegion1.eUp;
    for (;;)
    {
      if (localObject == paramActiveRegion2) {
        return localGLUhalfEdge1;
      }
      ((ActiveRegion)localObject).fixUpperEdge = false;
      ActiveRegion localActiveRegion = RegionBelow((ActiveRegion)localObject);
      GLUhalfEdge localGLUhalfEdge2 = localActiveRegion.eUp;
      if (localGLUhalfEdge2.Org != localGLUhalfEdge1.Org)
      {
        if (!localActiveRegion.fixUpperEdge)
        {
          FinishRegion(paramGLUtessellatorImpl, (ActiveRegion)localObject);
          return localGLUhalfEdge1;
        }
        localGLUhalfEdge2 = Mesh.__gl_meshConnect(localGLUhalfEdge1.Onext.Sym, localGLUhalfEdge2.Sym);
        if (localGLUhalfEdge2 == null) {
          throw new RuntimeException();
        }
        if (!FixUpperEdge(localActiveRegion, localGLUhalfEdge2)) {
          throw new RuntimeException();
        }
      }
      if (localGLUhalfEdge1.Onext != localGLUhalfEdge2)
      {
        if (!Mesh.__gl_meshSplice(localGLUhalfEdge2.Sym.Lnext, localGLUhalfEdge2)) {
          throw new RuntimeException();
        }
        if (!Mesh.__gl_meshSplice(localGLUhalfEdge1, localGLUhalfEdge2)) {
          throw new RuntimeException();
        }
      }
      FinishRegion(paramGLUtessellatorImpl, (ActiveRegion)localObject);
      localGLUhalfEdge1 = localActiveRegion.eUp;
      localObject = localActiveRegion;
    }
  }
  
  static void FinishRegion(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion)
  {
    GLUhalfEdge localGLUhalfEdge = paramActiveRegion.eUp;
    GLUface localGLUface = localGLUhalfEdge.Lface;
    localGLUface.inside = paramActiveRegion.inside;
    localGLUface.anEdge = localGLUhalfEdge;
    DeleteRegion(paramGLUtessellatorImpl, paramActiveRegion);
  }
  
  static boolean FixUpperEdge(ActiveRegion paramActiveRegion, GLUhalfEdge paramGLUhalfEdge)
  {
    assert (paramActiveRegion.fixUpperEdge);
    if (!Mesh.__gl_meshDelete(paramActiveRegion.eUp)) {
      return false;
    }
    paramActiveRegion.fixUpperEdge = false;
    paramActiveRegion.eUp = paramGLUhalfEdge;
    paramGLUhalfEdge.activeRegion = paramActiveRegion;
    return true;
  }
  
  static void GetIntersectData(GLUtessellatorImpl paramGLUtessellatorImpl, GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3, GLUvertex paramGLUvertex4, GLUvertex paramGLUvertex5)
  {
    Object[] arrayOfObject = new Object[4];
    float[] arrayOfFloat1 = new float[4];
    float[] arrayOfFloat2 = new float[2];
    float[] arrayOfFloat3 = new float[2];
    arrayOfObject[0] = paramGLUvertex2.data;
    arrayOfObject[1] = paramGLUvertex3.data;
    arrayOfObject[2] = paramGLUvertex4.data;
    arrayOfObject[3] = paramGLUvertex5.data;
    double[] arrayOfDouble1 = paramGLUvertex1.coords;
    double[] arrayOfDouble2 = paramGLUvertex1.coords;
    paramGLUvertex1.coords[2] = 0.0D;
    arrayOfDouble2[1] = 0.0D;
    arrayOfDouble1[0] = 0.0D;
    VertexWeights(paramGLUvertex1, paramGLUvertex2, paramGLUvertex3, arrayOfFloat2);
    VertexWeights(paramGLUvertex1, paramGLUvertex4, paramGLUvertex5, arrayOfFloat3);
    System.arraycopy(arrayOfFloat2, 0, arrayOfFloat1, 0, 2);
    System.arraycopy(arrayOfFloat3, 0, arrayOfFloat1, 2, 2);
    CallCombine(paramGLUtessellatorImpl, paramGLUvertex1, arrayOfObject, arrayOfFloat1, true);
  }
  
  static void InitEdgeDict(GLUtessellatorImpl paramGLUtessellatorImpl)
  {
    paramGLUtessellatorImpl.dict = Dict.dictNewDict(paramGLUtessellatorImpl, new Dict.DictLeq()
    {
      public boolean leq(Object paramAnonymousObject1, Object paramAnonymousObject2, Object paramAnonymousObject3)
      {
        return Sweep.EdgeLeq(Sweep.this, (ActiveRegion)paramAnonymousObject2, (ActiveRegion)paramAnonymousObject3);
      }
    });
    if (paramGLUtessellatorImpl.dict == null) {
      throw new RuntimeException();
    }
    AddSentinel(paramGLUtessellatorImpl, -4.0E+150D);
    AddSentinel(paramGLUtessellatorImpl, 4.0E+150D);
  }
  
  static boolean InitPriorityQ(GLUtessellatorImpl paramGLUtessellatorImpl)
  {
    PriorityQ localPriorityQ = PriorityQ.pqNewPriorityQ(new PriorityQ.Leq()
    {
      public boolean leq(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        return Geom.VertLeq((GLUvertex)paramAnonymousObject1, (GLUvertex)paramAnonymousObject2);
      }
    });
    paramGLUtessellatorImpl.pq = localPriorityQ;
    if (localPriorityQ == null) {
      return false;
    }
    GLUvertex localGLUvertex1 = paramGLUtessellatorImpl.mesh.vHead;
    for (GLUvertex localGLUvertex2 = localGLUvertex1.next;; localGLUvertex2 = localGLUvertex2.next)
    {
      if (localGLUvertex2 == localGLUvertex1) {}
      do
      {
        if ((localGLUvertex2 == localGLUvertex1) && (localPriorityQ.pqInit())) {
          break;
        }
        paramGLUtessellatorImpl.pq.pqDeletePriorityQ();
        paramGLUtessellatorImpl.pq = null;
        return false;
        localGLUvertex2.pqHandle = localPriorityQ.pqInsert(localGLUvertex2);
      } while (localGLUvertex2.pqHandle == 9223372036854775807L);
    }
    return true;
  }
  
  static boolean IsWindingInside(GLUtessellatorImpl paramGLUtessellatorImpl, int paramInt)
  {
    switch (paramGLUtessellatorImpl.windingRule)
    {
    default: 
      throw new InternalError();
    case 100130: 
      if ((paramInt & 0x1) == 0) {
        break;
      }
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return true;
            return false;
          } while (paramInt != 0);
          return false;
        } while (paramInt > 0);
        return false;
      } while (paramInt < 0);
      return false;
    } while ((paramInt >= 2) || (paramInt <= -2));
    return false;
  }
  
  private static ActiveRegion RegionAbove(ActiveRegion paramActiveRegion)
  {
    return (ActiveRegion)Dict.dictKey(Dict.dictSucc(paramActiveRegion.nodeUp));
  }
  
  private static ActiveRegion RegionBelow(ActiveRegion paramActiveRegion)
  {
    return (ActiveRegion)Dict.dictKey(Dict.dictPred(paramActiveRegion.nodeUp));
  }
  
  static void RemoveDegenerateEdges(GLUtessellatorImpl paramGLUtessellatorImpl)
  {
    GLUhalfEdge localGLUhalfEdge1 = paramGLUtessellatorImpl.mesh.eHead;
    GLUhalfEdge localGLUhalfEdge2;
    for (Object localObject = localGLUhalfEdge1.next;; localObject = localGLUhalfEdge2)
    {
      if (localObject == localGLUhalfEdge1) {
        return;
      }
      localGLUhalfEdge2 = ((GLUhalfEdge)localObject).next;
      GLUhalfEdge localGLUhalfEdge3 = ((GLUhalfEdge)localObject).Lnext;
      if ((Geom.VertEq(((GLUhalfEdge)localObject).Org, ((GLUhalfEdge)localObject).Sym.Org)) && (((GLUhalfEdge)localObject).Lnext.Lnext != localObject))
      {
        SpliceMergeVertices(paramGLUtessellatorImpl, localGLUhalfEdge3, (GLUhalfEdge)localObject);
        if (!Mesh.__gl_meshDelete((GLUhalfEdge)localObject)) {
          throw new RuntimeException();
        }
        localObject = localGLUhalfEdge3;
        localGLUhalfEdge3 = ((GLUhalfEdge)localObject).Lnext;
      }
      if (localGLUhalfEdge3.Lnext == localObject)
      {
        if (localGLUhalfEdge3 != localObject)
        {
          if ((localGLUhalfEdge3 == localGLUhalfEdge2) || (localGLUhalfEdge3 == localGLUhalfEdge2.Sym)) {
            localGLUhalfEdge2 = localGLUhalfEdge2.next;
          }
          if (!Mesh.__gl_meshDelete(localGLUhalfEdge3)) {
            throw new RuntimeException();
          }
        }
        if ((localObject == localGLUhalfEdge2) || (localObject == localGLUhalfEdge2.Sym)) {
          localGLUhalfEdge2 = localGLUhalfEdge2.next;
        }
        if (!Mesh.__gl_meshDelete((GLUhalfEdge)localObject)) {
          throw new RuntimeException();
        }
      }
    }
  }
  
  static boolean RemoveDegenerateFaces(GLUmesh paramGLUmesh)
  {
    GLUface localGLUface;
    for (Object localObject = paramGLUmesh.fHead.next;; localObject = localGLUface)
    {
      if (localObject == paramGLUmesh.fHead) {
        return true;
      }
      localGLUface = ((GLUface)localObject).next;
      GLUhalfEdge localGLUhalfEdge = ((GLUface)localObject).anEdge;
      assert (localGLUhalfEdge.Lnext != localGLUhalfEdge);
      if (localGLUhalfEdge.Lnext.Lnext == localGLUhalfEdge)
      {
        AddWinding(localGLUhalfEdge.Onext, localGLUhalfEdge);
        if (!Mesh.__gl_meshDelete(localGLUhalfEdge)) {
          return false;
        }
      }
    }
  }
  
  static void SpliceMergeVertices(GLUtessellatorImpl paramGLUtessellatorImpl, GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2)
  {
    Object[] arrayOfObject = new Object[4];
    float[] arrayOfFloat = { 0.5F, 0.5F, 0.0F, 0.0F };
    arrayOfObject[0] = paramGLUhalfEdge1.Org.data;
    arrayOfObject[1] = paramGLUhalfEdge2.Org.data;
    CallCombine(paramGLUtessellatorImpl, paramGLUhalfEdge1.Org, arrayOfObject, arrayOfFloat, false);
    if (!Mesh.__gl_meshSplice(paramGLUhalfEdge1, paramGLUhalfEdge2)) {
      throw new RuntimeException();
    }
  }
  
  static void SweepEvent(GLUtessellatorImpl paramGLUtessellatorImpl, GLUvertex paramGLUvertex)
  {
    paramGLUtessellatorImpl.event = paramGLUvertex;
    DebugEvent(paramGLUtessellatorImpl);
    GLUhalfEdge localGLUhalfEdge1 = paramGLUvertex.anEdge;
    ActiveRegion localActiveRegion1;
    do
    {
      if (localGLUhalfEdge1.activeRegion != null)
      {
        localActiveRegion1 = TopLeftRegion(localGLUhalfEdge1.activeRegion);
        if (localActiveRegion1 != null) {
          break;
        }
        throw new RuntimeException();
      }
      localGLUhalfEdge1 = localGLUhalfEdge1.Onext;
    } while (localGLUhalfEdge1 != paramGLUvertex.anEdge);
    ConnectLeftVertex(paramGLUtessellatorImpl, paramGLUvertex);
    return;
    ActiveRegion localActiveRegion2 = RegionBelow(localActiveRegion1);
    GLUhalfEdge localGLUhalfEdge2 = localActiveRegion2.eUp;
    GLUhalfEdge localGLUhalfEdge3 = FinishLeftRegions(paramGLUtessellatorImpl, localActiveRegion2, null);
    if (localGLUhalfEdge3.Onext == localGLUhalfEdge2)
    {
      ConnectRightVertex(paramGLUtessellatorImpl, localActiveRegion1, localGLUhalfEdge3);
      return;
    }
    AddRightEdges(paramGLUtessellatorImpl, localActiveRegion1, localGLUhalfEdge3.Onext, localGLUhalfEdge2, localGLUhalfEdge2, true);
  }
  
  static ActiveRegion TopLeftRegion(ActiveRegion paramActiveRegion)
  {
    GLUvertex localGLUvertex = paramActiveRegion.eUp.Org;
    do
    {
      paramActiveRegion = RegionAbove(paramActiveRegion);
    } while (paramActiveRegion.eUp.Org == localGLUvertex);
    GLUhalfEdge localGLUhalfEdge;
    if (paramActiveRegion.fixUpperEdge)
    {
      localGLUhalfEdge = Mesh.__gl_meshConnect(RegionBelow(paramActiveRegion).eUp.Sym, paramActiveRegion.eUp.Lnext);
      if (localGLUhalfEdge == null) {
        paramActiveRegion = null;
      }
    }
    else
    {
      return paramActiveRegion;
    }
    if (!FixUpperEdge(paramActiveRegion, localGLUhalfEdge)) {
      return null;
    }
    return RegionAbove(paramActiveRegion);
  }
  
  static ActiveRegion TopRightRegion(ActiveRegion paramActiveRegion)
  {
    GLUvertex localGLUvertex = paramActiveRegion.eUp.Sym.Org;
    do
    {
      paramActiveRegion = RegionAbove(paramActiveRegion);
    } while (paramActiveRegion.eUp.Sym.Org == localGLUvertex);
    return paramActiveRegion;
  }
  
  static void VertexWeights(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2, GLUvertex paramGLUvertex3, float[] paramArrayOfFloat)
  {
    double d1 = Geom.VertL1dist(paramGLUvertex2, paramGLUvertex1);
    double d2 = Geom.VertL1dist(paramGLUvertex3, paramGLUvertex1);
    paramArrayOfFloat[0] = ((float)(0.5D * d2 / (d1 + d2)));
    paramArrayOfFloat[1] = ((float)(0.5D * d1 / (d1 + d2)));
    double[] arrayOfDouble1 = paramGLUvertex1.coords;
    arrayOfDouble1[0] += paramArrayOfFloat[0] * paramGLUvertex2.coords[0] + paramArrayOfFloat[1] * paramGLUvertex3.coords[0];
    double[] arrayOfDouble2 = paramGLUvertex1.coords;
    arrayOfDouble2[1] += paramArrayOfFloat[0] * paramGLUvertex2.coords[1] + paramArrayOfFloat[1] * paramGLUvertex3.coords[1];
    double[] arrayOfDouble3 = paramGLUvertex1.coords;
    arrayOfDouble3[2] += paramArrayOfFloat[0] * paramGLUvertex2.coords[2] + paramArrayOfFloat[1] * paramGLUvertex3.coords[2];
  }
  
  static void WalkDirtyRegions(GLUtessellatorImpl paramGLUtessellatorImpl, ActiveRegion paramActiveRegion)
  {
    ActiveRegion localActiveRegion = RegionBelow(paramActiveRegion);
    for (;;)
    {
      if (!localActiveRegion.dirty)
      {
        if (paramActiveRegion.dirty) {
          break label48;
        }
        localActiveRegion = paramActiveRegion;
        paramActiveRegion = RegionAbove(paramActiveRegion);
        if ((paramActiveRegion != null) && (paramActiveRegion.dirty)) {
          break label48;
        }
      }
      label48:
      GLUhalfEdge localGLUhalfEdge1;
      GLUhalfEdge localGLUhalfEdge2;
      do
      {
        return;
        paramActiveRegion = localActiveRegion;
        localActiveRegion = RegionBelow(localActiveRegion);
        break;
        paramActiveRegion.dirty = false;
        localGLUhalfEdge1 = paramActiveRegion.eUp;
        localGLUhalfEdge2 = localActiveRegion.eUp;
        if ((localGLUhalfEdge1.Sym.Org != localGLUhalfEdge2.Sym.Org) && (CheckForLeftSplice(paramGLUtessellatorImpl, paramActiveRegion)))
        {
          if (!localActiveRegion.fixUpperEdge) {
            break label266;
          }
          DeleteRegion(paramGLUtessellatorImpl, localActiveRegion);
          if (!Mesh.__gl_meshDelete(localGLUhalfEdge2)) {
            throw new RuntimeException();
          }
          localActiveRegion = RegionBelow(paramActiveRegion);
          localGLUhalfEdge2 = localActiveRegion.eUp;
        }
        if (localGLUhalfEdge1.Org == localGLUhalfEdge2.Org) {
          break label210;
        }
        if ((localGLUhalfEdge1.Sym.Org == localGLUhalfEdge2.Sym.Org) || (paramActiveRegion.fixUpperEdge) || (localActiveRegion.fixUpperEdge) || ((localGLUhalfEdge1.Sym.Org != paramGLUtessellatorImpl.event) && (localGLUhalfEdge2.Sym.Org != paramGLUtessellatorImpl.event))) {
          break label306;
        }
      } while (CheckForIntersect(paramGLUtessellatorImpl, paramActiveRegion));
      for (;;)
      {
        label210:
        if ((localGLUhalfEdge1.Org != localGLUhalfEdge2.Org) || (localGLUhalfEdge1.Sym.Org != localGLUhalfEdge2.Sym.Org)) {
          break label313;
        }
        AddWinding(localGLUhalfEdge2, localGLUhalfEdge1);
        DeleteRegion(paramGLUtessellatorImpl, paramActiveRegion);
        if (Mesh.__gl_meshDelete(localGLUhalfEdge1)) {
          break label315;
        }
        throw new RuntimeException();
        label266:
        if (!paramActiveRegion.fixUpperEdge) {
          break;
        }
        DeleteRegion(paramGLUtessellatorImpl, paramActiveRegion);
        if (!Mesh.__gl_meshDelete(localGLUhalfEdge1)) {
          throw new RuntimeException();
        }
        paramActiveRegion = RegionAbove(localActiveRegion);
        localGLUhalfEdge1 = paramActiveRegion.eUp;
        break;
        label306:
        CheckForRightSplice(paramGLUtessellatorImpl, paramActiveRegion);
      }
      label313:
      continue;
      label315:
      paramActiveRegion = RegionAbove(localActiveRegion);
    }
  }
  
  public static boolean __gl_computeInterior(GLUtessellatorImpl paramGLUtessellatorImpl)
  {
    paramGLUtessellatorImpl.fatalError = false;
    RemoveDegenerateEdges(paramGLUtessellatorImpl);
    if (!InitPriorityQ(paramGLUtessellatorImpl)) {
      return false;
    }
    InitEdgeDict(paramGLUtessellatorImpl);
    for (;;)
    {
      GLUvertex localGLUvertex1 = (GLUvertex)paramGLUtessellatorImpl.pq.pqExtractMin();
      if (localGLUvertex1 == null)
      {
        paramGLUtessellatorImpl.event = ((ActiveRegion)Dict.dictKey(Dict.dictMin(paramGLUtessellatorImpl.dict))).eUp.Org;
        DebugEvent(paramGLUtessellatorImpl);
        DoneEdgeDict(paramGLUtessellatorImpl);
        DonePriorityQ(paramGLUtessellatorImpl);
        if (RemoveDegenerateFaces(paramGLUtessellatorImpl.mesh)) {
          break;
        }
        return false;
      }
      GLUvertex localGLUvertex2;
      do
      {
        GLUvertex localGLUvertex3 = (GLUvertex)paramGLUtessellatorImpl.pq.pqExtractMin();
        SpliceMergeVertices(paramGLUtessellatorImpl, localGLUvertex1.anEdge, localGLUvertex3.anEdge);
        localGLUvertex2 = (GLUvertex)paramGLUtessellatorImpl.pq.pqMinimum();
      } while ((localGLUvertex2 != null) && (Geom.VertEq(localGLUvertex2, localGLUvertex1)));
      SweepEvent(paramGLUtessellatorImpl, localGLUvertex1);
    }
    Mesh.__gl_meshCheckMesh(paramGLUtessellatorImpl.mesh);
    return true;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.Sweep
 * JD-Core Version:    0.7.0.1
 */