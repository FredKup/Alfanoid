package processing.opengl;

import java.util.zip.GZIPInputStream;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PShape;
import processing.core.PShapeOBJ;

public class PGraphics3D
  extends PGraphicsOpenGL
{
  protected static PShapeOpenGL createShapeImpl(PApplet paramPApplet, int paramInt)
  {
    PShapeOpenGL localPShapeOpenGL;
    if (paramInt == 0) {
      localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 0);
    }
    for (;;)
    {
      localPShapeOpenGL.is3D(true);
      return localPShapeOpenGL;
      if (paramInt == 2)
      {
        localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 2);
      }
      else
      {
        localPShapeOpenGL = null;
        if (paramInt == 3) {
          localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 3);
        }
      }
    }
  }
  
  protected static PShapeOpenGL createShapeImpl(PApplet paramPApplet, int paramInt, float... paramVarArgs)
  {
    int i = paramVarArgs.length;
    PShapeOpenGL localPShapeOpenGL;
    if (paramInt == 2)
    {
      if ((i != 2) && (i != 3))
      {
        showWarning("Wrong number of parameters");
        return null;
      }
      localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 1);
      localPShapeOpenGL.setKind(2);
    }
    for (;;)
    {
      if (localPShapeOpenGL != null) {
        localPShapeOpenGL.setParams(paramVarArgs);
      }
      localPShapeOpenGL.is3D(true);
      return localPShapeOpenGL;
      if (paramInt == 4)
      {
        if ((i != 4) && (i != 6))
        {
          showWarning("Wrong number of parameters");
          return null;
        }
        localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 1);
        localPShapeOpenGL.setKind(4);
      }
      else if (paramInt == 8)
      {
        if (i != 6)
        {
          showWarning("Wrong number of parameters");
          return null;
        }
        localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 1);
        localPShapeOpenGL.setKind(8);
      }
      else if (paramInt == 16)
      {
        if (i != 8)
        {
          showWarning("Wrong number of parameters");
          return null;
        }
        localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 1);
        localPShapeOpenGL.setKind(16);
      }
      else if (paramInt == 30)
      {
        if ((i != 4) && (i != 5) && (i != 8))
        {
          showWarning("Wrong number of parameters");
          return null;
        }
        localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 1);
        localPShapeOpenGL.setKind(30);
      }
      else if (paramInt == 31)
      {
        if (i != 4)
        {
          showWarning("Wrong number of parameters");
          return null;
        }
        localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 1);
        localPShapeOpenGL.setKind(31);
      }
      else if (paramInt == 32)
      {
        if ((i != 6) && (i != 7))
        {
          showWarning("Wrong number of parameters");
          return null;
        }
        localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 1);
        localPShapeOpenGL.setKind(32);
      }
      else if (paramInt == 41)
      {
        if ((i != 1) && (i != 3))
        {
          showWarning("Wrong number of parameters");
          return null;
        }
        localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 1);
        localPShapeOpenGL.setKind(41);
      }
      else if (paramInt == 40)
      {
        if (i != 1)
        {
          showWarning("Wrong number of parameters");
          return null;
        }
        localPShapeOpenGL = new PShapeOpenGL(paramPApplet, 1);
        localPShapeOpenGL.setKind(40);
      }
      else
      {
        showWarning("Unrecognized primitive type");
        localPShapeOpenGL = null;
      }
    }
  }
  
  protected static boolean isSupportedExtension(String paramString)
  {
    return paramString.equals("obj");
  }
  
  protected static PShape loadShapeImpl(PGraphics paramPGraphics, String paramString1, String paramString2)
  {
    Object localObject;
    if (paramString2.equals("obj")) {
      localObject = new PShapeOBJ(paramPGraphics.parent, paramString1);
    }
    while (localObject != null)
    {
      int i = paramPGraphics.textureMode;
      paramPGraphics.textureMode = 1;
      PShapeOpenGL localPShapeOpenGL = PShapeOpenGL.createShape3D(paramPGraphics.parent, (PShape)localObject);
      paramPGraphics.textureMode = i;
      return localPShapeOpenGL;
      boolean bool = paramString2.equals("objz");
      localObject = null;
      if (bool) {
        try
        {
          GZIPInputStream localGZIPInputStream = new GZIPInputStream(paramPGraphics.parent.createInput(paramString1));
          PShapeOBJ localPShapeOBJ = new PShapeOBJ(paramPGraphics.parent, PApplet.createReader(localGZIPInputStream));
          localObject = localPShapeOBJ;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          localObject = null;
        }
      }
    }
    return null;
  }
  
  protected void begin2D()
  {
    pushProjection();
    ortho(0.0F, this.width, 0.0F, this.height, -1.0F, 1.0F);
    pushMatrix();
    camera(this.width / 2, this.height / 2);
  }
  
  public PShape createShape()
  {
    return createShape(3);
  }
  
  public PShape createShape(int paramInt)
  {
    return createShapeImpl(this.parent, paramInt);
  }
  
  public PShape createShape(int paramInt, float... paramVarArgs)
  {
    return createShapeImpl(this.parent, paramInt, paramVarArgs);
  }
  
  public PShape createShape(PShape paramPShape)
  {
    return PShapeOpenGL.createShape3D(this.parent, paramPShape);
  }
  
  protected void defaultCamera()
  {
    camera();
  }
  
  protected void defaultPerspective()
  {
    perspective();
  }
  
  protected void end2D()
  {
    popMatrix();
    popProjection();
  }
  
  public boolean is2D()
  {
    return false;
  }
  
  public boolean is3D()
  {
    return true;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.PGraphics3D
 * JD-Core Version:    0.7.0.1
 */