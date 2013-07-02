package joptsimple;

import java.util.Collection;

class OptionArgumentConversionException extends OptionException
{
  private static final long serialVersionUID = -1L;
  private final String argument;
  private final Class<?> valueType;

  OptionArgumentConversionException(Collection<String> paramCollection, String paramString, Class<?> paramClass, Throwable paramThrowable)
  {
    super(paramCollection, paramThrowable);
    this.argument = paramString;
    this.valueType = paramClass;
  }

  public String getMessage()
  {
    return "Cannot convert argument '" + this.argument + "' of option " + multipleOptionMessage() + " to " + this.valueType;
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.OptionArgumentConversionException
 * JD-Core Version:    0.6.2
 */