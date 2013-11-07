package processing.data;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import processing.core.PApplet;

public class Table
{
  static final int CATEGORICAL = 5;
  static final int DOUBLE = 4;
  static final int FLOAT = 3;
  static final int INT = 1;
  static final int LONG = 2;
  static final int STRING;
  HashMapBlows[] columnCategories;
  HashMap<String, Integer> columnIndices;
  String[] columnTitles;
  int[] columnTypes;
  protected Object[] columns;
  protected int missingCategory = -1;
  protected double missingDouble = (0.0D / 0.0D);
  protected float missingFloat = (0.0F / 0.0F);
  protected int missingInt = 0;
  protected long missingLong = 0L;
  protected String missingString = null;
  protected int rowCount;
  protected RowIterator rowIterator;
  
  public Table()
  {
    init();
  }
  
  public Table(File paramFile)
    throws IOException
  {
    this(paramFile, null);
  }
  
  public Table(File paramFile, String paramString)
    throws IOException
  {
    parse(new FileInputStream(paramFile), checkOptions(paramFile, paramString));
  }
  
  public Table(InputStream paramInputStream)
    throws IOException
  {
    this(paramInputStream, null);
  }
  
  public Table(InputStream paramInputStream, String paramString)
    throws IOException
  {
    parse(paramInputStream, paramString);
  }
  
  public Table(ResultSet paramResultSet)
  {
    init();
    int i;
    int j;
    try
    {
      localResultSetMetaData = paramResultSet.getMetaData();
      i = localResultSetMetaData.getColumnCount();
      setColumnCount(i);
      j = 0;
    }
    catch (SQLException localSQLException)
    {
      ResultSetMetaData localResultSetMetaData;
      label78:
      throw new RuntimeException(localSQLException);
    }
    if (!paramResultSet.next())
    {
      return;
      setColumnTitle(j, localResultSetMetaData.getColumnName(j + 1));
      switch (localResultSetMetaData.getColumnType(j + 1))
      {
      case -6: 
      case 4: 
      case 5: 
        setColumnType(j, 1);
        break;
      case -5: 
        setColumnType(j, 2);
        break;
      case 6: 
        setColumnType(j, 3);
        break;
      case 3: 
      case 7: 
      case 8: 
        setColumnType(j, 4);
        break;
      }
    }
    label451:
    for (;;)
    {
      int k;
      switch (this.columnTypes[m])
      {
      default: 
        throw new IllegalArgumentException("column type " + this.columnTypes[m] + " not supported.");
      case 0: 
        setString(k, m, paramResultSet.getString(m + 1));
        break;
      case 1: 
        setInt(k, m, paramResultSet.getInt(m + 1));
        break;
      case 2: 
        setLong(k, m, paramResultSet.getLong(m + 1));
        break;
      case 3: 
        setFloat(k, m, paramResultSet.getFloat(m + 1));
        break;
      case 4: 
        setDouble(k, m, paramResultSet.getDouble(m + 1));
        break;
        for (;;)
        {
          if (j < i) {
            break label78;
          }
          k = 0;
          break;
          j++;
        }
      }
      for (int m = 0;; m++)
      {
        if (m < i) {
          break label451;
        }
        k++;
        break;
      }
    }
  }
  
  public static Iterator<TableRow> createIterator(ResultSet paramResultSet)
  {
    new Iterator()
    {
      boolean already;
      
      public boolean hasNext()
      {
        this.already = true;
        try
        {
          boolean bool = Table.this.next();
          return bool;
        }
        catch (SQLException localSQLException)
        {
          throw new RuntimeException(localSQLException);
        }
      }
      
      public TableRow next()
      {
        if (!this.already) {}
        for (;;)
        {
          try
          {
            Table.this.next();
            new TableRow()
            {
              private void immutable()
              {
                throw new IllegalArgumentException("This TableRow cannot be modified.");
              }
              
              public double getDouble(int paramAnonymous2Int)
              {
                try
                {
                  double d = this.val$rs.getDouble(paramAnonymous2Int);
                  return d;
                }
                catch (SQLException localSQLException)
                {
                  throw new RuntimeException(localSQLException);
                }
              }
              
              public double getDouble(String paramAnonymous2String)
              {
                try
                {
                  double d = this.val$rs.getDouble(paramAnonymous2String);
                  return d;
                }
                catch (SQLException localSQLException)
                {
                  throw new RuntimeException(localSQLException);
                }
              }
              
              public float getFloat(int paramAnonymous2Int)
              {
                try
                {
                  float f = this.val$rs.getFloat(paramAnonymous2Int);
                  return f;
                }
                catch (SQLException localSQLException)
                {
                  throw new RuntimeException(localSQLException);
                }
              }
              
              public float getFloat(String paramAnonymous2String)
              {
                try
                {
                  float f = this.val$rs.getFloat(paramAnonymous2String);
                  return f;
                }
                catch (SQLException localSQLException)
                {
                  throw new RuntimeException(localSQLException);
                }
              }
              
              public int getInt(int paramAnonymous2Int)
              {
                try
                {
                  int i = this.val$rs.getInt(paramAnonymous2Int);
                  return i;
                }
                catch (SQLException localSQLException)
                {
                  throw new RuntimeException(localSQLException);
                }
              }
              
              public int getInt(String paramAnonymous2String)
              {
                try
                {
                  int i = this.val$rs.getInt(paramAnonymous2String);
                  return i;
                }
                catch (SQLException localSQLException)
                {
                  throw new RuntimeException(localSQLException);
                }
              }
              
              public long getLong(int paramAnonymous2Int)
              {
                try
                {
                  long l = this.val$rs.getLong(paramAnonymous2Int);
                  return l;
                }
                catch (SQLException localSQLException)
                {
                  throw new RuntimeException(localSQLException);
                }
              }
              
              public long getLong(String paramAnonymous2String)
              {
                try
                {
                  long l = this.val$rs.getLong(paramAnonymous2String);
                  return l;
                }
                catch (SQLException localSQLException)
                {
                  throw new RuntimeException(localSQLException);
                }
              }
              
              public String getString(int paramAnonymous2Int)
              {
                try
                {
                  String str = this.val$rs.getString(paramAnonymous2Int);
                  return str;
                }
                catch (SQLException localSQLException)
                {
                  throw new RuntimeException(localSQLException);
                }
              }
              
              public String getString(String paramAnonymous2String)
              {
                try
                {
                  String str = this.val$rs.getString(paramAnonymous2String);
                  return str;
                }
                catch (SQLException localSQLException)
                {
                  throw new RuntimeException(localSQLException);
                }
              }
              
              public void setDouble(int paramAnonymous2Int, double paramAnonymous2Double)
              {
                immutable();
              }
              
              public void setDouble(String paramAnonymous2String, double paramAnonymous2Double)
              {
                immutable();
              }
              
              public void setFloat(int paramAnonymous2Int, float paramAnonymous2Float)
              {
                immutable();
              }
              
              public void setFloat(String paramAnonymous2String, float paramAnonymous2Float)
              {
                immutable();
              }
              
              public void setInt(int paramAnonymous2Int1, int paramAnonymous2Int2)
              {
                immutable();
              }
              
              public void setInt(String paramAnonymous2String, int paramAnonymous2Int)
              {
                immutable();
              }
              
              public void setLong(int paramAnonymous2Int, long paramAnonymous2Long)
              {
                immutable();
              }
              
              public void setLong(String paramAnonymous2String, long paramAnonymous2Long)
              {
                immutable();
              }
              
              public void setString(int paramAnonymous2Int, String paramAnonymous2String)
              {
                immutable();
              }
              
              public void setString(String paramAnonymous2String1, String paramAnonymous2String2)
              {
                immutable();
              }
            };
          }
          catch (SQLException localSQLException)
          {
            throw new RuntimeException(localSQLException);
          }
          this.already = false;
        }
      }
      
      public void remove()
      {
        throw new IllegalArgumentException("remove() not supported");
      }
    };
  }
  
  protected static int nextComma(char[] paramArrayOfChar, int paramInt)
  {
    int i = 0;
    int j = paramInt;
    if (j >= paramArrayOfChar.length) {
      j = paramArrayOfChar.length;
    }
    while ((i == 0) && (paramArrayOfChar[j] == ',')) {
      return j;
    }
    if (paramArrayOfChar[j] == '"') {
      if (i == 0) {
        break label47;
      }
    }
    label47:
    for (i = 0;; i = 1)
    {
      j++;
      break;
    }
  }
  
  protected static String[] splitLineCSV(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    int i = 1;
    int j = 0;
    int k = 0;
    String[] arrayOfString;
    int m;
    int n;
    if (k >= arrayOfChar.length)
    {
      arrayOfString = new String[i];
      m = 0;
      n = 0;
      if (n < arrayOfChar.length) {
        break label98;
      }
    }
    for (int i6 = m;; i6++)
    {
      if (i6 >= arrayOfString.length)
      {
        return arrayOfString;
        if ((j == 0) && (arrayOfChar[k] == ',')) {
          i++;
        }
        while (arrayOfChar[k] != '"')
        {
          k++;
          break;
        }
        if (j != 0) {}
        for (j = 0;; j = 1) {
          break;
        }
        label98:
        int i1 = n;
        int i2 = nextComma(arrayOfChar, n);
        n = i2 + 1;
        if ((arrayOfChar[i1] == '"') && (arrayOfChar[(i2 - 1)] == '"'))
        {
          i1++;
          i2--;
        }
        int i3 = i1;
        for (int i4 = i1;; i4++)
        {
          if (i3 >= i2)
          {
            String str = new String(arrayOfChar, i1, i4 - i1);
            int i5 = m + 1;
            arrayOfString[m] = str;
            m = i5;
            break;
          }
          if (arrayOfChar[i3] == '"') {
            i3++;
          }
          if (i3 != i4) {
            arrayOfChar[i4] = arrayOfChar[i3];
          }
          i3++;
        }
      }
      arrayOfString[i6] = "";
    }
  }
  
  public void addColumn()
  {
    addColumn(null, 0);
  }
  
  public void addColumn(String paramString)
  {
    addColumn(paramString, 0);
  }
  
  public void addColumn(String paramString, int paramInt)
  {
    insertColumn(this.columns.length, paramString, paramInt);
  }
  
  public TableRow addRow()
  {
    setRowCount(1 + this.rowCount);
    return new RowPointer(this, -1 + this.rowCount);
  }
  
  public TableRow addRow(Object[] paramArrayOfObject)
  {
    setRow(getRowCount(), paramArrayOfObject);
    return new RowPointer(this, -1 + this.rowCount);
  }
  
