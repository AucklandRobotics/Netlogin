package joptsimple;

import java.util.Collection;
import java.util.Collections;

class NoArgumentOptionSpec extends AbstractOptionSpec<Void>
{
  NoArgumentOptionSpec(String paramString)
  {
    this(Collections.singletonList(paramString), "");
  }

  NoArgumentOptionSpec(Collection<String> paramCollection, String paramString)
  {
    super(paramCollection, paramString);
  }

  void handleOption(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet, String paramString)
  {
    paramOptionSet.add(this);
  }

  boolean acceptsArguments()
  {
    return false;
  }

  boolean requiresArgument()
  {
    return false;
  }

  void accept(OptionSpecVisitor paramOptionSpecVisitor)
  {
    paramOptionSpecVisitor.visit(this);
  }

  protected Void convert(String paramString)
  {
    return null;
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.NoArgumentOptionSpec
 * JD-Core Version:    0.6.2
 */