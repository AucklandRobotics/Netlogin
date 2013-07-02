package joptsimple;

import java.util.Collection;

class OptionalArgumentOptionSpec<V> extends ArgumentAcceptingOptionSpec<V>
{
  OptionalArgumentOptionSpec(String paramString)
  {
    super(paramString, false);
  }

  OptionalArgumentOptionSpec(Collection<String> paramCollection, String paramString)
  {
    super(paramCollection, false, paramString);
  }

  protected void detectOptionArgument(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet)
  {
    if (paramArgumentList.hasMore())
    {
      String str = paramArgumentList.peek();
      if (!paramOptionParser.looksLikeAnOption(str))
        handleOptionArgument(paramOptionParser, paramOptionSet, paramArgumentList);
      else if ((isArgumentOfNumberType()) && (canConvertArgument(str)))
        addArguments(paramOptionSet, paramArgumentList.next());
      else
        paramOptionSet.add(this);
    }
    else
    {
      paramOptionSet.add(this);
    }
  }

  private void handleOptionArgument(OptionParser paramOptionParser, OptionSet paramOptionSet, ArgumentList paramArgumentList)
  {
    if (paramOptionParser.posixlyCorrect())
    {
      paramOptionSet.add(this);
      paramOptionParser.noMoreOptions();
    }
    else
    {
      addArguments(paramOptionSet, paramArgumentList.next());
    }
  }

  void accept(OptionSpecVisitor paramOptionSpecVisitor)
  {
    paramOptionSpecVisitor.visit(this);
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.OptionalArgumentOptionSpec
 * JD-Core Version:    0.6.2
 */