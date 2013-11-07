package processing.opengl.tess;

class PriorityQSort
  extends PriorityQ
{
  PriorityQHeap heap;
  boolean initialized;
  Object[] keys;
  PriorityQ.Leq leq;
  int max;
  int[] order;
  int size;
  
  static
  {
    if (!PriorityQSort.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public PriorityQSort(PriorityQ.Leq paramLeq)
  {
    this.heap = new PriorityQHeap(paramLeq);
    this.keys = new Object[32];
    this.size = 0;
    this.max = 32;
    this.initialized = false;
    this.leq = paramLeq;
  }
  
  private static boolean GT(PriorityQ.Leq paramLeq, Object paramObject1, Object paramObject2)
  {
    return !PriorityQ.LEQ(paramLeq, paramObject1, paramObject2);
  }
  
  private static boolean LT(PriorityQ.Leq paramLeq, Object paramObject1, Object paramObject2)
  {
    return !PriorityQ.LEQ(paramLeq, paramObject2, paramObject1);
  }
  
  private static void Swap(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfInt[paramInt1];
    paramArrayOfInt[paramInt1] = paramArrayOfInt[paramInt2];
    paramArrayOfInt[paramInt2] = i;
  }
  
  void pqDelete(int paramInt)
  {
    if (paramInt >= 0) {
      this.heap.pqDelete(paramInt);
    }
    for (;;)
    {
      return;
      int i = -(paramInt + 1);
      assert ((i < this.max) && (this.keys[i] != null));
      this.keys[i] = null;
      while ((this.size > 0) && (this.keys[this.order[(-1 + this.size)]] == null)) {
        this.size = (-1 + this.size);
      }
    }
  }
  
  void pqDeletePriorityQ()
  {
    if (this.heap != null) {
      this.heap.pqDeletePriorityQ();
    }
    this.order = null;
    this.keys = null;
  }
  
  Object pqExtractMin()
  {
    if (this.size == 0)
    {
      localObject1 = this.heap.pqExtractMin();
      return localObject1;
    }
    Object localObject1 = this.keys[this.order[(-1 + this.size)]];
    if (!this.heap.pqIsEmpty())
    {
      Object localObject2 = this.heap.pqMinimum();
      if (LEQ(this.leq, localObject2, localObject1)) {
        return this.heap.pqExtractMin();
      }
    }
    do
    {
      this.size = (-1 + this.size);
      if (this.size <= 0) {
        break;
      }
    } while (this.keys[this.order[(-1 + this.size)]] == null);
    return localObject1;
  }
  
  boolean pqInit()
  {
    Stack[] arrayOfStack = new Stack[50];
    int i = 0;
    int j;
    int k;
    int m;
    if (i >= arrayOfStack.length)
    {
      j = 2016473283;
      this.order = new int[1 + this.size];
      k = -1 + this.size;
      m = 0;
    }
    int i1;
    for (int n = 0;; n++)
    {
      if (n > k)
      {
        arrayOfStack[0].p = 0;
        arrayOfStack[0].r = k;
        i1 = 0 + 1;
        i1--;
        if (i1 >= 0) {
          break label136;
        }
        this.max = this.size;
        this.initialized = true;
        this.heap.pqInit();
        return true;
        arrayOfStack[i] = new Stack(null);
        i++;
        break;
      }
      this.order[n] = m;
      m++;
    }
    label136:
    int i2 = arrayOfStack[i1].p;
    int i3 = arrayOfStack[i1].r;
    label154:
    int i8;
    label170:
    int i9;
    if (i3 <= i2 + 10)
    {
      i8 = i2 + 1;
      if (i8 <= i3) {
        i9 = this.order[i8];
      }
    }
    for (int i10 = i8;; i10--)
    {
      if ((i10 <= i2) || (!LT(this.leq, this.keys[this.order[(i10 - 1)]], this.keys[i9])))
      {
        this.order[i10] = i9;
        i8++;
        break label170;
        break;
        j = Math.abs(1 + 1539415821 * j);
        int i4 = i2 + j % (1 + (i3 - i2));
        int i5 = this.order[i4];
        this.order[i4] = this.order[i2];
        this.order[i2] = i5;
        int i6 = i2 - 1;
        int i7 = i3 + 1;
        do
        {
          do
          {
            i6++;
          } while (GT(this.leq, this.keys[this.order[i6]], this.keys[i5]));
          do
          {
            i7--;
          } while (LT(this.leq, this.keys[this.order[i7]], this.keys[i5]));
          Swap(this.order, i6, i7);
        } while (i6 < i7);
        Swap(this.order, i6, i7);
        if (i6 - i2 < i3 - i7)
        {
          arrayOfStack[i1].p = (i7 + 1);
          arrayOfStack[i1].r = i3;
          i1++;
          i3 = i6 - 1;
          break label154;
        }
        arrayOfStack[i1].p = i2;
        arrayOfStack[i1].r = (i6 - 1);
        i1++;
        i2 = i7 + 1;
        break label154;
      }
      this.order[i10] = this.order[(i10 - 1)];
    }
  }
  
  int pqInsert(Object paramObject)
  {
    if (this.initialized) {
      return this.heap.pqInsert(paramObject);
    }
    int i = this.size;
    int j = 1 + this.size;
    this.size = j;
    if (j >= this.max)
    {
      Object[] arrayOfObject1 = this.keys;
      this.max <<= 1;
      Object[] arrayOfObject2 = new Object[this.max];
      System.arraycopy(this.keys, 0, arrayOfObject2, 0, this.keys.length);
      this.keys = arrayOfObject2;
      if (this.keys == null)
      {
        this.keys = arrayOfObject1;
        return 2147483647;
      }
    }
    assert (i != 2147483647);
    this.keys[i] = paramObject;
    return -(i + 1);
  }
  
  boolean pqIsEmpty()
  {
    return (this.size == 0) && (this.heap.pqIsEmpty());
  }
  
  Object pqMinimum()
  {
    Object localObject2;
    if (this.size == 0) {
      localObject2 = this.heap.pqMinimum();
    }
    Object localObject1;
    do
    {
      return localObject2;
      localObject1 = this.keys[this.order[(-1 + this.size)]];
      if (this.heap.pqIsEmpty()) {
        break;
      }
      localObject2 = this.heap.pqMinimum();
    } while (PriorityQ.LEQ(this.leq, localObject2, localObject1));
    return localObject1;
  }
  
  private static class Stack
  {
    int p;
    int r;
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.PriorityQSort
 * JD-Core Version:    0.7.0.1
 */