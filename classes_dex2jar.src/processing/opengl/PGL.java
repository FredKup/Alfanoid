package processing.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.EGLContextFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;
import processing.core.PApplet;
import processing.opengl.tess.PGLU;
import processing.opengl.tess.PGLUtessellator;
import processing.opengl.tess.PGLUtessellatorCallbackAdapter;

public class PGL
{
  public static final int ALIASED_LINE_WIDTH_RANGE = 33902;
  public static final int ALIASED_POINT_SIZE_RANGE = 33901;
  public static final int ALPHA = 6406;
  public static final int ARRAY_BUFFER = 34962;
  public static final int BACK = 1029;
  protected static boolean BIG_ENDIAN = false;
  public static final int BLEND = 3042;
  public static final int CCW = 2305;
  public static final int CLAMP_TO_EDGE = 33071;
  public static final int COLOR_ATTACHMENT0 = 36064;
  public static final int COLOR_ATTACHMENT1 = -1;
  public static final int COLOR_ATTACHMENT2 = -1;
  public static final int COLOR_ATTACHMENT3 = -1;
  public static final int COLOR_BUFFER_BIT = 16384;
  public static final int COMPILE_STATUS = 35713;
  public static final int CULL_FACE = 2884;
  public static final int CW = 2304;
  protected static final int DEFAULT_IN_EDGES = 32;
  protected static final int DEFAULT_IN_TEXTURES = 16;
  protected static final int DEFAULT_IN_VERTICES = 16;
  protected static final int DEFAULT_TESS_INDICES = 32;
  protected static final int DEFAULT_TESS_VERTICES = 16;
  public static final int DEPTH24_STENCIL8 = 35056;
  public static final int DEPTH_ATTACHMENT = 36096;
  public static final int DEPTH_BITS = 3414;
  public static final int DEPTH_BUFFER_BIT = 256;
  public static final int DEPTH_COMPONENT = 6402;
  public static final int DEPTH_COMPONENT16 = 33189;
  public static final int DEPTH_COMPONENT24 = 33190;
  public static final int DEPTH_COMPONENT32 = 33191;
  public static final int DEPTH_TEST = 2929;
  public static final int DEPTH_WRITEMASK = 2930;
  public static final int DRAW_FRAMEBUFFER = -1;
  public static final int DST_ALPHA = 772;
  public static final int DST_COLOR = 774;
  public static final int DYNAMIC_DRAW = 35048;
  protected static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
  protected static final int EGL_OPENGL_ES2_BIT = 4;
  public static final int ELEMENT_ARRAY_BUFFER = 34963;
  public static final int EXTENSIONS = 7939;
  public static final int FALSE = 0;
  public static final int FLOAT = 5126;
  protected static float FLOAT_EPS = 0.0F;
  protected static final int FLUSH_VERTEX_COUNT = 32768;
  public static boolean FORCE_SCREEN_FBO = false;
  public static final int FRAGMENT_SHADER = 35632;
  public static final int FRAMEBUFFER = 36160;
  public static final int FRAMEBUFFER_COMPLETE = 36053;
  protected static final String FRAMEBUFFER_ERROR_MESSAGE = "Framebuffer error (%1$s), rendering will probably not work as expected";
  public static final int FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
  public static final int FRAMEBUFFER_INCOMPLETE_DIMENSIONS = 36057;
  public static final int FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = -1;
  public static final int FRAMEBUFFER_INCOMPLETE_FORMATS = 36058;
  public static final int FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
  public static final int FRAMEBUFFER_INCOMPLETE_READ_BUFFER = -1;
  public static final int FRAMEBUFFER_UNSUPPORTED = 36061;
  public static final int FRONT = 1028;
  public static final int FRONT_AND_BACK = 1032;
  public static final int FUNC_ADD = 32774;
  public static final int FUNC_MAX = 32776;
  public static final int FUNC_MIN = 32775;
  public static final int FUNC_REVERSE_SUBTRACT = 32779;
  protected static final int INDEX_TYPE = 5123;
  public static final int INFO_LOG_LENGTH = 35716;
  public static final int LEQUAL = 515;
  public static final int LESS = 513;
  public static final int LINEAR = 9729;
  public static final int LINEAR_MIPMAP_LINEAR = 9987;
  public static final int LINEAR_MIPMAP_NEAREST = 9985;
  public static final int LINE_SMOOTH = -1;
  public static final int LINK_STATUS = 35714;
  protected static final int MAX_CAPS_JOINS_LENGTH = 1000;
  protected static final int MAX_FONT_TEX_SIZE = 512;
  protected static final int MAX_LIGHTS = 8;
  public static final int MAX_SAMPLES = -1;
  public static final int MAX_TEXTURE_MAX_ANISOTROPY = 34047;
  public static final int MAX_TEXTURE_SIZE = 3379;
  protected static final int MAX_VERTEX_INDEX = 32767;
  protected static final int MAX_VERTEX_INDEX1 = 32768;
  protected static final int MIN_ARRAYCOPY_SIZE = 2;
  protected static final float MIN_CAPS_JOINS_WEIGHT = 2.0F;
  public static final int MIN_DIRECT_BUFFER_SIZE = 1;
  protected static final boolean MIPMAPS_ENABLED = false;
  public static final int MULTISAMPLE = -1;
  public static final int NEAREST = 9728;
  public static final int ONE = 1;
  public static final int ONE_MINUS_DST_COLOR = 775;
  public static final int ONE_MINUS_SRC_ALPHA = 771;
  public static final int ONE_MINUS_SRC_COLOR = 769;
  public static final int POINT_SMOOTH = -1;
  public static final int POLYGON_SMOOTH = -1;
  public static final int READ_FRAMEBUFFER = -1;
  public static final int READ_ONLY = -1;
  public static final int READ_WRITE = -1;
  public static final int RENDERBUFFER = 36161;
  public static final int RENDERER = 7937;
  public static final int REPEAT = 10497;
  public static final int RGB = 6407;
  public static final int RGBA = 6408;
  public static final int RGBA8 = -1;
  public static final int SAMPLES = 32937;
  public static final boolean SAVE_SURFACE_TO_PIXELS = false;
  public static final int SCISSOR_TEST = 3089;
  protected static final String SHADER_PREPROCESSOR_DIRECTIVE = "#ifdef GL_ES\nprecision mediump float;\nprecision mediump int;\n#endif\n";
  public static final int SHADER_SOURCE_LENGTH = 35720;
  public static final int SHADING_LANGUAGE_VERSION = 35724;
  protected static final int SIZEOF_BYTE = 1;
  protected static final int SIZEOF_FLOAT = 4;
  protected static final int SIZEOF_INDEX = 2;
  protected static final int SIZEOF_INT = 4;
  protected static final int SIZEOF_SHORT = 2;
  public static final int SRC_ALPHA = 770;
  public static final int SRC_COLOR = 768;
  public static final int STATIC_DRAW = 35044;
  public static final int STENCIL_ATTACHMENT = 36128;
  public static final int STENCIL_BITS = 3415;
  public static final int STENCIL_BUFFER_BIT = 1024;
  public static final int STENCIL_INDEX = 6401;
  public static final int STENCIL_INDEX1 = 36166;
  public static final int STENCIL_INDEX4 = 36167;
  public static final int STENCIL_INDEX8 = 36168;
  public static final int STREAM_DRAW = 35040;
  public static final int TESS_WINDING_NONZERO = 100131;
  public static final int TESS_WINDING_ODD = 100130;
  public static final int TEXTURE0 = 33984;
  public static final int TEXTURE1 = 33985;
  public static final int TEXTURE2 = 33986;
  public static final int TEXTURE3 = 33987;
  public static final int TEXTURE_2D = 3553;
  public static final int TEXTURE_BINDING_2D = 32873;
  public static final int TEXTURE_MAG_FILTER = 10240;
  public static final int TEXTURE_MAX_ANISOTROPY = 34046;
  public static final int TEXTURE_MIN_FILTER = 10241;
  public static final int TEXTURE_WRAP_S = 10242;
  public static final int TEXTURE_WRAP_T = 10243;
  public static final int TRIANGLES = 4;
  public static final int TRIANGLE_FAN = 6;
  public static final int TRIANGLE_STRIP = 5;
  public static final int TRUE = 1;
  public static final int UNSIGNED_BYTE = 5121;
  public static final int UNSIGNED_INT = 5125;
  public static final int UNSIGNED_SHORT = 5123;
  public static final boolean USE_DIRECT_BUFFERS = true;
  public static final int VALIDATE_STATUS = 35715;
  public static final int VENDOR = 7936;
  public static final int VERSION = 7938;
  public static final int VERTEX_SHADER = 35633;
  public static final int VIEWPORT = 2978;
  public static final int WRITE_ONLY = -1;
  public static final int ZERO;
  protected static int backTex;
  protected static int[] boundTextures;
  public static EGLContext context;
  protected static int fboHeight;
  protected static boolean fboLayerByDefault;
  protected static boolean fboLayerCreated;
  protected static boolean fboLayerInUse;
  protected static int fboWidth;
  protected static boolean firstFrame;
  protected static int frontTex;
  public static GL10 gl;
  protected static IntBuffer glColorBuf;
  protected static IntBuffer glColorFbo;
  protected static IntBuffer glColorTex;
  protected static IntBuffer glDepth;
  protected static IntBuffer glDepthStencil;
  protected static IntBuffer glMultiFbo;
  protected static IntBuffer glStencil;
  protected static Thread glThread;
  public static PGLU glu;
  protected static boolean loadedTexShader;
  protected static int numSamples;
  protected static AndroidRenderer renderer;
  protected static int reqNumSamples;
  protected static float[] texCoords;
  protected static FloatBuffer texData;
  protected static int texFragShader;
  protected static String texFragShaderSource;
  protected static EGLContext texShaderContext;
  protected static int texShaderProgram;
  protected static int texTCoordLoc;
  protected static int texVertLoc;
  protected static int texVertShader;
  protected static String texVertShaderSource;
  protected static boolean[] texturingTargets;
  protected ByteBuffer byteBuffer;
  protected IntBuffer colorBuffer;
  protected FloatBuffer depthBuffer;
  protected IntBuffer intBuffer;
  protected PGraphicsOpenGL pg;
  protected ByteBuffer stencilBuffer;
  
