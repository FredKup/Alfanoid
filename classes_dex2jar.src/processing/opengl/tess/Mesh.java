package processing.opengl.tess;

class Mesh
{
  static
  {
    if (!Mesh.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  static void KillEdge(GLUhalfEdge paramGLUhalfEdge)
  {
    if (!paramGLUhalfEdge.first) {
      paramGLUhalfEdge = paramGLUhalfEdge.Sym;
    }
    GLUhalfEdge localGLUhalfEdge1 = paramGLUhalfEdge.next;
    GLUhalfEdge localGLUhalfEdge2 = paramGLUhalfEdge.Sym.next;
    localGLUhalfEdge1.Sym.next = localGLUhalfEdge2;
    localGLUhalfEdge2.Sym.next = localGLUhalfEdge1;
  }
  
  static void KillFace(GLUface paramGLUface1, GLUface paramGLUface2)
  {
    GLUhalfEdge localGLUhalfEdge1 = paramGLUface1.anEdge;
    GLUhalfEdge localGLUhalfEdge2 = localGLUhalfEdge1;
    do
    {
      localGLUhalfEdge2.Lface = paramGLUface2;
      localGLUhalfEdge2 = localGLUhalfEdge2.Lnext;
    } while (localGLUhalfEdge2 != localGLUhalfEdge1);
    GLUface localGLUface1 = paramGLUface1.prev;
    GLUface localGLUface2 = paramGLUface1.next;
    localGLUface2.prev = localGLUface1;
    localGLUface1.next = localGLUface2;
  }
  
  static void KillVertex(GLUvertex paramGLUvertex1, GLUvertex paramGLUvertex2)
  {
    GLUhalfEdge localGLUhalfEdge1 = paramGLUvertex1.anEdge;
    GLUhalfEdge localGLUhalfEdge2 = localGLUhalfEdge1;
    do
    {
      localGLUhalfEdge2.Org = paramGLUvertex2;
      localGLUhalfEdge2 = localGLUhalfEdge2.Onext;
    } while (localGLUhalfEdge2 != localGLUhalfEdge1);
    GLUvertex localGLUvertex1 = paramGLUvertex1.prev;
    GLUvertex localGLUvertex2 = paramGLUvertex1.next;
    localGLUvertex2.prev = localGLUvertex1;
    localGLUvertex1.next = localGLUvertex2;
  }
  
  static GLUhalfEdge MakeEdge(GLUhalfEdge paramGLUhalfEdge)
  {
    GLUhalfEdge localGLUhalfEdge1 = new GLUhalfEdge(true);
    GLUhalfEdge localGLUhalfEdge2 = new GLUhalfEdge(false);
    if (!paramGLUhalfEdge.first) {
      paramGLUhalfEdge = paramGLUhalfEdge.Sym;
    }
    GLUhalfEdge localGLUhalfEdge3 = paramGLUhalfEdge.Sym.next;
    localGLUhalfEdge2.next = localGLUhalfEdge3;
    localGLUhalfEdge3.Sym.next = localGLUhalfEdge1;
    localGLUhalfEdge1.next = paramGLUhalfEdge;
    paramGLUhalfEdge.Sym.next = localGLUhalfEdge2;
    localGLUhalfEdge1.Sym = localGLUhalfEdge2;
    localGLUhalfEdge1.Onext = localGLUhalfEdge1;
    localGLUhalfEdge1.Lnext = localGLUhalfEdge2;
    localGLUhalfEdge1.Org = null;
    localGLUhalfEdge1.Lface = null;
    localGLUhalfEdge1.winding = 0;
    localGLUhalfEdge1.activeRegion = null;
    localGLUhalfEdge2.Sym = localGLUhalfEdge1;
    localGLUhalfEdge2.Onext = localGLUhalfEdge2;
    localGLUhalfEdge2.Lnext = localGLUhalfEdge1;
    localGLUhalfEdge2.Org = null;
    localGLUhalfEdge2.Lface = null;
    localGLUhalfEdge2.winding = 0;
    localGLUhalfEdge2.activeRegion = null;
    return localGLUhalfEdge1;
  }
  
  static void MakeFace(GLUface paramGLUface1, GLUhalfEdge paramGLUhalfEdge, GLUface paramGLUface2)
  {
    assert (paramGLUface1 != null);
    GLUface localGLUface = paramGLUface2.prev;
    paramGLUface1.prev = localGLUface;
    localGLUface.next = paramGLUface1;
    paramGLUface1.next = paramGLUface2;
    paramGLUface2.prev = paramGLUface1;
    paramGLUface1.anEdge = paramGLUhalfEdge;
    paramGLUface1.data = null;
    paramGLUface1.trail = null;
    paramGLUface1.marked = false;
    paramGLUface1.inside = paramGLUface2.inside;
    GLUhalfEdge localGLUhalfEdge = paramGLUhalfEdge;
    do
    {
      localGLUhalfEdge.Lface = paramGLUface1;
      localGLUhalfEdge = localGLUhalfEdge.Lnext;
    } while (localGLUhalfEdge != paramGLUhalfEdge);
  }
  
  static void MakeVertex(GLUvertex paramGLUvertex1, GLUhalfEdge paramGLUhalfEdge, GLUvertex paramGLUvertex2)
  {
    assert (paramGLUvertex1 != null);
    GLUvertex localGLUvertex = paramGLUvertex2.prev;
    paramGLUvertex1.prev = localGLUvertex;
    localGLUvertex.next = paramGLUvertex1;
    paramGLUvertex1.next = paramGLUvertex2;
    paramGLUvertex2.prev = paramGLUvertex1;
    paramGLUvertex1.anEdge = paramGLUhalfEdge;
    paramGLUvertex1.data = null;
    GLUhalfEdge localGLUhalfEdge = paramGLUhalfEdge;
    do
    {
      localGLUhalfEdge.Org = paramGLUvertex1;
      localGLUhalfEdge = localGLUhalfEdge.Onext;
    } while (localGLUhalfEdge != paramGLUhalfEdge);
  }
  
  static void Splice(GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2)
  {
    GLUhalfEdge localGLUhalfEdge1 = paramGLUhalfEdge1.Onext;
    GLUhalfEdge localGLUhalfEdge2 = paramGLUhalfEdge2.Onext;
    localGLUhalfEdge1.Sym.Lnext = paramGLUhalfEdge2;
    localGLUhalfEdge2.Sym.Lnext = paramGLUhalfEdge1;
    paramGLUhalfEdge1.Onext = localGLUhalfEdge2;
    paramGLUhalfEdge2.Onext = localGLUhalfEdge1;
  }
  
  static GLUhalfEdge __gl_meshAddEdgeVertex(GLUhalfEdge paramGLUhalfEdge)
  {
    GLUhalfEdge localGLUhalfEdge1 = MakeEdge(paramGLUhalfEdge);
    GLUhalfEdge localGLUhalfEdge2 = localGLUhalfEdge1.Sym;
    Splice(localGLUhalfEdge1, paramGLUhalfEdge.Lnext);
    localGLUhalfEdge1.Org = paramGLUhalfEdge.Sym.Org;
    MakeVertex(new GLUvertex(), localGLUhalfEdge2, localGLUhalfEdge1.Org);
    GLUface localGLUface = paramGLUhalfEdge.Lface;
    localGLUhalfEdge2.Lface = localGLUface;
    localGLUhalfEdge1.Lface = localGLUface;
    return localGLUhalfEdge1;
  }
  
  public static void __gl_meshCheckMesh(GLUmesh paramGLUmesh)
  {
    GLUface localGLUface1 = paramGLUmesh.fHead;
    GLUvertex localGLUvertex1 = paramGLUmesh.vHead;
    GLUhalfEdge localGLUhalfEdge1 = paramGLUmesh.eHead;
    GLUface localGLUface2;
    for (Object localObject1 = localGLUface1;; localObject1 = localGLUface2)
    {
      localGLUface2 = ((GLUface)localObject1).next;
      if (localGLUface2 == localGLUface1)
      {
        if (($assertionsDisabled) || ((localGLUface2.prev == localObject1) && (localGLUface2.anEdge == null) && (localGLUface2.data == null))) {
          break;
        }
        throw new AssertionError();
      }
      assert (localGLUface2.prev == localObject1);
      GLUhalfEdge localGLUhalfEdge2 = localGLUface2.anEdge;
      do
      {
        assert (localGLUhalfEdge2.Sym != localGLUhalfEdge2);
        assert (localGLUhalfEdge2.Sym.Sym == localGLUhalfEdge2);
        assert (localGLUhalfEdge2.Lnext.Onext.Sym == localGLUhalfEdge2);
        assert (localGLUhalfEdge2.Onext.Sym.Lnext == localGLUhalfEdge2);
        assert (localGLUhalfEdge2.Lface == localGLUface2);
        localGLUhalfEdge2 = localGLUhalfEdge2.Lnext;
      } while (localGLUhalfEdge2 != localGLUface2.anEdge);
    }
    GLUvertex localGLUvertex2;
    for (Object localObject2 = localGLUvertex1;; localObject2 = localGLUvertex2)
    {
      localGLUvertex2 = ((GLUvertex)localObject2).next;
      if (localGLUvertex2 == localGLUvertex1)
      {
        if (($assertionsDisabled) || ((localGLUvertex2.prev == localObject2) && (localGLUvertex2.anEdge == null) && (localGLUvertex2.data == null))) {
          break;
        }
        throw new AssertionError();
      }
      assert (localGLUvertex2.prev == localObject2);
      GLUhalfEdge localGLUhalfEdge3 = localGLUvertex2.anEdge;
      do
      {
        assert (localGLUhalfEdge3.Sym != localGLUhalfEdge3);
        assert (localGLUhalfEdge3.Sym.Sym == localGLUhalfEdge3);
        assert (localGLUhalfEdge3.Lnext.Onext.Sym == localGLUhalfEdge3);
        assert (localGLUhalfEdge3.Onext.Sym.Lnext == localGLUhalfEdge3);
        assert (localGLUhalfEdge3.Org == localGLUvertex2);
        localGLUhalfEdge3 = localGLUhalfEdge3.Onext;
      } while (localGLUhalfEdge3 != localGLUvertex2.anEdge);
    }
    GLUhalfEdge localGLUhalfEdge4;
    for (Object localObject3 = localGLUhalfEdge1;; localObject3 = localGLUhalfEdge4)
    {
      localGLUhalfEdge4 = ((GLUhalfEdge)localObject3).next;
      if (localGLUhalfEdge4 == localGLUhalfEdge1)
      {
        if (($assertionsDisabled) || ((localGLUhalfEdge4.Sym.next == ((GLUhalfEdge)localObject3).Sym) && (localGLUhalfEdge4.Sym == paramGLUmesh.eHeadSym) && (localGLUhalfEdge4.Sym.Sym == localGLUhalfEdge4) && (localGLUhalfEdge4.Org == null) && (localGLUhalfEdge4.Sym.Org == null) && (localGLUhalfEdge4.Lface == null) && (localGLUhalfEdge4.Sym.Lface == null))) {
          break;
        }
        throw new AssertionError();
      }
      assert (localGLUhalfEdge4.Sym.next == ((GLUhalfEdge)localObject3).Sym);
      assert (localGLUhalfEdge4.Sym != localGLUhalfEdge4);
      assert (localGLUhalfEdge4.Sym.Sym == localGLUhalfEdge4);
      assert (localGLUhalfEdge4.Org != null);
      assert (localGLUhalfEdge4.Sym.Org != null);
      assert (localGLUhalfEdge4.Lnext.Onext.Sym == localGLUhalfEdge4);
      assert (localGLUhalfEdge4.Onext.Sym.Lnext == localGLUhalfEdge4);
    }
  }
  
  static GLUhalfEdge __gl_meshConnect(GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2)
  {
    GLUhalfEdge localGLUhalfEdge1 = MakeEdge(paramGLUhalfEdge1);
    GLUhalfEdge localGLUhalfEdge2 = localGLUhalfEdge1.Sym;
    GLUface localGLUface1 = paramGLUhalfEdge2.Lface;
    GLUface localGLUface2 = paramGLUhalfEdge1.Lface;
    int i = 0;
    if (localGLUface1 != localGLUface2)
    {
      i = 1;
      KillFace(paramGLUhalfEdge2.Lface, paramGLUhalfEdge1.Lface);
    }
    Splice(localGLUhalfEdge1, paramGLUhalfEdge1.Lnext);
    Splice(localGLUhalfEdge2, paramGLUhalfEdge2);
    localGLUhalfEdge1.Org = paramGLUhalfEdge1.Sym.Org;
    localGLUhalfEdge2.Org = paramGLUhalfEdge2.Org;
    GLUface localGLUface3 = paramGLUhalfEdge1.Lface;
    localGLUhalfEdge2.Lface = localGLUface3;
    localGLUhalfEdge1.Lface = localGLUface3;
    paramGLUhalfEdge1.Lface.anEdge = localGLUhalfEdge2;
    if (i == 0) {
      MakeFace(new GLUface(), localGLUhalfEdge1, paramGLUhalfEdge1.Lface);
    }
    return localGLUhalfEdge1;
  }
  
  static boolean __gl_meshDelete(GLUhalfEdge paramGLUhalfEdge)
  {
    GLUhalfEdge localGLUhalfEdge = paramGLUhalfEdge.Sym;
    GLUface localGLUface1 = paramGLUhalfEdge.Lface;
    GLUface localGLUface2 = paramGLUhalfEdge.Sym.Lface;
    int i = 0;
    if (localGLUface1 != localGLUface2)
    {
      i = 1;
      KillFace(paramGLUhalfEdge.Lface, paramGLUhalfEdge.Sym.Lface);
    }
    if (paramGLUhalfEdge.Onext == paramGLUhalfEdge)
    {
      KillVertex(paramGLUhalfEdge.Org, null);
      if (localGLUhalfEdge.Onext != localGLUhalfEdge) {
        break label151;
      }
      KillVertex(localGLUhalfEdge.Org, null);
      KillFace(localGLUhalfEdge.Lface, null);
    }
    for (;;)
    {
      KillEdge(paramGLUhalfEdge);
      return true;
      paramGLUhalfEdge.Sym.Lface.anEdge = paramGLUhalfEdge.Sym.Lnext;
      paramGLUhalfEdge.Org.anEdge = paramGLUhalfEdge.Onext;
      Splice(paramGLUhalfEdge, paramGLUhalfEdge.Sym.Lnext);
      if (i != 0) {
        break;
      }
      MakeFace(new GLUface(), paramGLUhalfEdge, paramGLUhalfEdge.Lface);
      break;
      label151:
      paramGLUhalfEdge.Lface.anEdge = localGLUhalfEdge.Sym.Lnext;
      localGLUhalfEdge.Org.anEdge = localGLUhalfEdge.Onext;
      Splice(localGLUhalfEdge, localGLUhalfEdge.Sym.Lnext);
    }
  }
  
  public static void __gl_meshDeleteMesh(GLUmesh paramGLUmesh)
  {
    GLUface localGLUface = paramGLUmesh.fHead.next;
    GLUvertex localGLUvertex;
    if (localGLUface == paramGLUmesh.fHead)
    {
      localGLUvertex = paramGLUmesh.vHead.next;
      label24:
      if (localGLUvertex != paramGLUmesh.vHead) {
        break label57;
      }
    }
    for (GLUhalfEdge localGLUhalfEdge = paramGLUmesh.eHead.next;; localGLUhalfEdge = localGLUhalfEdge.next) {
      if (localGLUhalfEdge == paramGLUmesh.eHead)
      {
        return;
        localGLUface = localGLUface.next;
        break;
        label57:
        localGLUvertex = localGLUvertex.next;
        break label24;
      }
    }
  }
  
  static void __gl_meshDeleteMeshZap(GLUmesh paramGLUmesh)
  {
    GLUface localGLUface = paramGLUmesh.fHead;
    for (;;)
    {
      if (localGLUface.next == localGLUface)
      {
        if (($assertionsDisabled) || (paramGLUmesh.vHead.next == paramGLUmesh.vHead)) {
          break;
        }
        throw new AssertionError();
      }
      __gl_meshZapFace(localGLUface.next);
    }
  }
  
  public static GLUhalfEdge __gl_meshMakeEdge(GLUmesh paramGLUmesh)
  {
    GLUvertex localGLUvertex1 = new GLUvertex();
    GLUvertex localGLUvertex2 = new GLUvertex();
    GLUface localGLUface = new GLUface();
    GLUhalfEdge localGLUhalfEdge = MakeEdge(paramGLUmesh.eHead);
    if (localGLUhalfEdge == null) {
      return null;
    }
    MakeVertex(localGLUvertex1, localGLUhalfEdge, paramGLUmesh.vHead);
    MakeVertex(localGLUvertex2, localGLUhalfEdge.Sym, paramGLUmesh.vHead);
    MakeFace(localGLUface, localGLUhalfEdge, paramGLUmesh.fHead);
    return localGLUhalfEdge;
  }
  
  public static GLUmesh __gl_meshNewMesh()
  {
    GLUmesh localGLUmesh = new GLUmesh();
    GLUvertex localGLUvertex = localGLUmesh.vHead;
    GLUface localGLUface = localGLUmesh.fHead;
    GLUhalfEdge localGLUhalfEdge1 = localGLUmesh.eHead;
    GLUhalfEdge localGLUhalfEdge2 = localGLUmesh.eHeadSym;
    localGLUvertex.prev = localGLUvertex;
    localGLUvertex.next = localGLUvertex;
    localGLUvertex.anEdge = null;
    localGLUvertex.data = null;
    localGLUface.prev = localGLUface;
    localGLUface.next = localGLUface;
    localGLUface.anEdge = null;
    localGLUface.data = null;
    localGLUface.trail = null;
    localGLUface.marked = false;
    localGLUface.inside = false;
    localGLUhalfEdge1.next = localGLUhalfEdge1;
    localGLUhalfEdge1.Sym = localGLUhalfEdge2;
    localGLUhalfEdge1.Onext = null;
    localGLUhalfEdge1.Lnext = null;
    localGLUhalfEdge1.Org = null;
    localGLUhalfEdge1.Lface = null;
    localGLUhalfEdge1.winding = 0;
    localGLUhalfEdge1.activeRegion = null;
    localGLUhalfEdge2.next = localGLUhalfEdge2;
    localGLUhalfEdge2.Sym = localGLUhalfEdge1;
    localGLUhalfEdge2.Onext = null;
    localGLUhalfEdge2.Lnext = null;
    localGLUhalfEdge2.Org = null;
    localGLUhalfEdge2.Lface = null;
    localGLUhalfEdge2.winding = 0;
    localGLUhalfEdge2.activeRegion = null;
    return localGLUmesh;
  }
  
  public static boolean __gl_meshSplice(GLUhalfEdge paramGLUhalfEdge1, GLUhalfEdge paramGLUhalfEdge2)
  {
    if (paramGLUhalfEdge1 == paramGLUhalfEdge2) {}
    int j;
    do
    {
      return true;
      GLUvertex localGLUvertex1 = paramGLUhalfEdge2.Org;
      GLUvertex localGLUvertex2 = paramGLUhalfEdge1.Org;
      int i = 0;
      if (localGLUvertex1 != localGLUvertex2)
      {
        i = 1;
        KillVertex(paramGLUhalfEdge2.Org, paramGLUhalfEdge1.Org);
      }
      GLUface localGLUface1 = paramGLUhalfEdge2.Lface;
      GLUface localGLUface2 = paramGLUhalfEdge1.Lface;
      j = 0;
      if (localGLUface1 != localGLUface2)
      {
        j = 1;
        KillFace(paramGLUhalfEdge2.Lface, paramGLUhalfEdge1.Lface);
      }
      Splice(paramGLUhalfEdge2, paramGLUhalfEdge1);
      if (i == 0)
      {
        MakeVertex(new GLUvertex(), paramGLUhalfEdge2, paramGLUhalfEdge1.Org);
        paramGLUhalfEdge1.Org.anEdge = paramGLUhalfEdge1;
      }
    } while (j != 0);
    MakeFace(new GLUface(), paramGLUhalfEdge2, paramGLUhalfEdge1.Lface);
    paramGLUhalfEdge1.Lface.anEdge = paramGLUhalfEdge1;
    return true;
  }
  
  public static GLUhalfEdge __gl_meshSplitEdge(GLUhalfEdge paramGLUhalfEdge)
  {
    GLUhalfEdge localGLUhalfEdge = __gl_meshAddEdgeVertex(paramGLUhalfEdge).Sym;
    Splice(paramGLUhalfEdge.Sym, paramGLUhalfEdge.Sym.Sym.Lnext);
    Splice(paramGLUhalfEdge.Sym, localGLUhalfEdge);
    paramGLUhalfEdge.Sym.Org = localGLUhalfEdge.Org;
    localGLUhalfEdge.Sym.Org.anEdge = localGLUhalfEdge.Sym;
    localGLUhalfEdge.Sym.Lface = paramGLUhalfEdge.Sym.Lface;
    localGLUhalfEdge.winding = paramGLUhalfEdge.winding;
    localGLUhalfEdge.Sym.winding = paramGLUhalfEdge.Sym.winding;
    return localGLUhalfEdge;
  }
  
  static GLUmesh __gl_meshUnion(GLUmesh paramGLUmesh1, GLUmesh paramGLUmesh2)
  {
    GLUface localGLUface1 = paramGLUmesh1.fHead;
    GLUvertex localGLUvertex1 = paramGLUmesh1.vHead;
    GLUhalfEdge localGLUhalfEdge1 = paramGLUmesh1.eHead;
    GLUface localGLUface2 = paramGLUmesh2.fHead;
    GLUvertex localGLUvertex2 = paramGLUmesh2.vHead;
    GLUhalfEdge localGLUhalfEdge2 = paramGLUmesh2.eHead;
    if (localGLUface2.next != localGLUface2)
    {
      localGLUface1.prev.next = localGLUface2.next;
      localGLUface2.next.prev = localGLUface1.prev;
      localGLUface2.prev.next = localGLUface1;
      localGLUface1.prev = localGLUface2.prev;
    }
    if (localGLUvertex2.next != localGLUvertex2)
    {
      localGLUvertex1.prev.next = localGLUvertex2.next;
      localGLUvertex2.next.prev = localGLUvertex1.prev;
      localGLUvertex2.prev.next = localGLUvertex1;
      localGLUvertex1.prev = localGLUvertex2.prev;
    }
    if (localGLUhalfEdge2.next != localGLUhalfEdge2)
    {
      localGLUhalfEdge1.Sym.next.Sym.next = localGLUhalfEdge2.next;
      localGLUhalfEdge2.next.Sym.next = localGLUhalfEdge1.Sym.next;
      localGLUhalfEdge2.Sym.next.Sym.next = localGLUhalfEdge1;
      localGLUhalfEdge1.Sym.next = localGLUhalfEdge2.Sym.next;
    }
    return paramGLUmesh1;
  }
  
  static void __gl_meshZapFace(GLUface paramGLUface)
  {
    GLUhalfEdge localGLUhalfEdge1 = paramGLUface.anEdge;
    GLUhalfEdge localGLUhalfEdge2 = localGLUhalfEdge1.Lnext;
    GLUhalfEdge localGLUhalfEdge3 = localGLUhalfEdge2;
    localGLUhalfEdge2 = localGLUhalfEdge3.Lnext;
    localGLUhalfEdge3.Lface = null;
    label48:
    GLUhalfEdge localGLUhalfEdge4;
    if (localGLUhalfEdge3.Sym.Lface == null)
    {
      if (localGLUhalfEdge3.Onext != localGLUhalfEdge3) {
        break label109;
      }
      KillVertex(localGLUhalfEdge3.Org, null);
      localGLUhalfEdge4 = localGLUhalfEdge3.Sym;
      if (localGLUhalfEdge4.Onext != localGLUhalfEdge4) {
        break label134;
      }
      KillVertex(localGLUhalfEdge4.Org, null);
    }
    for (;;)
    {
      KillEdge(localGLUhalfEdge3);
      if (localGLUhalfEdge3 != localGLUhalfEdge1) {
        break;
      }
      GLUface localGLUface1 = paramGLUface.prev;
      GLUface localGLUface2 = paramGLUface.next;
      localGLUface2.prev = localGLUface1;
      localGLUface1.next = localGLUface2;
      return;
      label109:
      localGLUhalfEdge3.Org.anEdge = localGLUhalfEdge3.Onext;
      Splice(localGLUhalfEdge3, localGLUhalfEdge3.Sym.Lnext);
      break label48;
      label134:
      localGLUhalfEdge4.Org.anEdge = localGLUhalfEdge4.Onext;
      Splice(localGLUhalfEdge4, localGLUhalfEdge4.Sym.Lnext);
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.Mesh
 * JD-Core Version:    0.7.0.1
 */