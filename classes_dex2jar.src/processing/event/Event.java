package processing.event;

public class Event
{
  public static final int ALT = 8;
  public static final int CTRL = 2;
  public static final int KEY = 1;
  public static final int META = 4;
  public static final int MOUSE = 2;
  public static final int SHIFT = 1;
  public static final int TOUCH = 3;
  protected int action;
  protected int flavor;
  protected long millis;
  protected int modifiers;
  protected Object nativeObject;
  
  public Event(Object paramObject, long paramLong, int paramInt1, int paramInt2)
  {
    this.nativeObject = paramObject;
    this.millis = paramLong;
    this.action = paramInt1;
    this.modifiers = paramInt2;
  }
  
  public int getAction()
  {
    return this.action;
  }
  
  public int getFlavor()
  {
    return this.flavor;
  }
  
  public long getMillis()
  {
    return this.millis;
  }
  
  public int getModifiers()
  {
    return this.modifiers;
  }
  
  public Object getNative()
  {
    return this.nativeObject;
  }
  
  public boolean isAltDown()
  {
    return (0x8 & this.modifiers) != 0;
  }
  
  public boolean isControlDown()
  {
    return (0x2 & this.modifiers) != 0;
  }
  
  public boolean isMetaDown()
  {
    return (0x4 & this.modifiers) != 0;
  }
  
  public boolean isShiftDown()
  {
    return (0x1 & this.modifiers) != 0;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.event.Event
 * JD-Core Version:    0.7.0.1
 */