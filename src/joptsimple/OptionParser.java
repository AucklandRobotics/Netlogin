package joptsimple;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import joptsimple.internal.AbbreviationMap;
import joptsimple.util.KeyValuePair;

public class OptionParser
{
  private final AbbreviationMap<AbstractOptionSpec<?>> recognizedOptions = new AbbreviationMap();
  private OptionParserState state = OptionParserState.moreOptions(false);
  private boolean posixlyCorrect;

  public OptionParser()
  {
  }

  public OptionParser(String paramString)
  {
    this();
    new OptionSpecTokenizer(paramString).configure(this);
  }

  public OptionSpecBuilder accepts(String paramString)
  {
    return acceptsAll(Collections.singletonList(paramString));
  }

  public OptionSpecBuilder accepts(String paramString1, String paramString2)
  {
    return acceptsAll(Collections.singletonList(paramString1), paramString2);
  }

  public OptionSpecBuilder acceptsAll(Collection<String> paramCollection)
  {
    return acceptsAll(paramCollection, "");
  }

  public OptionSpecBuilder acceptsAll(Collection<String> paramCollection, String paramString)
  {
    if (paramCollection.isEmpty())
      throw new IllegalArgumentException("need at least one option");
    ParserRules.ensureLegalOptions(paramCollection);
    return new OptionSpecBuilder(this, paramCollection, paramString);
  }

  public void posixlyCorrect(boolean paramBoolean)
  {
    this.posixlyCorrect = paramBoolean;
    this.state = OptionParserState.moreOptions(paramBoolean);
  }

  boolean posixlyCorrect()
  {
    return this.posixlyCorrect;
  }

  public void recognizeAlternativeLongOptions(boolean paramBoolean)
  {
    if (paramBoolean)
      recognize(new AlternativeLongOptionSpec());
    else
      this.recognizedOptions.remove(String.valueOf("W"));
  }

  void recognize(AbstractOptionSpec<?> paramAbstractOptionSpec)
  {
    this.recognizedOptions.putAll(paramAbstractOptionSpec.options(), paramAbstractOptionSpec);
  }

  public void printHelpOn(OutputStream paramOutputStream)
    throws IOException
  {
    printHelpOn(new OutputStreamWriter(paramOutputStream));
  }

  public void printHelpOn(Writer paramWriter)
    throws IOException
  {
    paramWriter.write(new HelpFormatter().format(this.recognizedOptions.toJavaUtilMap()));
    paramWriter.flush();
  }

  public OptionSet parse(String[] paramArrayOfString)
  {
    ArgumentList localArgumentList = new ArgumentList(paramArrayOfString);
    OptionSet localOptionSet = new OptionSet();
    while (localArgumentList.hasMore())
      this.state.handleArgument(this, localArgumentList, localOptionSet);
    reset();
    return localOptionSet;
  }

  void handleLongOptionToken(String paramString, ArgumentList paramArgumentList, OptionSet paramOptionSet)
  {
    KeyValuePair localKeyValuePair = parseLongOptionWithArgument(paramString);
    if (!isRecognized(localKeyValuePair.key))
      throw OptionException.unrecognizedOption(localKeyValuePair.key);
    AbstractOptionSpec localAbstractOptionSpec = specFor(localKeyValuePair.key);
    localAbstractOptionSpec.handleOption(this, paramArgumentList, paramOptionSet, localKeyValuePair.value);
  }

  void handleShortOptionToken(String paramString, ArgumentList paramArgumentList, OptionSet paramOptionSet)
  {
    KeyValuePair localKeyValuePair = parseShortOptionWithArgument(paramString);
    if (isRecognized(localKeyValuePair.key))
      specFor(localKeyValuePair.key).handleOption(this, paramArgumentList, paramOptionSet, localKeyValuePair.value);
    else
      handleShortOptionCluster(paramString, paramArgumentList, paramOptionSet);
  }

  private void handleShortOptionCluster(String paramString, ArgumentList paramArgumentList, OptionSet paramOptionSet)
  {
    char[] arrayOfChar = extractShortOptionsFrom(paramString);
    validateOptionCharacters(arrayOfChar);
    AbstractOptionSpec localAbstractOptionSpec = specFor(arrayOfChar[0]);
    Object localObject;
    if ((localAbstractOptionSpec.acceptsArguments()) && (arrayOfChar.length > 1))
    {
      localObject = String.valueOf(arrayOfChar, 1, arrayOfChar.length - 1);
      localAbstractOptionSpec.handleOption(this, paramArgumentList, paramOptionSet, (String)localObject);
    }
    else
    {
      for (char c : arrayOfChar)
        specFor(c).handleOption(this, paramArgumentList, paramOptionSet, null);
    }
  }

  void noMoreOptions()
  {
    this.state = OptionParserState.noMoreOptions();
  }

  boolean looksLikeAnOption(String paramString)
  {
    return (ParserRules.isShortOptionToken(paramString)) || (ParserRules.isLongOptionToken(paramString));
  }

  private boolean isRecognized(String paramString)
  {
    return this.recognizedOptions.contains(paramString);
  }

  private AbstractOptionSpec<?> specFor(char paramChar)
  {
    return specFor(String.valueOf(paramChar));
  }

  private AbstractOptionSpec<?> specFor(String paramString)
  {
    return (AbstractOptionSpec)this.recognizedOptions.get(paramString);
  }

  private void reset()
  {
    this.state = OptionParserState.moreOptions(this.posixlyCorrect);
  }

  private static char[] extractShortOptionsFrom(String paramString)
  {
    char[] arrayOfChar = new char[paramString.length() - 1];
    paramString.getChars(1, paramString.length(), arrayOfChar, 0);
    return arrayOfChar;
  }

  private void validateOptionCharacters(char[] paramArrayOfChar)
  {
    for (int i = 0; i < paramArrayOfChar.length; i++)
    {
      String str = String.valueOf(paramArrayOfChar[i]);
      if (!isRecognized(str))
        throw OptionException.unrecognizedOption(str);
      if (specFor(str).acceptsArguments())
      {
        if (i > 0)
          throw OptionException.illegalOptionCluster(str);
        return;
      }
    }
  }

  private static KeyValuePair parseLongOptionWithArgument(String paramString)
  {
    return KeyValuePair.valueOf(paramString.substring(2));
  }

  private static KeyValuePair parseShortOptionWithArgument(String paramString)
  {
    return KeyValuePair.valueOf(paramString.substring(1));
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.OptionParser
 * JD-Core Version:    0.6.2
 */