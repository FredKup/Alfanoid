package processing.core;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import processing.data.Table;
import processing.data.XML;
import processing.event.Event;
import processing.event.MouseEvent;
import processing.event.TouchEvent;
import processing.opengl.PGL;
import processing.opengl.PGraphics2D;
import processing.opengl.PGraphics3D;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PShader;

public class PApplet
  extends Activity
  implements PConstants, Runnable
{
  public static final String ARGS_BGCOLOR = "--bgcolor";
  public static final String ARGS_DISPLAY = "--display";
  public static final String ARGS_EDITOR_LOCATION = "--editor-location";
  public static final String ARGS_EXCLUSIVE = "--exclusive";
  public static final String ARGS_EXTERNAL = "--external";
  public static final String ARGS_HIDE_STOP = "--hide-stop";
  public static final String ARGS_LOCATION = "--location";
  public static final String ARGS_PRESENT = "--present";
  public static final String ARGS_SKETCH_FOLDER = "--sketch-path";
  public static final String ARGS_STOP_COLOR = "--stop-color";
  public static final boolean DEBUG = false;
  static final String ERROR_MIN_MAX = "Cannot use min() or max() on an empty array.";
  public static final String EXTERNAL_MOVE = "__MOVE__";
  public static final String EXTERNAL_STOP = "__STOP__";
  public static final byte[] ICON_IMAGE;
  static final int META_CTRL_ON = 4096;
  static final int META_META_ON = 65536;
  static final int PERLIN_SIZE = 4095;
  static final int PERLIN_YWRAP = 16;
  static final int PERLIN_YWRAPB = 4;
  static final int PERLIN_ZWRAP = 256;
  static final int PERLIN_ZWRAPB = 8;
  private static NumberFormat float_nf;
  private static boolean float_nf_commas;
  private static int float_nf_left;
  private static int float_nf_right;
  private static NumberFormat int_nf;
  private static boolean int_nf_commas;
  private static int int_nf_digits;
  protected static HashMap<String, Pattern> matchPatterns;
  protected static Time time = new Time();
  public int displayHeight;
  public int displayWidth;
  protected int dmouseX;
  protected int dmouseY;
  protected int emouseX;
  protected int emouseY;
  InternalEventQueue eventQueue = new InternalEventQueue();
  protected boolean exitCalled;
  boolean external = false;
  public boolean finished;
  public boolean focused = false;
  public int frameCount;
  public float frameRate = 10.0F;
  protected long frameRateLastNanos = 0L;
  protected long frameRatePeriod = 16666666L;
  protected float frameRateTarget = 60.0F;
  public PGraphics g;
  Handler handler;
  public int height;
  Random internalRandom;
  public char key;
  public int keyCode;
  public boolean keyPressed;
  protected boolean looping;
  long millisOffset = System.currentTimeMillis();
  int motionPointerId;
  public boolean mousePressed;
  public int mouseX;
  public int mouseY;
  protected boolean paused;
  float[] perlin;
  Random perlinRandom;
  int perlin_PI;
  int perlin_TWOPI;
  float perlin_amp_falloff = 0.5F;
  float[] perlin_cosTable;
  int perlin_octaves = 4;
  public int[] pixels;
  public int pmouseX;
  public int pmouseY;
  protected boolean redraw;
  HashMap<String, RegisteredMethods> registerMap = new HashMap();
  volatile int requestImageCount;
  public int requestImageMax = 4;
  public String sketchPath;
  protected boolean surfaceChanged;
  protected boolean surfaceReady;
  protected SurfaceView surfaceView;
  Thread thread;
  protected boolean viewFocused = false;
  public int width;
  protected boolean windowFocused = false;
  
  static
  {
    byte[] arrayOfByte = new byte['Í'];
    arrayOfByte[0] = 71;
    arrayOfByte[1] = 73;
    arrayOfByte[2] = 70;
    arrayOfByte[3] = 56;
    arrayOfByte[4] = 57;
    arrayOfByte[5] = 97;
    arrayOfByte[6] = 16;
    arrayOfByte[8] = 16;
    arrayOfByte[10] = -77;
    arrayOfByte[16] = -1;
    arrayOfByte[17] = -1;
    arrayOfByte[18] = -1;
    arrayOfByte[19] = 12;
    arrayOfByte[20] = 12;
    arrayOfByte[21] = 13;
    arrayOfByte[22] = -15;
    arrayOfByte[23] = -15;
    arrayOfByte[24] = -14;
    arrayOfByte[25] = 45;
    arrayOfByte[26] = 57;
    arrayOfByte[27] = 74;
    arrayOfByte[28] = 54;
    arrayOfByte[29] = 80;
    arrayOfByte[30] = 111;
    arrayOfByte[31] = 47;
    arrayOfByte[32] = 71;
    arrayOfByte[33] = 97;
    arrayOfByte[34] = 62;
    arrayOfByte[35] = 88;
    arrayOfByte[36] = 117;
    arrayOfByte[37] = 1;
    arrayOfByte[38] = 14;
    arrayOfByte[39] = 27;
    arrayOfByte[40] = 7;
    arrayOfByte[41] = 41;
    arrayOfByte[42] = 73;
    arrayOfByte[43] = 15;
    arrayOfByte[44] = 52;
    arrayOfByte[45] = 85;
    arrayOfByte[46] = 2;
    arrayOfByte[47] = 31;
    arrayOfByte[48] = 55;
    arrayOfByte[49] = 4;
    arrayOfByte[50] = 54;
    arrayOfByte[51] = 94;
    arrayOfByte[52] = 18;
    arrayOfByte[53] = 69;
    arrayOfByte[54] = 109;
    arrayOfByte[55] = 37;
    arrayOfByte[56] = 87;
    arrayOfByte[57] = 126;
    arrayOfByte[58] = -1;
    arrayOfByte[59] = -1;
    arrayOfByte[60] = -1;
    arrayOfByte[61] = 33;
    arrayOfByte[62] = -7;
    arrayOfByte[63] = 4;
    arrayOfByte[64] = 1;
    arrayOfByte[67] = 15;
    arrayOfByte[69] = 44;
    arrayOfByte[74] = 16;
    arrayOfByte[76] = 16;
    arrayOfByte[79] = 4;
    arrayOfByte[80] = 122;
    arrayOfByte[81] = -16;
    arrayOfByte[82] = -107;
    arrayOfByte[83] = 114;
    arrayOfByte[84] = -86;
    arrayOfByte[85] = -67;
    arrayOfByte[86] = 83;
    arrayOfByte[87] = 30;
    arrayOfByte[88] = -42;
    arrayOfByte[89] = 26;
    arrayOfByte[90] = -17;
    arrayOfByte[91] = -100;
    arrayOfByte[92] = -45;
    arrayOfByte[93] = 56;
    arrayOfByte[94] = -57;
    arrayOfByte[95] = -108;
    arrayOfByte[96] = 48;
    arrayOfByte[97] = 40;
    arrayOfByte[98] = 122;
    arrayOfByte[99] = -90;
    arrayOfByte[100] = 104;
    arrayOfByte[101] = 67;
    arrayOfByte[102] = -91;
    arrayOfByte[103] = -51;
    arrayOfByte[104] = 32;
    arrayOfByte[105] = -53;
    arrayOfByte[106] = 77;
    arrayOfByte[107] = -78;
    arrayOfByte[108] = -100;
    arrayOfByte[109] = 47;
    arrayOfByte[110] = -86;
    arrayOfByte[111] = 12;
    arrayOfByte[112] = 76;
    arrayOfByte[113] = -110;
    arrayOfByte[114] = -20;
    arrayOfByte[115] = -74;
    arrayOfByte[116] = -101;
    arrayOfByte[117] = 97;
    arrayOfByte[118] = -93;
    arrayOfByte[119] = 27;
    arrayOfByte[120] = 40;
    arrayOfByte[121] = 20;
    arrayOfByte[122] = -65;
    arrayOfByte[123] = 65;
    arrayOfByte[124] = 48;
    arrayOfByte[125] = -111;
    arrayOfByte[126] = 99;
    arrayOfByte[127] = -20;
    arrayOfByte[''] = -112;
    arrayOfByte[''] = -117;
    arrayOfByte[''] = -123;
    arrayOfByte[''] = -47;
    arrayOfByte[''] = -105;
    arrayOfByte[''] = 24;
    arrayOfByte[''] = 114;
    arrayOfByte[''] = -112;
    arrayOfByte[''] = 74;
    arrayOfByte[''] = 69;
    arrayOfByte[''] = 84;
    arrayOfByte[''] = 25;
    arrayOfByte[''] = 93;
    arrayOfByte[''] = 88;
    arrayOfByte[''] = -75;
    arrayOfByte[''] = 9;
    arrayOfByte[''] = 46;
    arrayOfByte[''] = 2;
    arrayOfByte[''] = 49;
    arrayOfByte[''] = 88;
    arrayOfByte[''] = -116;
    arrayOfByte[''] = -67;
    arrayOfByte[''] = 7;
    arrayOfByte[''] = -19;
    arrayOfByte[''] = -83;
    arrayOfByte[''] = 60;
    arrayOfByte[''] = 38;
    arrayOfByte[''] = 3;
    arrayOfByte[''] = -34;
    arrayOfByte[''] = 2;
    arrayOfByte[''] = 66;
    arrayOfByte[''] = -95;
    arrayOfByte[' '] = 27;
    arrayOfByte['¡'] = -98;
    arrayOfByte['¢'] = 13;
    arrayOfByte['£'] = 4;
    arrayOfByte['¤'] = -17;
    arrayOfByte['¥'] = 55;
    arrayOfByte['¦'] = 33;
    arrayOfByte['§'] = 109;
    arrayOfByte['¨'] = 11;
    arrayOfByte['©'] = 11;
    arrayOfByte['ª'] = -2;
    arrayOfByte['«'] = -128;
    arrayOfByte['¬'] = 121;
    arrayOfByte['­'] = 123;
    arrayOfByte['®'] = 62;
    arrayOfByte['¯'] = 91;
    arrayOfByte['°'] = 120;
    arrayOfByte['±'] = -128;
    arrayOfByte['²'] = 127;
    arrayOfByte['³'] = 122;
    arrayOfByte['´'] = 115;
    arrayOfByte['µ'] = 102;
    arrayOfByte['¶'] = 2;
    arrayOfByte['·'] = 119;
    arrayOfByte['¹'] = -116;
    arrayOfByte['º'] = -113;
    arrayOfByte['»'] = -119;
    arrayOfByte['¼'] = 6;
    arrayOfByte['½'] = 102;
    arrayOfByte['¾'] = 121;
    arrayOfByte['¿'] = -108;
    arrayOfByte['À'] = -126;
    arrayOfByte['Á'] = 5;
    arrayOfByte['Â'] = 18;
    arrayOfByte['Ã'] = 6;
    arrayOfByte['Ä'] = 4;
    arrayOfByte['Å'] = -102;
    arrayOfByte['Æ'] = -101;
    arrayOfByte['Ç'] = -100;
    arrayOfByte['È'] = 114;
    arrayOfByte['É'] = 15;
    arrayOfByte['Ê'] = 17;
    arrayOfByte['Ì'] = 59;
    ICON_IMAGE = arrayOfByte;
  }
  
  public static final float abs(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      paramFloat = -paramFloat;
    }
    return paramFloat;
  }
  
  public static final int abs(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = -paramInt;
    }
    return paramInt;
  }
  
  public static final float acos(float paramFloat)
  {
    return (float)Math.acos(paramFloat);
  }
  
  public static Object append(Object paramObject1, Object paramObject2)
  {
    int i = Array.getLength(paramObject1);
    Object localObject = expand(paramObject1, i + 1);
    Array.set(localObject, i, paramObject2);
    return localObject;
  }
  
  public static byte[] append(byte[] paramArrayOfByte, byte paramByte)
  {
    byte[] arrayOfByte = expand(paramArrayOfByte, 1 + paramArrayOfByte.length);
    arrayOfByte[(-1 + arrayOfByte.length)] = paramByte;
    return arrayOfByte;
  }
  
  public static char[] append(char[] paramArrayOfChar, char paramChar)
  {
    char[] arrayOfChar = expand(paramArrayOfChar, 1 + paramArrayOfChar.length);
    arrayOfChar[(-1 + arrayOfChar.length)] = paramChar;
    return arrayOfChar;
  }
  
  public static float[] append(float[] paramArrayOfFloat, float paramFloat)
  {
    float[] arrayOfFloat = expand(paramArrayOfFloat, 1 + paramArrayOfFloat.length);
    arrayOfFloat[(-1 + arrayOfFloat.length)] = paramFloat;
    return arrayOfFloat;
  }
  
  public static int[] append(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = expand(paramArrayOfInt, 1 + paramArrayOfInt.length);
    arrayOfInt[(-1 + arrayOfInt.length)] = paramInt;
    return arrayOfInt;
  }
  
  public static String[] append(String[] paramArrayOfString, String paramString)
  {
    String[] arrayOfString = expand(paramArrayOfString, 1 + paramArrayOfString.length);
    arrayOfString[(-1 + arrayOfString.length)] = paramString;
    return arrayOfString;
  }
  
  public static void arrayCopy(Object paramObject1, int paramInt1, Object paramObject2, int paramInt2, int paramInt3)
  {
    System.arraycopy(paramObject1, paramInt1, paramObject2, paramInt2, paramInt3);
  }
  
  public static void arrayCopy(Object paramObject1, Object paramObject2)
  {
    System.arraycopy(paramObject1, 0, paramObject2, 0, Array.getLength(paramObject1));
  }
  
  public static void arrayCopy(Object paramObject1, Object paramObject2, int paramInt)
  {
    System.arraycopy(paramObject1, 0, paramObject2, 0, paramInt);
  }
  
  public static final float asin(float paramFloat)
  {
    return (float)Math.asin(paramFloat);
  }
  
  public static final float atan(float paramFloat)
  {
    return (float)Math.atan(paramFloat);
  }
  
  public static final float atan2(float paramFloat1, float paramFloat2)
  {
    return (float)Math.atan2(paramFloat1, paramFloat2);
  }
  
  public static final String binary(byte paramByte)
  {
    return binary(paramByte, 8);
  }
  
  public static final String binary(char paramChar)
  {
    return binary(paramChar, 16);
  }
  
  public static final String binary(int paramInt)
  {
    return binary(paramInt, 32);
  }
  
  public static final String binary(int paramInt1, int paramInt2)
  {
    String str = Integer.toBinaryString(paramInt1);
    if (paramInt2 > 32) {
      paramInt2 = 32;
    }
    int i = str.length();
    if (i > paramInt2) {
      str = str.substring(i - paramInt2);
    }
    while (i >= paramInt2) {
      return str;
    }
    return "00000000000000000000000000000000".substring(32 - (paramInt2 - i)) + str;
  }
  
  public static int blendColor(int paramInt1, int paramInt2, int paramInt3)
  {
    return PImage.blendColor(paramInt1, paramInt2, paramInt3);
  }
  
  public static final int ceil(float paramFloat)
  {
    return (int)Math.ceil(paramFloat);
  }
  
  public static Object concat(Object paramObject1, Object paramObject2)
  {
    Class localClass = paramObject1.getClass().getComponentType();
    int i = Array.getLength(paramObject1);
    int j = Array.getLength(paramObject2);
    Object localObject = Array.newInstance(localClass, i + j);
    System.arraycopy(paramObject1, 0, localObject, 0, i);
    System.arraycopy(paramObject2, 0, localObject, i, j);
    return localObject;
  }
  
  public static byte[] concat(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
    System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
    System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, paramArrayOfByte1.length, paramArrayOfByte2.length);
    return arrayOfByte;
  }
  
  public static char[] concat(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
  {
    char[] arrayOfChar = new char[paramArrayOfChar1.length + paramArrayOfChar2.length];
    System.arraycopy(paramArrayOfChar1, 0, arrayOfChar, 0, paramArrayOfChar1.length);
    System.arraycopy(paramArrayOfChar2, 0, arrayOfChar, paramArrayOfChar1.length, paramArrayOfChar2.length);
    return arrayOfChar;
  }
  
  public static float[] concat(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float[] arrayOfFloat = new float[paramArrayOfFloat1.length + paramArrayOfFloat2.length];
    System.arraycopy(paramArrayOfFloat1, 0, arrayOfFloat, 0, paramArrayOfFloat1.length);
    System.arraycopy(paramArrayOfFloat2, 0, arrayOfFloat, paramArrayOfFloat1.length, paramArrayOfFloat2.length);
    return arrayOfFloat;
  }
  
  public static int[] concat(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int[] arrayOfInt = new int[paramArrayOfInt1.length + paramArrayOfInt2.length];
    System.arraycopy(paramArrayOfInt1, 0, arrayOfInt, 0, paramArrayOfInt1.length);
    System.arraycopy(paramArrayOfInt2, 0, arrayOfInt, paramArrayOfInt1.length, paramArrayOfInt2.length);
    return arrayOfInt;
  }
  
  public static String[] concat(String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    String[] arrayOfString = new String[paramArrayOfString1.length + paramArrayOfString2.length];
    System.arraycopy(paramArrayOfString1, 0, arrayOfString, 0, paramArrayOfString1.length);
    System.arraycopy(paramArrayOfString2, 0, arrayOfString, paramArrayOfString1.length, paramArrayOfString2.length);
    return arrayOfString;
  }
  
  public static boolean[] concat(boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2)
  {
    boolean[] arrayOfBoolean = new boolean[paramArrayOfBoolean1.length + paramArrayOfBoolean2.length];
    System.arraycopy(paramArrayOfBoolean1, 0, arrayOfBoolean, 0, paramArrayOfBoolean1.length);
    System.arraycopy(paramArrayOfBoolean2, 0, arrayOfBoolean, paramArrayOfBoolean1.length, paramArrayOfBoolean2.length);
    return arrayOfBoolean;
  }
  
  public static final float constrain(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 < paramFloat2) {
      return paramFloat2;
    }
    if (paramFloat1 > paramFloat3) {
      return paramFloat3;
    }
    return paramFloat1;
  }
  
  public static final int constrain(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 < paramInt2) {
      return paramInt2;
    }
    if (paramInt1 > paramInt3) {
      return paramInt3;
    }
    return paramInt1;
  }
  
  public static final float cos(float paramFloat)
  {
    return (float)Math.cos(paramFloat);
  }
  
  public static InputStream createInput(File paramFile)
  {
    if (paramFile == null) {
      throw new IllegalArgumentException("File passed to createInput() was null");
    }
    try
    {
      Object localObject = new FileInputStream(paramFile);
      if (paramFile.getName().toLowerCase().endsWith(".gz"))
      {
        GZIPInputStream localGZIPInputStream = new GZIPInputStream((InputStream)localObject);
        localObject = localGZIPInputStream;
      }
      return localObject;
    }
    catch (IOException localIOException)
    {
      System.err.println("Could not createInput() for " + paramFile);
      localIOException.printStackTrace();
    }
    return null;
  }
  
  public static void createPath(File paramFile)
  {
    try
    {
      String str = paramFile.getParent();
      if (str != null)
      {
        File localFile = new File(str);
        if (!localFile.exists()) {
          localFile.mkdirs();
        }
      }
      return;
    }
    catch (SecurityException localSecurityException)
    {
      System.err.println("You don't have permissions to create " + paramFile.getAbsolutePath());
    }
  }
  
  public static void createPath(String paramString)
  {
    createPath(new File(paramString));
  }
  
  public static BufferedReader createReader(File paramFile)
  {
    try
    {
      Object localObject = new FileInputStream(paramFile);
      if (paramFile.getName().toLowerCase().endsWith(".gz")) {
        localObject = new GZIPInputStream((InputStream)localObject);
      }
      BufferedReader localBufferedReader = createReader((InputStream)localObject);
      return localBufferedReader;
    }
    catch (Exception localException)
    {
      if (paramFile == null) {
        throw new RuntimeException("File passed to createReader() was null");
      }
      localException.printStackTrace();
      throw new RuntimeException("Couldn't create a reader for " + paramFile.getAbsolutePath());
    }
  }
  
  public static BufferedReader createReader(InputStream paramInputStream)
  {
    try
    {
      InputStreamReader localInputStreamReader1 = new InputStreamReader(paramInputStream, "UTF-8");
      localInputStreamReader2 = localInputStreamReader1;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        InputStreamReader localInputStreamReader2 = null;
      }
    }
    return new BufferedReader(localInputStreamReader2);
  }
  
  public static PrintWriter createWriter(File paramFile)
  {
    try
    {
      Object localObject = new FileOutputStream(paramFile);
      if (paramFile.getName().toLowerCase().endsWith(".gz")) {
        localObject = new GZIPOutputStream((OutputStream)localObject);
      }
      PrintWriter localPrintWriter = createWriter((OutputStream)localObject);
      return localPrintWriter;
    }
    catch (Exception localException)
    {
      if (paramFile == null) {
        throw new RuntimeException("File passed to createWriter() was null");
      }
      localException.printStackTrace();
      throw new RuntimeException("Couldn't create a writer for " + paramFile.getAbsolutePath());
    }
  }
  
  public static PrintWriter createWriter(OutputStream paramOutputStream)
  {
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(paramOutputStream, 8192), "UTF-8"));
      return localPrintWriter;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    return null;
  }
  
  public static int day()
  {
    time.setToNow();
    return time.monthDay;
  }
  
  public static final float degrees(float paramFloat)
  {
    return 57.295776F * paramFloat;
  }
  
  public static final float dist(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return sqrt(sq(paramFloat3 - paramFloat1) + sq(paramFloat4 - paramFloat2));
  }
  
  public static final float dist(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    return sqrt(sq(paramFloat4 - paramFloat1) + sq(paramFloat5 - paramFloat2) + sq(paramFloat6 - paramFloat3));
  }
  
  public static Process exec(String[] paramArrayOfString)
  {
    try
    {
      Process localProcess = Runtime.getRuntime().exec(paramArrayOfString);
      return localProcess;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new RuntimeException("Could not open " + join(paramArrayOfString, ' '));
    }
  }
  
  public static final float exp(float paramFloat)
  {
    return (float)Math.exp(paramFloat);
  }
  
  public static Object expand(Object paramObject)
  {
    return expand(paramObject, Array.getLength(paramObject) << 1);
  }
  
  public static Object expand(Object paramObject, int paramInt)
  {
    Object localObject = Array.newInstance(paramObject.getClass().getComponentType(), paramInt);
    System.arraycopy(paramObject, 0, localObject, 0, Math.min(Array.getLength(paramObject), paramInt));
    return localObject;
  }
  
  public static byte[] expand(byte[] paramArrayOfByte)
  {
    return expand(paramArrayOfByte, paramArrayOfByte.length << 1);
  }
  
  public static byte[] expand(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, Math.min(paramInt, paramArrayOfByte.length));
    return arrayOfByte;
  }
  
  public static char[] expand(char[] paramArrayOfChar)
  {
    return expand(paramArrayOfChar, paramArrayOfChar.length << 1);
  }
  
  public static char[] expand(char[] paramArrayOfChar, int paramInt)
  {
    char[] arrayOfChar = new char[paramInt];
    System.arraycopy(paramArrayOfChar, 0, arrayOfChar, 0, Math.min(paramInt, paramArrayOfChar.length));
    return arrayOfChar;
  }
  
  public static float[] expand(float[] paramArrayOfFloat)
  {
    return expand(paramArrayOfFloat, paramArrayOfFloat.length << 1);
  }
  
  public static float[] expand(float[] paramArrayOfFloat, int paramInt)
  {
    float[] arrayOfFloat = new float[paramInt];
    System.arraycopy(paramArrayOfFloat, 0, arrayOfFloat, 0, Math.min(paramInt, paramArrayOfFloat.length));
    return arrayOfFloat;
  }
  
  public static int[] expand(int[] paramArrayOfInt)
  {
    return expand(paramArrayOfInt, paramArrayOfInt.length << 1);
  }
  
  public static int[] expand(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = new int[paramInt];
    System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, Math.min(paramInt, paramArrayOfInt.length));
    return arrayOfInt;
  }
  
  public static String[] expand(String[] paramArrayOfString)
  {
    return expand(paramArrayOfString, paramArrayOfString.length << 1);
  }
  
  public static String[] expand(String[] paramArrayOfString, int paramInt)
  {
    String[] arrayOfString = new String[paramInt];
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, Math.min(paramInt, paramArrayOfString.length));
    return arrayOfString;
  }
  
  public static PImage[] expand(PImage[] paramArrayOfPImage)
  {
    return expand(paramArrayOfPImage, paramArrayOfPImage.length << 1);
  }
  
  public static PImage[] expand(PImage[] paramArrayOfPImage, int paramInt)
  {
    PImage[] arrayOfPImage = new PImage[paramInt];
    System.arraycopy(paramArrayOfPImage, 0, arrayOfPImage, 0, Math.min(paramInt, paramArrayOfPImage.length));
    return arrayOfPImage;
  }
  
  public static boolean[] expand(boolean[] paramArrayOfBoolean)
  {
    return expand(paramArrayOfBoolean, paramArrayOfBoolean.length << 1);
  }
  
  public static boolean[] expand(boolean[] paramArrayOfBoolean, int paramInt)
  {
    boolean[] arrayOfBoolean = new boolean[paramInt];
    System.arraycopy(paramArrayOfBoolean, 0, arrayOfBoolean, 0, Math.min(paramInt, paramArrayOfBoolean.length));
    return arrayOfBoolean;
  }
  
  public static final int floor(float paramFloat)
  {
    return (int)Math.floor(paramFloat);
  }
  
  public static String getExtension(String paramString)
  {
    String str1 = paramString.toLowerCase();
    int i = paramString.lastIndexOf('.');
    if (i == -1) {}
    String str2 = str1.substring(i + 1);
    int j = str2.indexOf('?');
    if (j != -1) {
      str2 = str2.substring(0, j);
    }
    return str2;
  }
  
  public static final String hex(byte paramByte)
  {
    return hex(paramByte, 2);
  }
  
  public static final String hex(char paramChar)
  {
    return hex(paramChar, 4);
  }
  
  public static final String hex(int paramInt)
  {
    return hex(paramInt, 8);
  }
  
  public static final String hex(int paramInt1, int paramInt2)
  {
    String str = Integer.toHexString(paramInt1).toUpperCase();
    if (paramInt2 > 8) {
      paramInt2 = 8;
    }
    int i = str.length();
    if (i > paramInt2) {
      str = str.substring(i - paramInt2);
    }
    while (i >= paramInt2) {
      return str;
    }
    return "00000000".substring(8 - (paramInt2 - i)) + str;
  }
  
  public static int hour()
  {
    time.setToNow();
    return time.hour;
  }
  
  public static String join(String[] paramArrayOfString, char paramChar)
  {
    return join(paramArrayOfString, String.valueOf(paramChar));
  }
  
  public static String join(String[] paramArrayOfString, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfString.length) {
        return localStringBuffer.toString();
      }
      if (i != 0) {
        localStringBuffer.append(paramString);
      }
      localStringBuffer.append(paramArrayOfString[i]);
    }
  }
  
  public static final float lerp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return paramFloat1 + paramFloat3 * (paramFloat2 - paramFloat1);
  }
  
  public static int lerpColor(int paramInt1, int paramInt2, float paramFloat, int paramInt3)
  {
    return PGraphics.lerpColor(paramInt1, paramInt2, paramFloat, paramInt3);
  }
  
  public static byte[] loadBytes(File paramFile)
  {
    return loadBytes(createInput(paramFile));
  }
  
  public static byte[] loadBytes(InputStream paramInputStream)
  {
    try
    {
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramInputStream);
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      int j;
      for (int i = localBufferedInputStream.read();; i = j)
      {
        if (i == -1) {
          return localByteArrayOutputStream.toByteArray();
        }
        localByteArrayOutputStream.write(i);
        j = localBufferedInputStream.read();
      }
      return null;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public static String[] loadStrings(File paramFile)
  {
    InputStream localInputStream = createInput(paramFile);
    if (localInputStream != null) {
      return loadStrings(localInputStream);
    }
    return null;
  }
  
  public static String[] loadStrings(InputStream paramInputStream)
  {
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, "UTF-8"));
      Object localObject = new String[100];
      int j;
      for (int i = 0;; i = j)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
        {
          localBufferedReader.close();
          if (i != localObject.length) {
            break;
          }
          return localObject;
        }
        if (i == localObject.length)
        {
          String[] arrayOfString2 = new String[i << 1];
          System.arraycopy(localObject, 0, arrayOfString2, 0, i);
          localObject = arrayOfString2;
        }
        j = i + 1;
        localObject[i] = str;
      }
      String[] arrayOfString1 = new String[i];
      System.arraycopy(localObject, 0, arrayOfString1, 0, i);
      return arrayOfString1;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }
  
  public static final float log(float paramFloat)
  {
    return (float)Math.log(paramFloat);
  }
  
  public static final float mag(float paramFloat1, float paramFloat2)
  {
    return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
  }
  
  public static final float mag(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 + paramFloat3 * paramFloat3);
  }
  
  public static void main(String[] paramArrayOfString) {}
  
  public static final float map(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    return paramFloat4 + (paramFloat5 - paramFloat4) * ((paramFloat1 - paramFloat2) / (paramFloat3 - paramFloat2));
  }
  
  public static String[] match(String paramString1, String paramString2)
  {
    Matcher localMatcher = matchPattern(paramString2).matcher(paramString1);
    if (localMatcher.find())
    {
      int i = 1 + localMatcher.groupCount();
      String[] arrayOfString = new String[i];
      for (int j = 0;; j++)
      {
        if (j >= i) {
          return arrayOfString;
        }
        arrayOfString[j] = localMatcher.group(j);
      }
    }
    return null;
  }
  
  public static String[][] matchAll(String paramString1, String paramString2)
  {
    Matcher localMatcher = matchPattern(paramString2).matcher(paramString1);
    ArrayList localArrayList = new ArrayList();
    int i = 1 + localMatcher.groupCount();
    String[][] arrayOfString1;
    if (!localMatcher.find())
    {
      if (!localArrayList.isEmpty()) {
        break label89;
      }
      arrayOfString1 = null;
    }
    for (;;)
    {
      return arrayOfString1;
      String[] arrayOfString = new String[i];
      for (int j = 0;; j++)
      {
        if (j >= i)
        {
          localArrayList.add(arrayOfString);
          break;
        }
        arrayOfString[j] = localMatcher.group(j);
      }
      label89:
      arrayOfString1 = (String[][])Array.newInstance(String.class, new int[] { localArrayList.size(), i });
      for (int k = 0; k < arrayOfString1.length; k++) {
        arrayOfString1[k] = ((String[])localArrayList.get(k));
      }
    }
  }
  
  static Pattern matchPattern(String paramString)
  {
    Pattern localPattern = null;
    if (matchPatterns == null) {
      matchPatterns = new HashMap();
    }
    for (;;)
    {
      if (localPattern == null)
      {
        if (matchPatterns.size() == 10) {
          matchPatterns.clear();
        }
        localPattern = Pattern.compile(paramString, 40);
        matchPatterns.put(paramString, localPattern);
      }
      return localPattern;
      localPattern = (Pattern)matchPatterns.get(paramString);
    }
  }
  
  public static final float max(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 > paramFloat2) {
      return paramFloat1;
    }
    return paramFloat2;
  }
  
  public static final float max(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 > paramFloat2) {
      if (paramFloat1 > paramFloat3) {
        paramFloat3 = paramFloat1;
      }
    }
    while (paramFloat2 <= paramFloat3) {
      return paramFloat3;
    }
    return paramFloat2;
  }
  
  public static final float max(float[] paramArrayOfFloat)
  {
    if (paramArrayOfFloat.length == 0) {
      throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
    }
    float f = paramArrayOfFloat[0];
    for (int i = 1;; i++)
    {
      if (i >= paramArrayOfFloat.length) {
        return f;
      }
      if (paramArrayOfFloat[i] > f) {
        f = paramArrayOfFloat[i];
      }
    }
  }
  
  public static final int max(int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) {
      return paramInt1;
    }
    return paramInt2;
  }
  
  public static final int max(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 > paramInt2) {
      if (paramInt1 > paramInt3) {
        paramInt3 = paramInt1;
      }
    }
    while (paramInt2 <= paramInt3) {
      return paramInt3;
    }
    return paramInt2;
  }
  
  public static final int max(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt.length == 0) {
      throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
    }
    int i = paramArrayOfInt[0];
    for (int j = 1;; j++)
    {
      if (j >= paramArrayOfInt.length) {
        return i;
      }
      if (paramArrayOfInt[j] > i) {
        i = paramArrayOfInt[j];
      }
    }
  }
  
  public static final float min(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 < paramFloat2) {
      return paramFloat1;
    }
    return paramFloat2;
  }
  
  public static final float min(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 < paramFloat2) {
      if (paramFloat1 < paramFloat3) {
        paramFloat3 = paramFloat1;
      }
    }
    while (paramFloat2 >= paramFloat3) {
      return paramFloat3;
    }
    return paramFloat2;
  }
  
  public static final float min(float[] paramArrayOfFloat)
  {
    if (paramArrayOfFloat.length == 0) {
      throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
    }
    float f = paramArrayOfFloat[0];
    for (int i = 1;; i++)
    {
      if (i >= paramArrayOfFloat.length) {
        return f;
      }
      if (paramArrayOfFloat[i] < f) {
        f = paramArrayOfFloat[i];
      }
    }
  }
  
  public static final int min(int paramInt1, int paramInt2)
  {
    if (paramInt1 < paramInt2) {
      return paramInt1;
    }
    return paramInt2;
  }
  
  public static final int min(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 < paramInt2) {
      if (paramInt1 < paramInt3) {
        paramInt3 = paramInt1;
      }
    }
    while (paramInt2 >= paramInt3) {
      return paramInt3;
    }
    return paramInt2;
  }
  
  public static final int min(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt.length == 0) {
      throw new ArrayIndexOutOfBoundsException("Cannot use min() or max() on an empty array.");
    }
    int i = paramArrayOfInt[0];
    for (int j = 1;; j++)
    {
      if (j >= paramArrayOfInt.length) {
        return i;
      }
      if (paramArrayOfInt[j] < i) {
        i = paramArrayOfInt[j];
      }
    }
  }
  
  public static int minute()
  {
    time.setToNow();
    return time.minute;
  }
  
  public static int month()
  {
    time.setToNow();
    return 1 + time.month;
  }
  
  public static String nf(float paramFloat, int paramInt1, int paramInt2)
  {
    if ((float_nf != null) && (float_nf_left == paramInt1) && (float_nf_right == paramInt2) && (!float_nf_commas)) {
      return float_nf.format(paramFloat);
    }
    float_nf = NumberFormat.getInstance();
    float_nf.setGroupingUsed(false);
    float_nf_commas = false;
    if (paramInt1 != 0) {
      float_nf.setMinimumIntegerDigits(paramInt1);
    }
    if (paramInt2 != 0)
    {
      float_nf.setMinimumFractionDigits(paramInt2);
      float_nf.setMaximumFractionDigits(paramInt2);
    }
    float_nf_left = paramInt1;
    float_nf_right = paramInt2;
    return float_nf.format(paramFloat);
  }
  
  public static String nf(int paramInt1, int paramInt2)
  {
    if ((int_nf != null) && (int_nf_digits == paramInt2) && (!int_nf_commas)) {
      return int_nf.format(paramInt1);
    }
    int_nf = NumberFormat.getInstance();
    int_nf.setGroupingUsed(false);
    int_nf_commas = false;
    int_nf.setMinimumIntegerDigits(paramInt2);
    int_nf_digits = paramInt2;
    return int_nf.format(paramInt1);
  }
  
  public static String[] nf(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    String[] arrayOfString = new String[paramArrayOfFloat.length];
    for (int i = 0;; i++)
    {
      if (i >= arrayOfString.length) {
        return arrayOfString;
      }
      arrayOfString[i] = nf(paramArrayOfFloat[i], paramInt1, paramInt2);
    }
  }
  
  public static String[] nf(int[] paramArrayOfInt, int paramInt)
  {
    String[] arrayOfString = new String[paramArrayOfInt.length];
    for (int i = 0;; i++)
    {
      if (i >= arrayOfString.length) {
        return arrayOfString;
      }
      arrayOfString[i] = nf(paramArrayOfInt[i], paramInt);
    }
  }
  
  public static String nfc(float paramFloat, int paramInt)
  {
    if ((float_nf != null) && (float_nf_left == 0) && (float_nf_right == paramInt) && (float_nf_commas)) {
      return float_nf.format(paramFloat);
    }
    float_nf = NumberFormat.getInstance();
    float_nf.setGroupingUsed(true);
    float_nf_commas = true;
    if (paramInt != 0)
    {
      float_nf.setMinimumFractionDigits(paramInt);
      float_nf.setMaximumFractionDigits(paramInt);
    }
    float_nf_left = 0;
    float_nf_right = paramInt;
    return float_nf.format(paramFloat);
  }
  
  public static String nfc(int paramInt)
  {
    if ((int_nf != null) && (int_nf_digits == 0) && (int_nf_commas)) {
      return int_nf.format(paramInt);
    }
    int_nf = NumberFormat.getInstance();
    int_nf.setGroupingUsed(true);
    int_nf_commas = true;
    int_nf.setMinimumIntegerDigits(0);
    int_nf_digits = 0;
    return int_nf.format(paramInt);
  }
  
  public static String[] nfc(float[] paramArrayOfFloat, int paramInt)
  {
    String[] arrayOfString = new String[paramArrayOfFloat.length];
    for (int i = 0;; i++)
    {
      if (i >= arrayOfString.length) {
        return arrayOfString;
      }
      arrayOfString[i] = nfc(paramArrayOfFloat[i], paramInt);
    }
  }
  
  public static String[] nfc(int[] paramArrayOfInt)
  {
    String[] arrayOfString = new String[paramArrayOfInt.length];
    for (int i = 0;; i++)
    {
      if (i >= arrayOfString.length) {
        return arrayOfString;
      }
      arrayOfString[i] = nfc(paramArrayOfInt[i]);
    }
  }
  
  public static String nfp(float paramFloat, int paramInt1, int paramInt2)
  {
    if (paramFloat < 0.0F) {
      return nf(paramFloat, paramInt1, paramInt2);
    }
    return '+' + nf(paramFloat, paramInt1, paramInt2);
  }
  
  public static String nfp(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0) {
      return nf(paramInt1, paramInt2);
    }
    return '+' + nf(paramInt1, paramInt2);
  }
  
  public static String[] nfp(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    String[] arrayOfString = new String[paramArrayOfFloat.length];
    for (int i = 0;; i++)
    {
      if (i >= arrayOfString.length) {
        return arrayOfString;
      }
      arrayOfString[i] = nfp(paramArrayOfFloat[i], paramInt1, paramInt2);
    }
  }
  
  public static String[] nfp(int[] paramArrayOfInt, int paramInt)
  {
    String[] arrayOfString = new String[paramArrayOfInt.length];
    for (int i = 0;; i++)
    {
      if (i >= arrayOfString.length) {
        return arrayOfString;
      }
      arrayOfString[i] = nfp(paramArrayOfInt[i], paramInt);
    }
  }
  
  public static String nfs(float paramFloat, int paramInt1, int paramInt2)
  {
    if (paramFloat < 0.0F) {
      return nf(paramFloat, paramInt1, paramInt2);
    }
    return ' ' + nf(paramFloat, paramInt1, paramInt2);
  }
  
  public static String nfs(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0) {
      return nf(paramInt1, paramInt2);
    }
    return ' ' + nf(paramInt1, paramInt2);
  }
  
  public static String[] nfs(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    String[] arrayOfString = new String[paramArrayOfFloat.length];
    for (int i = 0;; i++)
    {
      if (i >= arrayOfString.length) {
        return arrayOfString;
      }
      arrayOfString[i] = nfs(paramArrayOfFloat[i], paramInt1, paramInt2);
    }
  }
  
  public static String[] nfs(int[] paramArrayOfInt, int paramInt)
  {
    String[] arrayOfString = new String[paramArrayOfInt.length];
    for (int i = 0;; i++)
    {
      if (i >= arrayOfString.length) {
        return arrayOfString;
      }
      arrayOfString[i] = nfs(paramArrayOfInt[i], paramInt);
    }
  }
  
  private float noise_fsc(float paramFloat)
  {
    return 0.5F * (1.0F - this.perlin_cosTable[((int)(paramFloat * this.perlin_PI) % this.perlin_TWOPI)]);
  }
  
  public static final float norm(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return (paramFloat1 - paramFloat2) / (paramFloat3 - paramFloat2);
  }
  
  public static Process open(String[] paramArrayOfString)
  {
    return exec(paramArrayOfString);
  }
  
  public static void open(String paramString)
  {
    open(new String[] { paramString });
  }
  
  public static final boolean parseBoolean(int paramInt)
  {
    return paramInt != 0;
  }
  
  public static final boolean parseBoolean(String paramString)
  {
    return new Boolean(paramString).booleanValue();
  }
  
  public static final boolean[] parseBoolean(byte[] paramArrayOfByte)
  {
    boolean[] arrayOfBoolean = new boolean[paramArrayOfByte.length];
    int i = 0;
    if (i >= paramArrayOfByte.length) {
      return arrayOfBoolean;
    }
    if (paramArrayOfByte[i] != 0) {}
    for (int j = 1;; j = 0)
    {
      arrayOfBoolean[i] = j;
      i++;
      break;
    }
  }
  
  public static final boolean[] parseBoolean(int[] paramArrayOfInt)
  {
    boolean[] arrayOfBoolean = new boolean[paramArrayOfInt.length];
    int i = 0;
    if (i >= paramArrayOfInt.length) {
      return arrayOfBoolean;
    }
    if (paramArrayOfInt[i] != 0) {}
    for (int j = 1;; j = 0)
    {
      arrayOfBoolean[i] = j;
      i++;
      break;
    }
  }
  
  public static final boolean[] parseBoolean(String[] paramArrayOfString)
  {
    boolean[] arrayOfBoolean = new boolean[paramArrayOfString.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfString.length) {
        return arrayOfBoolean;
      }
      arrayOfBoolean[i] = new Boolean(paramArrayOfString[i]).booleanValue();
    }
  }
  
  public static final byte parseByte(char paramChar)
  {
    return (byte)paramChar;
  }
  
  public static final byte parseByte(float paramFloat)
  {
    return (byte)(int)paramFloat;
  }
  
  public static final byte parseByte(int paramInt)
  {
    return (byte)paramInt;
  }
  
  public static final byte parseByte(boolean paramBoolean)
  {
    if (paramBoolean) {
      return 1;
    }
    return 0;
  }
  
  public static final byte[] parseByte(char[] paramArrayOfChar)
  {
    byte[] arrayOfByte = new byte[paramArrayOfChar.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfChar.length) {
        return arrayOfByte;
      }
      arrayOfByte[i] = ((byte)paramArrayOfChar[i]);
    }
  }
  
  public static final byte[] parseByte(float[] paramArrayOfFloat)
  {
    byte[] arrayOfByte = new byte[paramArrayOfFloat.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfFloat.length) {
        return arrayOfByte;
      }
      arrayOfByte[i] = ((byte)(int)paramArrayOfFloat[i]);
    }
  }
  
  public static final byte[] parseByte(int[] paramArrayOfInt)
  {
    byte[] arrayOfByte = new byte[paramArrayOfInt.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfInt.length) {
        return arrayOfByte;
      }
      arrayOfByte[i] = ((byte)paramArrayOfInt[i]);
    }
  }
  
  public static final byte[] parseByte(boolean[] paramArrayOfBoolean)
  {
    byte[] arrayOfByte = new byte[paramArrayOfBoolean.length];
    int i = 0;
    if (i >= paramArrayOfBoolean.length) {
      return arrayOfByte;
    }
    if (paramArrayOfBoolean[i] != 0) {}
    for (int j = 1;; j = 0)
    {
      arrayOfByte[i] = j;
      i++;
      break;
    }
  }
  
  public static final float[] parseByte(byte[] paramArrayOfByte)
  {
    float[] arrayOfFloat = new float[paramArrayOfByte.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfByte.length) {
        return arrayOfFloat;
      }
      arrayOfFloat[i] = paramArrayOfByte[i];
    }
  }
  
  public static final char parseChar(byte paramByte)
  {
    return (char)(paramByte & 0xFF);
  }
  
  public static final char parseChar(int paramInt)
  {
    return (char)paramInt;
  }
  
  public static final char[] parseChar(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar = new char[paramArrayOfByte.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfByte.length) {
        return arrayOfChar;
      }
      arrayOfChar[i] = ((char)(0xFF & paramArrayOfByte[i]));
    }
  }
  
  public static final char[] parseChar(int[] paramArrayOfInt)
  {
    char[] arrayOfChar = new char[paramArrayOfInt.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfInt.length) {
        return arrayOfChar;
      }
      arrayOfChar[i] = ((char)paramArrayOfInt[i]);
    }
  }
  
  public static final float parseFloat(int paramInt)
  {
    return paramInt;
  }
  
  public static final float parseFloat(String paramString)
  {
    return parseFloat(paramString, (0.0F / 0.0F));
  }
  
  public static final float parseFloat(String paramString, float paramFloat)
  {
    try
    {
      float f = new Float(paramString).floatValue();
      return f;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return paramFloat;
  }
  
  public static final float[] parseFloat(int[] paramArrayOfInt)
  {
    float[] arrayOfFloat = new float[paramArrayOfInt.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfInt.length) {
        return arrayOfFloat;
      }
      arrayOfFloat[i] = paramArrayOfInt[i];
    }
  }
  
  public static final float[] parseFloat(String[] paramArrayOfString)
  {
    return parseFloat(paramArrayOfString, (0.0F / 0.0F));
  }
  
  public static final float[] parseFloat(String[] paramArrayOfString, float paramFloat)
  {
    float[] arrayOfFloat = new float[paramArrayOfString.length];
    int i = 0;
    for (;;)
    {
      if (i >= paramArrayOfString.length) {
        return arrayOfFloat;
      }
      try
      {
        arrayOfFloat[i] = new Float(paramArrayOfString[i]).floatValue();
        i++;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        for (;;)
        {
          arrayOfFloat[i] = paramFloat;
        }
      }
    }
  }
  
  public static final int parseInt(byte paramByte)
  {
    return paramByte & 0xFF;
  }
  
  public static final int parseInt(char paramChar)
  {
    return paramChar;
  }
  
  public static final int parseInt(float paramFloat)
  {
    return (int)paramFloat;
  }
  
  public static final int parseInt(String paramString)
  {
    return parseInt(paramString, 0);
  }
  
  public static final int parseInt(String paramString, int paramInt)
  {
    try
    {
      int i = paramString.indexOf('.');
      if (i == -1) {
        return Integer.parseInt(paramString);
      }
      int j = Integer.parseInt(paramString.substring(0, i));
      return j;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return paramInt;
  }
  
  public static final int parseInt(boolean paramBoolean)
  {
    if (paramBoolean) {
      return 1;
    }
    return 0;
  }
  
  public static final int[] parseInt(byte[] paramArrayOfByte)
  {
    int[] arrayOfInt = new int[paramArrayOfByte.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfByte.length) {
        return arrayOfInt;
      }
      arrayOfInt[i] = (0xFF & paramArrayOfByte[i]);
    }
  }
  
  public static final int[] parseInt(char[] paramArrayOfChar)
  {
    int[] arrayOfInt = new int[paramArrayOfChar.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfChar.length) {
        return arrayOfInt;
      }
      arrayOfInt[i] = paramArrayOfChar[i];
    }
  }
  
  public static int[] parseInt(float[] paramArrayOfFloat)
  {
    int[] arrayOfInt = new int[paramArrayOfFloat.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfFloat.length) {
        return arrayOfInt;
      }
      arrayOfInt[i] = ((int)paramArrayOfFloat[i]);
    }
  }
  
  public static int[] parseInt(String[] paramArrayOfString)
  {
    return parseInt(paramArrayOfString, 0);
  }
  
  public static int[] parseInt(String[] paramArrayOfString, int paramInt)
  {
    int[] arrayOfInt = new int[paramArrayOfString.length];
    int i = 0;
    for (;;)
    {
      if (i >= paramArrayOfString.length) {
        return arrayOfInt;
      }
      try
      {
        arrayOfInt[i] = Integer.parseInt(paramArrayOfString[i]);
        i++;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        for (;;)
        {
          arrayOfInt[i] = paramInt;
        }
      }
    }
  }
  
  public static final int[] parseInt(boolean[] paramArrayOfBoolean)
  {
    int[] arrayOfInt = new int[paramArrayOfBoolean.length];
    int i = 0;
    if (i >= paramArrayOfBoolean.length) {
      return arrayOfInt;
    }
    if (paramArrayOfBoolean[i] != 0) {}
    for (int j = 1;; j = 0)
    {
      arrayOfInt[i] = j;
      i++;
      break;
    }
  }
  
  public static final float pow(float paramFloat1, float paramFloat2)
  {
    return (float)Math.pow(paramFloat1, paramFloat2);
  }
  
  public static void print(byte paramByte)
  {
    System.out.print(paramByte);
    System.out.flush();
  }
  
  public static void print(char paramChar)
  {
    System.out.print(paramChar);
    System.out.flush();
  }
  
  public static void print(float paramFloat)
  {
    System.out.print(paramFloat);
    System.out.flush();
  }
  
  public static void print(int paramInt)
  {
    System.out.print(paramInt);
    System.out.flush();
  }
  
  public static void print(Object paramObject)
  {
    if (paramObject == null)
    {
      System.out.print("null");
      return;
    }
    System.out.println(paramObject.toString());
  }
  
  public static void print(String paramString)
  {
    System.out.print(paramString);
    System.out.flush();
  }
  
  public static void print(boolean paramBoolean)
  {
    System.out.print(paramBoolean);
    System.out.flush();
  }
  
  public static void println()
  {
    System.out.println();
  }
  
  public static void println(byte paramByte)
  {
    print(paramByte);
    System.out.println();
  }
  
  public static void println(char paramChar)
  {
    print(paramChar);
    System.out.println();
  }
  
  public static void println(float paramFloat)
  {
    print(paramFloat);
    System.out.println();
  }
  
  public static void println(int paramInt)
  {
    print(paramInt);
    System.out.println();
  }
  
  public static void println(Object paramObject)
  {
    if (paramObject == null) {
      System.out.println("null");
    }
    for (;;)
    {
      return;
      String str = paramObject.getClass().getName();
      if (str.charAt(0) != '[') {
        break;
      }
      switch (str.charAt(1))
      {
      default: 
        System.out.println(paramObject);
        return;
      case '[': 
        System.out.println(paramObject);
        return;
      case 'L': 
        Object[] arrayOfObject = (Object[])paramObject;
        int i1 = 0;
        if (i1 < arrayOfObject.length)
        {
          if ((arrayOfObject[i1] instanceof String)) {
            System.out.println("[" + i1 + "] \"" + arrayOfObject[i1] + "\"");
          }
          for (;;)
          {
            i1++;
            break;
            System.out.println("[" + i1 + "] " + arrayOfObject[i1]);
          }
        }
        break;
      case 'Z': 
        boolean[] arrayOfBoolean = (boolean[])paramObject;
        for (int n = 0; n < arrayOfBoolean.length; n++) {
          System.out.println("[" + n + "] " + arrayOfBoolean[n]);
        }
      case 'B': 
        byte[] arrayOfByte = (byte[])paramObject;
        for (int m = 0; m < arrayOfByte.length; m++) {
          System.out.println("[" + m + "] " + arrayOfByte[m]);
        }
      case 'C': 
        char[] arrayOfChar = (char[])paramObject;
        for (int k = 0; k < arrayOfChar.length; k++) {
          System.out.println("[" + k + "] '" + arrayOfChar[k] + "'");
        }
      case 'I': 
        int[] arrayOfInt = (int[])paramObject;
        for (int j = 0; j < arrayOfInt.length; j++) {
          System.out.println("[" + j + "] " + arrayOfInt[j]);
        }
      case 'F': 
        float[] arrayOfFloat = (float[])paramObject;
        for (int i = 0; i < arrayOfFloat.length; i++) {
          System.out.println("[" + i + "] " + arrayOfFloat[i]);
        }
      }
    }
    System.out.println(paramObject);
  }
  
  public static void println(String paramString)
  {
    print(paramString);
    System.out.println();
  }
  
  public static void println(boolean paramBoolean)
  {
    print(paramBoolean);
    System.out.println();
  }
  
  public static final float radians(float paramFloat)
  {
    return 0.01745329F * paramFloat;
  }
  
  private void registerNoArgs(String paramString, Object paramObject)
  {
    RegisteredMethods localRegisteredMethods = (RegisteredMethods)this.registerMap.get(paramString);
    if (localRegisteredMethods == null)
    {
      localRegisteredMethods = new RegisteredMethods();
      this.registerMap.put(paramString, localRegisteredMethods);
    }
    Class localClass = paramObject.getClass();
    try
    {
      localRegisteredMethods.add(paramObject, localClass.getMethod(paramString, new Class[0]));
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      die("There is no public " + paramString + "() method in the class " + paramObject.getClass().getName());
      return;
    }
    catch (Exception localException)
    {
      die("Could not register " + paramString + " + () for " + paramObject, localException);
    }
  }
  
  private void registerWithArgs(String paramString, Object paramObject, Class<?>[] paramArrayOfClass)
  {
    RegisteredMethods localRegisteredMethods = (RegisteredMethods)this.registerMap.get(paramString);
    if (localRegisteredMethods == null)
    {
      localRegisteredMethods = new RegisteredMethods();
      this.registerMap.put(paramString, localRegisteredMethods);
    }
    Class localClass = paramObject.getClass();
    try
    {
      localRegisteredMethods.add(paramObject, localClass.getMethod(paramString, paramArrayOfClass));
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      die("There is no public " + paramString + "() method in the class " + paramObject.getClass().getName());
      return;
    }
    catch (Exception localException)
    {
      die("Could not register " + paramString + " + () for " + paramObject, localException);
    }
  }
  
  public static Object reverse(Object paramObject)
  {
    Class localClass = paramObject.getClass().getComponentType();
    int i = Array.getLength(paramObject);
    Object localObject = Array.newInstance(localClass, i);
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return localObject;
      }
      Array.set(localObject, j, Array.get(paramObject, i - 1 - j));
    }
  }
  
  public static byte[] reverse(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    int i = -1 + paramArrayOfByte.length;
    for (int j = 0;; j++)
    {
      if (j >= paramArrayOfByte.length) {
        return arrayOfByte;
      }
      arrayOfByte[j] = paramArrayOfByte[(i - j)];
    }
  }
  
  public static char[] reverse(char[] paramArrayOfChar)
  {
    char[] arrayOfChar = new char[paramArrayOfChar.length];
    int i = -1 + paramArrayOfChar.length;
    for (int j = 0;; j++)
    {
      if (j >= paramArrayOfChar.length) {
        return arrayOfChar;
      }
      arrayOfChar[j] = paramArrayOfChar[(i - j)];
    }
  }
  
  public static float[] reverse(float[] paramArrayOfFloat)
  {
    float[] arrayOfFloat = new float[paramArrayOfFloat.length];
    int i = -1 + paramArrayOfFloat.length;
    for (int j = 0;; j++)
    {
      if (j >= paramArrayOfFloat.length) {
        return arrayOfFloat;
      }
      arrayOfFloat[j] = paramArrayOfFloat[(i - j)];
    }
  }
  
  public static int[] reverse(int[] paramArrayOfInt)
  {
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    int i = -1 + paramArrayOfInt.length;
    for (int j = 0;; j++)
    {
      if (j >= paramArrayOfInt.length) {
        return arrayOfInt;
      }
      arrayOfInt[j] = paramArrayOfInt[(i - j)];
    }
  }
  
  public static String[] reverse(String[] paramArrayOfString)
  {
    String[] arrayOfString = new String[paramArrayOfString.length];
    int i = -1 + paramArrayOfString.length;
    for (int j = 0;; j++)
    {
      if (j >= paramArrayOfString.length) {
        return arrayOfString;
      }
      arrayOfString[j] = paramArrayOfString[(i - j)];
    }
  }
  
  public static boolean[] reverse(boolean[] paramArrayOfBoolean)
  {
    boolean[] arrayOfBoolean = new boolean[paramArrayOfBoolean.length];
    int i = -1 + paramArrayOfBoolean.length;
    for (int j = 0;; j++)
    {
      if (j >= paramArrayOfBoolean.length) {
        return arrayOfBoolean;
      }
      arrayOfBoolean[j] = paramArrayOfBoolean[(i - j)];
    }
  }
  
  public static final int round(float paramFloat)
  {
    return Math.round(paramFloat);
  }
  
  public static void saveBytes(File paramFile, byte[] paramArrayOfByte)
  {
    try
    {
      createPath(paramFile.getAbsolutePath());
      Object localObject = new FileOutputStream(paramFile);
      if (paramFile.getName().toLowerCase().endsWith(".gz")) {
        localObject = new GZIPOutputStream((OutputStream)localObject);
      }
      saveBytes((OutputStream)localObject, paramArrayOfByte);
      ((OutputStream)localObject).close();
      return;
    }
    catch (IOException localIOException)
    {
      System.err.println("error saving bytes to " + paramFile);
      localIOException.printStackTrace();
    }
  }
  
  public static void saveBytes(OutputStream paramOutputStream, byte[] paramArrayOfByte)
  {
    try
    {
      paramOutputStream.write(paramArrayOfByte);
      paramOutputStream.flush();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public static boolean saveStream(File paramFile, InputStream paramInputStream)
  {
    File localFile1 = null;
    try
    {
      File localFile2 = paramFile.getParentFile();
      createPath(paramFile);
      localFile1 = File.createTempFile(paramFile.getName(), null, localFile2);
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramInputStream, 16384);
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile1));
      byte[] arrayOfByte = new byte[8192];
      for (;;)
      {
        int i = localBufferedInputStream.read(arrayOfByte);
        if (i == -1)
        {
          localBufferedOutputStream.flush();
          localBufferedOutputStream.close();
          if ((paramFile.exists()) && (!paramFile.delete())) {
            System.err.println("Could not replace " + paramFile.getAbsolutePath() + ".");
          }
          if (localFile1.renameTo(paramFile)) {
            break;
          }
          System.err.println("Could not rename temporary file " + localFile1.getAbsolutePath());
          return false;
        }
        localBufferedOutputStream.write(arrayOfByte, 0, i);
      }
      return true;
    }
    catch (IOException localIOException)
    {
      if (localFile1 != null) {
        localFile1.delete();
      }
      localIOException.printStackTrace();
      return false;
    }
  }
  
  public static void saveStrings(File paramFile, String[] paramArrayOfString)
  {
    try
    {
      String str = paramFile.getAbsolutePath();
      createPath(str);
      Object localObject = new FileOutputStream(str);
      if (paramFile.getName().toLowerCase().endsWith(".gz")) {
        localObject = new GZIPOutputStream((OutputStream)localObject);
      }
      saveStrings((OutputStream)localObject, paramArrayOfString);
      ((OutputStream)localObject).close();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
  
  public static void saveStrings(OutputStream paramOutputStream, String[] paramArrayOfString)
  {
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(new OutputStreamWriter(paramOutputStream, "UTF-8"));
      for (int i = 0;; i++)
      {
        if (i >= paramArrayOfString.length)
        {
          localPrintWriter.flush();
          return;
        }
        localPrintWriter.println(paramArrayOfString[i]);
      }
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
  }
  
  public static int second()
  {
    time.setToNow();
    return time.second;
  }
  
  public static Object shorten(Object paramObject)
  {
    return subset(paramObject, 0, -1 + Array.getLength(paramObject));
  }
  
  public static byte[] shorten(byte[] paramArrayOfByte)
  {
    return subset(paramArrayOfByte, 0, -1 + paramArrayOfByte.length);
  }
  
  public static char[] shorten(char[] paramArrayOfChar)
  {
    return subset(paramArrayOfChar, 0, -1 + paramArrayOfChar.length);
  }
  
  public static float[] shorten(float[] paramArrayOfFloat)
  {
    return subset(paramArrayOfFloat, 0, -1 + paramArrayOfFloat.length);
  }
  
  public static int[] shorten(int[] paramArrayOfInt)
  {
    return subset(paramArrayOfInt, 0, -1 + paramArrayOfInt.length);
  }
  
  public static String[] shorten(String[] paramArrayOfString)
  {
    return subset(paramArrayOfString, 0, -1 + paramArrayOfString.length);
  }
  
  public static boolean[] shorten(boolean[] paramArrayOfBoolean)
  {
    return subset(paramArrayOfBoolean, 0, -1 + paramArrayOfBoolean.length);
  }
  
  public static void showDepthWarning(String paramString)
  {
    PGraphics.showDepthWarning(paramString);
  }
  
  public static void showDepthWarningXYZ(String paramString)
  {
    PGraphics.showDepthWarningXYZ(paramString);
  }
  
  public static void showMethodWarning(String paramString)
  {
    PGraphics.showMethodWarning(paramString);
  }
  
  public static void showMissingWarning(String paramString)
  {
    PGraphics.showMissingWarning(paramString);
  }
  
  public static void showVariationWarning(String paramString)
  {
    PGraphics.showVariationWarning(paramString);
  }
  
  public static final float sin(float paramFloat)
  {
    return (float)Math.sin(paramFloat);
  }
  
  public static byte[] sort(byte[] paramArrayOfByte)
  {
    return sort(paramArrayOfByte, paramArrayOfByte.length);
  }
  
  public static byte[] sort(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte.length];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
    Arrays.sort(arrayOfByte, 0, paramInt);
    return arrayOfByte;
  }
  
  public static char[] sort(char[] paramArrayOfChar)
  {
    return sort(paramArrayOfChar, paramArrayOfChar.length);
  }
  
  public static char[] sort(char[] paramArrayOfChar, int paramInt)
  {
    char[] arrayOfChar = new char[paramArrayOfChar.length];
    System.arraycopy(paramArrayOfChar, 0, arrayOfChar, 0, paramArrayOfChar.length);
    Arrays.sort(arrayOfChar, 0, paramInt);
    return arrayOfChar;
  }
  
  public static float[] sort(float[] paramArrayOfFloat)
  {
    return sort(paramArrayOfFloat, paramArrayOfFloat.length);
  }
  
  public static float[] sort(float[] paramArrayOfFloat, int paramInt)
  {
    float[] arrayOfFloat = new float[paramArrayOfFloat.length];
    System.arraycopy(paramArrayOfFloat, 0, arrayOfFloat, 0, paramArrayOfFloat.length);
    Arrays.sort(arrayOfFloat, 0, paramInt);
    return arrayOfFloat;
  }
  
  public static int[] sort(int[] paramArrayOfInt)
  {
    return sort(paramArrayOfInt, paramArrayOfInt.length);
  }
  
  public static int[] sort(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramArrayOfInt.length);
    Arrays.sort(arrayOfInt, 0, paramInt);
    return arrayOfInt;
  }
  
  public static String[] sort(String[] paramArrayOfString)
  {
    return sort(paramArrayOfString, paramArrayOfString.length);
  }
  
  public static String[] sort(String[] paramArrayOfString, int paramInt)
  {
    String[] arrayOfString = new String[paramArrayOfString.length];
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramArrayOfString.length);
    Arrays.sort(arrayOfString, 0, paramInt);
    return arrayOfString;
  }
  
  public static final Object splice(Object paramObject1, Object paramObject2, int paramInt)
  {
    int i = Array.getLength(paramObject1);
    if (paramObject2.getClass().getName().charAt(0) == '[')
    {
      int j = Array.getLength(paramObject2);
      Object[] arrayOfObject2 = new Object[i + j];
      System.arraycopy(paramObject1, 0, arrayOfObject2, 0, paramInt);
      System.arraycopy(paramObject2, 0, arrayOfObject2, paramInt, j);
      System.arraycopy(paramObject1, paramInt, arrayOfObject2, paramInt + j, i - paramInt);
      return arrayOfObject2;
    }
    Object[] arrayOfObject1 = new Object[i + 1];
    System.arraycopy(paramObject1, 0, arrayOfObject1, 0, paramInt);
    Array.set(arrayOfObject1, paramInt, paramObject2);
    System.arraycopy(paramObject1, paramInt, arrayOfObject1, paramInt + 1, i - paramInt);
    return arrayOfObject1;
  }
  
  public static final byte[] splice(byte[] paramArrayOfByte, byte paramByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[1 + paramArrayOfByte.length];
    System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramInt);
    arrayOfByte[paramInt] = paramByte;
    System.arraycopy(paramArrayOfByte, paramInt, arrayOfByte, paramInt + 1, paramArrayOfByte.length - paramInt);
    return arrayOfByte;
  }
  
  public static final byte[] splice(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
    System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramInt);
    System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, paramInt, paramArrayOfByte2.length);
    System.arraycopy(paramArrayOfByte1, paramInt, arrayOfByte, paramInt + paramArrayOfByte2.length, paramArrayOfByte1.length - paramInt);
    return arrayOfByte;
  }
  
  public static final char[] splice(char[] paramArrayOfChar, char paramChar, int paramInt)
  {
    char[] arrayOfChar = new char[1 + paramArrayOfChar.length];
    System.arraycopy(paramArrayOfChar, 0, arrayOfChar, 0, paramInt);
    arrayOfChar[paramInt] = paramChar;
    System.arraycopy(paramArrayOfChar, paramInt, arrayOfChar, paramInt + 1, paramArrayOfChar.length - paramInt);
    return arrayOfChar;
  }
  
  public static final char[] splice(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt)
  {
    char[] arrayOfChar = new char[paramArrayOfChar1.length + paramArrayOfChar2.length];
    System.arraycopy(paramArrayOfChar1, 0, arrayOfChar, 0, paramInt);
    System.arraycopy(paramArrayOfChar2, 0, arrayOfChar, paramInt, paramArrayOfChar2.length);
    System.arraycopy(paramArrayOfChar1, paramInt, arrayOfChar, paramInt + paramArrayOfChar2.length, paramArrayOfChar1.length - paramInt);
    return arrayOfChar;
  }
  
  public static final float[] splice(float[] paramArrayOfFloat, float paramFloat, int paramInt)
  {
    float[] arrayOfFloat = new float[1 + paramArrayOfFloat.length];
    System.arraycopy(paramArrayOfFloat, 0, arrayOfFloat, 0, paramInt);
    arrayOfFloat[paramInt] = paramFloat;
    System.arraycopy(paramArrayOfFloat, paramInt, arrayOfFloat, paramInt + 1, paramArrayOfFloat.length - paramInt);
    return arrayOfFloat;
  }
  
  public static final float[] splice(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, int paramInt)
  {
    float[] arrayOfFloat = new float[paramArrayOfFloat1.length + paramArrayOfFloat2.length];
    System.arraycopy(paramArrayOfFloat1, 0, arrayOfFloat, 0, paramInt);
    System.arraycopy(paramArrayOfFloat2, 0, arrayOfFloat, paramInt, paramArrayOfFloat2.length);
    System.arraycopy(paramArrayOfFloat1, paramInt, arrayOfFloat, paramInt + paramArrayOfFloat2.length, paramArrayOfFloat1.length - paramInt);
    return arrayOfFloat;
  }
  
  public static final int[] splice(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = new int[1 + paramArrayOfInt.length];
    System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramInt2);
    arrayOfInt[paramInt2] = paramInt1;
    System.arraycopy(paramArrayOfInt, paramInt2, arrayOfInt, paramInt2 + 1, paramArrayOfInt.length - paramInt2);
    return arrayOfInt;
  }
  
  public static final int[] splice(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
  {
    int[] arrayOfInt = new int[paramArrayOfInt1.length + paramArrayOfInt2.length];
    System.arraycopy(paramArrayOfInt1, 0, arrayOfInt, 0, paramInt);
    System.arraycopy(paramArrayOfInt2, 0, arrayOfInt, paramInt, paramArrayOfInt2.length);
    System.arraycopy(paramArrayOfInt1, paramInt, arrayOfInt, paramInt + paramArrayOfInt2.length, paramArrayOfInt1.length - paramInt);
    return arrayOfInt;
  }
  
  public static final String[] splice(String[] paramArrayOfString, String paramString, int paramInt)
  {
    String[] arrayOfString = new String[1 + paramArrayOfString.length];
    System.arraycopy(paramArrayOfString, 0, arrayOfString, 0, paramInt);
    arrayOfString[paramInt] = paramString;
    System.arraycopy(paramArrayOfString, paramInt, arrayOfString, paramInt + 1, paramArrayOfString.length - paramInt);
    return arrayOfString;
  }
  
  public static final String[] splice(String[] paramArrayOfString1, String[] paramArrayOfString2, int paramInt)
  {
    String[] arrayOfString = new String[paramArrayOfString1.length + paramArrayOfString2.length];
    System.arraycopy(paramArrayOfString1, 0, arrayOfString, 0, paramInt);
    System.arraycopy(paramArrayOfString2, 0, arrayOfString, paramInt, paramArrayOfString2.length);
    System.arraycopy(paramArrayOfString1, paramInt, arrayOfString, paramInt + paramArrayOfString2.length, paramArrayOfString1.length - paramInt);
    return arrayOfString;
  }
  
  public static final boolean[] splice(boolean[] paramArrayOfBoolean, boolean paramBoolean, int paramInt)
  {
    boolean[] arrayOfBoolean = new boolean[1 + paramArrayOfBoolean.length];
    System.arraycopy(paramArrayOfBoolean, 0, arrayOfBoolean, 0, paramInt);
    arrayOfBoolean[paramInt] = paramBoolean;
    System.arraycopy(paramArrayOfBoolean, paramInt, arrayOfBoolean, paramInt + 1, paramArrayOfBoolean.length - paramInt);
    return arrayOfBoolean;
  }
  
  public static final boolean[] splice(boolean[] paramArrayOfBoolean1, boolean[] paramArrayOfBoolean2, int paramInt)
  {
    boolean[] arrayOfBoolean = new boolean[paramArrayOfBoolean1.length + paramArrayOfBoolean2.length];
    System.arraycopy(paramArrayOfBoolean1, 0, arrayOfBoolean, 0, paramInt);
    System.arraycopy(paramArrayOfBoolean2, 0, arrayOfBoolean, paramInt, paramArrayOfBoolean2.length);
    System.arraycopy(paramArrayOfBoolean1, paramInt, arrayOfBoolean, paramInt + paramArrayOfBoolean2.length, paramArrayOfBoolean1.length - paramInt);
    return arrayOfBoolean;
  }
  
  public static String[] split(String paramString, char paramChar)
  {
    if (paramString == null) {
      return null;
    }
    char[] arrayOfChar = paramString.toCharArray();
    int i = 0;
    for (int j = 0;; j++)
    {
      if (j >= arrayOfChar.length)
      {
        if (i != 0) {
          break;
        }
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = new String(paramString);
        return arrayOfString2;
      }
      if (arrayOfChar[j] == paramChar) {
        i++;
      }
    }
    String[] arrayOfString1 = new String[i + 1];
    int k = 0;
    int m = 0;
    for (int n = 0;; n++)
    {
      if (n >= arrayOfChar.length)
      {
        arrayOfString1[k] = new String(arrayOfChar, m, arrayOfChar.length - m);
        return arrayOfString1;
      }
      if (arrayOfChar[n] == paramChar)
      {
        int i1 = k + 1;
        arrayOfString1[k] = new String(arrayOfChar, m, n - m);
        m = n + 1;
        k = i1;
      }
    }
  }
  
  public static String[] split(String paramString1, String paramString2)
  {
    ArrayList localArrayList = new ArrayList();
    int j;
    for (int i = 0;; i = j + paramString2.length())
    {
      j = paramString1.indexOf(paramString2, i);
      if (j == -1)
      {
        localArrayList.add(paramString1.substring(i));
        String[] arrayOfString = new String[localArrayList.size()];
        localArrayList.toArray(arrayOfString);
        return arrayOfString;
      }
      localArrayList.add(paramString1.substring(i, j));
    }
  }
  
  public static String[] splitTokens(String paramString)
  {
    return splitTokens(paramString, " \t\n\r\f ");
  }
  
  public static String[] splitTokens(String paramString1, String paramString2)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    String[] arrayOfString = new String[localStringTokenizer.countTokens()];
    int j;
    for (int i = 0;; i = j)
    {
      if (!localStringTokenizer.hasMoreTokens()) {
        return arrayOfString;
      }
      j = i + 1;
      arrayOfString[i] = localStringTokenizer.nextToken();
    }
  }
  
  public static final float sq(float paramFloat)
  {
    return paramFloat * paramFloat;
  }
  
  public static final float sqrt(float paramFloat)
  {
    return (float)Math.sqrt(paramFloat);
  }
  
  public static final String str(byte paramByte)
  {
    return String.valueOf(paramByte);
  }
  
  public static final String str(char paramChar)
  {
    return String.valueOf(paramChar);
  }
  
  public static final String str(float paramFloat)
  {
    return String.valueOf(paramFloat);
  }
  
  public static final String str(int paramInt)
  {
    return String.valueOf(paramInt);
  }
  
  public static final String str(boolean paramBoolean)
  {
    return String.valueOf(paramBoolean);
  }
  
  public static final String[] str(byte[] paramArrayOfByte)
  {
    String[] arrayOfString = new String[paramArrayOfByte.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfByte.length) {
        return arrayOfString;
      }
      arrayOfString[i] = String.valueOf(paramArrayOfByte[i]);
    }
  }
  
  public static final String[] str(char[] paramArrayOfChar)
  {
    String[] arrayOfString = new String[paramArrayOfChar.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfChar.length) {
        return arrayOfString;
      }
      arrayOfString[i] = String.valueOf(paramArrayOfChar[i]);
    }
  }
  
  public static final String[] str(float[] paramArrayOfFloat)
  {
    String[] arrayOfString = new String[paramArrayOfFloat.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfFloat.length) {
        return arrayOfString;
      }
      arrayOfString[i] = String.valueOf(paramArrayOfFloat[i]);
    }
  }
  
  public static final String[] str(int[] paramArrayOfInt)
  {
    String[] arrayOfString = new String[paramArrayOfInt.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfInt.length) {
        return arrayOfString;
      }
      arrayOfString[i] = String.valueOf(paramArrayOfInt[i]);
    }
  }
  
  public static final String[] str(boolean[] paramArrayOfBoolean)
  {
    String[] arrayOfString = new String[paramArrayOfBoolean.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfBoolean.length) {
        return arrayOfString;
      }
      arrayOfString[i] = String.valueOf(paramArrayOfBoolean[i]);
    }
  }
  
  public static Object subset(Object paramObject, int paramInt)
  {
    return subset(paramObject, paramInt, Array.getLength(paramObject) - paramInt);
  }
  
  public static Object subset(Object paramObject, int paramInt1, int paramInt2)
  {
    Object localObject = Array.newInstance(paramObject.getClass().getComponentType(), paramInt2);
    System.arraycopy(paramObject, paramInt1, localObject, 0, paramInt2);
    return localObject;
  }
  
  public static byte[] subset(byte[] paramArrayOfByte, int paramInt)
  {
    return subset(paramArrayOfByte, paramInt, paramArrayOfByte.length - paramInt);
  }
  
  public static byte[] subset(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    return arrayOfByte;
  }
  
  public static char[] subset(char[] paramArrayOfChar, int paramInt)
  {
    return subset(paramArrayOfChar, paramInt, paramArrayOfChar.length - paramInt);
  }
  
  public static char[] subset(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    char[] arrayOfChar = new char[paramInt2];
    System.arraycopy(paramArrayOfChar, paramInt1, arrayOfChar, 0, paramInt2);
    return arrayOfChar;
  }
  
  public static float[] subset(float[] paramArrayOfFloat, int paramInt)
  {
    return subset(paramArrayOfFloat, paramInt, paramArrayOfFloat.length - paramInt);
  }
  
  public static float[] subset(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    float[] arrayOfFloat = new float[paramInt2];
    System.arraycopy(paramArrayOfFloat, paramInt1, arrayOfFloat, 0, paramInt2);
    return arrayOfFloat;
  }
  
  public static int[] subset(int[] paramArrayOfInt, int paramInt)
  {
    return subset(paramArrayOfInt, paramInt, paramArrayOfInt.length - paramInt);
  }
  
  public static int[] subset(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = new int[paramInt2];
    System.arraycopy(paramArrayOfInt, paramInt1, arrayOfInt, 0, paramInt2);
    return arrayOfInt;
  }
  
  public static String[] subset(String[] paramArrayOfString, int paramInt)
  {
    return subset(paramArrayOfString, paramInt, paramArrayOfString.length - paramInt);
  }
  
  public static String[] subset(String[] paramArrayOfString, int paramInt1, int paramInt2)
  {
    String[] arrayOfString = new String[paramInt2];
    System.arraycopy(paramArrayOfString, paramInt1, arrayOfString, 0, paramInt2);
    return arrayOfString;
  }
  
  public static boolean[] subset(boolean[] paramArrayOfBoolean, int paramInt)
  {
    return subset(paramArrayOfBoolean, paramInt, paramArrayOfBoolean.length - paramInt);
  }
  
  public static boolean[] subset(boolean[] paramArrayOfBoolean, int paramInt1, int paramInt2)
  {
    boolean[] arrayOfBoolean = new boolean[paramInt2];
    System.arraycopy(paramArrayOfBoolean, paramInt1, arrayOfBoolean, 0, paramInt2);
    return arrayOfBoolean;
  }
  
  public static final float tan(float paramFloat)
  {
    return (float)Math.tan(paramFloat);
  }
  
  private void tellPDE(String paramString)
  {
    Log.i(getComponentName().getPackageName(), "PROCESSING " + paramString);
  }
  
  public static String trim(String paramString)
  {
    return paramString.replace(' ', ' ').trim();
  }
  
  public static String[] trim(String[] paramArrayOfString)
  {
    String[] arrayOfString = new String[paramArrayOfString.length];
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfString.length) {
        return arrayOfString;
      }
      if (paramArrayOfString[i] != null) {
        arrayOfString[i] = paramArrayOfString[i].replace(' ', ' ').trim();
      }
    }
  }
  
  public static final int unbinary(String paramString)
  {
    return Integer.parseInt(paramString, 2);
  }
  
  public static final int unhex(String paramString)
  {
    return (int)Long.parseLong(paramString, 16);
  }
  
  public static String urlDecode(String paramString)
  {
    try
    {
      String str = URLDecoder.decode(paramString, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    return null;
  }
  
  public static String urlEncode(String paramString)
  {
    try
    {
      String str = URLEncoder.encode(paramString, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
    return null;
  }
  
  public static int year()
  {
    time.setToNow();
    return time.year;
  }
  
  public final float alpha(int paramInt)
  {
    return this.g.alpha(paramInt);
  }
  
  public void ambient(float paramFloat)
  {
    this.g.ambient(paramFloat);
  }
  
  public void ambient(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.ambient(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void ambient(int paramInt)
  {
    this.g.ambient(paramInt);
  }
  
  public void ambientLight(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.ambientLight(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void ambientLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.g.ambientLight(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public void andresNeedsBetterAPI()
  {
    if (this.looping) {
      ((GLSurfaceView)this.surfaceView).requestRender();
    }
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.g.applyMatrix(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public void applyMatrix(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16)
  {
    this.g.applyMatrix(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramFloat13, paramFloat14, paramFloat15, paramFloat16);
  }
  
  public void applyMatrix(PMatrix2D paramPMatrix2D)
  {
    this.g.applyMatrix(paramPMatrix2D);
  }
  
  public void applyMatrix(PMatrix3D paramPMatrix3D)
  {
    this.g.applyMatrix(paramPMatrix3D);
  }
  
  public void applyMatrix(PMatrix paramPMatrix)
  {
    this.g.applyMatrix(paramPMatrix);
  }
  
  public void arc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.g.arc(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public void arc(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt)
  {
    this.g.arc(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramInt);
  }
  
  public void background(float paramFloat)
  {
    this.g.background(paramFloat);
  }
  
  public void background(float paramFloat1, float paramFloat2)
  {
    this.g.background(paramFloat1, paramFloat2);
  }
  
  public void background(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.background(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void background(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.background(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void background(int paramInt)
  {
    this.g.background(paramInt);
  }
  
  public void background(int paramInt, float paramFloat)
  {
    this.g.background(paramInt, paramFloat);
  }
  
  public void background(PImage paramPImage)
  {
    this.g.background(paramPImage);
  }
  
  public void beginCamera()
  {
    this.g.beginCamera();
  }
  
  public void beginContour()
  {
    this.g.beginContour();
  }
  
  public PGL beginPGL()
  {
    return this.g.beginPGL();
  }
  
  public void beginShape()
  {
    this.g.beginShape();
  }
  
  public void beginShape(int paramInt)
  {
    this.g.beginShape(paramInt);
  }
  
  public void bezier(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    this.g.bezier(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
  }
  
  public void bezier(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
  {
    this.g.bezier(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12);
  }
  
  public void bezierDetail(int paramInt)
  {
    this.g.bezierDetail(paramInt);
  }
  
  public float bezierPoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    return this.g.bezierPoint(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
  }
  
  public float bezierTangent(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    return this.g.bezierTangent(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
  }
  
  public void bezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.g.bezierVertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public void bezierVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    this.g.bezierVertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9);
  }
  
  public void blend(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    this.g.blend(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9);
  }
  
  public void blend(PImage paramPImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    this.g.blend(paramPImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9);
  }
  
  public void blendMode(int paramInt)
  {
    this.g.blendMode(paramInt);
  }
  
  public final float blue(int paramInt)
  {
    return this.g.blue(paramInt);
  }
  
  public void box(float paramFloat)
  {
    this.g.box(paramFloat);
  }
  
  public void box(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.box(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void breakShape()
  {
    this.g.breakShape();
  }
  
  public final float brightness(int paramInt)
  {
    return this.g.brightness(paramInt);
  }
  
  public void camera()
  {
    this.g.camera();
  }
  
  public void camera(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    this.g.camera(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9);
  }
  
  protected String checkExtension(String paramString)
  {
    int i = paramString.lastIndexOf('.');
    if (i == -1) {
      return null;
    }
    return paramString.substring(i + 1).toLowerCase();
  }
  
  public void clear()
  {
    this.g.clear();
  }
  
  public void clip(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.clip(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public final int color(float paramFloat)
  {
    if (this.g == null)
    {
      int i = (int)paramFloat;
      if (i > 255) {
        i = 255;
      }
      for (;;)
      {
        return i | 0xFF000000 | i << 16 | i << 8;
        if (i < 0) {
          i = 0;
        }
      }
    }
    return this.g.color(paramFloat);
  }
  
  public final int color(float paramFloat1, float paramFloat2)
  {
    if (this.g == null)
    {
      int i = (int)paramFloat1;
      int j = (int)paramFloat2;
      if (i > 255)
      {
        i = 255;
        if (j <= 255) {
          break label58;
        }
      }
      for (;;)
      {
        return i | 0xFF000000 | i << 16 | i << 8;
        if (i >= 0) {
          break;
        }
        i = 0;
        break;
        label58:
        if (j >= 0) {}
      }
    }
    return this.g.color(paramFloat1, paramFloat2);
  }
  
  public final int color(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (this.g == null)
    {
      if (paramFloat1 > 255.0F)
      {
        paramFloat1 = 255.0F;
        if (paramFloat2 <= 255.0F) {
          break label73;
        }
        paramFloat2 = 255.0F;
        label31:
        if (paramFloat3 <= 255.0F) {
          break label84;
        }
        paramFloat3 = 255.0F;
      }
      for (;;)
      {
        return 0xFF000000 | (int)paramFloat1 << 16 | (int)paramFloat2 << 8 | (int)paramFloat3;
        if (paramFloat1 >= 0.0F) {
          break;
        }
        paramFloat1 = 0.0F;
        break;
        label73:
        if (paramFloat2 >= 0.0F) {
          break label31;
        }
        paramFloat2 = 0.0F;
        break label31;
        label84:
        if (paramFloat3 < 0.0F) {
          paramFloat3 = 0.0F;
        }
      }
    }
    return this.g.color(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public final int color(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (this.g == null)
    {
      if (paramFloat4 > 255.0F)
      {
        paramFloat4 = 255.0F;
        if (paramFloat1 <= 255.0F) {
          break label92;
        }
        paramFloat1 = 255.0F;
        label33:
        if (paramFloat2 <= 255.0F) {
          break label103;
        }
        paramFloat2 = 255.0F;
        label45:
        if (paramFloat3 <= 255.0F) {
          break label114;
        }
        paramFloat3 = 255.0F;
      }
      for (;;)
      {
        return (int)paramFloat4 << 24 | (int)paramFloat1 << 16 | (int)paramFloat2 << 8 | (int)paramFloat3;
        if (paramFloat4 >= 0.0F) {
          break;
        }
        paramFloat4 = 0.0F;
        break;
        label92:
        if (paramFloat1 >= 0.0F) {
          break label33;
        }
        paramFloat1 = 0.0F;
        break label33;
        label103:
        if (paramFloat2 >= 0.0F) {
          break label45;
        }
        paramFloat2 = 0.0F;
        break label45;
        label114:
        if (paramFloat3 < 0.0F) {
          paramFloat3 = 0.0F;
        }
      }
    }
    return this.g.color(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public final int color(int paramInt)
  {
    if (this.g == null)
    {
      if (paramInt > 255) {
        paramInt = 255;
      }
      for (;;)
      {
        return paramInt | 0xFF000000 | paramInt << 16 | paramInt << 8;
        if (paramInt < 0) {
          paramInt = 0;
        }
      }
    }
    return this.g.color(paramInt);
  }
  
  public final int color(int paramInt1, int paramInt2)
  {
    if (this.g == null)
    {
      if (paramInt2 > 255) {
        paramInt2 = 255;
      }
      while (paramInt1 > 255)
      {
        return paramInt2 << 24 | 0xFFFFFF & paramInt1;
        if (paramInt2 < 0) {
          paramInt2 = 0;
        }
      }
      return paramInt1 | paramInt2 << 24 | paramInt1 << 16 | paramInt1 << 8;
    }
    return this.g.color(paramInt1, paramInt2);
  }
  
  public final int color(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.g == null)
    {
      if (paramInt1 > 255)
      {
        paramInt1 = 255;
        if (paramInt2 <= 255) {
          break label65;
        }
        paramInt2 = 255;
        label29:
        if (paramInt3 <= 255) {
          break label74;
        }
        paramInt3 = 255;
      }
      for (;;)
      {
        return paramInt3 | 0xFF000000 | paramInt1 << 16 | paramInt2 << 8;
        if (paramInt1 >= 0) {
          break;
        }
        paramInt1 = 0;
        break;
        label65:
        if (paramInt2 >= 0) {
          break label29;
        }
        paramInt2 = 0;
        break label29;
        label74:
        if (paramInt3 < 0) {
          paramInt3 = 0;
        }
      }
    }
    return this.g.color(paramInt1, paramInt2, paramInt3);
  }
  
  public final int color(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.g == null)
    {
      if (paramInt4 > 255)
      {
        paramInt4 = 255;
        if (paramInt1 <= 255) {
          break label82;
        }
        paramInt1 = 255;
        label31:
        if (paramInt2 <= 255) {
          break label91;
        }
        paramInt2 = 255;
        label42:
        if (paramInt3 <= 255) {
          break label100;
        }
        paramInt3 = 255;
      }
      for (;;)
      {
        return paramInt3 | paramInt4 << 24 | paramInt1 << 16 | paramInt2 << 8;
        if (paramInt4 >= 0) {
          break;
        }
        paramInt4 = 0;
        break;
        label82:
        if (paramInt1 >= 0) {
          break label31;
        }
        paramInt1 = 0;
        break label31;
        label91:
        if (paramInt2 >= 0) {
          break label42;
        }
        paramInt2 = 0;
        break label42;
        label100:
        if (paramInt3 < 0) {
          paramInt3 = 0;
        }
      }
    }
    return this.g.color(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void colorMode(int paramInt)
  {
    this.g.colorMode(paramInt);
  }
  
  public void colorMode(int paramInt, float paramFloat)
  {
    this.g.colorMode(paramInt, paramFloat);
  }
  
  public void colorMode(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.colorMode(paramInt, paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void colorMode(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.colorMode(paramInt, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void copy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    this.g.copy(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
  }
  
  public void copy(PImage paramPImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    this.g.copy(paramPImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8);
  }
  
  protected PFont createDefaultFont(float paramFloat)
  {
    return createFont("SansSerif", paramFloat, true, null);
  }
  
  public PFont createFont(String paramString, float paramFloat)
  {
    return createFont(paramString, paramFloat, true, null);
  }
  
  public PFont createFont(String paramString, float paramFloat, boolean paramBoolean)
  {
    return createFont(paramString, paramFloat, paramBoolean, null);
  }
  
  public PFont createFont(String paramString, float paramFloat, boolean paramBoolean, char[] paramArrayOfChar)
  {
    String str = paramString.toLowerCase();
    if ((str.endsWith(".otf")) || (str.endsWith(".ttf"))) {}
    for (Typeface localTypeface = Typeface.createFromAsset(getBaseContext().getAssets(), paramString);; localTypeface = (Typeface)PFont.findNative(paramString)) {
      return new PFont(localTypeface, round(paramFloat), paramBoolean, paramArrayOfChar);
    }
  }
  
  public PGraphics createGraphics(int paramInt1, int paramInt2)
  {
    return createGraphics(paramInt1, paramInt2, "processing.core.PGraphicsAndroid2D");
  }
  
  public PGraphics createGraphics(int paramInt1, int paramInt2, String paramString)
  {
    Object localObject;
    if (paramString.equals("processing.core.PGraphicsAndroid2D")) {
      localObject = new PGraphicsAndroid2D();
    }
    for (;;)
    {
      ((PGraphics)localObject).setParent(this);
      ((PGraphics)localObject).setPrimary(false);
      ((PGraphics)localObject).setSize(paramInt1, paramInt2);
      return localObject;
      if (paramString.equals("processing.opengl.PGraphics2D"))
      {
        if (!this.g.isGL()) {
          throw new RuntimeException("createGraphics() with P2D requires size() to use P2D or P3D");
        }
        localObject = new PGraphics2D();
        continue;
      }
      if (paramString.equals("processing.opengl.PGraphics3D"))
      {
        if (!this.g.isGL()) {
          throw new RuntimeException("createGraphics() with P3D or OPENGL requires size() to use P2D or P3D");
        }
        localObject = new PGraphics3D();
        continue;
      }
      try
      {
        localClass = getClass().getClassLoader().loadClass(paramString);
        localObject = null;
        if (localClass == null) {
          continue;
        }
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        try
        {
          Class localClass;
          localConstructor = localClass.getConstructor(new Class[0]);
          localObject = null;
          if (localConstructor == null) {
            continue;
          }
        }
        catch (NoSuchMethodException localNoSuchMethodException)
        {
          Constructor localConstructor;
          throw new RuntimeException("Missing renderer constructor");
        }
        try
        {
          localObject = (PGraphics)localConstructor.newInstance(new Object[0]);
        }
        catch (InvocationTargetException localInvocationTargetException)
        {
          localInvocationTargetException.printStackTrace();
          throw new RuntimeException(localInvocationTargetException.getMessage());
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          localIllegalAccessException.printStackTrace();
          throw new RuntimeException(localIllegalAccessException.getMessage());
        }
        catch (InstantiationException localInstantiationException)
        {
          localInstantiationException.printStackTrace();
          throw new RuntimeException(localInstantiationException.getMessage());
        }
        localClassNotFoundException = localClassNotFoundException;
        throw new RuntimeException("Missing renderer class");
      }
    }
  }
  
  public PImage createImage(int paramInt1, int paramInt2, int paramInt3)
  {
    PImage localPImage = new PImage(paramInt1, paramInt2, paramInt3);
    localPImage.parent = this;
    return localPImage;
  }
  
  public InputStream createInput(String paramString)
  {
    Object localObject = createInputRaw(paramString);
    if ((localObject != null) && (paramString.toLowerCase().endsWith(".gz"))) {}
    try
    {
      GZIPInputStream localGZIPInputStream = new GZIPInputStream((InputStream)localObject);
      localObject = localGZIPInputStream;
      return localObject;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }
  
  /* Error */
  public InputStream createInputRaw(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: aload_1
    //   7: invokevirtual 284	java/lang/String:length	()I
    //   10: ifne +5 -> 15
    //   13: aconst_null
    //   14: areturn
    //   15: aload_1
    //   16: ldc_w 1516
    //   19: invokevirtual 1518	java/lang/String:indexOf	(Ljava/lang/String;)I
    //   22: iconst_m1
    //   23: if_icmpeq +56 -> 79
    //   26: new 1520	org/apache/http/client/methods/HttpGet
    //   29: dup
    //   30: aload_1
    //   31: invokestatic 1526	java/net/URI:create	(Ljava/lang/String;)Ljava/net/URI;
    //   34: invokespecial 1529	org/apache/http/client/methods/HttpGet:<init>	(Ljava/net/URI;)V
    //   37: astore 14
    //   39: new 1531	org/apache/http/impl/client/DefaultHttpClient
    //   42: dup
    //   43: invokespecial 1532	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   46: aload 14
    //   48: invokeinterface 1538 2 0
    //   53: invokeinterface 1544 1 0
    //   58: invokeinterface 1550 1 0
    //   63: astore 18
    //   65: aload 18
    //   67: areturn
    //   68: astore 17
    //   70: aload 17
    //   72: invokevirtual 392	java/io/IOException:printStackTrace	()V
    //   75: aconst_null
    //   76: areturn
    //   77: astore 20
    //   79: aload_0
    //   80: invokevirtual 1551	processing/core/PApplet:getAssets	()Landroid/content/res/AssetManager;
    //   83: astore_2
    //   84: aload_2
    //   85: aload_1
    //   86: invokevirtual 1555	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   89: astore 13
    //   91: aload 13
    //   93: ifnull +7 -> 100
    //   96: aload 13
    //   98: areturn
    //   99: astore_3
    //   100: new 358	java/io/File
    //   103: dup
    //   104: aload_1
    //   105: invokespecial 399	java/io/File:<init>	(Ljava/lang/String;)V
    //   108: astore 4
    //   110: aload 4
    //   112: invokevirtual 403	java/io/File:exists	()Z
    //   115: ifeq +24 -> 139
    //   118: new 353	java/io/FileInputStream
    //   121: dup
    //   122: aload 4
    //   124: invokespecial 356	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   127: astore 11
    //   129: aload 11
    //   131: ifnull +100 -> 231
    //   134: aload 11
    //   136: areturn
    //   137: astore 12
    //   139: new 358	java/io/File
    //   142: dup
    //   143: aload_0
    //   144: aload_1
    //   145: invokevirtual 1557	processing/core/PApplet:sketchPath	(Ljava/lang/String;)Ljava/lang/String;
    //   148: invokespecial 399	java/io/File:<init>	(Ljava/lang/String;)V
    //   151: astore 5
    //   153: aload 5
    //   155: invokevirtual 403	java/io/File:exists	()Z
    //   158: ifeq +24 -> 182
    //   161: new 353	java/io/FileInputStream
    //   164: dup
    //   165: aload 5
    //   167: invokespecial 356	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   170: astore 9
    //   172: aload 9
    //   174: ifnull +54 -> 228
    //   177: aload 9
    //   179: areturn
    //   180: astore 10
    //   182: aload_0
    //   183: invokevirtual 1560	processing/core/PApplet:getApplicationContext	()Landroid/content/Context;
    //   186: astore 6
    //   188: aload 6
    //   190: aload_1
    //   191: invokevirtual 1564	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   194: astore 8
    //   196: aload 8
    //   198: ifnull +8 -> 206
    //   201: aload 8
    //   203: areturn
    //   204: astore 7
    //   206: aconst_null
    //   207: areturn
    //   208: astore 17
    //   210: goto -140 -> 70
    //   213: astore 16
    //   215: goto -136 -> 79
    //   218: astore 19
    //   220: goto -141 -> 79
    //   223: astore 15
    //   225: goto -146 -> 79
    //   228: goto -46 -> 182
    //   231: goto -92 -> 139
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	234	0	this	PApplet
    //   0	234	1	paramString	String
    //   83	2	2	localAssetManager	android.content.res.AssetManager
    //   99	1	3	localIOException1	IOException
    //   108	15	4	localFile1	File
    //   151	15	5	localFile2	File
    //   186	3	6	localContext	Context
    //   204	1	7	localFileNotFoundException1	java.io.FileNotFoundException
    //   194	8	8	localFileInputStream1	FileInputStream
    //   170	8	9	localFileInputStream2	FileInputStream
    //   180	1	10	localFileNotFoundException2	java.io.FileNotFoundException
    //   127	8	11	localFileInputStream3	FileInputStream
    //   137	1	12	localFileNotFoundException3	java.io.FileNotFoundException
    //   89	8	13	localInputStream1	InputStream
    //   37	10	14	localHttpGet	org.apache.http.client.methods.HttpGet
    //   223	1	15	localMalformedURLException1	java.net.MalformedURLException
    //   213	1	16	localFileNotFoundException4	java.io.FileNotFoundException
    //   68	3	17	localIOException2	IOException
    //   208	1	17	localIOException3	IOException
    //   63	3	18	localInputStream2	InputStream
    //   218	1	19	localMalformedURLException2	java.net.MalformedURLException
    //   77	1	20	localFileNotFoundException5	java.io.FileNotFoundException
    // Exception table:
    //   from	to	target	type
    //   26	39	68	java/io/IOException
    //   26	39	77	java/io/FileNotFoundException
    //   84	91	99	java/io/IOException
    //   118	129	137	java/io/FileNotFoundException
    //   161	172	180	java/io/FileNotFoundException
    //   188	196	204	java/io/FileNotFoundException
    //   39	65	208	java/io/IOException
    //   39	65	213	java/io/FileNotFoundException
    //   26	39	218	java/net/MalformedURLException
    //   39	65	223	java/net/MalformedURLException
  }
  
  public OutputStream createOutput(String paramString)
  {
    try
    {
      File localFile = new File(paramString);
      if (!localFile.isAbsolute()) {
        localFile = new File(sketchPath(paramString));
      }
      Object localObject = new FileOutputStream(localFile);
      if (localFile.getName().toLowerCase().endsWith(".gz"))
      {
        GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream((OutputStream)localObject);
        localObject = localGZIPOutputStream;
      }
      return localObject;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }
  
  public BufferedReader createReader(String paramString)
  {
    try
    {
      InputStream localInputStream = createInput(paramString);
      if (localInputStream == null)
      {
        System.err.println(paramString + " does not exist or could not be read");
        return null;
      }
      BufferedReader localBufferedReader = createReader(localInputStream);
      return localBufferedReader;
    }
    catch (Exception localException)
    {
      if (paramString == null)
      {
        System.err.println("Filename passed to reader() was null");
        return null;
      }
      System.err.println("Couldn't create a reader for " + paramString);
    }
    return null;
  }
  
  public PShape createShape()
  {
    return this.g.createShape();
  }
  
  public PShape createShape(int paramInt)
  {
    return this.g.createShape(paramInt);
  }
  
  public PShape createShape(int paramInt, float... paramVarArgs)
  {
    return this.g.createShape(paramInt, paramVarArgs);
  }
  
  public PShape createShape(PShape paramPShape)
  {
    return this.g.createShape(paramPShape);
  }
  
  public Table createTable()
  {
    return new Table();
  }
  
  public PrintWriter createWriter(String paramString)
  {
    return createWriter(saveFile(paramString));
  }
  
  public XML createXML(String paramString)
  {
    try
    {
      XML localXML = new XML(paramString);
      return localXML;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public void curve(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    this.g.curve(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
  }
  
  public void curve(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
  {
    this.g.curve(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12);
  }
  
  public void curveDetail(int paramInt)
  {
    this.g.curveDetail(paramInt);
  }
  
  public float curvePoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    return this.g.curvePoint(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
  }
  
  public float curveTangent(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    return this.g.curveTangent(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
  }
  
  public void curveTightness(float paramFloat)
  {
    this.g.curveTightness(paramFloat);
  }
  
  public void curveVertex(float paramFloat1, float paramFloat2)
  {
    this.g.curveVertex(paramFloat1, paramFloat2);
  }
  
  public void curveVertex(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.curveVertex(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public File dataFile(String paramString)
  {
    return new File(dataPath(paramString));
  }
  
  public String dataPath(String paramString)
  {
    if (new File(paramString).isAbsolute()) {
      return paramString;
    }
    return this.sketchPath + File.separator + "data" + File.separator + paramString;
  }
  
  public void delay(int paramInt)
  {
    long l = paramInt;
    try
    {
      Thread.sleep(l);
      return;
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  protected void dequeueEvents()
  {
    for (;;)
    {
      if (!this.eventQueue.available()) {
        return;
      }
      Event localEvent = this.eventQueue.remove();
      switch (localEvent.getFlavor())
      {
      default: 
        break;
      case 1: 
        handleKeyEvent((processing.event.KeyEvent)localEvent);
        break;
      }
      handleMouseEvent((MouseEvent)localEvent);
    }
  }
  
  public void destroy()
  {
    exit();
  }
  
  public void die(String paramString)
  {
    stop();
    throw new RuntimeException(paramString);
  }
  
  public void die(String paramString, Exception paramException)
  {
    if (paramException != null) {
      paramException.printStackTrace();
    }
    die(paramString);
  }
  
  public void directionalLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.g.directionalLight(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public boolean displayable()
  {
    return this.g.displayable();
  }
  
  public final void dispose()
  {
    this.finished = true;
    if (this.thread == null) {
      return;
    }
    this.thread = null;
    if (this.g != null) {
      this.g.dispose();
    }
    handleMethods("dispose");
  }
  
  public void draw()
  {
    this.finished = true;
  }
  
  public void edge(boolean paramBoolean)
  {
    this.g.edge(paramBoolean);
  }
  
  public void ellipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.ellipse(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void ellipseMode(int paramInt)
  {
    this.g.ellipseMode(paramInt);
  }
  
  public void emissive(float paramFloat)
  {
    this.g.emissive(paramFloat);
  }
  
  public void emissive(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.emissive(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void emissive(int paramInt)
  {
    this.g.emissive(paramInt);
  }
  
  public void endCamera()
  {
    this.g.endCamera();
  }
  
  public void endContour()
  {
    this.g.endContour();
  }
  
  public void endPGL()
  {
    this.g.endPGL();
  }
  
  public void endShape()
  {
    this.g.endShape();
  }
  
  public void endShape(int paramInt)
  {
    this.g.endShape(paramInt);
  }
  
  public void exit()
  {
    if (this.thread == null) {
      exit2();
    }
    do
    {
      return;
      if (this.looping)
      {
        this.finished = true;
        this.exitCalled = true;
        return;
      }
    } while (this.looping);
    dispose();
    exit2();
  }
  
  void exit2()
  {
    try
    {
      System.exit(0);
      return;
    }
    catch (SecurityException localSecurityException) {}
  }
  
  public void fill(float paramFloat)
  {
    this.g.fill(paramFloat);
  }
  
  public void fill(float paramFloat1, float paramFloat2)
  {
    this.g.fill(paramFloat1, paramFloat2);
  }
  
  public void fill(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.fill(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void fill(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.fill(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void fill(int paramInt)
  {
    this.g.fill(paramInt);
  }
  
  public void fill(int paramInt, float paramFloat)
  {
    this.g.fill(paramInt, paramFloat);
  }
  
  public void filter(int paramInt)
  {
    this.g.filter(paramInt);
  }
  
  public void filter(int paramInt, float paramFloat)
  {
    this.g.filter(paramInt, paramFloat);
  }
  
  public void filter(PShader paramPShader)
  {
    this.g.filter(paramPShader);
  }
  
  public void flush()
  {
    this.g.flush();
  }
  
  public void focusGained() {}
  
  public void focusLost() {}
  
  public void frameRate(float paramFloat)
  {
    this.frameRateTarget = paramFloat;
    this.frameRatePeriod = ((1000000000.0D / this.frameRateTarget));
    this.g.setFrameRate(paramFloat);
  }
  
  public void frustum(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.g.frustum(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public int get(int paramInt1, int paramInt2)
  {
    return this.g.get(paramInt1, paramInt2);
  }
  
  public PImage get()
  {
    return this.g.get();
  }
  
  public PImage get(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return this.g.get(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public Object getCache(PImage paramPImage)
  {
    return this.g.getCache(paramPImage);
  }
  
  public PMatrix2D getMatrix(PMatrix2D paramPMatrix2D)
  {
    return this.g.getMatrix(paramPMatrix2D);
  }
  
  public PMatrix3D getMatrix(PMatrix3D paramPMatrix3D)
  {
    return this.g.getMatrix(paramPMatrix3D);
  }
  
  public PMatrix getMatrix()
  {
    return this.g.getMatrix();
  }
  
  public Object getNative()
  {
    return this.g.getNative();
  }
  
  public PShader getShader(int paramInt)
  {
    return this.g.getShader(paramInt);
  }
  
  public SurfaceHolder getSurfaceHolder()
  {
    return this.surfaceView.getHolder();
  }
  
  public final float green(int paramInt)
  {
    return this.g.green(paramInt);
  }
  
  public void handleDraw()
  {
    if (this.surfaceChanged)
    {
      int i = this.surfaceView.getWidth();
      int j = this.surfaceView.getHeight();
      if ((i != this.width) || (j != this.height))
      {
        this.width = i;
        this.height = j;
        this.g.setSize(this.width, this.height);
      }
      this.surfaceChanged = false;
      this.surfaceReady = true;
    }
    long l;
    if ((this.g != null) && (this.surfaceReady) && (!this.paused) && ((this.looping) || (this.redraw)))
    {
      this.g.beginDraw();
      l = System.nanoTime();
      if (this.frameCount != 0) {
        break label177;
      }
    }
    for (;;)
    {
      try
      {
        setup();
        this.g.endDraw();
        if (this.frameCount != 0) {
          handleMethods("post");
        }
        this.frameRateLastNanos = l;
        this.frameCount = (1 + this.frameCount);
        return;
      }
      catch (RendererChangeException localRendererChangeException)
      {
        return;
      }
      label177:
      float f = (float)(1000000.0D / ((l - this.frameRateLastNanos) / 1000000.0D)) / 1000.0F;
      this.frameRate = (0.9F * this.frameRate + 0.1F * f);
      if (this.frameCount != 0) {
        handleMethods("pre");
      }
      this.pmouseX = this.dmouseX;
      this.pmouseY = this.dmouseY;
      draw();
      this.dmouseX = this.mouseX;
      this.dmouseY = this.mouseY;
      dequeueEvents();
      handleMethods("draw");
      this.redraw = false;
    }
  }
  
  protected void handleKeyEvent(processing.event.KeyEvent paramKeyEvent)
  {
    this.key = paramKeyEvent.getKey();
    this.keyCode = paramKeyEvent.getKeyCode();
    switch (paramKeyEvent.getAction())
    {
    }
    for (;;)
    {
      handleMethods("keyEvent", new Object[] { paramKeyEvent });
      if ((paramKeyEvent.getAction() == 1) && (paramKeyEvent.getKeyCode() == 4)) {
        exit();
      }
      return;
      this.keyPressed = true;
      keyPressed(paramKeyEvent);
      continue;
      this.keyPressed = false;
      keyReleased(paramKeyEvent);
    }
  }
  
  protected void handleMethods(String paramString)
  {
    RegisteredMethods localRegisteredMethods = (RegisteredMethods)this.registerMap.get(paramString);
    if (localRegisteredMethods != null) {
      localRegisteredMethods.handle();
    }
  }
  
  protected void handleMethods(String paramString, Object[] paramArrayOfObject)
  {
    RegisteredMethods localRegisteredMethods = (RegisteredMethods)this.registerMap.get(paramString);
    if (localRegisteredMethods != null) {
      localRegisteredMethods.handle(paramArrayOfObject);
    }
  }
  
  protected void handleMouseEvent(MouseEvent paramMouseEvent)
  {
    if ((paramMouseEvent.getAction() == 4) || (paramMouseEvent.getAction() == 5))
    {
      this.pmouseX = this.emouseX;
      this.pmouseY = this.emouseY;
      this.mouseX = paramMouseEvent.getX();
      this.mouseY = paramMouseEvent.getY();
    }
    if (paramMouseEvent.getAction() == 1)
    {
      this.mouseX = paramMouseEvent.getX();
      this.mouseY = paramMouseEvent.getY();
      this.pmouseX = this.mouseX;
      this.pmouseY = this.mouseY;
      this.dmouseX = this.mouseX;
      this.dmouseY = this.mouseY;
    }
    switch (paramMouseEvent.getAction())
    {
    default: 
      handleMethods("mouseEvent", new Object[] { paramMouseEvent });
      switch (paramMouseEvent.getAction())
      {
      }
      break;
    }
    for (;;)
    {
      if ((paramMouseEvent.getAction() == 4) || (paramMouseEvent.getAction() == 5))
      {
        this.emouseX = this.mouseX;
        this.emouseY = this.mouseY;
      }
      if (paramMouseEvent.getAction() == 1)
      {
        this.emouseX = this.mouseX;
        this.emouseY = this.mouseY;
      }
      return;
      this.mousePressed = true;
      break;
      this.mousePressed = false;
      break;
      mousePressed(paramMouseEvent);
      continue;
      mouseReleased(paramMouseEvent);
      continue;
      mouseClicked(paramMouseEvent);
      continue;
      mouseDragged(paramMouseEvent);
      continue;
      mouseMoved(paramMouseEvent);
      continue;
      mouseEntered(paramMouseEvent);
      continue;
      mouseExited(paramMouseEvent);
    }
  }
  
  public void hint(int paramInt)
  {
    this.g.hint(paramInt);
  }
  
  public final float hue(int paramInt)
  {
    return this.g.hue(paramInt);
  }
  
  public void image(PImage paramPImage, float paramFloat1, float paramFloat2)
  {
    this.g.image(paramPImage, paramFloat1, paramFloat2);
  }
  
  public void image(PImage paramPImage, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.image(paramPImage, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void image(PImage paramPImage, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.g.image(paramPImage, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void imageMode(int paramInt)
  {
    this.g.imageMode(paramInt);
  }
  
  protected String insertFrame(String paramString)
  {
    int i = paramString.indexOf('#');
    int j = paramString.lastIndexOf('#');
    if ((i != -1) && (j - i > 0))
    {
      String str1 = paramString.substring(0, i);
      int k = 1 + (j - i);
      String str2 = paramString.substring(j + 1);
      paramString = str1 + nf(this.frameCount, k) + str2;
    }
    return paramString;
  }
  
  public boolean isGL()
  {
    return this.g.isGL();
  }
  
  public void keyPressed() {}
  
  public void keyPressed(processing.event.KeyEvent paramKeyEvent)
  {
    keyPressed();
  }
  
  public void keyReleased() {}
  
  public void keyReleased(processing.event.KeyEvent paramKeyEvent)
  {
    keyReleased();
  }
  
  public void keyTyped() {}
  
  public void keyTyped(processing.event.KeyEvent paramKeyEvent)
  {
    keyTyped();
  }
  
  public int lerpColor(int paramInt1, int paramInt2, float paramFloat)
  {
    return this.g.lerpColor(paramInt1, paramInt2, paramFloat);
  }
  
  public void lightFalloff(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.lightFalloff(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void lightSpecular(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.lightSpecular(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void lights()
  {
    this.g.lights();
  }
  
  public void line(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.line(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void line(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.g.line(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public void link(String paramString)
  {
    link(paramString, null);
  }
  
  public void link(String paramString1, String paramString2)
  {
    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString1)));
  }
  
  public byte[] loadBytes(String paramString)
  {
    InputStream localInputStream = createInput(paramString);
    if (localInputStream != null) {
      return loadBytes(localInputStream);
    }
    System.err.println("The file \"" + paramString + "\" " + "is missing or inaccessible, make sure " + "the URL is valid or that the file has been " + "added to your sketch and is readable.");
    return null;
  }
  
  public PFont loadFont(String paramString)
  {
    try
    {
      PFont localPFont = new PFont(createInput(paramString));
      return localPFont;
    }
    catch (Exception localException)
    {
      die("Could not load font " + paramString + ". " + "Make sure that the font has been copied " + "to the data folder of your sketch.", localException);
    }
    return null;
  }
  
  /* Error */
  public PImage loadImage(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual 1572	processing/core/PApplet:createInput	(Ljava/lang/String;)Ljava/io/InputStream;
    //   5: astore_2
    //   6: aload_2
    //   7: ifnonnull +34 -> 41
    //   10: getstatic 379	java/lang/System:err	Ljava/io/PrintStream;
    //   13: new 289	java/lang/StringBuilder
    //   16: dup
    //   17: ldc_w 2026
    //   20: invokespecial 298	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   23: aload_1
    //   24: invokevirtual 301	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: ldc_w 994
    //   30: invokevirtual 301	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: invokevirtual 305	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   36: invokevirtual 389	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   39: aconst_null
    //   40: areturn
    //   41: aload_2
    //   42: invokestatic 2032	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   45: astore 5
    //   47: aload_2
    //   48: invokevirtual 2035	java/io/InputStream:close	()V
    //   51: new 309	processing/core/PImage
    //   54: dup
    //   55: aload 5
    //   57: invokespecial 2037	processing/core/PImage:<init>	(Ljava/lang/Object;)V
    //   60: astore 7
    //   62: aload 7
    //   64: aload_0
    //   65: putfield 1506	processing/core/PImage:parent	Lprocessing/core/PApplet;
    //   68: aload 7
    //   70: areturn
    //   71: astore_3
    //   72: aload_2
    //   73: invokevirtual 2035	java/io/InputStream:close	()V
    //   76: aload_3
    //   77: athrow
    //   78: astore 4
    //   80: goto -4 -> 76
    //   83: astore 6
    //   85: goto -34 -> 51
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	88	0	this	PApplet
    //   0	88	1	paramString	String
    //   5	68	2	localInputStream	InputStream
    //   71	6	3	localObject	Object
    //   78	1	4	localIOException1	IOException
    //   45	11	5	localBitmap	android.graphics.Bitmap
    //   83	1	6	localIOException2	IOException
    //   60	9	7	localPImage	PImage
    // Exception table:
    //   from	to	target	type
    //   41	47	71	finally
    //   72	76	78	java/io/IOException
    //   47	51	83	java/io/IOException
  }
  
  public void loadPixels()
  {
    this.g.loadPixels();
    this.pixels = this.g.pixels;
  }
  
  public PShader loadShader(String paramString)
  {
    return this.g.loadShader(paramString);
  }
  
  public PShader loadShader(String paramString1, String paramString2)
  {
    return this.g.loadShader(paramString1, paramString2);
  }
  
  public PShape loadShape(String paramString)
  {
    return this.g.loadShape(paramString);
  }
  
  public String[] loadStrings(String paramString)
  {
    InputStream localInputStream = createInput(paramString);
    if (localInputStream != null) {
      return loadStrings(localInputStream);
    }
    System.err.println("The file \"" + paramString + "\" " + "is missing or inaccessible, make sure " + "the URL is valid or that the file has been " + "added to your sketch and is readable.");
    return null;
  }
  
  public Table loadTable(String paramString)
  {
    return loadTable(paramString, null);
  }
  
  public Table loadTable(String paramString1, String paramString2)
  {
    for (;;)
    {
      String str1;
      try
      {
        str1 = checkExtension(paramString1);
        if ((str1 != null) && ((str1.equals("csv")) || (str1.equals("tsv")))) {
          break label91;
        }
        return new Table(createInput(paramString1), paramString2);
      }
      catch (IOException localIOException)
      {
        String str2;
        localIOException.printStackTrace();
        return null;
      }
      str2 = str1 + "," + paramString2;
      paramString2 = str2;
      continue;
      label91:
      if (paramString2 == null) {
        paramString2 = str1;
      }
    }
  }
  
  public XML loadXML(String paramString)
  {
    return loadXML(paramString, null);
  }
  
  public XML loadXML(String paramString1, String paramString2)
  {
    try
    {
      XML localXML = new XML(createInput(paramString1), paramString2);
      return localXML;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public void loop()
  {
    try
    {
      if (!this.looping) {
        this.looping = true;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void mask(PImage paramPImage)
  {
    this.g.mask(paramPImage);
  }
  
  public void mask(int[] paramArrayOfInt)
  {
    this.g.mask(paramArrayOfInt);
  }
  
  public void method(String paramString)
  {
    try
    {
      getClass().getMethod(paramString, new Class[0]).invoke(this, new Object[0]);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.getTargetException().printStackTrace();
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      System.err.println("There is no public " + paramString + "() method " + "in the class " + getClass().getName());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public int millis()
  {
    return (int)(System.currentTimeMillis() - this.millisOffset);
  }
  
  public float modelX(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return this.g.modelX(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public float modelY(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return this.g.modelY(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public float modelZ(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return this.g.modelZ(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void mouseClicked() {}
  
  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    mouseClicked();
  }
  
  public void mouseDragged() {}
  
  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    mouseDragged();
  }
  
  public void mouseEntered() {}
  
  public void mouseEntered(MouseEvent paramMouseEvent)
  {
    mouseEntered();
  }
  
  public void mouseExited() {}
  
  public void mouseExited(MouseEvent paramMouseEvent)
  {
    mouseExited();
  }
  
  public void mouseMoved() {}
  
  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    mouseMoved();
  }
  
  public void mousePressed() {}
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    mousePressed();
  }
  
  public void mouseReleased() {}
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    mouseReleased();
  }
  
  protected void nativeKeyEvent(android.view.KeyEvent paramKeyEvent)
  {
    int i = (char)paramKeyEvent.getUnicodeChar();
    if ((i == 0) || (i == 65535)) {
      i = 65535;
    }
    int j = paramKeyEvent.getKeyCode();
    int k = paramKeyEvent.getAction();
    int m;
    if (k == 0) {
      m = 1;
    }
    for (;;)
    {
      processing.event.KeyEvent localKeyEvent = new processing.event.KeyEvent(paramKeyEvent, paramKeyEvent.getEventTime(), m, 0, i, j);
      if ((k == 0) && (j == 4)) {
        exit();
      }
      postEvent(localKeyEvent);
      return;
      m = 0;
      if (k == 1) {
        m = 2;
      }
    }
  }
  
  protected void nativeMotionEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getMetaState();
    int j = i & 0x1;
    int k = 0;
    if (j != 0) {
      k = 0x0 | 0x1;
    }
    if ((i & 0x1000) != 0) {
      k |= 0x2;
    }
    if ((0x10000 & i) != 0) {
      k |= 0x4;
    }
    if ((i & 0x2) != 0) {
      k |= 0x8;
    }
    switch (paramMotionEvent.getAction())
    {
    }
    int m;
    do
    {
      int n;
      do
      {
        return;
        this.motionPointerId = paramMotionEvent.getPointerId(0);
        postEvent(new MouseEvent(paramMotionEvent, paramMotionEvent.getEventTime(), 1, k, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY(), 21, 1));
        return;
        n = paramMotionEvent.findPointerIndex(this.motionPointerId);
      } while (n == -1);
      postEvent(new MouseEvent(paramMotionEvent, paramMotionEvent.getEventTime(), 4, k, (int)paramMotionEvent.getX(n), (int)paramMotionEvent.getY(n), 21, 1));
      return;
      m = paramMotionEvent.findPointerIndex(this.motionPointerId);
    } while (m == -1);
    postEvent(new MouseEvent(paramMotionEvent, paramMotionEvent.getEventTime(), 2, k, (int)paramMotionEvent.getX(m), (int)paramMotionEvent.getY(m), 21, 1));
  }
  
  public void noClip()
  {
    this.g.noClip();
  }
  
  public void noFill()
  {
    this.g.noFill();
  }
  
  public void noLights()
  {
    this.g.noLights();
  }
  
  public void noLoop()
  {
    try
    {
      if (this.looping) {
        this.looping = false;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void noSmooth()
  {
    this.g.noSmooth();
  }
  
  public void noStroke()
  {
    this.g.noStroke();
  }
  
  public void noTexture()
  {
    this.g.noTexture();
  }
  
  public void noTint()
  {
    this.g.noTint();
  }
  
  public float noise(float paramFloat)
  {
    return noise(paramFloat, 0.0F, 0.0F);
  }
  
  public float noise(float paramFloat1, float paramFloat2)
  {
    return noise(paramFloat1, paramFloat2, 0.0F);
  }
  
  public float noise(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    int i2;
    int i;
    int j;
    int k;
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;
    if (this.perlin == null)
    {
      if (this.perlinRandom == null) {
        this.perlinRandom = new Random();
      }
      this.perlin = new float[4096];
      i2 = 0;
      if (i2 >= 4096)
      {
        this.perlin_cosTable = PGraphics.cosLUT;
        this.perlin_PI = 720;
        this.perlin_TWOPI = 720;
        this.perlin_PI >>= 1;
      }
    }
    else
    {
      if (paramFloat1 < 0.0F) {
        paramFloat1 = -paramFloat1;
      }
      if (paramFloat2 < 0.0F) {
        paramFloat2 = -paramFloat2;
      }
      if (paramFloat3 < 0.0F) {
        paramFloat3 = -paramFloat3;
      }
      i = (int)paramFloat1;
      j = (int)paramFloat2;
      k = (int)paramFloat3;
      f1 = paramFloat1 - i;
      f2 = paramFloat2 - j;
      f3 = paramFloat3 - k;
      f4 = 0.0F;
      f5 = 0.5F;
    }
    for (int m = 0;; m++)
    {
      if (m >= this.perlin_octaves)
      {
        return f4;
        this.perlin[i2] = this.perlinRandom.nextFloat();
        i2++;
        break;
      }
      int n = i + (j << 4) + (k << 8);
      float f6 = noise_fsc(f1);
      float f7 = noise_fsc(f2);
      float f8 = this.perlin[(n & 0xFFF)];
      float f9 = f8 + f6 * (this.perlin[(0xFFF & n + 1)] - f8);
      float f10 = this.perlin[(0xFFF & n + 16)];
      float f11 = f9 + f7 * (f10 + f6 * (this.perlin[(0xFFF & 1 + (n + 16))] - f10) - f9);
      int i1 = n + 256;
      float f12 = this.perlin[(i1 & 0xFFF)];
      float f13 = f12 + f6 * (this.perlin[(0xFFF & i1 + 1)] - f12);
      float f14 = this.perlin[(0xFFF & i1 + 16)];
      float f15 = f13 + f7 * (f14 + f6 * (this.perlin[(0xFFF & 1 + (i1 + 16))] - f14) - f13);
      f4 += f5 * (f11 + noise_fsc(f3) * (f15 - f11));
      f5 *= this.perlin_amp_falloff;
      i <<= 1;
      f1 *= 2.0F;
      j <<= 1;
      f2 *= 2.0F;
      k <<= 1;
      f3 *= 2.0F;
      if (f1 >= 1.0F)
      {
        i++;
        f1 -= 1.0F;
      }
      if (f2 >= 1.0F)
      {
        j++;
        f2 -= 1.0F;
      }
      if (f3 >= 1.0F)
      {
        k++;
        f3 -= 1.0F;
      }
    }
  }
  
  public void noiseDetail(int paramInt)
  {
    if (paramInt > 0) {
      this.perlin_octaves = paramInt;
    }
  }
  
  public void noiseDetail(int paramInt, float paramFloat)
  {
    if (paramInt > 0) {
      this.perlin_octaves = paramInt;
    }
    if (paramFloat > 0.0F) {
      this.perlin_amp_falloff = paramFloat;
    }
  }
  
  public void noiseSeed(long paramLong)
  {
    if (this.perlinRandom == null) {
      this.perlinRandom = new Random();
    }
    this.perlinRandom.setSeed(paramLong);
    this.perlin = null;
  }
  
  public void normal(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.normal(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Window localWindow = getWindow();
    requestWindowFeature(1);
    localWindow.setFlags(256, 256);
    localWindow.setFlags(1024, 1024);
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    this.displayWidth = localDisplayMetrics.widthPixels;
    this.displayHeight = localDisplayMetrics.heightPixels;
    int i = sketchWidth();
    int j = sketchHeight();
    if (sketchRenderer().equals("processing.core.PGraphicsAndroid2D"))
    {
      this.surfaceView = new SketchSurfaceView(this, i, j);
      if ((i != this.displayWidth) || (j != this.displayHeight)) {
        break label246;
      }
      localWindow.setContentView(this.surfaceView);
    }
    for (;;)
    {
      this.finished = false;
      this.looping = true;
      this.redraw = true;
      this.sketchPath = getApplicationContext().getFilesDir().getAbsolutePath();
      this.handler = new Handler();
      start();
      return;
      if ((!sketchRenderer().equals("processing.opengl.PGraphics2D")) && (!sketchRenderer().equals("processing.opengl.PGraphics3D"))) {
        break;
      }
      this.surfaceView = new SketchSurfaceViewGL(this, i, j, sketchRenderer().equals("processing.opengl.PGraphics3D"));
      break;
      label246:
      RelativeLayout localRelativeLayout = new RelativeLayout(this);
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
      localLayoutParams.addRule(13);
      LinearLayout localLinearLayout = new LinearLayout(this);
      localLinearLayout.addView(this.surfaceView, sketchWidth(), sketchHeight());
      localRelativeLayout.addView(localLinearLayout, localLayoutParams);
      localWindow.setContentView(localRelativeLayout);
    }
  }
  
  public void onDestroy()
  {
    dispose();
    super.onDestroy();
  }
  
  protected void onPause()
  {
    super.onPause();
    this.paused = true;
    handleMethods("pause");
    pause();
  }
  
  protected void onResume()
  {
    super.onResume();
    this.paused = false;
    handleMethods("resume");
    resume();
  }
  
  protected void onStart()
  {
    tellPDE("onStart");
    super.onStart();
  }
  
  protected void onStop()
  {
    tellPDE("onStop");
    super.onStop();
  }
  
  public void orientation(int paramInt)
  {
    if (paramInt == 1) {
      setRequestedOrientation(1);
    }
    while (paramInt != 2) {
      return;
    }
    setRequestedOrientation(0);
  }
  
  public void ortho()
  {
    this.g.ortho();
  }
  
  public void ortho(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.ortho(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void ortho(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.g.ortho(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public XML parseXML(String paramString)
  {
    return parseXML(paramString, null);
  }
  
  public XML parseXML(String paramString1, String paramString2)
  {
    try
    {
      XML localXML = XML.parse(paramString1, paramString2);
      return localXML;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public void pause() {}
  
  public void perspective()
  {
    this.g.perspective();
  }
  
  public void perspective(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.perspective(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void point(float paramFloat1, float paramFloat2)
  {
    this.g.point(paramFloat1, paramFloat2);
  }
  
  public void point(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.point(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void pointLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.g.pointLight(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public void popMatrix()
  {
    this.g.popMatrix();
  }
  
  public void popStyle()
  {
    this.g.popStyle();
  }
  
  public void postEvent(Event paramEvent)
  {
    this.eventQueue.add(paramEvent);
    if (!this.looping) {
      dequeueEvents();
    }
  }
  
  public void printCamera()
  {
    this.g.printCamera();
  }
  
  public void printMatrix()
  {
    this.g.printMatrix();
  }
  
  public void printProjection()
  {
    this.g.printProjection();
  }
  
  public void pushMatrix()
  {
    this.g.pushMatrix();
  }
  
  public void pushStyle()
  {
    this.g.pushStyle();
  }
  
  public void quad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    this.g.quad(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
  }
  
  public void quadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.quadraticVertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void quadraticVertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.g.quadraticVertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  public final float random(float paramFloat)
  {
    if (paramFloat == 0.0F) {
      return 0.0F;
    }
    if (this.internalRandom == null) {
      this.internalRandom = new Random();
    }
    float f;
    do
    {
      f = paramFloat * this.internalRandom.nextFloat();
    } while (f == paramFloat);
    return f;
  }
  
  public final float random(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 >= paramFloat2) {
      return paramFloat1;
    }
    return paramFloat1 + random(paramFloat2 - paramFloat1);
  }
  
  public final void randomSeed(long paramLong)
  {
    if (this.internalRandom == null) {
      this.internalRandom = new Random();
    }
    this.internalRandom.setSeed(paramLong);
  }
  
  public void rect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.rect(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void rect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    this.g.rect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
  }
  
  public void rect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
  {
    this.g.rect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
  }
  
  public void rectMode(int paramInt)
  {
    this.g.rectMode(paramInt);
  }
  
  public final float red(int paramInt)
  {
    return this.g.red(paramInt);
  }
  
  public void redraw()
  {
    try
    {
      if (!this.looping) {
        this.redraw = true;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  @Deprecated
  public void registerDispose(Object paramObject)
  {
    registerNoArgs("dispose", paramObject);
  }
  
  @Deprecated
  public void registerDraw(Object paramObject)
  {
    registerNoArgs("draw", paramObject);
  }
  
  public void registerMethod(String paramString, Object paramObject)
  {
    if (paramString.equals("mouseEvent"))
    {
      registerWithArgs("mouseEvent", paramObject, new Class[] { MouseEvent.class });
      return;
    }
    if (paramString.equals("keyEvent"))
    {
      registerWithArgs("keyEvent", paramObject, new Class[] { processing.event.KeyEvent.class });
      return;
    }
    if (paramString.equals("touchEvent"))
    {
      registerWithArgs("touchEvent", paramObject, new Class[] { TouchEvent.class });
      return;
    }
    registerNoArgs(paramString, paramObject);
  }
  
  @Deprecated
  public void registerPost(Object paramObject)
  {
    registerNoArgs("post", paramObject);
  }
  
  @Deprecated
  public void registerPre(Object paramObject)
  {
    registerNoArgs("pre", paramObject);
  }
  
  @Deprecated
  public void registerSize(Object paramObject)
  {
    System.err.println("The registerSize() command is no longer supported.");
  }
  
  public void removeCache(PImage paramPImage)
  {
    this.g.removeCache(paramPImage);
  }
  
  public PImage requestImage(String paramString)
  {
    PImage localPImage = createImage(0, 0, 2);
    new AsyncImageLoader(paramString, localPImage).start();
    return localPImage;
  }
  
  public void resetMatrix()
  {
    this.g.resetMatrix();
  }
  
  public void resetShader()
  {
    this.g.resetShader();
  }
  
  public void resetShader(int paramInt)
  {
    this.g.resetShader(paramInt);
  }
  
  public void resume() {}
  
  public void rotate(float paramFloat)
  {
    this.g.rotate(paramFloat);
  }
  
  public void rotate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.rotate(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void rotateX(float paramFloat)
  {
    this.g.rotateX(paramFloat);
  }
  
  public void rotateY(float paramFloat)
  {
    this.g.rotateY(paramFloat);
  }
  
  public void rotateZ(float paramFloat)
  {
    this.g.rotateZ(paramFloat);
  }
  
  public void run()
  {
    long l1 = System.nanoTime();
    long l2 = 0L;
    for (;;)
    {
      if ((Thread.currentThread() != this.thread) || (this.finished))
      {
        if (!this.paused)
        {
          stop();
          if (this.exitCalled) {
            exit2();
          }
        }
        return;
      }
      try
      {
        do
        {
          Thread.sleep(100L);
        } while (this.paused);
        if (this.g != null) {
          this.g.requestDraw();
        }
        l3 = System.nanoTime();
        long l4 = l3 - l1;
        l5 = this.frameRatePeriod - l4 - l2;
        if (l5 <= 0L) {}
      }
      catch (InterruptedException localInterruptedException2)
      {
        try
        {
          long l3;
          long l5;
          Thread.sleep(l5 / 1000000L, (int)(l5 % 1000000L));
          label118:
          l2 = System.nanoTime() - l3 - l5;
          for (;;)
          {
            l1 = System.nanoTime();
            break;
            l2 = 0L;
            if (15 < 0) {
              Thread.yield();
            }
          }
          localInterruptedException2 = localInterruptedException2;
        }
        catch (InterruptedException localInterruptedException1)
        {
          break label118;
        }
      }
    }
  }
  
  public final float saturation(int paramInt)
  {
    return this.g.saturation(paramInt);
  }
  
  public void save(String paramString)
  {
    this.g.save(savePath(paramString));
  }
  
  public void saveBytes(String paramString, byte[] paramArrayOfByte)
  {
    saveBytes(saveFile(paramString), paramArrayOfByte);
  }
  
  public File saveFile(String paramString)
  {
    return new File(savePath(paramString));
  }
  
  public void saveFrame()
  {
    try
    {
      this.g.save(savePath("screen-" + nf(this.frameCount, 4) + ".tif"));
      return;
    }
    catch (SecurityException localSecurityException)
    {
      System.err.println("Can't use saveFrame() when running in a browser, unless using a signed applet.");
    }
  }
  
  public void saveFrame(String paramString)
  {
    try
    {
      this.g.save(savePath(insertFrame(paramString)));
      return;
    }
    catch (SecurityException localSecurityException)
    {
      System.err.println("Can't use saveFrame() when running in a browser, unless using a signed applet.");
    }
  }
  
  public String savePath(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    String str = sketchPath(paramString);
    createPath(str);
    return str;
  }
  
  public boolean saveStream(File paramFile, String paramString)
  {
    return saveStream(paramFile, createInputRaw(paramString));
  }
  
  public boolean saveStream(String paramString, InputStream paramInputStream)
  {
    return saveStream(saveFile(paramString), paramInputStream);
  }
  
  public boolean saveStream(String paramString1, String paramString2)
  {
    return saveStream(saveFile(paramString1), paramString2);
  }
  
  public void saveStrings(String paramString, String[] paramArrayOfString)
  {
    saveStrings(saveFile(paramString), paramArrayOfString);
  }
  
  public boolean saveTable(Table paramTable, String paramString)
  {
    return saveTable(paramTable, paramString, null);
  }
  
  public boolean saveTable(Table paramTable, String paramString1, String paramString2)
  {
    try
    {
      paramTable.save(saveFile(paramString1), paramString2);
      return true;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return false;
  }
  
  public boolean saveXML(XML paramXML, String paramString)
  {
    return saveXML(paramXML, paramString, null);
  }
  
  public boolean saveXML(XML paramXML, String paramString1, String paramString2)
  {
    return paramXML.save(saveFile(paramString1), paramString2);
  }
  
  public void scale(float paramFloat)
  {
    this.g.scale(paramFloat);
  }
  
  public void scale(float paramFloat1, float paramFloat2)
  {
    this.g.scale(paramFloat1, paramFloat2);
  }
  
  public void scale(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.scale(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public float screenX(float paramFloat1, float paramFloat2)
  {
    return this.g.screenX(paramFloat1, paramFloat2);
  }
  
  public float screenX(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return this.g.screenX(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public float screenY(float paramFloat1, float paramFloat2)
  {
    return this.g.screenY(paramFloat1, paramFloat2);
  }
  
  public float screenY(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return this.g.screenY(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public float screenZ(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    return this.g.screenZ(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void set(int paramInt1, int paramInt2, int paramInt3)
  {
    this.g.set(paramInt1, paramInt2, paramInt3);
  }
  
  public void set(int paramInt1, int paramInt2, PImage paramPImage)
  {
    this.g.set(paramInt1, paramInt2, paramPImage);
  }
  
  public void setCache(PImage paramPImage, Object paramObject)
  {
    this.g.setCache(paramPImage, paramObject);
  }
  
  public void setMatrix(PMatrix2D paramPMatrix2D)
  {
    this.g.setMatrix(paramPMatrix2D);
  }
  
  public void setMatrix(PMatrix3D paramPMatrix3D)
  {
    this.g.setMatrix(paramPMatrix3D);
  }
  
  public void setMatrix(PMatrix paramPMatrix)
  {
    this.g.setMatrix(paramPMatrix);
  }
  
  public void setup() {}
  
  public void shader(PShader paramPShader)
  {
    this.g.shader(paramPShader);
  }
  
  public void shader(PShader paramPShader, int paramInt)
  {
    this.g.shader(paramPShader, paramInt);
  }
  
  public void shape(PShape paramPShape)
  {
    this.g.shape(paramPShape);
  }
  
  public void shape(PShape paramPShape, float paramFloat1, float paramFloat2)
  {
    this.g.shape(paramPShape, paramFloat1, paramFloat2);
  }
  
  public void shape(PShape paramPShape, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.shape(paramPShape, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void shapeMode(int paramInt)
  {
    this.g.shapeMode(paramInt);
  }
  
  public void shearX(float paramFloat)
  {
    this.g.shearX(paramFloat);
  }
  
  public void shearY(float paramFloat)
  {
    this.g.shearY(paramFloat);
  }
  
  public void shininess(float paramFloat)
  {
    this.g.shininess(paramFloat);
  }
  
  public void size(int paramInt1, int paramInt2)
  {
    size(paramInt1, paramInt2, "processing.opengl.PGraphics2D", null);
  }
  
  public void size(int paramInt1, int paramInt2, String paramString)
  {
    size(paramInt1, paramInt2, paramString, null);
  }
  
  public void size(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    System.out.println("This size() method is ignored on Android.");
    System.out.println("See http://wiki.processing.org/w/Android for more information.");
  }
  
  public File sketchFile(String paramString)
  {
    return new File(sketchPath(paramString));
  }
  
  public int sketchHeight()
  {
    return this.displayHeight;
  }
  
  public String sketchPath(String paramString)
  {
    if (this.sketchPath == null) {}
    for (;;)
    {
      return paramString;
      try
      {
        boolean bool = new File(paramString).isAbsolute();
        if (bool) {}
      }
      catch (Exception localException)
      {
        label25:
        break label25;
      }
    }
    return getApplicationContext().getFileStreamPath(paramString).getAbsolutePath();
  }
  
  public int sketchQuality()
  {
    return 1;
  }
  
  public String sketchRenderer()
  {
    return "processing.core.PGraphicsAndroid2D";
  }
  
  public int sketchWidth()
  {
    return this.displayWidth;
  }
  
  public void smooth()
  {
    this.g.smooth();
  }
  
  public void smooth(int paramInt)
  {
    this.g.smooth(paramInt);
  }
  
  public void specular(float paramFloat)
  {
    this.g.specular(paramFloat);
  }
  
  public void specular(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.specular(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void specular(int paramInt)
  {
    this.g.specular(paramInt);
  }
  
  public void sphere(float paramFloat)
  {
    this.g.sphere(paramFloat);
  }
  
  public void sphereDetail(int paramInt)
  {
    this.g.sphereDetail(paramInt);
  }
  
  public void sphereDetail(int paramInt1, int paramInt2)
  {
    this.g.sphereDetail(paramInt1, paramInt2);
  }
  
  public void spotLight(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11)
  {
    this.g.spotLight(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11);
  }
  
  public void start()
  {
    this.finished = false;
    this.paused = false;
    if (this.thread == null)
    {
      this.thread = new Thread(this, "Animation Thread");
      this.thread.start();
    }
  }
  
  public void stop()
  {
    this.paused = true;
  }
  
  public void stroke(float paramFloat)
  {
    this.g.stroke(paramFloat);
  }
  
  public void stroke(float paramFloat1, float paramFloat2)
  {
    this.g.stroke(paramFloat1, paramFloat2);
  }
  
  public void stroke(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.stroke(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void stroke(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.stroke(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void stroke(int paramInt)
  {
    this.g.stroke(paramInt);
  }
  
  public void stroke(int paramInt, float paramFloat)
  {
    this.g.stroke(paramInt, paramFloat);
  }
  
  public void strokeCap(int paramInt)
  {
    this.g.strokeCap(paramInt);
  }
  
  public void strokeJoin(int paramInt)
  {
    this.g.strokeJoin(paramInt);
  }
  
  public void strokeWeight(float paramFloat)
  {
    this.g.strokeWeight(paramFloat);
  }
  
  public void style(PStyle paramPStyle)
  {
    this.g.style(paramPStyle);
  }
  
  public boolean surfaceKeyDown(int paramInt, android.view.KeyEvent paramKeyEvent)
  {
    nativeKeyEvent(paramKeyEvent);
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean surfaceKeyUp(int paramInt, android.view.KeyEvent paramKeyEvent)
  {
    nativeKeyEvent(paramKeyEvent);
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  public boolean surfaceTouchEvent(MotionEvent paramMotionEvent)
  {
    nativeMotionEvent(paramMotionEvent);
    return true;
  }
  
  public void surfaceWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
    this.focused = paramBoolean;
    if (this.focused)
    {
      focusGained();
      return;
    }
    focusLost();
  }
  
  public void text(char paramChar, float paramFloat1, float paramFloat2)
  {
    this.g.text(paramChar, paramFloat1, paramFloat2);
  }
  
  public void text(char paramChar, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.text(paramChar, paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void text(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.text(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void text(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.text(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void text(int paramInt, float paramFloat1, float paramFloat2)
  {
    this.g.text(paramInt, paramFloat1, paramFloat2);
  }
  
  public void text(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.text(paramInt, paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void text(String paramString, float paramFloat1, float paramFloat2)
  {
    this.g.text(paramString, paramFloat1, paramFloat2);
  }
  
  public void text(String paramString, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.text(paramString, paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void text(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.text(paramString, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void textAlign(int paramInt)
  {
    this.g.textAlign(paramInt);
  }
  
  public void textAlign(int paramInt1, int paramInt2)
  {
    this.g.textAlign(paramInt1, paramInt2);
  }
  
  public float textAscent()
  {
    return this.g.textAscent();
  }
  
  public float textDescent()
  {
    return this.g.textDescent();
  }
  
  public void textFont(PFont paramPFont)
  {
    this.g.textFont(paramPFont);
  }
  
  public void textFont(PFont paramPFont, float paramFloat)
  {
    this.g.textFont(paramPFont, paramFloat);
  }
  
  public void textLeading(float paramFloat)
  {
    this.g.textLeading(paramFloat);
  }
  
  public void textMode(int paramInt)
  {
    this.g.textMode(paramInt);
  }
  
  public void textSize(float paramFloat)
  {
    this.g.textSize(paramFloat);
  }
  
  public float textWidth(char paramChar)
  {
    return this.g.textWidth(paramChar);
  }
  
  public float textWidth(String paramString)
  {
    return this.g.textWidth(paramString);
  }
  
  public void texture(PImage paramPImage)
  {
    this.g.texture(paramPImage);
  }
  
  public void textureMode(int paramInt)
  {
    this.g.textureMode(paramInt);
  }
  
  public void textureWrap(int paramInt)
  {
    this.g.textureWrap(paramInt);
  }
  
  public void thread(final String paramString)
  {
    new Thread()
    {
      public void run()
      {
        PApplet.this.method(paramString);
      }
    }.start();
  }
  
  public void tint(float paramFloat)
  {
    this.g.tint(paramFloat);
  }
  
  public void tint(float paramFloat1, float paramFloat2)
  {
    this.g.tint(paramFloat1, paramFloat2);
  }
  
  public void tint(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.tint(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void tint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.tint(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void tint(int paramInt)
  {
    this.g.tint(paramInt);
  }
  
  public void tint(int paramInt, float paramFloat)
  {
    this.g.tint(paramInt, paramFloat);
  }
  
  public void translate(float paramFloat1, float paramFloat2)
  {
    this.g.translate(paramFloat1, paramFloat2);
  }
  
  public void translate(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.translate(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void triangle(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    this.g.triangle(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
  }
  
  @Deprecated
  public void unregisterDispose(Object paramObject)
  {
    unregisterMethod("dispose", paramObject);
  }
  
  @Deprecated
  public void unregisterDraw(Object paramObject)
  {
    unregisterMethod("draw", paramObject);
  }
  
  public void unregisterMethod(String paramString, Object paramObject)
  {
    RegisteredMethods localRegisteredMethods = (RegisteredMethods)this.registerMap.get(paramString);
    if (localRegisteredMethods == null) {
      die("No registered methods with the name " + paramString + "() were found.");
    }
    try
    {
      localRegisteredMethods.remove(paramObject);
      return;
    }
    catch (Exception localException)
    {
      die("Could not unregister " + paramString + "() for " + paramObject, localException);
    }
  }
  
  @Deprecated
  public void unregisterPost(Object paramObject)
  {
    unregisterMethod("post", paramObject);
  }
  
  @Deprecated
  public void unregisterPre(Object paramObject)
  {
    unregisterMethod("pre", paramObject);
  }
  
  @Deprecated
  public void unregisterSize(Object paramObject)
  {
    System.err.println("The unregisterSize() command is no longer supported.");
  }
  
  public void updatePixels()
  {
    this.g.updatePixels();
  }
  
  public void updatePixels(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.g.updatePixels(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void vertex(float paramFloat1, float paramFloat2)
  {
    this.g.vertex(paramFloat1, paramFloat2);
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.g.vertex(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.g.vertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void vertex(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    this.g.vertex(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
  }
  
  public void vertex(float[] paramArrayOfFloat)
  {
    this.g.vertex(paramArrayOfFloat);
  }
  
  class AsyncImageLoader
    extends Thread
  {
    String filename;
    PImage vessel;
    
    public AsyncImageLoader(String paramString, PImage paramPImage)
    {
      this.filename = paramString;
      this.vessel = paramPImage;
    }
    
    public void run()
    {
      PImage localPImage;
      if (PApplet.this.requestImageCount != PApplet.this.requestImageMax)
      {
        PApplet localPApplet1 = PApplet.this;
        localPApplet1.requestImageCount = (1 + localPApplet1.requestImageCount);
        localPImage = PApplet.this.loadImage(this.filename);
        if (localPImage != null) {
          break label96;
        }
        this.vessel.width = -1;
        this.vessel.height = -1;
      }
      for (;;)
      {
        for (;;)
        {
          PApplet localPApplet2 = PApplet.this;
          localPApplet2.requestImageCount = (-1 + localPApplet2.requestImageCount);
          return;
          try
          {
            Thread.sleep(10L);
          }
          catch (InterruptedException localInterruptedException) {}
        }
        break;
        label96:
        this.vessel.width = localPImage.width;
        this.vessel.height = localPImage.height;
        this.vessel.format = localPImage.format;
        this.vessel.pixels = localPImage.pixels;
        this.vessel.bitmap = localPImage.bitmap;
      }
    }
  }
  
  class InternalEventQueue
  {
    protected int count;
    protected int offset;
    protected Event[] queue = new Event[10];
    
    InternalEventQueue() {}
    
    void add(Event paramEvent)
    {
      try
      {
        if (this.count == this.queue.length) {
          this.queue = ((Event[])PApplet.expand(this.queue));
        }
        Event[] arrayOfEvent = this.queue;
        int i = this.count;
        this.count = (i + 1);
        arrayOfEvent[i] = paramEvent;
        return;
      }
      finally {}
    }
    
    /* Error */
    boolean available()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 26	processing/core/PApplet$InternalEventQueue:count	I
      //   6: istore_2
      //   7: iload_2
      //   8: ifeq +9 -> 17
      //   11: iconst_1
      //   12: istore_3
      //   13: aload_0
      //   14: monitorexit
      //   15: iload_3
      //   16: ireturn
      //   17: iconst_0
      //   18: istore_3
      //   19: goto -6 -> 13
      //   22: astore_1
      //   23: aload_0
      //   24: monitorexit
      //   25: aload_1
      //   26: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	27	0	this	InternalEventQueue
      //   22	4	1	localObject	Object
      //   6	2	2	i	int
      //   12	7	3	bool	boolean
      // Exception table:
      //   from	to	target	type
      //   2	7	22	finally
    }
    
    Event remove()
    {
      try
      {
        if (this.offset == this.count) {
          throw new RuntimeException("Nothing left on the event queue.");
        }
      }
      finally {}
      Event[] arrayOfEvent = this.queue;
      int i = this.offset;
      this.offset = (i + 1);
      Event localEvent = arrayOfEvent[i];
      if (this.offset == this.count)
      {
        this.offset = 0;
        this.count = 0;
      }
      return localEvent;
    }
  }
  
  class RegisteredMethods
  {
    int count;
    Object[] emptyArgs = new Object[0];
    Method[] methods;
    Object[] objects;
    
    RegisteredMethods() {}
    
    void add(Object paramObject, Method paramMethod)
    {
      if (findIndex(paramObject) == -1)
      {
        if (this.objects == null)
        {
          this.objects = new Object[5];
          this.methods = new Method[5];
        }
        for (;;)
        {
          this.objects[this.count] = paramObject;
          this.methods[this.count] = paramMethod;
          this.count = (1 + this.count);
          return;
          if (this.count == this.objects.length)
          {
            this.objects = ((Object[])PApplet.expand(this.objects));
            this.methods = ((Method[])PApplet.expand(this.methods));
          }
        }
      }
      PApplet.this.die(paramMethod.getName() + "() already added for this instance of " + paramObject.getClass().getName());
    }
    
    protected int findIndex(Object paramObject)
    {
      for (int i = 0;; i++)
      {
        if (i >= this.count) {
          i = -1;
        }
        while (this.objects[i] == paramObject) {
          return i;
        }
      }
    }
    
    void handle()
    {
      handle(this.emptyArgs);
    }
    
    void handle(Object[] paramArrayOfObject)
    {
      int i = 0;
      for (;;)
      {
        if (i >= this.count) {
          return;
        }
        try
        {
          this.methods[i].invoke(this.objects[i], paramArrayOfObject);
          i++;
        }
        catch (Exception localException)
        {
          for (;;)
          {
            if ((localException instanceof InvocationTargetException)) {}
            for (Object localObject = ((InvocationTargetException)localException).getCause(); (localObject instanceof RuntimeException); localObject = localException) {
              throw ((RuntimeException)localObject);
            }
            ((Throwable)localObject).printStackTrace();
          }
        }
      }
    }
    
    public void remove(Object paramObject)
    {
      int i = findIndex(paramObject);
      if (i != -1) {
        this.count = (-1 + this.count);
      }
      for (int j = i;; j++)
      {
        if (j >= this.count)
        {
          this.objects[this.count] = null;
          this.methods[this.count] = null;
          return;
        }
        this.objects[j] = this.objects[(j + 1)];
        this.methods[j] = this.methods[(j + 1)];
      }
    }
  }
  
  public static class RendererChangeException
    extends RuntimeException
  {}
  
  public class SketchSurfaceView
    extends SurfaceView
    implements SurfaceHolder.Callback
  {
    PGraphicsAndroid2D g2;
    SurfaceHolder surfaceHolder = getHolder();
    
    public SketchSurfaceView(Context paramContext, int paramInt1, int paramInt2)
    {
      super();
      this.surfaceHolder.addCallback(this);
      this.surfaceHolder.setType(2);
      this.g2 = new PGraphicsAndroid2D();
      this.g2.setSize(paramInt1, paramInt2);
      this.g2.setParent(PApplet.this);
      this.g2.setPrimary(true);
      PApplet.this.g = this.g2;
      setFocusable(true);
      setFocusableInTouchMode(true);
      requestFocus();
    }
    
    public boolean onKeyDown(int paramInt, android.view.KeyEvent paramKeyEvent)
    {
      return PApplet.this.surfaceKeyDown(paramInt, paramKeyEvent);
    }
    
    public boolean onKeyUp(int paramInt, android.view.KeyEvent paramKeyEvent)
    {
      return PApplet.this.surfaceKeyUp(paramInt, paramKeyEvent);
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      return PApplet.this.surfaceTouchEvent(paramMotionEvent);
    }
    
    public void onWindowFocusChanged(boolean paramBoolean)
    {
      PApplet.this.surfaceWindowFocusChanged(paramBoolean);
    }
    
    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
    {
      PApplet.this.surfaceChanged = true;
    }
    
    public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {}
    
    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {}
  }
  
  public class SketchSurfaceViewGL
    extends GLSurfaceView
  {
    PGraphicsOpenGL g3;
    SurfaceHolder surfaceHolder;
    
    public SketchSurfaceViewGL(Context paramContext, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      super();
      if (((ActivityManager)PApplet.this.getSystemService("activity")).getDeviceConfigurationInfo().reqGlEsVersion >= 131072) {}
      for (int i = 1; i == 0; i = 0) {
        throw new RuntimeException("OpenGL ES 2.0 is not supported by this device.");
      }
      this.surfaceHolder = getHolder();
      this.surfaceHolder.addCallback(this);
      if (paramBoolean) {}
      for (this.g3 = new PGraphics3D();; this.g3 = new PGraphics2D())
      {
        this.g3.setParent(PApplet.this);
        this.g3.setPrimary(true);
        this.g3.setSize(paramInt1, paramInt2);
        setEGLContextClientVersion(2);
        setRenderer(this.g3.pgl.getRenderer());
        setRenderMode(0);
        PApplet.this.g = this.g3;
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        return;
      }
    }
    
    public PGraphics getGraphics()
    {
      return this.g3;
    }
    
    public boolean onKeyDown(int paramInt, android.view.KeyEvent paramKeyEvent)
    {
      return PApplet.this.surfaceKeyDown(paramInt, paramKeyEvent);
    }
    
    public boolean onKeyUp(int paramInt, android.view.KeyEvent paramKeyEvent)
    {
      return PApplet.this.surfaceKeyUp(paramInt, paramKeyEvent);
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      return PApplet.this.surfaceTouchEvent(paramMotionEvent);
    }
    
    public void onWindowFocusChanged(boolean paramBoolean)
    {
      PApplet.this.surfaceWindowFocusChanged(paramBoolean);
    }
    
    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
    {
      super.surfaceChanged(paramSurfaceHolder, paramInt1, paramInt2, paramInt3);
      PApplet.this.surfaceChanged = true;
    }
    
    public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
    {
      super.surfaceCreated(paramSurfaceHolder);
    }
    
    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
    {
      super.surfaceDestroyed(paramSurfaceHolder);
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PApplet
 * JD-Core Version:    0.7.0.1
 */