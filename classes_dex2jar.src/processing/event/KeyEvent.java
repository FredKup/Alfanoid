package processing.event;

public class KeyEvent
  extends Event
{
  public static final int PRESS = 1;
  public static final int RELEASE = 2;
  public static final int TYPE = 3;
  char key;
  int keyCode;
  
  public KeyEvent(Object paramObject, long paramLong, int paramInt1, int paramInt2, char paramChar, int paramInt3)
  {
    super(paramObject, paramLong, paramInt1, paramInt2);
    this.flavor = 1;
    this.key = paramChar;
    this.keyCode = paramInt3;
  }
  
  public char getKey()
  {
    return this.key;
  }
  
  public int getKeyCode()
  {
    return this.keyCode;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.event.KeyEvent
 * JD-Core Version:    0.7.0.1
 */