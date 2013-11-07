package processing.opengl.tess;

class PriorityQHeap
  extends PriorityQ
{
  int freeList;
  PriorityQ.PQhandleElem[] handles;
  boolean initialized;
  PriorityQ.Leq leq;
  int max = 32;
  PriorityQ.PQnode[] nodes = new PriorityQ.PQnode[33];
  int size = 0;
  
  static
  {
    if (!PriorityQHeap.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public PriorityQHeap(PriorityQ.Leq paramLeq)
  {
    int i = 0;
    if (i >= this.nodes.length) {
      this.handles = new PriorityQ.PQhandleElem[33];
    }
    for (int j = 0;; j++)
    {
      if (j >= this.handles.length)
      {
        this.initialized = false;
        this.freeList = 0;
        this.leq = paramLeq;
        this.nodes[1].handle = 1;
        this.handles[1].key = null;
        return;
        this.nodes[i] = new PriorityQ.PQnode();
        i++;
        break;
      }
      this.handles[j] = new PriorityQ.PQhandleElem();
    }
  }
  
  void FloatDown(int paramInt)
  {
    PriorityQ.PQnode[] arrayOfPQnode = this.nodes;
    PriorityQ.PQhandleElem[] arrayOfPQhandleElem = this.handles;
    int i = arrayOfPQnode[paramInt].handle;
    for (;;)
    {
      int j = paramInt << 1;
      if ((j < this.size) && (LEQ(this.leq, arrayOfPQhandleElem[arrayOfPQnode[(j + 1)].handle].key, arrayOfPQhandleElem[arrayOfPQnode[j].handle].key))) {
        j++;
      }
      assert (j <= this.max);
      int k = arrayOfPQnode[j].handle;
      if ((j > this.size) || (LEQ(this.leq, arrayOfPQhandleElem[i].key, arrayOfPQhandleElem[k].key)))
      {
        arrayOfPQnode[paramInt].handle = i;
        arrayOfPQhandleElem[i].node = paramInt;
        return;
      }
      arrayOfPQnode[paramInt].handle = k;
      arrayOfPQhandleElem[k].node = paramInt;
      paramInt = j;
    }
  }
  
  void FloatUp(int paramInt)
  {
    PriorityQ.PQnode[] arrayOfPQnode = this.nodes;
    PriorityQ.PQhandleElem[] arrayOfPQhandleElem = this.handles;
    int i = arrayOfPQnode[paramInt].handle;
    for (;;)
    {
      int j = paramInt >> 1;
      int k = arrayOfPQnode[j].handle;
      if ((j == 0) || (LEQ(this.leq, arrayOfPQhandleElem[k].key, arrayOfPQhandleElem[i].key)))
      {
        arrayOfPQnode[paramInt].handle = i;
        arrayOfPQhandleElem[i].node = paramInt;
        return;
      }
      arrayOfPQnode[paramInt].handle = k;
      arrayOfPQhandleElem[k].node = paramInt;
      paramInt = j;
    }
  }
  
  void pqDelete(int paramInt)
  {
    PriorityQ.PQnode[] arrayOfPQnode = this.nodes;
    PriorityQ.PQhandleElem[] arrayOfPQhandleElem = this.handles;
    assert ((paramInt >= 1) && (paramInt <= this.max) && (arrayOfPQhandleElem[paramInt].key != null));
    int i = arrayOfPQhandleElem[paramInt].node;
    arrayOfPQnode[i].handle = arrayOfPQnode[this.size].handle;
    arrayOfPQhandleElem[arrayOfPQnode[i].handle].node = i;
    int j = -1 + this.size;
    this.size = j;
    if (i <= j)
    {
      if ((i > 1) && (!LEQ(this.leq, arrayOfPQhandleElem[arrayOfPQnode[(i >> 1)].handle].key, arrayOfPQhandleElem[arrayOfPQnode[i].handle].key))) {
        break label176;
      }
      FloatDown(i);
    }
    for (;;)
    {
      arrayOfPQhandleElem[paramInt].key = null;
      arrayOfPQhandleElem[paramInt].node = this.freeList;
      this.freeList = paramInt;
      return;
      label176:
      FloatUp(i);
    }
  }
  
  void pqDeletePriorityQ()
  {
    this.handles = null;
    this.nodes = null;
  }
  
  Object pqExtractMin()
  {
    PriorityQ.PQnode[] arrayOfPQnode = this.nodes;
    PriorityQ.PQhandleElem[] arrayOfPQhandleElem = this.handles;
    int i = arrayOfPQnode[1].handle;
    Object localObject = arrayOfPQhandleElem[i].key;
    if (this.size > 0)
    {
      arrayOfPQnode[1].handle = arrayOfPQnode[this.size].handle;
      arrayOfPQhandleElem[arrayOfPQnode[1].handle].node = 1;
      arrayOfPQhandleElem[i].key = null;
      arrayOfPQhandleElem[i].node = this.freeList;
      this.freeList = i;
      int j = -1 + this.size;
      this.size = j;
      if (j > 0) {
        FloatDown(1);
      }
    }
    return localObject;
  }
  
  boolean pqInit()
  {
    for (int i = this.size;; i--)
    {
      if (i < 1)
      {
        this.initialized = true;
        return true;
      }
      FloatDown(i);
    }
  }
  
  int pqInsert(Object paramObject)
  {
    int i = 1 + this.size;
    this.size = i;
    int j;
    if (i * 2 > this.max)
    {
      PriorityQ.PQnode[] arrayOfPQnode1 = this.nodes;
      PriorityQ.PQhandleElem[] arrayOfPQhandleElem1 = this.handles;
      this.max <<= 1;
      PriorityQ.PQnode[] arrayOfPQnode2 = new PriorityQ.PQnode[1 + this.max];
      System.arraycopy(this.nodes, 0, arrayOfPQnode2, 0, this.nodes.length);
      for (int k = this.nodes.length;; k++)
      {
        if (k >= arrayOfPQnode2.length)
        {
          this.nodes = arrayOfPQnode2;
          if (this.nodes != null) {
            break;
          }
          this.nodes = arrayOfPQnode1;
          j = 2147483647;
          return j;
        }
        arrayOfPQnode2[k] = new PriorityQ.PQnode();
      }
      PriorityQ.PQhandleElem[] arrayOfPQhandleElem2 = new PriorityQ.PQhandleElem[1 + this.max];
      System.arraycopy(this.handles, 0, arrayOfPQhandleElem2, 0, this.handles.length);
      for (int m = this.handles.length;; m++)
      {
        if (m >= arrayOfPQhandleElem2.length)
        {
          this.handles = arrayOfPQhandleElem2;
          if (this.handles != null) {
            break;
          }
          this.handles = arrayOfPQhandleElem1;
          return 2147483647;
        }
        arrayOfPQhandleElem2[m] = new PriorityQ.PQhandleElem();
      }
    }
    if (this.freeList == 0) {
      j = i;
    }
    for (;;)
    {
      this.nodes[i].handle = j;
      this.handles[j].node = i;
      this.handles[j].key = paramObject;
      if (this.initialized) {
        FloatUp(i);
      }
      if (($assertionsDisabled) || (j != 2147483647)) {
        break;
      }
      throw new AssertionError();
      j = this.freeList;
      this.freeList = this.handles[j].node;
    }
  }
  
  boolean pqIsEmpty()
  {
    return this.size == 0;
  }
  
  Object pqMinimum()
  {
    return this.handles[this.nodes[1].handle].key;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.PriorityQHeap
 * JD-Core Version:    0.7.0.1
 */