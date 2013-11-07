package processing.opengl.tess;

class GLUvertex
{
  public GLUhalfEdge anEdge;
  public double[] coords = new double[3];
  public Object data;
  public GLUvertex next;
  public int pqHandle;
  public GLUvertex prev;
  public double s;
  public double t;
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.GLUvertex
 * JD-Core Version:    0.7.0.1
 */