package nz.ac.auckland.cs.des;

import java.io.DataInputStream;
import java.io.IOException;

public class desDataInputStream extends DataInputStream
{
  desArrayInputStream d_in = (desArrayInputStream)this.in;

  public desDataInputStream(byte[] paramArrayOfByte, Key_schedule paramKey_schedule)
  {
    super(new desArrayInputStream(paramArrayOfByte, paramKey_schedule));
  }

  public desDataInputStream(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Key_schedule paramKey_schedule)
  {
    super(new desArrayInputStream(paramArrayOfByte, paramInt1, paramInt2, paramKey_schedule));
  }

  public C_Block readC_Block()
    throws IOException
  {
    byte[] arrayOfByte = new byte[C_Block.size()];
    read(arrayOfByte, 0, C_Block.size());
    return new C_Block(arrayOfByte);
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     nz.ac.auckland.cs.des.desDataInputStream
 * JD-Core Version:    0.6.2
 */