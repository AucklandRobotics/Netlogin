package joptsimple;

abstract class OptionParserState
{
	static OptionParserState noMoreOptions()
	{
		return new OptionParserState()
		{
			protected void handleArgument(OptionParser paramAnonymousOptionParser, ArgumentList paramAnonymousArgumentList, OptionSet paramAnonymousOptionSet)
			{
				paramAnonymousOptionSet.addNonOptionArgument(paramAnonymousArgumentList.next());
			}
		};
	}

	static OptionParserState moreOptions(boolean paramBoolean)
	{
		return new OptionParserState()
		{
			protected void handleArgument(OptionParser paramAnonymousOptionParser, ArgumentList paramAnonymousArgumentList, OptionSet paramAnonymousOptionSet)
			{
				String str = paramAnonymousArgumentList.next();
				if (ParserRules.isOptionTerminator(str))
				{
					paramAnonymousOptionParser.noMoreOptions();
				}
				else if (ParserRules.isLongOptionToken(str))
				{
					paramAnonymousOptionParser.handleLongOptionToken(str, paramAnonymousArgumentList, paramAnonymousOptionSet);
				}
				else if (ParserRules.isShortOptionToken(str))
				{
					paramAnonymousOptionParser.handleShortOptionToken(str, paramAnonymousArgumentList, paramAnonymousOptionSet);
				}
				else
				{
					//if (posixlyCorrect)
						paramAnonymousOptionParser.noMoreOptions();
					//paramAnonymousOptionSet.addNonOptionArgument(str);
				}
			}
		};
	}

	protected abstract void handleArgument(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet);
}