  static
  {
    FLOAT_EPS = 1.4E-45F;
    float f = 1.0F;
    do
    {
      f /= 2.0F;
    } while ((float)(1.0D + f / 2.0D) != 1.0D);
    FLOAT_EPS = f;
    if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {}
    for (boolean bool = true;; bool = false)
    {
      BIG_ENDIAN = bool;
      texturingTargets = new boolean[1];
      boundTextures = new int[1];
      fboLayerByDefault = FORCE_SCREEN_FBO;
      fboLayerCreated = false;
      fboLayerInUse = false;
      firstFrame = true;
      loadedTexShader = false;
      texCoords = new float[] { -1.0F, -1.0F, 0.0F, 0.0F, 1.0F, -1.0F, 1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F };
      texVertShaderSource = "attribute vec2 inVertex;attribute vec2 inTexcoord;varying vec2 vertTexcoord;void main() {  gl_Position = vec4(inVertex, 0, 1);  vertTexcoord = inTexcoord;}";
      texFragShaderSource = "#ifdef GL_ES\nprecision mediump float;\nprecision mediump int;\n#endif\nuniform sampler2D textureSampler;varying vec2 vertTexcoord;void main() {  gl_FragColor = texture2D(textureSampler, vertTexcoord.st);}";
      return;
    }
  }
  
  public PGL(PGraphicsOpenGL paramPGraphicsOpenGL)
  {
    this.pg = paramPGraphicsOpenGL;
    if (glu == null) {
      glu = new PGLU();
    }
    if (glColorTex == null)
    {
      glColorTex = allocateIntBuffer(2);
      glColorFbo = allocateIntBuffer(1);
      glMultiFbo = allocateIntBuffer(1);
      glColorBuf = allocateIntBuffer(1);
      glDepthStencil = allocateIntBuffer(1);
      glDepth = allocateIntBuffer(1);
      glStencil = allocateIntBuffer(1);
      fboLayerCreated = false;
      fboLayerInUse = false;
      firstFrame = false;
    }
    this.byteBuffer = allocateByteBuffer(1);
    this.intBuffer = allocateIntBuffer(1);
  }
  
  protected static ByteBuffer allocateByteBuffer(int paramInt)
  {
    return allocateDirectByteBuffer(paramInt);
  }
  
  protected static ByteBuffer allocateByteBuffer(byte[] paramArrayOfByte)
  {
    return allocateDirectByteBuffer(paramArrayOfByte.length);
  }
  
  protected static ByteBuffer allocateDirectByteBuffer(int paramInt)
  {
    return ByteBuffer.allocateDirect(1 * PApplet.max(1, paramInt)).order(ByteOrder.nativeOrder());
  }
  
  protected static FloatBuffer allocateDirectFloatBuffer(int paramInt)
  {
    return ByteBuffer.allocateDirect(4 * PApplet.max(1, paramInt)).order(ByteOrder.nativeOrder()).asFloatBuffer();
  }
  
  protected static IntBuffer allocateDirectIntBuffer(int paramInt)
  {
    return ByteBuffer.allocateDirect(4 * PApplet.max(1, paramInt)).order(ByteOrder.nativeOrder()).asIntBuffer();
  }
  
  protected static ShortBuffer allocateDirectShortBuffer(int paramInt)
  {
    return ByteBuffer.allocateDirect(2 * PApplet.max(1, paramInt)).order(ByteOrder.nativeOrder()).asShortBuffer();
  }
  
  protected static FloatBuffer allocateFloatBuffer(int paramInt)
  {
    return allocateDirectFloatBuffer(paramInt);
  }
  
  protected static FloatBuffer allocateFloatBuffer(float[] paramArrayOfFloat)
  {
    return allocateDirectFloatBuffer(paramArrayOfFloat.length);
  }
  
  protected static IntBuffer allocateIntBuffer(int paramInt)
  {
    return allocateDirectIntBuffer(paramInt);
  }
  
  protected static IntBuffer allocateIntBuffer(int[] paramArrayOfInt)
  {
    return allocateDirectIntBuffer(paramArrayOfInt.length);
  }
  
  protected static ShortBuffer allocateShortBuffer(int paramInt)
  {
    return allocateDirectShortBuffer(paramInt);
  }
  
  protected static ShortBuffer allocateShortBuffer(short[] paramArrayOfShort)
  {
    return allocateDirectShortBuffer(paramArrayOfShort.length);
  }
  
  protected static void fillByteBuffer(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, byte paramByte)
  {
    int i = paramInt2 - paramInt1;
    byte[] arrayOfByte = new byte[i];
    Arrays.fill(arrayOfByte, 0, i, paramByte);
    paramByteBuffer.position(paramInt1);
    paramByteBuffer.put(arrayOfByte, 0, i);
    paramByteBuffer.rewind();
  }
  
  protected static void fillFloatBuffer(FloatBuffer paramFloatBuffer, int paramInt1, int paramInt2, float paramFloat)
  {
    int i = paramInt2 - paramInt1;
    float[] arrayOfFloat = new float[i];
    Arrays.fill(arrayOfFloat, 0, i, paramFloat);
    paramFloatBuffer.position(paramInt1);
    paramFloatBuffer.put(arrayOfFloat, 0, i);
    paramFloatBuffer.rewind();
  }
  
  protected static void fillIntBuffer(IntBuffer paramIntBuffer, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt2 - paramInt1;
    int[] arrayOfInt = new int[i];
    Arrays.fill(arrayOfInt, 0, i, paramInt3);
    paramIntBuffer.position(paramInt1);
    paramIntBuffer.put(arrayOfInt, 0, i);
    paramIntBuffer.rewind();
  }
  
  protected static void fillShortBuffer(ShortBuffer paramShortBuffer, int paramInt1, int paramInt2, short paramShort)
  {
    int i = paramInt2 - paramInt1;
    short[] arrayOfShort = new short[i];
    Arrays.fill(arrayOfShort, 0, i, paramShort);
    paramShortBuffer.position(paramInt1);
    paramShortBuffer.put(arrayOfShort, 0, i);
    paramShortBuffer.rewind();
  }
  
  protected static void getByteArray(ByteBuffer paramByteBuffer, byte[] paramArrayOfByte)
  {
    if ((!paramByteBuffer.hasArray()) || (paramByteBuffer.array() != paramArrayOfByte))
    {
      paramByteBuffer.position(0);
      paramByteBuffer.get(paramArrayOfByte);
      paramByteBuffer.rewind();
    }
  }
  
  protected static void getFloatArray(FloatBuffer paramFloatBuffer, float[] paramArrayOfFloat)
  {
    if ((!paramFloatBuffer.hasArray()) || (paramFloatBuffer.array() != paramArrayOfFloat))
    {
      paramFloatBuffer.position(0);
      paramFloatBuffer.get(paramArrayOfFloat);
      paramFloatBuffer.rewind();
    }
  }
  
  protected static void getIntArray(IntBuffer paramIntBuffer, int[] paramArrayOfInt)
  {
    if ((!paramIntBuffer.hasArray()) || (paramIntBuffer.array() != paramArrayOfInt))
    {
      paramIntBuffer.position(0);
      paramIntBuffer.get(paramArrayOfInt);
      paramIntBuffer.rewind();
    }
  }
  
  protected static void getShortArray(ShortBuffer paramShortBuffer, short[] paramArrayOfShort)
  {
    if ((!paramShortBuffer.hasArray()) || (paramShortBuffer.array() != paramArrayOfShort))
    {
      paramShortBuffer.position(0);
      paramShortBuffer.get(paramArrayOfShort);
      paramShortBuffer.rewind();
    }
  }
  
  protected static int javaToNativeARGB(int paramInt)
  {
    if (BIG_ENDIAN) {
      return 0xFF & paramInt >> 24 | 0xFFFFFF00 & paramInt << 8;
    }
    return 0xFF000000 & paramInt | 0xFF0000 & paramInt << 16 | 0xFF00 & paramInt | 0xFF & paramInt >> 16;
  }
  
  protected static void javaToNativeARGB(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = paramInt1 * (paramInt2 - 1);
    int k = 0;
    int i3;
    int i5;
    if (k >= paramInt2 / 2) {
      if (paramInt2 % 2 == 1)
      {
        i3 = paramInt1 * (paramInt2 / 2);
        if (!BIG_ENDIAN) {
          break label288;
        }
        i5 = 0;
        label43:
        if (i5 < paramInt1) {
          break label252;
        }
      }
    }
    for (;;)
    {
      return;
      int i1;
      if (BIG_ENDIAN)
      {
        i1 = 0;
        label59:
        if (i1 < paramInt1) {}
      }
      for (;;)
      {
        j -= paramInt1 * 2;
        k++;
        break;
        int i2 = paramArrayOfInt[i];
        paramArrayOfInt[i] = (0xFF & paramArrayOfInt[j] >> 24 | 0xFFFFFF00 & paramArrayOfInt[j] << 8);
        paramArrayOfInt[j] = (0xFF & i2 >> 24 | 0xFFFFFF00 & i2 << 8);
        i++;
        j++;
        i1++;
        break label59;
        for (int m = 0; m < paramInt1; m++)
        {
          int n = paramArrayOfInt[i];
          paramArrayOfInt[i] = (0xFF000000 & paramArrayOfInt[j] | 0xFF0000 & paramArrayOfInt[j] << 16 | 0xFF00 & paramArrayOfInt[j] | 0xFF & paramArrayOfInt[j] >> 16);
          paramArrayOfInt[j] = (n & 0xFF000000 | 0xFF0000 & n << 16 | n & 0xFF00 | 0xFF & n >> 16);
          i++;
          j++;
        }
      }
      label252:
      paramArrayOfInt[i3] = (0xFF & paramArrayOfInt[i3] >> 24 | 0xFFFFFF00 & paramArrayOfInt[i3] << 8);
      i3++;
      i5++;
      break label43;
      label288:
      for (int i4 = 0; i4 < paramInt1; i4++)
      {
        paramArrayOfInt[i3] = (0xFF000000 & paramArrayOfInt[i3] | 0xFF0000 & paramArrayOfInt[i3] << 16 | 0xFF00 & paramArrayOfInt[i3] | 0xFF & paramArrayOfInt[i3] >> 16);
        i3++;
      }
    }
  }
  
