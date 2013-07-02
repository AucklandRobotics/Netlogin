package joptsimple;

import java.util.Collection;
import java.util.Iterator;

class ParserRules
{
  static final char HYPHEN_CHAR = '-';
  static final String HYPHEN = String.valueOf('-');
  static final String DOUBLE_HYPHEN = "--";
  static final String OPTION_TERMINATOR = "--";
  static final String RESERVED_FOR_EXTENSIONS = "W";

  protected ParserRules()
  {
    throw new UnsupportedOperationException();
  }

  static boolean isShortOptionToken(String paramString)
  {
    return (paramString.startsWith(HYPHEN)) && (!HYPHEN.equals(paramString)) && (!isLongOptionToken(paramString));
  }

  static boolean isLongOptionToken(String paramString)
  {
    return (paramString.startsWith("--")) && (!isOptionTerminator(paramString));
  }

  static boolean isOptionTerminator(String paramString)
  {
    return "--".equals(paramString);
  }

  static void ensureLegalOption(String paramString)
  {
    if (paramString.startsWith(HYPHEN))
      throw new IllegalOptionSpecificationException(String.valueOf(paramString));
    for (int i = 0; i < paramString.length(); i++)
      ensureLegalOptionCharacter(paramString.charAt(i));
  }

  static void ensureLegalOptions(Collection<String> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      ensureLegalOption(str);
    }
  }

  private static void ensureLegalOptionCharacter(char paramChar)
  {
    if ((!Character.isLetterOrDigit(paramChar)) && (!isAllowedPunctuation(paramChar)))
      throw new IllegalOptionSpecificationException(String.valueOf(paramChar));
  }

  private static boolean isAllowedPunctuation(char paramChar)
  {
    String str = "?.-";
    return str.indexOf(paramChar) != -1;
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.ParserRules
 * JD-Core Version:    0.6.2
 */