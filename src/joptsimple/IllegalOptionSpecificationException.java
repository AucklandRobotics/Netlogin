package joptsimple;

import java.util.Collections;

class IllegalOptionSpecificationException extends OptionException
{
  private static final long serialVersionUID = -1L;

  IllegalOptionSpecificationException(String paramString)
  {
    super(Collections.singletonList(paramString));
  }

  public String getMessage()
  {
    return singleOptionMessage() + " is not a legal option character";
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.IllegalOptionSpecificationException
 * JD-Core Version:    0.6.2
 */