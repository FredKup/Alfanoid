package processing.opengl.tess;

class GLUhalfEdge
{
  public GLUface Lface;
  public GLUhalfEdge Lnext;
  public GLUhalfEdge Onext;
  public GLUvertex Org;
  public GLUhalfEdge Sym;
  public ActiveRegion activeRegion;
  public boolean first;
  public GLUhalfEdge next;
  public int winding;
  
  public GLUhalfEdge(boolean paramBoolean)
  {
    this.first = paramBoolean;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.GLUhalfEdge
 * JD-Core Version:    0.7.0.1
 */