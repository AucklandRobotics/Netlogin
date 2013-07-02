package joptsimple;

import java.util.Collection;

class OptionMissingRequiredArgumentException extends OptionException
{
  private static final long serialVersionUID = -1L;

  OptionMissingRequiredArgumentException(Collection<String> paramCollection)
  {
    super(paramCollection);
  }

  public String getMessage()
  {
    return "Option " + multipleOptionMessage() + " requires an argument";
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.OptionMissingRequiredArgumentException
 * JD-Core Version:    0.6.2
 */