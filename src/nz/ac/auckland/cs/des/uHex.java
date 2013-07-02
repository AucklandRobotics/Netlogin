package nz.ac.auckland.cs.des;

class uHex
{
  public static String toHex(byte paramByte)
  {
    return "" + Character.forDigit(paramByte >>> 4 & 0xF, 16) + Character.forDigit(paramByte & 0xF, 16);
  }

  public static String toHex(byte[] paramArrayOfByte)
  {
    String str = "";
    for (int i = 0; i < paramArrayOfByte.length; i++)
      str = str + toHex(paramArrayOfByte[i]);
    return str;
  }

  public static String toHex(short paramShort)
  {
    return "" + Character.forDigit(paramShort >>> 12 & 0xF, 16) + Character.forDigit(paramShort >>> 8 & 0xF, 16) + Character.forDigit(paramShort >>> 4 & 0xF, 16) + Character.forDigit(paramShort & 0xF, 16);
  }

  public static String toHex(int paramInt)
  {
    return "" + Character.forDigit(paramInt >>> 28 & 0xF, 16) + Character.forDigit(paramInt >>> 24 & 0xF, 16) + Character.forDigit(paramInt >>> 20 & 0xF, 16) + Character.forDigit(paramInt >>> 16 & 0xF, 16) + Character.forDigit(paramInt >>> 12 & 0xF, 16) + Character.forDigit(paramInt >>> 8 & 0xF, 16) + Character.forDigit(paramInt >>> 4 & 0xF, 16) + Character.forDigit(paramInt & 0xF, 16);
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     nz.ac.auckland.cs.des.uHex
 * JD-Core Version:    0.6.2
 */