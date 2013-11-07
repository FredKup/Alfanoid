package processing.opengl.tess;

class Dict
{
  Object frame;
  DictNode head;
  DictLeq leq;
  
  static void dictDelete(Dict paramDict, DictNode paramDictNode)
  {
    paramDictNode.next.prev = paramDictNode.prev;
    paramDictNode.prev.next = paramDictNode.next;
  }
  
  static void dictDeleteDict(Dict paramDict)
  {
    paramDict.head = null;
    paramDict.frame = null;
    paramDict.leq = null;
  }
  
  static DictNode dictInsert(Dict paramDict, Object paramObject)
  {
    return dictInsertBefore(paramDict, paramDict.head, paramObject);
  }
  
  static DictNode dictInsertBefore(Dict paramDict, DictNode paramDictNode, Object paramObject)
  {
    do
    {
      paramDictNode = paramDictNode.prev;
    } while ((paramDictNode.key != null) && (!paramDict.leq.leq(paramDict.frame, paramDictNode.key, paramObject)));
    DictNode localDictNode = new DictNode();
    localDictNode.key = paramObject;
    localDictNode.next = paramDictNode.next;
    paramDictNode.next.prev = localDictNode;
    localDictNode.prev = paramDictNode;
    paramDictNode.next = localDictNode;
    return localDictNode;
  }
  
  static Object dictKey(DictNode paramDictNode)
  {
    return paramDictNode.key;
  }
  
  static DictNode dictMax(Dict paramDict)
  {
    return paramDict.head.prev;
  }
  
  static DictNode dictMin(Dict paramDict)
  {
    return paramDict.head.next;
  }
  
  static Dict dictNewDict(Object paramObject, DictLeq paramDictLeq)
  {
    Dict localDict = new Dict();
    localDict.head = new DictNode();
    localDict.head.key = null;
    localDict.head.next = localDict.head;
    localDict.head.prev = localDict.head;
    localDict.frame = paramObject;
    localDict.leq = paramDictLeq;
    return localDict;
  }
  
  static DictNode dictPred(DictNode paramDictNode)
  {
    return paramDictNode.prev;
  }
  
  static DictNode dictSearch(Dict paramDict, Object paramObject)
  {
    DictNode localDictNode = paramDict.head;
    do
    {
      localDictNode = localDictNode.next;
    } while ((localDictNode.key != null) && (!paramDict.leq.leq(paramDict.frame, paramObject, localDictNode.key)));
    return localDictNode;
  }
  
  static DictNode dictSucc(DictNode paramDictNode)
  {
    return paramDictNode.next;
  }
  
  public static abstract interface DictLeq
  {
    public abstract boolean leq(Object paramObject1, Object paramObject2, Object paramObject3);
  }
}


/* Location:           C:\Downloads\jd-gui-0.3.6.windows\classes_dex2jar.jar
 * Qualified Name:     processing.opengl.tess.Dict
 * JD-Core Version:    0.7.0.1
 */