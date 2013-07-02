package joptsimple;

import java.util.Collection;
import java.util.StringTokenizer;
import joptsimple.internal.Reflection;
import joptsimple.internal.ReflectionException;
import joptsimple.internal.Strings;
import joptsimple.internal.ValueConverter;

public abstract class ArgumentAcceptingOptionSpec<V> extends AbstractOptionSpec<V>
{
  private static final char NIL_VALUE_SEPARATOR = '\000';
  private final boolean argumentRequired;
  private ValueConverter<V> converter;
  private String argumentDescription = "";
  private String valueSeparator = String.valueOf('\000');

  ArgumentAcceptingOptionSpec(String paramString, boolean paramBoolean)
  {
    super(paramString);
    this.argumentRequired = paramBoolean;
  }

  ArgumentAcceptingOptionSpec(Collection<String> paramCollection, boolean paramBoolean, String paramString)
  {
    super(paramCollection, paramString);
    this.argumentRequired = paramBoolean;
  }

  public <T> ArgumentAcceptingOptionSpec<T> ofType(Class<T> paramClass)
  {
    this.converter = Reflection.findConverter(paramClass);
    return this;
  }

  public final ArgumentAcceptingOptionSpec<V> describedAs(String paramString)
  {
    this.argumentDescription = paramString;
    return this;
  }

  public final ArgumentAcceptingOptionSpec<V> withValuesSeparatedBy(char paramChar)
  {
    if (paramChar == 0)
      throw new IllegalArgumentException("cannot use U+0000 as separator");
    this.valueSeparator = String.valueOf(paramChar);
    return this;
  }

  final void handleOption(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet, String paramString)
  {
    if (Strings.isNullOrEmpty(paramString))
      detectOptionArgument(paramOptionParser, paramArgumentList, paramOptionSet);
    else
      addArguments(paramOptionSet, paramString);
  }

  protected void addArguments(OptionSet paramOptionSet, String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, this.valueSeparator);
    while (localStringTokenizer.hasMoreTokens())
      paramOptionSet.addWithArgument(this, localStringTokenizer.nextToken());
  }

  protected abstract void detectOptionArgument(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet);

  protected final V convert(String paramString)
  {
    if (this.converter == null)
      return paramString;
    try
    {
      return this.converter.convert(paramString);
    }
    catch (ReflectionException localReflectionException)
    {
      throw new OptionArgumentConversionException(options(), paramString, this.converter.valueType(), localReflectionException);
    }
  }

  protected boolean canConvertArgument(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, this.valueSeparator);
    try
    {
      while (localStringTokenizer.hasMoreTokens())
        convert(localStringTokenizer.nextToken());
      return true;
    }
    catch (OptionException localOptionException)
    {
    }
    return false;
  }

  protected boolean isArgumentOfNumberType()
  {
    return (this.converter != null) && (Number.class.isAssignableFrom(this.converter.valueType()));
  }

  boolean acceptsArguments()
  {
    return true;
  }

  boolean requiresArgument()
  {
    return this.argumentRequired;
  }

  String argumentDescription()
  {
    return this.argumentDescription;
  }

  Class<?> argumentType()
  {
    return this.converter == null ? String.class : this.converter.valueType();
  }

  public boolean equals(Object paramObject)
  {
    if (!super.equals(paramObject))
      return false;
    ArgumentAcceptingOptionSpec localArgumentAcceptingOptionSpec = (ArgumentAcceptingOptionSpec)paramObject;
    return requiresArgument() == localArgumentAcceptingOptionSpec.requiresArgument();
  }

  public int hashCode()
  {
    return super.hashCode() ^ (this.argumentRequired ? 0 : 1);
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.ArgumentAcceptingOptionSpec
 * JD-Core Version:    0.6.2
 */