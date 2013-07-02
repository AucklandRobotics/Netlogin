package joptsimple;

import java.util.Collections;

class UnrecognizedOptionException extends OptionException
{
  private static final long serialVersionUID = -1L;

  UnrecognizedOptionException(String paramString)
  {
    super(Collections.singletonList(paramString));
  }

  public String getMessage()
  {
    return singleOptionMessage() + " is not a recognized option";
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.UnrecognizedOptionException
 * JD-Core Version:    0.6.2
 */