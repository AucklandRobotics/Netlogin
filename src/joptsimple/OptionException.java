package joptsimple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class OptionException extends RuntimeException
{
  private static final long serialVersionUID = -1L;
  private final List<String> options = new ArrayList();

  protected OptionException(Collection<String> paramCollection)
  {
    this.options.addAll(paramCollection);
  }

  protected OptionException(Collection<String> paramCollection, Throwable paramThrowable)
  {
    super(paramThrowable);
    this.options.addAll(paramCollection);
  }

  public Collection<String> options()
  {
    return Collections.unmodifiableCollection(this.options);
  }

  protected final String singleOptionMessage()
  {
    return singleOptionMessage((String)this.options.get(0));
  }

  protected final String singleOptionMessage(String paramString)
  {
    return new StringBuilder().append("'").append(paramString).append("'").toString();
  }

  protected final String multipleOptionMessage()
  {
    StringBuilder localStringBuilder = new StringBuilder("[");
    Iterator localIterator = this.options.iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append(singleOptionMessage((String)localIterator.next()));
      if (localIterator.hasNext())
        localStringBuilder.append(", ");
    }
    localStringBuilder.append(']');
    return localStringBuilder.toString();
  }

  static OptionException illegalOptionCluster(String paramString)
  {
    return new IllegalOptionClusterException(paramString);
  }

  static OptionException unrecognizedOption(String paramString)
  {
    return new UnrecognizedOptionException(paramString);
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.OptionException
 * JD-Core Version:    0.6.2
 */