  protected void checkBounds(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 >= this.rowCount)) {
      throw new ArrayIndexOutOfBoundsException("Row " + paramInt1 + " does not exist.");
    }
    if ((paramInt2 < 0) || (paramInt2 >= this.columns.length)) {
      throw new ArrayIndexOutOfBoundsException("Column " + paramInt2 + " does not exist.");
    }
  }
  
  protected void checkColumn(int paramInt)
  {
    if (paramInt >= this.columns.length) {
      setColumnCount(paramInt + 1);
    }
  }
  
  public int checkColumnIndex(String paramString)
  {
    int i = getColumnIndex(paramString, false);
    if (i != -1) {
      return i;
    }
    addColumn(paramString);
    return -1 + getColumnCount();
  }
  
  protected String checkOptions(File paramFile, String paramString)
    throws IOException
  {
    String str1 = paramFile.getName();
    int i = str1.lastIndexOf('.');
    String str2 = null;
    if (i != -1)
    {
      str2 = str1.substring(i + 1).toLowerCase();
      if ((!str2.equals("csv")) && (!str2.equals("tsv"))) {
        str2 = null;
      }
    }
    if (str2 == null)
    {
      if (paramString == null) {
        throw new IOException("This table filename has no extension, and no options are set.");
      }
    }
    else
    {
      if (paramString != null) {
        break label89;
      }
      paramString = str2;
    }
    return paramString;
    label89:
    return str2 + "," + paramString;
  }
  
  protected void checkRow(int paramInt)
  {
    if (paramInt >= this.rowCount) {
      setRowCount(paramInt + 1);
    }
  }
  
  protected void checkSize(int paramInt1, int paramInt2)
  {
    checkRow(paramInt1);
    checkColumn(paramInt2);
  }
  
  protected void convertBasic(BufferedReader paramBufferedReader, boolean paramBoolean, File paramFile)
    throws IOException
  {
    DataOutputStream localDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(paramFile), 16384));
    localDataOutputStream.writeInt(0);
    localDataOutputStream.writeInt(getColumnCount());
    String[] arrayOfString2;
    int i5;
    label76:
    int[] arrayOfInt;
    int i;
    if (this.columnTitles != null)
    {
      localDataOutputStream.writeBoolean(true);
      arrayOfString2 = this.columnTitles;
      int i4 = arrayOfString2.length;
      i5 = 0;
      if (i5 >= i4)
      {
        arrayOfInt = this.columnTypes;
        i = arrayOfInt.length;
      }
    }
    int k;
    int m;
    String str;
    int i1;
    HashMapBlows[] arrayOfHashMapBlows;
    int i3;
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        k = -1;
        m = 0;
        str = paramBufferedReader.readLine();
        if (str != null) {
          break label217;
        }
        i1 = 0;
        arrayOfHashMapBlows = this.columnCategories;
        int i2 = arrayOfHashMapBlows.length;
        i3 = 0;
        if (i3 < i2) {
          break label322;
        }
        localDataOutputStream.flush();
        localDataOutputStream.close();
        RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "rw");
        localRandomAccessFile.writeInt(this.rowCount);
        localRandomAccessFile.close();
        return;
        localDataOutputStream.writeUTF(arrayOfString2[i5]);
        i5++;
        break;
        localDataOutputStream.writeBoolean(false);
        break label76;
      }
      localDataOutputStream.writeInt(arrayOfInt[j]);
    }
    label217:
    if (paramBoolean) {}
    for (String[] arrayOfString1 = PApplet.split(str, '\t');; arrayOfString1 = splitLineCSV(str))
    {
      convertRow(localDataOutputStream, arrayOfString1);
      m++;
      if ((m % 10000 != 0) || (m >= this.rowCount)) {
        break;
      }
      int n = m * 100 / this.rowCount;
      if (n == k) {
        break;
      }
      System.out.println(n + "%");
      k = n;
      break;
    }
    label322:
    HashMapBlows localHashMapBlows = arrayOfHashMapBlows[i3];
    if (localHashMapBlows == null) {
      localDataOutputStream.writeInt(0);
    }
    for (;;)
    {
      i1++;
      i3++;
      break;
      localHashMapBlows.write(localDataOutputStream);
      localHashMapBlows.writeln(PApplet.createWriter(new File(this.columnTitles[i1] + ".categories")));
    }
  }
  
  protected void convertRow(DataOutputStream paramDataOutputStream, String[] paramArrayOfString)
    throws IOException
  {
    if (paramArrayOfString.length > getColumnCount()) {
      throw new IllegalArgumentException("Row with too many columns: " + PApplet.join(paramArrayOfString, ","));
    }
    int i = 0;
    int j;
    if (i >= paramArrayOfString.length)
    {
      j = paramArrayOfString.length;
      if (j < getColumnCount()) {}
    }
    else
    {
      switch (this.columnTypes[i])
      {
      }
      for (;;)
      {
        i++;
        break;
        paramDataOutputStream.writeUTF(paramArrayOfString[i]);
        continue;
        paramDataOutputStream.writeInt(PApplet.parseInt(paramArrayOfString[i], this.missingInt));
        continue;
        try
        {
          paramDataOutputStream.writeLong(Long.parseLong(paramArrayOfString[i]));
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          paramDataOutputStream.writeLong(this.missingLong);
        }
        continue;
        paramDataOutputStream.writeFloat(PApplet.parseFloat(paramArrayOfString[i], this.missingFloat));
        continue;
        try
        {
          paramDataOutputStream.writeDouble(Double.parseDouble(paramArrayOfString[i]));
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          paramDataOutputStream.writeDouble(this.missingDouble);
        }
        continue;
        paramDataOutputStream.writeInt(this.columnCategories[i].index(paramArrayOfString[i]));
      }
    }
    switch (this.columnTypes[j])
    {
    }
    for (;;)
    {
      j++;
      break;
      paramDataOutputStream.writeUTF("");
      continue;
      paramDataOutputStream.writeInt(this.missingInt);
      continue;
      paramDataOutputStream.writeLong(this.missingLong);
      continue;
      paramDataOutputStream.writeFloat(this.missingFloat);
      continue;
      paramDataOutputStream.writeDouble(this.missingDouble);
      continue;
      paramDataOutputStream.writeInt(this.missingCategory);
    }
  }
  
  protected Table createSubset(int[] paramArrayOfInt)
  {
    Table localTable = new Table();
    localTable.setColumnTitles(this.columnTitles);
    localTable.columnTypes = this.columnTypes;
    localTable.setRowCount(paramArrayOfInt.length);
    int j;
    int k;
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfInt.length) {
        return localTable;
      }
      j = paramArrayOfInt[i];
      k = 0;
      if (k < this.columns.length) {
        break;
      }
    }
    switch (this.columnTypes[k])
    {
    }
    for (;;)
    {
      k++;
      break;
      localTable.setString(i, k, getString(j, k));
      continue;
      localTable.setInt(i, k, getInt(j, k));
      continue;
      localTable.setLong(i, k, getLong(j, k));
      continue;
      localTable.setFloat(i, k, getFloat(j, k));
      continue;
      localTable.setDouble(i, k, getDouble(j, k));
    }
  }
  
  public TableRow findRow(String paramString, int paramInt)
  {
    int i = findRowIndex(paramString, paramInt);
    if (i == -1) {
      return null;
    }
    return new RowPointer(this, i);
  }
  
  public TableRow findRow(String paramString1, String paramString2)
  {
    return findRow(paramString1, getColumnIndex(paramString2));
  }
  
  public int findRowIndex(String paramString, int paramInt)
  {
    checkBounds(-1, paramInt);
    String[] arrayOfString;
    int i;
    if (this.columnTypes[paramInt] == 0)
    {
      arrayOfString = (String[])this.columns[paramInt];
      if (paramString == null)
      {
        i = 0;
        if (i >= this.rowCount) {
          label40:
          i = -1;
        }
      }
    }
    label89:
    label93:
    String str;
    do
    {
      do
      {
        return i;
      } while (arrayOfString[i] == null);
      i++;
      break;
      for (i = 0;; i++)
      {
        if (i >= this.rowCount) {
          break label89;
        }
        if ((arrayOfString[i] != null) && (arrayOfString[i].equals(paramString))) {
          break;
        }
      }
      break label40;
      i = 0;
      if (i >= this.rowCount) {
        break label122;
      }
      str = getString(i, paramInt);
      if (str != null) {
        break label124;
      }
    } while (paramString == null);
    label122:
    label124:
    while (!str.equals(paramString))
    {
      i++;
      break label93;
      break;
    }
    return i;
  }
  
  public int findRowIndex(String paramString1, String paramString2)
  {
    return findRowIndex(paramString1, getColumnIndex(paramString2));
  }
  
  public int[] findRowIndices(String paramString, int paramInt)
  {
    int[] arrayOfInt = new int[this.rowCount];
    int i = 0;
    checkBounds(-1, paramInt);
    if (this.columnTypes[paramInt] == 0)
    {
      String[] arrayOfString = (String[])this.columns[paramInt];
      int i2;
      if (paramString == null)
      {
        i2 = 0;
        if (i2 < this.rowCount) {}
      }
      for (;;)
      {
        return PApplet.subset(arrayOfInt, 0, i);
        if (arrayOfString[i2] == null)
        {
          int i3 = i + 1;
          arrayOfInt[i] = i2;
          i = i3;
        }
        i2++;
        break;
        for (int n = 0; n < this.rowCount; n++) {
          if ((arrayOfString[n] != null) && (arrayOfString[n].equals(paramString)))
          {
            int i1 = i + 1;
            arrayOfInt[i] = n;
            i = i1;
          }
        }
      }
    }
    int j = 0;
    label147:
    String str;
    if (j < this.rowCount)
    {
      str = getString(j, paramInt);
      if (str != null) {
        break label196;
      }
      if (paramString == null)
      {
        int m = i + 1;
        arrayOfInt[i] = j;
        i = m;
      }
    }
    for (;;)
    {
      j++;
      break label147;
      break;
      label196:
      if (str.equals(paramString))
      {
        int k = i + 1;
        arrayOfInt[i] = j;
        i = k;
      }
    }
  }
  
  public int[] findRowIndices(String paramString1, String paramString2)
  {
    return findRowIndices(paramString1, getColumnIndex(paramString2));
  }
  
  public Iterator<TableRow> findRows(String paramString, int paramInt)
  {
    return new RowIndexIterator(this, findRowIndices(paramString, paramInt));
  }
  
  public Iterator<TableRow> findRows(String paramString1, String paramString2)
  {
    return findRows(paramString1, getColumnIndex(paramString2));
  }
  
  public int getColumnCount()
  {
    return this.columns.length;
  }
  
  public int getColumnIndex(String paramString)
  {
    return getColumnIndex(paramString, true);
  }
  
  protected int getColumnIndex(String paramString, boolean paramBoolean)
  {
    int i = -1;
    if (this.columnTitles == null)
    {
      if (paramBoolean) {
        throw new IllegalArgumentException("This table has no header, so no column titles are set.");
      }
    }
    else
    {
      if (this.columnIndices == null) {
        this.columnIndices = new HashMap();
      }
      Integer localInteger;
      for (int j = 0;; j++)
      {
        if (j >= this.columns.length)
        {
          localInteger = (Integer)this.columnIndices.get(paramString);
          if (localInteger != null) {
            break;
          }
          if (!paramBoolean) {
            return i;
          }
          throw new IllegalArgumentException("This table has no column named '" + paramString + "'");
        }
        this.columnIndices.put(this.columnTitles[j], Integer.valueOf(j));
      }
      i = localInteger.intValue();
    }
    return i;
  }
  
  public String getColumnTitle(int paramInt)
  {
    if (this.columnTitles == null) {
      return null;
    }
    return this.columnTitles[paramInt];
  }
  
  public String[] getColumnTitles()
  {
    return this.columnTitles;
  }
  
  public double getDouble(int paramInt1, int paramInt2)
  {
    checkBounds(paramInt1, paramInt2);
    if (this.columnTypes[paramInt2] == 4) {
      return ((double[])this.columns[paramInt2])[paramInt1];
    }
    String str = getString(paramInt1, paramInt2);
    if ((str == null) || (str.equals(this.missingString))) {
      return this.missingDouble;
    }
    try
    {
      double d = Double.parseDouble(str);
      return d;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return this.missingDouble;
  }
  
  public double getDouble(int paramInt, String paramString)
  {
    return getDouble(paramInt, getColumnIndex(paramString));
  }
  
  public double[] getDoubleColumn(int paramInt)
  {
    double[] arrayOfDouble = new double[this.rowCount];
    for (int i = 0;; i++)
    {
      if (i >= this.rowCount) {
        return arrayOfDouble;
      }
      arrayOfDouble[i] = getDouble(i, paramInt);
    }
  }
  
  public double[] getDoubleColumn(String paramString)
  {
    int i = getColumnIndex(paramString);
    if (i == -1) {
      return null;
    }
    return getDoubleColumn(i);
  }
  
  public double[] getDoubleRow(int paramInt)
  {
    double[] arrayOfDouble = new double[this.columns.length];
    for (int i = 0;; i++)
    {
      if (i >= this.columns.length) {
        return arrayOfDouble;
      }
      arrayOfDouble[i] = getDouble(paramInt, i);
    }
  }
  
  public float getFloat(int paramInt1, int paramInt2)
  {
    checkBounds(paramInt1, paramInt2);
    if (this.columnTypes[paramInt2] == 3) {
      return ((float[])this.columns[paramInt2])[paramInt1];
    }
    String str = getString(paramInt1, paramInt2);
    if ((str == null) || (str.equals(this.missingString))) {
      return this.missingFloat;
    }
    return PApplet.parseFloat(str, this.missingFloat);
  }
  
  public float getFloat(int paramInt, String paramString)
  {
    return getFloat(paramInt, getColumnIndex(paramString));
  }
  
  public float[] getFloatColumn(int paramInt)
  {
    float[] arrayOfFloat = new float[this.rowCount];
    for (int i = 0;; i++)
    {
      if (i >= this.rowCount) {
        return arrayOfFloat;
      }
      arrayOfFloat[i] = getFloat(i, paramInt);
    }
  }
  
  public float[] getFloatColumn(String paramString)
  {
    int i = getColumnIndex(paramString);
    if (i == -1) {
      return null;
    }
    return getFloatColumn(i);
  }
  
  public float[] getFloatRow(int paramInt)
  {
    float[] arrayOfFloat = new float[this.columns.length];
    for (int i = 0;; i++)
    {
      if (i >= this.columns.length) {
        return arrayOfFloat;
      }
      arrayOfFloat[i] = getFloat(paramInt, i);
    }
  }
  
  public int getInt(int paramInt1, int paramInt2)
  {
    checkBounds(paramInt1, paramInt2);
    if ((this.columnTypes[paramInt2] == 1) || (this.columnTypes[paramInt2] == 5)) {
      return ((int[])this.columns[paramInt2])[paramInt1];
    }
    String str = getString(paramInt1, paramInt2);
    if ((str == null) || (str.equals(this.missingString))) {
      return this.missingInt;
    }
    return PApplet.parseInt(str, this.missingInt);
  }
  
  public int getInt(int paramInt, String paramString)
  {
    return getInt(paramInt, getColumnIndex(paramString));
  }
  
  public int[] getIntColumn(int paramInt)
  {
    int[] arrayOfInt = new int[this.rowCount];
    for (int i = 0;; i++)
    {
      if (i >= this.rowCount) {
        return arrayOfInt;
      }
      arrayOfInt[i] = getInt(i, paramInt);
    }
  }
  
  public int[] getIntColumn(String paramString)
  {
    int i = getColumnIndex(paramString);
    if (i == -1) {
      return null;
    }
    return getIntColumn(i);
  }
  
  public int[] getIntRow(int paramInt)
  {
    int[] arrayOfInt = new int[this.columns.length];
    for (int i = 0;; i++)
    {
      if (i >= this.columns.length) {
        return arrayOfInt;
      }
      arrayOfInt[i] = getInt(paramInt, i);
    }
  }
  
  public long getLong(int paramInt1, int paramInt2)
  {
    checkBounds(paramInt1, paramInt2);
    if (this.columnTypes[paramInt2] == 2) {
      return ((long[])this.columns[paramInt2])[paramInt1];
    }
    String str = getString(paramInt1, paramInt2);
    if ((str == null) || (str.equals(this.missingString))) {
      return this.missingLong;
    }
    try
    {
      long l = Long.parseLong(str);
      return l;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return this.missingLong;
  }
  
  public long getLong(int paramInt, String paramString)
  {
    return getLong(paramInt, getColumnIndex(paramString));
  }
  
  public long[] getLongColumn(int paramInt)
  {
    long[] arrayOfLong = new long[this.rowCount];
    for (int i = 0;; i++)
    {
      if (i >= this.rowCount) {
        return arrayOfLong;
      }
      arrayOfLong[i] = getLong(i, paramInt);
    }
  }
  
  public long[] getLongColumn(String paramString)
  {
    int i = getColumnIndex(paramString);
    if (i == -1) {
      return null;
    }
    return getLongColumn(i);
  }
  
  public long[] getLongRow(int paramInt)
  {
    long[] arrayOfLong = new long[this.columns.length];
    for (int i = 0;; i++)
    {
      if (i >= this.columns.length) {
        return arrayOfLong;
      }
      arrayOfLong[i] = getLong(paramInt, i);
    }
  }
  
  protected float getMaxFloat()
  {
    int i = 0;
    float f1 = -3.402824E+038F;
    int k;
    for (int j = 0;; j++)
    {
      if (j >= getRowCount())
      {
        if (i == 0) {
          break label85;
        }
        return f1;
      }
      k = 0;
      if (k < getColumnCount()) {
        break;
      }
    }
    float f2 = getFloat(j, k);
    if (!Float.isNaN(f2))
    {
      if (i != 0) {
        break label72;
      }
      f1 = f2;
      i = 1;
    }
    for (;;)
    {
      k++;
      break;
      label72:
      if (f2 > f1) {
        f1 = f2;
      }
    }
    label85:
    return this.missingFloat;
  }
  
  public TableRow getRow(int paramInt)
  {
    return new RowPointer(this, paramInt);
  }
  
  public int getRowCount()
  {
    return this.rowCount;
  }
  
  protected HashMap<String, Integer> getRowLookup(int paramInt)
  {
    HashMap localHashMap = new HashMap();
    for (int i = 0;; i++)
    {
      if (i >= getRowCount()) {
        return localHashMap;
      }
      localHashMap.put(getString(i, paramInt), Integer.valueOf(i));
    }
  }
  
  public String getString(int paramInt1, int paramInt2)
  {
    checkBounds(paramInt1, paramInt2);
    if (this.columnTypes[paramInt2] == 0) {
      return ((String[])this.columns[paramInt2])[paramInt1];
    }
    if (this.columnTypes[paramInt2] == 5)
    {
      int i = getInt(paramInt1, paramInt2);
      return this.columnCategories[paramInt2].key(i);
    }
    return String.valueOf(Array.get(this.columns[paramInt2], paramInt1));
  }
  
  public String getString(int paramInt, String paramString)
  {
    return getString(paramInt, getColumnIndex(paramString));
  }
  
  public String[] getStringColumn(int paramInt)
  {
    String[] arrayOfString = new String[this.rowCount];
    for (int i = 0;; i++)
    {
      if (i >= this.rowCount) {
        return arrayOfString;
      }
      arrayOfString[i] = getString(i, paramInt);
    }
  }
  
  public String[] getStringColumn(String paramString)
  {
    int i = getColumnIndex(paramString);
    if (i == -1) {
      return null;
    }
    return getStringColumn(i);
  }
  
  public String[] getStringRow(int paramInt)
  {
    String[] arrayOfString = new String[this.columns.length];
    for (int i = 0;; i++)
    {
      if (i >= this.columns.length) {
        return arrayOfString;
      }
      arrayOfString[i] = getString(paramInt, i);
    }
  }
  
  protected String[] getUnique(int paramInt)
  {
    HashMapSucks localHashMapSucks = new HashMapSucks();
    for (int i = 0;; i++)
    {
      if (i >= getRowCount())
      {
        String[] arrayOfString = new String[localHashMapSucks.size()];
        localHashMapSucks.keySet().toArray(arrayOfString);
        return arrayOfString;
      }
      localHashMapSucks.check(getString(i, paramInt));
    }
  }
  
  protected String[] getUnique(String paramString)
  {
    return getUnique(getColumnIndex(paramString));
  }
  
  protected HashMap<String, Integer> getUniqueCount(int paramInt)
  {
    HashMapSucks localHashMapSucks = new HashMapSucks();
    for (int i = 0;; i++)
    {
      if (i >= this.rowCount) {
        return localHashMapSucks;
      }
      String str = getString(i, paramInt);
      if (str != null) {
        localHashMapSucks.increment(str);
      }
    }
  }
  
  protected HashMap<String, Integer> getUniqueCount(String paramString)
  {
    return getUniqueCount(getColumnIndex(paramString));
  }
  
  public boolean hasColumnTitles()
  {
    return this.columnTitles != null;
  }
  
  protected void init()
  {
    this.columns = new Object[0];
    this.columnTypes = new int[0];
    this.columnCategories = new HashMapBlows[0];
  }
  
  public void insertColumn(int paramInt)
  {
    insertColumn(paramInt, null, 0);
  }
  
  public void insertColumn(int paramInt, String paramString)
  {
    insertColumn(paramInt, paramString, 0);
  }
  
  public void insertColumn(int paramInt1, String paramString, int paramInt2)
  {
    if ((paramString != null) && (this.columnTitles == null)) {
      this.columnTitles = new String[this.columns.length];
    }
    if (this.columnTitles != null)
    {
      this.columnTitles = PApplet.splice(this.columnTitles, paramString, paramInt1);
      this.columnIndices = null;
    }
    this.columnTypes = PApplet.splice(this.columnTypes, paramInt2, paramInt1);
    HashMapBlows[] arrayOfHashMapBlows = new HashMapBlows[1 + this.columns.length];
    int i = 0;
    label76:
    if (i >= paramInt1) {
      arrayOfHashMapBlows[paramInt1] = new HashMapBlows();
    }
    for (int j = paramInt1;; j++)
    {
      if (j >= this.columns.length)
      {
        this.columnCategories = arrayOfHashMapBlows;
        Object[] arrayOfObject = new Object[1 + this.columns.length];
        System.arraycopy(this.columns, 0, arrayOfObject, 0, paramInt1);
        System.arraycopy(this.columns, paramInt1, arrayOfObject, paramInt1 + 1, this.columns.length - paramInt1);
        this.columns = arrayOfObject;
      }
      switch (paramInt2)
      {
      default: 
        return;
        arrayOfHashMapBlows[i] = this.columnCategories[i];
        i++;
        break label76;
        arrayOfHashMapBlows[(j + 1)] = this.columnCategories[j];
      }
    }
    this.columns[paramInt1] = new int[this.rowCount];
    return;
    this.columns[paramInt1] = new long[this.rowCount];
    return;
    this.columns[paramInt1] = new float[this.rowCount];
    return;
    this.columns[paramInt1] = new double[this.rowCount];
    return;
    this.columns[paramInt1] = new String[this.rowCount];
    return;
    this.columns[paramInt1] = new int[this.rowCount];
  }
  
  public void insertRow(int paramInt, Object[] paramArrayOfObject)
  {
    int i = 0;
    if (i >= this.columns.length)
    {
      setRow(paramInt, paramArrayOfObject);
      this.rowCount = (1 + this.rowCount);
      return;
    }
    switch (this.columnTypes[i])
    {
    }
    for (;;)
    {
      i++;
      break;
      int[] arrayOfInt = new int[1 + this.rowCount];
      System.arraycopy(this.columns[i], 0, arrayOfInt, 0, paramInt);
      System.arraycopy(this.columns[i], paramInt, arrayOfInt, paramInt + 1, 1 + (this.rowCount - paramInt));
      this.columns[i] = arrayOfInt;
      continue;
      long[] arrayOfLong = new long[1 + this.rowCount];
      System.arraycopy(this.columns[i], 0, arrayOfLong, 0, paramInt);
      System.arraycopy(this.columns[i], paramInt, arrayOfLong, paramInt + 1, 1 + (this.rowCount - paramInt));
      this.columns[i] = arrayOfLong;
      continue;
      float[] arrayOfFloat = new float[1 + this.rowCount];
      System.arraycopy(this.columns[i], 0, arrayOfFloat, 0, paramInt);
      System.arraycopy(this.columns[i], paramInt, arrayOfFloat, paramInt + 1, 1 + (this.rowCount - paramInt));
      this.columns[i] = arrayOfFloat;
      continue;
      double[] arrayOfDouble = new double[1 + this.rowCount];
      System.arraycopy(this.columns[i], 0, arrayOfDouble, 0, paramInt);
      System.arraycopy(this.columns[i], paramInt, arrayOfDouble, paramInt + 1, 1 + (this.rowCount - paramInt));
      this.columns[i] = arrayOfDouble;
      continue;
      String[] arrayOfString = new String[1 + this.rowCount];
      System.arraycopy(this.columns[i], 0, arrayOfString, 0, paramInt);
      System.arraycopy(this.columns[i], paramInt, arrayOfString, paramInt + 1, 1 + (this.rowCount - paramInt));
      this.columns[i] = arrayOfString;
    }
  }
  
  public int lastRowIndex()
  {
    return -1 + getRowCount();
  }
  
  public TableRow matchRow(String paramString, int paramInt)
  {
    int i = matchRowIndex(paramString, paramInt);
    if (i == -1) {
      return null;
    }
    return new RowPointer(this, i);
  }
  
  public TableRow matchRow(String paramString1, String paramString2)
  {
    return matchRow(paramString1, getColumnIndex(paramString2));
  }
  
  public int matchRowIndex(String paramString, int paramInt)
  {
    checkBounds(-1, paramInt);
    String[] arrayOfString;
    int i;
    if (this.columnTypes[paramInt] == 0)
    {
      arrayOfString = (String[])this.columns[paramInt];
      i = 0;
      if (i < this.rowCount) {}
    }
    label100:
    for (;;)
    {
      i = -1;
      do
      {
        return i;
      } while ((arrayOfString[i] != null) && (PApplet.match(arrayOfString[i], paramString) != null));
      i++;
      break;
      for (i = 0;; i++)
      {
        if (i >= this.rowCount) {
          break label100;
        }
        String str = getString(i, paramInt);
        if ((str != null) && (PApplet.match(str, paramString) != null)) {
          break;
        }
      }
    }
  }
  
  public int matchRowIndex(String paramString1, String paramString2)
  {
    return matchRowIndex(paramString1, getColumnIndex(paramString2));
  }
  
  public int[] matchRowIndices(String paramString, int paramInt)
  {
    int[] arrayOfInt = new int[this.rowCount];
    int i = 0;
    checkBounds(-1, paramInt);
    String[] arrayOfString;
    int m;
    if (this.columnTypes[paramInt] == 0)
    {
      arrayOfString = (String[])this.columns[paramInt];
      m = 0;
      if (m < this.rowCount) {}
    }
    for (;;)
    {
      return PApplet.subset(arrayOfInt, 0, i);
      if ((arrayOfString[m] != null) && (PApplet.match(arrayOfString[m], paramString) != null))
      {
        int n = i + 1;
        arrayOfInt[i] = m;
        i = n;
      }
      m++;
      break;
      for (int j = 0; j < this.rowCount; j++)
      {
        String str = getString(j, paramInt);
        if ((str != null) && (PApplet.match(str, paramString) != null))
        {
          int k = i + 1;
          arrayOfInt[i] = j;
          i = k;
        }
      }
    }
  }
  
  public int[] matchRowIndices(String paramString1, String paramString2)
  {
    return matchRowIndices(paramString1, getColumnIndex(paramString2));
  }
  
  public Iterator<TableRow> matchRows(String paramString, int paramInt)
  {
    return new RowIndexIterator(this, matchRowIndices(paramString, paramInt));
  }
  
  public Iterator<TableRow> matchRows(String paramString1, String paramString2)
  {
    return matchRows(paramString1, getColumnIndex(paramString2));
  }
  
  protected void parse(InputStream paramInputStream, String paramString)
    throws IOException
  {
    init();
    int i = 0;
    String str1 = null;
    boolean bool = false;
    String[] arrayOfString;
    int k;
    BufferedReader localBufferedReader;
    if (paramString != null)
    {
      arrayOfString = PApplet.splitTokens(paramString, " ,");
      int j = arrayOfString.length;
      k = 0;
      if (k < j) {}
    }
    else
    {
      localBufferedReader = PApplet.createReader(paramInputStream);
      if (i == 0) {
        break label172;
      }
      parseAwfulCSV(localBufferedReader, bool);
    }
    label140:
    label172:
    do
    {
      return;
      String str2 = arrayOfString[k];
      if (str2.equals("tsv")) {
        str1 = "tsv";
      }
      for (;;)
      {
        k++;
        break;
        if (str2.equals("csv"))
        {
          str1 = "csv";
        }
        else if (str2.equals("newlines"))
        {
          i = 1;
        }
        else
        {
          if (!str2.equals("header")) {
            break label140;
          }
          bool = true;
        }
      }
      throw new IllegalArgumentException("'" + str2 + "' is not a valid option for loading a Table");
      if ("tsv".equals(str1))
      {
        parseBasic(localBufferedReader, bool, true);
        return;
      }
    } while (!"csv".equals(str1));
    parseBasic(localBufferedReader, bool, false);
  }
  
  protected void parseAwfulCSV(BufferedReader paramBufferedReader, boolean paramBoolean)
    throws IOException
  {
    char[] arrayOfChar = new char[100];
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    for (;;)
    {
      int n = paramBufferedReader.read();
      if (n == -1)
      {
        if (i > 0) {
          setString(k, m, new String(arrayOfChar, 0, i));
        }
        return;
      }
      if (j != 0)
      {
        if (n == 34)
        {
          paramBufferedReader.mark(1);
          if (paramBufferedReader.read() == 34)
          {
            if (i == arrayOfChar.length) {
              arrayOfChar = PApplet.expand(arrayOfChar);
            }
            int i3 = i + 1;
            arrayOfChar[i] = '"';
            i = i3;
          }
          else
          {
            paramBufferedReader.reset();
            j = 0;
          }
        }
        else
        {
          if (i == arrayOfChar.length) {
            arrayOfChar = PApplet.expand(arrayOfChar);
          }
          int i2 = i + 1;
          arrayOfChar[i] = ((char)n);
          i = i2;
        }
      }
      else if (n == 34)
      {
        j = 1;
      }
      else if ((n == 13) || (n == 10))
      {
        if (n == 13)
        {
          paramBufferedReader.mark(1);
          if (paramBufferedReader.read() != 10) {
            paramBufferedReader.reset();
          }
        }
        setString(k, m, new String(arrayOfChar, 0, i));
        if ((k == 0) && (paramBoolean))
        {
          removeTitleRow();
          paramBoolean = false;
        }
        k++;
        m = 0;
        i = 0;
      }
      else if (n == 44)
      {
        setString(k, m, new String(arrayOfChar, 0, i));
        m++;
        checkColumn(m);
        i = 0;
      }
      else
      {
        if (i == arrayOfChar.length) {
          arrayOfChar = PApplet.expand(arrayOfChar);
        }
        int i1 = i + 1;
        arrayOfChar[i] = ((char)n);
        i = i1;
      }
    }
  }
  
  protected void parseBasic(BufferedReader paramBufferedReader, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    int i = this.rowCount;
    int j = 0;
    if (i == 0) {
      setRowCount(10);
    }
    String str = paramBufferedReader.readLine();
    if (str == null)
    {
      if (j != getRowCount()) {
        setRowCount(j);
      }
      return;
    }
    if (j == getRowCount()) {
      setRowCount(j << 1);
    }
    if ((j == 0) && (paramBoolean1))
    {
      if (paramBoolean2) {}
      for (String[] arrayOfString2 = PApplet.split(str, '\t');; arrayOfString2 = splitLineCSV(str))
      {
        setColumnTitles(arrayOfString2);
        paramBoolean1 = false;
        break;
      }
    }
    if (paramBoolean2) {}
    for (String[] arrayOfString1 = PApplet.split(str, '\t');; arrayOfString1 = splitLineCSV(str))
    {
      setRow(j, arrayOfString1);
      j++;
      break;
    }
  }
  
  /* Error */
  public void parseInto(Object paramObject, String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: aload_1
    //   6: invokevirtual 699	java/lang/Object:getClass	()Ljava/lang/Class;
    //   9: aload_2
    //   10: invokevirtual 705	java/lang/Class:getDeclaredField	(Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   13: astore 4
    //   15: aload 4
    //   17: invokevirtual 710	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   20: astore 41
    //   22: aload 41
    //   24: invokevirtual 713	java/lang/Class:isArray	()Z
    //   27: istore 42
    //   29: aconst_null
    //   30: astore 6
    //   32: aconst_null
    //   33: astore_3
    //   34: iload 42
    //   36: ifeq +27 -> 63
    //   39: aload 41
    //   41: invokevirtual 716	java/lang/Class:getComponentType	()Ljava/lang/Class;
    //   44: astore_3
    //   45: aload_0
    //   46: invokevirtual 239	processing/data/Table:getRowCount	()I
    //   49: istore 43
    //   51: aload_3
    //   52: iload 43
    //   54: invokestatic 720	java/lang/reflect/Array:newInstance	(Ljava/lang/Class;I)Ljava/lang/Object;
    //   57: astore 44
    //   59: aload 44
    //   61: astore 6
    //   63: aload_3
    //   64: invokevirtual 723	java/lang/Class:getEnclosingClass	()Ljava/lang/Class;
    //   67: astore 7
    //   69: aconst_null
    //   70: astore 8
    //   72: aload 7
    //   74: ifnonnull +137 -> 211
    //   77: iconst_0
    //   78: anewarray 701	java/lang/Class
    //   81: astore 39
    //   83: aload_3
    //   84: aload 39
    //   86: invokevirtual 727	java/lang/Class:getDeclaredConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   89: astore 8
    //   91: aload 8
    //   93: invokevirtual 732	java/lang/reflect/Constructor:isAccessible	()Z
    //   96: ifne +9 -> 105
    //   99: aload 8
    //   101: iconst_1
    //   102: invokevirtual 735	java/lang/reflect/Constructor:setAccessible	(Z)V
    //   105: aload_3
    //   106: invokevirtual 739	java/lang/Class:getDeclaredFields	()[Ljava/lang/reflect/Field;
    //   109: astore 11
    //   111: new 741	java/util/ArrayList
    //   114: dup
    //   115: invokespecial 742	java/util/ArrayList:<init>	()V
    //   118: astore 12
    //   120: aload 11
    //   122: arraylength
    //   123: istore 13
    //   125: iconst_0
    //   126: istore 14
    //   128: iload 14
    //   130: iload 13
    //   132: if_icmplt +125 -> 257
    //   135: aload_0
    //   136: invokevirtual 746	processing/data/Table:rows	()Ljava/lang/Iterable;
    //   139: invokeinterface 752 1 0
    //   144: astore 21
    //   146: iconst_0
    //   147: istore 22
    //   149: aload 21
    //   151: invokeinterface 757 1 0
    //   156: ifne +150 -> 306
    //   159: aload 4
    //   161: invokevirtual 758	java/lang/reflect/Field:isAccessible	()Z
    //   164: ifne +9 -> 173
    //   167: aload 4
    //   169: iconst_1
    //   170: invokevirtual 759	java/lang/reflect/Field:setAccessible	(Z)V
    //   173: aload 4
    //   175: aload_1
    //   176: aload 6
    //   178: invokevirtual 763	java/lang/reflect/Field:set	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   181: iload 22
    //   183: pop
    //   184: return
    //   185: astore 40
    //   187: aload 40
    //   189: invokevirtual 766	java/lang/NoSuchFieldException:printStackTrace	()V
    //   192: aconst_null
    //   193: astore 6
    //   195: goto -132 -> 63
    //   198: astore 5
    //   200: aload 5
    //   202: invokevirtual 767	java/lang/SecurityException:printStackTrace	()V
    //   205: aconst_null
    //   206: astore 6
    //   208: goto -145 -> 63
    //   211: iconst_1
    //   212: anewarray 701	java/lang/Class
    //   215: dup
    //   216: iconst_0
    //   217: aload 7
    //   219: aastore
    //   220: astore 9
    //   222: aload_3
    //   223: aload 9
    //   225: invokevirtual 727	java/lang/Class:getDeclaredConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   228: astore 38
    //   230: aload 38
    //   232: astore 8
    //   234: goto -143 -> 91
    //   237: astore 37
    //   239: aload 37
    //   241: invokevirtual 767	java/lang/SecurityException:printStackTrace	()V
    //   244: goto -139 -> 105
    //   247: astore 10
    //   249: aload 10
    //   251: invokevirtual 768	java/lang/NoSuchMethodException:printStackTrace	()V
    //   254: goto -149 -> 105
    //   257: aload 11
    //   259: iload 14
    //   261: aaload
    //   262: astore 15
    //   264: aload_0
    //   265: aload 15
    //   267: invokevirtual 769	java/lang/reflect/Field:getName	()Ljava/lang/String;
    //   270: iconst_0
    //   271: invokevirtual 260	processing/data/Table:getColumnIndex	(Ljava/lang/String;Z)I
    //   274: iconst_m1
    //   275: if_icmpeq +25 -> 300
    //   278: aload 15
    //   280: invokevirtual 758	java/lang/reflect/Field:isAccessible	()Z
    //   283: ifne +9 -> 292
    //   286: aload 15
    //   288: iconst_1
    //   289: invokevirtual 759	java/lang/reflect/Field:setAccessible	(Z)V
    //   292: aload 12
    //   294: aload 15
    //   296: invokevirtual 772	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   299: pop
    //   300: iinc 14 1
    //   303: goto -175 -> 128
    //   306: aload 21
    //   308: invokeinterface 775 1 0
    //   313: checkcast 777	processing/data/TableRow
    //   316: astore 27
    //   318: aload 7
    //   320: ifnonnull +57 -> 377
    //   323: aload 8
    //   325: iconst_0
    //   326: anewarray 4	java/lang/Object
    //   329: invokevirtual 780	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   332: astore 28
    //   334: aload 12
    //   336: invokevirtual 781	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   339: astore 29
    //   341: aload 29
    //   343: invokeinterface 757 1 0
    //   348: istore 30
    //   350: iload 30
    //   352: ifne +43 -> 395
    //   355: iload 22
    //   357: iconst_1
    //   358: iadd
    //   359: istore 35
    //   361: aload 6
    //   363: iload 22
    //   365: aload 28
    //   367: invokestatic 784	java/lang/reflect/Array:set	(Ljava/lang/Object;ILjava/lang/Object;)V
    //   370: iload 35
    //   372: istore 22
    //   374: goto -225 -> 149
    //   377: aload 8
    //   379: iconst_1
    //   380: anewarray 4	java/lang/Object
    //   383: dup
    //   384: iconst_0
    //   385: aload_1
    //   386: aastore
    //   387: invokevirtual 780	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   390: astore 28
    //   392: goto -58 -> 334
    //   395: aload 29
    //   397: invokeinterface 775 1 0
    //   402: checkcast 707	java/lang/reflect/Field
    //   405: astore 31
    //   407: aload 31
    //   409: invokevirtual 769	java/lang/reflect/Field:getName	()Ljava/lang/String;
    //   412: astore 32
    //   414: aload 31
    //   416: invokevirtual 710	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   419: ldc 202
    //   421: if_acmpne +33 -> 454
    //   424: aload 31
    //   426: aload 28
    //   428: aload 27
    //   430: aload 32
    //   432: invokeinterface 787 2 0
    //   437: invokevirtual 763	java/lang/reflect/Field:set	(Ljava/lang/Object;Ljava/lang/Object;)V
    //   440: goto -99 -> 341
    //   443: astore 17
    //   445: iload 22
    //   447: pop
    //   448: aload 17
    //   450: invokevirtual 788	java/lang/InstantiationException:printStackTrace	()V
    //   453: return
    //   454: aload 31
    //   456: invokevirtual 710	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   459: getstatic 792	java/lang/Integer:TYPE	Ljava/lang/Class;
    //   462: if_acmpne +33 -> 495
    //   465: aload 31
    //   467: aload 28
    //   469: aload 27
    //   471: aload 32
    //   473: invokeinterface 794 2 0
    //   478: invokevirtual 797	java/lang/reflect/Field:setInt	(Ljava/lang/Object;I)V
    //   481: goto -140 -> 341
    //   484: astore 18
    //   486: iload 22
    //   488: pop
    //   489: aload 18
    //   491: invokevirtual 798	java/lang/IllegalAccessException:printStackTrace	()V
    //   494: return
    //   495: aload 31
    //   497: invokevirtual 710	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   500: getstatic 799	java/lang/Long:TYPE	Ljava/lang/Class;
    //   503: if_acmpne +33 -> 536
    //   506: aload 31
    //   508: aload 28
    //   510: aload 27
    //   512: aload 32
    //   514: invokeinterface 801 2 0
    //   519: invokevirtual 804	java/lang/reflect/Field:setLong	(Ljava/lang/Object;J)V
    //   522: goto -181 -> 341
    //   525: astore 19
    //   527: iload 22
    //   529: pop
    //   530: aload 19
    //   532: invokevirtual 805	java/lang/IllegalArgumentException:printStackTrace	()V
    //   535: return
    //   536: aload 31
    //   538: invokevirtual 710	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   541: getstatic 806	java/lang/Float:TYPE	Ljava/lang/Class;
    //   544: if_acmpne +33 -> 577
    //   547: aload 31
    //   549: aload 28
    //   551: aload 27
    //   553: aload 32
    //   555: invokeinterface 809 2 0
    //   560: invokevirtual 812	java/lang/reflect/Field:setFloat	(Ljava/lang/Object;F)V
    //   563: goto -222 -> 341
    //   566: astore 20
    //   568: iload 22
    //   570: pop
    //   571: aload 20
    //   573: invokevirtual 813	java/lang/reflect/InvocationTargetException:printStackTrace	()V
    //   576: return
    //   577: aload 31
    //   579: invokevirtual 710	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   582: getstatic 814	java/lang/Double:TYPE	Ljava/lang/Class;
    //   585: if_acmpne +22 -> 607
    //   588: aload 31
    //   590: aload 28
    //   592: aload 27
    //   594: aload 32
    //   596: invokeinterface 816 2 0
    //   601: invokevirtual 819	java/lang/reflect/Field:setDouble	(Ljava/lang/Object;D)V
    //   604: goto -263 -> 341
    //   607: aload 31
    //   609: invokevirtual 710	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   612: getstatic 822	java/lang/Boolean:TYPE	Ljava/lang/Class;
    //   615: if_acmpne +55 -> 670
    //   618: aload 27
    //   620: aload 32
    //   622: invokeinterface 787 2 0
    //   627: astore 34
    //   629: aload 34
    //   631: ifnull -290 -> 341
    //   634: aload 34
    //   636: invokevirtual 277	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   639: ldc_w 824
    //   642: invokevirtual 283	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   645: ifne +14 -> 659
    //   648: aload 34
    //   650: ldc_w 826
    //   653: invokevirtual 283	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   656: ifeq -315 -> 341
    //   659: aload 31
    //   661: aload 28
    //   663: iconst_1
    //   664: invokevirtual 830	java/lang/reflect/Field:setBoolean	(Ljava/lang/Object;Z)V
    //   667: goto -326 -> 341
    //   670: aload 31
    //   672: invokevirtual 710	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   675: getstatic 833	java/lang/Character:TYPE	Ljava/lang/Class;
    //   678: if_acmpne -337 -> 341
    //   681: aload 27
    //   683: aload 32
    //   685: invokeinterface 787 2 0
    //   690: astore 33
    //   692: aload 33
    //   694: ifnull -353 -> 341
    //   697: aload 33
    //   699: invokevirtual 836	java/lang/String:length	()I
    //   702: ifle -361 -> 341
    //   705: aload 31
    //   707: aload 28
    //   709: aload 33
    //   711: iconst_0
    //   712: invokevirtual 840	java/lang/String:charAt	(I)C
    //   715: invokevirtual 844	java/lang/reflect/Field:setChar	(Ljava/lang/Object;C)V
    //   718: goto -377 -> 341
    //   721: astore 20
    //   723: goto -152 -> 571
    //   726: astore 19
    //   728: goto -198 -> 530
    //   731: astore 18
    //   733: goto -244 -> 489
    //   736: astore 17
    //   738: goto -290 -> 448
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	741	0	this	Table
    //   0	741	1	paramObject	Object
    //   0	741	2	paramString	String
    //   1	222	3	localClass1	java.lang.Class
    //   3	171	4	localField1	java.lang.reflect.Field
    //   198	3	5	localSecurityException1	java.lang.SecurityException
    //   30	332	6	localObject1	Object
    //   67	252	7	localClass2	java.lang.Class
    //   70	308	8	localObject2	Object
    //   220	4	9	arrayOfClass1	java.lang.Class[]
    //   247	3	10	localNoSuchMethodException	java.lang.NoSuchMethodException
    //   109	149	11	arrayOfField	java.lang.reflect.Field[]
    //   118	217	12	localArrayList	ArrayList
    //   123	10	13	i	int
    //   126	175	14	j	int
    //   262	33	15	localField2	java.lang.reflect.Field
    //   443	6	17	localInstantiationException1	java.lang.InstantiationException
    //   736	1	17	localInstantiationException2	java.lang.InstantiationException
    //   484	6	18	localIllegalAccessException1	java.lang.IllegalAccessException
    //   731	1	18	localIllegalAccessException2	java.lang.IllegalAccessException
    //   525	6	19	localIllegalArgumentException1	IllegalArgumentException
    //   726	1	19	localIllegalArgumentException2	IllegalArgumentException
    //   566	6	20	localInvocationTargetException1	java.lang.reflect.InvocationTargetException
    //   721	1	20	localInvocationTargetException2	java.lang.reflect.InvocationTargetException
    //   144	163	21	localIterator1	Iterator
    //   147	422	22	k	int
    //   316	366	27	localTableRow	TableRow
    //   332	376	28	localObject3	Object
    //   339	57	29	localIterator2	Iterator
    //   348	3	30	bool1	boolean
    //   405	301	31	localField3	java.lang.reflect.Field
    //   412	272	32	str1	String
    //   690	20	33	str2	String
    //   627	22	34	str3	String
    //   359	12	35	m	int
    //   237	3	37	localSecurityException2	java.lang.SecurityException
    //   228	3	38	localConstructor	java.lang.reflect.Constructor
    //   81	4	39	arrayOfClass2	java.lang.Class[]
    //   185	3	40	localNoSuchFieldException	java.lang.NoSuchFieldException
    //   20	20	41	localClass3	java.lang.Class
    //   27	8	42	bool2	boolean
    //   49	4	43	n	int
    //   57	3	44	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   5	29	185	java/lang/NoSuchFieldException
    //   39	59	185	java/lang/NoSuchFieldException
    //   5	29	198	java/lang/SecurityException
    //   39	59	198	java/lang/SecurityException
    //   77	91	237	java/lang/SecurityException
    //   91	105	237	java/lang/SecurityException
    //   211	230	237	java/lang/SecurityException
    //   77	91	247	java/lang/NoSuchMethodException
    //   91	105	247	java/lang/NoSuchMethodException
    //   211	230	247	java/lang/NoSuchMethodException
    //   149	173	443	java/lang/InstantiationException
    //   173	181	443	java/lang/InstantiationException
    //   306	318	443	java/lang/InstantiationException
    //   323	334	443	java/lang/InstantiationException
    //   334	341	443	java/lang/InstantiationException
    //   341	350	443	java/lang/InstantiationException
    //   377	392	443	java/lang/InstantiationException
    //   395	440	443	java/lang/InstantiationException
    //   454	481	443	java/lang/InstantiationException
    //   495	522	443	java/lang/InstantiationException
    //   536	563	443	java/lang/InstantiationException
    //   577	604	443	java/lang/InstantiationException
    //   607	629	443	java/lang/InstantiationException
    //   634	659	443	java/lang/InstantiationException
    //   659	667	443	java/lang/InstantiationException
    //   670	692	443	java/lang/InstantiationException
    //   697	718	443	java/lang/InstantiationException
    //   149	173	484	java/lang/IllegalAccessException
    //   173	181	484	java/lang/IllegalAccessException
    //   306	318	484	java/lang/IllegalAccessException
    //   323	334	484	java/lang/IllegalAccessException
    //   334	341	484	java/lang/IllegalAccessException
    //   341	350	484	java/lang/IllegalAccessException
    //   377	392	484	java/lang/IllegalAccessException
    //   395	440	484	java/lang/IllegalAccessException
    //   454	481	484	java/lang/IllegalAccessException
    //   495	522	484	java/lang/IllegalAccessException
    //   536	563	484	java/lang/IllegalAccessException
    //   577	604	484	java/lang/IllegalAccessException
    //   607	629	484	java/lang/IllegalAccessException
    //   634	659	484	java/lang/IllegalAccessException
    //   659	667	484	java/lang/IllegalAccessException
    //   670	692	484	java/lang/IllegalAccessException
    //   697	718	484	java/lang/IllegalAccessException
    //   149	173	525	java/lang/IllegalArgumentException
    //   173	181	525	java/lang/IllegalArgumentException
    //   306	318	525	java/lang/IllegalArgumentException
    //   323	334	525	java/lang/IllegalArgumentException
    //   334	341	525	java/lang/IllegalArgumentException
    //   341	350	525	java/lang/IllegalArgumentException
    //   377	392	525	java/lang/IllegalArgumentException
    //   395	440	525	java/lang/IllegalArgumentException
    //   454	481	525	java/lang/IllegalArgumentException
    //   495	522	525	java/lang/IllegalArgumentException
    //   536	563	525	java/lang/IllegalArgumentException
    //   577	604	525	java/lang/IllegalArgumentException
    //   607	629	525	java/lang/IllegalArgumentException
    //   634	659	525	java/lang/IllegalArgumentException
    //   659	667	525	java/lang/IllegalArgumentException
    //   670	692	525	java/lang/IllegalArgumentException
    //   697	718	525	java/lang/IllegalArgumentException
    //   149	173	566	java/lang/reflect/InvocationTargetException
    //   173	181	566	java/lang/reflect/InvocationTargetException
    //   306	318	566	java/lang/reflect/InvocationTargetException
    //   323	334	566	java/lang/reflect/InvocationTargetException
    //   334	341	566	java/lang/reflect/InvocationTargetException
    //   341	350	566	java/lang/reflect/InvocationTargetException
    //   377	392	566	java/lang/reflect/InvocationTargetException
    //   395	440	566	java/lang/reflect/InvocationTargetException
    //   454	481	566	java/lang/reflect/InvocationTargetException
    //   495	522	566	java/lang/reflect/InvocationTargetException
    //   536	563	566	java/lang/reflect/InvocationTargetException
    //   577	604	566	java/lang/reflect/InvocationTargetException
    //   607	629	566	java/lang/reflect/InvocationTargetException
    //   634	659	566	java/lang/reflect/InvocationTargetException
    //   659	667	566	java/lang/reflect/InvocationTargetException
    //   670	692	566	java/lang/reflect/InvocationTargetException
    //   697	718	566	java/lang/reflect/InvocationTargetException
    //   135	146	721	java/lang/reflect/InvocationTargetException
    //   361	370	721	java/lang/reflect/InvocationTargetException
    //   135	146	726	java/lang/IllegalArgumentException
    //   361	370	726	java/lang/IllegalArgumentException
    //   135	146	731	java/lang/IllegalAccessException
    //   361	370	731	java/lang/IllegalAccessException
    //   135	146	736	java/lang/InstantiationException
    //   361	370	736	java/lang/InstantiationException
  }
  
  public void removeColumn(int paramInt)
  {
    Object[] arrayOfObject = new Object[1 + this.columns.length];
    System.arraycopy(this.columns, 0, arrayOfObject, 0, paramInt);
    System.arraycopy(this.columns, paramInt + 1, arrayOfObject, paramInt, 1 + (this.columns.length - paramInt));
    this.columns = arrayOfObject;
  }
  
  public void removeColumn(String paramString)
  {
    removeColumn(getColumnIndex(paramString));
  }
  
  public void removeRow(int paramInt)
  {
    int i = 0;
    if (i >= this.columns.length)
    {
      this.rowCount = (-1 + this.rowCount);
      return;
    }
    switch (this.columnTypes[i])
    {
    }
    for (;;)
    {
      i++;
      break;
      int[] arrayOfInt = new int[-1 + this.rowCount];
      System.arraycopy(this.columns[i], 0, arrayOfInt, 0, paramInt);
      System.arraycopy(this.columns[i], paramInt + 1, arrayOfInt, paramInt, -1 + (this.rowCount - paramInt));
      this.columns[i] = arrayOfInt;
      continue;
      long[] arrayOfLong = new long[-1 + this.rowCount];
      System.arraycopy(this.columns[i], 0, arrayOfLong, 0, paramInt);
      System.arraycopy(this.columns[i], paramInt + 1, arrayOfLong, paramInt, -1 + (this.rowCount - paramInt));
      this.columns[i] = arrayOfLong;
      continue;
      float[] arrayOfFloat = new float[-1 + this.rowCount];
      System.arraycopy(this.columns[i], 0, arrayOfFloat, 0, paramInt);
      System.arraycopy(this.columns[i], paramInt + 1, arrayOfFloat, paramInt, -1 + (this.rowCount - paramInt));
      this.columns[i] = arrayOfFloat;
      continue;
      double[] arrayOfDouble = new double[-1 + this.rowCount];
      System.arraycopy(this.columns[i], 0, arrayOfDouble, 0, paramInt);
      System.arraycopy(this.columns[i], paramInt + 1, arrayOfDouble, paramInt, -1 + (this.rowCount - paramInt));
      this.columns[i] = arrayOfDouble;
      continue;
      String[] arrayOfString = new String[-1 + this.rowCount];
      System.arraycopy(this.columns[i], 0, arrayOfString, 0, paramInt);
      System.arraycopy(this.columns[i], paramInt + 1, arrayOfString, paramInt, -1 + (this.rowCount - paramInt));
      this.columns[i] = arrayOfString;
    }
  }
  
  @Deprecated
  public String[] removeTitleRow()
  {
    String[] arrayOfString = getStringRow(0);
    removeRow(0);
    setColumnTitles(arrayOfString);
    return arrayOfString;
  }
  
  public void removeTokens(String paramString)
  {
    for (int i = 0;; i++)
    {
      if (i >= getColumnCount()) {
        return;
      }
      removeTokens(paramString, i);
    }
  }
  
  public void removeTokens(String paramString, int paramInt)
  {
    int i = 0;
    if (i >= this.rowCount) {
      return;
    }
    String str = getString(i, paramInt);
    char[] arrayOfChar;
    int j;
    if (str != null)
    {
      arrayOfChar = str.toCharArray();
      j = 0;
    }
    for (int k = 0;; k++)
    {
      if (k >= arrayOfChar.length)
      {
        if (j != arrayOfChar.length) {
          setString(i, paramInt, new String(arrayOfChar, 0, j));
        }
        i++;
        break;
      }
      if (paramString.indexOf(arrayOfChar[k]) == -1)
      {
        if (j != k) {
          arrayOfChar[j] = arrayOfChar[k];
        }
        j++;
      }
    }
  }
  
  public void removeTokens(String paramString1, String paramString2)
  {
    removeTokens(paramString1, getColumnIndex(paramString2));
  }
  
  public void replace(String paramString1, String paramString2)
  {
    for (int i = 0;; i++)
    {
      if (i >= this.columns.length) {
        return;
      }
      replace(paramString1, paramString2, i);
    }
  }
  
  public void replace(String paramString1, String paramString2, int paramInt)
  {
    String[] arrayOfString;
    if (this.columnTypes[paramInt] == 0) {
      arrayOfString = (String[])this.columns[paramInt];
    }
    for (int i = 0;; i++)
    {
      if (i >= this.rowCount) {
        return;
      }
      if (arrayOfString[i].equals(paramString1)) {
        arrayOfString[i] = paramString2;
      }
    }
  }
  
  public void replace(String paramString1, String paramString2, String paramString3)
  {
    replace(paramString1, paramString2, getColumnIndex(paramString3));
  }
  
  public void replaceAll(String paramString1, String paramString2)
  {
    for (int i = 0;; i++)
    {
      if (i >= this.columns.length) {
        return;
      }
      replaceAll(paramString1, paramString2, i);
    }
  }
  
  public void replaceAll(String paramString1, String paramString2, int paramInt)
  {
    checkBounds(-1, paramInt);
    if (this.columnTypes[paramInt] == 0)
    {
      String[] arrayOfString = (String[])this.columns[paramInt];
      for (int i = 0;; i++)
      {
        if (i >= this.rowCount) {
          return;
        }
        if (arrayOfString[i] != null) {
          arrayOfString[i] = arrayOfString[i].replaceAll(paramString1, paramString2);
        }
      }
    }
    throw new IllegalArgumentException("replaceAll() can only be used on String columns");
  }
  
  public void replaceAll(String paramString1, String paramString2, String paramString3)
  {
    replaceAll(paramString1, paramString2, getColumnIndex(paramString3));
  }
  
  public Iterable<TableRow> rows()
  {
    new Iterable()
    {
      public Iterator<TableRow> iterator()
      {
        if (Table.this.rowIterator == null) {
          Table.this.rowIterator = new Table.RowIterator(Table.this);
        }
        for (;;)
        {
          return Table.this.rowIterator;
          Table.this.rowIterator.reset();
        }
      }
    };
  }
  
  public Iterator<TableRow> rows(int[] paramArrayOfInt)
  {
    return new RowIndexIterator(this, paramArrayOfInt);
  }
  
  public void save(File paramFile, String paramString)
    throws IOException
  {
    save(new FileOutputStream(paramFile), checkOptions(paramFile, paramString));
  }
  
  public void save(OutputStream paramOutputStream, String paramString)
  {
    PrintWriter localPrintWriter = PApplet.createWriter(paramOutputStream);
    String[] arrayOfString;
    int j;
    if (paramString != null)
    {
      arrayOfString = PApplet.splitTokens(paramString, ", ");
      int i = arrayOfString.length;
      j = 0;
      if (j < i) {}
    }
    else
    {
      localPrintWriter.close();
      return;
    }
    String str = arrayOfString[j];
    if (str.equals("csv")) {
      writeCSV(localPrintWriter);
    }
    for (;;)
    {
      j++;
      break;
      if (str.equals("tsv"))
      {
        writeTSV(localPrintWriter);
      }
      else
      {
        if (!str.equals("html")) {
          break label105;
        }
        writeHTML(localPrintWriter);
      }
    }
    label105:
    throw new IllegalArgumentException("'" + str + "' not understood. " + "Only csv, tsv, and html are " + "accepted as save parameters");
  }
  
  public void setColumnCount(int paramInt)
  {
    int i = this.columns.length;
    if (i != paramInt) {
      this.columns = ((Object[])PApplet.expand(this.columns, paramInt));
    }
    for (int j = i;; j++)
    {
      if (j >= paramInt)
      {
        if (this.columnTitles != null) {
          this.columnTitles = PApplet.expand(this.columnTitles, paramInt);
        }
        this.columnTypes = PApplet.expand(this.columnTypes, paramInt);
        this.columnCategories = ((HashMapBlows[])PApplet.expand(this.columnCategories, paramInt));
        return;
      }
      this.columns[j] = new String[this.rowCount];
    }
  }
  
  public void setColumnTitle(int paramInt, String paramString)
  {
    checkColumn(paramInt);
    if (this.columnTitles == null) {
      this.columnTitles = new String[getColumnCount()];
    }
    this.columnTitles[paramInt] = paramString;
    this.columnIndices = null;
  }
  
  public void setColumnTitles(String[] paramArrayOfString)
  {
    if (paramArrayOfString != null) {
      checkColumn(-1 + paramArrayOfString.length);
    }
    this.columnTitles = paramArrayOfString;
    this.columnIndices = null;
  }
  
  protected void setColumnType(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt2;
    int i1;
    switch (paramInt2)
    {
    default: 
      throw new IllegalArgumentException("That's not a valid column type.");
    case 1: 
      arrayOfInt2 = new int[this.rowCount];
      i1 = 0;
      if (i1 >= this.rowCount) {
        this.columns[paramInt1] = arrayOfInt2;
      }
    case 2: 
    case 3: 
    case 4: 
    case 0: 
      do
      {
        this.columnTypes[paramInt1] = paramInt2;
        return;
        arrayOfInt2[i1] = PApplet.parseInt(getString(i1, paramInt1), this.missingInt);
        i1++;
        break;
        long[] arrayOfLong = new long[this.rowCount];
        int n = 0;
        for (;;)
        {
          if (n >= this.rowCount)
          {
            this.columns[paramInt1] = arrayOfLong;
            break;
          }
          String str2 = getString(n, paramInt1);
          try
          {
            arrayOfLong[n] = Long.parseLong(str2);
            n++;
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            for (;;)
            {
              arrayOfLong[n] = this.missingLong;
            }
          }
        }
        float[] arrayOfFloat = new float[this.rowCount];
        for (int m = 0;; m++)
        {
          if (m >= this.rowCount)
          {
            this.columns[paramInt1] = arrayOfFloat;
            break;
          }
          arrayOfFloat[m] = PApplet.parseFloat(getString(m, paramInt1), this.missingFloat);
        }
        double[] arrayOfDouble = new double[this.rowCount];
        int k = 0;
        for (;;)
        {
          if (k >= this.rowCount)
          {
            this.columns[paramInt1] = arrayOfDouble;
            break;
          }
          String str1 = getString(k, paramInt1);
          try
          {
            arrayOfDouble[k] = Double.parseDouble(str1);
            k++;
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            for (;;)
            {
              arrayOfDouble[k] = this.missingDouble;
            }
          }
        }
      } while (this.columnTypes[paramInt1] == 0);
      String[] arrayOfString = new String[this.rowCount];
      for (int j = 0;; j++)
      {
        if (j >= this.rowCount)
        {
          this.columns[paramInt1] = arrayOfString;
          break;
        }
        arrayOfString[j] = getString(j, paramInt1);
      }
    }
    int[] arrayOfInt1 = new int[this.rowCount];
    HashMapBlows localHashMapBlows = new HashMapBlows();
    for (int i = 0;; i++)
    {
      if (i >= this.rowCount)
      {
        this.columnCategories[paramInt1] = localHashMapBlows;
        this.columns[paramInt1] = arrayOfInt1;
        break;
      }
      arrayOfInt1[i] = localHashMapBlows.index(getString(i, paramInt1));
    }
  }
  
  public void setColumnType(int paramInt, String paramString)
  {
    int i;
    if (paramString.equals("String")) {
      i = 0;
    }
    for (;;)
    {
      setColumnType(paramInt, i);
      return;
      if (paramString.equals("int"))
      {
        i = 1;
      }
      else if (paramString.equals("long"))
      {
        i = 2;
      }
      else if (paramString.equals("float"))
      {
        i = 3;
      }
      else if (paramString.equals("double"))
      {
        i = 4;
      }
      else
      {
        if (!paramString.equals("categorical")) {
          break;
        }
        i = 5;
      }
    }
    throw new IllegalArgumentException("'" + paramString + "' is not a valid column type.");
  }
  
  protected void setColumnType(String paramString, int paramInt)
  {
    setColumnType(checkColumnIndex(paramString), paramInt);
  }
  
  public void setColumnType(String paramString1, String paramString2)
  {
    setColumnType(checkColumnIndex(paramString1), paramString2);
  }
  
  public void setColumnTypes(Table paramTable)
  {
    int i = 1;
    boolean bool = paramTable.hasColumnTitles();
    int j = 0;
    if (bool)
    {
      j = paramTable.getColumnIndex("title", true);
      i = paramTable.getColumnIndex("type", true);
    }
    setColumnTitles(paramTable.getStringColumn(j));
    if (paramTable.getColumnCount() > 1) {}
    for (int k = 0;; k++)
    {
      if (k >= paramTable.getRowCount()) {
        return;
      }
      setColumnType(k, paramTable.getString(k, i));
    }
  }
  
  public void setDouble(int paramInt1, int paramInt2, double paramDouble)
  {
    if (this.columnTypes[paramInt2] == 0)
    {
      setString(paramInt1, paramInt2, String.valueOf(paramDouble));
      return;
    }
    checkSize(paramInt1, paramInt2);
    if (this.columnTypes[paramInt2] != 4) {
      throw new IllegalArgumentException("Column " + paramInt2 + " is not a 'double' column.");
    }
    ((double[])this.columns[paramInt2])[paramInt1] = paramDouble;
  }
  
  public void setDouble(int paramInt, String paramString, double paramDouble)
  {
    setDouble(paramInt, getColumnIndex(paramString), paramDouble);
  }
  
  public void setFloat(int paramInt1, int paramInt2, float paramFloat)
  {
    if (this.columnTypes[paramInt2] == 0)
    {
      setString(paramInt1, paramInt2, String.valueOf(paramFloat));
      return;
    }
    checkSize(paramInt1, paramInt2);
    if (this.columnTypes[paramInt2] != 3) {
      throw new IllegalArgumentException("Column " + paramInt2 + " is not a float column.");
    }
    ((float[])this.columns[paramInt2])[paramInt1] = paramFloat;
  }
  
  public void setFloat(int paramInt, String paramString, float paramFloat)
  {
    setFloat(paramInt, getColumnIndex(paramString), paramFloat);
  }
  
  public void setInt(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.columnTypes[paramInt2] == 0)
    {
      setString(paramInt1, paramInt2, String.valueOf(paramInt3));
      return;
    }
    checkSize(paramInt1, paramInt2);
    if (this.columnTypes[paramInt2] != 1) {
      throw new IllegalArgumentException("Column " + paramInt2 + " is not an int column.");
    }
    ((int[])this.columns[paramInt2])[paramInt1] = paramInt3;
  }
  
  public void setInt(int paramInt1, String paramString, int paramInt2)
  {
    setInt(paramInt1, getColumnIndex(paramString), paramInt2);
  }
  
  public void setLong(int paramInt1, int paramInt2, long paramLong)
  {
    if (this.columnTypes[paramInt2] == 0)
    {
      setString(paramInt1, paramInt2, String.valueOf(paramLong));
      return;
    }
    checkSize(paramInt1, paramInt2);
    if (this.columnTypes[paramInt2] != 2) {
      throw new IllegalArgumentException("Column " + paramInt2 + " is not a 'long' column.");
    }
    ((long[])this.columns[paramInt2])[paramInt1] = paramLong;
  }
  
  public void setLong(int paramInt, String paramString, long paramLong)
  {
    setLong(paramInt, getColumnIndex(paramString), paramLong);
  }
  
  public void setMissingDouble(double paramDouble)
  {
    this.missingDouble = paramDouble;
  }
  
  public void setMissingFloat(float paramFloat)
  {
    this.missingFloat = paramFloat;
  }
  
  public void setMissingInt(int paramInt)
  {
    this.missingInt = paramInt;
  }
  
  public void setMissingLong(long paramLong)
  {
    this.missingLong = paramLong;
  }
  
  public void setMissingString(String paramString)
  {
    this.missingString = paramString;
  }
  
  public void setRow(int paramInt, Object[] paramArrayOfObject)
  {
    checkSize(paramInt, -1 + paramArrayOfObject.length);
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfObject.length) {
        return;
      }
      setRowCol(paramInt, i, paramArrayOfObject[i]);
    }
  }
  
  protected void setRowCol(int paramInt1, int paramInt2, Object paramObject)
  {
    switch (this.columnTypes[paramInt2])
    {
    default: 
      throw new IllegalArgumentException("That's not a valid column type.");
    case 0: 
      String[] arrayOfString = (String[])this.columns[paramInt2];
      if (paramObject == null)
      {
        arrayOfString[paramInt1] = null;
        return;
      }
      arrayOfString[paramInt1] = String.valueOf(paramObject);
      return;
    case 1: 
      int[] arrayOfInt2 = (int[])this.columns[paramInt2];
      if (paramObject == null)
      {
        arrayOfInt2[paramInt1] = this.missingInt;
        return;
      }
      if ((paramObject instanceof Integer))
      {
        arrayOfInt2[paramInt1] = ((Integer)paramObject).intValue();
        return;
      }
      arrayOfInt2[paramInt1] = PApplet.parseInt(String.valueOf(paramObject), this.missingInt);
      return;
    case 2: 
      long[] arrayOfLong = (long[])this.columns[paramInt2];
      if (paramObject == null)
      {
        arrayOfLong[paramInt1] = this.missingLong;
        return;
      }
      if ((paramObject instanceof Long))
      {
        arrayOfLong[paramInt1] = ((Long)paramObject).longValue();
        return;
      }
      try
      {
        arrayOfLong[paramInt1] = Long.parseLong(String.valueOf(paramObject));
        return;
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        arrayOfLong[paramInt1] = this.missingLong;
        return;
      }
    case 3: 
      float[] arrayOfFloat = (float[])this.columns[paramInt2];
      if (paramObject == null)
      {
        arrayOfFloat[paramInt1] = this.missingFloat;
        return;
      }
      if ((paramObject instanceof Float))
      {
        arrayOfFloat[paramInt1] = ((Float)paramObject).floatValue();
        return;
      }
      arrayOfFloat[paramInt1] = PApplet.parseFloat(String.valueOf(paramObject), this.missingFloat);
      return;
    case 4: 
      double[] arrayOfDouble = (double[])this.columns[paramInt2];
      if (paramObject == null)
      {
        arrayOfDouble[paramInt1] = this.missingDouble;
        return;
      }
      if ((paramObject instanceof Double))
      {
        arrayOfDouble[paramInt1] = ((Double)paramObject).doubleValue();
        return;
      }
      try
      {
        arrayOfDouble[paramInt1] = Double.parseDouble(String.valueOf(paramObject));
        return;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        arrayOfDouble[paramInt1] = this.missingDouble;
        return;
      }
    }
    int[] arrayOfInt1 = (int[])this.columns[paramInt2];
    if (paramObject == null)
    {
      arrayOfInt1[paramInt1] = this.missingCategory;
      return;
    }
    arrayOfInt1[paramInt1] = this.columnCategories[paramInt2].index(String.valueOf(paramObject));
  }
  
  public void setRowCount(int paramInt)
  {
    long l;
    int i;
    if (paramInt != this.rowCount)
    {
      if (paramInt > 1000000) {
        System.out.println("setting row count to " + PApplet.nfc(paramInt));
      }
      l = System.currentTimeMillis();
      i = 0;
    }
    for (;;)
    {
      if (i >= this.columns.length)
      {
        if (paramInt > 1000000)
        {
          int j = (int)(System.currentTimeMillis() - l);
          System.out.println("  resize took " + PApplet.nfc(j) + " ms");
        }
        this.rowCount = paramInt;
        return;
      }
      switch (this.columnTypes[i])
      {
      default: 
        label156:
        if (paramInt <= 1000000) {
          break;
        }
      }
      try
      {
        Thread.sleep(10L);
        i++;
        continue;
        this.columns[i] = PApplet.expand((int[])this.columns[i], paramInt);
        break label156;
        this.columns[i] = PApplet.expand((long[])this.columns[i], paramInt);
        break label156;
        this.columns[i] = PApplet.expand((float[])this.columns[i], paramInt);
        break label156;
        this.columns[i] = PApplet.expand((double[])this.columns[i], paramInt);
        break label156;
        this.columns[i] = PApplet.expand((String[])this.columns[i], paramInt);
        break label156;
        this.columns[i] = PApplet.expand((int[])this.columns[i], paramInt);
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          localInterruptedException.printStackTrace();
        }
      }
    }
  }
  
  public void setString(int paramInt1, int paramInt2, String paramString)
  {
    checkSize(paramInt1, paramInt2);
    if (this.columnTypes[paramInt2] != 0) {
      throw new IllegalArgumentException("Column " + paramInt2 + " is not a String column.");
    }
    ((String[])this.columns[paramInt2])[paramInt1] = paramString;
  }
  
  public void setString(int paramInt, String paramString1, String paramString2)
  {
    setString(paramInt, checkColumnIndex(paramString1), paramString2);
  }
  
  public void setTableType(String paramString)
  {
    for (int i = 0;; i++)
    {
      if (i >= getColumnCount()) {
        return;
      }
      setColumnType(i, paramString);
    }
  }
  
  public void trim()
  {
    for (int i = 0;; i++)
    {
      if (i >= getColumnCount()) {
        return;
      }
      trim(i);
    }
  }
  
  public void trim(int paramInt)
  {
    String[] arrayOfString;
    if (this.columnTypes[paramInt] == 0) {
      arrayOfString = (String[])this.columns[paramInt];
    }
    for (int i = 0;; i++)
    {
      if (i >= this.rowCount) {
        return;
      }
      if (arrayOfString[i] != null) {
        arrayOfString[i] = PApplet.trim(arrayOfString[i]);
      }
    }
  }
  
  public void trim(String paramString)
  {
    trim(getColumnIndex(paramString));
  }
  
  protected void writeCSV(PrintWriter paramPrintWriter)
  {
    if (this.columnTitles != null) {}
    int i;
    for (int k = 0;; k++)
    {
      if (k >= this.columns.length)
      {
        paramPrintWriter.println();
        i = 0;
        if (i < this.rowCount) {
          break;
        }
        paramPrintWriter.flush();
        return;
      }
      if (k != 0) {
        paramPrintWriter.print(',');
      }
      if (this.columnTitles[k] != null) {
        writeEntryCSV(paramPrintWriter, this.columnTitles[k]);
      }
    }
    for (int j = 0;; j++)
    {
      if (j >= getColumnCount())
      {
        paramPrintWriter.println();
        i++;
        break;
      }
      if (j != 0) {
        paramPrintWriter.print(',');
      }
      String str = getString(i, j);
      if (str != null) {
        writeEntryCSV(paramPrintWriter, str);
      }
    }
  }
  
  protected void writeEntryCSV(PrintWriter paramPrintWriter, String paramString)
  {
    char[] arrayOfChar;
    int i;
    if (paramString != null)
    {
      if (paramString.indexOf('"') == -1) {
        break label75;
      }
      arrayOfChar = paramString.toCharArray();
      paramPrintWriter.print('"');
      i = 0;
      if (i >= arrayOfChar.length) {
        paramPrintWriter.print('"');
      }
    }
    else
    {
      return;
    }
    if (arrayOfChar[i] == '"') {
      paramPrintWriter.print("\"\"");
    }
    for (;;)
    {
      i++;
      break;
      paramPrintWriter.print(arrayOfChar[i]);
    }
    label75:
    if ((paramString.indexOf(',') != -1) || (paramString.indexOf('\n') != -1) || (paramString.indexOf('\r') != -1))
    {
      paramPrintWriter.print('"');
      paramPrintWriter.print(paramString);
      paramPrintWriter.print('"');
      return;
    }
    if ((paramString.length() > 0) && ((paramString.charAt(0) == ' ') || (paramString.charAt(-1 + paramString.length()) == ' ')))
    {
      paramPrintWriter.print('"');
      paramPrintWriter.print(paramString);
      paramPrintWriter.print('"');
      return;
    }
    paramPrintWriter.print(paramString);
  }
  
  protected void writeEntryHTML(PrintWriter paramPrintWriter, String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    int i = arrayOfChar.length;
    int j = 0;
    if (j >= i) {
      return;
    }
    char c = arrayOfChar[j];
    if (c == '<') {
      paramPrintWriter.print("&lt;");
    }
    for (;;)
    {
      j++;
      break;
      if (c == '>') {
        paramPrintWriter.print("&gt;");
      } else if (c == '&') {
        paramPrintWriter.print("&amp;");
      } else if (c == '\'') {
        paramPrintWriter.print("&apos;");
      } else if (c == '"') {
        paramPrintWriter.print("&quot;");
      } else {
        paramPrintWriter.print(c);
      }
    }
  }
  
  protected void writeHTML(PrintWriter paramPrintWriter)
  {
    paramPrintWriter.println("<html>");
    paramPrintWriter.println("<head>");
    paramPrintWriter.println("  <meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" />");
    paramPrintWriter.println("</head>");
    paramPrintWriter.println("<body>");
    paramPrintWriter.println("  <table>");
    int i = 0;
    if (i >= getRowCount())
    {
      paramPrintWriter.println("  </table>");
      paramPrintWriter.println("</body>");
      paramPrintWriter.println("</hmtl>");
      paramPrintWriter.flush();
      return;
    }
    paramPrintWriter.println("    <tr>");
    for (int j = 0;; j++)
    {
      if (j >= getColumnCount())
      {
        paramPrintWriter.println("    </tr>");
        i++;
        break;
      }
      String str = getString(i, j);
      paramPrintWriter.print("      <td>");
      writeEntryHTML(paramPrintWriter, str);
      paramPrintWriter.println("      </td>");
    }
  }
  
  protected void writeTSV(PrintWriter paramPrintWriter)
  {
    if (this.columnTitles != null) {}
    int i;
    for (int k = 0;; k++)
    {
      if (k >= this.columns.length)
      {
        paramPrintWriter.println();
        i = 0;
        if (i < this.rowCount) {
          break;
        }
        paramPrintWriter.flush();
        return;
      }
      if (k != 0) {
        paramPrintWriter.print('\t');
      }
      if (this.columnTitles[k] != null) {
        paramPrintWriter.print(this.columnTitles[k]);
      }
    }
    for (int j = 0;; j++)
    {
      if (j >= getColumnCount())
      {
        paramPrintWriter.println();
        i++;
        break;
      }
      if (j != 0) {
        paramPrintWriter.print('\t');
      }
      String str = getString(i, j);
      if (str != null) {
        paramPrintWriter.print(str);
      }
    }
  }
  
  class HashMapBlows
  {
    HashMap<String, Integer> dataToIndex = new HashMap();
    ArrayList<String> indexToData = new ArrayList();
    
    HashMapBlows() {}
    
    int index(String paramString)
    {
      Integer localInteger = (Integer)this.dataToIndex.get(paramString);
      if (localInteger != null) {
        return localInteger.intValue();
      }
      int i = this.dataToIndex.size();
      this.dataToIndex.put(paramString, Integer.valueOf(i));
      this.indexToData.add(paramString);
      return i;
    }
    
    String key(int paramInt)
    {
      return (String)this.indexToData.get(paramInt);
    }
    
    void read(DataInputStream paramDataInputStream)
      throws IOException
    {
      int i = paramDataInputStream.readInt();
      this.dataToIndex = new HashMap(i);
      for (int j = 0;; j++)
      {
        if (j >= i) {
          return;
        }
        String str = paramDataInputStream.readUTF();
        this.dataToIndex.put(str, Integer.valueOf(j));
        this.indexToData.add(str);
      }
    }
    
    int size()
    {
      return this.dataToIndex.size();
    }
    
    void write(DataOutputStream paramDataOutputStream)
      throws IOException
    {
      paramDataOutputStream.writeInt(size());
      Iterator localIterator = this.indexToData.iterator();
      for (;;)
      {
        if (!localIterator.hasNext()) {
          return;
        }
        paramDataOutputStream.writeUTF((String)localIterator.next());
      }
    }
    
    void writeln(PrintWriter paramPrintWriter)
      throws IOException
    {
      Iterator localIterator = this.indexToData.iterator();
      for (;;)
      {
        if (!localIterator.hasNext())
        {
          paramPrintWriter.flush();
          paramPrintWriter.close();
          return;
        }
        paramPrintWriter.println((String)localIterator.next());
      }
    }
  }
  
  class HashMapSucks
    extends HashMap<String, Integer>
  {
    HashMapSucks() {}
    
    void check(String paramString)
    {
      if (get(paramString) == null) {
        put(paramString, Integer.valueOf(0));
      }
    }
    
    void increment(String paramString)
    {
      Integer localInteger = (Integer)get(paramString);
      if (localInteger == null)
      {
        put(paramString, Integer.valueOf(1));
        return;
      }
      put(paramString, Integer.valueOf(1 + localInteger.intValue()));
    }
  }
  
  static class RowIndexIterator
    implements Iterator<TableRow>
  {
    int index;
    int[] indices;
    Table.RowPointer rp;
    Table table;
    
    public RowIndexIterator(Table paramTable, int[] paramArrayOfInt)
    {
      this.table = paramTable;
      this.indices = paramArrayOfInt;
      this.index = -1;
      this.rp = new Table.RowPointer(paramTable, -1);
    }
    
    public boolean hasNext()
    {
      return 1 + this.index < this.indices.length;
    }
    
    public TableRow next()
    {
      Table.RowPointer localRowPointer = this.rp;
      int[] arrayOfInt = this.indices;
      int i = 1 + this.index;
      this.index = i;
      localRowPointer.setRow(arrayOfInt[i]);
      return this.rp;
    }
    
    public void remove()
    {
      this.table.removeRow(this.indices[this.index]);
    }
    
    public void reset()
    {
      this.index = -1;
    }
  }
  
  static class RowIterator
    implements Iterator<TableRow>
  {
    int row;
    Table.RowPointer rp;
    Table table;
    
    public RowIterator(Table paramTable)
    {
      this.table = paramTable;
      this.row = -1;
      this.rp = new Table.RowPointer(paramTable, this.row);
    }
    
    public boolean hasNext()
    {
      return 1 + this.row < this.table.getRowCount();
    }
    
    public TableRow next()
    {
      Table.RowPointer localRowPointer = this.rp;
      int i = 1 + this.row;
      this.row = i;
      localRowPointer.setRow(i);
      return this.rp;
    }
    
    public void remove()
    {
      this.table.removeRow(this.row);
    }
    
    public void reset()
    {
      this.row = -1;
    }
  }
  
  static class RowPointer
    implements TableRow
  {
    int row;
    Table table;
    
    public RowPointer(Table paramTable, int paramInt)
    {
      this.table = paramTable;
      this.row = paramInt;
    }
    
    public double getDouble(int paramInt)
    {
      return this.table.getDouble(this.row, paramInt);
    }
    
    public double getDouble(String paramString)
    {
      return this.table.getDouble(this.row, paramString);
    }
    
    public float getFloat(int paramInt)
    {
      return this.table.getFloat(this.row, paramInt);
    }
    
    public float getFloat(String paramString)
    {
      return this.table.getFloat(this.row, paramString);
    }
    
    public int getInt(int paramInt)
    {
      return this.table.getInt(this.row, paramInt);
    }
    
    public int getInt(String paramString)
    {
      return this.table.getInt(this.row, paramString);
    }
    
    public long getLong(int paramInt)
    {
      return this.table.getLong(this.row, paramInt);
    }
    
    public long getLong(String paramString)
    {
      return this.table.getLong(this.row, paramString);
    }
    
    public String getString(int paramInt)
    {
      return this.table.getString(this.row, paramInt);
    }
    
    public String getString(String paramString)
    {
      return this.table.getString(this.row, paramString);
    }
    
    public void setDouble(int paramInt, double paramDouble)
    {
      this.table.setDouble(this.row, paramInt, paramDouble);
    }
    
    public void setDouble(String paramString, double paramDouble)
    {
      this.table.setDouble(this.row, paramString, paramDouble);
    }
    
    public void setFloat(int paramInt, float paramFloat)
    {
      this.table.setFloat(this.row, paramInt, paramFloat);
    }
    
    public void setFloat(String paramString, float paramFloat)
    {
      this.table.setFloat(this.row, paramString, paramFloat);
    }
    
    public void setInt(int paramInt1, int paramInt2)
    {
      this.table.setInt(this.row, paramInt1, paramInt2);
    }
    
    public void setInt(String paramString, int paramInt)
    {
      this.table.setInt(this.row, paramString, paramInt);
    }
    
    public void setLong(int paramInt, long paramLong)
    {
      this.table.setLong(this.row, paramInt, paramLong);
    }
    
    public void setLong(String paramString, long paramLong)
    {
      this.table.setLong(this.row, paramString, paramLong);
    }
    
    public void setRow(int paramInt)
    {
      this.row = paramInt;
    }
    
    public void setString(int paramInt, String paramString)
    {
      this.table.setString(this.row, paramInt, paramString);
    }
    
    public void setString(String paramString1, String paramString2)
    {
      this.table.setString(this.row, paramString1, paramString2);
    }
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.data.Table
 * JD-Core Version:    0.7.0.1
 */