package processing.opengl.tess;

public abstract interface PGLUtessellatorCallback
{
  public abstract void begin(int paramInt);
  
  public abstract void beginData(int paramInt, Object paramObject);
  
  public abstract void combine(double[] paramArrayOfDouble, Object[] paramArrayOfObject1, float[] paramArrayOfFloat, Object[] paramArrayOfObject2);
  
  public abstract void combineData(double[] paramArrayOfDouble, Object[] paramArrayOfObject1, float[] paramArrayOfFloat, Object[] paramArrayOfObject2, Object paramObject);
  
  public abstract void edgeFlag(boolean paramBoolean);
  
  public abstract void edgeFlagData(boolean paramBoolean, Object paramObject);
  
  public abstract void end();
  
  public abstract void endData(Object paramObject);
  
  public abstract void error(int paramInt);
  
  public abstract void errorData(int paramInt, Object paramObject);
  
  public abstract void vertex(Object paramObject);
  
  public abstract void vertexData(Object paramObject1, Object paramObject2);
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.PGLUtessellatorCallback
 * JD-Core Version:    0.7.0.1
 */