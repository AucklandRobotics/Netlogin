package joptsimple;

import java.util.NoSuchElementException;

class OptionSpecTokenizer
{
  private static final char POSIXLY_CORRECT_MARKER = '+';
  private String specification;
  private int index;

  OptionSpecTokenizer(String paramString)
  {
    if (paramString == null)
      throw new NullPointerException("null option specification");
    this.specification = paramString;
  }

  boolean hasMore()
  {
    return this.index < this.specification.length();
  }

  AbstractOptionSpec<?> next()
  {
    if (!hasMore())
      throw new NoSuchElementException();
    String str = String.valueOf(this.specification.charAt(this.index++));
    Object localObject;
    if ("W".equals(str))
    {
      localObject = handleReservedForExtensionsToken();
      if (localObject != null)
        return (AbstractOptionSpec<?>) localObject;
    }
    ParserRules.ensureLegalOption(str);
    if (!hasMore())
      localObject = new NoArgumentOptionSpec(str);
    else if (this.specification.charAt(this.index) == ':')
      localObject = handleArgumentAcceptingOption(str);
    else
      localObject = new NoArgumentOptionSpec(str);
    return (AbstractOptionSpec<?>) localObject;
  }

  void configure(OptionParser paramOptionParser)
  {
    adjustForPosixlyCorrect(paramOptionParser);
    while (hasMore())
      paramOptionParser.recognize(next());
  }

  private void adjustForPosixlyCorrect(OptionParser paramOptionParser)
  {
    if ('+' == this.specification.charAt(0))
    {
      paramOptionParser.posixlyCorrect(true);
      this.specification = this.specification.substring(1);
    }
  }

  private AbstractOptionSpec<?> handleReservedForExtensionsToken()
  {
    if (!hasMore())
      return new NoArgumentOptionSpec("W");
    if (this.specification.charAt(this.index) == ';')
    {
      this.index += 1;
      return new AlternativeLongOptionSpec();
    }
    return null;
  }

  private AbstractOptionSpec<?> handleArgumentAcceptingOption(String paramString)
  {
    this.index += 1;
    if ((hasMore()) && (this.specification.charAt(this.index) == ':'))
    {
      this.index += 1;
      return new OptionalArgumentOptionSpec(paramString);
    }
    return new RequiredArgumentOptionSpec(paramString);
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.OptionSpecTokenizer
 * JD-Core Version:    0.6.2
 */