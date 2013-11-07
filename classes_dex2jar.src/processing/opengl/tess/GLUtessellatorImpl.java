package processing.opengl.tess;

public class GLUtessellatorImpl
  implements PGLUtessellator
{
  private static final double GLU_TESS_DEFAULT_TOLERANCE = 0.0D;
  private static PGLUtessellatorCallback NULL_CB;
  public static final int TESS_MAX_CACHE = 100;
  boolean avoidDegenerateTris;
  boolean boundaryOnly;
  CachedVertex[] cache = new CachedVertex[100];
  int cacheCount;
  private PGLUtessellatorCallback callBegin;
  private PGLUtessellatorCallback callBeginData;
  private PGLUtessellatorCallback callCombine;
  private PGLUtessellatorCallback callCombineData;
  private PGLUtessellatorCallback callEdgeFlag;
  private PGLUtessellatorCallback callEdgeFlagData;
  private PGLUtessellatorCallback callEnd;
  private PGLUtessellatorCallback callEndData;
  private PGLUtessellatorCallback callError;
  private PGLUtessellatorCallback callErrorData;
  private PGLUtessellatorCallback callVertex;
  private PGLUtessellatorCallback callVertexData;
  Dict dict;
  GLUvertex event;
  boolean fatalError;
  boolean flagBoundary;
  private boolean flushCacheOnNextVertex;
  private GLUhalfEdge lastEdge;
  GLUface lonelyTriList;
  GLUmesh mesh;
  double[] normal = new double[3];
  private Object polygonData;
  PriorityQ pq;
  private double relTolerance;
  double[] sUnit = new double[3];
  private int state = 0;
  double[] tUnit = new double[3];
  int windingRule;
  
  static
  {
    if (!GLUtessellatorImpl.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      NULL_CB = new PGLUtessellatorCallbackAdapter();
      return;
    }
  }
  
  private GLUtessellatorImpl()
  {
    this.normal[0] = 0.0D;
    this.normal[1] = 0.0D;
    this.normal[2] = 0.0D;
    this.relTolerance = 0.0D;
    this.windingRule = 100130;
    this.flagBoundary = false;
    this.boundaryOnly = false;
    this.callBegin = NULL_CB;
    this.callEdgeFlag = NULL_CB;
    this.callVertex = NULL_CB;
    this.callEnd = NULL_CB;
    this.callError = NULL_CB;
    this.callCombine = NULL_CB;
    this.callBeginData = NULL_CB;
    this.callEdgeFlagData = NULL_CB;
    this.callVertexData = NULL_CB;
    this.callEndData = NULL_CB;
    this.callErrorData = NULL_CB;
    this.callCombineData = NULL_CB;
    this.polygonData = null;
    for (int i = 0;; i++)
    {
      if (i >= this.cache.length) {
        return;
      }
      this.cache[i] = new CachedVertex();
    }
  }
  
  private boolean addVertex(double[] paramArrayOfDouble, Object paramObject)
  {
    GLUhalfEdge localGLUhalfEdge1 = this.lastEdge;
    GLUhalfEdge localGLUhalfEdge2;
    if (localGLUhalfEdge1 == null)
    {
      localGLUhalfEdge2 = Mesh.__gl_meshMakeEdge(this.mesh);
      if (localGLUhalfEdge2 == null) {}
      while (!Mesh.__gl_meshSplice(localGLUhalfEdge2, localGLUhalfEdge2.Sym)) {
        return false;
      }
    }
    for (;;)
    {
      localGLUhalfEdge2.Org.data = paramObject;
      localGLUhalfEdge2.Org.coords[0] = paramArrayOfDouble[0];
      localGLUhalfEdge2.Org.coords[1] = paramArrayOfDouble[1];
      localGLUhalfEdge2.Org.coords[2] = paramArrayOfDouble[2];
      localGLUhalfEdge2.winding = 1;
      localGLUhalfEdge2.Sym.winding = -1;
      this.lastEdge = localGLUhalfEdge2;
      return true;
      if (Mesh.__gl_meshSplitEdge(localGLUhalfEdge1) == null) {
        break;
      }
      localGLUhalfEdge2 = localGLUhalfEdge1.Lnext;
    }
  }
  
  private void cacheVertex(double[] paramArrayOfDouble, Object paramObject)
  {
    if (this.cache[this.cacheCount] == null) {
      this.cache[this.cacheCount] = new CachedVertex();
    }
    CachedVertex localCachedVertex = this.cache[this.cacheCount];
    localCachedVertex.data = paramObject;
    localCachedVertex.coords[0] = paramArrayOfDouble[0];
    localCachedVertex.coords[1] = paramArrayOfDouble[1];
    localCachedVertex.coords[2] = paramArrayOfDouble[2];
    this.cacheCount = (1 + this.cacheCount);
  }
  
  private boolean flushCache()
  {
    CachedVertex[] arrayOfCachedVertex = this.cache;
    this.mesh = Mesh.__gl_meshNewMesh();
    if (this.mesh == null) {
      return false;
    }
    for (int i = 0;; i++)
    {
      if (i >= this.cacheCount)
      {
        this.cacheCount = 0;
        this.flushCacheOnNextVertex = false;
        return true;
      }
      CachedVertex localCachedVertex = arrayOfCachedVertex[i];
      if (!addVertex(localCachedVertex.coords, localCachedVertex.data)) {
        break;
      }
    }
  }
  
  public static PGLUtessellator gluNewTess()
  {
    return new GLUtessellatorImpl();
  }
  
  private void gotoState(int paramInt)
  {
    for (;;)
    {
      if (this.state == paramInt) {
        return;
      }
      if (this.state < paramInt)
      {
        if (this.state == 0)
        {
          callErrorOrErrorData(100151);
          gluTessBeginPolygon(null);
        }
        else if (this.state == 1)
        {
          callErrorOrErrorData(100152);
          gluTessBeginContour();
        }
      }
      else if (this.state == 2)
      {
        callErrorOrErrorData(100154);
        gluTessEndContour();
      }
      else if (this.state == 1)
      {
        callErrorOrErrorData(100153);
        makeDormant();
      }
    }
  }
  
  private void makeDormant()
  {
    if (this.mesh != null) {
      Mesh.__gl_meshDeleteMesh(this.mesh);
    }
    this.state = 0;
    this.lastEdge = null;
    this.mesh = null;
  }
  
  private void requireState(int paramInt)
  {
    if (this.state != paramInt) {
      gotoState(paramInt);
    }
  }
  
  void callBeginOrBeginData(int paramInt)
  {
    if (this.callBeginData != NULL_CB)
    {
      this.callBeginData.beginData(paramInt, this.polygonData);
      return;
    }
    this.callBegin.begin(paramInt);
  }
  
  void callCombineOrCombineData(double[] paramArrayOfDouble, Object[] paramArrayOfObject1, float[] paramArrayOfFloat, Object[] paramArrayOfObject2)
  {
    if (this.callCombineData != NULL_CB)
    {
      this.callCombineData.combineData(paramArrayOfDouble, paramArrayOfObject1, paramArrayOfFloat, paramArrayOfObject2, this.polygonData);
      return;
    }
    this.callCombine.combine(paramArrayOfDouble, paramArrayOfObject1, paramArrayOfFloat, paramArrayOfObject2);
  }
  
  void callEdgeFlagOrEdgeFlagData(boolean paramBoolean)
  {
    if (this.callEdgeFlagData != NULL_CB)
    {
      this.callEdgeFlagData.edgeFlagData(paramBoolean, this.polygonData);
      return;
    }
    this.callEdgeFlag.edgeFlag(paramBoolean);
  }
  
  void callEndOrEndData()
  {
    if (this.callEndData != NULL_CB)
    {
      this.callEndData.endData(this.polygonData);
      return;
    }
    this.callEnd.end();
  }
  
  void callErrorOrErrorData(int paramInt)
  {
    if (this.callErrorData != NULL_CB)
    {
      this.callErrorData.errorData(paramInt, this.polygonData);
      return;
    }
    this.callError.error(paramInt);
  }
  
  void callVertexOrVertexData(Object paramObject)
  {
    if (this.callVertexData != NULL_CB)
    {
      this.callVertexData.vertexData(paramObject, this.polygonData);
      return;
    }
    this.callVertex.vertex(paramObject);
  }
  
  public void gluBeginPolygon()
  {
    gluTessBeginPolygon(null);
    gluTessBeginContour();
  }
  
  public void gluDeleteTess()
  {
    requireState(0);
  }
  
  public void gluEndPolygon()
  {
    gluTessEndContour();
    gluTessEndPolygon();
  }
  
  public void gluGetTessProperty(int paramInt1, double[] paramArrayOfDouble, int paramInt2)
  {
    int i = 1;
    switch (paramInt1)
    {
    default: 
      paramArrayOfDouble[paramInt2] = 0.0D;
      callErrorOrErrorData(100900);
      return;
    case 100142: 
      assert ((0.0D <= this.relTolerance) && (this.relTolerance <= 1.0D));
      paramArrayOfDouble[paramInt2] = this.relTolerance;
      return;
    case 100140: 
      assert ((this.windingRule == 100130) || (this.windingRule == 100131) || (this.windingRule == 100132) || (this.windingRule == 100133) || (this.windingRule == 100134));
      paramArrayOfDouble[paramInt2] = this.windingRule;
      return;
    case 100141: 
      assert ((this.boundaryOnly) || (!this.boundaryOnly));
      if (this.boundaryOnly) {}
      for (;;)
      {
        paramArrayOfDouble[paramInt2] = i;
        return;
        i = 0;
      }
    }
    if (this.avoidDegenerateTris) {}
    for (;;)
    {
      paramArrayOfDouble[paramInt2] = i;
      return;
      i = 0;
    }
  }
  
  public void gluNextContour(int paramInt)
  {
    gluTessEndContour();
    gluTessBeginContour();
  }
  
  public void gluTessBeginContour()
  {
    requireState(1);
    this.state = 2;
    this.lastEdge = null;
    if (this.cacheCount > 0) {
      this.flushCacheOnNextVertex = true;
    }
  }
  
  public void gluTessBeginPolygon(Object paramObject)
  {
    requireState(0);
    this.state = 1;
    this.cacheCount = 0;
    this.flushCacheOnNextVertex = false;
    this.mesh = null;
    this.polygonData = paramObject;
  }
  
  public void gluTessCallback(int paramInt, PGLUtessellatorCallback paramPGLUtessellatorCallback)
  {
    boolean bool1 = true;
    switch (paramInt)
    {
    default: 
      callErrorOrErrorData(100900);
      return;
    case 100100: 
      if (paramPGLUtessellatorCallback == null) {
        paramPGLUtessellatorCallback = NULL_CB;
      }
      this.callBegin = paramPGLUtessellatorCallback;
      return;
    case 100106: 
      if (paramPGLUtessellatorCallback == null) {
        paramPGLUtessellatorCallback = NULL_CB;
      }
      this.callBeginData = paramPGLUtessellatorCallback;
      return;
    case 100104: 
      PGLUtessellatorCallback localPGLUtessellatorCallback2;
      if (paramPGLUtessellatorCallback == null)
      {
        localPGLUtessellatorCallback2 = NULL_CB;
        this.callEdgeFlag = localPGLUtessellatorCallback2;
        if (paramPGLUtessellatorCallback == null) {
          break label135;
        }
      }
      for (boolean bool2 = bool1;; bool2 = false)
      {
        this.flagBoundary = bool2;
        return;
        localPGLUtessellatorCallback2 = paramPGLUtessellatorCallback;
        break;
      }
    case 100110: 
      PGLUtessellatorCallback localPGLUtessellatorCallback1;
      if (paramPGLUtessellatorCallback == null)
      {
        localPGLUtessellatorCallback1 = NULL_CB;
        this.callBegin = localPGLUtessellatorCallback1;
        this.callEdgeFlagData = localPGLUtessellatorCallback1;
        if (paramPGLUtessellatorCallback == null) {
          break label178;
        }
      }
      for (;;)
      {
        this.flagBoundary = bool1;
        return;
        localPGLUtessellatorCallback1 = paramPGLUtessellatorCallback;
        break;
        bool1 = false;
      }
    case 100101: 
      if (paramPGLUtessellatorCallback == null) {
        paramPGLUtessellatorCallback = NULL_CB;
      }
      this.callVertex = paramPGLUtessellatorCallback;
      return;
    case 100107: 
      if (paramPGLUtessellatorCallback == null) {
        paramPGLUtessellatorCallback = NULL_CB;
      }
      this.callVertexData = paramPGLUtessellatorCallback;
      return;
    case 100102: 
      if (paramPGLUtessellatorCallback == null) {
        paramPGLUtessellatorCallback = NULL_CB;
      }
      this.callEnd = paramPGLUtessellatorCallback;
      return;
    case 100108: 
      if (paramPGLUtessellatorCallback == null) {
        paramPGLUtessellatorCallback = NULL_CB;
      }
      this.callEndData = paramPGLUtessellatorCallback;
      return;
    case 100103: 
      if (paramPGLUtessellatorCallback == null) {
        paramPGLUtessellatorCallback = NULL_CB;
      }
      this.callError = paramPGLUtessellatorCallback;
      return;
    case 100109: 
      if (paramPGLUtessellatorCallback == null) {
        paramPGLUtessellatorCallback = NULL_CB;
      }
      this.callErrorData = paramPGLUtessellatorCallback;
      return;
    case 100105: 
      label135:
      label178:
      if (paramPGLUtessellatorCallback == null) {
        paramPGLUtessellatorCallback = NULL_CB;
      }
      this.callCombine = paramPGLUtessellatorCallback;
      return;
    }
    if (paramPGLUtessellatorCallback == null) {
      paramPGLUtessellatorCallback = NULL_CB;
    }
    this.callCombineData = paramPGLUtessellatorCallback;
  }
  
  public void gluTessEndContour()
  {
    requireState(2);
    this.state = 1;
  }
  
  public void gluTessEndPolygon()
  {
    try
    {
      requireState(1);
      this.state = 0;
      if (this.mesh == null)
      {
        if ((!this.flagBoundary) && (Render.__gl_renderCache(this)))
        {
          this.polygonData = null;
          return;
        }
        if (!flushCache()) {
          throw new RuntimeException();
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      callErrorOrErrorData(100902);
      return;
    }
    Normal.__gl_projectPolygon(this);
    if (!Sweep.__gl_computeInterior(this)) {
      throw new RuntimeException();
    }
    GLUmesh localGLUmesh = this.mesh;
    if (!this.fatalError)
    {
      if (this.boundaryOnly) {}
      for (boolean bool = TessMono.__gl_meshSetWindingNumber(localGLUmesh, 1, true); !bool; bool = TessMono.__gl_meshTessellateInterior(localGLUmesh, this.avoidDegenerateTris)) {
        throw new RuntimeException();
      }
      Mesh.__gl_meshCheckMesh(localGLUmesh);
      if ((this.callBegin != NULL_CB) || (this.callEnd != NULL_CB) || (this.callVertex != NULL_CB) || (this.callEdgeFlag != NULL_CB) || (this.callBeginData != NULL_CB) || (this.callEndData != NULL_CB) || (this.callVertexData != NULL_CB) || (this.callEdgeFlagData != NULL_CB))
      {
        if (!this.boundaryOnly) {
          break label240;
        }
        Render.__gl_renderBoundary(this, localGLUmesh);
      }
    }
    for (;;)
    {
      Mesh.__gl_meshDeleteMesh(localGLUmesh);
      this.polygonData = null;
      return;
      label240:
      Render.__gl_renderMesh(this, localGLUmesh);
    }
  }
  
  public void gluTessNormal(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.normal[0] = paramDouble1;
    this.normal[1] = paramDouble2;
    this.normal[2] = paramDouble3;
  }
  
  public void gluTessProperty(int paramInt, double paramDouble)
  {
    boolean bool = true;
    int i;
    switch (paramInt)
    {
    default: 
      callErrorOrErrorData(100900);
      return;
    case 100142: 
      if ((paramDouble >= 0.0D) && (paramDouble <= 1.0D)) {
        break;
      }
    case 100140: 
      do
      {
        callErrorOrErrorData(100901);
        return;
        this.relTolerance = paramDouble;
        return;
        i = (int)paramDouble;
      } while (i != paramDouble);
      switch (i)
      {
      }
    case 100141: 
      if (paramDouble != 0.0D) {}
      for (;;)
      {
        this.boundaryOnly = bool;
        return;
        this.windingRule = i;
        return;
        bool = false;
      }
    }
    if (paramDouble != 0.0D) {}
    for (;;)
    {
      this.avoidDegenerateTris = bool;
      return;
      bool = false;
    }
  }
  
  public void gluTessVertex(double[] paramArrayOfDouble, int paramInt, Object paramObject)
  {
    int i = 0;
    double[] arrayOfDouble = new double[3];
    requireState(2);
    if (this.flushCacheOnNextVertex) {
      if (!flushCache()) {
        callErrorOrErrorData(100902);
      }
    }
    label155:
    do
    {
      return;
      this.lastEdge = null;
      for (int j = 0;; j++)
      {
        if (j >= 3)
        {
          if (i != 0) {
            callErrorOrErrorData(100155);
          }
          if (this.mesh != null) {
            break label155;
          }
          if (this.cacheCount >= 100) {
            break;
          }
          cacheVertex(arrayOfDouble, paramObject);
          return;
        }
        double d = paramArrayOfDouble[(j + paramInt)];
        if (d < -1.0E+150D)
        {
          d = -1.0E+150D;
          i = 1;
        }
        if (d > 1.0E+150D)
        {
          d = 1.0E+150D;
          i = 1;
        }
        arrayOfDouble[j] = d;
      }
      if (!flushCache())
      {
        callErrorOrErrorData(100902);
        return;
      }
    } while (addVertex(arrayOfDouble, paramObject));
    callErrorOrErrorData(100902);
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.GLUtessellatorImpl
 * JD-Core Version:    0.7.0.1
 */