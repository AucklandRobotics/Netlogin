package joptsimple;

abstract interface OptionSpecVisitor
{
  public abstract void visit(NoArgumentOptionSpec paramNoArgumentOptionSpec);

  public abstract void visit(RequiredArgumentOptionSpec<?> paramRequiredArgumentOptionSpec);

  public abstract void visit(OptionalArgumentOptionSpec<?> paramOptionalArgumentOptionSpec);

  public abstract void visit(AlternativeLongOptionSpec paramAlternativeLongOptionSpec);
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.OptionSpecVisitor
 * JD-Core Version:    0.6.2
 */