package processing.opengl;

import java.lang.reflect.Array;
import java.net.URL;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.WeakHashMap;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PFont.Glyph;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PMatrix;
import processing.core.PMatrix2D;
import processing.core.PMatrix3D;
import processing.core.PShape;
import processing.core.PVector;

public class PGraphicsOpenGL
  extends PGraphics
{
  static final String ALREADY_BEGAN_CONTOUR_ERROR = "Already called beginContour()";
  static final String ALREADY_DRAWING_ERROR = "Already called beginDraw()";
  static final String BLEND_DRIVER_ERROR = "blendMode(%1$s) is not supported by this hardware (or driver)";
  static final String BLEND_RENDERER_ERROR = "blendMode(%1$s) is not supported by this renderer";
  protected static final int EDGE_MIDDLE = 0;
  protected static final int EDGE_SINGLE = 3;
  protected static final int EDGE_START = 1;
  protected static final int EDGE_STOP = 2;
  protected static final int FB_STACK_DEPTH = 16;
  protected static final int FLUSH_CONTINUOUSLY = 0;
  protected static final int FLUSH_WHEN_FULL = 1;
  public static String GLSL_VERSION;
  protected static final int IMMEDIATE = 0;
  protected static final int INIT_INDEX_BUFFER_SIZE = 512;
  protected static final int INIT_VERTEX_BUFFER_SIZE = 256;
  static final String INVALID_FILTER_SHADER_ERROR = "Object is not a valid shader to use as filter";
  static final String INVALID_PROCESSING_SHADER_ERROR = "The GLSL code doesn't seem to contain a valid shader to use in Processing";
  protected static final int MATRIX_STACK_DEPTH = 32;
  protected static final int MIN_POINT_ACCURACY = 20;
  static final String NESTED_DRAW_ERROR = "Already called drawing on another PGraphicsOpenGL object";
  static final String NO_BEGIN_CONTOUR_ERROR = "Need to call beginContour() first";
  static final String NO_BEGIN_DRAW_ERROR = "Cannot call endDraw() before beginDraw()";
  static final String NO_COLOR_SHADER_ERROR = "Your shader cannot be used to render colored geometry, using default shader instead.";
  static final String NO_LIGHT_SHADER_ERROR = "Your shader cannot be used to render lit geometry, using default shader instead.";
  static final String NO_TEXLIGHT_SHADER_ERROR = "Your shader cannot be used to render textured and lit geometry, using default shader instead.";
  static final String NO_TEXTURE_SHADER_ERROR = "Your shader cannot be used to render textured geometry, using default shader instead.";
  public static String OPENGL_EXTENSIONS;
  public static String OPENGL_RENDERER;
  static final String OPENGL_THREAD_ERROR = "Cannot run the OpenGL renderer outside the main thread, change your code\nso the drawing calls are all inside the main thread, \nor use the default renderer instead.";
  public static String OPENGL_VENDOR;
  public static String OPENGL_VERSION;
  protected static final int OP_NONE = 0;
  protected static final int OP_READ = 1;
  protected static final int OP_WRITE = 2;
  protected static final float POINT_ACCURACY_FACTOR = 10.0F;
  protected static final int RETAINED = 1;
  static final String TESSELLATION_ERROR = "Tessellation Error: %1$s";
  static final String TOO_LONG_STROKE_PATH_ERROR = "Stroke path is too long, some bevel triangles won't be added";
  static final String TOO_MANY_SMOOTH_CALLS_ERROR = "The smooth/noSmooth functions are being called too often.\nThis results in screen flickering, so they will be disabled\nfor the rest of the sketch's execution";
  static final String UNKNOWN_SHADER_KIND_ERROR = "Unknown shader kind";
  static final String UNSUPPORTED_SHAPE_FORMAT_ERROR = "Unsupported shape format";
  static final String UNSUPPORTED_SMOOTH_ERROR = "Smooth is not supported by this hardware (or driver)";
  static final String UNSUPPORTED_SMOOTH_LEVEL_ERROR = "Smooth level %1$s is not available. Using %2$s instead";
  static final String WRONG_SHADER_TYPE_ERROR = "shader() called with a wrong shader";
  public static boolean anisoSamplingSupported;
  public static boolean autoMipmapGenSupported;
  public static boolean blendEqSupported;
  protected static FrameBuffer currentFramebuffer;
  protected static ColorShader defColorShader;
  protected static URL defColorShaderFragURL;
  protected static URL defColorShaderVertURL;
  protected static LightShader defLightShader;
  protected static URL defLightShaderVertURL;
  protected static LineShader defLineShader;
  protected static URL defLineShaderFragURL;
  protected static URL defLineShaderVertURL;
  protected static PointShader defPointShader;
  protected static URL defPointShaderFragURL;
  protected static URL defPointShaderVertURL;
  protected static TexlightShader defTexlightShader;
  protected static URL defTexlightShaderVertURL;
  protected static TexureShader defTextureShader;
  protected static URL defTextureShaderFragURL;
  protected static URL defTextureShaderVertURL;
  public static int depthBits;
  protected static FrameBuffer drawFramebuffer;
  protected static FrameBuffer[] fbStack = new FrameBuffer[16];
  protected static int fbStackDepth;
  public static boolean fboMultisampleSupported;
  protected static HashMap<GLResource, Boolean> glFrameBuffers;
  protected static boolean glParamsRead;
  protected static HashMap<GLResource, Boolean> glRenderBuffers;
  protected static HashMap<GLResource, Boolean> glTextureObjects;
  protected static HashMap<GLResource, Boolean> glVertexBuffers;
  protected static HashMap<GLResource, Boolean> glslFragmentShaders;
  protected static HashMap<GLResource, Boolean> glslPrograms;
  protected static HashMap<GLResource, Boolean> glslVertexShaders;
  protected static PMatrix3D identity;
  protected static TexureShader maskShader;
  protected static URL maskShaderFragURL;
  public static float maxAnisoAmount;
  public static float maxLineWidth;
  public static float maxPointSize;
  public static int maxSamples;
  public static int maxTextureSize;
  public static boolean npotTexSupported;
  public static boolean packedDepthStencilSupported;
  protected static PGraphicsOpenGL pgCurrent;
  protected static PGraphicsOpenGL pgPrimary = null;
  protected static FrameBuffer readFramebuffer;
  public static int stencilBits;
  protected static Tessellator tessellator;
  protected final float[][] QUAD_POINT_SIGNS;
  protected int blendMode;
  protected boolean breakShape;
  public PMatrix3D camera;
  public float cameraAspect;
  protected float cameraEyeX;
  protected float cameraEyeY;
  protected float cameraEyeZ;
  public float cameraFOV;
  public float cameraFar;
  public PMatrix3D cameraInv;
  protected float[][] cameraInvStack;
  public float cameraNear;
  protected float[][] cameraStack;
  public float cameraX;
  public float cameraY;
  public float cameraZ;
  protected boolean clearColorBuffer;
  protected boolean clearColorBuffer0;
  protected boolean clip;
  protected int[] clipRect;
  protected ColorShader colorShader;
  public float currentLightFalloffConstant;
  public float currentLightFalloffLinear;
  public float currentLightFalloffQuadratic;
  public float[] currentLightSpecular;
  protected boolean defaultEdges;
  protected boolean drawing;
  protected PImage filterImage;
  protected Texture filterTexture;
  protected FloatBuffer floatBuffer;
  protected int flushMode = 1;
  protected WeakHashMap<PFont, FontTexture> fontMap = new WeakHashMap();
  public int glLineAttrib;
  public int glLineColor;
  public int glLineIndex;
  public int glLineVertex;
  protected float[] glModelview;
  protected float[] glNormal;
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
  protected float[] glProjection;
  protected float[] glProjmodelview;
  protected InGeometry inGeo;
  public boolean initialized;
  protected IntBuffer intBuffer;
  protected int lastSmoothCall;
  public float[] lightAmbient;
  public int lightCount;
  public float[] lightDiffuse;
  public float[] lightFalloffCoefficients;
  public float[] lightNormal;
  public float[] lightPosition;
  protected LightShader lightShader;
  public float[] lightSpecular;
  public float[] lightSpotParameters;
  public int[] lightType;
  public boolean lights;
  protected boolean lightsAllocated;
  protected int lineBuffersContext;
  protected boolean lineBuffersCreated = false;
  protected LineShader lineShader;
  protected boolean manipulatingCamera;
  protected boolean matricesAllocated = false;
  public PMatrix3D modelview;
  public PMatrix3D modelviewInv;
  protected float[][] modelviewInvStack;
  protected float[][] modelviewStack;
  protected int modelviewStackDepth;
  protected FrameBuffer multisampleFramebuffer;
  protected IntBuffer nativePixelBuffer;
  protected int[] nativePixels;
  protected FrameBuffer offscreenFramebuffer;
  protected boolean offscreenMultisample;
  protected boolean openContour;
  public PGL pgl;
  protected boolean pixOpChangedFB;
  protected IntBuffer pixelBuffer;
  protected int pixelsOp;
  protected int pointBuffersContext;
  protected boolean pointBuffersCreated = false;
  protected PointShader pointShader;
  protected int polyBuffersContext;
  protected boolean polyBuffersCreated = false;
  public PMatrix3D projection;
  protected float[][] projectionStack;
  protected int projectionStackDepth;
  public PMatrix3D projmodelview;
  protected Texture ptexture;
  protected boolean restoreSurface;
  protected boolean setgetPixels;
  protected boolean shaderWarningsEnabled = true;
  protected boolean sized;
  protected int smoothCallCount;
  protected boolean smoothDisabled;
  protected TessGeometry tessGeo;
  protected TexCache texCache;
  protected TexlightShader texlightShader;
  FontTexture textTex;
  protected Texture texture;
  protected PImage textureImage0;
  protected int textureSampling;
  protected TexureShader textureShader;
  protected int textureWrap;
  protected IntBuffer viewport;
  
  static
  {
    pgCurrent = null;
    glParamsRead = false;
    glTextureObjects = new HashMap();
    glVertexBuffers = new HashMap();
    glFrameBuffers = new HashMap();
    glRenderBuffers = new HashMap();
    glslPrograms = new HashMap();
    glslVertexShaders = new HashMap();
    glslFragmentShaders = new HashMap();
    defColorShaderVertURL = PGraphicsOpenGL.class.getResource("ColorVert.glsl");
    defTextureShaderVertURL = PGraphicsOpenGL.class.getResource("TextureVert.glsl");
    defLightShaderVertURL = PGraphicsOpenGL.class.getResource("LightVert.glsl");
    defTexlightShaderVertURL = PGraphicsOpenGL.class.getResource("TexlightVert.glsl");
    defColorShaderFragURL = PGraphicsOpenGL.class.getResource("ColorFrag.glsl");
    defTextureShaderFragURL = PGraphicsOpenGL.class.getResource("TextureFrag.glsl");
    defLineShaderVertURL = PGraphicsOpenGL.class.getResource("LineVert.glsl");
    defLineShaderFragURL = PGraphicsOpenGL.class.getResource("LineFrag.glsl");
    defPointShaderVertURL = PGraphicsOpenGL.class.getResource("PointVert.glsl");
    defPointShaderFragURL = PGraphicsOpenGL.class.getResource("PointFrag.glsl");
    maskShaderFragURL = PGraphicsOpenGL.class.getResource("MaskFrag.glsl");
    identity = new PMatrix3D();
  }
  
  public PGraphicsOpenGL()
  {
    int[] arrayOfInt1 = { 32, 16 };
    this.modelviewStack = ((float[][])Array.newInstance(Float.TYPE, arrayOfInt1));
    int[] arrayOfInt2 = { 32, 16 };
    this.modelviewInvStack = ((float[][])Array.newInstance(Float.TYPE, arrayOfInt2));
    int[] arrayOfInt3 = { 32, 16 };
    this.cameraStack = ((float[][])Array.newInstance(Float.TYPE, arrayOfInt3));
    int[] arrayOfInt4 = { 32, 16 };
    this.cameraInvStack = ((float[][])Array.newInstance(Float.TYPE, arrayOfInt4));
    int[] arrayOfInt5 = { 32, 16 };
    this.projectionStack = ((float[][])Array.newInstance(Float.TYPE, arrayOfInt5));
    this.lightCount = 0;
    this.lightsAllocated = false;
    this.textureWrap = 0;
    this.textureSampling = 5;
    this.clip = false;
    this.clipRect = new int[] { 0, 0, 0, 0 };
    this.drawing = false;
    this.restoreSurface = false;
    this.smoothDisabled = false;
    this.smoothCallCount = 0;
    this.lastSmoothCall = -10;
    this.pixelsOp = 0;
    this.openContour = false;
    this.breakShape = false;
    this.defaultEdges = false;
    this.QUAD_POINT_SIGNS = new float[][] { { -1.0F, 1.0F }, { -1.0F, -1.0F }, { 1.0F, -1.0F }, { 1.0F, 1.0F } };
    this.pgl = new PGL(this);
    if (tessellator == null) {
      tessellator = new Tessellator();
    }
    this.intBuffer = PGL.allocateIntBuffer(2);
    this.floatBuffer = PGL.allocateFloatBuffer(2);
    this.viewport = PGL.allocateIntBuffer(4);
    this.inGeo = newInGeometry(0);
    this.tessGeo = newTessGeometry(0);
    this.texCache = newTexCache();
    this.initialized = false;
  }
  
  protected static boolean diff(float paramFloat1, float paramFloat2)
  {
    return PGL.FLOAT_EPS <= Math.abs(paramFloat1 - paramFloat2);
  }
  
  protected static int expandArraySize(int paramInt1, int paramInt2)
  {
    while (paramInt1 < paramInt2) {
      paramInt1 <<= 1;
    }
    return paramInt1;
  }
  
  private static void invRotate(PMatrix3D paramPMatrix3D, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = PApplet.cos(-paramFloat1);
    float f2 = PApplet.sin(-paramFloat1);
    float f3 = 1.0F - f1;
    paramPMatrix3D.preApply(f1 + paramFloat2 * (f3 * paramFloat2), paramFloat3 * (f3 * paramFloat2) - f2 * paramFloat4, paramFloat4 * (f3 * paramFloat2) + f2 * paramFloat3, 0.0F, paramFloat3 * (f3 * paramFloat2) + f2 * paramFloat4, f1 + paramFloat3 * (f3 * paramFloat3), paramFloat4 * (f3 * paramFloat3) - f2 * paramFloat2, 0.0F, paramFloat4 * (f3 * paramFloat2) - f2 * paramFloat3, paramFloat4 * (f3 * paramFloat3) + f2 * paramFloat2, f1 + paramFloat4 * (f3 * paramFloat4), 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  protected static void invScale(PMatrix3D paramPMatrix3D, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    paramPMatrix3D.preApply(1.0F / paramFloat1, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F / paramFloat2, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F / paramFloat3, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  protected static void invTranslate(PMatrix3D paramPMatrix3D, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    paramPMatrix3D.preApply(1.0F, 0.0F, 0.0F, -paramFloat1, 0.0F, 1.0F, 0.0F, -paramFloat2, 0.0F, 0.0F, 1.0F, -paramFloat3, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  protected static boolean nonZero(float paramFloat)
  {
    return PGL.FLOAT_EPS <= Math.abs(paramFloat);
  }
  
  protected static boolean same(float paramFloat1, float paramFloat2)
  {
    return Math.abs(paramFloat1 - paramFloat2) < PGL.FLOAT_EPS;
  }
  
  protected static boolean zero(float paramFloat)
  {
    return Math.abs(paramFloat) < PGL.FLOAT_EPS;
  }
  
  protected Texture addTexture(PImage paramPImage)
  {
    return addTexture(paramPImage, new Texture.Parameters(2, this.textureSampling, getHint(-8), this.textureWrap));
  }
  
  protected Texture addTexture(PImage paramPImage, Texture.Parameters paramParameters)
  {
    if ((paramPImage.width == 0) || (paramPImage.height == 0)) {
      return null;
    }
    if (paramPImage.parent == null) {
      paramPImage.parent = this.parent;
    }
    Texture localTexture = new Texture(paramPImage.parent, paramPImage.width, paramPImage.height, paramParameters);
    pgPrimary.setCache(paramPImage, localTexture);
    return localTexture;
  }
  
  protected void allocate()
  {
    super.allocate();
    if (!this.matricesAllocated)
    {
      this.projection = new PMatrix3D();
      this.camera = new PMatrix3D();
      this.cameraInv = new PMatrix3D();
      this.modelview = new PMatrix3D();
      this.modelviewInv = new PMatrix3D();
      this.projmodelview = new PMatrix3D();
      this.matricesAllocated = true;
    }
    if (!this.lightsAllocated)
    {
      this.lightType = new int[8];
      this.lightPosition = new float[32];
      this.lightNormal = new float[24];
      this.lightAmbient = new float[24];
      this.lightDiffuse = new float[24];
      this.lightSpecular = new float[24];
      this.lightFalloffCoefficients = new float[24];
      this.lightSpotParameters = new float[16];
      this.currentLightSpecular = new float[3];
      this.lightsAllocated = true;
    }
  }
  
  protected void allocatePixels()
  {
    if ((this.pixels == null) || (this.pixels.length != this.width * this.height))
    {
      this.pixels = new int[this.width * this.height];
      this.pixelBuffer = PGL.allocateIntBuffer(this.pixels);
    }
  }
  
  public void ambientLight(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    ambientLight(paramFloat1, paramFloat2, paramFloat3, 0.0F, 0.0F, 0.0F);
  }
  
  public void ambientLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    enableLighting();
    if (this.lightCount == 8) {
      throw new RuntimeException("can only create 8 lights");
    }
    this.lightType[this.lightCount] = 0;
    lightPosition(this.lightCount, paramFloat4, paramFloat5, paramFloat6, false);
    lightNormal(this.lightCount, 0.0F, 0.0F, 0.0F);
    lightAmbient(this.lightCount, paramFloat1, paramFloat2, paramFloat3);
    noLightDiffuse(this.lightCount);
    noLightSpecular(this.lightCount);
    noLightSpot(this.lightCount);
    lightFalloff(this.lightCount, this.currentLightFalloffConstant, this.currentLightFalloffLinear, this.currentLightFalloffQuadratic);
    this.lightCount = (1 + this.lightCount);
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    applyMatrixImpl(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4, paramFloat5, 0.0F, paramFloat6, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    applyMatrixImpl(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramFloat13, paramFloat14, paramFloat15, paramFloat16);
  }
  
  public void applyMatrix(PMatrix2D paramPMatrix2D)
  {
    applyMatrixImpl(paramPMatrix2D.m00, paramPMatrix2D.m01, 0.0F, paramPMatrix2D.m02, paramPMatrix2D.m10, paramPMatrix2D.m11, 0.0F, paramPMatrix2D.m12, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void applyMatrix(PMatrix3D paramPMatrix3D)
  {
    applyMatrixImpl(paramPMatrix3D.m00, paramPMatrix3D.m01, paramPMatrix3D.m02, paramPMatrix3D.m03, paramPMatrix3D.m10, paramPMatrix3D.m11, paramPMatrix3D.m12, paramPMatrix3D.m13, paramPMatrix3D.m20, paramPMatrix3D.m21, paramPMatrix3D.m22, paramPMatrix3D.m23, paramPMatrix3D.m30, paramPMatrix3D.m31, paramPMatrix3D.m32, paramPMatrix3D.m33);
  }
  
  protected void applyMatrixImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    this.modelview.apply(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramFloat13, paramFloat14, paramFloat15, paramFloat16);
    this.modelviewInv.set(this.modelview);
    this.modelviewInv.invert();
    this.projmodelview.apply(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramFloat13, paramFloat14, paramFloat15, paramFloat16);
  }
  
  public void applyProjection(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    flush();
    this.projection.apply(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramFloat13, paramFloat14, paramFloat15, paramFloat16);
    updateProjmodelview();
  }
  
  public void applyProjection(PMatrix3D paramPMatrix3D)
  {
    flush();
    this.projection.apply(paramPMatrix3D);
    updateProjmodelview();
  }
  
  protected void arcImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt)
  {
    beginShape(11);
    this.defaultEdges = false;
    this.normalMode = 1;
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
    this.inGeo.addArc(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, this.fill, this.stroke, paramInt);
    endShape();
  }
  
  protected void backgroundImpl()
  {
    flush();
    this.pgl.depthMask(true);
    this.pgl.clearDepth(1.0F);
    this.pgl.clear(256);
    if (this.hints[5] != 0) {
      this.pgl.depthMask(false);
    }
    for (;;)
    {
      this.pgl.clearColor(this.backgroundR, this.backgroundG, this.backgroundB, this.backgroundA);
      this.pgl.clear(16384);
      if (this.parent.frameCount > 0) {
        this.clearColorBuffer = true;
      }
      return;
      this.pgl.depthMask(true);
    }
  }
  
  protected void backgroundImpl(PImage paramPImage)
  {
    backgroundImpl();
    set(0, 0, paramPImage);
    if (this.parent.frameCount > 0) {
      this.clearColorBuffer = true;
    }
  }
  
  protected void begin2D() {}
  
  public void beginCamera()
  {
    if (this.manipulatingCamera) {
      throw new RuntimeException("beginCamera() cannot be called again before endCamera()");
    }
    this.manipulatingCamera = true;
  }
  
  public void beginContour()
  {
    if (this.openContour)
    {
      PGraphics.showWarning("Already called beginContour()");
      return;
    }
    this.openContour = true;
    this.breakShape = true;
  }
  
  public void beginDraw()
  {
    report("top beginDraw()");
    if (!checkGLThread()) {
      return;
    }
    if (this.drawing)
    {
      PGraphics.showWarning("Already called beginDraw()");
      return;
    }
    if ((pgCurrent != null) && (!pgCurrent.primarySurface) && (!this.primarySurface))
    {
      PGraphics.showWarning("Already called drawing on another PGraphicsOpenGL object");
      return;
    }
    if (!glParamsRead) {
      getGLParameters();
    }
    if (this.primarySurface) {
      beginOnscreenDraw();
    }
    for (;;)
    {
      setDefaults();
      pgCurrent = this;
      this.drawing = true;
      report("bot beginDraw()");
      return;
      beginOffscreenDraw();
    }
  }
  
  protected void beginOffscreenDraw()
  {
    updateOffscreen();
    this.offscreenFramebuffer.setColorBuffer(this.texture);
    if (this.clip)
    {
      this.pgl.enable(3089);
      this.pgl.scissor(this.clipRect[0], this.clipRect[1], this.clipRect[2], this.clipRect[3]);
      return;
    }
    this.pgl.disable(3089);
  }
  
  protected void beginOnscreenDraw()
  {
    updatePrimary();
    this.pgl.beginDraw(this.clearColorBuffer);
    if (drawFramebuffer == null) {
      drawFramebuffer = new FrameBuffer(this.parent, this.width, this.height, true);
    }
    drawFramebuffer.setFBO(this.pgl.getDrawFramebuffer());
    if (readFramebuffer == null) {
      readFramebuffer = new FrameBuffer(this.parent, this.width, this.height, true);
    }
    readFramebuffer.setFBO(this.pgl.getReadFramebuffer());
    if (currentFramebuffer == null) {
      setFramebuffer(drawFramebuffer);
    }
    if (this.pgl.isFBOBacked())
    {
      if (this.texture != null) {
        break label159;
      }
      this.texture = this.pgl.wrapBackTexture();
    }
    while (this.ptexture == null)
    {
      this.ptexture = this.pgl.wrapFrontTexture();
      return;
      label159:
      this.texture.glName = this.pgl.getBackTextureName();
    }
    this.ptexture.glName = this.pgl.getFrontTextureName();
  }
  
  public PGL beginPGL()
  {
    flush();
    this.pgl.beginGL();
    return this.pgl;
  }
  
  protected void beginPixelsOp(int paramInt)
  {
    FrameBuffer localFrameBuffer;
    if (this.primarySurface) {
      if (paramInt == 1) {
        if ((this.pgl.isFBOBacked()) && (this.pgl.isMultisampled()))
        {
          this.pgl.syncBackTexture();
          localFrameBuffer = readFramebuffer;
          if (localFrameBuffer != currentFramebuffer)
          {
            pushFramebuffer();
            setFramebuffer(localFrameBuffer);
            this.pixOpChangedFB = true;
          }
          if (paramInt != 1) {
            break label173;
          }
          this.pgl.readBuffer(currentFramebuffer.getDefaultDrawBuffer());
        }
      }
    }
    for (;;)
    {
      this.pixelsOp = paramInt;
      return;
      localFrameBuffer = drawFramebuffer;
      break;
      localFrameBuffer = null;
      if (paramInt != 2) {
        break;
      }
      localFrameBuffer = drawFramebuffer;
      break;
      if (paramInt == 1)
      {
        if (this.offscreenMultisample) {
          this.multisampleFramebuffer.copy(this.offscreenFramebuffer, currentFramebuffer);
        }
        localFrameBuffer = this.offscreenFramebuffer;
        break;
      }
      localFrameBuffer = null;
      if (paramInt != 2) {
        break;
      }
      if (this.offscreenMultisample)
      {
        localFrameBuffer = this.multisampleFramebuffer;
        break;
      }
      localFrameBuffer = this.offscreenFramebuffer;
      break;
      label173:
      if (paramInt == 2) {
        this.pgl.drawBuffer(currentFramebuffer.getDefaultDrawBuffer());
      }
    }
  }
  
  public void beginShape(int paramInt)
  {
    this.shape = paramInt;
    this.curveVertexCount = 0;
    this.inGeo.clear();
    this.breakShape = true;
    this.defaultEdges = true;
    this.textureImage0 = this.textureImage;
    super.noTexture();
    this.normalMode = 0;
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
    this.inGeo.addBezierVertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, this.fill, this.stroke, this.bezierDetail, vertexCode(), this.shape);
  }
  
  protected void bindBackTexture()
  {
    if (this.primarySurface)
    {
      this.pgl.bindFrontTexture();
      return;
    }
    this.ptexture.bind();
  }
  
  public void blendMode(int paramInt)
  {
    if (this.blendMode != paramInt)
    {
      flush();
      setBlendMode(paramInt);
    }
  }
  
  public void box(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    beginShape(17);
    this.defaultEdges = false;
    this.normalMode = 2;
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.addBox(paramFloat1, paramFloat2, paramFloat3, this.fill, this.stroke);
    endShape();
  }
  
  public void camera()
  {
    camera(this.cameraX, this.cameraY, this.cameraZ, this.cameraX, this.cameraY, 0.0F, 0.0F, 1.0F, 0.0F);
  }
  
  public void camera(float paramFloat1, float paramFloat2)
  {
    this.modelview.reset();
    this.modelview.translate(-paramFloat1, -paramFloat2);
    this.modelviewInv.set(this.modelview);
    this.modelviewInv.invert();
    this.camera.set(this.modelview);
    this.cameraInv.set(this.modelviewInv);
    updateProjmodelview();
  }
  
  public void camera(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    float f1 = paramFloat1 - paramFloat4;
    float f2 = paramFloat2 - paramFloat5;
    float f3 = paramFloat3 - paramFloat6;
    float f4 = PApplet.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
    if (nonZero(f4))
    {
      f1 /= f4;
      f2 /= f4;
      f3 /= f4;
    }
    this.cameraEyeX = paramFloat1;
    this.cameraEyeY = paramFloat2;
    this.cameraEyeZ = paramFloat3;
    float f5 = paramFloat8 * f3 - paramFloat9 * f2;
    float f6 = f3 * -paramFloat7 + paramFloat9 * f1;
    float f7 = paramFloat7 * f2 - paramFloat8 * f1;
    float f8 = f2 * f7 - f3 * f6;
    float f9 = f7 * -f1 + f3 * f5;
    float f10 = f1 * f6 - f2 * f5;
    float f11 = PApplet.sqrt(f5 * f5 + f6 * f6 + f7 * f7);
    if (nonZero(f11))
    {
      f5 /= f11;
      f6 /= f11;
      f7 /= f11;
    }
    float f12 = PApplet.sqrt(f8 * f8 + f9 * f9 + f10 * f10);
    if (nonZero(f12))
    {
      f8 /= f12;
      f9 /= f12;
      f10 /= f12;
    }
    this.modelview.set(f5, f6, f7, 0.0F, f8, f9, f10, 0.0F, f1, f2, f3, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
    float f13 = -paramFloat1;
    float f14 = -paramFloat2;
    float f15 = -paramFloat3;
    this.modelview.translate(f13, f14, f15);
    this.modelviewInv.set(this.modelview);
    this.modelviewInv.invert();
    this.camera.set(this.modelview);
    this.cameraInv.set(this.modelviewInv);
    updateProjmodelview();
  }
  
  public boolean canDraw()
  {
    return this.pgl.canDraw();
  }
  
  protected boolean checkGLThread()
  {
    if (this.pgl.threadIsCurrent()) {
      return true;
    }
    PGraphics.showWarning("Cannot run the OpenGL renderer outside the main thread, change your code\nso the drawing calls are all inside the main thread, \nor use the default renderer instead.");
    return false;
  }
  
  protected void checkTexture(Texture paramTexture)
  {
    if ((!paramTexture.isColorBuffer()) && (paramTexture.usingMipmaps == this.hints[8]))
    {
      if (this.hints[8] == 0) {
        break label82;
      }
      paramTexture.usingMipmaps(false, this.textureSampling);
    }
    for (;;)
    {
      if (((paramTexture.usingRepeat) && (this.textureWrap == 0)) || ((!paramTexture.usingRepeat) && (this.textureWrap == 1)))
      {
        if (this.textureWrap != 0) {
          break;
        }
        paramTexture.usingRepeat(false);
      }
      return;
      label82:
      paramTexture.usingMipmaps(true, this.textureSampling);
    }
    paramTexture.usingRepeat(true);
  }
  
  protected void clipImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    flush();
    this.pgl.enable(3089);
    float f = paramFloat4 - paramFloat2;
    this.clipRect[0] = ((int)paramFloat1);
    this.clipRect[1] = ((int)(this.height - paramFloat2 - f));
    this.clipRect[2] = ((int)(paramFloat3 - paramFloat1));
    this.clipRect[3] = ((int)f);
    this.pgl.scissor(this.clipRect[0], this.clipRect[1], this.clipRect[2], this.clipRect[3]);
    this.clip = true;
  }
  
  protected void colorShaderCheck()
  {
    if ((this.shaderWarningsEnabled) && ((this.texlightShader != null) || (this.lightShader != null) || (this.textureShader != null))) {
      PGraphics.showWarning("Your shader cannot be used to render colored geometry, using default shader instead.");
    }
  }
  
  protected int createFrameBufferObject(int paramInt)
  {
    deleteFinalizedFrameBufferObjects();
    this.pgl.genFramebuffers(1, this.intBuffer);
    int i = this.intBuffer.get(0);
    GLResource localGLResource = new GLResource(i, paramInt);
    if (!glFrameBuffers.containsKey(localGLResource)) {
      glFrameBuffers.put(localGLResource, Boolean.valueOf(false));
    }
    return i;
  }
  
  protected int createGLSLFragShaderObject(int paramInt)
  {
    deleteFinalizedGLSLFragShaderObjects();
    int i = this.pgl.createShader(35632);
    GLResource localGLResource = new GLResource(i, paramInt);
    if (!glslFragmentShaders.containsKey(localGLResource)) {
      glslFragmentShaders.put(localGLResource, Boolean.valueOf(false));
    }
    return i;
  }
  
  protected int createGLSLProgramObject(int paramInt)
  {
    deleteFinalizedGLSLProgramObjects();
    int i = this.pgl.createProgram();
    GLResource localGLResource = new GLResource(i, paramInt);
    if (!glslPrograms.containsKey(localGLResource)) {
      glslPrograms.put(localGLResource, Boolean.valueOf(false));
    }
    return i;
  }
  
  protected int createGLSLVertShaderObject(int paramInt)
  {
    deleteFinalizedGLSLVertShaderObjects();
    int i = this.pgl.createShader(35633);
    GLResource localGLResource = new GLResource(i, paramInt);
    if (!glslVertexShaders.containsKey(localGLResource)) {
      glslVertexShaders.put(localGLResource, Boolean.valueOf(false));
    }
    return i;
  }
  
  protected void createLineBuffers()
  {
    if ((!this.lineBuffersCreated) || (lineBufferContextIsOutdated()))
    {
      this.lineBuffersContext = this.pgl.getCurrentContext();
      this.glLineVertex = createVertexBufferObject(this.lineBuffersContext);
      this.pgl.bindBuffer(34962, this.glLineVertex);
      this.pgl.bufferData(34962, 3072, null, 35044);
      this.glLineColor = createVertexBufferObject(this.lineBuffersContext);
      this.pgl.bindBuffer(34962, this.glLineColor);
      this.pgl.bufferData(34962, 1024, null, 35044);
      this.glLineAttrib = createVertexBufferObject(this.lineBuffersContext);
      this.pgl.bindBuffer(34962, this.glLineAttrib);
      this.pgl.bufferData(34962, 4096, null, 35044);
      this.pgl.bindBuffer(34962, 0);
      this.glLineIndex = createVertexBufferObject(this.lineBuffersContext);
      this.pgl.bindBuffer(34963, this.glLineIndex);
      this.pgl.bufferData(34963, 1024, null, 35044);
      this.pgl.bindBuffer(34963, 0);
      this.lineBuffersCreated = true;
    }
  }
  
  protected void createPointBuffers()
  {
    if ((!this.pointBuffersCreated) || (pointBuffersContextIsOutdated()))
    {
      this.pointBuffersContext = this.pgl.getCurrentContext();
      this.glPointVertex = createVertexBufferObject(this.pointBuffersContext);
      this.pgl.bindBuffer(34962, this.glPointVertex);
      this.pgl.bufferData(34962, 3072, null, 35044);
      this.glPointColor = createVertexBufferObject(this.pointBuffersContext);
      this.pgl.bindBuffer(34962, this.glPointColor);
      this.pgl.bufferData(34962, 1024, null, 35044);
      this.glPointAttrib = createVertexBufferObject(this.pointBuffersContext);
      this.pgl.bindBuffer(34962, this.glPointAttrib);
      this.pgl.bufferData(34962, 2048, null, 35044);
      this.pgl.bindBuffer(34962, 0);
      this.glPointIndex = createVertexBufferObject(this.pointBuffersContext);
      this.pgl.bindBuffer(34963, this.glPointIndex);
      this.pgl.bufferData(34963, 1024, null, 35044);
      this.pgl.bindBuffer(34963, 0);
      this.pointBuffersCreated = true;
    }
  }
  
  protected void createPolyBuffers()
  {
    if ((!this.polyBuffersCreated) || (polyBuffersContextIsOutdated()))
    {
      this.polyBuffersContext = this.pgl.getCurrentContext();
      this.glPolyVertex = createVertexBufferObject(this.polyBuffersContext);
      this.pgl.bindBuffer(34962, this.glPolyVertex);
      this.pgl.bufferData(34962, 3072, null, 35044);
      this.glPolyColor = createVertexBufferObject(this.polyBuffersContext);
      this.pgl.bindBuffer(34962, this.glPolyColor);
      this.pgl.bufferData(34962, 1024, null, 35044);
      this.glPolyNormal = createVertexBufferObject(this.polyBuffersContext);
      this.pgl.bindBuffer(34962, this.glPolyNormal);
      this.pgl.bufferData(34962, 3072, null, 35044);
      this.glPolyTexcoord = createVertexBufferObject(this.polyBuffersContext);
      this.pgl.bindBuffer(34962, this.glPolyTexcoord);
      this.pgl.bufferData(34962, 2048, null, 35044);
      this.glPolyAmbient = pgPrimary.createVertexBufferObject(this.polyBuffersContext);
      this.pgl.bindBuffer(34962, this.glPolyAmbient);
      this.pgl.bufferData(34962, 1024, null, 35044);
      this.glPolySpecular = pgPrimary.createVertexBufferObject(this.polyBuffersContext);
      this.pgl.bindBuffer(34962, this.glPolySpecular);
      this.pgl.bufferData(34962, 1024, null, 35044);
      this.glPolyEmissive = pgPrimary.createVertexBufferObject(this.polyBuffersContext);
      this.pgl.bindBuffer(34962, this.glPolyEmissive);
      this.pgl.bufferData(34962, 1024, null, 35044);
      this.glPolyShininess = pgPrimary.createVertexBufferObject(this.polyBuffersContext);
      this.pgl.bindBuffer(34962, this.glPolyShininess);
      this.pgl.bufferData(34962, 1024, null, 35044);
      this.pgl.bindBuffer(34962, 0);
      this.glPolyIndex = createVertexBufferObject(this.polyBuffersContext);
      this.pgl.bindBuffer(34963, this.glPolyIndex);
      this.pgl.bufferData(34963, 1024, null, 35044);
      this.pgl.bindBuffer(34963, 0);
      this.polyBuffersCreated = true;
    }
  }
  
  protected int createRenderBufferObject(int paramInt)
  {
    deleteFinalizedRenderBufferObjects();
    this.pgl.genRenderbuffers(1, this.intBuffer);
    int i = this.intBuffer.get(0);
    GLResource localGLResource = new GLResource(i, paramInt);
    if (!glRenderBuffers.containsKey(localGLResource)) {
      glRenderBuffers.put(localGLResource, Boolean.valueOf(false));
    }
    return i;
  }
  
  protected int createTextureObject(int paramInt)
  {
    deleteFinalizedTextureObjects();
    this.pgl.genTextures(1, this.intBuffer);
    int i = this.intBuffer.get(0);
    GLResource localGLResource = new GLResource(i, paramInt);
    if (!glTextureObjects.containsKey(localGLResource)) {
      glTextureObjects.put(localGLResource, Boolean.valueOf(false));
    }
    return i;
  }
  
  protected int createVertexBufferObject(int paramInt)
  {
    deleteFinalizedVertexBufferObjects();
    this.pgl.genBuffers(1, this.intBuffer);
    int i = this.intBuffer.get(0);
    GLResource localGLResource = new GLResource(i, paramInt);
    if (!glVertexBuffers.containsKey(localGLResource)) {
      glVertexBuffers.put(localGLResource, Boolean.valueOf(false));
    }
    return i;
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
    this.inGeo.addCurveVertex(paramFloat1, paramFloat2, paramFloat3, this.fill, this.stroke, this.curveDetail, vertexCode(), this.shape);
  }
  
  protected void defaultCamera()
  {
    camera();
  }
  
  protected void defaultPerspective()
  {
    perspective();
  }
  
  protected void defaultSettings()
  {
    super.defaultSettings();
    this.manipulatingCamera = false;
    this.clearColorBuffer = false;
    textureMode(2);
    ambient(255);
    specular(125);
    emissive(0);
    shininess(1.0F);
    this.setAmbient = false;
  }
  
  protected void deleteAllFrameBufferObjects()
  {
    Iterator localIterator = glFrameBuffers.keySet().iterator();
    while (localIterator.hasNext())
    {
      GLResource localGLResource = (GLResource)localIterator.next();
      this.intBuffer.put(0, localGLResource.id);
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteFramebuffers(1, this.intBuffer);
      }
    }
    glFrameBuffers.clear();
  }
  
  protected void deleteAllGLSLFragShaderObjects()
  {
    Iterator localIterator = glslFragmentShaders.keySet().iterator();
    while (localIterator.hasNext())
    {
      GLResource localGLResource = (GLResource)localIterator.next();
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteShader(localGLResource.id);
      }
    }
    glslFragmentShaders.clear();
  }
  
  protected void deleteAllGLSLProgramObjects()
  {
    Iterator localIterator = glslPrograms.keySet().iterator();
    while (localIterator.hasNext())
    {
      GLResource localGLResource = (GLResource)localIterator.next();
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteProgram(localGLResource.id);
      }
    }
    glslPrograms.clear();
  }
  
  protected void deleteAllGLSLVertShaderObjects()
  {
    Iterator localIterator = glslVertexShaders.keySet().iterator();
    while (localIterator.hasNext())
    {
      GLResource localGLResource = (GLResource)localIterator.next();
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteShader(localGLResource.id);
      }
    }
    glslVertexShaders.clear();
  }
  
  protected void deleteAllRenderBufferObjects()
  {
    Iterator localIterator = glRenderBuffers.keySet().iterator();
    while (localIterator.hasNext())
    {
      GLResource localGLResource = (GLResource)localIterator.next();
      this.intBuffer.put(0, localGLResource.id);
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteRenderbuffers(1, this.intBuffer);
      }
    }
    glRenderBuffers.clear();
  }
  
  protected void deleteAllTextureObjects()
  {
    Iterator localIterator = glTextureObjects.keySet().iterator();
    while (localIterator.hasNext())
    {
      GLResource localGLResource = (GLResource)localIterator.next();
      this.intBuffer.put(0, localGLResource.id);
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteTextures(1, this.intBuffer);
      }
    }
    glTextureObjects.clear();
  }
  
  protected void deleteAllVertexBufferObjects()
  {
    Iterator localIterator = glVertexBuffers.keySet().iterator();
    while (localIterator.hasNext())
    {
      GLResource localGLResource = (GLResource)localIterator.next();
      this.intBuffer.put(0, localGLResource.id);
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteBuffers(1, this.intBuffer);
      }
    }
    glVertexBuffers.clear();
  }
  
  protected void deleteDefaultShaders()
  {
    defColorShader = null;
    defTextureShader = null;
    defLightShader = null;
    defTexlightShader = null;
    defLineShader = null;
    defPointShader = null;
    maskShader = null;
  }
  
  protected void deleteFinalizedFrameBufferObjects()
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator1 = glFrameBuffers.keySet().iterator();
    while (localIterator1.hasNext())
    {
      GLResource localGLResource2 = (GLResource)localIterator1.next();
      if (((Boolean)glFrameBuffers.get(localGLResource2)).booleanValue())
      {
        localHashSet.add(localGLResource2);
        this.intBuffer.put(0, localGLResource2.id);
        if (this.pgl.threadIsCurrent()) {
          this.pgl.deleteFramebuffers(1, this.intBuffer);
        }
      }
    }
    Iterator localIterator2 = localHashSet.iterator();
    while (localIterator2.hasNext())
    {
      GLResource localGLResource1 = (GLResource)localIterator2.next();
      glFrameBuffers.remove(localGLResource1);
    }
  }
  
  protected void deleteFinalizedGLResources()
  {
    deleteFinalizedTextureObjects();
    deleteFinalizedVertexBufferObjects();
    deleteFinalizedFrameBufferObjects();
    deleteFinalizedRenderBufferObjects();
    deleteFinalizedGLSLProgramObjects();
    deleteFinalizedGLSLVertShaderObjects();
    deleteFinalizedGLSLFragShaderObjects();
  }
  
  protected void deleteFinalizedGLSLFragShaderObjects()
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator1 = glslFragmentShaders.keySet().iterator();
    while (localIterator1.hasNext())
    {
      GLResource localGLResource2 = (GLResource)localIterator1.next();
      if (((Boolean)glslFragmentShaders.get(localGLResource2)).booleanValue())
      {
        localHashSet.add(localGLResource2);
        if (this.pgl.threadIsCurrent()) {
          this.pgl.deleteShader(localGLResource2.id);
        }
      }
    }
    Iterator localIterator2 = localHashSet.iterator();
    while (localIterator2.hasNext())
    {
      GLResource localGLResource1 = (GLResource)localIterator2.next();
      glslFragmentShaders.remove(localGLResource1);
    }
  }
  
  protected void deleteFinalizedGLSLProgramObjects()
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator1 = glslPrograms.keySet().iterator();
    while (localIterator1.hasNext())
    {
      GLResource localGLResource2 = (GLResource)localIterator1.next();
      if (((Boolean)glslPrograms.get(localGLResource2)).booleanValue())
      {
        localHashSet.add(localGLResource2);
        if (this.pgl.threadIsCurrent()) {
          this.pgl.deleteProgram(localGLResource2.id);
        }
      }
    }
    Iterator localIterator2 = localHashSet.iterator();
    while (localIterator2.hasNext())
    {
      GLResource localGLResource1 = (GLResource)localIterator2.next();
      glslPrograms.remove(localGLResource1);
    }
  }
  
  protected void deleteFinalizedGLSLVertShaderObjects()
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator1 = glslVertexShaders.keySet().iterator();
    while (localIterator1.hasNext())
    {
      GLResource localGLResource2 = (GLResource)localIterator1.next();
      if (((Boolean)glslVertexShaders.get(localGLResource2)).booleanValue())
      {
        localHashSet.add(localGLResource2);
        if (this.pgl.threadIsCurrent()) {
          this.pgl.deleteShader(localGLResource2.id);
        }
      }
    }
    Iterator localIterator2 = localHashSet.iterator();
    while (localIterator2.hasNext())
    {
      GLResource localGLResource1 = (GLResource)localIterator2.next();
      glslVertexShaders.remove(localGLResource1);
    }
  }
  
  protected void deleteFinalizedRenderBufferObjects()
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator1 = glRenderBuffers.keySet().iterator();
    while (localIterator1.hasNext())
    {
      GLResource localGLResource2 = (GLResource)localIterator1.next();
      if (((Boolean)glRenderBuffers.get(localGLResource2)).booleanValue())
      {
        localHashSet.add(localGLResource2);
        this.intBuffer.put(0, localGLResource2.id);
        if (this.pgl.threadIsCurrent()) {
          this.pgl.deleteRenderbuffers(1, this.intBuffer);
        }
      }
    }
    Iterator localIterator2 = localHashSet.iterator();
    while (localIterator2.hasNext())
    {
      GLResource localGLResource1 = (GLResource)localIterator2.next();
      glRenderBuffers.remove(localGLResource1);
    }
  }
  
  protected void deleteFinalizedTextureObjects()
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator1 = glTextureObjects.keySet().iterator();
    while (localIterator1.hasNext())
    {
      GLResource localGLResource2 = (GLResource)localIterator1.next();
      if (((Boolean)glTextureObjects.get(localGLResource2)).booleanValue())
      {
        localHashSet.add(localGLResource2);
        this.intBuffer.put(0, localGLResource2.id);
        if (this.pgl.threadIsCurrent()) {
          this.pgl.deleteTextures(1, this.intBuffer);
        }
      }
    }
    Iterator localIterator2 = localHashSet.iterator();
    while (localIterator2.hasNext())
    {
      GLResource localGLResource1 = (GLResource)localIterator2.next();
      glTextureObjects.remove(localGLResource1);
    }
  }
  
  protected void deleteFinalizedVertexBufferObjects()
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator1 = glVertexBuffers.keySet().iterator();
    while (localIterator1.hasNext())
    {
      GLResource localGLResource2 = (GLResource)localIterator1.next();
      if (((Boolean)glVertexBuffers.get(localGLResource2)).booleanValue())
      {
        localHashSet.add(localGLResource2);
        this.intBuffer.put(0, localGLResource2.id);
        if (this.pgl.threadIsCurrent()) {
          this.pgl.deleteBuffers(1, this.intBuffer);
        }
      }
    }
    Iterator localIterator2 = localHashSet.iterator();
    while (localIterator2.hasNext())
    {
      GLResource localGLResource1 = (GLResource)localIterator2.next();
      glVertexBuffers.remove(localGLResource1);
    }
  }
  
  protected void deleteFrameBufferObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glFrameBuffers.containsKey(localGLResource))
    {
      this.intBuffer.put(0, paramInt1);
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteFramebuffers(1, this.intBuffer);
      }
      glFrameBuffers.remove(localGLResource);
    }
  }
  
  protected void deleteGLSLFragShaderObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glslFragmentShaders.containsKey(localGLResource))
    {
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteShader(localGLResource.id);
      }
      glslFragmentShaders.remove(localGLResource);
    }
  }
  
  protected void deleteGLSLProgramObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glslPrograms.containsKey(localGLResource))
    {
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteProgram(localGLResource.id);
      }
      glslPrograms.remove(localGLResource);
    }
  }
  
  protected void deleteGLSLVertShaderObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glslVertexShaders.containsKey(localGLResource))
    {
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteShader(localGLResource.id);
      }
      glslVertexShaders.remove(localGLResource);
    }
  }
  
  protected void deleteLineBuffers()
  {
    if (this.lineBuffersCreated)
    {
      deleteVertexBufferObject(this.glLineVertex, this.lineBuffersContext);
      this.glLineVertex = 0;
      deleteVertexBufferObject(this.glLineColor, this.lineBuffersContext);
      this.glLineColor = 0;
      deleteVertexBufferObject(this.glLineAttrib, this.lineBuffersContext);
      this.glLineAttrib = 0;
      deleteVertexBufferObject(this.glLineIndex, this.lineBuffersContext);
      this.glLineIndex = 0;
      this.lineBuffersCreated = false;
    }
  }
  
  protected void deletePointBuffers()
  {
    if (this.pointBuffersCreated)
    {
      deleteVertexBufferObject(this.glPointVertex, this.pointBuffersContext);
      this.glPointVertex = 0;
      deleteVertexBufferObject(this.glPointColor, this.pointBuffersContext);
      this.glPointColor = 0;
      deleteVertexBufferObject(this.glPointAttrib, this.pointBuffersContext);
      this.glPointAttrib = 0;
      deleteVertexBufferObject(this.glPointIndex, this.pointBuffersContext);
      this.glPointIndex = 0;
      this.pointBuffersCreated = false;
    }
  }
  
  protected void deletePolyBuffers()
  {
    if (this.polyBuffersCreated)
    {
      deleteVertexBufferObject(this.glPolyVertex, this.polyBuffersContext);
      this.glPolyVertex = 0;
      deleteVertexBufferObject(this.glPolyColor, this.polyBuffersContext);
      this.glPolyColor = 0;
      deleteVertexBufferObject(this.glPolyNormal, this.polyBuffersContext);
      this.glPolyNormal = 0;
      deleteVertexBufferObject(this.glPolyTexcoord, this.polyBuffersContext);
      this.glPolyTexcoord = 0;
      deleteVertexBufferObject(this.glPolyAmbient, this.polyBuffersContext);
      this.glPolyAmbient = 0;
      deleteVertexBufferObject(this.glPolySpecular, this.polyBuffersContext);
      this.glPolySpecular = 0;
      deleteVertexBufferObject(this.glPolyEmissive, this.polyBuffersContext);
      this.glPolyEmissive = 0;
      deleteVertexBufferObject(this.glPolyShininess, this.polyBuffersContext);
      this.glPolyShininess = 0;
      deleteVertexBufferObject(this.glPolyIndex, this.polyBuffersContext);
      this.glPolyIndex = 0;
      this.polyBuffersCreated = false;
    }
  }
  
  protected void deleteRenderBufferObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glRenderBuffers.containsKey(localGLResource))
    {
      this.intBuffer.put(0, paramInt1);
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteRenderbuffers(1, this.intBuffer);
      }
      glRenderBuffers.remove(localGLResource);
    }
  }
  
  protected void deleteTextureObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glTextureObjects.containsKey(localGLResource))
    {
      this.intBuffer.put(0, paramInt1);
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteTextures(1, this.intBuffer);
      }
      glTextureObjects.remove(localGLResource);
    }
  }
  
  protected void deleteVertexBufferObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glVertexBuffers.containsKey(localGLResource))
    {
      this.intBuffer.put(0, paramInt1);
      if (this.pgl.threadIsCurrent()) {
        this.pgl.deleteBuffers(1, this.intBuffer);
      }
      glVertexBuffers.remove(localGLResource);
    }
  }
  
  public void directionalLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    enableLighting();
    if (this.lightCount == 8) {
      throw new RuntimeException("can only create 8 lights");
    }
    this.lightType[this.lightCount] = 1;
    lightPosition(this.lightCount, 0.0F, 0.0F, 0.0F, true);
    lightNormal(this.lightCount, paramFloat4, paramFloat5, paramFloat6);
    noLightAmbient(this.lightCount);
    lightDiffuse(this.lightCount, paramFloat1, paramFloat2, paramFloat3);
    lightSpecular(this.lightCount, this.currentLightSpecular[0], this.currentLightSpecular[1], this.currentLightSpecular[2]);
    noLightSpot(this.lightCount);
    noLightFalloff(this.lightCount);
    this.lightCount = (1 + this.lightCount);
  }
  
  protected void disableLighting()
  {
    if (this.lights)
    {
      flush();
      this.lights = false;
    }
  }
  
  public void dispose()
  {
    super.dispose();
    deleteFinalizedGLResources();
    deletePolyBuffers();
    deleteLineBuffers();
    deletePointBuffers();
    deleteDefaultShaders();
    this.pgl.deleteSurface();
  }
  
  protected void drawPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 * paramInt4;
    if ((this.nativePixels == null) || (this.nativePixels.length < i))
    {
      this.nativePixels = new int[i];
      this.nativePixelBuffer = PGL.allocateIntBuffer(this.nativePixels);
    }
    if ((paramInt1 <= 0) && (paramInt2 <= 0)) {}
    try
    {
      int m;
      int n;
      int i1;
      if ((paramInt3 < this.width) || (paramInt4 < this.height))
      {
        int k = paramInt1 + paramInt2 * this.width;
        m = 0;
        n = k;
        i1 = paramInt2;
      }
      while (i1 < paramInt2 + paramInt4)
      {
        System.arraycopy(this.pixels, n, this.nativePixels, m, paramInt3);
        n += this.width;
        m += paramInt3;
        i1++;
        continue;
        PApplet.arrayCopy(this.pixels, 0, this.nativePixels, 0, i);
      }
      PGL.javaToNativeARGB(this.nativePixels, paramInt3, paramInt4);
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      label158:
      boolean bool;
      int j;
      break label158;
    }
    PGL.putIntArray(this.nativePixelBuffer, this.nativePixels);
    if ((this.primarySurface) && (!this.pgl.isFBOBacked())) {
      loadTextureImpl(2, false);
    }
    if ((!this.primarySurface) || ((this.pgl.isFBOBacked()) && ((!this.pgl.isFBOBacked()) || (!this.pgl.isMultisampled()))))
    {
      bool = this.offscreenMultisample;
      j = 0;
      if (!bool) {}
    }
    else
    {
      j = 1;
    }
    if (j != 0)
    {
      this.pgl.copyToTexture(this.texture.glTarget, this.texture.glFormat, this.texture.glName, paramInt1, paramInt2, paramInt3, paramInt4, this.nativePixelBuffer);
      beginPixelsOp(2);
      drawTexture(paramInt1, paramInt2, paramInt3, paramInt4);
      endPixelsOp();
      return;
    }
    this.pgl.copyToTexture(this.texture.glTarget, this.texture.glFormat, this.texture.glName, paramInt1, this.height - (paramInt2 + paramInt4), paramInt3, paramInt4, this.nativePixelBuffer);
  }
  
  protected void drawTexture()
  {
    this.pgl.disable(3042);
    this.pgl.drawTexture(this.texture.glTarget, this.texture.glName, this.texture.glWidth, this.texture.glHeight, 0, 0, this.width, this.height);
    this.pgl.enable(3042);
  }
  
  protected void drawTexture(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.pgl.disable(3042);
    this.pgl.drawTexture(this.texture.glTarget, this.texture.glName, this.texture.glWidth, this.texture.glHeight, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4, paramInt1, this.height - (paramInt2 + paramInt4), paramInt1 + paramInt3, this.height - paramInt2);
    this.pgl.enable(3042);
  }
  
  public void drawTexture(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    beginPGL();
    this.pgl.drawTexture(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
    endPGL();
  }
  
  public void drawTexture(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12)
  {
    beginPGL();
    this.pgl.drawTexture(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10, paramInt11, paramInt12);
    endPGL();
  }
  
  public void ellipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    beginShape(11);
    this.defaultEdges = false;
    this.normalMode = 1;
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
    this.inGeo.addEllipse(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.fill, this.stroke, this.ellipseMode);
    endShape();
  }
  
  protected void enableLighting()
  {
    if (!this.lights)
    {
      flush();
      this.lights = true;
    }
  }
  
  protected void end2D() {}
  
  public void endCamera()
  {
    if (!this.manipulatingCamera) {
      throw new RuntimeException("Cannot call endCamera() without first calling beginCamera()");
    }
    this.camera.set(this.modelview);
    this.cameraInv.set(this.modelviewInv);
    this.manipulatingCamera = false;
  }
  
  public void endContour()
  {
    if (!this.openContour)
    {
      PGraphics.showWarning("Need to call beginContour() first");
      return;
    }
    this.openContour = false;
  }
  
  public void endDraw()
  {
    report("top endDraw()");
    if (!this.drawing)
    {
      PGraphics.showWarning("Cannot call endDraw() before beginDraw()");
      return;
    }
    flush();
    if (this.primarySurface)
    {
      endOnscreenDraw();
      if (pgCurrent != pgPrimary) {
        break label68;
      }
    }
    label68:
    for (pgCurrent = null;; pgCurrent = pgPrimary)
    {
      this.drawing = false;
      report("bot endDraw()");
      return;
      endOffscreenDraw();
      break;
    }
  }
  
  protected void endOffscreenDraw()
  {
    if (this.offscreenMultisample) {
      this.multisampleFramebuffer.copy(this.offscreenFramebuffer, currentFramebuffer);
    }
    if (!this.clearColorBuffer0)
    {
      if (this.offscreenMultisample)
      {
        pushFramebuffer();
        setFramebuffer(this.offscreenFramebuffer);
      }
      this.offscreenFramebuffer.setColorBuffer(this.ptexture);
      drawTexture();
      this.offscreenFramebuffer.setColorBuffer(this.texture);
      if (this.offscreenMultisample) {
        popFramebuffer();
      }
    }
    popFramebuffer();
    this.texture.updateTexels();
    pgPrimary.restoreGL();
  }
  
  protected void endOnscreenDraw()
  {
    this.pgl.endDraw(this.clearColorBuffer0);
  }
  
  public void endPGL()
  {
    this.pgl.endGL();
    restoreGL();
  }
  
  protected void endPixelsOp()
  {
    if (this.pixOpChangedFB)
    {
      popFramebuffer();
      this.pixOpChangedFB = false;
    }
    this.pgl.readBuffer(currentFramebuffer.getDefaultReadBuffer());
    this.pgl.drawBuffer(currentFramebuffer.getDefaultDrawBuffer());
    this.pixelsOp = 0;
  }
  
  public void endShape(int paramInt)
  {
    tessellate(paramInt);
    if ((this.flushMode == 0) || ((this.flushMode == 1) && (this.tessGeo.isFull()))) {
      flush();
    }
  }
  
  protected void endShape(int[] paramArrayOfInt)
  {
    endShape(paramArrayOfInt, null);
  }
  
  protected void endShape(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if ((this.shape != 8) && (this.shape != 9)) {
      throw new RuntimeException("Indices and edges can only be set for TRIANGLE shapes");
    }
    tessellate(paramArrayOfInt1, paramArrayOfInt2);
    if ((this.flushMode == 0) || ((this.flushMode == 1) && (this.tessGeo.isFull()))) {
      flush();
    }
  }
  
  protected void fillFromCalc()
  {
    super.fillFromCalc();
    if (!this.setAmbient)
    {
      ambientFromCalc();
      this.setAmbient = false;
    }
  }
  
  public void filter(int paramInt)
  {
    PImage localPImage = get();
    localPImage.filter(paramInt);
    set(0, 0, localPImage);
  }
  
  public void filter(int paramInt, float paramFloat)
  {
    PImage localPImage = get();
    localPImage.filter(paramInt, paramFloat);
    set(0, 0, localPImage);
  }
  
  public void filter(PShader paramPShader)
  {
    if (!(paramPShader instanceof TexureShader)) {
      PGraphics.showWarning("Object is not a valid shader to use as filter");
    }
    do
    {
      return;
      this.pgl.needFBOLayer();
      loadTexture();
      if ((this.filterTexture == null) || (this.filterTexture.contextIsOutdated()))
      {
        this.filterTexture = new Texture(this.parent, this.texture.width, this.texture.height, this.texture.getParameters());
        this.filterTexture.invertedY(true);
        this.filterImage = wrapTexture(this.filterTexture);
      }
      this.filterTexture.set(this.texture);
      this.pgl.depthMask(false);
      this.pgl.disable(2929);
      begin2D();
      boolean bool1 = this.lights;
      this.lights = false;
      int i = this.textureMode;
      this.textureMode = 1;
      boolean bool2 = this.stroke;
      this.stroke = false;
      TexureShader localTexureShader = this.textureShader;
      this.textureShader = ((TexureShader)paramPShader);
      beginShape(17);
      texture(this.filterImage);
      vertex(0.0F, 0.0F, 0.0F, 0.0F);
      vertex(this.width, 0.0F, 1.0F, 0.0F);
      vertex(this.width, this.height, 1.0F, 1.0F);
      vertex(0.0F, this.height, 0.0F, 1.0F);
      endShape();
      end2D();
      this.textureShader = localTexureShader;
      this.stroke = bool2;
      this.lights = bool1;
      this.textureMode = i;
      if (this.hints[2] == 0) {
        this.pgl.enable(2929);
      }
    } while (this.hints[5] != 0);
    this.pgl.depthMask(true);
  }
  
  protected void finalizeFrameBufferObject(int paramInt1, int paramInt2)
  {
    try
    {
      GLResource localGLResource = new GLResource(paramInt1, paramInt2);
      if (glFrameBuffers.containsKey(localGLResource)) {
        glFrameBuffers.put(localGLResource, Boolean.valueOf(true));
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  protected void finalizeGLSLFragShaderObject(int paramInt1, int paramInt2)
  {
    try
    {
      GLResource localGLResource = new GLResource(paramInt1, paramInt2);
      if (glslFragmentShaders.containsKey(localGLResource)) {
        glslFragmentShaders.put(localGLResource, Boolean.valueOf(true));
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  protected void finalizeGLSLProgramObject(int paramInt1, int paramInt2)
  {
    try
    {
      GLResource localGLResource = new GLResource(paramInt1, paramInt2);
      if (glslPrograms.containsKey(localGLResource)) {
        glslPrograms.put(localGLResource, Boolean.valueOf(true));
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  protected void finalizeGLSLVertShaderObject(int paramInt1, int paramInt2)
  {
    try
    {
      GLResource localGLResource = new GLResource(paramInt1, paramInt2);
      if (glslVertexShaders.containsKey(localGLResource)) {
        glslVertexShaders.put(localGLResource, Boolean.valueOf(true));
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  protected void finalizeRenderBufferObject(int paramInt1, int paramInt2)
  {
    try
    {
      GLResource localGLResource = new GLResource(paramInt1, paramInt2);
      if (glRenderBuffers.containsKey(localGLResource)) {
        glRenderBuffers.put(localGLResource, Boolean.valueOf(true));
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  protected void finalizeTextureObject(int paramInt1, int paramInt2)
  {
    try
    {
      GLResource localGLResource = new GLResource(paramInt1, paramInt2);
      if (glTextureObjects.containsKey(localGLResource)) {
        glTextureObjects.put(localGLResource, Boolean.valueOf(true));
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  protected void finalizeVertexBufferObject(int paramInt1, int paramInt2)
  {
    try
    {
      GLResource localGLResource = new GLResource(paramInt1, paramInt2);
      if (glVertexBuffers.containsKey(localGLResource)) {
        glVertexBuffers.put(localGLResource, Boolean.valueOf(true));
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void flush()
  {
    int i;
    int j;
    label44:
    int k;
    label66:
    int m;
    label83:
    PMatrix3D localPMatrix3D2;
    PMatrix3D localPMatrix3D1;
    if ((this.tessGeo.polyVertexCount > 0) && (this.tessGeo.polyIndexCount > 0))
    {
      i = 1;
      if ((this.tessGeo.lineVertexCount <= 0) || (this.tessGeo.lineIndexCount <= 0)) {
        break label265;
      }
      j = 1;
      if ((this.tessGeo.pointVertexCount <= 0) || (this.tessGeo.pointIndexCount <= 0)) {
        break label270;
      }
      k = 1;
      if ((!this.modified) || (this.pixels == null)) {
        break label275;
      }
      m = 1;
      if (m != 0) {
        flushPixels();
      }
      if ((k != 0) || (j != 0) || (i != 0))
      {
        if (this.flushMode != 1) {
          break label281;
        }
        localPMatrix3D2 = this.modelview;
        localPMatrix3D1 = this.modelviewInv;
        PMatrix3D localPMatrix3D3 = identity;
        this.modelviewInv = localPMatrix3D3;
        this.modelview = localPMatrix3D3;
        this.projmodelview.set(this.projection);
      }
    }
    for (;;)
    {
      if (i != 0)
      {
        flushPolys();
        if (this.raw != null) {
          rawPolys();
        }
      }
      if (is3D())
      {
        if (j != 0)
        {
          flushLines();
          if (this.raw != null) {
            rawLines();
          }
        }
        if (k != 0)
        {
          flushPoints();
          if (this.raw != null) {
            rawPoints();
          }
        }
      }
      if (this.flushMode == 1)
      {
        this.modelview = localPMatrix3D2;
        this.modelviewInv = localPMatrix3D1;
        updateProjmodelview();
      }
      this.tessGeo.clear();
      this.texCache.clear();
      this.setgetPixels = false;
      return;
      i = 0;
      break;
      label265:
      j = 0;
      break label44;
      label270:
      k = 0;
      break label66;
      label275:
      m = 0;
      break label83;
      label281:
      localPMatrix3D1 = null;
      localPMatrix3D2 = null;
    }
  }
  
  protected void flushLines()
  {
    updateLineBuffers();
    LineShader localLineShader = getLineShader();
    localLineShader.bind();
    IndexCache localIndexCache = this.tessGeo.lineIndexCache;
    for (int i = 0; i < localIndexCache.size; i++)
    {
      int j = localIndexCache.indexOffset[i];
      int k = localIndexCache.indexCount[i];
      int m = localIndexCache.vertexOffset[i];
      localLineShader.setVertexAttribute(this.glLineVertex, 4, 5126, 0, 4 * (m * 4));
      localLineShader.setColorAttribute(this.glLineColor, 4, 5121, 0, 1 * (m * 4));
      localLineShader.setLineAttribute(this.glLineAttrib, 4, 5126, 0, 4 * (m * 4));
      this.pgl.bindBuffer(34963, this.glLineIndex);
      this.pgl.drawElements(4, k, 5123, j * 2);
      this.pgl.bindBuffer(34963, 0);
    }
    localLineShader.unbind();
    unbindLineBuffers();
  }
  
  protected void flushPixels()
  {
    drawPixels(this.mx1, this.my1, 1 + (this.mx2 - this.mx1), 1 + (this.my2 - this.my1));
    this.modified = false;
  }
  
  protected void flushPoints()
  {
    updatePointBuffers();
    PointShader localPointShader = getPointShader();
    localPointShader.bind();
    IndexCache localIndexCache = this.tessGeo.pointIndexCache;
    for (int i = 0; i < localIndexCache.size; i++)
    {
      int j = localIndexCache.indexOffset[i];
      int k = localIndexCache.indexCount[i];
      int m = localIndexCache.vertexOffset[i];
      localPointShader.setVertexAttribute(this.glPointVertex, 4, 5126, 0, 4 * (m * 4));
      localPointShader.setColorAttribute(this.glPointColor, 4, 5121, 0, 1 * (m * 4));
      localPointShader.setPointAttribute(this.glPointAttrib, 2, 5126, 0, 4 * (m * 2));
      this.pgl.bindBuffer(34963, this.glPointIndex);
      this.pgl.drawElements(4, k, 5123, j * 2);
      this.pgl.bindBuffer(34963, 0);
    }
    localPointShader.unbind();
    unbindPointBuffers();
  }
  
  protected void flushPolys()
  {
    updatePolyBuffers(this.lights, this.texCache.hasTexture);
    this.texCache.beginRender();
    for (int i = 0; i < this.texCache.size; i++)
    {
      Texture localTexture = this.texCache.getTexture(i);
      boolean bool1 = this.lights;
      boolean bool2;
      BaseShader localBaseShader;
      IndexCache localIndexCache;
      int m;
      label105:
      int n;
      if (localTexture != null)
      {
        bool2 = true;
        localBaseShader = getPolyShader(bool1, bool2);
        localBaseShader.bind();
        int j = this.texCache.firstCache[i];
        int k = this.texCache.lastCache[i];
        localIndexCache = this.tessGeo.polyIndexCache;
        m = j;
        if (m > k) {
          break label430;
        }
        if (m != j) {
          break label392;
        }
        n = this.texCache.firstIndex[i];
        label130:
        if (m != k) {
          break label405;
        }
      }
      label392:
      label405:
      for (int i1 = 1 + (this.texCache.lastIndex[i] - n);; i1 = localIndexCache.indexOffset[m] + localIndexCache.indexCount[m] - n)
      {
        int i2 = localIndexCache.vertexOffset[m];
        localBaseShader.setVertexAttribute(this.glPolyVertex, 4, 5126, 0, 4 * (i2 * 4));
        localBaseShader.setColorAttribute(this.glPolyColor, 4, 5121, 0, 1 * (i2 * 4));
        if (this.lights)
        {
          localBaseShader.setNormalAttribute(this.glPolyNormal, 3, 5126, 0, 4 * (i2 * 3));
          localBaseShader.setAmbientAttribute(this.glPolyAmbient, 4, 5121, 0, 1 * (i2 * 4));
          localBaseShader.setSpecularAttribute(this.glPolySpecular, 4, 5121, 0, 1 * (i2 * 4));
          localBaseShader.setEmissiveAttribute(this.glPolyEmissive, 4, 5121, 0, 1 * (i2 * 4));
          localBaseShader.setShininessAttribute(this.glPolyShininess, 1, 5126, 0, i2 * 4);
        }
        if (localTexture != null)
        {
          localBaseShader.setTexcoordAttribute(this.glPolyTexcoord, 2, 5126, 0, 4 * (i2 * 2));
          localBaseShader.setTexture(localTexture);
        }
        this.pgl.bindBuffer(34963, this.glPolyIndex);
        this.pgl.drawElements(4, i1, 5123, n * 2);
        this.pgl.bindBuffer(34963, 0);
        m++;
        break label105;
        bool2 = false;
        break;
        n = localIndexCache.indexOffset[m];
        break label130;
      }
      label430:
      localBaseShader.unbind();
    }
    this.texCache.endRender();
    unbindPolyBuffers();
  }
  
  public void frustum(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    flush();
    float f1 = 2.0F * paramFloat5;
    float f2 = paramFloat2 - paramFloat1;
    float f3 = paramFloat4 - paramFloat3;
    float f4 = paramFloat6 - paramFloat5;
    this.projection.set(f1 / f2, 0.0F, (paramFloat2 + paramFloat1) / f2, 0.0F, 0.0F, -f1 / f3, (paramFloat4 + paramFloat3) / f3, 0.0F, 0.0F, 0.0F, -(paramFloat6 + paramFloat5) / f4, -(f1 * paramFloat6) / f4, 0.0F, 0.0F, -1.0F, 0.0F);
    updateProjmodelview();
  }
  
  public int get(int paramInt1, int paramInt2)
  {
    loadPixels();
    this.setgetPixels = true;
    return super.get(paramInt1, paramInt2);
  }
  
  protected FontTexture getFontTexture(PFont paramPFont)
  {
    return (FontTexture)this.fontMap.get(paramPFont);
  }
  
  protected void getGLParameters()
  {
    OPENGL_VENDOR = this.pgl.getString(7936);
    OPENGL_RENDERER = this.pgl.getString(7937);
    OPENGL_VERSION = this.pgl.getString(7938);
    OPENGL_EXTENSIONS = this.pgl.getString(7939);
    GLSL_VERSION = this.pgl.getString(35724);
    if ((this.pgl.getGLVersion()[0] < 2) && ((OPENGL_EXTENSIONS.indexOf("_fragment_shader") == -1) || (OPENGL_EXTENSIONS.indexOf("_vertex_shader") == -1) || (OPENGL_EXTENSIONS.indexOf("_shader_objects") == -1) || (OPENGL_EXTENSIONS.indexOf("_shading_language") == -1))) {
      throw new RuntimeException("Processing cannot run because GLSL shaders are not available.");
    }
    boolean bool1;
    if (-1 < OPENGL_EXTENSIONS.indexOf("_texture_non_power_of_two")) {
      bool1 = true;
    }
    for (;;)
    {
      npotTexSupported = bool1;
      boolean bool2;
      label175:
      boolean bool3;
      label194:
      boolean bool4;
      label214:
      boolean bool5;
      if (-1 < OPENGL_EXTENSIONS.indexOf("_generate_mipmap"))
      {
        bool2 = true;
        autoMipmapGenSupported = bool2;
        if (-1 >= OPENGL_EXTENSIONS.indexOf("_framebuffer_multisample")) {
          break label420;
        }
        bool3 = true;
        fboMultisampleSupported = bool3;
        if (-1 >= OPENGL_EXTENSIONS.indexOf("_packed_depth_stencil")) {
          break label425;
        }
        bool4 = true;
        packedDepthStencilSupported = bool4;
        if (-1 >= OPENGL_EXTENSIONS.indexOf("_texture_filter_anisotropic")) {
          break label431;
        }
        bool5 = true;
        anisoSamplingSupported = bool5;
      }
      try
      {
        this.pgl.blendEquation(32774);
        blendEqSupported = true;
        depthBits = this.pgl.getDepthBits();
        stencilBits = this.pgl.getStencilBits();
        this.pgl.getIntegerv(3379, this.intBuffer);
        maxTextureSize = this.intBuffer.get(0);
        this.pgl.getIntegerv(-1, this.intBuffer);
        maxSamples = this.intBuffer.get(0);
        this.pgl.getIntegerv(33902, this.intBuffer);
        maxLineWidth = this.intBuffer.get(0);
        this.pgl.getIntegerv(33901, this.intBuffer);
        maxPointSize = this.intBuffer.get(0);
        if (anisoSamplingSupported)
        {
          this.pgl.getFloatv(34047, this.floatBuffer);
          maxAnisoAmount = this.floatBuffer.get(0);
        }
        glParamsRead = true;
        return;
        bool1 = false;
        continue;
        bool2 = false;
        break label175;
        label420:
        bool3 = false;
        break label194;
        label425:
        bool4 = false;
        break label214;
        label431:
        bool5 = false;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          blendEqSupported = false;
        }
      }
    }
  }
  
  protected boolean getHint(int paramInt)
  {
    if (paramInt > 0) {
      return this.hints[paramInt];
    }
    return this.hints[(-paramInt)] == 0;
  }
  
  protected void getImpl(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PImage paramPImage, int paramInt5, int paramInt6)
  {
    loadPixels();
    this.setgetPixels = true;
    super.getImpl(paramInt1, paramInt2, paramInt3, paramInt4, paramPImage, paramInt5, paramInt6);
  }
  
  protected LineShader getLineShader()
  {
    if (this.lineShader == null) {
      if (defLineShader == null) {
        defLineShader = new LineShader(this.parent, defLineShaderVertURL, defLineShaderFragURL);
      }
    }
    for (LineShader localLineShader = defLineShader;; localLineShader = this.lineShader)
    {
      localLineShader.setRenderer(this);
      localLineShader.loadAttributes();
      localLineShader.loadUniforms();
      return localLineShader;
    }
  }
  
  public PMatrix3D getMatrix(PMatrix3D paramPMatrix3D)
  {
    if (paramPMatrix3D == null) {
      paramPMatrix3D = new PMatrix3D();
    }
    paramPMatrix3D.set(this.modelview);
    return paramPMatrix3D;
  }
  
  public PMatrix getMatrix()
  {
    return this.modelview.get();
  }
  
  protected PointShader getPointShader()
  {
    if (this.pointShader == null) {
      if (defPointShader == null) {
        defPointShader = new PointShader(this.parent, defPointShaderVertURL, defPointShaderFragURL);
      }
    }
    for (PointShader localPointShader = defPointShader;; localPointShader = this.pointShader)
    {
      localPointShader.setRenderer(this);
      localPointShader.loadAttributes();
      localPointShader.loadUniforms();
      return localPointShader;
    }
  }
  
  protected BaseShader getPolyShader(boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject;
    if (paramBoolean1) {
      if (paramBoolean2) {
        if (this.texlightShader == null)
        {
          if (defTexlightShader == null) {
            defTexlightShader = new TexlightShader(this.parent, defTexlightShaderVertURL, defTextureShaderFragURL);
          }
          localObject = defTexlightShader;
          texlightShaderCheck();
        }
      }
    }
    for (;;)
    {
      ((BaseShader)localObject).setRenderer(this);
      ((BaseShader)localObject).loadAttributes();
      ((BaseShader)localObject).loadUniforms();
      return localObject;
      localObject = this.texlightShader;
      continue;
      if (this.lightShader == null)
      {
        if (defLightShader == null) {
          defLightShader = new LightShader(this.parent, defLightShaderVertURL, defColorShaderFragURL);
        }
        localObject = defLightShader;
        lightShaderCheck();
      }
      else
      {
        localObject = this.lightShader;
        continue;
        if (paramBoolean2)
        {
          if (this.textureShader == null)
          {
            if (defTextureShader == null) {
              defTextureShader = new TexureShader(this.parent, defTextureShaderVertURL, defTextureShaderFragURL);
            }
            localObject = defTextureShader;
            textureShaderCheck();
          }
          else
          {
            localObject = this.textureShader;
          }
        }
        else if (this.colorShader == null)
        {
          if (defColorShader == null) {
            defColorShader = new ColorShader(this.parent, defColorShaderVertURL, defColorShaderFragURL);
          }
          localObject = defColorShader;
          colorShaderCheck();
        }
        else
        {
          localObject = this.colorShader;
        }
      }
    }
  }
  
  protected int getShaderType(String paramString)
  {
    String[] arrayOfString = this.parent.loadStrings(paramString);
    int i = -1;
    int j = 0;
    if (j < arrayOfString.length)
    {
      if (arrayOfString[j].equals("#define PROCESSING_POINT_SHADER")) {
        i = 5;
      }
      for (;;)
      {
        j++;
        break;
        if (arrayOfString[j].equals("#define PROCESSING_LINE_SHADER")) {
          i = 4;
        } else if (arrayOfString[j].equals("#define PROCESSING_COLOR_SHADER")) {
          i = 0;
        } else if (arrayOfString[j].equals("#define PROCESSING_LIGHT_SHADER")) {
          i = 1;
        } else if (arrayOfString[j].equals("#define PROCESSING_TEXTURE_SHADER")) {
          i = 2;
        } else if (arrayOfString[j].equals("#define PROCESSING_TEXLIGHT_SHADER")) {
          i = 3;
        }
      }
    }
    return i;
  }
  
  public Texture getTexture()
  {
    loadTexture();
    return this.texture;
  }
  
  public Texture getTexture(PImage paramPImage)
  {
    Texture localTexture = (Texture)initCache(paramPImage);
    if (localTexture == null) {
      return null;
    }
    if (paramPImage.isModified())
    {
      if ((paramPImage.width != localTexture.width) || (paramPImage.height != localTexture.height)) {
        localTexture.init(paramPImage.width, paramPImage.height);
      }
      updateTexture(paramPImage, localTexture);
    }
    if (localTexture.hasBuffers()) {
      localTexture.bufferUpdate();
    }
    checkTexture(localTexture);
    return localTexture;
  }
  
  public void hint(int paramInt)
  {
    int i = this.hints[PApplet.abs(paramInt)];
    super.hint(paramInt);
    if (i == this.hints[PApplet.abs(paramInt)]) {}
    do
    {
      do
      {
        return;
        if (paramInt == 2)
        {
          flush();
          this.pgl.disable(2929);
          return;
        }
        if (paramInt == -2)
        {
          flush();
          this.pgl.enable(2929);
          return;
        }
        if (paramInt == 5)
        {
          flush();
          this.pgl.depthMask(false);
          return;
        }
        if (paramInt == -5)
        {
          flush();
          this.pgl.depthMask(true);
          return;
        }
        if (paramInt == -6)
        {
          flush();
          setFlushMode(1);
          return;
        }
        if (paramInt == 6)
        {
          flush();
          setFlushMode(0);
          return;
        }
        if (paramInt != -7) {
          break;
        }
      } while ((this.tessGeo.lineVertexCount <= 0) || (this.tessGeo.lineIndexCount <= 0));
      flush();
      return;
    } while ((paramInt != 7) || (this.tessGeo.lineVertexCount <= 0) || (this.tessGeo.lineIndexCount <= 0));
    flush();
  }
  
  public Object initCache(PImage paramPImage)
  {
    Object localObject;
    if (!checkGLThread()) {
      localObject = null;
    }
    do
    {
      do
      {
        return localObject;
        localObject = (Texture)pgPrimary.getCache(paramPImage);
      } while ((localObject != null) && (!((Texture)localObject).contextIsOutdated()));
      localObject = addTexture(paramPImage);
    } while (localObject == null);
    paramPImage.loadPixels();
    ((Texture)localObject).set(paramPImage.pixels);
    return localObject;
  }
  
  protected void initOffscreen()
  {
    loadTextureImpl(4, false);
    if (this.offscreenFramebuffer != null) {
      this.offscreenFramebuffer.release();
    }
    if (this.multisampleFramebuffer != null) {
      this.multisampleFramebuffer.release();
    }
    boolean bool;
    if ((depthBits == 24) && (stencilBits == 8) && (packedDepthStencilSupported))
    {
      bool = true;
      if ((!fboMultisampleSupported) || (1 >= this.quality)) {
        break label190;
      }
      this.multisampleFramebuffer = new FrameBuffer(this.parent, this.texture.glWidth, this.texture.glHeight, this.quality, 0, depthBits, stencilBits, bool, false);
      this.multisampleFramebuffer.clear();
      this.offscreenMultisample = true;
      this.offscreenFramebuffer = new FrameBuffer(this.parent, this.texture.glWidth, this.texture.glHeight, 1, 1, 0, 0, false, false);
    }
    for (;;)
    {
      this.offscreenFramebuffer.setColorBuffer(this.texture);
      this.offscreenFramebuffer.clear();
      this.initialized = true;
      return;
      bool = false;
      break;
      label190:
      this.quality = 0;
      this.offscreenFramebuffer = new FrameBuffer(this.parent, this.texture.glWidth, this.texture.glHeight, 1, 1, depthBits, stencilBits, bool, false);
      this.offscreenMultisample = false;
    }
  }
  
  protected void initPrimary()
  {
    this.pgl.initSurface(this.quality);
    if (this.texture != null)
    {
      pgPrimary.removeCache(this);
      this.ptexture = null;
      this.texture = null;
    }
    pgPrimary = this;
    this.initialized = true;
  }
  
  public boolean isGL()
  {
    return true;
  }
  
  protected void lightAmbient(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    this.lightAmbient[(0 + paramInt * 3)] = this.calcR;
    this.lightAmbient[(1 + paramInt * 3)] = this.calcG;
    this.lightAmbient[(2 + paramInt * 3)] = this.calcB;
  }
  
  protected void lightDiffuse(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    this.lightDiffuse[(0 + paramInt * 3)] = this.calcR;
    this.lightDiffuse[(1 + paramInt * 3)] = this.calcG;
    this.lightDiffuse[(2 + paramInt * 3)] = this.calcB;
  }
  
  public void lightFalloff(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.currentLightFalloffConstant = paramFloat1;
    this.currentLightFalloffLinear = paramFloat2;
    this.currentLightFalloffQuadratic = paramFloat3;
  }
  
  protected void lightFalloff(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.lightFalloffCoefficients[(0 + paramInt * 3)] = paramFloat1;
    this.lightFalloffCoefficients[(1 + paramInt * 3)] = paramFloat2;
    this.lightFalloffCoefficients[(2 + paramInt * 3)] = paramFloat3;
  }
  
  protected void lightNormal(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = paramFloat1 * this.modelviewInv.m00 + paramFloat2 * this.modelviewInv.m10 + paramFloat3 * this.modelviewInv.m20;
    float f2 = paramFloat1 * this.modelviewInv.m01 + paramFloat2 * this.modelviewInv.m11 + paramFloat3 * this.modelviewInv.m21;
    float f3 = paramFloat1 * this.modelviewInv.m02 + paramFloat2 * this.modelviewInv.m12 + paramFloat3 * this.modelviewInv.m22;
    float f4 = 1.0F / PApplet.dist(0.0F, 0.0F, 0.0F, f1, f2, f3);
    this.lightNormal[(0 + paramInt * 3)] = (f1 * f4);
    this.lightNormal[(1 + paramInt * 3)] = (f4 * f2);
    this.lightNormal[(2 + paramInt * 3)] = (f4 * f3);
  }
  
  protected void lightPosition(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean)
  {
    this.lightPosition[(0 + paramInt * 4)] = (paramFloat1 * this.modelview.m00 + paramFloat2 * this.modelview.m01 + paramFloat3 * this.modelview.m02 + this.modelview.m03);
    this.lightPosition[(1 + paramInt * 4)] = (paramFloat1 * this.modelview.m10 + paramFloat2 * this.modelview.m11 + paramFloat3 * this.modelview.m12 + this.modelview.m13);
    this.lightPosition[(2 + paramInt * 4)] = (paramFloat1 * this.modelview.m20 + paramFloat2 * this.modelview.m21 + paramFloat3 * this.modelview.m22 + this.modelview.m23);
    float[] arrayOfFloat = this.lightPosition;
    int i = 3 + paramInt * 4;
    if (paramBoolean) {}
    for (float f = 1.0F;; f = 0.0F)
    {
      arrayOfFloat[i] = f;
      return;
    }
  }
  
  protected void lightShaderCheck()
  {
    if ((this.shaderWarningsEnabled) && ((this.texlightShader != null) || (this.textureShader != null) || (this.colorShader != null))) {
      PGraphics.showWarning("Your shader cannot be used to render lit geometry, using default shader instead.");
    }
  }
  
  public void lightSpecular(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    colorCalc(paramFloat1, paramFloat2, paramFloat3);
    this.currentLightSpecular[0] = this.calcR;
    this.currentLightSpecular[1] = this.calcG;
    this.currentLightSpecular[2] = this.calcB;
  }
  
  protected void lightSpecular(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.lightSpecular[(0 + paramInt * 3)] = paramFloat1;
    this.lightSpecular[(1 + paramInt * 3)] = paramFloat2;
    this.lightSpecular[(2 + paramInt * 3)] = paramFloat3;
  }
  
  protected void lightSpot(int paramInt, float paramFloat1, float paramFloat2)
  {
    this.lightSpotParameters[(0 + paramInt * 2)] = Math.max(0.0F, PApplet.cos(paramFloat1));
    this.lightSpotParameters[(1 + paramInt * 2)] = paramFloat2;
  }
  
  public void lights()
  {
    enableLighting();
    int i = this.colorMode;
    this.colorMode = 1;
    lightFalloff(1.0F, 0.0F, 0.0F);
    lightSpecular(0.0F, 0.0F, 0.0F);
    ambientLight(0.5F * this.colorModeX, 0.5F * this.colorModeY, 0.5F * this.colorModeZ);
    directionalLight(0.5F * this.colorModeX, 0.5F * this.colorModeY, 0.5F * this.colorModeZ, 0.0F, 0.0F, -1.0F);
    this.colorMode = i;
  }
  
  public void line(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    lineImpl(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4, 0.0F);
  }
  
  public void line(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    lineImpl(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  protected boolean lineBufferContextIsOutdated()
  {
    return !this.pgl.contextIsCurrent(this.lineBuffersContext);
  }
  
  protected void lineImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    beginShape(5);
    this.defaultEdges = false;
    this.normalMode = 1;
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
    this.inGeo.addLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, this.fill, this.stroke);
    endShape();
  }
  
  public void loadPixels()
  {
    if ((this.primarySurface) && (this.sized)) {}
    int i;
    do
    {
      return;
      boolean bool = this.drawing;
      i = 0;
      if (!bool)
      {
        beginDraw();
        i = 1;
      }
      if (!this.setgetPixels) {
        flush();
      }
      allocatePixels();
      if (!this.setgetPixels) {
        readPixels();
      }
    } while (i == 0);
    endDraw();
  }
  
  public PShader loadShader(String paramString)
  {
    int i = getShaderType(paramString);
    if (i == -1)
    {
      PGraphics.showWarning("The GLSL code doesn't seem to contain a valid shader to use in Processing");
      return null;
    }
    Object localObject;
    if (i == 5)
    {
      localObject = new PointShader(this.parent);
      ((PShader)localObject).setVertexShader(defPointShaderVertURL);
    }
    for (;;)
    {
      ((PShader)localObject).setFragmentShader(paramString);
      return localObject;
      if (i == 4)
      {
        localObject = new LineShader(this.parent);
        ((PShader)localObject).setVertexShader(defLineShaderVertURL);
      }
      else if (i == 3)
      {
        localObject = new TexlightShader(this.parent);
        ((PShader)localObject).setVertexShader(defTexlightShaderVertURL);
      }
      else if (i == 1)
      {
        localObject = new LightShader(this.parent);
        ((PShader)localObject).setVertexShader(defLightShaderVertURL);
      }
      else if (i == 2)
      {
        localObject = new TexureShader(this.parent);
        ((PShader)localObject).setVertexShader(defTextureShaderVertURL);
      }
      else
      {
        localObject = null;
        if (i == 0)
        {
          localObject = new ColorShader(this.parent);
          ((PShader)localObject).setVertexShader(defColorShaderVertURL);
        }
      }
    }
  }
  
  public PShader loadShader(String paramString1, String paramString2)
  {
    int i = getShaderType(paramString2);
    if (i == -1) {}
    for (int j = getShaderType(paramString1);; j = i)
    {
      Object localObject;
      if (j == -1)
      {
        PGraphics.showWarning("The GLSL code doesn't seem to contain a valid shader to use in Processing");
        localObject = null;
      }
      do
      {
        for (;;)
        {
          return localObject;
          if ((paramString1 != null) && (!paramString1.equals(""))) {
            break;
          }
          if (j == 5)
          {
            localObject = new PointShader(this.parent);
            ((PShader)localObject).setFragmentShader(defPointShaderFragURL);
          }
          while (localObject != null)
          {
            ((PShader)localObject).setVertexShader(paramString2);
            return localObject;
            if (j == 4)
            {
              localObject = new LineShader(this.parent);
              ((PShader)localObject).setFragmentShader(defLineShaderFragURL);
            }
            else if (j == 3)
            {
              localObject = new TexlightShader(this.parent);
              ((PShader)localObject).setFragmentShader(defTextureShaderFragURL);
            }
            else if (j == 1)
            {
              localObject = new LightShader(this.parent);
              ((PShader)localObject).setFragmentShader(defColorShaderFragURL);
            }
            else if (j == 2)
            {
              localObject = new TexureShader(this.parent);
              ((PShader)localObject).setFragmentShader(defTextureShaderFragURL);
            }
            else
            {
              localObject = null;
              if (j == 0)
              {
                localObject = new ColorShader(this.parent);
                ((PShader)localObject).setFragmentShader(defColorShaderFragURL);
              }
            }
          }
        }
        if (j == 5) {
          return new PointShader(this.parent, paramString2, paramString1);
        }
        if (j == 4) {
          return new LineShader(this.parent, paramString2, paramString1);
        }
        if (j == 3) {
          return new TexlightShader(this.parent, paramString2, paramString1);
        }
        if (j == 1) {
          return new LightShader(this.parent, paramString2, paramString1);
        }
        if (j == 2) {
          return new TexureShader(this.parent, paramString2, paramString1);
        }
        localObject = null;
      } while (j != 0);
      return new ColorShader(this.parent, paramString2, paramString1);
    }
  }
  
  public PShape loadShape(String paramString)
  {
    String str = PApplet.getExtension(paramString);
    if (PGraphics2D.isSupportedExtension(str)) {
      return PGraphics2D.loadShapeImpl(this, paramString, str);
    }
    if (PGraphics3D.isSupportedExtension(str)) {
      return PGraphics3D.loadShapeImpl(this, paramString, str);
    }
    PGraphics.showWarning("Unsupported shape format");
    return null;
  }
  
  public void loadTexture()
  {
    if (!this.drawing) {
      beginDraw();
    }
    for (int i = 1;; i = 0)
    {
      flush();
      if (this.primarySurface) {
        if (this.pgl.isFBOBacked()) {
          this.pgl.syncBackTexture();
        }
      }
      for (;;)
      {
        if (i != 0) {
          endDraw();
        }
        return;
        loadTextureImpl(2, false);
        if ((this.nativePixels == null) || (this.nativePixels.length < this.width * this.height))
        {
          this.nativePixels = new int[this.width * this.height];
          this.nativePixelBuffer = PGL.allocateIntBuffer(this.nativePixels);
        }
        beginPixelsOp(1);
        try
        {
          this.pgl.readPixels(0, 0, this.width, this.height, 6408, 5121, this.nativePixelBuffer);
          label138:
          endPixelsOp();
          this.texture.setNative(this.nativePixelBuffer, 0, 0, this.width, this.height);
          continue;
          if (this.offscreenMultisample) {
            this.multisampleFramebuffer.copy(this.offscreenFramebuffer, currentFramebuffer);
          }
        }
        catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
        {
          break label138;
        }
      }
    }
  }
  
  protected void loadTextureImpl(int paramInt, boolean paramBoolean)
  {
    if ((this.width == 0) || (this.height == 0)) {}
    Texture.Parameters localParameters;
    do
    {
      do
      {
        return;
      } while ((this.texture != null) && (!this.texture.contextIsOutdated()));
      localParameters = new Texture.Parameters(2, paramInt, paramBoolean);
      this.texture = new Texture(this.parent, this.width, this.height, localParameters);
      this.texture.invertedY(true);
      this.texture.colorBufferOf(this);
      pgPrimary.setCache(this, this.texture);
    } while (this.primarySurface);
    this.ptexture = new Texture(this.parent, this.width, this.height, localParameters);
    this.ptexture.invertedY(true);
    this.ptexture.colorBufferOf(this);
  }
  
  public void mask(PImage paramPImage)
  {
    if ((paramPImage.width != this.width) || (paramPImage.height != this.height)) {
      throw new RuntimeException("The PImage used with mask() must be the same size as the applet.");
    }
    if (maskShader == null) {
      maskShader = new TexureShader(this.parent, defTextureShaderVertURL, maskShaderFragURL);
    }
    maskShader.set("mask", paramPImage);
    filter(maskShader);
  }
  
  public float modelX(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = paramFloat1 * this.modelview.m00 + paramFloat2 * this.modelview.m01 + paramFloat3 * this.modelview.m02 + this.modelview.m03;
    float f2 = paramFloat1 * this.modelview.m10 + paramFloat2 * this.modelview.m11 + paramFloat3 * this.modelview.m12 + this.modelview.m13;
    float f3 = paramFloat1 * this.modelview.m20 + paramFloat2 * this.modelview.m21 + paramFloat3 * this.modelview.m22 + this.modelview.m23;
    float f4 = paramFloat1 * this.modelview.m30 + paramFloat2 * this.modelview.m31 + paramFloat3 * this.modelview.m32 + this.modelview.m33;
    float f5 = f1 * this.cameraInv.m00 + f2 * this.cameraInv.m01 + f3 * this.cameraInv.m02 + f4 * this.cameraInv.m03;
    float f6 = f1 * this.cameraInv.m30 + f2 * this.cameraInv.m31 + f3 * this.cameraInv.m32 + f4 * this.cameraInv.m33;
    if (nonZero(f6)) {
      f5 /= f6;
    }
    return f5;
  }
  
  public float modelY(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = paramFloat1 * this.modelview.m00 + paramFloat2 * this.modelview.m01 + paramFloat3 * this.modelview.m02 + this.modelview.m03;
    float f2 = paramFloat1 * this.modelview.m10 + paramFloat2 * this.modelview.m11 + paramFloat3 * this.modelview.m12 + this.modelview.m13;
    float f3 = paramFloat1 * this.modelview.m20 + paramFloat2 * this.modelview.m21 + paramFloat3 * this.modelview.m22 + this.modelview.m23;
    float f4 = paramFloat1 * this.modelview.m30 + paramFloat2 * this.modelview.m31 + paramFloat3 * this.modelview.m32 + this.modelview.m33;
    float f5 = f1 * this.cameraInv.m10 + f2 * this.cameraInv.m11 + f3 * this.cameraInv.m12 + f4 * this.cameraInv.m13;
    float f6 = f1 * this.cameraInv.m30 + f2 * this.cameraInv.m31 + f3 * this.cameraInv.m32 + f4 * this.cameraInv.m33;
    if (nonZero(f6)) {
      f5 /= f6;
    }
    return f5;
  }
  
  public float modelZ(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = paramFloat1 * this.modelview.m00 + paramFloat2 * this.modelview.m01 + paramFloat3 * this.modelview.m02 + this.modelview.m03;
    float f2 = paramFloat1 * this.modelview.m10 + paramFloat2 * this.modelview.m11 + paramFloat3 * this.modelview.m12 + this.modelview.m13;
    float f3 = paramFloat1 * this.modelview.m20 + paramFloat2 * this.modelview.m21 + paramFloat3 * this.modelview.m22 + this.modelview.m23;
    float f4 = paramFloat1 * this.modelview.m30 + paramFloat2 * this.modelview.m31 + paramFloat3 * this.modelview.m32 + this.modelview.m33;
    float f5 = f1 * this.cameraInv.m20 + f2 * this.cameraInv.m21 + f3 * this.cameraInv.m22 + f4 * this.cameraInv.m23;
    float f6 = f1 * this.cameraInv.m30 + f2 * this.cameraInv.m31 + f3 * this.cameraInv.m32 + f4 * this.cameraInv.m33;
    if (nonZero(f6)) {
      f5 /= f6;
    }
    return f5;
  }
  
  protected InGeometry newInGeometry(int paramInt)
  {
    return new InGeometry(paramInt);
  }
  
  protected TessGeometry newTessGeometry(int paramInt)
  {
    return new TessGeometry(paramInt);
  }
  
  protected TexCache newTexCache()
  {
    return new TexCache();
  }
  
  public void noClip()
  {
    if (this.clip)
    {
      flush();
      this.pgl.disable(3089);
      this.clip = false;
    }
  }
  
  protected void noLightAmbient(int paramInt)
  {
    this.lightAmbient[(0 + paramInt * 3)] = 0.0F;
    this.lightAmbient[(1 + paramInt * 3)] = 0.0F;
    this.lightAmbient[(2 + paramInt * 3)] = 0.0F;
  }
  
  protected void noLightDiffuse(int paramInt)
  {
    this.lightDiffuse[(0 + paramInt * 3)] = 0.0F;
    this.lightDiffuse[(1 + paramInt * 3)] = 0.0F;
    this.lightDiffuse[(2 + paramInt * 3)] = 0.0F;
  }
  
  protected void noLightFalloff(int paramInt)
  {
    this.lightFalloffCoefficients[(0 + paramInt * 3)] = 1.0F;
    this.lightFalloffCoefficients[(1 + paramInt * 3)] = 0.0F;
    this.lightFalloffCoefficients[(2 + paramInt * 3)] = 0.0F;
  }
  
  protected void noLightSpecular(int paramInt)
  {
    this.lightSpecular[(0 + paramInt * 3)] = 0.0F;
    this.lightSpecular[(1 + paramInt * 3)] = 0.0F;
    this.lightSpecular[(2 + paramInt * 3)] = 0.0F;
  }
  
  protected void noLightSpot(int paramInt)
  {
    this.lightSpotParameters[(0 + paramInt * 2)] = 0.0F;
    this.lightSpotParameters[(1 + paramInt * 2)] = 0.0F;
  }
  
  public void noLights()
  {
    disableLighting();
    this.lightCount = 0;
  }
  
  public void noSmooth()
  {
    if (this.smoothDisabled) {}
    do
    {
      return;
      this.smooth = false;
    } while (1 >= this.quality);
    this.smoothCallCount = (1 + this.smoothCallCount);
    if ((this.parent.frameCount - this.lastSmoothCall < 30) && (5 < this.smoothCallCount))
    {
      this.smoothDisabled = true;
      PGraphics.showWarning("The smooth/noSmooth functions are being called too often.\nThis results in screen flickering, so they will be disabled\nfor the rest of the sketch's execution");
    }
    this.lastSmoothCall = this.parent.frameCount;
    this.quality = 0;
    restartPGL();
  }
  
  protected boolean nonOrthoProjection()
  {
    return (nonZero(this.projection.m01)) || (nonZero(this.projection.m02)) || (nonZero(this.projection.m10)) || (nonZero(this.projection.m12)) || (nonZero(this.projection.m20)) || (nonZero(this.projection.m21)) || (nonZero(this.projection.m30)) || (nonZero(this.projection.m31)) || (nonZero(this.projection.m32)) || (diff(this.projection.m33, 1.0F));
  }
  
  public void ortho()
  {
    ortho(0.0F, this.width, 0.0F, this.height, this.cameraNear, this.cameraFar);
  }
  
  public void ortho(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    ortho(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.cameraNear, this.cameraFar);
  }
  
  public void ortho(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    float f1 = paramFloat1 - this.width / 2;
    float f2 = paramFloat2 - this.width / 2;
    float f3 = paramFloat3 - this.height / 2;
    float f4 = paramFloat4 - this.height / 2;
    flush();
    float f5 = 2.0F / (f2 - f1);
    float f6 = 2.0F / (f4 - f3);
    float f7 = -2.0F / (paramFloat6 - paramFloat5);
    float f8 = -(f2 + f1) / (f2 - f1);
    float f9 = -(f4 + f3) / (f4 - f3);
    float f10 = -(paramFloat6 + paramFloat5) / (paramFloat6 - paramFloat5);
    this.projection.set(f5, 0.0F, 0.0F, f8, 0.0F, -f6, 0.0F, f9, 0.0F, 0.0F, f7, f10, 0.0F, 0.0F, 0.0F, 1.0F);
    updateProjmodelview();
  }
  
  protected boolean orthoProjection()
  {
    return (zero(this.projection.m01)) && (zero(this.projection.m02)) && (zero(this.projection.m10)) && (zero(this.projection.m12)) && (zero(this.projection.m20)) && (zero(this.projection.m21)) && (zero(this.projection.m30)) && (zero(this.projection.m31)) && (zero(this.projection.m32)) && (same(this.projection.m33, 1.0F));
  }
  
  public void perspective()
  {
    perspective(this.cameraFOV, this.cameraAspect, this.cameraNear, this.cameraFar);
  }
  
  public void perspective(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramFloat3 * (float)Math.tan(paramFloat1 / 2.0F);
    float f2 = -f1;
    frustum(f2 * paramFloat2, f1 * paramFloat2, f2, f1, paramFloat3, paramFloat4);
  }
  
  public void point(float paramFloat1, float paramFloat2)
  {
    pointImpl(paramFloat1, paramFloat2, 0.0F);
  }
  
  public void point(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    pointImpl(paramFloat1, paramFloat2, paramFloat3);
  }
  
  protected boolean pointBuffersContextIsOutdated()
  {
    return !this.pgl.contextIsCurrent(this.pointBuffersContext);
  }
  
  protected void pointImpl(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    beginShape(3);
    this.defaultEdges = false;
    this.normalMode = 1;
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
    this.inGeo.addPoint(paramFloat1, paramFloat2, paramFloat3, this.fill, this.stroke);
    endShape();
  }
  
  public void pointLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    enableLighting();
    if (this.lightCount == 8) {
      throw new RuntimeException("can only create 8 lights");
    }
    this.lightType[this.lightCount] = 2;
    lightPosition(this.lightCount, paramFloat4, paramFloat5, paramFloat6, false);
    lightNormal(this.lightCount, 0.0F, 0.0F, 0.0F);
    noLightAmbient(this.lightCount);
    lightDiffuse(this.lightCount, paramFloat1, paramFloat2, paramFloat3);
    lightSpecular(this.lightCount, this.currentLightSpecular[0], this.currentLightSpecular[1], this.currentLightSpecular[2]);
    noLightSpot(this.lightCount);
    lightFalloff(this.lightCount, this.currentLightFalloffConstant, this.currentLightFalloffLinear, this.currentLightFalloffQuadratic);
    this.lightCount = (1 + this.lightCount);
  }
  
  protected boolean polyBuffersContextIsOutdated()
  {
    return !this.pgl.contextIsCurrent(this.polyBuffersContext);
  }
  
  protected void popFramebuffer()
  {
    if (fbStackDepth == 0) {
      throw new RuntimeException("popFramebuffer call is unbalanced.");
    }
    fbStackDepth = -1 + fbStackDepth;
    FrameBuffer localFrameBuffer = fbStack[fbStackDepth];
    if (currentFramebuffer != localFrameBuffer)
    {
      currentFramebuffer.finish();
      currentFramebuffer = localFrameBuffer;
      currentFramebuffer.bind();
    }
  }
  
  public void popMatrix()
  {
    if (this.modelviewStackDepth == 0) {
      throw new RuntimeException("Too many calls to popMatrix(), and not enough to pushMatrix().");
    }
    this.modelviewStackDepth = (-1 + this.modelviewStackDepth);
    this.modelview.set(this.modelviewStack[this.modelviewStackDepth]);
    this.modelviewInv.set(this.modelviewInvStack[this.modelviewStackDepth]);
    this.camera.set(this.cameraStack[this.modelviewStackDepth]);
    this.cameraInv.set(this.cameraInvStack[this.modelviewStackDepth]);
    updateProjmodelview();
  }
  
  public void popProjection()
  {
    flush();
    if (this.projectionStackDepth == 0) {
      throw new RuntimeException("Too many calls to popMatrix(), and not enough to pushMatrix().");
    }
    this.projectionStackDepth = (-1 + this.projectionStackDepth);
    this.projection.set(this.projectionStack[this.projectionStackDepth]);
    updateProjmodelview();
  }
  
  public void printCamera()
  {
    this.camera.print();
  }
  
  public void printMatrix()
  {
    this.modelview.print();
  }
  
  public void printProjection()
  {
    this.projection.print();
  }
  
  protected void pushFramebuffer()
  {
    if (fbStackDepth == 16) {
      throw new RuntimeException("Too many pushFramebuffer calls");
    }
    fbStack[fbStackDepth] = currentFramebuffer;
    fbStackDepth = 1 + fbStackDepth;
  }
  
  public void pushMatrix()
  {
    if (this.modelviewStackDepth == 32) {
      throw new RuntimeException("Too many calls to pushMatrix().");
    }
    this.modelview.get(this.modelviewStack[this.modelviewStackDepth]);
    this.modelviewInv.get(this.modelviewInvStack[this.modelviewStackDepth]);
    this.camera.get(this.cameraStack[this.modelviewStackDepth]);
    this.cameraInv.get(this.cameraInvStack[this.modelviewStackDepth]);
    this.modelviewStackDepth = (1 + this.modelviewStackDepth);
  }
  
  public void pushProjection()
  {
    if (this.projectionStackDepth == 32) {
      throw new RuntimeException("Too many calls to pushMatrix().");
    }
    this.projection.get(this.projectionStack[this.projectionStackDepth]);
    this.projectionStackDepth = (1 + this.projectionStackDepth);
  }
  
  public void quad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    beginShape(17);
    this.defaultEdges = false;
    this.normalMode = 1;
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
    this.inGeo.addQuad(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4, 0.0F, paramFloat5, paramFloat6, 0.0F, paramFloat7, paramFloat8, 0.0F, this.fill, this.stroke);
    endShape();
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
    this.inGeo.addQuadraticVertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, this.fill, this.stroke, this.bezierDetail, vertexCode(), this.shape);
  }
  
  void rawLines()
  {
    this.raw.colorMode(1);
    this.raw.noFill();
    this.raw.strokeCap(this.strokeCap);
    this.raw.strokeJoin(this.strokeJoin);
    this.raw.beginShape(5);
    float[] arrayOfFloat1 = this.tessGeo.lineVertices;
    int[] arrayOfInt = this.tessGeo.lineColors;
    float[] arrayOfFloat2 = this.tessGeo.lineDirections;
    short[] arrayOfShort = this.tessGeo.lineIndices;
    IndexCache localIndexCache = this.tessGeo.lineIndexCache;
    for (int i = 0; i < localIndexCache.size; i++)
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
        if (zero(f1)) {}
        for (;;)
        {
          n++;
          break;
          float[] arrayOfFloat3 = { 0.0F, 0.0F, 0.0F, 0.0F };
          float[] arrayOfFloat4 = { 0.0F, 0.0F, 0.0F, 0.0F };
          int i3 = PGL.nativeToJavaARGB(arrayOfInt[i1]);
          int i4 = PGL.nativeToJavaARGB(arrayOfInt[i2]);
          if (this.flushMode == 0)
          {
            float[] arrayOfFloat5 = { 0.0F, 0.0F, 0.0F, 0.0F };
            float[] arrayOfFloat6 = { 0.0F, 0.0F, 0.0F, 0.0F };
            PApplet.arrayCopy(arrayOfFloat1, i1 * 4, arrayOfFloat5, 0, 4);
            PApplet.arrayCopy(arrayOfFloat1, i2 * 4, arrayOfFloat6, 0, 4);
            this.modelview.mult(arrayOfFloat5, arrayOfFloat3);
            this.modelview.mult(arrayOfFloat6, arrayOfFloat4);
          }
          for (;;)
          {
            if (!this.raw.is3D()) {
              break label489;
            }
            this.raw.strokeWeight(f1);
            this.raw.stroke(i3);
            this.raw.vertex(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2]);
            this.raw.strokeWeight(f2);
            this.raw.stroke(i4);
            this.raw.vertex(arrayOfFloat4[0], arrayOfFloat4[1], arrayOfFloat4[2]);
            break;
            PApplet.arrayCopy(arrayOfFloat1, i1 * 4, arrayOfFloat3, 0, 4);
            PApplet.arrayCopy(arrayOfFloat1, i2 * 4, arrayOfFloat4, 0, 4);
          }
          label489:
          if (this.raw.is2D())
          {
            float f3 = screenXImpl(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat3[3]);
            float f4 = screenYImpl(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat3[3]);
            float f5 = screenXImpl(arrayOfFloat4[0], arrayOfFloat4[1], arrayOfFloat4[2], arrayOfFloat4[3]);
            float f6 = screenYImpl(arrayOfFloat4[0], arrayOfFloat4[1], arrayOfFloat4[2], arrayOfFloat4[3]);
            this.raw.strokeWeight(f1);
            this.raw.stroke(i3);
            this.raw.vertex(f3, f4);
            this.raw.strokeWeight(f2);
            this.raw.stroke(i4);
            this.raw.vertex(f5, f6);
          }
        }
      }
    }
    this.raw.endShape();
  }
  
  void rawPoints()
  {
    this.raw.colorMode(1);
    this.raw.noFill();
    this.raw.strokeCap(this.strokeCap);
    this.raw.beginShape(3);
    float[] arrayOfFloat1 = this.tessGeo.pointVertices;
    int[] arrayOfInt = this.tessGeo.pointColors;
    float[] arrayOfFloat2 = this.tessGeo.pointOffsets;
    short[] arrayOfShort = this.tessGeo.pointIndices;
    IndexCache localIndexCache = this.tessGeo.pointIndexCache;
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
        label179:
        int i2;
        int i3;
        float[] arrayOfFloat3;
        if (0.0F < f1)
        {
          f2 = f1 / 0.5F;
          i1 = 1 + PApplet.max(20, (int)(6.283186F * f2 / 10.0F));
          i2 = m + arrayOfShort[(n * 3)];
          i3 = PGL.nativeToJavaARGB(arrayOfInt[i2]);
          arrayOfFloat3 = new float[] { 0.0F, 0.0F, 0.0F, 0.0F };
          if (this.flushMode != 0) {
            break label345;
          }
          float[] arrayOfFloat4 = { 0.0F, 0.0F, 0.0F, 0.0F };
          PApplet.arrayCopy(arrayOfFloat1, i2 * 4, arrayOfFloat4, 0, 4);
          this.modelview.mult(arrayOfFloat4, arrayOfFloat3);
          label273:
          if (!this.raw.is3D()) {
            break label360;
          }
          this.raw.strokeWeight(f2);
          this.raw.stroke(i3);
          this.raw.vertex(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2]);
        }
        for (;;)
        {
          n += i1;
          break;
          f2 = -f1 / 0.5F;
          i1 = 5;
          break label179;
          label345:
          PApplet.arrayCopy(arrayOfFloat1, i2 * 4, arrayOfFloat3, 0, 4);
          break label273;
          label360:
          if (this.raw.is2D())
          {
            float f3 = screenXImpl(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat3[3]);
            float f4 = screenYImpl(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat3[3]);
            this.raw.strokeWeight(f2);
            this.raw.stroke(i3);
            this.raw.vertex(f3, f4);
          }
        }
      }
    }
    this.raw.endShape();
  }
  
  void rawPolys()
  {
    this.raw.colorMode(1);
    this.raw.noStroke();
    this.raw.beginShape(9);
    float[] arrayOfFloat1 = this.tessGeo.polyVertices;
    int[] arrayOfInt = this.tessGeo.polyColors;
    float[] arrayOfFloat2 = this.tessGeo.polyTexCoords;
    short[] arrayOfShort = this.tessGeo.polyIndices;
    for (int i = 0; i < this.texCache.size; i++)
    {
      PImage localPImage = this.texCache.getTextureImage(i);
      int j = this.texCache.firstCache[i];
      int k = this.texCache.lastCache[i];
      IndexCache localIndexCache = this.tessGeo.polyIndexCache;
      for (int m = j; m <= k; m++)
      {
        int n;
        int i1;
        label170:
        int i3;
        label186:
        int i4;
        int i5;
        int i6;
        float[] arrayOfFloat3;
        float[] arrayOfFloat4;
        float[] arrayOfFloat5;
        int i7;
        int i8;
        int i9;
        if (m == j)
        {
          n = this.texCache.firstIndex[i];
          if (m != k) {
            break label647;
          }
          i1 = 1 + (this.texCache.lastIndex[i] - n);
          int i2 = localIndexCache.vertexOffset[m];
          i3 = n / 3;
          if (i3 >= (n + i1) / 3) {
            continue;
          }
          i4 = i2 + arrayOfShort[(0 + i3 * 3)];
          i5 = i2 + arrayOfShort[(1 + i3 * 3)];
          i6 = i2 + arrayOfShort[(2 + i3 * 3)];
          arrayOfFloat3 = new float[] { 0.0F, 0.0F, 0.0F, 0.0F };
          arrayOfFloat4 = new float[] { 0.0F, 0.0F, 0.0F, 0.0F };
          arrayOfFloat5 = new float[] { 0.0F, 0.0F, 0.0F, 0.0F };
          i7 = PGL.nativeToJavaARGB(arrayOfInt[i4]);
          i8 = PGL.nativeToJavaARGB(arrayOfInt[i5]);
          i9 = PGL.nativeToJavaARGB(arrayOfInt[i6]);
          if (this.flushMode != 0) {
            break label672;
          }
          float[] arrayOfFloat6 = { 0.0F, 0.0F, 0.0F, 0.0F };
          float[] arrayOfFloat7 = { 0.0F, 0.0F, 0.0F, 0.0F };
          float[] arrayOfFloat8 = { 0.0F, 0.0F, 0.0F, 0.0F };
          PApplet.arrayCopy(arrayOfFloat1, i4 * 4, arrayOfFloat6, 0, 4);
          PApplet.arrayCopy(arrayOfFloat1, i5 * 4, arrayOfFloat7, 0, 4);
          PApplet.arrayCopy(arrayOfFloat1, i6 * 4, arrayOfFloat8, 0, 4);
          this.modelview.mult(arrayOfFloat6, arrayOfFloat3);
          this.modelview.mult(arrayOfFloat7, arrayOfFloat4);
          this.modelview.mult(arrayOfFloat8, arrayOfFloat5);
          label472:
          if (localPImage == null) {
            break label964;
          }
          this.raw.texture(localPImage);
          if (!this.raw.is3D()) {
            break label711;
          }
          this.raw.fill(i7);
          this.raw.vertex(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat2[(0 + i4 * 2)], arrayOfFloat2[(1 + i4 * 2)]);
          this.raw.fill(i8);
          this.raw.vertex(arrayOfFloat4[0], arrayOfFloat4[1], arrayOfFloat4[2], arrayOfFloat2[(0 + i5 * 2)], arrayOfFloat2[(1 + i5 * 2)]);
          this.raw.fill(i9);
          this.raw.vertex(arrayOfFloat5[0], arrayOfFloat5[1], arrayOfFloat5[2], arrayOfFloat2[(0 + i6 * 2)], arrayOfFloat2[(1 + i6 * 2)]);
        }
        label647:
        label672:
        label964:
        for (;;)
        {
          i3++;
          break label186;
          n = localIndexCache.indexOffset[m];
          break;
          i1 = localIndexCache.indexOffset[m] + localIndexCache.indexCount[m] - n;
          break label170;
          PApplet.arrayCopy(arrayOfFloat1, i4 * 4, arrayOfFloat3, 0, 4);
          PApplet.arrayCopy(arrayOfFloat1, i5 * 4, arrayOfFloat4, 0, 4);
          PApplet.arrayCopy(arrayOfFloat1, i6 * 4, arrayOfFloat5, 0, 4);
          break label472;
          label711:
          if (this.raw.is2D())
          {
            float f7 = screenXImpl(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat3[3]);
            float f8 = screenYImpl(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat3[3]);
            float f9 = screenXImpl(arrayOfFloat4[0], arrayOfFloat4[1], arrayOfFloat4[2], arrayOfFloat4[3]);
            float f10 = screenYImpl(arrayOfFloat4[0], arrayOfFloat4[1], arrayOfFloat4[2], arrayOfFloat4[3]);
            float f11 = screenXImpl(arrayOfFloat5[0], arrayOfFloat5[1], arrayOfFloat5[2], arrayOfFloat5[3]);
            float f12 = screenYImpl(arrayOfFloat5[0], arrayOfFloat5[1], arrayOfFloat5[2], arrayOfFloat5[3]);
            this.raw.fill(i7);
            this.raw.vertex(f7, f8, arrayOfFloat2[(0 + i4 * 2)], arrayOfFloat2[(1 + i4 * 2)]);
            this.raw.fill(i8);
            this.raw.vertex(f9, f10, arrayOfFloat2[(0 + i5 * 2)], arrayOfFloat2[(1 + i5 * 2)]);
            this.raw.fill(i8);
            this.raw.vertex(f11, f12, arrayOfFloat2[(0 + i6 * 2)], arrayOfFloat2[(1 + i6 * 2)]);
            continue;
            if (this.raw.is3D())
            {
              this.raw.fill(i7);
              this.raw.vertex(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2]);
              this.raw.fill(i8);
              this.raw.vertex(arrayOfFloat4[0], arrayOfFloat4[1], arrayOfFloat4[2]);
              this.raw.fill(i9);
              this.raw.vertex(arrayOfFloat5[0], arrayOfFloat5[1], arrayOfFloat5[2]);
            }
            else if (this.raw.is2D())
            {
              float f1 = screenXImpl(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat3[3]);
              float f2 = screenYImpl(arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat3[3]);
              float f3 = screenXImpl(arrayOfFloat4[0], arrayOfFloat4[1], arrayOfFloat4[2], arrayOfFloat4[3]);
              float f4 = screenYImpl(arrayOfFloat4[0], arrayOfFloat4[1], arrayOfFloat4[2], arrayOfFloat4[3]);
              float f5 = screenXImpl(arrayOfFloat5[0], arrayOfFloat5[1], arrayOfFloat5[2], arrayOfFloat5[3]);
              float f6 = screenYImpl(arrayOfFloat5[0], arrayOfFloat5[1], arrayOfFloat5[2], arrayOfFloat5[3]);
              this.raw.fill(i7);
              this.raw.vertex(f1, f2);
              this.raw.fill(i8);
              this.raw.vertex(f3, f4);
              this.raw.fill(i9);
              this.raw.vertex(f5, f6);
            }
          }
        }
      }
    }
    this.raw.endShape();
  }
  
  protected void readPixels()
  {
    beginPixelsOp(1);
    try
    {
      this.pgl.readPixels(0, 0, this.width, this.height, 6408, 5121, this.pixelBuffer);
      label32:
      endPixelsOp();
      try
      {
        PGL.getIntArray(this.pixelBuffer, this.pixels);
        PGL.nativeToJavaARGB(this.pixels, this.width, this.height);
        return;
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      break label32;
    }
  }
  
  public void rect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    beginShape(17);
    this.defaultEdges = false;
    this.normalMode = 1;
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
    this.inGeo.addRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.fill, this.stroke, this.rectMode);
    endShape();
  }
  
  public void rect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    beginShape(20);
    this.defaultEdges = false;
    this.normalMode = 1;
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
    this.inGeo.addRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, this.fill, this.stroke, this.bezierDetail, this.rectMode);
    endShape(2);
  }
  
  protected void removeFontTexture(PFont paramPFont)
  {
    this.fontMap.remove(paramPFont);
  }
  
  protected void removeFrameBufferObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glFrameBuffers.containsKey(localGLResource)) {
      glFrameBuffers.remove(localGLResource);
    }
  }
  
  protected void removeGLSLFragShaderObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glslFragmentShaders.containsKey(localGLResource)) {
      glslFragmentShaders.remove(localGLResource);
    }
  }
  
  protected void removeGLSLProgramObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glslPrograms.containsKey(localGLResource)) {
      glslPrograms.remove(localGLResource);
    }
  }
  
  protected void removeGLSLVertShaderObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glslVertexShaders.containsKey(localGLResource)) {
      glslVertexShaders.remove(localGLResource);
    }
  }
  
  protected void removeRenderBufferObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glRenderBuffers.containsKey(localGLResource)) {
      glRenderBuffers.remove(localGLResource);
    }
  }
  
  protected void removeTextureObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glTextureObjects.containsKey(localGLResource)) {
      glTextureObjects.remove(localGLResource);
    }
  }
  
  protected void removeVertexBufferObject(int paramInt1, int paramInt2)
  {
    GLResource localGLResource = new GLResource(paramInt1, paramInt2);
    if (glVertexBuffers.containsKey(localGLResource)) {
      glVertexBuffers.remove(localGLResource);
    }
  }
  
  public void report(String paramString)
  {
    if (this.hints[4] == 0)
    {
      int i = this.pgl.getError();
      if (i != 0)
      {
        String str = this.pgl.errorString(i);
        PGraphics.showWarning("OpenGL error " + i + " at " + paramString + ": " + str);
      }
    }
  }
  
  public void requestDraw()
  {
    if (this.primarySurface)
    {
      if (this.initialized) {
        this.pgl.requestDraw();
      }
    }
    else {
      return;
    }
    initPrimary();
  }
  
  public void resetMatrix()
  {
    this.modelview.reset();
    this.modelviewInv.reset();
    this.projmodelview.set(this.projection);
    this.camera.reset();
    this.cameraInv.reset();
  }
  
  public void resetProjection()
  {
    flush();
    this.projection.reset();
    updateProjmodelview();
  }
  
  public void resetShader()
  {
    resetShader(20);
  }
  
  public void resetShader(int paramInt)
  {
    flush();
    if ((paramInt == 9) || (paramInt == 17) || (paramInt == 20))
    {
      this.textureShader = null;
      this.colorShader = null;
      this.texlightShader = null;
      this.lightShader = null;
      return;
    }
    if (paramInt == 5)
    {
      this.lineShader = null;
      return;
    }
    if (paramInt == 3)
    {
      this.pointShader = null;
      return;
    }
    PGraphics.showWarning("Unknown shader kind");
  }
  
  public void resize(int paramInt1, int paramInt2)
  {
    PGraphics.showMethodWarning("resize");
  }
  
  protected void restartPGL()
  {
    this.initialized = false;
  }
  
  protected void restoreGL()
  {
    setBlendMode(this.blendMode);
    if (this.hints[2] != 0)
    {
      this.pgl.disable(2929);
      this.pgl.depthFunc(515);
      if (this.quality >= 2) {
        break label220;
      }
      this.pgl.disable(-1);
      label53:
      this.pgl.viewport(this.viewport.get(0), this.viewport.get(1), this.viewport.get(2), this.viewport.get(3));
      if (!this.clip) {
        break label255;
      }
      this.pgl.enable(3089);
      this.pgl.scissor(this.clipRect[0], this.clipRect[1], this.clipRect[2], this.clipRect[3]);
      label140:
      this.pgl.frontFace(2304);
      this.pgl.disable(2884);
      this.pgl.activeTexture(33984);
      if (this.hints[5] == 0) {
        break label268;
      }
      this.pgl.depthMask(false);
    }
    for (;;)
    {
      currentFramebuffer.bind();
      this.pgl.drawBuffer(currentFramebuffer.getDefaultDrawBuffer());
      return;
      this.pgl.enable(2929);
      break;
      label220:
      this.pgl.enable(-1);
      this.pgl.disable(-1);
      this.pgl.disable(-1);
      this.pgl.disable(-1);
      break label53;
      label255:
      this.pgl.disable(3089);
      break label140;
      label268:
      this.pgl.depthMask(true);
    }
  }
  
  protected void restoreSurfaceFromPixels()
  {
    drawPixels(0, 0, this.width, this.height);
  }
  
  public void rotate(float paramFloat)
  {
    rotateImpl(paramFloat, 0.0F, 0.0F, 1.0F);
  }
  
  public void rotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    rotateImpl(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  protected void rotateImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramFloat2 * paramFloat2 + paramFloat3 * paramFloat3 + paramFloat4 * paramFloat4;
    if (zero(f1)) {
      return;
    }
    if (diff(f1, 1.0F))
    {
      float f2 = PApplet.sqrt(f1);
      paramFloat2 /= f2;
      paramFloat3 /= f2;
      paramFloat4 /= f2;
    }
    this.modelview.rotate(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    invRotate(this.modelviewInv, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    updateProjmodelview();
  }
  
  public void rotateX(float paramFloat)
  {
    rotateImpl(paramFloat, 1.0F, 0.0F, 0.0F);
  }
  
  public void rotateY(float paramFloat)
  {
    rotateImpl(paramFloat, 0.0F, 1.0F, 0.0F);
  }
  
  public void rotateZ(float paramFloat)
  {
    rotateImpl(paramFloat, 0.0F, 0.0F, 1.0F);
  }
  
  protected void saveSurfaceToPixels()
  {
    allocatePixels();
    readPixels();
  }
  
  public void scale(float paramFloat)
  {
    scaleImpl(paramFloat, paramFloat, paramFloat);
  }
  
  public void scale(float paramFloat1, float paramFloat2)
  {
    scaleImpl(paramFloat1, paramFloat2, 1.0F);
  }
  
  public void scale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    scaleImpl(paramFloat1, paramFloat2, paramFloat3);
  }
  
  protected void scaleImpl(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.modelview.scale(paramFloat1, paramFloat2, paramFloat3);
    invScale(this.modelviewInv, paramFloat1, paramFloat2, paramFloat3);
    this.projmodelview.scale(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public float screenX(float paramFloat1, float paramFloat2)
  {
    return screenXImpl(paramFloat1, paramFloat2, 0.0F);
  }
  
  public float screenX(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return screenXImpl(paramFloat1, paramFloat2, paramFloat3);
  }
  
  protected float screenXImpl(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return screenXImpl(paramFloat1 * this.modelview.m00 + paramFloat2 * this.modelview.m01 + paramFloat3 * this.modelview.m02 + this.modelview.m03, paramFloat1 * this.modelview.m10 + paramFloat2 * this.modelview.m11 + paramFloat3 * this.modelview.m12 + this.modelview.m13, paramFloat1 * this.modelview.m20 + paramFloat2 * this.modelview.m21 + paramFloat3 * this.modelview.m22 + this.modelview.m23, paramFloat1 * this.modelview.m30 + paramFloat2 * this.modelview.m31 + paramFloat3 * this.modelview.m32 + this.modelview.m33);
  }
  
  protected float screenXImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramFloat1 * this.projection.m00 + paramFloat2 * this.projection.m01 + paramFloat3 * this.projection.m02 + paramFloat4 * this.projection.m03;
    float f2 = paramFloat1 * this.projection.m30 + paramFloat2 * this.projection.m31 + paramFloat3 * this.projection.m32 + paramFloat4 * this.projection.m33;
    if (nonZero(f2)) {
      f1 /= f2;
    }
    return this.width * (f1 + 1.0F) / 2.0F;
  }
  
  public float screenY(float paramFloat1, float paramFloat2)
  {
    return screenYImpl(paramFloat1, paramFloat2, 0.0F);
  }
  
  public float screenY(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return screenYImpl(paramFloat1, paramFloat2, paramFloat3);
  }
  
  protected float screenYImpl(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return screenYImpl(paramFloat1 * this.modelview.m00 + paramFloat2 * this.modelview.m01 + paramFloat3 * this.modelview.m02 + this.modelview.m03, paramFloat1 * this.modelview.m10 + paramFloat2 * this.modelview.m11 + paramFloat3 * this.modelview.m12 + this.modelview.m13, paramFloat1 * this.modelview.m20 + paramFloat2 * this.modelview.m21 + paramFloat3 * this.modelview.m22 + this.modelview.m23, paramFloat1 * this.modelview.m30 + paramFloat2 * this.modelview.m31 + paramFloat3 * this.modelview.m32 + this.modelview.m33);
  }
  
  protected float screenYImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramFloat1 * this.projection.m10 + paramFloat2 * this.projection.m11 + paramFloat3 * this.projection.m12 + paramFloat4 * this.projection.m13;
    float f2 = paramFloat1 * this.projection.m30 + paramFloat2 * this.projection.m31 + paramFloat3 * this.projection.m32 + paramFloat4 * this.projection.m33;
    if (nonZero(f2)) {
      f1 /= f2;
    }
    float f3 = this.height * (f1 + 1.0F) / 2.0F;
    return this.height - f3;
  }
  
  public float screenZ(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return screenZImpl(paramFloat1, paramFloat2, paramFloat3);
  }
  
  protected float screenZImpl(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return screenZImpl(paramFloat1 * this.modelview.m00 + paramFloat2 * this.modelview.m01 + paramFloat3 * this.modelview.m02 + this.modelview.m03, paramFloat1 * this.modelview.m10 + paramFloat2 * this.modelview.m11 + paramFloat3 * this.modelview.m12 + this.modelview.m13, paramFloat1 * this.modelview.m20 + paramFloat2 * this.modelview.m21 + paramFloat3 * this.modelview.m22 + this.modelview.m23, paramFloat1 * this.modelview.m30 + paramFloat2 * this.modelview.m31 + paramFloat3 * this.modelview.m32 + this.modelview.m33);
  }
  
  protected float screenZImpl(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramFloat1 * this.projection.m20 + paramFloat2 * this.projection.m21 + paramFloat3 * this.projection.m22 + paramFloat4 * this.projection.m23;
    float f2 = paramFloat1 * this.projection.m30 + paramFloat2 * this.projection.m31 + paramFloat3 * this.projection.m32 + paramFloat4 * this.projection.m33;
    if (nonZero(f2)) {
      f1 /= f2;
    }
    return (f1 + 1.0F) / 2.0F;
  }
  
  public void set(int paramInt1, int paramInt2, int paramInt3)
  {
    loadPixels();
    this.setgetPixels = true;
    super.set(paramInt1, paramInt2, paramInt3);
  }
  
  protected void setBlendMode(int paramInt)
  {
    this.blendMode = paramInt;
    this.pgl.enable(3042);
    if (paramInt == 0)
    {
      if (blendEqSupported) {
        this.pgl.blendEquation(32774);
      }
      this.pgl.blendFunc(1, 0);
    }
    do
    {
      return;
      if (paramInt == 1)
      {
        if (blendEqSupported) {
          this.pgl.blendEquation(32774);
        }
        this.pgl.blendFunc(770, 771);
        return;
      }
      if (paramInt == 2)
      {
        if (blendEqSupported) {
          this.pgl.blendEquation(32774);
        }
        this.pgl.blendFunc(770, 1);
        return;
      }
      if (paramInt == 4)
      {
        if (blendEqSupported) {
          this.pgl.blendEquation(32774);
        }
        this.pgl.blendFunc(775, 0);
        return;
      }
      if (paramInt == 8)
      {
        if (blendEqSupported)
        {
          this.pgl.blendEquation(32776);
          this.pgl.blendFunc(770, 772);
          return;
        }
        PGraphics.showWarning("blendMode(%1$s) is not supported by this hardware (or driver)", new Object[] { "LIGHTEST" });
        return;
      }
      if (paramInt == 16)
      {
        if (blendEqSupported)
        {
          this.pgl.blendEquation(32775);
          this.pgl.blendFunc(770, 772);
          return;
        }
        PGraphics.showWarning("blendMode(%1$s) is not supported by this hardware (or driver)", new Object[] { "DARKEST" });
        return;
      }
      if (paramInt == 32)
      {
        if (blendEqSupported)
        {
          this.pgl.blendEquation(32779);
          this.pgl.blendFunc(1, 1);
          return;
        }
        PGraphics.showWarning("blendMode(%1$s) is not supported by this hardware (or driver)", new Object[] { "DIFFERENCE" });
        return;
      }
      if (paramInt == 64)
      {
        if (blendEqSupported) {
          this.pgl.blendEquation(32774);
        }
        this.pgl.blendFunc(775, 769);
        return;
      }
      if (paramInt == 128)
      {
        if (blendEqSupported) {
          this.pgl.blendEquation(32774);
        }
        this.pgl.blendFunc(774, 768);
        return;
      }
      if (paramInt == 256)
      {
        if (blendEqSupported) {
          this.pgl.blendEquation(32774);
        }
        this.pgl.blendFunc(775, 1);
        return;
      }
      if (paramInt == 512)
      {
        PGraphics.showWarning("blendMode(%1$s) is not supported by this renderer", new Object[] { "OVERLAY" });
        return;
      }
      if (paramInt == 1024)
      {
        PGraphics.showWarning("blendMode(%1$s) is not supported by this renderer", new Object[] { "HARD_LIGHT" });
        return;
      }
      if (paramInt == 2048)
      {
        PGraphics.showWarning("blendMode(%1$s) is not supported by this renderer", new Object[] { "SOFT_LIGHT" });
        return;
      }
      if (paramInt == 4096)
      {
        PGraphics.showWarning("blendMode(%1$s) is not supported by this renderer", new Object[] { "DODGE" });
        return;
      }
    } while (paramInt != 8192);
    PGraphics.showWarning("blendMode(%1$s) is not supported by this renderer", new Object[] { "BURN" });
  }
  
  protected void setDefaults()
  {
    this.inGeo.clear();
    this.tessGeo.clear();
    this.texCache.clear();
    super.noTexture();
    setBlendMode(1);
    if (this.hints[2] != 0)
    {
      this.pgl.disable(2929);
      this.pgl.depthFunc(515);
      if (this.hints[6] == 0) {
        break label477;
      }
      this.flushMode = 0;
      label74:
      if (this.primarySurface)
      {
        this.pgl.getIntegerv(32937, this.intBuffer);
        int i = this.intBuffer.get(0);
        if ((this.quality != i) && (1 < i) && (1 < this.quality)) {
          this.quality = i;
        }
      }
      if (this.quality >= 2) {
        break label485;
      }
      this.pgl.disable(-1);
      label150:
      this.pgl.disable(-1);
      this.pgl.disable(-1);
      this.pgl.disable(-1);
      this.viewport.put(0, 0);
      this.viewport.put(1, 0);
      this.viewport.put(2, this.width);
      this.viewport.put(3, this.height);
      this.pgl.viewport(this.viewport.get(0), this.viewport.get(1), this.viewport.get(2), this.viewport.get(3));
      if (!this.sized) {
        break label496;
      }
      background(this.backgroundColor);
      defaultPerspective();
      defaultCamera();
      this.sized = false;
      label287:
      if (is3D())
      {
        noLights();
        lightFalloff(1.0F, 0.0F, 0.0F);
        lightSpecular(0.0F, 0.0F, 0.0F);
      }
      this.pgl.frontFace(2304);
      this.pgl.disable(2884);
      this.pgl.activeTexture(33984);
      this.normalZ = 0.0F;
      this.normalY = 0.0F;
      this.normalX = 0.0F;
      this.pgl.depthMask(true);
      this.pgl.clearDepth(1.0F);
      this.pgl.clearStencil(0);
      this.pgl.clear(1280);
      if (!this.settingsInited) {
        defaultSettings();
      }
      if (this.restoreSurface)
      {
        restoreSurfaceFromPixels();
        this.restoreSurface = false;
      }
      if (this.hints[5] == 0) {
        break label525;
      }
      this.pgl.depthMask(false);
    }
    for (;;)
    {
      this.pixelsOp = 0;
      this.modified = false;
      this.setgetPixels = false;
      this.clearColorBuffer0 = this.clearColorBuffer;
      this.clearColorBuffer = false;
      return;
      this.pgl.enable(2929);
      break;
      label477:
      this.flushMode = 1;
      break label74;
      label485:
      this.pgl.enable(-1);
      break label150;
      label496:
      this.modelview.set(this.camera);
      this.modelviewInv.set(this.cameraInv);
      updateProjmodelview();
      break label287;
      label525:
      this.pgl.depthMask(true);
    }
  }
  
  protected void setFlushMode(int paramInt)
  {
    this.flushMode = paramInt;
  }
  
  protected void setFontTexture(PFont paramPFont, FontTexture paramFontTexture)
  {
    this.fontMap.put(paramPFont, paramFontTexture);
  }
  
  public void setFrameRate(float paramFloat)
  {
    this.pgl.setFrameRate(paramFloat);
  }
  
  protected void setFramebuffer(FrameBuffer paramFrameBuffer)
  {
    if (currentFramebuffer != paramFrameBuffer)
    {
      currentFramebuffer = paramFrameBuffer;
      currentFramebuffer.bind();
    }
  }
  
  protected void setImpl(PImage paramPImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    loadPixels();
    this.setgetPixels = true;
    super.setImpl(paramPImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public void setMatrix(PMatrix2D paramPMatrix2D)
  {
    resetMatrix();
    applyMatrix(paramPMatrix2D);
  }
  
  public void setMatrix(PMatrix3D paramPMatrix3D)
  {
    resetMatrix();
    applyMatrix(paramPMatrix3D);
  }
  
  public void setPrimary(boolean paramBoolean)
  {
    super.setPrimary(paramBoolean);
    this.format = 2;
  }
  
  public void setProjection(PMatrix3D paramPMatrix3D)
  {
    flush();
    this.projection.set(paramPMatrix3D);
    updateProjmodelview();
  }
  
  public void setSize(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
    allocate();
    reapplySettings();
    this.cameraFOV = 1.047198F;
    this.cameraX = (this.width / 2.0F);
    this.cameraY = (this.height / 2.0F);
    this.cameraZ = (this.cameraY / (float)Math.tan(this.cameraFOV / 2.0F));
    this.cameraNear = (this.cameraZ / 10.0F);
    this.cameraFar = (10.0F * this.cameraZ);
    this.cameraAspect = (this.width / this.height);
    restartPGL();
    this.sized = true;
  }
  
  public void shader(PShader paramPShader)
  {
    shader(paramPShader, 20);
  }
  
  public void shader(PShader paramPShader, int paramInt)
  {
    flush();
    if ((paramInt == 9) || (paramInt == 17) || (paramInt == 20))
    {
      if ((paramPShader instanceof TexureShader))
      {
        this.textureShader = ((TexureShader)paramPShader);
        return;
      }
      if ((paramPShader instanceof ColorShader))
      {
        this.colorShader = ((ColorShader)paramPShader);
        return;
      }
      if ((paramPShader instanceof TexlightShader))
      {
        this.texlightShader = ((TexlightShader)paramPShader);
        return;
      }
      if ((paramPShader instanceof LightShader))
      {
        this.lightShader = ((LightShader)paramPShader);
        return;
      }
      PGraphics.showWarning("shader() called with a wrong shader");
      return;
    }
    if (paramInt == 5)
    {
      if ((paramPShader instanceof LineShader))
      {
        this.lineShader = ((LineShader)paramPShader);
        return;
      }
      PGraphics.showWarning("shader() called with a wrong shader");
      return;
    }
    if (paramInt == 3)
    {
      if ((paramPShader instanceof PointShader))
      {
        this.pointShader = ((PointShader)paramPShader);
        return;
      }
      PGraphics.showWarning("shader() called with a wrong shader");
      return;
    }
    PGraphics.showWarning("Unknown shader kind");
  }
  
  public void shaderWarnings(boolean paramBoolean)
  {
    this.shaderWarningsEnabled = paramBoolean;
  }
  
  protected void shape(PShape paramPShape, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramPShape.isVisible())
    {
      flush();
      pushMatrix();
      if (this.shapeMode != 3) {
        break label62;
      }
      translate(paramFloat1 - paramPShape.getWidth() / 2.0F, paramFloat2 - paramPShape.getHeight() / 2.0F, paramFloat3 - paramPShape.getDepth() / 2.0F);
    }
    for (;;)
    {
      paramPShape.draw(this);
      popMatrix();
      return;
      label62:
      if ((this.shapeMode == 0) || (this.shapeMode == 1)) {
        translate(paramFloat1, paramFloat2, paramFloat3);
      }
    }
  }
  
  protected void shape(PShape paramPShape, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    if (paramPShape.isVisible())
    {
      flush();
      pushMatrix();
      if (this.shapeMode != 3) {
        break label81;
      }
      translate(paramFloat1 - paramFloat4 / 2.0F, paramFloat2 - paramFloat5 / 2.0F, paramFloat3 - paramFloat6 / 2.0F);
      scale(paramFloat4 / paramPShape.getWidth(), paramFloat5 / paramPShape.getHeight(), paramFloat6 / paramPShape.getDepth());
    }
    for (;;)
    {
      paramPShape.draw(this);
      popMatrix();
      return;
      label81:
      if (this.shapeMode == 0)
      {
        translate(paramFloat1, paramFloat2, paramFloat3);
        scale(paramFloat4 / paramPShape.getWidth(), paramFloat5 / paramPShape.getHeight(), paramFloat6 / paramPShape.getDepth());
      }
      else if (this.shapeMode == 1)
      {
        float f1 = paramFloat4 - paramFloat1;
        float f2 = paramFloat5 - paramFloat2;
        float f3 = paramFloat6 - paramFloat3;
        translate(paramFloat1, paramFloat2, paramFloat3);
        scale(f1 / paramPShape.getWidth(), f2 / paramPShape.getHeight(), f3 / paramPShape.getDepth());
      }
    }
  }
  
  public void shearX(float paramFloat)
  {
    applyMatrixImpl(1.0F, (float)Math.tan(paramFloat), 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void shearY(float paramFloat)
  {
    applyMatrixImpl(1.0F, 0.0F, 0.0F, 0.0F, (float)Math.tan(paramFloat), 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
  }
  
  public void smooth()
  {
    smooth(2);
  }
  
  public void smooth(int paramInt)
  {
    if (this.smoothDisabled) {
      return;
    }
    this.smooth = true;
    if (maxSamples < paramInt)
    {
      if (maxSamples <= 0) {
        break label144;
      }
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      arrayOfObject[1] = Integer.valueOf(maxSamples);
      PGraphics.showWarning("Smooth level %1$s is not available. Using %2$s instead", arrayOfObject);
    }
    for (;;)
    {
      paramInt = maxSamples;
      if (this.quality == paramInt) {
        break;
      }
      this.smoothCallCount = (1 + this.smoothCallCount);
      if ((this.parent.frameCount - this.lastSmoothCall < 30) && (5 < this.smoothCallCount))
      {
        this.smoothDisabled = true;
        PGraphics.showWarning("The smooth/noSmooth functions are being called too often.\nThis results in screen flickering, so they will be disabled\nfor the rest of the sketch's execution");
      }
      this.lastSmoothCall = this.parent.frameCount;
      this.quality = paramInt;
      if (this.quality == 1) {
        this.quality = 0;
      }
      restartPGL();
      return;
      label144:
      PGraphics.showWarning("Smooth is not supported by this hardware (or driver)");
    }
  }
  
  public void sphere(float paramFloat)
  {
    beginShape(9);
    this.defaultEdges = false;
    this.normalMode = 2;
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    endShape(this.inGeo.addSphere(paramFloat, this.sphereDetailU, this.sphereDetailV, this.fill, this.stroke));
  }
  
  public void spotLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11)
  {
    enableLighting();
    if (this.lightCount == 8) {
      throw new RuntimeException("can only create 8 lights");
    }
    this.lightType[this.lightCount] = 3;
    lightPosition(this.lightCount, paramFloat4, paramFloat5, paramFloat6, false);
    lightNormal(this.lightCount, paramFloat7, paramFloat8, paramFloat9);
    noLightAmbient(this.lightCount);
    lightDiffuse(this.lightCount, paramFloat1, paramFloat2, paramFloat3);
    lightSpecular(this.lightCount, this.currentLightSpecular[0], this.currentLightSpecular[1], this.currentLightSpecular[2]);
    lightSpot(this.lightCount, paramFloat10, paramFloat11);
    lightFalloff(this.lightCount, this.currentLightFalloffConstant, this.currentLightFalloffLinear, this.currentLightFalloffQuadratic);
    this.lightCount = (1 + this.lightCount);
  }
  
  public void strokeCap(int paramInt)
  {
    this.strokeCap = paramInt;
  }
  
  public void strokeJoin(int paramInt)
  {
    this.strokeJoin = paramInt;
  }
  
  public void strokeWeight(float paramFloat)
  {
    this.strokeWeight = paramFloat;
  }
  
  protected void swapTextures()
  {
    int i = this.texture.glName;
    this.texture.glName = this.ptexture.glName;
    this.ptexture.glName = i;
    if (!this.primarySurface) {
      this.offscreenFramebuffer.setColorBuffer(this.texture);
    }
  }
  
  protected void tessellate(int paramInt)
  {
    boolean bool1 = true;
    tessellator.setInGeometry(this.inGeo);
    tessellator.setTessGeometry(this.tessGeo);
    Tessellator localTessellator1 = tessellator;
    boolean bool2;
    if ((this.fill) || (this.textureImage != null))
    {
      bool2 = bool1;
      localTessellator1.setFill(bool2);
      tessellator.setStroke(this.stroke);
      tessellator.setStrokeColor(this.strokeColor);
      tessellator.setStrokeWeight(this.strokeWeight);
      tessellator.setStrokeCap(this.strokeCap);
      tessellator.setStrokeJoin(this.strokeJoin);
      tessellator.setTexCache(this.texCache, this.textureImage0, this.textureImage);
      tessellator.setTransform(this.modelview);
      tessellator.set3D(is3D());
      if (this.shape != 3) {
        break label158;
      }
      tessellator.tessellatePoints();
    }
    label158:
    do
    {
      return;
      bool2 = false;
      break;
      if (this.shape == 5)
      {
        tessellator.tessellateLines();
        return;
      }
      if (this.shape == 50)
      {
        tessellator.tessellateLineStrip();
        return;
      }
      if (this.shape == 51)
      {
        tessellator.tessellateLineLoop();
        return;
      }
      if ((this.shape == 8) || (this.shape == 9))
      {
        if ((this.stroke) && (this.defaultEdges)) {
          this.inGeo.addTrianglesEdges();
        }
        if (this.normalMode == 0) {
          this.inGeo.calcTrianglesNormals();
        }
        tessellator.tessellateTriangles();
        return;
      }
      if (this.shape == 11)
      {
        if ((this.stroke) && (this.defaultEdges)) {
          this.inGeo.addTriangleFanEdges();
        }
        if (this.normalMode == 0) {
          this.inGeo.calcTriangleFanNormals();
        }
        tessellator.tessellateTriangleFan();
        return;
      }
      if (this.shape == 10)
      {
        if ((this.stroke) && (this.defaultEdges)) {
          this.inGeo.addTriangleStripEdges();
        }
        if (this.normalMode == 0) {
          this.inGeo.calcTriangleStripNormals();
        }
        tessellator.tessellateTriangleStrip();
        return;
      }
      if ((this.shape == 16) || (this.shape == 17))
      {
        if ((this.stroke) && (this.defaultEdges)) {
          this.inGeo.addQuadsEdges();
        }
        if (this.normalMode == 0) {
          this.inGeo.calcQuadsNormals();
        }
        tessellator.tessellateQuads();
        return;
      }
      if (this.shape == 18)
      {
        if ((this.stroke) && (this.defaultEdges)) {
          this.inGeo.addQuadStripEdges();
        }
        if (this.normalMode == 0) {
          this.inGeo.calcQuadStripNormals();
        }
        tessellator.tessellateQuadStrip();
        return;
      }
    } while (this.shape != 20);
    boolean bool4;
    Tessellator localTessellator2;
    boolean bool3;
    if ((this.stroke) && (this.defaultEdges))
    {
      InGeometry localInGeometry = this.inGeo;
      if (paramInt == 2)
      {
        bool4 = bool1;
        localInGeometry.addPolygonEdges(bool4);
      }
    }
    else
    {
      localTessellator2 = tessellator;
      if (paramInt != 2) {
        break label558;
      }
      bool3 = bool1;
      label535:
      if (this.normalMode != 0) {
        break label564;
      }
    }
    for (;;)
    {
      localTessellator2.tessellatePolygon(false, bool3, bool1);
      return;
      bool4 = false;
      break;
      label558:
      bool3 = false;
      break label535;
      label564:
      bool1 = false;
    }
  }
  
  protected void tessellate(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (paramArrayOfInt2 != null)
    {
      int i = paramArrayOfInt2.length / 2;
      int j = 0;
      if (j < i)
      {
        int k = paramArrayOfInt2[(0 + j * 2)];
        int m = paramArrayOfInt2[(1 + j * 2)];
        InGeometry localInGeometry = this.inGeo;
        boolean bool2;
        if (j == 0)
        {
          bool2 = true;
          label54:
          if (j != i - 1) {
            break label92;
          }
        }
        label92:
        for (boolean bool3 = true;; bool3 = false)
        {
          localInGeometry.addEdge(k, m, bool2, bool3);
          j++;
          break;
          bool2 = false;
          break label54;
        }
      }
    }
    tessellator.setInGeometry(this.inGeo);
    tessellator.setTessGeometry(this.tessGeo);
    Tessellator localTessellator = tessellator;
    boolean bool1;
    if (!this.fill)
    {
      PImage localPImage = this.textureImage;
      bool1 = false;
      if (localPImage == null) {}
    }
    else
    {
      bool1 = true;
    }
    localTessellator.setFill(bool1);
    tessellator.setStroke(this.stroke);
    tessellator.setStrokeColor(this.strokeColor);
    tessellator.setStrokeWeight(this.strokeWeight);
    tessellator.setStrokeCap(this.strokeCap);
    tessellator.setStrokeJoin(this.strokeJoin);
    tessellator.setTexCache(this.texCache, this.textureImage0, this.textureImage);
    tessellator.setTransform(this.modelview);
    tessellator.set3D(is3D());
    if ((this.stroke) && (this.defaultEdges) && (paramArrayOfInt2 == null)) {
      this.inGeo.addTrianglesEdges();
    }
    if (this.normalMode == 0) {
      this.inGeo.calcTrianglesNormals();
    }
    tessellator.tessellateTriangles(paramArrayOfInt1);
  }
  
  protected void texlightShaderCheck()
  {
    if ((this.shaderWarningsEnabled) && ((this.lightShader != null) || (this.textureShader != null) || (this.colorShader != null))) {
      PGraphics.showWarning("Your shader cannot be used to render textured and lit geometry, using default shader instead.");
    }
  }
  
  protected void textCharImpl(char paramChar, float paramFloat1, float paramFloat2)
  {
    PFont.Glyph localGlyph = this.textFont.getGlyph(paramChar);
    if (localGlyph != null)
    {
      FontTexture.TextureInfo localTextureInfo = this.textTex.getTexInfo(localGlyph);
      if (localTextureInfo == null) {
        localTextureInfo = this.textTex.addToTexture(localGlyph);
      }
      if (this.textMode == 4)
      {
        float f1 = localGlyph.height / this.textFont.getSize();
        float f2 = localGlyph.width / this.textFont.getSize();
        float f3 = localGlyph.leftExtent / this.textFont.getSize();
        float f4 = localGlyph.topExtent / this.textFont.getSize();
        float f5 = paramFloat1 + f3 * this.textSize;
        float f6 = paramFloat2 - f4 * this.textSize;
        textCharModelImpl(localTextureInfo, f5, f6, f5 + f2 * this.textSize, f6 + f1 * this.textSize);
      }
    }
  }
  
  protected void textCharModelImpl(FontTexture.TextureInfo paramTextureInfo, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (this.textTex.currentTex != paramTextureInfo.texIndex) {
      this.textTex.setTexture(paramTextureInfo.texIndex);
    }
    PImage localPImage = this.textTex.getCurrentTexture();
    beginShape(17);
    texture(localPImage);
    vertex(paramFloat1, paramFloat2, paramTextureInfo.u0, paramTextureInfo.v0);
    vertex(paramFloat3, paramFloat2, paramTextureInfo.u1, paramTextureInfo.v0);
    vertex(paramFloat3, paramFloat4, paramTextureInfo.u1, paramTextureInfo.v1);
    vertex(paramFloat1, paramFloat4, paramTextureInfo.u0, paramTextureInfo.v1);
    endShape();
  }
  
  protected void textLineImpl(char[] paramArrayOfChar, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    this.textTex = pgPrimary.getFontTexture(this.textFont);
    if (this.textTex == null)
    {
      this.textTex = new FontTexture(this.parent, this.textFont, maxTextureSize, maxTextureSize, is3D());
      pgPrimary.setFontTexture(this.textFont, this.textTex);
    }
    for (;;)
    {
      this.textTex.begin();
      int i = this.textureMode;
      boolean bool1 = this.stroke;
      float f1 = this.normalX;
      float f2 = this.normalY;
      float f3 = this.normalZ;
      boolean bool2 = this.tint;
      int j = this.tintColor;
      int k = this.blendMode;
      this.textureMode = 1;
      this.stroke = false;
      this.normalX = 0.0F;
      this.normalY = 0.0F;
      this.normalZ = 1.0F;
      this.tint = true;
      this.tintColor = this.fillColor;
      blendMode(1);
      super.textLineImpl(paramArrayOfChar, paramInt1, paramInt2, paramFloat1, paramFloat2);
      this.textureMode = i;
      this.stroke = bool1;
      this.normalX = f1;
      this.normalY = f2;
      this.normalZ = f3;
      this.tint = bool2;
      this.tintColor = j;
      blendMode(k);
      this.textTex.end();
      return;
      if (this.textTex.contextIsOutdated())
      {
        this.textTex = new FontTexture(this.parent, this.textFont, PApplet.min(512, maxTextureSize), PApplet.min(512, maxTextureSize), is3D());
        pgPrimary.setFontTexture(this.textFont, this.textTex);
      }
    }
  }
  
  protected boolean textModeCheck(int paramInt)
  {
    return paramInt == 4;
  }
  
  public void textureSampling(int paramInt)
  {
    this.textureSampling = paramInt;
  }
  
  protected void textureShaderCheck()
  {
    if ((this.shaderWarningsEnabled) && ((this.texlightShader != null) || (this.lightShader != null) || (this.colorShader != null))) {
      PGraphics.showWarning("Your shader cannot be used to render textured geometry, using default shader instead.");
    }
  }
  
  public void textureWrap(int paramInt)
  {
    this.textureWrap = paramInt;
  }
  
  public void translate(float paramFloat1, float paramFloat2)
  {
    translateImpl(paramFloat1, paramFloat2, 0.0F);
  }
  
  public void translate(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    translateImpl(paramFloat1, paramFloat2, paramFloat3);
  }
  
  protected void translateImpl(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.modelview.translate(paramFloat1, paramFloat2, paramFloat3);
    invTranslate(this.modelviewInv, paramFloat1, paramFloat2, paramFloat3);
    this.projmodelview.translate(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void triangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    beginShape(9);
    this.defaultEdges = false;
    this.normalMode = 1;
    this.inGeo.setMaterial(this.fillColor, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess);
    this.inGeo.setNormal(this.normalX, this.normalY, this.normalZ);
    this.inGeo.addTriangle(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4, 0.0F, paramFloat5, paramFloat6, 0.0F, this.fill, this.stroke);
    endShape();
  }
  
  protected void unbindBackTexture()
  {
    if (this.primarySurface)
    {
      this.pgl.unbindFrontTexture();
      return;
    }
    this.ptexture.unbind();
  }
  
  protected void unbindLineBuffers()
  {
    this.pgl.bindBuffer(34962, 0);
    this.pgl.bindBuffer(34963, 0);
  }
  
  protected void unbindPointBuffers()
  {
    this.pgl.bindBuffer(34962, 0);
    this.pgl.bindBuffer(34963, 0);
  }
  
  protected void unbindPolyBuffers()
  {
    this.pgl.bindBuffer(34962, 0);
    this.pgl.bindBuffer(34963, 0);
  }
  
  public void updateDisplay()
  {
    flush();
    beginPixelsOp(2);
    drawTexture();
    endPixelsOp();
  }
  
  protected void updateGLModelview()
  {
    if (this.glModelview == null) {
      this.glModelview = new float[16];
    }
    this.glModelview[0] = this.modelview.m00;
    this.glModelview[1] = this.modelview.m10;
    this.glModelview[2] = this.modelview.m20;
    this.glModelview[3] = this.modelview.m30;
    this.glModelview[4] = this.modelview.m01;
    this.glModelview[5] = this.modelview.m11;
    this.glModelview[6] = this.modelview.m21;
    this.glModelview[7] = this.modelview.m31;
    this.glModelview[8] = this.modelview.m02;
    this.glModelview[9] = this.modelview.m12;
    this.glModelview[10] = this.modelview.m22;
    this.glModelview[11] = this.modelview.m32;
    this.glModelview[12] = this.modelview.m03;
    this.glModelview[13] = this.modelview.m13;
    this.glModelview[14] = this.modelview.m23;
    this.glModelview[15] = this.modelview.m33;
  }
  
  protected void updateGLNormal()
  {
    if (this.glNormal == null) {
      this.glNormal = new float[9];
    }
    this.glNormal[0] = this.modelviewInv.m00;
    this.glNormal[1] = this.modelviewInv.m01;
    this.glNormal[2] = this.modelviewInv.m02;
    this.glNormal[3] = this.modelviewInv.m10;
    this.glNormal[4] = this.modelviewInv.m11;
    this.glNormal[5] = this.modelviewInv.m12;
    this.glNormal[6] = this.modelviewInv.m20;
    this.glNormal[7] = this.modelviewInv.m21;
    this.glNormal[8] = this.modelviewInv.m22;
  }
  
  protected void updateGLProjection()
  {
    if (this.glProjection == null) {
      this.glProjection = new float[16];
    }
    this.glProjection[0] = this.projection.m00;
    this.glProjection[1] = this.projection.m10;
    this.glProjection[2] = this.projection.m20;
    this.glProjection[3] = this.projection.m30;
    this.glProjection[4] = this.projection.m01;
    this.glProjection[5] = this.projection.m11;
    this.glProjection[6] = this.projection.m21;
    this.glProjection[7] = this.projection.m31;
    this.glProjection[8] = this.projection.m02;
    this.glProjection[9] = this.projection.m12;
    this.glProjection[10] = this.projection.m22;
    this.glProjection[11] = this.projection.m32;
    this.glProjection[12] = this.projection.m03;
    this.glProjection[13] = this.projection.m13;
    this.glProjection[14] = this.projection.m23;
    this.glProjection[15] = this.projection.m33;
  }
  
  protected void updateGLProjmodelview()
  {
    if (this.glProjmodelview == null) {
      this.glProjmodelview = new float[16];
    }
    this.glProjmodelview[0] = this.projmodelview.m00;
    this.glProjmodelview[1] = this.projmodelview.m10;
    this.glProjmodelview[2] = this.projmodelview.m20;
    this.glProjmodelview[3] = this.projmodelview.m30;
    this.glProjmodelview[4] = this.projmodelview.m01;
    this.glProjmodelview[5] = this.projmodelview.m11;
    this.glProjmodelview[6] = this.projmodelview.m21;
    this.glProjmodelview[7] = this.projmodelview.m31;
    this.glProjmodelview[8] = this.projmodelview.m02;
    this.glProjmodelview[9] = this.projmodelview.m12;
    this.glProjmodelview[10] = this.projmodelview.m22;
    this.glProjmodelview[11] = this.projmodelview.m32;
    this.glProjmodelview[12] = this.projmodelview.m03;
    this.glProjmodelview[13] = this.projmodelview.m13;
    this.glProjmodelview[14] = this.projmodelview.m23;
    this.glProjmodelview[15] = this.projmodelview.m33;
  }
  
  protected void updateLineBuffers()
  {
    createLineBuffers();
    int i = this.tessGeo.lineVertexCount;
    int j = i * 4;
    int k = i * 4;
    this.tessGeo.updateLineVerticesBuffer();
    this.pgl.bindBuffer(34962, this.glLineVertex);
    this.pgl.bufferData(34962, j * 4, this.tessGeo.lineVerticesBuffer, 35044);
    this.tessGeo.updateLineColorsBuffer();
    this.pgl.bindBuffer(34962, this.glLineColor);
    this.pgl.bufferData(34962, k, this.tessGeo.lineColorsBuffer, 35044);
    this.tessGeo.updateLineDirectionsBuffer();
    this.pgl.bindBuffer(34962, this.glLineAttrib);
    this.pgl.bufferData(34962, j * 4, this.tessGeo.lineDirectionsBuffer, 35044);
    this.tessGeo.updateLineIndicesBuffer();
    this.pgl.bindBuffer(34963, this.glLineIndex);
    this.pgl.bufferData(34963, 2 * this.tessGeo.lineIndexCount, this.tessGeo.lineIndicesBuffer, 35044);
  }
  
  protected void updateOffscreen()
  {
    int i = 1;
    if (!this.initialized) {
      initOffscreen();
    }
    for (;;)
    {
      pushFramebuffer();
      if (!this.offscreenMultisample) {
        break;
      }
      setFramebuffer(this.multisampleFramebuffer);
      return;
      int j;
      if ((this.offscreenFramebuffer != null) && (this.offscreenFramebuffer.contextIsOutdated()))
      {
        j = i;
        label52:
        if ((this.multisampleFramebuffer == null) || (!this.multisampleFramebuffer.contextIsOutdated())) {
          break label93;
        }
      }
      for (;;)
      {
        if ((j == 0) && (i == 0)) {
          break label98;
        }
        restartPGL();
        initOffscreen();
        break;
        j = 0;
        break label52;
        label93:
        i = 0;
      }
      label98:
      swapTextures();
    }
    setFramebuffer(this.offscreenFramebuffer);
  }
  
  protected void updatePointBuffers()
  {
    createPointBuffers();
    int i = this.tessGeo.pointVertexCount;
    int j = i * 4;
    int k = i * 4;
    this.tessGeo.updatePointVerticesBuffer();
    this.pgl.bindBuffer(34962, this.glPointVertex);
    this.pgl.bufferData(34962, j * 4, this.tessGeo.pointVerticesBuffer, 35044);
    this.tessGeo.updatePointColorsBuffer();
    this.pgl.bindBuffer(34962, this.glPointColor);
    this.pgl.bufferData(34962, k, this.tessGeo.pointColorsBuffer, 35044);
    this.tessGeo.updatePointOffsetsBuffer();
    this.pgl.bindBuffer(34962, this.glPointAttrib);
    this.pgl.bufferData(34962, j * 2, this.tessGeo.pointOffsetsBuffer, 35044);
    this.tessGeo.updatePointIndicesBuffer();
    this.pgl.bindBuffer(34963, this.glPointIndex);
    this.pgl.bufferData(34963, 2 * this.tessGeo.pointIndexCount, this.tessGeo.pointIndicesBuffer, 35044);
  }
  
  protected void updatePolyBuffers(boolean paramBoolean1, boolean paramBoolean2)
  {
    createPolyBuffers();
    int i = this.tessGeo.polyVertexCount;
    int j = i * 4;
    int k = i * 4;
    this.tessGeo.updatePolyVerticesBuffer();
    this.pgl.bindBuffer(34962, this.glPolyVertex);
    this.pgl.bufferData(34962, j * 4, this.tessGeo.polyVerticesBuffer, 35044);
    this.tessGeo.updatePolyColorsBuffer();
    this.pgl.bindBuffer(34962, this.glPolyColor);
    this.pgl.bufferData(34962, k, this.tessGeo.polyColorsBuffer, 35044);
    if (paramBoolean1)
    {
      this.tessGeo.updatePolyNormalsBuffer();
      this.pgl.bindBuffer(34962, this.glPolyNormal);
      this.pgl.bufferData(34962, j * 3, this.tessGeo.polyNormalsBuffer, 35044);
      this.tessGeo.updatePolyAmbientBuffer();
      this.pgl.bindBuffer(34962, this.glPolyAmbient);
      this.pgl.bufferData(34962, k, this.tessGeo.polyAmbientBuffer, 35044);
      this.tessGeo.updatePolySpecularBuffer();
      this.pgl.bindBuffer(34962, this.glPolySpecular);
      this.pgl.bufferData(34962, k, this.tessGeo.polySpecularBuffer, 35044);
      this.tessGeo.updatePolyEmissiveBuffer();
      this.pgl.bindBuffer(34962, this.glPolyEmissive);
      this.pgl.bufferData(34962, k, this.tessGeo.polyEmissiveBuffer, 35044);
      this.tessGeo.updatePolyShininessBuffer();
      this.pgl.bindBuffer(34962, this.glPolyShininess);
      this.pgl.bufferData(34962, j, this.tessGeo.polyShininessBuffer, 35044);
    }
    if (paramBoolean2)
    {
      this.tessGeo.updatePolyTexCoordsBuffer();
      this.pgl.bindBuffer(34962, this.glPolyTexcoord);
      this.pgl.bufferData(34962, j * 2, this.tessGeo.polyTexCoordsBuffer, 35044);
    }
    this.tessGeo.updatePolyIndicesBuffer();
    this.pgl.bindBuffer(34963, this.glPolyIndex);
    this.pgl.bufferData(34963, 2 * this.tessGeo.polyIndexCount, this.tessGeo.polyIndicesBuffer, 35044);
  }
  
  protected void updatePrimary()
  {
    this.pgl.update();
  }
  
  public void updateProjmodelview()
  {
    this.projmodelview.set(this.projection);
    this.projmodelview.apply(this.modelview);
  }
  
  public void updateTexture()
  {
    this.texture.updateTexels();
  }
  
  public void updateTexture(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.texture.updateTexels(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void updateTexture(PImage paramPImage, Texture paramTexture)
  {
    if (paramTexture != null)
    {
      int i = paramPImage.getModifiedX1();
      int j = paramPImage.getModifiedY1();
      int k = 1 + (paramPImage.getModifiedX2() - i);
      int m = 1 + (paramPImage.getModifiedY2() - j);
      paramTexture.set(paramPImage.pixels, i, j, k, m, paramPImage.format);
    }
    paramPImage.setModified(false);
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
    int i;
    int j;
    label36:
    int k;
    float f1;
    float f4;
    float f2;
    if (this.textureImage != null)
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
          break label176;
        }
        j = this.fillColor;
      }
      boolean bool = this.stroke;
      k = 0;
      f1 = 0.0F;
      if (bool)
      {
        k = this.strokeColor;
        f1 = this.strokeWeight;
      }
      if ((i == 0) || (this.textureMode != 2)) {
        break label198;
      }
      f4 = PApplet.min(1.0F, paramFloat4 / this.textureImage.width);
      f2 = PApplet.min(1.0F, paramFloat5 / this.textureImage.height);
    }
    for (float f3 = f4;; f3 = paramFloat4)
    {
      this.inGeo.addVertex(paramFloat1, paramFloat2, paramFloat3, j, this.normalX, this.normalY, this.normalZ, f3, f2, k, f1, this.ambientColor, this.specularColor, this.emissiveColor, this.shininess, vertexCode());
      return;
      i = 0;
      break;
      label176:
      if (this.tint)
      {
        j = this.tintColor;
        break label36;
      }
      j = -1;
      break label36;
      label198:
      f2 = paramFloat5;
    }
  }
  
  protected PImage wrapTexture(Texture paramTexture)
  {
    PImage localPImage = new PImage();
    localPImage.parent = this.parent;
    localPImage.width = paramTexture.width;
    localPImage.height = paramTexture.height;
    localPImage.format = 2;
    pgPrimary.setCache(localPImage, paramTexture);
    return localPImage;
  }
  
  protected class BaseShader
    extends PShader
  {
    protected int bufferLoc;
    protected int modelviewLoc;
    protected int projectionLoc;
    protected int transformLoc;
    protected int viewportLoc;
    
    public BaseShader(PApplet paramPApplet)
    {
      super();
    }
    
    public BaseShader(PApplet paramPApplet, String paramString1, String paramString2)
    {
      super(paramString1, paramString2);
    }
    
    public BaseShader(PApplet paramPApplet, URL paramURL1, URL paramURL2)
    {
      super(paramURL1, paramURL2);
    }
    
    public void loadUniforms()
    {
      this.transformLoc = getUniformLoc("transform");
      this.modelviewLoc = getUniformLoc("modelview");
      this.projectionLoc = getUniformLoc("projection");
      this.viewportLoc = getUniformLoc("viewport");
      this.bufferLoc = getUniformLoc("buffer");
    }
    
    public void setAmbientAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
    
    public void setColorAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
    
    protected void setCommonUniforms()
    {
      if (-1 < this.transformLoc)
      {
        this.pgCurrent.updateGLProjmodelview();
        setUniformMatrix(this.transformLoc, this.pgCurrent.glProjmodelview);
      }
      if (-1 < this.modelviewLoc)
      {
        this.pgCurrent.updateGLModelview();
        setUniformMatrix(this.modelviewLoc, this.pgCurrent.glModelview);
      }
      if (-1 < this.projectionLoc)
      {
        this.pgCurrent.updateGLProjection();
        setUniformMatrix(this.projectionLoc, this.pgCurrent.glProjection);
      }
      if (-1 < this.viewportLoc)
      {
        float f1 = this.pgCurrent.viewport.get(0);
        float f2 = this.pgCurrent.viewport.get(1);
        float f3 = this.pgCurrent.viewport.get(2);
        float f4 = this.pgCurrent.viewport.get(3);
        setUniformValue(this.viewportLoc, f1, f2, f3, f4);
      }
      if (-1 < this.bufferLoc)
      {
        setUniformValue(this.bufferLoc, this.lastTexUnit);
        this.pgl.activeTexture(33984 + this.lastTexUnit);
        this.pgCurrent.bindBackTexture();
      }
    }
    
    public void setEmissiveAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
    
    public void setNormalAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
    
    public void setShininessAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
    
    public void setSpecularAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
    
    public void setTexcoordAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
    
    public void setTexture(Texture paramTexture) {}
    
    public void setVertexAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
    
    public void unbind()
    {
      if (-1 < this.bufferLoc)
      {
        this.pgl.needFBOLayer();
        this.pgl.activeTexture(33984 + this.lastTexUnit);
        this.pgCurrent.unbindBackTexture();
        this.pgl.activeTexture(33984);
      }
      this.pgl.bindBuffer(34962, 0);
      super.unbind();
    }
  }
  
  protected class ColorShader
    extends PGraphicsOpenGL.BaseShader
  {
    protected int colorLoc;
    protected int vertexLoc;
    
    public ColorShader(PApplet paramPApplet)
    {
      super(paramPApplet);
    }
    
    public ColorShader(PApplet paramPApplet, String paramString1, String paramString2)
    {
      super(paramPApplet, paramString1, paramString2);
    }
    
    public ColorShader(PApplet paramPApplet, URL paramURL1, URL paramURL2)
    {
      super(paramPApplet, paramURL1, paramURL2);
    }
    
    public void bind()
    {
      super.bind();
      if (this.pgCurrent == null)
      {
        setRenderer(PGraphicsOpenGL.pgCurrent);
        loadAttributes();
        loadUniforms();
      }
      if (-1 < this.vertexLoc) {
        this.pgl.enableVertexAttribArray(this.vertexLoc);
      }
      if (-1 < this.colorLoc) {
        this.pgl.enableVertexAttribArray(this.colorLoc);
      }
      setCommonUniforms();
    }
    
    public void loadAttributes()
    {
      this.vertexLoc = getAttributeLoc("vertex");
      this.colorLoc = getAttributeLoc("color");
    }
    
    public void loadUniforms()
    {
      super.loadUniforms();
    }
    
    public void setColorAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.colorLoc, paramInt1, paramInt2, paramInt3, true, paramInt4, paramInt5);
    }
    
    public void setVertexAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.vertexLoc, paramInt1, paramInt2, paramInt3, false, paramInt4, paramInt5);
    }
    
    public void unbind()
    {
      if (-1 < this.vertexLoc) {
        this.pgl.disableVertexAttribArray(this.vertexLoc);
      }
      if (-1 < this.colorLoc) {
        this.pgl.disableVertexAttribArray(this.colorLoc);
      }
      super.unbind();
    }
  }
  
  protected class GLResource
  {
    int context;
    int id;
    
    GLResource(int paramInt1, int paramInt2)
    {
      this.id = paramInt1;
      this.context = paramInt2;
    }
    
    public boolean equals(Object paramObject)
    {
      GLResource localGLResource = (GLResource)paramObject;
      return (localGLResource.id == this.id) && (localGLResource.context == this.context);
    }
    
    public int hashCode()
    {
      return 31 * (527 + this.id) + this.context;
    }
  }
  
  protected class InGeometry
  {
    int[] ambient;
    int ambientColor;
    boolean[] breaks;
    int[] colors;
    int edgeCount;
    int[][] edges;
    int[] emissive;
    int emissiveColor;
    int fillColor;
    int firstEdge;
    int firstVertex;
    int lastEdge;
    int lastVertex;
    float normalX;
    float normalY;
    float normalZ;
    float[] normals;
    int renderMode;
    float[] shininess;
    float shininessFactor;
    int[] specular;
    int specularColor;
    int strokeColor;
    int[] strokeColors;
    float strokeWeight;
    float[] strokeWeights;
    float[] texcoords;
    int vertexCount;
    float[] vertices;
    
    InGeometry(int paramInt)
    {
      this.renderMode = paramInt;
      allocate();
    }
    
    void addArc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
    {
      float f1 = paramFloat3 / 2.0F;
      float f2 = paramFloat4 / 2.0F;
      float f3 = paramFloat1 + f1;
      float f4 = paramFloat2 + f2;
      int i = (int)(0.5F + 720.0F * (paramFloat5 / 6.283186F));
      int j = (int)(0.5F + 720.0F * (paramFloat6 / 6.283186F));
      if (paramBoolean1) {
        addVertex(f3, f4, 0);
      }
      int k = i;
      int m = 0;
      if (k < j)
      {
        int i3 = k % 720;
        if (i3 < 0) {
          i3 += 720;
        }
        int i4 = addVertex(f3 + f1 * PGraphicsOpenGL.cosLUT[i3], f4 + f2 * PGraphicsOpenGL.sinLUT[i3], 0);
        if (paramBoolean2)
        {
          if (paramInt != 3) {
            break label179;
          }
          if (k != i) {
            break label173;
          }
          bool3 = true;
          addEdge(m, i4, bool3, false);
        }
        label173:
        label179:
        while (i >= k) {
          for (;;)
          {
            k++;
            m = i4;
            break;
            boolean bool3 = false;
          }
        }
        boolean bool1;
        if (k == i + 1)
        {
          bool1 = true;
          label198:
          if ((paramInt != 0) || (k != j - 1)) {
            break label237;
          }
        }
        label237:
        for (boolean bool2 = true;; bool2 = false)
        {
          addEdge(m, i4, bool1, bool2);
          break;
          bool1 = false;
          break label198;
        }
      }
      int n = addVertex(f3 + f1 * PGraphicsOpenGL.cosLUT[(j % 720)], f4 + f2 * PGraphicsOpenGL.sinLUT[(j % 720)], 0);
      if ((paramBoolean2) && (paramInt == 3)) {
        addEdge(n, 0, false, true);
      }
      if ((paramInt == 2) || (paramInt == 1))
      {
        int i1 = i % 720;
        if (i1 < 0) {
          i1 += 720;
        }
        int i2 = addVertex(f3 + f1 * PGraphicsOpenGL.cosLUT[i1], f4 + f2 * PGraphicsOpenGL.sinLUT[i1], 0);
        if ((paramBoolean2) && (paramInt == 2)) {
          addEdge(n, i2, false, true);
        }
      }
    }
    
    void addBezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
    {
      addBezierVertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramBoolean1, paramBoolean2, paramInt1, paramInt2, 20);
    }
    
    void addBezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, int paramInt3)
    {
      PGraphicsOpenGL.this.bezierInitCheck();
      PGraphicsOpenGL.this.bezierVertexCheck(paramInt3, this.vertexCount);
      PMatrix3D localPMatrix3D = PGraphicsOpenGL.this.bezierDrawMatrix;
      float f1 = getLastVertexX();
      float f2 = getLastVertexY();
      float f3 = getLastVertexZ();
      float f4 = f1 * localPMatrix3D.m10 + paramFloat1 * localPMatrix3D.m11 + paramFloat4 * localPMatrix3D.m12 + paramFloat7 * localPMatrix3D.m13;
      float f5 = f1 * localPMatrix3D.m20 + paramFloat1 * localPMatrix3D.m21 + paramFloat4 * localPMatrix3D.m22 + paramFloat7 * localPMatrix3D.m23;
      float f6 = f1 * localPMatrix3D.m30 + paramFloat1 * localPMatrix3D.m31 + paramFloat4 * localPMatrix3D.m32 + paramFloat7 * localPMatrix3D.m33;
      float f7 = f2 * localPMatrix3D.m10 + paramFloat2 * localPMatrix3D.m11 + paramFloat5 * localPMatrix3D.m12 + paramFloat8 * localPMatrix3D.m13;
      float f8 = f2 * localPMatrix3D.m20 + paramFloat2 * localPMatrix3D.m21 + paramFloat5 * localPMatrix3D.m22 + paramFloat8 * localPMatrix3D.m23;
      float f9 = f2 * localPMatrix3D.m30 + paramFloat2 * localPMatrix3D.m31 + paramFloat5 * localPMatrix3D.m32 + paramFloat8 * localPMatrix3D.m33;
      float f10 = f3 * localPMatrix3D.m10 + paramFloat3 * localPMatrix3D.m11 + paramFloat6 * localPMatrix3D.m12 + paramFloat9 * localPMatrix3D.m13;
      float f11 = f3 * localPMatrix3D.m20 + paramFloat3 * localPMatrix3D.m21 + paramFloat6 * localPMatrix3D.m22 + paramFloat9 * localPMatrix3D.m23;
      float f12 = f3 * localPMatrix3D.m30 + paramFloat3 * localPMatrix3D.m31 + paramFloat6 * localPMatrix3D.m32 + paramFloat9 * localPMatrix3D.m33;
      float f13 = f11;
      int i = 0;
      float f14 = f1;
      float f15 = f2;
      float f16 = f3;
      if (i < paramInt1)
      {
        float f17 = f14 + f4;
        float f18 = f4 + f5;
        float f19 = f5 + f6;
        f15 += f7;
        float f20 = f7 + f8;
        float f21 = f8 + f9;
        f16 += f10;
        float f22 = f10 + f13;
        float f23 = f13 + f12;
        if ((i == 0) && (paramInt2 == 4)) {}
        for (int j = 4;; j = 0)
        {
          addVertex(f17, f15, f16, j);
          i++;
          f13 = f23;
          f10 = f22;
          f8 = f21;
          f7 = f20;
          f5 = f19;
          f4 = f18;
          f14 = f17;
          break;
        }
      }
    }
    
    void addBox(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2)
    {
      float f1 = -paramFloat1 / 2.0F;
      float f2 = paramFloat1 / 2.0F;
      float f3 = -paramFloat2 / 2.0F;
      float f4 = paramFloat2 / 2.0F;
      float f5 = -paramFloat3 / 2.0F;
      float f6 = paramFloat3 / 2.0F;
      if ((paramBoolean1) || (paramBoolean2))
      {
        setNormal(0.0F, 0.0F, 1.0F);
        addVertex(f1, f3, f5, 0.0F, 0.0F, 0);
        addVertex(f2, f3, f5, 1.0F, 0.0F, 0);
        addVertex(f2, f4, f5, 1.0F, 1.0F, 0);
        addVertex(f1, f4, f5, 0.0F, 1.0F, 0);
        setNormal(1.0F, 0.0F, 0.0F);
        addVertex(f2, f3, f5, 0.0F, 0.0F, 0);
        addVertex(f2, f3, f6, 1.0F, 0.0F, 0);
        addVertex(f2, f4, f6, 1.0F, 1.0F, 0);
        addVertex(f2, f4, f5, 0.0F, 1.0F, 0);
        setNormal(0.0F, 0.0F, -1.0F);
        addVertex(f2, f3, f6, 0.0F, 0.0F, 0);
        addVertex(f1, f3, f6, 1.0F, 0.0F, 0);
        addVertex(f1, f4, f6, 1.0F, 1.0F, 0);
        addVertex(f2, f4, f6, 0.0F, 1.0F, 0);
        setNormal(-1.0F, 0.0F, 0.0F);
        addVertex(f1, f3, f6, 0.0F, 0.0F, 0);
        addVertex(f1, f3, f5, 1.0F, 0.0F, 0);
        addVertex(f1, f4, f5, 1.0F, 1.0F, 0);
        addVertex(f1, f4, f6, 0.0F, 1.0F, 0);
        setNormal(0.0F, 1.0F, 0.0F);
        addVertex(f1, f3, f6, 0.0F, 0.0F, 0);
        addVertex(f2, f3, f6, 1.0F, 0.0F, 0);
        addVertex(f2, f3, f5, 1.0F, 1.0F, 0);
        addVertex(f1, f3, f5, 0.0F, 1.0F, 0);
        setNormal(0.0F, -1.0F, 0.0F);
        addVertex(f1, f4, f5, 0.0F, 0.0F, 0);
        addVertex(f2, f4, f5, 1.0F, 0.0F, 0);
        addVertex(f2, f4, f6, 1.0F, 1.0F, 0);
        addVertex(f1, f4, f6, 0.0F, 1.0F, 0);
      }
      if (paramBoolean2)
      {
        addEdge(0, 1, true, true);
        addEdge(1, 2, true, true);
        addEdge(2, 3, true, true);
        addEdge(3, 0, true, true);
        addEdge(0, 9, true, true);
        addEdge(1, 8, true, true);
        addEdge(2, 11, true, true);
        addEdge(3, 10, true, true);
        addEdge(8, 9, true, true);
        addEdge(9, 10, true, true);
        addEdge(10, 11, true, true);
        addEdge(11, 8, true, true);
      }
    }
    
    void addCurveVertex(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
    {
      addCurveVertex(paramFloat1, paramFloat2, paramFloat3, paramBoolean1, paramBoolean2, paramInt1, paramInt2, 20);
    }
    
    void addCurveVertex(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, int paramInt3)
    {
      PGraphicsOpenGL.this.curveVertexCheck(paramInt3);
      float[] arrayOfFloat1 = PGraphicsOpenGL.this.curveVertices[PGraphicsOpenGL.this.curveVertexCount];
      arrayOfFloat1[0] = paramFloat1;
      arrayOfFloat1[1] = paramFloat2;
      arrayOfFloat1[2] = paramFloat3;
      PGraphicsOpenGL.access$708(PGraphicsOpenGL.this);
      if (PGraphicsOpenGL.this.curveVertexCount > 3)
      {
        float[] arrayOfFloat2 = PGraphicsOpenGL.this.curveVertices[(-4 + PGraphicsOpenGL.this.curveVertexCount)];
        float[] arrayOfFloat3 = PGraphicsOpenGL.this.curveVertices[(-3 + PGraphicsOpenGL.this.curveVertexCount)];
        float[] arrayOfFloat4 = PGraphicsOpenGL.this.curveVertices[(-2 + PGraphicsOpenGL.this.curveVertexCount)];
        float[] arrayOfFloat5 = PGraphicsOpenGL.this.curveVertices[(-1 + PGraphicsOpenGL.this.curveVertexCount)];
        addCurveVertexSegment(arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat2[2], arrayOfFloat3[0], arrayOfFloat3[1], arrayOfFloat3[2], arrayOfFloat4[0], arrayOfFloat4[1], arrayOfFloat4[2], arrayOfFloat5[0], arrayOfFloat5[1], arrayOfFloat5[2], paramInt1, paramInt2);
      }
    }
    
    void addCurveVertexSegment(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, int paramInt1, int paramInt2)
    {
      PMatrix3D localPMatrix3D = PGraphicsOpenGL.this.curveDrawMatrix;
      float f1 = paramFloat1 * localPMatrix3D.m10 + paramFloat4 * localPMatrix3D.m11 + paramFloat7 * localPMatrix3D.m12 + paramFloat10 * localPMatrix3D.m13;
      float f2 = paramFloat1 * localPMatrix3D.m20 + paramFloat4 * localPMatrix3D.m21 + paramFloat7 * localPMatrix3D.m22 + paramFloat10 * localPMatrix3D.m23;
      float f3 = paramFloat1 * localPMatrix3D.m30 + paramFloat4 * localPMatrix3D.m31 + paramFloat7 * localPMatrix3D.m32 + paramFloat10 * localPMatrix3D.m33;
      float f4 = paramFloat2 * localPMatrix3D.m10 + paramFloat5 * localPMatrix3D.m11 + paramFloat8 * localPMatrix3D.m12 + paramFloat11 * localPMatrix3D.m13;
      float f5 = paramFloat2 * localPMatrix3D.m20 + paramFloat5 * localPMatrix3D.m21 + paramFloat8 * localPMatrix3D.m22 + paramFloat11 * localPMatrix3D.m23;
      float f6 = paramFloat2 * localPMatrix3D.m30 + paramFloat5 * localPMatrix3D.m31 + paramFloat8 * localPMatrix3D.m32 + paramFloat11 * localPMatrix3D.m33;
      float f7 = paramFloat3 * localPMatrix3D.m10 + paramFloat6 * localPMatrix3D.m11 + paramFloat9 * localPMatrix3D.m12 + paramFloat12 * localPMatrix3D.m13;
      float f8 = paramFloat3 * localPMatrix3D.m20 + paramFloat6 * localPMatrix3D.m21 + paramFloat9 * localPMatrix3D.m22 + paramFloat12 * localPMatrix3D.m23;
      float f9 = paramFloat3 * localPMatrix3D.m30 + paramFloat6 * localPMatrix3D.m31 + paramFloat9 * localPMatrix3D.m32 + paramFloat12 * localPMatrix3D.m33;
      int i = PGraphicsOpenGL.this.curveVertexCount;
      if (paramInt2 == 4) {}
      for (int j = 4;; j = 0)
      {
        addVertex(paramFloat4, paramFloat5, paramFloat6, j);
        for (int k = 0; k < paramInt1; k++)
        {
          paramFloat4 += f1;
          f1 += f2;
          f2 += f3;
          paramFloat5 += f4;
          f4 += f5;
          f5 += f6;
          paramFloat6 += f7;
          f7 += f8;
          f8 += f9;
          addVertex(paramFloat4, paramFloat5, paramFloat6, 0);
        }
      }
      PGraphicsOpenGL.access$1902(PGraphicsOpenGL.this, i);
    }
    
    int addEdge(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    {
      int i = 1;
      edgeCheck();
      int[] arrayOfInt = this.edges[this.edgeCount];
      arrayOfInt[0] = paramInt1;
      arrayOfInt[i] = paramInt2;
      int j;
      if (paramBoolean1)
      {
        j = i;
        if (!paramBoolean2) {
          break label82;
        }
      }
      for (;;)
      {
        arrayOfInt[2] = (j + i * 2);
        this.lastEdge = this.edgeCount;
        this.edgeCount = (1 + this.edgeCount);
        return this.lastEdge;
        j = 0;
        break;
        label82:
        i = 0;
      }
    }
    
    void addEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
    {
      float f1;
      float f2;
      float f3;
      float f4;
      if (paramInt == 1)
      {
        float f17 = paramFloat3 - paramFloat1;
        f1 = paramFloat4 - paramFloat2;
        f2 = f17;
        f3 = paramFloat2;
        f4 = paramFloat1;
      }
      for (;;)
      {
        if (f2 < 0.0F)
        {
          f4 += f2;
          f2 = -f2;
        }
        if (f1 < 0.0F)
        {
          f3 += f1;
          f1 = -f1;
        }
        float f5 = f2 / 2.0F;
        float f6 = f1 / 2.0F;
        float f7 = f4 + f5;
        float f8 = f3 + f6;
        int i = PApplet.max(20, (int)(6.283186F * PApplet.dist(PGraphicsOpenGL.pgCurrent.screenX(f4, f3), PGraphicsOpenGL.pgCurrent.screenY(f4, f3), PGraphicsOpenGL.pgCurrent.screenX(f4 + f2, f3 + f1), PGraphicsOpenGL.pgCurrent.screenY(f2 + f4, f1 + f3)) / 10.0F));
        float f9 = 720.0F / i;
        if (paramBoolean1) {
          addVertex(f7, f8, 0);
        }
        int j = 0;
        int k = 0;
        int m = 0;
        float f10 = 0.0F;
        int n = 0;
        label198:
        float f11;
        boolean bool;
        label267:
        int i1;
        if (k < i)
        {
          j = addVertex(f7 + f5 * PGraphicsOpenGL.cosLUT[((int)f10)], f8 + f6 * PGraphicsOpenGL.sinLUT[((int)f10)], 0);
          f11 = (f10 + f9) % 720.0F;
          if (k > 0)
          {
            if (!paramBoolean2) {
              break label441;
            }
            if (k == 1)
            {
              bool = true;
              addEdge(m, j, bool, false);
              i1 = n;
            }
          }
        }
        for (;;)
        {
          k++;
          m = j;
          n = i1;
          f10 = f11;
          break label198;
          if (paramInt == 2)
          {
            float f14 = paramFloat1 - paramFloat3;
            float f15 = paramFloat2 - paramFloat4;
            float f16 = paramFloat3 * 2.0F;
            f1 = paramFloat4 * 2.0F;
            f2 = f16;
            f3 = f15;
            f4 = f14;
            break;
          }
          if (paramInt != 3) {
            break label448;
          }
          float f12 = paramFloat1 - paramFloat3 / 2.0F;
          float f13 = paramFloat2 - paramFloat4 / 2.0F;
          f1 = paramFloat4;
          f2 = paramFloat3;
          f3 = f13;
          f4 = f12;
          break;
          bool = false;
          break label267;
          i1 = j;
          continue;
          addVertex(f7 + f5 * PGraphicsOpenGL.cosLUT[0], f8 + f6 * PGraphicsOpenGL.sinLUT[0], 0);
          if (paramBoolean2) {
            addEdge(j, n, false, true);
          }
          return;
          label441:
          i1 = n;
        }
        label448:
        f1 = paramFloat4;
        f2 = paramFloat3;
        f3 = paramFloat2;
        f4 = paramFloat1;
      }
    }
    
    void addLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean1, boolean paramBoolean2)
    {
      int i = addVertex(paramFloat1, paramFloat2, paramFloat3, 0);
      int j = addVertex(paramFloat4, paramFloat5, paramFloat6, 0);
      if (paramBoolean2) {
        addEdge(i, j, true, true);
      }
    }
    
    void addPoint(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2)
    {
      addVertex(paramFloat1, paramFloat2, paramFloat3, 0);
    }
    
    void addPolygonEdges(boolean paramBoolean)
    {
      int i = this.firstVertex;
      int j = 1 + this.firstVertex;
      int k = i;
      boolean bool = true;
      while (j <= this.lastVertex) {
        if (this.breaks[j] != 0)
        {
          if (paramBoolean) {
            addEdge(j - 1, k, bool, true);
          }
          bool = true;
          k = j;
          j++;
        }
        else
        {
          if (j == this.lastVertex) {
            if ((paramBoolean) && (k + 1 < j))
            {
              addEdge(j - 1, j, bool, false);
              addEdge(j, k, false, true);
            }
          }
          for (;;)
          {
            bool = false;
            break;
            addEdge(j - 1, j, bool, true);
            continue;
            if ((j < this.lastVertex) && (this.breaks[(j + 1)] != 0) && (!paramBoolean)) {
              addEdge(j - 1, j, bool, true);
            } else {
              addEdge(j - 1, j, bool, false);
            }
          }
        }
      }
    }
    
    void addQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, boolean paramBoolean1, boolean paramBoolean2)
    {
      int i = addVertex(paramFloat1, paramFloat2, paramFloat3, 0.0F, 0.0F, 0);
      int j = addVertex(paramFloat4, paramFloat5, paramFloat6, 1.0F, 0.0F, 0);
      int k = addVertex(paramFloat7, paramFloat8, paramFloat9, 1.0F, 1.0F, 0);
      int m = addVertex(paramFloat10, paramFloat11, paramFloat12, 0.0F, 1.0F, 0);
      if (paramBoolean2)
      {
        addEdge(i, j, true, false);
        addEdge(j, k, false, false);
        addEdge(k, m, false, false);
        addEdge(m, i, false, true);
      }
    }
    
    void addQuadStripEdges()
    {
      for (int i = 1; i < (1 + (this.lastVertex - this.firstVertex)) / 2; i++)
      {
        int j = this.firstVertex + 2 * (i - 1);
        int k = 1 + (this.firstVertex + 2 * (i - 1));
        int m = 1 + (this.firstVertex + i * 2);
        int n = this.firstVertex + i * 2;
        addEdge(j, k, true, false);
        addEdge(k, m, false, false);
        addEdge(m, n, false, false);
        addEdge(n, j, false, true);
      }
    }
    
    public void addQuadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
    {
      addQuadraticVertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramBoolean1, paramBoolean2, paramInt1, paramInt2, 20);
    }
    
    public void addQuadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, int paramInt3)
    {
      float f1 = getLastVertexX();
      float f2 = getLastVertexY();
      float f3 = getLastVertexZ();
      addBezierVertex(f1 + 2.0F * (paramFloat1 - f1) / 3.0F, f2 + 2.0F * (paramFloat2 - f2) / 3.0F, f3 + 2.0F * (paramFloat3 - f3) / 3.0F, paramFloat4 + 2.0F * (paramFloat1 - paramFloat4) / 3.0F, paramFloat5 + 2.0F * (paramFloat2 - paramFloat5) / 3.0F, paramFloat6 + 2.0F * (paramFloat3 - paramFloat6) / 3.0F, paramFloat4, paramFloat5, paramFloat6, paramBoolean1, paramBoolean2, paramInt1, paramInt2, paramInt3);
    }
    
    void addQuadsEdges()
    {
      for (int i = 0; i < (1 + (this.lastVertex - this.firstVertex)) / 4; i++)
      {
        int j = 0 + i * 4;
        int k = 1 + i * 4;
        int m = 2 + i * 4;
        int n = 3 + i * 4;
        addEdge(j, k, true, false);
        addEdge(k, m, false, false);
        addEdge(m, n, false, false);
        addEdge(n, j, false, true);
      }
    }
    
    void addRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2)
    {
      float f7;
      float f8;
      float f9;
      float f10;
      float f11;
      switch (paramInt2)
      {
      default: 
        f7 = paramFloat4;
        f8 = paramFloat3;
        f9 = paramFloat2;
        f10 = paramFloat1;
        if (f10 > f8) {
          f11 = f8;
        }
        break;
      }
      for (;;)
      {
        float f12;
        if (f9 > f7) {
          f12 = f9;
        }
        for (;;)
        {
          float f13 = PApplet.min((f10 - f11) / 2.0F, (f12 - f7) / 2.0F);
          if (paramFloat5 > f13) {
            paramFloat5 = f13;
          }
          if (paramFloat6 > f13) {
            paramFloat6 = f13;
          }
          if (paramFloat7 > f13) {
            paramFloat7 = f13;
          }
          if (paramFloat8 > f13) {
            paramFloat8 = f13;
          }
          if (PGraphicsOpenGL.nonZero(paramFloat6))
          {
            addVertex(f10 - paramFloat6, f7, 0);
            float f18 = f7 + paramFloat6;
            addQuadraticVertex(f10, f7, 0.0F, f10, f18, 0.0F, paramBoolean1, paramBoolean2, paramInt1, 0);
            label185:
            if (!PGraphicsOpenGL.nonZero(paramFloat7)) {
              break label483;
            }
            addVertex(f10, f12 - paramFloat7, 0);
            float f17 = f10 - paramFloat7;
            addQuadraticVertex(f10, f12, 0.0F, f17, f12, 0.0F, paramBoolean1, paramBoolean2, paramInt1, 0);
            label234:
            if (!PGraphicsOpenGL.nonZero(paramFloat8)) {
              break label496;
            }
            addVertex(f11 + paramFloat8, f12, 0);
            float f16 = f12 - paramFloat8;
            addQuadraticVertex(f11, f12, 0.0F, f11, f16, 0.0F, paramBoolean1, paramBoolean2, paramInt1, 0);
            label283:
            if (!PGraphicsOpenGL.nonZero(paramFloat5)) {
              break label509;
            }
            float f14 = f7 + paramFloat5;
            addVertex(f11, f14, 0);
            float f15 = f11 + paramFloat5;
            addQuadraticVertex(f11, f7, 0.0F, f15, f7, 0.0F, paramBoolean1, paramBoolean2, paramInt1, 0);
          }
          for (;;)
          {
            if (paramBoolean2) {
              addPolygonEdges(true);
            }
            return;
            f7 = paramFloat4;
            f8 = paramFloat3;
            f9 = paramFloat2;
            f10 = paramFloat1;
            break;
            float f20 = paramFloat3 + paramFloat1;
            f7 = paramFloat4 + paramFloat2;
            f8 = f20;
            f9 = paramFloat2;
            f10 = paramFloat1;
            break;
            f8 = paramFloat1 + paramFloat3;
            f7 = paramFloat2 + paramFloat4;
            float f19 = paramFloat1 - paramFloat3;
            f9 = paramFloat2 - paramFloat4;
            f10 = f19;
            break;
            float f1 = paramFloat3 / 2.0F;
            float f2 = paramFloat4 / 2.0F;
            float f3 = paramFloat1 + f1;
            float f4 = paramFloat2 + f2;
            float f5 = paramFloat1 - f1;
            float f6 = paramFloat2 - f2;
            f7 = f4;
            f8 = f3;
            f9 = f6;
            f10 = f5;
            break;
            addVertex(f10, f7, 0);
            break label185;
            label483:
            addVertex(f10, f12, 0);
            break label234;
            label496:
            addVertex(f11, f12, 0);
            break label283;
            label509:
            addVertex(f11, f7, 0);
          }
          f12 = f7;
          f7 = f9;
        }
        f11 = f10;
        f10 = f8;
      }
    }
    
    void addRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
    {
      float f7;
      float f8;
      float f9;
      float f10;
      switch (paramInt)
      {
      default: 
        f7 = paramFloat4;
        f8 = paramFloat3;
        f9 = paramFloat2;
        f10 = paramFloat1;
        if (f10 <= f8) {
          break;
        }
      }
      for (;;)
      {
        float f12;
        if (f9 > f7) {
          f12 = f7;
        }
        for (;;)
        {
          addQuad(f8, f12, 0.0F, f10, f12, 0.0F, f10, f9, 0.0F, f8, f9, 0.0F, paramBoolean1, paramBoolean2);
          return;
          f7 = paramFloat4;
          f8 = paramFloat3;
          f9 = paramFloat2;
          f10 = paramFloat1;
          break;
          float f14 = paramFloat3 + paramFloat1;
          f7 = paramFloat4 + paramFloat2;
          f8 = f14;
          f9 = paramFloat2;
          f10 = paramFloat1;
          break;
          f8 = paramFloat1 + paramFloat3;
          f7 = paramFloat2 + paramFloat4;
          float f13 = paramFloat1 - paramFloat3;
          f9 = paramFloat2 - paramFloat4;
          f10 = f13;
          break;
          float f1 = paramFloat3 / 2.0F;
          float f2 = paramFloat4 / 2.0F;
          float f3 = paramFloat1 + f1;
          float f4 = paramFloat2 + f2;
          float f5 = paramFloat1 - f1;
          float f6 = paramFloat2 - f2;
          f7 = f4;
          f8 = f3;
          f9 = f6;
          f10 = f5;
          break;
          f12 = f9;
          f9 = f7;
        }
        float f11 = f8;
        f8 = f10;
        f10 = f11;
      }
    }
    
    int[] addSphere(float paramFloat, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    {
      if ((paramInt1 < 3) || (paramInt2 < 2))
      {
        PGraphicsOpenGL.this.sphereDetail(30);
        paramInt2 = 30;
        paramInt1 = paramInt2;
      }
      int[] arrayOfInt;
      float f1;
      float f2;
      for (;;)
      {
        arrayOfInt = new int[paramInt1 * 3 + (3 + paramInt1 * 6) * (paramInt2 - 2) + paramInt1 * 3];
        f1 = 1.0F / paramInt1;
        f2 = 1.0F / paramInt2;
        float f3 = 1.0F;
        for (int i = 0; i < paramInt1; i++)
        {
          setNormal(0.0F, 1.0F, 0.0F);
          addVertex(0.0F, paramFloat, 0.0F, f3, 1.0F, 0);
          f3 -= f1;
        }
        PGraphicsOpenGL.this.sphereDetail(paramInt1, paramInt2);
      }
      float f4 = 1.0F;
      float f5 = 1.0F - f2;
      for (int j = 0; j < paramInt1; j++)
      {
        setNormal(PGraphicsOpenGL.this.sphereX[j], PGraphicsOpenGL.this.sphereY[j], PGraphicsOpenGL.this.sphereZ[j]);
        addVertex(paramFloat * PGraphicsOpenGL.this.sphereX[j], paramFloat * PGraphicsOpenGL.this.sphereY[j], paramFloat * PGraphicsOpenGL.this.sphereZ[j], f4, f5, 0);
        f4 -= f1;
      }
      int k = paramInt1 + paramInt1;
      setNormal(PGraphicsOpenGL.this.sphereX[0], PGraphicsOpenGL.this.sphereY[0], PGraphicsOpenGL.this.sphereZ[0]);
      addVertex(paramFloat * PGraphicsOpenGL.this.sphereX[0], paramFloat * PGraphicsOpenGL.this.sphereY[0], paramFloat * PGraphicsOpenGL.this.sphereZ[0], f4, f5, 0);
      int m = k + 1;
      for (int n = 0; n < paramInt1; n++)
      {
        int i20 = paramInt1 + n;
        int i21 = paramInt1 + n - paramInt1;
        arrayOfInt[(0 + n * 3)] = i20;
        arrayOfInt[(1 + n * 3)] = i21;
        arrayOfInt[(2 + n * 3)] = (i20 + 1);
        addEdge(i21, i20, true, true);
        addEdge(i20, i20 + 1, true, true);
      }
      int i1 = 0 + paramInt1 * 3;
      int i2 = 2;
      int i3 = paramInt1;
      int i4 = i1;
      int i5 = 0;
      while (i2 < paramInt2)
      {
        int i10 = i5 + paramInt1;
        float f7 = 1.0F;
        f5 -= f2;
        for (int i11 = 0; i11 < paramInt1; i11++)
        {
          int i19 = i10 + i11;
          setNormal(PGraphicsOpenGL.this.sphereX[i19], PGraphicsOpenGL.this.sphereY[i19], PGraphicsOpenGL.this.sphereZ[i19]);
          addVertex(paramFloat * PGraphicsOpenGL.this.sphereX[i19], paramFloat * PGraphicsOpenGL.this.sphereY[i19], paramFloat * PGraphicsOpenGL.this.sphereZ[i19], f7, f5, 0);
          f7 -= f1;
        }
        int i12 = m + paramInt1;
        setNormal(PGraphicsOpenGL.this.sphereX[i10], PGraphicsOpenGL.this.sphereY[i10], PGraphicsOpenGL.this.sphereZ[i10]);
        addVertex(paramFloat * PGraphicsOpenGL.this.sphereX[i10], paramFloat * PGraphicsOpenGL.this.sphereY[i10], paramFloat * PGraphicsOpenGL.this.sphereZ[i10], f7, f5, 0);
        int i13 = i12 + 1;
        for (int i14 = 0; i14 < paramInt1; i14++)
        {
          int i17 = m + i14;
          int i18 = -1 + (m + i14 - paramInt1);
          arrayOfInt[(0 + (i4 + i14 * 6))] = i17;
          arrayOfInt[(1 + (i4 + i14 * 6))] = i18;
          arrayOfInt[(2 + (i4 + i14 * 6))] = (i18 + 1);
          arrayOfInt[(3 + (i4 + i14 * 6))] = i17;
          arrayOfInt[(4 + (i4 + i14 * 6))] = (i18 + 1);
          arrayOfInt[(5 + (i4 + i14 * 6))] = (i17 + 1);
          addEdge(i18, i17, true, true);
          addEdge(i17, i17 + 1, true, true);
          addEdge(i18 + 1, i17, true, true);
        }
        int i15 = i4 + paramInt1 * 6;
        arrayOfInt[(i15 + 0)] = i12;
        arrayOfInt[(i15 + 1)] = (i12 - paramInt1);
        arrayOfInt[(i15 + 2)] = (i12 - 1);
        int i16 = i15 + 3;
        addEdge(i12 - paramInt1, i12 - 1, true, true);
        addEdge(i12 - 1, i12, true, true);
        i2++;
        i4 = i16;
        i5 = i10;
        i3 = m;
        m = i13;
      }
      float f6 = 1.0F;
      for (int i6 = 0; i6 < paramInt1; i6++)
      {
        setNormal(0.0F, -1.0F, 0.0F);
        addVertex(0.0F, -paramFloat, 0.0F, f6, 0.0F, 0);
        f6 -= f1;
      }
      (m + paramInt1);
      for (int i7 = 0; i7 < paramInt1; i7++)
      {
        int i8 = i3 + i7;
        int i9 = 1 + (paramInt1 + (i3 + i7));
        arrayOfInt[(0 + (i4 + i7 * 3))] = i8;
        arrayOfInt[(1 + (i4 + i7 * 3))] = i9;
        arrayOfInt[(2 + (i4 + i7 * 3))] = (i8 + 1);
        addEdge(i8, i8 + 1, true, true);
        addEdge(i8, i9, true, true);
      }
      (i4 + paramInt1 * 3);
      return arrayOfInt;
    }
    
    void addTriangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, boolean paramBoolean1, boolean paramBoolean2)
    {
      int i = addVertex(paramFloat1, paramFloat2, paramFloat3, 0);
      int j = addVertex(paramFloat4, paramFloat5, paramFloat6, 0);
      int k = addVertex(paramFloat7, paramFloat8, paramFloat9, 0);
      if (paramBoolean2)
      {
        addEdge(i, j, true, false);
        addEdge(j, k, false, false);
        addEdge(k, i, false, true);
      }
    }
    
    void addTriangleFanEdges()
    {
      for (int i = 1 + this.firstVertex; i < this.lastVertex; i++)
      {
        int j = this.firstVertex;
        int k = i + 1;
        addEdge(j, i, true, false);
        addEdge(i, k, false, false);
        addEdge(k, j, false, true);
      }
    }
    
    void addTriangleStripEdges()
    {
      int i = 1 + this.firstVertex;
      if (i < this.lastVertex)
      {
        int j;
        if (i % 2 == 0) {
          j = i - 1;
        }
        for (int k = i + 1;; k = i - 1)
        {
          addEdge(i, j, true, false);
          addEdge(j, k, false, false);
          addEdge(k, i, false, true);
          i++;
          break;
          j = i + 1;
        }
      }
    }
    
    void addTrianglesEdges()
    {
      for (int i = 0; i < (1 + (this.lastVertex - this.firstVertex)) / 3; i++)
      {
        int j = 0 + i * 3;
        int k = 1 + i * 3;
        int m = 2 + i * 3;
        addEdge(j, k, true, false);
        addEdge(k, m, false, false);
        addEdge(m, j, false, true);
      }
    }
    
    int addVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, int paramInt)
    {
      return addVertex(paramFloat1, paramFloat2, paramFloat3, this.fillColor, this.normalX, this.normalY, this.normalZ, paramFloat4, paramFloat5, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininessFactor, paramInt);
    }
    
    int addVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt)
    {
      return addVertex(paramFloat1, paramFloat2, 0.0F, this.fillColor, this.normalX, this.normalY, this.normalZ, paramFloat3, paramFloat4, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininessFactor, paramInt);
    }
    
    int addVertex(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt)
    {
      return addVertex(paramFloat1, paramFloat2, paramFloat3, this.fillColor, this.normalX, this.normalY, this.normalZ, 0.0F, 0.0F, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininessFactor, paramInt);
    }
    
    int addVertex(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt1, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt2, float paramFloat9, int paramInt3, int paramInt4, int paramInt5, float paramFloat10, int paramInt6)
    {
      vertexCheck();
      PGraphicsOpenGL.access$002(PGraphicsOpenGL.this, 0);
      int i = 3 * this.vertexCount;
      float[] arrayOfFloat1 = this.vertices;
      int j = i + 1;
      arrayOfFloat1[i] = paramFloat1;
      float[] arrayOfFloat2 = this.vertices;
      int k = j + 1;
      arrayOfFloat2[j] = paramFloat2;
      this.vertices[k] = paramFloat3;
      this.colors[this.vertexCount] = PGL.javaToNativeARGB(paramInt1);
      int m = 3 * this.vertexCount;
      float[] arrayOfFloat3 = this.normals;
      int n = m + 1;
      arrayOfFloat3[m] = paramFloat4;
      float[] arrayOfFloat4 = this.normals;
      int i1 = n + 1;
      arrayOfFloat4[n] = paramFloat5;
      this.normals[i1] = paramFloat6;
      int i2 = 2 * this.vertexCount;
      float[] arrayOfFloat5 = this.texcoords;
      int i3 = i2 + 1;
      arrayOfFloat5[i2] = paramFloat7;
      this.texcoords[i3] = paramFloat8;
      this.strokeColors[this.vertexCount] = PGL.javaToNativeARGB(paramInt2);
      this.strokeWeights[this.vertexCount] = paramFloat9;
      this.ambient[this.vertexCount] = PGL.javaToNativeARGB(paramInt3);
      this.specular[this.vertexCount] = PGL.javaToNativeARGB(paramInt4);
      this.emissive[this.vertexCount] = PGL.javaToNativeARGB(paramInt5);
      this.shininess[this.vertexCount] = paramFloat10;
      boolean[] arrayOfBoolean = this.breaks;
      int i4 = this.vertexCount;
      if (paramInt6 == 4) {}
      for (int i5 = 1;; i5 = 0)
      {
        arrayOfBoolean[i4] = i5;
        this.lastVertex = this.vertexCount;
        this.vertexCount = (1 + this.vertexCount);
        return this.lastVertex;
      }
    }
    
    int addVertex(float paramFloat1, float paramFloat2, int paramInt)
    {
      return addVertex(paramFloat1, paramFloat2, 0.0F, this.fillColor, this.normalX, this.normalY, this.normalZ, 0.0F, 0.0F, this.strokeColor, this.strokeWeight, this.ambientColor, this.specularColor, this.emissiveColor, this.shininessFactor, paramInt);
    }
    
    void allocate()
    {
      this.vertices = new float[48];
      this.colors = new int[16];
      this.normals = new float[48];
      this.texcoords = new float[32];
      this.strokeColors = new int[16];
      this.strokeWeights = new float[16];
      this.ambient = new int[16];
      this.specular = new int[16];
      this.emissive = new int[16];
      this.shininess = new float[16];
      this.breaks = new boolean[16];
      int[] arrayOfInt = { 32, 3 };
      this.edges = ((int[][])Array.newInstance(Integer.TYPE, arrayOfInt));
      clear();
    }
    
    void calcQuadStripNormals()
    {
      for (int i = 1; i < (1 + (this.lastVertex - this.firstVertex)) / 2; i++)
      {
        int j = this.firstVertex + 2 * (i - 1);
        int k = 1 + (this.firstVertex + 2 * (i - 1));
        int m = this.firstVertex + i * 2;
        int n = 1 + (this.firstVertex + i * 2);
        calcTriangleNormal(j, n, k);
        calcTriangleNormal(j, m, n);
      }
    }
    
    void calcQuadsNormals()
    {
      for (int i = 0; i < (1 + (this.lastVertex - this.firstVertex)) / 4; i++)
      {
        int j = 0 + i * 4;
        int k = 1 + i * 4;
        int m = 2 + i * 4;
        int n = 3 + i * 4;
        calcTriangleNormal(j, k, m);
        calcTriangleNormal(m, n, j);
      }
    }
    
    void calcTriangleFanNormals()
    {
      for (int i = 1 + this.firstVertex; i < this.lastVertex; i++) {
        calcTriangleNormal(this.firstVertex, i, i + 1);
      }
    }
    
    void calcTriangleNormal(int paramInt1, int paramInt2, int paramInt3)
    {
      int i = paramInt1 * 3;
      float[] arrayOfFloat1 = this.vertices;
      int j = i + 1;
      float f1 = arrayOfFloat1[i];
      float[] arrayOfFloat2 = this.vertices;
      int k = j + 1;
      float f2 = arrayOfFloat2[j];
      float f3 = this.vertices[k];
      int m = paramInt2 * 3;
      float[] arrayOfFloat3 = this.vertices;
      int n = m + 1;
      float f4 = arrayOfFloat3[m];
      float[] arrayOfFloat4 = this.vertices;
      int i1 = n + 1;
      float f5 = arrayOfFloat4[n];
      float f6 = this.vertices[i1];
      int i2 = paramInt3 * 3;
      float[] arrayOfFloat5 = this.vertices;
      int i3 = i2 + 1;
      float f7 = arrayOfFloat5[i2];
      float[] arrayOfFloat6 = this.vertices;
      int i4 = i3 + 1;
      float f8 = arrayOfFloat6[i3];
      float f9 = this.vertices[i4];
      float f10 = f7 - f4;
      float f11 = f8 - f5;
      float f12 = f9 - f6;
      float f13 = f1 - f4;
      float f14 = f2 - f5;
      float f15 = f3 - f6;
      float f16 = f11 * f15 - f14 * f12;
      float f17 = f12 * f13 - f15 * f10;
      float f18 = f14 * f10 - f13 * f11;
      float f19 = PApplet.sqrt(f16 * f16 + f17 * f17 + f18 * f18);
      float f20 = f16 / f19;
      float f21 = f17 / f19;
      float f22 = f18 / f19;
      int i5 = paramInt1 * 3;
      float[] arrayOfFloat7 = this.normals;
      int i6 = i5 + 1;
      arrayOfFloat7[i5] = f20;
      float[] arrayOfFloat8 = this.normals;
      int i7 = i6 + 1;
      arrayOfFloat8[i6] = f21;
      this.normals[i7] = f22;
      int i8 = paramInt2 * 3;
      float[] arrayOfFloat9 = this.normals;
      int i9 = i8 + 1;
      arrayOfFloat9[i8] = f20;
      float[] arrayOfFloat10 = this.normals;
      int i10 = i9 + 1;
      arrayOfFloat10[i9] = f21;
      this.normals[i10] = f22;
      int i11 = paramInt3 * 3;
      float[] arrayOfFloat11 = this.normals;
      int i12 = i11 + 1;
      arrayOfFloat11[i11] = f20;
      float[] arrayOfFloat12 = this.normals;
      int i13 = i12 + 1;
      arrayOfFloat12[i12] = f21;
      this.normals[i13] = f22;
    }
    
    void calcTriangleStripNormals()
    {
      int i = 1 + this.firstVertex;
      if (i < this.lastVertex)
      {
        int j;
        if (i % 2 == 0) {
          j = i + 1;
        }
        for (int k = i - 1;; k = i + 1)
        {
          calcTriangleNormal(j, i, k);
          i++;
          break;
          j = i - 1;
        }
      }
    }
    
    void calcTrianglesNormals()
    {
      for (int i = 0; i < (1 + (this.lastVertex - this.firstVertex)) / 3; i++) {
        calcTriangleNormal(0 + i * 3, 1 + i * 3, 2 + i * 3);
      }
    }
    
    void clear()
    {
      this.lastVertex = 0;
      this.firstVertex = 0;
      this.vertexCount = 0;
      this.lastEdge = 0;
      this.firstEdge = 0;
      this.edgeCount = 0;
    }
    
    void clearEdges()
    {
      this.lastEdge = 0;
      this.firstEdge = 0;
      this.edgeCount = 0;
    }
    
    void edgeCheck()
    {
      if (this.edgeCount == this.edges.length) {
        expandEdges(this.edgeCount << 1);
      }
    }
    
    void expandAmbient(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.ambient, 0, arrayOfInt, 0, this.vertexCount);
      this.ambient = arrayOfInt;
    }
    
    void expandBreaks(int paramInt)
    {
      boolean[] arrayOfBoolean = new boolean[paramInt];
      PApplet.arrayCopy(this.breaks, 0, arrayOfBoolean, 0, this.vertexCount);
      this.breaks = arrayOfBoolean;
    }
    
    void expandColors(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.colors, 0, arrayOfInt, 0, this.vertexCount);
      this.colors = arrayOfInt;
    }
    
    void expandEdges(int paramInt)
    {
      int[] arrayOfInt = { paramInt, 3 };
      int[][] arrayOfInt1 = (int[][])Array.newInstance(Integer.TYPE, arrayOfInt);
      PApplet.arrayCopy(this.edges, 0, arrayOfInt1, 0, this.edgeCount);
      this.edges = arrayOfInt1;
    }
    
    void expandEmissive(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.emissive, 0, arrayOfInt, 0, this.vertexCount);
      this.emissive = arrayOfInt;
    }
    
    void expandNormals(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt * 3];
      PApplet.arrayCopy(this.normals, 0, arrayOfFloat, 0, 3 * this.vertexCount);
      this.normals = arrayOfFloat;
    }
    
    void expandShininess(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt];
      PApplet.arrayCopy(this.shininess, 0, arrayOfFloat, 0, this.vertexCount);
      this.shininess = arrayOfFloat;
    }
    
    void expandSpecular(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.specular, 0, arrayOfInt, 0, this.vertexCount);
      this.specular = arrayOfInt;
    }
    
    void expandStrokeColors(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.strokeColors, 0, arrayOfInt, 0, this.vertexCount);
      this.strokeColors = arrayOfInt;
    }
    
    void expandStrokeWeights(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt];
      PApplet.arrayCopy(this.strokeWeights, 0, arrayOfFloat, 0, this.vertexCount);
      this.strokeWeights = arrayOfFloat;
    }
    
    void expandTexCoords(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt * 2];
      PApplet.arrayCopy(this.texcoords, 0, arrayOfFloat, 0, 2 * this.vertexCount);
      this.texcoords = arrayOfFloat;
    }
    
    void expandVertices(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt * 3];
      PApplet.arrayCopy(this.vertices, 0, arrayOfFloat, 0, 3 * this.vertexCount);
      this.vertices = arrayOfFloat;
    }
    
    float getLastVertexX()
    {
      return this.vertices[(0 + 3 * (-1 + this.vertexCount))];
    }
    
    float getLastVertexY()
    {
      return this.vertices[(1 + 3 * (-1 + this.vertexCount))];
    }
    
    float getLastVertexZ()
    {
      return this.vertices[(2 + 3 * (-1 + this.vertexCount))];
    }
    
    int getNumEdgeIndices(boolean paramBoolean)
    {
      int i = 6 * (1 + (this.lastEdge - this.firstEdge));
      int j = 0;
      if (paramBoolean) {
        for (int k = this.firstEdge; k <= this.lastEdge; k++)
        {
          int[] arrayOfInt = this.edges[k];
          if ((arrayOfInt[2] == 0) || (arrayOfInt[2] == 1)) {
            j += 6;
          }
        }
      }
      return i + j;
    }
    
    int getNumEdgeVertices(boolean paramBoolean)
    {
      int i = 4 * (1 + (this.lastEdge - this.firstEdge));
      int j = 0;
      if (paramBoolean) {
        for (int k = this.firstEdge; k <= this.lastEdge; k++)
        {
          int[] arrayOfInt = this.edges[k];
          if ((arrayOfInt[2] == 0) || (arrayOfInt[2] == 1)) {
            j++;
          }
        }
      }
      return i + j;
    }
    
    float[][] getVertexData()
    {
      int[] arrayOfInt = { this.vertexCount, 37 };
      float[][] arrayOfFloat = (float[][])Array.newInstance(Float.TYPE, arrayOfInt);
      for (int i = 0; i < this.vertexCount; i++)
      {
        float[] arrayOfFloat1 = arrayOfFloat[i];
        arrayOfFloat1[0] = this.vertices[(0 + i * 3)];
        arrayOfFloat1[1] = this.vertices[(1 + i * 3)];
        arrayOfFloat1[2] = this.vertices[(2 + i * 3)];
        arrayOfFloat1[3] = ((0xFF & this.colors[i] >> 16) / 255.0F);
        arrayOfFloat1[4] = ((0xFF & this.colors[i] >> 8) / 255.0F);
        arrayOfFloat1[5] = ((0xFF & this.colors[i] >> 0) / 255.0F);
        arrayOfFloat1[6] = ((0xFF & this.colors[i] >> 24) / 255.0F);
        arrayOfFloat1[7] = this.texcoords[(0 + i * 2)];
        arrayOfFloat1[8] = this.texcoords[(1 + i * 2)];
        arrayOfFloat1[9] = this.normals[(0 + i * 3)];
        arrayOfFloat1[10] = this.normals[(1 + i * 3)];
        arrayOfFloat1[11] = this.normals[(2 + i * 3)];
        arrayOfFloat1[13] = ((0xFF & this.strokeColors[i] >> 16) / 255.0F);
        arrayOfFloat1[14] = ((0xFF & this.strokeColors[i] >> 8) / 255.0F);
        arrayOfFloat1[15] = ((0xFF & this.strokeColors[i] >> 0) / 255.0F);
        arrayOfFloat1[16] = ((0xFF & this.strokeColors[i] >> 24) / 255.0F);
        arrayOfFloat1[17] = this.strokeWeights[i];
      }
      return arrayOfFloat;
    }
    
    void getVertexMax(PVector paramPVector)
    {
      for (int i = 0; i < this.vertexCount; i++)
      {
        int j = i * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.vertices;
        int k = j + 1;
        paramPVector.x = PApplet.max(f1, arrayOfFloat1[j]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.vertices;
        int m = k + 1;
        paramPVector.y = PApplet.max(f2, arrayOfFloat2[k]);
        paramPVector.z = PApplet.max(paramPVector.z, this.vertices[m]);
      }
    }
    
    void getVertexMin(PVector paramPVector)
    {
      for (int i = 0; i < this.vertexCount; i++)
      {
        int j = i * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.vertices;
        int k = j + 1;
        paramPVector.x = PApplet.min(f1, arrayOfFloat1[j]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.vertices;
        int m = k + 1;
        paramPVector.y = PApplet.min(f2, arrayOfFloat2[k]);
        paramPVector.z = PApplet.min(paramPVector.z, this.vertices[m]);
      }
    }
    
    int getVertexSum(PVector paramPVector)
    {
      for (int i = 0; i < this.vertexCount; i++)
      {
        int j = i * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.vertices;
        int k = j + 1;
        paramPVector.x = (f1 + arrayOfFloat1[j]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.vertices;
        int m = k + 1;
        paramPVector.y = (f2 + arrayOfFloat2[k]);
        paramPVector.z += this.vertices[m];
      }
      return this.vertexCount;
    }
    
    float getVertexX(int paramInt)
    {
      return this.vertices[(0 + paramInt * 3)];
    }
    
    float getVertexY(int paramInt)
    {
      return this.vertices[(1 + paramInt * 3)];
    }
    
    float getVertexZ(int paramInt)
    {
      return this.vertices[(2 + paramInt * 3)];
    }
    
    void setMaterial(int paramInt1, int paramInt2, float paramFloat1, int paramInt3, int paramInt4, int paramInt5, float paramFloat2)
    {
      this.fillColor = paramInt1;
      this.strokeColor = paramInt2;
      this.strokeWeight = paramFloat1;
      this.ambientColor = paramInt3;
      this.specularColor = paramInt4;
      this.emissiveColor = paramInt5;
      this.shininessFactor = paramFloat2;
    }
    
    void setNormal(float paramFloat1, float paramFloat2, float paramFloat3)
    {
      this.normalX = paramFloat1;
      this.normalY = paramFloat2;
      this.normalZ = paramFloat3;
    }
    
    void trim()
    {
      if ((this.vertexCount > 0) && (this.vertexCount < this.vertices.length / 3))
      {
        trimVertices();
        trimColors();
        trimNormals();
        trimTexCoords();
        trimStrokeColors();
        trimStrokeWeights();
        trimAmbient();
        trimSpecular();
        trimEmissive();
        trimShininess();
        trimBreaks();
      }
      if ((this.edgeCount > 0) && (this.edgeCount < this.edges.length)) {
        trimEdges();
      }
    }
    
    void trimAmbient()
    {
      int[] arrayOfInt = new int[this.vertexCount];
      PApplet.arrayCopy(this.ambient, 0, arrayOfInt, 0, this.vertexCount);
      this.ambient = arrayOfInt;
    }
    
    void trimBreaks()
    {
      boolean[] arrayOfBoolean = new boolean[this.vertexCount];
      PApplet.arrayCopy(this.breaks, 0, arrayOfBoolean, 0, this.vertexCount);
      this.breaks = arrayOfBoolean;
    }
    
    void trimColors()
    {
      int[] arrayOfInt = new int[this.vertexCount];
      PApplet.arrayCopy(this.colors, 0, arrayOfInt, 0, this.vertexCount);
      this.colors = arrayOfInt;
    }
    
    void trimEdges()
    {
      int[] arrayOfInt = { this.edgeCount, 3 };
      int[][] arrayOfInt1 = (int[][])Array.newInstance(Integer.TYPE, arrayOfInt);
      PApplet.arrayCopy(this.edges, 0, arrayOfInt1, 0, this.edgeCount);
      this.edges = arrayOfInt1;
    }
    
    void trimEmissive()
    {
      int[] arrayOfInt = new int[this.vertexCount];
      PApplet.arrayCopy(this.emissive, 0, arrayOfInt, 0, this.vertexCount);
      this.emissive = arrayOfInt;
    }
    
    void trimNormals()
    {
      float[] arrayOfFloat = new float[3 * this.vertexCount];
      PApplet.arrayCopy(this.normals, 0, arrayOfFloat, 0, 3 * this.vertexCount);
      this.normals = arrayOfFloat;
    }
    
    void trimShininess()
    {
      float[] arrayOfFloat = new float[this.vertexCount];
      PApplet.arrayCopy(this.shininess, 0, arrayOfFloat, 0, this.vertexCount);
      this.shininess = arrayOfFloat;
    }
    
    void trimSpecular()
    {
      int[] arrayOfInt = new int[this.vertexCount];
      PApplet.arrayCopy(this.specular, 0, arrayOfInt, 0, this.vertexCount);
      this.specular = arrayOfInt;
    }
    
    void trimStrokeColors()
    {
      int[] arrayOfInt = new int[this.vertexCount];
      PApplet.arrayCopy(this.strokeColors, 0, arrayOfInt, 0, this.vertexCount);
      this.strokeColors = arrayOfInt;
    }
    
    void trimStrokeWeights()
    {
      float[] arrayOfFloat = new float[this.vertexCount];
      PApplet.arrayCopy(this.strokeWeights, 0, arrayOfFloat, 0, this.vertexCount);
      this.strokeWeights = arrayOfFloat;
    }
    
    void trimTexCoords()
    {
      float[] arrayOfFloat = new float[2 * this.vertexCount];
      PApplet.arrayCopy(this.texcoords, 0, arrayOfFloat, 0, 2 * this.vertexCount);
      this.texcoords = arrayOfFloat;
    }
    
    void trimVertices()
    {
      float[] arrayOfFloat = new float[3 * this.vertexCount];
      PApplet.arrayCopy(this.vertices, 0, arrayOfFloat, 0, 3 * this.vertexCount);
      this.vertices = arrayOfFloat;
    }
    
    void vertexCheck()
    {
      if (this.vertexCount == this.vertices.length / 3)
      {
        int i = this.vertexCount << 1;
        expandVertices(i);
        expandColors(i);
        expandNormals(i);
        expandTexCoords(i);
        expandStrokeColors(i);
        expandStrokeWeights(i);
        expandAmbient(i);
        expandSpecular(i);
        expandEmissive(i);
        expandShininess(i);
        expandBreaks(i);
      }
    }
  }
  
  protected class IndexCache
  {
    int[] indexCount;
    int[] indexOffset;
    int size;
    int[] vertexCount;
    int[] vertexOffset;
    
    IndexCache()
    {
      allocate();
    }
    
    int addNew()
    {
      arrayCheck();
      init(this.size);
      this.size = (1 + this.size);
      return -1 + this.size;
    }
    
    int addNew(int paramInt)
    {
      arrayCheck();
      this.indexCount[this.size] = this.indexCount[paramInt];
      this.indexOffset[this.size] = this.indexOffset[paramInt];
      this.vertexCount[this.size] = this.vertexCount[paramInt];
      this.vertexOffset[this.size] = this.vertexOffset[paramInt];
      this.size = (1 + this.size);
      return -1 + this.size;
    }
    
    void allocate()
    {
      this.indexCount = new int[2];
      this.indexOffset = new int[2];
      this.vertexCount = new int[2];
      this.vertexOffset = new int[2];
      this.size = 0;
    }
    
    void arrayCheck()
    {
      if (this.size == this.indexCount.length)
      {
        int i = this.size << 1;
        expandIndexCount(i);
        expandIndexOffset(i);
        expandVertexCount(i);
        expandVertexOffset(i);
      }
    }
    
    void clear()
    {
      this.size = 0;
    }
    
    void expandIndexCount(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.indexCount, 0, arrayOfInt, 0, this.size);
      this.indexCount = arrayOfInt;
    }
    
    void expandIndexOffset(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.indexOffset, 0, arrayOfInt, 0, this.size);
      this.indexOffset = arrayOfInt;
    }
    
    void expandVertexCount(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.vertexCount, 0, arrayOfInt, 0, this.size);
      this.vertexCount = arrayOfInt;
    }
    
    void expandVertexOffset(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.vertexOffset, 0, arrayOfInt, 0, this.size);
      this.vertexOffset = arrayOfInt;
    }
    
    int getLast()
    {
      if (this.size == 0)
      {
        arrayCheck();
        init(0);
        this.size = 1;
      }
      return -1 + this.size;
    }
    
    void incCounts(int paramInt1, int paramInt2, int paramInt3)
    {
      int[] arrayOfInt1 = this.indexCount;
      arrayOfInt1[paramInt1] = (paramInt2 + arrayOfInt1[paramInt1]);
      int[] arrayOfInt2 = this.vertexCount;
      arrayOfInt2[paramInt1] = (paramInt3 + arrayOfInt2[paramInt1]);
    }
    
    void init(int paramInt)
    {
      if (paramInt > 0)
      {
        this.indexOffset[paramInt] = (this.indexOffset[(paramInt - 1)] + this.indexCount[(paramInt - 1)]);
        this.vertexOffset[paramInt] = (this.vertexOffset[(paramInt - 1)] + this.vertexCount[(paramInt - 1)]);
      }
      for (;;)
      {
        this.indexCount[paramInt] = 0;
        this.vertexCount[paramInt] = 0;
        return;
        this.indexOffset[paramInt] = 0;
        this.vertexOffset[paramInt] = 0;
      }
    }
  }
  
  protected class LightShader
    extends PGraphicsOpenGL.BaseShader
  {
    protected int ambientLoc;
    protected int colorLoc;
    protected int emissiveLoc;
    protected int lightAmbientLoc;
    protected int lightCountLoc;
    protected int lightDiffuseLoc;
    protected int lightFalloffLoc;
    protected int lightNormalLoc;
    protected int lightPositionLoc;
    protected int lightSpecularLoc;
    protected int lightSpotLoc;
    protected int normalLoc;
    protected int normalMatrixLoc;
    protected int shininessLoc;
    protected int specularLoc;
    protected int vertexLoc;
    
    public LightShader(PApplet paramPApplet)
    {
      super(paramPApplet);
    }
    
    public LightShader(PApplet paramPApplet, String paramString1, String paramString2)
    {
      super(paramPApplet, paramString1, paramString2);
    }
    
    public LightShader(PApplet paramPApplet, URL paramURL1, URL paramURL2)
    {
      super(paramPApplet, paramURL1, paramURL2);
    }
    
    public void bind()
    {
      super.bind();
      if (this.pgCurrent == null)
      {
        setRenderer(PGraphicsOpenGL.pgCurrent);
        loadAttributes();
        loadUniforms();
      }
      if (-1 < this.vertexLoc) {
        this.pgl.enableVertexAttribArray(this.vertexLoc);
      }
      if (-1 < this.colorLoc) {
        this.pgl.enableVertexAttribArray(this.colorLoc);
      }
      if (-1 < this.normalLoc) {
        this.pgl.enableVertexAttribArray(this.normalLoc);
      }
      if (-1 < this.ambientLoc) {
        this.pgl.enableVertexAttribArray(this.ambientLoc);
      }
      if (-1 < this.specularLoc) {
        this.pgl.enableVertexAttribArray(this.specularLoc);
      }
      if (-1 < this.emissiveLoc) {
        this.pgl.enableVertexAttribArray(this.emissiveLoc);
      }
      if (-1 < this.shininessLoc) {
        this.pgl.enableVertexAttribArray(this.shininessLoc);
      }
      if (-1 < this.normalMatrixLoc)
      {
        this.pgCurrent.updateGLNormal();
        setUniformMatrix(this.normalMatrixLoc, this.pgCurrent.glNormal);
      }
      int i = this.pgCurrent.lightCount;
      setUniformValue(this.lightCountLoc, i);
      setUniformVector(this.lightPositionLoc, this.pgCurrent.lightPosition, 4, i);
      setUniformVector(this.lightNormalLoc, this.pgCurrent.lightNormal, 3, i);
      setUniformVector(this.lightAmbientLoc, this.pgCurrent.lightAmbient, 3, i);
      setUniformVector(this.lightDiffuseLoc, this.pgCurrent.lightDiffuse, 3, i);
      setUniformVector(this.lightSpecularLoc, this.pgCurrent.lightSpecular, 3, i);
      setUniformVector(this.lightFalloffLoc, this.pgCurrent.lightFalloffCoefficients, 3, i);
      setUniformVector(this.lightSpotLoc, this.pgCurrent.lightSpotParameters, 2, i);
      setCommonUniforms();
    }
    
    public void loadAttributes()
    {
      this.vertexLoc = getAttributeLoc("vertex");
      this.colorLoc = getAttributeLoc("color");
      this.normalLoc = getAttributeLoc("normal");
      this.ambientLoc = getAttributeLoc("ambient");
      this.specularLoc = getAttributeLoc("specular");
      this.emissiveLoc = getAttributeLoc("emissive");
      this.shininessLoc = getAttributeLoc("shininess");
    }
    
    public void loadUniforms()
    {
      super.loadUniforms();
      this.normalMatrixLoc = getUniformLoc("normalMatrix");
      this.lightCountLoc = getUniformLoc("lightCount");
      this.lightPositionLoc = getUniformLoc("lightPosition");
      this.lightNormalLoc = getUniformLoc("lightNormal");
      this.lightAmbientLoc = getUniformLoc("lightAmbient");
      this.lightDiffuseLoc = getUniformLoc("lightDiffuse");
      this.lightSpecularLoc = getUniformLoc("lightSpecular");
      this.lightFalloffLoc = getUniformLoc("lightFalloff");
      this.lightSpotLoc = getUniformLoc("lightSpot");
    }
    
    public void setAmbientAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.ambientLoc, paramInt1, paramInt2, paramInt3, true, paramInt4, paramInt5);
    }
    
    public void setColorAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.colorLoc, paramInt1, paramInt2, paramInt3, true, paramInt4, paramInt5);
    }
    
    public void setEmissiveAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.emissiveLoc, paramInt1, paramInt2, paramInt3, true, paramInt4, paramInt5);
    }
    
    public void setNormalAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.normalLoc, paramInt1, paramInt2, paramInt3, false, paramInt4, paramInt5);
    }
    
    public void setShininessAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.shininessLoc, paramInt1, paramInt2, paramInt3, false, paramInt4, paramInt5);
    }
    
    public void setSpecularAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.specularLoc, paramInt1, paramInt2, paramInt3, true, paramInt4, paramInt5);
    }
    
    public void setVertexAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.vertexLoc, paramInt1, paramInt2, paramInt3, false, paramInt4, paramInt5);
    }
    
    public void unbind()
    {
      if (-1 < this.vertexLoc) {
        this.pgl.disableVertexAttribArray(this.vertexLoc);
      }
      if (-1 < this.colorLoc) {
        this.pgl.disableVertexAttribArray(this.colorLoc);
      }
      if (-1 < this.normalLoc) {
        this.pgl.disableVertexAttribArray(this.normalLoc);
      }
      if (-1 < this.ambientLoc) {
        this.pgl.disableVertexAttribArray(this.ambientLoc);
      }
      if (-1 < this.specularLoc) {
        this.pgl.disableVertexAttribArray(this.specularLoc);
      }
      if (-1 < this.emissiveLoc) {
        this.pgl.disableVertexAttribArray(this.emissiveLoc);
      }
      if (-1 < this.shininessLoc) {
        this.pgl.disableVertexAttribArray(this.shininessLoc);
      }
      super.unbind();
    }
  }
  
  protected class LineShader
    extends PGraphicsOpenGL.BaseShader
  {
    protected int colorLoc;
    protected int directionLoc;
    protected int perspectiveLoc;
    protected int scaleLoc;
    protected int vertexLoc;
    
    public LineShader(PApplet paramPApplet)
    {
      super(paramPApplet);
    }
    
    public LineShader(PApplet paramPApplet, String paramString1, String paramString2)
    {
      super(paramPApplet, paramString1, paramString2);
    }
    
    public LineShader(PApplet paramPApplet, URL paramURL1, URL paramURL2)
    {
      super(paramPApplet, paramURL1, paramURL2);
    }
    
    public void bind()
    {
      super.bind();
      if (this.pgCurrent == null)
      {
        setRenderer(PGraphicsOpenGL.pgCurrent);
        loadAttributes();
        loadUniforms();
      }
      if (-1 < this.vertexLoc) {
        this.pgl.enableVertexAttribArray(this.vertexLoc);
      }
      if (-1 < this.colorLoc) {
        this.pgl.enableVertexAttribArray(this.colorLoc);
      }
      if (-1 < this.directionLoc) {
        this.pgl.enableVertexAttribArray(this.directionLoc);
      }
      if ((this.pgCurrent.getHint(7)) && (this.pgCurrent.nonOrthoProjection()))
      {
        setUniformValue(this.perspectiveLoc, 1);
        if (!this.pgCurrent.getHint(6)) {
          break label154;
        }
        setUniformValue(this.scaleLoc, 1.0F, 1.0F, 1.0F);
      }
      for (;;)
      {
        setCommonUniforms();
        return;
        setUniformValue(this.perspectiveLoc, 0);
        break;
        label154:
        if (PGraphicsOpenGL.this.orthoProjection()) {
          setUniformValue(this.scaleLoc, 1.0F, 1.0F, 0.99F);
        } else {
          setUniformValue(this.scaleLoc, 0.99F, 0.99F, 0.99F);
        }
      }
    }
    
    public void loadAttributes()
    {
      this.vertexLoc = getAttributeLoc("vertex");
      this.colorLoc = getAttributeLoc("color");
      this.directionLoc = getAttributeLoc("direction");
    }
    
    public void loadUniforms()
    {
      super.loadUniforms();
      this.viewportLoc = getUniformLoc("viewport");
      this.perspectiveLoc = getUniformLoc("perspective");
      this.scaleLoc = getUniformLoc("scale");
    }
    
    public void setColorAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.colorLoc, paramInt1, paramInt2, paramInt3, true, paramInt4, paramInt5);
    }
    
    public void setLineAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.directionLoc, paramInt1, paramInt2, paramInt3, false, paramInt4, paramInt5);
    }
    
    public void setVertexAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.vertexLoc, paramInt1, paramInt2, paramInt3, false, paramInt4, paramInt5);
    }
    
    public void unbind()
    {
      if (-1 < this.vertexLoc) {
        this.pgl.disableVertexAttribArray(this.vertexLoc);
      }
      if (-1 < this.colorLoc) {
        this.pgl.disableVertexAttribArray(this.colorLoc);
      }
      if (-1 < this.directionLoc) {
        this.pgl.disableVertexAttribArray(this.directionLoc);
      }
      super.unbind();
    }
  }
  
  protected class PointShader
    extends PGraphicsOpenGL.BaseShader
  {
    protected int colorLoc;
    protected int offsetLoc;
    protected int perspectiveLoc;
    protected int vertexLoc;
    
    public PointShader(PApplet paramPApplet)
    {
      super(paramPApplet);
    }
    
    public PointShader(PApplet paramPApplet, String paramString1, String paramString2)
    {
      super(paramPApplet, paramString1, paramString2);
    }
    
    public PointShader(PApplet paramPApplet, URL paramURL1, URL paramURL2)
    {
      super(paramPApplet, paramURL1, paramURL2);
    }
    
    public void bind()
    {
      super.bind();
      if (this.pgCurrent == null)
      {
        setRenderer(PGraphicsOpenGL.pgCurrent);
        loadAttributes();
        loadUniforms();
      }
      if (-1 < this.vertexLoc) {
        this.pgl.enableVertexAttribArray(this.vertexLoc);
      }
      if (-1 < this.colorLoc) {
        this.pgl.enableVertexAttribArray(this.colorLoc);
      }
      if (-1 < this.offsetLoc) {
        this.pgl.enableVertexAttribArray(this.offsetLoc);
      }
      if ((this.pgCurrent.getHint(7)) && (this.pgCurrent.nonOrthoProjection())) {
        setUniformValue(this.perspectiveLoc, 1);
      }
      for (;;)
      {
        super.setCommonUniforms();
        return;
        setUniformValue(this.perspectiveLoc, 0);
      }
    }
    
    public void loadAttributes()
    {
      this.vertexLoc = getAttributeLoc("vertex");
      this.colorLoc = getAttributeLoc("color");
      this.offsetLoc = getAttributeLoc("offset");
    }
    
    public void loadUniforms()
    {
      super.loadUniforms();
      this.perspectiveLoc = getUniformLoc("perspective");
    }
    
    public void setColorAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.colorLoc, paramInt1, paramInt2, paramInt3, true, paramInt4, paramInt5);
    }
    
    public void setPointAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.offsetLoc, paramInt1, paramInt2, paramInt3, false, paramInt4, paramInt5);
    }
    
    public void setVertexAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.vertexLoc, paramInt1, paramInt2, paramInt3, false, paramInt4, paramInt5);
    }
    
    public void unbind()
    {
      if (-1 < this.vertexLoc) {
        this.pgl.disableVertexAttribArray(this.vertexLoc);
      }
      if (-1 < this.colorLoc) {
        this.pgl.disableVertexAttribArray(this.colorLoc);
      }
      if (-1 < this.offsetLoc) {
        this.pgl.disableVertexAttribArray(this.offsetLoc);
      }
      super.unbind();
    }
  }
  
  protected class TessGeometry
  {
    int firstLineIndex;
    int firstLineVertex;
    int firstPointIndex;
    int firstPointVertex;
    int firstPolyIndex;
    int firstPolyVertex;
    int lastLineIndex;
    int lastLineVertex;
    int lastPointIndex;
    int lastPointVertex;
    int lastPolyIndex;
    int lastPolyVertex;
    int[] lineColors;
    IntBuffer lineColorsBuffer;
    float[] lineDirections;
    FloatBuffer lineDirectionsBuffer;
    PGraphicsOpenGL.IndexCache lineIndexCache = new PGraphicsOpenGL.IndexCache(PGraphicsOpenGL.this);
    int lineIndexCount;
    short[] lineIndices;
    ShortBuffer lineIndicesBuffer;
    int lineVertexCount;
    float[] lineVertices;
    FloatBuffer lineVerticesBuffer;
    int[] pointColors;
    IntBuffer pointColorsBuffer;
    PGraphicsOpenGL.IndexCache pointIndexCache = new PGraphicsOpenGL.IndexCache(PGraphicsOpenGL.this);
    int pointIndexCount;
    short[] pointIndices;
    ShortBuffer pointIndicesBuffer;
    float[] pointOffsets;
    FloatBuffer pointOffsetsBuffer;
    int pointVertexCount;
    float[] pointVertices;
    FloatBuffer pointVerticesBuffer;
    int[] polyAmbient;
    IntBuffer polyAmbientBuffer;
    int[] polyColors;
    IntBuffer polyColorsBuffer;
    int[] polyEmissive;
    IntBuffer polyEmissiveBuffer;
    PGraphicsOpenGL.IndexCache polyIndexCache = new PGraphicsOpenGL.IndexCache(PGraphicsOpenGL.this);
    int polyIndexCount;
    short[] polyIndices;
    ShortBuffer polyIndicesBuffer;
    float[] polyNormals;
    FloatBuffer polyNormalsBuffer;
    float[] polyShininess;
    FloatBuffer polyShininessBuffer;
    int[] polySpecular;
    IntBuffer polySpecularBuffer;
    float[] polyTexCoords;
    FloatBuffer polyTexCoordsBuffer;
    int polyVertexCount;
    float[] polyVertices;
    FloatBuffer polyVerticesBuffer;
    int renderMode;
    
    TessGeometry(int paramInt)
    {
      this.renderMode = paramInt;
      allocate();
    }
    
    void addPolyVertex(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt1, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt2, int paramInt3, int paramInt4, float paramFloat9)
    {
      polyVertexCheck();
      setPolyVertex(-1 + this.polyVertexCount, paramFloat1, paramFloat2, paramFloat3, paramInt1, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramInt2, paramInt3, paramInt4, paramFloat9);
    }
    
    void addPolyVertex(PGraphicsOpenGL.InGeometry paramInGeometry, int paramInt)
    {
      addPolyVertices(paramInGeometry, paramInt, paramInt);
    }
    
    void addPolyVertices(PGraphicsOpenGL.InGeometry paramInGeometry)
    {
      addPolyVertices(paramInGeometry, paramInGeometry.firstVertex, paramInGeometry.lastVertex);
    }
    
    void addPolyVertices(PGraphicsOpenGL.InGeometry paramInGeometry, int paramInt1, int paramInt2)
    {
      int i = 1 + (paramInt2 - paramInt1);
      polyVertexCheck(i);
      PMatrix3D localPMatrix3D1;
      PMatrix3D localPMatrix3D2;
      int i23;
      if ((this.renderMode == 0) && (PGraphicsOpenGL.this.flushMode == 1))
      {
        localPMatrix3D1 = PGraphicsOpenGL.this.modelview;
        localPMatrix3D2 = PGraphicsOpenGL.this.modelviewInv;
        i23 = 0;
      }
      while (i23 < i)
      {
        int i24 = paramInt1 + i23;
        int i25 = i23 + this.firstPolyVertex;
        int i26 = i24 * 3;
        float[] arrayOfFloat12 = paramInGeometry.vertices;
        int i27 = i26 + 1;
        float f9 = arrayOfFloat12[i26];
        float[] arrayOfFloat13 = paramInGeometry.vertices;
        int i28 = i27 + 1;
        float f10 = arrayOfFloat13[i27];
        float f11 = paramInGeometry.vertices[i28];
        int i29 = i24 * 3;
        float[] arrayOfFloat14 = paramInGeometry.normals;
        int i30 = i29 + 1;
        float f12 = arrayOfFloat14[i29];
        float[] arrayOfFloat15 = paramInGeometry.normals;
        int i31 = i30 + 1;
        float f13 = arrayOfFloat15[i30];
        float f14 = paramInGeometry.normals[i31];
        int i32 = i25 * 4;
        float[] arrayOfFloat16 = this.polyVertices;
        int i33 = i32 + 1;
        arrayOfFloat16[i32] = (f9 * localPMatrix3D1.m00 + f10 * localPMatrix3D1.m01 + f11 * localPMatrix3D1.m02 + localPMatrix3D1.m03);
        float[] arrayOfFloat17 = this.polyVertices;
        int i34 = i33 + 1;
        arrayOfFloat17[i33] = (f9 * localPMatrix3D1.m10 + f10 * localPMatrix3D1.m11 + f11 * localPMatrix3D1.m12 + localPMatrix3D1.m13);
        float[] arrayOfFloat18 = this.polyVertices;
        int i35 = i34 + 1;
        arrayOfFloat18[i34] = (f9 * localPMatrix3D1.m20 + f10 * localPMatrix3D1.m21 + f11 * localPMatrix3D1.m22 + localPMatrix3D1.m23);
        this.polyVertices[i35] = (f9 * localPMatrix3D1.m30 + f10 * localPMatrix3D1.m31 + f11 * localPMatrix3D1.m32 + localPMatrix3D1.m33);
        int i36 = i25 * 3;
        float[] arrayOfFloat19 = this.polyNormals;
        int i37 = i36 + 1;
        arrayOfFloat19[i36] = (f12 * localPMatrix3D2.m00 + f13 * localPMatrix3D2.m10 + f14 * localPMatrix3D2.m20);
        float[] arrayOfFloat20 = this.polyNormals;
        int i38 = i37 + 1;
        arrayOfFloat20[i37] = (f12 * localPMatrix3D2.m01 + f13 * localPMatrix3D2.m11 + f14 * localPMatrix3D2.m21);
        this.polyNormals[i38] = (f12 * localPMatrix3D2.m02 + f13 * localPMatrix3D2.m12 + f14 * localPMatrix3D2.m22);
        i23++;
        continue;
        if (i <= 2) {
          for (int i7 = 0; i7 < i; i7++)
          {
            int i8 = paramInt1 + i7;
            int i9 = i7 + this.firstPolyVertex;
            int i10 = i8 * 3;
            float[] arrayOfFloat3 = paramInGeometry.vertices;
            int i11 = i10 + 1;
            float f3 = arrayOfFloat3[i10];
            float[] arrayOfFloat4 = paramInGeometry.vertices;
            int i12 = i11 + 1;
            float f4 = arrayOfFloat4[i11];
            float f5 = paramInGeometry.vertices[i12];
            int i13 = i8 * 3;
            float[] arrayOfFloat5 = paramInGeometry.normals;
            int i14 = i13 + 1;
            float f6 = arrayOfFloat5[i13];
            float[] arrayOfFloat6 = paramInGeometry.normals;
            int i15 = i14 + 1;
            float f7 = arrayOfFloat6[i14];
            float f8 = paramInGeometry.normals[i15];
            int i16 = i9 * 4;
            float[] arrayOfFloat7 = this.polyVertices;
            int i17 = i16 + 1;
            arrayOfFloat7[i16] = f3;
            float[] arrayOfFloat8 = this.polyVertices;
            int i18 = i17 + 1;
            arrayOfFloat8[i17] = f4;
            float[] arrayOfFloat9 = this.polyVertices;
            int i19 = i18 + 1;
            arrayOfFloat9[i18] = f5;
            this.polyVertices[i19] = 1.0F;
            int i20 = i9 * 3;
            float[] arrayOfFloat10 = this.polyNormals;
            int i21 = i20 + 1;
            arrayOfFloat10[i20] = f6;
            float[] arrayOfFloat11 = this.polyNormals;
            int i22 = i21 + 1;
            arrayOfFloat11[i21] = f7;
            this.polyNormals[i22] = f8;
          }
        }
        for (int j = 0; j < i; j++)
        {
          int i5 = paramInt1 + j;
          int i6 = j + this.firstPolyVertex;
          PApplet.arrayCopy(paramInGeometry.vertices, i5 * 3, this.polyVertices, i6 * 4, 3);
          this.polyVertices[(3 + i6 * 4)] = 1.0F;
        }
        PApplet.arrayCopy(paramInGeometry.normals, paramInt1 * 3, this.polyNormals, 3 * this.firstPolyVertex, i * 3);
      }
      if (i <= 2) {
        for (int k = 0; k < i; k++)
        {
          int m = paramInt1 + k;
          int n = k + this.firstPolyVertex;
          int i1 = m * 2;
          float[] arrayOfFloat1 = paramInGeometry.texcoords;
          int i2 = i1 + 1;
          float f1 = arrayOfFloat1[i1];
          float f2 = paramInGeometry.texcoords[i2];
          this.polyColors[n] = paramInGeometry.colors[m];
          int i3 = n * 2;
          float[] arrayOfFloat2 = this.polyTexCoords;
          int i4 = i3 + 1;
          arrayOfFloat2[i3] = f1;
          this.polyTexCoords[i4] = f2;
          this.polyAmbient[n] = paramInGeometry.ambient[m];
          this.polySpecular[n] = paramInGeometry.specular[m];
          this.polyEmissive[n] = paramInGeometry.emissive[m];
          this.polyShininess[n] = paramInGeometry.shininess[m];
        }
      }
      PApplet.arrayCopy(paramInGeometry.colors, paramInt1, this.polyColors, this.firstPolyVertex, i);
      PApplet.arrayCopy(paramInGeometry.texcoords, paramInt1 * 2, this.polyTexCoords, 2 * this.firstPolyVertex, i * 2);
      PApplet.arrayCopy(paramInGeometry.ambient, paramInt1, this.polyAmbient, this.firstPolyVertex, i);
      PApplet.arrayCopy(paramInGeometry.specular, paramInt1, this.polySpecular, this.firstPolyVertex, i);
      PApplet.arrayCopy(paramInGeometry.emissive, paramInt1, this.polyEmissive, this.firstPolyVertex, i);
      PApplet.arrayCopy(paramInGeometry.shininess, paramInt1, this.polyShininess, this.firstPolyVertex, i);
    }
    
    void allocate()
    {
      this.polyVertices = new float[64];
      this.polyColors = new int[16];
      this.polyNormals = new float[48];
      this.polyTexCoords = new float[32];
      this.polyAmbient = new int[16];
      this.polySpecular = new int[16];
      this.polyEmissive = new int[16];
      this.polyShininess = new float[16];
      this.polyIndices = new short[16];
      this.lineVertices = new float[64];
      this.lineColors = new int[16];
      this.lineDirections = new float[64];
      this.lineIndices = new short[16];
      this.pointVertices = new float[64];
      this.pointColors = new int[16];
      this.pointOffsets = new float[32];
      this.pointIndices = new short[16];
      this.polyVerticesBuffer = PGL.allocateFloatBuffer(this.polyVertices);
      this.polyColorsBuffer = PGL.allocateIntBuffer(this.polyColors);
      this.polyNormalsBuffer = PGL.allocateFloatBuffer(this.polyNormals);
      this.polyTexCoordsBuffer = PGL.allocateFloatBuffer(this.polyTexCoords);
      this.polyAmbientBuffer = PGL.allocateIntBuffer(this.polyAmbient);
      this.polySpecularBuffer = PGL.allocateIntBuffer(this.polySpecular);
      this.polyEmissiveBuffer = PGL.allocateIntBuffer(this.polyEmissive);
      this.polyShininessBuffer = PGL.allocateFloatBuffer(this.polyShininess);
      this.polyIndicesBuffer = PGL.allocateShortBuffer(this.polyIndices);
      this.lineVerticesBuffer = PGL.allocateFloatBuffer(this.lineVertices);
      this.lineColorsBuffer = PGL.allocateIntBuffer(this.lineColors);
      this.lineDirectionsBuffer = PGL.allocateFloatBuffer(this.lineDirections);
      this.lineIndicesBuffer = PGL.allocateShortBuffer(this.lineIndices);
      this.pointVerticesBuffer = PGL.allocateFloatBuffer(this.pointVertices);
      this.pointColorsBuffer = PGL.allocateIntBuffer(this.pointColors);
      this.pointOffsetsBuffer = PGL.allocateFloatBuffer(this.pointOffsets);
      this.pointIndicesBuffer = PGL.allocateShortBuffer(this.pointIndices);
      clear();
    }
    
    void applyMatrixOnLineGeometry(PMatrix2D paramPMatrix2D, int paramInt1, int paramInt2)
    {
      if (paramInt1 < paramInt2) {
        while (paramInt1 <= paramInt2)
        {
          int i = paramInt1 * 4;
          float[] arrayOfFloat1 = this.lineVertices;
          int j = i + 1;
          float f1 = arrayOfFloat1[i];
          float f2 = this.lineVertices[j];
          int k = paramInt1 * 4;
          float[] arrayOfFloat2 = this.lineDirections;
          int m = k + 1;
          float f3 = arrayOfFloat2[k];
          float f4 = this.lineDirections[m];
          float f5 = f3 - f1;
          float f6 = f4 - f2;
          int n = paramInt1 * 4;
          float[] arrayOfFloat3 = this.lineVertices;
          int i1 = n + 1;
          arrayOfFloat3[n] = (f1 * paramPMatrix2D.m00 + f2 * paramPMatrix2D.m01 + paramPMatrix2D.m02);
          this.lineVertices[i1] = (f1 * paramPMatrix2D.m10 + f2 * paramPMatrix2D.m11 + paramPMatrix2D.m12);
          int i2 = paramInt1 * 4;
          float[] arrayOfFloat4 = this.lineDirections;
          int i3 = i2 + 1;
          arrayOfFloat4[i2] = (f5 * paramPMatrix2D.m00 + f6 * paramPMatrix2D.m01);
          this.lineDirections[i3] = (f5 * paramPMatrix2D.m10 + f6 * paramPMatrix2D.m11);
          paramInt1++;
        }
      }
    }
    
    void applyMatrixOnLineGeometry(PMatrix3D paramPMatrix3D, int paramInt1, int paramInt2)
    {
      if (paramInt1 < paramInt2) {
        while (paramInt1 <= paramInt2)
        {
          int i = paramInt1 * 4;
          float[] arrayOfFloat1 = this.lineVertices;
          int j = i + 1;
          float f1 = arrayOfFloat1[i];
          float[] arrayOfFloat2 = this.lineVertices;
          int k = j + 1;
          float f2 = arrayOfFloat2[j];
          float[] arrayOfFloat3 = this.lineVertices;
          int m = k + 1;
          float f3 = arrayOfFloat3[k];
          float f4 = this.lineVertices[m];
          int n = paramInt1 * 4;
          float[] arrayOfFloat4 = this.lineDirections;
          int i1 = n + 1;
          float f5 = arrayOfFloat4[n];
          float[] arrayOfFloat5 = this.lineDirections;
          int i2 = i1 + 1;
          float f6 = arrayOfFloat5[i1];
          float f7 = this.lineDirections[i2];
          float f8 = f5 - f1;
          float f9 = f6 - f2;
          float f10 = f7 - f3;
          int i3 = paramInt1 * 4;
          float[] arrayOfFloat6 = this.lineVertices;
          int i4 = i3 + 1;
          arrayOfFloat6[i3] = (f1 * paramPMatrix3D.m00 + f2 * paramPMatrix3D.m01 + f3 * paramPMatrix3D.m02 + f4 * paramPMatrix3D.m03);
          float[] arrayOfFloat7 = this.lineVertices;
          int i5 = i4 + 1;
          arrayOfFloat7[i4] = (f1 * paramPMatrix3D.m10 + f2 * paramPMatrix3D.m11 + f3 * paramPMatrix3D.m12 + f4 * paramPMatrix3D.m13);
          float[] arrayOfFloat8 = this.lineVertices;
          int i6 = i5 + 1;
          arrayOfFloat8[i5] = (f1 * paramPMatrix3D.m20 + f2 * paramPMatrix3D.m21 + f3 * paramPMatrix3D.m22 + f4 * paramPMatrix3D.m23);
          this.lineVertices[i6] = (f1 * paramPMatrix3D.m30 + f2 * paramPMatrix3D.m31 + f3 * paramPMatrix3D.m32 + f4 * paramPMatrix3D.m33);
          int i7 = paramInt1 * 4;
          float[] arrayOfFloat9 = this.lineDirections;
          int i8 = i7 + 1;
          arrayOfFloat9[i7] = (f8 * paramPMatrix3D.m00 + f9 * paramPMatrix3D.m01 + f10 * paramPMatrix3D.m02);
          float[] arrayOfFloat10 = this.lineDirections;
          int i9 = i8 + 1;
          arrayOfFloat10[i8] = (f8 * paramPMatrix3D.m10 + f9 * paramPMatrix3D.m11 + f10 * paramPMatrix3D.m12);
          this.lineDirections[i9] = (f8 * paramPMatrix3D.m20 + f9 * paramPMatrix3D.m21 + f10 * paramPMatrix3D.m22);
          paramInt1++;
        }
      }
    }
    
    void applyMatrixOnLineGeometry(PMatrix paramPMatrix, int paramInt1, int paramInt2)
    {
      if ((paramPMatrix instanceof PMatrix2D)) {
        applyMatrixOnLineGeometry((PMatrix2D)paramPMatrix, paramInt1, paramInt2);
      }
      while (!(paramPMatrix instanceof PMatrix3D)) {
        return;
      }
      applyMatrixOnLineGeometry((PMatrix3D)paramPMatrix, paramInt1, paramInt2);
    }
    
    void applyMatrixOnPointGeometry(PMatrix2D paramPMatrix2D, int paramInt1, int paramInt2)
    {
      if (paramInt1 < paramInt2) {
        while (paramInt1 <= paramInt2)
        {
          int i = paramInt1 * 4;
          float[] arrayOfFloat1 = this.pointVertices;
          int j = i + 1;
          float f1 = arrayOfFloat1[i];
          float f2 = this.pointVertices[j];
          int k = paramInt1 * 4;
          float[] arrayOfFloat2 = this.pointVertices;
          int m = k + 1;
          arrayOfFloat2[k] = (f1 * paramPMatrix2D.m00 + f2 * paramPMatrix2D.m01 + paramPMatrix2D.m02);
          this.pointVertices[m] = (f1 * paramPMatrix2D.m10 + f2 * paramPMatrix2D.m11 + paramPMatrix2D.m12);
          paramInt1++;
        }
      }
    }
    
    void applyMatrixOnPointGeometry(PMatrix3D paramPMatrix3D, int paramInt1, int paramInt2)
    {
      if (paramInt1 < paramInt2) {
        while (paramInt1 <= paramInt2)
        {
          int i = paramInt1 * 4;
          float[] arrayOfFloat1 = this.pointVertices;
          int j = i + 1;
          float f1 = arrayOfFloat1[i];
          float[] arrayOfFloat2 = this.pointVertices;
          int k = j + 1;
          float f2 = arrayOfFloat2[j];
          float[] arrayOfFloat3 = this.pointVertices;
          int m = k + 1;
          float f3 = arrayOfFloat3[k];
          float f4 = this.pointVertices[m];
          int n = paramInt1 * 4;
          float[] arrayOfFloat4 = this.pointVertices;
          int i1 = n + 1;
          arrayOfFloat4[n] = (f1 * paramPMatrix3D.m00 + f2 * paramPMatrix3D.m01 + f3 * paramPMatrix3D.m02 + f4 * paramPMatrix3D.m03);
          float[] arrayOfFloat5 = this.pointVertices;
          int i2 = i1 + 1;
          arrayOfFloat5[i1] = (f1 * paramPMatrix3D.m10 + f2 * paramPMatrix3D.m11 + f3 * paramPMatrix3D.m12 + f4 * paramPMatrix3D.m13);
          float[] arrayOfFloat6 = this.pointVertices;
          int i3 = i2 + 1;
          arrayOfFloat6[i2] = (f1 * paramPMatrix3D.m20 + f2 * paramPMatrix3D.m21 + f3 * paramPMatrix3D.m22 + f4 * paramPMatrix3D.m23);
          this.pointVertices[i3] = (f1 * paramPMatrix3D.m30 + f2 * paramPMatrix3D.m31 + f3 * paramPMatrix3D.m32 + f4 * paramPMatrix3D.m33);
          paramInt1++;
        }
      }
    }
    
    void applyMatrixOnPointGeometry(PMatrix paramPMatrix, int paramInt1, int paramInt2)
    {
      if ((paramPMatrix instanceof PMatrix2D)) {
        applyMatrixOnPointGeometry((PMatrix2D)paramPMatrix, paramInt1, paramInt2);
      }
      while (!(paramPMatrix instanceof PMatrix3D)) {
        return;
      }
      applyMatrixOnPointGeometry((PMatrix3D)paramPMatrix, paramInt1, paramInt2);
    }
    
    void applyMatrixOnPolyGeometry(PMatrix2D paramPMatrix2D, int paramInt1, int paramInt2)
    {
      if (paramInt1 < paramInt2) {
        while (paramInt1 <= paramInt2)
        {
          int i = paramInt1 * 4;
          float[] arrayOfFloat1 = this.polyVertices;
          int j = i + 1;
          float f1 = arrayOfFloat1[i];
          float f2 = this.polyVertices[j];
          int k = paramInt1 * 3;
          float[] arrayOfFloat2 = this.polyNormals;
          int m = k + 1;
          float f3 = arrayOfFloat2[k];
          float f4 = this.polyNormals[m];
          int n = paramInt1 * 4;
          float[] arrayOfFloat3 = this.polyVertices;
          int i1 = n + 1;
          arrayOfFloat3[n] = (f1 * paramPMatrix2D.m00 + f2 * paramPMatrix2D.m01 + paramPMatrix2D.m02);
          this.polyVertices[i1] = (f1 * paramPMatrix2D.m10 + f2 * paramPMatrix2D.m11 + paramPMatrix2D.m12);
          int i2 = paramInt1 * 3;
          float[] arrayOfFloat4 = this.polyNormals;
          int i3 = i2 + 1;
          arrayOfFloat4[i2] = (f3 * paramPMatrix2D.m00 + f4 * paramPMatrix2D.m01);
          this.polyNormals[i3] = (f3 * paramPMatrix2D.m10 + f4 * paramPMatrix2D.m11);
          paramInt1++;
        }
      }
    }
    
    void applyMatrixOnPolyGeometry(PMatrix3D paramPMatrix3D, int paramInt1, int paramInt2)
    {
      if (paramInt1 < paramInt2) {
        while (paramInt1 <= paramInt2)
        {
          int i = paramInt1 * 4;
          float[] arrayOfFloat1 = this.polyVertices;
          int j = i + 1;
          float f1 = arrayOfFloat1[i];
          float[] arrayOfFloat2 = this.polyVertices;
          int k = j + 1;
          float f2 = arrayOfFloat2[j];
          float[] arrayOfFloat3 = this.polyVertices;
          int m = k + 1;
          float f3 = arrayOfFloat3[k];
          float f4 = this.polyVertices[m];
          int n = paramInt1 * 3;
          float[] arrayOfFloat4 = this.polyNormals;
          int i1 = n + 1;
          float f5 = arrayOfFloat4[n];
          float[] arrayOfFloat5 = this.polyNormals;
          int i2 = i1 + 1;
          float f6 = arrayOfFloat5[i1];
          float f7 = this.polyNormals[i2];
          int i3 = paramInt1 * 4;
          float[] arrayOfFloat6 = this.polyVertices;
          int i4 = i3 + 1;
          arrayOfFloat6[i3] = (f1 * paramPMatrix3D.m00 + f2 * paramPMatrix3D.m01 + f3 * paramPMatrix3D.m02 + f4 * paramPMatrix3D.m03);
          float[] arrayOfFloat7 = this.polyVertices;
          int i5 = i4 + 1;
          arrayOfFloat7[i4] = (f1 * paramPMatrix3D.m10 + f2 * paramPMatrix3D.m11 + f3 * paramPMatrix3D.m12 + f4 * paramPMatrix3D.m13);
          float[] arrayOfFloat8 = this.polyVertices;
          int i6 = i5 + 1;
          arrayOfFloat8[i5] = (f1 * paramPMatrix3D.m20 + f2 * paramPMatrix3D.m21 + f3 * paramPMatrix3D.m22 + f4 * paramPMatrix3D.m23);
          this.polyVertices[i6] = (f1 * paramPMatrix3D.m30 + f2 * paramPMatrix3D.m31 + f3 * paramPMatrix3D.m32 + f4 * paramPMatrix3D.m33);
          int i7 = paramInt1 * 3;
          float[] arrayOfFloat9 = this.polyNormals;
          int i8 = i7 + 1;
          arrayOfFloat9[i7] = (f5 * paramPMatrix3D.m00 + f6 * paramPMatrix3D.m01 + f7 * paramPMatrix3D.m02);
          float[] arrayOfFloat10 = this.polyNormals;
          int i9 = i8 + 1;
          arrayOfFloat10[i8] = (f5 * paramPMatrix3D.m10 + f6 * paramPMatrix3D.m11 + f7 * paramPMatrix3D.m12);
          this.polyNormals[i9] = (f5 * paramPMatrix3D.m20 + f6 * paramPMatrix3D.m21 + f7 * paramPMatrix3D.m22);
          paramInt1++;
        }
      }
    }
    
    void applyMatrixOnPolyGeometry(PMatrix paramPMatrix, int paramInt1, int paramInt2)
    {
      if ((paramPMatrix instanceof PMatrix2D)) {
        applyMatrixOnPolyGeometry((PMatrix2D)paramPMatrix, paramInt1, paramInt2);
      }
      while (!(paramPMatrix instanceof PMatrix3D)) {
        return;
      }
      applyMatrixOnPolyGeometry((PMatrix3D)paramPMatrix, paramInt1, paramInt2);
    }
    
    void calcPolyNormal(int paramInt1, int paramInt2, int paramInt3)
    {
      int i = paramInt1 * 4;
      float[] arrayOfFloat1 = this.polyVertices;
      int j = i + 1;
      float f1 = arrayOfFloat1[i];
      float[] arrayOfFloat2 = this.polyVertices;
      int k = j + 1;
      float f2 = arrayOfFloat2[j];
      float f3 = this.polyVertices[k];
      int m = paramInt2 * 4;
      float[] arrayOfFloat3 = this.polyVertices;
      int n = m + 1;
      float f4 = arrayOfFloat3[m];
      float[] arrayOfFloat4 = this.polyVertices;
      int i1 = n + 1;
      float f5 = arrayOfFloat4[n];
      float f6 = this.polyVertices[i1];
      int i2 = paramInt3 * 4;
      float[] arrayOfFloat5 = this.polyVertices;
      int i3 = i2 + 1;
      float f7 = arrayOfFloat5[i2];
      float[] arrayOfFloat6 = this.polyVertices;
      int i4 = i3 + 1;
      float f8 = arrayOfFloat6[i3];
      float f9 = this.polyVertices[i4];
      float f10 = f7 - f4;
      float f11 = f8 - f5;
      float f12 = f9 - f6;
      float f13 = f1 - f4;
      float f14 = f2 - f5;
      float f15 = f3 - f6;
      float f16 = f11 * f15 - f14 * f12;
      float f17 = f12 * f13 - f15 * f10;
      float f18 = f14 * f10 - f13 * f11;
      float f19 = PApplet.sqrt(f16 * f16 + f17 * f17 + f18 * f18);
      float f20 = f16 / f19;
      float f21 = f17 / f19;
      float f22 = f18 / f19;
      int i5 = paramInt1 * 3;
      float[] arrayOfFloat7 = this.polyNormals;
      int i6 = i5 + 1;
      arrayOfFloat7[i5] = f20;
      float[] arrayOfFloat8 = this.polyNormals;
      int i7 = i6 + 1;
      arrayOfFloat8[i6] = f21;
      this.polyNormals[i7] = f22;
      int i8 = paramInt2 * 3;
      float[] arrayOfFloat9 = this.polyNormals;
      int i9 = i8 + 1;
      arrayOfFloat9[i8] = f20;
      float[] arrayOfFloat10 = this.polyNormals;
      int i10 = i9 + 1;
      arrayOfFloat10[i9] = f21;
      this.polyNormals[i10] = f22;
      int i11 = paramInt3 * 3;
      float[] arrayOfFloat11 = this.polyNormals;
      int i12 = i11 + 1;
      arrayOfFloat11[i11] = f20;
      float[] arrayOfFloat12 = this.polyNormals;
      int i13 = i12 + 1;
      arrayOfFloat12[i12] = f21;
      this.polyNormals[i13] = f22;
    }
    
    void clear()
    {
      this.polyVertexCount = 0;
      this.lastPolyVertex = 0;
      this.firstPolyVertex = 0;
      this.polyIndexCount = 0;
      this.lastPolyIndex = 0;
      this.firstPolyIndex = 0;
      this.lineVertexCount = 0;
      this.lastLineVertex = 0;
      this.firstLineVertex = 0;
      this.lineIndexCount = 0;
      this.lastLineIndex = 0;
      this.firstLineIndex = 0;
      this.pointVertexCount = 0;
      this.lastPointVertex = 0;
      this.firstPointVertex = 0;
      this.pointIndexCount = 0;
      this.lastPointIndex = 0;
      this.firstPointIndex = 0;
      this.polyIndexCache.clear();
      this.lineIndexCache.clear();
      this.pointIndexCache.clear();
    }
    
    void expandLineColors(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.lineColors, 0, arrayOfInt, 0, this.lineVertexCount);
      this.lineColors = arrayOfInt;
      this.lineColorsBuffer = PGL.allocateIntBuffer(this.lineColors);
    }
    
    void expandLineDirections(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt * 4];
      PApplet.arrayCopy(this.lineDirections, 0, arrayOfFloat, 0, 4 * this.lineVertexCount);
      this.lineDirections = arrayOfFloat;
      this.lineDirectionsBuffer = PGL.allocateFloatBuffer(this.lineDirections);
    }
    
    void expandLineIndices(int paramInt)
    {
      short[] arrayOfShort = new short[paramInt];
      PApplet.arrayCopy(this.lineIndices, 0, arrayOfShort, 0, this.lineIndexCount);
      this.lineIndices = arrayOfShort;
      this.lineIndicesBuffer = PGL.allocateShortBuffer(this.lineIndices);
    }
    
    void expandLineVertices(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt * 4];
      PApplet.arrayCopy(this.lineVertices, 0, arrayOfFloat, 0, 4 * this.lineVertexCount);
      this.lineVertices = arrayOfFloat;
      this.lineVerticesBuffer = PGL.allocateFloatBuffer(this.lineVertices);
    }
    
    void expandPointColors(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.pointColors, 0, arrayOfInt, 0, this.pointVertexCount);
      this.pointColors = arrayOfInt;
      this.pointColorsBuffer = PGL.allocateIntBuffer(this.pointColors);
    }
    
    void expandPointIndices(int paramInt)
    {
      short[] arrayOfShort = new short[paramInt];
      PApplet.arrayCopy(this.pointIndices, 0, arrayOfShort, 0, this.pointIndexCount);
      this.pointIndices = arrayOfShort;
      this.pointIndicesBuffer = PGL.allocateShortBuffer(this.pointIndices);
    }
    
    void expandPointOffsets(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt * 2];
      PApplet.arrayCopy(this.pointOffsets, 0, arrayOfFloat, 0, 2 * this.pointVertexCount);
      this.pointOffsets = arrayOfFloat;
      this.pointOffsetsBuffer = PGL.allocateFloatBuffer(this.pointOffsets);
    }
    
    void expandPointVertices(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt * 4];
      PApplet.arrayCopy(this.pointVertices, 0, arrayOfFloat, 0, 4 * this.pointVertexCount);
      this.pointVertices = arrayOfFloat;
      this.pointVerticesBuffer = PGL.allocateFloatBuffer(this.pointVertices);
    }
    
    void expandPolyAmbient(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.polyAmbient, 0, arrayOfInt, 0, this.polyVertexCount);
      this.polyAmbient = arrayOfInt;
      this.polyAmbientBuffer = PGL.allocateIntBuffer(this.polyAmbient);
    }
    
    void expandPolyColors(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.polyColors, 0, arrayOfInt, 0, this.polyVertexCount);
      this.polyColors = arrayOfInt;
      this.polyColorsBuffer = PGL.allocateIntBuffer(this.polyColors);
    }
    
    void expandPolyEmissive(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.polyEmissive, 0, arrayOfInt, 0, this.polyVertexCount);
      this.polyEmissive = arrayOfInt;
      this.polyEmissiveBuffer = PGL.allocateIntBuffer(this.polyEmissive);
    }
    
    void expandPolyIndices(int paramInt)
    {
      short[] arrayOfShort = new short[paramInt];
      PApplet.arrayCopy(this.polyIndices, 0, arrayOfShort, 0, this.polyIndexCount);
      this.polyIndices = arrayOfShort;
      this.polyIndicesBuffer = PGL.allocateShortBuffer(this.polyIndices);
    }
    
    void expandPolyNormals(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt * 3];
      PApplet.arrayCopy(this.polyNormals, 0, arrayOfFloat, 0, 3 * this.polyVertexCount);
      this.polyNormals = arrayOfFloat;
      this.polyNormalsBuffer = PGL.allocateFloatBuffer(this.polyNormals);
    }
    
    void expandPolyShininess(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt];
      PApplet.arrayCopy(this.polyShininess, 0, arrayOfFloat, 0, this.polyVertexCount);
      this.polyShininess = arrayOfFloat;
      this.polyShininessBuffer = PGL.allocateFloatBuffer(this.polyShininess);
    }
    
    void expandPolySpecular(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.polySpecular, 0, arrayOfInt, 0, this.polyVertexCount);
      this.polySpecular = arrayOfInt;
      this.polySpecularBuffer = PGL.allocateIntBuffer(this.polySpecular);
    }
    
    void expandPolyTexCoords(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt * 2];
      PApplet.arrayCopy(this.polyTexCoords, 0, arrayOfFloat, 0, 2 * this.polyVertexCount);
      this.polyTexCoords = arrayOfFloat;
      this.polyTexCoordsBuffer = PGL.allocateFloatBuffer(this.polyTexCoords);
    }
    
    void expandPolyVertices(int paramInt)
    {
      float[] arrayOfFloat = new float[paramInt * 4];
      PApplet.arrayCopy(this.polyVertices, 0, arrayOfFloat, 0, 4 * this.polyVertexCount);
      this.polyVertices = arrayOfFloat;
      this.polyVerticesBuffer = PGL.allocateFloatBuffer(this.polyVertices);
    }
    
    void getLineVertexMax(PVector paramPVector, int paramInt1, int paramInt2)
    {
      while (paramInt1 <= paramInt2)
      {
        int i = paramInt1 * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.lineVertices;
        int j = i + 1;
        paramPVector.x = PApplet.max(f1, arrayOfFloat1[i]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.lineVertices;
        int k = j + 1;
        paramPVector.y = PApplet.max(f2, arrayOfFloat2[j]);
        paramPVector.z = PApplet.max(paramPVector.z, this.lineVertices[k]);
        paramInt1++;
      }
    }
    
    void getLineVertexMin(PVector paramPVector, int paramInt1, int paramInt2)
    {
      while (paramInt1 <= paramInt2)
      {
        int i = paramInt1 * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.lineVertices;
        int j = i + 1;
        paramPVector.x = PApplet.min(f1, arrayOfFloat1[i]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.lineVertices;
        int k = j + 1;
        paramPVector.y = PApplet.min(f2, arrayOfFloat2[j]);
        paramPVector.z = PApplet.min(paramPVector.z, this.lineVertices[k]);
        paramInt1++;
      }
    }
    
    int getLineVertexSum(PVector paramPVector, int paramInt1, int paramInt2)
    {
      for (int i = paramInt1; i <= paramInt2; i++)
      {
        int j = i * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.lineVertices;
        int k = j + 1;
        paramPVector.x = (f1 + arrayOfFloat1[j]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.lineVertices;
        int m = k + 1;
        paramPVector.y = (f2 + arrayOfFloat2[k]);
        paramPVector.z += this.lineVertices[m];
      }
      return 1 + (paramInt2 - paramInt1);
    }
    
    void getPointVertexMax(PVector paramPVector, int paramInt1, int paramInt2)
    {
      while (paramInt1 <= paramInt2)
      {
        int i = paramInt1 * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.pointVertices;
        int j = i + 1;
        paramPVector.x = PApplet.max(f1, arrayOfFloat1[i]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.pointVertices;
        int k = j + 1;
        paramPVector.y = PApplet.max(f2, arrayOfFloat2[j]);
        paramPVector.z = PApplet.max(paramPVector.z, this.pointVertices[k]);
        paramInt1++;
      }
    }
    
    void getPointVertexMin(PVector paramPVector, int paramInt1, int paramInt2)
    {
      while (paramInt1 <= paramInt2)
      {
        int i = paramInt1 * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.pointVertices;
        int j = i + 1;
        paramPVector.x = PApplet.min(f1, arrayOfFloat1[i]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.pointVertices;
        int k = j + 1;
        paramPVector.y = PApplet.min(f2, arrayOfFloat2[j]);
        paramPVector.z = PApplet.min(paramPVector.z, this.pointVertices[k]);
        paramInt1++;
      }
    }
    
    int getPointVertexSum(PVector paramPVector, int paramInt1, int paramInt2)
    {
      for (int i = paramInt1; i <= paramInt2; i++)
      {
        int j = i * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.pointVertices;
        int k = j + 1;
        paramPVector.x = (f1 + arrayOfFloat1[j]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.pointVertices;
        int m = k + 1;
        paramPVector.y = (f2 + arrayOfFloat2[k]);
        paramPVector.z += this.pointVertices[m];
      }
      return 1 + (paramInt2 - paramInt1);
    }
    
    void getPolyVertexMax(PVector paramPVector, int paramInt1, int paramInt2)
    {
      while (paramInt1 <= paramInt2)
      {
        int i = paramInt1 * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.polyVertices;
        int j = i + 1;
        paramPVector.x = PApplet.max(f1, arrayOfFloat1[i]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.polyVertices;
        int k = j + 1;
        paramPVector.y = PApplet.max(f2, arrayOfFloat2[j]);
        paramPVector.z = PApplet.max(paramPVector.z, this.polyVertices[k]);
        paramInt1++;
      }
    }
    
    void getPolyVertexMin(PVector paramPVector, int paramInt1, int paramInt2)
    {
      while (paramInt1 <= paramInt2)
      {
        int i = paramInt1 * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.polyVertices;
        int j = i + 1;
        paramPVector.x = PApplet.min(f1, arrayOfFloat1[i]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.polyVertices;
        int k = j + 1;
        paramPVector.y = PApplet.min(f2, arrayOfFloat2[j]);
        paramPVector.z = PApplet.min(paramPVector.z, this.polyVertices[k]);
        paramInt1++;
      }
    }
    
    int getPolyVertexSum(PVector paramPVector, int paramInt1, int paramInt2)
    {
      for (int i = paramInt1; i <= paramInt2; i++)
      {
        int j = i * 4;
        float f1 = paramPVector.x;
        float[] arrayOfFloat1 = this.polyVertices;
        int k = j + 1;
        paramPVector.x = (f1 + arrayOfFloat1[j]);
        float f2 = paramPVector.y;
        float[] arrayOfFloat2 = this.polyVertices;
        int m = k + 1;
        paramPVector.y = (f2 + arrayOfFloat2[k]);
        paramPVector.z += this.polyVertices[m];
      }
      return 1 + (paramInt2 - paramInt1);
    }
    
    void incLineIndices(int paramInt1, int paramInt2, int paramInt3)
    {
      while (paramInt1 <= paramInt2)
      {
        short[] arrayOfShort = this.lineIndices;
        arrayOfShort[paramInt1] = ((short)(paramInt3 + arrayOfShort[paramInt1]));
        paramInt1++;
      }
    }
    
    void incPointIndices(int paramInt1, int paramInt2, int paramInt3)
    {
      while (paramInt1 <= paramInt2)
      {
        short[] arrayOfShort = this.pointIndices;
        arrayOfShort[paramInt1] = ((short)(paramInt3 + arrayOfShort[paramInt1]));
        paramInt1++;
      }
    }
    
    void incPolyIndices(int paramInt1, int paramInt2, int paramInt3)
    {
      while (paramInt1 <= paramInt2)
      {
        short[] arrayOfShort = this.polyIndices;
        arrayOfShort[paramInt1] = ((short)(paramInt3 + arrayOfShort[paramInt1]));
        paramInt1++;
      }
    }
    
    boolean isFull()
    {
      return (32768 <= this.polyVertexCount) || (32768 <= this.lineVertexCount) || (32768 <= this.pointVertexCount);
    }
    
    void lineIndexCheck(int paramInt)
    {
      int i = this.lineIndices.length;
      if (paramInt + this.lineIndexCount > i) {
        expandLineIndices(PGraphicsOpenGL.expandArraySize(i, paramInt + this.lineIndexCount));
      }
      this.firstLineIndex = this.lineIndexCount;
      this.lineIndexCount = (paramInt + this.lineIndexCount);
      this.lastLineIndex = (-1 + this.lineIndexCount);
    }
    
    void lineVertexCheck(int paramInt)
    {
      int i = this.lineVertices.length / 4;
      if (paramInt + this.lineVertexCount > i)
      {
        int j = PGraphicsOpenGL.expandArraySize(i, paramInt + this.lineVertexCount);
        expandLineVertices(j);
        expandLineColors(j);
        expandLineDirections(j);
      }
      this.firstLineVertex = this.lineVertexCount;
      this.lineVertexCount = (paramInt + this.lineVertexCount);
      this.lastLineVertex = (-1 + this.lineVertexCount);
    }
    
    void pointIndexCheck(int paramInt)
    {
      int i = this.pointIndices.length;
      if (paramInt + this.pointIndexCount > i) {
        expandPointIndices(PGraphicsOpenGL.expandArraySize(i, paramInt + this.pointIndexCount));
      }
      this.firstPointIndex = this.pointIndexCount;
      this.pointIndexCount = (paramInt + this.pointIndexCount);
      this.lastPointIndex = (-1 + this.pointIndexCount);
    }
    
    void pointVertexCheck(int paramInt)
    {
      int i = this.pointVertices.length / 4;
      if (paramInt + this.pointVertexCount > i)
      {
        int j = PGraphicsOpenGL.expandArraySize(i, paramInt + this.pointVertexCount);
        expandPointVertices(j);
        expandPointColors(j);
        expandPointOffsets(j);
      }
      this.firstPointVertex = this.pointVertexCount;
      this.pointVertexCount = (paramInt + this.pointVertexCount);
      this.lastPointVertex = (-1 + this.pointVertexCount);
    }
    
    void polyIndexCheck()
    {
      if (this.polyIndexCount == this.polyIndices.length) {
        expandPolyIndices(this.polyIndexCount << 1);
      }
      this.firstPolyIndex = this.polyIndexCount;
      this.polyIndexCount = (1 + this.polyIndexCount);
      this.lastPolyIndex = (-1 + this.polyIndexCount);
    }
    
    void polyIndexCheck(int paramInt)
    {
      int i = this.polyIndices.length;
      if (paramInt + this.polyIndexCount > i) {
        expandPolyIndices(PGraphicsOpenGL.expandArraySize(i, paramInt + this.polyIndexCount));
      }
      this.firstPolyIndex = this.polyIndexCount;
      this.polyIndexCount = (paramInt + this.polyIndexCount);
      this.lastPolyIndex = (-1 + this.polyIndexCount);
    }
    
    void polyVertexCheck()
    {
      if (this.polyVertexCount == this.polyVertices.length / 4)
      {
        int i = this.polyVertexCount << 1;
        expandPolyVertices(i);
        expandPolyColors(i);
        expandPolyNormals(i);
        expandPolyTexCoords(i);
        expandPolyAmbient(i);
        expandPolySpecular(i);
        expandPolyEmissive(i);
        expandPolyShininess(i);
      }
      this.firstPolyVertex = this.polyVertexCount;
      this.polyVertexCount = (1 + this.polyVertexCount);
      this.lastPolyVertex = (-1 + this.polyVertexCount);
    }
    
    void polyVertexCheck(int paramInt)
    {
      int i = this.polyVertices.length / 4;
      if (paramInt + this.polyVertexCount > i)
      {
        int j = PGraphicsOpenGL.expandArraySize(i, paramInt + this.polyVertexCount);
        expandPolyVertices(j);
        expandPolyColors(j);
        expandPolyNormals(j);
        expandPolyTexCoords(j);
        expandPolyAmbient(j);
        expandPolySpecular(j);
        expandPolyEmissive(j);
        expandPolyShininess(j);
      }
      this.firstPolyVertex = this.polyVertexCount;
      this.polyVertexCount = (paramInt + this.polyVertexCount);
      this.lastPolyVertex = (-1 + this.polyVertexCount);
    }
    
    void setLineVertex(int paramInt1, PGraphicsOpenGL.InGeometry paramInGeometry, int paramInt2, int paramInt3)
    {
      int i = paramInt2 * 3;
      float[] arrayOfFloat1 = paramInGeometry.vertices;
      int j = i + 1;
      float f1 = arrayOfFloat1[i];
      float[] arrayOfFloat2 = paramInGeometry.vertices;
      int k = j + 1;
      float f2 = arrayOfFloat2[j];
      float f3 = paramInGeometry.vertices[k];
      if ((this.renderMode == 0) && (PGraphicsOpenGL.this.flushMode == 1))
      {
        PMatrix3D localPMatrix3D = PGraphicsOpenGL.this.modelview;
        int i7 = paramInt1 * 4;
        float[] arrayOfFloat9 = this.lineVertices;
        int i8 = i7 + 1;
        arrayOfFloat9[i7] = (f1 * localPMatrix3D.m00 + f2 * localPMatrix3D.m01 + f3 * localPMatrix3D.m02 + localPMatrix3D.m03);
        float[] arrayOfFloat10 = this.lineVertices;
        int i9 = i8 + 1;
        arrayOfFloat10[i8] = (f1 * localPMatrix3D.m10 + f2 * localPMatrix3D.m11 + f3 * localPMatrix3D.m12 + localPMatrix3D.m13);
        float[] arrayOfFloat11 = this.lineVertices;
        int i10 = i9 + 1;
        arrayOfFloat11[i9] = (f1 * localPMatrix3D.m20 + f2 * localPMatrix3D.m21 + f3 * localPMatrix3D.m22 + localPMatrix3D.m23);
        this.lineVertices[i10] = (f1 * localPMatrix3D.m30 + f2 * localPMatrix3D.m31 + f3 * localPMatrix3D.m32 + localPMatrix3D.m33);
      }
      for (;;)
      {
        this.lineColors[paramInt1] = paramInt3;
        int i3 = paramInt1 * 4;
        float[] arrayOfFloat6 = this.lineDirections;
        int i4 = i3 + 1;
        arrayOfFloat6[i3] = 0.0F;
        float[] arrayOfFloat7 = this.lineDirections;
        int i5 = i4 + 1;
        arrayOfFloat7[i4] = 0.0F;
        float[] arrayOfFloat8 = this.lineDirections;
        int i6 = i5 + 1;
        arrayOfFloat8[i5] = 0.0F;
        this.lineDirections[i6] = 0.0F;
        return;
        int m = paramInt1 * 4;
        float[] arrayOfFloat3 = this.lineVertices;
        int n = m + 1;
        arrayOfFloat3[m] = f1;
        float[] arrayOfFloat4 = this.lineVertices;
        int i1 = n + 1;
        arrayOfFloat4[n] = f2;
        float[] arrayOfFloat5 = this.lineVertices;
        int i2 = i1 + 1;
        arrayOfFloat5[i1] = f3;
        this.lineVertices[i2] = 1.0F;
      }
    }
    
    void setLineVertex(int paramInt1, PGraphicsOpenGL.InGeometry paramInGeometry, int paramInt2, int paramInt3, int paramInt4, float paramFloat)
    {
      int i = paramInt2 * 3;
      float[] arrayOfFloat1 = paramInGeometry.vertices;
      int j = i + 1;
      float f1 = arrayOfFloat1[i];
      float[] arrayOfFloat2 = paramInGeometry.vertices;
      int k = j + 1;
      float f2 = arrayOfFloat2[j];
      float f3 = paramInGeometry.vertices[k];
      int m = paramInt3 * 3;
      float[] arrayOfFloat3 = paramInGeometry.vertices;
      int n = m + 1;
      float f4 = arrayOfFloat3[m];
      float[] arrayOfFloat4 = paramInGeometry.vertices;
      int i1 = n + 1;
      float f5 = arrayOfFloat4[n];
      float f6 = paramInGeometry.vertices[i1];
      float f7 = f4 - f1;
      float f8 = f5 - f2;
      float f9 = f6 - f3;
      if ((this.renderMode == 0) && (PGraphicsOpenGL.this.flushMode == 1))
      {
        PMatrix3D localPMatrix3D = PGraphicsOpenGL.this.modelview;
        int i9 = paramInt1 * 4;
        float[] arrayOfFloat10 = this.lineVertices;
        int i10 = i9 + 1;
        arrayOfFloat10[i9] = (f1 * localPMatrix3D.m00 + f2 * localPMatrix3D.m01 + f3 * localPMatrix3D.m02 + localPMatrix3D.m03);
        float[] arrayOfFloat11 = this.lineVertices;
        int i11 = i10 + 1;
        arrayOfFloat11[i10] = (f1 * localPMatrix3D.m10 + f2 * localPMatrix3D.m11 + f3 * localPMatrix3D.m12 + localPMatrix3D.m13);
        float[] arrayOfFloat12 = this.lineVertices;
        int i12 = i11 + 1;
        arrayOfFloat12[i11] = (f1 * localPMatrix3D.m20 + f2 * localPMatrix3D.m21 + f3 * localPMatrix3D.m22 + localPMatrix3D.m23);
        this.lineVertices[i12] = (f1 * localPMatrix3D.m30 + f2 * localPMatrix3D.m31 + f3 * localPMatrix3D.m32 + localPMatrix3D.m33);
        int i13 = paramInt1 * 4;
        float[] arrayOfFloat13 = this.lineDirections;
        int i14 = i13 + 1;
        arrayOfFloat13[i13] = (f7 * localPMatrix3D.m00 + f8 * localPMatrix3D.m01 + f9 * localPMatrix3D.m02);
        float[] arrayOfFloat14 = this.lineDirections;
        int i15 = i14 + 1;
        arrayOfFloat14[i14] = (f7 * localPMatrix3D.m10 + f8 * localPMatrix3D.m11 + f9 * localPMatrix3D.m12);
        this.lineDirections[i15] = (f7 * localPMatrix3D.m20 + f8 * localPMatrix3D.m21 + f9 * localPMatrix3D.m22);
      }
      for (;;)
      {
        this.lineColors[paramInt1] = paramInt4;
        this.lineDirections[(3 + paramInt1 * 4)] = paramFloat;
        return;
        int i2 = paramInt1 * 4;
        float[] arrayOfFloat5 = this.lineVertices;
        int i3 = i2 + 1;
        arrayOfFloat5[i2] = f1;
        float[] arrayOfFloat6 = this.lineVertices;
        int i4 = i3 + 1;
        arrayOfFloat6[i3] = f2;
        float[] arrayOfFloat7 = this.lineVertices;
        int i5 = i4 + 1;
        arrayOfFloat7[i4] = f3;
        this.lineVertices[i5] = 1.0F;
        int i6 = paramInt1 * 4;
        float[] arrayOfFloat8 = this.lineDirections;
        int i7 = i6 + 1;
        arrayOfFloat8[i6] = f7;
        float[] arrayOfFloat9 = this.lineDirections;
        int i8 = i7 + 1;
        arrayOfFloat9[i7] = f8;
        this.lineDirections[i8] = f9;
      }
    }
    
    void setPointVertex(int paramInt1, PGraphicsOpenGL.InGeometry paramInGeometry, int paramInt2)
    {
      int i = paramInt2 * 3;
      float[] arrayOfFloat1 = paramInGeometry.vertices;
      int j = i + 1;
      float f1 = arrayOfFloat1[i];
      float[] arrayOfFloat2 = paramInGeometry.vertices;
      int k = j + 1;
      float f2 = arrayOfFloat2[j];
      float f3 = paramInGeometry.vertices[k];
      if ((this.renderMode == 0) && (PGraphicsOpenGL.this.flushMode == 1))
      {
        PMatrix3D localPMatrix3D = PGraphicsOpenGL.this.modelview;
        int i3 = paramInt1 * 4;
        float[] arrayOfFloat6 = this.pointVertices;
        int i4 = i3 + 1;
        arrayOfFloat6[i3] = (f1 * localPMatrix3D.m00 + f2 * localPMatrix3D.m01 + f3 * localPMatrix3D.m02 + localPMatrix3D.m03);
        float[] arrayOfFloat7 = this.pointVertices;
        int i5 = i4 + 1;
        arrayOfFloat7[i4] = (f1 * localPMatrix3D.m10 + f2 * localPMatrix3D.m11 + f3 * localPMatrix3D.m12 + localPMatrix3D.m13);
        float[] arrayOfFloat8 = this.pointVertices;
        int i6 = i5 + 1;
        arrayOfFloat8[i5] = (f1 * localPMatrix3D.m20 + f2 * localPMatrix3D.m21 + f3 * localPMatrix3D.m22 + localPMatrix3D.m23);
        this.pointVertices[i6] = (f1 * localPMatrix3D.m30 + f2 * localPMatrix3D.m31 + f3 * localPMatrix3D.m32 + localPMatrix3D.m33);
      }
      for (;;)
      {
        this.pointColors[paramInt1] = paramInGeometry.strokeColors[paramInt2];
        return;
        int m = paramInt1 * 4;
        float[] arrayOfFloat3 = this.pointVertices;
        int n = m + 1;
        arrayOfFloat3[m] = f1;
        float[] arrayOfFloat4 = this.pointVertices;
        int i1 = n + 1;
        arrayOfFloat4[n] = f2;
        float[] arrayOfFloat5 = this.pointVertices;
        int i2 = i1 + 1;
        arrayOfFloat5[i1] = f3;
        this.pointVertices[i2] = 1.0F;
      }
    }
    
    void setPolyVertex(int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt2)
    {
      setPolyVertex(paramInt1, paramFloat1, paramFloat2, paramFloat3, paramInt2, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0, 0, 0, 0.0F);
    }
    
    void setPolyVertex(int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt2, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt3, int paramInt4, int paramInt5, float paramFloat9)
    {
      if ((this.renderMode == 0) && (PGraphicsOpenGL.this.flushMode == 1))
      {
        PMatrix3D localPMatrix3D1 = PGraphicsOpenGL.this.modelview;
        PMatrix3D localPMatrix3D2 = PGraphicsOpenGL.this.modelviewInv;
        int i5 = paramInt1 * 4;
        float[] arrayOfFloat7 = this.polyVertices;
        int i6 = i5 + 1;
        arrayOfFloat7[i5] = (paramFloat1 * localPMatrix3D1.m00 + paramFloat2 * localPMatrix3D1.m01 + paramFloat3 * localPMatrix3D1.m02 + localPMatrix3D1.m03);
        float[] arrayOfFloat8 = this.polyVertices;
        int i7 = i6 + 1;
        arrayOfFloat8[i6] = (paramFloat1 * localPMatrix3D1.m10 + paramFloat2 * localPMatrix3D1.m11 + paramFloat3 * localPMatrix3D1.m12 + localPMatrix3D1.m13);
        float[] arrayOfFloat9 = this.polyVertices;
        int i8 = i7 + 1;
        arrayOfFloat9[i7] = (paramFloat1 * localPMatrix3D1.m20 + paramFloat2 * localPMatrix3D1.m21 + paramFloat3 * localPMatrix3D1.m22 + localPMatrix3D1.m23);
        this.polyVertices[i8] = (paramFloat1 * localPMatrix3D1.m30 + paramFloat2 * localPMatrix3D1.m31 + paramFloat3 * localPMatrix3D1.m32 + localPMatrix3D1.m33);
        int i9 = paramInt1 * 3;
        float[] arrayOfFloat10 = this.polyNormals;
        int i10 = i9 + 1;
        arrayOfFloat10[i9] = (paramFloat4 * localPMatrix3D2.m00 + paramFloat5 * localPMatrix3D2.m10 + paramFloat6 * localPMatrix3D2.m20);
        float[] arrayOfFloat11 = this.polyNormals;
        int i11 = i10 + 1;
        arrayOfFloat11[i10] = (paramFloat4 * localPMatrix3D2.m01 + paramFloat5 * localPMatrix3D2.m11 + paramFloat6 * localPMatrix3D2.m21);
        this.polyNormals[i11] = (paramFloat4 * localPMatrix3D2.m02 + paramFloat5 * localPMatrix3D2.m12 + paramFloat6 * localPMatrix3D2.m22);
      }
      for (;;)
      {
        this.polyColors[paramInt1] = paramInt2;
        int i3 = paramInt1 * 2;
        float[] arrayOfFloat6 = this.polyTexCoords;
        int i4 = i3 + 1;
        arrayOfFloat6[i3] = paramFloat7;
        this.polyTexCoords[i4] = paramFloat8;
        this.polyAmbient[paramInt1] = paramInt3;
        this.polySpecular[paramInt1] = paramInt4;
        this.polyEmissive[paramInt1] = paramInt5;
        this.polyShininess[paramInt1] = paramFloat9;
        return;
        int i = paramInt1 * 4;
        float[] arrayOfFloat1 = this.polyVertices;
        int j = i + 1;
        arrayOfFloat1[i] = paramFloat1;
        float[] arrayOfFloat2 = this.polyVertices;
        int k = j + 1;
        arrayOfFloat2[j] = paramFloat2;
        float[] arrayOfFloat3 = this.polyVertices;
        int m = k + 1;
        arrayOfFloat3[k] = paramFloat3;
        this.polyVertices[m] = 1.0F;
        int n = paramInt1 * 3;
        float[] arrayOfFloat4 = this.polyNormals;
        int i1 = n + 1;
        arrayOfFloat4[n] = paramFloat4;
        float[] arrayOfFloat5 = this.polyNormals;
        int i2 = i1 + 1;
        arrayOfFloat5[i1] = paramFloat5;
        this.polyNormals[i2] = paramFloat6;
      }
    }
    
    void trim()
    {
      if ((this.polyVertexCount > 0) && (this.polyVertexCount < this.polyVertices.length / 4))
      {
        trimPolyVertices();
        trimPolyColors();
        trimPolyNormals();
        trimPolyTexCoords();
        trimPolyAmbient();
        trimPolySpecular();
        trimPolyEmissive();
        trimPolyShininess();
      }
      if ((this.polyIndexCount > 0) && (this.polyIndexCount < this.polyIndices.length)) {
        trimPolyIndices();
      }
      if ((this.lineVertexCount > 0) && (this.lineVertexCount < this.lineVertices.length / 4))
      {
        trimLineVertices();
        trimLineColors();
        trimLineDirections();
      }
      if ((this.lineIndexCount > 0) && (this.lineIndexCount < this.lineIndices.length)) {
        trimLineIndices();
      }
      if ((this.pointVertexCount > 0) && (this.pointVertexCount < this.pointVertices.length / 4))
      {
        trimPointVertices();
        trimPointColors();
        trimPointOffsets();
      }
      if ((this.pointIndexCount > 0) && (this.pointIndexCount < this.pointIndices.length)) {
        trimPointIndices();
      }
    }
    
    void trimLineColors()
    {
      int[] arrayOfInt = new int[this.lineVertexCount];
      PApplet.arrayCopy(this.lineColors, 0, arrayOfInt, 0, this.lineVertexCount);
      this.lineColors = arrayOfInt;
      this.lineColorsBuffer = PGL.allocateIntBuffer(this.lineColors);
    }
    
    void trimLineDirections()
    {
      float[] arrayOfFloat = new float[4 * this.lineVertexCount];
      PApplet.arrayCopy(this.lineDirections, 0, arrayOfFloat, 0, 4 * this.lineVertexCount);
      this.lineDirections = arrayOfFloat;
      this.lineDirectionsBuffer = PGL.allocateFloatBuffer(this.lineDirections);
    }
    
    void trimLineIndices()
    {
      short[] arrayOfShort = new short[this.lineIndexCount];
      PApplet.arrayCopy(this.lineIndices, 0, arrayOfShort, 0, this.lineIndexCount);
      this.lineIndices = arrayOfShort;
      this.lineIndicesBuffer = PGL.allocateShortBuffer(this.lineIndices);
    }
    
    void trimLineVertices()
    {
      float[] arrayOfFloat = new float[4 * this.lineVertexCount];
      PApplet.arrayCopy(this.lineVertices, 0, arrayOfFloat, 0, 4 * this.lineVertexCount);
      this.lineVertices = arrayOfFloat;
      this.lineVerticesBuffer = PGL.allocateFloatBuffer(this.lineVertices);
    }
    
    void trimPointColors()
    {
      int[] arrayOfInt = new int[this.pointVertexCount];
      PApplet.arrayCopy(this.pointColors, 0, arrayOfInt, 0, this.pointVertexCount);
      this.pointColors = arrayOfInt;
      this.pointColorsBuffer = PGL.allocateIntBuffer(this.pointColors);
    }
    
    void trimPointIndices()
    {
      short[] arrayOfShort = new short[this.pointIndexCount];
      PApplet.arrayCopy(this.pointIndices, 0, arrayOfShort, 0, this.pointIndexCount);
      this.pointIndices = arrayOfShort;
      this.pointIndicesBuffer = PGL.allocateShortBuffer(this.pointIndices);
    }
    
    void trimPointOffsets()
    {
      float[] arrayOfFloat = new float[2 * this.pointVertexCount];
      PApplet.arrayCopy(this.pointOffsets, 0, arrayOfFloat, 0, 2 * this.pointVertexCount);
      this.pointOffsets = arrayOfFloat;
      this.pointOffsetsBuffer = PGL.allocateFloatBuffer(this.pointOffsets);
    }
    
    void trimPointVertices()
    {
      float[] arrayOfFloat = new float[4 * this.pointVertexCount];
      PApplet.arrayCopy(this.pointVertices, 0, arrayOfFloat, 0, 4 * this.pointVertexCount);
      this.pointVertices = arrayOfFloat;
      this.pointVerticesBuffer = PGL.allocateFloatBuffer(this.pointVertices);
    }
    
    void trimPolyAmbient()
    {
      int[] arrayOfInt = new int[this.polyVertexCount];
      PApplet.arrayCopy(this.polyAmbient, 0, arrayOfInt, 0, this.polyVertexCount);
      this.polyAmbient = arrayOfInt;
      this.polyAmbientBuffer = PGL.allocateIntBuffer(this.polyAmbient);
    }
    
    void trimPolyColors()
    {
      int[] arrayOfInt = new int[this.polyVertexCount];
      PApplet.arrayCopy(this.polyColors, 0, arrayOfInt, 0, this.polyVertexCount);
      this.polyColors = arrayOfInt;
      this.polyColorsBuffer = PGL.allocateIntBuffer(this.polyColors);
    }
    
    void trimPolyEmissive()
    {
      int[] arrayOfInt = new int[this.polyVertexCount];
      PApplet.arrayCopy(this.polyEmissive, 0, arrayOfInt, 0, this.polyVertexCount);
      this.polyEmissive = arrayOfInt;
      this.polyEmissiveBuffer = PGL.allocateIntBuffer(this.polyEmissive);
    }
    
    void trimPolyIndices()
    {
      short[] arrayOfShort = new short[this.polyIndexCount];
      PApplet.arrayCopy(this.polyIndices, 0, arrayOfShort, 0, this.polyIndexCount);
      this.polyIndices = arrayOfShort;
      this.polyIndicesBuffer = PGL.allocateShortBuffer(this.polyIndices);
    }
    
    void trimPolyNormals()
    {
      float[] arrayOfFloat = new float[3 * this.polyVertexCount];
      PApplet.arrayCopy(this.polyNormals, 0, arrayOfFloat, 0, 3 * this.polyVertexCount);
      this.polyNormals = arrayOfFloat;
      this.polyNormalsBuffer = PGL.allocateFloatBuffer(this.polyNormals);
    }
    
    void trimPolyShininess()
    {
      float[] arrayOfFloat = new float[this.polyVertexCount];
      PApplet.arrayCopy(this.polyShininess, 0, arrayOfFloat, 0, this.polyVertexCount);
      this.polyShininess = arrayOfFloat;
      this.polyShininessBuffer = PGL.allocateFloatBuffer(this.polyShininess);
    }
    
    void trimPolySpecular()
    {
      int[] arrayOfInt = new int[this.polyVertexCount];
      PApplet.arrayCopy(this.polySpecular, 0, arrayOfInt, 0, this.polyVertexCount);
      this.polySpecular = arrayOfInt;
      this.polySpecularBuffer = PGL.allocateIntBuffer(this.polySpecular);
    }
    
    void trimPolyTexCoords()
    {
      float[] arrayOfFloat = new float[2 * this.polyVertexCount];
      PApplet.arrayCopy(this.polyTexCoords, 0, arrayOfFloat, 0, 2 * this.polyVertexCount);
      this.polyTexCoords = arrayOfFloat;
      this.polyTexCoordsBuffer = PGL.allocateFloatBuffer(this.polyTexCoords);
    }
    
    void trimPolyVertices()
    {
      float[] arrayOfFloat = new float[4 * this.polyVertexCount];
      PApplet.arrayCopy(this.polyVertices, 0, arrayOfFloat, 0, 4 * this.polyVertexCount);
      this.polyVertices = arrayOfFloat;
      this.polyVerticesBuffer = PGL.allocateFloatBuffer(this.polyVertices);
    }
    
    protected void updateLineColorsBuffer()
    {
      updateLineColorsBuffer(0, this.lineVertexCount);
    }
    
    protected void updateLineColorsBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateIntBuffer(this.lineColorsBuffer, this.lineColors, paramInt1, paramInt2);
    }
    
    protected void updateLineDirectionsBuffer()
    {
      updateLineDirectionsBuffer(0, this.lineVertexCount);
    }
    
    protected void updateLineDirectionsBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateFloatBuffer(this.lineDirectionsBuffer, this.lineDirections, paramInt1 * 4, paramInt2 * 4);
    }
    
    protected void updateLineIndicesBuffer()
    {
      updateLineIndicesBuffer(0, this.lineIndexCount);
    }
    
    protected void updateLineIndicesBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateShortBuffer(this.lineIndicesBuffer, this.lineIndices, paramInt1, paramInt2);
    }
    
    protected void updateLineVerticesBuffer()
    {
      updateLineVerticesBuffer(0, this.lineVertexCount);
    }
    
    protected void updateLineVerticesBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateFloatBuffer(this.lineVerticesBuffer, this.lineVertices, paramInt1 * 4, paramInt2 * 4);
    }
    
    protected void updatePointColorsBuffer()
    {
      updatePointColorsBuffer(0, this.pointVertexCount);
    }
    
    protected void updatePointColorsBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateIntBuffer(this.pointColorsBuffer, this.pointColors, paramInt1, paramInt2);
    }
    
    protected void updatePointIndicesBuffer()
    {
      updatePointIndicesBuffer(0, this.pointIndexCount);
    }
    
    protected void updatePointIndicesBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateShortBuffer(this.pointIndicesBuffer, this.pointIndices, paramInt1, paramInt2);
    }
    
    protected void updatePointOffsetsBuffer()
    {
      updatePointOffsetsBuffer(0, this.pointVertexCount);
    }
    
    protected void updatePointOffsetsBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateFloatBuffer(this.pointOffsetsBuffer, this.pointOffsets, paramInt1 * 2, paramInt2 * 2);
    }
    
    protected void updatePointVerticesBuffer()
    {
      updatePointVerticesBuffer(0, this.pointVertexCount);
    }
    
    protected void updatePointVerticesBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateFloatBuffer(this.pointVerticesBuffer, this.pointVertices, paramInt1 * 4, paramInt2 * 4);
    }
    
    protected void updatePolyAmbientBuffer()
    {
      updatePolyAmbientBuffer(0, this.polyVertexCount);
    }
    
    protected void updatePolyAmbientBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateIntBuffer(this.polyAmbientBuffer, this.polyAmbient, paramInt1, paramInt2);
    }
    
    protected void updatePolyColorsBuffer()
    {
      updatePolyColorsBuffer(0, this.polyVertexCount);
    }
    
    protected void updatePolyColorsBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateIntBuffer(this.polyColorsBuffer, this.polyColors, paramInt1, paramInt2);
    }
    
    protected void updatePolyEmissiveBuffer()
    {
      updatePolyEmissiveBuffer(0, this.polyVertexCount);
    }
    
    protected void updatePolyEmissiveBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateIntBuffer(this.polyEmissiveBuffer, this.polyEmissive, paramInt1, paramInt2);
    }
    
    protected void updatePolyIndicesBuffer()
    {
      updatePolyIndicesBuffer(0, this.polyIndexCount);
    }
    
    protected void updatePolyIndicesBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateShortBuffer(this.polyIndicesBuffer, this.polyIndices, paramInt1, paramInt2);
    }
    
    protected void updatePolyNormalsBuffer()
    {
      updatePolyNormalsBuffer(0, this.polyVertexCount);
    }
    
    protected void updatePolyNormalsBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateFloatBuffer(this.polyNormalsBuffer, this.polyNormals, paramInt1 * 3, paramInt2 * 3);
    }
    
    protected void updatePolyShininessBuffer()
    {
      updatePolyShininessBuffer(0, this.polyVertexCount);
    }
    
    protected void updatePolyShininessBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateFloatBuffer(this.polyShininessBuffer, this.polyShininess, paramInt1, paramInt2);
    }
    
    protected void updatePolySpecularBuffer()
    {
      updatePolySpecularBuffer(0, this.polyVertexCount);
    }
    
    protected void updatePolySpecularBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateIntBuffer(this.polySpecularBuffer, this.polySpecular, paramInt1, paramInt2);
    }
    
    protected void updatePolyTexCoordsBuffer()
    {
      updatePolyTexCoordsBuffer(0, this.polyVertexCount);
    }
    
    protected void updatePolyTexCoordsBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateFloatBuffer(this.polyTexCoordsBuffer, this.polyTexCoords, paramInt1 * 2, paramInt2 * 2);
    }
    
    protected void updatePolyVerticesBuffer()
    {
      updatePolyVerticesBuffer(0, this.polyVertexCount);
    }
    
    protected void updatePolyVerticesBuffer(int paramInt1, int paramInt2)
    {
      PGL.updateFloatBuffer(this.polyVerticesBuffer, this.polyVertices, paramInt1 * 4, paramInt2 * 4);
    }
  }
  
  protected class Tessellator
  {
    boolean accurate2DStrokes = true;
    TessellatorCallback callback = new TessellatorCallback();
    int dupCount;
    int[] dupIndices;
    boolean fill;
    int firstLineIndexCache;
    int firstPointIndexCache;
    int firstPolyIndexCache;
    int firstTexCache;
    int firstTexIndex;
    PGL.Tessellator gluTess = PGraphicsOpenGL.this.pgl.createTessellator(this.callback);
    PGraphicsOpenGL.InGeometry in;
    boolean is2D = false;
    boolean is3D = true;
    int lastLineIndexCache;
    int lastPointIndexCache;
    int lastPolyIndexCache;
    PImage newTexImage;
    PImage prevTexImage;
    int[] rawIndices = new int[512];
    int rawSize;
    boolean stroke;
    int strokeCap;
    int strokeColor;
    int strokeJoin;
    float strokeWeight;
    PGraphicsOpenGL.TessGeometry tess;
    PGraphicsOpenGL.TexCache texCache;
    PMatrix transform = null;
    
    public Tessellator() {}
    
    void addDupIndex(int paramInt)
    {
      if (this.dupIndices == null) {
        this.dupIndices = new int[16];
      }
      if (this.dupIndices.length == this.dupCount)
      {
        int[] arrayOfInt = new int[this.dupCount << 1];
        PApplet.arrayCopy(this.dupIndices, 0, arrayOfInt, 0, this.dupCount);
        this.dupIndices = arrayOfInt;
      }
      if (paramInt < this.dupIndices[0])
      {
        for (int m = this.dupCount; m > 0; m--) {
          this.dupIndices[m] = this.dupIndices[(m - 1)];
        }
        this.dupIndices[0] = paramInt;
        this.dupCount = (1 + this.dupCount);
      }
      int j;
      do
      {
        return;
        do
        {
          int i = this.dupIndices[(-1 + this.dupCount)];
          j = 0;
          if (i < paramInt)
          {
            this.dupIndices[this.dupCount] = paramInt;
            this.dupCount = (1 + this.dupCount);
            return;
            j++;
          }
        } while ((j >= -1 + this.dupCount) || (this.dupIndices[j] == paramInt));
      } while ((this.dupIndices[j] >= paramInt) || (paramInt >= this.dupIndices[(j + 1)]));
      for (int k = this.dupCount; k > j + 1; k--) {
        this.dupIndices[k] = this.dupIndices[(k - 1)];
      }
      this.dupIndices[(j + 1)] = paramInt;
      this.dupCount = (1 + this.dupCount);
    }
    
    int addLine2D(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      PGraphicsOpenGL.IndexCache localIndexCache = this.tess.polyIndexCache;
      int i = localIndexCache.vertexCount[paramInt3];
      if (32768 <= i + 4) {
        paramInt3 = localIndexCache.addNew();
      }
      for (int j = 0;; j = i)
      {
        int k = localIndexCache.indexOffset[paramInt3] + localIndexCache.indexCount[paramInt3];
        int m = localIndexCache.vertexOffset[paramInt3] + localIndexCache.vertexCount[paramInt3];
        int n;
        float f1;
        label92:
        float f2;
        float f3;
        float f4;
        float f5;
        float f11;
        float f9;
        if (paramBoolean)
        {
          n = this.strokeColor;
          if (!paramBoolean) {
            break label577;
          }
          f1 = this.strokeWeight;
          f2 = this.in.vertices[(0 + paramInt1 * 3)];
          f3 = this.in.vertices[(1 + paramInt1 * 3)];
          f4 = this.in.vertices[(0 + paramInt2 * 3)];
          f5 = this.in.vertices[(1 + paramInt2 * 3)];
          float f6 = f4 - f2;
          float f7 = f5 - f3;
          float f8 = PApplet.sqrt(f6 * f6 + f7 * f7);
          if (!PGraphicsOpenGL.nonZero(f8)) {
            break label591;
          }
          f11 = -f7 / f8;
          f9 = f6 / f8;
        }
        for (float f10 = f11;; f10 = 0.0F)
        {
          PGraphicsOpenGL.TessGeometry localTessGeometry1 = this.tess;
          int i1 = m + 1;
          localTessGeometry1.setPolyVertex(m, f2 + f10 * f1 / 2.0F, f3 + f9 * f1 / 2.0F, 0.0F, n);
          short[] arrayOfShort1 = this.tess.polyIndices;
          int i2 = k + 1;
          arrayOfShort1[k] = ((short)(j + 0));
          PGraphicsOpenGL.TessGeometry localTessGeometry2 = this.tess;
          int i3 = i1 + 1;
          localTessGeometry2.setPolyVertex(i1, f2 - f10 * f1 / 2.0F, f3 - f9 * f1 / 2.0F, 0.0F, n);
          short[] arrayOfShort2 = this.tess.polyIndices;
          int i4 = i2 + 1;
          arrayOfShort2[i2] = ((short)(j + 1));
          if (!paramBoolean)
          {
            n = this.in.strokeColors[paramInt2];
            f1 = this.in.strokeWeights[paramInt2];
          }
          PGraphicsOpenGL.TessGeometry localTessGeometry3 = this.tess;
          int i5 = i3 + 1;
          localTessGeometry3.setPolyVertex(i3, f4 - f10 * f1 / 2.0F, f5 - f9 * f1 / 2.0F, 0.0F, n);
          short[] arrayOfShort3 = this.tess.polyIndices;
          int i6 = i4 + 1;
          arrayOfShort3[i4] = ((short)(j + 2));
          short[] arrayOfShort4 = this.tess.polyIndices;
          int i7 = i6 + 1;
          arrayOfShort4[i6] = ((short)(j + 2));
          short[] arrayOfShort5 = this.tess.polyIndices;
          int i8 = i7 + 1;
          arrayOfShort5[i7] = ((short)(j + 0));
          PGraphicsOpenGL.TessGeometry localTessGeometry4 = this.tess;
          (i5 + 1);
          localTessGeometry4.setPolyVertex(i5, f4 + f10 * f1 / 2.0F, f5 + f9 * f1 / 2.0F, 0.0F, n);
          short[] arrayOfShort6 = this.tess.polyIndices;
          (i8 + 1);
          arrayOfShort6[i8] = ((short)(j + 3));
          localIndexCache.incCounts(paramInt3, 6, 4);
          return paramInt3;
          n = this.in.strokeColors[paramInt1];
          break;
          label577:
          f1 = this.in.strokeWeights[paramInt1];
          break label92;
          label591:
          f9 = 0.0F;
        }
      }
    }
    
    int addLine3D(int paramInt1, int paramInt2, int paramInt3, short[] paramArrayOfShort, boolean paramBoolean)
    {
      PGraphicsOpenGL.IndexCache localIndexCache = this.tess.lineIndexCache;
      int i = localIndexCache.vertexCount[paramInt3];
      int j;
      int m;
      label56:
      int i1;
      int n;
      if ((paramArrayOfShort != null) && (-1 < paramArrayOfShort[0]) && (-1 < paramArrayOfShort[1]))
      {
        j = 1;
        int k = i + 4;
        if (j == 0) {
          break label663;
        }
        m = 1;
        if (32768 > m + k) {
          break label866;
        }
        paramInt3 = localIndexCache.addNew();
        i1 = 1;
        n = 0;
      }
      for (;;)
      {
        int i2 = localIndexCache.indexOffset[paramInt3] + localIndexCache.indexCount[paramInt3];
        int i3 = localIndexCache.vertexOffset[paramInt3] + localIndexCache.vertexCount[paramInt3];
        int i4;
        label123:
        float f1;
        label134:
        int i9;
        label258:
        float f2;
        label269:
        int i15;
        if (paramBoolean)
        {
          i4 = this.strokeColor;
          if (!paramBoolean) {
            break label683;
          }
          f1 = this.strokeWeight;
          PGraphicsOpenGL.TessGeometry localTessGeometry1 = this.tess;
          int i5 = i3 + 1;
          localTessGeometry1.setLineVertex(i3, this.in, paramInt1, paramInt2, i4, f1 / 2.0F);
          short[] arrayOfShort1 = this.tess.lineIndices;
          int i6 = i2 + 1;
          arrayOfShort1[i2] = ((short)(n + 0));
          PGraphicsOpenGL.TessGeometry localTessGeometry2 = this.tess;
          int i7 = i5 + 1;
          localTessGeometry2.setLineVertex(i5, this.in, paramInt1, paramInt2, i4, -f1 / 2.0F);
          short[] arrayOfShort2 = this.tess.lineIndices;
          int i8 = i6 + 1;
          arrayOfShort2[i6] = ((short)(n + 1));
          if (!paramBoolean) {
            break label697;
          }
          i9 = this.strokeColor;
          if (!paramBoolean) {
            break label711;
          }
          f2 = this.strokeWeight;
          PGraphicsOpenGL.TessGeometry localTessGeometry3 = this.tess;
          int i10 = i7 + 1;
          localTessGeometry3.setLineVertex(i7, this.in, paramInt2, paramInt1, i9, -f2 / 2.0F);
          short[] arrayOfShort3 = this.tess.lineIndices;
          int i11 = i8 + 1;
          arrayOfShort3[i8] = ((short)(n + 2));
          short[] arrayOfShort4 = this.tess.lineIndices;
          int i12 = i11 + 1;
          arrayOfShort4[i11] = ((short)(n + 2));
          short[] arrayOfShort5 = this.tess.lineIndices;
          int i13 = i12 + 1;
          arrayOfShort5[i12] = ((short)(n + 1));
          PGraphicsOpenGL.TessGeometry localTessGeometry4 = this.tess;
          int i14 = i10 + 1;
          localTessGeometry4.setLineVertex(i10, this.in, paramInt2, paramInt1, i9, f2 / 2.0F);
          short[] arrayOfShort6 = this.tess.lineIndices;
          i15 = i13 + 1;
          arrayOfShort6[i13] = ((short)(n + 3));
          localIndexCache.incCounts(paramInt3, 6, 4);
          if (paramArrayOfShort != null) {
            if ((-1 < paramArrayOfShort[0]) && (-1 < paramArrayOfShort[1]))
            {
              this.tess.setLineVertex(i14, this.in, paramInt1, i4);
              if (i1 == 0) {
                break label725;
              }
              PGraphics.showWarning("Stroke path is too long, some bevel triangles won't be added");
              short[] arrayOfShort12 = this.tess.lineIndices;
              int i21 = i15 + 1;
              arrayOfShort12[i15] = ((short)(n + 4));
              short[] arrayOfShort13 = this.tess.lineIndices;
              int i22 = i21 + 1;
              arrayOfShort13[i21] = ((short)(n + 0));
              short[] arrayOfShort14 = this.tess.lineIndices;
              int i23 = i22 + 1;
              arrayOfShort14[i22] = ((short)(n + 0));
              short[] arrayOfShort15 = this.tess.lineIndices;
              int i24 = i23 + 1;
              arrayOfShort15[i23] = ((short)(n + 4));
              short[] arrayOfShort16 = this.tess.lineIndices;
              int i25 = i24 + 1;
              arrayOfShort16[i24] = ((short)(n + 1));
              this.tess.lineIndices[i25] = ((short)(n + 1));
            }
          }
        }
        for (;;)
        {
          localIndexCache.incCounts(paramInt3, 6, 1);
          paramArrayOfShort[0] = ((short)(n + 2));
          paramArrayOfShort[1] = ((short)(n + 3));
          return paramInt3;
          j = 0;
          break;
          label663:
          m = 0;
          break label56;
          i4 = this.in.strokeColors[paramInt1];
          break label123;
          label683:
          f1 = this.in.strokeWeights[paramInt1];
          break label134;
          label697:
          i9 = this.in.strokeColors[paramInt2];
          break label258;
          label711:
          f2 = this.in.strokeWeights[paramInt2];
          break label269;
          label725:
          short[] arrayOfShort7 = this.tess.lineIndices;
          int i16 = i15 + 1;
          arrayOfShort7[i15] = ((short)(n + 4));
          short[] arrayOfShort8 = this.tess.lineIndices;
          int i17 = i16 + 1;
          arrayOfShort8[i16] = paramArrayOfShort[0];
          short[] arrayOfShort9 = this.tess.lineIndices;
          int i18 = i17 + 1;
          arrayOfShort9[i17] = ((short)(n + 0));
          short[] arrayOfShort10 = this.tess.lineIndices;
          int i19 = i18 + 1;
          arrayOfShort10[i18] = ((short)(n + 4));
          short[] arrayOfShort11 = this.tess.lineIndices;
          int i20 = i19 + 1;
          arrayOfShort11[i19] = paramArrayOfShort[1];
          this.tess.lineIndices[i20] = ((short)(n + 1));
        }
        label866:
        n = i;
        i1 = 0;
      }
    }
    
    void beginNoTex()
    {
      this.prevTexImage = this.newTexImage;
      this.newTexImage = null;
      setFirstTexIndex(this.tess.polyIndexCount, -1 + this.tess.polyIndexCache.size);
    }
    
    void beginTex()
    {
      setFirstTexIndex(this.tess.polyIndexCount, -1 + this.tess.polyIndexCache.size);
    }
    
    int dupIndexPos(int paramInt)
    {
      for (int i = 0; i < this.dupCount; i++) {
        if (this.dupIndices[i] == paramInt) {
          return i;
        }
      }
      return 0;
    }
    
    void endNoTex()
    {
      setLastTexIndex(this.tess.lastPolyIndex, -1 + this.tess.polyIndexCache.size);
    }
    
    void endTex()
    {
      setLastTexIndex(this.tess.lastPolyIndex, -1 + this.tess.polyIndexCache.size);
    }
    
    void expandRawIndices(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.rawIndices, 0, arrayOfInt, 0, this.rawSize);
      this.rawIndices = arrayOfInt;
    }
    
    boolean noCapsJoins()
    {
      float f = 1.0F;
      if (this.transform != null)
      {
        if (!(this.transform instanceof PMatrix2D)) {
          break label68;
        }
        PMatrix2D localPMatrix2D = (PMatrix2D)this.transform;
        f = (float)Math.sqrt(Math.abs(localPMatrix2D.m00 * localPMatrix2D.m11 - localPMatrix2D.m01 * localPMatrix2D.m10));
      }
      while (f * this.strokeWeight < 2.0F)
      {
        return true;
        label68:
        if ((this.transform instanceof PMatrix3D))
        {
          PMatrix3D localPMatrix3D = (PMatrix3D)this.transform;
          f = (float)Math.pow(Math.abs(localPMatrix3D.m00 * (localPMatrix3D.m11 * localPMatrix3D.m22 - localPMatrix3D.m12 * localPMatrix3D.m21) + localPMatrix3D.m01 * (localPMatrix3D.m12 * localPMatrix3D.m20 - localPMatrix3D.m10 * localPMatrix3D.m22) + localPMatrix3D.m02 * (localPMatrix3D.m10 * localPMatrix3D.m21 - localPMatrix3D.m11 * localPMatrix3D.m20)), 0.333333343267441D);
        }
      }
      return false;
    }
    
    boolean noCapsJoins(int paramInt)
    {
      if (!this.accurate2DStrokes) {}
      while (1000 <= paramInt) {
        return true;
      }
      return noCapsJoins();
    }
    
    void set3D(boolean paramBoolean)
    {
      if (paramBoolean)
      {
        this.is2D = false;
        this.is3D = true;
        return;
      }
      this.is2D = true;
      this.is3D = false;
    }
    
    void setAccurate2DStrokes(boolean paramBoolean)
    {
      this.accurate2DStrokes = paramBoolean;
    }
    
    void setFill(boolean paramBoolean)
    {
      this.fill = paramBoolean;
    }
    
    void setFirstTexIndex(int paramInt1, int paramInt2)
    {
      if (this.texCache != null)
      {
        this.firstTexIndex = paramInt1;
        this.firstTexCache = PApplet.max(0, paramInt2);
      }
    }
    
    void setInGeometry(PGraphicsOpenGL.InGeometry paramInGeometry)
    {
      this.in = paramInGeometry;
      this.firstPolyIndexCache = -1;
      this.lastPolyIndexCache = -1;
      this.firstLineIndexCache = -1;
      this.lastLineIndexCache = -1;
      this.firstPointIndexCache = -1;
      this.lastPointIndexCache = -1;
    }
    
    void setLastTexIndex(int paramInt1, int paramInt2)
    {
      if (this.texCache != null)
      {
        if ((this.prevTexImage != this.newTexImage) || (this.texCache.size == 0)) {
          this.texCache.addTexture(this.newTexImage, this.firstTexIndex, this.firstTexCache, paramInt1, paramInt2);
        }
      }
      else {
        return;
      }
      this.texCache.setLastIndex(paramInt1, paramInt2);
    }
    
    void setRawSize(int paramInt)
    {
      int i = this.rawIndices.length;
      if (i < paramInt) {
        expandRawIndices(PGraphicsOpenGL.expandArraySize(i, paramInt));
      }
      this.rawSize = paramInt;
    }
    
    void setStroke(boolean paramBoolean)
    {
      this.stroke = paramBoolean;
    }
    
    void setStrokeCap(int paramInt)
    {
      this.strokeCap = paramInt;
    }
    
    void setStrokeColor(int paramInt)
    {
      this.strokeColor = PGL.javaToNativeARGB(paramInt);
    }
    
    void setStrokeJoin(int paramInt)
    {
      this.strokeJoin = paramInt;
    }
    
    void setStrokeWeight(float paramFloat)
    {
      this.strokeWeight = paramFloat;
    }
    
    void setTessGeometry(PGraphicsOpenGL.TessGeometry paramTessGeometry)
    {
      this.tess = paramTessGeometry;
    }
    
    void setTexCache(PGraphicsOpenGL.TexCache paramTexCache, PImage paramPImage1, PImage paramPImage2)
    {
      this.texCache = paramTexCache;
      this.prevTexImage = paramPImage1;
      this.newTexImage = paramPImage2;
    }
    
    void setTransform(PMatrix paramPMatrix)
    {
      this.transform = paramPMatrix;
    }
    
    void splitRawIndices()
    {
      this.tess.polyIndexCheck(this.rawSize);
      int i = this.tess.firstPolyIndex;
      int j = 0;
      int k = this.in.firstVertex;
      int m = this.in.firstVertex;
      this.dupCount = 0;
      PGraphicsOpenGL.IndexCache localIndexCache = this.tess.polyIndexCache;
      int i1;
      int i2;
      int i3;
      int i4;
      int i5;
      int i6;
      int i7;
      int i8;
      int i9;
      int i10;
      int i11;
      int i12;
      int i13;
      if (this.in.renderMode == 1)
      {
        n = localIndexCache.addNew();
        this.firstPolyIndexCache = n;
        i1 = this.rawSize / 3;
        i2 = 0;
        i3 = -1;
        i4 = m;
        i5 = k;
        if (i2 >= i1) {
          break label664;
        }
        if (n == -1) {
          n = localIndexCache.addNew();
        }
        i6 = this.rawIndices[(0 + i2 * 3)];
        i7 = this.rawIndices[(1 + i2 * 3)];
        i8 = this.rawIndices[(2 + i2 * 3)];
        i9 = i6 - k;
        i10 = i7 - k;
        i11 = i8 - k;
        i12 = localIndexCache.vertexCount[n];
        if (i9 >= 0) {
          break label444;
        }
        addDupIndex(i9);
        i13 = i9;
        label199:
        if (i10 >= 0) {
          break label454;
        }
        addDupIndex(i10);
        label210:
        if (i11 >= 0) {
          break label464;
        }
        addDupIndex(i11);
      }
      int i14;
      int i15;
      int i16;
      for (;;)
      {
        this.tess.polyIndices[(0 + (i + i2 * 3))] = ((short)i13);
        this.tess.polyIndices[(1 + (i + i2 * 3))] = ((short)i10);
        this.tess.polyIndices[(2 + (i + i2 * 3))] = ((short)i11);
        i14 = 2 + i2 * 3;
        i4 = PApplet.max(i4, PApplet.max(i6, i7, i8));
        i15 = PApplet.min(i5, PApplet.min(i6, i7, i8));
        i16 = PApplet.max(i3, PApplet.max(i13, i10, i11));
        if (((32765 > i16 + this.dupCount) || (i16 + this.dupCount >= 32768)) && (i2 != i1 - 1)) {
          break label665;
        }
        if (this.dupCount <= 0) {
          break label543;
        }
        for (int i23 = j; i23 <= i14; i23++)
        {
          int i25 = this.tess.polyIndices[(i + i23)];
          if (i25 < 0) {
            this.tess.polyIndices[(i + i23)] = ((short)(i16 + 1 + dupIndexPos(i25)));
          }
        }
        n = localIndexCache.getLast();
        break;
        label444:
        i13 = i9 + i12;
        break label199;
        label454:
        i10 += i12;
        break label210;
        label464:
        i11 += i12;
      }
      int i21 = 0;
      if (k <= i4)
      {
        this.tess.addPolyVertices(this.in, k, i4);
        i21 = 1 + (i4 - k);
      }
      for (int i24 = 0; i24 < this.dupCount; i24++) {
        this.tess.addPolyVertex(this.in, k + this.dupIndices[i24]);
      }
      label543:
      this.tess.addPolyVertices(this.in, i15, i4);
      i21 = 1 + (i4 - i15);
      localIndexCache.incCounts(n, 1 + (i14 - j), i21 + this.dupCount);
      this.lastPolyIndexCache = n;
      int n = -1;
      int i17 = -1;
      int i18 = i4 + 1;
      int i22 = i14 + 1;
      if (this.dupIndices != null) {
        Arrays.fill(this.dupIndices, 0, this.dupCount, 0);
      }
      this.dupCount = 0;
      int i20 = i22;
      int i19 = i18;
      for (;;)
      {
        i2++;
        i3 = i17;
        i5 = i19;
        k = i18;
        j = i20;
        break;
        label664:
        return;
        label665:
        i17 = i16;
        i18 = k;
        i19 = i15;
        i20 = j;
      }
    }
    
    void tessellateEdges()
    {
      if ((!this.stroke) || (this.in.edgeCount == 0)) {}
      do
      {
        return;
        if (this.is3D)
        {
          tessellateEdges3D();
          return;
        }
      } while (!this.is2D);
      beginNoTex();
      tessellateEdges2D();
      endNoTex();
    }
    
    void tessellateEdges2D()
    {
      int i = this.in.getNumEdgeVertices(false);
      if (noCapsJoins(i))
      {
        int n = this.in.getNumEdgeIndices(false);
        this.tess.polyVertexCheck(i);
        this.tess.polyIndexCheck(n);
        if (this.in.renderMode == 1) {}
        int i3;
        for (int i1 = this.tess.polyIndexCache.addNew();; i1 = this.tess.polyIndexCache.getLast())
        {
          this.firstLineIndexCache = i1;
          if (this.firstPolyIndexCache == -1) {
            this.firstPolyIndexCache = i1;
          }
          int i2 = this.in.firstEdge;
          i3 = i1;
          for (int i4 = i2; i4 <= this.in.lastEdge; i4++)
          {
            int[] arrayOfInt2 = this.in.edges[i4];
            i3 = addLine2D(arrayOfInt2[0], arrayOfInt2[1], i3, false);
          }
        }
        this.lastPolyIndexCache = i3;
        this.lastLineIndexCache = i3;
        return;
      }
      LinePath localLinePath = new LinePath(1);
      int j = this.in.firstEdge;
      if (j <= this.in.lastEdge)
      {
        int[] arrayOfInt1 = this.in.edges[j];
        int k = arrayOfInt1[0];
        int m = arrayOfInt1[1];
        switch (arrayOfInt1[2])
        {
        }
        for (;;)
        {
          j++;
          break;
          localLinePath.lineTo(this.in.vertices[(0 + m * 3)], this.in.vertices[(1 + m * 3)], this.in.strokeColors[m]);
          continue;
          localLinePath.moveTo(this.in.vertices[(0 + k * 3)], this.in.vertices[(1 + k * 3)], this.in.strokeColors[k]);
          localLinePath.lineTo(this.in.vertices[(0 + m * 3)], this.in.vertices[(1 + m * 3)], this.in.strokeColors[m]);
          continue;
          localLinePath.lineTo(this.in.vertices[(0 + m * 3)], this.in.vertices[(1 + m * 3)], this.in.strokeColors[m]);
          localLinePath.moveTo(this.in.vertices[(0 + m * 3)], this.in.vertices[(1 + m * 3)], this.in.strokeColors[m]);
          continue;
          localLinePath.moveTo(this.in.vertices[(0 + k * 3)], this.in.vertices[(1 + k * 3)], this.in.strokeColors[k]);
          localLinePath.lineTo(this.in.vertices[(0 + m * 3)], this.in.vertices[(1 + m * 3)], this.in.strokeColors[m]);
          localLinePath.moveTo(this.in.vertices[(0 + m * 3)], this.in.vertices[(1 + m * 3)], this.in.strokeColors[m]);
        }
      }
      tessellateLinePath(localLinePath);
    }
    
    void tessellateEdges3D()
    {
      boolean bool;
      int k;
      label66:
      int m;
      int n;
      label102:
      int i1;
      int i2;
      if (!noCapsJoins())
      {
        bool = true;
        int i = this.in.getNumEdgeVertices(bool);
        int j = this.in.getNumEdgeIndices(bool);
        this.tess.lineVertexCheck(i);
        this.tess.lineIndexCheck(j);
        if (this.in.renderMode != 1) {
          break label194;
        }
        k = this.tess.lineIndexCache.addNew();
        this.firstLineIndexCache = k;
        short[] arrayOfShort = { -1, -1 };
        m = this.in.firstEdge;
        n = k;
        if (m > this.in.lastEdge) {
          break label226;
        }
        int[] arrayOfInt = this.in.edges[m];
        i1 = arrayOfInt[0];
        i2 = arrayOfInt[1];
        if (!bool) {
          break label209;
        }
        n = addLine3D(i1, i2, n, arrayOfShort, false);
        if ((arrayOfInt[2] == 2) || (arrayOfInt[2] == 3))
        {
          arrayOfShort[1] = -1;
          arrayOfShort[0] = -1;
        }
      }
      for (;;)
      {
        m++;
        break label102;
        bool = false;
        break;
        label194:
        k = this.tess.lineIndexCache.getLast();
        break label66;
        label209:
        n = addLine3D(i1, i2, n, null, false);
      }
      label226:
      this.lastLineIndexCache = n;
    }
    
    void tessellateLineLoop()
    {
      int i = 1 + (this.in.lastVertex - this.in.firstVertex);
      if ((this.stroke) && (2 <= i))
      {
        updateTex();
        if (!this.is3D) {
          break label47;
        }
        tessellateLineLoop3D(i);
      }
      label47:
      while (!this.is2D) {
        return;
      }
      beginNoTex();
      tessellateLineLoop2D(i);
      endNoTex();
    }
    
    void tessellateLineLoop2D(int paramInt)
    {
      int i = paramInt * 4;
      int j = 3 * (paramInt * 2);
      if (noCapsJoins(i))
      {
        this.tess.polyVertexCheck(i);
        this.tess.polyIndexCheck(j);
        if (this.in.renderMode == 1) {}
        int i3;
        for (int i1 = this.tess.polyIndexCache.addNew();; i1 = this.tess.polyIndexCache.getLast())
        {
          this.firstLineIndexCache = i1;
          if (this.firstPolyIndexCache == -1) {
            this.firstPolyIndexCache = i1;
          }
          int i2 = this.in.firstVertex;
          i3 = i1;
          int i4 = 0;
          while (i4 < paramInt - 1)
          {
            int i6 = 1 + (i4 + this.in.firstVertex);
            int i7 = addLine2D(i2, i6, i3, false);
            i4++;
            i3 = i7;
            i2 = i6;
          }
        }
        int i5 = addLine2D(this.in.lastVertex, this.in.firstVertex, i3, false);
        this.lastPolyIndexCache = i5;
        this.lastLineIndexCache = i5;
        return;
      }
      int k = this.in.firstVertex;
      LinePath localLinePath = new LinePath(1);
      localLinePath.moveTo(this.in.vertices[(0 + k * 3)], this.in.vertices[(1 + k * 3)], this.in.strokeColors[k]);
      for (int m = 0; m < paramInt - 1; m++)
      {
        int n = 1 + (k + m);
        localLinePath.lineTo(this.in.vertices[(0 + n * 3)], this.in.vertices[(1 + n * 3)], this.in.strokeColors[n]);
      }
      localLinePath.closePath();
      tessellateLinePath(localLinePath);
    }
    
    void tessellateLineLoop3D(int paramInt)
    {
      int i;
      int m;
      label68:
      int n;
      short[] arrayOfShort;
      int i1;
      int i2;
      label107:
      int i3;
      if (noCapsJoins())
      {
        i = 0;
        int j = i + paramInt * 4;
        int k = 3 * (paramInt * 2) + 3 * (i * 2);
        this.tess.lineVertexCheck(j);
        this.tess.lineIndexCheck(k);
        if (this.in.renderMode != 1) {
          break label169;
        }
        m = this.tess.lineIndexCache.addNew();
        this.firstLineIndexCache = m;
        n = this.in.firstVertex;
        arrayOfShort = new short[] { -1, -1 };
        i1 = 0;
        i2 = m;
        if (i1 >= paramInt - 1) {
          break label201;
        }
        i3 = 1 + (i1 + this.in.firstVertex);
        if (i <= 0) {
          break label184;
        }
      }
      label169:
      label184:
      for (int i4 = addLine3D(n, i3, i2, arrayOfShort, false);; i4 = addLine3D(n, i3, i2, null, false))
      {
        i1++;
        i2 = i4;
        n = i3;
        break label107;
        i = paramInt - 1;
        break;
        m = this.tess.lineIndexCache.getLast();
        break label68;
      }
      label201:
      this.lastLineIndexCache = addLine3D(this.in.lastVertex, this.in.firstVertex, i2, arrayOfShort, false);
    }
    
    public void tessellateLinePath(LinePath paramLinePath)
    {
      TessellatorCallback localTessellatorCallback = this.callback;
      boolean bool;
      int i;
      label36:
      int j;
      label47:
      float[] arrayOfFloat;
      LinePath.PathIterator localPathIterator;
      if (this.in.renderMode == 1)
      {
        bool = true;
        localTessellatorCallback.init(bool, true, false);
        if (this.strokeCap != 2) {
          break label161;
        }
        i = 1;
        if (this.strokeJoin != 2) {
          break label181;
        }
        j = 1;
        LinePath localLinePath = LinePath.createStrokedPath(paramLinePath, this.strokeWeight, i, j);
        this.gluTess.beginPolygon();
        arrayOfFloat = new float[6];
        localPathIterator = localLinePath.getPathIterator();
        switch (localPathIterator.getWindingRule())
        {
        default: 
          label108:
          if (localPathIterator.isDone()) {
            break;
          }
          switch (localPathIterator.currentSegment(arrayOfFloat))
          {
          }
          break;
        }
      }
      for (;;)
      {
        localPathIterator.next();
        break label108;
        bool = false;
        break;
        label161:
        if (this.strokeCap == 4)
        {
          i = 2;
          break label36;
        }
        i = 0;
        break label36;
        label181:
        if (this.strokeJoin == 32)
        {
          j = 2;
          break label47;
        }
        j = 0;
        break label47;
        this.gluTess.setWindingRule(100130);
        break label108;
        this.gluTess.setWindingRule(100131);
        break label108;
        this.gluTess.beginContour();
        double[] arrayOfDouble = new double[25];
        arrayOfDouble[0] = arrayOfFloat[0];
        arrayOfDouble[1] = arrayOfFloat[1];
        arrayOfDouble[2] = 0.0D;
        arrayOfDouble[3] = arrayOfFloat[2];
        arrayOfDouble[4] = arrayOfFloat[3];
        arrayOfDouble[5] = arrayOfFloat[4];
        arrayOfDouble[6] = arrayOfFloat[5];
        arrayOfDouble[7] = 0.0D;
        arrayOfDouble[8] = 0.0D;
        arrayOfDouble[9] = 1.0D;
        arrayOfDouble[10] = 0.0D;
        arrayOfDouble[11] = 0.0D;
        arrayOfDouble[12] = 0.0D;
        arrayOfDouble[13] = 0.0D;
        arrayOfDouble[14] = 0.0D;
        arrayOfDouble[15] = 0.0D;
        arrayOfDouble[16] = 0.0D;
        arrayOfDouble[17] = 0.0D;
        arrayOfDouble[18] = 0.0D;
        arrayOfDouble[19] = 0.0D;
        arrayOfDouble[20] = 0.0D;
        arrayOfDouble[21] = 0.0D;
        arrayOfDouble[22] = 0.0D;
        arrayOfDouble[23] = 0.0D;
        arrayOfDouble[24] = 0.0D;
        this.gluTess.addVertex(arrayOfDouble);
        continue;
        this.gluTess.endContour();
      }
      this.gluTess.endPolygon();
    }
    
    void tessellateLineStrip()
    {
      int i = 1 + (this.in.lastVertex - this.in.firstVertex);
      int j;
      if ((this.stroke) && (2 <= i))
      {
        updateTex();
        j = i - 1;
        if (!this.is3D) {
          break label51;
        }
        tessellateLineStrip3D(j);
      }
      label51:
      while (!this.is2D) {
        return;
      }
      beginNoTex();
      tessellateLineStrip2D(j);
      endNoTex();
    }
    
    void tessellateLineStrip2D(int paramInt)
    {
      int i = paramInt * 4;
      int j = 3 * (paramInt * 2);
      if (noCapsJoins(i))
      {
        this.tess.polyVertexCheck(i);
        this.tess.polyIndexCheck(j);
        if (this.in.renderMode == 1) {}
        int i3;
        for (int i1 = this.tess.polyIndexCache.addNew();; i1 = this.tess.polyIndexCache.getLast())
        {
          this.firstLineIndexCache = i1;
          if (this.firstPolyIndexCache == -1) {
            this.firstPolyIndexCache = i1;
          }
          int i2 = this.in.firstVertex;
          i3 = i1;
          int i4 = 0;
          while (i4 < paramInt)
          {
            int i5 = 1 + (i4 + this.in.firstVertex);
            int i6 = addLine2D(i2, i5, i3, false);
            i4++;
            i3 = i6;
            i2 = i5;
          }
        }
        this.lastPolyIndexCache = i3;
        this.lastLineIndexCache = i3;
        return;
      }
      int k = this.in.firstVertex;
      LinePath localLinePath = new LinePath(1);
      localLinePath.moveTo(this.in.vertices[(0 + k * 3)], this.in.vertices[(1 + k * 3)], this.in.strokeColors[k]);
      for (int m = 0; m < paramInt; m++)
      {
        int n = 1 + (k + m);
        localLinePath.lineTo(this.in.vertices[(0 + n * 3)], this.in.vertices[(1 + n * 3)], this.in.strokeColors[n]);
      }
      tessellateLinePath(localLinePath);
    }
    
    void tessellateLineStrip3D(int paramInt)
    {
      int i;
      int m;
      label68:
      int n;
      short[] arrayOfShort;
      int i1;
      int i2;
      label107:
      int i3;
      if (noCapsJoins())
      {
        i = 0;
        int j = i + paramInt * 4;
        int k = 3 * (paramInt * 2) + 3 * (i * 2);
        this.tess.lineVertexCheck(j);
        this.tess.lineIndexCheck(k);
        if (this.in.renderMode != 1) {
          break label167;
        }
        m = this.tess.lineIndexCache.addNew();
        this.firstLineIndexCache = m;
        n = this.in.firstVertex;
        arrayOfShort = new short[] { -1, -1 };
        i1 = 0;
        i2 = m;
        if (i1 >= paramInt) {
          break label199;
        }
        i3 = 1 + (i1 + this.in.firstVertex);
        if (i <= 0) {
          break label182;
        }
      }
      label167:
      label182:
      for (int i4 = addLine3D(n, i3, i2, arrayOfShort, false);; i4 = addLine3D(n, i3, i2, null, false))
      {
        i1++;
        i2 = i4;
        n = i3;
        break label107;
        i = paramInt - 1;
        break;
        m = this.tess.lineIndexCache.getLast();
        break label68;
      }
      label199:
      this.lastLineIndexCache = i2;
    }
    
    void tessellateLines()
    {
      int i = 1 + (this.in.lastVertex - this.in.firstVertex);
      int j;
      if ((this.stroke) && (2 <= i))
      {
        updateTex();
        j = i / 2;
        if (!this.is3D) {
          break label51;
        }
        tessellateLines3D(j);
      }
      label51:
      while (!this.is2D) {
        return;
      }
      beginNoTex();
      tessellateLines2D(j);
      endNoTex();
    }
    
    void tessellateLines2D(int paramInt)
    {
      int i = paramInt * 4;
      int j = 3 * (paramInt * 2);
      int k = this.in.firstVertex;
      if (noCapsJoins(i))
      {
        this.tess.polyVertexCheck(i);
        this.tess.polyIndexCheck(j);
        if (this.in.renderMode == 1) {}
        int i3;
        for (int i2 = this.tess.polyIndexCache.addNew();; i2 = this.tess.polyIndexCache.getLast())
        {
          this.firstLineIndexCache = i2;
          if (this.firstPolyIndexCache == -1) {
            this.firstPolyIndexCache = i2;
          }
          i3 = i2;
          for (int i4 = 0; i4 < paramInt; i4++) {
            i3 = addLine2D(0 + (k + i4 * 2), 1 + (k + i4 * 2), i3, false);
          }
        }
        this.lastPolyIndexCache = i3;
        this.lastLineIndexCache = i3;
        return;
      }
      LinePath localLinePath = new LinePath(1);
      for (int m = 0; m < paramInt; m++)
      {
        int n = 0 + (k + m * 2);
        int i1 = 1 + (k + m * 2);
        localLinePath.moveTo(this.in.vertices[(0 + n * 3)], this.in.vertices[(1 + n * 3)], this.in.strokeColors[n]);
        localLinePath.lineTo(this.in.vertices[(0 + i1 * 3)], this.in.vertices[(1 + i1 * 3)], this.in.strokeColors[i1]);
      }
      tessellateLinePath(localLinePath);
    }
    
    void tessellateLines3D(int paramInt)
    {
      int i = paramInt * 4;
      int j = 3 * (paramInt * 2);
      int k = this.in.firstVertex;
      this.tess.lineVertexCheck(i);
      this.tess.lineIndexCheck(j);
      if (this.in.renderMode == 1) {}
      int i1;
      for (int m = this.tess.lineIndexCache.addNew();; m = this.tess.lineIndexCache.getLast())
      {
        this.firstLineIndexCache = m;
        int n = 0;
        i1 = m;
        while (n < paramInt)
        {
          i1 = addLine3D(0 + (k + n * 2), 1 + (k + n * 2), i1, null, false);
          n++;
        }
      }
      this.lastLineIndexCache = i1;
    }
    
    void tessellatePoints()
    {
      if (this.strokeCap == 2)
      {
        tessellateRoundPoints();
        return;
      }
      tessellateSquarePoints();
    }
    
    void tessellatePolygon(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      beginTex();
      int i = 1 + (this.in.lastVertex - this.in.firstVertex);
      if ((this.fill) && (3 <= i))
      {
        this.firstPolyIndexCache = -1;
        TessellatorCallback localTessellatorCallback = this.callback;
        boolean bool;
        if (this.in.renderMode == 1)
        {
          bool = true;
          localTessellatorCallback.init(bool, false, paramBoolean3);
          this.gluTess.beginPolygon();
          if (!paramBoolean1) {
            break label771;
          }
          this.gluTess.setWindingRule(100131);
        }
        for (;;)
        {
          this.gluTess.beginContour();
          for (int j = this.in.firstVertex; j <= this.in.lastVertex; j++)
          {
            if (this.in.breaks[j] != 0)
            {
              this.gluTess.endContour();
              this.gluTess.beginContour();
            }
            int k = 0xFF & this.in.colors[j] >> 24;
            int m = 0xFF & this.in.colors[j] >> 16;
            int n = 0xFF & this.in.colors[j] >> 8;
            int i1 = 0xFF & this.in.colors[j] >> 0;
            int i2 = 0xFF & this.in.ambient[j] >> 24;
            int i3 = 0xFF & this.in.ambient[j] >> 16;
            int i4 = 0xFF & this.in.ambient[j] >> 8;
            int i5 = 0xFF & this.in.ambient[j] >> 0;
            int i6 = 0xFF & this.in.specular[j] >> 24;
            int i7 = 0xFF & this.in.specular[j] >> 16;
            int i8 = 0xFF & this.in.specular[j] >> 8;
            int i9 = 0xFF & this.in.specular[j] >> 0;
            int i10 = 0xFF & this.in.emissive[j] >> 24;
            int i11 = 0xFF & this.in.emissive[j] >> 16;
            int i12 = 0xFF & this.in.emissive[j] >> 8;
            int i13 = 0xFF & this.in.emissive[j] >> 0;
            double[] arrayOfDouble = new double[25];
            arrayOfDouble[0] = this.in.vertices[(0 + j * 3)];
            arrayOfDouble[1] = this.in.vertices[(1 + j * 3)];
            arrayOfDouble[2] = this.in.vertices[(2 + j * 3)];
            arrayOfDouble[3] = k;
            arrayOfDouble[4] = m;
            arrayOfDouble[5] = n;
            arrayOfDouble[6] = i1;
            arrayOfDouble[7] = this.in.normals[(0 + j * 3)];
            arrayOfDouble[8] = this.in.normals[(1 + j * 3)];
            arrayOfDouble[9] = this.in.normals[(2 + j * 3)];
            arrayOfDouble[10] = this.in.texcoords[(0 + j * 2)];
            arrayOfDouble[11] = this.in.texcoords[(1 + j * 2)];
            arrayOfDouble[12] = i2;
            arrayOfDouble[13] = i3;
            arrayOfDouble[14] = i4;
            arrayOfDouble[15] = i5;
            arrayOfDouble[16] = i6;
            arrayOfDouble[17] = i7;
            arrayOfDouble[18] = i8;
            arrayOfDouble[19] = i9;
            arrayOfDouble[20] = i10;
            arrayOfDouble[21] = i11;
            arrayOfDouble[22] = i12;
            arrayOfDouble[23] = i13;
            arrayOfDouble[24] = this.in.shininess[j];
            this.gluTess.addVertex(arrayOfDouble);
          }
          bool = false;
          break;
          label771:
          this.gluTess.setWindingRule(100130);
        }
        this.gluTess.endContour();
        this.gluTess.endPolygon();
      }
      endTex();
      tessellateEdges();
    }
    
    void tessellateQuadStrip()
    {
      beginTex();
      int i = 1 + (this.in.lastVertex - this.in.firstVertex);
      if ((this.fill) && (4 <= i))
      {
        setRawSize(6 * (-1 + i / 2));
        int j = 0;
        for (int k = 1; k < i / 2; k++)
        {
          int m = this.in.firstVertex + 2 * (k - 1);
          int n = 1 + (this.in.firstVertex + 2 * (k - 1));
          int i1 = 1 + (this.in.firstVertex + k * 2);
          int i2 = this.in.firstVertex + k * 2;
          int[] arrayOfInt1 = this.rawIndices;
          int i3 = j + 1;
          arrayOfInt1[j] = m;
          int[] arrayOfInt2 = this.rawIndices;
          int i4 = i3 + 1;
          arrayOfInt2[i3] = n;
          int[] arrayOfInt3 = this.rawIndices;
          int i5 = i4 + 1;
          arrayOfInt3[i4] = i2;
          int[] arrayOfInt4 = this.rawIndices;
          int i6 = i5 + 1;
          arrayOfInt4[i5] = n;
          int[] arrayOfInt5 = this.rawIndices;
          int i7 = i6 + 1;
          arrayOfInt5[i6] = i1;
          int[] arrayOfInt6 = this.rawIndices;
          j = i7 + 1;
          arrayOfInt6[i7] = i2;
        }
        splitRawIndices();
      }
      endTex();
      tessellateEdges();
    }
    
    void tessellateQuads()
    {
      int i = 0;
      beginTex();
      int j = 1 + (this.in.lastVertex - this.in.firstVertex);
      if ((this.fill) && (4 <= j))
      {
        int k = j / 4;
        setRawSize(k * 6);
        int m = 0;
        while (i < k)
        {
          int n = 0 + (this.in.firstVertex + i * 4);
          int i1 = 1 + (this.in.firstVertex + i * 4);
          int i2 = 2 + (this.in.firstVertex + i * 4);
          int i3 = 3 + (this.in.firstVertex + i * 4);
          int[] arrayOfInt1 = this.rawIndices;
          int i4 = m + 1;
          arrayOfInt1[m] = n;
          int[] arrayOfInt2 = this.rawIndices;
          int i5 = i4 + 1;
          arrayOfInt2[i4] = i1;
          int[] arrayOfInt3 = this.rawIndices;
          int i6 = i5 + 1;
          arrayOfInt3[i5] = i2;
          int[] arrayOfInt4 = this.rawIndices;
          int i7 = i6 + 1;
          arrayOfInt4[i6] = i2;
          int[] arrayOfInt5 = this.rawIndices;
          int i8 = i7 + 1;
          arrayOfInt5[i7] = i3;
          int[] arrayOfInt6 = this.rawIndices;
          m = i8 + 1;
          arrayOfInt6[i8] = n;
          i++;
        }
        splitRawIndices();
      }
      endTex();
      tessellateEdges();
    }
    
    void tessellateRoundPoints()
    {
      int i = 1 + (this.in.lastVertex - this.in.firstVertex);
      int j;
      int k;
      int m;
      if ((this.stroke) && (1 <= i))
      {
        j = 1 + PApplet.max(20, (int)(6.283186F * this.strokeWeight / 10.0F));
        if (32768 <= j) {
          throw new RuntimeException("Error in point tessellation.");
        }
        updateTex();
        k = j * i;
        m = i * (3 * (j - 1));
        if (!this.is3D) {
          break label101;
        }
        tessellateRoundPoints3D(k, m, j);
      }
      label101:
      while (!this.is2D) {
        return;
      }
      beginNoTex();
      tessellateRoundPoints2D(k, m, j);
      endNoTex();
    }
    
    void tessellateRoundPoints2D(int paramInt1, int paramInt2, int paramInt3)
    {
      int i = paramInt3 - 1;
      this.tess.polyVertexCheck(paramInt1);
      this.tess.polyIndexCheck(paramInt2);
      int j = this.tess.firstPolyVertex;
      int k = this.tess.firstPolyIndex;
      PGraphicsOpenGL.IndexCache localIndexCache = this.tess.polyIndexCache;
      int m;
      int n;
      int i1;
      int i2;
      label89:
      int i3;
      int i5;
      int i4;
      if (this.in.renderMode == 1)
      {
        m = localIndexCache.addNew();
        this.firstPointIndexCache = m;
        n = this.in.firstVertex;
        i1 = k;
        i2 = j;
        if (n > this.in.lastVertex) {
          break label502;
        }
        i3 = localIndexCache.vertexCount[m];
        if (32768 > i3 + paramInt3) {
          break label515;
        }
        i5 = localIndexCache.addNew();
        i4 = 0;
      }
      for (;;)
      {
        float f1 = this.in.vertices[(0 + n * 3)];
        float f2 = this.in.vertices[(1 + n * 3)];
        int i6 = this.in.strokeColors[n];
        float f3 = 720.0F / i;
        this.tess.setPolyVertex(i2, f1, f2, 0.0F, i6);
        int i7 = i2 + 1;
        int i8 = 0;
        float f4 = 0.0F;
        for (;;)
        {
          if (i8 < i)
          {
            this.tess.setPolyVertex(i7, f1 + 0.5F * PGraphicsOpenGL.cosLUT[((int)f4)] * this.strokeWeight, f2 + 0.5F * PGraphicsOpenGL.sinLUT[((int)f4)] * this.strokeWeight, 0.0F, i6);
            i7++;
            f4 = (f4 + f3) % 720.0F;
            i8++;
            continue;
            m = localIndexCache.getLast();
            break;
          }
        }
        for (int i9 = 1; i9 < paramInt3 - 1; i9++)
        {
          short[] arrayOfShort4 = this.tess.polyIndices;
          int i13 = i1 + 1;
          arrayOfShort4[i1] = ((short)(i4 + 0));
          short[] arrayOfShort5 = this.tess.polyIndices;
          int i14 = i13 + 1;
          arrayOfShort5[i13] = ((short)(i4 + i9));
          short[] arrayOfShort6 = this.tess.polyIndices;
          i1 = i14 + 1;
          arrayOfShort6[i14] = ((short)(1 + (i4 + i9)));
        }
        short[] arrayOfShort1 = this.tess.polyIndices;
        int i10 = i1 + 1;
        arrayOfShort1[i1] = ((short)(i4 + 0));
        short[] arrayOfShort2 = this.tess.polyIndices;
        int i11 = i10 + 1;
        arrayOfShort2[i10] = ((short)(i4 + 1));
        short[] arrayOfShort3 = this.tess.polyIndices;
        int i12 = i11 + 1;
        arrayOfShort3[i11] = ((short)(-1 + (i4 + paramInt3)));
        localIndexCache.incCounts(i5, 3 * (paramInt3 - 1), paramInt3);
        n++;
        i1 = i12;
        m = i5;
        i2 = i7;
        break label89;
        label502:
        this.lastPolyIndexCache = m;
        this.lastPointIndexCache = m;
        return;
        label515:
        i4 = i3;
        i5 = m;
      }
    }
    
    void tessellateRoundPoints3D(int paramInt1, int paramInt2, int paramInt3)
    {
      int i = paramInt3 - 1;
      this.tess.pointVertexCheck(paramInt1);
      this.tess.pointIndexCheck(paramInt2);
      int j = this.tess.firstPointVertex;
      int k = this.tess.firstPointVertex;
      int m = this.tess.firstPointIndex;
      PGraphicsOpenGL.IndexCache localIndexCache = this.tess.pointIndexCache;
      int n;
      int i1;
      int i2;
      if (this.in.renderMode == 1)
      {
        n = localIndexCache.addNew();
        this.firstPointIndexCache = n;
        i1 = this.in.firstVertex;
        i2 = n;
      }
      for (int i3 = i1;; i3++)
      {
        if (i3 > this.in.lastVertex) {
          break label534;
        }
        int i4 = localIndexCache.vertexCount[i2];
        if (32768 <= i4 + paramInt3)
        {
          i2 = localIndexCache.addNew();
          i4 = 0;
        }
        int i5 = 0;
        for (;;)
        {
          if (i5 < paramInt3)
          {
            this.tess.setPointVertex(j, this.in, i3);
            int i15 = j + 1;
            i5++;
            j = i15;
            continue;
            n = localIndexCache.getLast();
            break;
          }
        }
        this.tess.pointOffsets[(0 + k * 2)] = 0.0F;
        this.tess.pointOffsets[(1 + k * 2)] = 0.0F;
        int i6 = k + 1;
        float f1 = 720.0F / i;
        k = i6;
        float f2 = 0.0F;
        int i7 = 0;
        while (i7 < i)
        {
          this.tess.pointOffsets[(0 + k * 2)] = (0.5F * PGraphicsOpenGL.cosLUT[((int)f2)] * this.strokeWeight);
          this.tess.pointOffsets[(1 + k * 2)] = (0.5F * PGraphicsOpenGL.sinLUT[((int)f2)] * this.strokeWeight);
          f2 = (f2 + f1) % 720.0F;
          int i14 = k + 1;
          i7++;
          k = i14;
        }
        int i8 = m;
        for (int i9 = 1; i9 < paramInt3 - 1; i9++)
        {
          short[] arrayOfShort4 = this.tess.pointIndices;
          int i12 = i8 + 1;
          arrayOfShort4[i8] = ((short)(i4 + 0));
          short[] arrayOfShort5 = this.tess.pointIndices;
          int i13 = i12 + 1;
          arrayOfShort5[i12] = ((short)(i4 + i9));
          short[] arrayOfShort6 = this.tess.pointIndices;
          i8 = i13 + 1;
          arrayOfShort6[i13] = ((short)(1 + (i4 + i9)));
        }
        short[] arrayOfShort1 = this.tess.pointIndices;
        int i10 = i8 + 1;
        arrayOfShort1[i8] = ((short)(i4 + 0));
        short[] arrayOfShort2 = this.tess.pointIndices;
        int i11 = i10 + 1;
        arrayOfShort2[i10] = ((short)(i4 + 1));
        short[] arrayOfShort3 = this.tess.pointIndices;
        m = i11 + 1;
        arrayOfShort3[i11] = ((short)(-1 + (i4 + paramInt3)));
        localIndexCache.incCounts(i2, 3 * (paramInt3 - 1), paramInt3);
      }
      label534:
      this.lastPointIndexCache = i2;
    }
    
    void tessellateSquarePoints()
    {
      int i = 1 + (this.in.lastVertex - this.in.firstVertex);
      int j;
      int k;
      if ((this.stroke) && (1 <= i))
      {
        updateTex();
        j = i * 5;
        k = i * 12;
        if (!this.is3D) {
          break label57;
        }
        tessellateSquarePoints3D(j, k);
      }
      label57:
      while (!this.is2D) {
        return;
      }
      beginNoTex();
      tessellateSquarePoints2D(j, k);
      endNoTex();
    }
    
    void tessellateSquarePoints2D(int paramInt1, int paramInt2)
    {
      this.tess.polyVertexCheck(paramInt1);
      this.tess.polyIndexCheck(paramInt2);
      int i = this.tess.firstPolyVertex;
      int j = this.tess.firstPolyIndex;
      PGraphicsOpenGL.IndexCache localIndexCache = this.tess.polyIndexCache;
      int k;
      int m;
      int n;
      int i1;
      label82:
      int i2;
      int i4;
      int i3;
      if (this.in.renderMode == 1)
      {
        k = localIndexCache.addNew();
        this.firstPointIndexCache = k;
        m = this.in.firstVertex;
        n = j;
        i1 = i;
        if (m > this.in.lastVertex) {
          break label476;
        }
        i2 = localIndexCache.vertexCount[k];
        if (32768 > i2 + 5) {
          break label489;
        }
        i4 = localIndexCache.addNew();
        i3 = 0;
      }
      for (;;)
      {
        float f1 = this.in.vertices[(0 + m * 3)];
        float f2 = this.in.vertices[(1 + m * 3)];
        int i5 = this.in.strokeColors[m];
        this.tess.setPolyVertex(i1, f1, f2, 0.0F, i5);
        int i6 = i1 + 1;
        int i7 = 0;
        for (;;)
        {
          if (i7 < 4)
          {
            this.tess.setPolyVertex(i6, f1 + 0.5F * PGraphicsOpenGL.this.QUAD_POINT_SIGNS[i7][0] * this.strokeWeight, f2 + 0.5F * PGraphicsOpenGL.this.QUAD_POINT_SIGNS[i7][1] * this.strokeWeight, 0.0F, i5);
            i6++;
            i7++;
            continue;
            k = localIndexCache.getLast();
            break;
          }
        }
        for (int i8 = 1; i8 < 4; i8++)
        {
          short[] arrayOfShort4 = this.tess.polyIndices;
          int i12 = n + 1;
          arrayOfShort4[n] = ((short)(i3 + 0));
          short[] arrayOfShort5 = this.tess.polyIndices;
          int i13 = i12 + 1;
          arrayOfShort5[i12] = ((short)(i3 + i8));
          short[] arrayOfShort6 = this.tess.polyIndices;
          n = i13 + 1;
          arrayOfShort6[i13] = ((short)(1 + (i3 + i8)));
        }
        short[] arrayOfShort1 = this.tess.polyIndices;
        int i9 = n + 1;
        arrayOfShort1[n] = ((short)(i3 + 0));
        short[] arrayOfShort2 = this.tess.polyIndices;
        int i10 = i9 + 1;
        arrayOfShort2[i9] = ((short)(i3 + 1));
        short[] arrayOfShort3 = this.tess.polyIndices;
        int i11 = i10 + 1;
        arrayOfShort3[i10] = ((short)(-1 + (i3 + 5)));
        localIndexCache.incCounts(i4, 12, 5);
        m++;
        n = i11;
        k = i4;
        i1 = i6;
        break label82;
        label476:
        this.lastPolyIndexCache = k;
        this.lastPointIndexCache = k;
        return;
        label489:
        i3 = i2;
        i4 = k;
      }
    }
    
    void tessellateSquarePoints3D(int paramInt1, int paramInt2)
    {
      this.tess.pointVertexCheck(paramInt1);
      this.tess.pointIndexCheck(paramInt2);
      int i = this.tess.firstPointVertex;
      int j = this.tess.firstPointVertex;
      int k = this.tess.firstPointIndex;
      PGraphicsOpenGL.IndexCache localIndexCache = this.tess.pointIndexCache;
      int m;
      int n;
      int i1;
      if (this.in.renderMode == 1)
      {
        m = localIndexCache.addNew();
        this.firstPointIndexCache = m;
        n = this.in.firstVertex;
        i1 = m;
      }
      for (int i2 = n;; i2++)
      {
        if (i2 > this.in.lastVertex) {
          break label499;
        }
        int i3 = localIndexCache.vertexCount[i1];
        if (32768 <= i3 + 5)
        {
          i1 = localIndexCache.addNew();
          i3 = 0;
        }
        int i4 = 0;
        for (;;)
        {
          if (i4 < 5)
          {
            this.tess.setPointVertex(i, this.in, i2);
            int i13 = i + 1;
            i4++;
            i = i13;
            continue;
            m = localIndexCache.getLast();
            break;
          }
        }
        this.tess.pointOffsets[(0 + j * 2)] = 0.0F;
        this.tess.pointOffsets[(1 + j * 2)] = 0.0F;
        j++;
        int i5 = 0;
        while (i5 < 4)
        {
          this.tess.pointOffsets[(0 + j * 2)] = (0.5F * PGraphicsOpenGL.this.QUAD_POINT_SIGNS[i5][0] * this.strokeWeight);
          this.tess.pointOffsets[(1 + j * 2)] = (0.5F * PGraphicsOpenGL.this.QUAD_POINT_SIGNS[i5][1] * this.strokeWeight);
          int i12 = j + 1;
          i5++;
          j = i12;
        }
        int i6 = k;
        for (int i7 = 1; i7 < 4; i7++)
        {
          short[] arrayOfShort4 = this.tess.pointIndices;
          int i10 = i6 + 1;
          arrayOfShort4[i6] = ((short)(i3 + 0));
          short[] arrayOfShort5 = this.tess.pointIndices;
          int i11 = i10 + 1;
          arrayOfShort5[i10] = ((short)(i3 + i7));
          short[] arrayOfShort6 = this.tess.pointIndices;
          i6 = i11 + 1;
          arrayOfShort6[i11] = ((short)(1 + (i3 + i7)));
        }
        short[] arrayOfShort1 = this.tess.pointIndices;
        int i8 = i6 + 1;
        arrayOfShort1[i6] = ((short)(i3 + 0));
        short[] arrayOfShort2 = this.tess.pointIndices;
        int i9 = i8 + 1;
        arrayOfShort2[i8] = ((short)(i3 + 1));
        short[] arrayOfShort3 = this.tess.pointIndices;
        k = i9 + 1;
        arrayOfShort3[i9] = ((short)(-1 + (i3 + 5)));
        localIndexCache.incCounts(i1, 12, 5);
      }
      label499:
      this.lastPointIndexCache = i1;
    }
    
    void tessellateTriangleFan()
    {
      beginTex();
      int i = 1 + (this.in.lastVertex - this.in.firstVertex);
      if ((this.fill) && (3 <= i))
      {
        setRawSize(3 * (i - 2));
        int j = 0;
        for (int k = 1 + this.in.firstVertex; k < this.in.lastVertex; k++)
        {
          int[] arrayOfInt1 = this.rawIndices;
          int m = j + 1;
          arrayOfInt1[j] = this.in.firstVertex;
          int[] arrayOfInt2 = this.rawIndices;
          int n = m + 1;
          arrayOfInt2[m] = k;
          int[] arrayOfInt3 = this.rawIndices;
          j = n + 1;
          arrayOfInt3[n] = (k + 1);
        }
        splitRawIndices();
      }
      endTex();
      tessellateEdges();
    }
    
    void tessellateTriangleStrip()
    {
      beginTex();
      int i = 1 + (this.in.lastVertex - this.in.firstVertex);
      if ((this.fill) && (3 <= i))
      {
        setRawSize(3 * (i - 2));
        int j = 0;
        int k = 1 + this.in.firstVertex;
        if (k < this.in.lastVertex)
        {
          int[] arrayOfInt1 = this.rawIndices;
          int m = j + 1;
          arrayOfInt1[j] = k;
          if (k % 2 == 0)
          {
            int[] arrayOfInt4 = this.rawIndices;
            int i1 = m + 1;
            arrayOfInt4[m] = (k - 1);
            int[] arrayOfInt5 = this.rawIndices;
            j = i1 + 1;
            arrayOfInt5[i1] = (k + 1);
          }
          for (;;)
          {
            k++;
            break;
            int[] arrayOfInt2 = this.rawIndices;
            int n = m + 1;
            arrayOfInt2[m] = (k + 1);
            int[] arrayOfInt3 = this.rawIndices;
            j = n + 1;
            arrayOfInt3[n] = (k - 1);
          }
        }
        splitRawIndices();
      }
      endTex();
      tessellateEdges();
    }
    
    void tessellateTriangles()
    {
      beginTex();
      int i = 1 + (this.in.lastVertex - this.in.firstVertex);
      if ((this.fill) && (3 <= i))
      {
        setRawSize(i);
        int j = 0;
        int k = this.in.firstVertex;
        while (k <= this.in.lastVertex)
        {
          int[] arrayOfInt = this.rawIndices;
          int m = j + 1;
          arrayOfInt[j] = k;
          k++;
          j = m;
        }
        splitRawIndices();
      }
      endTex();
      tessellateEdges();
    }
    
    void tessellateTriangles(int[] paramArrayOfInt)
    {
      beginTex();
      int i = 1 + (this.in.lastVertex - this.in.firstVertex);
      if ((this.fill) && (3 <= i))
      {
        int j = paramArrayOfInt.length;
        setRawSize(j);
        PApplet.arrayCopy(paramArrayOfInt, this.rawIndices, j);
        splitRawIndices();
      }
      endTex();
      tessellateEdges();
    }
    
    void updateTex()
    {
      beginTex();
      endTex();
    }
    
    protected class TessellatorCallback
      implements PGL.TessellatorCallback
    {
      PGraphicsOpenGL.IndexCache cache;
      int cacheIndex;
      boolean calcNormals;
      int primitive;
      boolean strokeTess;
      int vertCount;
      int vertFirst;
      
      protected TessellatorCallback() {}
      
      protected void addIndex(int paramInt)
      {
        PGraphicsOpenGL.Tessellator.this.tess.polyIndexCheck();
        PGraphicsOpenGL.Tessellator.this.tess.polyIndices[(-1 + PGraphicsOpenGL.Tessellator.this.tess.polyIndexCount)] = ((short)(paramInt + this.vertFirst));
      }
      
      public void begin(int paramInt)
      {
        this.cacheIndex = this.cache.getLast();
        if (PGraphicsOpenGL.Tessellator.this.firstPolyIndexCache == -1) {
          PGraphicsOpenGL.Tessellator.this.firstPolyIndexCache = this.cacheIndex;
        }
        if ((this.strokeTess) && (PGraphicsOpenGL.Tessellator.this.firstLineIndexCache == -1)) {
          PGraphicsOpenGL.Tessellator.this.firstLineIndexCache = this.cacheIndex;
        }
        this.vertFirst = this.cache.vertexCount[this.cacheIndex];
        this.vertCount = 0;
        switch (paramInt)
        {
        default: 
          return;
        case 6: 
          this.primitive = 11;
          return;
        case 5: 
          this.primitive = 10;
          return;
        }
        this.primitive = 9;
      }
      
      protected void calcTriNormal(int paramInt1, int paramInt2, int paramInt3)
      {
        PGraphicsOpenGL.Tessellator.this.tess.calcPolyNormal(paramInt1 + this.vertFirst, paramInt2 + this.vertFirst, paramInt3 + this.vertFirst);
      }
      
      public void combine(double[] paramArrayOfDouble, Object[] paramArrayOfObject1, float[] paramArrayOfFloat, Object[] paramArrayOfObject2)
      {
        double[] arrayOfDouble1 = new double[33];
        arrayOfDouble1[0] = paramArrayOfDouble[0];
        arrayOfDouble1[1] = paramArrayOfDouble[1];
        arrayOfDouble1[2] = paramArrayOfDouble[2];
        for (int i = 3; i < 25; i++)
        {
          arrayOfDouble1[i] = 0.0D;
          for (int j = 0; j < 4; j++)
          {
            double[] arrayOfDouble2 = (double[])paramArrayOfObject1[j];
            if (arrayOfDouble2 != null) {
              arrayOfDouble1[i] += paramArrayOfFloat[j] * arrayOfDouble2[i];
            }
          }
        }
        double d = Math.sqrt(arrayOfDouble1[7] * arrayOfDouble1[7] + arrayOfDouble1[8] * arrayOfDouble1[8] + arrayOfDouble1[9] * arrayOfDouble1[9]);
        arrayOfDouble1[7] /= d;
        arrayOfDouble1[8] /= d;
        arrayOfDouble1[9] /= d;
        paramArrayOfObject2[0] = arrayOfDouble1;
      }
      
      public void end()
      {
        int i = 1;
        if (32768 <= this.vertFirst + this.vertCount)
        {
          this.cacheIndex = this.cache.addNew();
          this.vertFirst = 0;
        }
        int j = this.primitive;
        int k = 0;
        switch (j)
        {
        }
        for (;;)
        {
          this.cache.incCounts(this.cacheIndex, k, this.vertCount);
          PGraphicsOpenGL.Tessellator.this.lastPolyIndexCache = this.cacheIndex;
          if (this.strokeTess) {
            PGraphicsOpenGL.Tessellator.this.lastLineIndexCache = this.cacheIndex;
          }
          return;
          int i2 = 3 * (-2 + this.vertCount);
          while (i < -1 + this.vertCount)
          {
            addIndex(0);
            addIndex(i);
            addIndex(i + 1);
            if (this.calcNormals) {
              calcTriNormal(0, i, i + 1);
            }
            i++;
          }
          k = i2;
          continue;
          k = 3 * (-2 + this.vertCount);
          if (i < -1 + this.vertCount)
          {
            if (i % 2 == 0)
            {
              addIndex(i + 1);
              addIndex(i);
              addIndex(i - 1);
              if (this.calcNormals) {
                calcTriNormal(i + 1, i, i - 1);
              }
            }
            for (;;)
            {
              i++;
              break;
              addIndex(i - 1);
              addIndex(i);
              addIndex(i + 1);
              if (this.calcNormals) {
                calcTriNormal(i - 1, i, i + 1);
              }
            }
            int m = this.vertCount;
            for (int n = 0; n < this.vertCount; n++) {
              addIndex(n);
            }
            boolean bool = this.calcNormals;
            int i1 = 0;
            if (bool) {
              while (i1 < this.vertCount / 3)
              {
                calcTriNormal(0 + i1 * 3, 1 + i1 * 3, 2 + i1 * 3);
                i1++;
              }
            }
            k = m;
          }
        }
      }
      
      public void error(int paramInt)
      {
        PGraphics.showWarning("Tessellation Error: %1$s", new Object[] { PGraphicsOpenGL.this.pgl.tessError(paramInt) });
      }
      
      public void init(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
      {
        this.strokeTess = paramBoolean2;
        this.calcNormals = paramBoolean3;
        this.cache = PGraphicsOpenGL.Tessellator.this.tess.polyIndexCache;
        if (paramBoolean1) {
          this.cache.addNew();
        }
      }
      
      public void vertex(Object paramObject)
      {
        if ((paramObject instanceof double[]))
        {
          double[] arrayOfDouble = (double[])paramObject;
          if (arrayOfDouble.length < 25) {
            throw new RuntimeException("TessCallback vertex() data is not of length 25");
          }
          if (this.vertCount < 32768)
          {
            int i = (int)arrayOfDouble[3] << 24 | (int)arrayOfDouble[4] << 16 | (int)arrayOfDouble[5] << 8 | (int)arrayOfDouble[6];
            int j = (int)arrayOfDouble[12] << 24 | (int)arrayOfDouble[13] << 16 | (int)arrayOfDouble[14] << 8 | (int)arrayOfDouble[15];
            int k = (int)arrayOfDouble[16] << 24 | (int)arrayOfDouble[17] << 16 | (int)arrayOfDouble[18] << 8 | (int)arrayOfDouble[19];
            int m = (int)arrayOfDouble[20] << 24 | (int)arrayOfDouble[21] << 16 | (int)arrayOfDouble[22] << 8 | (int)arrayOfDouble[23];
            PGraphicsOpenGL.Tessellator.this.tess.addPolyVertex((float)arrayOfDouble[0], (float)arrayOfDouble[1], (float)arrayOfDouble[2], i, (float)arrayOfDouble[7], (float)arrayOfDouble[8], (float)arrayOfDouble[9], (float)arrayOfDouble[10], (float)arrayOfDouble[11], j, k, m, (float)arrayOfDouble[24]);
            this.vertCount = (1 + this.vertCount);
            return;
          }
          throw new RuntimeException("The tessellator is generating too many vertices, reduce complexity of shape.");
        }
        throw new RuntimeException("TessCallback vertex() data not understood");
      }
    }
  }
  
  protected class TexCache
  {
    int[] firstCache;
    int[] firstIndex;
    boolean hasTexture;
    int[] lastCache;
    int[] lastIndex;
    int size;
    Texture tex0;
    PImage[] textures;
    
    TexCache()
    {
      allocate();
    }
    
    void addTexture(PImage paramPImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      arrayCheck();
      this.textures[this.size] = paramPImage;
      this.firstIndex[this.size] = paramInt1;
      this.lastIndex[this.size] = paramInt3;
      this.firstCache[this.size] = paramInt2;
      this.lastCache[this.size] = paramInt4;
      boolean bool1 = this.hasTexture;
      if (paramPImage != null) {}
      for (boolean bool2 = true;; bool2 = false)
      {
        this.hasTexture = (bool2 | bool1);
        this.size = (1 + this.size);
        return;
      }
    }
    
    void allocate()
    {
      this.textures = new PImage[16];
      this.firstIndex = new int[16];
      this.lastIndex = new int[16];
      this.firstCache = new int[16];
      this.lastCache = new int[16];
      this.size = 0;
      this.hasTexture = false;
    }
    
    void arrayCheck()
    {
      if (this.size == this.textures.length)
      {
        int i = this.size << 1;
        expandTextures(i);
        expandFirstIndex(i);
        expandLastIndex(i);
        expandFirstCache(i);
        expandLastCache(i);
      }
    }
    
    void beginRender()
    {
      this.tex0 = null;
    }
    
    void clear()
    {
      Arrays.fill(this.textures, 0, this.size, null);
      this.size = 0;
      this.hasTexture = false;
    }
    
    void endRender()
    {
      if (this.hasTexture) {
        for (int i = 0; i < this.size; i++)
        {
          PImage localPImage = this.textures[i];
          if (localPImage != null)
          {
            Texture localTexture = PGraphicsOpenGL.pgPrimary.getTexture(localPImage);
            if (localTexture != null)
            {
              localTexture.unbind();
              PGraphicsOpenGL.this.pgl.disableTexturing(localTexture.glTarget);
            }
          }
        }
      }
    }
    
    void expandFirstCache(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.firstCache, 0, arrayOfInt, 0, this.size);
      this.firstCache = arrayOfInt;
    }
    
    void expandFirstIndex(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.firstIndex, 0, arrayOfInt, 0, this.size);
      this.firstIndex = arrayOfInt;
    }
    
    void expandLastCache(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.lastCache, 0, arrayOfInt, 0, this.size);
      this.lastCache = arrayOfInt;
    }
    
    void expandLastIndex(int paramInt)
    {
      int[] arrayOfInt = new int[paramInt];
      PApplet.arrayCopy(this.lastIndex, 0, arrayOfInt, 0, this.size);
      this.lastIndex = arrayOfInt;
    }
    
    void expandTextures(int paramInt)
    {
      PImage[] arrayOfPImage = new PImage[paramInt];
      PApplet.arrayCopy(this.textures, 0, arrayOfPImage, 0, this.size);
      this.textures = arrayOfPImage;
    }
    
    Texture getTexture(int paramInt)
    {
      PImage localPImage = this.textures[paramInt];
      Texture localTexture = null;
      if (localPImage != null)
      {
        localTexture = PGraphicsOpenGL.pgPrimary.getTexture(localPImage);
        if (localTexture != null)
        {
          localTexture.bind();
          this.tex0 = localTexture;
        }
      }
      if ((localTexture == null) && (this.tex0 != null))
      {
        this.tex0.unbind();
        PGraphicsOpenGL.this.pgl.disableTexturing(this.tex0.glTarget);
      }
      return localTexture;
    }
    
    PImage getTextureImage(int paramInt)
    {
      return this.textures[paramInt];
    }
    
    void setLastIndex(int paramInt1, int paramInt2)
    {
      this.lastIndex[(-1 + this.size)] = paramInt1;
      this.lastCache[(-1 + this.size)] = paramInt2;
    }
  }
  
  protected class TexlightShader
    extends PGraphicsOpenGL.LightShader
  {
    protected float[] tcmat;
    protected int texCoordLoc;
    protected int texMatrixLoc;
    protected int texOffsetLoc;
    protected int textureLoc;
    
    public TexlightShader(PApplet paramPApplet)
    {
      super(paramPApplet);
    }
    
    public TexlightShader(PApplet paramPApplet, String paramString1, String paramString2)
    {
      super(paramPApplet, paramString1, paramString2);
    }
    
    public TexlightShader(PApplet paramPApplet, URL paramURL1, URL paramURL2)
    {
      super(paramPApplet, paramURL1, paramURL2);
    }
    
    public void bind()
    {
      this.firstTexUnit = 1;
      super.bind();
      if (-1 < this.texCoordLoc) {
        this.pgl.enableVertexAttribArray(this.texCoordLoc);
      }
    }
    
    public void loadAttributes()
    {
      super.loadAttributes();
      this.texCoordLoc = getAttributeLoc("texCoord");
    }
    
    public void loadUniforms()
    {
      super.loadUniforms();
      this.textureLoc = getUniformLoc("texture");
      this.texMatrixLoc = getUniformLoc("texMatrix");
      this.texOffsetLoc = getUniformLoc("texOffset");
    }
    
    public void setTexcoordAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.texCoordLoc, paramInt1, paramInt2, paramInt3, false, paramInt4, paramInt5);
    }
    
    public void setTexture(Texture paramTexture)
    {
      float f2;
      float f1;
      if (paramTexture.invertedX())
      {
        f2 = 1.0F;
        f1 = -1.0F;
      }
      for (;;)
      {
        float f3;
        if (paramTexture.invertedY()) {
          f3 = -1.0F;
        }
        for (float f4 = 1.0F;; f4 = 0.0F)
        {
          float f5 = f1 * paramTexture.maxTexcoordU;
          float f6 = f2 * paramTexture.maxTexcoordU;
          float f7 = f3 * paramTexture.maxTexcoordV;
          float f8 = f4 * paramTexture.maxTexcoordV;
          if (-1 < this.texMatrixLoc)
          {
            if (this.tcmat == null) {
              this.tcmat = new float[16];
            }
            this.tcmat[0] = f5;
            this.tcmat[4] = 0.0F;
            this.tcmat[8] = 0.0F;
            this.tcmat[12] = f6;
            this.tcmat[1] = 0.0F;
            this.tcmat[5] = f7;
            this.tcmat[9] = 0.0F;
            this.tcmat[13] = f8;
            this.tcmat[2] = 0.0F;
            this.tcmat[6] = 0.0F;
            this.tcmat[10] = 0.0F;
            this.tcmat[14] = 0.0F;
            this.tcmat[3] = 0.0F;
            this.tcmat[7] = 0.0F;
            this.tcmat[11] = 0.0F;
            this.tcmat[15] = 0.0F;
            setUniformMatrix(this.texMatrixLoc, this.tcmat);
          }
          setUniformValue(this.texOffsetLoc, 1.0F / paramTexture.width, 1.0F / paramTexture.height);
          setUniformValue(this.textureLoc, 0);
          return;
          f3 = 1.0F;
        }
        f1 = 1.0F;
        f2 = 0.0F;
      }
    }
    
    public void unbind()
    {
      if (-1 < this.texCoordLoc) {
        this.pgl.disableVertexAttribArray(this.texCoordLoc);
      }
      super.unbind();
    }
  }
  
  protected class TexureShader
    extends PGraphicsOpenGL.ColorShader
  {
    protected float[] tcmat;
    protected int texCoordLoc;
    protected int texMatrixLoc;
    protected int texOffsetLoc;
    protected int textureLoc;
    
    public TexureShader(PApplet paramPApplet)
    {
      super(paramPApplet);
    }
    
    public TexureShader(PApplet paramPApplet, String paramString1, String paramString2)
    {
      super(paramPApplet, paramString1, paramString2);
    }
    
    public TexureShader(PApplet paramPApplet, URL paramURL1, URL paramURL2)
    {
      super(paramPApplet, paramURL1, paramURL2);
    }
    
    public void bind()
    {
      this.firstTexUnit = 1;
      super.bind();
      if (-1 < this.texCoordLoc) {
        this.pgl.enableVertexAttribArray(this.texCoordLoc);
      }
    }
    
    public void loadAttributes()
    {
      super.loadAttributes();
      this.texCoordLoc = getAttributeLoc("texCoord");
    }
    
    public void loadUniforms()
    {
      super.loadUniforms();
      this.textureLoc = getUniformLoc("texture");
      this.texMatrixLoc = getUniformLoc("texMatrix");
      this.texOffsetLoc = getUniformLoc("texOffset");
    }
    
    public void setTexcoordAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      setAttributeVBO(this.texCoordLoc, paramInt1, paramInt2, paramInt3, false, paramInt4, paramInt5);
    }
    
    public void setTexture(Texture paramTexture)
    {
      float f2;
      float f1;
      if (paramTexture.invertedX())
      {
        f2 = 1.0F;
        f1 = -1.0F;
      }
      for (;;)
      {
        float f3;
        if (paramTexture.invertedY()) {
          f3 = -1.0F;
        }
        for (float f4 = 1.0F;; f4 = 0.0F)
        {
          float f5 = f1 * paramTexture.maxTexcoordU();
          float f6 = f2 * paramTexture.maxTexcoordU();
          float f7 = f3 * paramTexture.maxTexcoordV();
          float f8 = f4 * paramTexture.maxTexcoordV();
          if (-1 < this.texMatrixLoc)
          {
            if (this.tcmat == null) {
              this.tcmat = new float[16];
            }
            this.tcmat[0] = f5;
            this.tcmat[4] = 0.0F;
            this.tcmat[8] = 0.0F;
            this.tcmat[12] = f6;
            this.tcmat[1] = 0.0F;
            this.tcmat[5] = f7;
            this.tcmat[9] = 0.0F;
            this.tcmat[13] = f8;
            this.tcmat[2] = 0.0F;
            this.tcmat[6] = 0.0F;
            this.tcmat[10] = 0.0F;
            this.tcmat[14] = 0.0F;
            this.tcmat[3] = 0.0F;
            this.tcmat[7] = 0.0F;
            this.tcmat[11] = 0.0F;
            this.tcmat[15] = 0.0F;
            setUniformMatrix(this.texMatrixLoc, this.tcmat);
          }
          setUniformValue(this.texOffsetLoc, 1.0F / paramTexture.width, 1.0F / paramTexture.height);
          setUniformValue(this.textureLoc, 0);
          return;
          f3 = 1.0F;
        }
        f1 = 1.0F;
        f2 = 0.0F;
      }
    }
    
    public void unbind()
    {
      if (-1 < this.texCoordLoc) {
        this.pgl.disableVertexAttribArray(this.texCoordLoc);
      }
      super.unbind();
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.PGraphicsOpenGL
 * JD-Core Version:    0.7.0.1
 */