package nz.ac.auckland.cs.des;

import java.io.ByteArrayInputStream;

public class desArrayInputStream extends ByteArrayInputStream
{
  public desArrayInputStream(byte[] paramArrayOfByte, Key_schedule paramKey_schedule)
  {
    super(paramArrayOfByte);
    des_decrypt(paramKey_schedule, 0, paramArrayOfByte.length);
  }

  public desArrayInputStream(byte[] paramArrayOfByte, int paramInt1, int paramInt2, Key_schedule paramKey_schedule)
  {
    super(paramArrayOfByte);
    des_decrypt(paramKey_schedule, paramInt1, paramInt2);
  }

  void des_decrypt(Key_schedule paramKey_schedule, int paramInt1, int paramInt2)
  {
    des_encrypt localdes_encrypt = new des_encrypt(this.buf, paramInt1, paramInt2);
    localdes_encrypt.des_cbc_decrypt(paramKey_schedule);
    this.buf = localdes_encrypt.get_input();
    this.pos = 0;
    this.count = this.buf.length;
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     nz.ac.auckland.cs.des.desArrayInputStream
 * JD-Core Version:    0.6.2
 */