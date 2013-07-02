package nz.ac.auckland.cs.des;

import java.io.DataOutputStream;
import java.io.IOException;

public class desDataOutputStream extends DataOutputStream
{
  betterArrayOutputStream d_out = (betterArrayOutputStream)this.out;

  public desDataOutputStream()
  {
    super(new betterArrayOutputStream(1024));
  }

  public desDataOutputStream(int paramInt)
  {
    super(new betterArrayOutputStream(paramInt));
  }

  public byte[] des_encrypt(Key_schedule paramKey_schedule)
  {
    des_encrypt localdes_encrypt = new des_encrypt(this.d_out.toByteArray());
    localdes_encrypt.des_cbc_encrypt(paramKey_schedule);
    return localdes_encrypt.get_input();
  }

  public byte[] toByteArray()
  {
    return this.d_out.toByteArray();
  }

  public void writeBytes(String paramString, int paramInt)
    throws IOException
  {
    writeBytes(paramString);
    this.d_out.resize(paramInt - paramString.length());
  }

  public void writeC_Block(C_Block paramC_Block)
    throws IOException
  {
    byte[] arrayOfByte = paramC_Block.getDataRef();
    write(arrayOfByte, 0, arrayOfByte.length);
  }

  public void reset()
  {
    this.d_out.reset();
  }

  public int length()
  {
    return this.d_out.size();
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     nz.ac.auckland.cs.des.desDataOutputStream
 * JD-Core Version:    0.6.2
 */