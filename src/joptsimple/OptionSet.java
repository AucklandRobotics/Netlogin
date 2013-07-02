package joptsimple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OptionSet
{
  private final Map<String, AbstractOptionSpec<?>> detectedOptions = new HashMap();
  private final Map<AbstractOptionSpec<?>, List<String>> optionsToArguments = new IdentityHashMap();
  private final List<String> nonOptionArguments = new ArrayList();

  public boolean has(String paramString)
  {
    return this.detectedOptions.containsKey(paramString);
  }

  public boolean has(OptionSpec<?> paramOptionSpec)
  {
    return this.optionsToArguments.containsKey(paramOptionSpec);
  }

  public boolean hasArgument(String paramString)
  {
    return !valuesOf(paramString).isEmpty();
  }

  public boolean hasArgument(OptionSpec<?> paramOptionSpec)
  {
    return !valuesOf(paramOptionSpec).isEmpty();
  }

  public Object valueOf(String paramString)
  {
    return valueOf((OptionSpec)this.detectedOptions.get(paramString));
  }

  public <V> V valueOf(OptionSpec<V> paramOptionSpec)
  {
    if (paramOptionSpec == null)
      return null;
    List localList = valuesOf(paramOptionSpec);
    switch (localList.size())
    {
    case 0:
      return null;
    case 1:
      return (V) localList.get(0);
    }
    throw new MultipleArgumentsForOptionException(paramOptionSpec.options());
  }

  public List<?> valuesOf(String paramString)
  {
    return valuesOf((OptionSpec)this.detectedOptions.get(paramString));
  }

  public <V> List<V> valuesOf(OptionSpec<V> paramOptionSpec)
  {
    List localList = (List)this.optionsToArguments.get(paramOptionSpec);
    if (localList == null)
      return Collections.emptyList();
    AbstractOptionSpec localAbstractOptionSpec = (AbstractOptionSpec)paramOptionSpec;
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localArrayList.add(localAbstractOptionSpec.convert(str));
    }
    return Collections.unmodifiableList(localArrayList);
  }

  public List<String> nonOptionArguments()
  {
    return Collections.unmodifiableList(this.nonOptionArguments);
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if ((paramObject == null) || (!getClass().equals(paramObject.getClass())))
      return false;
    OptionSet localOptionSet = (OptionSet)paramObject;
    HashMap localHashMap1 = new HashMap(this.optionsToArguments);
    HashMap localHashMap2 = new HashMap(localOptionSet.optionsToArguments);
    return (this.detectedOptions.equals(localOptionSet.detectedOptions)) && (localHashMap1.equals(localHashMap2)) && (this.nonOptionArguments.equals(localOptionSet.nonOptionArguments()));
  }

  public int hashCode()
  {
    HashMap localHashMap = new HashMap(this.optionsToArguments);
    return this.detectedOptions.hashCode() ^ localHashMap.hashCode() ^ this.nonOptionArguments.hashCode();
  }

  void add(AbstractOptionSpec<?> paramAbstractOptionSpec)
  {
    addWithArgument(paramAbstractOptionSpec, null);
  }

  void addWithArgument(AbstractOptionSpec<?> paramAbstractOptionSpec, String paramString)
  {
    Object localObject = paramAbstractOptionSpec.options().iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str = (String)((Iterator)localObject).next();
      this.detectedOptions.put(str, paramAbstractOptionSpec);
    }
    localObject = (List)this.optionsToArguments.get(paramAbstractOptionSpec);
    if (localObject == null)
    {
      localObject = new ArrayList();
      this.optionsToArguments.put(paramAbstractOptionSpec, (List<String>) localObject);
    }
    if (paramString != null)
      ((List)localObject).add(paramString);
  }

  void addNonOptionArgument(String paramString)
  {
    this.nonOptionArguments.add(paramString);
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.OptionSet
 * JD-Core Version:    0.6.2
 */