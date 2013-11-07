package processing.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PMatrix;
import processing.core.PMatrix2D;
import processing.core.PMatrix3D;
import processing.core.PShape;
import processing.core.PVector;

public class PShapeOpenGL
  extends PShape
{
  protected static final int MATRIX = 3;
  protected static final int NORMAL_MODE_AUTO = 0;
  protected static final int NORMAL_MODE_SHAPE = 1;
  protected static final int NORMAL_MODE_VERTEX = 2;
  protected static final int ROTATE = 1;
  protected static final int SCALE = 2;
  protected static final int TRANSLATE;
  protected int bezierDetail = 20;
  protected boolean breakShape = false;
  protected int context;
  protected int curveDetail = 20;
  protected float curveTightness = 0.0F;
  protected int ellipseMode;
  protected int firstLineIndexCache;
  protected int firstLineVertex;
  protected int firstModifiedLineAttribute;
  protected int firstModifiedLineColor;
  protected int firstModifiedLineVertex;
  protected int firstModifiedPointAttribute;
  protected int firstModifiedPointColor;
  protected int firstModifiedPointVertex;
  protected int firstModifiedPolyAmbient;
  protected int firstModifiedPolyColor;
  protected int firstModifiedPolyEmissive;
  protected int firstModifiedPolyNormal;
  protected int firstModifiedPolyShininess;
  protected int firstModifiedPolySpecular;
  protected int firstModifiedPolyTexcoord;
  protected int firstModifiedPolyVertex;
  protected int firstPointIndexCache;
  protected int firstPointVertex;
  protected int firstPolyIndexCache;
  protected int firstPolyVertex;
  public int glLineAttrib;
  public int glLineColor;
  public int glLineIndex;
  public int glLineVertex;
  public int glPointAttrib;
  public int glPointColor;
  public int glPointIndex;
  public int glPointVertex;
  public int glPolyAmbient;
  public int glPolyColor;
  public int glPolyEmissive;
  public int glPolyIndex;
  public int glPolyNormal;
  public int glPolyShininess;
  public int glPolySpecular;
  public int glPolyTexcoord;
  public int glPolyVertex;
  protected boolean hasLines;
  protected boolean hasPoints;
  protected boolean hasPolys;
  protected int imageMode;
  protected PGraphicsOpenGL.InGeometry inGeo;
  protected boolean isClosed;
  protected boolean isSolid;
  protected int lastLineIndexCache;
  protected int lastLineVertex;
  protected int lastModifiedLineAttribute;
  protected int lastModifiedLineColor;
  protected int lastModifiedLineVertex;
  protected int lastModifiedPointAttribute;
  protected int lastModifiedPointColor;
  protected int lastModifiedPointVertex;
  protected int lastModifiedPolyAmbient;
  protected int lastModifiedPolyColor;
  protected int lastModifiedPolyEmissive;
  protected int lastModifiedPolyNormal;
  protected int lastModifiedPolyShininess;
  protected int lastModifiedPolySpecular;
  protected int lastModifiedPolyTexcoord;
  protected int lastModifiedPolyVertex;
  protected int lastPointIndexCache;
  protected int lastPointVertex;
  protected int lastPolyIndexCache;
  protected int lastPolyVertex;
  protected int lineIndCopyOffset;
  protected int lineIndexOffset;
  protected int lineVertCopyOffset;
  protected int lineVertexAbs;
  protected int lineVertexOffset;
  protected int lineVertexRel;
  protected boolean modified;
  protected boolean modifiedLineAttributes;
  protected boolean modifiedLineColors;
  protected boolean modifiedLineVertices;
  protected boolean modifiedPointAttributes;
  protected boolean modifiedPointColors;
  protected boolean modifiedPointVertices;
  protected boolean modifiedPolyAmbient;
  protected boolean modifiedPolyColors;
  protected boolean modifiedPolyEmissive;
  protected boolean modifiedPolyNormals;
  protected boolean modifiedPolyShininess;
  protected boolean modifiedPolySpecular;
  protected boolean modifiedPolyTexCoords;
  protected boolean modifiedPolyVertices;
  protected boolean needBufferInit;
  protected int normalMode;
  protected float normalX;
  protected float normalY;
  protected float normalZ;
  protected PGraphicsOpenGL pg;
  protected PGL pgl;
  protected int pointIndCopyOffset;
  protected int pointIndexOffset;
  protected int pointVertCopyOffset;
  protected int pointVertexAbs;
  protected int pointVertexOffset;
  protected int pointVertexRel;
  protected int polyIndCopyOffset;
  protected int polyIndexOffset;
  protected int polyVertCopyOffset;
  protected int polyVertexAbs;
  protected int polyVertexOffset;
  protected int polyVertexRel;
  protected int rectMode;
  protected PShapeOpenGL root;
  protected boolean shapeCreated = false;
  protected int shapeMode;
  protected boolean strokedTexture;
  protected PGraphicsOpenGL.TessGeometry tessGeo;
  protected boolean tessellated;
  protected PGraphicsOpenGL.Tessellator tessellator;
  protected HashSet<PImage> textures;
  protected PMatrix transform;
  
  PShapeOpenGL() {}
  
  public PShapeOpenGL(PApplet paramPApplet, int paramInt)
  {
    this.pg = ((PGraphicsOpenGL)paramPApplet.g);
    this.pgl = this.pg.pgl;
    this.context = this.pgl.createEmptyContext();
    this.glPolyVertex = 0;
    this.glPolyColor = 0;
    this.glPolyNormal = 0;
    this.glPolyTexcoord = 0;
    this.glPolyAmbient = 0;
    this.glPolySpecular = 0;
    this.glPolyEmissive = 0;
    this.glPolyShininess = 0;
    this.glPolyIndex = 0;
    this.glLineVertex = 0;
    this.glLineColor = 0;
    this.glLineAttrib = 0;
    this.glLineIndex = 0;
    this.glPointVertex = 0;
    this.glPointColor = 0;
    this.glPointAttrib = 0;
    this.glPointIndex = 0;
    this.tessellator = PGraphicsOpenGL.tessellator;
    this.family = paramInt;
    this.root = this;
    this.parent = null;
    this.tessellated = false;
    if ((paramInt == 3) || (paramInt == 1) || (paramInt == 2)) {
      this.inGeo = this.pg.newInGeometry(1);
    }
    this.textureMode = this.pg.textureMode;
    this.rectMode = this.pg.rectMode;
    this.ellipseMode = this.pg.ellipseMode;
    this.shapeMode = this.pg.shapeMode;
    this.imageMode = this.pg.imageMode;
    colorMode(this.pg.colorMode, this.pg.colorModeX, this.pg.colorModeY, this.pg.colorModeZ, this.pg.colorModeA);
    this.fill = this.pg.fill;
    this.fillColor = this.pg.fillColor;
    this.stroke = this.pg.stroke;
    this.strokeColor = this.pg.strokeColor;
    this.strokeWeight = this.pg.strokeWeight;
    this.strokeCap = this.pg.strokeCap;
    this.strokeJoin = this.pg.strokeJoin;
    this.tint = this.pg.tint;
    this.tintColor = this.pg.tintColor;
    this.setAmbient = this.pg.setAmbient;
    this.ambientColor = this.pg.ambientColor;
    this.specularColor = this.pg.specularColor;
    this.emissiveColor = this.pg.emissiveColor;
    this.shininess = this.pg.shininess;
    this.normalY = 0.0F;
    this.normalX = 0.0F;
    this.normalZ = 1.0F;
    this.normalMode = 0;
    this.breakShape = true;
    if (paramInt == 0) {
      this.shapeCreated = true;
    }
  }
  
  public static void copyGroup2D(PApplet paramPApplet, PShape paramPShape1, PShape paramPShape2)
  {
    copyMatrix(paramPShape1, paramPShape2);
    copyStyles(paramPShape1, paramPShape2);
    copyImage(paramPShape1, paramPShape2);
    for (int i = 0; i < paramPShape1.getChildCount(); i++) {
      paramPShape2.addChild(createShape2D(paramPApplet, paramPShape1.getChild(i)));
    }
  }
  
  public static void copyGroup3D(PApplet paramPApplet, PShape paramPShape1, PShape paramPShape2)
  {
    copyMatrix(paramPShape1, paramPShape2);
    copyStyles(paramPShape1, paramPShape2);
    copyImage(paramPShape1, paramPShape2);
    for (int i = 0; i < paramPShape1.getChildCount(); i++) {
      paramPShape2.addChild(createShape3D(paramPApplet, paramPShape1.getChild(i)));
    }
  }
  
  public static PShapeOpenGL createShape2D(PApplet paramPApplet, PShape paramPShape)
  {
    PShapeOpenGL localPShapeOpenGL;
    if (paramPShape.getFamily() == 0)
    {
      localPShapeOpenGL = PGraphics2D.createShapeImpl(paramPApplet, 0);
      copyGroup2D(paramPApplet, paramPShape, localPShapeOpenGL);
    }
    for (;;)
    {
      localPShapeOpenGL.setName(paramPShape.getName());
      localPShapeOpenGL.width = paramPShape.width;
      localPShapeOpenGL.height = paramPShape.height;
      return localPShapeOpenGL;
      if (paramPShape.getFamily() == 1)
      {
        localPShapeOpenGL = PGraphics2D.createShapeImpl(paramPApplet, paramPShape.getKind(), paramPShape.getParams());
        PShape.copyPrimitive(paramPShape, localPShapeOpenGL);
      }
      else if (paramPShape.getFamily() == 3)
      {
        localPShapeOpenGL = PGraphics2D.createShapeImpl(paramPApplet, 3);
        PShape.copyGeometry(paramPShape, localPShapeOpenGL);
      }
      else
      {
        int i = paramPShape.getFamily();
        localPShapeOpenGL = null;
        if (i == 2)
        {
          localPShapeOpenGL = PGraphics2D.createShapeImpl(paramPApplet, 2);
          PShape.copyPath(paramPShape, localPShapeOpenGL);
        }
      }
    }
  }
  
  public static PShapeOpenGL createShape3D(PApplet paramPApplet, PShape paramPShape)
  {
    PShapeOpenGL localPShapeOpenGL;
    if (paramPShape.getFamily() == 0)
    {
      localPShapeOpenGL = PGraphics3D.createShapeImpl(paramPApplet, 0);
      copyGroup3D(paramPApplet, paramPShape, localPShapeOpenGL);
    }
    for (;;)
    {
      localPShapeOpenGL.setName(paramPShape.getName());
      localPShapeOpenGL.width = paramPShape.width;
      localPShapeOpenGL.height = paramPShape.height;
      localPShapeOpenGL.depth = paramPShape.depth;
      return localPShapeOpenGL;
      if (paramPShape.getFamily() == 1)
      {
        localPShapeOpenGL = PGraphics3D.createShapeImpl(paramPApplet, paramPShape.getKind(), paramPShape.getParams());
        PShape.copyPrimitive(paramPShape, localPShapeOpenGL);
      }
      else if (paramPShape.getFamily() == 3)
      {
        localPShapeOpenGL = PGraphics3D.createShapeImpl(paramPApplet, 3);
        PShape.copyGeometry(paramPShape, localPShapeOpenGL);
      }
      else
      {
        int i = paramPShape.getFamily();
        localPShapeOpenGL = null;
        if (i == 2)
        {
          localPShapeOpenGL = PGraphics3D.createShapeImpl(paramPApplet, 2);
          PShape.copyPath(paramPShape, localPShapeOpenGL);
        }
      }
    }
  }
  
  public void addChild(PShape paramPShape)
  {
    if ((paramPShape instanceof PShapeOpenGL))
    {
      if (this.family == 0)
      {
        PShapeOpenGL localPShapeOpenGL = (PShapeOpenGL)paramPShape;
        super.addChild(localPShapeOpenGL);
        localPShapeOpenGL.updateRoot(this.root);
        markForTessellation();
        if (localPShapeOpenGL.family == 0)
        {
          if (localPShapeOpenGL.textures != null)
          {
            Iterator localIterator = localPShapeOpenGL.textures.iterator();
            while (localIterator.hasNext()) {
              addTexture((PImage)localIterator.next());
            }
          }
          if (localPShapeOpenGL.strokedTexture) {
            strokedTexture(true);
          }
        }
        do
        {
          do
          {
            return;
          } while (localPShapeOpenGL.image == null);
          addTexture(localPShapeOpenGL.image);
        } while (!localPShapeOpenGL.stroke);
        strokedTexture(true);
        return;
      }
      PGraphics.showWarning("Cannot add child shape to non-group shape.");
      return;
    }
    PGraphics.showWarning("Shape must be 3D to be added to the group.");
  }
  
  protected void addTexture(PImage paramPImage)
  {
    if (this.textures == null) {
      this.textures = new HashSet();
    }
    this.textures.add(paramPImage);
    if (this.parent != null) {
      ((PShapeOpenGL)this.parent).addTexture(paramPImage);
    }
  }
  
  protected void aggregate()
  {
    if ((this.root == this) && (this.parent == null))
    {
      this.polyIndexOffset = 0;
      this.polyVertexOffset = 0;
      this.polyVertexAbs = 0;
      this.polyVertexRel = 0;
      this.lineIndexOffset = 0;
      this.lineVertexOffset = 0;
      this.lineVertexAbs = 0;
      this.lineVertexRel = 0;
      this.pointIndexOffset = 0;
      this.pointVertexOffset = 0;
      this.pointVertexAbs = 0;
      this.pointVertexRel = 0;
      aggregateImpl();
    }
  }
  
  protected void aggregateImpl()
  {
    boolean bool1 = true;
    if (this.family == 0)
    {
      this.hasPolys = false;
      this.hasLines = false;
      this.hasPoints = false;
      for (int i = 0; i < this.childCount; i++)
      {
        PShapeOpenGL localPShapeOpenGL = (PShapeOpenGL)this.children[i];
        localPShapeOpenGL.aggregateImpl();
        this.hasPolys |= localPShapeOpenGL.hasPolys;
        this.hasLines |= localPShapeOpenGL.hasLines;
        this.hasPoints |= localPShapeOpenGL.hasPoints;
      }
    }
    boolean bool2;
    boolean bool3;
    if ((-1 < this.firstPolyIndexCache) && (-1 < this.lastPolyIndexCache))
    {
      bool2 = bool1;
      this.hasPolys = bool2;
      if ((-1 >= this.firstLineIndexCache) || (-1 >= this.lastLineIndexCache)) {
        break label306;
      }
      bool3 = bool1;
      label142:
      this.hasLines = bool3;
      if ((-1 >= this.firstPointIndexCache) || (-1 >= this.lastPointIndexCache)) {
        break label311;
      }
    }
    for (;;)
    {
      this.hasPoints = bool1;
      if (this.hasPolys) {
        updatePolyIndexCache();
      }
      if (is3D())
      {
        if (this.hasLines) {
          updateLineIndexCache();
        }
        if (this.hasPoints) {
          updatePointIndexCache();
        }
      }
      if (this.matrix != null)
      {
        if (this.hasPolys) {
          this.tessGeo.applyMatrixOnPolyGeometry(this.matrix, this.firstPolyVertex, this.lastPolyVertex);
        }
        if (is3D())
        {
          if (this.hasLines) {
            this.tessGeo.applyMatrixOnLineGeometry(this.matrix, this.firstLineVertex, this.lastLineVertex);
          }
          if (this.hasPoints) {
            this.tessGeo.applyMatrixOnPointGeometry(this.matrix, this.firstPointVertex, this.lastPointVertex);
          }
        }
      }
      return;
      bool2 = false;
      break;
      label306:
      bool3 = false;
      break label142;
      label311:
      bool1 = false;
    }
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    transform(3, new float[] { paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6 });
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    transform(3, new float[] { paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramFloat13, paramFloat14, paramFloat15, paramFloat16 });
  }
  
  public void applyMatrix(PMatrix2D paramPMatrix2D)
  {
    float[] arrayOfFloat = new float[6];
    arrayOfFloat[0] = paramPMatrix2D.m00;
    arrayOfFloat[1] = paramPMatrix2D.m01;
    arrayOfFloat[2] = paramPMatrix2D.m02;
    arrayOfFloat[3] = paramPMatrix2D.m10;
    arrayOfFloat[4] = paramPMatrix2D.m11;
    arrayOfFloat[5] = paramPMatrix2D.m12;
    transform(3, arrayOfFloat);
  }
  
  protected void applyMatrixImpl(PMatrix paramPMatrix)
  {
    if (this.hasPolys)
    {
      this.tessGeo.applyMatrixOnPolyGeometry(paramPMatrix, this.firstPolyVertex, this.lastPolyVertex);
      this.root.setModifiedPolyVertices(this.firstPolyVertex, this.lastPolyVertex);
      this.root.setModifiedPolyNormals(this.firstPolyVertex, this.lastPolyVertex);
    }
    if (is3D())
    {
      if (this.hasLines)
      {
        this.tessGeo.applyMatrixOnLineGeometry(paramPMatrix, this.firstLineVertex, this.lastLineVertex);
        this.root.setModifiedLineVertices(this.firstLineVertex, this.lastLineVertex);
        this.root.setModifiedLineAttributes(this.firstLineVertex, this.lastLineVertex);
      }
      if (this.hasPoints)
      {
        this.tessGeo.applyMatrixOnPointGeometry(paramPMatrix, this.firstPointVertex, this.lastPointVertex);
        this.root.setModifiedPointVertices(this.firstPointVertex, this.lastPointVertex);
      }
    }
  }
  
  public void beginContour()
  {
    super.beginContour();
    this.breakShape = true;
  }
  
  public void bezierDetail(int paramInt)
  {
    this.bezierDetail = paramInt;
    this.pg.bezierDetail(paramInt);
  }
  
  public void bezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    bezierVertexImpl(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4, 0.0F, paramFloat5, paramFloat6, 0.0F);
  }
  
  public void bezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    bezierVertexImpl(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9);
  }
  
  protected void bezierVertexImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
    this.inGeo.addBezierVertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, this.fill, this.stroke, this.bezierDetail, vertexCode(), this.kind);
  }
  
  protected void calcTransform(int paramInt1, int paramInt2, float... paramVarArgs)
  {
    if (this.transform == null) {
      if (paramInt2 == 2)
      {
        this.transform = new PMatrix2D();
        switch (paramInt1)
        {
        }
      }
    }
    for (;;)
    {
      this.matrix.apply(this.transform);
      return;
      this.transform = new PMatrix3D();
      break;
      this.transform.reset();
      break;
      if (paramInt2 == 3)
      {
        this.transform.translate(paramVarArgs[0], paramVarArgs[1], paramVarArgs[2]);
      }
      else
      {
        this.transform.translate(paramVarArgs[0], paramVarArgs[1]);
        continue;
        if (paramInt2 == 3)
        {
          this.transform.rotate(paramVarArgs[0], paramVarArgs[1], paramVarArgs[2], paramVarArgs[3]);
        }
        else
        {
          this.transform.rotate(paramVarArgs[0]);
          continue;
          if (paramInt2 == 3)
          {
            this.transform.scale(paramVarArgs[0], paramVarArgs[1], paramVarArgs[2]);
          }
          else
          {
            this.transform.scale(paramVarArgs[0], paramVarArgs[1]);
            continue;
            if (paramInt2 == 3) {
              this.transform.set(paramVarArgs[0], paramVarArgs[1], paramVarArgs[2], paramVarArgs[3], paramVarArgs[4], paramVarArgs[5], paramVarArgs[6], paramVarArgs[7], paramVarArgs[8], paramVarArgs[9], paramVarArgs[10], paramVarArgs[11], paramVarArgs[12], paramVarArgs[13], paramVarArgs[14], paramVarArgs[15]);
            } else {
              this.transform.set(paramVarArgs[0], paramVarArgs[1], paramVarArgs[2], paramVarArgs[3], paramVarArgs[4], paramVarArgs[5]);
            }
          }
        }
      }
    }
  }
  
  protected boolean contextIsOutdated()
  {
    if (!this.pgl.contextIsCurrent(this.context)) {}
    for (boolean bool = true;; bool = false)
    {
      if (bool)
      {
        this.pg.removeVertexBufferObject(this.glPolyVertex, this.context);
        this.pg.removeVertexBufferObject(this.glPolyColor, this.context);
        this.pg.removeVertexBufferObject(this.glPolyNormal, this.context);
        this.pg.removeVertexBufferObject(this.glPolyTexcoord, this.context);
        this.pg.removeVertexBufferObject(this.glPolyAmbient, this.context);
        this.pg.removeVertexBufferObject(this.glPolySpecular, this.context);
        this.pg.removeVertexBufferObject(this.glPolyEmissive, this.context);
        this.pg.removeVertexBufferObject(this.glPolyShininess, this.context);
        this.pg.removeVertexBufferObject(this.glPolyIndex, this.context);
        this.pg.removeVertexBufferObject(this.glLineVertex, this.context);
        this.pg.removeVertexBufferObject(this.glLineColor, this.context);
        this.pg.removeVertexBufferObject(this.glLineAttrib, this.context);
        this.pg.removeVertexBufferObject(this.glLineIndex, this.context);
        this.pg.removeVertexBufferObject(this.glPointVertex, this.context);
        this.pg.removeVertexBufferObject(this.glPointColor, this.context);
        this.pg.removeVertexBufferObject(this.glPointAttrib, this.context);
        this.pg.removeVertexBufferObject(this.glPointIndex, this.context);
        this.glPolyVertex = 0;
        this.glPolyColor = 0;
        this.glPolyNormal = 0;
        this.glPolyTexcoord = 0;
        this.glPolyAmbient = 0;
        this.glPolySpecular = 0;
        this.glPolyEmissive = 0;
        this.glPolyShininess = 0;
        this.glPolyIndex = 0;
        this.glLineVertex = 0;
        this.glLineColor = 0;
        this.glLineAttrib = 0;
        this.glLineIndex = 0;
        this.glPointVertex = 0;
        this.glPointColor = 0;
        this.glPointAttrib = 0;
        this.glPointIndex = 0;
      }
      return bool;
    }
  }
  
  protected void copyLineAttributes(int paramInt1, int paramInt2)
  {
    this.tessGeo.updateLineDirectionsBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glLineAttrib);
    this.tessGeo.lineDirectionsBuffer.position(paramInt1 * 4);
    this.pgl.bufferSubData(34962, 4 * (paramInt1 * 4), 4 * (paramInt2 * 4), this.tessGeo.lineDirectionsBuffer);
    this.tessGeo.lineDirectionsBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyLineColors(int paramInt1, int paramInt2)
  {
    this.tessGeo.updateLineColorsBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glLineColor);
    this.tessGeo.lineColorsBuffer.position(paramInt1);
    this.pgl.bufferSubData(34962, paramInt1 * 4, paramInt2 * 4, this.tessGeo.lineColorsBuffer);
    this.tessGeo.lineColorsBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyLineVertices(int paramInt1, int paramInt2)
  {
    this.tessGeo.updateLineVerticesBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glLineVertex);
    this.tessGeo.lineVerticesBuffer.position(paramInt1 * 4);
    this.pgl.bufferSubData(34962, 4 * (paramInt1 * 4), 4 * (paramInt2 * 4), this.tessGeo.lineVerticesBuffer);
    this.tessGeo.lineVerticesBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyPointAttributes(int paramInt1, int paramInt2)
  {
    this.tessGeo.updatePointOffsetsBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glPointAttrib);
    this.tessGeo.pointOffsetsBuffer.position(paramInt1 * 2);
    this.pgl.bufferSubData(34962, 4 * (paramInt1 * 2), 4 * (paramInt2 * 2), this.tessGeo.pointOffsetsBuffer);
    this.tessGeo.pointOffsetsBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyPointColors(int paramInt1, int paramInt2)
  {
    this.tessGeo.updatePointColorsBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glPointColor);
    this.tessGeo.pointColorsBuffer.position(paramInt1);
    this.pgl.bufferSubData(34962, paramInt1 * 4, paramInt2 * 4, this.tessGeo.pointColorsBuffer);
    this.tessGeo.pointColorsBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyPointVertices(int paramInt1, int paramInt2)
  {
    this.tessGeo.updatePointVerticesBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glPointVertex);
    this.tessGeo.pointVerticesBuffer.position(paramInt1 * 4);
    this.pgl.bufferSubData(34962, 4 * (paramInt1 * 4), 4 * (paramInt2 * 4), this.tessGeo.pointVerticesBuffer);
    this.tessGeo.pointVerticesBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyPolyAmbient(int paramInt1, int paramInt2)
  {
    this.tessGeo.updatePolyAmbientBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glPolyAmbient);
    this.tessGeo.polyAmbientBuffer.position(paramInt1);
    this.pgl.bufferSubData(34962, paramInt1 * 4, paramInt2 * 4, this.tessGeo.polyAmbientBuffer);
    this.tessGeo.polyAmbientBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyPolyColors(int paramInt1, int paramInt2)
  {
    this.tessGeo.updatePolyColorsBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glPolyColor);
    this.tessGeo.polyColorsBuffer.position(paramInt1);
    this.pgl.bufferSubData(34962, paramInt1 * 4, paramInt2 * 4, this.tessGeo.polyColorsBuffer);
    this.tessGeo.polyColorsBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyPolyEmissive(int paramInt1, int paramInt2)
  {
    this.tessGeo.updatePolyEmissiveBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glPolyEmissive);
    this.tessGeo.polyEmissiveBuffer.position(paramInt1);
    this.pgl.bufferSubData(34962, paramInt1 * 4, paramInt2 * 4, this.tessGeo.polyEmissiveBuffer);
    this.tessGeo.polyEmissiveBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyPolyNormals(int paramInt1, int paramInt2)
  {
    this.tessGeo.updatePolyNormalsBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glPolyNormal);
    this.tessGeo.polyNormalsBuffer.position(paramInt1 * 3);
    this.pgl.bufferSubData(34962, 4 * (paramInt1 * 3), 4 * (paramInt2 * 3), this.tessGeo.polyNormalsBuffer);
    this.tessGeo.polyNormalsBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyPolyShininess(int paramInt1, int paramInt2)
  {
    this.tessGeo.updatePolyShininessBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glPolyShininess);
    this.tessGeo.polyShininessBuffer.position(paramInt1);
    this.pgl.bufferSubData(34962, paramInt1 * 4, paramInt2 * 4, this.tessGeo.polyShininessBuffer);
    this.tessGeo.polyShininessBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyPolySpecular(int paramInt1, int paramInt2)
  {
    this.tessGeo.updatePolySpecularBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glPolySpecular);
    this.tessGeo.polySpecularBuffer.position(paramInt1);
    this.pgl.bufferSubData(34962, paramInt1 * 4, paramInt2 * 4, this.tessGeo.polySpecularBuffer);
    this.tessGeo.polySpecularBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyPolyTexCoords(int paramInt1, int paramInt2)
  {
    this.tessGeo.updatePolyTexCoordsBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glPolyTexcoord);
    this.tessGeo.polyTexCoordsBuffer.position(paramInt1 * 2);
    this.pgl.bufferSubData(34962, 4 * (paramInt1 * 2), 4 * (paramInt2 * 2), this.tessGeo.polyTexCoordsBuffer);
    this.tessGeo.polyTexCoordsBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  protected void copyPolyVertices(int paramInt1, int paramInt2)
  {
    this.tessGeo.updatePolyVerticesBuffer(paramInt1, paramInt2);
    this.pgl.bindBuffer(34962, this.glPolyVertex);
    this.tessGeo.polyVerticesBuffer.position(paramInt1 * 4);
    this.pgl.bufferSubData(34962, 4 * (paramInt1 * 4), 4 * (paramInt2 * 4), this.tessGeo.polyVerticesBuffer);
    this.tessGeo.polyVerticesBuffer.rewind();
    this.pgl.bindBuffer(34962, 0);
  }
  
  public void curveDetail(int paramInt)
  {
    this.curveDetail = paramInt;
    this.pg.curveDetail(paramInt);
  }
  
  public void curveTightness(float paramFloat)
  {
    this.curveTightness = paramFloat;
    this.pg.curveTightness(paramFloat);
  }
  
  public void curveVertex(float paramFloat1, float paramFloat2)
  {
    curveVertexImpl(paramFloat1, paramFloat2, 0.0F);
  }
  
  public void curveVertex(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    curveVertexImpl(paramFloat1, paramFloat2, paramFloat3);
  }
  
  protected void curveVertexImpl(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
    this.inGeo.addCurveVertex(paramFloat1, paramFloat2, paramFloat3, this.fill, this.stroke, this.curveDetail, vertexCode(), this.kind);
  }
  
  protected void deleteLineBuffers()
  {
    if (this.glLineVertex != 0)
    {
      this.pg.deleteVertexBufferObject(this.glLineVertex, this.context);
      this.glLineVertex = 0;
    }
    if (this.glLineColor != 0)
    {
      this.pg.deleteVertexBufferObject(this.glLineColor, this.context);
      this.glLineColor = 0;
    }
    if (this.glLineAttrib != 0)
    {
      this.pg.deleteVertexBufferObject(this.glLineAttrib, this.context);
      this.glLineAttrib = 0;
    }
    if (this.glLineIndex != 0)
    {
      this.pg.deleteVertexBufferObject(this.glLineIndex, this.context);
      this.glLineIndex = 0;
    }
  }
  
  protected void deletePointBuffers()
  {
    if (this.glPointVertex != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPointVertex, this.context);
      this.glPointVertex = 0;
    }
    if (this.glPointColor != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPointColor, this.context);
      this.glPointColor = 0;
    }
    if (this.glPointAttrib != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPointAttrib, this.context);
      this.glPointAttrib = 0;
    }
    if (this.glPointIndex != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPointIndex, this.context);
      this.glPointIndex = 0;
    }
  }
  
  protected void deletePolyBuffers()
  {
    if (this.glPolyVertex != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPolyVertex, this.context);
      this.glPolyVertex = 0;
    }
    if (this.glPolyColor != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPolyColor, this.context);
      this.glPolyColor = 0;
    }
    if (this.glPolyNormal != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPolyNormal, this.context);
      this.glPolyNormal = 0;
    }
    if (this.glPolyTexcoord != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPolyTexcoord, this.context);
      this.glPolyTexcoord = 0;
    }
    if (this.glPolyAmbient != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPolyAmbient, this.context);
      this.glPolyAmbient = 0;
    }
    if (this.glPolySpecular != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPolySpecular, this.context);
      this.glPolySpecular = 0;
    }
    if (this.glPolyEmissive != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPolyEmissive, this.context);
      this.glPolyEmissive = 0;
    }
    if (this.glPolyShininess != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPolyShininess, this.context);
      this.glPolyShininess = 0;
    }
    if (this.glPolyIndex != 0)
    {
      this.pg.deleteVertexBufferObject(this.glPolyIndex, this.context);
      this.glPolyIndex = 0;
    }
  }
  
  public void draw()
  {
    draw(this.pg);
  }
  
  public void draw(PGraphics paramPGraphics)
  {
    PGraphicsOpenGL localPGraphicsOpenGL;
    if ((paramPGraphics instanceof PGraphicsOpenGL))
    {
      localPGraphicsOpenGL = (PGraphicsOpenGL)paramPGraphics;
      if (this.visible)
      {
        pre(localPGraphicsOpenGL);
        updateTessellation();
        updateGeometry();
        if (this.family != 0) {
          break label122;
        }
        if (fragmentedGroup(localPGraphicsOpenGL)) {
          for (int i = 0; i < this.childCount; i++) {
            ((PShapeOpenGL)this.children[i]).draw(localPGraphicsOpenGL);
          }
        }
        if ((this.textures == null) || (this.textures.size() != 1)) {
          break label140;
        }
      }
    }
    label140:
    for (PImage localPImage = (PImage)this.textures.toArray()[0];; localPImage = null)
    {
      render(localPGraphicsOpenGL, localPImage);
      for (;;)
      {
        post(localPGraphicsOpenGL);
        return;
        label122:
        render(localPGraphicsOpenGL, this.image);
      }
      super.draw(paramPGraphics);
      return;
    }
  }
  
  protected void drawGeometry(PGraphics paramPGraphics)
  {
    this.vertexCount = this.inGeo.vertexCount;
    this.vertices = this.inGeo.getVertexData();
    super.drawGeometry(paramPGraphics);
    this.vertexCount = 0;
    this.vertices = ((float[][])null);
  }
  
  public void endShape(int paramInt)
  {
    super.endShape(paramInt);
    this.inGeo.trim();
    if (paramInt == 2) {}
    for (boolean bool = true;; bool = false)
    {
      this.isClosed = bool;
      markForTessellation();
      this.shapeCreated = true;
      return;
    }
  }
  
  protected void finalize()
    throws Throwable
  {
    try
    {
      finalizePolyBuffers();
      finalizeLineBuffers();
      finalizePointBuffers();
      return;
    }
    finally
    {
      super.finalize();
    }
  }
  
  protected void finalizeLineBuffers()
  {
    if (this.glLineVertex != 0) {
      this.pg.finalizeVertexBufferObject(this.glLineVertex, this.context);
    }
    if (this.glLineColor != 0) {
      this.pg.finalizeVertexBufferObject(this.glLineColor, this.context);
    }
    if (this.glLineAttrib != 0) {
      this.pg.finalizeVertexBufferObject(this.glLineAttrib, this.context);
    }
    if (this.glLineIndex != 0) {
      this.pg.finalizeVertexBufferObject(this.glLineIndex, this.context);
    }
  }
  
  protected void finalizePointBuffers()
  {
    if (this.glPointVertex != 0) {
      this.pg.finalizeVertexBufferObject(this.glPointVertex, this.context);
    }
    if (this.glPointColor != 0) {
      this.pg.finalizeVertexBufferObject(this.glPointColor, this.context);
    }
    if (this.glPointAttrib != 0) {
      this.pg.finalizeVertexBufferObject(this.glPointAttrib, this.context);
    }
    if (this.glPointIndex != 0) {
      this.pg.finalizeVertexBufferObject(this.glPointIndex, this.context);
    }
  }
  
  protected void finalizePolyBuffers()
  {
    if (this.glPolyVertex != 0) {
      this.pg.finalizeVertexBufferObject(this.glPolyVertex, this.context);
    }
    if (this.glPolyColor != 0) {
      this.pg.finalizeVertexBufferObject(this.glPolyColor, this.context);
    }
    if (this.glPolyNormal != 0) {
      this.pg.finalizeVertexBufferObject(this.glPolyNormal, this.context);
    }
    if (this.glPolyTexcoord != 0) {
      this.pg.finalizeVertexBufferObject(this.glPolyTexcoord, this.context);
    }
    if (this.glPolyAmbient != 0) {
      this.pg.finalizeVertexBufferObject(this.glPolyAmbient, this.context);
    }
    if (this.glPolySpecular != 0) {
      this.pg.finalizeVertexBufferObject(this.glPolySpecular, this.context);
    }
    if (this.glPolyEmissive != 0) {
      this.pg.finalizeVertexBufferObject(this.glPolyEmissive, this.context);
    }
    if (this.glPolyShininess != 0) {
      this.pg.finalizeVertexBufferObject(this.glPolyShininess, this.context);
    }
    if (this.glPolyIndex != 0) {
      this.pg.finalizeVertexBufferObject(this.glPolyIndex, this.context);
    }
  }
  
  protected boolean fragmentedGroup(PGraphicsOpenGL paramPGraphicsOpenGL)
  {
    return (paramPGraphicsOpenGL.getHint(6)) || ((this.textures != null) && (1 < this.textures.size())) || (this.strokedTexture);
  }
  
  public int getAmbient(int paramInt)
  {
    if (this.family != 0) {
      return PGL.nativeToJavaARGB(this.inGeo.ambient[paramInt]);
    }
    return 0;
  }
  
  public float getDepth()
  {
    PVector localPVector1 = new PVector((1.0F / 1.0F), (1.0F / 1.0F), (1.0F / 1.0F));
    PVector localPVector2 = new PVector((1.0F / -1.0F), (1.0F / -1.0F), (1.0F / -1.0F));
    if (this.shapeCreated)
    {
      getVertexMin(localPVector1);
      getVertexMax(localPVector2);
    }
    this.depth = (localPVector2.z - localPVector1.z);
    return this.depth;
  }
  
  public int getEmissive(int paramInt)
  {
    if (this.family == 0) {
      return PGL.nativeToJavaARGB(this.inGeo.emissive[paramInt]);
    }
    return 0;
  }
  
  public int getFill(int paramInt)
  {
    if ((this.family != 0) && (this.image == null)) {
      return PGL.nativeToJavaARGB(this.inGeo.colors[paramInt]);
    }
    return 0;
  }
  
  public float getHeight()
  {
    PVector localPVector1 = new PVector((1.0F / 1.0F), (1.0F / 1.0F), (1.0F / 1.0F));
    PVector localPVector2 = new PVector((1.0F / -1.0F), (1.0F / -1.0F), (1.0F / -1.0F));
    if (this.shapeCreated)
    {
      getVertexMin(localPVector1);
      getVertexMax(localPVector2);
    }
    this.height = (localPVector2.y - localPVector1.y);
    return this.height;
  }
  
  public PVector getNormal(int paramInt, PVector paramPVector)
  {
    if (paramPVector == null) {
      paramPVector = new PVector();
    }
    paramPVector.x = this.inGeo.normals[(0 + paramInt * 3)];
    paramPVector.y = this.inGeo.normals[(1 + paramInt * 3)];
    paramPVector.z = this.inGeo.normals[(2 + paramInt * 3)];
    return paramPVector;
  }
  
  public float getNormalX(int paramInt)
  {
    return this.inGeo.normals[(0 + paramInt * 3)];
  }
  
  public float getNormalY(int paramInt)
  {
    return this.inGeo.normals[(1 + paramInt * 3)];
  }
  
  public float getNormalZ(int paramInt)
  {
    return this.inGeo.normals[(2 + paramInt * 3)];
  }
  
  public float getShininess(int paramInt)
  {
    if (this.family == 0) {
      return this.inGeo.shininess[paramInt];
    }
    return 0.0F;
  }
  
  public int getSpecular(int paramInt)
  {
    if (this.family == 0) {
      return PGL.nativeToJavaARGB(this.inGeo.specular[paramInt]);
    }
    return 0;
  }
  
  public int getStroke(int paramInt)
  {
    if (this.family != 0) {
      return PGL.nativeToJavaARGB(this.inGeo.strokeColors[paramInt]);
    }
    return 0;
  }
  
  public float getStrokeWeight(int paramInt)
  {
    if (this.family != 0) {
      return this.inGeo.strokeWeights[paramInt];
    }
    return 0.0F;
  }
  
  public PShape getTessellation()
  {
    updateTessellation();
    float[] arrayOfFloat1 = this.tessGeo.polyVertices;
    float[] arrayOfFloat2 = this.tessGeo.polyNormals;
    int[] arrayOfInt = this.tessGeo.polyColors;
    float[] arrayOfFloat3 = this.tessGeo.polyTexCoords;
    short[] arrayOfShort = this.tessGeo.polyIndices;
    PShapeOpenGL localPShapeOpenGL;
    PGraphicsOpenGL.IndexCache localIndexCache;
    if (is3D())
    {
      localPShapeOpenGL = PGraphics3D.createShapeImpl(this.pg.parent, 3);
      localPShapeOpenGL.beginShape(9);
      localPShapeOpenGL.noStroke();
      localIndexCache = this.tessGeo.polyIndexCache;
    }
    for (int i = this.firstPolyIndexCache;; i++)
    {
      if (i > this.lastPolyIndexCache) {
        break label789;
      }
      int j = localIndexCache.indexOffset[i];
      int k = localIndexCache.indexCount[i];
      int m = localIndexCache.vertexOffset[i];
      int n = j / 3;
      label138:
      if (n < (j + k) / 3)
      {
        int i1 = m + arrayOfShort[(0 + n * 3)];
        int i2 = m + arrayOfShort[(1 + n * 3)];
        int i3 = m + arrayOfShort[(2 + n * 3)];
        if (is3D())
        {
          float f7 = arrayOfFloat1[(0 + i1 * 4)];
          float f8 = arrayOfFloat1[(1 + i1 * 4)];
          float f9 = arrayOfFloat1[(2 + i1 * 4)];
          float f10 = arrayOfFloat1[(0 + i2 * 4)];
          float f11 = arrayOfFloat1[(1 + i2 * 4)];
          float f12 = arrayOfFloat1[(2 + i2 * 4)];
          float f13 = arrayOfFloat1[(0 + i3 * 4)];
          float f14 = arrayOfFloat1[(1 + i3 * 4)];
          float f15 = arrayOfFloat1[(2 + i3 * 4)];
          float f16 = arrayOfFloat2[(0 + i1 * 3)];
          float f17 = arrayOfFloat2[(1 + i1 * 3)];
          float f18 = arrayOfFloat2[(2 + i1 * 3)];
          float f19 = arrayOfFloat2[(0 + i2 * 3)];
          float f20 = arrayOfFloat2[(1 + i2 * 3)];
          float f21 = arrayOfFloat2[(2 + i2 * 3)];
          float f22 = arrayOfFloat2[(0 + i3 * 3)];
          float f23 = arrayOfFloat2[(1 + i3 * 3)];
          float f24 = arrayOfFloat2[(2 + i3 * 3)];
          int i7 = PGL.nativeToJavaARGB(arrayOfInt[i1]);
          int i8 = PGL.nativeToJavaARGB(arrayOfInt[i2]);
          int i9 = PGL.nativeToJavaARGB(arrayOfInt[i3]);
          localPShapeOpenGL.fill(i7);
          localPShapeOpenGL.normal(f16, f17, f18);
          localPShapeOpenGL.vertex(f7, f8, f9, arrayOfFloat3[(0 + i1 * 2)], arrayOfFloat3[(1 + i1 * 2)]);
          localPShapeOpenGL.fill(i8);
          localPShapeOpenGL.normal(f19, f20, f21);
          localPShapeOpenGL.vertex(f10, f11, f12, arrayOfFloat3[(0 + i2 * 2)], arrayOfFloat3[(1 + i2 * 2)]);
          localPShapeOpenGL.fill(i9);
          localPShapeOpenGL.normal(f22, f23, f24);
          localPShapeOpenGL.vertex(f13, f14, f15, arrayOfFloat3[(0 + i3 * 2)], arrayOfFloat3[(1 + i3 * 2)]);
        }
        for (;;)
        {
          n++;
          break label138;
          if (is2D())
          {
            localPShapeOpenGL = PGraphics2D.createShapeImpl(this.pg.parent, 3);
            break;
          }
          PGraphics.showWarning("This shape is not either 2D or 3D!");
          return null;
          if (is2D())
          {
            float f1 = arrayOfFloat1[(0 + i1 * 4)];
            float f2 = arrayOfFloat1[(1 + i1 * 4)];
            float f3 = arrayOfFloat1[(0 + i2 * 4)];
            float f4 = arrayOfFloat1[(1 + i2 * 4)];
            float f5 = arrayOfFloat1[(0 + i3 * 4)];
            float f6 = arrayOfFloat1[(1 + i3 * 4)];
            int i4 = PGL.nativeToJavaARGB(arrayOfInt[i1]);
            int i5 = PGL.nativeToJavaARGB(arrayOfInt[i2]);
            int i6 = PGL.nativeToJavaARGB(arrayOfInt[i3]);
            localPShapeOpenGL.fill(i4);
            localPShapeOpenGL.vertex(f1, f2, arrayOfFloat3[(0 + i1 * 2)], arrayOfFloat3[(1 + i1 * 2)]);
            localPShapeOpenGL.fill(i5);
            localPShapeOpenGL.vertex(f3, f4, arrayOfFloat3[(0 + i2 * 2)], arrayOfFloat3[(1 + i2 * 2)]);
            localPShapeOpenGL.fill(i6);
            localPShapeOpenGL.vertex(f5, f6, arrayOfFloat3[(0 + i3 * 2)], arrayOfFloat3[(1 + i3 * 2)]);
          }
        }
      }
    }
    label789:
    localPShapeOpenGL.endShape();
    return localPShapeOpenGL;
  }
  
  public float getTextureU(int paramInt)
  {
    return this.inGeo.texcoords[(0 + paramInt * 2)];
  }
  
  public float getTextureV(int paramInt)
  {
    return this.inGeo.texcoords[(1 + paramInt * 2)];
  }
  
  public int getTint(int paramInt)
  {
    if ((this.family != 0) && (this.image != null)) {
      return PGL.nativeToJavaARGB(this.inGeo.colors[paramInt]);
    }
    return 0;
  }
  
  public PVector getVertex(int paramInt, PVector paramPVector)
  {
    if (paramPVector == null) {
      paramPVector = new PVector();
    }
    paramPVector.x = this.inGeo.vertices[(0 + paramInt * 3)];
    paramPVector.y = this.inGeo.vertices[(1 + paramInt * 3)];
    paramPVector.z = this.inGeo.vertices[(2 + paramInt * 3)];
    return paramPVector;
  }
  
  public int getVertexCount()
  {
    updateTessellation();
    if (this.family == 0) {
      return 0;
    }
    return this.inGeo.vertexCount;
  }
  
  protected void getVertexMax(PVector paramPVector)
  {
    updateTessellation();
    if (this.family == 0) {
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).getVertexMax(paramPVector);
      }
    }
    if (this.hasPolys) {
      this.tessGeo.getPolyVertexMax(paramPVector, this.firstPolyVertex, this.lastPolyVertex);
    }
    if (is3D())
    {
      if (this.hasLines) {
        this.tessGeo.getLineVertexMax(paramPVector, this.firstLineVertex, this.lastLineVertex);
      }
      if (this.hasPoints) {
        this.tessGeo.getPointVertexMax(paramPVector, this.firstPointVertex, this.lastPointVertex);
      }
    }
  }
  
  protected void getVertexMin(PVector paramPVector)
  {
    updateTessellation();
    if (this.family == 0) {
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).getVertexMin(paramPVector);
      }
    }
    if (this.hasPolys) {
      this.tessGeo.getPolyVertexMin(paramPVector, this.firstPolyVertex, this.lastPolyVertex);
    }
    if (is3D())
    {
      if (this.hasLines) {
        this.tessGeo.getLineVertexMin(paramPVector, this.firstLineVertex, this.lastLineVertex);
      }
      if (this.hasPoints) {
        this.tessGeo.getPointVertexMin(paramPVector, this.firstPointVertex, this.lastPointVertex);
      }
    }
  }
  
  protected int getVertexSum(PVector paramPVector, int paramInt)
  {
    updateTessellation();
    if (this.family == 0) {
      for (int i = 0; i < this.childCount; i++) {
        paramInt += ((PShapeOpenGL)this.children[i]).getVertexSum(paramPVector, paramInt);
      }
    }
    if (this.hasPolys) {
      paramInt += this.tessGeo.getPolyVertexSum(paramPVector, this.firstPolyVertex, this.lastPolyVertex);
    }
    if (is3D())
    {
      if (this.hasLines) {
        paramInt += this.tessGeo.getLineVertexSum(paramPVector, this.firstLineVertex, this.lastLineVertex);
      }
      if (this.hasPoints) {
        paramInt += this.tessGeo.getPointVertexSum(paramPVector, this.firstPointVertex, this.lastPointVertex);
      }
    }
    return paramInt;
  }
  
  public float getVertexX(int paramInt)
  {
    return this.inGeo.vertices[(0 + paramInt * 3)];
  }
  
  public float getVertexY(int paramInt)
  {
    return this.inGeo.vertices[(1 + paramInt * 3)];
  }
  
  public float getVertexZ(int paramInt)
  {
    return this.inGeo.vertices[(2 + paramInt * 3)];
  }
  
  public float getWidth()
  {
    PVector localPVector1 = new PVector((1.0F / 1.0F), (1.0F / 1.0F), (1.0F / 1.0F));
    PVector localPVector2 = new PVector((1.0F / -1.0F), (1.0F / -1.0F), (1.0F / -1.0F));
    if (this.shapeCreated)
    {
      getVertexMin(localPVector1);
      getVertexMax(localPVector2);
    }
    this.width = (localPVector2.x - localPVector1.x);
    return this.width;
  }
  
  protected boolean hasStrokedTexture()
  {
    if (this.family == 0) {
      return this.strokedTexture;
    }
    return (this.image != null) && (this.stroke);
  }
  
  protected boolean hasTexture(PImage paramPImage)
  {
    if (this.family == 0) {
      if ((this.textures == null) || (!this.textures.contains(paramPImage))) {}
    }
    while (this.image == paramPImage)
    {
      return true;
      return false;
    }
    return false;
  }
  
  protected void initBuffers()
  {
    if (this.needBufferInit)
    {
      this.context = this.pgl.getCurrentContext();
      if ((this.tessGeo.polyVertexCount > 0) && (this.tessGeo.polyIndexCount > 0)) {
        initPolyBuffers();
      }
      if ((this.tessGeo.lineVertexCount > 0) && (this.tessGeo.lineIndexCount > 0)) {
        initLineBuffers();
      }
      if ((this.tessGeo.pointVertexCount > 0) && (this.tessGeo.pointIndexCount > 0)) {
        initPointBuffers();
      }
      this.needBufferInit = false;
    }
  }
  
  protected void initLineBuffers()
  {
    int i = this.tessGeo.lineVertexCount;
    int j = i * 4;
    int k = i * 4;
    this.tessGeo.updateLineVerticesBuffer();
    this.glLineVertex = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glLineVertex);
    this.pgl.bufferData(34962, j * 4, this.tessGeo.lineVerticesBuffer, 35044);
    this.tessGeo.updateLineColorsBuffer();
    this.glLineColor = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glLineColor);
    this.pgl.bufferData(34962, k, this.tessGeo.lineColorsBuffer, 35044);
    this.tessGeo.updateLineDirectionsBuffer();
    this.glLineAttrib = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glLineAttrib);
    this.pgl.bufferData(34962, j * 4, this.tessGeo.lineDirectionsBuffer, 35044);
    this.pgl.bindBuffer(34962, 0);
    this.tessGeo.updateLineIndicesBuffer();
    this.glLineIndex = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34963, this.glLineIndex);
    this.pgl.bufferData(34963, 2 * this.tessGeo.lineIndexCount, this.tessGeo.lineIndicesBuffer, 35044);
    this.pgl.bindBuffer(34963, 0);
  }
  
  protected void initPointBuffers()
  {
    int i = this.tessGeo.pointVertexCount;
    int j = i * 4;
    int k = i * 4;
    this.tessGeo.updatePointVerticesBuffer();
    this.glPointVertex = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glPointVertex);
    this.pgl.bufferData(34962, j * 4, this.tessGeo.pointVerticesBuffer, 35044);
    this.tessGeo.updatePointColorsBuffer();
    this.glPointColor = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glPointColor);
    this.pgl.bufferData(34962, k, this.tessGeo.pointColorsBuffer, 35044);
    this.tessGeo.updatePointOffsetsBuffer();
    this.glPointAttrib = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glPointAttrib);
    this.pgl.bufferData(34962, j * 2, this.tessGeo.pointOffsetsBuffer, 35044);
    this.pgl.bindBuffer(34962, 0);
    this.tessGeo.updatePointIndicesBuffer();
    this.glPointIndex = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34963, this.glPointIndex);
    this.pgl.bufferData(34963, 2 * this.tessGeo.pointIndexCount, this.tessGeo.pointIndicesBuffer, 35044);
    this.pgl.bindBuffer(34963, 0);
  }
  
  protected void initPolyBuffers()
  {
    int i = this.tessGeo.polyVertexCount;
    int j = i * 4;
    int k = i * 4;
    this.tessGeo.updatePolyVerticesBuffer();
    this.glPolyVertex = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glPolyVertex);
    this.pgl.bufferData(34962, j * 4, this.tessGeo.polyVerticesBuffer, 35044);
    this.tessGeo.updatePolyColorsBuffer();
    this.glPolyColor = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glPolyColor);
    this.pgl.bufferData(34962, k, this.tessGeo.polyColorsBuffer, 35044);
    this.tessGeo.updatePolyNormalsBuffer();
    this.glPolyNormal = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glPolyNormal);
    this.pgl.bufferData(34962, j * 3, this.tessGeo.polyNormalsBuffer, 35044);
    this.tessGeo.updatePolyTexCoordsBuffer();
    this.glPolyTexcoord = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glPolyTexcoord);
    this.pgl.bufferData(34962, j * 2, this.tessGeo.polyTexCoordsBuffer, 35044);
    this.tessGeo.updatePolyAmbientBuffer();
    this.glPolyAmbient = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glPolyAmbient);
    this.pgl.bufferData(34962, k, this.tessGeo.polyAmbientBuffer, 35044);
    this.tessGeo.updatePolySpecularBuffer();
    this.glPolySpecular = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glPolySpecular);
    this.pgl.bufferData(34962, k, this.tessGeo.polySpecularBuffer, 35044);
    this.tessGeo.updatePolyEmissiveBuffer();
    this.glPolyEmissive = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glPolyEmissive);
    this.pgl.bufferData(34962, k, this.tessGeo.polyEmissiveBuffer, 35044);
    this.tessGeo.updatePolyShininessBuffer();
    this.glPolyShininess = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34962, this.glPolyShininess);
    this.pgl.bufferData(34962, j, this.tessGeo.polyShininessBuffer, 35044);
    this.pgl.bindBuffer(34962, 0);
    this.tessGeo.updatePolyIndicesBuffer();
    this.glPolyIndex = this.pg.createVertexBufferObject(this.context);
    this.pgl.bindBuffer(34963, this.glPolyIndex);
    this.pgl.bufferData(34963, 2 * this.tessGeo.polyIndexCount, this.tessGeo.polyIndicesBuffer, 35044);
    this.pgl.bindBuffer(34963, 0);
  }
  
  protected void markForTessellation()
  {
    this.root.tessellated = false;
    this.tessellated = false;
  }
  
  public void normal(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!this.openShape) {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "normal()" });
    }
    do
    {
      return;
      if (this.family == 0)
      {
        PGraphics.showWarning("Cannot set normal in GROUP shape");
        return;
      }
      this.normalX = paramFloat1;
      this.normalY = paramFloat2;
      this.normalZ = paramFloat3;
      if (this.normalMode == 0)
      {
        this.normalMode = 1;
        return;
      }
    } while (this.normalMode != 1);
    this.normalMode = 2;
  }
  
  protected void post(PGraphics paramPGraphics)
  {
    if ((paramPGraphics instanceof PGraphicsOpenGL)) {
      return;
    }
    super.post(paramPGraphics);
  }
  
  protected void pre(PGraphics paramPGraphics)
  {
    if ((paramPGraphics instanceof PGraphicsOpenGL))
    {
      if (!this.style) {
        styles(paramPGraphics);
      }
      return;
    }
    super.pre(paramPGraphics);
  }
  
  public void quadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    quadraticVertexImpl(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4, 0.0F);
  }
  
  public void quadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    quadraticVertexImpl(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  protected void quadraticVertexImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
    this.inGeo.addQuadraticVertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, this.fill, this.stroke, this.bezierDetail, vertexCode(), this.kind);
  }
  
  protected void rawLines(PGraphicsOpenGL paramPGraphicsOpenGL)
  {
    PGraphics localPGraphics = paramPGraphicsOpenGL.getRaw();
    localPGraphics.colorMode(1);
    localPGraphics.noFill();
    localPGraphics.strokeCap(this.strokeCap);
    localPGraphics.strokeJoin(this.strokeJoin);
    localPGraphics.beginShape(5);
    float[] arrayOfFloat1 = this.tessGeo.lineVertices;
    int[] arrayOfInt = this.tessGeo.lineColors;
    float[] arrayOfFloat2 = this.tessGeo.lineDirections;
    short[] arrayOfShort = this.tessGeo.lineIndices;
    PGraphicsOpenGL.IndexCache localIndexCache = this.tessGeo.lineIndexCache;
    for (int i = this.firstLineIndexCache; i <= this.lastLineIndexCache; i++)
    {
      int j = localIndexCache.indexOffset[i];
      int k = localIndexCache.indexCount[i];
      int m = localIndexCache.vertexOffset[i];
      int n = j / 6;
      if (n < (j + k) / 6)
      {
        int i1 = m + arrayOfShort[(0 + n * 6)];
        int i2 = m + arrayOfShort[(5 + n * 6)];
        float f1 = 2.0F * arrayOfFloat2[(3 + i1 * 4)];
        float f2 = 2.0F * arrayOfFloat2[(3 + i2 * 4)];
        if (PGraphicsOpenGL.zero(f1)) {}
        for (;;)
        {
          n++;
          break;
          float[] arrayOfFloat3 = { 0.0F, 0.0F, 0.0F, 0.0F };
          float[] arrayOfFloat4 = { 0.0F, 0.0F, 0.0F, 0.0F };
          float[] arrayOfFloat5 = { 0.0F, 0.0F, 0.0F, 0.0F };
          float[] arrayOfFloat6 = { 0.0F, 0.0F, 0.0F, 0.0F };
          int i3 = PGL.nativeToJavaARGB(arrayOfInt[i1]);
          int i4 = PGL.nativeToJavaARGB(arrayOfInt[i2]);
          PApplet.arrayCopy(arrayOfFloat1, i1 * 4, arrayOfFloat3, 0, 4);
          PApplet.arrayCopy(arrayOfFloat1, i2 * 4, arrayOfFloat4, 0, 4);
          paramPGraphicsOpenGL.modelview.mult(arrayOfFloat3, arrayOfFloat5);
          paramPGraphicsOpenGL.modelview.mult(arrayOfFloat4, arrayOfFloat6);
          if (localPGraphics.is3D())
          {
            localPGraphics.strokeWeight(f1);
            localPGraphics.stroke(i3);
            localPGraphics.vertex(arrayOfFloat5[0], arrayOfFloat5[1], arrayOfFloat5[2]);
            localPGraphics.strokeWeight(f2);
            localPGraphics.stroke(i4);
            localPGraphics.vertex(arrayOfFloat6[0], arrayOfFloat6[1], arrayOfFloat6[2]);
          }
          else if (localPGraphics.is2D())
          {
            float f3 = paramPGraphicsOpenGL.screenXImpl(arrayOfFloat5[0], arrayOfFloat5[1], arrayOfFloat5[2], arrayOfFloat5[3]);
            float f4 = paramPGraphicsOpenGL.screenYImpl(arrayOfFloat5[0], arrayOfFloat5[1], arrayOfFloat5[2], arrayOfFloat5[3]);
            float f5 = paramPGraphicsOpenGL.screenXImpl(arrayOfFloat6[0], arrayOfFloat6[1], arrayOfFloat6[2], arrayOfFloat6[3]);
            float f6 = paramPGraphicsOpenGL.screenYImpl(arrayOfFloat6[0], arrayOfFloat6[1], arrayOfFloat6[2], arrayOfFloat6[3]);
            localPGraphics.strokeWeight(f1);
            localPGraphics.stroke(i3);
            localPGraphics.vertex(f3, f4);
            localPGraphics.strokeWeight(f2);
            localPGraphics.stroke(i4);
            localPGraphics.vertex(f5, f6);
          }
        }
      }
    }
    localPGraphics.endShape();
  }
  
  protected void rawPoints(PGraphicsOpenGL paramPGraphicsOpenGL)
  {
    PGraphics localPGraphics = paramPGraphicsOpenGL.getRaw();
    localPGraphics.colorMode(1);
    localPGraphics.noFill();
    localPGraphics.strokeCap(this.strokeCap);
    localPGraphics.beginShape(3);
    float[] arrayOfFloat1 = this.tessGeo.pointVertices;
    int[] arrayOfInt = this.tessGeo.pointColors;
    float[] arrayOfFloat2 = this.tessGeo.pointOffsets;
    short[] arrayOfShort = this.tessGeo.pointIndices;
    PGraphicsOpenGL.IndexCache localIndexCache = this.tessGeo.pointIndexCache;
    for (int i = 0; i < localIndexCache.size; i++)
    {
      int j = localIndexCache.indexOffset[i];
      int k = localIndexCache.indexCount[i];
      int m = localIndexCache.vertexOffset[i];
      int n = j;
      if (n < (j + k) / 3)
      {
        float f1 = arrayOfFloat2[(2 + n * 2)];
        float f2;
        int i1;
        label176:
        int i3;
        float[] arrayOfFloat3;
        if (0.0F < f1)
        {
          f2 = f1 / 0.5F;
          i1 = 1 + PApplet.max(20, (int)(6.283186F * f2 / 10.0F));
          int i2 = m + arrayOfShort[(n * 3)];
          i3 = PGL.nativeToJavaARGB(arrayOfInt[i2]);
          arrayOfFloat3 = new float[] { 0.0F, 0.0F, 0.0F, 0.0F };
          float[] arrayOfFloat4 = { 0.0F, 0.0F, 0.0F, 0.0F };
          PApplet.arrayCopy(arrayOfFloat1, i2 * 4, arrayOfFloat4, 0, 4);
          paramPGraphicsOpenGL.modelview.mult(arrayOfFloat4, arrayOfFloat3);
          if (!localPGraphics.is3D()) {
            break label324;
          }
          localPGraphics.strokeWeight(f2);
          localPGraphics.stroke(i3);
          localPGraphics.vertex(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2]);
        }
        for (;;)
        {
          n += i1;
          break;
          f2 = -f1 / 0.5F;
          i1 = 5;
          break label176;
          label324:
          if (localPGraphics.is2D())
          {
            float f3 = paramPGraphicsOpenGL.screenXImpl(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat3[3]);
            float f4 = paramPGraphicsOpenGL.screenYImpl(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat3[3]);
            localPGraphics.strokeWeight(f2);
            localPGraphics.stroke(i3);
            localPGraphics.vertex(f3, f4);
          }
        }
      }
    }
    localPGraphics.endShape();
  }
  
  protected void rawPolys(PGraphicsOpenGL paramPGraphicsOpenGL, PImage paramPImage)
  {
    PGraphics localPGraphics = paramPGraphicsOpenGL.getRaw();
    localPGraphics.colorMode(1);
    localPGraphics.noStroke();
    localPGraphics.beginShape(9);
    float[] arrayOfFloat1 = this.tessGeo.polyVertices;
    int[] arrayOfInt = this.tessGeo.polyColors;
    float[] arrayOfFloat2 = this.tessGeo.polyTexCoords;
    short[] arrayOfShort = this.tessGeo.polyIndices;
    PGraphicsOpenGL.IndexCache localIndexCache = this.tessGeo.polyIndexCache;
    for (int i = this.firstPolyIndexCache; i <= this.lastPolyIndexCache; i++)
    {
      int j = localIndexCache.indexOffset[i];
      int k = localIndexCache.indexCount[i];
      int m = localIndexCache.vertexOffset[i];
      int n = j / 3;
      if (n < (j + k) / 3)
      {
        int i1 = m + arrayOfShort[(0 + n * 3)];
        int i2 = m + arrayOfShort[(1 + n * 3)];
        int i3 = m + arrayOfShort[(2 + n * 3)];
        float[] arrayOfFloat3 = { 0.0F, 0.0F, 0.0F, 0.0F };
        float[] arrayOfFloat4 = { 0.0F, 0.0F, 0.0F, 0.0F };
        float[] arrayOfFloat5 = { 0.0F, 0.0F, 0.0F, 0.0F };
        float[] arrayOfFloat6 = { 0.0F, 0.0F, 0.0F, 0.0F };
        float[] arrayOfFloat7 = { 0.0F, 0.0F, 0.0F, 0.0F };
        float[] arrayOfFloat8 = { 0.0F, 0.0F, 0.0F, 0.0F };
        int i4 = PGL.nativeToJavaARGB(arrayOfInt[i1]);
        int i5 = PGL.nativeToJavaARGB(arrayOfInt[i2]);
        int i6 = PGL.nativeToJavaARGB(arrayOfInt[i3]);
        PApplet.arrayCopy(arrayOfFloat1, i1 * 4, arrayOfFloat3, 0, 4);
        PApplet.arrayCopy(arrayOfFloat1, i2 * 4, arrayOfFloat4, 0, 4);
        PApplet.arrayCopy(arrayOfFloat1, i3 * 4, arrayOfFloat5, 0, 4);
        paramPGraphicsOpenGL.modelview.mult(arrayOfFloat3, arrayOfFloat6);
        paramPGraphicsOpenGL.modelview.mult(arrayOfFloat4, arrayOfFloat7);
        paramPGraphicsOpenGL.modelview.mult(arrayOfFloat5, arrayOfFloat8);
        if (paramPImage != null)
        {
          localPGraphics.texture(paramPImage);
          if (localPGraphics.is3D())
          {
            localPGraphics.fill(i4);
            localPGraphics.vertex(arrayOfFloat6[0], arrayOfFloat6[1], arrayOfFloat6[2], arrayOfFloat2[(0 + i1 * 2)], arrayOfFloat2[(1 + i1 * 2)]);
            localPGraphics.fill(i5);
            localPGraphics.vertex(arrayOfFloat7[0], arrayOfFloat7[1], arrayOfFloat7[2], arrayOfFloat2[(0 + i2 * 2)], arrayOfFloat2[(1 + i2 * 2)]);
            localPGraphics.fill(i6);
            localPGraphics.vertex(arrayOfFloat8[0], arrayOfFloat8[1], arrayOfFloat8[2], arrayOfFloat2[(0 + i3 * 2)], arrayOfFloat2[(1 + i3 * 2)]);
          }
        }
        for (;;)
        {
          n++;
          break;
          if (localPGraphics.is2D())
          {
            float f7 = paramPGraphicsOpenGL.screenXImpl(arrayOfFloat6[0], arrayOfFloat6[1], arrayOfFloat6[2], arrayOfFloat6[3]);
            float f8 = paramPGraphicsOpenGL.screenYImpl(arrayOfFloat6[0], arrayOfFloat6[1], arrayOfFloat6[2], arrayOfFloat6[3]);
            float f9 = paramPGraphicsOpenGL.screenXImpl(arrayOfFloat7[0], arrayOfFloat7[1], arrayOfFloat7[2], arrayOfFloat7[3]);
            float f10 = paramPGraphicsOpenGL.screenYImpl(arrayOfFloat7[0], arrayOfFloat7[1], arrayOfFloat7[2], arrayOfFloat7[3]);
            float f11 = paramPGraphicsOpenGL.screenXImpl(arrayOfFloat8[0], arrayOfFloat8[1], arrayOfFloat8[2], arrayOfFloat8[3]);
            float f12 = paramPGraphicsOpenGL.screenYImpl(arrayOfFloat8[0], arrayOfFloat8[1], arrayOfFloat8[2], arrayOfFloat8[3]);
            localPGraphics.fill(i4);
            localPGraphics.vertex(f7, f8, arrayOfFloat2[(0 + i1 * 2)], arrayOfFloat2[(1 + i1 * 2)]);
            localPGraphics.fill(i5);
            localPGraphics.vertex(f9, f10, arrayOfFloat2[(0 + i2 * 2)], arrayOfFloat2[(1 + i2 * 2)]);
            localPGraphics.fill(i5);
            localPGraphics.vertex(f11, f12, arrayOfFloat2[(0 + i3 * 2)], arrayOfFloat2[(1 + i3 * 2)]);
            continue;
            if (localPGraphics.is3D())
            {
              localPGraphics.fill(i4);
              localPGraphics.vertex(arrayOfFloat6[0], arrayOfFloat6[1], arrayOfFloat6[2]);
              localPGraphics.fill(i5);
              localPGraphics.vertex(arrayOfFloat7[0], arrayOfFloat7[1], arrayOfFloat7[2]);
              localPGraphics.fill(i6);
              localPGraphics.vertex(arrayOfFloat8[0], arrayOfFloat8[1], arrayOfFloat8[2]);
            }
            else if (localPGraphics.is2D())
            {
              float f1 = paramPGraphicsOpenGL.screenXImpl(arrayOfFloat6[0], arrayOfFloat6[1], arrayOfFloat6[2], arrayOfFloat6[3]);
              float f2 = paramPGraphicsOpenGL.screenYImpl(arrayOfFloat6[0], arrayOfFloat6[1], arrayOfFloat6[2], arrayOfFloat6[3]);
              float f3 = paramPGraphicsOpenGL.screenXImpl(arrayOfFloat7[0], arrayOfFloat7[1], arrayOfFloat7[2], arrayOfFloat7[3]);
              float f4 = paramPGraphicsOpenGL.screenYImpl(arrayOfFloat7[0], arrayOfFloat7[1], arrayOfFloat7[2], arrayOfFloat7[3]);
              float f5 = paramPGraphicsOpenGL.screenXImpl(arrayOfFloat8[0], arrayOfFloat8[1], arrayOfFloat8[2], arrayOfFloat8[3]);
              float f6 = paramPGraphicsOpenGL.screenYImpl(arrayOfFloat8[0], arrayOfFloat8[1], arrayOfFloat8[2], arrayOfFloat8[3]);
              localPGraphics.fill(i4);
              localPGraphics.vertex(f1, f2);
              localPGraphics.fill(i5);
              localPGraphics.vertex(f3, f4);
              localPGraphics.fill(i6);
              localPGraphics.vertex(f5, f6);
            }
          }
        }
      }
    }
    localPGraphics.endShape();
  }
  
  protected void release()
  {
    deletePolyBuffers();
    deleteLineBuffers();
    deletePointBuffers();
  }
  
  protected void removeTexture(PImage paramPImage)
  {
    if ((this.textures == null) || (!this.textures.contains(paramPImage))) {
      return;
    }
    for (int i = 0;; i++)
    {
      int j = this.childCount;
      int k = 0;
      if (i < j)
      {
        if (((PShapeOpenGL)this.children[i]).hasTexture(paramPImage)) {
          k = 1;
        }
      }
      else
      {
        if (k == 0)
        {
          this.textures.remove(paramPImage);
          if (this.textures.size() == 0) {
            this.textures = null;
          }
        }
        if (this.parent == null) {
          break;
        }
        ((PShapeOpenGL)this.parent).removeTexture(paramPImage);
        return;
      }
    }
  }
  
  protected void render(PGraphicsOpenGL paramPGraphicsOpenGL, PImage paramPImage)
  {
    if (this.root == null) {
      throw new RuntimeException("Error rendering PShapeOpenGL, root shape is null");
    }
    if (this.hasPolys)
    {
      renderPolys(paramPGraphicsOpenGL, paramPImage);
      if (paramPGraphicsOpenGL.haveRaw()) {
        rawPolys(paramPGraphicsOpenGL, paramPImage);
      }
    }
    if (is3D())
    {
      if (this.hasLines)
      {
        renderLines(paramPGraphicsOpenGL);
        if (paramPGraphicsOpenGL.haveRaw()) {
          rawLines(paramPGraphicsOpenGL);
        }
      }
      if (this.hasPoints)
      {
        renderPoints(paramPGraphicsOpenGL);
        if (paramPGraphicsOpenGL.haveRaw()) {
          rawPoints(paramPGraphicsOpenGL);
        }
      }
    }
  }
  
  protected void renderLines(PGraphicsOpenGL paramPGraphicsOpenGL)
  {
    PGraphicsOpenGL.LineShader localLineShader = paramPGraphicsOpenGL.getLineShader();
    localLineShader.bind();
    PGraphicsOpenGL.IndexCache localIndexCache = this.tessGeo.lineIndexCache;
    for (int i = this.firstLineIndexCache; i <= this.lastLineIndexCache; i++)
    {
      int j = localIndexCache.indexOffset[i];
      int k = localIndexCache.indexCount[i];
      int m = localIndexCache.vertexOffset[i];
      localLineShader.setVertexAttribute(this.root.glLineVertex, 4, 5126, 0, 4 * (m * 4));
      localLineShader.setColorAttribute(this.root.glLineColor, 4, 5121, 0, 1 * (m * 4));
      localLineShader.setLineAttribute(this.root.glLineAttrib, 4, 5126, 0, 4 * (m * 4));
      this.pgl.bindBuffer(34963, this.root.glLineIndex);
      this.pgl.drawElements(4, k, 5123, j * 2);
      this.pgl.bindBuffer(34963, 0);
    }
    localLineShader.unbind();
  }
  
  protected void renderPoints(PGraphicsOpenGL paramPGraphicsOpenGL)
  {
    PGraphicsOpenGL.PointShader localPointShader = paramPGraphicsOpenGL.getPointShader();
    localPointShader.bind();
    PGraphicsOpenGL.IndexCache localIndexCache = this.tessGeo.pointIndexCache;
    for (int i = this.firstPointIndexCache; i <= this.lastPointIndexCache; i++)
    {
      int j = localIndexCache.indexOffset[i];
      int k = localIndexCache.indexCount[i];
      int m = localIndexCache.vertexOffset[i];
      localPointShader.setVertexAttribute(this.root.glPointVertex, 4, 5126, 0, 4 * (m * 4));
      localPointShader.setColorAttribute(this.root.glPointColor, 4, 5121, 0, 1 * (m * 4));
      localPointShader.setPointAttribute(this.root.glPointAttrib, 2, 5126, 0, 4 * (m * 2));
      this.pgl.bindBuffer(34963, this.root.glPointIndex);
      this.pgl.drawElements(4, k, 5123, j * 2);
      this.pgl.bindBuffer(34963, 0);
    }
    localPointShader.unbind();
  }
  
  protected void renderPolys(PGraphicsOpenGL paramPGraphicsOpenGL, PImage paramPImage)
  {
    Texture localTexture = null;
    if (paramPImage != null)
    {
      localTexture = paramPGraphicsOpenGL.getTexture(paramPImage);
      if (localTexture != null) {
        localTexture.bind();
      }
    }
    PGraphicsOpenGL.IndexCache localIndexCache = this.tessGeo.polyIndexCache;
    int i = this.firstPolyIndexCache;
    Object localObject1 = localTexture;
    int j = 0;
    PGraphicsOpenGL.BaseShader localBaseShader = null;
    int k = 0;
    boolean bool2;
    label121:
    int m;
    int n;
    Object localObject2;
    if (i <= this.lastPolyIndexCache) {
      if ((is3D()) || ((localObject1 != null) && ((this.firstLineIndexCache == -1) || (i < this.firstLineIndexCache)) && ((this.firstPointIndexCache == -1) || (i < this.firstPointIndexCache))))
      {
        if (k != 0) {
          break label541;
        }
        boolean bool1 = paramPGraphicsOpenGL.lights;
        if (localObject1 != null)
        {
          bool2 = true;
          localBaseShader = paramPGraphicsOpenGL.getPolyShader(bool1, bool2);
          localBaseShader.bind();
          m = j;
          n = 1;
          localObject2 = localObject1;
        }
      }
    }
    for (;;)
    {
      int i1 = localIndexCache.indexOffset[i];
      int i2 = localIndexCache.indexCount[i];
      int i3 = localIndexCache.vertexOffset[i];
      localBaseShader.setVertexAttribute(this.root.glPolyVertex, 4, 5126, 0, 4 * (i3 * 4));
      localBaseShader.setColorAttribute(this.root.glPolyColor, 4, 5121, 0, 1 * (i3 * 4));
      if (paramPGraphicsOpenGL.lights)
      {
        localBaseShader.setNormalAttribute(this.root.glPolyNormal, 3, 5126, 0, 4 * (i3 * 3));
        localBaseShader.setAmbientAttribute(this.root.glPolyAmbient, 4, 5121, 0, 1 * (i3 * 4));
        localBaseShader.setSpecularAttribute(this.root.glPolySpecular, 4, 5121, 0, 1 * (i3 * 4));
        localBaseShader.setEmissiveAttribute(this.root.glPolyEmissive, 4, 5121, 0, 1 * (i3 * 4));
        localBaseShader.setShininessAttribute(this.root.glPolyShininess, 1, 5126, 0, i3 * 4);
      }
      if (localObject2 != null)
      {
        localBaseShader.setTexcoordAttribute(this.root.glPolyTexcoord, 2, 5126, 0, 4 * (i3 * 2));
        localBaseShader.setTexture(localObject2);
      }
      this.pgl.bindBuffer(34963, this.root.glPolyIndex);
      this.pgl.drawElements(4, i2, 5123, i1 * 2);
      this.pgl.bindBuffer(34963, 0);
      i++;
      k = n;
      localObject1 = localObject2;
      j = m;
      break;
      bool2 = false;
      break label121;
      if (j == 0)
      {
        if (localObject1 != null)
        {
          ((Texture)localObject1).unbind();
          localObject1 = null;
        }
        if ((localBaseShader != null) && (localBaseShader.bound())) {
          localBaseShader.unbind();
        }
        localBaseShader = paramPGraphicsOpenGL.getPolyShader(paramPGraphicsOpenGL.lights, false);
        localBaseShader.bind();
        m = 1;
        localObject2 = localObject1;
        n = 0;
        continue;
        if ((localBaseShader != null) && (localBaseShader.bound())) {
          localBaseShader.unbind();
        }
        if (localObject1 != null) {
          ((Texture)localObject1).unbind();
        }
      }
      else
      {
        label541:
        m = j;
        n = k;
        localObject2 = localObject1;
      }
    }
  }
  
  public void resetMatrix()
  {
    if ((this.shapeCreated) && (this.matrix != null))
    {
      if (this.family == 0) {
        updateTessellation();
      }
      if (this.matrix.invert())
      {
        if (this.tessellated) {
          applyMatrixImpl(this.matrix);
        }
        this.matrix = null;
      }
    }
    else
    {
      return;
    }
    PGraphics.showWarning("The transformation matrix cannot be inverted");
  }
  
  public void rotate(float paramFloat)
  {
    transform(1, new float[] { paramFloat });
  }
  
  public void rotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    transform(1, new float[] { paramFloat1, paramFloat2, paramFloat3, paramFloat4 });
  }
  
  public void rotateX(float paramFloat)
  {
    rotate(paramFloat, 1.0F, 0.0F, 0.0F);
  }
  
  public void rotateY(float paramFloat)
  {
    rotate(paramFloat, 0.0F, 1.0F, 0.0F);
  }
  
  public void rotateZ(float paramFloat)
  {
    transform(1, new float[] { paramFloat });
  }
  
  public void scale(float paramFloat)
  {
    transform(2, new float[] { paramFloat, paramFloat });
  }
  
  public void scale(float paramFloat1, float paramFloat2)
  {
    transform(2, new float[] { paramFloat1, paramFloat2 });
  }
  
  public void scale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    transform(2, new float[] { paramFloat1, paramFloat2, paramFloat3 });
  }
  
  public void setAmbient(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setAmbient()" });
    }
    for (;;)
    {
      return;
      if (this.family != 0) {
        break;
      }
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setAmbient(paramInt);
      }
    }
    setAmbientImpl(paramInt);
  }
  
  public void setAmbient(int paramInt1, int paramInt2)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setAmbient()" });
      return;
    }
    this.inGeo.ambient[paramInt1] = PGL.javaToNativeARGB(paramInt2);
    markForTessellation();
    this.setAmbient = true;
  }
  
  protected void setAmbientImpl(int paramInt)
  {
    if (this.ambientColor == paramInt) {
      return;
    }
    this.ambientColor = paramInt;
    Arrays.fill(this.inGeo.ambient, 0, this.inGeo.vertexCount, PGL.javaToNativeARGB(this.ambientColor));
    if ((this.shapeCreated) && (this.tessellated) && (this.hasPolys))
    {
      if (!is3D()) {
        break label115;
      }
      Arrays.fill(this.tessGeo.polyAmbient, this.firstPolyVertex, 1 + this.lastPolyVertex, PGL.javaToNativeARGB(this.ambientColor));
      this.root.setModifiedPolyAmbient(this.firstPolyVertex, this.lastPolyVertex);
    }
    for (;;)
    {
      this.setAmbient = true;
      return;
      label115:
      if (is2D())
      {
        int i = 1 + this.lastPolyVertex;
        if (-1 < this.firstLineVertex) {
          i = this.firstLineVertex;
        }
        if (-1 < this.firstPointVertex) {
          i = this.firstPointVertex;
        }
        Arrays.fill(this.tessGeo.polyAmbient, this.firstPolyVertex, i, PGL.javaToNativeARGB(this.ambientColor));
        this.root.setModifiedPolyColors(this.firstPolyVertex, i - 1);
      }
    }
  }
  
  public void setEmissive(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setEmissive()" });
    }
    for (;;)
    {
      return;
      if (this.family != 0) {
        break;
      }
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setEmissive(paramInt);
      }
    }
    setEmissiveImpl(paramInt);
  }
  
  public void setEmissive(int paramInt1, int paramInt2)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setEmissive()" });
      return;
    }
    this.inGeo.emissive[paramInt1] = PGL.javaToNativeARGB(paramInt2);
    markForTessellation();
  }
  
  protected void setEmissiveImpl(int paramInt)
  {
    if (this.emissiveColor == paramInt) {}
    do
    {
      do
      {
        return;
        this.emissiveColor = paramInt;
        Arrays.fill(this.inGeo.emissive, 0, this.inGeo.vertexCount, PGL.javaToNativeARGB(this.emissiveColor));
      } while ((!this.shapeCreated) || (!this.tessellated) || (this.tessGeo.polyVertexCount <= 0));
      if (is3D())
      {
        Arrays.fill(this.tessGeo.polyEmissive, this.firstPolyVertex, 1 + this.lastPolyVertex, PGL.javaToNativeARGB(this.emissiveColor));
        this.root.setModifiedPolyEmissive(this.firstPolyVertex, this.lastPolyVertex);
        return;
      }
    } while (!is2D());
    int i = 1 + this.lastPolyVertex;
    if (-1 < this.firstLineVertex) {
      i = this.firstLineVertex;
    }
    if (-1 < this.firstPointVertex) {
      i = this.firstPointVertex;
    }
    Arrays.fill(this.tessGeo.polyEmissive, this.firstPolyVertex, i, PGL.javaToNativeARGB(this.emissiveColor));
    this.root.setModifiedPolyColors(this.firstPolyVertex, i - 1);
  }
  
  public void setFill(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setFill()" });
    }
    for (;;)
    {
      return;
      if (this.family != 0) {
        break;
      }
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setFill(paramInt);
      }
    }
    setFillImpl(paramInt);
  }
  
  public void setFill(int paramInt1, int paramInt2)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setFill()" });
    }
    while (this.image != null) {
      return;
    }
    this.inGeo.colors[paramInt1] = PGL.javaToNativeARGB(paramInt2);
    markForTessellation();
  }
  
  public void setFill(boolean paramBoolean)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setFill()" });
      return;
    }
    if (this.family == 0) {
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setFill(paramBoolean);
      }
    }
    if ((this.fill) && (!paramBoolean)) {
      setFillImpl(0);
    }
    this.fill = paramBoolean;
  }
  
  protected void setFillImpl(int paramInt)
  {
    if (this.fillColor == paramInt) {}
    for (;;)
    {
      return;
      this.fillColor = paramInt;
      if (this.image == null)
      {
        Arrays.fill(this.inGeo.colors, 0, this.inGeo.vertexCount, PGL.javaToNativeARGB(this.fillColor));
        if ((this.shapeCreated) && (this.tessellated) && (this.hasPolys))
        {
          if (!is3D()) {
            break label134;
          }
          Arrays.fill(this.tessGeo.polyColors, this.firstPolyVertex, 1 + this.lastPolyVertex, PGL.javaToNativeARGB(this.fillColor));
          this.root.setModifiedPolyColors(this.firstPolyVertex, this.lastPolyVertex);
        }
      }
      while (!this.setAmbient)
      {
        setAmbientImpl(paramInt);
        this.setAmbient = false;
        return;
        label134:
        if (is2D())
        {
          int i = 1 + this.lastPolyVertex;
          if (-1 < this.firstLineVertex) {
            i = this.firstLineVertex;
          }
          if (-1 < this.firstPointVertex) {
            i = this.firstPointVertex;
          }
          Arrays.fill(this.tessGeo.polyColors, this.firstPolyVertex, i, PGL.javaToNativeARGB(this.fillColor));
          this.root.setModifiedPolyColors(this.firstPolyVertex, i - 1);
        }
      }
    }
  }
  
  protected void setFirstStrokeVertex(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == this.firstLineIndexCache) && (this.firstLineVertex == -1))
    {
      this.lastLineVertex = paramInt2;
      this.firstLineVertex = paramInt2;
    }
    if ((paramInt1 == this.firstPointIndexCache) && (this.firstPointVertex == -1))
    {
      this.lastPointVertex = paramInt2;
      this.firstPointVertex = paramInt2;
    }
  }
  
  protected void setLastStrokeVertex(int paramInt)
  {
    if (-1 < this.lastLineVertex) {
      this.lastLineVertex = paramInt;
    }
    if (-1 < this.lastPointVertex) {
      this.lastPointVertex = (paramInt + this.lastPointVertex);
    }
  }
  
  protected void setModifiedLineAttributes(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedLineAttribute) {
      this.firstModifiedLineAttribute = paramInt1;
    }
    if (paramInt2 > this.lastModifiedLineAttribute) {
      this.lastModifiedLineAttribute = paramInt2;
    }
    this.modifiedLineAttributes = true;
    this.modified = true;
  }
  
  protected void setModifiedLineColors(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedLineColor) {
      this.firstModifiedLineColor = paramInt1;
    }
    if (paramInt2 > this.lastModifiedLineColor) {
      this.lastModifiedLineColor = paramInt2;
    }
    this.modifiedLineColors = true;
    this.modified = true;
  }
  
  protected void setModifiedLineVertices(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedLineVertex) {
      this.firstModifiedLineVertex = paramInt1;
    }
    if (paramInt2 > this.lastModifiedLineVertex) {
      this.lastModifiedLineVertex = paramInt2;
    }
    this.modifiedLineVertices = true;
    this.modified = true;
  }
  
  protected void setModifiedPointAttributes(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedPointAttribute) {
      this.firstModifiedPointAttribute = paramInt1;
    }
    if (paramInt2 > this.lastModifiedPointAttribute) {
      this.lastModifiedPointAttribute = paramInt2;
    }
    this.modifiedPointAttributes = true;
    this.modified = true;
  }
  
  protected void setModifiedPointColors(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedPointColor) {
      this.firstModifiedPointColor = paramInt1;
    }
    if (paramInt2 > this.lastModifiedPointColor) {
      this.lastModifiedPointColor = paramInt2;
    }
    this.modifiedPointColors = true;
    this.modified = true;
  }
  
  protected void setModifiedPointVertices(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedPointVertex) {
      this.firstModifiedPointVertex = paramInt1;
    }
    if (paramInt2 > this.lastModifiedPointVertex) {
      this.lastModifiedPointVertex = paramInt2;
    }
    this.modifiedPointVertices = true;
    this.modified = true;
  }
  
  protected void setModifiedPolyAmbient(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedPolyAmbient) {
      this.firstModifiedPolyAmbient = paramInt1;
    }
    if (paramInt2 > this.lastModifiedPolyAmbient) {
      this.lastModifiedPolyAmbient = paramInt2;
    }
    this.modifiedPolyAmbient = true;
    this.modified = true;
  }
  
  protected void setModifiedPolyColors(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedPolyColor) {
      this.firstModifiedPolyColor = paramInt1;
    }
    if (paramInt2 > this.lastModifiedPolyColor) {
      this.lastModifiedPolyColor = paramInt2;
    }
    this.modifiedPolyColors = true;
    this.modified = true;
  }
  
  protected void setModifiedPolyEmissive(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedPolyEmissive) {
      this.firstModifiedPolyEmissive = paramInt1;
    }
    if (paramInt2 > this.lastModifiedPolyEmissive) {
      this.lastModifiedPolyEmissive = paramInt2;
    }
    this.modifiedPolyEmissive = true;
    this.modified = true;
  }
  
  protected void setModifiedPolyNormals(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedPolyNormal) {
      this.firstModifiedPolyNormal = paramInt1;
    }
    if (paramInt2 > this.lastModifiedPolyNormal) {
      this.lastModifiedPolyNormal = paramInt2;
    }
    this.modifiedPolyNormals = true;
    this.modified = true;
  }
  
  protected void setModifiedPolyShininess(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedPolyShininess) {
      this.firstModifiedPolyShininess = paramInt1;
    }
    if (paramInt2 > this.lastModifiedPolyShininess) {
      this.lastModifiedPolyShininess = paramInt2;
    }
    this.modifiedPolyShininess = true;
    this.modified = true;
  }
  
  protected void setModifiedPolySpecular(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedPolySpecular) {
      this.firstModifiedPolySpecular = paramInt1;
    }
    if (paramInt2 > this.lastModifiedPolySpecular) {
      this.lastModifiedPolySpecular = paramInt2;
    }
    this.modifiedPolySpecular = true;
    this.modified = true;
  }
  
  protected void setModifiedPolyTexCoords(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedPolyTexcoord) {
      this.firstModifiedPolyTexcoord = paramInt1;
    }
    if (paramInt2 > this.lastModifiedPolyTexcoord) {
      this.lastModifiedPolyTexcoord = paramInt2;
    }
    this.modifiedPolyTexCoords = true;
    this.modified = true;
  }
  
  protected void setModifiedPolyVertices(int paramInt1, int paramInt2)
  {
    if (paramInt1 < this.firstModifiedPolyVertex) {
      this.firstModifiedPolyVertex = paramInt1;
    }
    if (paramInt2 > this.lastModifiedPolyVertex) {
      this.lastModifiedPolyVertex = paramInt2;
    }
    this.modifiedPolyVertices = true;
    this.modified = true;
  }
  
  public void setNormal(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setNormal()" });
      return;
    }
    this.inGeo.normals[(0 + paramInt * 3)] = paramFloat1;
    this.inGeo.normals[(1 + paramInt * 3)] = paramFloat2;
    this.inGeo.normals[(2 + paramInt * 3)] = paramFloat3;
    markForTessellation();
  }
  
  public void setParams(float[] paramArrayOfFloat)
  {
    if (this.family != 1)
    {
      PGraphics.showWarning("Parameters can only be set to PRIMITIVE shapes");
      return;
    }
    super.setParams(paramArrayOfFloat);
    markForTessellation();
    this.shapeCreated = true;
  }
  
  public void setPath(int paramInt1, float[][] paramArrayOfFloat, int paramInt2, int[] paramArrayOfInt)
  {
    if (this.family != 2)
    {
      PGraphics.showWarning("Vertex coordinates and codes can only be set to PATH shapes");
      return;
    }
    super.setPath(paramInt1, paramArrayOfFloat, paramInt2, paramArrayOfInt);
    markForTessellation();
    this.shapeCreated = true;
  }
  
  public void setShininess(float paramFloat)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setShininess()" });
    }
    for (;;)
    {
      return;
      if (this.family != 0) {
        break;
      }
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setShininess(paramFloat);
      }
    }
    setShininessImpl(paramFloat);
  }
  
  public void setShininess(int paramInt, float paramFloat)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setShininess()" });
      return;
    }
    this.inGeo.shininess[paramInt] = paramFloat;
    markForTessellation();
  }
  
  protected void setShininessImpl(float paramFloat)
  {
    if (PGraphicsOpenGL.same(this.shininess, paramFloat)) {}
    do
    {
      do
      {
        return;
        this.shininess = paramFloat;
        Arrays.fill(this.inGeo.shininess, 0, this.inGeo.vertexCount, paramFloat);
      } while ((!this.shapeCreated) || (!this.tessellated) || (!this.hasPolys));
      if (is3D())
      {
        Arrays.fill(this.tessGeo.polyShininess, this.firstPolyVertex, 1 + this.lastPolyVertex, paramFloat);
        this.root.setModifiedPolyShininess(this.firstPolyVertex, this.lastPolyVertex);
        return;
      }
    } while (!is2D());
    int i = 1 + this.lastPolyVertex;
    if (-1 < this.firstLineVertex) {
      i = this.firstLineVertex;
    }
    if (-1 < this.firstPointVertex) {
      i = this.firstPointVertex;
    }
    Arrays.fill(this.tessGeo.polyShininess, this.firstPolyVertex, i, paramFloat);
    this.root.setModifiedPolyColors(this.firstPolyVertex, i - 1);
  }
  
  public void setSpecular(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setSpecular()" });
    }
    for (;;)
    {
      return;
      if (this.family != 0) {
        break;
      }
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setSpecular(paramInt);
      }
    }
    setSpecularImpl(paramInt);
  }
  
  public void setSpecular(int paramInt1, int paramInt2)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setSpecular()" });
      return;
    }
    this.inGeo.specular[paramInt1] = PGL.javaToNativeARGB(paramInt2);
    markForTessellation();
  }
  
  protected void setSpecularImpl(int paramInt)
  {
    if (this.specularColor == paramInt) {}
    do
    {
      do
      {
        return;
        this.specularColor = paramInt;
        Arrays.fill(this.inGeo.specular, 0, this.inGeo.vertexCount, PGL.javaToNativeARGB(this.specularColor));
      } while ((!this.shapeCreated) || (!this.tessellated) || (!this.hasPolys));
      if (is3D())
      {
        Arrays.fill(this.tessGeo.polySpecular, this.firstPolyVertex, 1 + this.lastPolyVertex, PGL.javaToNativeARGB(this.specularColor));
        this.root.setModifiedPolySpecular(this.firstPolyVertex, this.lastPolyVertex);
        return;
      }
    } while (!is2D());
    int i = 1 + this.lastPolyVertex;
    if (-1 < this.firstLineVertex) {
      i = this.firstLineVertex;
    }
    if (-1 < this.firstPointVertex) {
      i = this.firstPointVertex;
    }
    Arrays.fill(this.tessGeo.polySpecular, this.firstPolyVertex, i, PGL.javaToNativeARGB(this.specularColor));
    this.root.setModifiedPolyColors(this.firstPolyVertex, i - 1);
  }
  
  public void setStroke(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStroke()" });
    }
    for (;;)
    {
      return;
      if (this.family != 0) {
        break;
      }
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setStroke(paramInt);
      }
    }
    setStrokeImpl(paramInt);
  }
  
  public void setStroke(int paramInt1, int paramInt2)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStroke()" });
      return;
    }
    this.inGeo.strokeColors[paramInt1] = PGL.javaToNativeARGB(paramInt2);
    markForTessellation();
  }
  
  public void setStroke(boolean paramBoolean)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStroke()" });
      return;
    }
    int i = this.family;
    int j = 0;
    if (i == 0) {
      while (j < this.childCount)
      {
        ((PShapeOpenGL)this.children[j]).setStroke(paramBoolean);
        j++;
      }
    }
    if (this.stroke != paramBoolean)
    {
      if (this.stroke)
      {
        markForTessellation();
        paramBoolean = false;
      }
      setStrokeImpl(0);
      if ((is2D()) && (this.parent != null)) {
        ((PShapeOpenGL)this.parent).strokedTexture(false);
      }
    }
    this.stroke = paramBoolean;
  }
  
  public void setStrokeCap(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStrokeCap()" });
    }
    for (;;)
    {
      return;
      if (this.family != 0) {
        break;
      }
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setStrokeCap(paramInt);
      }
    }
    if ((is2D()) && (this.strokeCap != paramInt)) {
      markForTessellation();
    }
    this.strokeCap = paramInt;
  }
  
  protected void setStrokeImpl(int paramInt)
  {
    if (this.strokeColor == paramInt) {}
    label180:
    label232:
    do
    {
      for (;;)
      {
        return;
        this.strokeColor = paramInt;
        Arrays.fill(this.inGeo.strokeColors, 0, this.inGeo.vertexCount, PGL.javaToNativeARGB(this.strokeColor));
        if ((this.shapeCreated) && (this.tessellated) && ((this.hasLines) || (this.hasPoints)))
        {
          if (this.hasLines)
          {
            if (!is3D()) {
              break label180;
            }
            Arrays.fill(this.tessGeo.lineColors, this.firstLineVertex, 1 + this.lastLineVertex, PGL.javaToNativeARGB(this.strokeColor));
            this.root.setModifiedLineColors(this.firstLineVertex, this.lastLineVertex);
          }
          while (this.hasPoints)
          {
            if (!is3D()) {
              break label232;
            }
            Arrays.fill(this.tessGeo.pointColors, this.firstPointVertex, 1 + this.lastPointVertex, PGL.javaToNativeARGB(this.strokeColor));
            this.root.setModifiedPointColors(this.firstPointVertex, this.lastPointVertex);
            return;
            if (is2D())
            {
              Arrays.fill(this.tessGeo.polyColors, this.firstLineVertex, 1 + this.lastLineVertex, PGL.javaToNativeARGB(this.strokeColor));
              this.root.setModifiedPolyColors(this.firstLineVertex, this.lastLineVertex);
            }
          }
        }
      }
    } while (!is2D());
    Arrays.fill(this.tessGeo.polyColors, this.firstPointVertex, 1 + this.lastPointVertex, PGL.javaToNativeARGB(this.strokeColor));
    this.root.setModifiedPolyColors(this.firstPointVertex, this.lastPointVertex);
  }
  
  public void setStrokeJoin(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStrokeJoin()" });
    }
    for (;;)
    {
      return;
      if (this.family != 0) {
        break;
      }
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setStrokeJoin(paramInt);
      }
    }
    if ((is2D()) && (this.strokeJoin != paramInt)) {
      markForTessellation();
    }
    this.strokeJoin = paramInt;
  }
  
  public void setStrokeWeight(float paramFloat)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStrokeWeight()" });
    }
    for (;;)
    {
      return;
      if (this.family != 0) {
        break;
      }
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setStrokeWeight(paramFloat);
      }
    }
    setStrokeWeightImpl(paramFloat);
  }
  
  public void setStrokeWeight(int paramInt, float paramFloat)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setStrokeWeight()" });
      return;
    }
    this.inGeo.strokeWeights[paramInt] = paramFloat;
    markForTessellation();
  }
  
  protected void setStrokeWeightImpl(float paramFloat)
  {
    if (PGraphicsOpenGL.same(this.strokeWeight, paramFloat)) {}
    label248:
    do
    {
      for (;;)
      {
        return;
        float f1 = this.strokeWeight;
        this.strokeWeight = paramFloat;
        Arrays.fill(this.inGeo.strokeWeights, 0, this.inGeo.vertexCount, this.strokeWeight);
        if ((this.shapeCreated) && (this.tessellated) && ((this.hasLines) || (this.hasPoints)))
        {
          float f2 = paramFloat / f1;
          if (this.hasLines)
          {
            if (!is3D()) {
              break label248;
            }
            for (int m = this.firstLineVertex; m <= this.lastLineVertex; m++)
            {
              float[] arrayOfFloat3 = this.tessGeo.lineDirections;
              int n = 3 + m * 4;
              arrayOfFloat3[n] = (f2 * arrayOfFloat3[n]);
            }
            this.root.setModifiedLineAttributes(this.firstLineVertex, this.lastLineVertex);
          }
          while (this.hasPoints)
          {
            if (!is3D()) {
              break label278;
            }
            for (int i = this.firstPointVertex; i <= this.lastPointVertex; i++)
            {
              float[] arrayOfFloat1 = this.tessGeo.pointOffsets;
              int j = 0 + i * 2;
              arrayOfFloat1[j] = (f2 * arrayOfFloat1[j]);
              float[] arrayOfFloat2 = this.tessGeo.pointOffsets;
              int k = 1 + i * 2;
              arrayOfFloat2[k] = (f2 * arrayOfFloat2[k]);
            }
            if (is2D()) {
              markForTessellation();
            }
          }
        }
      }
      this.root.setModifiedPointAttributes(this.firstPointVertex, this.lastPointVertex);
      return;
    } while (!is2D());
    label278:
    markForTessellation();
  }
  
  public void setTexture(PImage paramPImage)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTexture()" });
    }
    do
    {
      do
      {
        for (;;)
        {
          return;
          if (this.family != 0) {
            break;
          }
          for (int i = 0; i < this.childCount; i++) {
            ((PShapeOpenGL)this.children[i]).texture(paramPImage);
          }
        }
        PImage localPImage = this.image;
        this.image = paramPImage;
        if ((localPImage != paramPImage) && (this.parent != null)) {
          ((PShapeOpenGL)this.parent).removeTexture(paramPImage);
        }
      } while (this.parent == null);
      ((PShapeOpenGL)this.parent).addTexture(this.image);
    } while ((!is2D()) || (!this.stroke));
    ((PShapeOpenGL)this.parent).strokedTexture(true);
  }
  
  public void setTextureMode(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTextureMode()" });
    }
    for (;;)
    {
      return;
      if (this.family != 0) {
        break;
      }
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).textureMode(paramInt);
      }
    }
    this.textureMode = paramInt;
  }
  
  public void setTextureUV(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTextureUV()" });
      return;
    }
    this.inGeo.texcoords[(0 + paramInt * 2)] = paramFloat1;
    this.inGeo.texcoords[(1 + paramInt * 2)] = paramFloat2;
    markForTessellation();
  }
  
  public void setTint(int paramInt)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTint()" });
    }
    for (;;)
    {
      return;
      if (this.family != 0) {
        break;
      }
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setTint(paramInt);
      }
    }
    setTintImpl(paramInt);
  }
  
  public void setTint(int paramInt1, int paramInt2)
  {
    if (this.openShape) {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTint()" });
    }
    while (this.image == null) {
      return;
    }
    this.inGeo.colors[paramInt1] = PGL.javaToNativeARGB(paramInt2);
    markForTessellation();
  }
  
  public void setTint(boolean paramBoolean)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setTint()" });
      return;
    }
    if (this.family == 0) {
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).setTint(this.fill);
      }
    }
    if ((this.tint) && (!paramBoolean)) {
      setTintImpl(-1);
    }
    this.tint = paramBoolean;
  }
  
  protected void setTintImpl(int paramInt)
  {
    if (this.tintColor == paramInt) {}
    do
    {
      do
      {
        do
        {
          return;
          this.tintColor = paramInt;
        } while (this.image == null);
        Arrays.fill(this.inGeo.colors, 0, this.inGeo.vertexCount, PGL.javaToNativeARGB(this.tintColor));
      } while ((!this.shapeCreated) || (!this.tessellated) || (!this.hasPolys));
      if (is3D())
      {
        Arrays.fill(this.tessGeo.polyColors, this.firstPolyVertex, 1 + this.lastPolyVertex, PGL.javaToNativeARGB(this.tintColor));
        this.root.setModifiedPolyColors(this.firstPolyVertex, this.lastPolyVertex);
        return;
      }
    } while (!is2D());
    int i = 1 + this.lastPolyVertex;
    if (-1 < this.firstLineVertex) {
      i = this.firstLineVertex;
    }
    if (-1 < this.firstPointVertex) {
      i = this.firstPointVertex;
    }
    Arrays.fill(this.tessGeo.polyColors, this.firstPolyVertex, i, PGL.javaToNativeARGB(this.tintColor));
    this.root.setModifiedPolyColors(this.firstPolyVertex, i - 1);
  }
  
  public void setVertex(int paramInt, float paramFloat1, float paramFloat2)
  {
    setVertex(paramInt, paramFloat1, paramFloat2, 0.0F);
  }
  
  public void setVertex(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setVertex()" });
      return;
    }
    this.inGeo.vertices[(0 + paramInt * 3)] = paramFloat1;
    this.inGeo.vertices[(1 + paramInt * 3)] = paramFloat2;
    this.inGeo.vertices[(2 + paramInt * 3)] = paramFloat3;
    markForTessellation();
  }
  
  public void setVertex(int paramInt, PVector paramPVector)
  {
    if (this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called outside beginShape() and endShape()", new Object[] { "setVertex()" });
      return;
    }
    this.inGeo.vertices[(0 + paramInt * 3)] = paramPVector.x;
    this.inGeo.vertices[(1 + paramInt * 3)] = paramPVector.y;
    this.inGeo.vertices[(2 + paramInt * 3)] = paramPVector.z;
    markForTessellation();
  }
  
  public void solid(boolean paramBoolean)
  {
    if (this.family == 0) {
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).solid(paramBoolean);
      }
    }
    this.isSolid = paramBoolean;
  }
  
  protected boolean startStrokedTex(int paramInt)
  {
    return (this.image != null) && ((paramInt == this.firstLineIndexCache) || (paramInt == this.firstPointIndexCache));
  }
  
  protected void strokedTexture(boolean paramBoolean)
  {
    if (this.strokedTexture == paramBoolean) {
      return;
    }
    if (paramBoolean) {
      this.strokedTexture = true;
    }
    label18:
    label39:
    label76:
    label82:
    label85:
    for (;;)
    {
      int i;
      if (this.parent != null)
      {
        ((PShapeOpenGL)this.parent).strokedTexture(paramBoolean);
        return;
        i = 0;
        if (i >= this.childCount) {
          break label82;
        }
        if (!((PShapeOpenGL)this.children[i]).hasStrokedTexture()) {
          break label76;
        }
      }
      for (int j = 1;; j = 0)
      {
        if (j != 0) {
          break label85;
        }
        this.strokedTexture = false;
        break label18;
        break;
        i++;
        break label39;
      }
    }
  }
  
  protected void styles(PGraphics paramPGraphics)
  {
    if ((paramPGraphics instanceof PGraphicsOpenGL))
    {
      if (this.stroke)
      {
        stroke(paramPGraphics.strokeColor);
        strokeWeight(paramPGraphics.strokeWeight);
        strokeCap(paramPGraphics.strokeCap);
        strokeJoin(paramPGraphics.strokeJoin);
        if (!this.fill) {
          break label101;
        }
        fill(paramPGraphics.fillColor);
      }
      for (;;)
      {
        ambient(paramPGraphics.ambientColor);
        specular(paramPGraphics.specularColor);
        emissive(paramPGraphics.emissiveColor);
        shininess(paramPGraphics.shininess);
        return;
        noStroke();
        break;
        label101:
        noFill();
      }
    }
    super.styles(paramPGraphics);
  }
  
  protected void tessellate()
  {
    if ((this.root == this) && (this.parent == null))
    {
      if (this.tessGeo == null) {
        this.tessGeo = this.pg.newTessGeometry(1);
      }
      this.tessGeo.clear();
      tessellateImpl();
      this.tessGeo.trim();
      this.modified = false;
      this.needBufferInit = true;
      this.modifiedPolyVertices = false;
      this.modifiedPolyColors = false;
      this.modifiedPolyNormals = false;
      this.modifiedPolyTexCoords = false;
      this.modifiedPolyAmbient = false;
      this.modifiedPolySpecular = false;
      this.modifiedPolyEmissive = false;
      this.modifiedPolyShininess = false;
      this.modifiedLineVertices = false;
      this.modifiedLineColors = false;
      this.modifiedLineAttributes = false;
      this.modifiedPointVertices = false;
      this.modifiedPointColors = false;
      this.modifiedPointAttributes = false;
      this.firstModifiedPolyVertex = 2147483647;
      this.lastModifiedPolyVertex = -2147483648;
      this.firstModifiedPolyColor = 2147483647;
      this.lastModifiedPolyColor = -2147483648;
      this.firstModifiedPolyNormal = 2147483647;
      this.lastModifiedPolyNormal = -2147483648;
      this.firstModifiedPolyTexcoord = 2147483647;
      this.lastModifiedPolyTexcoord = -2147483648;
      this.firstModifiedPolyAmbient = 2147483647;
      this.lastModifiedPolyAmbient = -2147483648;
      this.firstModifiedPolySpecular = 2147483647;
      this.lastModifiedPolySpecular = -2147483648;
      this.firstModifiedPolyEmissive = 2147483647;
      this.lastModifiedPolyEmissive = -2147483648;
      this.firstModifiedPolyShininess = 2147483647;
      this.lastModifiedPolyShininess = -2147483648;
      this.firstModifiedLineVertex = 2147483647;
      this.lastModifiedLineVertex = -2147483648;
      this.firstModifiedLineColor = 2147483647;
      this.lastModifiedLineColor = -2147483648;
      this.firstModifiedLineAttribute = 2147483647;
      this.lastModifiedLineAttribute = -2147483648;
      this.firstModifiedPointVertex = 2147483647;
      this.lastModifiedPointVertex = -2147483648;
      this.firstModifiedPointColor = 2147483647;
      this.lastModifiedPointColor = -2147483648;
      this.firstModifiedPointAttribute = 2147483647;
      this.lastModifiedPointAttribute = -2147483648;
    }
  }
  
  protected void tessellateArc()
  {
    float f6;
    float f5;
    float f4;
    float f3;
    float f2;
    float f1;
    int i;
    float f7;
    float f8;
    float f9;
    float f10;
    float f11;
    float f12;
    if (this.params.length != 6)
    {
      int j = this.params.length;
      f6 = 0.0F;
      f5 = 0.0F;
      f4 = 0.0F;
      f3 = 0.0F;
      f2 = 0.0F;
      f1 = 0.0F;
      if (j != 7) {}
    }
    else
    {
      f1 = this.params[0];
      f2 = this.params[1];
      f3 = this.params[2];
      f4 = this.params[3];
      f5 = this.params[4];
      f6 = this.params[5];
      if (this.params.length == 7)
      {
        i = (int)this.params[6];
        f7 = f6;
        f8 = f5;
        f9 = f4;
        f10 = f3;
        f11 = f2;
        f12 = f1;
      }
    }
    for (;;)
    {
      this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
      this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
      PGraphicsOpenGL.InGeometry localInGeometry = this.inGeo;
      boolean bool1 = this.fill;
      boolean bool2 = this.stroke;
      localInGeometry.addArc(f12, f11, f10, f9, f8, f7, bool1, bool2, i);
      this.tessellator.tessellateTriangleFan();
      return;
      f7 = f6;
      f8 = f5;
      f9 = f4;
      f10 = f3;
      f11 = f2;
      f12 = f1;
      i = 0;
    }
  }
  
  protected void tessellateBox()
  {
    float f1;
    float f2;
    float f3;
    if (this.params.length == 1)
    {
      float f6 = this.params[0];
      f1 = f6;
      f2 = f6;
      f3 = f6;
    }
    for (;;)
    {
      this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
      PGraphicsOpenGL.InGeometry localInGeometry = this.inGeo;
      boolean bool1 = this.fill;
      boolean bool2 = this.stroke;
      localInGeometry.addBox(f3, f2, f1, bool1, bool2);
      this.tessellator.tessellateQuads();
      return;
      if (this.params.length == 3)
      {
        float f4 = this.params[0];
        float f5 = this.params[1];
        f1 = this.params[2];
        f2 = f5;
        f3 = f4;
      }
      else
      {
        f1 = 0.0F;
        f2 = 0.0F;
        f3 = 0.0F;
      }
    }
  }
  
  protected void tessellateEllipse()
  {
    float f5;
    float f1;
    float f2;
    float f3;
    if (this.params.length == 4)
    {
      f5 = this.params[0];
      float f6 = this.params[1];
      float f7 = this.params[2];
      f1 = this.params[3];
      f2 = f7;
      f3 = f6;
    }
    for (float f4 = f5;; f4 = 0.0F)
    {
      this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
      this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
      PGraphicsOpenGL.InGeometry localInGeometry = this.inGeo;
      boolean bool1 = this.fill;
      boolean bool2 = this.stroke;
      int i = this.ellipseMode;
      localInGeometry.addEllipse(f4, f3, f2, f1, bool1, bool2, i);
      this.tessellator.tessellateTriangleFan();
      return;
      f1 = 0.0F;
      f2 = 0.0F;
      f3 = 0.0F;
    }
  }
  
  protected void tessellateImpl()
  {
    this.tessGeo = this.root.tessGeo;
    this.firstPolyIndexCache = -1;
    this.lastPolyIndexCache = -1;
    this.firstLineIndexCache = -1;
    this.lastLineIndexCache = -1;
    this.firstPointIndexCache = -1;
    this.lastPointIndexCache = -1;
    if (this.family == 0) {
      for (int j = 0; j < this.childCount; j++) {
        ((PShapeOpenGL)this.children[j]).tessellateImpl();
      }
    }
    boolean bool1;
    if (this.shapeCreated)
    {
      this.inGeo.clearEdges();
      this.tessellator.setInGeometry(this.inGeo);
      this.tessellator.setTessGeometry(this.tessGeo);
      PGraphicsOpenGL.Tessellator localTessellator1 = this.tessellator;
      if ((!this.fill) && (this.image == null)) {
        break label381;
      }
      bool1 = true;
      localTessellator1.setFill(bool1);
      this.tessellator.setStroke(this.stroke);
      this.tessellator.setStrokeColor(this.strokeColor);
      this.tessellator.setStrokeWeight(this.strokeWeight);
      this.tessellator.setStrokeCap(this.strokeCap);
      this.tessellator.setStrokeJoin(this.strokeJoin);
      this.tessellator.setTexCache(null, null, null);
      this.tessellator.setTransform(this.matrix);
      this.tessellator.set3D(is3D());
      if (this.family != 3) {
        break label769;
      }
      if (this.kind != 3) {
        break label386;
      }
      this.tessellator.tessellatePoints();
    }
    label769:
    for (;;)
    {
      if ((this.image != null) && (this.parent != null)) {
        ((PShapeOpenGL)this.parent).addTexture(this.image);
      }
      this.firstPolyIndexCache = this.tessellator.firstPolyIndexCache;
      this.lastPolyIndexCache = this.tessellator.lastPolyIndexCache;
      this.firstLineIndexCache = this.tessellator.firstLineIndexCache;
      this.lastLineIndexCache = this.tessellator.lastLineIndexCache;
      this.firstPointIndexCache = this.tessellator.firstPointIndexCache;
      this.lastPointIndexCache = this.tessellator.lastPointIndexCache;
      this.lastPolyVertex = -1;
      this.firstPolyVertex = -1;
      this.lastLineVertex = -1;
      this.firstLineVertex = -1;
      this.lastPointVertex = -1;
      this.firstPointVertex = -1;
      this.tessellated = true;
      return;
      label381:
      bool1 = false;
      break;
      label386:
      if (this.kind == 5)
      {
        this.tessellator.tessellateLines();
      }
      else if (this.kind == 50)
      {
        this.tessellator.tessellateLineStrip();
      }
      else if (this.kind == 51)
      {
        this.tessellator.tessellateLineLoop();
      }
      else if ((this.kind == 8) || (this.kind == 9))
      {
        if (this.stroke) {
          this.inGeo.addTrianglesEdges();
        }
        if (this.normalMode == 0) {
          this.inGeo.calcTrianglesNormals();
        }
        this.tessellator.tessellateTriangles();
      }
      else if (this.kind == 11)
      {
        if (this.stroke) {
          this.inGeo.addTriangleFanEdges();
        }
        if (this.normalMode == 0) {
          this.inGeo.calcTriangleFanNormals();
        }
        this.tessellator.tessellateTriangleFan();
      }
      else if (this.kind == 10)
      {
        if (this.stroke) {
          this.inGeo.addTriangleStripEdges();
        }
        if (this.normalMode == 0) {
          this.inGeo.calcTriangleStripNormals();
        }
        this.tessellator.tessellateTriangleStrip();
      }
      else if ((this.kind == 16) || (this.kind == 17))
      {
        if (this.stroke) {
          this.inGeo.addQuadsEdges();
        }
        if (this.normalMode == 0) {
          this.inGeo.calcQuadsNormals();
        }
        this.tessellator.tessellateQuads();
      }
      else if (this.kind == 18)
      {
        if (this.stroke) {
          this.inGeo.addQuadStripEdges();
        }
        if (this.normalMode == 0) {
          this.inGeo.calcQuadStripNormals();
        }
        this.tessellator.tessellateQuadStrip();
      }
      else if (this.kind == 20)
      {
        if (this.stroke) {
          this.inGeo.addPolygonEdges(this.isClosed);
        }
        PGraphicsOpenGL.Tessellator localTessellator2 = this.tessellator;
        boolean bool2 = this.isSolid;
        boolean bool3 = this.isClosed;
        int i = this.normalMode;
        boolean bool4 = false;
        if (i == 0) {
          bool4 = true;
        }
        localTessellator2.tessellatePolygon(bool2, bool3, bool4);
        continue;
        if (this.family == 1)
        {
          this.inGeo.clear();
          if (this.kind == 2) {
            tessellatePoint();
          } else if (this.kind == 4) {
            tessellateLine();
          } else if (this.kind == 8) {
            tessellateTriangle();
          } else if (this.kind == 16) {
            tessellateQuad();
          } else if (this.kind == 30) {
            tessellateRect();
          } else if (this.kind == 31) {
            tessellateEllipse();
          } else if (this.kind == 32) {
            tessellateArc();
          } else if (this.kind == 41) {
            tessellateBox();
          } else if (this.kind == 40) {
            tessellateSphere();
          }
        }
        else if (this.family == 2)
        {
          this.inGeo.clear();
          tessellatePath();
        }
      }
    }
  }
  
  protected void tessellateLine()
  {
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;
    float f6;
    if (this.params.length == 4)
    {
      float f12 = this.params[0];
      float f13 = this.params[1];
      float f14 = this.params[2];
      float f15 = this.params[3];
      f1 = 0.0F;
      f2 = f15;
      f3 = f14;
      f4 = 0.0F;
      f5 = f13;
      f6 = f12;
    }
    for (;;)
    {
      this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
      this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
      PGraphicsOpenGL.InGeometry localInGeometry = this.inGeo;
      boolean bool1 = this.fill;
      boolean bool2 = this.stroke;
      localInGeometry.addLine(f6, f5, f4, f3, f2, f1, bool1, bool2);
      this.tessellator.tessellateLines();
      return;
      if (this.params.length == 6)
      {
        float f7 = this.params[0];
        float f8 = this.params[1];
        float f9 = this.params[2];
        float f10 = this.params[3];
        float f11 = this.params[4];
        f1 = this.params[5];
        f2 = f11;
        f3 = f10;
        f4 = f9;
        f5 = f8;
        f6 = f7;
      }
      else
      {
        f1 = 0.0F;
        f2 = 0.0F;
        f3 = 0.0F;
        f4 = 0.0F;
        f5 = 0.0F;
        f6 = 0.0F;
      }
    }
  }
  
  protected void tessellatePath()
  {
    if (this.vertices == null) {
      return;
    }
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    if (this.vertexCodeCount == 0)
    {
      if (this.vertices[0].length == 2) {
        for (int i6 = 0; i6 < this.vertexCount; i6++) {
          this.inGeo.addVertex(this.vertices[i6][0], this.vertices[i6][1], 0);
        }
      }
      for (int i5 = 0; i5 < this.vertexCount; i5++) {
        this.inGeo.addVertex(this.vertices[i5][0], this.vertices[i5][1], this.vertices[i5][2], 0);
      }
    }
    int i = 4;
    if (this.vertices[0].length == 2)
    {
      int i1 = 0;
      int i2 = 0;
      int i3 = this.vertexCodeCount;
      if (i1 < i3)
      {
        int i4;
        switch (this.vertexCodes[i1])
        {
        default: 
          i4 = i2;
        }
        for (;;)
        {
          i1++;
          i2 = i4;
          break;
          this.inGeo.addVertex(this.vertices[i2][0], this.vertices[i2][1], i);
          i4 = i2 + 1;
          i = 0;
          continue;
          this.inGeo.addQuadraticVertex(this.vertices[(i2 + 0)][0], this.vertices[(i2 + 0)][1], 0.0F, this.vertices[(i2 + 1)][0], this.vertices[(i2 + 1)][1], 0.0F, this.fill, this.stroke, this.bezierDetail, i);
          i4 = i2 + 2;
          i = 0;
          continue;
          this.inGeo.addBezierVertex(this.vertices[(i2 + 0)][0], this.vertices[(i2 + 0)][1], 0.0F, this.vertices[(i2 + 1)][0], this.vertices[(i2 + 1)][1], 0.0F, this.vertices[(i2 + 2)][0], this.vertices[(i2 + 2)][1], 0.0F, this.fill, this.stroke, this.bezierDetail, i);
          i4 = i2 + 3;
          i = 0;
          continue;
          this.inGeo.addCurveVertex(this.vertices[i2][0], this.vertices[i2][1], 0.0F, this.fill, this.stroke, this.curveDetail, i);
          i4 = i2 + 1;
          i = 0;
          continue;
          i = 4;
          i4 = i2;
        }
      }
    }
    else
    {
      int j = 0;
      int k = 0;
      int m = this.vertexCodeCount;
      if (j < m)
      {
        int n;
        switch (this.vertexCodes[j])
        {
        default: 
          n = k;
        }
        for (;;)
        {
          j++;
          k = n;
          break;
          this.inGeo.addVertex(this.vertices[k][0], this.vertices[k][1], this.vertices[k][2], i);
          n = k + 1;
          i = 0;
          continue;
          this.inGeo.addQuadraticVertex(this.vertices[(k + 0)][0], this.vertices[(k + 0)][1], this.vertices[(k + 0)][2], this.vertices[(k + 1)][0], this.vertices[(k + 1)][1], this.vertices[(k + 0)][2], this.fill, this.stroke, this.bezierDetail, i);
          n = k + 2;
          i = 0;
          continue;
          this.inGeo.addBezierVertex(this.vertices[(k + 0)][0], this.vertices[(k + 0)][1], this.vertices[(k + 0)][2], this.vertices[(k + 1)][0], this.vertices[(k + 1)][1], this.vertices[(k + 1)][2], this.vertices[(k + 2)][0], this.vertices[(k + 2)][1], this.vertices[(k + 2)][2], this.fill, this.stroke, this.bezierDetail, i);
          n = k + 3;
          i = 0;
          continue;
          this.inGeo.addCurveVertex(this.vertices[k][0], this.vertices[k][1], this.vertices[k][2], this.fill, this.stroke, this.curveDetail, i);
          n = k + 1;
          i = 0;
          continue;
          i = 4;
          n = k;
        }
      }
    }
    if (this.stroke) {
      this.inGeo.addPolygonEdges(this.isClosed);
    }
    this.tessellator.tessellatePolygon(false, this.isClosed, true);
  }
  
  protected void tessellatePoint()
  {
    float f1;
    float f2;
    float f3;
    if (this.params.length == 2)
    {
      float f6 = this.params[0];
      float f7 = this.params[1];
      f1 = 0.0F;
      f2 = f7;
      f3 = f6;
    }
    for (;;)
    {
      this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
      this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
      PGraphicsOpenGL.InGeometry localInGeometry = this.inGeo;
      boolean bool1 = this.fill;
      boolean bool2 = this.stroke;
      localInGeometry.addPoint(f3, f2, f1, bool1, bool2);
      this.tessellator.tessellatePoints();
      return;
      if (this.params.length == 3)
      {
        float f4 = this.params[0];
        float f5 = this.params[1];
        f1 = this.params[2];
        f2 = f5;
        f3 = f4;
      }
      else
      {
        f1 = 0.0F;
        f2 = 0.0F;
        f3 = 0.0F;
      }
    }
  }
  
  protected void tessellateQuad()
  {
    float f9;
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;
    float f6;
    float f7;
    if (this.params.length == 8)
    {
      f9 = this.params[0];
      float f10 = this.params[1];
      float f11 = this.params[2];
      float f12 = this.params[3];
      float f13 = this.params[4];
      f1 = this.params[5];
      f2 = this.params[6];
      f3 = this.params[7];
      f4 = f13;
      f5 = f12;
      f6 = f11;
      f7 = f10;
    }
    for (float f8 = f9;; f8 = 0.0F)
    {
      this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
      this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
      PGraphicsOpenGL.InGeometry localInGeometry = this.inGeo;
      boolean bool1 = this.fill;
      boolean bool2 = this.stroke;
      localInGeometry.addQuad(f8, f7, 0.0F, f6, f5, 0.0F, f4, f1, 0.0F, f2, f3, 0.0F, bool1, bool2);
      this.tessellator.tessellateQuads();
      return;
      f1 = 0.0F;
      f2 = 0.0F;
      f3 = 0.0F;
      f4 = 0.0F;
      f5 = 0.0F;
      f6 = 0.0F;
      f7 = 0.0F;
    }
  }
  
  protected void tessellateRect()
  {
    float f1 = 0.0F;
    int i;
    float f2;
    float f3;
    float f4;
    float f5;
    float f6;
    float f7;
    float f8;
    if (this.params.length == 4)
    {
      float f20 = this.params[0];
      float f21 = this.params[1];
      float f22 = this.params[2];
      float f23 = this.params[3];
      i = 0;
      f2 = 0.0F;
      f3 = 0.0F;
      f4 = 0.0F;
      f5 = f23;
      f6 = f22;
      f7 = f21;
      f8 = f20;
    }
    for (;;)
    {
      this.rectMode = 0;
      this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
      this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
      if (i != 0)
      {
        PGraphicsOpenGL.InGeometry localInGeometry2 = this.inGeo;
        boolean bool3 = this.fill;
        boolean bool4 = this.stroke;
        int k = this.bezierDetail;
        int m = this.rectMode;
        localInGeometry2.addRect(f8, f7, f6, f5, f4, f3, f2, f1, bool3, bool4, k, m);
        this.tessellator.tessellatePolygon(false, true, true);
        return;
        if (this.params.length == 5)
        {
          float f16 = this.params[0];
          float f17 = this.params[1];
          float f18 = this.params[2];
          float f19 = this.params[3];
          f1 = this.params[4];
          i = 1;
          f2 = f1;
          f3 = f1;
          f4 = f1;
          f5 = f19;
          f6 = f18;
          f7 = f17;
          f8 = f16;
          continue;
        }
        if (this.params.length == 8)
        {
          float f9 = this.params[0];
          float f10 = this.params[1];
          float f11 = this.params[2];
          float f12 = this.params[3];
          float f13 = this.params[4];
          float f14 = this.params[5];
          float f15 = this.params[6];
          f1 = this.params[7];
          i = 1;
          f2 = f15;
          f3 = f14;
          f4 = f13;
          f5 = f12;
          f6 = f11;
          f7 = f10;
          f8 = f9;
        }
      }
      else
      {
        PGraphicsOpenGL.InGeometry localInGeometry1 = this.inGeo;
        boolean bool1 = this.fill;
        boolean bool2 = this.stroke;
        int j = this.rectMode;
        localInGeometry1.addRect(f8, f7, f6, f5, bool1, bool2, j);
        this.tessellator.tessellateQuads();
        return;
      }
      f1 = 0.0F;
      i = 0;
      f2 = 0.0F;
      f3 = 0.0F;
      f4 = 0.0F;
      f5 = 0.0F;
      f6 = 0.0F;
      f7 = 0.0F;
      f8 = 0.0F;
    }
  }
  
  protected void tessellateSphere()
  {
    int i = this.pg.sphereDetailU;
    int j = this.pg.sphereDetailV;
    if (this.params.length == 1) {}
    for (float f = this.params[0];; f = 0.0F)
    {
      this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
      PGraphicsOpenGL.InGeometry localInGeometry = this.inGeo;
      boolean bool1 = this.fill;
      boolean bool2 = this.stroke;
      int[] arrayOfInt = localInGeometry.addSphere(f, i, j, bool1, bool2);
      this.tessellator.tessellateTriangles(arrayOfInt);
      return;
    }
  }
  
  protected void tessellateTriangle()
  {
    float f7;
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;
    if (this.params.length == 6)
    {
      f7 = this.params[0];
      float f8 = this.params[1];
      float f9 = this.params[2];
      float f10 = this.params[3];
      float f11 = this.params[4];
      f1 = this.params[5];
      f2 = f11;
      f3 = f10;
      f4 = f9;
      f5 = f8;
    }
    for (float f6 = f7;; f6 = 0.0F)
    {
      this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
      this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
      PGraphicsOpenGL.InGeometry localInGeometry = this.inGeo;
      boolean bool1 = this.fill;
      boolean bool2 = this.stroke;
      localInGeometry.addTriangle(f6, f5, 0.0F, f4, f3, 0.0F, f2, f1, 0.0F, bool1, bool2);
      this.tessellator.tessellateTriangles();
      return;
      f1 = 0.0F;
      f2 = 0.0F;
      f3 = 0.0F;
      f4 = 0.0F;
      f5 = 0.0F;
    }
  }
  
  protected void transform(int paramInt, float... paramVarArgs)
  {
    int i = 2;
    if (paramInt == 1) {
      if (paramVarArgs.length != 1) {}
    }
    for (;;)
    {
      transformImpl(paramInt, i, paramVarArgs);
      return;
      i = 3;
      continue;
      if (paramInt == 3)
      {
        if (paramVarArgs.length != 6) {
          i = 3;
        }
      }
      else {
        i = paramVarArgs.length;
      }
    }
  }
  
  protected void transformImpl(int paramInt1, int paramInt2, float... paramVarArgs)
  {
    checkMatrix(paramInt2);
    calcTransform(paramInt1, paramInt2, paramVarArgs);
    if (this.tessellated) {
      applyMatrixImpl(this.transform);
    }
  }
  
  public void translate(float paramFloat1, float paramFloat2)
  {
    transform(0, new float[] { paramFloat1, paramFloat2 });
  }
  
  public void translate(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    transform(0, new float[] { paramFloat1, paramFloat2, paramFloat3 });
  }
  
  protected void updateGeometry()
  {
    this.root.initBuffers();
    if (this.root.modified) {
      this.root.updateGeometryImpl();
    }
  }
  
  protected void updateGeometryImpl()
  {
    if (this.modifiedPolyVertices)
    {
      int i9 = this.firstModifiedPolyVertex;
      copyPolyVertices(i9, 1 + (this.lastModifiedPolyVertex - i9));
      this.modifiedPolyVertices = false;
      this.firstModifiedPolyVertex = 2147483647;
      this.lastModifiedPolyVertex = -2147483648;
    }
    if (this.modifiedPolyColors)
    {
      int i8 = this.firstModifiedPolyColor;
      copyPolyColors(i8, 1 + (this.lastModifiedPolyColor - i8));
      this.modifiedPolyColors = false;
      this.firstModifiedPolyColor = 2147483647;
      this.lastModifiedPolyColor = -2147483648;
    }
    if (this.modifiedPolyNormals)
    {
      int i7 = this.firstModifiedPolyNormal;
      copyPolyNormals(i7, 1 + (this.lastModifiedPolyNormal - i7));
      this.modifiedPolyNormals = false;
      this.firstModifiedPolyNormal = 2147483647;
      this.lastModifiedPolyNormal = -2147483648;
    }
    if (this.modifiedPolyTexCoords)
    {
      int i6 = this.firstModifiedPolyTexcoord;
      copyPolyTexCoords(i6, 1 + (this.lastModifiedPolyTexcoord - i6));
      this.modifiedPolyTexCoords = false;
      this.firstModifiedPolyTexcoord = 2147483647;
      this.lastModifiedPolyTexcoord = -2147483648;
    }
    if (this.modifiedPolyAmbient)
    {
      int i5 = this.firstModifiedPolyAmbient;
      copyPolyAmbient(i5, 1 + (this.lastModifiedPolyAmbient - i5));
      this.modifiedPolyAmbient = false;
      this.firstModifiedPolyAmbient = 2147483647;
      this.lastModifiedPolyAmbient = -2147483648;
    }
    if (this.modifiedPolySpecular)
    {
      int i4 = this.firstModifiedPolySpecular;
      copyPolySpecular(i4, 1 + (this.lastModifiedPolySpecular - i4));
      this.modifiedPolySpecular = false;
      this.firstModifiedPolySpecular = 2147483647;
      this.lastModifiedPolySpecular = -2147483648;
    }
    if (this.modifiedPolyEmissive)
    {
      int i3 = this.firstModifiedPolyEmissive;
      copyPolyEmissive(i3, 1 + (this.lastModifiedPolyEmissive - i3));
      this.modifiedPolyEmissive = false;
      this.firstModifiedPolyEmissive = 2147483647;
      this.lastModifiedPolyEmissive = -2147483648;
    }
    if (this.modifiedPolyShininess)
    {
      int i2 = this.firstModifiedPolyShininess;
      copyPolyShininess(i2, 1 + (this.lastModifiedPolyShininess - i2));
      this.modifiedPolyShininess = false;
      this.firstModifiedPolyShininess = 2147483647;
      this.lastModifiedPolyShininess = -2147483648;
    }
    if (this.modifiedLineVertices)
    {
      int i1 = this.firstModifiedLineVertex;
      copyLineVertices(i1, 1 + (this.lastModifiedLineVertex - i1));
      this.modifiedLineVertices = false;
      this.firstModifiedLineVertex = 2147483647;
      this.lastModifiedLineVertex = -2147483648;
    }
    if (this.modifiedLineColors)
    {
      int n = this.firstModifiedLineColor;
      copyLineColors(n, 1 + (this.lastModifiedLineColor - n));
      this.modifiedLineColors = false;
      this.firstModifiedLineColor = 2147483647;
      this.lastModifiedLineColor = -2147483648;
    }
    if (this.modifiedLineAttributes)
    {
      int m = this.firstModifiedLineAttribute;
      copyLineAttributes(m, 1 + (this.lastModifiedLineAttribute - m));
      this.modifiedLineAttributes = false;
      this.firstModifiedLineAttribute = 2147483647;
      this.lastModifiedLineAttribute = -2147483648;
    }
    if (this.modifiedPointVertices)
    {
      int k = this.firstModifiedPointVertex;
      copyPointVertices(k, 1 + (this.lastModifiedPointVertex - k));
      this.modifiedPointVertices = false;
      this.firstModifiedPointVertex = 2147483647;
      this.lastModifiedPointVertex = -2147483648;
    }
    if (this.modifiedPointColors)
    {
      int j = this.firstModifiedPointColor;
      copyPointColors(j, 1 + (this.lastModifiedPointColor - j));
      this.modifiedPointColors = false;
      this.firstModifiedPointColor = 2147483647;
      this.lastModifiedPointColor = -2147483648;
    }
    if (this.modifiedPointAttributes)
    {
      int i = this.firstModifiedPointAttribute;
      copyPointAttributes(i, 1 + (this.lastModifiedPointAttribute - i));
      this.modifiedPointAttributes = false;
      this.firstModifiedPointAttribute = 2147483647;
      this.lastModifiedPointAttribute = -2147483648;
    }
    this.modified = false;
  }
  
  protected void updateLineIndexCache()
  {
    PGraphicsOpenGL.IndexCache localIndexCache = this.tessGeo.lineIndexCache;
    if (this.family == 0)
    {
      this.lastLineIndexCache = -1;
      this.firstLineIndexCache = -1;
      int i1 = 0;
      int i2 = -1;
      while (i1 < this.childCount)
      {
        PShapeOpenGL localPShapeOpenGL4 = (PShapeOpenGL)this.children[i1];
        int i3 = localPShapeOpenGL4.firstLineIndexCache;
        int i4;
        int i5;
        if (-1 < i3)
        {
          i4 = 1 + (localPShapeOpenGL4.lastLineIndexCache - i3);
          i5 = i3;
          label81:
          if (i5 >= i3 + i4) {
            break label174;
          }
          if (i2 != -1) {
            break label123;
          }
          i2 = localIndexCache.addNew(i5);
          this.firstLineIndexCache = i2;
        }
        for (;;)
        {
          i5++;
          break label81;
          i4 = -1;
          break;
          label123:
          if (localIndexCache.vertexOffset[i2] == localIndexCache.vertexOffset[i5]) {
            localIndexCache.incCounts(i2, localIndexCache.indexCount[i5], localIndexCache.vertexCount[i5]);
          } else {
            i2 = localIndexCache.addNew(i5);
          }
        }
        label174:
        if (-1 < localPShapeOpenGL4.firstLineVertex)
        {
          if (this.firstLineVertex == -1) {
            this.firstLineVertex = 2147483647;
          }
          this.firstLineVertex = PApplet.min(this.firstLineVertex, localPShapeOpenGL4.firstLineVertex);
        }
        if (-1 < localPShapeOpenGL4.lastLineVertex) {
          this.lastLineVertex = PApplet.max(this.lastLineVertex, localPShapeOpenGL4.lastLineVertex);
        }
        i1++;
      }
      this.lastLineIndexCache = i2;
      return;
    }
    int i = localIndexCache.vertexOffset[this.firstLineIndexCache];
    this.lastLineVertex = i;
    this.firstLineVertex = i;
    int j = this.firstLineIndexCache;
    if (j <= this.lastLineIndexCache)
    {
      int k = localIndexCache.indexOffset[j];
      int m = localIndexCache.indexCount[j];
      int n = localIndexCache.vertexCount[j];
      if (32768 <= n + this.root.lineVertexRel)
      {
        this.root.lineVertexRel = 0;
        this.root.lineVertexOffset = this.root.lineVertexAbs;
        localIndexCache.indexOffset[j] = this.root.lineIndexOffset;
      }
      for (;;)
      {
        localIndexCache.vertexOffset[j] = this.root.lineVertexOffset;
        PShapeOpenGL localPShapeOpenGL1 = this.root;
        localPShapeOpenGL1.lineIndexOffset = (m + localPShapeOpenGL1.lineIndexOffset);
        PShapeOpenGL localPShapeOpenGL2 = this.root;
        localPShapeOpenGL2.lineVertexAbs = (n + localPShapeOpenGL2.lineVertexAbs);
        PShapeOpenGL localPShapeOpenGL3 = this.root;
        localPShapeOpenGL3.lineVertexRel = (n + localPShapeOpenGL3.lineVertexRel);
        this.lastLineVertex = (n + this.lastLineVertex);
        j++;
        break;
        this.tessGeo.incLineIndices(k, -1 + (k + m), this.root.lineVertexRel);
      }
    }
    this.lastLineVertex = (-1 + this.lastLineVertex);
  }
  
  protected void updatePointIndexCache()
  {
    PGraphicsOpenGL.IndexCache localIndexCache = this.tessGeo.pointIndexCache;
    if (this.family == 0)
    {
      this.lastPointIndexCache = -1;
      this.firstPointIndexCache = -1;
      int i1 = 0;
      int i2 = -1;
      while (i1 < this.childCount)
      {
        PShapeOpenGL localPShapeOpenGL4 = (PShapeOpenGL)this.children[i1];
        int i3 = localPShapeOpenGL4.firstPointIndexCache;
        int i4;
        int i5;
        if (-1 < i3)
        {
          i4 = 1 + (localPShapeOpenGL4.lastPointIndexCache - i3);
          i5 = i3;
          label81:
          if (i5 >= i3 + i4) {
            break label174;
          }
          if (i2 != -1) {
            break label123;
          }
          i2 = localIndexCache.addNew(i5);
          this.firstPointIndexCache = i2;
        }
        for (;;)
        {
          i5++;
          break label81;
          i4 = -1;
          break;
          label123:
          if (localIndexCache.vertexOffset[i2] == localIndexCache.vertexOffset[i5]) {
            localIndexCache.incCounts(i2, localIndexCache.indexCount[i5], localIndexCache.vertexCount[i5]);
          } else {
            i2 = localIndexCache.addNew(i5);
          }
        }
        label174:
        if (-1 < localPShapeOpenGL4.firstPointVertex)
        {
          if (this.firstPointVertex == -1) {
            this.firstPointVertex = 2147483647;
          }
          this.firstPointVertex = PApplet.min(this.firstPointVertex, localPShapeOpenGL4.firstPointVertex);
        }
        if (-1 < localPShapeOpenGL4.lastPointVertex) {
          this.lastPointVertex = PApplet.max(this.lastPointVertex, localPShapeOpenGL4.lastPointVertex);
        }
        i1++;
      }
      this.lastPointIndexCache = i2;
      return;
    }
    int i = localIndexCache.vertexOffset[this.firstPointIndexCache];
    this.lastPointVertex = i;
    this.firstPointVertex = i;
    int j = this.firstPointIndexCache;
    if (j <= this.lastPointIndexCache)
    {
      int k = localIndexCache.indexOffset[j];
      int m = localIndexCache.indexCount[j];
      int n = localIndexCache.vertexCount[j];
      if (32768 <= n + this.root.pointVertexRel)
      {
        this.root.pointVertexRel = 0;
        this.root.pointVertexOffset = this.root.pointVertexAbs;
        localIndexCache.indexOffset[j] = this.root.pointIndexOffset;
      }
      for (;;)
      {
        localIndexCache.vertexOffset[j] = this.root.pointVertexOffset;
        PShapeOpenGL localPShapeOpenGL1 = this.root;
        localPShapeOpenGL1.pointIndexOffset = (m + localPShapeOpenGL1.pointIndexOffset);
        PShapeOpenGL localPShapeOpenGL2 = this.root;
        localPShapeOpenGL2.pointVertexAbs = (n + localPShapeOpenGL2.pointVertexAbs);
        PShapeOpenGL localPShapeOpenGL3 = this.root;
        localPShapeOpenGL3.pointVertexRel = (n + localPShapeOpenGL3.pointVertexRel);
        this.lastPointVertex = (n + this.lastPointVertex);
        j++;
        break;
        this.tessGeo.incPointIndices(k, -1 + (k + m), this.root.pointVertexRel);
      }
    }
    this.lastPointVertex = (-1 + this.lastPointVertex);
  }
  
  protected void updatePolyIndexCache()
  {
    PGraphicsOpenGL.IndexCache localIndexCache = this.tessGeo.polyIndexCache;
    if (this.family == 0)
    {
      this.lastPolyIndexCache = -1;
      this.firstPolyIndexCache = -1;
      int i1 = 0;
      int i2 = -1;
      while (i1 < this.childCount)
      {
        PShapeOpenGL localPShapeOpenGL4 = (PShapeOpenGL)this.children[i1];
        int i3 = localPShapeOpenGL4.firstPolyIndexCache;
        int i4;
        int i5;
        if (-1 < i3)
        {
          i4 = 1 + (localPShapeOpenGL4.lastPolyIndexCache - i3);
          i5 = i3;
          label81:
          if (i5 >= i3 + i4) {
            break label174;
          }
          if (i2 != -1) {
            break label123;
          }
          i2 = localIndexCache.addNew(i5);
          this.firstPolyIndexCache = i2;
        }
        for (;;)
        {
          i5++;
          break label81;
          i4 = -1;
          break;
          label123:
          if (localIndexCache.vertexOffset[i2] == localIndexCache.vertexOffset[i5]) {
            localIndexCache.incCounts(i2, localIndexCache.indexCount[i5], localIndexCache.vertexCount[i5]);
          } else {
            i2 = localIndexCache.addNew(i5);
          }
        }
        label174:
        if (-1 < localPShapeOpenGL4.firstPolyVertex)
        {
          if (this.firstPolyVertex == -1) {
            this.firstPolyVertex = 2147483647;
          }
          this.firstPolyVertex = PApplet.min(this.firstPolyVertex, localPShapeOpenGL4.firstPolyVertex);
        }
        if (-1 < localPShapeOpenGL4.lastPolyVertex) {
          this.lastPolyVertex = PApplet.max(this.lastPolyVertex, localPShapeOpenGL4.lastPolyVertex);
        }
        i1++;
      }
      this.lastPolyIndexCache = i2;
    }
    do
    {
      return;
      int i = localIndexCache.vertexOffset[this.firstPolyIndexCache];
      this.lastPolyVertex = i;
      this.firstPolyVertex = i;
      int j = this.firstPolyIndexCache;
      if (j <= this.lastPolyIndexCache)
      {
        int k = localIndexCache.indexOffset[j];
        int m = localIndexCache.indexCount[j];
        int n = localIndexCache.vertexCount[j];
        if ((32768 <= n + this.root.polyVertexRel) || ((is2D()) && (startStrokedTex(j))))
        {
          this.root.polyVertexRel = 0;
          this.root.polyVertexOffset = this.root.polyVertexAbs;
          localIndexCache.indexOffset[j] = this.root.polyIndexOffset;
        }
        for (;;)
        {
          localIndexCache.vertexOffset[j] = this.root.polyVertexOffset;
          if (is2D()) {
            setFirstStrokeVertex(j, this.lastPolyVertex);
          }
          PShapeOpenGL localPShapeOpenGL1 = this.root;
          localPShapeOpenGL1.polyIndexOffset = (m + localPShapeOpenGL1.polyIndexOffset);
          PShapeOpenGL localPShapeOpenGL2 = this.root;
          localPShapeOpenGL2.polyVertexAbs = (n + localPShapeOpenGL2.polyVertexAbs);
          PShapeOpenGL localPShapeOpenGL3 = this.root;
          localPShapeOpenGL3.polyVertexRel = (n + localPShapeOpenGL3.polyVertexRel);
          this.lastPolyVertex = (n + this.lastPolyVertex);
          j++;
          break;
          this.tessGeo.incPolyIndices(k, -1 + (k + m), this.root.polyVertexRel);
        }
      }
      this.lastPolyVertex = (-1 + this.lastPolyVertex);
    } while (!is2D());
    setLastStrokeVertex(this.lastPolyVertex);
  }
  
  protected void updateRoot(PShape paramPShape)
  {
    this.root = ((PShapeOpenGL)paramPShape);
    if (this.family == 0) {
      for (int i = 0; i < this.childCount; i++) {
        ((PShapeOpenGL)this.children[i]).updateRoot(paramPShape);
      }
    }
  }
  
  protected void updateTessellation()
  {
    if ((!this.root.tessellated) || (this.root.contextIsOutdated()))
    {
      this.root.tessellate();
      this.root.aggregate();
    }
  }
  
  public void vertex(float paramFloat1, float paramFloat2)
  {
    vertexImpl(paramFloat1, paramFloat2, 0.0F, 0.0F, 0.0F);
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    vertexImpl(paramFloat1, paramFloat2, paramFloat3, 0.0F, 0.0F);
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    vertexImpl(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4);
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    vertexImpl(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
  }
  
  protected int vertexCode()
  {
    if (this.breakShape)
    {
      this.breakShape = false;
      return 4;
    }
    return 0;
  }
  
  protected void vertexImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    if (!this.openShape)
    {
      PGraphics.showWarning("%1$s can only be called between beginShape() and endShape()", new Object[] { "vertex()" });
      return;
    }
    if (this.family == 0)
    {
      PGraphics.showWarning("Cannot add vertices to GROUP shape");
      return;
    }
    int i;
    int j;
    label74:
    float f4;
    float f1;
    if (this.image != null)
    {
      i = 1;
      if (!this.fill)
      {
        j = 0;
        if (i == 0) {}
      }
      else
      {
        if (i != 0) {
          break label220;
        }
        j = this.fillColor;
      }
      if ((this.image == null) || (this.textureMode != 2)) {
        break label242;
      }
      f4 = PApplet.min(1.0F, paramFloat4 / this.image.width);
      f1 = PApplet.min(1.0F, paramFloat5 / this.image.height);
    }
    for (float f2 = f4;; f2 = paramFloat4)
    {
      boolean bool = this.stroke;
      int k = 0;
      float f3 = 0.0F;
      if (bool)
      {
        k = this.strokeColor;
        f3 = this.strokeWeight;
      }
      this.inGeo.addVertex(paramFloat1, paramFloat2, paramFloat3, j, this.normalX, this.normalY, this.normalZ, f2, f1, k, f3, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess, vertexCode());
      markForTessellation();
      return;
      i = 0;
      break;
      label220:
      if (this.tint)
      {
        j = this.tintColor;
        break label74;
      }
      j = -1;
      break label74;
      label242:
      f1 = paramFloat5;
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.PShapeOpenGL
 * JD-Core Version:    0.7.0.1
 */