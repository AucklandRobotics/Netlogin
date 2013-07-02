package joptsimple;

import java.util.Collections;

class AlternativeLongOptionSpec extends ArgumentAcceptingOptionSpec<String>
{
  AlternativeLongOptionSpec()
  {
    super(Collections.singletonList("W"), true, "Alternative form of long options");
    describedAs("opt=value");
  }

  protected void detectOptionArgument(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet)
  {
    if (!paramArgumentList.hasMore())
      throw new OptionMissingRequiredArgumentException(options());
    paramArgumentList.treatNextAsLongOption();
  }

  void accept(OptionSpecVisitor paramOptionSpecVisitor)
  {
    paramOptionSpecVisitor.visit(this);
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.AlternativeLongOptionSpec
 * JD-Core Version:    0.6.2
 */