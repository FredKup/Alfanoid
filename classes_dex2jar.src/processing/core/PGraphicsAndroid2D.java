package processing.core;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import java.lang.reflect.Array;
import java.util.zip.GZIPInputStream;
import processing.data.XML;

public class PGraphicsAndroid2D
  extends PGraphics
{
  static int[] getset = new int[1];
  boolean breakShape;
  public Canvas canvas;
  float[] curveCoordX;
  float[] curveCoordY;
  float[] curveDrawX;
  float[] curveDrawY;
  Paint fillPaint = new Paint();
  RectF imageImplDstRect;
  Rect imageImplSrcRect;
  Path path = new Path();
  RectF rect = new RectF();
  float[] screenPoint;
  Paint strokePaint;
  Paint tintPaint;
  float[] transform = new float[9];
  
  public PGraphicsAndroid2D()
  {
    this.fillPaint.setStyle(Paint.Style.FILL);
    this.strokePaint = new Paint();
    this.strokePaint.setStyle(Paint.Style.STROKE);
    this.tintPaint = new Paint(2);
  }
  
  protected void allocate()
  {
    this.bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
    this.canvas = new Canvas(this.bitmap);
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    Matrix localMatrix = new Matrix();
    localMatrix.setValues(new float[] { paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, 0.0F, 0.0F, 1.0F });
    this.canvas.concat(localMatrix);
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    showVariationWarning("applyMatrix");
  }
  
  protected void arcImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt)
  {
    if (paramFloat6 - paramFloat5 >= 6.283186F) {
      ellipseImpl(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    }
    float f1;
    float f3;
    label186:
    do
    {
      do
      {
        do
        {
          return;
          f1 = paramFloat5 * 57.295776F;
          for (float f2 = paramFloat6 * 57.295776F;; f2 += 360.0F)
          {
            if (f1 >= 0.0F)
            {
              if (f1 > f2)
              {
                float f4 = f1;
                f1 = f2;
                f2 = f4;
              }
              f3 = f2 - f1;
              this.rect.set(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
              if (paramInt != 0) {
                break label186;
              }
              if (this.fill)
              {
                Canvas localCanvas5 = this.canvas;
                RectF localRectF5 = this.rect;
                Paint localPaint5 = this.fillPaint;
                localCanvas5.drawArc(localRectF5, f1, f3, true, localPaint5);
              }
              if (!this.stroke) {
                break;
              }
              Canvas localCanvas4 = this.canvas;
              RectF localRectF4 = this.rect;
              Paint localPaint4 = this.strokePaint;
              localCanvas4.drawArc(localRectF4, f1, f3, false, localPaint4);
              return;
            }
            f1 += 360.0F;
          }
          if (paramInt != 1) {
            break;
          }
          if (this.fill) {
            showMissingWarning("arc");
          }
        } while (!this.stroke);
        Canvas localCanvas3 = this.canvas;
        RectF localRectF3 = this.rect;
        Paint localPaint3 = this.strokePaint;
        localCanvas3.drawArc(localRectF3, f1, f3, false, localPaint3);
        return;
        if (paramInt == 2)
        {
          showMissingWarning("arc");
          return;
        }
      } while (paramInt != 3);
      if (this.fill)
      {
        Canvas localCanvas2 = this.canvas;
        RectF localRectF2 = this.rect;
        Paint localPaint2 = this.fillPaint;
        localCanvas2.drawArc(localRectF2, f1, f3, true, localPaint2);
      }
    } while (!this.stroke);
    Canvas localCanvas1 = this.canvas;
    RectF localRectF1 = this.rect;
    Paint localPaint1 = this.strokePaint;
    localCanvas1.drawArc(localRectF1, f1, f3, true, localPaint1);
  }
  
  public void backgroundImpl()
  {
    this.canvas.drawColor(this.backgroundColor);
  }
  
  public void beginDraw()
  {
    checkSettings();
    resetMatrix();
    this.vertexCount = 0;
  }
  
  public void beginRaw(PGraphics paramPGraphics)
  {
    showMethodWarning("beginRaw");
  }
  
  public void beginShape(int paramInt)
  {
    this.shape = paramInt;
    this.vertexCount = 0;
    this.curveVertexCount = 0;
  }
  
  protected void beginTextScreenMode()
  {
    loadPixels();
  }
  
  public void bezierDetail(int paramInt) {}
  
  public void bezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    bezierVertexCheck();
    this.path.cubicTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public void bezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    showDepthWarningXYZ("bezierVertex");
  }
  
  public void box(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showMethodWarning("box");
  }
  
  public void breakShape()
  {
    this.breakShape = true;
  }
  
  public void copy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    this.rect.set(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    Rect localRect = new Rect(paramInt5, paramInt6, paramInt5 + paramInt7, paramInt6 + paramInt8);
    this.canvas.drawBitmap(this.bitmap, localRect, this.rect, null);
  }
  
  public void curveDetail(int paramInt) {}
  
  public void curveVertex(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("curveVertex");
  }
  
  protected void curveVertexCheck()
  {
    super.curveVertexCheck();
    if (this.curveCoordX == null)
    {
      this.curveCoordX = new float[4];
      this.curveCoordY = new float[4];
      this.curveDrawX = new float[4];
      this.curveDrawY = new float[4];
    }
  }
  
  protected void curveVertexSegment(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    this.curveCoordX[0] = paramFloat1;
    this.curveCoordY[0] = paramFloat2;
    this.curveCoordX[1] = paramFloat3;
    this.curveCoordY[1] = paramFloat4;
    this.curveCoordX[2] = paramFloat5;
    this.curveCoordY[2] = paramFloat6;
    this.curveCoordX[3] = paramFloat7;
    this.curveCoordY[3] = paramFloat8;
    this.curveToBezierMatrix.mult(this.curveCoordX, this.curveDrawX);
    this.curveToBezierMatrix.mult(this.curveCoordY, this.curveDrawY);
    if (this.vertexCount == 0)
    {
      this.path.moveTo(this.curveDrawX[0], this.curveDrawY[0]);
      this.vertexCount = 1;
    }
    this.path.cubicTo(this.curveDrawX[1], this.curveDrawY[1], this.curveDrawX[2], this.curveDrawY[2], this.curveDrawX[3], this.curveDrawY[3]);
  }
  
  public void dispose()
  {
    this.bitmap.recycle();
  }
  
  protected void drawPath()
  {
    if (this.fill) {
      this.canvas.drawPath(this.path, this.fillPaint);
    }
    if (this.stroke) {
      this.canvas.drawPath(this.path, this.strokePaint);
    }
  }
  
  protected void ellipseImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.rect.set(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
    if (this.fill) {
      this.canvas.drawOval(this.rect, this.fillPaint);
    }
    if (this.stroke) {
      this.canvas.drawOval(this.rect, this.strokePaint);
    }
  }
  
  public void endDraw()
  {
    Canvas localCanvas;
    if (this.primarySurface) {
      localCanvas = null;
    }
    for (;;)
    {
      try
      {
        localCanvas = this.parent.getSurfaceHolder().lockCanvas(null);
        if (localCanvas != null) {
          localCanvas.drawBitmap(this.bitmap, new Matrix(), null);
        }
        if (localCanvas != null) {
          this.parent.getSurfaceHolder().unlockCanvasAndPost(localCanvas);
        }
        setModified();
        super.updatePixels();
        return;
      }
      finally
      {
        if (localCanvas != null) {
          this.parent.getSurfaceHolder().unlockCanvasAndPost(localCanvas);
        }
      }
      loadPixels();
    }
  }
  
  public void endRaw()
  {
    showMethodWarning("endRaw");
  }
  
  public void endShape(int paramInt)
  {
    Matrix localMatrix;
    int j;
    if ((this.shape == 3) && (this.stroke) && (this.vertexCount > 0))
    {
      localMatrix = this.canvas.getMatrix();
      if ((this.strokeWeight == 1.0F) && (localMatrix.isIdentity()))
      {
        if (this.screenPoint == null) {
          this.screenPoint = new float[2];
        }
        j = 0;
        if (j < this.vertexCount) {}
      }
    }
    for (;;)
    {
      this.shape = 0;
      return;
      this.screenPoint[0] = this.vertices[j][0];
      this.screenPoint[1] = this.vertices[j][1];
      localMatrix.mapPoints(this.screenPoint);
      set(PApplet.round(this.screenPoint[0]), PApplet.round(this.screenPoint[1]), this.strokeColor);
      float f4 = this.vertices[j][0];
      float f5 = this.vertices[j][1];
      set(PApplet.round(screenX(f4, f5)), PApplet.round(screenY(f4, f5)), this.strokeColor);
      j++;
      break;
      float f1 = this.strokeWeight / 2.0F;
      this.strokePaint.setStyle(Paint.Style.FILL);
      for (int i = 0;; i++)
      {
        if (i >= this.vertexCount)
        {
          this.strokePaint.setStyle(Paint.Style.STROKE);
          break;
        }
        float f2 = this.vertices[i][0];
        float f3 = this.vertices[i][1];
        this.rect.set(f2 - f1, f3 - f1, f2 + f1, f3 + f1);
        this.canvas.drawOval(this.rect, this.strokePaint);
      }
      if ((this.shape == 20) && (!this.path.isEmpty()))
      {
        if (paramInt == 2) {
          this.path.close();
        }
        drawPath();
      }
    }
  }
  
  protected void endTextScreenMode()
  {
    updatePixels();
  }
  
  protected void fillFromCalc()
  {
    super.fillFromCalc();
    this.fillPaint.setColor(this.fillColor);
    this.fillPaint.setShader(null);
  }
  
  public int get(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
      return 0;
    }
    return this.bitmap.getPixel(paramInt1, paramInt2);
  }
  
  public PImage get()
  {
    return get(0, 0, this.width, this.height);
  }
  
  public PMatrix2D getMatrix(PMatrix2D paramPMatrix2D)
  {
    if (paramPMatrix2D == null) {
      paramPMatrix2D = new PMatrix2D();
    }
    Matrix localMatrix = new Matrix();
    this.canvas.getMatrix(localMatrix);
    localMatrix.getValues(this.transform);
    float f1 = this.transform[0];
    float f2 = this.transform[1];
    float f3 = this.transform[2];
    float f4 = this.transform[3];
    float f5 = this.transform[4];
    float f6 = this.transform[5];
    paramPMatrix2D.set(f1, f2, f3, f4, f5, f6);
    return paramPMatrix2D;
  }
  
  public PMatrix3D getMatrix(PMatrix3D paramPMatrix3D)
  {
    showVariationWarning("getMatrix");
    return paramPMatrix3D;
  }
  
  public PMatrix getMatrix()
  {
    return getMatrix(null);
  }
  
  protected void imageImpl(PImage paramPImage, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int[] arrayOfInt;
    int i;
    label249:
    Canvas localCanvas;
    Bitmap localBitmap;
    Rect localRect;
    RectF localRectF;
    if ((paramPImage.bitmap == null) && (paramPImage.format == 4))
    {
      paramPImage.bitmap = Bitmap.createBitmap(paramPImage.width, paramPImage.height, Bitmap.Config.ARGB_8888);
      arrayOfInt = new int[paramPImage.pixels.length];
      i = 0;
      if (i >= arrayOfInt.length)
      {
        paramPImage.bitmap.setPixels(arrayOfInt, 0, paramPImage.width, 0, 0, paramPImage.width, paramPImage.height);
        paramPImage.modified = false;
      }
    }
    else
    {
      if ((paramPImage.bitmap == null) || (paramPImage.width != paramPImage.bitmap.getWidth()) || (paramPImage.height != paramPImage.bitmap.getHeight()))
      {
        paramPImage.bitmap = Bitmap.createBitmap(paramPImage.width, paramPImage.height, Bitmap.Config.ARGB_8888);
        paramPImage.modified = true;
      }
      if (paramPImage.modified)
      {
        if (!paramPImage.bitmap.isMutable()) {
          paramPImage.bitmap = Bitmap.createBitmap(paramPImage.width, paramPImage.height, Bitmap.Config.ARGB_8888);
        }
        paramPImage.bitmap.setPixels(paramPImage.pixels, 0, paramPImage.width, 0, 0, paramPImage.width, paramPImage.height);
        paramPImage.modified = false;
      }
      if (this.imageImplSrcRect != null) {
        break label325;
      }
      this.imageImplSrcRect = new Rect(paramInt1, paramInt2, paramInt3, paramInt4);
      this.imageImplDstRect = new RectF(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      localCanvas = this.canvas;
      localBitmap = paramPImage.bitmap;
      localRect = this.imageImplSrcRect;
      localRectF = this.imageImplDstRect;
      if (!this.tint) {
        break label356;
      }
    }
    label325:
    label356:
    for (Paint localPaint = this.tintPaint;; localPaint = null)
    {
      localCanvas.drawBitmap(localBitmap, localRect, localRectF, localPaint);
      return;
      arrayOfInt[i] = (0xFFFFFF | paramPImage.pixels[i] << 24);
      i++;
      break;
      this.imageImplSrcRect.set(paramInt1, paramInt2, paramInt3, paramInt4);
      this.imageImplDstRect.set(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      break label249;
    }
  }
  
  public void line(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (this.stroke) {
      this.canvas.drawLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.strokePaint);
    }
  }
  
  public void loadPixels()
  {
    if ((this.pixels == null) || (this.pixels.length != this.width * this.height)) {
      this.pixels = new int[this.width * this.height];
    }
    this.bitmap.getPixels(this.pixels, 0, this.width, 0, 0, this.width, this.height);
  }
  
  public PShape loadShape(String paramString)
  {
    String str = PApplet.getExtension(paramString);
    if (str.equals("svg")) {
      return new PShapeSVG(this.parent.loadXML(paramString));
    }
    if (str.equals("svgz")) {
      try
      {
        PShapeSVG localPShapeSVG = new PShapeSVG(new XML(new GZIPInputStream(this.parent.createInput(paramString))));
        return localPShapeSVG;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
    }
    PGraphics.showWarning("Unsupported format");
    return null;
  }
  
  public void mask(PImage paramPImage)
  {
    showMethodWarning("mask");
  }
  
  public void mask(int[] paramArrayOfInt)
  {
    showMethodWarning("mask");
  }
  
  public void noSmooth()
  {
    this.smooth = false;
    this.strokePaint.setAntiAlias(false);
    this.fillPaint.setAntiAlias(false);
  }
  
  public void point(float paramFloat1, float paramFloat2)
  {
    beginShape(3);
    vertex(paramFloat1, paramFloat2);
    endShape();
  }
  
  public void popMatrix()
  {
    this.canvas.restore();
  }
  
  public void printMatrix()
  {
    getMatrix(null).print();
  }
  
  public void pushMatrix()
  {
    this.canvas.save(1);
  }
  
  public void quad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    this.path.reset();
    this.path.moveTo(paramFloat1, paramFloat2);
    this.path.lineTo(paramFloat3, paramFloat4);
    this.path.lineTo(paramFloat5, paramFloat6);
    this.path.lineTo(paramFloat7, paramFloat8);
    this.path.close();
    drawPath();
  }
  
  public void quadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    bezierVertexCheck();
    this.path.quadTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void quadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    showDepthWarningXYZ("quadVertex");
  }
  
  protected void rectImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (this.fill) {
      this.canvas.drawRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.fillPaint);
    }
    if (this.stroke) {
      this.canvas.drawRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.strokePaint);
    }
  }
  
  public void requestDraw()
  {
    this.parent.handleDraw();
  }
  
  public void resetMatrix()
  {
    this.canvas.setMatrix(new Matrix());
  }
  
  public void resize(int paramInt1, int paramInt2)
  {
    showMethodWarning("resize");
  }
  
  public void rotate(float paramFloat)
  {
    this.canvas.rotate(57.295776F * paramFloat);
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
  
  public void scale(float paramFloat)
  {
    this.canvas.scale(paramFloat, paramFloat);
  }
  
  public void scale(float paramFloat1, float paramFloat2)
  {
    this.canvas.scale(paramFloat1, paramFloat2);
  }
  
  public void scale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("scale");
  }
  
  public float screenX(float paramFloat1, float paramFloat2)
  {
    if (this.screenPoint == null) {
      this.screenPoint = new float[2];
    }
    this.screenPoint[0] = paramFloat1;
    this.screenPoint[1] = paramFloat2;
    this.canvas.getMatrix().mapPoints(this.screenPoint);
    return this.screenPoint[0];
  }
  
  public float screenX(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("screenX");
    return 0.0F;
  }
  
  public float screenY(float paramFloat1, float paramFloat2)
  {
    if (this.screenPoint == null) {
      this.screenPoint = new float[2];
    }
    this.screenPoint[0] = paramFloat1;
    this.screenPoint[1] = paramFloat2;
    this.canvas.getMatrix().mapPoints(this.screenPoint);
    return this.screenPoint[1];
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
  
  public void set(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 >= this.width) || (paramInt2 >= this.height)) {
      return;
    }
    this.bitmap.setPixel(paramInt1, paramInt2, paramInt3);
  }
  
  public void set(int paramInt1, int paramInt2, PImage paramPImage)
  {
    if (paramPImage.format == 4) {
      throw new RuntimeException("set() not available for ALPHA images");
    }
    if (paramPImage.bitmap == null)
    {
      this.canvas.drawBitmap(paramPImage.pixels, 0, paramPImage.width, paramInt1, paramInt2, paramPImage.width, paramPImage.height, false, null);
      return;
    }
    if ((paramPImage.width != paramPImage.bitmap.getWidth()) || (paramPImage.height != paramPImage.bitmap.getHeight()))
    {
      paramPImage.bitmap = Bitmap.createBitmap(paramPImage.width, paramPImage.height, Bitmap.Config.ARGB_8888);
      paramPImage.modified = true;
    }
    if (paramPImage.modified)
    {
      if (!paramPImage.bitmap.isMutable()) {
        paramPImage.bitmap = Bitmap.createBitmap(paramPImage.width, paramPImage.height, Bitmap.Config.ARGB_8888);
      }
      paramPImage.bitmap.setPixels(paramPImage.pixels, 0, paramPImage.width, 0, 0, paramPImage.width, paramPImage.height);
      paramPImage.modified = false;
    }
    this.canvas.save(1);
    this.canvas.setMatrix(null);
    this.canvas.drawBitmap(paramPImage.bitmap, paramInt1, paramInt2, null);
    this.canvas.restore();
  }
  
  public void setMatrix(PMatrix2D paramPMatrix2D)
  {
    Matrix localMatrix = new Matrix();
    float[] arrayOfFloat = new float[9];
    arrayOfFloat[0] = paramPMatrix2D.m00;
    arrayOfFloat[1] = paramPMatrix2D.m01;
    arrayOfFloat[2] = paramPMatrix2D.m02;
    arrayOfFloat[3] = paramPMatrix2D.m10;
    arrayOfFloat[4] = paramPMatrix2D.m11;
    arrayOfFloat[5] = paramPMatrix2D.m12;
    arrayOfFloat[6] = 0.0F;
    arrayOfFloat[7] = 0.0F;
    arrayOfFloat[8] = 1.0F;
    localMatrix.setValues(arrayOfFloat);
    this.canvas.setMatrix(localMatrix);
  }
  
  public void setMatrix(PMatrix3D paramPMatrix3D)
  {
    showVariationWarning("setMatrix");
  }
  
  public void setSize(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
    this.width1 = (-1 + this.width);
    this.height1 = (-1 + this.height);
    allocate();
    reapplySettings();
  }
  
  public void shearX(float paramFloat)
  {
    this.canvas.skew((float)Math.tan(paramFloat), 0.0F);
  }
  
  public void shearY(float paramFloat)
  {
    this.canvas.skew(0.0F, (float)Math.tan(paramFloat));
  }
  
  public void smooth()
  {
    this.smooth = true;
    this.strokePaint.setAntiAlias(true);
    this.fillPaint.setAntiAlias(true);
  }
  
  public void sphere(float paramFloat)
  {
    showMethodWarning("sphere");
  }
  
  public void strokeCap(int paramInt)
  {
    super.strokeCap(paramInt);
    if (this.strokeCap == 2)
    {
      this.strokePaint.setStrokeCap(Paint.Cap.ROUND);
      return;
    }
    if (this.strokeCap == 4)
    {
      this.strokePaint.setStrokeCap(Paint.Cap.SQUARE);
      return;
    }
    this.strokePaint.setStrokeCap(Paint.Cap.BUTT);
  }
  
  protected void strokeFromCalc()
  {
    super.strokeFromCalc();
    this.strokePaint.setColor(this.strokeColor);
    this.strokePaint.setShader(null);
  }
  
  public void strokeJoin(int paramInt)
  {
    super.strokeJoin(paramInt);
    if (this.strokeJoin == 8)
    {
      this.strokePaint.setStrokeJoin(Paint.Join.MITER);
      return;
    }
    if (this.strokeJoin == 2)
    {
      this.strokePaint.setStrokeJoin(Paint.Join.ROUND);
      return;
    }
    this.strokePaint.setStrokeJoin(Paint.Join.BEVEL);
  }
  
  public void strokeWeight(float paramFloat)
  {
    super.strokeWeight(paramFloat);
    this.strokePaint.setStrokeWidth(paramFloat);
  }
  
  public void textFont(PFont paramPFont)
  {
    super.textFont(paramPFont);
    this.fillPaint.setTypeface((Typeface)paramPFont.getNative());
  }
  
  protected void textLineImpl(char[] paramArrayOfChar, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    if ((Typeface)this.textFont.getNative() == null)
    {
      showWarning("Inefficient font rendering: use createFont() with a TTF/OTF instead of loadFont().");
      super.textLineImpl(paramArrayOfChar, paramInt1, paramInt2, paramFloat1, paramFloat2);
      return;
    }
    this.fillPaint.setAntiAlias(this.textFont.smooth);
    int i = paramInt2 - paramInt1;
    this.canvas.drawText(paramArrayOfChar, paramInt1, i, paramFloat1, paramFloat2, this.fillPaint);
    this.fillPaint.setAntiAlias(this.smooth);
  }
  
  protected boolean textModeCheck(int paramInt)
  {
    return paramInt == 4;
  }
  
  public void textSize(float paramFloat)
  {
    if (this.textFont == null) {
      defaultFontOrDeath("textSize", paramFloat);
    }
    if ((Typeface)this.textFont.getNative() != null) {
      this.fillPaint.setTextSize(paramFloat);
    }
    this.textSize = paramFloat;
    this.textLeading = (1.275F * (textAscent() + textDescent()));
  }
  
  protected float textWidthImpl(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if ((Typeface)this.textFont.getNative() == null) {
      return super.textWidthImpl(paramArrayOfChar, paramInt1, paramInt2);
    }
    int i = paramInt2 - paramInt1;
    return this.fillPaint.measureText(paramArrayOfChar, paramInt1, i);
  }
  
  public void texture(PImage paramPImage)
  {
    showMethodWarning("texture");
  }
  
  protected void tintFromCalc()
  {
    super.tintFromCalc();
    this.tintPaint.setColorFilter(new PorterDuffColorFilter(this.tintColor, PorterDuff.Mode.MULTIPLY));
  }
  
  public void translate(float paramFloat1, float paramFloat2)
  {
    this.canvas.translate(paramFloat1, paramFloat2);
  }
  
  public void triangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.path.reset();
    this.path.moveTo(paramFloat1, paramFloat2);
    this.path.lineTo(paramFloat3, paramFloat4);
    this.path.lineTo(paramFloat5, paramFloat6);
    this.path.close();
    drawPath();
  }
  
  public void updatePixels()
  {
    this.bitmap.setPixels(this.pixels, 0, this.width, 0, 0, this.width, this.height);
  }
  
  public void updatePixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt1 != 0) || (paramInt2 != 0) || (paramInt3 != this.width) || (paramInt4 != this.height)) {
      showVariationWarning("updatePixels(x, y, w, h)");
    }
    updatePixels();
  }
  
  public void vertex(float paramFloat1, float paramFloat2)
  {
    if (this.shape == 20) {
      if (this.vertexCount == 0)
      {
        this.path.reset();
        this.path.moveTo(paramFloat1, paramFloat2);
        this.vertexCount = 1;
      }
    }
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                return;
                if (this.breakShape)
                {
                  this.path.moveTo(paramFloat1, paramFloat2);
                  this.breakShape = false;
                  return;
                }
                this.path.lineTo(paramFloat1, paramFloat2);
                return;
                this.curveVertexCount = 0;
                if (this.vertexCount == this.vertices.length)
                {
                  int[] arrayOfInt = { this.vertexCount << 1, 37 };
                  float[][] arrayOfFloat = (float[][])Array.newInstance(Float.TYPE, arrayOfInt);
                  System.arraycopy(this.vertices, 0, arrayOfFloat, 0, this.vertexCount);
                  this.vertices = arrayOfFloat;
                }
                this.vertices[this.vertexCount][0] = paramFloat1;
                this.vertices[this.vertexCount][1] = paramFloat2;
                this.vertexCount = (1 + this.vertexCount);
                switch (this.shape)
                {
                case 3: 
                case 4: 
                case 6: 
                case 7: 
                case 8: 
                case 12: 
                case 13: 
                case 14: 
                case 15: 
                default: 
                  return;
                }
              } while (this.vertexCount % 2 != 0);
              line(this.vertices[(-2 + this.vertexCount)][0], this.vertices[(-2 + this.vertexCount)][1], paramFloat1, paramFloat2);
              this.vertexCount = 0;
              return;
            } while (this.vertexCount % 3 != 0);
            triangle(this.vertices[(-3 + this.vertexCount)][0], this.vertices[(-3 + this.vertexCount)][1], this.vertices[(-2 + this.vertexCount)][0], this.vertices[(-2 + this.vertexCount)][1], paramFloat1, paramFloat2);
            this.vertexCount = 0;
            return;
          } while (this.vertexCount < 3);
          triangle(this.vertices[(-2 + this.vertexCount)][0], this.vertices[(-2 + this.vertexCount)][1], paramFloat1, paramFloat2, this.vertices[(-3 + this.vertexCount)][0], this.vertices[(-3 + this.vertexCount)][1]);
          return;
        } while (this.vertexCount < 3);
        triangle(this.vertices[0][0], this.vertices[0][1], this.vertices[(-2 + this.vertexCount)][0], this.vertices[(-2 + this.vertexCount)][1], paramFloat1, paramFloat2);
        return;
      } while (this.vertexCount % 4 != 0);
      quad(this.vertices[(-4 + this.vertexCount)][0], this.vertices[(-4 + this.vertexCount)][1], this.vertices[(-3 + this.vertexCount)][0], this.vertices[(-3 + this.vertexCount)][1], this.vertices[(-2 + this.vertexCount)][0], this.vertices[(-2 + this.vertexCount)][1], paramFloat1, paramFloat2);
      this.vertexCount = 0;
      return;
    } while ((this.vertexCount < 4) || (this.vertexCount % 2 != 0));
    quad(this.vertices[(-4 + this.vertexCount)][0], this.vertices[(-4 + this.vertexCount)][1], this.vertices[(-2 + this.vertexCount)][0], this.vertices[(-2 + this.vertexCount)][1], paramFloat1, paramFloat2, this.vertices[(-3 + this.vertexCount)][0], this.vertices[(-3 + this.vertexCount)][1]);
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    showDepthWarningXYZ("vertex");
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    showVariationWarning("vertex(x, y, u, v)");
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    showDepthWarningXYZ("vertex");
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PGraphicsAndroid2D
 * JD-Core Version:    0.7.0.1
 */