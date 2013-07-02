package joptsimple;

import java.util.Collection;

public class OptionSpecBuilder extends NoArgumentOptionSpec
{
  private final OptionParser parser;

  OptionSpecBuilder(OptionParser paramOptionParser, Collection<String> paramCollection, String paramString)
  {
    super(paramCollection, paramString);
    this.parser = paramOptionParser;
    paramOptionParser.recognize(this);
  }

  public ArgumentAcceptingOptionSpec<String> withRequiredArg()
  {
    RequiredArgumentOptionSpec localRequiredArgumentOptionSpec = new RequiredArgumentOptionSpec(options(), description());
    this.parser.recognize(localRequiredArgumentOptionSpec);
    return localRequiredArgumentOptionSpec;
  }

  public ArgumentAcceptingOptionSpec<String> withOptionalArg()
  {
    OptionalArgumentOptionSpec localOptionalArgumentOptionSpec = new OptionalArgumentOptionSpec(options(), description());
    this.parser.recognize(localOptionalArgumentOptionSpec);
    return localOptionalArgumentOptionSpec;
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.OptionSpecBuilder
 * JD-Core Version:    0.6.2
 */