  protected static int javaToNativeRGB(int paramInt)
  {
    if (BIG_ENDIAN) {
      return 0xFF | 0xFFFFFF00 & paramInt << 8;
    }
    return 0xFF000000 | 0xFF0000 & paramInt << 16 | 0xFF00 & paramInt | 0xFF & paramInt >> 16;
  }
  
  protected static void javaToNativeRGB(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = paramInt1 * (paramInt2 - 1);
    int k = 0;
    int i3;
    int i5;
    if (k >= paramInt2 / 2) {
      if (paramInt2 % 2 == 1)
      {
        i3 = paramInt1 * (paramInt2 / 2);
        if (!BIG_ENDIAN) {
          break label258;
        }
        i5 = 0;
        label43:
        if (i5 < paramInt1) {
          break label230;
        }
      }
    }
    for (;;)
    {
      return;
      int i1;
      if (BIG_ENDIAN)
      {
        i1 = 0;
        label59:
        if (i1 < paramInt1) {}
      }
      for (;;)
      {
        j -= paramInt1 * 2;
        k++;
        break;
        int i2 = paramArrayOfInt[i];
        paramArrayOfInt[i] = (0xFF | 0xFFFFFF00 & paramArrayOfInt[j] << 8);
        paramArrayOfInt[j] = (0xFF | 0xFFFFFF00 & i2 << 8);
        i++;
        j++;
        i1++;
        break label59;
        for (int m = 0; m < paramInt1; m++)
        {
          int n = paramArrayOfInt[i];
          paramArrayOfInt[i] = (0xFF000000 | 0xFF0000 & paramArrayOfInt[j] << 16 | 0xFF00 & paramArrayOfInt[j] | 0xFF & paramArrayOfInt[j] >> 16);
          paramArrayOfInt[j] = (0xFF000000 | 0xFF0000 & n << 16 | n & 0xFF00 | 0xFF & n >> 16);
          i++;
          j++;
        }
      }
      label230:
      paramArrayOfInt[i3] = (0xFF | 0xFFFFFF00 & paramArrayOfInt[i3] << 8);
      i3++;
      i5++;
      break label43;
      label258:
      for (int i4 = 0; i4 < paramInt1; i4++)
      {
        paramArrayOfInt[i3] = (0xFF000000 | 0xFF0000 & paramArrayOfInt[i3] << 16 | 0xFF00 & paramArrayOfInt[i3] | 0xFF & paramArrayOfInt[i3] >> 16);
        i3++;
      }
    }
  }
  
  protected static int nativeToJavaARGB(int paramInt)
  {
    if (BIG_ENDIAN) {
      return paramInt & 0xFF000000 | 0xFFFFFF & paramInt >> 8;
    }
    return paramInt & 0xFF000000 | 0xFF0000 & paramInt << 16 | 0xFF00 & paramInt | 0xFF & paramInt >> 16;
  }
  
  protected static void nativeToJavaARGB(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = paramInt1 * (paramInt2 - 1);
    int k = 0;
    int i3;
    int i5;
    if (k >= paramInt2 / 2) {
      if (paramInt2 % 2 == 1)
      {
        i3 = paramInt1 * (paramInt2 / 2);
        if (!BIG_ENDIAN) {
          break label279;
        }
        i5 = 0;
        label43:
        if (i5 < paramInt1) {
          break label246;
        }
      }
    }
    for (;;)
    {
      return;
      int i1;
      if (BIG_ENDIAN)
      {
        i1 = 0;
        label59:
        if (i1 < paramInt1) {}
      }
      for (;;)
      {
        j -= paramInt1 * 2;
        k++;
        break;
        int i2 = paramArrayOfInt[i];
        paramArrayOfInt[i] = (0xFF000000 & paramArrayOfInt[j] | 0xFFFFFF & paramArrayOfInt[j] >> 8);
        paramArrayOfInt[j] = (i2 & 0xFF000000 | 0xFFFFFF & i2 >> 8);
        i++;
        j++;
        i1++;
        break label59;
        for (int m = 0; m < paramInt1; m++)
        {
          int n = paramArrayOfInt[i];
          paramArrayOfInt[i] = (0xFF000000 & paramArrayOfInt[j] | 0xFF0000 & paramArrayOfInt[j] << 16 | 0xFF00 & paramArrayOfInt[j] | 0xFF & paramArrayOfInt[j] >> 16);
          paramArrayOfInt[j] = (n & 0xFF000000 | 0xFF0000 & n << 16 | n & 0xFF00 | 0xFF & n >> 16);
          i++;
          j++;
        }
      }
      label246:
      paramArrayOfInt[i3] = (0xFF000000 & paramArrayOfInt[i3] | 0xFFFFFF & paramArrayOfInt[i3] >> 8);
      i3++;
      i5++;
      break label43;
      label279:
      for (int i4 = 0; i4 < paramInt1; i4++)
      {
        paramArrayOfInt[i3] = (0xFF000000 & paramArrayOfInt[i3] | 0xFF0000 & paramArrayOfInt[i3] << 16 | 0xFF00 & paramArrayOfInt[i3] | 0xFF & paramArrayOfInt[i3] >> 16);
        i3++;
      }
    }
  }
  
  protected static int nativeToJavaRGB(int paramInt)
  {
    if (BIG_ENDIAN) {
      return 0xFF | 0xFFFFFF00 & paramInt << 8;
    }
    return 0xFF000000 | 0xFF0000 & paramInt << 16 | 0xFF00 & paramInt | 0xFF & paramInt >> 16;
  }
  
  protected static void nativeToJavaRGB(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = paramInt1 * (paramInt2 - 1);
    int k = 0;
    int i3;
    int i5;
    if (k >= paramInt2 / 2) {
      if (paramInt2 % 2 == 1)
      {
        i3 = paramInt1 * (paramInt2 / 2);
        if (!BIG_ENDIAN) {
          break label258;
        }
        i5 = 0;
        label43:
        if (i5 < paramInt1) {
          break label230;
        }
      }
    }
    for (;;)
    {
      return;
      int i1;
      if (BIG_ENDIAN)
      {
        i1 = 0;
        label59:
        if (i1 < paramInt1) {}
      }
      for (;;)
      {
        j -= paramInt1 * 2;
        k++;
        break;
        int i2 = paramArrayOfInt[i];
        paramArrayOfInt[i] = (0xFF000000 | 0xFFFFFF & paramArrayOfInt[j] >> 8);
        paramArrayOfInt[j] = (0xFF000000 | 0xFFFFFF & i2 >> 8);
        i++;
        j++;
        i1++;
        break label59;
        for (int m = 0; m < paramInt1; m++)
        {
          int n = paramArrayOfInt[i];
          paramArrayOfInt[i] = (0xFF000000 | 0xFF0000 & paramArrayOfInt[j] << 16 | 0xFF00 & paramArrayOfInt[j] | 0xFF & paramArrayOfInt[j] >> 16);
          paramArrayOfInt[j] = (0xFF000000 | 0xFF0000 & n << 16 | n & 0xFF00 | 0xFF & n >> 16);
          i++;
          j++;
        }
      }
      label230:
      paramArrayOfInt[i3] = (0xFF000000 | 0xFFFFFF & paramArrayOfInt[i3] >> 8);
      i3++;
      i5++;
      break label43;
      label258:
      for (int i4 = 0; i4 < paramInt1; i4++)
      {
        paramArrayOfInt[i3] = (0xFF000000 | 0xFF0000 & paramArrayOfInt[i3] << 16 | 0xFF00 & paramArrayOfInt[i3] | 0xFF & paramArrayOfInt[i3] >> 16);
        i3++;
      }
    }
  }
  
  protected static int nextPowerOfTwo(int paramInt)
  {
    int i = 1;
    for (;;)
    {
      if (i >= paramInt) {
        return i;
      }
      i <<= 1;
    }
  }
  
  protected static void putByteArray(ByteBuffer paramByteBuffer, byte[] paramArrayOfByte)
  {
    if ((!paramByteBuffer.hasArray()) || (paramByteBuffer.array() != paramArrayOfByte))
    {
      paramByteBuffer.position(0);
      paramByteBuffer.put(paramArrayOfByte);
      paramByteBuffer.rewind();
    }
  }
  
  protected static void putFloatArray(FloatBuffer paramFloatBuffer, float[] paramArrayOfFloat)
  {
    if ((!paramFloatBuffer.hasArray()) || (paramFloatBuffer.array() != paramArrayOfFloat))
    {
      paramFloatBuffer.position(0);
      paramFloatBuffer.put(paramArrayOfFloat);
      paramFloatBuffer.rewind();
    }
  }
  
  protected static void putIntArray(IntBuffer paramIntBuffer, int[] paramArrayOfInt)
  {
    if ((!paramIntBuffer.hasArray()) || (paramIntBuffer.array() != paramArrayOfInt))
    {
      paramIntBuffer.position(0);
      paramIntBuffer.put(paramArrayOfInt);
      paramIntBuffer.rewind();
    }
  }
  
  protected static void putShortArray(ShortBuffer paramShortBuffer, short[] paramArrayOfShort)
  {
    if ((!paramShortBuffer.hasArray()) || (paramShortBuffer.array() != paramArrayOfShort))
    {
      paramShortBuffer.position(0);
      paramShortBuffer.put(paramArrayOfShort);
      paramShortBuffer.rewind();
    }
  }
  
