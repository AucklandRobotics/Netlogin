package joptsimple;

import java.util.Collections;

class IllegalOptionClusterException extends OptionException
{
  private static final long serialVersionUID = -1L;

  IllegalOptionClusterException(String paramString)
  {
    super(Collections.singletonList(paramString));
  }

  public String getMessage()
  {
    return "Option cluster containing " + singleOptionMessage() + " is illegal";
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.IllegalOptionClusterException
 * JD-Core Version:    0.6.2
 */