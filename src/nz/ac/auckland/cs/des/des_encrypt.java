package nz.ac.auckland.cs.des;

public class des_encrypt
{
  private C_Block[] input;
  private C_Block ivec;

  public des_encrypt(String paramString)
  {
    int i = (paramString.length() + 7) / 8;
    byte[] arrayOfByte = new byte[paramString.length()];
    paramString.getBytes(0, paramString.length() - 1, arrayOfByte, 0);
    init(arrayOfByte, 0, arrayOfByte.length, new C_Block());
  }

  public des_encrypt(String paramString, C_Block paramC_Block)
  {
    int i = (paramString.length() + 7) / 8;
    byte[] arrayOfByte = new byte[paramString.length()];
    paramString.getBytes(0, paramString.length() - 1, arrayOfByte, 0);
    init(arrayOfByte, 0, arrayOfByte.length, paramC_Block);
  }

  public des_encrypt(byte[] paramArrayOfByte)
  {
    init(paramArrayOfByte, 0, paramArrayOfByte.length, new C_Block());
  }

  public des_encrypt(byte[] paramArrayOfByte, C_Block paramC_Block)
  {
    init(paramArrayOfByte, 0, paramArrayOfByte.length, paramC_Block);
  }

  public des_encrypt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    init(paramArrayOfByte, paramInt1, paramInt2, new C_Block());
  }

  public des_encrypt(byte[] paramArrayOfByte, int paramInt1, int paramInt2, C_Block paramC_Block)
  {
    init(paramArrayOfByte, paramInt1, paramInt2, paramC_Block);
  }

  public void init(byte[] paramArrayOfByte, int paramInt1, int paramInt2, C_Block paramC_Block)
  {
    int i = ((paramInt2 > paramArrayOfByte.length ? paramArrayOfByte.length : paramInt2) + 7) / 8;
    this.input = new C_Block[i];
    for (int j = 0; j < i; j++)
      this.input[j] = new C_Block(paramArrayOfByte, j * 8 + paramInt1);
    this.ivec = ((C_Block)paramC_Block.clone());
  }

  public void set_ivec()
  {
    this.ivec = new C_Block();
  }

  public void set_ivec(C_Block paramC_Block)
  {
    this.ivec = ((C_Block)paramC_Block.clone());
  }

  public C_Block get_ivec()
  {
    return this.ivec;
  }

  public byte[] get_input()
  {
    byte[] arrayOfByte = new byte[this.input.length * 8];
    get_input(arrayOfByte);
    return arrayOfByte;
  }

  public void get_input(byte[] paramArrayOfByte)
    throws ArrayIndexOutOfBoundsException
  {
    if (paramArrayOfByte.length < this.input.length * 8)
      throw new ArrayIndexOutOfBoundsException("Not enough space for copy");
    int k = 0;
    for (int i = 0; i < this.input.length; i++)
      for (int j = 0; j < 8; j++)
        paramArrayOfByte[(k++)] = this.input[i].data[j];
  }

  public C_Block[] get_input_C_Block()
  {
    return this.input;
  }

  public String toString()
  {
    String str = "";
    for (int i = 0; i < this.input.length; i++)
      str = str + this.input[i].toString();
    return str;
  }

  public void des_cbc_encrypt(Key_schedule paramKey_schedule)
  {
    for (int i = 0; i < this.input.length; i++)
    {
      this.input[i].Xor(this.ivec);
      this.input[i] = this.input[i].des_ecb_encrypt(paramKey_schedule, true, false, false);
      this.ivec = ((C_Block)this.input[i].clone());
    }
  }

  public void des_cbc_decrypt(Key_schedule paramKey_schedule)
  {
    for (int i = 0; i < this.input.length; i++)
    {
      C_Block localC_Block = (C_Block)this.input[i].clone();
      this.input[i] = this.input[i].des_ecb_encrypt(paramKey_schedule, false, false, false);
      this.input[i].Xor(this.ivec);
      this.ivec = localC_Block;
    }
  }

  int des_cbc_cksum(Key_schedule paramKey_schedule)
  {
    des_cbc_encrypt(paramKey_schedule);
    return this.input[(this.input.length - 1)].second_int();
  }

  int get_last_des_cbc_cksum()
  {
    return this.input[(this.input.length - 1)].second_int();
  }

  public void des_pcbc_encrypt(Key_schedule paramKey_schedule)
  {
    for (int i = 0; i < this.input.length; i++)
    {
      int[] arrayOfInt = this.input[i].int_array();
      this.input[i].Xor(this.ivec);
      this.input[i] = this.input[i].des_ecb_encrypt(paramKey_schedule, true, false, false);
      this.ivec.int_to_char(arrayOfInt[0] ^ this.input[i].first_int(), arrayOfInt[1] ^ this.input[i].second_int());
    }
  }

  public void des_pcbc_decrypt(Key_schedule paramKey_schedule)
  {
    for (int i = 0; i < this.input.length; i++)
    {
      int[] arrayOfInt = this.input[i].int_array();
      this.input[i] = this.input[i].des_ecb_encrypt(paramKey_schedule, false, false, false);
      this.input[i].Xor(this.ivec);
      this.ivec.int_to_char(arrayOfInt[0] ^ this.input[i].first_int(), arrayOfInt[1] ^ this.input[i].second_int());
    }
  }

  public void epc_encrypt(Key_schedule paramKey_schedule)
  {
    Object localObject = (C_Block)this.ivec.clone();
    for (int i = 0; i < this.input.length; i++)
    {
      C_Block localC_Block = (C_Block)this.input[i].clone();
      this.input[i].Xor((C_Block)localObject);
      this.input[i] = this.input[i].des_ecb_encrypt(paramKey_schedule, true, false, false);
      localObject = localC_Block;
    }
  }

  public void epc_decrypt(Key_schedule paramKey_schedule)
  {
    C_Block localC_Block = (C_Block)this.ivec.clone();
    for (int i = 0; i < this.input.length; i++)
    {
      this.input[i] = this.input[i].des_ecb_encrypt(paramKey_schedule, false, false, false);
      this.input[i].Xor(localC_Block);
      localC_Block = (C_Block)this.input[i].clone();
    }
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     nz.ac.auckland.cs.des.des_encrypt
 * JD-Core Version:    0.6.2
 */