  protected static ByteBuffer updateByteBuffer(ByteBuffer paramByteBuffer, byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if ((paramByteBuffer == null) || (paramByteBuffer.capacity() < paramArrayOfByte.length)) {
      paramByteBuffer = allocateDirectByteBuffer(paramArrayOfByte.length);
    }
    paramByteBuffer.position(0);
    paramByteBuffer.put(paramArrayOfByte);
    paramByteBuffer.rewind();
    return paramByteBuffer;
  }
  
  protected static void updateByteBuffer(ByteBuffer paramByteBuffer, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    paramByteBuffer.position(paramInt1);
    paramByteBuffer.put(paramArrayOfByte, paramInt1, paramInt2);
    paramByteBuffer.rewind();
  }
  
  protected static FloatBuffer updateFloatBuffer(FloatBuffer paramFloatBuffer, float[] paramArrayOfFloat, boolean paramBoolean)
  {
    if ((paramFloatBuffer == null) || (paramFloatBuffer.capacity() < paramArrayOfFloat.length)) {
      paramFloatBuffer = allocateDirectFloatBuffer(paramArrayOfFloat.length);
    }
    paramFloatBuffer.position(0);
    paramFloatBuffer.put(paramArrayOfFloat);
    paramFloatBuffer.rewind();
    return paramFloatBuffer;
  }
  
  protected static void updateFloatBuffer(FloatBuffer paramFloatBuffer, float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    paramFloatBuffer.position(paramInt1);
    paramFloatBuffer.put(paramArrayOfFloat, paramInt1, paramInt2);
    paramFloatBuffer.rewind();
  }
  
  protected static IntBuffer updateIntBuffer(IntBuffer paramIntBuffer, int[] paramArrayOfInt, boolean paramBoolean)
  {
    if ((paramIntBuffer == null) || (paramIntBuffer.capacity() < paramArrayOfInt.length)) {
      paramIntBuffer = allocateDirectIntBuffer(paramArrayOfInt.length);
    }
    paramIntBuffer.position(0);
    paramIntBuffer.put(paramArrayOfInt);
    paramIntBuffer.rewind();
    return paramIntBuffer;
  }
  
  protected static void updateIntBuffer(IntBuffer paramIntBuffer, int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    paramIntBuffer.position(paramInt1);
    paramIntBuffer.put(paramArrayOfInt, paramInt1, paramInt2);
    paramIntBuffer.rewind();
  }
  
  protected static ShortBuffer updateShortBuffer(ShortBuffer paramShortBuffer, short[] paramArrayOfShort, boolean paramBoolean)
  {
    if ((paramShortBuffer == null) || (paramShortBuffer.capacity() < paramArrayOfShort.length)) {
      paramShortBuffer = allocateDirectShortBuffer(paramArrayOfShort.length);
    }
    paramShortBuffer.position(0);
    paramShortBuffer.put(paramArrayOfShort);
    paramShortBuffer.rewind();
    return paramShortBuffer;
  }
  
  protected static void updateShortBuffer(ShortBuffer paramShortBuffer, short[] paramArrayOfShort, int paramInt1, int paramInt2)
  {
    paramShortBuffer.position(paramInt1);
    paramShortBuffer.put(paramArrayOfShort, paramInt1, paramInt2);
    paramShortBuffer.rewind();
  }
  
  public void activeTexture(int paramInt)
  {
    GLES20.glActiveTexture(paramInt);
  }
  
  public void attachShader(int paramInt1, int paramInt2)
  {
    GLES20.glAttachShader(paramInt1, paramInt2);
  }
  
  protected void beginDraw(boolean paramBoolean)
  {
    if (fboLayerInUse(paramBoolean))
    {
      bindFramebuffer(36160, glColorFbo.get(0));
      framebufferTexture2D(36160, 36064, 3553, glColorTex.get(backTex), 0);
      if (1 < numSamples) {
        bindFramebuffer(36160, glMultiFbo.get(0));
      }
      if (firstFrame)
      {
        int i = this.pg.backgroundColor;
        float f = (0xFF & i >> 24) / 255.0F;
        clearColor((0xFF & i >> 16) / 255.0F, (0xFF & i >> 8) / 255.0F, (i & 0xFF) / 255.0F, f);
        clear(16384);
      }
    }
    for (fboLayerInUse = true;; fboLayerInUse = false)
    {
      if (firstFrame) {
        firstFrame = false;
      }
      if (!fboLayerByDefault) {
        FORCE_SCREEN_FBO = false;
      }
      return;
      if (paramBoolean) {
        break;
      }
      drawTexture(3553, glColorTex.get(frontTex), fboWidth, fboHeight, 0, 0, this.pg.width, this.pg.height, 0, 0, this.pg.width, this.pg.height);
      break;
    }
  }
  
  protected void beginGL() {}
  
  public void bindBuffer(int paramInt1, int paramInt2)
  {
    GLES20.glBindBuffer(paramInt1, paramInt2);
  }
  
  public void bindFramebuffer(int paramInt1, int paramInt2)
  {
    GLES20.glBindFramebuffer(paramInt1, paramInt2);
  }
  
  protected void bindFrontTexture()
  {
    if (!texturingIsEnabled(3553)) {
      enableTexturing(3553);
    }
    bindTexture(3553, glColorTex.get(frontTex));
  }
  
  public void bindRenderbuffer(int paramInt1, int paramInt2)
  {
    GLES20.glBindRenderbuffer(paramInt1, paramInt2);
  }
  
  public void bindTexture(int paramInt1, int paramInt2)
  {
    GLES20.glBindTexture(paramInt1, paramInt2);
    if (paramInt1 == 3553) {
      boundTextures[0] = paramInt2;
    }
  }
  
  public void blendEquation(int paramInt)
  {
    GLES20.glBlendEquation(paramInt);
  }
  
  public void blendFunc(int paramInt1, int paramInt2)
  {
    GLES20.glBlendFunc(paramInt1, paramInt2);
  }
  
  public void blitFramebuffer(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10) {}
  
  public void bufferData(int paramInt1, int paramInt2, Buffer paramBuffer, int paramInt3)
  {
    GLES20.glBufferData(paramInt1, paramInt2, paramBuffer, paramInt3);
  }
  
  public void bufferSubData(int paramInt1, int paramInt2, int paramInt3, Buffer paramBuffer)
  {
    GLES20.glBufferSubData(paramInt1, paramInt2, paramInt3, paramBuffer);
  }
  
  protected boolean canDraw()
  {
    return true;
  }
  
  public int checkFramebufferStatus(int paramInt)
  {
    return GLES20.glCheckFramebufferStatus(paramInt);
  }
  
  public void clear(int paramInt)
  {
    GLES20.glClear(paramInt);
  }
  
