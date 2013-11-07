package processing.data;

public abstract interface TableRow
{
  public abstract double getDouble(int paramInt);
  
  public abstract double getDouble(String paramString);
  
  public abstract float getFloat(int paramInt);
  
  public abstract float getFloat(String paramString);
  
  public abstract int getInt(int paramInt);
  
  public abstract int getInt(String paramString);
  
  public abstract long getLong(int paramInt);
  
  public abstract long getLong(String paramString);
  
  public abstract String getString(int paramInt);
  
  public abstract String getString(String paramString);
  
  public abstract void setDouble(int paramInt, double paramDouble);
  
  public abstract void setDouble(String paramString, double paramDouble);
  
  public abstract void setFloat(int paramInt, float paramFloat);
  
  public abstract void setFloat(String paramString, float paramFloat);
  
  public abstract void setInt(int paramInt1, int paramInt2);
  
  public abstract void setInt(String paramString, int paramInt);
  
  public abstract void setLong(int paramInt, long paramLong);
  
  public abstract void setLong(String paramString, long paramLong);
  
  public abstract void setString(int paramInt, String paramString);
  
  public abstract void setString(String paramString1, String paramString2);
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.data.TableRow
 * JD-Core Version:    0.7.0.1
 */