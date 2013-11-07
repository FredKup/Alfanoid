package processing.opengl.tess;

class TessMono
{
  static
  {
    if (!TessMono.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public static void __gl_meshDiscardExterior(GLUmesh paramGLUmesh)
  {
    GLUface localGLUface;
    for (Object localObject = paramGLUmesh.fHead.next;; localObject = localGLUface)
    {
      if (localObject == paramGLUmesh.fHead) {
        return;
      }
      localGLUface = ((GLUface)localObject).next;
      if (!((GLUface)localObject).inside) {
        Mesh.__gl_meshZapFace((GLUface)localObject);
      }
    }
  }
  
  public static boolean __gl_meshSetWindingNumber(GLUmesh paramGLUmesh, int paramInt, boolean paramBoolean)
  {
    Object localObject = paramGLUmesh.eHead.next;
    if (localObject == paramGLUmesh.eHead) {
      return true;
    }
    GLUhalfEdge localGLUhalfEdge = ((GLUhalfEdge)localObject).next;
    int i;
    if (((GLUhalfEdge)localObject).Sym.Lface.inside != ((GLUhalfEdge)localObject).Lface.inside) {
      if (((GLUhalfEdge)localObject).Lface.inside)
      {
        i = paramInt;
        label57:
        ((GLUhalfEdge)localObject).winding = i;
      }
    }
    label88:
    do
    {
      for (;;)
      {
        localObject = localGLUhalfEdge;
        break;
        i = -paramInt;
        break label57;
        if (paramBoolean) {
          break label88;
        }
        ((GLUhalfEdge)localObject).winding = 0;
      }
    } while (Mesh.__gl_meshDelete((GLUhalfEdge)localObject));
    return false;
  }
  
  public static boolean __gl_meshTessellateInterior(GLUmesh paramGLUmesh, boolean paramBoolean)
  {
    GLUface localGLUface;
    for (Object localObject = paramGLUmesh.fHead.next;; localObject = localGLUface)
    {
      if (localObject == paramGLUmesh.fHead) {
        return true;
      }
      localGLUface = ((GLUface)localObject).next;
      if ((((GLUface)localObject).inside) && (!__gl_meshTessellateMonoRegion((GLUface)localObject, paramBoolean))) {
        return false;
      }
    }
  }
  
  static boolean __gl_meshTessellateMonoRegion(GLUface paramGLUface, boolean paramBoolean)
  {
    GLUhalfEdge localGLUhalfEdge1 = paramGLUface.anEdge;
    if ((!$assertionsDisabled) && ((localGLUhalfEdge1.Lnext == localGLUhalfEdge1) || (localGLUhalfEdge1.Lnext.Lnext == localGLUhalfEdge1))) {
      throw new AssertionError();
    }
    while (Geom.VertLeq(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge1.Org)) {
      localGLUhalfEdge1 = localGLUhalfEdge1.Onext.Sym;
    }
    GLUhalfEdge localGLUhalfEdge2;
    int i;
    if (!Geom.VertLeq(localGLUhalfEdge1.Org, localGLUhalfEdge1.Sym.Org))
    {
      localGLUhalfEdge2 = localGLUhalfEdge1.Onext.Sym;
      i = 0;
    }
    for (;;)
    {
      if (localGLUhalfEdge1.Lnext == localGLUhalfEdge2)
      {
        label99:
        if (($assertionsDisabled) || (localGLUhalfEdge2.Lnext != localGLUhalfEdge1)) {
          break label536;
        }
        throw new AssertionError();
        localGLUhalfEdge1 = localGLUhalfEdge1.Lnext;
        break;
      }
      if ((paramBoolean) && (i == 0))
      {
        if (Geom.EdgeCos(localGLUhalfEdge2.Lnext.Org, localGLUhalfEdge2.Org, localGLUhalfEdge2.Lnext.Lnext.Org) <= -0.9999900000000001D)
        {
          do
          {
            localGLUhalfEdge2 = localGLUhalfEdge2.Onext.Sym;
            i = 1;
          } while ((localGLUhalfEdge1.Lnext != localGLUhalfEdge2) && (Geom.EdgeCos(localGLUhalfEdge2.Lnext.Org, localGLUhalfEdge2.Org, localGLUhalfEdge2.Lnext.Lnext.Org) <= -0.9999900000000001D));
          label219:
          if (localGLUhalfEdge1.Lnext == localGLUhalfEdge2) {
            break label399;
          }
        }
      }
      else {
        if (!Geom.VertLeq(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge2.Org)) {
          break label457;
        }
      }
      for (;;)
      {
        if ((localGLUhalfEdge2.Lnext == localGLUhalfEdge1) || ((!Geom.EdgeGoesLeft(localGLUhalfEdge2.Lnext)) && (Geom.EdgeSign(localGLUhalfEdge2.Org, localGLUhalfEdge2.Sym.Org, localGLUhalfEdge2.Lnext.Sym.Org) > 0.0D)))
        {
          localGLUhalfEdge2 = localGLUhalfEdge2.Onext.Sym;
          break;
          if (Geom.EdgeCos(localGLUhalfEdge1.Onext.Sym.Org, localGLUhalfEdge1.Org, localGLUhalfEdge1.Onext.Sym.Onext.Sym.Org) > -0.9999900000000001D) {
            break label219;
          }
          do
          {
            localGLUhalfEdge1 = localGLUhalfEdge1.Lnext;
            i = 1;
            if (localGLUhalfEdge1.Lnext == localGLUhalfEdge2) {
              break;
            }
          } while (Geom.EdgeCos(localGLUhalfEdge1.Onext.Sym.Org, localGLUhalfEdge1.Org, localGLUhalfEdge1.Onext.Sym.Onext.Sym.Org) <= -0.9999900000000001D);
          break label219;
          label399:
          break label99;
        }
        localGLUhalfEdge4 = Mesh.__gl_meshConnect(localGLUhalfEdge2.Lnext, localGLUhalfEdge2);
        if (localGLUhalfEdge4 == null) {
          return false;
        }
        localGLUhalfEdge2 = localGLUhalfEdge4.Sym;
        i = 0;
      }
      label457:
      while ((localGLUhalfEdge2.Lnext != localGLUhalfEdge1) && ((Geom.EdgeGoesRight(localGLUhalfEdge1.Onext.Sym)) || (Geom.EdgeSign(localGLUhalfEdge1.Sym.Org, localGLUhalfEdge1.Org, localGLUhalfEdge1.Onext.Sym.Org) >= 0.0D)))
      {
        do
        {
          localGLUhalfEdge3 = Mesh.__gl_meshConnect(localGLUhalfEdge1, localGLUhalfEdge1.Onext.Sym);
          i = 0;
        } while (localGLUhalfEdge3 == null);
        localGLUhalfEdge1 = localGLUhalfEdge3.Sym;
      }
      localGLUhalfEdge1 = localGLUhalfEdge1.Lnext;
    }
    label536:
    while (localGLUhalfEdge2.Lnext.Lnext != localGLUhalfEdge1)
    {
      GLUhalfEdge localGLUhalfEdge5;
      do
      {
        GLUhalfEdge localGLUhalfEdge4;
        GLUhalfEdge localGLUhalfEdge3;
        localGLUhalfEdge5 = Mesh.__gl_meshConnect(localGLUhalfEdge2.Lnext, localGLUhalfEdge2);
      } while (localGLUhalfEdge5 == null);
      localGLUhalfEdge2 = localGLUhalfEdge5.Sym;
    }
    return true;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.TessMono
 * JD-Core Version:    0.7.0.1
 */