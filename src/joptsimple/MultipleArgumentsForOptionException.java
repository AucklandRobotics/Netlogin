package joptsimple;

import java.util.Collection;

class MultipleArgumentsForOptionException extends OptionException
{
  private static final long serialVersionUID = -1L;

  MultipleArgumentsForOptionException(Collection<String> paramCollection)
  {
    super(paramCollection);
  }

  public String getMessage()
  {
    return "Found multiple arguments for option " + multipleOptionMessage() + ", but you asked for only one";
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.MultipleArgumentsForOptionException
 * JD-Core Version:    0.6.2
 */