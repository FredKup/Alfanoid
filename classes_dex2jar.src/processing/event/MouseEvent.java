package processing.event;

public class MouseEvent
  extends Event
{
  public static final int CLICK = 3;
  public static final int DRAG = 4;
  public static final int ENTER = 6;
  public static final int EXIT = 7;
  public static final int MOVE = 5;
  public static final int PRESS = 1;
  public static final int RELEASE = 2;
  protected int button;
  protected int clickCount;
  protected int x;
  protected int y;
  
  public MouseEvent(Object paramObject, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    super(paramObject, paramLong, paramInt1, paramInt2);
    this.flavor = 2;
    this.x = paramInt3;
    this.y = paramInt4;
    this.button = paramInt5;
    this.clickCount = paramInt6;
  }
  
  public int getButton()
  {
    return this.button;
  }
  
  public int getClickCount()
  {
    return this.clickCount;
  }
  
  public int getX()
  {
    return this.x;
  }
  
  public int getY()
  {
    return this.y;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.event.MouseEvent
 * JD-Core Version:    0.7.0.1
 */