  public void clearColor(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    GLES20.glClearColor(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void clearDepth(float paramFloat)
  {
    GLES20.glClearDepthf(paramFloat);
  }
  
  public void clearStencil(int paramInt)
  {
    GLES20.glClearStencil(paramInt);
  }
  
  public void colorMask(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    GLES20.glColorMask(paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);
  }
  
  public void compileShader(int paramInt)
  {
    GLES20.glCompileShader(paramInt);
  }
  
  protected boolean compiled(int paramInt)
  {
    this.intBuffer.rewind();
    getShaderiv(paramInt, 35713, this.intBuffer);
    return this.intBuffer.get(0) != 0;
  }
  
  protected boolean contextIsCurrent(int paramInt)
  {
    return (paramInt == -1) || (paramInt == context.hashCode());
  }
  
  protected void copyToTexture(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, IntBuffer paramIntBuffer)
  {
    activeTexture(33984);
    boolean bool = texturingIsEnabled(paramInt1);
    int i = 0;
    if (!bool)
    {
      enableTexturing(paramInt1);
      i = 1;
    }
    bindTexture(paramInt1, paramInt3);
    texSubImage2D(paramInt1, 0, paramInt4, paramInt5, paramInt6, paramInt7, paramInt2, 5121, paramIntBuffer);
    bindTexture(paramInt1, 0);
    if (i != 0) {
      disableTexturing(paramInt1);
    }
  }
  
  protected int createEmptyContext()
  {
    return -1;
  }
  
  public int createProgram()
  {
    return GLES20.glCreateProgram();
  }
  
  protected int createProgram(int paramInt1, int paramInt2)
  {
    int i = createProgram();
    if (i != 0)
    {
      attachShader(i, paramInt1);
      attachShader(i, paramInt2);
      linkProgram(i);
      if (!linked(i))
      {
        System.err.println("Could not link program: ");
        System.err.println(getProgramInfoLog(i));
        deleteProgram(i);
        i = 0;
      }
    }
    return i;
  }
  
  public int createShader(int paramInt)
  {
    return GLES20.glCreateShader(paramInt);
  }
  
  protected int createShader(int paramInt, String paramString)
  {
    int i = createShader(paramInt);
    if (i != 0)
    {
      shaderSource(i, paramString);
      compileShader(i);
      if (!compiled(i))
      {
        System.err.println("Could not compile shader " + paramInt + ":");
        System.err.println(getShaderInfoLog(i));
        deleteShader(i);
        i = 0;
      }
    }
    return i;
  }
  
  protected Tessellator createTessellator(TessellatorCallback paramTessellatorCallback)
  {
    return new Tessellator(paramTessellatorCallback);
  }
  
  public void cullFace(int paramInt)
  {
    GLES20.glCullFace(paramInt);
  }
  
  public void deleteBuffers(int paramInt, IntBuffer paramIntBuffer)
  {
    GLES20.glDeleteBuffers(paramInt, paramIntBuffer);
  }
  
  public void deleteFramebuffers(int paramInt, IntBuffer paramIntBuffer)
  {
    GLES20.glDeleteFramebuffers(paramInt, paramIntBuffer);
  }
  
  public void deleteProgram(int paramInt)
  {
    GLES20.glDeleteProgram(paramInt);
  }
  
  public void deleteRenderbuffers(int paramInt, IntBuffer paramIntBuffer)
  {
    GLES20.glDeleteRenderbuffers(paramInt, paramIntBuffer);
  }
  
  public void deleteShader(int paramInt)
  {
    GLES20.glDeleteShader(paramInt);
  }
  
  protected void deleteSurface()
  {
    if (glColorTex != null)
    {
      deleteTextures(2, glColorTex);
      deleteFramebuffers(1, glColorFbo);
      deleteFramebuffers(1, glMultiFbo);
      deleteRenderbuffers(1, glColorBuf);
      deleteRenderbuffers(1, glDepthStencil);
      deleteRenderbuffers(1, glDepth);
      deleteRenderbuffers(1, glStencil);
    }
    fboLayerCreated = false;
    fboLayerInUse = false;
    firstFrame = true;
  }
  
  public void deleteTextures(int paramInt, IntBuffer paramIntBuffer)
  {
    GLES20.glDeleteTextures(paramInt, paramIntBuffer);
  }
  
  public void depthFunc(int paramInt)
  {
    GLES20.glDepthFunc(paramInt);
  }
  
  public void depthMask(boolean paramBoolean)
  {
    GLES20.glDepthMask(paramBoolean);
  }
  
  public void disable(int paramInt)
  {
    if (-1 < paramInt) {
      GLES20.glDisable(paramInt);
    }
  }
  
  protected void disableTexturing(int paramInt)
  {
    if (paramInt == 3553) {
      texturingTargets[0] = false;
    }
  }
  
  public void disableVertexAttribArray(int paramInt)
  {
    GLES20.glDisableVertexAttribArray(paramInt);
  }
  
  public void drawArrays(int paramInt1, int paramInt2, int paramInt3)
  {
    GLES20.glDrawArrays(paramInt1, paramInt2, paramInt3);
  }
  
  public void drawBuffer(int paramInt) {}
  
  public void drawElements(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    GLES20.glDrawElements(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void drawTexture(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    drawTexture(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt5, paramInt6, paramInt7, paramInt8);
  }
  
  protected void drawTexture(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12)
  {
    if ((!loadedTexShader) || (texShaderContext.hashCode() != context.hashCode()))
    {
      texVertShader = createShader(35633, texVertShaderSource);
      texFragShader = createShader(35632, texFragShaderSource);
      if ((texVertShader > 0) && (texFragShader > 0)) {
        texShaderProgram = createProgram(texVertShader, texFragShader);
      }
      if (texShaderProgram > 0)
      {
        texVertLoc = getAttribLocation(texShaderProgram, "inVertex");
        texTCoordLoc = getAttribLocation(texShaderProgram, "inTexcoord");
      }
      loadedTexShader = true;
      texShaderContext = context;
    }
    if (texData == null) {
      texData = allocateDirectFloatBuffer(texCoords.length);
    }
    boolean bool2;
    if (texShaderProgram > 0)
    {
      boolean bool1 = getDepthTest();
      disable(2929);
      bool2 = getDepthWriteMask();
      depthMask(false);
      useProgram(texShaderProgram);
      enableVertexAttribArray(texVertLoc);
      enableVertexAttribArray(texTCoordLoc);
      texCoords[0] = (2.0F * paramInt9 / this.pg.width - 1.0F);
      texCoords[1] = (2.0F * paramInt10 / this.pg.height - 1.0F);
      texCoords[2] = (paramInt5 / paramInt3);
      texCoords[3] = (paramInt6 / paramInt4);
      texCoords[4] = (2.0F * paramInt11 / this.pg.width - 1.0F);
      texCoords[5] = (2.0F * paramInt10 / this.pg.height - 1.0F);
      texCoords[6] = (paramInt7 / paramInt3);
      texCoords[7] = (paramInt6 / paramInt4);
      texCoords[8] = (2.0F * paramInt9 / this.pg.width - 1.0F);
      texCoords[9] = (2.0F * paramInt12 / this.pg.height - 1.0F);
      texCoords[10] = (paramInt5 / paramInt3);
      texCoords[11] = (paramInt8 / paramInt4);
      texCoords[12] = (2.0F * paramInt11 / this.pg.width - 1.0F);
      texCoords[13] = (2.0F * paramInt12 / this.pg.height - 1.0F);
      texCoords[14] = (paramInt7 / paramInt3);
      texCoords[15] = (paramInt8 / paramInt4);
      texData.rewind();
      texData.put(texCoords);
      activeTexture(33984);
      boolean bool3 = texturingIsEnabled(3553);
      int i = 0;
      if (!bool3)
      {
        enableTexturing(3553);
        i = 1;
      }
      bindTexture(paramInt1, paramInt2);
      bindBuffer(34962, 0);
      texData.position(0);
      vertexAttribPointer(texVertLoc, 2, 5126, false, 16, texData);
      texData.position(2);
      vertexAttribPointer(texTCoordLoc, 2, 5126, false, 16, texData);
      drawArrays(5, 0, 4);
      bindTexture(paramInt1, 0);
      if (i != 0) {
        disableTexturing(3553);
      }
      disableVertexAttribArray(texVertLoc);
      disableVertexAttribArray(texTCoordLoc);
      useProgram(0);
      if (!bool1) {
        break label626;
      }
      enable(2929);
    }
    for (;;)
    {
      depthMask(bool2);
      return;
      label626:
      disable(2929);
    }
  }
  
  public void enable(int paramInt)
  {
    if (-1 < paramInt) {
      GLES20.glEnable(paramInt);
    }
  }
  
  protected void enableTexturing(int paramInt)
  {
    if (paramInt == 3553) {
      texturingTargets[0] = true;
    }
  }
  
  public void enableVertexAttribArray(int paramInt)
  {
    GLES20.glEnableVertexAttribArray(paramInt);
  }
  
  protected void endDraw(boolean paramBoolean)
  {
    if (fboLayerInUse)
    {
      syncBackTexture();
      bindFramebuffer(36160, 0);
      clearDepth(1.0F);
      clearColor(0.0F, 0.0F, 0.0F, 0.0F);
      clear(16640);
      disable(3042);
      drawTexture(3553, glColorTex.get(backTex), fboWidth, fboHeight, 0, 0, this.pg.width, this.pg.height, 0, 0, this.pg.width, this.pg.height);
      int i = frontTex;
      frontTex = backTex;
      backTex = i;
    }
    flush();
  }
  
  protected void endGL() {}
  
  public String errorString(int paramInt)
  {
    return GLU.gluErrorString(paramInt);
  }
  
  protected boolean fboLayerInUse(boolean paramBoolean)
  {
    if ((paramBoolean) && (!FORCE_SCREEN_FBO) && (1 >= numSamples)) {}
    for (int i = 0; (i != 0) && (glColorFbo.get(0) != 0); i = 1) {
      return true;
    }
    return false;
  }
  
  public void finish() {}
  
  public void flush() {}
  
  public void framebufferRenderbuffer(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    GLES20.glFramebufferRenderbuffer(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void framebufferTexture2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    GLES20.glFramebufferTexture2D(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  public void frontFace(int paramInt)
  {
    GLES20.glFrontFace(paramInt);
  }
  
  public void genBuffers(int paramInt, IntBuffer paramIntBuffer)
  {
    GLES20.glGenBuffers(paramInt, paramIntBuffer);
  }
  
  public void genFramebuffers(int paramInt, IntBuffer paramIntBuffer)
  {
    GLES20.glGenFramebuffers(paramInt, paramIntBuffer);
  }
  
  public void genRenderbuffers(int paramInt, IntBuffer paramIntBuffer)
  {
    GLES20.glGenRenderbuffers(paramInt, paramIntBuffer);
  }
  
  public void genTextures(int paramInt, IntBuffer paramIntBuffer)
  {
    GLES20.glGenTextures(paramInt, paramIntBuffer);
  }
  
  public void generateMipmap(int paramInt)
  {
    GLES20.glGenerateMipmap(paramInt);
  }
  
  public int getAttribLocation(int paramInt, String paramString)
  {
    return GLES20.glGetAttribLocation(paramInt, paramString);
  }
  
  int getBackTextureName()
  {
    return glColorTex.get(backTex);
  }
  
  public void getBooleanv(int paramInt, IntBuffer paramIntBuffer)
  {
    if (-1 < paramInt)
    {
      GLES20.glGetBooleanv(paramInt, paramIntBuffer);
      return;
    }
    fillIntBuffer(paramIntBuffer, 0, paramIntBuffer.capacity(), 0);
  }
  
  protected int getColorValue(int paramInt1, int paramInt2)
  {
    if (this.colorBuffer == null) {
      this.colorBuffer = IntBuffer.allocate(1);
    }
    this.colorBuffer.rewind();
    readPixels(paramInt1, -1 + (this.pg.height - paramInt2), 1, 1, 6408, 5121, this.colorBuffer);
    return this.colorBuffer.get();
  }
  
  public AndroidConfigChooser getConfigChooser(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return new AndroidConfigChooser(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public AndroidContextFactory getContextFactory()
  {
    return new AndroidContextFactory();
  }
  
  protected int getCurrentContext()
  {
    return context.hashCode();
  }
  
  protected int getDefaultDrawBuffer()
  {
    if (fboLayerInUse) {
      return 36064;
    }
    return 1029;
  }
  
  protected int getDefaultReadBuffer()
  {
    if (fboLayerInUse) {
      return 36064;
    }
    return 1028;
  }
  
  protected int getDepthBits()
  {
    this.intBuffer.rewind();
    getIntegerv(3414, this.intBuffer);
    return this.intBuffer.get(0);
  }
  
  protected boolean getDepthTest()
  {
    this.intBuffer.rewind();
    getBooleanv(2929, this.intBuffer);
    return this.intBuffer.get(0) != 0;
  }
  
  protected float getDepthValue(int paramInt1, int paramInt2)
  {
    return 0.0F;
  }
  
  protected boolean getDepthWriteMask()
  {
    this.intBuffer.rewind();
    getBooleanv(2930, this.intBuffer);
    return this.intBuffer.get(0) != 0;
  }
  
  protected int getDrawFramebuffer()
  {
    boolean bool = fboLayerInUse;
    int i = 0;
    if (bool) {
      i = glColorFbo.get(0);
    }
    return i;
  }
  
  public int getError()
  {
    return GLES20.glGetError();
  }
  
  public void getFloatv(int paramInt, FloatBuffer paramFloatBuffer)
  {
    if (-1 < paramInt)
    {
      GLES20.glGetFloatv(paramInt, paramFloatBuffer);
      return;
    }
    fillFloatBuffer(paramFloatBuffer, 0, paramFloatBuffer.capacity(), 0.0F);
  }
  
  int getFrontTextureName()
  {
    return glColorTex.get(frontTex);
  }
  
  protected int[] getGLVersion()
  {
    String str = getString(7938).trim();
    int[] arrayOfInt = new int[3];
    String[] arrayOfString1 = str.split(" ");
    int i = 0;
    for (;;)
    {
      if (i >= arrayOfString1.length) {
        label33:
        return arrayOfInt;
      }
      String[] arrayOfString2;
      if (arrayOfString1[i].indexOf(".") > 0) {
        arrayOfString2 = arrayOfString1[i].split("\\.");
      }
      try
      {
        arrayOfInt[0] = Integer.parseInt(arrayOfString2[0]);
        label70:
        if (1 < arrayOfString2.length) {}
        try
        {
          arrayOfInt[1] = Integer.parseInt(arrayOfString2[1]);
          label87:
          if (2 >= arrayOfString2.length) {
            break label33;
          }
          try
          {
            arrayOfInt[2] = Integer.parseInt(arrayOfString2[2]);
            return arrayOfInt;
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            return arrayOfInt;
          }
          i++;
        }
        catch (NumberFormatException localNumberFormatException3)
        {
          break label87;
        }
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        break label70;
      }
    }
  }
  
  public void getIntegerv(int paramInt, IntBuffer paramIntBuffer)
  {
    if (-1 < paramInt)
    {
      GLES20.glGetIntegerv(paramInt, paramIntBuffer);
      return;
    }
    fillIntBuffer(paramIntBuffer, 0, paramIntBuffer.capacity(), 0);
  }
  
  public String getProgramInfoLog(int paramInt)
  {
    return GLES20.glGetProgramInfoLog(paramInt);
  }
  
  public void getProgramiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer)
  {
    GLES20.glGetProgramiv(paramInt1, paramInt2, paramIntBuffer);
  }
  
  protected int getReadFramebuffer()
  {
    boolean bool = fboLayerInUse;
    int i = 0;
    if (bool) {
      i = glColorFbo.get(0);
    }
    return i;
  }
  
  public AndroidRenderer getRenderer()
  {
    renderer = new AndroidRenderer();
    return renderer;
  }
  
  public String getShaderInfoLog(int paramInt)
  {
    return GLES20.glGetShaderInfoLog(paramInt);
  }
  
  public void getShaderiv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer)
  {
    GLES20.glGetShaderiv(paramInt1, paramInt2, paramIntBuffer);
  }
  
  protected int getStencilBits()
  {
    this.intBuffer.rewind();
    getIntegerv(3415, this.intBuffer);
    return this.intBuffer.get(0);
  }
  
  protected byte getStencilValue(int paramInt1, int paramInt2)
  {
    return 0;
  }
  
  public String getString(int paramInt)
  {
    return GLES20.glGetString(paramInt);
  }
  
  public void getTexParameteriv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer)
  {
    GLES20.glGetTexParameteriv(paramInt1, paramInt2, paramIntBuffer);
  }
  
  public int getUniformLocation(int paramInt, String paramString)
  {
    return GLES20.glGetUniformLocation(paramInt, paramString);
  }
  
  protected void initSurface(int paramInt)
  {
    reqNumSamples = qualityToSamples(paramInt);
    fboLayerCreated = false;
    fboLayerInUse = false;
    firstFrame = true;
  }
  
  protected void initTexture(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    initTexture(paramInt1, paramInt2, paramInt3, paramInt4, 0);
  }
  
  protected void initTexture(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int[] arrayOfInt = new int[256];
    Arrays.fill(arrayOfInt, javaToNativeARGB(paramInt5));
    IntBuffer localIntBuffer = allocateDirectIntBuffer(256);
    localIntBuffer.put(arrayOfInt);
    localIntBuffer.rewind();
    int i = 0;
    if (i >= paramInt4) {
      return;
    }
    int j = PApplet.min(16, paramInt4 - i);
    for (int k = 0;; k += 16)
    {
      if (k >= paramInt3)
      {
        i += 16;
        break;
      }
      texSubImage2D(paramInt1, 0, k, i, PApplet.min(16, paramInt3 - k), j, paramInt2, 5121, localIntBuffer);
    }
  }
  
  protected boolean isFBOBacked()
  {
    return fboLayerInUse;
  }
  
  protected boolean isMultisampled()
  {
    return false;
  }
  
  public void linkProgram(int paramInt)
  {
    GLES20.glLinkProgram(paramInt);
  }
  
  protected boolean linked(int paramInt)
  {
    this.intBuffer.rewind();
    getProgramiv(paramInt, 35714, this.intBuffer);
    return this.intBuffer.get(0) != 0;
  }
  
  public ByteBuffer mapBuffer(int paramInt1, int paramInt2)
  {
    return null;
  }
  
  public ByteBuffer mapBufferRange(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return null;
  }
  
  protected void needFBOLayer()
  {
    FORCE_SCREEN_FBO = true;
  }
  
  protected int qualityToSamples(int paramInt)
  {
    if (paramInt <= 1) {
      return 1;
    }
    return 2 * (paramInt / 2);
  }
  
  public void readBuffer(int paramInt) {}
  
  public void readPixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Buffer paramBuffer)
  {
    GLES20.glReadPixels(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramBuffer);
  }
  
  public void renderbufferStorage(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    GLES20.glRenderbufferStorage(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void renderbufferStorageMultisample(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {}
  
  protected void requestDraw()
  {
    this.pg.parent.andresNeedsBetterAPI();
  }
  
  public void scissor(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    GLES20.glScissor(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void setFrameRate(float paramFloat) {}
  
  public void shaderSource(int paramInt, String paramString)
  {
    GLES20.glShaderSource(paramInt, paramString);
  }
  
  protected void syncBackTexture()
  {
    if (1 < numSamples)
    {
      bindFramebuffer(-1, glMultiFbo.get(0));
      bindFramebuffer(-1, glColorFbo.get(0));
      blitFramebuffer(0, 0, fboWidth, fboHeight, 0, 0, fboWidth, fboHeight, 16384, 9728);
    }
  }
  
  protected String tessError(int paramInt)
  {
    return PGLU.gluErrorString(paramInt);
  }
  
  public void texImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Buffer paramBuffer)
  {
    GLES20.glTexImage2D(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBuffer);
  }
  
  public void texParameterf(int paramInt1, int paramInt2, float paramFloat)
  {
    GLES20.glTexParameterf(paramInt1, paramInt2, paramFloat);
  }
  
  public void texParameteri(int paramInt1, int paramInt2, int paramInt3)
  {
    GLES20.glTexParameteri(paramInt1, paramInt2, paramInt3);
  }
  
  public void texSubImage2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Buffer paramBuffer)
  {
    GLES20.glTexSubImage2D(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBuffer);
  }
  
  protected boolean textureIsBound(int paramInt1, int paramInt2)
  {
    boolean bool = false;
    if (paramInt1 == 3553)
    {
      int i = boundTextures[0];
      bool = false;
      if (i == paramInt2) {
        bool = true;
      }
    }
    return bool;
  }
  
  protected boolean texturingIsEnabled(int paramInt)
  {
    int i = 0;
    if (paramInt == 3553) {
      i = texturingTargets[0];
    }
    return i;
  }
  
  protected boolean threadIsCurrent()
  {
    return Thread.currentThread() == glThread;
  }
  
  protected void unbindFrontTexture()
  {
    if (textureIsBound(3553, glColorTex.get(frontTex)))
    {
      if (!texturingIsEnabled(3553))
      {
        enableTexturing(3553);
        bindTexture(3553, 0);
        disableTexturing(3553);
      }
    }
    else {
      return;
    }
    bindTexture(3553, 0);
  }
  
  public void uniform1f(int paramInt, float paramFloat)
  {
    GLES20.glUniform1f(paramInt, paramFloat);
  }
  
  public void uniform1fv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer)
  {
    GLES20.glUniform1fv(paramInt1, paramInt2, paramFloatBuffer);
  }
  
  public void uniform1i(int paramInt1, int paramInt2)
  {
    GLES20.glUniform1i(paramInt1, paramInt2);
  }
  
  public void uniform1iv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer)
  {
    GLES20.glUniform1iv(paramInt1, paramInt2, paramIntBuffer);
  }
  
  public void uniform2f(int paramInt, float paramFloat1, float paramFloat2)
  {
    GLES20.glUniform2f(paramInt, paramFloat1, paramFloat2);
  }
  
  public void uniform2fv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer)
  {
    GLES20.glUniform2fv(paramInt1, paramInt2, paramFloatBuffer);
  }
  
  public void uniform2i(int paramInt1, int paramInt2, int paramInt3)
  {
    GLES20.glUniform2i(paramInt1, paramInt2, paramInt3);
  }
  
  public void uniform2iv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer)
  {
    GLES20.glUniform2iv(paramInt1, paramInt2, paramIntBuffer);
  }
  
  public void uniform3f(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    GLES20.glUniform3f(paramInt, paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void uniform3fv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer)
  {
    GLES20.glUniform3fv(paramInt1, paramInt2, paramFloatBuffer);
  }
  
  public void uniform3i(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    GLES20.glUniform3i(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void uniform3iv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer)
  {
    GLES20.glUniform3iv(paramInt1, paramInt2, paramIntBuffer);
  }
  
  public void uniform4f(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    GLES20.glUniform4f(paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void uniform4fv(int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer)
  {
    GLES20.glUniform4fv(paramInt1, paramInt2, paramFloatBuffer);
  }
  
  public void uniform4i(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    GLES20.glUniform4i(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  public void uniform4iv(int paramInt1, int paramInt2, IntBuffer paramIntBuffer)
  {
    GLES20.glUniform4iv(paramInt1, paramInt2, paramIntBuffer);
  }
  
  public void uniformMatrix2fv(int paramInt1, int paramInt2, boolean paramBoolean, FloatBuffer paramFloatBuffer)
  {
    GLES20.glUniformMatrix2fv(paramInt1, paramInt2, paramBoolean, paramFloatBuffer);
  }
  
  public void uniformMatrix3fv(int paramInt1, int paramInt2, boolean paramBoolean, FloatBuffer paramFloatBuffer)
  {
    GLES20.glUniformMatrix3fv(paramInt1, paramInt2, paramBoolean, paramFloatBuffer);
  }
  
  public void uniformMatrix4fv(int paramInt1, int paramInt2, boolean paramBoolean, FloatBuffer paramFloatBuffer)
  {
    GLES20.glUniformMatrix4fv(paramInt1, paramInt2, paramBoolean, paramFloatBuffer);
  }
  
  public void unmapBuffer(int paramInt) {}
  
  protected void update()
  {
    label83:
    int i;
    label92:
    int j;
    label105:
    int k;
    int m;
    int n;
    if (!fboLayerCreated)
    {
      String str = getString(7939);
      if (-1 >= str.indexOf("texture_non_power_of_two")) {
        break label469;
      }
      fboWidth = this.pg.width;
      fboHeight = this.pg.height;
      getIntegerv(-1, this.intBuffer);
      if ((-1 >= str.indexOf("_framebuffer_multisample")) || (1 >= this.intBuffer.get(0))) {
        break label498;
      }
      numSamples = reqNumSamples;
      if (1 >= numSamples) {
        break label505;
      }
      i = 1;
      if (str.indexOf("packed_depth_stencil") == -1) {
        break label510;
      }
      j = 1;
      k = getDepthBits();
      m = getStencilBits();
      genTextures(2, glColorTex);
      n = 0;
      label128:
      if (n < 2) {
        break label515;
      }
      bindTexture(3553, 0);
      backTex = 0;
      frontTex = 1;
      genFramebuffers(1, glColorFbo);
      bindFramebuffer(36160, glColorFbo.get(0));
      framebufferTexture2D(36160, 36064, 3553, glColorTex.get(backTex), 0);
      if (i != 0)
      {
        genFramebuffers(1, glMultiFbo);
        bindFramebuffer(36160, glMultiFbo.get(0));
        genRenderbuffers(1, glColorBuf);
        bindRenderbuffer(36161, glColorBuf.get(0));
        renderbufferStorageMultisample(36161, numSamples, -1, fboWidth, fboHeight);
        framebufferRenderbuffer(36160, 36064, 36161, glColorBuf.get(0));
      }
      if ((j == 0) || (k != 24) || (m != 8)) {
        break label651;
      }
      genRenderbuffers(1, glDepthStencil);
      bindRenderbuffer(36161, glDepthStencil.get(0));
      if (i == 0) {
        break label634;
      }
      renderbufferStorageMultisample(36161, numSamples, 35056, fboWidth, fboHeight);
      label331:
      framebufferRenderbuffer(36160, 36096, 36161, glDepthStencil.get(0));
      framebufferRenderbuffer(36160, 36128, 36161, glDepthStencil.get(0));
    }
    label469:
    label498:
    label505:
    label510:
    int i3;
    label515:
    label634:
    do
    {
      validateFramebuffer();
      clearDepth(1.0F);
      clearStencil(0);
      int i2 = this.pg.backgroundColor;
      float f = (0xFF & i2 >> 24) / 255.0F;
      clearColor((0xFF & i2 >> 16) / 255.0F, (0xFF & i2 >> 8) / 255.0F, (i2 & 0xFF) / 255.0F, f);
      clear(17664);
      bindFramebuffer(36160, 0);
      fboLayerCreated = true;
      return;
      fboWidth = nextPowerOfTwo(this.pg.width);
      fboHeight = nextPowerOfTwo(this.pg.height);
      break;
      numSamples = 1;
      break label83;
      i = 0;
      break label92;
      j = 0;
      break label105;
      bindTexture(3553, glColorTex.get(n));
      texParameteri(3553, 10241, 9728);
      texParameteri(3553, 10240, 9728);
      texParameteri(3553, 10242, 33071);
      texParameteri(3553, 10243, 33071);
      texImage2D(3553, 0, 6408, fboWidth, fboHeight, 0, 6408, 5121, null);
      initTexture(3553, 6408, fboWidth, fboHeight, this.pg.backgroundColor);
      n++;
      break label128;
      renderbufferStorage(36161, 35056, fboWidth, fboHeight);
      break label331;
      if (k > 0)
      {
        i3 = 33189;
        if (k != 32) {
          break label812;
        }
        i3 = 33191;
        genRenderbuffers(1, glDepth);
        bindRenderbuffer(36161, glDepth.get(0));
        if (i == 0) {
          break label840;
        }
        renderbufferStorageMultisample(36161, numSamples, i3, fboWidth, fboHeight);
        framebufferRenderbuffer(36160, 36096, 36161, glDepth.get(0));
      }
    } while (m <= 0);
    label651:
    label671:
    label713:
    int i1 = 36166;
    if (m == 8)
    {
      i1 = 36168;
      label750:
      genRenderbuffers(1, glStencil);
      bindRenderbuffer(36161, glStencil.get(0));
      if (i == 0) {
        break label883;
      }
      renderbufferStorageMultisample(36161, numSamples, i1, fboWidth, fboHeight);
    }
    for (;;)
    {
      framebufferRenderbuffer(36160, 36128, 36161, glStencil.get(0));
      break;
      label812:
      if (k == 24)
      {
        i3 = 33190;
        break label671;
      }
      if (k != 16) {
        break label671;
      }
      i3 = 33189;
      break label671;
      label840:
      renderbufferStorage(36161, i3, fboWidth, fboHeight);
      break label713;
      if (m == 4)
      {
        i1 = 36167;
        break label750;
      }
      if (m != 1) {
        break label750;
      }
      i1 = 36166;
      break label750;
      label883:
      renderbufferStorage(36161, i1, fboWidth, fboHeight);
    }
  }
  
  public void useProgram(int paramInt)
  {
    GLES20.glUseProgram(paramInt);
  }
  
  protected boolean validateFramebuffer()
  {
    int i = checkFramebufferStatus(36160);
    if (i == 36053) {
      return true;
    }
    if (i == 36054) {
      System.err.println(String.format("Framebuffer error (%1$s), rendering will probably not work as expected", new Object[] { "incomplete attachment" }));
    }
    for (;;)
    {
      return false;
      if (i == 36055) {
        System.err.println(String.format("Framebuffer error (%1$s), rendering will probably not work as expected", new Object[] { "incomplete missing attachment" }));
      } else if (i == 36057) {
        System.err.println(String.format("Framebuffer error (%1$s), rendering will probably not work as expected", new Object[] { "incomplete dimensions" }));
      } else if (i == 36058) {
        System.err.println(String.format("Framebuffer error (%1$s), rendering will probably not work as expected", new Object[] { "incomplete formats" }));
      } else if (i == 36061) {
        System.err.println(String.format("Framebuffer error (%1$s), rendering will probably not work as expected", new Object[] { "framebuffer unsupported" }));
      } else {
        System.err.println(String.format("Framebuffer error (%1$s), rendering will probably not work as expected", new Object[] { "unknown error" }));
      }
    }
  }
  
  public void validateProgram(int paramInt)
  {
    GLES20.glValidateProgram(paramInt);
  }
  
  public void vertexAttri4fv(int paramInt, FloatBuffer paramFloatBuffer)
  {
    GLES20.glVertexAttrib4fv(paramInt, paramFloatBuffer);
  }
  
  public void vertexAttrib1f(int paramInt, float paramFloat)
  {
    GLES20.glVertexAttrib1f(paramInt, paramFloat);
  }
  
  public void vertexAttrib1fv(int paramInt, FloatBuffer paramFloatBuffer)
  {
    GLES20.glVertexAttrib1fv(paramInt, paramFloatBuffer);
  }
  
  public void vertexAttrib2f(int paramInt, float paramFloat1, float paramFloat2)
  {
    GLES20.glVertexAttrib2f(paramInt, paramFloat1, paramFloat2);
  }
  
  public void vertexAttrib2fv(int paramInt, FloatBuffer paramFloatBuffer)
  {
    GLES20.glVertexAttrib2fv(paramInt, paramFloatBuffer);
  }
  
  public void vertexAttrib3f(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    GLES20.glVertexAttrib3f(paramInt, paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void vertexAttrib3fv(int paramInt, FloatBuffer paramFloatBuffer)
  {
    GLES20.glVertexAttrib3fv(paramInt, paramFloatBuffer);
  }
  
  public void vertexAttrib4f(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    GLES20.glVertexAttrib4f(paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void vertexAttribPointer(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, int paramInt5)
  {
    GLES20.glVertexAttribPointer(paramInt1, paramInt2, paramInt3, paramBoolean, paramInt4, paramInt5);
  }
  
  public void vertexAttribPointer(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, Buffer paramBuffer)
  {
    GLES20.glVertexAttribPointer(paramInt1, paramInt2, paramInt3, paramBoolean, paramInt4, paramBuffer);
  }
  
  public void viewport(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    GLES20.glViewport(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected Texture wrapBackTexture()
  {
    Texture localTexture = new Texture(this.pg.parent);
    localTexture.init(this.pg.width, this.pg.height, glColorTex.get(backTex), 3553, 6408, fboWidth, fboHeight, 9728, 9728, 33071, 33071);
    localTexture.invertedY(true);
    localTexture.colorBufferOf(this.pg);
    this.pg.setCache(this.pg, localTexture);
    return localTexture;
  }
  
  protected Texture wrapFrontTexture()
  {
    Texture localTexture = new Texture(this.pg.parent);
    localTexture.init(this.pg.width, this.pg.height, glColorTex.get(frontTex), 3553, 6408, fboWidth, fboHeight, 9728, 9728, 33071, 33071);
    localTexture.invertedY(true);
    localTexture.colorBufferOf(this.pg);
    return localTexture;
  }
  
  protected class AndroidConfigChooser
    implements GLSurfaceView.EGLConfigChooser
  {
    public int alphaBits;
    public int alphaTarget;
    public int blueBits;
    public int blueTarget;
    protected int[] configAttribsGL = { 12324, 4, 12323, 4, 12322, 4, 12352, 4, 12344 };
    public int depthBits;
    public int depthTarget;
    public int greenBits;
    public int greenTarget;
    public int redBits;
    public int redTarget;
    public int stencilBits;
    public int stencilTarget;
    public int[] tempValue = new int[1];
    
    public AndroidConfigChooser(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
    {
      this.redTarget = paramInt1;
      this.greenTarget = paramInt2;
      this.blueTarget = paramInt3;
      this.alphaTarget = paramInt4;
      this.depthTarget = paramInt5;
      this.stencilTarget = paramInt6;
    }
    
    public EGLConfig chooseBestConfig(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig[] paramArrayOfEGLConfig)
    {
      Object localObject = null;
      float f1 = 1000.0F;
      int i = paramArrayOfEGLConfig.length;
      for (int j = 0;; j++)
      {
        if (j >= i) {
          return localObject;
        }
        EGLConfig localEGLConfig = paramArrayOfEGLConfig[j];
        if (findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12352, 0) == 4)
        {
          int k = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12325, 0);
          int m = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12326, 0);
          int n = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12324, 0);
          int i1 = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12323, 0);
          int i2 = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12322, 0);
          int i3 = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12321, 0);
          float f2 = 0.2F * PApplet.abs(n - this.redTarget) + 0.2F * PApplet.abs(i1 - this.greenTarget) + 0.2F * PApplet.abs(i2 - this.blueTarget) + 0.15F * PApplet.abs(i3 - this.blueTarget) + 0.15F * PApplet.abs(k - this.depthTarget) + 0.1F * PApplet.abs(m - this.stencilTarget);
          if (f2 < f1)
          {
            localObject = localEGLConfig;
            f1 = f2;
            this.redBits = n;
            this.greenBits = i1;
            this.blueBits = i2;
            this.alphaBits = i3;
            this.depthBits = k;
            this.stencilBits = m;
          }
        }
      }
    }
    
    public EGLConfig chooseConfig(EGL10 paramEGL10, EGLDisplay paramEGLDisplay)
    {
      int[] arrayOfInt = new int[1];
      paramEGL10.eglChooseConfig(paramEGLDisplay, this.configAttribsGL, null, 0, arrayOfInt);
      int i = arrayOfInt[0];
      if (i <= 0) {
        throw new IllegalArgumentException("No EGL configs match configSpec");
      }
      EGLConfig[] arrayOfEGLConfig = new EGLConfig[i];
      paramEGL10.eglChooseConfig(paramEGLDisplay, this.configAttribsGL, arrayOfEGLConfig, i, arrayOfInt);
      return chooseBestConfig(paramEGL10, paramEGLDisplay, arrayOfEGLConfig);
    }
    
    protected int findConfigAttrib(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig, int paramInt1, int paramInt2)
    {
      if (paramEGL10.eglGetConfigAttrib(paramEGLDisplay, paramEGLConfig, paramInt1, this.tempValue)) {
        paramInt2 = this.tempValue[0];
      }
      return paramInt2;
    }
    
    protected String printConfig(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig)
    {
      int i = findConfigAttrib(paramEGL10, paramEGLDisplay, paramEGLConfig, 12324, 0);
      int j = findConfigAttrib(paramEGL10, paramEGLDisplay, paramEGLConfig, 12323, 0);
      int k = findConfigAttrib(paramEGL10, paramEGLDisplay, paramEGLConfig, 12322, 0);
      int m = findConfigAttrib(paramEGL10, paramEGLDisplay, paramEGLConfig, 12321, 0);
      int n = findConfigAttrib(paramEGL10, paramEGLDisplay, paramEGLConfig, 12325, 0);
      int i1 = findConfigAttrib(paramEGL10, paramEGLDisplay, paramEGLConfig, 12326, 0);
      int i2 = findConfigAttrib(paramEGL10, paramEGLDisplay, paramEGLConfig, 12352, 0);
      int i3 = findConfigAttrib(paramEGL10, paramEGLDisplay, paramEGLConfig, 12333, 0);
      int i4 = findConfigAttrib(paramEGL10, paramEGLDisplay, paramEGLConfig, 12320, 0);
      int i5 = findConfigAttrib(paramEGL10, paramEGLDisplay, paramEGLConfig, 12422, 0);
      Object[] arrayOfObject1 = new Object[6];
      arrayOfObject1[0] = Integer.valueOf(i);
      arrayOfObject1[1] = Integer.valueOf(j);
      arrayOfObject1[2] = Integer.valueOf(k);
      arrayOfObject1[3] = Integer.valueOf(m);
      arrayOfObject1[4] = Integer.valueOf(n);
      arrayOfObject1[5] = Integer.valueOf(i1);
      StringBuilder localStringBuilder = new StringBuilder(String.valueOf(String.format("EGLConfig rgba=%d%d%d%d depth=%d stencil=%d", arrayOfObject1))).append(" type=").append(i2).append(" native=").append(i3).append(" buffer size=").append(i4).append(" buffer surface=").append(i5);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(findConfigAttrib(paramEGL10, paramEGLDisplay, paramEGLConfig, 12327, 0));
      return String.format(" caveat=0x%04x", arrayOfObject2);
    }
  }
  
  protected class AndroidContextFactory
    implements GLSurfaceView.EGLContextFactory
  {
    protected AndroidContextFactory() {}
    
    public EGLContext createContext(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig)
    {
      int[] arrayOfInt = { 12440, 2, 12344 };
      return paramEGL10.eglCreateContext(paramEGLDisplay, paramEGLConfig, EGL10.EGL_NO_CONTEXT, arrayOfInt);
    }
    
    public void destroyContext(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLContext paramEGLContext)
    {
      paramEGL10.eglDestroyContext(paramEGLDisplay, paramEGLContext);
    }
  }
  
  protected class AndroidRenderer
    implements GLSurfaceView.Renderer
  {
    public AndroidRenderer() {}
    
    public void onDrawFrame(GL10 paramGL10)
    {
      PGL.gl = paramGL10;
      PGL.glThread = Thread.currentThread();
      PGL.this.pg.parent.handleDraw();
    }
    
    public void onSurfaceChanged(GL10 paramGL10, int paramInt1, int paramInt2)
    {
      PGL.gl = paramGL10;
      PGL.this.pg.setSize(paramInt1, paramInt2);
    }
    
    public void onSurfaceCreated(GL10 paramGL10, EGLConfig paramEGLConfig)
    {
      PGL.gl = paramGL10;
      PGL.context = ((EGL10)EGLContext.getEGL()).eglGetCurrentContext();
    }
  }
  
  protected class Tessellator
  {
    protected PGL.TessellatorCallback callback;
    protected GLUCallback gluCallback;
    protected PGLUtessellator tess;
    
    public Tessellator(PGL.TessellatorCallback paramTessellatorCallback)
    {
      this.callback = paramTessellatorCallback;
      this.tess = PGLU.gluNewTess();
      this.gluCallback = new GLUCallback();
      PGLU.gluTessCallback(this.tess, 100100, this.gluCallback);
      PGLU.gluTessCallback(this.tess, 100102, this.gluCallback);
      PGLU.gluTessCallback(this.tess, 100101, this.gluCallback);
      PGLU.gluTessCallback(this.tess, 100105, this.gluCallback);
      PGLU.gluTessCallback(this.tess, 100103, this.gluCallback);
    }
    
    public void addVertex(double[] paramArrayOfDouble)
    {
      PGLU.gluTessVertex(this.tess, paramArrayOfDouble, 0, paramArrayOfDouble);
    }
    
    public void beginContour()
    {
      PGLU.gluTessBeginContour(this.tess);
    }
    
    public void beginPolygon()
    {
      PGLU.gluTessBeginPolygon(this.tess, null);
    }
    
    public void endContour()
    {
      PGLU.gluTessEndContour(this.tess);
    }
    
    public void endPolygon()
    {
      PGLU.gluTessEndPolygon(this.tess);
    }
    
    public void setWindingRule(int paramInt)
    {
      PGLU.gluTessProperty(this.tess, 100140, paramInt);
    }
    
    protected class GLUCallback
      extends PGLUtessellatorCallbackAdapter
    {
      protected GLUCallback() {}
      
      public void begin(int paramInt)
      {
        PGL.Tessellator.this.callback.begin(paramInt);
      }
      
      public void combine(double[] paramArrayOfDouble, Object[] paramArrayOfObject1, float[] paramArrayOfFloat, Object[] paramArrayOfObject2)
      {
        PGL.Tessellator.this.callback.combine(paramArrayOfDouble, paramArrayOfObject1, paramArrayOfFloat, paramArrayOfObject2);
      }
      
      public void end()
      {
        PGL.Tessellator.this.callback.end();
      }
      
      public void error(int paramInt)
      {
        PGL.Tessellator.this.callback.error(paramInt);
      }
      
      public void vertex(Object paramObject)
      {
        PGL.Tessellator.this.callback.vertex(paramObject);
      }
    }
  }
  
  protected static abstract interface TessellatorCallback
  {
    public abstract void begin(int paramInt);
    
    public abstract void combine(double[] paramArrayOfDouble, Object[] paramArrayOfObject1, float[] paramArrayOfFloat, Object[] paramArrayOfObject2);
    
    public abstract void end();
    
    public abstract void error(int paramInt);
    
    public abstract void vertex(Object paramObject);
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.PGL
 * JD-Core Version:    0.7.0.1
 */