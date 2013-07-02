package nz.ac.auckland.cs.des;

public class des_hash_init
{
  public static Key_schedule des_hash_key1;
  public static Key_schedule des_hash_key2;
  static boolean des_hash_inited = false;

  public des_hash_init()
  {
    byte[] arrayOfByte1 = { -102, -45, -68, 36, 16, -30, -113, 14 };
    byte[] arrayOfByte2 = { -30, -107, 20, 51, 89, -61, -20, -88 };
    if (!des_hash_inited)
    {
      des_hash_key1 = new Key_schedule(new C_Block(arrayOfByte1));
      des_hash_key2 = new Key_schedule(new C_Block(arrayOfByte2));
      des_hash_inited = true;
    }
  }

  public Key_schedule get_Key_schedule1()
  {
    return des_hash_key1;
  }

  public Key_schedule get_Key_schedule2()
  {
    return des_hash_key2;
  }

  public String toString()
  {
    return des_hash_key1.toString() + " " + des_hash_key2.toString();
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     nz.ac.auckland.cs.des.des_hash_init
 * JD-Core Version:    0.6.2
 */