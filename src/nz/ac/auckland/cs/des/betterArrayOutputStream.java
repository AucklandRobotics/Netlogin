package nz.ac.auckland.cs.des;

import java.io.ByteArrayOutputStream;

public class betterArrayOutputStream extends ByteArrayOutputStream
{
  public betterArrayOutputStream()
  {
  }

  public betterArrayOutputStream(int paramInt)
  {
    super(paramInt);
  }

  public void resize(int paramInt)
  {
    if (paramInt < 0)
    {
      this.count += paramInt;
      if (this.count < 0)
        this.count = 0;
    }
    else if (paramInt > 0)
    {
      while (paramInt-- > 0)
        write(0);
    }
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     nz.ac.auckland.cs.des.betterArrayOutputStream
 * JD-Core Version:    0.6.2
 */