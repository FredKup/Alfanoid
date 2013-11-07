package processing.core;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

public class PShapeOBJ
  extends PShape
{
  public PShapeOBJ(PApplet paramPApplet, BufferedReader paramBufferedReader)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    ArrayList localArrayList3 = new ArrayList();
    ArrayList localArrayList4 = new ArrayList();
    ArrayList localArrayList5 = new ArrayList();
    parseOBJ(paramPApplet, paramBufferedReader, localArrayList1, localArrayList2, localArrayList3, localArrayList4, localArrayList5);
    this.family = 0;
    addChildren(localArrayList1, localArrayList2, localArrayList3, localArrayList4, localArrayList5);
  }
  
  public PShapeOBJ(PApplet paramPApplet, String paramString)
  {
    this(paramPApplet, paramPApplet.createReader(paramString));
  }
  
  protected PShapeOBJ(OBJFace paramOBJFace, OBJMaterial paramOBJMaterial, ArrayList<PVector> paramArrayList1, ArrayList<PVector> paramArrayList2, ArrayList<PVector> paramArrayList3)
  {
    this.family = 3;
    if (paramOBJFace.vertIdx.size() == 3)
    {
      this.kind = 9;
      this.stroke = false;
      this.fill = true;
      this.fillColor = rgbaValue(paramOBJMaterial.kd);
      this.ambientColor = rgbaValue(paramOBJMaterial.ka);
      this.specularColor = rgbaValue(paramOBJMaterial.ks);
      this.shininess = paramOBJMaterial.ns;
      if (paramOBJMaterial.kdMap != null) {
        this.tintColor = rgbaValue(paramOBJMaterial.kd, paramOBJMaterial.d);
      }
      this.vertexCount = paramOBJFace.vertIdx.size();
      int[] arrayOfInt = { this.vertexCount, 12 };
      this.vertices = ((float[][])Array.newInstance(Float.TYPE, arrayOfInt));
    }
    for (int i = 0;; i++)
    {
      if (i >= paramOBJFace.vertIdx.size())
      {
        return;
        if (paramOBJFace.vertIdx.size() == 4)
        {
          this.kind = 17;
          break;
        }
        this.kind = 20;
        break;
      }
      PVector localPVector1 = (PVector)paramArrayList1.get(-1 + ((Integer)paramOBJFace.vertIdx.get(i)).intValue());
      int j = paramOBJFace.normIdx.size();
      PVector localPVector2 = null;
      if (i < j)
      {
        int n = -1 + ((Integer)paramOBJFace.normIdx.get(i)).intValue();
        localPVector2 = null;
        if (-1 < n) {
          localPVector2 = (PVector)paramArrayList2.get(n);
        }
      }
      this.vertices[i][0] = localPVector1.x;
      this.vertices[i][1] = localPVector1.y;
      this.vertices[i][2] = localPVector1.z;
      this.vertices[i][3] = paramOBJMaterial.kd.x;
      this.vertices[i][4] = paramOBJMaterial.kd.y;
      this.vertices[i][5] = paramOBJMaterial.kd.z;
      this.vertices[i][6] = 1.0F;
      if (localPVector2 != null)
      {
        this.vertices[i][9] = localPVector2.x;
        this.vertices[i][10] = localPVector2.y;
        this.vertices[i][11] = localPVector2.z;
      }
      if ((paramOBJMaterial != null) && (paramOBJMaterial.kdMap != null))
      {
        int k = paramOBJFace.texIdx.size();
        PVector localPVector3 = null;
        if (i < k)
        {
          int m = -1 + ((Integer)paramOBJFace.texIdx.get(i)).intValue();
          localPVector3 = null;
          if (-1 < m) {
            localPVector3 = (PVector)paramArrayList3.get(m);
          }
        }
        this.image = paramOBJMaterial.kdMap;
        if (localPVector3 != null)
        {
          this.vertices[i][7] = localPVector3.x;
          this.vertices[i][8] = localPVector3.y;
        }
      }
    }
  }
  
  protected static void parseMTL(PApplet paramPApplet, BufferedReader paramBufferedReader, ArrayList<OBJMaterial> paramArrayList, Hashtable<String, Integer> paramHashtable)
  {
    Object localObject = null;
    for (;;)
    {
      String[] arrayOfString;
      String str2;
      OBJMaterial localOBJMaterial;
      try
      {
        String str1 = paramBufferedReader.readLine();
        if (str1 == null) {
          return;
        }
        arrayOfString = str1.trim().split("\\s+");
        if (arrayOfString.length <= 0) {
          continue;
        }
        if (arrayOfString[0].equals("newmtl"))
        {
          str2 = arrayOfString[1];
          localOBJMaterial = new OBJMaterial(str2);
        }
      }
      catch (Exception localException1) {}
      try
      {
        paramHashtable.put(str2, new Integer(paramArrayList.size()));
        paramArrayList.add(localOBJMaterial);
        localObject = localOBJMaterial;
      }
      catch (Exception localException2)
      {
        label137:
        break label137;
      }
      if ((arrayOfString[0].equals("map_Kd")) && (arrayOfString.length > 1))
      {
        localObject.kdMap = paramPApplet.loadImage(arrayOfString[1]);
        continue;
        localException1.printStackTrace();
      }
      else if ((arrayOfString[0].equals("Ka")) && (arrayOfString.length > 3))
      {
        localObject.ka.x = Float.valueOf(arrayOfString[1]).floatValue();
        localObject.ka.y = Float.valueOf(arrayOfString[2]).floatValue();
        localObject.ka.z = Float.valueOf(arrayOfString[3]).floatValue();
      }
      else if ((arrayOfString[0].equals("Kd")) && (arrayOfString.length > 3))
      {
        localObject.kd.x = Float.valueOf(arrayOfString[1]).floatValue();
        localObject.kd.y = Float.valueOf(arrayOfString[2]).floatValue();
        localObject.kd.z = Float.valueOf(arrayOfString[3]).floatValue();
      }
      else if ((arrayOfString[0].equals("Ks")) && (arrayOfString.length > 3))
      {
        localObject.ks.x = Float.valueOf(arrayOfString[1]).floatValue();
        localObject.ks.y = Float.valueOf(arrayOfString[2]).floatValue();
        localObject.ks.z = Float.valueOf(arrayOfString[3]).floatValue();
      }
      else if (((arrayOfString[0].equals("d")) || (arrayOfString[0].equals("Tr"))) && (arrayOfString.length > 1))
      {
        localObject.d = Float.valueOf(arrayOfString[1]).floatValue();
      }
      else if ((arrayOfString[0].equals("Ns")) && (arrayOfString.length > 1))
      {
        localObject.ns = Float.valueOf(arrayOfString[1]).floatValue();
      }
    }
  }
  
  protected static void parseOBJ(PApplet paramPApplet, BufferedReader paramBufferedReader, ArrayList<OBJFace> paramArrayList, ArrayList<OBJMaterial> paramArrayList1, ArrayList<PVector> paramArrayList2, ArrayList<PVector> paramArrayList3, ArrayList<PVector> paramArrayList4)
  {
    Hashtable localHashtable = new Hashtable();
    int i = -1;
    int j = 0;
    int k = 0;
    int m = 0;
    String str1 = "object";
    String[] arrayOfString1;
    label438:
    do
    {
      for (;;)
      {
        try
        {
          String str2 = paramBufferedReader.readLine();
          if (str2 == null)
          {
            if (paramArrayList1.size() != 0) {
              break label846;
            }
            paramArrayList1.add(new OBJMaterial());
            return;
          }
          String str3 = str2.trim();
          if ((str3.equals("")) || (str3.indexOf('#') == 0)) {
            continue;
          }
          if (!str3.contains("\\"))
          {
            arrayOfString1 = str3.split("\\s+");
            if (arrayOfString1.length <= 0) {
              continue;
            }
            if (arrayOfString1[0].equals("v"))
            {
              paramArrayList2.add(new PVector(Float.valueOf(arrayOfString1[1]).floatValue(), Float.valueOf(arrayOfString1[2]).floatValue(), Float.valueOf(arrayOfString1[3]).floatValue()));
              m = 1;
            }
          }
          else
          {
            str3 = str3.split("\\\\")[0];
            String str4 = paramBufferedReader.readLine();
            if (str4 == null) {
              continue;
            }
            str3 = str3 + str4;
            continue;
          }
          if (arrayOfString1[0].equals("vn"))
          {
            PVector localPVector = new PVector(Float.valueOf(arrayOfString1[1]).floatValue(), Float.valueOf(arrayOfString1[2]).floatValue(), Float.valueOf(arrayOfString1[3]).floatValue());
            paramArrayList3.add(localPVector);
            k = 1;
            continue;
          }
          if (arrayOfString1[0].equals("vt"))
          {
            paramArrayList4.add(new PVector(Float.valueOf(arrayOfString1[1]).floatValue(), 1.0F - Float.valueOf(arrayOfString1[2]).floatValue()));
            j = 1;
            continue;
          }
          if (arrayOfString1[0].equals("o")) {
            continue;
          }
          if (arrayOfString1[0].equals("mtllib"))
          {
            if (arrayOfString1[1] == null) {
              continue;
            }
            BufferedReader localBufferedReader = paramPApplet.createReader(arrayOfString1[1]);
            if (localBufferedReader != null) {
              parseMTL(paramPApplet, localBufferedReader, paramArrayList1, localHashtable);
            }
            localBufferedReader.close();
            continue;
          }
          if (!arrayOfString1[0].equals("g")) {
            break label438;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
        if (1 >= arrayOfString1.length) {
          break label850;
        }
        str1 = arrayOfString1[1];
        break label847;
        if (!arrayOfString1[0].equals("usemtl")) {
          break;
        }
        if (arrayOfString1[1] != null)
        {
          String str6 = arrayOfString1[1];
          if (!localHashtable.containsKey(str6)) {
            break label857;
          }
          i = ((Integer)localHashtable.get(str6)).intValue();
        }
      }
    } while (!arrayOfString1[0].equals("f"));
    OBJFace localOBJFace = new OBJFace();
    localOBJFace.matIdx = i;
    localOBJFace.name = str1;
    for (int n = 1;; n++)
    {
      if (n >= arrayOfString1.length)
      {
        paramArrayList.add(localOBJFace);
        break;
      }
      String str5 = arrayOfString1[n];
      if (str5.indexOf("/") > 0)
      {
        String[] arrayOfString2 = str5.split("/");
        if (arrayOfString2.length > 2)
        {
          if ((arrayOfString2[0].length() > 0) && (m != 0)) {
            localOBJFace.vertIdx.add(Integer.valueOf(arrayOfString2[0]));
          }
          if ((arrayOfString2[1].length() > 0) && (j != 0)) {
            localOBJFace.texIdx.add(Integer.valueOf(arrayOfString2[1]));
          }
          if ((arrayOfString2[2].length() > 0) && (k != 0)) {
            localOBJFace.normIdx.add(Integer.valueOf(arrayOfString2[2]));
          }
        }
        else if (arrayOfString2.length > 1)
        {
          if ((arrayOfString2[0].length() > 0) && (m != 0)) {
            localOBJFace.vertIdx.add(Integer.valueOf(arrayOfString2[0]));
          }
          if (arrayOfString2[1].length() > 0) {
            if (j != 0) {
              localOBJFace.texIdx.add(Integer.valueOf(arrayOfString2[1]));
            } else if (k != 0) {
              localOBJFace.normIdx.add(Integer.valueOf(arrayOfString2[1]));
            }
          }
        }
        else if ((arrayOfString2.length > 0) && (arrayOfString2[0].length() > 0) && (m != 0))
        {
          localOBJFace.vertIdx.add(Integer.valueOf(arrayOfString2[0]));
        }
      }
      else if ((str5.length() > 0) && (m != 0))
      {
        localOBJFace.vertIdx.add(Integer.valueOf(str5));
        continue;
        label846:
        return;
        for (;;)
        {
          label847:
          break;
          label850:
          str1 = "";
        }
        label857:
        i = -1;
        break;
      }
    }
  }
  
  protected static int rgbaValue(PVector paramPVector)
  {
    return 0xFF000000 | (int)(255.0F * paramPVector.x) << 16 | (int)(255.0F * paramPVector.y) << 8 | (int)(255.0F * paramPVector.z);
  }
  
  protected static int rgbaValue(PVector paramPVector, float paramFloat)
  {
    return (int)(paramFloat * 255.0F) << 24 | (int)(255.0F * paramPVector.x) << 16 | (int)(255.0F * paramPVector.y) << 8 | (int)(255.0F * paramPVector.z);
  }
  
  protected void addChildren(ArrayList<OBJFace> paramArrayList, ArrayList<OBJMaterial> paramArrayList1, ArrayList<PVector> paramArrayList2, ArrayList<PVector> paramArrayList3, ArrayList<PVector> paramArrayList4)
  {
    int i = -1;
    OBJMaterial localOBJMaterial = null;
    for (int j = 0;; j++)
    {
      if (j >= paramArrayList.size()) {
        return;
      }
      OBJFace localOBJFace = (OBJFace)paramArrayList.get(j);
      if ((i != localOBJFace.matIdx) || (localOBJFace.matIdx == -1))
      {
        i = PApplet.max(0, localOBJFace.matIdx);
        localOBJMaterial = (OBJMaterial)paramArrayList1.get(i);
      }
      addChild(new PShapeOBJ(localOBJFace, localOBJMaterial, paramArrayList2, paramArrayList3, paramArrayList4));
    }
  }
  
  protected static class OBJFace
  {
    int matIdx = -1;
    String name = "";
    ArrayList<Integer> normIdx = new ArrayList();
    ArrayList<Integer> texIdx = new ArrayList();
    ArrayList<Integer> vertIdx = new ArrayList();
  }
  
  protected static class OBJMaterial
  {
    float d;
    PVector ka;
    PVector kd;
    PImage kdMap;
    PVector ks;
    String name;
    float ns;
    
    OBJMaterial()
    {
      this("default");
    }
    
    OBJMaterial(String paramString)
    {
      this.name = paramString;
      this.ka = new PVector(0.5F, 0.5F, 0.5F);
      this.kd = new PVector(0.5F, 0.5F, 0.5F);
      this.ks = new PVector(0.5F, 0.5F, 0.5F);
      this.d = 1.0F;
      this.ns = 0.0F;
      this.kdMap = null;
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.core.PShapeOBJ
 * JD-Core Version:    0.7.0.1
 */