package joptsimple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

abstract class AbstractOptionSpec<V>
  implements OptionSpec<V>
{
  private final List<String> options = new ArrayList();
  private final String description;

  protected AbstractOptionSpec(String paramString)
  {
    this(Collections.singletonList(paramString), "");
  }

  protected AbstractOptionSpec(Collection<String> paramCollection, String paramString)
  {
    arrangeOptions(paramCollection);
    this.description = paramString;
  }

  public final Collection<String> options()
  {
    return Collections.unmodifiableCollection(this.options);
  }

  public final List<V> values(OptionSet paramOptionSet)
  {
    return paramOptionSet.valuesOf(this);
  }

  public final V value(OptionSet paramOptionSet)
  {
    return paramOptionSet.valueOf(this);
  }

  String description()
  {
    return this.description;
  }

  protected abstract V convert(String paramString);

  abstract void handleOption(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet, String paramString);

  abstract boolean acceptsArguments();

  abstract boolean requiresArgument();

  abstract void accept(OptionSpecVisitor paramOptionSpecVisitor);

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AbstractOptionSpec))
      return false;
    AbstractOptionSpec localAbstractOptionSpec = (AbstractOptionSpec)paramObject;
    return this.options.equals(localAbstractOptionSpec.options);
  }

  public int hashCode()
  {
    return this.options.hashCode();
  }

  private void arrangeOptions(Collection<String> paramCollection)
  {
    if (paramCollection.size() == 1)
    {
      this.options.addAll(paramCollection);
      return;
    }
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (str.length() == 1)
        localArrayList1.add(str);
      else
        localArrayList2.add(str);
    }
    Collections.sort(localArrayList1);
    Collections.sort(localArrayList2);
    this.options.addAll(localArrayList1);
    this.options.addAll(localArrayList2);
  }

  public String toString()
  {
    return this.options.toString();
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.AbstractOptionSpec
 * JD-Core Version:    0.6.2
 */