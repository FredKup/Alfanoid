package processing.opengl.tess;

class ActiveRegion
{
  boolean dirty;
  GLUhalfEdge eUp;
  boolean fixUpperEdge;
  boolean inside;
  DictNode nodeUp;
  boolean sentinel;
  int windingNumber;
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.ActiveRegion
 * JD-Core Version:    0.7.0.1
 */