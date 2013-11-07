package processing.opengl.tess;

class GLUmesh
{
  GLUhalfEdge eHead = new GLUhalfEdge(true);
  GLUhalfEdge eHeadSym = new GLUhalfEdge(false);
  GLUface fHead = new GLUface();
  GLUvertex vHead = new GLUvertex();
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.GLUmesh
 * JD-Core Version:    0.7.0.1
 */