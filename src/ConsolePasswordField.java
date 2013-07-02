import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;

public class ConsolePasswordField
{
  public static final char[] getPassword(InputStream paramInputStream, String paramString)
    throws IOException
  {
    MaskingThread localMaskingThread = new MaskingThread(paramString);
    Thread localThread = new Thread(localMaskingThread);
    localThread.start();
    char localObject[];
    char arrayOfChar1[] = localObject = new char[128];
    int i = arrayOfChar1.length;
    int j = 0;
    while (true)
    {
      int k;
      switch (k = paramInputStream.read())
      {
      case -1:
      case 10:
        break;
      case 13:
        int m = paramInputStream.read();
        if ((m == 10) || (m == -1))
          break;
        if (!(paramInputStream instanceof PushbackInputStream))
          paramInputStream = new PushbackInputStream(paramInputStream);
        ((PushbackInputStream)paramInputStream).unread(m);
      default:
        i--;
        if (i < 0)
        {
          arrayOfChar1 = new char[j + 128];
          i = arrayOfChar1.length - j - 1;
          System.arraycopy(localObject, 0, arrayOfChar1, 0, j);
          Arrays.fill((char[])localObject, ' ');
          localObject = arrayOfChar1;
        }
        arrayOfChar1[(j++)] = ((char)k);
      }
    }
    localMaskingThread.stopMasking();
    if (j == 0)
      return null;
    char[] arrayOfChar2 = new char[j];
    System.arraycopy(arrayOfChar1, 0, arrayOfChar2, 0, j);
    Arrays.fill(arrayOfChar1, ' ');
    return arrayOfChar2;
  }
}

/* Location:           C:\Documents and Settings\Josh\Desktop\JNetLogin.jar
 * Qualified Name:     ConsolePasswordField
 * JD-Core Version:    0.6.2
 */