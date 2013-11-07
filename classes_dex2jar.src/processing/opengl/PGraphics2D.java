package processing.opengl;

import java.util.zip.GZIPInputStream;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PMatrix3D;
import processing.core.PShape;
import processing.core.PShapeSVG;
import processing.data.XML;

public class PGraphics2D
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
      localPShapeOpenGL.is3D(false);
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
      if (i != 2)
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
      localPShapeOpenGL.is3D(false);
      return localPShapeOpenGL;
      if (paramInt == 4)
      {
        if (i != 4)
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
        showWarning("Primitive not supported in 2D");
        localPShapeOpenGL = null;
      }
      else if (paramInt == 40)
      {
        showWarning("Primitive not supported in 2D");
        localPShapeOpenGL = null;
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
    return (paramString.equals("svg")) || (paramString.equals("svgz"));
  }
  
  protected static PShape loadShapeImpl(PGraphics paramPGraphics, String paramString1, String paramString2)
  {
    Object localObject;
    if (paramString2.equals("svg")) {
      localObject = new PShapeSVG(paramPGraphics.parent.loadXML(paramString1));
    }
    while (localObject != null)
    {
      return PShapeOpenGL.createShape2D(paramPGraphics.parent, (PShape)localObject);
      boolean bool = paramString2.equals("svgz");
      localObject = null;
      if (bool) {
        try
        {
          PShapeSVG localPShapeSVG = new PShapeSVG(new XML(new GZIPInputStream(paramPGraphics.parent.createInput(paramString1))));
          localObject = localPShapeSVG;
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
  
  public void ambientLight(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMethodWarning("ambientLight");
  }
  
  public void ambientLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMethodWarning("ambientLight");
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    showVariationWarning("applyMatrix");
  }
  
  public void applyMatrix(PMatrix3D paramPMatrix3D)
  {
    showVariationWarning("applyMatrix");
  }
  
  protected void begin2D()
  {
    pushProjection();
    defaultPerspective();
    pushMatrix();
    defaultCamera();
  }
  
  public void beginCamera()
  {
    showMethodWarning("beginCamera");
  }
  
  public void bezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    showDepthWarningXYZ("bezierVertex");
  }
  
  public void box(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMethodWarning("box");
  }
  
  public void camera()
  {
    showMethodWarning("camera");
  }
  
  public void camera(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    showMethodWarning("camera");
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
    return PShapeOpenGL.createShape2D(this.parent, paramPShape);
  }
  
  public void curveVertex(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("curveVertex");
  }
  
  protected void defaultCamera()
  {
    super.camera(this.width / 2, this.height / 2);
  }
  
  protected void defaultPerspective()
  {
    super.ortho(0.0F, this.width, 0.0F, this.height, -1.0F, 1.0F);
  }
  
  public void directionalLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMethodWarning("directionalLight");
  }
  
  protected void end2D()
  {
    popMatrix();
    popProjection();
  }
  
  public void endCamera()
  {
    showMethodWarning("endCamera");
  }
  
  public void frustum(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMethodWarning("frustum");
  }
  
  public PMatrix3D getMatrix(PMatrix3D paramPMatrix3D)
  {
    showVariationWarning("getMatrix");
    return paramPMatrix3D;
  }
  
  public void hint(int paramInt)
  {
    if (paramInt == 7)
    {
      showWarning("Strokes cannot be perspective-corrected in 2D.");
      return;
    }
    super.hint(paramInt);
  }
  
  public boolean is2D()
  {
    return true;
  }
  
  public boolean is3D()
  {
    return false;
  }
  
  public void lightFalloff(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMethodWarning("lightFalloff");
  }
  
  public void lightSpecular(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMethodWarning("lightSpecular");
  }
  
  public void lights()
  {
    showMethodWarning("lights");
  }
  
  public void noLights()
  {
    showMethodWarning("noLights");
  }
  
  public void ortho()
  {
    showMethodWarning("ortho");
  }
  
  public void ortho(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    showMethodWarning("ortho");
  }
  
  public void ortho(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMethodWarning("ortho");
  }
  
  public void perspective()
  {
    showMethodWarning("perspective");
  }
  
  public void perspective(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    showMethodWarning("perspective");
  }
  
  public void pointLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showMethodWarning("pointLight");
  }
  
  public void quadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showDepthWarningXYZ("quadVertex");
  }
  
  public void rotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    showVariationWarning("rotate");
  }
  
  public void rotateX(float paramFloat)
  {
    showDepthWarning("rotateX");
  }
  
  public void rotateY(float paramFloat)
  {
    showDepthWarning("rotateY");
  }
  
  public void rotateZ(float paramFloat)
  {
    showDepthWarning("rotateZ");
  }
  
  public void scale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("scale");
  }
  
  public float screenX(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("screenX");
    return 0.0F;
  }
  
  public float screenY(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("screenY");
    return 0.0F;
  }
  
  public float screenZ(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("screenZ");
    return 0.0F;
  }
  
  public void setMatrix(PMatrix3D paramPMatrix3D)
  {
    showVariationWarning("setMatrix");
  }
  
  public void shape(PShape paramPShape)
  {
    if (paramPShape.is2D())
    {
      super.shape(paramPShape);
      return;
    }
    showWarning("The shape object is not 2D, cannot be displayed with this renderer");
  }
  
  public void shape(PShape paramPShape, float paramFloat1, float paramFloat2)
  {
    if (paramPShape.is2D())
    {
      super.shape(paramPShape, paramFloat1, paramFloat2);
      return;
    }
    showWarning("The shape object is not 2D, cannot be displayed with this renderer");
  }
  
  public void shape(PShape paramPShape, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("shape");
  }
  
  public void shape(PShape paramPShape, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (paramPShape.is2D())
    {
      super.shape(paramPShape, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      return;
    }
    showWarning("The shape object is not 2D, cannot be displayed with this renderer");
  }
  
  public void shape(PShape paramPShape, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showDepthWarningXYZ("shape");
  }
  
  public void sphere(float paramFloat)
  {
    showMethodWarning("sphere");
  }
  
  public void spotLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11)
  {
    showMethodWarning("spotLight");
  }
  
  public void translate(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("translate");
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("vertex");
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    showDepthWarningXYZ("vertex");
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.PGraphics2D
 * JD-Core Version:    0.7.0.1
 */