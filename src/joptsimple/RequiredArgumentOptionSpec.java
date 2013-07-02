package joptsimple;

import java.util.Collection;

class RequiredArgumentOptionSpec<V> extends ArgumentAcceptingOptionSpec<V>
{
  RequiredArgumentOptionSpec(String paramString)
  {
    super(paramString, true);
  }

  RequiredArgumentOptionSpec(Collection<String> paramCollection, String paramString)
  {
    super(paramCollection, true, paramString);
  }

  protected void detectOptionArgument(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet)
  {
    if (!paramArgumentList.hasMore())
      throw new OptionMissingRequiredArgumentException(options());
    addArguments(paramOptionSet, paramArgumentList.next());
  }

  void accept(OptionSpecVisitor paramOptionSpecVisitor)
  {
    paramOptionSpecVisitor.visit(this);
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.RequiredArgumentOptionSpec
 * JD-Core Version:    0.6.2
 */