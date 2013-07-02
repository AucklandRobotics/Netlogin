package joptsimple;

class ArgumentList
{
  private final String[] arguments;
  private int currentIndex;

  ArgumentList(String[] paramArrayOfString)
  {
    this.arguments = ((String[])paramArrayOfString.clone());
  }

  boolean hasMore()
  {
    return this.currentIndex < this.arguments.length;
  }

  String next()
  {
    return this.arguments[(this.currentIndex++)];
  }

  String peek()
  {
    return this.arguments[this.currentIndex];
  }

  void treatNextAsLongOption()
  {
    if ('-' != this.arguments[this.currentIndex].charAt(0))
      this.arguments[this.currentIndex] = ("--" + this.arguments[this.currentIndex]);
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     joptsimple.ArgumentList
 * JD-Core Version:    0.6.2
 */