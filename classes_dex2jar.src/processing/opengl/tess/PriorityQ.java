package processing.opengl.tess;

abstract class PriorityQ
{
  public static final int INIT_SIZE = 32;
  
  public static boolean LEQ(Leq paramLeq, Object paramObject1, Object paramObject2)
  {
    return Geom.VertLeq((GLUvertex)paramObject1, (GLUvertex)paramObject2);
  }
  
  static PriorityQ pqNewPriorityQ(Leq paramLeq)
  {
    return new PriorityQSort(paramLeq);
  }
  
  abstract void pqDelete(int paramInt);
  
  abstract void pqDeletePriorityQ();
  
  abstract Object pqExtractMin();
  
  abstract boolean pqInit();
  
  abstract int pqInsert(Object paramObject);
  
  abstract boolean pqIsEmpty();
  
  abstract Object pqMinimum();
  
  public static abstract interface Leq
  {
    public abstract boolean leq(Object paramObject1, Object paramObject2);
  }
  
  public static class PQhandleElem
  {
    Object key;
    int node;
  }
  
  public static class PQnode
  {
    int handle;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.PriorityQ
 * JD-Core Version:    0.7.0.1
 */